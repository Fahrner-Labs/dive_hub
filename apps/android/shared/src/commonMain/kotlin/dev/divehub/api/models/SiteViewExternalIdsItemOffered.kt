// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A reference's values from its Source, which any User can take up with POST …/adopt (ADR 0025): empty fields take them
 */
@Serializable
data class SiteViewExternalIdsItemOffered(
    @SerialName("name") val name: String?,
    @SerialName("position") val position: SiteViewExternalIdsItemOfferedPosition?,
    @SerialName("country") val country: String?,
    @SerialName("waterBody") val waterBody: String?,
    @SerialName("description") val description: String?,
    @SerialName("maxDepthM") val maxDepthM: Double?,
    @SerialName("waterType") val waterType: String?
)
