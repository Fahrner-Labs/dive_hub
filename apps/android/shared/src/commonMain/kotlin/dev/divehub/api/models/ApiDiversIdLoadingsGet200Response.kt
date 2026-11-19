// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDiversIdLoadingsGet200Response(
    /** When the estimate was computed */
    @SerialName("computedAt") val computedAt: String,
    /** What the estimate rests on: always shown with the numbers (ADR 0071) */
    @SerialName("basis") val basis: ApiDiversIdLoadingsGet200ResponseBasis,
    /** Null when no usable Dive is in the 72 h window, the latest Dive ended over 48 h ago, or none has a profile */
    @SerialName("n2") val n2: ApiDiversIdLoadingsGet200ResponseN2?,
    /** Null when no Recording carries CNS; a zero is a real zero */
    @SerialName("cns") val cns: ApiDiversIdLoadingsGet200ResponseCns?,
    /** Null when no Dive in the 24 h window carries OTU; a zero is a real zero */
    @SerialName("otu") val otu: ApiDiversIdLoadingsGet200ResponseOtu?,
    /** The no-fly time after the latest Dive, as on the Dive's assessment; information, not advice */
    @SerialName("noFly") val noFly: ApiDiversIdLoadingsGet200ResponseNoFly?
)
