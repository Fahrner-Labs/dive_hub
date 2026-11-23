package dev.divehub.shared.write

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/** One field changed on both sides (ADR 0084 D3): a decision for the User, shown as "this phone" vs "the server". */
@Serializable
internal data class ConflictField(
    /** The API's field name: `notes`, `set.durationSeconds`, `reset.maxDepthM`, `participants.<diverId>`. */
    val field: String,
    val base: JsonElement? = null,
    val server: JsonElement? = null,
    val local: JsonElement? = null,
    /** For a reset the local side is "drop the override", which no JSON value expresses: `set`, `reset` or `value`. */
    val localAction: String? = null,
)

/** The quietly merged patch to re-send with the fresh version, and the fields that need the User (none = auto-merge). */
internal data class MergeOutcome(
    val patch: JsonObject,
    val conflicts: List<ConflictField>,
) {
    val hasConflicts: Boolean get() = conflicts.isNotEmpty()
}

/** A participants list merged element-wise by `diverId`, and the roster entries that need the User. */
internal data class ParticipantsMergeOutcome(
    val participants: JsonArray,
    val conflicts: List<ConflictField>,
) {
    val hasConflicts: Boolean get() = conflicts.isNotEmpty()
}

/**
 * The client-side three-way merge (ADR 0084 D3, §4.1): pure JSON in and out, so one engine serves the queue's replay,
 * the 410 resync rebase and (later) iOS. The base is the stored payload the edit started from; the server is the fresh
 * view a 412 carried; the local side is the queued PATCH body.
 *
 * Field classes: scalars and their overrides use the classic three-way rule, `suit` and `weightingFeedback` are one
 * field each, `cylinders` and `lead` are whole-list fields, and `participants` merges element-wise by `diverId` because
 * the joint-dive union adds them server-side as a matter of course (ADR 0082).
 */
internal object DiveMerge {
    private val DIRECT_FIELDS = listOf("notes", "siteId", "suit", "weightingFeedback", "cylinders", "lead")

    fun mergeDive(base: JsonObject, server: JsonObject, patch: JsonObject): MergeOutcome {
        val merged = mutableMapOf<String, JsonElement>()
        val conflicts = mutableListOf<ConflictField>()

        for (field in DIRECT_FIELDS) {
            if (!patch.containsKey(field)) continue
            val local = patch[field] ?: JsonNull
            val baseValue = directValue(base, field)
            val serverValue = directValue(server, field)
            when {
                jsonEquals(local, baseValue) -> Unit
                jsonEquals(serverValue, baseValue) -> merged[field] = local
                jsonEquals(local, serverValue) -> Unit
                else -> conflicts += ConflictField(field, baseValue, serverValue, local, "value")
            }
        }

        val baseValues = base["values"] as? JsonObject
        val serverValues = server["values"] as? JsonObject
        val baseOverrides = overrides(base)
        val serverOverrides = overrides(server)
        val set = mutableMapOf<String, JsonElement>()
        val reset = linkedSetOf<String>()

        val patchSet = patch["set"] as? JsonObject
        patchSet?.forEach { (field, local) ->
            val baseValue = baseValues?.get(field)
            val serverValue = serverValues?.get(field)
            val baseHad = field in baseOverrides
            val serverHas = field in serverOverrides
            val localChanged = !jsonEquals(local, baseValue)
            val serverChanged = baseHad != serverHas || !jsonEquals(serverValue, baseValue)
            when {
                !localChanged -> Unit
                !serverChanged -> set[field] = local
                // Both sides want the same effective value: nothing left to send.
                jsonEquals(local, serverValue) -> Unit
                else -> conflicts += ConflictField("set.$field", baseValue, serverValue, local, "set")
            }
        }

        val patchReset = patch["reset"] as? JsonArray
        patchReset?.forEach { element ->
            val field = (element as? JsonPrimitive)?.content ?: return@forEach
            if (patchSet?.containsKey(field) == true) return@forEach
            val baseValue = baseValues?.get(field)
            val serverValue = serverValues?.get(field)
            val baseHad = field in baseOverrides
            val serverHas = field in serverOverrides
            val serverChanged = baseHad != serverHas || !jsonEquals(serverValue, baseValue)
            when {
                // Without an Override a reset changes nothing; a form that sends the whole list must not conflict.
                !baseHad -> Unit
                !serverChanged -> reset += field
                // The server also dropped the override: both sides want the recording's value again.
                !serverHas -> Unit
                else -> conflicts += ConflictField("reset.$field", baseValue, serverValue, null, "reset")
            }
        }

        if (set.isNotEmpty()) merged["set"] = JsonObject(set)
        if (reset.isNotEmpty()) merged["reset"] = JsonArray(reset.map { JsonPrimitive(it) })
        return MergeOutcome(JsonObject(merged), conflicts)
    }

