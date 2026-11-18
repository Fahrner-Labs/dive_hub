// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The last scheduled fetch whose Import finished, as that Import came out: a fetch under way or failed doesn't replace it; null before the first
 */
@Serializable
data class ConnectionViewLastFetch(
    @SerialName("at") val at: String,
    /** New Dives */
    @SerialName("created") val created: Int,
    /** Dives changed or filled */
    @SerialName("updated") val updated: Int,
    /** Ambiguous entries and dives of a computer with no choice yet: they wait for the User (a manual import asks about them) */
    @SerialName("waitingForYou") val waitingForYou: Int,
    /** Conflicts of the Connection that wait, whenever they were found: decided under "Needs your decision" (GET /api/provider-conflicts), not by an import */
    @SerialName("conflicts") val conflicts: Int
)
