// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiPushConfigGet200ResponseAndroid(
    @SerialName("projectId") val projectId: String,
    @SerialName("applicationId") val applicationId: String,
    @SerialName("apiKey") val apiKey: String,
    @SerialName("gcmSenderId") val gcmSenderId: String
)
