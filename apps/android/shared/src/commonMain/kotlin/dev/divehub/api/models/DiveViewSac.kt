// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * SAC on this dive, computed from its Cylinders, its average depth and its duration, never stored (ADR 0045)
 */
@Serializable
data class DiveViewSac(
    /** Litres per minute at the surface, summed over all the Dive's Cylinders, with real gas */
    @SerialName("litresPerMinute") val litresPerMinute: Double,
    @SerialName("barPerMinute") val barPerMinute: Double?
)
