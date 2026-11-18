// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The Connection of the Dive's Diver; null when that Diver is not connected to this Provider
 */
@Serializable
data class DiveProviderStatusConnection(
    @SerialName("id") val id: String,
    @SerialName("state") val state: String,
    @SerialName("accountLabel") val accountLabel: String
)
