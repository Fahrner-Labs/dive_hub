// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem(
    /** Link to the Dive: GET /dives/{id} */
    @SerialName("diveId") val diveId: String,
    @SerialName("startsAt") val startsAt: String,
    @SerialName("number") val number: Int?,
    /** In the answer's `unit` */
    @SerialName("carried") val carried: Double,
    /** What it needed: carried, corrected by its feedback; in the answer's `unit` */
    @SerialName("needed") val needed: Double,
    /** How much it counts: felt right twice, older Dives less */
    @SerialName("weight") val weight: Double
)
