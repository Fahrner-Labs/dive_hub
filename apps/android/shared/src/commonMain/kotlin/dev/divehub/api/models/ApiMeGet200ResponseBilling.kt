// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The User's plan and status (hosted-DiveHub plan §4.4); shown in Settings and used to grey the paywall
 */
@Serializable
data class ApiMeGet200ResponseBilling(
    /** False on an instance without billing: every feature is free and the client shows no Plan section */
    @SerialName("configured") val configured: Boolean,
    @SerialName("plan") val plan: String,
    /** none: never subscribed */
    @SerialName("status") val status: String,
    /** The period that was paid for; canceled keeps it until it ends */
    @SerialName("currentPeriodEnd") val currentPeriodEnd: String?,
    /** Whether the paid features (automation, Push runs) may be used now */
    @SerialName("entitled") val entitled: Boolean
)
