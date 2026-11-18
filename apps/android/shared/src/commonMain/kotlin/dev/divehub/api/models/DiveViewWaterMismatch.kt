// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Set when the computer was set to other water than the site's: clients say so (docs/spec/clients.md)
 */
@Serializable
data class DiveViewWaterMismatch(
    /** What the Primary recording's computer was set to */
    @SerialName("computer") val computer: String,
    @SerialName("site") val site: String,
    @SerialName("depthPercent") val depthPercent: Double?
)
