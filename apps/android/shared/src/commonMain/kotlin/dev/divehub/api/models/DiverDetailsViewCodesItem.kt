// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiverDetailsViewCodesItem(
    /** buddy: another diver scans it in the Provider's app to add the person to their buddy list. professional: a diver scans it on a logbook entry to verify the dive */
    @SerialName("kind") val kind: String,
    @SerialName("provider") val provider: String,
    /** Draw this text, unchanged, as a QR code; never build or parse it (docs/spec/clients.md) */
    @SerialName("text") val text: String
)
