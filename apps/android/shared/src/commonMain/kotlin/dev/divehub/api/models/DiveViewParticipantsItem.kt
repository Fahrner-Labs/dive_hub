// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewParticipantsItem(
    @SerialName("diverId") val diverId: String,
    /** The Diver's name, as every User sees it */
    @SerialName("name") val name: String,
    /** buddy: dived together; guide: led the dive; instructor: taught on it */
    @SerialName("role") val role: String,
    /** A certification card of this Participant that has expired or expires within 90 days. Set only for a Participant the signed-in User manages (ADR 0028): other Users never see a Diver's cards, nor this hint */
    @SerialName("certificationHint") val certificationHint: DiveViewParticipantsItemCertificationHint? = null
)
