// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveListView(
    @SerialName("dives") val dives: List<DiveListViewDivesItem>,
    @SerialName("total") val total: Int,
    /** How many Dives each filter would show for the Diver and the search, whatever filters are applied (ADR 0040) */
    @SerialName("counts") val counts: DiveListViewCounts,
    /** The Diver’s whole logbook, not narrowed by search, site or filters */
    @SerialName("totals") val totals: DiveListViewTotals,
    /** Only while sorted by date: the months of this page in page order, each with all the matching Dives of that month */
    @SerialName("months") val months: List<DiveListViewMonthsItem>
)
