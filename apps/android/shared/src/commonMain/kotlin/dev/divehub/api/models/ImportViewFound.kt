// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What an upload holds, once it is analysed (ADR 0044); null before, and for an import from a Provider
 */
@Serializable
data class ImportViewFound(
    /** Dives with a gas: open circuit, rebreather, gauge */
    @SerialName("scuba") val scuba: Int,
    /** Apnea sessions */
    @SerialName("apnea") val apnea: Int,
    /** Files that are no dive (other sports, health data, anything else); they are not kept */
    @SerialName("otherFiles") val otherFiles: Int
)
