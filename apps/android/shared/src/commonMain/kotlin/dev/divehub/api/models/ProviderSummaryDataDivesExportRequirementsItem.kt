// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What sending needs (ADR 0029)
 */
@Serializable
data class ProviderSummaryDataDivesExportRequirementsItem(
    /** site_external_id: the Dive site's External ID at source. diver_mapping: the Provider can tell who each Participant in roles is. A client that doesn't know the type shows description and that it can't be fixed there (docs/spec/clients.md) */
    @SerialName("type") val type: String,
    /** blocking: sending refuses while unmet. advisory: sent without it; the Push says what was left out */
    @SerialName("severity") val severity: String,
    /** English; only for a client that doesn't know the type */
    @SerialName("description") val description: String,
    /** site_external_id: a site Source. diver_mapping: the service whose account a Diver needs */
    @SerialName("source") val source: String,
    @SerialName("typed") val typed: Boolean?,
    @SerialName("pattern") val pattern: String?,
    @SerialName("prefixes") val prefixes: List<String>?,
    @SerialName("createdBySend") val createdBySend: Boolean?,
    @SerialName("roles") val roles: List<String>?
)
