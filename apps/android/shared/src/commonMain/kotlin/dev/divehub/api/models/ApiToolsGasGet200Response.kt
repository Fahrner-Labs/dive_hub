// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsGasGet200Response(
    @SerialName("gas") val gas: ApiToolsGasGet200ResponseGas,
    /** Under 18 % oxygen at the surface */
    @SerialName("hypoxic") val hypoxic: Boolean,
    @SerialName("ppO2Limits") val ppO2Limits: ApiToolsGasGet200ResponsePpO2Limits,
    /** Rounded down to 0.1 m */
    @SerialName("modWorkingM") val modWorkingM: Double,
    /** Rounded down to 0.1 m */
    @SerialName("modContingencyM") val modContingencyM: Double,
    /** Null without a depth */
    @SerialName("atDepth") val atDepth: ApiToolsGasGet200ResponseAtDepth?,
    @SerialName("conditions") val conditions: ApiToolsGasGet200ResponseConditions,
    @SerialName("oxygenNarcotic") val oxygenNarcotic: Boolean
)
