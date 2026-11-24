package dev.divehub.shared.write

import dev.divehub.api.models.DiveView
import dev.divehub.shared.planning.GasMix
import dev.divehub.shared.planning.SacCylinder
import dev.divehub.shared.planning.SacDive
import dev.divehub.shared.planning.sacOfDive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.sync.writeDiveDetail
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.doubleOrNull
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
    private val DIRECT_FIELDS = listOf("notes", "suit", "weightingFeedback")

    fun apply(store: DiveStore, diveId: String, patch: JsonObject) {
        val detail = detailOf(store, diveId) ?: return
        val result = detail.toMutableMap()
        for (field in DIRECT_FIELDS) {
            if (patch.containsKey(field)) result[field] = patch[field] ?: JsonNull
        }
        if (patch.containsKey("cylinders")) {
            result["cylinders"] = cylindersWithPod(detail["cylinders"], patch["cylinders"])
        }
        if (patch.containsKey("lead")) {
            val lead = patch["lead"] as? JsonArray ?: JsonArray(emptyList())
            result["lead"] = lead
            result["leadTotalKg"] = leadTotal(lead)
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
        // SAC is computed from the Cylinders: an edited list must not leave the page showing the old figure.
        if (patch.containsKey("cylinders")) {
            sacFor(result["values"], result["cylinders"])?.let { (sac, missing) ->
                result["sac"] = sac
                result["sacMissing"] = missing
            }
        }

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

    /**
     * The local answer to a Cylinder edit keeps the tank-pod mark (the patch never carries `fromPod`, which is the
     * server's own description of an import): a Cylinder tied to the same series stays marked, a new one is not.
     */
    private fun cylindersWithPod(before: JsonElement?, after: JsonElement?): JsonArray {
        val old = (before as? JsonArray)?.mapNotNull { it as? JsonObject }.orEmpty()
        val next = (after as? JsonArray)?.mapNotNull { it as? JsonObject }.orEmpty()
        return JsonArray(
            next.map { item ->
                val pod = old.firstOrNull { sameSeries(it, item) }?.get("fromPod") as? JsonPrimitive
                JsonObject(
                    item.toMutableMap().apply {
                        put("fromPod", pod?.takeIf { it.booleanOrNull != null } ?: JsonPrimitive(false))
                    },
                )
            },
        )
    }

    private fun sameSeries(left: JsonObject, right: JsonObject): Boolean {
        val leftSeries = left["series"] as? JsonObject ?: return false
        val rightSeries = right["series"] as? JsonObject ?: return false
        val leftRecording = (leftSeries["recordingId"] as? JsonPrimitive)?.content ?: return false
        val rightRecording = (rightSeries["recordingId"] as? JsonPrimitive)?.content ?: return false
        val leftChannel = (leftSeries["channel"] as? JsonPrimitive)?.content ?: return false
        val rightChannel = (rightSeries["channel"] as? JsonPrimitive)?.content ?: return false
        return leftRecording == rightRecording && leftChannel == rightChannel
    }

    /** The SAC the server would compute from the edited Cylinders (ADR 0045), as the dive page reads it. */
    private fun sacFor(values: JsonElement?, cylinders: JsonElement?): Pair<JsonElement, JsonElement>? {
        val valueObject = values as? JsonObject ?: return null
        val durationSeconds = number(valueObject["durationSeconds"]) ?: return null
        val avgDepthM = number(valueObject["avgDepthM"])
        val list = (cylinders as? JsonArray).orEmpty().mapNotNull { element ->
            val cylinder = element as? JsonObject ?: return@mapNotNull null
            val gas = cylinder["gas"] as? JsonObject
            SacCylinder(
                volumeL = number(cylinder["volumeL"]),
                startPressureBar = number(cylinder["startPressureBar"]),
                endPressureBar = number(cylinder["endPressureBar"]),
                gas = gas?.let { GasMix(number(it["o2"]) ?: 21.0, number(it["he"]) ?: 0.0) },
            )
        }
        val outcome = sacOfDive(SacDive(durationSeconds, avgDepthM), list)
        val sac = outcome.sac?.let { values ->
            buildJsonObject {
                put("litresPerMinute", values.litresPerMinute)
                values.barPerMinute?.let { put("barPerMinute", it) }
            }
        } ?: JsonNull
        val missing = outcome.missing?.let { JsonPrimitive(it.name) } ?: JsonNull
        return sac to missing
    }

    private fun number(element: JsonElement?): Double? = (element as? JsonPrimitive)?.doubleOrNull

    /** The server's `leadTotalKg`: null when there are no entries, the rounded sum otherwise. */
    private fun leadTotal(lead: JsonArray): JsonElement {
        if (lead.isEmpty()) return JsonNull
        val total = lead.sumOf { element ->
            val entry = element as? JsonObject ?: return@sumOf 0.0
            (entry["amountKg"] as? JsonPrimitive)?.content?.toDoubleOrNull() ?: 0.0
        }
        return JsonPrimitive(kotlin.math.round(total * 100) / 100)
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
