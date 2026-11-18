// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * With provider_number_taken: the dive at the Provider that has the number (ADR 0048)
 */
@Serializable
data class ProviderProblemExisting(
    @SerialName("remoteId") val remoteId: String,
    @SerialName("number") val number: Int?,
    @SerialName("startsAt") val startsAt: String?,
    @SerialName("maxDepthM") val maxDepthM: Double?,
    @SerialName("durationMinutes") val durationMinutes: Double?
)
