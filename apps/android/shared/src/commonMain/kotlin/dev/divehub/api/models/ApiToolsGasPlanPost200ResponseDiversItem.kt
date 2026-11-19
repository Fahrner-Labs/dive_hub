// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsGasPlanPost200ResponseDiversItem(
    @SerialName("name") val name: String,
    @SerialName("diverId") val diverId: String?,
    @SerialName("sacLpm") val sacLpm: ApiToolsGasPlanPost200ResponseDiversItemSacLpm,
    @SerialName("volumeL") val volumeL: Double,
    @SerialName("startPressureBar") val startPressureBar: Double,
    @SerialName("litres") val litres: Double,
    @SerialName("endPressureBar") val endPressureBar: Double,
    /** At every level */
    @SerialName("rockBottom") val rockBottom: List<ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem>,
    @SerialName("ascentPressureBar") val ascentPressureBar: Double,
    @SerialName("turnPressureBar") val turnPressureBar: Double?,
    @SerialName("belowRockBottom") val belowRockBottom: Boolean,
    @SerialName("atAscentStartBar") val atAscentStartBar: Double,
    @SerialName("marginBar") val marginBar: Double,
    /** Negative when the plan stays too long */
    @SerialName("marginMinutes") val marginMinutes: Double,
    @SerialName("controlling") val controlling: Boolean,
    @SerialName("recentDiveEndedAt") val recentDiveEndedAt: String?,
    /** At the start and at the end of every segment */
    @SerialName("pressuresBar") val pressuresBar: List<Double>
)
