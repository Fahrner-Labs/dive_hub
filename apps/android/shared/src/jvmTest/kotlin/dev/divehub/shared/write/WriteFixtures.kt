package dev.divehub.shared.write

import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal const val NOW = "2026-11-23T10:00:00Z"
internal val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }

internal fun jsonOf(text: String): JsonObject = JSON.parseToJsonElement(text) as JsonObject

/** A minimal but complete DiveView: every non-null field the generated serializer requires. */
internal fun diveObject(
    id: String = "dive-1",
    version: Int = 3,
    notes: String? = "A good dive",
    durationSeconds: Double = 3000.0,
    maxDepthM: Double? = 24.3,
    overrides: List<String> = emptyList(),
    startsAt: String = "2026-11-01T10:00:00Z",
    siteId: String? = "site-1",
    suit: JsonElement? = null,
    weightingFeedback: JsonElement? = null,
    cylinders: JsonArray = JsonArray(emptyList()),
    lead: JsonArray = JsonArray(emptyList()),
    participants: JsonArray = JsonArray(emptyList()),
): JsonObject = buildJsonObject {
    put("id", id)
    put("diverId", "diver-1")
    put("version", version)
    put(
        "values",
        buildJsonObject {
            put("number", 42)
            put(
                "startsAt",
                buildJsonObject {
                    put("at", startsAt)
                    put("utcOffsetSeconds", 3600)
                },
            )
            put("durationSeconds", durationSeconds)
            put("maxDepthM", maxDepthM)
            put("avgDepthM", 12.0)
            put("waterTemperatureC", 18.0)
        },
    )
    put("overrides", JsonArray(overrides.map { JsonPrimitive(it) }))
    put("utcOffsetSource", "device")
    put("fromProvider", JsonNull)
    put("fromRecording", JsonNull)
    put("notes", notes?.let { JsonPrimitive(it) } ?: JsonNull)
    put(
        "site",
        siteId?.let {
            buildJsonObject {
                put("id", it)
                put("name", "Reef")
            }
        } ?: JsonNull,
    )
    put("waterType", "salt")
    put("waterMismatch", JsonNull)
    put("position", JsonNull)
    put("recordings", JsonArray(emptyList()))
    put("cylinders", cylinders)
    put("lead", lead)
    put("leadTotalKg", 4.0)
    put("weightingFeedback", weightingFeedback ?: JsonNull)
    put("suit", suit ?: JsonNull)
    put("sac", JsonNull)
    put("sacMissing", JsonNull)
    put("participants", participants)
    put("verificationCodes", JsonArray(emptyList()))
}

/** Put a Dive row in the store from an API payload, as the sync would: replay only needs its version and payload. */
internal fun DiveStore.putDive(detail: JsonObject, version: Long? = 3) {
    upsertDive(
        Dive(
            id = (detail["id"] as JsonPrimitive).content,
            diverId = (detail["diverId"] as JsonPrimitive).content,
            version = version,
            startsAt = "2026-11-01T10:00:00Z",
            detailJson = detail.toString(),
        ),
    )
}

internal fun participant(diverId: String, role: String): JsonObject = buildJsonObject {
    put("diverId", diverId)
    put("name", diverId)
    put("role", role)
}

internal fun participants(vararg pairs: Pair<String, String>): JsonArray =
    JsonArray(pairs.map { (diverId, role) -> participant(diverId, role) })
