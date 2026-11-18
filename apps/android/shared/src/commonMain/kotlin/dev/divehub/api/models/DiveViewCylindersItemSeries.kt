// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The pressure series of one of the Dive's Recordings that measured this Cylinder
 */
@Serializable
data class DiveViewCylindersItemSeries(
    @SerialName("recordingId") val recordingId: String,
    /** A tank pod's pressure channel of that Recording: tankPressure, further pods tankPressure:<n> */
    @SerialName("channel") val channel: String
)
