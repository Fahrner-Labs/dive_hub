// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class EquipmentItemDetailLastCylinder(
    @SerialName("volumeL") val volumeL: Double?,
    @SerialName("workingPressureBar") val workingPressureBar: Double?,
    @SerialName("material") val material: String?,
    @SerialName("gas") val gas: EquipmentItemDetailLastCylinderGas?
)
