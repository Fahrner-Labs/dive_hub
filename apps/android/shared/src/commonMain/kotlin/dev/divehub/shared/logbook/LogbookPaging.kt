package dev.divehub.shared.logbook

/** A logbook page holds 25 Dives (ADR 0040); paging is offset based (ADR 0017). */
const val LOGBOOK_PAGE_SIZE: Int = 25

/** The offset paging the logbook's first page and its page buttons need. Pages are 1-based in the client. */
object LogbookPaging {
    fun offset(page: Int): Int = (page.coerceAtLeast(1) - 1) * LOGBOOK_PAGE_SIZE

    fun pageCount(total: Int): Int = if (total <= 0) 0 else (total + LOGBOOK_PAGE_SIZE - 1) / LOGBOOK_PAGE_SIZE

    /** The 1-based positions this page shows, "26–50 of 312"; null when there is nothing to show. */
    fun range(page: Int, total: Int): IntRange? {
        if (total <= 0) return null
        val from = ((page.coerceAtLeast(1) - 1) * LOGBOOK_PAGE_SIZE + 1).coerceAtMost(total)
        val to = (page.coerceAtLeast(1) * LOGBOOK_PAGE_SIZE).coerceAtMost(total)
        return from..to
    }
}
