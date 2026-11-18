// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class EquipmentDue(
    @SerialName("itemId") val itemId: String,
    @SerialName("itemName") val itemName: String,
    @SerialName("category") val category: String,
    @SerialName("diverId") val diverId: String,
    @SerialName("diverName") val diverName: String,
    @SerialName("scheduleId") val scheduleId: String,
    @SerialName("scheduleName") val scheduleName: String,
    /** Computed on every read, never stored (ADR 0034) */
    @SerialName("due") val due: EquipmentDueDue
)
