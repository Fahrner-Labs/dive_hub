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
        val value = if (imperial) metres * FEET_PER_METRE else metres
        val text = String.format(locale, "%.1f", value)
        return context.getString(if (imperial) R.string.logbook_depth_feet else R.string.logbook_depth_metres, text)
    }

    fun role(context: Context, role: String): String = when (role) {
        "buddy" -> context.getString(R.string.participant_role_buddy)
        "guide" -> context.getString(R.string.participant_role_guide)
        "instructor" -> context.getString(R.string.participant_role_instructor)
        else -> role
    }
}
