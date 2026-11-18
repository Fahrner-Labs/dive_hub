// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A refusal. Besides code and error, it carries the extras its code names; a module adds only extras of its own types
 */
@Serializable
data class Problem(
    /** Stable, machine-readable reason; clients translate it */
    @SerialName("code") val code: String,
    /** English description, for logs and scripts */
    @SerialName("error") val error: String,
    /** The Provider a provider_* code is about (ADR 0027) */
    @SerialName("provider") val provider: String? = null,
    /** Its name, to put into the translated text */
    @SerialName("providerName") val providerName: String? = null,
    /** The Diver that already has the account: with diver_external_id_taken, to offer it instead (ADR 0028); with provider_account_held, the external Diver here; connect again with claim to make it yours */
    @SerialName("diver") val diver: ProblemDiver? = null,
    /** With centre_external_id_taken: the Dive centre that already has the number, to open it instead (ADR 0043) */
    @SerialName("centre") val centre: ProblemCentre? = null,
    /** With provider_number_taken: the dive at the Provider that has the number (ADR 0048) */
    @SerialName("existing") val existing: ProblemExisting? = null,
    @SerialName("diveId") val diveId: String? = null,
    /** With push_run_active: the run that is going (ADR 0047) */
    @SerialName("runId") val runId: String? = null
)
