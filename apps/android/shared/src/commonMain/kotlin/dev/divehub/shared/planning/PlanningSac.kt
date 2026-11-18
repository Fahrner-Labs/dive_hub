// The planning SAC, ported from apps/server/src/planning/planning-sac.ts (FAH-270; ADR 0033).
// A plan needs a bad day's consumption, so the 85th percentile of the SAC of a Diver's last 20 Dives that have one,
// not the median. Fewer than 5 and the default stands, said so.
package dev.divehub.shared.planning

import kotlin.math.floor
import kotlin.math.min

const val PLANNING_SAC_DIVES = 20
const val PLANNING_SAC_MIN_DIVES = 5
const val PLANNING_SAC_PERCENTILE = 0.85
const val DEFAULT_PLANNING_SAC_LPM = 20.0

/** One of the Diver's Dives as the planning SAC reads it; a Dive without a SAC has a null [sacLpm]. */
data class PlanningSacDive(val sacLpm: Double?)

enum class PlanningSacSource { logbook, default }

data class PlanningSacResult(val lpm: Double, val basedOn: Int, val source: PlanningSacSource)

/** [dives] newest first; the answer is rounded to 0.1 like the route's. */
fun planningSac(dives: List<PlanningSacDive>): PlanningSacResult {
    val sacs = dives.mapNotNull { it.sacLpm }.take(PLANNING_SAC_DIVES)
    if (sacs.size < PLANNING_SAC_MIN_DIVES) {
        return PlanningSacResult(DEFAULT_PLANNING_SAC_LPM, sacs.size, PlanningSacSource.default)
    }
    val sorted = sacs.sorted()
    // Linear between ranks (the common "inclusive" percentile).
    val rank = PLANNING_SAC_PERCENTILE * (sorted.size - 1) + 1e-9
    val low = floor(rank).toInt()
    val high = min(low + 1, sorted.size - 1)
    val value = sorted[low] + (rank - low) * (sorted[high] - sorted[low])
    return PlanningSacResult(roundTo(value, 1), sorted.size, PlanningSacSource.logbook)
}
