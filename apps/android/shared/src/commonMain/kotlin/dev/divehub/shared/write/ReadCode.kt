package dev.divehub.shared.write

import dev.divehub.api.models.ApiVerificationCodesReadPost200Response
import dev.divehub.api.models.ApiVerificationCodesReadPost200ResponseCandidatesItem
import dev.divehub.shared.store.ScanResult
import dev.divehub.shared.store.ScanStatus
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * What `POST /api/verification-codes/read` answered, shaped for the scanner screen. The generated model keeps the
 * route's two `existing` shapes as one `JsonElement`; this is the typed view the screen needs to show a centre or a
 * person and what taking the code would change.
 */
@Serializable
data class ReadCode(
    val kind: String,
    val centreNumber: String? = null,
    val name: String? = null,
    val existing: ReadExisting? = null,
    val accountId: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val leaderNumber: String? = null,
    val candidates: List<ApiVerificationCodesReadPost200ResponseCandidatesItem> = emptyList(),
) {
    val isCentre: Boolean get() = kind == "centre"
    val personName: String get() = listOfNotNull(firstName, lastName).joinToString(" ").trim()
}

@Serializable
data class ReadExisting(
    val id: String,
    val name: String,
    val canEdit: Boolean = false,
    val changes: List<ReadChange> = emptyList(),
)

@Serializable
data class ReadChange(val field: String, val from: String? = null, val to: String)

object ReadCodes {
    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    /** The read answer a resolved scan kept, or null when the stored answer is not one (an old row, a bad write). */
    fun parse(result: ScanResult): ReadCode? {
        if (result.status != ScanStatus.Read) return null
        val text = result.detailJson ?: return null
        val wire = runCatching { json.decodeFromString<ApiVerificationCodesReadPost200Response>(text) }.getOrNull()
            ?: return null
        // The route's two `existing` shapes are one JsonElement in the generated model; the screen needs the union.
        val existing = wire.existing?.let { element ->
            runCatching { json.decodeFromString<ReadExisting>(element.toString()) }.getOrNull()
        }
        return ReadCode(
            kind = wire.kind,
            centreNumber = wire.centreNumber,
            name = wire.name,
            existing = existing,
            accountId = wire.accountId,
            firstName = wire.firstName,
            lastName = wire.lastName,
            email = wire.email,
            leaderNumber = wire.leaderNumber,
            candidates = wire.candidates ?: emptyList(),
        )
    }
}
