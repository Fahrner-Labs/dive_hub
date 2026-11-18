// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A requirement this Dive doesn't meet, from Dive Hub's own data
 */
@Serializable
data class UnmetView(
    /** As in the Provider's requirements */
    @SerialName("type") val type: String,
    @SerialName("severity") val severity: String,
    @SerialName("source") val source: String,
    @SerialName("siteId") val siteId: String? = null,
    /** site_external_id: sending finds the site at the Provider or creates it there as a private site (the site has a position), so it does not stop a send; offering the pick stays */
    @SerialName("bySend") val bySend: Boolean? = null,
    /** diver_mapping: the Participant */
    @SerialName("diverId") val diverId: String? = null,
    @SerialName("diverName") val diverName: String? = null,
    @SerialName("role") val role: String? = null,
    /** diver_mapping: ways to fix it. diver_external_id: set the Diver's account at source (PUT /api/divers/{id}/external-ids/{source}). list_entry: link or add an entry in the account's own list at the Provider (SSI: the buddy list) */
    @SerialName("fixes") val fixes: List<String>? = null
)
