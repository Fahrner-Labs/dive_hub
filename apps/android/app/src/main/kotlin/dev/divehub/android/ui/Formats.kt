package dev.divehub.android.ui

import android.content.Context
import dev.divehub.android.R
import dev.divehub.shared.logbook.durationMinutes
import java.text.NumberFormat
import java.util.Locale

/**
 * The numbers and units every page says the same way (ADR 0014): whole numbers in the UI language, durations in
 * minutes and hours, depths in metres or feet, and a Diver's role. A page adds only what is its own.
 */
internal object Formats {
    private const val FEET_PER_METRE = 1 / 0.3048
    private const val PSI_PER_BAR = 14.5038
    private val IMPERIAL_REGIONS = setOf("US", "LR", "MM")

    fun number(locale: Locale, value: Int): String = NumberFormat.getIntegerInstance(locale).format(value)

    /** The User's unit system: their choice, else the device region (ADR 0014). */
    fun imperial(preference: String?, locale: Locale): Boolean = when (preference) {
        "imperial" -> true
        "metric" -> false
        else -> locale.country in IMPERIAL_REGIONS
    }

    /** "45 min", "1 h 5 min". */
    fun duration(context: Context, seconds: Double): String {
        val minutes = durationMinutes(seconds)
        val hours = minutes / 60
        val rest = minutes % 60
        return if (hours == 0L) {
            context.getString(R.string.logbook_duration_minutes, rest)
        } else {
            context.getString(R.string.logbook_duration_hours_minutes, hours, rest)
        }
    }

    /** "18.5 m", "60.7 ft". */
    fun depth(context: Context, metres: Double, locale: Locale, imperial: Boolean): String {
        val text = String.format(locale, "%.1f", depthIn(metres, imperial))
        return context.getString(if (imperial) R.string.logbook_depth_feet else R.string.logbook_depth_metres, text)
    }

    /** A depth in the User's units: metres or feet. */
    fun depthIn(metres: Double, imperial: Boolean): Double = if (imperial) metres * FEET_PER_METRE else metres

    /** A depth typed in the User's units, back in metres. */
    fun depthFromDisplay(value: Double, imperial: Boolean): Double = if (imperial) value / FEET_PER_METRE else value

    /** "200 bar", "2,901 psi": whole numbers, as a gauge is read. */
    fun pressure(context: Context, bar: Double, locale: Locale, imperial: Boolean): String {
        val text = NumberFormat.getIntegerInstance(locale).format(pressureIn(bar, imperial))
        return context.getString(
            if (imperial) R.string.tools_pressure_psi else R.string.tools_pressure_bar,
            text,
        )
    }

    /** A pressure in the User's units: bar or psi. */
    fun pressureIn(bar: Double, imperial: Boolean): Double = if (imperial) bar * PSI_PER_BAR else bar

    /** A pressure typed in the User's units, back in bar. */
    fun pressureFromDisplay(value: Double, imperial: Boolean): Double = if (imperial) value / PSI_PER_BAR else value

    /** A number with the given decimals in the UI language. */
    fun decimal(locale: Locale, value: Double, places: Int): String =
        NumberFormat.getNumberInstance(locale).apply {
            minimumFractionDigits = places
            maximumFractionDigits = places
        }.format(value)

    fun role(context: Context, role: String): String = when (role) {
        "buddy" -> context.getString(R.string.participant_role_buddy)
        "guide" -> context.getString(R.string.participant_role_guide)
        "instructor" -> context.getString(R.string.participant_role_instructor)
        else -> role
    }
}
