// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiveCentresPostRequestExternalIdsItem(
    @SerialName("source") val source: String,
    /** As the User typed or pasted it: the bare number, or the centre's whole code */
    @SerialName("externalId") val externalId: String
)
