// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SignOut200Response(
    @SerialName("success") val success: Boolean? = null,
    /** Provider logout URL when RP-initiated logout is available */
    @SerialName("url") val url: String? = null,
    /** Whether the client should redirect to the provider logout URL */
    @SerialName("redirect") val redirect: Boolean? = null
)
