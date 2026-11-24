// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiveCentresPostRequest(
    /** A uuidv7 the client made, so an offline create replays as itself: the same id answers 200 with the centre, never a second one (ADR 0084) */
    @SerialName("id") val id: String? = null,
    /** As the centre spells it; for an SSI code, exactly as SSI does */
    @SerialName("name") val name: String,
    @SerialName("externalIds") val externalIds: List<ApiDiveCentresPostRequestExternalIdsItem>? = null,
    /** Dive sites the centre is responsible for */
    @SerialName("siteIds") val siteIds: List<String>? = null
)
