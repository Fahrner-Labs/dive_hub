// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ProviderSummaryDataDivesExport(
    @SerialName("operations") val operations: List<String>,
    /** How find finds a record: by our reference, by a time window, near a position */
    @SerialName("findBy") val findBy: List<String>,
    /** confirmed: an ID comes back. handed_over: no ID, so no update, delete or link */
    @SerialName("delivery") val delivery: String,
    @SerialName("requirements") val requirements: List<ProviderSummaryDataDivesExportRequirementsItem>,
    /** Field names read-back differences are reported under; translate them */
    @SerialName("readBackFields") val readBackFields: List<String>,
    @SerialName("numbers") val numbers: ProviderSummaryDataDivesExportNumbers?
)
