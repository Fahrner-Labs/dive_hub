// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Which settings to show: only what the Provider can
 */
@Serializable
data class ConnectionViewOffers(
    @SerialName("sendChanges") val sendChanges: Boolean,
    @SerialName("sendNewDives") val sendNewDives: Boolean,
    @SerialName("deleteAtProvider") val deleteAtProvider: Boolean,
    /** The intervals to offer, in minutes; none: the Provider has no scheduled fetch */
    @SerialName("fetchEvery") val fetchEvery: List<Int>
)
