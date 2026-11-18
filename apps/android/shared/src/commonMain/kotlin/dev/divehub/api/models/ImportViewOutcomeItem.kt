// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ImportViewOutcomeItem(
    @SerialName("fileName") val fileName: String,
    /** linked: a Provider's logbook entry tied to a Dive here and filled where it was empty (ADR 0030) */
    @SerialName("result") val result: String,
    @SerialName("diveId") val diveId: String? = null,
    @SerialName("recordingId") val recordingId: String? = null,
    /** A Provider's dive: its ID there */
    @SerialName("remoteId") val remoteId: String? = null,
    /** A Provider's dive: its own number there */
    @SerialName("remoteNumber") val remoteNumber: Int? = null,
    /** Why; clients translate it. A failure’s detail stays in the server log */
    @SerialName("reason") val reason: String? = null,
    /** For a duplicate-candidate: what has been decided since. diveId is then the Dive it went to */
    @SerialName("decision") val decision: String? = null
)
