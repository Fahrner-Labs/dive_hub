package dev.divehub.android.ui.dive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.android.ui.followStoreWrites
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.NeighboursView
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.dive.profileRecording
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.StoreLogbook
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** What the dive page draws; `error` is a code the UI translates, never the server's English text. */
data class DiveDetailUiState(
    val diveId: String? = null,
    val loading: Boolean = true,
    val dive: DiveView? = null,
    val providers: ProviderDirectory = ProviderDirectory.EMPTY,
    val neighbours: NeighboursView? = null,
    /** The previous Dive's page, read ahead so a swipe drags its content in instead of a blank page (FAH-421). */
    val previousDive: DiveView? = null,
    /** The next Dive's page, read ahead so a swipe drags its content in instead of a blank page (FAH-421). */
    val nextDive: DiveView? = null,
    val samples: RecordingSamplesView? = null,
    /** A stored profile that no longer decodes leaves the rest of the page standing. */
    val samplesError: String? = null,
    /** The profiles phase has not stored this Dive's series yet; the page says it is still syncing. */
    val samplesPending: Boolean = false,
    val error: String? = null,
    /** No answer at all: offline, or the instance cannot be reached (client contract §2). */
    val offline: Boolean = false,
    /** The live hint stream is delivering; the page stays quiet instead of showing "Last synced …". */
    val connected: Boolean = false,
    /** The store's last successful sync; the page's "Last synced …" line. Null before the first sync. */
    val syncedAt: String? = null,
)

/**
 * One Dive, read-only (client contract, Dives): its values with their overrides, its Recordings, the Primary
 * recording's profile, the Providers its values may come from, and the Dives on either side in the list it was opened
 * from (ADR 0042). From FAH-277 every logbook read comes from the store; the store's sync line and per-Dive profiles
 * checkpoint decide what the page says about staleness. Every query's maths lives in `shared/dive`.
 */
