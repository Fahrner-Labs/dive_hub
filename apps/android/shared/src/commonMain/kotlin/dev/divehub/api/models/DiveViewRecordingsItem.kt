// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewRecordingsItem(
    @SerialName("id") val id: String,
    @SerialName("isPrimary") val isPrimary: Boolean,
    @SerialName("device") val device: DiveViewRecordingsItemDevice?,
    @SerialName("startsAt") val startsAt: String,
    @SerialName("durationSeconds") val durationSeconds: Double,
    @SerialName("maxDepthM") val maxDepthM: Double?,
    @SerialName("parser") val parser: String,
    @SerialName("partOf") val partOf: String?,
    @SerialName("summary") val summary: DiveViewRecordingsItemSummary,
    @SerialName("channels") val channels: List<String>
)
