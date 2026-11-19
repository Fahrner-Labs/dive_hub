package dev.divehub.android.ui.dive

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.SyncLine
import dev.divehub.android.ui.Formats
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewRecordingsItem
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
import dev.divehub.shared.dive.partsOf
import dev.divehub.shared.dive.perMinute
import dev.divehub.shared.dive.profileRecording
import dev.divehub.shared.dive.startSetByHand
import dev.divehub.shared.dive.summarize
import dev.divehub.shared.dive.surfaceBands
import dev.divehub.shared.dive.surfaceBreakSeconds
import dev.divehub.shared.dive.surfaceBreakTotalSeconds
import dev.divehub.shared.dive.temperatureSeries
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.dive.timeZoneNote
import dev.divehub.shared.dive.TimeZoneOrigin
import java.util.Locale

/** The dive page route: the page itself holds no navigation state; back returns the logbook as it was. */
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.dive?.let { title(context, it, locale) } ?: "") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text(stringResource(R.string.dive_back)) }
                },
            )
        },
    ) { padding ->
        val modifier = Modifier.padding(padding).fillMaxSize()
        when {
            state.loading && state.dive == null -> Loading(modifier)
            state.offline && state.dive == null -> Notice(
                modifier = modifier,
                text = stringResource(R.string.dive_offline),
                retry = stringResource(R.string.dive_retry),
                onRetry = onRetry,
            )
            state.error != null && state.dive == null -> Notice(
                modifier = modifier,
                text = errorText(context, state.error),
                retry = stringResource(R.string.dive_retry),
                onRetry = onRetry,
            )
            state.dive != null -> DiveBody(state, state.dive, units, onOpenDive, modifier)
        }
    }
}

private fun title(context: Context, dive: DiveView, locale: Locale): String = dive.values.number?.let {
    context.getString(R.string.dive_title_number, it)
} ?: DiveDetailText.start(context, dive.values.startsAt.at, dive.values.startsAt.utcOffsetSeconds, locale)

private fun errorText(context: Context, code: String?): String = when (code) {
    "dive_not_found", "not_found" -> context.getString(R.string.dive_error_not_found)
    ErrorCodes.SIGN_IN_REQUIRED -> context.getString(R.string.error_sign_in_required)
    ErrorCodes.SERVER_ERROR -> context.getString(R.string.error_server)
    else -> context.getString(R.string.error_unknown)
}

