package dev.divehub.android.ui.logbook

import android.content.Context
import android.provider.Settings
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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

/** The brief's `Radius.control` for a filled action; a quiet text action stays text. */
private val ControlCorner = 8.dp

/** The dive-number label column: a minimum so titles start on the same line, wide enough for four digits. */
private val NumberColumn = 32.dp

/** The skeleton's bar height, chip and row count, and the pause before it replaces a blank sheet (the brief's 150 ms). */
private val SkeletonBarHeight = 12.dp
private val SkeletonChipHeight = 32.dp
private const val SkeletonRows = 6
private const val SkeletonDelayMs = 150L
private const val SkeletonPulseMs = 1_600
private const val SkeletonPulseAlpha = 0.55f
private const val AnimatorScaleDisabled = 0f

/** The content's arrival over the skeleton (ADR 0064): 150 ms, and instant when animations are off. */
private const val ContentRevealMs = 150

/** Which of the screen's exclusive states is on stage; [Crossfade] fades the content in over the skeleton. */
private enum class LogbookPhase { Loading, Offline, Error, List }

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
                        modifier = Modifier.semantics { heading() },
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
            val phase = when {
                state.loading && state.data == null -> LogbookPhase.Loading
                state.offline && state.data == null -> LogbookPhase.Offline
                state.error != null && state.data == null -> LogbookPhase.Error
                else -> LogbookPhase.List
            }
            Crossfade(
                targetState = phase,
                animationSpec = tween(ContentRevealMs),
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
                        LogbookList(state, units, onRefresh, onRetry, onShowAll, onPage, onToggleFilter, onOpenDive)
                    }
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
    var showBars by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(SkeletonDelayMs)
        showBars = true
    }
    // ADR 0064: the bars pulse gently, and stand still when the system's animations are off.
    val context = LocalContext.current
    val reducedMotion = remember(context) {
        Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) ==
            AnimatorScaleDisabled
    }
    val barAlpha = if (reducedMotion) {
        1f
    } else {
        rememberInfiniteTransition(label = "logbook skeleton").animateFloat(
            initialValue = 1f,
            targetValue = SkeletonPulseAlpha,
            animationSpec = infiniteRepeatable(
                animation = tween(SkeletonPulseMs, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "skeleton bar alpha",
        ).value
    }
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
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.s3),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
        verticalAlignment = Alignment.Top,
    ) {
        SkeletonBar(Modifier.widthIn(min = NumberColumn), alpha = alpha)
        Column(Modifier.weight(1f)) {
            SkeletonBar(Modifier.fillMaxWidth(if (index % 3 == 0) 0.62f else 0.48f), alpha = alpha)
            Spacer(Modifier.height(Spacing.s1))
            SkeletonBar(Modifier.fillMaxWidth(0.34f), alpha = alpha)
        }
        SkeletonBar(Modifier.width(SKETCH_WIDTH.dp), height = SKETCH_HEIGHT.dp, alpha = alpha)
    }
}

@Composable
private fun SkeletonBar(modifier: Modifier, height: Dp = SkeletonBarHeight, alpha: Float = 1f) {
    Box(
        modifier
            .height(height)
            .alpha(alpha)
            .clip(RoundedCornerShape(ControlCorner))
            .background(MaterialTheme.colorScheme.surfaceVariant),
    )
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
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            message = message,
            action = { InkButton(retry, onRetry) },
        )
    }
}

@Composable
private fun LogbookList(
    state: LogbookUiState,
    units: String?,
    onRefresh: () -> Unit,
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
                        // No dead end (the brief's EmptyState): filters go back to all dives, and a genuinely empty
                        // logbook offers the refresh that would pick up a first import.
                        if (filtersEmpty) {
                            InkButton(stringResource(R.string.logbook_refresh), onRefresh)
                        } else {
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
                label = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(LogbookText.filterName(context, chip.filter, connected))
                        Text(
                            LogbookText.number(locale, chip.count),
                            style = DiveHubText.meta,
                            color = if (chip.applied) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                        )
                    }
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
        badge = if (dive.findings > 0) {
            {
                FindingsBadge(
                    context.resources.getQuantityString(
                        R.plurals.logbook_findings,
                        dive.findings,
                        dive.findings,
                    ),
                )
            }
        } else {
            null
        },
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

/**
 * A short state with the facts: findings can be information, a note or a caution (glossary), so the mark is the
 * neutral badge the web uses, never the danger tone. A future severity from the API can tint it.
 */
@Composable
private fun FindingsBadge(text: String) {
    Text(
        text,
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = Spacing.s2),
        style = DiveHubText.metaStrong,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
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
    FlowRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.s4, vertical = Spacing.s4),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2, Alignment.CenterHorizontally),
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

/** The brief's ink primary button: ink in the light, the inverted ink in the dark, with the control radius. */
@Composable
private fun InkButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(ControlCorner),
        colors = ButtonDefaults.buttonColors(
            containerColor = DiveHubColors.ink,
            contentColor = DiveHubColors.onInk,
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
