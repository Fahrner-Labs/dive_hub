package dev.divehub.shared.dive

import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemSummarySurfaceBreaksItem
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.api.models.RecordingSamplesViewSeriesItem
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

/** The depth profile in words (WCAG 1.1.1): what the picture shows, and the samples per minute as rows. */
data class ProfileSummary(
    val maxDepthM: Double,
    /** Seconds from the Recording's start. */
    val maxDepthAtSeconds: Double,
    val durationSeconds: Double,
    val minTemperatureC: Double?,
    val maxTemperatureC: Double?,
)

data class ProfileRow(val minute: Int, val depthM: Double, val temperatureC: Double?)

/** A Surface break on the profile's time axis, as fractions of the Recording's span (ADR 0063). */
data class SurfaceBand(val fromFraction: Double, val toFraction: Double)

fun depthSeries(samples: RecordingSamplesView): RecordingSamplesViewSeriesItem? =
    samples.series.firstOrNull { it.channel == DEPTH_CHANNEL }

fun temperatureSeries(samples: RecordingSamplesView): RecordingSamplesViewSeriesItem? =
    samples.series.firstOrNull { it.channel == TEMPERATURE_CHANNEL }

private const val DEPTH_CHANNEL = "depth"
private const val TEMPERATURE_CHANNEL = "temperature"
private val PRESSURE_CHANNEL = Regex("^tankPressure(?::(\\d+))?$")

/** A Recording's tank-pressure series, first pod first; series without samples are left out (ADR 0045). */
fun pressureSeries(samples: RecordingSamplesView): List<RecordingSamplesViewSeriesItem> =
    samples.series
        .filter { it.values.isNotEmpty() && PRESSURE_CHANNEL.matches(it.channel) }
        .sortedBy { PRESSURE_CHANNEL.matchEntire(it.channel)?.groupValues?.get(1)?.toIntOrNull() ?: 0 }

/** Deepest point and when, total time, and the temperature range when it was recorded. Null without samples. */
fun summarize(depth: RecordingSamplesViewSeriesItem, temperature: RecordingSamplesViewSeriesItem? = null): ProfileSummary? {
    if (depth.values.isEmpty() || depth.offsetsMs.isEmpty()) return null
    var deepest = 0
    for (index in 1 until depth.values.size) {
        if (depth.values[index] > depth.values[deepest]) deepest = index
    }
    val temperatures = temperature?.values.orEmpty()
    return ProfileSummary(
        maxDepthM = depth.values[deepest],
        maxDepthAtSeconds = (depth.offsetsMs.getOrNull(deepest) ?: 0) / 1000.0,
        durationSeconds = (depth.offsetsMs.lastOrNull() ?: 0) / 1000.0,
        minTemperatureC = temperatures.minOrNull(),
        maxTemperatureC = temperatures.maxOrNull(),
    )
}

/** The index of the sample nearest to a moment; a tie keeps the earlier sample. Null without samples. */
fun nearestIndex(series: RecordingSamplesViewSeriesItem?, milliseconds: Int): Int? {
    if (series == null || series.offsetsMs.isEmpty() || series.values.isEmpty()) return null
    var best = 0
    for (index in 1 until series.offsetsMs.size) {
        if (abs(series.offsetsMs[index] - milliseconds) < abs(series.offsetsMs[best] - milliseconds)) best = index
    }
    return best
}

/** The value of the sample nearest to a moment; null for a series without samples. */
fun nearest(series: RecordingSamplesViewSeriesItem?, milliseconds: Int): Double? =
    nearestIndex(series, milliseconds)?.let { series?.values?.getOrNull(it) }

/** The offset on the profile's time axis under a touch at [fraction] (0 at the start, 1 at the end). */
fun timeAtFraction(fraction: Double, spanMs: Int): Int =
    (fraction.coerceIn(0.0, 1.0) * spanMs).roundToInt()

/** One row per whole minute: the sample nearest to it (depth, and temperature when recorded). */
fun perMinute(depth: RecordingSamplesViewSeriesItem, temperature: RecordingSamplesViewSeriesItem? = null): List<ProfileRow> {
    val minutes = floor((depth.offsetsMs.lastOrNull() ?: 0) / 60_000.0).toInt()
    return (0..minutes).map { minute ->
        ProfileRow(
            minute = minute,
            depthM = nearest(depth, minute * 60_000) ?: 0.0,
            temperatureC = nearest(temperature, minute * 60_000),
        )
    }
}

/** Each Surface break's length in seconds, in the Recording's order (ADR 0063). */
fun surfaceBreakSeconds(recording: DiveViewRecordingsItem): List<Double> =
    recording.summary.surfaceBreaks.orEmpty().map { (it.endS - it.startS).coerceAtLeast(0.0) }

/** The time the Dive spent at the surface in its Surface breaks; the duration includes it. */
fun surfaceBreakTotalSeconds(recording: DiveViewRecordingsItem): Double = surfaceBreakSeconds(recording).sum()

/** The Surface breaks as fractions of the profile's span, for the band at the surface. Nothing spans a zero span. */
fun surfaceBands(
    breaks: List<DiveViewRecordingsItemSummarySurfaceBreaksItem>,
    totalSeconds: Double,
): List<SurfaceBand> {
    if (totalSeconds <= 0.0) return emptyList()
    return breaks.mapNotNull { item ->
        val from = (item.startS / totalSeconds).coerceIn(0.0, 1.0)
        val to = (item.endS / totalSeconds).coerceIn(0.0, 1.0)
        if (to <= from) null else SurfaceBand(from, to)
    }
}

/** The parts of a joined Recording, in the order they were dived (ADR 0063). */
fun partsOf(recording: DiveViewRecordingsItem, recordings: List<DiveViewRecordingsItem>): List<DiveViewRecordingsItem> =
    recordings.filter { it.partOf == recording.id }.sortedBy { it.startsAt }

/** The Recordings that are computers of their own: never the parts of a joined one (ADR 0063). */
fun computerRecordings(recordings: List<DiveViewRecordingsItem>): List<DiveViewRecordingsItem> =
    recordings.filter { it.partOf == null }

/**
 * The Recording the dive page draws its profile from (ADR 0016): the Primary one, else the first computer of its own.
 * One rule for the samples, the drawn profile and the Surface breaks said with the duration.
 */
fun profileRecording(recordings: List<DiveViewRecordingsItem>): DiveViewRecordingsItem? =
    recordings.firstOrNull { it.isPrimary } ?: recordings.firstOrNull { it.partOf == null }
