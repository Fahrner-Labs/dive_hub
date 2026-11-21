package dev.divehub.android.ui.dive

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.horizontalDrag
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.SyncLine
import dev.divehub.android.ui.components.EmptyState
import dev.divehub.android.ui.components.FactRow
import dev.divehub.android.ui.components.InkButton
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.Section
import dev.divehub.android.ui.components.SkeletonBar
import dev.divehub.android.ui.components.rememberReducedMotion
import dev.divehub.android.ui.components.rememberSkeletonAlpha
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewCylindersItem
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemSummaryGasesItem
import dev.divehub.api.models.DiveViewSuit
import dev.divehub.api.models.DiveViewWeightingFeedback
import dev.divehub.api.models.NeighboursView
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.api.models.RecordingSamplesViewSeriesItem
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.dive.DepthReading
import dev.divehub.shared.dive.DiveValue
import dev.divehub.shared.dive.ProfileSummary
import dev.divehub.shared.dive.computerRecordings
import dev.divehub.shared.dive.depthReading
import dev.divehub.shared.dive.depthSeries
import dev.divehub.shared.dive.deviceName
import dev.divehub.shared.dive.markOf
import dev.divehub.shared.dive.nearest
import dev.divehub.shared.dive.nearestIndex
import dev.divehub.shared.dive.partsOf
import dev.divehub.shared.dive.perMinute
import dev.divehub.shared.dive.pressureSeries
import dev.divehub.shared.dive.profileRecording
import dev.divehub.shared.dive.startSetByHand
import dev.divehub.shared.dive.summarize
import dev.divehub.shared.dive.surfaceBands
import dev.divehub.shared.dive.surfaceBreakSeconds
import dev.divehub.shared.dive.surfaceBreakTotalSeconds
import dev.divehub.shared.dive.temperatureSeries
import dev.divehub.shared.dive.timeAtFraction
import dev.divehub.shared.dive.TimeZoneOrigin
import dev.divehub.shared.dive.timeZoneNote
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.mixName
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import java.util.Locale

/** The pager draws the current Dive in the middle, so either adjacent page is one swipe away. (FAH-344) */
private const val CURRENT_DIVE_PAGE = 1
private const val PAGER_PAGES = 3

/** ADR 0064's content arrival: the skeleton gives way to the page over 150 ms, instantly with animations off. */
private const val ContentRevealMs = 150

/** At 150 % and above the top bar stacks its title under the navigation icons and a row stacks its marker. */
private const val LargeTextFontScale = 1.5f

/** The brief's named fixed dimensions, which are not spacing steps. */
private val IconSize = 24.dp
private val TopBarActionsReserve = 96.dp
private val IconStroke = 2.dp
private val ControlCorner = 8.dp
private val ChartHeight = 160.dp
private val ChartStroke = 2.dp
private val ScrubDot = 5.dp

/** The skeleton's pause before it replaces a blank sheet (ADR 0064's 150 ms). */
private const val SkeletonDelayMs = 150L
private const val SkeletonFactRows = 5

/**
 * The dive page route: the page itself holds no navigation state; back returns the logbook as it was.
 */
@Composable
fun DiveDetailRoute(
    viewModel: DiveDetailViewModel,
    units: String?,
    onBack: () -> Unit,
    onOpenDive: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DiveDetailScreen(
        state = state,
        units = units,
        onBack = onBack,
        onRetry = viewModel::retry,
        onOpenDive = onOpenDive,
    )
}

/**
 * One Dive, read-only (client contract, Dives). Everything on it is drawn from [state], so the contract-visible
 * states — an edited value with what the recording says, the time-zone note, a Dive without a Recording, a water
 * mismatch and Surface breaks — have screenshot tests.
 *
 * One sheet under the frame's top bar (design-system-android.md): the facts lead, then titled sections separated by a
 * hairline, with the profile as the one bold element.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiveDetailScreen(
    state: DiveDetailUiState,
    units: String?,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onOpenDive: (String) -> Unit,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val neighbours = state.neighbours
    val inList = neighbours?.position != null
    val loading = state.loading && state.dive == null
    val previous = neighbours?.previous?.id
    val next = neighbours?.next?.id
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DiveTopBar(
                title = state.dive?.let { title(context, it, locale) } ?: "",
                onBack = onBack,
                inList = inList,
                previous = previous,
                next = next,
                loading = loading,
                onOpenDive = onOpenDive,
            )
        },
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding).fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            DivePager(
                state = state,
                units = units,
                neighbours = neighbours,
                onRetry = onRetry,
                onOpenDive = onOpenDive,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

/**
 * The Dive page as a three-page pager: the current Dive in the middle, an empty page on either side for the neighbour
 * a swipe opens. Once a swipe settles, [onOpenDive] moves the page and the pager snaps back to the middle; at either end
 * of the list the spring-back is the end-stop hint. The buttons in the top bar open the same neighbour directly.
 */
