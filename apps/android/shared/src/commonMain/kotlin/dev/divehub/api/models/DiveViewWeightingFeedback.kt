// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * How the weighting felt on this Dive (ADR 0031); null when nothing was said
 */
@Serializable
data class DiveViewWeightingFeedback(
    /** How the weighting felt */
    @SerialName("felt") val felt: String,
    @SerialName("amountKg") val amountKg: Double?
)
