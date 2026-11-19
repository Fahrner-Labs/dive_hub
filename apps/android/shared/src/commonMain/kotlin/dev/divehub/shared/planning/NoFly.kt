// DAN's no-fly time and the on-device deco signal, ported from apps/server/src/assessment/series-rules.ts (FAH-271;
// ADR 0036, ADR 0071). `enteredDeco` is not in an API response, so the client derives it from the synced series: the
// computer's no-decompression limit reached zero at least once (apps/server/src/assessment/profile-rules.ts).
package dev.divehub.shared.planning

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/** A Dive as the series rules see it. */
data class SeriesDive(
    val id: String,
    val startMs: Long,
    val endMs: Double,
    /** The local calendar day the dive started on (YYYY-MM-DD). */
    val day: String,
    val maxDepthM: Double?,
    val enteredDeco: Boolean,
)

/** The synced Dive fields `seriesDiveOf` reads; the API's startsAt and duration are enough. */
data class SeriesDiveInput(
    val id: String,
    val startMs: Long,
    val utcOffsetSeconds: Int?,
    val durationSeconds: Double,
    val maxDepthM: Double?,
)

private const val HOUR_MS: Long = 3_600_000
private val NOT_COVERED = setOf("apnea", "ccr", "scr")

/** The series rules cover open-circuit and gauge dives, not apnea, CCR or SCR (the server's `NOT_COVERED`). */
fun seriesRulesApply(diveMode: String?): Boolean = diveMode == null || diveMode !in NOT_COVERED

private fun dayNumber(day: String): Long = LocalDate.parse(day).toEpochDays()

/** A Dive as the series rules see it; its day is the local one where the offset is known. */
fun seriesDiveOf(d: SeriesDiveInput, enteredDeco: Boolean): SeriesDive {
    val local = Instant.fromEpochMilliseconds(d.startMs + (d.utcOffsetSeconds ?: 0) * 1000L)
        .toLocalDateTime(TimeZone.UTC)
    return SeriesDive(
        id = d.id,
        startMs = d.startMs,
        endMs = d.startMs + d.durationSeconds * 1000,
        day = local.date.toString(),
        maxDepthM = d.maxDepthM,
        enteredDeco = enteredDeco,
    )
}

/** The smallest no-decompression limit in the series, or null when it carries none (profile-rules.ts). */
fun minNdlSeconds(ndlSeconds: List<Double>): Double? = ndlSeconds.minOrNull()

/** The computer reached zero no-decompression time at least once, so the Dive entered decompression. */
fun enteredDecoFromNdl(ndlSeconds: List<Double>): Boolean = minNdlSeconds(ndlSeconds) == 0.0

/** How long after a dive DAN's guideline says not to fly (ADR 0036): information beside the findings, not a finding. */
data class NoFly(
    val hours: Int,
    /** A single dive without stops; several dives that day or diving the day before; a dive with decompression stops. */
    val reason: NoFlyReason,
    /** ISO time (UTC) from which the guideline is met. */
    val until: String,
)

enum class NoFlyReason { single, several, decompression }

/**
 * DAN's no-fly time after dive [id], if it is the last dive of its day (the time runs from the day's last dive): 12 hours
 * after a single dive, 18 after several dives or days of diving, and 24 here after decompression, where DAN says
 * "substantially longer than 18". [dives]: the Diver's dives of that day and the day before, at least.
 */
fun noFlyAfter(dives: List<SeriesDive>, id: String): NoFly? {
    val dive = dives.find { it.id == id } ?: return null
    val sameDay = dives.filter { it.day == dive.day }.sortedBy { it.startMs }
    if (sameDay.lastOrNull()?.id != dive.id) return null
    val previousDay = dayNumber(dive.day) - 1
    val several = sameDay.size > 1 || dives.any { dayNumber(it.day) == previousDay }
    val hours = if (sameDay.any { it.enteredDeco }) 24 else if (several) 18 else 12
    return NoFly(
        hours = hours,
        reason = when (hours) {
            24 -> NoFlyReason.decompression
            18 -> NoFlyReason.several
            else -> NoFlyReason.single
        },
        until = isoUtc((dive.endMs + hours * HOUR_MS).toLong()),
    )
}
