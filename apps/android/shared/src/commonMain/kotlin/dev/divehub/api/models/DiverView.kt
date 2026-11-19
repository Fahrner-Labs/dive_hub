// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiverView(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    /** The User's own Diver */
    @SerialName("isOwn") val isOwn: Boolean,
    /** The dives made before the first one in this logbook (ADR 0048): the first Dive here gets the next number */
    @SerialName("divesBefore") val divesBefore: Int,
    /** Whether apnea sessions count in the Dive numbers (ADR 0048) */
    @SerialName("apneaCounts") val apneaCounts: Boolean,
    @SerialName("diveCount") val diveCount: Int,
    @SerialName("deviceCount") val deviceCount: Int,
    /** Rules of the dive assessment not shown for this Diver (ADR 0036); show them again with PUT /divers/{id}/muted-rules/{rule} */
    @SerialName("mutedRules") val mutedRules: List<String>,
    /** What the Divers list shows of a Diver's certification cards (ADR 0050); the cards are GET /divers/{id}/certifications */
    @SerialName("certifications") val certifications: DiverViewCertifications
)
