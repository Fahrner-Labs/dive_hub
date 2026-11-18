// Bühlmann ZHL-16C tissue simulation, ported from apps/server/src/planning/tissues.ts (FAH-268).
// Clean-room from the published coefficients and formulas, not from any GPL implementation:
//   - Bühlmann, "Tauchmedizin" (ZHL-16C half-times and a/b values), as tabulated in
//     https://en.wikipedia.org/wiki/B%C3%BChlmann_decompression_algorithm
//   - Haldane (constant depth) and Schreiner (changing depth) equations; water vapour 0.0627 bar
//   - Baker, "Understanding M-values": gradient factors; the tolerated tissue pressure at GF high is
//     Pamb + GF·(M − Pamb), with the M-value M = a + Pamb/b.
package dev.divehub.shared.planning

import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.max

/** One ZHL-16C compartment: [halfTime] in minutes, and its a and b coefficients. */
data class Zhl16cCompartment(val halfTime: Double, val a: Double, val b: Double)

/** The 16 compartments (ZHL-16C, compartment 1b: 5.0 min), verbatim from the server's table. */
val ZHL16C_COMPARTMENTS: List<Zhl16cCompartment> = listOf(
    Zhl16cCompartment(5.0, 1.1696, 0.5578),
    Zhl16cCompartment(8.0, 1.0, 0.6514),
    Zhl16cCompartment(12.5, 0.8618, 0.7222),
    Zhl16cCompartment(18.5, 0.7562, 0.7825),
    Zhl16cCompartment(27.0, 0.62, 0.8126),
    Zhl16cCompartment(38.3, 0.5043, 0.8434),
    Zhl16cCompartment(54.3, 0.441, 0.8693),
    Zhl16cCompartment(77.0, 0.4, 0.891),
    Zhl16cCompartment(109.0, 0.375, 0.9092),
    Zhl16cCompartment(146.0, 0.35, 0.9222),
    Zhl16cCompartment(187.0, 0.3295, 0.9319),
    Zhl16cCompartment(239.0, 0.3065, 0.9403),
    Zhl16cCompartment(305.0, 0.2835, 0.9477),
    Zhl16cCompartment(390.0, 0.261, 0.9544),
    Zhl16cCompartment(498.0, 0.248, 0.9602),
    Zhl16cCompartment(635.0, 0.2327, 0.9653),
)

const val WATER_VAPOUR_BAR = 0.0627

/** One point of a dive's depth profile, its offset from the dive's start in seconds. */
data class TissuePoint(val tSeconds: Double, val depthM: Double)

/** Clean tissues: saturated with air at the surface, where every Bühlmann simulation in Dive Hub starts. */
fun surfaceSaturatedTissues(conditions: Conditions): List<Double> {
    val surface = surfacePressureBar(conditions.altitudeM)
    return ZHL16C_COMPARTMENTS.map { 0.79 * (surface - WATER_VAPOUR_BAR) }
}

/**
 * One Schreiner step for every compartment: the inspired inert-gas pressure changes linearly at [rateBarPerMin]
 * over [minutes]. With a rate of zero it is Haldane's equation, as Ndl steps a constant depth.
 */
fun schreinerStep(
    tissues: List<Double>,
    inspiredBar: Double,
    rateBarPerMin: Double,
    minutes: Double,
): List<Double> = ZHL16C_COMPARTMENTS.mapIndexed { i, compartment ->
    val k = ln(2.0) / compartment.halfTime
    inspiredBar + rateBarPerMin * (minutes - 1 / k) -
        (inspiredBar - tissues[i] - rateBarPerMin / k) * exp(-k * minutes)
}

/**
 * Advance the 16 compartments over a depth/time series breathing one gas. Depth moves linearly between points;
 * before the first point the dive is at the surface, and a duplicate timestamp changes depth without time.
 */
fun advanceTissues(tissues: List<Double>, points: List<TissuePoint>, gas: GasMix, conditions: Conditions): List<Double> {
    val fN2 = (100 - gas.o2 - gas.he) / 100
    var state = tissues.toList()
    var previousSeconds = 0.0
    var previousDepthM = 0.0
    for (point in points.sortedBy { it.tSeconds }) {
        val seconds = max(previousSeconds, point.tSeconds)
        val minutes = (seconds - previousSeconds) / 60
        if (minutes > 0) {
            val inspired = (ambientPressureBar(previousDepthM, conditions) - WATER_VAPOUR_BAR) * fN2
            val nextInspired = (ambientPressureBar(point.depthM, conditions) - WATER_VAPOUR_BAR) * fN2
            state = schreinerStep(state, inspired, (nextInspired - inspired) / minutes, minutes)
        }
        previousSeconds = seconds
        previousDepthM = point.depthM
    }
    return state
}

/**
 * The worst compartment as a percent of its tolerated tissue pressure at the surface and GF high: 100 % is the
 * gradient-factor limit where Ndl would call for a stop.
 */
fun worstCompartmentPercent(tissues: List<Double>, gfHigh: Double, conditions: Conditions): Double {
    val surface = surfacePressureBar(conditions.altitudeM)
    val gf = gfHigh / 100
    var worst = 0.0
    ZHL16C_COMPARTMENTS.forEachIndexed { i, compartment ->
        val mValue = compartment.a + surface / compartment.b
        val tolerated = surface + gf * (mValue - surface)
        worst = max(worst, (tissues[i] / tolerated) * 100)
    }
    return worst
}
