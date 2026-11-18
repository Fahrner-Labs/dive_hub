// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveProviderStatus(
    @SerialName("provider") val provider: String,
    /** The Dive's Connection deletes at the Provider by itself (5 minutes after the Dive is deleted here; Undo cancels it): don't ask about deleting the copy there, and don't send `alsoAt` for it (docs/spec/clients.md) */
    @SerialName("deletesItself") val deletesItself: Boolean,
    /** The Dive's entry in the queue of Waiting pushes for this Provider (ADR 0056); null when none */
    @SerialName("waiting") val waiting: WaitingPushView?,
    @SerialName("connection") val connection: DiveProviderStatusConnection?,
    @SerialName("siteId") val siteId: String?,
    /** What the Provider's requirements need and the Dive lacks; never asks the Provider */
    @SerialName("unmet") val unmet: List<UnmetView>,
    @SerialName("current") val current: DiveProviderStatusCurrent?,
    @SerialName("conflicts") val conflicts: List<ConflictFieldView>?,
    /** Newest first */
    @SerialName("pushes") val pushes: List<DiveProviderStatusPushesItem>
)
