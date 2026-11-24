// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiVerificationCodesReadPost200Response(
    @SerialName("kind") val kind: String,
    @SerialName("centreNumber") val centreNumber: String? = null,
    /** As the code spells it */
    @SerialName("name") val name: String? = null,
    @SerialName("existing") val existing: JsonElement?,
    /** The person's SSI account */
    @SerialName("accountId") val accountId: String? = null,
    @SerialName("firstName") val firstName: String? = null,
    @SerialName("lastName") val lastName: String? = null,
    @SerialName("email") val email: String? = null,
    /** When no Diver has the account: Divers of the code's name without an SSI account that the User may change. Offer them before a new Diver */
    @SerialName("candidates") val candidates: List<ApiVerificationCodesReadPost200ResponseCandidatesItem>? = null,
    @SerialName("leaderNumber") val leaderNumber: String? = null
)
