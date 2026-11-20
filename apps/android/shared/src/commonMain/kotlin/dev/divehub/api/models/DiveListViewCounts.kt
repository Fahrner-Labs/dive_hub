// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * How many Dives each filter would show for the Diver and the search, whatever filters are applied (ADR 0040)
 */
@Serializable
data class DiveListViewCounts(
    @SerialName("noRecording") val noRecording: Int,
    @SerialName("noSite") val noSite: Int,
    @SerialName("withFindings") val withFindings: Int,
    @SerialName("notAtProvider") val notAtProvider: Int,
    @SerialName("changedSinceSent") val changedSinceSent: Int
)
