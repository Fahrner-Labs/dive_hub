// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The Primary recording’s depth, reduced to draw a sketch of the dive (ADR 0041); null without a Recording, while the Dive waits to be assessed, or when its assessment is of a Recording it no longer has as Primary
 */
@Serializable
data class DiveProfileView(
    /** Depth in metres, evenly spread over `spanSeconds`; the deepest sample of each stretch */
    @SerialName("depthsM") val depthsM: List<Double>,
    /** Seconds from the first to the last depth sample of the Primary recording */
    @SerialName("spanSeconds") val spanSeconds: Double,
    /** Stretches of the ascent that were fast, [start s, end s, band 1–3 for brisk, quick, very quick], in seconds from the first sample (ADR 0036) */
    @SerialName("ascentBands") val ascentBands: List<List<JsonElement>>
)
