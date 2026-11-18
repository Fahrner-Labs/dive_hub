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
import dev.divehub.shared.logbook.toggleFilter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** What the logbook screen draws; `error` is a code the UI translates, never the server's English text. */
data class LogbookUiState(
    val loading: Boolean = true,
    val refreshing: Boolean = false,
    val page: Int = 1,
    val filters: Set<LogbookFilter> = emptySet(),
    val data: ApiDivesGet200Response? = null,
    val providers: ProviderDirectory = ProviderDirectory.EMPTY,
    val error: String? = null,
    /** No answer at all: offline, or the instance cannot be reached. Distinct from an error code (client contract §2). */
    val offline: Boolean = false,
) {
    val pageCount: Int get() = data?.let { LogbookPaging.pageCount(it.total) } ?: 0
    val range: IntRange? get() = data?.let { LogbookPaging.range(page, it.total) }
}

/**
 * The read-only logbook (ADR 0040): one page at a time, the "Show only" filters and the Provider names they and the
 * rows show. All list maths is in `shared/logbook`; this only holds what the screen last read.
 */
class LogbookViewModel(private val repository: LogbookRepository) : ViewModel() {
    private val _state = MutableStateFlow(LogbookUiState())
    val state: StateFlow<LogbookUiState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var providers: ProviderDirectory? = null

    init {
        load(page = 1, filters = emptySet())
    }

    fun refresh() = load(_state.value.page, _state.value.filters, refreshing = true)

    fun retry() = load(_state.value.page, _state.value.filters)

    fun showPage(page: Int) = load(page, _state.value.filters)

    fun toggle(filter: LogbookFilter) = load(page = 1, filters = toggleFilter(_state.value.filters, filter))

    fun showAll() = load(page = 1, filters = emptySet())

    private fun load(page: Int, filters: Set<LogbookFilter>, refreshing: Boolean = false) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = !refreshing,
                    refreshing = refreshing,
                    page = page,
                    filters = filters,
                    data = if (refreshing) it.data else null,
                    error = null,
                    offline = false,
                )
            }
            try {
                val data = repository.page(page, filters)
                val directory = providers ?: repository.providers().also {
                    if (it.names.isNotEmpty() || it.connected.isNotEmpty()) providers = it
                }
                _state.update { it.copy(loading = false, refreshing = false, data = data, providers = directory) }
            } catch (error: DiveHubException) {
                _state.update { it.copy(loading = false, refreshing = false, error = error.code, offline = false) }
            } catch (_: NoAnswerException) {
                _state.update { it.copy(loading = false, refreshing = false, offline = true) }
            }
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { LogbookViewModel(container.logbook) }
        }
    }
}
