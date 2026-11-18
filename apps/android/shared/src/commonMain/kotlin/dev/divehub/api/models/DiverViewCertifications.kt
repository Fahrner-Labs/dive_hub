// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What the Divers list shows of a Diver's certification cards (ADR 0050); the cards are GET /divers/{id}/certifications
 */
@Serializable
data class DiverViewCertifications(
    @SerialName("count") val count: Int,
    @SerialName("newest") val newest: DiverViewCertificationsNewest?,
    /** Cards whose expiry date has passed */
    @SerialName("expired") val expired: Int,
    /** Cards expiring within 90 days (not yet expired) */
    @SerialName("expiringSoon") val expiringSoon: Int
)
