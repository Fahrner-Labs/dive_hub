package dev.divehub.android.ui.logbook

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.SyncLine
import dev.divehub.android.ui.writes.WriteQueueLine
import dev.divehub.android.ui.components.ContentRevealMs
import dev.divehub.android.ui.components.DiveRow
import dev.divehub.android.ui.components.EmptyState
import dev.divehub.android.ui.components.InkButton
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.Radius
import dev.divehub.android.ui.components.SkeletonBar
import dev.divehub.android.ui.components.SkeletonDelayMs
import dev.divehub.android.ui.components.rememberReducedMotion
import dev.divehub.android.ui.components.rememberSkeletonAlpha
import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewDivesItemParticipantsItem
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.LogbookMonth
import dev.divehub.shared.logbook.LogbookPaging
import dev.divehub.shared.logbook.SAME_DAY_SECONDS
import dev.divehub.shared.logbook.SKETCH_HEIGHT
import dev.divehub.shared.logbook.SKETCH_WIDTH
import dev.divehub.shared.logbook.filterChips
import dev.divehub.shared.logbook.initials
import dev.divehub.shared.logbook.mixName
import dev.divehub.shared.logbook.monthGroups
import dev.divehub.shared.logbook.sketchScale
import dev.divehub.shared.sync.SyncProgress
import kotlinx.coroutines.delay
import java.util.Locale

/** The participant circle's fixed size, not a spacing step. */
private val AvatarSize = 24.dp

/** A chip's 1 dp outline and the progress track's end gap (the brief's pill chip; no M3 stop dot). */
private val ChipBorder = 1.dp
private val ProgressGap = 0.dp

/** The dive-number label column: a minimum so titles start on the same line, wide enough for four digits. */
private val NumberColumn = 32.dp

/** The skeleton's chip and row count; its bar height and corner come from the shared skeleton. */
private val SkeletonChipHeight = 32.dp
private val SkeletonFigureWidth = 48.dp
private val SkeletonFigureShortWidth = 36.dp
private const val SkeletonRows = 6

/** Which of the screen's exclusive states is on stage; [Crossfade] fades the content in over the skeleton. */
private enum class LogbookPhase { Loading, Offline, Error, List }

/**
 * The logbook route: the list keeps its filters in the view model and its scroll position in the destination's saved
 * state. [focusDiveId] and [focusPosition] are a Dive the User came back from; the list scrolls to its row, or pages to
 * it when a server-answered filter keeps the pager, then [onFocusHandled] clears them.
 */
@Composable
fun LogbookRoute(
    viewModel: LogbookViewModel,
    units: String?,
    onOpenDive: (String, Set<LogbookFilter>) -> Unit,
    focusDiveId: String? = null,
    focusPosition: Int? = null,
    onFocusHandled: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LogbookScreen(
        state = state,
        units = units,
        onRefresh = viewModel::refresh,
        onRetry = viewModel::retry,
        onShowAll = viewModel::showAll,
        onPage = viewModel::showPage,
        onToggleFilter = viewModel::toggle,
        focusDiveId = focusDiveId,
        focusPage = focusPosition?.let(LogbookPaging::pageOf),
        onFocusHandled = onFocusHandled,
        onOpenDive = { dive -> onOpenDive(dive.id, state.filters) },
    )
}

