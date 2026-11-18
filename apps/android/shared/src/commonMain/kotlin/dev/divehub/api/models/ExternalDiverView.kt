// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ExternalDiverView(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    /** Services this person has an account at here (the account itself isn't shown) */
    @SerialName("accounts") val accounts: List<String>,
    /** A Dive (of any User) lists this Diver; then it can't be deleted */
    @SerialName("inUse") val inUse: Boolean,
    /** Whether the signed-in User may delete it: whoever added it, or an admin */
    @SerialName("canDelete") val canDelete: Boolean
)
