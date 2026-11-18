// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PushRunView(
    @SerialName("id") val id: String,
    @SerialName("connectionId") val connectionId: String,
    @SerialName("provider") val provider: String,
    @SerialName("diverId") val diverId: String,
    @SerialName("status") val status: String,
    @SerialName("stopCode") val stopCode: String?,
    @SerialName("updates") val updates: Boolean,
    @SerialName("updatesRemovingVerification") val updatesRemovingVerification: Boolean,
    @SerialName("numbers") val numbers: Boolean,
    @SerialName("numbersRemovingVerification") val numbersRemovingVerification: Boolean,
    /** Stop was asked for; the Dive being sent is finished first */
    @SerialName("cancelRequested") val cancelRequested: Boolean,
    @SerialName("counts") val counts: PushRunViewCounts,
    /** Conflicts of this run that still wait in Needs your decision (GET /api/provider-conflicts); an entry a later send closed is not counted, one a later run found again counts in that run */
    @SerialName("conflicts") val conflicts: Int,
    /** What the run left out when it was planned, as counts */
    @SerialName("leftOut") val leftOut: PushRunViewLeftOut,
    /** Pause between two Dives; 0 when this instance no longer has the Provider */
    @SerialName("pauseMs") val pauseMs: Int,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("startedAt") val startedAt: String?,
    @SerialName("finishedAt") val finishedAt: String?,
    @SerialName("dismissedAt") val dismissedAt: String?
)
