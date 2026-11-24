package dev.divehub.shared.write

import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWrite
import dev.divehub.shared.store.PendingWriteKind
import dev.divehub.shared.store.PendingWriteStatus
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlin.time.Clock

/** The entity types the Phase B queue knows: a Dive PATCH and its Participants PUT (ADR 0084 D4, T8c). */
object WriteEntities {
    const val DIVE = "dive"
    const val DIVE_PARTICIPANTS = "dive_participants"
}

/**
 * The enqueue side of the offline write queue (ADR 0084 D4): a write lands here with the version it started from, and
 * [editDive] / [editDiveParticipants] put it in the store at once ("write as if online"). Repeated edits to one entity coalesce into one entry — the latest field values, the
 * *original* base version — so the User sees "1 change waiting", not seven.
 *
 * A patch's base payload is captured at enqueue (`baseJson`), not read from the store at replay: a feed refresh may
 * have moved the row meanwhile, and the three-way merge must still know what the edit was made against.
 */
class WriteQueue(
    private val store: DiveStore,
    private val clock: () -> String = { Clock.System.now().toString() },
    /** The app's queue indicator reads this after a write lands; the store itself has no screen revision. */
    private val onEnqueued: () -> Unit = {},
) {
    /**
     * Apply a Dive edit to the store at once ("write as if online") and queue it. The store keeps the base version; the
     * replay's 2xx replaces the detail with the server's answer (ADR 0084 D3 step 2).
     */
    fun editDive(diveId: String, patch: JsonObject): PendingWrite {
        val entry = enqueueDivePatch(diveId, patch)
        DiveLocalEdit.apply(store, diveId, patch)
        return entry
    }

    /** Queue a Dive PATCH body (`notes`, `siteId`, `set`, `reset`, `suit`, `weightingFeedback`, `cylinders`, `lead`). */
    fun enqueueDivePatch(diveId: String, patch: JsonObject): PendingWrite {
        require(patch.isNotEmpty()) { "An empty Dive patch is not a write" }
        val (version, baseJson) = baseOf(diveId)
        return enqueuePatch(WriteEntities.DIVE, diveId, patch, version, baseJson)
    }

    /** Apply the Participants edit to the store at once and queue it as `PUT /api/dives/{id}/participants` (T8c). */
    fun editDiveParticipants(diveId: String, participants: JsonArray): PendingWrite {
        val entry = enqueueDiveParticipants(diveId, participants)
        DiveLocalEdit.applyParticipants(store, diveId, participants)
        return entry
    }

    /** Queue the whole Participants list as `PUT /api/dives/{id}/participants` (ADR 0082 joint dives, T8c). */
    fun enqueueDiveParticipants(diveId: String, participants: JsonArray): PendingWrite {
        val (version, baseJson) = baseOf(diveId)
        val patch = JsonObject(mapOf("participants" to roster(participants)))
        return enqueuePatch(WriteEntities.DIVE_PARTICIPANTS, diveId, patch, version, baseJson)
    }

    /**
     * Queue one patch against an entity, coalescing into the entry already waiting for it. The first edit's base
     * version and base payload stay: the replay must answer with the version the *change* started from, not the
     * version after a local edit that never reached the server.
     */
    fun enqueuePatch(
        entityType: String,
        entityId: String,
        patch: JsonObject,
        baseVersion: Long,
        baseJson: String?,
    ): PendingWrite {
        require(patch.isNotEmpty()) { "An empty patch is not a write" }
        val existing = store.pendingPatch(entityType, entityId)
        if (existing != null) {
            val merged = mergePatches(jsonObject(existing.payloadJson), patch)
            val updated = existing.copy(
                payloadJson = merged.toString(),
                // The original base version wins; a store row cannot have a newer version for an unplayed edit.
                baseVersion = existing.baseVersion ?: baseVersion,
                baseJson = existing.baseJson ?: baseJson,
                status = PendingWriteStatus.Pending,
                lastError = null,
                conflictJson = null,
            )
            store.updatePendingWrite(updated)
            onEnqueued()
            return updated
        }
        return store.addPendingWrite(
            PendingWrite(
                id = 0,
                kind = PendingWriteKind.Patch,
                entityType = entityType,
                entityId = entityId,
                payloadJson = patch.toString(),
                enqueuedAt = clock(),
                baseVersion = baseVersion,
                baseJson = baseJson,
            ),
        ).also { onEnqueued() }
    }

    /**
     * Queue a deferred action (Phase C: a scanned code, a create). It replays verbatim in global enqueue order and
     * never merges; T6a only stores it, the scanner flows arrive in T11.
     */
    fun enqueueAction(entityType: String, entityId: String, payload: JsonObject): PendingWrite = store.addPendingWrite(
        PendingWrite(
            id = 0,
            kind = PendingWriteKind.Action,
            entityType = entityType,
            entityId = entityId,
            payloadJson = payload.toString(),
            enqueuedAt = clock(),
        ),
    ).also { onEnqueued() }

    private fun baseOf(diveId: String): Pair<Long, String?> {
        val dive = store.diveIncludingDeleted(diveId)
            ?: throw IllegalArgumentException("Dive $diveId is not in the store")
        val version = dive.version
            ?: throw IllegalArgumentException("Dive $diveId has no server version to queue against")
        return version to dive.detailJson
    }

    /**
     * The wire roster is exactly `{diverId, role}` (the endpoint refuses extra properties); a UI list may carry the
     * Diver's name for display, and the queue drops it rather than sending it.
     */
    private fun roster(participants: JsonArray): JsonArray = JsonArray(
        participants.mapNotNull { element ->
            val entry = element as? JsonObject ?: return@mapNotNull null
            val diverId = entry["diverId"] as? JsonPrimitive ?: return@mapNotNull null
            val role = entry["role"] as? JsonPrimitive ?: return@mapNotNull null
            buildJsonObject {
                put("diverId", diverId)
                put("role", role)
            }
        },
    )

    private fun jsonObject(payload: String): JsonObject =
        Json.parseToJsonElement(payload) as? JsonObject ?: JsonObject(emptyMap())

    /**
     * Coalesce two PATCH bodies field by field: top-level fields take the newer value (an explicit `null` included),
     * `set` merges per field, `reset` unions, and a newer `set`/`reset` for the same field clears the older one.
     */
    private fun mergePatches(existing: JsonObject, next: JsonObject): JsonObject {
        val merged = existing.toMutableMap()
        val currentSet = (merged["set"] as? JsonObject)?.toMutableMap() ?: mutableMapOf()
        val currentReset = (merged["reset"] as? JsonArray)
            ?.mapNotNull { (it as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content }
            ?.toMutableSet()
            ?: linkedSetOf<String>()

        (next["set"] as? JsonObject)?.forEach { (field, value) ->
            currentSet[field] = value
            currentReset.remove(field)
        }
        (next["reset"] as? JsonArray)?.forEach { element ->
            val field = (element as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content ?: return@forEach
            currentReset.add(field)
            currentSet.remove(field)
        }
        next.forEach { (key, value) ->
            if (key != "set" && key != "reset") merged[key] = value
        }

        if (currentSet.isEmpty()) merged.remove("set") else merged["set"] = JsonObject(currentSet)
        if (currentReset.isEmpty()) merged.remove("reset") else merged["reset"] = JsonArray(currentReset.map(::JsonPrimitive))
        return JsonObject(merged)
    }
}
