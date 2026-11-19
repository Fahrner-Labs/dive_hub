// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Always shown with the total; null together with it
 */
@Serializable
data class ApiDiversIdLeadEstimateGet200ResponseRange(
    @SerialName("low") val low: Double,
    @SerialName("high") val high: Double
)
