package dev.divehub.android.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.shared.settings.SettingsRepository
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

/** What the settings screen draws; the email and the app version come from the session and the build. */
data class SettingsUiState(
    val instanceUrl: String? = null,
    /** The store's last full sync; the same source as the logbook's line. Null before the first sync. */
    val syncedAt: String? = null,
    /** The last sync attempt got no answer (client contract, Offline reads). */
    val offline: Boolean = false,
    val syncing: Boolean = false,
)

/**
 * The settings screen's small state holder: the stored instance address and the sync state, plus the "Sync now"
 * trigger. The reads live in `shared/settings` (ADR 0073); this only keeps what the screen last read.
 */
class SettingsViewModel(
    private val settings: SettingsRepository,
    private val revision: StateFlow<Long>,
    private val offlineState: StateFlow<Boolean>,
    private val refreshSync: suspend () -> Unit,
    private val io: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    instanceUrl = withContext(io) { settings.instanceUrl() },
                    syncedAt = withContext(io) { settings.syncState().lastFullSyncAt },
                )
            }
        }
        // A sync or hint changes the store's revision; the settings line follows it like the logbook's does.
        viewModelScope.launch {
            revision.collect { readSync() }
        }
        viewModelScope.launch {
            offlineState.collect { offline -> _state.update { it.copy(offline = offline) } }
        }
    }

    /** One refresh through the same guarded path as the WorkManager worker; the revision then updates the line. */
    fun syncNow() {
        if (_state.value.syncing) return
        _state.update { it.copy(syncing = true) }
        viewModelScope.launch {
            try {
                refreshSync()
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                // The line keeps the last stored sync; the next live hint or poll tries again.
            } finally {
                _state.update { it.copy(syncing = false) }
                readSync()
            }
        }
    }

    private fun readSync() {
        viewModelScope.launch {
            val syncedAt = withContext(io) { settings.syncState().lastFullSyncAt }
            _state.update { it.copy(syncedAt = syncedAt) }
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    settings = container.settings,
                    revision = container.syncRevision,
                    offlineState = container.syncOffline,
                    refreshSync = container::refreshNow,
                )
            }
        }
    }
}
