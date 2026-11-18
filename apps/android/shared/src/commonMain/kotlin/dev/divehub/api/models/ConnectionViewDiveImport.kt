// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * What importing this account's dives may do (ADR 0030); null when the Provider imports no dives
 */
@Serializable
data class ConnectionViewDiveImport(
    /** off; add: only link and fill Dives here, never create; create: also create Dives Dive Hub doesn't have */
    @SerialName("mode") val mode: String,
    /** How far apart in local time a logbook entry and a Dive here may start, on the same day */
    @SerialName("windowMinutes") val windowMinutes: Int
)
