package dev.divehub.shared.write

import dev.divehub.api.models.DiveView
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.sync.writeDiveDetail
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * "Write as if online" (ADR 0084 D4): the queued edit lands in the store immediately, so the logbook and the dive page
 * show the User's intent without waiting for a replay. The row keeps its base `version`; the replay's 2xx replaces the
 * whole detail with the server's authoritative answer.
 *
 * A reset drops the Override and keeps the value it had until the replay answers: the value a Recording gives without
 * an override is the server's to compute (ADR 0015), not something the payload holds.
 */
internal object DiveLocalEdit {
    private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }
    private val DIRECT_FIELDS = listOf("notes", "suit", "weightingFeedback", "cylinders", "lead")

    fun apply(store: DiveStore, diveId: String, patch: JsonObject) {
        val detail = detailOf(store, diveId) ?: return
        val result = detail.toMutableMap()
        for (field in DIRECT_FIELDS) {
            if (patch.containsKey(field)) result[field] = patch[field] ?: JsonNull
        }
        if (patch.containsKey("siteId")) {
            result["site"] = siteOf(store, patch["siteId"])
        }

        val values = (detail["values"] as? JsonObject)?.toMutableMap() ?: mutableMapOf()
        val overrides = (detail["overrides"] as? JsonArray)
            ?.mapNotNull { (it as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content }
            ?.toMutableSet()
            ?: linkedSetOf()

        (patch["set"] as? JsonObject)?.forEach { (field, value) ->
            values[field] = value
            overrides.add(field)
        }
        (patch["reset"] as? JsonArray)?.forEach { element ->
            (element as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content?.let(overrides::remove)
        }
        result["values"] = JsonObject(values)
        result["overrides"] = JsonArray(overrides.map(::JsonPrimitive))

        write(store, JsonObject(result))
    }

    fun applyParticipants(store: DiveStore, diveId: String, participants: JsonArray) {
        val detail = detailOf(store, diveId) ?: return
        val roster = participants.mapNotNull { element ->
            val entry = element as? JsonObject ?: return@mapNotNull null
            val diverId = (entry["diverId"] as? JsonPrimitive)?.takeIf { it.isString }?.content ?: return@mapNotNull null
            val role = (entry["role"] as? JsonPrimitive)?.takeIf { it.isString }?.content ?: return@mapNotNull null
            buildJsonObject {
                put("diverId", diverId)
                put("name", store.diver(diverId)?.name ?: diverId)
                put("role", role)
            }
        }
        val result = detail.toMutableMap()
        result["participants"] = JsonArray(roster)
        write(store, JsonObject(result))
    }

    private fun detailOf(store: DiveStore, diveId: String): JsonObject? {
        val payload = store.diveIncludingDeleted(diveId)?.detailJson ?: return null
        return runCatching { JSON.parseToJsonElement(payload) as? JsonObject }.getOrNull()
    }

    private fun siteOf(store: DiveStore, element: kotlinx.serialization.json.JsonElement?): kotlinx.serialization.json.JsonElement {
        val id = (element as? JsonPrimitive)?.takeIf { it.isString }?.content ?: return JsonNull
        return buildJsonObject {
            put("id", id)
            put("name", store.diveSite(id)?.name ?: id)
        }
    }

    private fun write(store: DiveStore, detail: JsonObject) {
        val view = runCatching { JSON.decodeFromString(DiveView.serializer(), detail.toString()) }.getOrNull() ?: return
        store.transaction { store.writeDiveDetail(view) }
        // `view.version` is the edit's base version, so writeDiveDetail keeps it: the server has not seen this edit yet.
    }
}
