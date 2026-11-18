// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class NeighboursView(
    @SerialName("previous") val previous: NeighboursViewPrevious?,
    @SerialName("next") val next: NeighboursViewNext?,
    @SerialName("position") val position: Int?,
    /** How many Dives the list shows */
    @SerialName("total") val total: Int
)
