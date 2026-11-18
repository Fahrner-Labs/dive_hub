// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class EquipmentItem(
    @SerialName("id") val id: String,
    @SerialName("diverId") val diverId: String,
    /** What kind of gear it is */
    @SerialName("category") val category: String,
    @SerialName("name") val name: String,
    @SerialName("maker") val maker: String?,
    @SerialName("model") val model: String?,
    @SerialName("serial") val serial: String?,
    @SerialName("purchaseDate") val purchaseDate: String?,
    @SerialName("notes") val notes: String?,
    /** in_use, retired, lost or sold; a used item is retired, never deleted */
    @SerialName("equipmentStatus") val equipmentStatus: String,
    /** Counts every Dive of its Diver while in use, minus the ones it was taken off */
    @SerialName("onEveryDive") val onEveryDive: Boolean,
    /** YYYY-MM-DD; earlier logbook Dives count from this date */
    @SerialName("inUseFrom") val inUseFrom: String,
    @SerialName("inUseUntil") val inUseUntil: String?,
    @SerialName("version") val version: Int,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("deviceId") val deviceId: String?,
    @SerialName("schedules") val schedules: List<ServiceSchedule>
)
