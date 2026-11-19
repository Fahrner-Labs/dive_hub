// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLeadEstimateGet200Response(
    @SerialName("unit") val unit: String,
    @SerialName("total") val total: Double?,
    @SerialName("range") val range: ApiDiversIdLeadEstimateGet200ResponseRange?,
    @SerialName("confidence") val confidence: String,
    @SerialName("basis") val basis: ApiDiversIdLeadEstimateGet200ResponseBasis?,
    /** Each is part of the total; show them with their reason */
    @SerialName("adjustments") val adjustments: List<ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem>,
    /** What the answer had to assume or couldn't use; tell the User where to add it */
    @SerialName("missing") val missing: List<ApiDiversIdLeadEstimateGet200ResponseMissingItem>,
    @SerialName("reserve") val reserve: ApiDiversIdLeadEstimateGet200ResponseReserve,
    @SerialName("leftOut") val leftOut: ApiDiversIdLeadEstimateGet200ResponseLeftOut,
    /** The estimate is a starting point, never a substitute for this */
    @SerialName("weightCheck") val weightCheck: ApiDiversIdLeadEstimateGet200ResponseWeightCheck
)
