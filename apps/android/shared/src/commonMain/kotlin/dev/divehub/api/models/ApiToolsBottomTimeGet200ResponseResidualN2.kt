// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiToolsBottomTimeGet200ResponseResidualN2(
    /** The Diver's worst compartment at now, as a percent of its tolerated pressure at the surface and GF high; the NDL starts from these tissues */
    @SerialName("worstCompartmentPercent") val worstCompartmentPercent: Double,
    /** How many logged Dives the tissue state rests on */
    @SerialName("basedOnDives") val basedOnDives: Int,
    /** When the latest Dive the tissue state rests on ended */
    @SerialName("lastDiveEndedAt") val lastDiveEndedAt: String
)
