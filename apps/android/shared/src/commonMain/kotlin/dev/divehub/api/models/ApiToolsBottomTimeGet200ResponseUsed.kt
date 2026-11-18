// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsBottomTimeGet200ResponseUsed(
    @SerialName("gfHigh") val gfHigh: ApiToolsBottomTimeGet200ResponseUsedGfHigh,
    @SerialName("sacLpm") val sacLpm: ApiToolsBottomTimeGet200ResponseUsedSacLpm,
    @SerialName("water") val water: ApiToolsBottomTimeGet200ResponseUsedWater,
    @SerialName("altitudeM") val altitudeM: ApiToolsBottomTimeGet200ResponseUsedAltitudeM,
    @SerialName("gasRule") val gasRule: ApiToolsBottomTimeGet200ResponseUsedGasRule
)