    /**
     * Participants by `diverId` (ADR 0056's removal semantics, widened to the device in ADR 0084): a side that left the
     * element alone takes what the other side says; a removal against an untouched element removes it; a removal against
     * a role change, and a role changed differently on both sides, are conflicts.
     */
    fun mergeParticipants(base: JsonObject, server: JsonObject, local: JsonArray): ParticipantsMergeOutcome {
        val baseRoles = roles(base["participants"])
        val serverRoles = roles(server["participants"])
        val localRoles = roles(local)
        val conflicts = mutableListOf<ConflictField>()
        val merged = LinkedHashMap<String, String>()

        fun decide(diverId: String) {
            val fromBase = baseRoles[diverId]
            val fromServer = serverRoles[diverId]
            val fromLocal = localRoles[diverId]
            when {
                fromLocal == fromBase -> fromServer?.let { merged[diverId] = it }
                fromLocal == fromServer -> fromLocal?.let { merged[diverId] = it }
                fromServer == fromBase -> fromLocal?.let { merged[diverId] = it }
                fromLocal == null -> conflicts += ConflictField(
                    field = "participants.$diverId",
                    base = fromBase?.let { JsonPrimitive(it) },
                    server = fromServer?.let { JsonPrimitive(it) },
                    local = null,
                    localAction = "remove",
                )
                fromServer == null -> conflicts += ConflictField(
                    field = "participants.$diverId",
                    base = fromBase?.let { JsonPrimitive(it) },
                    server = null,
                    local = JsonPrimitive(fromLocal),
                    localAction = "set",
                )
                else -> conflicts += ConflictField(
                    field = "participants.$diverId",
                    base = fromBase?.let { JsonPrimitive(it) },
                    server = JsonPrimitive(fromServer),
                    local = JsonPrimitive(fromLocal),
                    localAction = "set",
                )
            }
        }

        localRoles.keys.forEach(::decide)
        serverRoles.keys.filterNot { it in localRoles }.forEach(::decide)

        val participants = JsonArray(
            merged.map { (diverId, role) ->
                buildJsonObject {
                    put("diverId", diverId)
                    put("role", role)
                }
            },
        )
        return ParticipantsMergeOutcome(participants, conflicts)
    }

    private fun directValue(json: JsonObject, field: String): JsonElement? = when (field) {
        "siteId" -> (json["site"] as? JsonObject)?.get("id")
        else -> json[field]
    }

    private fun overrides(json: JsonObject): Set<String> =
        (json["overrides"] as? JsonArray)
            ?.mapNotNull { (it as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content }
            ?.toSet()
            .orEmpty()

    private fun roles(element: JsonElement?): Map<String, String> {
        val array = element as? JsonArray ?: return emptyMap()
        val result = LinkedHashMap<String, String>()
        for (item in array) {
            val entry = item as? JsonObject ?: continue
            val diverId = (entry["diverId"] as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content ?: continue
            val role = (entry["role"] as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content ?: continue
            result[diverId] = role
        }
        return result
    }

    /**
     * JSON equality with the server's own normalisation in mind: an absent nullable and an explicit `null` are the same
     * unknown, object key order never matters, and `18` equals `18.0` (a replay re-encodes numbers the server sent).
     */
    private fun jsonEquals(a: JsonElement?, b: JsonElement?): Boolean {
        if (a == null || a is JsonNull) return b == null || b is JsonNull
        if (b == null || b is JsonNull) return false
        if (a is JsonPrimitive && b is JsonPrimitive) {
            if (a.isString || b.isString) return a.isString == b.isString && a.content == b.content
            val aNumber = a.content.toDoubleOrNull()
            val bNumber = b.content.toDoubleOrNull()
            if (aNumber != null && bNumber != null) return aNumber == bNumber
            return a.content == b.content
        }
        if (a is JsonObject && b is JsonObject) {
            if (a.keys != b.keys) return false
            return a.keys.all { jsonEquals(a[it], b[it]) }
        }
        if (a is JsonArray && b is JsonArray) {
            if (a.size != b.size) return false
            return a.indices.all { jsonEquals(a[it], b[it]) }
        }
        return a == b
    }
}