@Composable
private fun DivePager(
    state: DiveDetailUiState,
    units: String?,
    neighbours: NeighboursView?,
    onRetry: () -> Unit,
    onOpenDive: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = CURRENT_DIVE_PAGE) { PAGER_PAGES }
    val currentNeighbours by rememberUpdatedState(neighbours)
    val currentOnOpenDive by rememberUpdatedState(onOpenDive)
    LaunchedEffect(pagerState) {
        // Gate on the gesture ending, not the page changing: an interrupted end-stop can settle back on the same
        // page, which emits no settledPage change but must still run the spring-back again (FAH-344 review). The
        // scroll this block starts is filtered upstream, so collectLatest only cancels the block on a new drag.
        snapshotFlow { pagerState.isScrollInProgress }
            .filter { !it }
            .collectLatest {
                val page = pagerState.currentPage
                if (page == CURRENT_DIVE_PAGE) return@collectLatest
                val target = if (page < CURRENT_DIVE_PAGE) {
                    currentNeighbours?.previous?.id
                } else {
                    currentNeighbours?.next?.id
                }
                if (target != null) {
                    currentOnOpenDive(target)
                    pagerState.scrollToPage(CURRENT_DIVE_PAGE)
                } else {
                    // The ends of the list: the spring-back says there is nothing further (FAH-344).
                    pagerState.animateScrollToPage(CURRENT_DIVE_PAGE)
                }
            }
    }
    HorizontalPager(
        state = pagerState,
        modifier = modifier.testTag("dive_pager"),
    ) { page ->
        if (page == CURRENT_DIVE_PAGE) {
            DiveContent(state, units, onRetry, Modifier.fillMaxSize())
        } else {
            Box(Modifier.fillMaxSize())
        }
    }
}

/** Which of the screen's exclusive states is on stage; [Crossfade] fades the page in over the skeleton. */
private enum class DivePhase { Loading, Offline, Error, Dive }

@Composable
private fun DiveContent(
    state: DiveDetailUiState,
    units: String?,
    onRetry: () -> Unit,
    modifier: Modifier,
) {
    val phase = when {
        state.loading && state.dive == null -> DivePhase.Loading
        state.offline && state.dive == null -> DivePhase.Offline
        state.error != null && state.dive == null -> DivePhase.Error
        state.dive != null -> DivePhase.Dive
        else -> DivePhase.Loading
    }
    Crossfade(
        targetState = phase,
        modifier = modifier,
        animationSpec = tween(if (rememberReducedMotion()) 0 else ContentRevealMs),
        label = "dive content",
    ) { shown ->
        when (shown) {
            DivePhase.Loading -> Loading(Modifier.fillMaxSize())
            DivePhase.Offline -> NoContent(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(R.string.dive_offline_title),
                message = stringResource(R.string.dive_offline),
                retry = stringResource(R.string.dive_retry),
                onRetry = onRetry,
            )
            DivePhase.Error -> NoContent(
                modifier = Modifier.fillMaxSize(),
                title = if (isNotFound(state.error)) {
                    stringResource(R.string.dive_not_found_title)
                } else {
                    stringResource(R.string.dive_error_title)
                },
                message = errorText(LocalContext.current, state.error),
                retry = stringResource(R.string.dive_retry),
                onRetry = onRetry,
            )
            DivePhase.Dive -> state.dive?.let { DiveBody(state, it, units, onRetry) }
        }
    }
}

private fun title(context: Context, dive: DiveView, locale: Locale): String = dive.values.number?.let {
    context.getString(R.string.dive_title_number, it)
} ?: DiveDetailText.start(context, dive.values.startsAt.at, dive.values.startsAt.utcOffsetSeconds, locale)

/**
 * The Dive page's top bar: the title centred between the back arrow and the neighbour chevrons (ADR 0018, 2026-11-20
 * amendment). At 150 % and above the title stacks under the icons, because "Tauchgang 42" at 200 % no longer fits
 * between them; the page title grows instead of breaking inside a word.
 */
@Composable
private fun DiveTopBar(
    title: String,
    onBack: () -> Unit,
    inList: Boolean,
    previous: String?,
    next: String?,
    loading: Boolean,
    onOpenDive: (String) -> Unit,
) {
    val titleModifier = Modifier.semantics { heading() }
    val neighbours: @Composable () -> Unit = {
        // A Dive outside the list the page was opened from has no neighbours to name (ADR 0042).
        if (inList) {
            NeighbourAction(
                direction = Direction.Left,
                target = previous,
                label = stringResource(R.string.dive_previous),
                unavailable = stringResource(R.string.dive_previous_unavailable),
                onOpenDive = onOpenDive,
            )
            NeighbourAction(
                direction = Direction.Right,
                target = next,
                label = stringResource(R.string.dive_next),
                unavailable = stringResource(R.string.dive_next_unavailable),
                onOpenDive = onOpenDive,
            )
        }
    }
    DiveHubTopBar(
        title = title,
        onBack = onBack,
        centerTitle = true,
        stackedTitle = true,
        loading = loading,
        titleModifier = titleModifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        // The data still arriving: reserve the two chevrons' width so the title does not jump when they appear.
        actions = { if (loading) Spacer(Modifier.width(TopBarActionsReserve)) else neighbours() },
    )
}

private fun isNotFound(code: String?): Boolean = code == "dive_not_found" || code == "not_found"

private fun errorText(context: Context, code: String?): String = when {
    isNotFound(code) -> context.getString(R.string.dive_error_not_found)
    code == ErrorCodes.SIGN_IN_REQUIRED -> context.getString(R.string.error_sign_in_required)
    code == ErrorCodes.SERVER_ERROR -> context.getString(R.string.error_server)
    else -> context.getString(R.string.error_unknown)
}

