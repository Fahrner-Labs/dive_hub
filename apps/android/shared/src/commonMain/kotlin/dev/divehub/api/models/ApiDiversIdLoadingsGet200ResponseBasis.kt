// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What the estimate rests on: always shown with the numbers (ADR 0071)
 */
@Serializable
data class ApiDiversIdLoadingsGet200ResponseBasis(
    /** How far back the N₂ replay looks; the latest Dives decide the rest */
    @SerialName("windowHours") val windowHours: Int,
    /** Dives in the window whose profiles were replayed */
    @SerialName("divesUsed") val divesUsed: Int,
    /** Dives in the window without a depth profile: they contribute nothing */
    @SerialName("divesSkippedNoProfile") val divesSkippedNoProfile: Int,
    @SerialName("lastDiveEndedAt") val lastDiveEndedAt: String?
)
