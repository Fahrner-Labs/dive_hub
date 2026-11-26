package dev.divehub.shared.sync

import dev.divehub.shared.DiveHubException
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.write.WriteDrain
import dev.divehub.shared.write.WriteNotice
import dev.divehub.shared.write.WriteReplayResult
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
 * One reason the live stream stopped. The app turns it into a diagnostics entry (ADR 0080) so a field report names
 * the refusal instead of only "offline": a status such as 404/503/429, or [ErrorCodes.NO_ANSWER] for a timeout.
 */
data class StreamFailure(val code: String, val status: Int?, val message: String)

internal fun Throwable.toStreamFailure(): StreamFailure = when (this) {
    is NoAnswerException -> StreamFailure(
        code = ErrorCodes.NO_ANSWER,
        status = null,
        message = "Live stream got no answer (timeout or unreachable)",
    )
    is DiveHubException -> StreamFailure(
        code = code,
        status = status,
        message = "Live stream refused with HTTP ${status ?: "?"} ($code)",
    )
    else -> StreamFailure(
        code = "live_stream_failed",
        status = null,
        message = "Live stream failed: ${javaClass.simpleName}",
    )
}

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
    private val maxReconnectDelayMs: Long = 60_000,
    /**
     * Called once per stream-failure state change (not on every retry). The app records it in the diagnostics buffer
     * (ADR 0080); a refusal that repeats at the same code and status is already represented by the previous entry.
     */
    private val onStreamFailure: (StreamFailure) -> Unit = {},
    /**
     * The offline write queue's replay (ADR 0084 D3). Draining after a refresh/hint is what turns a queued edit into a
     * server write — and a 412 into a parked conflict — on the same triggers the read side uses.
     */
    private val writes: WriteDrain? = null,
) {
    private val _revision = MutableStateFlow(0L)

    /** Bumped after each hint or refresh that left the store changed; open screens read it to refetch. */
    val revision: StateFlow<Long> = _revision.asStateFlow()

    /**
     * A write to the store that did not come from a sync: the offline queue's local edit ("write as if online",
     * ADR 0084). Open screens reread the store on the same revision they already follow.
     */
    fun noteStoreWrite() = bump()

    private val _progress = MutableStateFlow(SyncProgress.EMPTY)

    /**
     * The store's counts after each syncing phase advances; the logbook's pre-sync state draws a determinate bar from
     * it. [SyncProgress.EMPTY] until the first phase reports.
     */
    val progress: StateFlow<SyncProgress> = _progress.asStateFlow()

    init {
        engine.onProgress = { _progress.value = it }
    }

    private val _streamHealthy = MutableStateFlow(false)
    val streamHealthy: StateFlow<Boolean> = _streamHealthy.asStateFlow()

    private val _offline = MutableStateFlow(false)

    /**
     * Changes a replay had to drop because their thing is gone (ADR 0084 D3 step 4). They wait here until the User
     * dismisses them, so opening the queue screen after the drain that dropped them still says what happened; a
     * process death ends them, as the ADR drops the entry rather than keeping a notice.
     */
    private val _notices = MutableStateFlow<List<WriteNotice>>(emptyList())
    val notices: StateFlow<List<WriteNotice>> = _notices.asStateFlow()

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

    /** The failure already reported, so a retry loop does not fill the buffer with the same refusal (ADR 0080). */
    private var lastStreamFailure: String? = null

    /**
     * One event from the stream. A connect refreshes (hints are not replayed, so this is also the resync). A dive or
     * site hint drains the change feed from its durable cursor when the server has one — it covers everything since
     * then, not only the hinted thing (ADR 0083 D6) — and falls back to the hint's own refetch on a pre-feed server.
     * Unknown kinds are ignored (ADR 0076).
     */
    suspend fun handle(event: LiveEvent) {
        when (event) {
            LiveEvent.Connected -> {
                _streamHealthy.value = true
                _offline.value = false
                lastStreamFailure = null
                mark(engine.refresh())
                drainWrites()
                profilesSoon()
            }
            is LiveEvent.Hint -> {
                _streamHealthy.value = true
                if (event.hint.kind != "dive" && event.hint.kind != "site") return
                mark(engine.applyHint(event.hint.kind, event.hint.id))
                drainWrites()
                // A drain (or a dive hint) may have marked a Dive's profiles pending; the background phase picks it up.
                profilesSoon()
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

    /**
     * A push data message's hint (ADR 0085): the same treatment as a live hint, from a background process that has no
     * open screen. The caller compares the store before and after to decide whether anything new really arrived.
     */
    suspend fun applyPushHint(kind: String, id: String) {
        handle(LiveEvent.Hint(LiveHint(kind, id)))
    }

    /** The opened Dive's detail again, so a change missed while the stream was down is not shown as fresh. */
    suspend fun refreshDiveOnce(id: String) {
        mark(engine.applyDiveHint(id))
        drainWrites()
        profilesSoon()
    }

    /** One refresh regardless of stream health; the WorkManager worker runs this without a foreground poll. */
    suspend fun refreshOnce() {
        mark(engine.refresh())
        drainWrites()
    }

    /** *Sync now* from the sign-out warning, or the app's initial run: drain the queue and tell open screens. */
    suspend fun drainWritesNow() {
        drainWrites()
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
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            recordStreamFailure(error)
            throw error
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
                // Any answer that is not a timeout backs off (server refusals 404/503/429 and generic stream errors),
                // so a broken or overloaded server does not get a reconnect every few seconds; a no-answer drop keeps
                // the base retry, since a phone out of signal should reconnect as soon as it can.
                var refusals = 0
                while (isActive) {
                    var failure: Exception? = null
                    try {
                        runLive()
                    } catch (error: CancellationException) {
                        throw error
                    } catch (error: Exception) {
                        failure = error
                        if (error is NoAnswerException) _offline.value = true
                        _streamHealthy.value = false
                    }
                    val wait = when {
                        failure == null || failure is NoAnswerException -> {
                            refusals = 0
                            reconnectDelayMs
                        }
                        else -> {
                            val step = backoffDelay(refusals)
                            refusals += 1
                            step
                        }
                    }
                    delay(wait)
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

    /** Report a stream failure once per state change; a repeated code and status is the state still being down. */
    private fun recordStreamFailure(error: Throwable) {
        val failure = error.toStreamFailure()
        val key = "${failure.code}:${failure.status}"
        if (key == lastStreamFailure) return
        lastStreamFailure = key
        onStreamFailure(failure)
    }

    private fun backoffDelay(refusals: Int): Long {
        var delay = reconnectDelayMs
        repeat(refusals.coerceAtMost(MAX_BACKOFF_DOUBLINGS)) {
            delay = (delay * 2).coerceAtMost(maxReconnectDelayMs)
        }
        return delay
    }

    fun stop() {
        running.value = false
        _offline.value = false
        _progress.value = SyncProgress.EMPTY
        _notices.value = emptyList()
        lastStreamFailure = null
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

    /**
     * Replay the queue after the store may have moved. A replay that applied, parked, dropped or failed something
     * changes what open screens should show; a 401 already ended the session and left the queue intact (ADR 0084 D8).
     */
    private suspend fun drainWrites() {
        val drain = writes ?: return
        val result = try {
            drain.drain()
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            // A transient replay failure is a state on the entry, not a reason to break the read refresh loop.
            return
        }
        when (result) {
            is WriteReplayResult.Drained -> {
                if (result.notices.isNotEmpty()) _notices.update { it + result.notices }
                if (result.applied + result.conflicted + result.dropped + result.failed > 0) bump()
            }
            is WriteReplayResult.Stopped -> bump()
            WriteReplayResult.Empty, WriteReplayResult.SessionEnded -> Unit
        }
    }

    /** The User has read the dropped-change notices. */
    fun dismissNotices() {
        _notices.value = emptyList()
    }

    private fun bump() {
        _revision.update { it + 1 }
        _progress.value = engine.progress()
    }

    private companion object {
        /** Cap the doubling so a long refusal streak cannot overflow the delay arithmetic. */
        const val MAX_BACKOFF_DOUBLINGS = 6
    }
}
