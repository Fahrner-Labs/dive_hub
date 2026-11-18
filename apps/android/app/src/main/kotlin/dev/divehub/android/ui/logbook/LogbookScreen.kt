package dev.divehub.android.ui.logbook

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import android.content.Context
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
import dev.divehub.api.models.ApiDivesGet200Response
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItem
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItemParticipantsItem
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.SAME_DAY_SECONDS
import dev.divehub.shared.logbook.filterChips
import dev.divehub.shared.logbook.initials
import dev.divehub.shared.logbook.mixName
import dev.divehub.shared.logbook.monthGroups
import dev.divehub.shared.logbook.sketchScale
import java.util.Locale

/** The logbook route: the list keeps its page and filters in the view model, not in an address. */
@Composable
fun LogbookRoute(viewModel: LogbookViewModel, units: String?, onSignOut: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LogbookScreen(
        state = state,
        units = units,
        onRefresh = viewModel::refresh,
        onRetry = viewModel::retry,
        onShowAll = viewModel::showAll,
        onPage = viewModel::showPage,
        onToggleFilter = viewModel::toggle,
        onSignOut = onSignOut,
    )
}

/**
 * The logbook's one screen (ADR 0040): the Diver's whole logbook above the list, "Show only" filters with their counts,
 * rows under month headings, a page at a time and pull-to-refresh. Everything here is drawn from [state], so the
 * contract-visible states have screenshot tests.
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
    onSignOut: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.logbook_title)) },
                actions = { TextButton(onClick = onSignOut) { Text(stringResource(R.string.sign_out)) } },
            )
        },
    ) { padding ->
        val modifier = Modifier.padding(padding).fillMaxSize()
        when {
            state.loading && state.data == null -> Loading(modifier)
            state.offline && state.data == null -> Offline(modifier, onRetry)
            state.error != null && state.data == null -> Error(modifier, state.error, onRetry)
            else -> PullToRefreshBox(
                isRefreshing = state.refreshing,
                onRefresh = onRefresh,
                modifier = modifier,
            ) {
                LogbookList(state, units, onRetry, onShowAll, onPage, onToggleFilter)
            }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier) {
    val label = stringResource(R.string.loading)
    Box(modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(Modifier.semantics { contentDescription = label })
    }
}

@Composable
private fun Offline(modifier: Modifier, onRetry: () -> Unit) {
    Notice(
        modifier = modifier,
        text = stringResource(R.string.logbook_offline),
        retry = stringResource(R.string.logbook_retry),
        onRetry = onRetry,
    )
}

@Composable
private fun Error(modifier: Modifier, code: String, onRetry: () -> Unit) {
    val context = LocalContext.current
    Notice(
        modifier = modifier,
        text = when (code) {
            ErrorCodes.SIGN_IN_REQUIRED -> context.getString(R.string.error_sign_in_required)
            ErrorCodes.SERVER_ERROR -> context.getString(R.string.error_server)
            else -> context.getString(R.string.error_unknown)
        },
        retry = stringResource(R.string.logbook_retry),
        onRetry = onRetry,
    )
}

@Composable
private fun Notice(modifier: Modifier, text: String, retry: String, onRetry: () -> Unit) {
    Column(
        modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text,
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) { Text(retry) }
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
        item {
            Totals(data, locale, imperial)
            if (chips.isNotEmpty()) {
                Filters(chips, state.providers.connected, onToggleFilter)
            }
            if (state.error != null) {
                Text(
                    stringResource(R.string.logbook_refresh_failed),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .semantics { liveRegion = LiveRegionMode.Polite },
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
                TextButton(onClick = onRetry) { Text(stringResource(R.string.logbook_retry)) }
            }
            if (data.total > 0) Range(state)
        }

        if (data.total == 0) {
            item {
                Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        if (filtersEmpty) {
                            stringResource(R.string.logbook_none)
                        } else {
                            stringResource(
                                R.string.logbook_no_match_filters,
                                state.filters.map { LogbookText.filterName(context, it, state.providers.connected) }
                                    .joinToString(", "),
                            )
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                    if (!filtersEmpty) {
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = onShowAll) { Text(stringResource(R.string.logbook_show_all)) }
                    }
                }
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
                    DiveRow(
                        dive = dive,
                        scaleM = scaleM,
                        providerName = state.providers::nameOf,
                        locale = locale,
                        imperial = imperial,
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                }
            }
        }

        if (state.pageCount > 1) {
            item(key = "pager") { Pager(state, onPage) }
        }
    }
}

@Composable
private fun Totals(data: ApiDivesGet200Response, locale: Locale, imperial: Boolean) {
    val context = LocalContext.current
    val totals = data.totals
    val text = if (totals.dives == 0) {
        stringResource(R.string.logbook_none)
    } else {
        val deepest = totals.deepestM?.let { LogbookText.depth(context, it, locale, imperial) } ?: "–"
        context.resources.getQuantityString(
            R.plurals.logbook_summary,
            totals.dives,
            LogbookText.number(locale, totals.dives),
            LogbookText.duration(context, totals.durationSeconds),
            deepest,
        )
    }
    Text(
        text,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .semantics { heading() },
        style = MaterialTheme.typography.bodyMedium,
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
    Column(Modifier.padding(bottom = 4.dp)) {
        Text(
            stringResource(R.string.logbook_show_only),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FlowRow(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            chips.forEach { chip ->
                FilterChip(
                    selected = chip.applied,
                    onClick = { onToggle(chip.filter) },
                    label = { Text("${LogbookText.filterName(context, chip.filter, connected)} · ${chip.count}") },
                )
            }
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
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun MonthHead(context: Context, month: String, dives: Int?, durationSeconds: Double?, locale: Locale) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            LogbookText.month(context, month, locale),
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.titleMedium,
        )
        if (dives != null && durationSeconds != null) {
            Text(
                context.resources.getQuantityString(
                    R.plurals.logbook_month_figures,
                    dives,
                    dives,
                    LogbookText.duration(context, durationSeconds),
                ),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DiveRow(
    dive: ApiDivesGet200ResponseDivesItem,
    scaleM: Double,
    providerName: (String) -> String,
    locale: Locale,
    imperial: Boolean,
) {
    val context = LocalContext.current
    val day = LogbookText.diveDay(context, dive.startsAt, dive.utcOffsetSeconds, dive.utcOffsetSource, locale)
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp)) {
        Text(
            dive.number?.toString() ?: "–",
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(
                dive.site?.name ?: day,
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Fact(if (dive.site != null) day else stringResource(R.string.logbook_no_site))
                Fact(dive.maxDepthM?.let { LogbookText.depth(context, it, locale, imperial) } ?: "–")
                Fact(LogbookText.duration(context, dive.durationSeconds))
            }
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                dive.gases.forEach { gas -> Fact(mixName(gas.o2, gas.he, stringResource(R.string.dive_air))) }
                if (dive.recordings == 0) {
                    Fact(stringResource(R.string.logbook_no_recording))
                    dive.fromProvider?.let { Fact(stringResource(R.string.logbook_from_provider, providerName(it))) }
                }
                dive.surfaceIntervalSeconds?.takeIf { it < SAME_DAY_SECONDS }?.let {
                    Fact(stringResource(R.string.logbook_surface_interval, LogbookText.duration(context, it)))
                }
                if (dive.findings > 0) {
                    Text(
                        context.resources.getQuantityString(R.plurals.logbook_findings, dive.findings, dive.findings),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            Participants(dive.participants)
        }
        Spacer(Modifier.width(8.dp))
        ProfileSketch(dive.profile, dive.recordings > 0, scaleM)
    }
}

@Composable
private fun Fact(text: String) {
    Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

/**
 * Who was on the Dive: up to three circles with initials, then "+2". The circles are decoration; the names and roles
 * are the row's accessible text (client contract §2), so the circle alone never carries the meaning.
 */
