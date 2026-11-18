// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ProviderSummarySignIn(
    @SerialName("kind") val kind: String,
    @SerialName("login") val login: String?,
    /** Offer "Keep me signed in": password sign-in, and this server can keep passwords (DIVEHUB_ENCRYPTION_KEY) */
    @SerialName("canKeepPassword") val canKeepPassword: Boolean,
    @SerialName("createUrl") val createUrl: String?,
    /** Token sign-in: the User may give the date the token expires; shown from a week before */
    @SerialName("expires") val expires: Boolean
)
