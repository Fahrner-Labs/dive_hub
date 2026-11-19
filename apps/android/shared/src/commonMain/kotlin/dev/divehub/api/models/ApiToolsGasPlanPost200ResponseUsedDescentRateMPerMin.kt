// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsGasPlanPost200ResponseUsedDescentRateMPerMin(
    @SerialName("value") val value: Double,
    /** Where the value came from: the request, the Diver's latest Recording with a GF, the median of their Recordings' SAC, the site, or the stated default */
    @SerialName("source") val source: String
)
