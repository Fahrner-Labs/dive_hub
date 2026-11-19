// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLeadEstimateGet200ResponseLeftOut(
    /** Dives with lead and a suit that could not be used: no site, or a site without a water type (ADR 0025) */
    @SerialName("noWaterType") val noWaterType: Int
)
