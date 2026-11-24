package dev.divehub.android.ui.dive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.shared.dive.DiveEditDraft
import dev.divehub.shared.dive.DiveEditParticipant
import dev.divehub.shared.divers.DiverSearch
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.store.Diver
import dev.divehub.shared.write.WriteQueue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** The pause after the last keystroke before the live search asks the server, so a word makes one request. */
private const val SEARCH_DEBOUNCE_MS = 300L

/** A Diver the add sheet may offer: one the device holds, or a live search result with whose logbook it is in. */
data class DiverChoice(
    val id: String,
    val name: String,
    val managed: Boolean,
    val external: Boolean,
)

/** The Participants add sheet's live search over every Diver, which needs a server answer (online only). */
sealed interface DiverSearchState {
    /** Nothing typed, offline, or the sheet just opened: the cached managed Divers are the list. */
    data object Idle : DiverSearchState

    data class Searching(val query: String) : DiverSearchState

    data class Found(val query: String, val divers: List<DiverChoice>) : DiverSearchState

    /** [query] could not be searched; the sheet falls back to the cached managed Divers. */
    data class Failed(val query: String) : DiverSearchState
}

/** One Dive's edit: the draft (scalars and roster), the sites and Divers a picker may choose from, and whether a connection is answering. */
data class DiveEditUiState(
    val loading: Boolean = true,
    val missing: Boolean = false,
    val draft: DiveEditDraft? = null,
    val originalSiteName: String? = null,
    val sites: List<DiveSite> = emptyList(),
    /** Every Diver the store knows, for the Participants sheet; the Dive's own Diver is filtered out there. */
    val divers: List<Diver> = emptyList(),
    val ownerDiverId: String? = null,
    /** The add sheet's live search; [DiverSearchState.Idle] until the User types while online. */
    val participantSearch: DiverSearchState = DiverSearchState.Idle,
    val online: Boolean = false,
    val saving: Boolean = false,
    val saved: Boolean = false,
    val failed: Boolean = false,
)

/**
 * The Dive editor's state: it opens the Dive from the store (never a request, so it works offline), applies the form's
 * changes to an immutable [DiveEditDraft], and saves by enqueuing its `patch` and, when the roster changed, a
 * `dive_participants` entry through the Phase A queue. Nothing is sent directly (client contract, Offline writes); the
 * drain is the conflict plumbing's job (T6b/T7).
 */
class DiveEditViewModel(
    private val store: StoreLogbook,
    private val queue: WriteQueue,
    private val online: StateFlow<Boolean>,
    private val onStoreWrite: () -> Unit,
    private val diverSearch: DiverSearch,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow(DiveEditUiState())
    val state: StateFlow<DiveEditUiState> = _state.asStateFlow()
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            online.collect { connected ->
                _state.update { it.copy(online = connected) }
                if (!connected) {
                    // A search needs an answer: dropping the live list leaves the cached managed Divers the sheet shows.
                    searchJob?.cancel()
                    _state.update { it.copy(participantSearch = DiverSearchState.Idle) }
                }
            }
        }
    }

    /** Read the Dive and the cached sites once; a re-open of the same Dive keeps the User's draft. */
    fun open(id: String) {
        if (_state.value.draft?.diveId == id && !_state.value.loading) return
        viewModelScope.launch {
            val read = withContext(ioDispatcher) {
                store.dive(id)?.let { stored ->
                    EditSource(
                        dive = stored.dive,
                        sites = store.sites(),
                        divers = store.divers(),
                        ownerDiverId = stored.dive.diverId,
                    )
                }
            }
            _state.update { current ->
                if (read == null) {
                    current.copy(loading = false, missing = true)
                } else {
                    current.copy(
                        loading = false,
                        missing = false,
                        draft = DiveEditDraft.of(read.dive),
                        originalSiteName = read.dive.site?.name,
                        sites = read.sites,
                        divers = read.divers,
                        ownerDiverId = read.ownerDiverId,
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

    /**
     * Search every Diver of the instance by name after a short pause, so a typed word makes one request. Offline (or
     * with nothing typed) the cached managed Divers stay the list and no request is made.
     */
    fun searchParticipants(text: String) {
        searchJob?.cancel()
        val query = text.trim()
        if (query.isEmpty() || !online.value) {
            _state.update { it.copy(participantSearch = DiverSearchState.Idle) }
            return
        }
        _state.update { it.copy(participantSearch = DiverSearchState.Searching(query)) }
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MS)
            try {
                val found = withContext(ioDispatcher) { diverSearch.search(query) }
                _state.update { current ->
                    if (current.participantSearch.searches(query)) {
                        current.copy(
                            participantSearch = DiverSearchState.Found(
                                query = query,
                                divers = found.map { DiverChoice(it.id, it.name, it.managed, it.external) },
                            ),
                        )
                    } else {
                        current
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _state.update { current ->
                    if (current.participantSearch.searches(query)) {
                        current.copy(participantSearch = DiverSearchState.Failed(query))
                    } else {
                        current
                    }
                }
            }
        }
    }

    /** The sheet closed or its field cleared: forget the live list so a re-open starts from the cached Divers. */
    fun resetParticipantSearch() {
        searchJob?.cancel()
        _state.update { it.copy(participantSearch = DiverSearchState.Idle) }
    }

    /** Queue the draft's PATCH and, when it changed, its Participants PUT; the store shows both at once. */
    fun save() {
        val draft = _state.value.draft ?: return
        val patch = draft.patch()
        if (patch == null && !draft.participantsChanged) {
            _state.update { it.copy(saved = true) }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(saving = true, failed = false) }
            try {
                withContext(ioDispatcher) {
                    if (patch != null) queue.editDive(draft.diveId, patch)
                    if (draft.participantsChanged) {
                        // A joint dive's union may have reached the store since the form opened (ADR 0082): keep those
                        // people, or the whole-list PUT would read as the User removing them. The queue takes the
                        // roster and version the form itself opened with, so a removal or role change from the feed is
                        // merged element-wise instead of being silently reverted (ADR 0084 D3).
                        val fromFeed = store.dive(draft.diveId)?.dive?.participants.orEmpty().map {
                            DiveEditParticipant(diverId = it.diverId, name = it.name, role = it.role)
                        }
                        draft.participantsJson(fromFeed)?.let {
                            queue.editDiveParticipants(
                                draft.diveId,
                                it,
                                baseVersion = draft.version.toLong(),
                                baseJson = draft.participantsBaseJson().toString(),
                            )
                        }
                    }
                }
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
                    diverSearch = container.diverSearch,
                )
            }
        }
    }
}

/** The search answer that belongs to [query] and to no earlier keystroke. */
private fun DiverSearchState.searches(query: String): Boolean =
    this is DiverSearchState.Searching && this.query == query

/** The store reads an edit needs: the Dive, where its site and the roster picker come from, and the Dive's own Diver. */
private data class EditSource(
    val dive: dev.divehub.api.models.DiveView,
    val sites: List<DiveSite>,
    val divers: List<Diver>,
    val ownerDiverId: String,
)
