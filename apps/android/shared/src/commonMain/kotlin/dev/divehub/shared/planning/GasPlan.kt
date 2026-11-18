// A gas plan, ported from apps/server/src/planning/gas-plan.ts (FAH-270; ADR 0033, slice 22).
// The litres of each stretch of a planned dive, and per Diver the pressures that follow from their Cylinder and SAC —
// end, rock bottom, the ascent pressure of the chosen rule. Limits from the Diver's own input and settings, not advice;
// the dive computer and training govern. Air and nitrox 21 to 40 %, at most 40 m. This is the kernel: the route's
// rounding stays in the service shell, checked by the server runner.
package dev.divehub.shared.planning

import kotlin.math.abs
import kotlin.math.min

/** Deeper than this is never answered (ADR 0032). */
const val MAX_DEPTH_M = 40.0
const val ASCENT_RATE_M_PER_MIN = 9.0
const val SAFETY_STOP_DEPTH_M = 5.0
const val SAFETY_STOP_MINUTES = 3.0

/** Never ascend below this with the default rule (ADR 0033). */
const val MIN_ASCENT_BAR = 50.0
const val ROCK_BOTTOM_STRESS = 2.0
const val ROCK_BOTTOM_PROBLEM_MINUTES = 1.0
const val ROCK_BOTTOM_MARGIN_BAR = 10.0
const val ROCK_BOTTOM_EPSILON = 1e-6

/** Turn rules share the supply out and back; the share of the smaller supply spent on the way out. */
enum class TurnRule(val share: Double) {
    halves(1.0 / 2),
    thirds(1.0 / 3),
    sixths(1.0 / 6),
}

sealed interface GasRule {
    object RockBottom : GasRule

    /** Finish the dive with this pressure left. */
    data class Reserve(val bar: Double) : GasRule

    /** Start the ascent at this pressure. */
    data class AscentPressure(val bar: Double) : GasRule

    data class Turn(val rule: TurnRule) : GasRule
}

data class GasPlanDiver(
    val name: String,
    val volumeL: Double,
    val startPressureBar: Double,
    val sacLpm: Double,
)

data class GasPlanInput(
    val gas: GasMix,
    val conditions: Conditions,
    /** Depth and the minutes spent there after arriving. */
    val levels: List<GasPlanLevel>,
    val divers: List<GasPlanDiver>,
    val rule: GasRule,
    val ppO2Contingency: Double,
    /** Metres a minute between levels and to the surface; 18 down and 9 up when left out. */
    val descentRateMPerMin: Double? = null,
    val ascentRateMPerMin: Double? = null,
)

data class GasPlanLevel(val depthM: Double, val minutes: Double)

enum class SegmentKind { descent, level, ascent, safety_stop }

data class PlanSegment(
    val kind: SegmentKind,
    /** Where it starts and where it ends (the same for a level and the stop); in the order they are dived. */
    val fromDepthM: Double,
    val depthM: Double,
    val minutes: Double,
    /** Mean ambient pressure over the stretch. */
    val ambientBar: Double,
)

data class RockBottomPoint(val depthM: Double, val pressureBar: Double)

data class DiverPlan(
    val name: String,
    /** Litres at the surface's pressure over the whole plan. */
    val litres: Double,
    val endPressureBar: Double,
    /** At every level: the pressure that covers two Divers' trouble there and a direct ascent, plus the margin. */
    val rockBottom: List<RockBottomPoint>,
    /** The pressure at which this Diver must start up, by the chosen rule. */
    val ascentPressureBar: Double,
    /** Where this Diver turns, under halves, thirds and sixths; otherwise null. */
    val turnPressureBar: Double?,
    /** The ascent pressure is below rock bottom: the rule leaves less than two Divers' trouble needs. */
    val belowRockBottom: Boolean,
    /** The pressure at the end of the last level, when the plan starts up. */
    val atAscentStartBar: Double,
    /** The pressure at the start and at the end of every stretch: one more than there are stretches. */
    val pressuresBar: List<Double>,
    val marginBar: Double,
    /** Minutes more at the last level before the ascent pressure; negative when the plan stays too long. */
    val marginMinutes: Double,
    val controlling: Boolean,
)

