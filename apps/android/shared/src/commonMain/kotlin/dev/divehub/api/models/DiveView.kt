// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DiveView(
    @SerialName("id") val id: String,
    /** The Diver whose logbook this Dive is in */
    @SerialName("diverId") val diverId: String,
    /** Send it back with an edit; it changes with every change */
    @SerialName("version") val version: Int,
    @SerialName("values") val values: DiveViewValues,
    /** Values the User set by hand */
    @SerialName("overrides") val overrides: List<String>,
    /** Where the start's UTC offset came from (ADR 0030): device, position (the time zone where the dive was), nearby (the Diver's Dives within 7 days) or unknown (startsAt is the local wall-clock time kept as if UTC: show it in UTC without an offset). Clients say where it came from when it isn't the device (docs/spec/clients.md) */
    @SerialName("utcOffsetSource") val utcOffsetSource: String,
    @SerialName("fromProvider") val fromProvider: String?,
    @SerialName("fromRecording") val fromRecording: DiveViewFromRecording?,
    @SerialName("notes") val notes: String?,
    @SerialName("site") val site: DiveViewSite?,
    @SerialName("waterType") val waterType: String?,
    @SerialName("waterMismatch") val waterMismatch: DiveViewWaterMismatch?,
    @SerialName("position") val position: DiveViewPosition?,
    @SerialName("recordings") val recordings: List<DiveViewRecordingsItem>,
    /** The Dive's Cylinders in their order (ADR 0045) */
    @SerialName("cylinders") val cylinders: List<DiveViewCylindersItem>,
    /** The Lead the Dive was dived with, as placed entries in their order (ADR 0031); none means unknown */
    @SerialName("lead") val lead: List<DiveViewLeadItem>,
    @SerialName("leadTotalKg") val leadTotalKg: Double?,
    @SerialName("weightingFeedback") val weightingFeedback: DiveViewWeightingFeedback?,
    @SerialName("suit") val suit: DiveViewSuit?,
    @SerialName("sac") val sac: DiveViewSac?,
    @SerialName("sacMissing") val sacMissing: String?,
    /** Buddies first, then guides and instructors (ADR 0028) */
    @SerialName("participants") val participants: List<DiveViewParticipantsItem>,
    /** The codes that verify the Dive at a Provider (ADR 0043): those of the Dive centres responsible for its site, by centre name, then those of its Participants with a leader number, whatever their role, by name. No other condition: not a Push, not the Diver. Draw each text as a QR code (docs/spec/clients.md) */
    @SerialName("verificationCodes") val verificationCodes: List<DiveViewVerificationCodesItem>
)
