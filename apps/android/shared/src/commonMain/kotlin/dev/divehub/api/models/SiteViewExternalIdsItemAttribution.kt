// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What the Source's license asks to show with its data
 */
@Serializable
data class SiteViewExternalIdsItemAttribution(
    @SerialName("text") val text: String,
    @SerialName("url") val url: String
)
