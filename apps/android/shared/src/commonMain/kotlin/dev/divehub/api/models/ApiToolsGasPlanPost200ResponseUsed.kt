// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsGasPlanPost200ResponseUsed(
    @SerialName("water") val water: ApiToolsGasPlanPost200ResponseUsedWater,
    @SerialName("altitudeM") val altitudeM: ApiToolsGasPlanPost200ResponseUsedAltitudeM,
    @SerialName("gasRule") val gasRule: ApiToolsGasPlanPost200ResponseUsedGasRule,
    @SerialName("descentRateMPerMin") val descentRateMPerMin: ApiToolsGasPlanPost200ResponseUsedDescentRateMPerMin,
    @SerialName("ascentRateMPerMin") val ascentRateMPerMin: ApiToolsGasPlanPost200ResponseUsedAscentRateMPerMin
)
