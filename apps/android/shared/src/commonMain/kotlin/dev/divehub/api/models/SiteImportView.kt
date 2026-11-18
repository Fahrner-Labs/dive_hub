// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SiteImportView(
    @SerialName("id") val id: String,
    @SerialName("status") val status: String,
    @SerialName("sources") val sources: List<String>,
    /** Where to import: a country, a box, or everywhere */
    @SerialName("area") val area: JsonElement,
    @SerialName("language") val language: String,
    /** false: the run only matched and filled sites already in the hub */
    @SerialName("createSites") val createSites: Boolean,
    @SerialName("progress") val progress: SiteImportViewProgress,
    @SerialName("counts") val counts: SiteImportViewCounts?,
    @SerialName("findings") val findings: List<JsonElement>,
    /** Why it failed (a problem code) */
    @SerialName("failureCode") val failureCode: String?,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("finishedAt") val finishedAt: String?
)
