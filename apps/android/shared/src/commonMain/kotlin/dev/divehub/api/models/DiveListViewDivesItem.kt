// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveListViewDivesItem(
    @SerialName("id") val id: String,
    @SerialName("diverId") val diverId: String,
    @SerialName("number") val number: Int?,
    @SerialName("startsAt") val startsAt: String,
    @SerialName("utcOffsetSeconds") val utcOffsetSeconds: Int?,
    /** unknown: startsAt is the local wall-clock time kept as if UTC; show it in UTC without an offset (ADR 0030) */
    @SerialName("utcOffsetSource") val utcOffsetSource: String,
    @SerialName("durationSeconds") val durationSeconds: Double,
    @SerialName("maxDepthM") val maxDepthM: Double?,
    @SerialName("avgDepthM") val avgDepthM: Double?,
    @SerialName("site") val site: DiveListViewDivesItemSite?,
    /** How many findings of the dive assessment differ from guidance (note or caution) and were not put aside (ADR 0036); 0: none */
    @SerialName("findings") val findings: Int,
    /** How many Recordings the Dive has; 0: a logbook entry only (from a Provider or typed) */
    @SerialName("recordings") val recordings: Int,
    @SerialName("fromProvider") val fromProvider: String?,
    /** The mixes the Primary recording’s computer knew; O₂ and He as percent */
    @SerialName("gases") val gases: List<DiveListViewDivesItemGasesItem>,
    @SerialName("surfaceIntervalSeconds") val surfaceIntervalSeconds: Double?,
    /** The Divers on the Dive besides its own Diver: buddies first, then guides and instructors, each by name (ADR 0028) */
    @SerialName("participants") val participants: List<DiveListViewDivesItemParticipantsItem>,
    /** The Dive's entries in the queue of Waiting pushes, one per Provider (ADR 0056): a small icon for waiting, a warning for held, an error with Retry for stopped (docs/spec/clients.md) */
    @SerialName("waiting") val waiting: List<WaitingPushView>,
    @SerialName("profile") val profile: DiveProfileView?
)
