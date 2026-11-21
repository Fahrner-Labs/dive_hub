package dev.divehub.android.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.DiagnosticsAccess
import dev.divehub.shared.diagnostics.ReportProblemResult
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

/** What the settings screens draw; the email and the app version come from the session and the build. */
data class SettingsUiState(
    val instanceUrl: String? = null,
    /** The store's last full sync; the instance row's "Offline — last synced …" when the stream is down. */
    val syncedAt: String? = null,
    /** The live hint stream is delivering; the instance row says "Connected". */
    val connected: Boolean = false,
    val syncing: Boolean = false,
    val diagnostics: SettingsDiagnosticsState = SettingsDiagnosticsState(),
)

/**
 * The Debug subscreen's read model: whether the instance accepts uploads (null while checking), what the local
 * buffer holds, and the one-shot results of *Report a problem* and *Share local logs*.
 */
data class SettingsDiagnosticsState(
    val enabled: Boolean? = null,
    val count: Int = 0,
    val preview: List<DiagnosticEntry> = emptyList(),
    val reporting: Boolean = false,
    val reportResult: ReportProblemResult? = null,
    /** A share was asked for while the buffer was empty; say so instead of sending an empty file. */
    val shareEmpty: Boolean = false,
)

/**
 * The settings screen's small state holder: the stored instance address and the sync state, plus the "Sync now"
 * trigger. The reads live in `shared/settings` (ADR 0073); this only keeps what the screen last read. The Debug
 * subscreen reads FAH-321's capture layer (ADR 0080) without owning any of it.
 */
class SettingsViewModel(
    private val settings: SettingsRepository,
    private val revision: StateFlow<Long>,
    private val connectedState: StateFlow<Boolean>,
    private val refreshSync: suspend () -> Unit,
    private val diagnostics: DiagnosticsAccess,
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
            connectedState.collect { connected -> _state.update { it.copy(connected = connected) } }
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

    /** Upload the local buffer through FAH-321's flow; only an instance that allows it and a non-empty buffer can. */
    fun reportProblem() {
        val current = _state.value.diagnostics
        if (current.reporting || current.enabled != true || current.count == 0) return
        _state.update { it.copy(diagnostics = it.diagnostics.copy(reporting = true, reportResult = null)) }
        viewModelScope.launch {
            val result = withContext(io) { diagnostics.reportProblem() }
            val count = withContext(io) { diagnostics.count() }
            val preview = withContext(io) { diagnostics.preview(PREVIEW_LIMIT) }
            // A 403 is ADR 0080 D1's "turned off meanwhile": the status line stops claiming the instance accepts
            // uploads, and the action disables itself until the screen reads the capability again.
            val disabledMeanwhile = result is ReportProblemResult.Failed &&
                result.code == ErrorCodes.CLIENT_DIAGNOSTICS_DISABLED
            _state.update {
                it.copy(
                    diagnostics = it.diagnostics.copy(
                        reporting = false,
                        reportResult = result,
                        enabled = if (disabledMeanwhile) false else it.diagnostics.enabled,
                        count = count,
                        preview = preview,
                    ),
                )
            }
        }
    }

    /**
     * Read FAH-321's NDJSON export and hand it to [onReady] for the share sheet. An empty buffer sets [shareEmpty]
     * instead, so the action reports "no entries" rather than sending an empty file.
     */
    fun shareLogs(onReady: (String) -> Unit) {
        viewModelScope.launch {
            val ndjson = withContext(io) { diagnostics.exportNdjson() }
            if (ndjson.isBlank()) {
                _state.update { it.copy(diagnostics = it.diagnostics.copy(shareEmpty = true)) }
            } else {
                _state.update { it.copy(diagnostics = it.diagnostics.copy(shareEmpty = false)) }
                onReady(ndjson)
            }
        }
    }

    /** Re-read the instance capability, the count and the preview; the Debug route calls this on each entry. */
    fun refreshDiagnostics() {
        viewModelScope.launch {
            val enabled = withContext(io) { diagnostics.instanceEnabled() }
            val count = withContext(io) { diagnostics.count() }
            val preview = withContext(io) { diagnostics.preview(PREVIEW_LIMIT) }
            _state.update {
                it.copy(
                    diagnostics = it.diagnostics.copy(
                        enabled = enabled,
                        count = count,
                        preview = preview,
                        // One-shot results belong to the visit that produced them; a fresh read starts clean.
                        reportResult = null,
                        shareEmpty = false,
                    ),
                )
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
        /** The newest entries the Debug subscreen previews; the export and the upload still carry the whole buffer. */
        const val PREVIEW_LIMIT: Int = 3

        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    settings = container.settings,
                    revision = container.syncRevision,
                    connectedState = container.syncConnected,
                    refreshSync = container::refreshNow,
                    diagnostics = container.diagnostics,
                )
            }
        }
    }
}
