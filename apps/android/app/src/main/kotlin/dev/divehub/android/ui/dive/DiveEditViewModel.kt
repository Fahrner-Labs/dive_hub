package dev.divehub.android.ui.dive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.shared.dive.DiveEditDraft
import dev.divehub.shared.dive.DiveEditSeriesOption
import dev.divehub.shared.dive.pressureSeriesOptions
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.write.WriteQueue
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

/** One Dive's scalar edit: the draft, the sites a picker may choose from, and whether a connection is answering. */
data class DiveEditUiState(
    val loading: Boolean = true,
    val missing: Boolean = false,
    val draft: DiveEditDraft? = null,
    val originalSiteName: String? = null,
    val sites: List<DiveSite> = emptyList(),
    /** The tank pod series of the Dive's Recordings a Cylinder may name as "measured by" (ADR 0045). */
    val seriesOptions: List<DiveEditSeriesOption> = emptyList(),
    val online: Boolean = false,
    val saving: Boolean = false,
    val saved: Boolean = false,
    val failed: Boolean = false,
)

/**
 * The Dive editor's state: it opens the Dive from the store (never a request, so it works offline), applies the form's
 * changes to an immutable [DiveEditDraft], and saves by enqueuing one `patch` through the Phase A queue. Nothing is
 * sent directly (client contract, Offline writes); the drain is the conflict plumbing's job (T6b/T7).
 */
class DiveEditViewModel(
    private val store: StoreLogbook,
    private val queue: WriteQueue,
    private val online: StateFlow<Boolean>,
    private val onStoreWrite: () -> Unit,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow(DiveEditUiState())
    val state: StateFlow<DiveEditUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            online.collect { connected -> _state.update { it.copy(online = connected) } }
        }
    }

    /** Read the Dive and the cached sites once; a re-open of the same Dive keeps the User's draft. */
    fun open(id: String) {
        if (_state.value.draft?.diveId == id && !_state.value.loading) return
        viewModelScope.launch {
            val read = withContext(ioDispatcher) {
                val stored = store.dive(id)
                val sites = store.sites()
                stored?.let { Triple(it.dive, sites, pressureSeriesOptions(it.dive.recordings)) }
            }
            _state.update { current ->
                if (read == null) {
                    current.copy(loading = false, missing = true)
                } else {
                    val (dive, sites, seriesOptions) = read
                    current.copy(
                        loading = false,
                        missing = false,
                        draft = DiveEditDraft.of(dive),
                        originalSiteName = dive.site?.name,
                        sites = sites,
                        seriesOptions = seriesOptions,
                    )
                }
            }
        }
    }

    fun edit(change: (DiveEditDraft) -> DiveEditDraft) {
        _state.update { current ->
            val draft = current.draft ?: return@update current
            current.copy(draft = change(draft))
        }
    }

    fun retry() {
        val id = _state.value.draft?.diveId ?: return
        _state.update { it.copy(loading = true, missing = false) }
        open(id)
    }

    /** Queue the draft's PATCH; the local store shows it at once, so the Dive page behind the editor updates too. */
    fun save() {
        val draft = _state.value.draft ?: return
        val patch = draft.patch()
        if (patch == null) {
            _state.update { it.copy(saved = true) }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(saving = true, failed = false) }
            try {
                withContext(ioDispatcher) { queue.editDive(draft.diveId, patch) }
                onStoreWrite()
                _state.update { it.copy(saving = false, saved = true) }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _state.update { it.copy(saving = false, failed = true) }
            }
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DiveEditViewModel(
                    store = container.storeLogbook,
                    queue = container.writeQueue,
                    online = container.online,
                    onStoreWrite = container::noteStoreWrite,
                )
            }
        }
    }
}
