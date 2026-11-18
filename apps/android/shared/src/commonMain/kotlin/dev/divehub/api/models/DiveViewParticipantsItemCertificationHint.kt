// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A certification card of this Participant that has expired or expires within 90 days. Set only for a Participant the signed-in User manages (ADR 0028): other Users never see a Diver's cards, nor this hint
 */
@Serializable
data class DiveViewParticipantsItemCertificationHint(
    /** The card's agency, free text */
    @SerialName("agency") val agency: String,
    /** The card's level, free text */
    @SerialName("level") val level: String,
    /** soon: expires within 90 days; expired: the date has passed. An expired card outranks one that only expires soon */
    @SerialName("expiry") val expiry: String
)
