package dev.divehub.android.ui.logbook

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.SyncLine
import dev.divehub.android.ui.components.DiveRow
import dev.divehub.android.ui.components.EmptyState
import dev.divehub.android.ui.components.Section
import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewDivesItemParticipantsItem
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.SAME_DAY_SECONDS
import dev.divehub.shared.logbook.filterChips
import dev.divehub.shared.logbook.initials
import dev.divehub.shared.logbook.mixName
import dev.divehub.shared.logbook.monthGroups
import dev.divehub.shared.logbook.sketchScale
import dev.divehub.shared.sync.SyncProgress
import java.util.Locale

/** The participant circle's fixed size, not a spacing step. */
private val AvatarSize = 24.dp

/** The brief's `Radius.control` for a filled action; a quiet text action stays text. */
private val ControlCorner = 8.dp

/**
 * The logbook route: the list keeps its page and filters in the view model, not in an address. [onPage] tells the
 * destination when the User changed the page, so the route can save it for process death.
 */
@Composable
fun LogbookRoute(
    viewModel: LogbookViewModel,
    units: String?,
    onOpenDive: (String, Set<LogbookFilter>) -> Unit,
    onPage: (Int) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LogbookScreen(
        state = state,
        units = units,
        onRefresh = viewModel::refresh,
        onRetry = viewModel::retry,
        onShowAll = {
            viewModel.showAll()
            onPage(1)
        },
        onPage = { page ->
            viewModel.showPage(page)
            onPage(page)
        },
        onToggleFilter = { filter ->
            viewModel.toggle(filter)
            onPage(1)
        },
        onOpenDive = { dive -> onOpenDive(dive.id, state.filters) },
    )
}

