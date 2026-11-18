// SAC on a Dive, ported from apps/server/src/dives/calc/sac.ts (FAH-268; ADR 0045, ADR 0033).
// Computed from the Dive's Cylinders, never stored. The kernel doubles are unrounded; the route's two-place view
// is what [sacOfDive] answers, and the golden vectors pin both.
package dev.divehub.shared.planning

import kotlin.math.floor

/** Below this a Dive is too short for its SAC to say anything (ADR 0033). */
const val MIN_SAC_SECONDS = 15.0 * 60

data class SacDive(val durationSeconds: Double, val avgDepthM: Double?)

data class SacCylinder(
    val volumeL: Double?,
    val startPressureBar: Double?,
    val endPressureBar: Double?,
    val gas: GasMix?,
)

data class Sac(val litresPerMinute: Double, val barPerMinute: Double?)

/** The unrounded kernel values; [surfaceMinutes] is the Dive's time at the surface's pressure. */
data class SacValues(val litresPerMinute: Double, val barPerMinute: Double?, val surfaceMinutes: Double)

/** Why a Dive with Cylinders has no SAC. */
enum class SacMissing { too_short, no_average_depth, cylinder_incomplete }

data class SacOutcome<T>(val sac: T?, val missing: SacMissing?)

/** The SAC's doubles before the route's rounding. */
fun sacValues(dive: SacDive, cylinders: List<SacCylinder>): SacOutcome<SacValues> {
    if (cylinders.isEmpty()) return SacOutcome(null, null)
    if (dive.durationSeconds < MIN_SAC_SECONDS) return SacOutcome(null, SacMissing.too_short)
    if (dive.avgDepthM == null) return SacOutcome(null, SacMissing.no_average_depth)
    if (cylinders.any { it.volumeL == null || it.startPressureBar == null || it.endPressureBar == null }) {
        return SacOutcome(null, SacMissing.cylinder_incomplete)
    }
    // Minutes at the surface's pressure: the time times the ambient pressure at the average depth (ADR 0033).
    val surfaceMinutes = (dive.durationSeconds / 60) * (dive.avgDepthM / 10 + 1)
    val litres = cylinders.sumOf { cylinder ->
        surfaceLitres(cylinder.volumeL!!, cylinder.startPressureBar!!, cylinder.gas) -
            surfaceLitres(cylinder.volumeL, cylinder.endPressureBar!!, cylinder.gas)
    }
    val only = cylinders.first()
    val values = SacValues(
        litresPerMinute = litres / surfaceMinutes,
        barPerMinute = if (cylinders.size == 1) (only.startPressureBar!! - only.endPressureBar!!) / surfaceMinutes else null,
        surfaceMinutes = surfaceMinutes,
    )
    return SacOutcome(values, null)
}

/** The route's answer: [sacValues] rounded to two places, without [SacValues.surfaceMinutes]. */
fun sacOfDive(dive: SacDive, cylinders: List<SacCylinder>): SacOutcome<Sac> {
    val values = sacValues(dive, cylinders)
    val sac = values.sac ?: return SacOutcome(null, values.missing)
    return SacOutcome(
        Sac(roundTo(sac.litresPerMinute, 2), sac.barPerMinute?.let { roundTo(it, 2) }),
        null,
    )
}

/** JavaScript's `Math.round`: ties go towards positive infinity. */
internal fun jsRound(value: Double): Double = floor(value + 0.5)

internal fun roundTo(value: Double, places: Int): Double {
    val factor = 10.0.powInt(places)
    return jsRound(value * factor) / factor
}

private fun Double.powInt(exponent: Int): Double {
    var result = 1.0
    repeat(exponent) { result *= this }
    return result
}
