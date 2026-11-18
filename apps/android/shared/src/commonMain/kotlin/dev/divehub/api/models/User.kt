// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("emailVerified") val emailVerified: Boolean,
    @SerialName("image") val image: String? = null,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("role") val role: String? = null,
    @SerialName("banned") val banned: Boolean? = null,
    @SerialName("banReason") val banReason: String? = null,
    @SerialName("banExpires") val banExpires: String? = null,
    @SerialName("twoFactorEnabled") val twoFactorEnabled: Boolean? = null
)
