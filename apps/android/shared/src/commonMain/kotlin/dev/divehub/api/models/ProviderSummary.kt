// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ProviderSummary(
    @SerialName("id") val id: String,
    /** Proper name, the same in every UI language */
    @SerialName("name") val name: String,
    @SerialName("signIn") val signIn: ProviderSummarySignIn,
    @SerialName("data") val data: ProviderSummaryData,
    /** Things a client must say: shows_unconfirmed, the Provider shows dives sent this way as unconfirmed; presents_as_app, Dive Hub introduces itself to the Provider as its own app (ADR 0049) */
    @SerialName("notices") val notices: List<String>,
    @SerialName("limits") val limits: ProviderSummaryLimits
)
