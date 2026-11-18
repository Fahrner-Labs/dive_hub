// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ServiceScheduleDueMonths(
    /** YYYY-MM-DD */
    @SerialName("dueDate") val dueDate: String,
    @SerialName("remainingDays") val remainingDays: Int,
    /** ok, due_soon, due or overdue; the worst dimension wins */
    @SerialName("state") val state: String
)
