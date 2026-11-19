// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLoadingsGet200ResponseN2(
    /** The worst compartment as a percent of its tolerated pressure at the surface and GF high; 100 % is the decompression model's limit, not a verdict */
    @SerialName("worstCompartmentPercent") val worstCompartmentPercent: Double,
    @SerialName("gfHigh") val gfHigh: Double
)
