// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DeletedDivesPage(
    @SerialName("dives") val dives: List<DeletedDivesPageDivesItem>,
    /** Every deleted Dive of the Divers the User manages */
    @SerialName("total") val total: Int,
    /** Pass as `cursor` to get the next page; absent on the last page */
    @SerialName("nextCursor") val nextCursor: String? = null,
    /** Every deleted Dive still at a Provider, across all pages (not only this one): clients remind the User and let it keep the copy there without loading every page */
    @SerialName("stillAt") val stillAt: List<DeletedDivesPageStillAtItem>
)
