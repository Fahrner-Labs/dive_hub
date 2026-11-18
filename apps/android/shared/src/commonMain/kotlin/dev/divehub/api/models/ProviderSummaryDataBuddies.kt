// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * find: the account's own list of people (SSI: the buddy list), GET /api/connections/{id}/buddies
 */
@Serializable
data class ProviderSummaryDataBuddies(
    @SerialName("import") val import: ProviderSummaryDataBuddiesImport?
)
