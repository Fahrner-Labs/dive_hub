package dev.divehub.android.ui.logbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.api.models.ApiDivesGet200Response
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.LogbookPaging
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.logbook.StoredLogbookPage
import dev.divehub.shared.logbook.toggleFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** What the logbook screen draws; `error` is a code the UI translates, never the server's English text. */
data class LogbookUiState(
    val loading: Boolean = true,
    val refreshing: Boolean = false,
    val page: Int = 1,
    val filters: Set<LogbookFilter> = emptySet(),
    val data: ApiDivesGet200Response? = null,
    val providers: ProviderDirectory = ProviderDirectory.EMPTY,
    val error: String? = null,
    /** No answer at all: offline, or the instance cannot be reached (client contract §2). */
    val offline: Boolean = false,
    /** The store's last successful sync; the list's "Last synced …" line. Null before the first sync. */
    val syncedAt: String? = null,
    /** Dives with Recordings whose sample series are still syncing (phase 3); the per-row marker reads it. */
    val profilePending: Set<String> = emptySet(),
    /** A server-only filter was applied offline, so the stored list is shown without it. */
    val filterUnavailable: Boolean = false,
) {
    val pageCount: Int get() = data?.let { LogbookPaging.pageCount(it.total) } ?: 0
    val range: IntRange? get() = data?.let { LogbookPaging.range(page, it.total) }
}

/** `changed-since-sent` and `not-at-provider` need Push state the store does not hold (client contract, Offline reads). */
private val SERVER_ONLY_FILTERS = setOf(LogbookFilter.CHANGED_SINCE_SENT, LogbookFilter.NOT_AT_PROVIDER)

/**
 * The read-only logbook (ADR 0040): one page at a time, the "Show only" filters and the Provider names they and the
 * rows show. The store is the source (FAH-277); the two Push-backed filters are fetched online and said unavailable
 * offline. All list maths is in `shared/logbook`; this only holds what the screen last read.
 */
class LogbookViewModel(
    private val store: StoreLogbook,
    private val api: LogbookRepository,
    private val revision: StateFlow<Long>,
    private val offlineState: StateFlow<Boolean>,
    private val refreshSync: suspend () -> Unit,
) : ViewModel() {
    private val _state = MutableStateFlow(LogbookUiState())
    val state: StateFlow<LogbookUiState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var observing: Job? = null
    private var offlineJob: Job? = null
    private var providers: ProviderDirectory? = null
    private var loadedFor: String? = null

    /** Reads the first page for this User once; a different User (or a sign-in again) starts from nothing. */
    fun start(userId: String) {
        if (loadedFor == userId) return
        loadedFor = userId
        providers = null
        _state.value = LogbookUiState()
        observe()
        load(page = 1, filters = emptySet())
    }

    /** Forgets everything about the previous User (client contract §1 Security). */
    fun forget() {
        loadedFor = null
        providers = null
        loadJob?.cancel()
        observing?.cancel()
        offlineJob?.cancel()
        _state.value = LogbookUiState()
    }

    fun refresh() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.update { it.copy(refreshing = true, error = null) }
            try {
                refreshSync()
            } catch (_: Exception) {
                // The store stays the source; the cached page below is still worth showing.
            }
            read(_state.value.page, _state.value.filters, refreshing = true)
        }
    }

    fun retry() = load(_state.value.page, _state.value.filters)

    fun showPage(page: Int) = load(page, _state.value.filters)

    fun toggle(filter: LogbookFilter) = load(page = 1, filters = toggleFilter(_state.value.filters, filter))

    fun showAll() = load(page = 1, filters = emptySet())

    private fun load(page: Int, filters: Set<LogbookFilter>, refreshing: Boolean = false) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch { read(page, filters, refreshing) }
    }

    private suspend fun read(page: Int, filters: Set<LogbookFilter>, refreshing: Boolean) {
        _state.update {
            it.copy(
                loading = !refreshing && it.data == null,
                refreshing = refreshing,
                page = page,
                filters = filters,
                data = if (refreshing) it.data else null,
                error = null,
                filterUnavailable = false,
            )
        }
        val result = try {
            withContext(Dispatchers.IO) {
                if (store.canAnswer(filters)) store.page(page, filters) else readServerFiltered(page, filters)
            }
        } catch (error: DiveHubException) {
            _state.update { it.copy(loading = false, refreshing = false, error = error.code, offline = false) }
            return
        } catch (_: NoAnswerException) {
            // The server-only filter needs a connection; show the stored list without it rather than "no connection".
            val cached = withContext(Dispatchers.IO) { store.page(page, filters - SERVER_ONLY_FILTERS) }
            _state.update {
                it.copy(
                    loading = false,
                    refreshing = false,
                    data = cached.data,
                    syncedAt = cached.syncedAt,
                    profilePending = cached.profilePending,
                    offline = true,
                    filterUnavailable = filters.any(SERVER_ONLY_FILTERS::contains),
                )
            }
            return
        }
        // Before the first sync there is nothing to show: keep the loading state, or the pre-sync offline state.
        if (result.syncedAt == null && result.data.total == 0) {
            _state.update {
                it.copy(
                    loading = !offlineState.value,
                    refreshing = false,
                    data = null,
                    syncedAt = null,
                    profilePending = result.profilePending,
                    offline = offlineState.value,
                )
            }
            return
        }
        val directory = providers ?: api.providers().also {
            if (it.names.isNotEmpty() || it.connected.isNotEmpty()) providers = it
        }
        _state.update {
            it.copy(
                loading = false,
                refreshing = false,
                data = result.data,
                syncedAt = result.syncedAt,
                profilePending = result.profilePending,
                providers = directory,
                offline = offlineState.value,
                error = null,
            )
        }
    }

    /** The two Push-backed filters go to the API; everything else, including the page's rows, comes from the store. */
    private suspend fun readServerFiltered(page: Int, filters: Set<LogbookFilter>): StoredLogbookPage {
        val data = api.page(page, filters)
        val context = store.context()
        return StoredLogbookPage(data = data, syncedAt = context.syncedAt, profilePending = context.profilePending)
    }

    private fun observe() {
        observing?.cancel()
        offlineJob?.cancel()
        observing = viewModelScope.launch {
            var last = revision.value
            revision.collect { value ->
                if (value != last) {
                    last = value
                    load(_state.value.page, _state.value.filters, refreshing = true)
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
                LogbookViewModel(
                    store = container.storeLogbook,
                    api = container.logbook,
                    revision = container.syncRevision,
                    offlineState = container.syncOffline,
                    refreshSync = { container.refreshNow() },
                )
            }
        }
    }
}
