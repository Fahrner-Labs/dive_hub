// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Sites created, updated, unchanged, kept (only fields Users changed differed), linked (references on hand-made sites); objects skipped without a name, because their site was deleted in the hub, because it was merged into another, or because the run created no sites; hand-made sites whose page now offers the data; objects gone from the Source
 */
@Serializable
data class SiteImportViewCounts(
    @SerialName("created") val created: Int,
    @SerialName("updated") val updated: Int,
    @SerialName("unchanged") val unchanged: Int,
    @SerialName("kept") val kept: Int,
    @SerialName("linked") val linked: Int,
    @SerialName("skippedNoName") val skippedNoName: Int,
    @SerialName("skippedDeleted") val skippedDeleted: Int,
    @SerialName("skippedMerged") val skippedMerged: Int,
    @SerialName("skippedNew") val skippedNew: Int,
    @SerialName("offered") val offered: Int,
    @SerialName("gone") val gone: Int
)
