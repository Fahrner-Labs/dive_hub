// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * How many Waiting pushes of the Connection are in each state
 */
@Serializable
data class ConnectionViewWaiting(
    @SerialName("waiting") val waiting: Int,
    @SerialName("held") val held: Int,
    @SerialName("stopped") val stopped: Int
)
