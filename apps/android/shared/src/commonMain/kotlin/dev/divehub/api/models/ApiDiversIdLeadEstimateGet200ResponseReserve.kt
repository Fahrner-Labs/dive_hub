// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLeadEstimateGet200ResponseReserve(
    /** The cylinder pressure the estimate is for: neutral at 5 m with this much left */
    @SerialName("bar") val bar: Double,
    /** The median of the Diver's logged end pressures, else 50 bar */
    @SerialName("fromDives") val fromDives: Boolean
)
