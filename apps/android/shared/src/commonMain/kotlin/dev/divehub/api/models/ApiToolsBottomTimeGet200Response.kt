// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsBottomTimeGet200Response(
    @SerialName("status") val status: String,
    /** Why there is no number: deeper than 40 m, beyond the MOD at the contingency ppO2, or a mix other than air and nitrox 21-40 % */
    @SerialName("reason") val reason: String? = null,
    @SerialName("modM") val modM: Double? = null,
    /** Whole minutes from leaving the surface to leaving the depth */
    @SerialName("minutes") val minutes: Int? = null,
    /** The limits that decide; several when they bind together */
    @SerialName("binding") val binding: List<String>? = null,
    @SerialName("limits") val limits: ApiToolsBottomTimeGet200ResponseLimits? = null,
    @SerialName("otu") val otu: Double? = null,
    @SerialName("ppO2AtDepth") val ppO2AtDepth: Double? = null,
    @SerialName("ppO2AboveWorking") val ppO2AboveWorking: Boolean? = null,
    /** The pressure the gas limit ends at; null without a Cylinder */
    @SerialName("ascentPressureBar") val ascentPressureBar: Double? = null,
    @SerialName("used") val used: ApiToolsBottomTimeGet200ResponseUsed,
    @SerialName("gas") val gas: ApiToolsBottomTimeGet200ResponseGas,
    @SerialName("depthM") val depthM: Double,
    /** When the Diver's latest Dive within the last 48 hours ended; `residualN2` says whether its profile seeded the limit, else the real limit is likely shorter than shown */
    @SerialName("recentDiveEndedAt") val recentDiveEndedAt: String?,
    /** The residual nitrogen the no-decompression limit was seeded with; null without a Dive in the last 48 hours or without a profile */
    @SerialName("residualN2") val residualN2: ApiToolsBottomTimeGet200ResponseResidualN2?
)
