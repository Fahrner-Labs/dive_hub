// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewValues(
    @SerialName("number") val number: Int?,
    @SerialName("startsAt") val startsAt: DiveViewValuesStartsAt,
    @SerialName("durationSeconds") val durationSeconds: Double,
    @SerialName("maxDepthM") val maxDepthM: Double?,
    @SerialName("avgDepthM") val avgDepthM: Double?,
    @SerialName("waterTemperatureC") val waterTemperatureC: Double?
)
