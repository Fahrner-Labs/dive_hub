package dev.divehub.shared.tools

// The units the tools take and show (ADR 0014, ADR 0073): the values are SI at the kernel's edge, and the client
// converts only for display and back. Kept in shared/ so iOS uses the same arithmetic and the same input ranges.
const val FEET_PER_METRE = 1 / 0.3048
const val PSI_PER_BAR = 14.5038

fun depthIn(metres: Double, imperial: Boolean): Double = if (imperial) metres * FEET_PER_METRE else metres

fun depthFromDisplay(value: Double, imperial: Boolean): Double = if (imperial) value / FEET_PER_METRE else value

fun pressureIn(bar: Double, imperial: Boolean): Double = if (imperial) bar * PSI_PER_BAR else bar

fun pressureFromDisplay(value: Double, imperial: Boolean): Double = if (imperial) value / PSI_PER_BAR else value

/** The ranges the API's query schemas accept; the screen refuses the same ones. */
object ToolRanges {
    val O2 = 1.0..100.0
    val HELIUM = 0.0..100.0
    val DEPTH_M = 1.0..300.0
    val VOLUME_L = 1.0..60.0
    val PRESSURE_BAR = 1.0..350.0
    val SAC_LPM = 1.0..100.0
    val GF_HIGH = 20.0..100.0
    val ALTITUDE_M = 0.0..5000.0
    val PPO2 = 1.0..2.0
    val RULE_BAR = 0.0..350.0
    val DESCENT_RATE_MPM = 3.0..30.0
    val ASCENT_RATE_MPM = 3.0..18.0
}
