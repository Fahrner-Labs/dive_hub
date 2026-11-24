package dev.divehub.android.ui.scan

import dev.divehub.shared.store.ScanResult
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
    val candidates: List<ReadCandidate> = emptyList(),
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

@Serializable
data class ReadCandidate(val id: String, val name: String)

object ReadCodes {
    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    /** The read answer a resolved scan kept, or null when the stored answer is not one (an old row, a bad write). */
    fun parse(result: ScanResult): ReadCode? {
        if (result.status != dev.divehub.shared.store.ScanStatus.Read) return null
        val text = result.detailJson ?: return null
        return runCatching { json.decodeFromString<ReadCode>(text) }.getOrNull()
    }
}