/** The page's settling shape in flat bars: the facts lead, then a section and its rows. */
@Composable
private fun Loading(modifier: Modifier) {
    val label = stringResource(R.string.loading)
    var showBars by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(SkeletonDelayMs)
        showBars = true
    }
    val alpha = rememberSkeletonAlpha("dive skeleton")
    // The bars take the final text's line boxes, so the skeleton is the page's layout and never reflows on arrival.
    val sectionHeight = with(LocalDensity.current) { DiveHubText.section.lineHeight.toDp() }
    val labelHeight = with(LocalDensity.current) { DiveHubText.label.lineHeight.toDp() }
    val bodyHeight = with(LocalDensity.current) { DiveHubText.body.lineHeight.toDp() }
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.s4)
            .semantics {
                contentDescription = label
                progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
            },
    ) {
        Spacer(Modifier.height(Spacing.s4))
        if (showBars) {
            SkeletonBar(Modifier.fillMaxWidth(0.3f), height = sectionHeight, alpha = alpha)
            Spacer(Modifier.height(Spacing.s5))
            repeat(SkeletonFactRows) { SkeletonFactRow(alpha, labelHeight, bodyHeight) }
            Spacer(Modifier.height(Spacing.s5))
            SkeletonBar(Modifier.fillMaxWidth(0.45f), height = sectionHeight, alpha = alpha)
            Spacer(Modifier.height(Spacing.s2))
            repeat(2) { SkeletonFactRow(alpha, labelHeight, bodyHeight) }
        }
    }
}

@Composable
private fun SkeletonFactRow(alpha: Float, labelHeight: Dp, bodyHeight: Dp) {
    if (LocalDensity.current.fontScale >= LargeTextFontScale) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.s3),
        ) {
            SkeletonBar(Modifier.fillMaxWidth(0.35f), height = labelHeight, alpha = alpha)
            Spacer(Modifier.height(Spacing.s1))
            SkeletonBar(Modifier.fillMaxWidth(0.55f), height = bodyHeight, alpha = alpha)
        }
    } else {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.s3),
            horizontalArrangement = Arrangement.spacedBy(Spacing.s4),
        ) {
            SkeletonBar(Modifier.weight(0.4f), height = bodyHeight, alpha = alpha)
            SkeletonBar(Modifier.weight(0.6f), height = bodyHeight, alpha = alpha)
        }
    }
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

@Composable
private fun DiveBody(
    state: DiveDetailUiState,
    dive: DiveView,
    units: String?,
    onRetry: () -> Unit,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = Formats.imperial(units, locale)
    val primary = profileRecording(dive.recordings)

    LazyColumn(Modifier.fillMaxSize().testTag("dive_scroll")) {
        item { SyncLine(state.syncedAt, state.connected, state.offline, R.string.dive_offline_cached) }
        item { Facts(dive, locale, imperial) }
        item { Cylinders(dive, locale, imperial) }
        item { Weighting(dive, locale, imperial) }
        item { Notes(dive.notes) }
        item { Recordings(dive, locale, state.providers) }
        if (primary != null) {
            item { Profile(state, primary, locale, imperial, onRetry) }
        }
        if (dive.participants.isNotEmpty()) {
            item { Participants(dive) }
        }
        dive.position?.let { position ->
            item { Position(position.latitude, position.longitude, locale) }
        }
    }
}

/** A neighbour in the top bar: enabled by its id, so at the ends of the list the greying is the end-stop (FAH-344). */
@Composable
private fun NeighbourAction(
    direction: Direction,
    target: String?,
    label: String,
    unavailable: String,
    onOpenDive: (String) -> Unit,
) {
    IconButton(
        onClick = { target?.let(onOpenDive) },
        enabled = target != null,
        modifier = Modifier.semantics { contentDescription = if (target == null) unavailable else label },
    ) {
        DirectionIcon(direction)
    }
}

private enum class Direction { Back, Left, Right }

