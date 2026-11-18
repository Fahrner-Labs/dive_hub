// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewRecordingsItemSummaryGasesItem(
    @SerialName("o2") val o2: Double,
    @SerialName("he") val he: Double,
    @SerialName("circuit") val circuit: String? = null,
    /** With a tank pod: the size of the tank, litres */
    @SerialName("tankVolumeL") val tankVolumeL: Double? = null,
    @SerialName("startPressureBar") val startPressureBar: Double? = null,
    @SerialName("endPressureBar") val endPressureBar: Double? = null
)
