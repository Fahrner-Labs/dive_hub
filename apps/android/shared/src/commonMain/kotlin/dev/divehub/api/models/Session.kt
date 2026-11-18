// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Session(
    @SerialName("id") val id: String,
    @SerialName("expiresAt") val expiresAt: String,
    @SerialName("token") val token: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("ipAddress") val ipAddress: String? = null,
    @SerialName("userAgent") val userAgent: String? = null,
    @SerialName("userId") val userId: String,
    @SerialName("impersonatedBy") val impersonatedBy: String? = null
)