/** The header's arrows, drawn here so the app needs no separate icon dependency. */
@Composable
private fun DirectionIcon(direction: Direction) {
    val color = LocalContentColor.current
    Canvas(Modifier.size(IconSize)) {
        val stroke = Stroke(width = IconStroke.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        val width = size.width
        val height = size.height
        val path = Path().apply {
            when (direction) {
                Direction.Back -> {
                    moveTo(width * 0.72f, height * 0.5f)
                    lineTo(width * 0.28f, height * 0.5f)
                    moveTo(width * 0.46f, height * 0.28f)
                    lineTo(width * 0.28f, height * 0.5f)
                    lineTo(width * 0.46f, height * 0.72f)
                }
                Direction.Left -> {
                    moveTo(width * 0.62f, height * 0.25f)
                    lineTo(width * 0.38f, height * 0.5f)
                    lineTo(width * 0.62f, height * 0.75f)
                }
                Direction.Right -> {
                    moveTo(width * 0.38f, height * 0.25f)
                    lineTo(width * 0.62f, height * 0.5f)
                    lineTo(width * 0.38f, height * 0.75f)
                }
            }
        }
        drawPath(path, color, style = stroke)
    }
}

/**
 * What the Dive was, at a glance: the page's lead, before the first section's hairline. Every value is a [FactRow],
 * so figures line up down the column and an edited value keeps its mark and note under it.
 */
@Composable
private fun Facts(dive: DiveView, locale: Locale, imperial: Boolean) {
    val context = LocalContext.current
    val values = dive.values
    Column(Modifier.fillMaxWidth().padding(horizontal = Spacing.s4)) {
        FactRow(
            label = stringResource(R.string.dive_number),
            value = values.number?.let { Formats.number(locale, it) } ?: "–",
        )
        FactRow(
            label = stringResource(R.string.dive_start),
            value = DiveDetailText.start(context, values.startsAt.at, values.startsAt.utcOffsetSeconds, locale),
            mark = DiveDetailText.editedValue(context, dive.markOf(DiveValue.STARTS_AT), DiveValue.STARTS_AT, locale, imperial),
            notes = listOfNotNull(
                timeZoneNote(dive.utcOffsetSource, dive.startSetByHand)?.let { timeZoneText(context, it) }
                    ?.takeIf { it.isNotEmpty() },
            ),
        )
        val surface = profileRecording(dive.recordings)?.let(::surfaceBreakTotalSeconds) ?: 0.0
        FactRow(
            label = stringResource(R.string.dive_duration),
            value = DiveDetailText.duration(context, values.durationSeconds) +
                if (surface > 0) " · " + context.getString(R.string.dive_at_surface, DiveDetailText.duration(context, surface)) else "",
            mark = DiveDetailText.editedValue(
                context,
                dive.markOf(DiveValue.DURATION_SECONDS),
                DiveValue.DURATION_SECONDS,
                locale,
                imperial,
            ),
        )
        FactRow(
            label = stringResource(R.string.dive_max_depth),
            value = values.maxDepthM?.let { DiveDetailText.depth(context, it, locale, imperial) } ?: "–",
            mark = DiveDetailText.editedValue(context, dive.markOf(DiveValue.MAX_DEPTH_M), DiveValue.MAX_DEPTH_M, locale, imperial),
        )
        FactRow(
            label = stringResource(R.string.dive_avg_depth),
            value = values.avgDepthM?.let { DiveDetailText.depth(context, it, locale, imperial) } ?: "–",
            mark = DiveDetailText.editedValue(context, dive.markOf(DiveValue.AVG_DEPTH_M), DiveValue.AVG_DEPTH_M, locale, imperial),
        )
        FactRow(
            label = stringResource(R.string.dive_water_temperature),
            value = values.waterTemperatureC?.let { DiveDetailText.temperature(context, it, locale, imperial) } ?: "–",
            mark = DiveDetailText.editedValue(
                context,
                dive.markOf(DiveValue.WATER_TEMPERATURE_C),
                DiveValue.WATER_TEMPERATURE_C,
                locale,
                imperial,
            ),
        )
        Water(dive, locale)
    }
}

@Composable
private fun Water(dive: DiveView, locale: Locale) {
    val context = LocalContext.current
    val mismatch = dive.waterMismatch
    val notes = if (mismatch != null) {
        listOf(
            context.getString(
                R.string.dive_water_mismatch,
                DiveDetailText.waterInSentence(context, mismatch.computer),
                DiveDetailText.waterInSentence(context, mismatch.site),
            ),
            DiveDetailText.depthReadingText(context, depthReading(mismatch.depthPercent), locale),
        )
    } else {
        listOf(
            when {
                dive.site == null -> context.getString(R.string.dive_choose_site_for_water)
                dive.waterType == null -> context.getString(R.string.dive_water_unknown)
                else -> context.getString(R.string.dive_water_from_site)
            },
        )
    }
    FactRow(
        label = stringResource(R.string.dive_water_type),
        value = dive.waterType?.let { DiveDetailText.waterName(context, it) } ?: stringResource(R.string.dive_no_water_type),
        notes = notes,
    )
}

/** The Dive's Cylinders in their order with the SAC the server computed (ADR 0045), or why it has none. */
@Composable
private fun Cylinders(dive: DiveView, locale: Locale, imperial: Boolean) {
    if (dive.cylinders.isEmpty() && dive.sac == null && dive.sacMissing == null) return
    val context = LocalContext.current
    Section(
        stringResource(R.string.dive_cylinders),
        Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
    ) {
        dive.cylinders.forEachIndexed { index, cylinder ->
            FactRow(
                label = stringResource(R.string.dive_cylinder_n, index + 1),
                value = cylinderLine(context, cylinder, locale, imperial),
                notes = buildList {
                    add(cylinderPressures(context, cylinder, locale, imperial))
                    if (cylinder.fromPod) add(stringResource(R.string.dive_cylinder_from_pod))
                },
            )
        }
        val sac = dive.sac
        val sacMissing = dive.sacMissing
        when {
            sac != null -> FactRow(
                label = stringResource(R.string.dive_sac),
                value = stringResource(
                    R.string.dive_sac_litres_value,
                    Formats.decimal(locale, sac.litresPerMinute, 1),
                ),
                notes = listOfNotNull(
                    sac.barPerMinute?.let {
                        stringResource(
                            R.string.dive_sac_bar_value,
                            Formats.pressureRate(context, it, locale, imperial),
                        )
                    },
                ),
            )
            sacMissing != null -> FactRow(
                label = stringResource(R.string.dive_sac),
                value = sacMissingText(context, sacMissing),
            )
        }
    }
}

private fun cylinderLine(
    context: Context,
    cylinder: DiveViewCylindersItem,
    locale: Locale,
    imperial: Boolean,
): String = buildList {
    cylinder.volumeL?.let { add(DiveDetailText.volume(context, it, locale)) }
    cylinder.material?.let { add(DiveDetailText.material(context, it)) }
    cylinder.workingPressureBar?.let {
        add(context.getString(R.string.dive_cylinder_working_pressure, Formats.pressure(context, it, locale, imperial)))
    }
    add(DiveDetailText.gas(context, cylinder.gas?.o2, cylinder.gas?.he))
}.joinToString(" · ")

private fun cylinderPressures(
    context: Context,
    cylinder: DiveViewCylindersItem,
    locale: Locale,
    imperial: Boolean,
): String {
    val start = cylinder.startPressureBar
    val end = cylinder.endPressureBar
    if (start == null && end == null) return context.getString(R.string.dive_cylinder_pressures_unknown)
    val startText = start?.let { Formats.pressure(context, it, locale, imperial) } ?: "–"
    val endText = end?.let { Formats.pressure(context, it, locale, imperial) } ?: "–"
    if (start != null && end != null && start > end) {
        val used = Formats.pressure(context, start - end, locale, imperial)
        return context.getString(R.string.dive_cylinder_pressures_used, startText, endText, used)
    }
    return context.getString(R.string.dive_cylinder_pressures, startText, endText)
}

private fun sacMissingText(context: Context, missing: String): String = when (missing) {
    "too_short" -> context.getString(R.string.dive_sac_missing_too_short)
    "no_average_depth" -> context.getString(R.string.dive_sac_missing_no_average_depth)
    "cylinder_incomplete" -> context.getString(R.string.dive_sac_missing_cylinder_incomplete)
    else -> context.getString(R.string.dive_sac_missing)
}

/** The suit, the Lead entries and how the weighting felt (ADR 0031); nothing when the Dive has none of them. */
@Composable
private fun Weighting(dive: DiveView, locale: Locale, imperial: Boolean) {
    val suit = dive.suit
    val lead = dive.lead
    val feedback = dive.weightingFeedback
    if (suit == null && lead.isEmpty() && feedback == null) return
    val context = LocalContext.current
    Section(
        stringResource(R.string.dive_weighting),
        Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
    ) {
        suit?.let {
            FactRow(label = stringResource(R.string.dive_suit), value = suitText(context, locale, it))
        }
        FactRow(label = stringResource(R.string.dive_lead), value = leadText(context, dive, locale, imperial))
        feedback?.let {
            FactRow(label = stringResource(R.string.dive_feedback), value = feedbackText(context, it, locale, imperial))
        }
    }
}

private fun suitText(context: Context, locale: Locale, suit: DiveViewSuit): String =
    DiveDetailText.suit(context, locale, suit.type, suit.thicknessMm, (suit.hood as? JsonPrimitive)?.booleanOrNull, suit.undergarment)

private fun leadText(context: Context, dive: DiveView, locale: Locale, imperial: Boolean): String {
    if (dive.lead.isEmpty()) return context.getString(R.string.dive_lead_unknown)
    if (dive.leadTotalKg == 0.0) return context.getString(R.string.dive_lead_none)
    val entries = dive.lead.joinToString("\n") { entry ->
        val amount = DiveDetailText.mass(context, entry.amountKg, locale, imperial)
        entry.placement?.let { "$amount ${DiveDetailText.placement(context, it)}" } ?: amount
    }
    val total = dive.leadTotalKg?.let {
        "\n" + context.getString(R.string.dive_lead_total, DiveDetailText.mass(context, it, locale, imperial))
    }.orEmpty()
    return entries + total
}

private fun feedbackText(
    context: Context,
    feedback: DiveViewWeightingFeedback,
    locale: Locale,
    imperial: Boolean,
): String = when (feedback.felt) {
    "right" -> context.getString(R.string.dive_feedback_right)
    "too_heavy" -> feedback.amountKg?.let {
        context.getString(R.string.dive_feedback_too_heavy, DiveDetailText.mass(context, it, locale, imperial))
    } ?: context.getString(R.string.dive_feedback_too_heavy_unknown)
    "too_light" -> feedback.amountKg?.let {
        context.getString(R.string.dive_feedback_too_light, DiveDetailText.mass(context, it, locale, imperial))
    } ?: context.getString(R.string.dive_feedback_too_light_unknown)
    else -> feedback.felt
}

@Composable
private fun Notes(notes: String?) {
    if (notes.isNullOrBlank()) return
    Section(
        stringResource(R.string.dive_notes),
        Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
    ) {
        Text(notes, style = DiveHubText.body)
    }
}

@Composable
private fun timeZoneText(context: Context, origin: TimeZoneOrigin): String = when (origin) {
    TimeZoneOrigin.POSITION -> context.getString(R.string.dive_time_zone_position)
    TimeZoneOrigin.NEARBY -> context.getString(R.string.dive_time_zone_nearby)
    TimeZoneOrigin.UNKNOWN -> context.getString(R.string.dive_time_zone_unknown)
    TimeZoneOrigin.DEVICE -> ""
}

/** The Recordings by Device, the Primary marked and a joined Recording's parts listed under it (ADR 0016, ADR 0063). */
@Composable
private fun Recordings(dive: DiveView, locale: Locale, providers: ProviderDirectory) {
    val context = LocalContext.current
    Section(
        stringResource(R.string.dive_recordings),
        Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
    ) {
        val computers = computerRecordings(dive.recordings)
        if (computers.isEmpty()) {
            Text(
                dive.fromProvider?.let { provider ->
                    context.getString(R.string.dive_no_recording_from, providers.nameOf(provider))
                } ?: context.getString(R.string.dive_no_recording),
                style = DiveHubText.body,
            )
            Text(
                stringResource(R.string.dive_no_recording_next),
                modifier = Modifier.padding(top = Spacing.s1),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            return@Section
        }
        computers.forEachIndexed { index, recording ->
            if (index > 0) {
                Spacer(Modifier.height(Spacing.s3))
                HorizontalDivider(color = DiveHubColors.border)
                Spacer(Modifier.height(Spacing.s3))
            }
            val name = recordingName(context, dive, recording)
            if (LocalDensity.current.fontScale >= LargeTextFontScale) {
                // The marker goes under the name, so a long device name keeps the whole width at 200 %.
                Column {
                    Text(name, style = DiveHubText.bodyStrong)
                    if (recording.isPrimary) {
                        Text(
                            stringResource(R.string.dive_primary),
                            modifier = Modifier.padding(top = Spacing.s1),
                            style = DiveHubText.metaStrong,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            } else {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(name, modifier = Modifier.weight(1f), style = DiveHubText.bodyStrong)
                    if (recording.isPrimary) {
                        Text(
                            stringResource(R.string.dive_primary),
                            style = DiveHubText.metaStrong,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
            recording.summary.waterType?.let {
                Text(
                    stringResource(R.string.dive_computer_water) + ": " + DiveDetailText.waterName(context, it),
                    modifier = Modifier.padding(top = Spacing.s1),
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            val parts = partsOf(recording, dive.recordings)
            if (parts.isNotEmpty()) {
                Text(
                    stringResource(R.string.dive_parts),
                    modifier = Modifier.padding(top = Spacing.s3),
                    style = DiveHubText.label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                parts.forEach { part ->
                    Text(
                        context.getString(
                            R.string.dive_recording_line,
                            recordingName(context, dive, part),
                            DiveDetailText.start(context, part.startsAt, dive.values.startsAt.utcOffsetSeconds, locale),
                            DiveDetailText.duration(context, part.durationSeconds),
                        ),
                        style = DiveHubText.meta,
                    )
                }
            }
        }
    }
}

private fun recordingName(context: Context, dive: DiveView, recording: DiveViewRecordingsItem): String =
    recording.device?.let { "${deviceName(it.manufacturer, it.product)} (${it.serialNumber})" }
        ?: context.getString(R.string.dive_recording_n, dive.recordings.indexOf(recording) + 1)

/** The depth profile with its text alternative: the summary and the samples per minute as a table (WCAG 1.1.1). */
@Composable
private fun Profile(
    state: DiveDetailUiState,
    recording: DiveViewRecordingsItem,
    locale: Locale,
    imperial: Boolean,
    onRetry: () -> Unit,
) {
    val context = LocalContext.current
    Section(
        stringResource(R.string.dive_profile),
        Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
    ) {
        val samples = state.samples
        val depth = samples?.let(::depthSeries)
        val summary = if (samples != null && depth != null) summarize(depth, temperatureSeries(samples)) else null
        if (depth == null || summary == null) {
            if (state.samplesPending) {
                Text(
                    stringResource(R.string.dive_profile_pending),
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                return@Section
            }
            val failure = when {
                state.samplesError != null -> errorText(context, state.samplesError)
                samples != null -> context.getString(R.string.dive_profile_failed)
                else -> return@Section
            }
            Text(failure, style = DiveHubText.meta, color = MaterialTheme.colorScheme.error)
            QuietButton(
                stringResource(R.string.dive_retry),
                onClick = onRetry,
                modifier = Modifier.padding(top = Spacing.s1),
            )
            return@Section
        }
        val summaryText = profileSummaryText(context, summary, locale, imperial)
        val temperature = temperatureSeries(samples)
        val pressures = pressureSeries(samples)
        val podGases = recording.summary.gases.orEmpty().filter { it.startPressureBar != null || it.endPressureBar != null }
        val pressureTanks = pressures.mapIndexed { index, series ->
            tankLabel(pressures.size, index, podGases.getOrNull(index), locale) to series
        }
        var scrubFraction by remember(depth) { mutableStateOf<Float?>(null) }
        val spanMs = depth.offsetsMs.lastOrNull() ?: 0
        val scrubIndex = scrubFraction?.let { nearestIndex(depth, timeAtFraction(it.toDouble(), spanMs)) }
        val scrubOffsetMs = scrubIndex?.let { depth.offsetsMs[it] }
        val readingMs = scrubOffsetMs ?: depth.offsetsMs.firstOrNull() ?: 0
        ScrubReadout(
            offsetMs = readingMs,
            depthM = nearest(depth, readingMs),
            temperatureC = nearest(temperature, readingMs),
            tanks = pressureTanks,
            locale = locale,
            imperial = imperial,
        )
        ProfileChart(
            depth = depth,
            bands = surfaceBands(recording.summary.surfaceBreaks.orEmpty(), summary.durationSeconds),
            description = summaryText,
            scrubMs = scrubOffsetMs,
            onScrub = { scrubFraction = it },
        )
        Text(
            summaryText,
            modifier = Modifier.padding(top = Spacing.s2),
            style = DiveHubText.body,
        )
        surfaceBreakSeconds(recording).forEach { seconds ->
            Text(
                context.getString(R.string.dive_surface_break, DiveDetailText.duration(context, seconds)),
                modifier = Modifier.padding(top = Spacing.s1),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        ProfileTable(depth, temperature, pressureTanks, locale, imperial)
    }
}

/** What the touched moment of the profile reads, announced as a live region for TalkBack (FAH-337). */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ScrubReadout(
    offsetMs: Int,
    depthM: Double?,
    temperatureC: Double?,
    tanks: List<Pair<String, RecordingSamplesViewSeriesItem>>,
    locale: Locale,
    imperial: Boolean,
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = Spacing.s1)
            .semantics(mergeDescendants = true) { liveRegion = LiveRegionMode.Polite },
    ) {
        FlowRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
            verticalArrangement = Arrangement.spacedBy(Spacing.s2),
        ) {
            Readout(
                label = stringResource(R.string.dive_time),
                value = DiveDetailText.elapsed(offsetMs / 1000.0, locale),
            )
            Readout(
                label = stringResource(R.string.dive_depth),
                value = depthM?.let { DiveDetailText.depth(context, it, locale, imperial) } ?: "–",
            )
            Readout(
                label = stringResource(R.string.dive_temperature),
                value = temperatureC?.let { DiveDetailText.temperature(context, it, locale, imperial) } ?: "–",
            )
            tanks.forEach { (label, series) ->
                Readout(
                    label = label,
                    value = nearest(series, offsetMs)?.let { DiveDetailText.pressure(context, it, locale, imperial) } ?: "–",
                )
            }
        }
    }
}

/** A tank by the gas and size it holds, else by its pod or number; several also by their number (ADR 0045). */
@Composable
private fun tankLabel(
    count: Int,
    index: Int,
    gas: DiveViewRecordingsItemSummaryGasesItem?,
    locale: Locale,
): String {
    if (gas != null) {
        val context = LocalContext.current
        val mix = mixName(gas.o2, gas.he, stringResource(R.string.dive_air))
        val volume = gas.tankVolumeL?.let { DiveDetailText.litres(context, it, locale) }
        val what = if (volume == null) mix else stringResource(R.string.dive_tank_gas_size, mix, volume)
        return if (count == 1) what else stringResource(R.string.dive_tank_numbered_gas, index + 1, what)
    }
    return if (count == 1) stringResource(R.string.dive_tank_pressure) else stringResource(R.string.dive_tank_numbered, index + 1)
}

@Composable
private fun Readout(label: String, value: String) {
    Column {
        Text(label, style = DiveHubText.meta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = DiveHubText.bodyStrong)
    }
}

private fun profileSummaryText(context: Context, summary: ProfileSummary, locale: Locale, imperial: Boolean): String {
    val head = context.getString(
        R.string.dive_profile_summary,
        DiveDetailText.depth(context, summary.maxDepthM, locale, imperial),
        DiveDetailText.duration(context, summary.maxDepthAtSeconds),
        DiveDetailText.duration(context, summary.durationSeconds),
    )
    val min = summary.minTemperatureC ?: return head
    val max = summary.maxTemperatureC ?: return head
    val range = if (min == max) {
        DiveDetailText.temperature(context, min, locale, imperial)
    } else {
        context.getString(
            R.string.dive_profile_temperature_range,
            DiveDetailText.temperature(context, min, locale, imperial),
            DiveDetailText.temperature(context, max, locale, imperial),
        )
    }
    return head + " " + context.getString(R.string.dive_profile_temperature, range)
}

@Composable
private fun ProfileChart(
    depth: RecordingSamplesViewSeriesItem,
    bands: List<dev.divehub.shared.dive.SurfaceBand>,
    description: String,
    scrubMs: Int?,
    onScrub: (Float) -> Unit,
) {
    // The profile is the one place the depth ramp is drawn (design-system-android.md): shallow at the surface,
    // deep at the bottom, both the web's data tokens. In the dark the deep end is 1.73:1 against the chart, so the
    // outline and the scrub dot take the shallow end (7.5:1) while the area keeps the ramp (FAH-366 critique).
    val dark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val line = if (dark) DiveHubColors.depthShallow else DiveHubColors.depthDeep
    val area = Brush.verticalGradient(listOf(DiveHubColors.depthShallow, DiveHubColors.depthDeep))
    val surface = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.32f)
    val scrub = MaterialTheme.colorScheme.onSurface
    val span = (depth.offsetsMs.lastOrNull() ?: 0).toFloat().takeIf { it > 0f } ?: 1f
    val deepest = depth.values.maxOrNull()?.toFloat()?.takeIf { it > 0f } ?: 1f
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(ChartHeight)
            .clip(RoundedCornerShape(ControlCorner))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .testTag("dive_profile_chart")
            .pointerInput(depth) {
                detectTapGestures { offset ->
                    onScrub((offset.x / size.width).coerceIn(0f, 1f))
                }
            }
            .pointerInput(depth) {
                // Only a horizontal drag scrubs, so a vertical swipe still scrolls the page.
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    fun at(x: Float) {
                        onScrub((x / size.width).coerceIn(0f, 1f))
                    }
                    val drag = awaitHorizontalTouchSlopOrCancellation(down.id) { change, _ ->
                        at(change.position.x)
                        change.consume()
                    } ?: return@awaitEachGesture
                    at(drag.position.x)
                    horizontalDrag(drag.id) { change ->
                        at(change.position.x)
                        change.consume()
                    }
                }
            }
            .semantics {
                contentDescription = description
                // TalkBack moves the scrub with the adjustable action and hears the readout's live region (FAH-337).
                progressBarRangeInfo = ProgressBarRangeInfo(((scrubMs ?: 0) / span).coerceIn(0f, 1f), 0f..1f)
                setProgress { fraction ->
                    onScrub(fraction.coerceIn(0f, 1f))
                    true
                }
            },
    ) {
        val width = size.width
        val height = size.height
        val points = depth.offsetsMs.mapIndexed { index, offset ->
            Offset(
                x = (offset / span).coerceIn(0f, 1f) * width,
                // Deeper is lower on the chart, so the water's ramp runs shallow to deep down the page.
                y = ((depth.values.getOrNull(index) ?: 0.0).toFloat() / deepest).coerceIn(0f, 1f) * (height - 2f),
            )
        }
        if (points.size < 2) return@Canvas
        val outline = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.drop(1).forEach { lineTo(it.x, it.y) }
        }
        val filled = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.drop(1).forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, height)
            lineTo(points.first().x, height)
            close()
        }
        drawPath(filled, area)
        // The band at the surface, under the dive's line so the profile stays readable (ADR 0063).
        bands.forEach { band ->
            drawRect(
                color = surface,
                topLeft = Offset(band.fromFraction.toFloat() * width, 0f),
                size = Size((band.toFraction - band.fromFraction).toFloat() * width, height),
            )
        }
        drawPath(outline, line, style = Stroke(width = ChartStroke.toPx()))
        // The scrub line goes over the surface bands and the profile, on the sample the readout names (FAH-337).
        if (scrubMs != null) {
            val x = (scrubMs / span).coerceIn(0f, 1f) * width
            drawLine(scrub, Offset(x, 0f), Offset(x, height), strokeWidth = ChartStroke.toPx())
            val index = nearestIndex(depth, scrubMs)
            if (index != null) {
                val y = ((depth.values.getOrNull(index) ?: 0.0).toFloat() / deepest).coerceIn(0f, 1f) * (height - 2f)
                drawCircle(line, radius = ScrubDot.toPx(), center = Offset(x, y))
            }
        }
    }
}

@Composable
private fun ProfileTable(
    depth: RecordingSamplesViewSeriesItem,
    temperature: RecordingSamplesViewSeriesItem?,
    tanks: List<Pair<String, RecordingSamplesViewSeriesItem>>,
    locale: Locale,
    imperial: Boolean,
) {
    val context = LocalContext.current
    var open by remember { mutableStateOf(false) }
    QuietButton(
        stringResource(R.string.dive_profile_table),
        onClick = { open = !open },
        expanded = open,
    )
    if (!open) return
    val rows = perMinute(depth, temperature)
    Column(Modifier.fillMaxWidth().testTag("dive_profile_table")) {
        // Four columns at 320 dp and 200 % squeeze a unit onto its own line and run neighbours together, so the
        // text alternative becomes one readable fact line per minute instead (FAH-366 critique; the brief's
        // "a table becomes fact rows at phone width").
        if (LocalDensity.current.fontScale >= LargeTextFontScale) {
            val temperatureLabel = stringResource(R.string.dive_temperature)
            rows.forEach { row ->
                FactRow(
                    label = DiveDetailText.duration(context, row.minute * 60.0),
                    value = DiveDetailText.depth(context, row.depthM, locale, imperial),
                    notes = buildList {
                        add(
                            temperatureLabel + ": " +
                                (row.temperatureC?.let { DiveDetailText.temperature(context, it, locale, imperial) } ?: "–"),
                        )
                        tanks.forEach { (label, series) ->
                            val pressure = nearest(series, row.minute * 60_000)
                                ?.let { DiveDetailText.pressure(context, it, locale, imperial) } ?: "–"
                            add("$label: $pressure")
                        }
                    },
                )
            }
            return
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = Spacing.s2, bottom = Spacing.s1),
        ) {
            TableHead(stringResource(R.string.dive_time), Modifier.weight(1f))
            TableHead(stringResource(R.string.dive_depth), Modifier.weight(1f))
            TableHead(stringResource(R.string.dive_temperature), Modifier.weight(1f))
            tanks.forEach { (label, _) -> TableHead(label, Modifier.weight(1f)) }
        }
        HorizontalDivider(color = DiveHubColors.border)
        rows.forEach { row ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.s1),
            ) {
                TableCell(DiveDetailText.duration(context, row.minute * 60.0), Modifier.weight(1f))
                TableCell(DiveDetailText.depth(context, row.depthM, locale, imperial), Modifier.weight(1f))
                TableCell(
                    row.temperatureC?.let { DiveDetailText.temperature(context, it, locale, imperial) } ?: "–",
                    Modifier.weight(1f),
                )
                tanks.forEach { (_, series) ->
                    TableCell(
                        nearest(series, row.minute * 60_000)?.let { DiveDetailText.pressure(context, it, locale, imperial) } ?: "–",
                        Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun TableHead(text: String, modifier: Modifier) {
    Text(text, modifier = modifier, style = DiveHubText.metaStrong, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun TableCell(text: String, modifier: Modifier) {
    Text(text, modifier = modifier, style = DiveHubText.meta)
}

@Composable
private fun Participants(dive: DiveView) {
    val context = LocalContext.current
    Section(
        stringResource(R.string.dive_participants),
        Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
    ) {
        dive.participants.forEach { person ->
            Text(
                context.getString(R.string.dive_participant, person.name, DiveDetailText.role(context, person.role)),
                style = DiveHubText.body,
            )
        }
    }
}

/** A Dive's position is private: only what the API sends is shown, and nothing is sent anywhere by itself. */
@Composable
private fun Position(latitude: Double, longitude: Double, locale: Locale) {
    val context = LocalContext.current
    Section(
        stringResource(R.string.dive_position),
        Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
    ) {
        Text(DiveDetailText.position(context, latitude, longitude, locale), style = DiveHubText.body)
    }
}
