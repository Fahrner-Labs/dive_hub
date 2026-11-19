// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLoadingsGet200ResponseCns(
    /** The latest Recording's CNS, decayed to now with the 90-minute surface half-life */
    @SerialName("percentNow") val percentNow: Double,
    /** The computer's own CNS at the end of that Dive */
    @SerialName("percentAtLastDiveEnd") val percentAtLastDiveEnd: Double,
    @SerialName("halfLifeMinutes") val halfLifeMinutes: Int
)
