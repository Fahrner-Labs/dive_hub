// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveProviderStatusPushesItem(
    @SerialName("id") val id: String,
    /** link: tied to a dive already there, nothing sent. number: only its number there was changed (ADR 0048) */
    @SerialName("action") val action: String,
    /** confirmed: the Provider accepted it and gave its ID (not SSI's dive-center confirmation); handed_over: delivered without an ID */
    @SerialName("state") val state: String,
    @SerialName("remoteId") val remoteId: String?,
    @SerialName("remoteNumber") val remoteNumber: Int?,
    /** The remote dive was found deleted at the Provider */
    @SerialName("remoteGone") val remoteGone: Boolean,
    @SerialName("failureCode") val failureCode: String?,
    @SerialName("leftOut") val leftOut: List<DiveProviderStatusPushesItemLeftOutItem>?,
    @SerialName("differences") val differences: List<DiveProviderStatusPushesItemDifferencesItem>?,
    @SerialName("createdAt") val createdAt: String
)
