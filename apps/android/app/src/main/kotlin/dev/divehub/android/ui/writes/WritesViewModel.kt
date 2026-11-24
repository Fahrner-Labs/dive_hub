package dev.divehub.android.ui.writes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.android.ui.followStoreWrites
import dev.divehub.shared.write.ConflictResolution
import dev.divehub.shared.write.ConflictSide
import dev.divehub.shared.write.QueuedWriteView
import dev.divehub.shared.write.WriteNotice
import dev.divehub.shared.write.WriteConflictView
import dev.divehub.shared.write.WriteQueueSnapshot
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The offline queue as the Android surface shows it (ADR 0084 D4): how many writes wait, which entries need the
 * User's decision, and whether this instance can take writes at all. [visible] stays true while the capability is
 * unknown and saved writes exist — an offline start must not hide the User's own data — but an explicit pre-feed
 * answer (`writeable == false`) hides the whole write surface, as D7 requires.
 */
data class WritesUiState(
    val writeable: Boolean? = null,
    val pending: Int = 0,
    val conflicted: Int = 0,
    val failed: Int = 0,
    val conflicts: List<WriteConflictView> = emptyList(),
    /** Waiting and failed entries, for the queue screen's "what waits" list (client contract, Offline writes). */
    val queued: List<QueuedWriteView> = emptyList(),
    /** Entries a replay had to drop because the thing they edit is gone; named, never a silent loss. */
    val notices: List<WriteNotice> = emptyList(),
    val busy: Boolean = false,
    /** The last decision or retry could not be saved; the screen says so instead of pretending it landed. */
    val actionFailed: Boolean = false,
) {
    val total: Int get() = pending + conflicted + failed
    val visible: Boolean get() = (total > 0 || notices.isNotEmpty()) && writeable != false
    val canReview: Boolean get() = conflicted > 0 && writeable != false
}

/**
 * The queue's small state holder: reads [WriteQueueSnapshot] and the parked conflicts from the shared core, follows
 * the sync revision like the other screens, and resolves a conflict against a fresh server version.
 */
class WritesViewModel(
    writeable: StateFlow<Boolean?>,
    revision: StateFlow<Long>,
    queueRevision: StateFlow<Long>,
    private val readSnapshot: () -> WriteQueueSnapshot,
    private val readConflicts: () -> List<WriteConflictView>,
    private val readQueued: () -> List<QueuedWriteView>,
    notices: StateFlow<List<WriteNotice>>,
    private val resolveConflict: suspend (Long, Map<String, ConflictSide>) -> ConflictResolution?,
    private val drain: suspend () -> Unit,
    private val retryQueue: suspend () -> Unit,
    private val clearNotices: () -> Unit,
    private val io: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow(WritesUiState())
    val state: StateFlow<WritesUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            writeable.collect { value -> _state.update { it.copy(writeable = value) } }
        }
        // A replay, a refresh that rebases the queue, or a decision writes the queue and bumps the revision.
        viewModelScope.launch { revision.followStoreWrites().collect { read() } }
        // An edit lands in the queue before any sync runs; the queue's own revision shows it at once.
        viewModelScope.launch { queueRevision.collect { read() } }
        // A replay may have dropped a change; that notice belongs to the queue screen until the User reads it.
        viewModelScope.launch { notices.collect { value -> _state.update { it.copy(notices = value) } } }
    }

    /** Re-read the queue; the conflict screen calls this on entry in case a sync landed while it was closed. */
    fun refresh() {
        viewModelScope.launch { read() }
    }

    /** A sign-out or another User: the queue and the capability belong to the previous session, so draw neither. */
    fun forget() {
        _state.value = WritesUiState()
    }

    /** The User has read the dropped-change notices. */
    fun dismissNotices() {
        clearNotices()
    }

    /** Retry entries that failed with a server refusal: they rejoin the queue and one replay attempt runs. */
    fun retryFailed() {
        if (_state.value.busy) return
        _state.update { it.copy(busy = true, actionFailed = false) }
        viewModelScope.launch {
            try {
                retryQueue()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _state.update { it.copy(actionFailed = true) }
            }
            read()
            _state.update { it.copy(busy = false) }
        }
    }

    /**
     * Keep the chosen side for every field of one parked entry (ADR 0084 D3). A field without a choice keeps the
     * preselected "this phone" side. The kept values re-enter the queue against the fresh version and one drain
     * follows, so the decision is sent as soon as the connection allows.
     */
    fun resolve(entryId: Long, choices: Map<String, ConflictSide>) {
        if (_state.value.busy) return
        _state.update { it.copy(busy = true, actionFailed = false) }
        viewModelScope.launch {
            val resolution = try {
                withContext(io) { resolveConflict(entryId, choices) }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                null
            }
            if (resolution == null) {
                _state.update { it.copy(busy = false, actionFailed = true) }
                return@launch
            }
            try {
                drain()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                // Re-queued and waiting is a healthy state; the next sync trigger tries again.
            }
            read()
            _state.update { it.copy(busy = false) }
        }
    }

    private suspend fun read() {
        val snapshot = withContext(io) { readSnapshot() }
        val conflicts = if (snapshot.conflicted > 0) withContext(io) { readConflicts() } else emptyList()
        val queued = if (snapshot.pending + snapshot.failed > 0) withContext(io) { readQueued() } else emptyList()
        _state.update {
            it.copy(
                pending = snapshot.pending,
                conflicted = snapshot.conflicted,
                failed = snapshot.failed,
                conflicts = conflicts,
                queued = queued,
            )
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                WritesViewModel(
                    writeable = container.writeable,
                    revision = container.syncRevision,
                    queueRevision = container.queueRevision,
                    readSnapshot = container.writeReplayer::snapshot,
                    readConflicts = container.writeReplayer::conflicts,
                    readQueued = container.writeReplayer::queued,
                    notices = container.writeNotices,
                    resolveConflict = container::resolveWriteConflict,
                    drain = container::drainWrites,
                    retryQueue = container::retryWrites,
                    clearNotices = container::dismissWriteNotices,
                )
            }
        }
    }
}