/**
 * The logbook's one screen (ADR 0040): the Diver's whole logbook above the list, "Show only" filters with their counts,
 * rows under month headings, a page at a time and pull-to-refresh. One sheet under the frame's top bar, sections kept
 * apart by a hairline and the rows in the design system's one comfortable density (design-system-android.md).
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
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.logbook_title),
                        style = DiveHubText.section,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding).fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            val modifier = Modifier.fillMaxSize()
            when {
                state.loading && state.data == null -> Loading(modifier, state.syncProgress)
                state.offline && state.data == null -> Offline(modifier, onRetry)
                state.error != null && state.data == null -> Error(modifier, state.error, onRetry)
                else -> PullToRefreshBox(
                    isRefreshing = state.refreshing,
                    onRefresh = onRefresh,
                    modifier = modifier,
                ) {
                    LogbookList(state, units, onRetry, onShowAll, onPage, onToggleFilter, onOpenDive)
                }
            }
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
    Box(modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(Spacing.s5),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (countable) {
                CircularProgressIndicator(progress = { (done.toFloat() / total).coerceIn(0f, 1f) })
            } else {
                CircularProgressIndicator()
            }
            Spacer(Modifier.height(Spacing.s4))
            Text(
                label,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                style = DiveHubText.body,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun Offline(modifier: Modifier, onRetry: () -> Unit) {
    NoContent(
        modifier = modifier,
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
private fun NoContent(modifier: Modifier, message: String, retry: String, onRetry: () -> Unit) {
    Box(modifier, contentAlignment = Alignment.Center) {
        EmptyState(
            message = message,
            action = { InkButton(retry, onRetry) },
        )
    }
}

@Composable
private fun LogbookList(
    state: LogbookUiState,
    units: String?,
    onRetry: () -> Unit,
    onShowAll: () -> Unit,
    onPage: (Int) -> Unit,
    onToggleFilter: (LogbookFilter) -> Unit,
    onOpenDive: (DiveListViewDivesItem) -> Unit,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = LogbookText.imperial(units, locale)
    val data = state.data ?: return
    val groups = monthGroups(data.dives, data.months)
    val scaleM = sketchScale(data.totals.deepestM)
    val chips = filterChips(data.counts, state.filters)
    val filtersEmpty = state.filters.isEmpty()

    LazyColumn(Modifier.fillMaxSize()) {
        item(key = "header") {
            Column(Modifier.fillMaxWidth()) {
                SyncLine(state.syncedAt, state.connected, state.offline, R.string.logbook_offline_cached)
                Column(Modifier.padding(horizontal = Spacing.s4)) {
                    if (state.filterUnavailable) {
                        Text(
                            stringResource(R.string.logbook_filter_offline),
                            modifier = Modifier
                                .padding(vertical = Spacing.s1)
                                .semantics { liveRegion = LiveRegionMode.Polite },
                            style = DiveHubText.meta,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Summary(data, locale, imperial)
                    if (state.error != null) {
                        RefreshError(onRetry)
                    }
                }
            }
        }

        if (chips.isNotEmpty()) {
            item(key = "filters") {
                Section(
                    title = stringResource(R.string.logbook_show_only),
                    modifier = Modifier.padding(horizontal = Spacing.s4),
                    showSeparator = false,
                ) {
                    Filters(chips, state.providers.connected, onToggleFilter)
                }
            }
        }

        if (data.total > 0) {
            item(key = "range") { Range(state) }
        }

        if (data.total == 0) {
            item(key = "empty") {
                EmptyState(
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
                        if (!filtersEmpty) {
                            QuietButton(stringResource(R.string.logbook_show_all), onShowAll)
                        }
                    },
                )
            }
        } else {
            groups.forEach { group ->
                val month = group.month
                if (month != null) {
                    item(key = "month-$month") {
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

        if (state.pageCount > 1) {
            item(key = "pager") { Pager(state, onPage) }
        }
    }
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
    val deepest = totals.deepestM?.let { LogbookText.depth(context, it, locale, imperial) } ?: "–"
    val text = context.resources.getQuantityString(
        R.plurals.logbook_summary,
        totals.dives,
        LogbookText.number(locale, totals.dives),
        LogbookText.duration(context, totals.durationSeconds),
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
    FlowRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        verticalArrangement = Arrangement.spacedBy(Spacing.s1),
    ) {
        chips.forEach { chip ->
            FilterChip(
                selected = chip.applied,
                onClick = { onToggle(chip.filter) },
                label = {
                    Text("${LogbookText.filterName(context, chip.filter, connected)} · ${chip.count}")
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
        footer = {
            Column {
                if (dive.findings > 0) {
                    Text(
                        context.resources.getQuantityString(
                            R.plurals.logbook_findings,
                            dive.findings,
                            dive.findings,
                        ),
                        style = DiveHubText.metaStrong,
                        color = MaterialTheme.colorScheme.error,
                    )
                    Spacer(Modifier.height(Spacing.s1))
                }
                ProfileSketch(dive.profile, dive.recordings > 0, scaleM)
            }
        },
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
        MaterialTheme.colorScheme.surfaceVariant,
        MaterialTheme.colorScheme.errorContainer,
    )
    val tone = tones[(person.diverId.sumOf { it.code }) % tones.size]
    Box(
        Modifier
            .size(AvatarSize)
            .clip(CircleShape)
            .background(tone)
            .clearAndSetSemantics {},
        contentAlignment = Alignment.Center,
    ) {
        Text(initials(person.name), style = DiveHubText.metaStrong)
    }
}

@Composable
private fun Pager(state: LogbookUiState, onPage: (Int) -> Unit) {
    val pages = state.pageCount
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.s4, vertical = Spacing.s4),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuietButton(stringResource(R.string.logbook_previous), { onPage(state.page - 1) }, enabled = state.page > 1)
        Text(
            stringResource(R.string.logbook_page_of, state.page, pages),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        QuietButton(stringResource(R.string.logbook_next), { onPage(state.page + 1) }, enabled = state.page < pages)
    }
}

/** The brief's ink primary button: ink in the light, the inverted ink in the dark, with the control radius. */
@Composable
private fun InkButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(ControlCorner),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Text(text)
    }
}

/**
 * A text-like action (paging, "Show all dives", retry): no fill and no accent, because the accent means a link, focus
 * or data and these are navigation.
 */
@Composable
private fun QuietButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
    ) {
        Text(text)
    }
}
