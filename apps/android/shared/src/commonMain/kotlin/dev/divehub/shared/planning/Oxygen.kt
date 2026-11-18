// Oxygen exposure, ported from apps/server/src/planning/oxygen.ts (FAH-268). Clean-room from published tables and formulas.
package dev.divehub.shared.planning

import kotlin.math.pow

/**
 * NOAA Diving Manual (4th ed.) single-exposure limits: ppO2 in bar to minutes, as printed by Shearwater
 * ("Shearwater and the CNS oxygen clock"). Between two rows the limit is linear.
 */
private val NOAA_ROWS: List<Pair<Double, Double>> = listOf(
    0.6 to 720.0,
    0.7 to 570.0,
    0.8 to 450.0,
    0.9 to 360.0,
    1.0 to 300.0,
    1.1 to 240.0,
    1.2 to 210.0,
    1.3 to 180.0,
    1.4 to 150.0,
    1.5 to 120.0,
    1.6 to 45.0,
)

/** Minutes one exposure may last at a ppO2: 0 above 1.6 bar, unlimited below the table's first row. */
fun oxygenLimitMinutes(ppO2: Double): Double {
    val first = NOAA_ROWS.first()
    val last = NOAA_ROWS.last()
    if (ppO2 > last.first + 1e-9) return 0.0
    if (ppO2 < first.first - 1e-9) return Double.POSITIVE_INFINITY
    for (i in 1 until NOAA_ROWS.size) {
        val (p1, m1) = NOAA_ROWS[i]
        val (p0, m0) = NOAA_ROWS[i - 1]
        if (ppO2 <= p1 + 1e-9) return m0 + ((ppO2 - p0) / (p1 - p0)) * (m1 - m0)
    }
    return last.second
}

/** Oxygen toxicity units (Hamilton, REPEX, SPUMS J 27(1)): minutes × ((ppO2 − 0.5) / 0.5)^0.83. */
fun otu(ppO2: Double, minutes: Double): Double {
    if (ppO2 <= 0.5) return 0.0
    return minutes * ((ppO2 - 0.5) / 0.5).pow(0.83)
}
