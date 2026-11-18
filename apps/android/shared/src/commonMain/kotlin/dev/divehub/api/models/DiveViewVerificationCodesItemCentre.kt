// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The Dive centre the code belongs to; name it beside the code
 */
@Serializable
data class DiveViewVerificationCodesItemCentre(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    /** The name to show */
    @SerialName("displayName") val displayName: String
)
