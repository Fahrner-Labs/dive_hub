// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * With centre_external_id_taken: the Dive centre that already has the number, to open it instead (ADR 0043)
 */
@Serializable
data class ProblemCentre(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