@Composable
private fun Loading(modifier: Modifier) {
    val label = stringResource(R.string.loading)
    Box(modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(Modifier.semantics { contentDescription = label })
    }
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
private fun DiveBody(
    state: DiveDetailUiState,
    dive: DiveView,
    units: String?,
    onOpenDive: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = Formats.imperial(units, locale)
    val primary = profileRecording(dive.recordings)

    LazyColumn(modifier) {
        item { SyncLine(state.syncedAt, state.offline, R.string.dive_offline_cached) }
        item { Neighbours(state.neighbours, onOpenDive) }
        item { Facts(dive, locale, imperial) }
        item { Recordings(dive, locale, state.providers) }
        if (primary != null) {
            item { Profile(state, primary, locale, imperial) }
        }
        if (dive.participants.isNotEmpty()) {
            item { Participants(dive) }
        }
        dive.position?.let { position ->
            item { Position(position.latitude, position.longitude, locale) }
        }
    }
}

/**
 * Previous and next in the order of the list the Dive was opened from (ADR 0042). "Previous" is the row above. At the
 * ends of the list the control stays, so the others don't move, and says it is unavailable. Nothing when the list
 * doesn't show this Dive.
 */
@Composable
private fun Neighbours(neighbours: NeighboursView?, onOpenDive: (String) -> Unit) {
    val n = neighbours ?: return
    val position = n.position ?: return
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NeighbourButton(
            label = stringResource(R.string.dive_previous),
            unavailable = stringResource(R.string.dive_previous_unavailable),
            target = n.previous?.id,
            onOpenDive = onOpenDive,
        )
        Text(
            stringResource(R.string.dive_position_of, position, n.total),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        NeighbourButton(
            label = stringResource(R.string.dive_next),
            unavailable = stringResource(R.string.dive_next_unavailable),
            target = n.next?.id,
            onOpenDive = onOpenDive,
        )
    }
}

@Composable
private fun NeighbourButton(label: String, unavailable: String, target: String?, onOpenDive: (String) -> Unit) {
    TextButton(
        onClick = { target?.let(onOpenDive) },
        enabled = target != null,
        modifier = if (target == null) Modifier.semantics { contentDescription = unavailable } else Modifier,
    ) {
        Text(label)
    }
}

@Composable
private fun Facts(dive: DiveView, locale: Locale, imperial: Boolean) {
    val context = LocalContext.current
    val values = dive.values
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        FactRow(
            label = stringResource(R.string.dive_number),
            value = values.number?.let { Formats.number(locale, it) } ?: "–",
        )
        FactRow(
            label = stringResource(R.string.dive_start),
            value = DiveDetailText.start(context, values.startsAt.at, values.startsAt.utcOffsetSeconds, locale),
            mark = DiveDetailText.editedValue(context, dive.markOf(DiveValue.STARTS_AT), DiveValue.STARTS_AT, locale, imperial),
            note = timeZoneNote(dive.utcOffsetSource, dive.startSetByHand)?.let { timeZoneText(context, it) },
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
    FactRow(
        label = stringResource(R.string.dive_water_type),
        value = dive.waterType?.let { DiveDetailText.waterName(context, it) } ?: stringResource(R.string.dive_no_water_type),
        note = when {
            mismatch != null -> null
            dive.site == null -> context.getString(R.string.dive_choose_site_for_water)
            dive.waterType == null -> context.getString(R.string.dive_water_unknown)
            else -> context.getString(R.string.dive_water_from_site)
        },
    )
    if (mismatch != null) {
        Column(Modifier.padding(start = 150.dp, bottom = 8.dp)) {
            Text(
                context.getString(
                    R.string.dive_water_mismatch,
                    DiveDetailText.waterInSentence(context, mismatch.computer),
                    DiveDetailText.waterInSentence(context, mismatch.site),
                ),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                DiveDetailText.depthReadingText(context, depthReading(mismatch.depthPercent), locale),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun FactRow(label: String, value: String, mark: String? = null, note: String? = null) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(
            label,
            modifier = Modifier.width(150.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Column(Modifier.weight(1f)) {
            Text(value, style = MaterialTheme.typography.titleMedium)
            mark?.let { Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            note?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        }
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
    SectionHeading(stringResource(R.string.dive_recordings))
    val computers = computerRecordings(dive.recordings)
    if (computers.isEmpty()) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                dive.fromProvider?.let { provider ->
                    context.getString(R.string.dive_no_recording_from, providers.nameOf(provider))
                } ?: context.getString(R.string.dive_no_recording),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                stringResource(R.string.dive_no_recording_next),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        return
    }
    Column(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        computers.forEach { recording ->
            val name = recordingName(context, dive, recording)
            Row(Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
                if (recording.isPrimary) {
                    Text(
                        stringResource(R.string.dive_primary),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            recording.summary.waterType?.let {
                Text(
                    stringResource(R.string.dive_computer_water) + ": " + DiveDetailText.waterName(context, it),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            val parts = partsOf(recording, dive.recordings)
            if (parts.isNotEmpty()) {
                Text(
                    stringResource(R.string.dive_parts),
                    modifier = Modifier.padding(top = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
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
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            HorizontalDivider(Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)
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
) {
    val context = LocalContext.current
    SectionHeading(stringResource(R.string.dive_profile))
    val samples = state.samples
    val depth = samples?.let(::depthSeries)
    val summary = if (samples != null && depth != null) summarize(depth, temperatureSeries(samples)) else null
    if (depth == null || summary == null) {
        if (state.samplesPending) {
            Text(
                stringResource(R.string.dive_profile_pending),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            return
        }
        val failure = when {
            state.samplesError != null -> errorText(context, state.samplesError)
            samples != null -> context.getString(R.string.dive_profile_failed)
            else -> return
        }
        Text(
            failure,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
        )
        return
    }
    val summaryText = profileSummaryText(context, summary, locale, imperial)
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        ProfileChart(
            depth = depth,
            bands = surfaceBands(recording.summary.surfaceBreaks.orEmpty(), summary.durationSeconds),
            description = summaryText,
        )
        Text(summaryText, modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.bodyMedium)
        surfaceBreakSeconds(recording).forEach { seconds ->
            Text(
                context.getString(R.string.dive_surface_break, DiveDetailText.duration(context, seconds)),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        ProfileTable(depth, temperatureSeries(samples), locale, imperial)
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
private fun ProfileChart(depth: RecordingSamplesViewSeriesItem, bands: List<dev.divehub.shared.dive.SurfaceBand>, description: String) {
    val line = MaterialTheme.colorScheme.primary
    val area = MaterialTheme.colorScheme.primaryContainer
    val surface = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.18f)
    val span = (depth.offsetsMs.lastOrNull() ?: 0).toFloat().takeIf { it > 0f } ?: 1f
    val deepest = depth.values.maxOrNull()?.toFloat()?.takeIf { it > 0f } ?: 1f
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .semantics { contentDescription = description },
    ) {
        val width = size.width
        val height = size.height
        val points = depth.offsetsMs.mapIndexed { index, offset ->
            Offset(
                x = (offset / span).coerceIn(0f, 1f) * width,
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
        drawPath(outline, line, style = Stroke(width = 2.dp.toPx()))
    }
}

@Composable
private fun ProfileTable(
    depth: RecordingSamplesViewSeriesItem,
    temperature: RecordingSamplesViewSeriesItem?,
    locale: Locale,
    imperial: Boolean,
) {
    val context = LocalContext.current
    var open by remember { mutableStateOf(false) }
    TextButton(onClick = { open = !open }) { Text(stringResource(R.string.dive_profile_table)) }
    if (!open) return
    val rows = perMinute(depth, temperature)
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            TableHead(stringResource(R.string.dive_time), Modifier.weight(1f))
            TableHead(stringResource(R.string.dive_depth), Modifier.weight(1f))
            TableHead(stringResource(R.string.dive_temperature), Modifier.weight(1f))
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        rows.forEach { row ->
            Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
                TableCell(DiveDetailText.duration(context, row.minute * 60.0), Modifier.weight(1f))
                TableCell(DiveDetailText.depth(context, row.depthM, locale, imperial), Modifier.weight(1f))
                TableCell(
                    row.temperatureC?.let { DiveDetailText.temperature(context, it, locale, imperial) } ?: "–",
                    Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun TableHead(text: String, modifier: Modifier) {
    Text(text, modifier = modifier, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun TableCell(text: String, modifier: Modifier) {
    Text(text, modifier = modifier, style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun Participants(dive: DiveView) {
    val context = LocalContext.current
    SectionHeading(stringResource(R.string.dive_participants))
    Column(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        dive.participants.forEach { person ->
            Text(
                context.getString(R.string.dive_participant, person.name, DiveDetailText.role(context, person.role)),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

/** A Dive's position is private: only what the API sends is shown, and nothing is sent anywhere by itself. */
@Composable
private fun Position(latitude: Double, longitude: Double, locale: Locale) {
    val context = LocalContext.current
    SectionHeading(stringResource(R.string.dive_position))
    Text(
        DiveDetailText.position(context, latitude, longitude, locale),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun SectionHeading(text: String) {
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
            .semantics { heading() },
        style = MaterialTheme.typography.titleMedium,
    )
}
