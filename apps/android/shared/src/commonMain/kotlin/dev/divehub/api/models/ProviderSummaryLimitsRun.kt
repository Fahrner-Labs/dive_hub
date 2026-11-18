// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * ADR 0047; null: the Provider takes no Push runs
 */
@Serializable
data class ProviderSummaryLimitsRun(
    /** Pause between two Dives of a Push run */
    @SerialName("pauseMs") val pauseMs: Int,
    /** The most Dives one Push run sends; start again for the rest */
    @SerialName("maxDives") val maxDives: Int
)
