// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Set: private to this Diver's account at the Source (ADR 0057), seen only by Users who manage the Diver and used only by sends for the Diver. Null: public
 */
@Serializable
data class SiteViewExternalIdsItemPrivateTo(
    @SerialName("diverId") val diverId: String
)