class DiveDetailViewModel(
    private val store: StoreLogbook,
    private val api: DiveRepository,
    private val revision: StateFlow<Long>,
    private val offlineState: StateFlow<Boolean>,
    private val connectedState: StateFlow<Boolean>,
    private val refreshDive: suspend (String) -> Unit,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow(DiveDetailUiState())
    val state: StateFlow<DiveDetailUiState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var observing: Job? = null
    private var offlineJob: Job? = null
    private var connectedJob: Job? = null
    private var filters: Set<LogbookFilter> = emptySet()
    private var providers: ProviderDirectory? = null

    /** Opens a Dive with the list's settings; a previous User's page is gone already (see [forget]). */
    fun open(diveId: String, filters: Set<LogbookFilter> = this.filters) {
        this.filters = filters
        observe()
        load(diveId)
        refresh(diveId)
    }

    fun openNeighbour(diveId: String) {
        // A neighbour move is the same screen changing content, not a new destination: keep the current Dive on
        // screen until the next one is fully read, so the swap never flashes a blank page or a spinner (FAH-421).
        load(diveId, reset = false)
        refresh(diveId)
    }

    fun retry() {
        val diveId = _state.value.diveId ?: return
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            refreshNow(diveId)
            read(diveId, reset = true)
        }
    }

    private fun refresh(diveId: String) {
        viewModelScope.launch { refreshNow(diveId) }
    }

    private suspend fun refreshNow(diveId: String) {
        try {
            refreshDive(diveId)
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            // The store stays the source; the cached page below is still worth showing.
        }
    }

    /** Forgets everything about the previous User (client contract §1 Security). */
    fun forget() {
        loadJob?.cancel()
        observing?.cancel()
        offlineJob?.cancel()
        connectedJob?.cancel()
        observing = null
        offlineJob = null
        connectedJob = null
        providers = null
        filters = emptySet()
        _state.value = DiveDetailUiState()
    }

    private fun load(diveId: String, reset: Boolean = true) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch { read(diveId, reset) }
    }

    /** A sync write rereads the same page in place; it must never put the loading state back on screen. */
    private suspend fun read(diveId: String, reset: Boolean) {
        if (reset) {
            _state.value = DiveDetailUiState(diveId = diveId, loading = true)
        } else {
            // Keep the previous notice on screen while a background reread runs; a blank page draws nothing.
            _state.update { it.copy(diveId = diveId) }
        }
        val stored = withContext(ioDispatcher) { store.dive(diveId) }
        if (!stillOpen(diveId)) return
        if (stored == null) {
            val synced = withContext(ioDispatcher) { store.context().hasSync }
            if (!stillOpen(diveId)) return
            _state.update {
                it.copy(
                    loading = false,
                    error = if (synced) "not_found" else null,
                    offline = !synced,
                    connected = connectedState.value,
                    dive = null,
                    neighbours = null,
                    previousDive = null,
                    nextDive = null,
                    samples = null,
                    samplesPending = false,
                    samplesError = null,
                )
            }
            return
        }
        val dive = stored.dive

        // The Dive and its profile go out together: a neighbour step must never pair one Dive's values with the
        // previous Dive's chart while the local read finishes (FAH-421).
        var samples: RecordingSamplesView? = null
        var samplesPending = false
        var samplesError: String? = null
        val recording = profileRecording(dive.recordings)
        if (recording != null) {
            samples = withContext(ioDispatcher) { store.samples(recording.id) }
            if (!stillOpen(diveId)) return
            when {
                samples != null -> Unit
                stored.profilePending -> samplesPending = true
                withContext(ioDispatcher) { store.hasSeries(recording.id) } -> samplesError = "internal_error"
            }
        }
        if (!stillOpen(diveId)) return
        _state.update {
            it.copy(
                loading = false,
                dive = dive,
                samples = samples,
                samplesPending = samplesPending,
                samplesError = samplesError,
                syncedAt = stored.syncedAt,
                offline = offlineState.value,
                connected = connectedState.value,
                error = null,
            )
        }

        val directory = providers ?: api.providers().also {
            if (it.names.isNotEmpty() || it.connected.isNotEmpty()) providers = it
        }
        if (!stillOpen(diveId)) return
        _state.update { it.copy(providers = directory) }

        // The page stands without these: neighbours that cannot be read just hide the controls, and the adjacent
        // pages are read ahead from the store so a swipe drags the real Dive in (FAH-421).
        try {
            val neighbours = if (store.canAnswer(filters)) {
                withContext(ioDispatcher) { store.neighbours(diveId, filters) }
            } else {
                // The Push-backed filters only the API can apply (client contract, Offline reads).
                api.neighbours(diveId, filters)
            }
            val previousDive = neighbours.previous?.id?.let {
                withContext(ioDispatcher) { store.dive(it)?.dive }
            }
            val nextDive = neighbours.next?.id?.let {
                withContext(ioDispatcher) { store.dive(it)?.dive }
            }
            if (stillOpen(diveId)) {
                _state.update {
                    it.copy(neighbours = neighbours, previousDive = previousDive, nextDive = nextDive)
                }
            }
        } catch (_: DiveHubException) {
        } catch (_: NoAnswerException) {
        }
    }

    /** A different Dive (or a forgotten User) owns the state now; a read that started earlier must not write over it. */
    private fun stillOpen(diveId: String): Boolean = _state.value.diveId == diveId

    private fun observe() {
        if (observing != null) return
        observing = viewModelScope.launch {
            revision.followStoreWrites().collect {
                _state.value.diveId?.let { id -> load(id, reset = false) }
            }
        }
        offlineJob = viewModelScope.launch {
            offlineState.collect { offline -> _state.update { it.copy(offline = offline) } }
        }
        connectedJob = viewModelScope.launch {
            // ADR 0066: on every (re)connect, refetch what is on screen. Hints are not replayed.
            var wasConnected = connectedState.value
            connectedState.collect { connected ->
                _state.update { it.copy(connected = connected) }
                if (connected && !wasConnected) _state.value.diveId?.let { refresh(it) }
                wasConnected = connected
            }
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DiveDetailViewModel(
                    store = container.storeLogbook,
                    api = container.dive,
                    revision = container.syncRevision,
                    offlineState = container.syncOffline,
                    connectedState = container.syncConnected,
                    refreshDive = { id -> container.refreshDiveNow(id) },
                )
            }
        }
    }
}
