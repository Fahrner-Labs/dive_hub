// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ServiceSchedule(
    @SerialName("id") val id: String,
    @SerialName("itemId") val itemId: String,
    @SerialName("name") val name: String,
    @SerialName("intervalMonths") val intervalMonths: Int?,
    @SerialName("intervalDives") val intervalDives: Int?,
    @SerialName("intervalHours") val intervalHours: Double?,
    /** Last done before Dive Hub, YYYY-MM-DD */
    @SerialName("startDate") val startDate: String,
    @SerialName("active") val active: Boolean,
    @SerialName("version") val version: Int,
    /** Null for an inactive schedule */
    @SerialName("due") val due: ServiceScheduleDue?
)
