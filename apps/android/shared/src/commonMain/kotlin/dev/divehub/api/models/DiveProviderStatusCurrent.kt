// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The remote dive this Dive has now; null when none (never sent, deleted)
 */
@Serializable
data class DiveProviderStatusCurrent(
    @SerialName("remoteId") val remoteId: String,
    @SerialName("remoteNumber") val remoteNumber: Int?,
    @SerialName("sentAt") val sentAt: String,
    /** false: the Dive changed since it was sent (outdated), or it was linked and never sent */
    @SerialName("upToDate") val upToDate: Boolean,
    /** An update now would remove the dive centre's verification of the dive at the Provider (SSI: it changes the start time there, or the dive was linked and never sent, so the start time there isn't known). Say so before sending (docs/spec/clients.md, ADR 0043) */
    @SerialName("updateRemovesVerification") val updateRemovesVerification: Boolean,
    @SerialName("number") val number: DiveProviderStatusCurrentNumber?
)