@Composable
private fun Participants(people: List<ApiDivesGet200ResponseDivesItemParticipantsItem>) {
    if (people.isEmpty()) return
    val context = LocalContext.current
    val description = context.getString(
        R.string.logbook_with,
        people.joinToString(", ") { "${it.name} (${LogbookText.role(context, it.role)})" },
    )
    Row(
        Modifier
            .padding(top = 6.dp)
            .semantics(mergeDescendants = true) { contentDescription = description },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        people.take(3).forEach { person -> AvatarCircle(person) }
        val more = people.size - 3
        if (more > 0) {
            Text(
                context.getString(R.string.logbook_more_people, more),
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun AvatarCircle(person: ApiDivesGet200ResponseDivesItemParticipantsItem) {
    val tones = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.surfaceVariant,
        MaterialTheme.colorScheme.errorContainer,
    )
    val tone = tones[(person.diverId.sumOf { it.code }) % tones.size]
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(tone)
            .clearAndSetSemantics {},
        contentAlignment = Alignment.Center,
    ) {
        Text(initials(person.name), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun Pager(state: LogbookUiState, onPage: (Int) -> Unit) {
    val pages = state.pageCount
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(onClick = { onPage(state.page - 1) }, enabled = state.page > 1) {
            Text(stringResource(R.string.logbook_previous))
        }
        Text(
            stringResource(R.string.logbook_page_of, state.page, pages),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        TextButton(onClick = { onPage(state.page + 1) }, enabled = state.page < pages) {
            Text(stringResource(R.string.logbook_next))
        }
    }
}
