// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SiteView(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("position") val position: SiteViewPosition?,
    @SerialName("country") val country: String?,
    @SerialName("waterBody") val waterBody: String?,
    @SerialName("description") val description: String?,
    @SerialName("maxDepthM") val maxDepthM: Double?,
    @SerialName("waterType") val waterType: String?,
    @SerialName("externalIds") val externalIds: List<SiteViewExternalIdsItem>,
    /** Send it back with an edit; it changes with every change */
    @SerialName("version") val version: Int,
    /** How many of the signed-in User's Dives are at the site */
    @SerialName("diveCount") val diveCount: Int,
    /** Whether any Dive (of any User) is at the site; then it can't be deleted */
    @SerialName("inUse") val inUse: Boolean,
    /** Whether the signed-in User may delete it: its creator or an admin */
    @SerialName("canDelete") val canDelete: Boolean,
    /** Metres from the position asked for (with latitude/longitude) */
    @SerialName("distanceM") val distanceM: Double? = null,
    @SerialName("mergedInto") val mergedInto: String?
)
