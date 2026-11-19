package dev.divehub.android.ui.dive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.NeighboursView
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.dive.profileRecording
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.StoreLogbook
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
    val samples: RecordingSamplesView? = null,
    /** A profile that failed on its own leaves the rest of the page standing. */
    val samplesError: String? = null,
    val samplesOffline: Boolean = false,
    /** The profiles phase has not stored this Dive's series yet; the page says it is still syncing. */
    val samplesPending: Boolean = false,
    val error: String? = null,
    /** No answer at all: offline, or the instance cannot be reached (client contract §2). */
    val offline: Boolean = false,
    /** The store's last successful sync; the page's "Last synced …" line. Null before the first sync. */
    val syncedAt: String? = null,
    /** This Dive's Recordings are in the store but their sample series are still syncing (phase 3). */
    val profilePending: Boolean = false,
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
    private val refreshSync: suspend () -> Unit,
) : ViewModel() {
    private val _state = MutableStateFlow(DiveDetailUiState())
    val state: StateFlow<DiveDetailUiState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var observing: Job? = null
    private var offlineJob: Job? = null
    private var filters: Set<LogbookFilter> = emptySet()
    private var providers: ProviderDirectory? = null

    /** Opens a Dive with the list's settings; a previous User's page is gone already (see [forget]). */
    fun open(diveId: String, filters: Set<LogbookFilter> = this.filters) {
        this.filters = filters
        observe()
        load(diveId)
    }

    fun openNeighbour(diveId: String) = load(diveId)

    fun retry() {
        val diveId = _state.value.diveId ?: return
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            try {
                refreshSync()
            } catch (_: Exception) {
                // The store stays the source; the cached page below is still worth showing.
            }
            read(diveId)
        }
    }

    /** Forgets everything about the previous User (client contract §1 Security). */
    fun forget() {
        loadJob?.cancel()
        observing?.cancel()
        offlineJob?.cancel()
        observing = null
        offlineJob = null
        providers = null
        filters = emptySet()
        _state.value = DiveDetailUiState()
    }

    private fun load(diveId: String) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch { read(diveId) }
    }

    private suspend fun read(diveId: String) {
        _state.value = DiveDetailUiState(diveId = diveId, loading = true)
        val stored = withContext(Dispatchers.IO) { store.dive(diveId) }
        if (stored == null) {
            val synced = withContext(Dispatchers.IO) { store.context().hasSync }
            _state.update {
                if (synced) {
                    it.copy(loading = false, error = "not_found")
                } else {
                    it.copy(loading = false, offline = true)
                }
            }
            return
        }
        val dive = stored.dive
        val directory = providers ?: api.providers().also {
            if (it.names.isNotEmpty() || it.connected.isNotEmpty()) providers = it
        }
        _state.update {
            it.copy(
                loading = false,
                dive = dive,
                providers = directory,
                syncedAt = stored.syncedAt,
                profilePending = stored.profilePending,
                offline = offlineState.value,
            )
        }

        // The page stands without these: neighbours that cannot be read just hide the controls, and a profile that
        // is not in the store yet says it is still syncing.
        try {
            val neighbours = withContext(Dispatchers.IO) { store.neighbours(diveId, filters) }
            _state.update { it.copy(neighbours = neighbours) }
        } catch (_: Exception) {
        }

        val recording = profileRecording(dive.recordings)
        if (recording != null) {
            val samples = withContext(Dispatchers.IO) { store.samples(recording.id) }
            if (samples != null) {
                _state.update { it.copy(samples = samples) }
            } else if (stored.profilePending) {
                _state.update { it.copy(samplesPending = true) }
            }
        }
    }

    private fun observe() {
        if (observing != null) return
        observing = viewModelScope.launch {
            var last = revision.value
            revision.collect { value ->
                if (value != last) {
                    last = value
                    _state.value.diveId?.let { id -> load(id) }
                }
            }
        }
        offlineJob = viewModelScope.launch {
            offlineState.collect { offline -> _state.update { it.copy(offline = offline) } }
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
                    refreshSync = { container.refreshNow() },
                )
            }
        }
    }
}
