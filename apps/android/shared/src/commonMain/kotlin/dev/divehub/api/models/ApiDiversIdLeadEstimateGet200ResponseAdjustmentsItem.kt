// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem(
    @SerialName("kind") val kind: String,
    /** In the answer's `unit` */
    @SerialName("amount") val amount: Double,
    @SerialName("water") val water: String? = null
)
