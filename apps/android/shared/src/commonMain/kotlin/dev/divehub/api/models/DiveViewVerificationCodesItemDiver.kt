// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The professional the code belongs to, a Participant of the Dive; name them beside the code
 */
@Serializable
data class DiveViewVerificationCodesItemDiver(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
