// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ImportViewUnsentToItem(
    @SerialName("connectionId") val connectionId: String,
    @SerialName("provider") val provider: String,
    /** How many of the Import’s new Dives still have no remote dive there */
    @SerialName("count") val count: Int
)
