// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * ADR 0048; null when the Connection doesn't follow, the Provider declares no numbers or the Dive has no number
 */
@Serializable
data class DiveProviderStatusCurrentNumber(
    /** The Dive's number here */
    @SerialName("wanted") val wanted: Int,
    /** The Provider's number (remoteNumber) is another. Not "changed since sent": don't offer an update for it */
    @SerialName("differs") val differs: Boolean,
    /** The Provider sets numbers on an update: offer "Set number" (POST /api/dives/{id}/providers/{provider}/number) */
    @SerialName("canSet") val canSet: Boolean,
    /** Setting it can remove a verification the dive has there: warn before */
    @SerialName("removesVerification") val removesVerification: Boolean
)
