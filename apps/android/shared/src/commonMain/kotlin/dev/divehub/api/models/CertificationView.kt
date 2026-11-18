// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.
// Do not edit by hand: change the OpenAPI document and run the generator.
package dev.divehub.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A certification card of a Diver (ADR 0050)
 */
@Serializable
data class CertificationView(
    @SerialName("id") val id: String,
    @SerialName("diverId") val diverId: String,
    /** Free text (SSI, PADI, CMAS, …) */
    @SerialName("agency") val agency: String,
    /** The course or level, free text; for an imported card as the agency names it */
    @SerialName("level") val level: String,
    @SerialName("number") val number: String?,
    @SerialName("issuedOn") val issuedOn: String?,
    @SerialName("expiresOn") val expiresOn: String?,
    @SerialName("divesAtIssue") val divesAtIssue: Int?,
    @SerialName("instructorName") val instructorName: String?,
    @SerialName("instructorNumber") val instructorNumber: String?,
    @SerialName("centreName") val centreName: String?,
    @SerialName("centreNumber") val centreNumber: String?,
    @SerialName("centreCity") val centreCity: String?,
    @SerialName("centreCountry") val centreCountry: String?,
    /** self_declared: typed by the User; card_seen: the User says they looked at the card; verified_with_agency: read from the agency's own record by an import (never settable by hand) */
    @SerialName("verification") val verification: String,
    /** none: no expiry date; soon: expires within 90 days (the day itself is still valid); expired: the date has passed. Expired cards come last in the list */
    @SerialName("expiry") val expiry: String,
    @SerialName("notes") val notes: String?,
    @SerialName("source") val source: String?,
    @SerialName("cardId") val cardId: String?,
    @SerialName("category") val category: String?,
    /** pending: a job will fetch the picture; failed: show "Fetch again" (POST …/image) */
    @SerialName("imageState") val imageState: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("importedAt") val importedAt: String?,
    /** Send it back with a change; 409 certification_changed when the card changed meanwhile */
    @SerialName("version") val version: Int,
    @SerialName("createdAt") val createdAt: String
)
