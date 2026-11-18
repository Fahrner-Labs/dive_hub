package dev.divehub.android.ui.dive

import android.content.Context
import dev.divehub.android.R
import dev.divehub.android.ui.logbook.LogbookText
import dev.divehub.shared.dive.DepthReading
import dev.divehub.shared.dive.DiveValue
import dev.divehub.shared.dive.MarkedValue
import dev.divehub.shared.dive.RecordedValue
import dev.divehub.shared.dive.diveLocalTime
import dev.divehub.shared.dive.utcOffsetText
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Number, time and unit text of the dive page (ADR 0014). Values are stored in metres, seconds and °C. */
internal object DiveDetailText {
    private const val FAHRENHEIT_PER_CELSIUS = 9.0 / 5.0

    fun number(locale: Locale, value: Int): String = NumberFormat.getIntegerInstance(locale).format(value)

    /** The local time at the dive, with where its offset lies, "(UTC+2)"; as logged in UTC when there is none. */
    fun start(context: Context, at: String, utcOffsetSeconds: Int?, locale: Locale): String {
        val local = diveLocalTime(at, utcOffsetSeconds) ?: return at
        val pattern = context.getString(R.string.dive_start_pattern)
        val text = LocalDateTime
            .of(local.year, local.month.ordinal + 1, local.day, local.hour, local.minute, local.second)
            .format(DateTimeFormatter.ofPattern(pattern, locale))
        val offset = utcOffsetText(utcOffsetSeconds) ?: return text
        return context.getString(R.string.dive_time_with_offset, text, offset)
    }

    fun duration(context: Context, seconds: Double): String = LogbookText.duration(context, seconds)

    fun depth(context: Context, metres: Double, locale: Locale, imperial: Boolean): String =
        LogbookText.depth(context, metres, locale, imperial)

    /** "18 °C" or "64 °F", whole numbers as a dive page shows water temperature. */
    fun temperature(context: Context, celsius: Double, locale: Locale, imperial: Boolean): String {
        val value = if (imperial) celsius * FAHRENHEIT_PER_CELSIUS + 32 else celsius
        val text = String.format(locale, "%.0f", value)
        return context.getString(
            if (imperial) R.string.dive_temperature_fahrenheit else R.string.dive_temperature_celsius,
            text,
        )
    }

    /** What a value set by hand says the recording had: "edited · recording: 20.0 m". */
    fun editedValue(
        context: Context,
        marked: MarkedValue,
        field: DiveValue,
        locale: Locale,
        imperial: Boolean,
    ): String? {
        if (!marked.edited) return null
        val recorded = marked.recorded ?: return null
        val text = when (recorded) {
            is RecordedValue.Time -> start(context, recorded.at, recorded.utcOffsetSeconds, locale)
            is RecordedValue.Number -> when (field) {
                DiveValue.DURATION_SECONDS -> duration(context, recorded.value)
                DiveValue.WATER_TEMPERATURE_C -> temperature(context, recorded.value, locale, imperial)
                else -> depth(context, recorded.value, locale, imperial)
            }
        }
        return context.getString(R.string.dive_edited_value, text)
    }

    /** "Salt water" for a water type the API names; an unknown one stays as it came. */
    fun waterName(context: Context, waterType: String): String = when (waterType) {
        "fresh" -> context.getString(R.string.water_fresh)
        "salt" -> context.getString(R.string.water_salt)
        "brackish" -> context.getString(R.string.water_brackish)
        "en13319" -> context.getString(R.string.water_en13319)
        "custom" -> context.getString(R.string.water_custom)
        else -> waterType
    }

    /** The same water inside a sentence: "salt water", "a custom density". */
    fun waterInSentence(context: Context, waterType: String): String = when (waterType) {
        "fresh" -> context.getString(R.string.water_in_sentence_fresh)
        "salt" -> context.getString(R.string.water_in_sentence_salt)
        "brackish" -> context.getString(R.string.water_in_sentence_brackish)
        "en13319" -> context.getString(R.string.water_in_sentence_en13319)
        "custom" -> context.getString(R.string.water_in_sentence_custom)
        else -> waterType
    }

    /** How the computer's depths read against the site's water, "about 2 % shallow". */
    fun depthReadingText(context: Context, reading: DepthReading, locale: Locale): String = when (reading) {
        DepthReading.Off -> context.getString(R.string.dive_water_reads_off)
        is DepthReading.Shallow -> context.getString(
            R.string.dive_water_reads_shallow,
            NumberFormat.getIntegerInstance(locale).format(reading.percent),
        )
        is DepthReading.Deep -> context.getString(
            R.string.dive_water_reads_deep,
            NumberFormat.getIntegerInstance(locale).format(reading.percent),
        )
    }

    /** "28.4950° N, 34.5160° E" (ADR 0020); only what the API sends is shown. */
    fun position(context: Context, latitude: Double, longitude: Double, locale: Locale): String {
        val decimals = NumberFormat.getNumberInstance(locale).apply {
            minimumFractionDigits = 4
            maximumFractionDigits = 4
        }
        val lat = context.getString(
            if (latitude < 0) R.string.dive_position_south else R.string.dive_position_north,
            decimals.format(abs(latitude)),
        )
        val lon = context.getString(
            if (longitude < 0) R.string.dive_position_west else R.string.dive_position_east,
            decimals.format(abs(longitude)),
        )
        return context.getString(R.string.dive_position_value, lat, lon)
    }

    fun role(context: Context, role: String): String = LogbookText.role(context, role)

    private fun abs(value: Double): Double = if (value < 0) -value else value
}
