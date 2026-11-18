// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * ADR 0048: null when the Provider numbers by itself. Else a Connection can be set to number dives here as in Dive Hub (diveNumbers.follow)
 */
@Serializable
data class ProviderSummaryDataDivesExportNumbers(
    /** The operations on which a client sets the dive's number here; update goes with POST /api/dives/{id}/providers/{provider}/number */
    @SerialName("set") val set: List<String>,
    /** Changing the number of a dive already there can remove a verification it has: warn before */
    @SerialName("changeRemovesVerification") val changeRemovesVerification: Boolean
)
