// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The Firebase client config for Android; no `android` member means push is off and the client polls
 */
@Serializable
data class ApiPushConfigGet200Response(
    @SerialName("android") val android: ApiPushConfigGet200ResponseAndroid? = null
)
