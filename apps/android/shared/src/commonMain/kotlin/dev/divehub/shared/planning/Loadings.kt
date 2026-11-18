// Current residual loadings from the Diver's logged dives, ported from apps/server/src/planning/loadings.ts (FAH-271;
// ADR 0071). The N2 replay uses the recorded profiles through the shared ZHL-16C core; CNS decays with a fixed sourced
// half-life; OTU sums a hard 24 h window. An estimate from the Diver's own logbook, never advice (ADR 0032/0036).
package dev.divehub.shared.planning

import kotlin.math.max
import kotlin.math.pow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/** The slowest compartment (635 min) gets more than six half-times in this window. */
const val N2_REPLAY_WINDOW_HOURS: Int = 72
/** After this, the tissues are back at surface saturation within noise; the planner uses the same window. */
const val N2_STALE_AFTER_HOURS: Int = 48
/** Shearwater, "The CNS oxygen clock": a 90-minute elimination half-life on the surface. */
const val CNS_HALF_LIFE_MINUTES: Int = 90
/** Garmin documents that a dive's OTU expire after 24 hours; NOAA's REPEX is the accumulation model. */
const val OTU_WINDOW_HOURS: Int = 24

private const val HOUR_MS: Long = 3_600_000
private val AIR = GasMix(21.0, 0.0)

/**
 * This slice replays every Dive as salt water at sea level, the planner's default. Per-Dive water (on the Recording)
 * and altitude (on the site) are a later refinement; ADR 0071 records the approximation.
 */
val LOADINGS_DEFAULT_CONDITIONS: Conditions = Conditions(PlanningWater.salt, 0.0)

/** A logged dive as the loadings functions see it; the sync fills these from the Dive and its Recording. */
data class LoadingDive(
    val startMs: Long,
    val endMs: Long,
    /** Depth samples relative to the dive's start; empty when there is no profile. */
    val samples: List<TissuePoint>,
    /** The first gas; air when the logbook has none. Switch information is not used yet. */
    val gas: GasMix? = null,
    val otuStart: Double? = null,
    val otuEnd: Double? = null,
)

/** What the N2 estimate rests on: always returned, so a display can name its basis. */
data class LoadingBasis(
    val windowHours: Int,
    val divesUsed: Int,
    val divesSkippedNoProfile: Int,
    val lastDiveEndedAt: String?,
)

enum class N2UnavailableReason { stale, no_profile, no_recent_dive }

/** The replay's answer: either the worst compartment and its tissues, or why there is no estimate. */
sealed interface N2Loadings {
    val gfHigh: Double
    val basis: LoadingBasis

    data class Estimate(
        val worstCompartmentPercent: Double,
        override val gfHigh: Double,
        override val basis: LoadingBasis,
        /** The replayed compartments. */
        val tissues: List<Double>,
    ) : N2Loadings

    data class Unavailable(
        val reason: N2UnavailableReason,
        override val gfHigh: Double,
        override val basis: LoadingBasis,
    ) : N2Loadings
}

private fun offGasAtSurface(tissues: List<Double>, minutes: Double, conditions: Conditions): List<Double> =
    advanceTissues(tissues, listOf(TissuePoint(0.0, 0.0), TissuePoint(minutes * 60, 0.0)), AIR, conditions)

/** Replay one dive from the current state; the last sample is brought to the surface at the dive's end. */
private fun replayDive(tissues: List<Double>, dive: LoadingDive, conditions: Conditions): List<Double> {
    val points = dive.samples.sortedBy { it.tSeconds }.toMutableList()
    val durationSeconds = (dive.endMs - dive.startMs) / 1000.0
    if (points.last().tSeconds < durationSeconds) points.add(TissuePoint(durationSeconds, 0.0))
    return advanceTissues(tissues, points, dive.gas ?: AIR, conditions)
}

/**
 * Replay the dives that ended within 72 h, off-gas on air at the surface up to [nowMs], and express the worst
 * compartment as a percent of its tolerated tissue pressure at [gfHigh]. The result carries a reason when the last
 * dive is stale, no usable dive is in the window, or no dive is logged at all.
 */