/**
 * The logbook's one screen (ADR 0040): the Diver's whole logbook above the list, "Show only" filters behind the top
 * bar's filter action, rows under sticky month headings and pull-to-refresh. One sheet under the frame's top bar,
 * sections kept apart by a hairline and the rows in the design system's one comfortable density
 * (design-system-android.md). The stored filters read the whole list; the two server-answered ones keep a pager.
 * Everything here is drawn from [state], so the contract-visible states have screenshot tests.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogbookScreen(
    state: LogbookUiState,
    units: String?,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onShowAll: () -> Unit,
    onPage: (Int) -> Unit,
    onToggleFilter: (LogbookFilter) -> Unit,
    onOpenDive: (DiveListViewDivesItem) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    focusDiveId: String? = null,
    focusPage: Int? = null,
    onFocusHandled: () -> Unit = {},
) {
    val data = state.data
    // One grouping for the list and the focus effect; a sync write replaces the data, a status update does not.
    val groups = remember(data) { data?.let { monthGroups(it.dives, it.months) }.orEmpty() }
    LaunchedEffect(focusDiveId, focusPage, state.paged, groups) {
        if (data == null || (focusDiveId == null && focusPage == null)) return@LaunchedEffect
        if (state.paged) {
            // The server-answered filters keep a pager; the row may sit on another page, so turn the position into one.
            focusPage?.let { page -> if (page != state.page) onPage(page) }
        } else if (focusDiveId != null) {
            val index = logbookDiveItemIndex(groups, focusDiveId)
            // The month header is pinned over the top edge; one item up keeps the Dive's title clear of it.
            if (index != null) listState.scrollToItem((index - 1).coerceAtLeast(0))
        }
        onFocusHandled()
    }
    val counts = state.data?.counts ?: state.lastCounts
    val chips = counts?.let { filterChips(it, state.filters) }.orEmpty()
    var filtersOpen by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(chips.isEmpty()) {
        if (chips.isEmpty()) filtersOpen = false
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DiveHubTopBar(
                title = stringResource(R.string.logbook_title),
                titleModifier = Modifier.semantics { heading() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                actions = {
                    if (chips.isNotEmpty()) {
                        FilterAction(selectedCount = state.filters.size, onClick = { filtersOpen = true })
                    }
                },
            )
        },
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding).fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            val modifier = Modifier.fillMaxSize()
            val phase = when {
                state.loading && state.data == null -> LogbookPhase.Loading
                state.offline && state.data == null -> LogbookPhase.Offline
                state.error != null && state.data == null -> LogbookPhase.Error
                else -> LogbookPhase.List
            }
            val reducedMotion = rememberReducedMotion()
            Crossfade(
                targetState = phase,
                animationSpec = tween(if (reducedMotion) 0 else ContentRevealMs),
                label = "logbook content",
            ) { shown ->
                when (shown) {
                    LogbookPhase.Loading -> Loading(modifier, state.syncProgress)
                    LogbookPhase.Offline -> Offline(modifier, onRetry)
                    LogbookPhase.Error -> Error(modifier, state.error ?: ErrorCodes.SERVER_ERROR, onRetry)
                    LogbookPhase.List -> PullToRefreshBox(
                        isRefreshing = state.refreshing,
                        onRefresh = onRefresh,
                        modifier = modifier,
                    ) {
                        LogbookList(state, units, groups, onRefresh, onRetry, onShowAll, onPage, onOpenDive, listState)
                    }
                }
            }
        }
    }

    if (filtersOpen && chips.isNotEmpty()) {
        FilterSheet(
            chips = chips,
            connected = state.providers.connected,
            unavailable = state.filterUnavailable,
            onToggle = onToggleFilter,
            onDismiss = { filtersOpen = false },
        )
    }
}

@Composable
private fun FilterAction(selectedCount: Int, onClick: () -> Unit) {
    val label = if (selectedCount > 0) {
        stringResource(R.string.logbook_filters_selected, selectedCount)
    } else {
        stringResource(R.string.logbook_filters)
    }
    Box(
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .clickable(role = Role.Button, onClick = onClick)
            .semantics(mergeDescendants = true) { contentDescription = label }
            .padding(horizontal = Spacing.s2),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_filter), contentDescription = null)
            if (selectedCount > 0) {
                Spacer(Modifier.width(Spacing.s1))
                Badge(
                    modifier = Modifier
                        .border(ChipBorder, MaterialTheme.colorScheme.outline, CircleShape)
                        .clearAndSetSemantics {},
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ) {
                    Text(
                        selectedCount.toString(),
                        style = DiveHubText.metaStrong.copy(fontFeatureSettings = "tnum"),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterSheet(
    chips: List<dev.divehub.shared.logbook.FilterChip>,
    connected: List<String>,
    unavailable: Boolean,
    onToggle: (LogbookFilter) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = RoundedCornerShape(topStart = Radius.sheet, topEnd = Radius.sheet),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.s5, vertical = Spacing.s5),
            verticalArrangement = Arrangement.spacedBy(Spacing.s2),
        ) {
            Text(
                stringResource(R.string.logbook_show_only),
                modifier = Modifier.semantics { heading() },
                style = DiveHubText.section,
            )
            if (unavailable) {
                Text(
                    stringResource(R.string.logbook_filter_offline),
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Filters(chips, connected, onToggle)
        }
    }
}

@Composable
private fun Loading(modifier: Modifier, progress: SyncProgress?) {
    val total = progress?.storedDives ?: 0
    val done = progress?.detailedDives ?: 0
    // Phase 1 only knows how many Dives there are; the count of detailed Dives makes the bar determinate.
    val countable = total > 0 && done > 0
    val label = if (countable) {
        stringResource(R.string.logbook_sync_progress, done, total)
    } else {
        stringResource(R.string.logbook_sync_progress_starting)
    }
    var showBars by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(SkeletonDelayMs)
        showBars = true
    }
    // ADR 0064: the bars pulse gently, and stand still when the system's animations are off.
    val barAlpha = rememberSkeletonAlpha("logbook skeleton")
    // The brief's rule 7: the logbook's own shape in flat bars, not a centred spinner. The quiet meta line keeps the
    // determinate sync count; the bars are decoration, so only the label speaks.
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.s4),
    ) {
        Spacer(Modifier.height(Spacing.s4))
        Text(
            label,
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (countable) {
            Spacer(Modifier.height(Spacing.s2))
            LinearProgressIndicator(
                progress = { (done.toFloat() / total).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                gapSize = ProgressGap,
                drawStopIndicator = {},
            )
        }
        if (showBars) {
            Spacer(Modifier.height(Spacing.s5))
            SkeletonBar(Modifier.fillMaxWidth(0.55f), alpha = barAlpha)
            Spacer(Modifier.height(Spacing.s5))
            SkeletonBar(Modifier.fillMaxWidth(0.28f), alpha = barAlpha)
            Spacer(Modifier.height(Spacing.s2))
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s2)) {
                SkeletonBar(Modifier.fillMaxWidth(0.3f), height = SkeletonChipHeight, alpha = barAlpha)
                SkeletonBar(Modifier.fillMaxWidth(0.24f), height = SkeletonChipHeight, alpha = barAlpha)
            }
            Spacer(Modifier.height(Spacing.s5))
            SkeletonBar(Modifier.fillMaxWidth(0.36f), alpha = barAlpha)
            Spacer(Modifier.height(Spacing.s2))
            repeat(SkeletonRows) { index -> SkeletonDiveRow(index, barAlpha) }
        }
    }
}

/** The logbook's settling shape: row bars where the number, title, facts and sketch land. */
@Composable
private fun SkeletonDiveRow(index: Int, alpha: Float) {
    // The row's final layout: number, then title, facts and the sketch under them, and the end-aligned figure lines.
    val numberWidth = NumberColumn * LocalDensity.current.fontScale
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.s3),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
        verticalAlignment = Alignment.Top,
    ) {
        SkeletonBar(Modifier.widthIn(min = numberWidth), alpha = alpha)
        Column(Modifier.weight(1f)) {
            SkeletonBar(Modifier.fillMaxWidth(if (index % 3 == 0) 0.62f else 0.48f), alpha = alpha)
            Spacer(Modifier.height(Spacing.s1))
            SkeletonBar(Modifier.fillMaxWidth(0.34f), alpha = alpha)
            Spacer(Modifier.height(Spacing.s1))
            SkeletonBar(
                Modifier.width(SKETCH_WIDTH.dp),
                height = SKETCH_HEIGHT.dp,
                alpha = alpha,
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            SkeletonBar(Modifier.width(SkeletonFigureWidth), alpha = alpha)
            Spacer(Modifier.height(Spacing.s1))
            SkeletonBar(Modifier.width(SkeletonFigureShortWidth), alpha = alpha)
        }
    }
}

