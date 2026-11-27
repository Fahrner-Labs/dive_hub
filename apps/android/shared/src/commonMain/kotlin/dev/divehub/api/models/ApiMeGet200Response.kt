// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiMeGet200Response(
    @SerialName("user") val user: UserView,
    @SerialName("ownDiver") val ownDiver: ApiMeGet200ResponseOwnDiver,
    @SerialName("preferences") val preferences: ApiMeGet200ResponsePreferences,
    /** The User's plan and status (hosted-DiveHub plan §4.4); shown in Settings and used to grey the paywall */
    @SerialName("billing") val billing: ApiMeGet200ResponseBilling? = null
)
