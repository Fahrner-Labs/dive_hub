// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * With entitled false: the paid plan the upsell names, and what it unlocks (hosted-DiveHub plan §4.4)
 */
@Serializable
data class ConnectionViewOffersOffer(
    /** The plan that unlocks automation (pro) */
    @SerialName("plan") val plan: String,
    /** What it unlocks (automation, push_runs) */
    @SerialName("features") val features: List<String>
)
