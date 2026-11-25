// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The registration to keep for DELETE /api/devices/{id}; the token itself is not echoed
 */
@Serializable
data class ApiDevicesPost200Response(
    @SerialName("id") val id: String,
    /** The client platform; iOS and web are reserved for their providers, Android is the first (ADR 0085) */
    @SerialName("platform") val platform: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("lastSeenAt") val lastSeenAt: String
)
