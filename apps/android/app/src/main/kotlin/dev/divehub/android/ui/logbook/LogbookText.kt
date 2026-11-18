package dev.divehub.android.ui.logbook

import android.content.Context
import dev.divehub.android.R
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.durationMinutes
import java.text.NumberFormat
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Number and date text for the logbook rows (ADR 0014). Values are stored in metres and seconds. */
internal object LogbookText {
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

    /** "April 2026" for a local month "2026-04". */
    fun month(context: Context, month: String, locale: Locale): String =
        YearMonth.parse(month).atDay(1).format(DateTimeFormatter.ofPattern(context.getString(R.string.logbook_month_pattern), locale))

    /**
     * A row's day and local time, "Thu 2 Apr, 09:00". An offset of null uses the device's zone, except where the time
     * was logged without a zone (`unknown`): then it is the wall-clock time as stored (ADR 0030).
     */
    fun diveDay(context: Context, iso: String, utcOffsetSeconds: Int?, utcOffsetSource: String, locale: Locale): String {
        val instant = runCatching { Instant.parse(iso) }.getOrNull() ?: return iso
        val local = when {
            utcOffsetSeconds != null -> instant.atOffset(ZoneOffset.ofTotalSeconds(utcOffsetSeconds)).toLocalDateTime()
            utcOffsetSource == "unknown" -> instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
            else -> instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
        return local.format(DateTimeFormatter.ofPattern(context.getString(R.string.logbook_day_pattern), locale))
    }

    fun filterName(context: Context, filter: LogbookFilter, connected: List<String>): String = when (filter) {
        LogbookFilter.NO_RECORDING -> context.getString(R.string.logbook_only_no_recording)
        LogbookFilter.NO_SITE -> context.getString(R.string.logbook_only_no_site)
        LogbookFilter.WITH_FINDINGS -> context.getString(R.string.logbook_only_with_findings)
        LogbookFilter.NOT_AT_PROVIDER -> context.getString(
            R.string.logbook_only_not_at_provider,
            connected.ifEmpty { listOf(context.getString(R.string.provider_some_service)) }.joinToString(", "),
        )
        LogbookFilter.CHANGED_SINCE_SENT -> context.getString(R.string.logbook_only_changed_since_sent)
    }

    fun role(context: Context, role: String): String = when (role) {
        "buddy" -> context.getString(R.string.participant_role_buddy)
        "guide" -> context.getString(R.string.participant_role_guide)
        "instructor" -> context.getString(R.string.participant_role_instructor)
        else -> role
    }
}
