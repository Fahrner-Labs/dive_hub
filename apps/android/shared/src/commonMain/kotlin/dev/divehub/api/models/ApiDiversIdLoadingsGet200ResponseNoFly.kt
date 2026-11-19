// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLoadingsGet200ResponseNoFly(
    /** DAN's guideline: 12 after a single dive without stops, 18 after several dives or days of diving, 24 after decompression */
    @SerialName("hours") val hours: Int,
    @SerialName("reason") val reason: String,
    @SerialName("until") val until: String,
    @SerialName("source") val source: ApiDiversIdLoadingsGet200ResponseNoFlySource
)