@Composable
private fun Offline(modifier: Modifier, onRetry: () -> Unit) {
    NoContent(
        modifier = modifier,
        title = stringResource(R.string.logbook_offline_title),
        message = stringResource(R.string.logbook_offline),
        retry = stringResource(R.string.logbook_retry),
        onRetry = onRetry,
    )
}

@Composable
private fun Error(modifier: Modifier, code: String, onRetry: () -> Unit) {
    val context = LocalContext.current
    NoContent(
        modifier = modifier,
        title = stringResource(R.string.logbook_error_title),
        message = when (code) {
            ErrorCodes.SIGN_IN_REQUIRED -> context.getString(R.string.error_sign_in_required)
            ErrorCodes.SERVER_ERROR -> context.getString(R.string.error_server)
            else -> context.getString(R.string.error_unknown)
        },
        retry = stringResource(R.string.logbook_retry),
        onRetry = onRetry,
    )
}

@Composable
private fun NoContent(modifier: Modifier, title: String, message: String, retry: String, onRetry: () -> Unit) {
    Box(modifier, contentAlignment = Alignment.Center) {
        EmptyState(
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            title = title,
            message = message,
            action = { InkButton(retry, onRetry) },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LogbookList(
    state: LogbookUiState,
    units: String?,
    groups: List<LogbookMonth>,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onShowAll: () -> Unit,
    onPage: (Int) -> Unit,
    onOpenDive: (DiveListViewDivesItem) -> Unit,
    listState: LazyListState,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = LogbookText.imperial(units, locale)
    val data = state.data ?: return
    val scaleM = sketchScale(data.totals.deepestM)
    val filtersEmpty = state.filters.isEmpty()

    // The scrolling content is the sheet; the frame stays `background` (design system, "Surfaces").
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        // The list ends in plain rows: keep the rhythm above the bottom bar (FAH-424). The empty state stays
        // centred in the full viewport, as the offline and error states are.
        contentPadding = if (data.total == 0) PaddingValues() else PaddingValues(bottom = Spacing.s5),
    ) {
        item(key = "header") {
            Column(Modifier.fillMaxWidth()) {
                SyncLine(state.syncedAt, state.connected, state.offline, R.string.logbook_offline_cached)
                WriteQueueLine()
                Column(Modifier.padding(horizontal = Spacing.s4)) {
                    Summary(data, locale, imperial)
                    if (state.error != null) {
                        RefreshError(onRetry)
                    }
                }
            }
        }

        if (state.paged && data.total > 0) {
            item(key = "range") { Range(state) }
        }

        if (data.total == 0) {
            item(key = "empty") {
                // A genuinely empty logbook takes the same centred placement as offline and error. With filters
                // pressed the list is empty only because of them: the message stays directly under the summary,
                // where it is seen, not a viewport below it.
                val placement = if (filtersEmpty) {
                    Modifier.fillParentMaxHeight().fillMaxWidth()
                } else {
                    Modifier.fillMaxWidth()
                }
                Box(placement, contentAlignment = Alignment.Center) {
                    EmptyState(
                        title = if (filtersEmpty) stringResource(R.string.logbook_empty_title) else null,
                        message = if (filtersEmpty) {
                            stringResource(R.string.logbook_none)
                        } else {
                            stringResource(
                                R.string.logbook_no_match_filters,
                                state.filters.map { LogbookText.filterName(context, it, state.providers.connected) }
                                    .joinToString(", "),
                            )
                        },
                        action = {
                            // No dead end (the brief's EmptyState): filters go back to all dives, and a genuinely
                            // empty logbook offers the refresh that would pick up a first import.
                            if (filtersEmpty) {
                                InkButton(stringResource(R.string.logbook_refresh), onRefresh)
                            } else {
                                QuietButton(stringResource(R.string.logbook_show_all), onShowAll)
                            }
                        },
                    )
                }
            }
        } else {
            groups.forEach { group ->
                val month = group.month
                if (month != null) {
                    stickyHeader(key = "month-$month") {
                        MonthHead(context, month, group.figures?.dives, group.figures?.durationSeconds, locale)
                    }
                }
                items(group.dives, key = { it.id }) { dive ->
                    LogbookDiveRow(
                        dive = dive,
                        scaleM = scaleM,
                        providerName = state.providers::nameOf,
                        profilePending = dive.id in state.profilePending,
                        locale = locale,
                        imperial = imperial,
                        onOpen = { onOpenDive(dive) },
                    )
                    HorizontalDivider(color = DiveHubColors.border)
                }
            }
        }

        if (state.paged && state.pageCount > 1) {
            item(key = "pager") { Pager(state, onPage) }
        }
    }
}

/**
 * The `LazyColumn` item of [diveId]: the header is item 0, then each month heading followed by its rows. Null when the
 * filter does not show the Dive — the server-answered pager may stand on another page.
 */
internal fun logbookDiveItemIndex(groups: List<LogbookMonth>, diveId: String): Int? {
    var index = 1
    for (group in groups) {
        if (group.month != null) index += 1
        val at = group.dives.indexOfFirst { it.id == diveId }
        if (at >= 0) return index + at
        index += group.dives.size
    }
    return null
}

@Composable
private fun RefreshError(onRetry: () -> Unit) {
    Column(Modifier.padding(vertical = Spacing.s2)) {
        Text(
            stringResource(R.string.logbook_refresh_failed),
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = DiveHubText.body,
            color = MaterialTheme.colorScheme.error,
        )
        QuietButton(stringResource(R.string.logbook_retry), onRetry)
    }
}

@Composable
private fun Summary(data: DiveListView, locale: Locale, imperial: Boolean) {
    val context = LocalContext.current
    val totals = data.totals
    if (totals.dives == 0) return
    // The summary is one sentence, so its figures stay whole: a value never leaves its unit at 200 % or in German.
    val deepest = (totals.deepestM?.let { LogbookText.depth(context, it, locale, imperial) } ?: "–").noBreakSpaces()
    val text = context.resources.getQuantityString(
        R.plurals.logbook_summary,
        totals.dives,
        LogbookText.number(locale, totals.dives),
        LogbookText.duration(context, totals.durationSeconds).noBreakSpaces(),
        deepest,
    )
    Text(
        text,
        modifier = Modifier.padding(vertical = Spacing.s4),
        style = DiveHubText.body,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Filters(
    chips: List<dev.divehub.shared.logbook.FilterChip>,
    connected: List<String>,
    onToggle: (LogbookFilter) -> Unit,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    FlowRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        verticalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        chips.forEach { chip ->
            FilterChip(
                selected = chip.applied,
                onClick = { onToggle(chip.filter) },
                shape = RoundedCornerShape(percent = 50),
                border = BorderStroke(ChipBorder, MaterialTheme.colorScheme.outline),
                label = {
                    val countColor = MaterialTheme.colorScheme.onSurfaceVariant
                    val countStyle = SpanStyle(
                        fontSize = DiveHubText.meta.fontSize,
                        fontWeight = DiveHubText.meta.fontWeight,
                        letterSpacing = DiveHubText.meta.letterSpacing,
                        color = countColor,
                    )
                    // A non-breaking space keeps the count with the label's last word when the chip wraps.
                    Text(
                        buildAnnotatedString {
                            append(LogbookText.filterName(context, chip.filter, connected))
                            append('\u00A0')
                            withStyle(countStyle) { append(LogbookText.number(locale, chip.count)) }
                        },
                        style = DiveHubText.label,
                    )
                },
                leadingIcon = if (chip.applied) {
                    {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = null,
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

@Composable
private fun Range(state: LogbookUiState) {
    val context = LocalContext.current
    val range = state.range ?: return
    val total = state.data?.total ?: return
    val text = context.resources.getQuantityString(
        R.plurals.logbook_range,
        total,
        range.first,
        range.last,
        total,
    )
    Text(
        text,
        modifier = Modifier.padding(horizontal = Spacing.s4, vertical = Spacing.s2),
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MonthHead(context: Context, month: String, dives: Int?, durationSeconds: Double?, locale: Locale) {
    FlowRow(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = Spacing.s4, end = Spacing.s4, top = Spacing.s5, bottom = Spacing.s2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(Spacing.s1),
        itemVerticalAlignment = Alignment.Bottom,
    ) {
        Text(
            LogbookText.month(context, month, locale),
            modifier = Modifier.semantics { heading() },
            style = DiveHubText.bodyStrong,
        )
        if (dives != null && durationSeconds != null) {
            Text(
                context.resources.getQuantityString(
                    R.plurals.logbook_month_figures,
                    dives,
                    dives,
                    LogbookText.duration(context, durationSeconds),
                ),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun LogbookDiveRow(
    dive: DiveListViewDivesItem,
    scaleM: Double,
    providerName: (String) -> String,
    profilePending: Boolean,
    locale: Locale,
    imperial: Boolean,
    onOpen: () -> Unit,
) {
    val context = LocalContext.current
    val numberWidth = NumberColumn * LocalDensity.current.fontScale
    val day = LogbookText.diveDay(context, dive.startsAt, dive.utcOffsetSeconds, dive.utcOffsetSource, locale)
    val noSite = stringResource(R.string.logbook_no_site)
    val air = stringResource(R.string.dive_air)
    val noRecording = stringResource(R.string.logbook_no_recording)
    val profileSyncing = stringResource(R.string.logbook_profile_pending)
    val fromProvider = dive.fromProvider?.let { stringResource(R.string.logbook_from_provider, providerName(it)) }
    val surfaceInterval = dive.surfaceIntervalSeconds?.takeIf { it < SAME_DAY_SECONDS }?.let {
        stringResource(R.string.logbook_surface_interval, LogbookText.duration(context, it))
    }
    val facts = buildList {
        add(if (dive.site != null) day else noSite)
        dive.gases.forEach { gas -> add(mixName(gas.o2, gas.he, air)) }
        if (dive.recordings == 0) {
            add(noRecording)
            fromProvider?.let { add(it) }
        }
        surfaceInterval?.let { add(it) }
        if (profilePending && dive.recordings > 0) {
            add(profileSyncing)
        }
    }
    DiveRow(
        title = dive.site?.name ?: day,
        depth = dive.maxDepthM?.let { LogbookText.depth(context, it, locale, imperial) } ?: "–",
        duration = LogbookText.duration(context, dive.durationSeconds),
        modifier = Modifier.padding(horizontal = Spacing.s4),
        leading = {
            Text(
                dive.number?.toString() ?: "–",
                modifier = Modifier.widthIn(min = numberWidth),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
            )
        },
        facts = facts,
        avatars = if (dive.participants.isEmpty()) {
            null
        } else {
            { Participants(dive.participants) }
        },
        footer = { ProfileSketch(dive.profile, dive.recordings > 0, scaleM) },
        onClick = onOpen,
    )
}

/**
 * Who was on the Dive: up to three circles with initials, then "+2". The circles are decoration; the names and roles
 * are the row's accessible text (client contract §2), so the circle alone never carries the meaning.
 */
@Composable
private fun Participants(people: List<DiveListViewDivesItemParticipantsItem>) {
    val context = LocalContext.current
    val description = context.getString(
        R.string.logbook_with,
        people.joinToString(", ") { "${it.name} (${LogbookText.role(context, it.role)})" },
    )
    Row(
        Modifier
            .semantics(mergeDescendants = true) { contentDescription = description },
        horizontalArrangement = Arrangement.spacedBy(Spacing.s1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        people.take(3).forEach { person -> AvatarCircle(person) }
        val more = people.size - 3
        if (more > 0) {
            Text(
                context.getString(R.string.logbook_more_people, more),
                modifier = Modifier.padding(start = Spacing.s2),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun AvatarCircle(person: DiveListViewDivesItemParticipantsItem) {
    val tones = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        DiveHubColors.successSoft,
        MaterialTheme.colorScheme.surfaceVariant,
    )
    val tone = tones[(person.diverId.sumOf { it.code }) % tones.size]
    // The circle grows with the font scale so the scaled initials never clip at 200 %.
    val size = AvatarSize * LocalDensity.current.fontScale
    Box(
        Modifier
            .size(size)
            .clip(CircleShape)
            .background(tone)
            .clearAndSetSemantics {},
        contentAlignment = Alignment.Center,
    ) {
        Text(initials(person.name), style = DiveHubText.metaStrong)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Pager(state: LogbookUiState, onPage: (Int) -> Unit) {
    val pages = state.pageCount
    // The actions wrap instead of overflowing: at 320 dp and 200 % two "previous/next page" labels cannot share a row.
    // The quiet action has no horizontal inner padding, so the row carries the separation the old TextButton gave
    // its labels; s2 alone read as one tight run (FAH-427).
    FlowRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.s4, vertical = Spacing.s4),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s4, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(Spacing.s2),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        QuietButton(stringResource(R.string.logbook_previous), { onPage(state.page - 1) }, enabled = state.page > 1)
        Text(
            stringResource(R.string.logbook_page_of, state.page, pages),
            textAlign = TextAlign.Center,
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        QuietButton(stringResource(R.string.logbook_next), { onPage(state.page + 1) }, enabled = state.page < pages)
    }
}

/** Keeps a formatted figure and its unit together while the summary wraps. */
private fun String.noBreakSpaces(): String = replace(' ', '\u00A0')
