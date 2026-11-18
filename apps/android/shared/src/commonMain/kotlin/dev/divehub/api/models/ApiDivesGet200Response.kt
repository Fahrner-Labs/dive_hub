// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiDivesGet200Response(
    @SerialName("dives") val dives: List<ApiDivesGet200ResponseDivesItem>,
    @SerialName("total") val total: Int,
    /** How many Dives each filter would show for the Diver and the search, whatever filters are applied (ADR 0040) */
    @SerialName("counts") val counts: ApiDivesGet200ResponseCounts,
    /** The Diver’s whole logbook, not narrowed by search, site or filters */
    @SerialName("totals") val totals: ApiDivesGet200ResponseTotals,
    /** Only while sorted by date: the months of this page in page order, each with all the matching Dives of that month */
    @SerialName("months") val months: List<ApiDivesGet200ResponseMonthsItem>
)
