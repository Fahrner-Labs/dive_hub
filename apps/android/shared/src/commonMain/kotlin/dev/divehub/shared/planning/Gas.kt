// Gas numbers for a planned dive, ported from apps/server/src/planning/gas.ts (FAH-268).
// Clean-room from the formulas in docs/research/2026-10-06-gas-and-ndl-tools.md; no GPL code.
package dev.divehub.shared.planning

import kotlin.math.floor
import kotlin.math.min
import kotlin.math.pow

/** The waters the planner knows; the names match the server's `PlanningWater`. */
enum class PlanningWater { fresh, salt, en13319 }

data class GasMix(val o2: Double, val he: Double)

data class Conditions(
    val water: PlanningWater,
    /** Altitude of the surface in metres above sea level. */
    val altitudeM: Double,
    /** Overrides the density of [water] in kg/m³. */
    val densityKgM3: Double? = null,
)

private val DENSITY = mapOf(
    PlanningWater.fresh to 1000.0,
    PlanningWater.salt to 1025.0,
    PlanningWater.en13319 to 1020.0,
)
const val GRAVITY = 9.80665
const val SEA_LEVEL_BAR = 1.01325

/** Surface pressure in bar from the standard atmosphere. */
fun surfacePressureBar(altitudeM: Double): Double = SEA_LEVEL_BAR * (1 - 2.25577e-5 * altitudeM).pow(5.25588)

/** Metres of water per bar of pressure. */
fun metresPerBar(conditions: Conditions): Double =
    1e5 / ((conditions.densityKgM3 ?: DENSITY.getValue(conditions.water)) * GRAVITY)

/** Maximum operating depth in metres: where the mix reaches the ppO2 limit (bar). */
fun modMetres(gas: GasMix, ppO2Limit: Double, conditions: Conditions): Double {
    val ambientBar = ppO2Limit / (gas.o2 / 100)
    return (ambientBar - surfacePressureBar(conditions.altitudeM)) * metresPerBar(conditions)
}

/** Ambient pressure in bar at a depth in metres. */
fun ambientPressureBar(depthM: Double, conditions: Conditions): Double =
    surfacePressureBar(conditions.altitudeM) + depthM / metresPerBar(conditions)

/** Depth at which an ambient pressure is reached. */
private fun depthOfPressure(bar: Double, conditions: Conditions): Double =
    (bar - surfacePressureBar(conditions.altitudeM)) * metresPerBar(conditions)

/** The richest oxygen percent (whole, rounded down) whose ppO2 stays within the limit at the depth; at most 100. */
fun bestMixPercent(depthM: Double, ppO2Limit: Double, conditions: Conditions): Int =
    min(100, floor((ppO2Limit / ambientPressureBar(depthM, conditions)) * 100).toInt())

/** Equivalent air depth: the depth on air with the same nitrogen pressure (helium counts as no nitrogen). */
fun eadMetres(gas: GasMix, depthM: Double, conditions: Conditions): Double {
    val n2 = (100 - gas.o2 - gas.he) / 100
    return depthOfPressure((ambientPressureBar(depthM, conditions) * n2) / 0.79, conditions)
}

/** Equivalent narcotic depth. Oxygen counts as narcotic by default; only helium is not. */
fun endMetres(gas: GasMix, depthM: Double, conditions: Conditions, oxygenNarcotic: Boolean): Double {
    if (!oxygenNarcotic) return eadMetres(gas, depthM, conditions)
    return depthOfPressure(ambientPressureBar(depthM, conditions) * (1 - gas.he / 100), conditions)
}

/** Below 18 % oxygen at the surface a mix is hypoxic. */
fun isHypoxic(gas: GasMix): Boolean = gas.o2 < 18
