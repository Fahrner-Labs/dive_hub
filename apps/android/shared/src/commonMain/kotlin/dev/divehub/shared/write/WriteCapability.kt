package dev.divehub.shared.write

import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException

/**
 * Whether this server can take the native client's offline writes (ADR 0083 D7, ADR 0084). [Writeable] means the
 * change feed answered, so the write path (ETag/If-Match/412) is there too; [ReadOnly] means the server answered
 * `404` for the feed — a pre-feed release — and every write surface is hidden, not disabled. [Unknown] means the
 * probe did not get a usable answer (offline, a server error, an ended session); it is never treated as a refusal.
 */
enum class WriteCapability { Unknown, Writeable, ReadOnly }

/**
 * Probes `GET /api/sync/changes` (ADR 0083 D7): a `2xx` proves both the feed and the write contract, a `404` proves a
 * pre-feed server. It deliberately reads no version and no body — the server's release number is not the contract.
 *
 * A request with no answer or a server refusal leaves the capability [WriteCapability.Unknown], so an offline start
 * neither locks the client into read-only nor claims write access it has not proven. The probe never retries: the
 * next sync trigger owns when to ask again.
 */
class ServerWriteCapabilityProbe(private val api: ApiClient) {
    suspend fun probe(): WriteCapability {
        val response = try {
            api.get(FEED_PATH, retry = false)
        } catch (error: CancellationException) {
            throw error
        } catch (_: NoAnswerException) {
            return WriteCapability.Unknown
        } catch (_: DiveHubException) {
            return WriteCapability.Unknown
        }
        return when {
            response.status.isSuccess() -> WriteCapability.Writeable
            response.status.value == 404 -> WriteCapability.ReadOnly
            else -> WriteCapability.Unknown
        }
    }

    private companion object {
        const val FEED_PATH = "/api/sync/changes"
    }
}