enum class GasPlanLimit { depth, mod, gas }

sealed interface GasPlan {
    data class Ok(
        val runtimeMinutes: Double,
        val segments: List<PlanSegment>,
        val divers: List<DiverPlan>,
        /** The Diver who runs out of margin first: the plan follows them. */
        val controllingIndex: Int,
        val fits: Boolean,
        /** The run time at which to leave the last level for the controlling Diver to start up at their ascent pressure. */
        val leaveLastLevelAtMinute: Double,
    ) : GasPlan

    data class OutsideLimits(val reason: GasPlanLimit, val levelIndex: Int?) : GasPlan
}

fun planGas(input: GasPlanInput): GasPlan {
    val gas = input.gas
    val conditions = input.conditions
    val levels = input.levels
    val divers = input.divers
    if (gas.he != 0.0 || gas.o2 < 21 || gas.o2 > 40) return GasPlan.OutsideLimits(GasPlanLimit.gas, null)
    val mod = modMetres(gas, input.ppO2Contingency, conditions)
    levels.forEachIndexed { i, level ->
        if (level.depthM > MAX_DEPTH_M) return GasPlan.OutsideLimits(GasPlanLimit.depth, i)
        if (level.depthM > mod) return GasPlan.OutsideLimits(GasPlanLimit.mod, i)
    }

    fun ambient(depthM: Double): Double = ambientPressureBar(depthM, conditions)
    val descentRate = input.descentRateMPerMin ?: DESCENT_RATE_M_PER_MIN
    val ascentRate = input.ascentRateMPerMin ?: ASCENT_RATE_M_PER_MIN
    val segments = mutableListOf<PlanSegment>()
    var at = 0.0
    fun move(toM: Double) {
        if (toM == at) return
        val down = toM > at
        segments += PlanSegment(
            kind = if (down) SegmentKind.descent else SegmentKind.ascent,
            fromDepthM = at,
            depthM = toM,
            minutes = abs(toM - at) / (if (down) descentRate else ascentRate),
            ambientBar = (ambient(at) + ambient(toM)) / 2,
        )
        at = toM
    }
    fun stay(kind: SegmentKind, minutes: Double) {
        segments += PlanSegment(kind, at, at, minutes, ambient(at))
    }
    for (level in levels) {
        move(level.depthM)
        stay(SegmentKind.level, level.minutes)
    }
    if (at > SAFETY_STOP_DEPTH_M) {
        move(SAFETY_STOP_DEPTH_M)
        stay(SegmentKind.safety_stop, SAFETY_STOP_MINUTES)
    }
    move(0.0)

    // Litres of one Diver per litre-a-minute of SAC: all of it, and the ascent with its safety stop.
    val perSac = segments.sumOf { it.minutes * it.ambientBar }
    val lastLevel = segments.indexOfLast { it.kind == SegmentKind.level }
    val finalStretch = segments.subList(lastLevel + 1, segments.size)
    val ascentPerSac = finalStretch.sumOf { it.minutes * it.ambientBar }
    val minutesToLeave = segments.subList(0, segments.size - finalStretch.size).sumOf { it.minutes }
    val lastDepthM = levels.last().depthM
    // Sharing gas, a group comes up no faster than it planned to, and never faster than the usual rate.
    val troubleRate = min(ascentRate, ASCENT_RATE_M_PER_MIN)
    fun rockBottomPerSac(depthM: Double): Double =
        ROCK_BOTTOM_PROBLEM_MINUTES * ambient(depthM) +
            (depthM / troubleRate) * ((ambient(depthM) + ambient(0.0)) / 2)

    val startLitres = divers.map { surfaceLitres(it.volumeL, it.startPressureBar, gas) }
    // Turn rules are matched by litres: the smaller supply sets what is spent on the way out.
    val turnLitres = when (val rule = input.rule) {
        is GasRule.Turn -> rule.rule.share * startLitres.min()
        else -> 0.0
    }

    val plans = divers.mapIndexed { i, d ->
        val others = divers.filterIndexed { j, _ -> j != i }.map { it.sacLpm }
        val buddySac = if (others.isNotEmpty()) others.max() else d.sacLpm
        val litres = d.sacLpm * perSac
        val rockBottom = levels.map { level ->
            RockBottomPoint(
                level.depthM,
                pressureForLitres(
                    d.volumeL,
                    (d.sacLpm + buddySac) * ROCK_BOTTOM_STRESS * rockBottomPerSac(level.depthM),
                    gas,
                ) + ROCK_BOTTOM_MARGIN_BAR,
            )
        }
        val highestRockBottom = rockBottom.maxOf { it.pressureBar }
        val ascentLitres = d.sacLpm * ascentPerSac
        val turnPressureBar = if (input.rule is GasRule.Turn) {
            pressureForLitres(d.volumeL, startLitres[i] - turnLitres, gas)
        } else {
            null
        }
        val ascentPressureBar = when (val rule = input.rule) {
            GasRule.RockBottom -> maxOf(MIN_ASCENT_BAR, highestRockBottom)
            is GasRule.AscentPressure -> rule.bar
            is GasRule.Reserve -> pressureForLitres(
                d.volumeL,
                surfaceLitres(d.volumeL, rule.bar, gas) + ascentLitres,
                gas,
            )
            // Out and back spends twice the turn litres; what is left is the reserve, and the ascent comes on top.
            is GasRule.Turn -> pressureForLitres(
                d.volumeL,
                startLitres[i] - 2 * turnLitres + ascentLitres,
                gas,
            )
        }
        // In litres, which go negative when the plan overdraws the Cylinder (a pressure would stop at 0).
        val atAscentStartLitres = startLitres[i] - (litres - ascentLitres)
        val atAscentStartBar = pressureForLitres(d.volumeL, atAscentStartLitres, gas)
        val marginLitres = atAscentStartLitres - surfaceLitres(d.volumeL, ascentPressureBar, gas)
        var spent = 0.0
        val pressuresBar = buildList {
            add(d.startPressureBar)
            for (segment in segments) {
                spent += d.sacLpm * segment.minutes * segment.ambientBar
                add(pressureForLitres(d.volumeL, startLitres[i] - spent, gas))
            }
        }
        DiverPlan(
            name = d.name,
            litres = litres,
            pressuresBar = pressuresBar,
            endPressureBar = pressureForLitres(d.volumeL, startLitres[i] - litres, gas),
            rockBottom = rockBottom,
            ascentPressureBar = ascentPressureBar,
            turnPressureBar = turnPressureBar,
            belowRockBottom = ascentPressureBar < highestRockBottom - ROCK_BOTTOM_EPSILON,
            atAscentStartBar = atAscentStartBar,
            marginBar = atAscentStartBar - ascentPressureBar,
            marginMinutes = marginLitres / (d.sacLpm * ambient(lastDepthM)),
            controlling = false,
        )
    }
    var controllingIndex = 0
    for (i in plans.indices) {
        if (plans[i].marginMinutes < plans[controllingIndex].marginMinutes) controllingIndex = i
    }
    val controlling = plans[controllingIndex].copy(controlling = true)
    val withControlling = plans.toMutableList().also { it[controllingIndex] = controlling }
    val leftover = controlling.marginMinutes
    return GasPlan.Ok(
        runtimeMinutes = segments.sumOf { it.minutes },
        segments = segments,
        divers = withControlling,
        controllingIndex = controllingIndex,
        fits = leftover >= 0,
        leaveLastLevelAtMinute = minutesToLeave + leftover,
    )
}
