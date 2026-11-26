// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Which settings to show, and whether this User may use them
 */
@Serializable
data class ConnectionViewOffers(
    @SerialName("sendChanges") val sendChanges: Boolean,
    @SerialName("sendNewDives") val sendNewDives: Boolean,
    @SerialName("deleteAtProvider") val deleteAtProvider: Boolean,
    /** The intervals to offer, in minutes; none: the Provider has no scheduled fetch */
    @SerialName("fetchEvery") val fetchEvery: List<Int>,
    /** False when the paid plan is needed and this User lacks it: grey the switches and show the upsell from `offer` (a hosted instance with billing only; always true where billing is off; absent on an older server) */
    @SerialName("entitled") val entitled: Boolean? = null,
    @SerialName("offer") val offer: ConnectionViewOffersOffer? = null
)