fun tissuesAfterDives(
    dives: List<LoadingDive>,
    nowMs: Long,
    gfHigh: Double,
    conditions: Conditions = LOADINGS_DEFAULT_CONDITIONS,
): N2Loadings {
    val past = dives.filter { it.endMs <= nowMs }
    val lastEndMs = past.maxOfOrNull { it.endMs }
    val inWindow = past.filter { nowMs - it.endMs <= N2_REPLAY_WINDOW_HOURS * HOUR_MS }
    val used = inWindow.filter { it.samples.isNotEmpty() }.sortedBy { it.startMs }
    val basis = LoadingBasis(
        windowHours = N2_REPLAY_WINDOW_HOURS,
        divesUsed = used.size,
        divesSkippedNoProfile = inWindow.size - used.size,
        lastDiveEndedAt = lastEndMs?.let { isoUtc(it) },
    )
    fun unavailable(reason: N2UnavailableReason) = N2Loadings.Unavailable(reason, gfHigh, basis)

    if (lastEndMs == null) return unavailable(N2UnavailableReason.no_recent_dive)
    if (nowMs - lastEndMs > N2_STALE_AFTER_HOURS * HOUR_MS) return unavailable(N2UnavailableReason.stale)
    if (used.isEmpty()) return unavailable(N2UnavailableReason.no_profile)

    var tissues = surfaceSaturatedTissues(conditions)
    var cursorMs = used.first().startMs
    for (dive in used) {
        val gapMinutes = max(0, dive.startMs - cursorMs) / 60_000.0
        if (gapMinutes > 0) tissues = offGasAtSurface(tissues, gapMinutes, conditions)
        tissues = replayDive(tissues, dive, conditions)
        cursorMs = max(cursorMs, dive.endMs)
    }
    tissues = offGasAtSurface(tissues, max(0, nowMs - cursorMs) / 60_000.0, conditions)

    return N2Loadings.Estimate(
        worstCompartmentPercent = worstCompartmentPercent(tissues, gfHigh, conditions),
        gfHigh = gfHigh,
        basis = basis,
        tissues = tissues,
    )
}

/**
 * CNS percent left after [elapsedMinutes] on the surface. Shearwater, "The CNS oxygen clock": CNS never decreases
 * during a dive; at the surface the elimination half-life is 90 minutes (the source already cited as
 * `SOURCES.noaa_oxygen` in apps/server/src/assessment/rules.ts). Fixed, not a setting (ADR 0071).
 */
fun cnsDecayed(cnsEnd: Double, elapsedMinutes: Double): Double {
    if (elapsedMinutes <= 0) return cnsEnd
    return cnsEnd * 0.5.pow(elapsedMinutes / CNS_HALF_LIFE_MINUTES)
}

/** The OTU of the dives that ended within the last 24 h; null when none in the window carries OTU. */
fun otuLast24h(dives: List<LoadingDive>, nowMs: Long): Double? {
    var total = 0.0
    var found = false
    for (dive in dives) {
        val elapsedMs = nowMs - dive.endMs
        val otuEnd = dive.otuEnd
        if (elapsedMs < 0 || elapsedMs >= OTU_WINDOW_HOURS * HOUR_MS || otuEnd == null) continue
        total += otuEnd - (dive.otuStart ?: 0.0)
        found = true
    }
    return if (found) total else null
}

/** JavaScript's `Date#toISOString`: UTC with milliseconds, which is how the API carries `lastDiveEndedAt`. */
internal fun isoUtc(epochMillis: Long): String {
    val local = Instant.fromEpochMilliseconds(epochMillis).toLocalDateTime(TimeZone.UTC)
    val year = local.year.toString().padStart(4, '0')
    val month = (local.month.ordinal + 1).toString().padStart(2, '0')
    val day = local.day.toString().padStart(2, '0')
    val hour = local.hour.toString().padStart(2, '0')
    val minute = local.minute.toString().padStart(2, '0')
    val second = local.second.toString().padStart(2, '0')
    val millis = (local.nanosecond / 1_000_000).toString().padStart(3, '0')
    return "$year-$month-${day}T$hour:$minute:$second.${millis}Z"
}
