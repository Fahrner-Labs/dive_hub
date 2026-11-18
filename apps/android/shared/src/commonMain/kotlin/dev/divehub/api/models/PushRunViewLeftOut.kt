// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What the run left out when it was planned, as counts
 */
@Serializable
data class PushRunViewLeftOut(
    @SerialName("upToDate") val upToDate: Int,
    @SerialName("requirementsUnmet") val requirementsUnmet: Int,
    @SerialName("updateNotChosen") val updateNotChosen: Int,
    @SerialName("removesVerification") val removesVerification: Int,
    @SerialName("updateUnsupported") val updateUnsupported: Int,
    @SerialName("beyondCeiling") val beyondCeiling: Int,
    @SerialName("notFound") val notFound: Int,
    /** Dives whose number differs and that a run with numbers would set */
    @SerialName("numberNotChosen") val numberNotChosen: Int,
    /** Number changes that wait for a dive the run doesn't move (a Dive here left out, or one with no number); set them from their own pages */
    @SerialName("numberTaken") val numberTaken: Int,
    /** Number changes whose number a dive only at the Provider holds; import that dive (ADR 0052) */
    @SerialName("numberHeldAtProvider") val numberHeldAtProvider: Int
)
