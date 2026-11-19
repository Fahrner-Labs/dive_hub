// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem(
    @SerialName("depthM") val depthM: Double,
    @SerialName("pressureBar") val pressureBar: Double
)
