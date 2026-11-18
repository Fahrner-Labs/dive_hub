package dev.divehub.shared.logbook

import dev.divehub.api.models.ApiDivesGet200ResponseCounts

/** The logbook's "Show only" filters (ADR 0040), in the order they are offered. All pressed filters must fit. */
enum class LogbookFilter(val wire: String) {
    NO_RECORDING("no-recording"),
    NO_SITE("no-site"),
    WITH_FINDINGS("with-findings"),
    NOT_AT_PROVIDER("not-at-provider"),
    CHANGED_SINCE_SENT("changed-since-sent"),
    ;
}

/** How many Dives this filter would show, whatever filters are pressed (ADR 0040): the counts never narrow. */
fun ApiDivesGet200ResponseCounts.countOf(filter: LogbookFilter): Int = when (filter) {
    LogbookFilter.NO_RECORDING -> noRecording
    LogbookFilter.NO_SITE -> noSite
    LogbookFilter.WITH_FINDINGS -> withFindings
    LogbookFilter.NOT_AT_PROVIDER -> notAtProvider
    LogbookFilter.CHANGED_SINCE_SENT -> changedSinceSent
}

/** A filter as the screen offers it: its own count, and whether it narrows the list now. */
data class FilterChip(val filter: LogbookFilter, val count: Int, val applied: Boolean)

/** The filters to offer: a count of 0 hides one unless it is pressed (ADR 0040). */
fun filterChips(counts: ApiDivesGet200ResponseCounts, applied: Set<LogbookFilter>): List<FilterChip> =
    LogbookFilter.entries
        .map { FilterChip(it, counts.countOf(it), it in applied) }
        .filter { it.count > 0 || it.applied }

/** This filter pressed or released. */
fun toggleFilter(applied: Set<LogbookFilter>, filter: LogbookFilter): Set<LogbookFilter> =
    if (filter in applied) applied - filter else applied + filter
