// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewRecordingsItemSummary(
    @SerialName("diveNumber") val diveNumber: Int? = null,
    @SerialName("diveMode") val diveMode: String? = null,
    @SerialName("decoModel") val decoModel: String? = null,
    @SerialName("gfLow") val gfLow: Double? = null,
    @SerialName("gfHigh") val gfHigh: Double? = null,
    /** The computer's salinity setting, not the water of the Dive (that is the site's) */
    @SerialName("waterType") val waterType: String? = null,
    /** Density the computer computed depths with, kg/m³ */
    @SerialName("waterDensity") val waterDensity: Double? = null,
    @SerialName("gases") val gases: List<DiveViewRecordingsItemSummaryGasesItem>? = null,
    @SerialName("minTemperatureC") val minTemperatureC: Double? = null,
    @SerialName("maxTemperatureC") val maxTemperatureC: Double? = null,
    @SerialName("avgHeartRate") val avgHeartRate: Double? = null,
    @SerialName("surfaceIntervalSeconds") val surfaceIntervalSeconds: Double? = null,
    @SerialName("cnsStart") val cnsStart: Double? = null,
    @SerialName("cnsEnd") val cnsEnd: Double? = null,
    /** Oxygen dose, OTU */
    @SerialName("otuStart") val otuStart: Double? = null,
    @SerialName("otuEnd") val otuEnd: Double? = null,
    /** Gas consumption at the surface as a tank pod measured it, L/min */
    @SerialName("sacLpm") val sacLpm: Double? = null,
    /** The personal setting of the computer where its model has one (Suunto: -2 to +2) */
    @SerialName("conservatism") val conservatism: Double? = null,
    @SerialName("surfacePressureBar") val surfacePressureBar: Double? = null,
    /** Source values without a word in our vocabulary, by source field name */
    @SerialName("extras") val extras: Map<String, String>? = null,
    /** A joined Recording's Surface breaks (ADR 0063): where its parts meet, seconds from its start. Clients mark them in the profile and show each one's length with the duration (docs/spec/clients.md) */
    @SerialName("surfaceBreaks") val surfaceBreaks: List<DiveViewRecordingsItemSummarySurfaceBreaksItem>? = null
)
