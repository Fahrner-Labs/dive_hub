// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsGasPlanPost200ResponseSegmentsItem(
    @SerialName("kind") val kind: String,
    @SerialName("fromDepthM") val fromDepthM: Double,
    /** Where it ends; the same as fromDepthM for a level and the stop */
    @SerialName("depthM") val depthM: Double,
    @SerialName("minutes") val minutes: Double,
    /** Mean over the stretch */
    @SerialName("ambientBar") val ambientBar: Double
)
