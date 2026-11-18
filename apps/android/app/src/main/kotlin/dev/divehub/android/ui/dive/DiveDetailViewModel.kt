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
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.dive.DiveRepository
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.logbook.ProviderDirectory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
    val error: String? = null,
    /** No answer at all: offline, or the instance cannot be reached. Distinct from an error code (client contract §2). */
    val offline: Boolean = false,
)

/**
 * One Dive, read-only (client contract, Dives): its values with their overrides, its Recordings, the Primary
 * recording's profile, the Providers its values may come from, and the Dives on either side in the list it was opened
 * from (ADR 0042). Every query's maths lives in `shared/dive`; this only holds what the screen last read.
 */
class DiveDetailViewModel(
    private val repository: DiveRepository,
    private val logbook: LogbookRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(DiveDetailUiState())
    val state: StateFlow<DiveDetailUiState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var filters: Set<LogbookFilter> = emptySet()
    private var providers: ProviderDirectory? = null

    /** Opens a Dive with the list's settings; a previous User's page is gone already (see [forget]). */
    fun open(diveId: String, filters: Set<LogbookFilter> = this.filters) {
        this.filters = filters
        load(diveId)
    }

    fun openNeighbour(diveId: String) = load(diveId)

    fun retry() {
        _state.value.diveId?.let(::load)
    }

    /** Forgets everything about the previous User (client contract §1 Security). */
    fun forget() {
        loadJob?.cancel()
        providers = null
        filters = emptySet()
        _state.value = DiveDetailUiState()
    }

    private fun load(diveId: String) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.value = DiveDetailUiState(diveId = diveId, loading = true)
            val dive = try {
                repository.dive(diveId)
            } catch (error: DiveHubException) {
                _state.update { it.copy(loading = false, error = error.code, offline = false) }
                return@launch
            } catch (_: NoAnswerException) {
                _state.update { it.copy(loading = false, offline = true) }
                return@launch
            }
            val directory = providers ?: logbook.providers().also {
                if (it.names.isNotEmpty() || it.connected.isNotEmpty()) providers = it
            }
            _state.update { it.copy(loading = false, dive = dive, providers = directory) }

            // The page stands without these: a neighbour that cannot be read just hides the controls, and a profile
            // that cannot be read says so in its own place.
            try {
                val neighbours = repository.neighbours(diveId, filters)
                _state.update { it.copy(neighbours = neighbours) }
            } catch (_: DiveHubException) {
            } catch (_: NoAnswerException) {
            }

            val recording = dive.recordings.firstOrNull { it.isPrimary }
                ?: dive.recordings.firstOrNull { it.partOf == null }
            if (recording != null) {
                try {
                    val samples = repository.samples(recording.id)
                    _state.update { it.copy(samples = samples) }
                } catch (error: DiveHubException) {
                    _state.update { it.copy(samplesError = error.code) }
                } catch (_: NoAnswerException) {
                    _state.update { it.copy(samplesOffline = true) }
                }
            }
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { DiveDetailViewModel(container.dive, container.logbook) }
        }
    }
}
