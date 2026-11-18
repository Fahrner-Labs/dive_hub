// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveProviderStatusPushesItemLeftOutItem(
    @SerialName("diverId") val diverId: String,
    @SerialName("name") val name: String,
    /** no_reference: nothing tells the Provider who they are (give the Diver an account there). not_at_provider: the Provider doesn't have them (SSI: add them to your buddy list in SSI's app, then update) */
    @SerialName("reason") val reason: String
)
