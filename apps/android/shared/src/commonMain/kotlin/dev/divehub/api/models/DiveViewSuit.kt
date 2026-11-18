// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The exposure suit (ADR 0031); null when unknown
 */
@Serializable
data class DiveViewSuit(
    /** `none` is a dive without a suit */
    @SerialName("type") val type: String,
    @SerialName("thicknessMm") val thicknessMm: Double?,
    /** Whether a hood was worn; null when not said */
    @SerialName("hood") val hood: JsonElement?,
    @SerialName("undergarment") val undergarment: String?
)
