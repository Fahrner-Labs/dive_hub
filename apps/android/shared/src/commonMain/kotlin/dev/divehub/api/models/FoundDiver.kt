// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A Diver of the instance as every User sees it: by name, nothing else
 */
@Serializable
data class FoundDiver(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    /** The signed-in User keeps this Diver's logbook */
    @SerialName("managed") val managed: Boolean,
    /** No User keeps its logbook: someone Users dived with (ADR 0028) */
    @SerialName("external") val external: Boolean
)
