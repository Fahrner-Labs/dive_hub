package dev.divehub.shared.sync

import dev.divehub.shared.NoAnswerException
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

    private val _offline = MutableStateFlow(false)

    /**
     * True when the last sync attempt got no answer at all (offline, or the instance cannot be reached). Screens with
     * cached data say so instead of showing the pre-sync "no connection" state (client contract, Offline reads).
     */
    val offline: StateFlow<Boolean> = _offline.asStateFlow()

    /** Started between [start] and [stop]; a listener cannot launch work after a wipe once this is false. */
    private val running = MutableStateFlow(false)

    private var liveJob: Job? = null
    private var pollJob: Job? = null
    private var profilesJob: Job? = null
    private var profilesAgain = false
    private var scope: CoroutineScope? = null

    /**
     * One event from the stream. A connect re-runs phase 1 (hints are not replayed, so this is the resync); a Dive hint
     * re-reads its list row and detail, and a site hint that site. Unknown kinds are ignored (ADR 0076).
     */
    suspend fun handle(event: LiveEvent) {
        when (event) {
            LiveEvent.Connected -> {
                _streamHealthy.value = true
                _offline.value = false
                mark(engine.refresh())
                profilesSoon()
            }
            is LiveEvent.Hint -> {
                _streamHealthy.value = true
                val dive = event.hint.kind == "dive"
                val result = when (event.hint.kind) {
                    "dive" -> engine.applyDiveHint(event.hint.id)
                    "site" -> engine.applySiteHint(event.hint.id)
                    else -> return
                }
                mark(result)
                // A dive hint marked its profiles pending; the background phase picks that work up.
                if (dive) profilesSoon()
            }
        }
    }

    /** The polling fallback: phase 1 again, but only while the stream is down (a healthy stream is fresher). */
    suspend fun pollOnce() {
        if (_streamHealthy.value) return
        refreshOnce()
        profilesSoon()
    }

    /** The app's first sync result: mark a no-answer run and let open screens reread what landed. */
    fun initialFinished(result: SyncResult) = mark(result)

    /** The opened Dive's detail again, so a change missed while the stream was down is not shown as fresh. */
    suspend fun refreshDiveOnce(id: String) {
        mark(engine.applyDiveHint(id))
        profilesSoon()
    }

    /** One refresh regardless of stream health; the WorkManager worker runs this without a foreground poll. */
    suspend fun refreshOnce() {
        mark(engine.refresh())
    }

    /** Drain the pending profiles phase and tell open screens; a no-op when every Dive already has its profiles. */
    suspend fun profilesOnce() {
        if (!engine.hasPendingProfiles()) return
        var changed = false
        while (true) {
            when (engine.syncProfiles()) {
                SyncResult.Complete -> changed = true
                SyncResult.SessionEnded -> return
                is SyncResult.Stopped -> {
                    changed = true
                    break
                }
            }
            // A hint that arrived during the run may have marked another Dive pending after the snapshot.
            if (!engine.hasPendingProfiles()) break
        }
        if (changed) bump()
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
        this.scope = scope
        running.value = true
        profilesAgain = false
        if (live != null) {
            liveJob = scope.launch {
                while (isActive) {
                    try {
                        runLive()
                    } catch (error: CancellationException) {
                        throw error
                    } catch (error: Exception) {
                        if (error is NoAnswerException) _offline.value = true
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
                } catch (error: Exception) {
                    // The next tick tries again; a 401 has already ended the session.
                    if (error is NoAnswerException) _offline.value = true
                }
            }
        }
    }

    fun stop() {
        running.value = false
        _offline.value = false
        liveJob?.cancel()
        pollJob?.cancel()
        profilesJob?.cancel()
        liveJob = null
        pollJob = null
        profilesJob = null
        profilesAgain = false
        scope = null
        _streamHealthy.value = false
    }

    /** Start phase 3 in the background without delaying the hint stream or the poll that asked for it. */
    private fun profilesSoon() {
        if (!running.value) return
        val scope = scope ?: return
        if (profilesJob?.isActive == true) {
            // A hint arrived while the phase was running; run once more after it finishes.
            profilesAgain = true
            return
        }
        if (!engine.hasPendingProfiles()) return
        profilesJob = scope.launch {
            do {
                // Re-checked inside the job: a stop between the launch and this line must not write after a wipe.
                if (!running.value) return@launch
                profilesAgain = false
                try {
                    profilesOnce()
                } catch (error: CancellationException) {
                    throw error
                } catch (_: Exception) {
                    // A later connect, hint or poll tries again; the per-Dive checkpoint keeps the resume point.
                }
            } while (profilesAgain && running.value)
        }
    }

    /** A result changes the store's revision, and a no-answer result turns the screens' cached state on. */
    private fun mark(result: SyncResult) {
        _offline.value = result is SyncResult.Stopped && result.error is NoAnswerException
        if (result != SyncResult.SessionEnded) bump()
    }

    private fun bump() = _revision.update { it + 1 }
}
