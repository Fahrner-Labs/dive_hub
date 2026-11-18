// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ProviderSummaryDataDives(
    @SerialName("export") val export: ProviderSummaryDataDivesExport?,
    @SerialName("import") val import: ProviderSummaryDataDivesImport?,
    /** true: the dives come as the original files of the dive computer (each is placed like an uploaded file), so the import only decides whether to fetch: no add-or-create choice and no matching window apply. Absent: the dives come as entries to match */
    @SerialName("asFiles") val asFiles: Boolean? = null,
    /** Which way each value of a dive travels; absent: every value travels both ways as far as the Provider's operations allow (imported if it imports, sent if it exports); present: a value missing from the map is not imported */
    @SerialName("fields") val fields: Map<String, List<String>>? = null
)
