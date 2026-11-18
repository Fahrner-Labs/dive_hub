package dev.divehub.shared.dive

import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewFromRecording
import kotlin.math.abs
import kotlin.math.round

/**
 * The Dive values that can be set by hand (ADR 0015). The number is not one of them: the server computes it
 * (ADR 0048), so a dive page only ever shows it.
 */
enum class DiveValue(val wire: String) {
    STARTS_AT("startsAt"),
    DURATION_SECONDS("durationSeconds"),
    MAX_DEPTH_M("maxDepthM"),
    AVG_DEPTH_M("avgDepthM"),
    WATER_TEMPERATURE_C("waterTemperatureC"),
    ;
}

/** What the Primary recording says for a value (ADR 0015); SI units, as the API sends them. */
sealed interface RecordedValue {
    data class Number(val value: Double) : RecordedValue

    data class Time(val at: String, val utcOffsetSeconds: Int?) : RecordedValue
}

/** A value in effect, and what the recording says where the User set it by hand. */
data class MarkedValue(val edited: Boolean, val recorded: RecordedValue?)

fun DiveView.recordedValueOf(field: DiveValue): RecordedValue? {
    val from: DiveViewFromRecording = fromRecording ?: return null
    return when (field) {
        DiveValue.STARTS_AT -> RecordedValue.Time(from.startsAt.at, from.startsAt.utcOffsetSeconds)
        DiveValue.DURATION_SECONDS -> from.durationSeconds.let(RecordedValue::Number)
        DiveValue.MAX_DEPTH_M -> from.maxDepthM?.let(RecordedValue::Number)
        DiveValue.AVG_DEPTH_M -> from.avgDepthM?.let(RecordedValue::Number)
        DiveValue.WATER_TEMPERATURE_C -> from.waterTemperatureC?.let(RecordedValue::Number)
    }
}

/**
 * The "edited" mark a value carries (ADR 0015): visible text with the recording's value, never only a tooltip.
 * `startsAt` showing in `overrides` also means the User set the start by hand, which silences the time-zone note.
 */
fun DiveView.markOf(field: DiveValue): MarkedValue =
    MarkedValue(edited = field.wire in overrides, recorded = recordedValueOf(field))

/** Whether the User set the Dive's start by hand; the time-zone note stays away then (client contract, Dives). */
val DiveView.startSetByHand: Boolean get() = DiveValue.STARTS_AT.wire in overrides

private val MANUFACTURERS = mapOf(
    "garmin" to "Garmin",
    "suunto" to "Suunto",
    "shearwater" to "Shearwater",
    "mares" to "Mares",
    "scubapro" to "Scubapro",
    "aqualung" to "Aqualung",
    "cressi" to "Cressi",
    "oceanic" to "Oceanic",
    "ratio" to "Ratio",
    "deepblu" to "Deepblu",
)

/** How a dive computer maker spells itself; an unknown id keeps its words, capitalised (as the web client does). */
fun manufacturerName(id: String): String {
    MANUFACTURERS[id.lowercase()]?.let { return it }
    val words = id.replace('_', ' ')
    return words.replaceFirstChar { it.uppercase() }
}

/** "Garmin Descent Mk3", the Recording's Device as the UI names it (ADR 0016). */
fun deviceName(manufacturer: String, product: String?): String =
    if (product.isNullOrBlank()) manufacturerName(manufacturer) else "${manufacturerName(manufacturer)} $product"

/** How the computer's depths read against the site's water (ADR 0025). */
sealed interface DepthReading {
    data class Shallow(val percent: Int) : DepthReading

    data class Deep(val percent: Int) : DepthReading

    data object Off : DepthReading
}

/** `waterMismatch.depthPercent` as words: negative reads shallow, positive deep, null may read a little off. */
fun depthReading(depthPercent: Double?): DepthReading = when {
    depthPercent == null -> DepthReading.Off
    depthPercent < 0 -> DepthReading.Shallow(round(abs(depthPercent)).toInt())
    else -> DepthReading.Deep(round(depthPercent).toInt())
}
