package dev.divehub.shared.logbook

import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewMonthsItem
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/** A page's Dives under a month heading, with the month's figures for all its matching Dives (ADR 0040). */
data class LogbookMonth(
    /** Local `YYYY-MM`, or null when the list is not sorted by date. */
    val month: String?,
    val figures: DiveListViewMonthsItem?,
    val dives: List<DiveListViewDivesItem>,
)

/** The local month of a Dive: its start plus its UTC offset; an unknown offset keeps the wall-clock time (ADR 0040). */
fun localMonthOf(startsAt: String, utcOffsetSeconds: Int?): String? = runCatching {
    val local = Instant.parse(startsAt)
        .plus(utcOffsetSeconds ?: 0, DateTimeUnit.SECOND)
        .toLocalDateTime(TimeZone.UTC)
    "${local.year.toString().padStart(4, '0')}-${(local.month.ordinal + 1).toString().padStart(2, '0')}"
}.getOrNull()

/**
 * The page's Dives grouped by their consecutive months. Without `months` (the list isn't sorted by date) the page is
 * one group without a heading. Figures come from the API, so a month on page 2 counts all its Dives, not its share.
 */
fun monthGroups(
    dives: List<DiveListViewDivesItem>,
    months: List<DiveListViewMonthsItem>,
): List<LogbookMonth> {
    if (months.isEmpty()) return listOf(LogbookMonth(null, null, dives))
    val figures = months.associateBy { it.month }
    val groups = mutableListOf<LogbookMonth>()
    for (dive in dives) {
        val month = localMonthOf(dive.startsAt, dive.utcOffsetSeconds)
        val last = groups.lastOrNull()
        if (last != null && last.month == month) {
            groups[groups.lastIndex] = last.copy(dives = last.dives + dive)
        } else {
            groups += LogbookMonth(month, month?.let(figures::get), listOf(dive))
        }
    }
    return groups
}
