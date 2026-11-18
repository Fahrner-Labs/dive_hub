// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DeleteProblemProvidersItem(
    @SerialName("provider") val provider: String,
    /** What happened to the copy there */
    @SerialName("copy") val copy: String
)
