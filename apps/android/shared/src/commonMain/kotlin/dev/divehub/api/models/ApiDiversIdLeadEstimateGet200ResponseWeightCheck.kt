// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The estimate is a starting point, never a substitute for this
 */
@Serializable
data class ApiDiversIdLeadEstimateGet200ResponseWeightCheck(
    /** Always both, in this order; clients show them with every answer */
    @SerialName("steps") val steps: List<String>,
    /** The pressure for the second step: the same as `reserve.bar` */
    @SerialName("reserveBar") val reserveBar: Double
)
