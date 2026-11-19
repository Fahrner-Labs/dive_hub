package dev.divehub.shared.sync

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Keeps the store fresh (ADR 0066): while the live stream is healthy it applies its hints; when it drops, the
 * foreground poll re-runs phase 1 until a reconnect resyncs. [revision] moves after every write so open screens can
 * refresh from the store, and [streamHealthy] lets the app show or suspend polling.
 */
class SyncRefresher(
    private val engine: SyncEngine,
    private val live: LiveHints?,
    private val reconnectDelayMs: Long = 5_000,
    private val pollIntervalMs: Long = 60_000,
) {
    private val _revision = MutableStateFlow(0L)

    /** Bumped after each hint or refresh that left the store changed; open screens read it to refetch. */
    val revision: StateFlow<Long> = _revision.asStateFlow()

    private val _streamHealthy = MutableStateFlow(false)
    val streamHealthy: StateFlow<Boolean> = _streamHealthy.asStateFlow()

    private var liveJob: Job? = null
    private var pollJob: Job? = null

    /**
     * One event from the stream. A connect re-runs phase 1 (hints are not replayed, so this is the resync); a Dive hint
     * re-reads its list row and detail, and a site hint that site. Unknown kinds are ignored (ADR 0076).
     */
    suspend fun handle(event: LiveEvent) {
        when (event) {
            LiveEvent.Connected -> {
                _streamHealthy.value = true
                if (engine.refresh() != SyncResult.SessionEnded) bump()
            }
            is LiveEvent.Hint -> {
                _streamHealthy.value = true
                val result = when (event.hint.kind) {
                    "dive" -> engine.applyDiveHint(event.hint.id)
                    "site" -> engine.applySiteHint(event.hint.id)
                    else -> return
                }
                if (result != SyncResult.SessionEnded) bump()
            }
        }
    }

    /** The polling fallback: phase 1 again, but only while the stream is down (a healthy stream is fresher). */
    suspend fun pollOnce() {
        if (_streamHealthy.value) return
        if (engine.refresh() != SyncResult.SessionEnded) bump()
    }

    /** Collect one connection to the end; returns when the stream drops, and [streamHealthy] turns false. */
    suspend fun runLive() {
        val source = live ?: return
        try {
            source.events().collect { handle(it) }
        } finally {
            _streamHealthy.value = false
        }
    }

    /** Start the live reconnect loop and the foreground poll in [scope]; both end with [stop] or the scope. */
    fun start(scope: CoroutineScope) {
        if (liveJob != null || pollJob != null) return
        if (live != null) {
            liveJob = scope.launch {
                while (isActive) {
                    try {
                        runLive()
                    } catch (error: CancellationException) {
                        throw error
                    } catch (_: Exception) {
                        _streamHealthy.value = false
                    }
                    delay(reconnectDelayMs)
                }
            }
        }
        pollJob = scope.launch {
            while (isActive) {
                delay(pollIntervalMs)
                try {
                    pollOnce()
                } catch (error: CancellationException) {
                    throw error
                } catch (_: Exception) {
                    // The next tick tries again; a 401 has already ended the session.
                }
            }
        }
    }

    fun stop() {
        liveJob?.cancel()
        pollJob?.cancel()
        liveJob = null
        pollJob = null
        _streamHealthy.value = false
    }

    private fun bump() = _revision.update { it + 1 }
}
