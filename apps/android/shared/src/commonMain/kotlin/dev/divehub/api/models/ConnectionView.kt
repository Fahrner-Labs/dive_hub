// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ConnectionView(
    @SerialName("id") val id: String,
    @SerialName("provider") val provider: String,
    /** The Diver whose account at the Provider this is */
    @SerialName("diverId") val diverId: String,
    @SerialName("diverName") val diverName: String,
    /** The account at the Provider (SSI: user master ID) */
    @SerialName("accountId") val accountId: String,
    /** What the User recognises the account by (SSI: the e-mail) */
    @SerialName("accountLabel") val accountLabel: String,
    /** The password is kept (sealed); otherwise only the Provider's access, which can expire */
    @SerialName("keepSignedIn") val keepSignedIn: Boolean,
    /** needs_sign_in: the Provider no longer accepts the sign-in; sign in again */
    @SerialName("state") val state: String,
    @SerialName("lastUsedAt") val lastUsedAt: String?,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("diveNumbers") val diveNumbers: ConnectionViewDiveNumbers?,
    @SerialName("diveImport") val diveImport: ConnectionViewDiveImport?,
    /** The kinds of dive an import fetches (both by default); a dive of another kind is skipped with the reason kind_not_chosen */
    @SerialName("fetchKinds") val fetchKinds: List<String>,
    @SerialName("keyExpiresOn") val keyExpiresOn: String?,
    /** Today is within 7 days of keyExpiresOn or past it: say the key expires, with the way to sign in again */
    @SerialName("keyExpiresSoon") val keyExpiresSoon: Boolean,
    /** Send a changed Dive by itself, 30 seconds after its last change (ADR 0056) */
    @SerialName("sendChanges") val sendChanges: Boolean,
    /** Send a new Dive of the Diver by itself */
    @SerialName("sendNewDives") val sendNewDives: Boolean,
    /** Delete the Dive's copy at the Provider by itself, 5 minutes after the Dive is deleted here (Undo cancels it) */
    @SerialName("deleteAtProvider") val deleteAtProvider: Boolean,
    @SerialName("fetchEveryMinutes") val fetchEveryMinutes: Int?,
    /** Which settings to show: only what the Provider can */
    @SerialName("offers") val offers: ConnectionViewOffers,
    /** How many Waiting pushes of the Connection are in each state */
    @SerialName("waiting") val waiting: ConnectionViewWaiting,
    @SerialName("lastFetch") val lastFetch: ConnectionViewLastFetch?,
    /** The new Dives of a large Import (more than 20) that were not sent by themselves: offer to send them (a Push run for these ids: POST /api/connections/{id}/push-runs/preview with dives.ids) or Dismiss (POST /api/connections/{id}/unsent-dives/dismiss). Empty: no offer */
    @SerialName("unsentDives") val unsentDives: List<String>
)
