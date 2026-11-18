// Real gas in a Cylinder, ported from apps/server/src/dives/calc/real-gas.ts (FAH-268; ADR 0045, ADR 0033).
// A full cylinder holds less than pressure × volume says, about 3 % less at 200 bar and 10 % at 300 bar for air.
// The compressibility factor Z is a cubic in the pressure per gas, Z = 1 + a·p + b·p² + c·p³, mixed by the gas's
// fractions. The coefficients are the server's own least-squares fit through NIST's isotherms at 20 °C from 1 to
// 351 bar (scripts/fit-compressibility.mjs).
package dev.divehub.shared.planning

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

private data class Cubic(val a: Double, val b: Double, val c: Double)

private val O2 = Cubic(-7.698130e-4, 2.362918e-6, -9.607164e-11)
private val N2 = Cubic(-2.850092e-4, 3.167268e-6, -2.215000e-9)
private val HE = Cubic(4.869641e-4, -6.053372e-8, 2.456439e-11)

/** The fit's range: above it the cubic is not to be trusted, so Z stays what it is here. */
const val MAX_BAR = 351.0
private val AIR = GasMix(21.0, 0.0)

private fun minusOne(cubic: Cubic, bar: Double): Double = cubic.a * bar + cubic.b * bar.pow(2) + cubic.c * bar.pow(3)

/** The compressibility factor of a gas (air when unknown) at a pressure in bar, at 20 °C. */
fun compressibility(bar: Double, gas: GasMix?): Double {
    val mix = gas ?: AIR
    val p = min(max(bar, 0.0), MAX_BAR)
    return 1 + (mix.o2 * minusOne(O2, p) + mix.he * minusOne(HE, p) + (100 - mix.o2 - mix.he) * minusOne(N2, p)) / 100
}

/** The litres at the surface's pressure that a cylinder of [volumeL] holds at [bar]. */
fun surfaceLitres(volumeL: Double, bar: Double, gas: GasMix?): Double = (volumeL * bar) / compressibility(bar, gas)

/** The pressure at which a cylinder of [volumeL] holds [litres] (the inverse of [surfaceLitres], found by bisection). */
fun pressureForLitres(volumeL: Double, litres: Double, gas: GasMix?): Double {
    if (litres <= 0) return 0.0
    if (litres >= surfaceLitres(volumeL, MAX_BAR, gas)) return MAX_BAR
    var low = 0.0
    var high = MAX_BAR
    for (i in 0 until 60) {
        val mid = (low + high) / 2
        if (surfaceLitres(volumeL, mid, gas) < litres) low = mid else high = mid
    }
    return (low + high) / 2
}
