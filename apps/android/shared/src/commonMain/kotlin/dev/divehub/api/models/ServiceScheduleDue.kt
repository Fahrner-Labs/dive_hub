// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Computed on every read, never stored (ADR 0034)
 */
@Serializable
data class ServiceScheduleDue(
    /** The newest service record that resets it, else the schedule start, YYYY-MM-DD */
    @SerialName("anchor") val anchor: String,
    /** ok, due_soon, due or overdue; the worst dimension wins */
    @SerialName("state") val state: String,
    @SerialName("reason") val reason: String?,
    @SerialName("months") val months: ServiceScheduleDueMonths?,
    @SerialName("dives") val dives: ServiceScheduleDueDives?,
    @SerialName("hours") val hours: ServiceScheduleDueHours?
)
