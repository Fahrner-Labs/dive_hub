package dev.divehub.android.ui.logbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.android.ui.followStoreWrites
import dev.divehub.api.models.DiveListView
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.LogbookPaging
import dev.divehub.shared.logbook.LogbookRepository
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.logbook.StoredLogbookPage
import dev.divehub.shared.logbook.toggleFilter
import dev.divehub.shared.sync.SyncProgress
import kotlinx.coroutines.CoroutineDispatcher
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
    val data: DiveListView? = null,
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
    /** The sync's phase counts while the first page is still syncing; null until the first report. */
    val syncProgress: SyncProgress? = null,
) {
    val pageCount: Int get() = data?.let { LogbookPaging.pageCount(it.total) } ?: 0
    val range: IntRange? get() = data?.let { LogbookPaging.range(page, it.total) }
}

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
    private val syncProgress: StateFlow<SyncProgress>,
    private val refreshSync: suspend () -> Unit,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow(LogbookUiState())
    val state: StateFlow<LogbookUiState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var followJob: Job? = null
    private var observing: Job? = null
    private var offlineJob: Job? = null
    private var progressJob: Job? = null
    private var providers: ProviderDirectory? = null
    private var loadedFor: String? = null

    /** A pull-to-refresh owns the spinner and reads once at its end; revisions it writes are not followed again. */
    private var gesture = false
    private var followedRevision = 0L

    /**
     * Reads the first page for this User once; a different User (or a sign-in again) starts from nothing. [page] is
     * the page restored from the destination after process death, so the list comes back where the User left it.
     */
    fun start(userId: String, page: Int = 1) {
        if (loadedFor == userId) return
        loadedFor = userId
        providers = null
        gesture = false
        val first = page.coerceAtLeast(1)
        _state.value = LogbookUiState(page = first)
        observe()
        load(page = first, filters = emptySet())
    }

    /** Forgets everything about the previous User (client contract §1 Security). */
    fun forget() {
        loadedFor = null
        providers = null
        gesture = false
        loadJob?.cancel()
        followJob?.cancel()
        observing?.cancel()
        offlineJob?.cancel()
        progressJob?.cancel()
        _state.value = LogbookUiState()
    }

    fun refresh() {
        loadJob?.cancel()
        followJob?.cancel()
        loadJob = viewModelScope.launch {
            gesture = true
            _state.update { it.copy(refreshing = true, error = null) }
            try {
                refreshSync()
            } catch (_: Exception) {
                // The store stays the source; the cached page below is still worth showing.
            }
            // Everything this gesture wrote is read below; the debounced follow-up would read it again.
            followedRevision = revision.value
            try {
                read(_state.value.page, _state.value.filters, refreshing = true, keepData = true)
            } finally {
                gesture = false
            }
            // A write that landed while this gesture's read ran was skipped by the guard; reread it once.
            if (revision.value > followedRevision) {
                followedRevision = revision.value
                read(_state.value.page, _state.value.filters, refreshing = false, keepData = true)
            }
        }
    }

    fun retry() = refresh()

    fun showPage(page: Int) = load(page, _state.value.filters)

    fun toggle(filter: LogbookFilter) = load(page = 1, filters = toggleFilter(_state.value.filters, filter))

    fun showAll() = load(page = 1, filters = emptySet())

    private fun load(page: Int, filters: Set<LogbookFilter>) {
        loadJob?.cancel()
        followJob?.cancel()
        loadJob = viewModelScope.launch { read(page, filters, refreshing = false, keepData = false) }
    }

    private suspend fun read(page: Int, filters: Set<LogbookFilter>, refreshing: Boolean, keepData: Boolean) {
        _state.update {
            it.copy(
                loading = if (keepData) it.loading && it.data == null else it.data == null,
                refreshing = refreshing,
                page = page,
                filters = filters,
                data = if (keepData) it.data else null,
                error = null,
                filterUnavailable = false,
            )
        }
        val result = try {
            withContext(ioDispatcher) {
                if (store.canAnswer(filters)) store.page(page, filters) else readServerFiltered(page, filters)
            }
        } catch (error: DiveHubException) {
            _state.update { it.copy(loading = false, refreshing = false, error = error.code, offline = false) }
            return
        } catch (_: NoAnswerException) {
            // The server-only filter needs a connection; show the stored list without it rather than "no connection".
            val cached = withContext(ioDispatcher) { store.page(page, store.storedFilters(filters)) }
            val preSync = cached.syncedAt == null && cached.data.total == 0
            _state.update {
                it.copy(
                    loading = false,
                    refreshing = false,
                    data = if (preSync) null else cached.data,
                    syncedAt = if (preSync) null else cached.syncedAt,
                    profilePending = cached.profilePending,
                    offline = true,
                    filterUnavailable = !store.canAnswer(filters),
                )
            }
            return
        }
        // A page change or a new User since this read started owns the state now.
        if (_state.value.page != page || _state.value.filters != filters) return
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
        _state.update {
            it.copy(
                loading = false,
                refreshing = false,
                data = result.data,
                syncedAt = result.syncedAt,
                profilePending = result.profilePending,
                offline = offlineState.value,
                error = null,
            )
        }
        val directory = providers ?: api.providers().also {
            if (it.names.isNotEmpty() || it.connected.isNotEmpty()) providers = it
        }
        if (_state.value.page != page || _state.value.filters != filters) return
        _state.update { it.copy(providers = directory) }
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
        progressJob?.cancel()
        followedRevision = revision.value
        observing = viewModelScope.launch {
            revision.followStoreWrites().collect { value ->
                if (gesture || value <= followedRevision) return@collect
                followedRevision = value
                // A page or filter change cancels this read: it belongs to the page that asked for it.
                followJob?.cancel()
                followJob = viewModelScope.launch {
                    read(_state.value.page, _state.value.filters, refreshing = false, keepData = true)
                }
            }
        }
        offlineJob = viewModelScope.launch {
            offlineState.collect { offline -> _state.update { it.copy(offline = offline) } }
        }
        progressJob = viewModelScope.launch {
            syncProgress.collect { progress -> _state.update { it.copy(syncProgress = progress) } }
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
                    syncProgress = container.syncProgress,
                    refreshSync = { container.refreshNow() },
                )
            }
        }
    }
}
