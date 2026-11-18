// No-decompression limit, ported from apps/server/src/planning/ndl.ts (FAH-268).
// Bühlmann ZHL-16C for nitrogen with gradient factors; clean-room from the published coefficients and formulas.
// The limit is the actual one (ADR 0032): the time from leaving the surface until any compartment's tolerated
// pressure at GF high rises above the surface pressure, the descent included, in whole minutes rounded down.
// No credit for the ascent. One minute is taken off (ADR 0046), the margin the reference implementations carry.
// This is the kernel: the routes' gas restriction (air and nitrox 21–40 %, no helium, at most 40 m, the MOD) stays
// in the service layer.
package dev.divehub.shared.planning

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/** Descent rate, metres per minute (ADR 0032). */
const val DESCENT_RATE_M_PER_MIN = 18.0
private const val STEP_SECONDS = 1.0
/** Beyond this the depth is not limiting and the answer is infinity. */
private const val SEARCH_LIMIT_MINUTES = 1000.0
private const val EPSILON = 1e-9

data class NdlInput(
    val gas: GasMix,
    val depthM: Double,
    /** Gradient factor high in percent (for a no-stop dive only this one matters). */
    val gfHigh: Double,
    val conditions: Conditions,
    /** The 16 compartment pressures to start from; default clean tissues saturated with air at the surface. */
    val initialTissues: List<Double>? = null,
)

/** Whole minutes from leaving the surface until the first stop would be needed, less one; infinity when it never is. */
fun ndlMinutes(input: NdlInput): Double {
    val gas = input.gas
    val conditions = input.conditions
    val depthM = input.depthM
    val gf = input.gfHigh / 100
    val fN2 = (100 - gas.o2 - gas.he) / 100
    val surface = surfacePressureBar(conditions.altitudeM)
    val barPerMetre = 1 / metresPerBar(conditions)
    val descentSeconds = (depthM / DESCENT_RATE_M_PER_MIN) * 60
    // The Diver's residual nitrogen when the planner seeds it, else clean tissues saturated with air at the surface.
    var tissue = input.initialTissues?.toList() ?: surfaceSaturatedTissues(conditions)
    val stepMinutes = STEP_SECONDS / 60
    val rateBarPerMin = DESCENT_RATE_M_PER_MIN * barPerMetre

    var t = 0.0
    while (t < SEARCH_LIMIT_MINUTES * 60) {
        val descending = t < descentSeconds - EPSILON
        val dt = (if (descending) min(STEP_SECONDS, descentSeconds - t) else STEP_SECONDS) / 60
        val depthNow = if (descending) min(depthM, (t / 60) * DESCENT_RATE_M_PER_MIN) else depthM
        val ambient = ambientPressureBar(depthNow, conditions)
        val inspired = (ambient - WATER_VAPOUR_BAR) * fN2
        val rate = if (descending) rateBarPerMin * fN2 else 0.0
        // Schreiner; with rate 0 it is Haldane's.
        tissue = schreinerStep(tissue, inspired, rate, dt)
        t += dt * 60
        if (t >= descentSeconds - EPSILON) {
            // Tolerated ambient pressure per compartment at GF high; a stop is needed once one exceeds the surface's.
            for (i in ZHL16C_COMPARTMENTS.indices) {
                val compartment = ZHL16C_COMPARTMENTS[i]
                val tolerated = (tissue[i] - gf * compartment.a) / (gf / compartment.b - gf + 1)
                if (tolerated > surface + EPSILON) return max(0.0, floor(t / 60) - 1)
            }
        }
    }
    return Double.POSITIVE_INFINITY
}
