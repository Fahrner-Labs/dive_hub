package dev.divehub.android.ui.dive

import android.content.Context
import dev.divehub.android.R
import dev.divehub.android.ui.Formats
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

    fun duration(context: Context, seconds: Double): String = Formats.duration(context, seconds)

    fun depth(context: Context, metres: Double, locale: Locale, imperial: Boolean): String =
        Formats.depth(context, metres, locale, imperial)

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

    fun role(context: Context, role: String): String = Formats.role(context, role)

    /** "12 L", "11.1 L": a whole size keeps no decimal. */
    fun volume(context: Context, litres: Double, locale: Locale): String {
        val text = if (litres == whole(litres)) Formats.number(locale, litres.toInt()) else Formats.decimal(locale, litres, 1)
        return context.getString(R.string.dive_litres, text)
    }

    /** "4.0 kg", "8.8 lb": lead and a feedback amount are shown in the User's unit (ADR 0014). */
    fun mass(context: Context, kg: Double, locale: Locale, imperial: Boolean): String {
        val text = Formats.decimal(locale, if (imperial) kg / KG_PER_LB else kg, 1)
        return context.getString(if (imperial) R.string.dive_mass_pounds else R.string.dive_mass_kilograms, text)
    }

    /** The gas a Cylinder held: "Air", "EAN32", "Trimix 21/35"; null when the API sent no mix. */
    fun gas(context: Context, o2: Double?, he: Double?): String {
        if (o2 == null || he == null) return context.getString(R.string.dive_gas_unknown)
        val oxygen = percent(o2)
        return when {
            he > 0 -> context.getString(R.string.dive_gas_trimix, oxygen, percent(he))
            o2 == 21.0 -> context.getString(R.string.dive_gas_air)
            else -> context.getString(R.string.dive_gas_nitrox, oxygen)
        }
    }

    /** A suit with what is known about it: "Wetsuit, 5 mm, with hood", "Drysuit, heavy undergarment". */
    fun suit(context: Context, locale: Locale, type: String, thicknessMm: Double?, hood: Boolean?, undergarment: String?): String {
        val parts = buildList {
            add(
                when (type) {
                    "none" -> context.getString(R.string.tools_suit_none)
                    "skin" -> context.getString(R.string.tools_suit_skin)
                    "wetsuit" -> context.getString(R.string.tools_suit_wetsuit)
                    "semidry" -> context.getString(R.string.tools_suit_semidry)
                    "drysuit" -> context.getString(R.string.tools_suit_drysuit)
                    else -> type
                },
            )
            thicknessMm?.let { add(context.getString(R.string.dive_suit_thickness, decimalText(locale, it))) }
            hood?.let { add(context.getString(if (it) R.string.dive_suit_with_hood else R.string.dive_suit_without_hood)) }
            undergarment?.let {
                add(
                    when (it) {
                        "light" -> context.getString(R.string.dive_undergarment_light)
                        "medium" -> context.getString(R.string.dive_undergarment_medium)
                        "heavy" -> context.getString(R.string.dive_undergarment_heavy)
                        else -> it
                    },
                )
            }
        }
        return parts.joinToString(", ")
    }

    fun material(context: Context, material: String): String = when (material) {
        "aluminium" -> context.getString(R.string.tools_material_aluminium)
        "steel" -> context.getString(R.string.tools_material_steel)
        "carbon" -> context.getString(R.string.tools_material_carbon)
        else -> material
    }

    fun placement(context: Context, placement: String): String = when (placement) {
        "belt" -> context.getString(R.string.dive_placement_belt)
        "integrated" -> context.getString(R.string.dive_placement_integrated)
        "trim" -> context.getString(R.string.dive_placement_trim)
        "ankle" -> context.getString(R.string.dive_placement_ankle)
        "backplate" -> context.getString(R.string.dive_placement_backplate)
        else -> context.getString(R.string.dive_placement_other)
    }

    private fun whole(value: Double): Double = value.toLong().toDouble()

    private fun decimalText(locale: Locale, value: Double): String =
        if (value == whole(value)) Formats.number(locale, value.toInt()) else Formats.decimal(locale, value, 1)

    private fun percent(value: Double): String =
        if (value == whole(value)) value.toInt().toString() else String.format(Locale.ROOT, "%.1f", value)

    private fun abs(value: Double): Double = if (value < 0) -value else value

    private const val KG_PER_LB = 0.45359237
}
