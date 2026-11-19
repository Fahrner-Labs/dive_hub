// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DeletedDivesPageDivesItem(
    @SerialName("id") val id: String,
    @SerialName("diverId") val diverId: String,
    /** Send it back to restore the Dive */
    @SerialName("version") val version: Int,
    @SerialName("number") val number: Int?,
    @SerialName("startsAt") val startsAt: String,
    @SerialName("utcOffsetSeconds") val utcOffsetSeconds: Int?,
    @SerialName("utcOffsetSource") val utcOffsetSource: String,
    @SerialName("durationSeconds") val durationSeconds: Double,
    @SerialName("maxDepthM") val maxDepthM: Double?,
    @SerialName("site") val site: DeletedDivesPageDivesItemSite?,
    @SerialName("deletedAt") val deletedAt: String,
    @SerialName("mergedInto") val mergedInto: String?,
    @SerialName("movedTo") val movedTo: String?,
    @SerialName("continuedInto") val continuedInto: String?,
    /** The other Dives appended in the same append, in start order: clients list them as one entry, which one restore brings back */
    @SerialName("appendedWith") val appendedWith: List<String>,
    /** Providers the dive is still at: clients remind the User and offer to delete it there (docs/spec/clients.md) */
    @SerialName("stillAt") val stillAt: List<DeletedDivesPageDivesItemStillAtItem>,
    /** The Dive's entries in the queue of Waiting pushes: a delete at a Provider that goes out 5 minutes after deleting here (Undo cancels it), or one that is held or stopped, with its reason */
    @SerialName("waiting") val waiting: List<WaitingPushView>
)
