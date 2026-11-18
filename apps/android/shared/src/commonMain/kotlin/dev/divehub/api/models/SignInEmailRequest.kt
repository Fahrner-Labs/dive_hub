// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SignInEmailRequest(
    /** Email of the user */
    @SerialName("email") val email: String,
    /** Password of the user */
    @SerialName("password") val password: String,
    /** Callback URL to use as a redirect for email verification */
    @SerialName("callbackURL") val callbackURL: String? = null,
    /** If this is false, the session will not be remembered. Default is `true`. */
    @SerialName("rememberMe") val rememberMe: Boolean? = null
)
