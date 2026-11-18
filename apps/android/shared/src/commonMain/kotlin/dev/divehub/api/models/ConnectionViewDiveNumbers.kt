// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * ADR 0048; null when the Provider declares no numbers
 */
@Serializable
data class ConnectionViewDiveNumbers(
    /** Dives are numbered here as in Dive Hub (off until the User turns it on; it sends nothing by itself) */
    @SerialName("follow") val follow: Boolean
)
