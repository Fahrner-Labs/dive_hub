// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewLeadItem(
    /** In kilograms; 0 as the only entry means no lead */
    @SerialName("amountKg") val amountKg: Double,
    @SerialName("placement") val placement: String?
)
