package dev.divehub.android.ui.logbook

import android.content.Context
import dev.divehub.android.R
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.durationMinutes
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Number and date text for the logbook rows (ADR 0014). Values are stored in metres and seconds. */
internal object LogbookText {
    fun number(locale: Locale, value: Int): String = String.format(locale, "%,d", value)

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

    /** "18.5 m". */
    fun depth(context: Context, metres: Double, locale: Locale): String =
        context.getString(R.string.logbook_depth, String.format(locale, "%.1f", metres))

    /** "April 2026" for a local month "2026-04". */
    fun month(month: String, locale: Locale): String =
        YearMonth.parse(month).atDay(1).format(DateTimeFormatter.ofPattern("LLLL yyyy", locale))

    /** A row's day and local time, "Thu 2 Apr, 09:00"; an unknown offset is the wall-clock time as stored (ADR 0030). */
    fun diveDay(iso: String, utcOffsetSeconds: Int?, locale: Locale): String {
        val instant = runCatching { Instant.parse(iso) }.getOrNull() ?: return iso
        val local = instant.atOffset(ZoneOffset.ofTotalSeconds(utcOffsetSeconds ?: 0)).toLocalDateTime()
        return local.format(DateTimeFormatter.ofPattern("EEE d MMM, HH:mm", locale))
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
