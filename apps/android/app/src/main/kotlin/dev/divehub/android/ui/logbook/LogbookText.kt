package dev.divehub.android.ui.logbook

import android.content.Context
import dev.divehub.android.R
import dev.divehub.android.ui.Formats
import dev.divehub.shared.logbook.LogbookFilter
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Date text for the logbook rows, over the units every page says the same way ([Formats]). */
internal object LogbookText {
    fun number(locale: Locale, value: Int): String = Formats.number(locale, value)

    /** The User's unit system: their choice, else the device region (ADR 0014). */
    fun imperial(preference: String?, locale: Locale): Boolean = Formats.imperial(preference, locale)

    fun duration(context: Context, seconds: Double): String = Formats.duration(context, seconds)

    fun depth(context: Context, metres: Double, locale: Locale, imperial: Boolean): String =
        Formats.depth(context, metres, locale, imperial)

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

    fun role(context: Context, role: String): String = Formats.role(context, role)
}
