// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What a Diver's SSI buddy code says about the person, and the codes built from it. Seen by every User (ADR 0043, amending ADR 0028)
 */
@Serializable
data class DiverDetailsView(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    /** No User keeps its logbook */
    @SerialName("external") val external: Boolean,
    /** Whether the signed-in User may change the details: an external Diver, or one whose logbook they keep */
    @SerialName("canEdit") val canEdit: Boolean,
    @SerialName("firstName") val firstName: String?,
    @SerialName("lastName") val lastName: String?,
    @SerialName("email") val email: String?,
    @SerialName("leaderNumber") val leaderNumber: String?,
    @SerialName("accounts") val accounts: List<DiverDetailsViewAccountsItem>,
    /** Empty while the account, a name or the e-mail is missing; the professional's only with a leader number */
    @SerialName("codes") val codes: List<DiverDetailsViewCodesItem>
)
