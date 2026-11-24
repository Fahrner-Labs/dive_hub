// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class CentreView(
    @SerialName("id") val id: String,
    /** The whole name, as its Source spells it (SSI: with the town). This is what is edited, and what the code holds */
    @SerialName("name") val name: String,
    /** The name to show: the whole name shortened by the rule of the Source the centre has an ID at (SSI: without the town) */
    @SerialName("displayName") val displayName: String,
    @SerialName("externalIds") val externalIds: List<CentreViewExternalIdsItem>,
    /** The Dive sites the centre is responsible for, by name */
    @SerialName("sites") val sites: List<CentreViewSitesItem>,
    @SerialName("verificationCode") val verificationCode: CentreViewVerificationCode?,
    /** Send it back with a rename; it changes with every rename */
    @SerialName("version") val version: Int,
    /** Whether the signed-in User may delete it: its creator or an admin */
    @SerialName("canDelete") val canDelete: Boolean
)
