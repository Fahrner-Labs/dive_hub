package dev.divehub.shared.write

import dev.divehub.shared.ApiClient
import dev.divehub.shared.NoAnswerException
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Whether this instance can take native writes (ADR 0083 D7, client contract "Offline writes"): the client probes the
 * change feed once per session — a 404 means the server predates it, so the write UI is hidden (hidden, not disabled).
 * The probe is the capability test, never a version number.
 *
 * `null` means "not known yet": no write UI is offered until an answer arrives, so a pre-feed server never shows an
 * edit that could only fail. No answer (offline) leaves the state as it was; the probe is repeated when a connection
 * returns, so a cold start on the boat does not hide writes for the whole session.
 */
class WriteCapability(private val api: ApiClient) {
    private val _writeable = MutableStateFlow<Boolean?>(null)

    /** `true` when the instance answered the feed probe with a 2xx, `false` when it has no feed, `null` before that. */
    val writeable: StateFlow<Boolean?> = _writeable.asStateFlow()

    /** Probe once per session. A no-answer keeps the previous state; the next session asks again. */
    suspend fun probe() {
        val answer = try {
            api.get(PROBE_PATH, retry = false)
        } catch (error: CancellationException) {
            throw error
        } catch (_: NoAnswerException) {
            return
        }
        // Only the contract's two answers decide: a 2xx is writeable, a 404 is a server without the feed. A 5xx or a
        // session refusal is not evidence about the server's age, so the state stays as it was (and is asked again).
        _writeable.value = when {
            answer.status.isSuccess() -> true
            answer.status == HttpStatusCode.NotFound -> false
            else -> return
        }
    }

    /** A signed-out session forgets the answer; the next sign-in probes its own instance. */
    fun reset() {
        _writeable.value = null
    }

    companion object {
        /** A bootstrap page of the feed: cheap, and the contract's own capability question (D7). */
        const val PROBE_PATH: String = "/api/sync/changes?limit=1"
    }
}
