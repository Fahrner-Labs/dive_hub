// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The code a diver scans in the Provider's app so that it shows a dive as verified (ADR 0043). Not a secret
 */
@Serializable
data class CentreViewVerificationCode(
    @SerialName("provider") val provider: String,
    /** Draw this text, unchanged, as a QR code; never build or parse it (docs/spec/clients.md) */
    @SerialName("text") val text: String
)
