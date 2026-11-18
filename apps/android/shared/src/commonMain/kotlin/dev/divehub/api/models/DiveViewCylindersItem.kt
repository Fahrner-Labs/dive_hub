// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewCylindersItem(
    @SerialName("volumeL") val volumeL: Double?,
    @SerialName("workingPressureBar") val workingPressureBar: Double?,
    @SerialName("material") val material: String?,
    @SerialName("gas") val gas: DiveViewCylindersItemGas?,
    @SerialName("startPressureBar") val startPressureBar: Double?,
    @SerialName("endPressureBar") val endPressureBar: Double?,
    @SerialName("equipmentItemId") val equipmentItemId: String? = null,
    /** An import made it from a tank pod's data and it still has the pod's series: clients say so (docs/spec/clients.md) */
    @SerialName("fromPod") val fromPod: Boolean,
    @SerialName("series") val series: DiveViewCylindersItemSeries?
)
