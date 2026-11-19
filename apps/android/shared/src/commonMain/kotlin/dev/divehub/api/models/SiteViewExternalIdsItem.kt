// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Where a site comes from, or where else it is known (ADR 0021)
 */
@Serializable
data class SiteViewExternalIdsItem(
    @SerialName("source") val source: String,
    /** The Source's name, e.g. "OpenStreetMap" */
    @SerialName("name") val name: String,
    /** e.g. "node/123", "Q42", "3314" */
    @SerialName("externalId") val externalId: String,
    @SerialName("url") val url: String?,
    /** The site was created or filled from this Source ("From OpenStreetMap"); otherwise a reference only ("Also in …") */
    @SerialName("providesData") val providesData: Boolean,
    @SerialName("attribution") val attribution: SiteViewExternalIdsItemAttribution?,
    @SerialName("offered") val offered: SiteViewExternalIdsItemOffered?,
    @SerialName("privateTo") val privateTo: SiteViewExternalIdsItemPrivateTo?
)
