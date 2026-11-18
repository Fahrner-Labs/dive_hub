// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The Diver that already has the account: with diver_external_id_taken, to offer it instead (ADR 0028); with provider_account_held, the external Diver here; connect again with claim to make it yours
 */
@Serializable
data class DeleteProblemDiver(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    /** With provider_account_held: the Dives it is a Participant on */
    @SerialName("dives") val dives: Int? = null
)
