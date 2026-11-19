package dev.divehub.android.ui.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.api.models.ApiDiversIdLoadingsGet200Response
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.tools.assemblePersonalizedSources
import dev.divehub.shared.tools.currentLoadings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** What the current-loadings screen draws. */
data class LoadingsUiState(
    val loading: Boolean = true,
    val storeEmpty: Boolean = false,
    val personalized: Boolean = false,
    val syncedAt: String? = null,
    val profilesComplete: Boolean = true,
    val storeError: String? = null,
    val answer: ApiDiversIdLoadingsGet200Response? = null,
)

/**
 * The current loadings from the synced store (FAH-276; ADR 0071): replaying the Diver's profiles, the latest CNS,
 * the last 24 hours of OTU and DAN's no-fly time. Nothing is fetched from the API.
 */
class LoadingsViewModel(private val store: DiveStore) : ViewModel() {
    private val _state = MutableStateFlow(LoadingsUiState())
    val state: StateFlow<LoadingsUiState> = _state.asStateFlow()

    private var loadJob: Job? = null

    fun start() = load()

    fun forget() {
        loadJob?.cancel()
        _state.value = LoadingsUiState(loading = false)
    }

    fun reload() = load()

    private fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch(Dispatchers.Default) {
            try {
                val now = System.currentTimeMillis()
                val sources = assemblePersonalizedSources(store, now)
                _state.update {
                    it.copy(
                        loading = false,
                        storeEmpty = sources.storeEmpty,
                        personalized = sources.ownDiverId != null,
                        syncedAt = sources.syncedAt,
                        profilesComplete = sources.profilesComplete,
                        storeError = null,
                        answer = currentLoadings(sources, now),
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(loading = false, personalized = false, syncedAt = null, storeError = STORE_UNREADABLE, answer = null)
                }
            }
        }
    }

    companion object {
        const val STORE_UNREADABLE = "store_unreadable"

        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { LoadingsViewModel(container.diveStore) }
        }
    }
}
