// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WaitingPushView(
    @SerialName("provider") val provider: String,
    @SerialName("action") val action: String,
    /** waiting: goes out at `dueAt`; held: needs the User; stopped: failed for good (Retry) */
    @SerialName("state") val state: String,
    @SerialName("reason") val reason: String?,
    /** When it goes out, for a waiting entry */
    @SerialName("dueAt") val dueAt: String
)
