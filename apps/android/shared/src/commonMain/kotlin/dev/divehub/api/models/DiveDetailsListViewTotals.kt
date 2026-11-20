// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The Diver’s whole logbook, not narrowed by search, site or filters
 */
@Serializable
data class DiveDetailsListViewTotals(
    @SerialName("dives") val dives: Int,
    @SerialName("durationSeconds") val durationSeconds: Double,
    @SerialName("deepestM") val deepestM: Double?,
    @SerialName("lastDiveAt") val lastDiveAt: String?
)
