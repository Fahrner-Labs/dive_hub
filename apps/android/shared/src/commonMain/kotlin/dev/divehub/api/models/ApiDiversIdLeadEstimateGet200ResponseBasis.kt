// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLeadEstimateGet200ResponseBasis(
    @SerialName("kind") val kind: String,
    @SerialName("dives") val dives: List<ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem>? = null,
    /** In the answer's `unit` */
    @SerialName("personal") val personal: Double? = null,
    /** In the answer's `unit` */
    @SerialName("rule") val rule: Double? = null,
    @SerialName("personalOffset") val personalOffset: Double? = null,
    @SerialName("offsetFromSuits") val offsetFromSuits: Int? = null
)
