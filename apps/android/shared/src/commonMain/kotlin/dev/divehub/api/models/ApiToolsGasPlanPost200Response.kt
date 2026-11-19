// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsGasPlanPost200Response(
    @SerialName("status") val status: String,
    @SerialName("gas") val gas: ApiToolsGasPlanPost200ResponseGas,
    @SerialName("used") val used: ApiToolsGasPlanPost200ResponseUsed,
    @SerialName("rule") val rule: ApiToolsGasPlanPost200ResponseRule,
    @SerialName("runtimeMinutes") val runtimeMinutes: Double? = null,
    /** Every stretch in the order it is dived */
    @SerialName("segments") val segments: List<ApiToolsGasPlanPost200ResponseSegmentsItem>? = null,
    @SerialName("divers") val divers: List<ApiToolsGasPlanPost200ResponseDiversItem>? = null,
    @SerialName("controllingIndex") val controllingIndex: Int? = null,
    @SerialName("fits") val fits: Boolean? = null,
    /** Run time at which the controlling Diver must leave the last level */
    @SerialName("leaveLastLevelAtMinute") val leaveLastLevelAtMinute: Double? = null,
    @SerialName("reason") val reason: String? = null,
    @SerialName("levelIndex") val levelIndex: Int? = null
)
