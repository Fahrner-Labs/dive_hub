// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * find: the account's certification cards (SSI: get_ccards), POST /api/connections/{id}/certifications/import (ADR 0050)
 */
@Serializable
data class ProviderSummaryDataCertifications(
    @SerialName("import") val import: ProviderSummaryDataCertificationsImport?
)
