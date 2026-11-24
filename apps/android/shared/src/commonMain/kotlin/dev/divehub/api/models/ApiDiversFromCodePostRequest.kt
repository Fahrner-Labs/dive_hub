// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversFromCodePostRequest(
    /** The code's text as scanned or pasted, unchanged */
    @SerialName("text") val text: String,
    /** The Diver the User chose for a code whose account no Diver has */
    @SerialName("diverId") val diverId: String? = null,
    /** Make a new external Diver when no Diver has the account */
    @SerialName("create") val create: Boolean? = null
)
