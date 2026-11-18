// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ImportView(
    @SerialName("id") val id: String,
    /** awaiting_choice: the upload holds several kinds of dive and nothing is imported until the User starts it with the kinds to import (ADR 0044); cancelled: the User ended that wait, or nobody answered for seven days (errorCode choice_expired) */
    @SerialName("status") val status: String,
    /** The uploaded file's name; for an import from a Provider, the Provider's name */
    @SerialName("uploadName") val uploadName: String,
    @SerialName("provider") val provider: String?,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("finishedAt") val finishedAt: String?,
    @SerialName("errorCode") val errorCode: String?,
    @SerialName("found") val found: ImportViewFound?,
    @SerialName("kinds") val kinds: List<String>?,
    /** The User’s Connections that send new dives by themselves but left this Import’s out (more than 20 at once, ADR 0056) and offer them instead (the Connection’s unsentDives); link to that Connection’s settings. Empty: none */
    @SerialName("unsentTo") val unsentTo: List<ImportViewUnsentToItem>,
    @SerialName("outcome") val outcome: List<ImportViewOutcomeItem>
)
