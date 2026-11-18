// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ProviderSummaryData(
    @SerialName("dives") val dives: ProviderSummaryDataDives?,
    @SerialName("diveSites") val diveSites: ProviderSummaryDataDiveSites?,
    @SerialName("buddies") val buddies: ProviderSummaryDataBuddies?,
    @SerialName("certifications") val certifications: ProviderSummaryDataCertifications?
)
