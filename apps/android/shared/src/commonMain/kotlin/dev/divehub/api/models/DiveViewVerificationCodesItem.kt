// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveViewVerificationCodesItem(
    /** Whose code it is: a Dive centre's (centre is set) or a professional's (diver is set) */
    @SerialName("kind") val kind: String,
    @SerialName("centre") val centre: DiveViewVerificationCodesItemCentre?,
    @SerialName("diver") val diver: DiveViewVerificationCodesItemDiver?,
    @SerialName("provider") val provider: String,
    /** Draw this text, unchanged, as a QR code; never build or parse it (docs/spec/clients.md) */
    @SerialName("text") val text: String
)
