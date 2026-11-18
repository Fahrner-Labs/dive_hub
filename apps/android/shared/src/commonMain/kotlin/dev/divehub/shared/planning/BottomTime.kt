// Bottom time for a planned dive, ported from apps/server/src/planning/bottom-time.ts (FAH-270; ADR 0032, slice 21):
// the shortest of the no-decompression limit, the oxygen limit and the gas down to the ascent pressure of a gas rule
// (ADR 0033), and which one binds — every limit that floors to the same whole minute. An estimate from the Diver's own
// input and settings, not advice; the dive computer and training govern. Air and nitrox 21 to 40 % only, no-stop only,
// at most 40 m. This is the kernel: the route's rounding stays in the service shell.
package dev.divehub.shared.planning

import kotlin.math.floor
import kotlin.math.max

data class BottomTimeCylinder(val volumeL: Double, val startPressureBar: Double)

data class PpO2Limits(val working: Double, val contingency: Double)

data class BottomTimeInput(
    val gas: GasMix,
    val depthM: Double,
    val gfHigh: Double,
    val conditions: Conditions,
    /** Null: no gas limit. */
    val cylinder: BottomTimeCylinder?,
    /** Litres a minute at the surface. */
    val sacLpm: Double,
    /** Where the gas ends: the ascent pressure of this rule (ADR 0033). */
    val rule: GasRule,
    val ppO2: PpO2Limits,
    /** The Diver's residual tissue pressures at the start; default clean tissues (ADR 0071, FAH-241). */
    val initialTissues: List<Double>? = null,
)

enum class BottomTimeLimit { ndl, oxygen, gas }

enum class BottomTimeOutside { depth, mod, gas }

data class BottomTimeLimits(
    val ndl: Double,
    val oxygen: Double,
    /** Minutes; null without a Cylinder. */
    val gas: Double?,
)

sealed interface BottomTime {
    data class Ok(
        /** Whole minutes from leaving the surface to leaving the depth. */
        val minutes: Double,
        /** The limits that equal [minutes]: more than one when they bind together. */
        val binding: List<BottomTimeLimit>,
        val limits: BottomTimeLimits,
        val otu: Double,
        val ppO2AtDepth: Double,
        /** The pressure the gas limit ends at; null without a Cylinder. */
        val ascentPressureBar: Double?,
        /** Above the working ppO2 limit, within the contingency one. */
        val ppO2AboveWorking: Boolean,
    ) : BottomTime

    data class OutsideLimits(val reason: BottomTimeOutside, val modM: Double?) : BottomTime
}

fun bottomTime(input: BottomTimeInput): BottomTime {
    val gas = input.gas
    val depthM = input.depthM
    val conditions = input.conditions
    val supported = gas.he == 0.0 && gas.o2 >= 21 && gas.o2 <= 40
    val modM = if (supported) modMetres(gas, input.ppO2.contingency, conditions) else null
    if (!supported) return BottomTime.OutsideLimits(BottomTimeOutside.gas, modM)
    if (depthM > MAX_DEPTH_M) return BottomTime.OutsideLimits(BottomTimeOutside.depth, modM)
    if (depthM > modM!!) return BottomTime.OutsideLimits(BottomTimeOutside.mod, modM)

    val ambientBar = ambientPressureBar(depthM, conditions)
    val ppO2AtDepth = (gas.o2 / 100) * ambientBar

    val ndl = ndlMinutes(
        NdlInput(
            gas = gas,
            depthM = depthM,
            gfHigh = input.gfHigh,
            conditions = conditions,
            initialTissues = input.initialTissues,
        ),
    )
    val oxygen = oxygenLimitMinutes(ppO2AtDepth)

    // The gas limit is the minute to leave the depth by in a plan of one Diver at this depth (ADR 0033).
    var gasLimit: Double? = null
    var ascentPressureBar: Double? = null
    val cylinder = input.cylinder
    if (cylinder != null) {
        val plan = planGas(
            GasPlanInput(
                gas = gas,
                conditions = conditions,
                levels = listOf(GasPlanLevel(depthM, 0.0)),
                rule = input.rule,
                ppO2Contingency = input.ppO2.contingency,
                divers = listOf(GasPlanDiver("", cylinder.volumeL, cylinder.startPressureBar, input.sacLpm)),
            ),
        )
        if (plan is GasPlan.Ok) {
            gasLimit = max(0.0, plan.leaveLastLevelAtMinute)
            ascentPressureBar = plan.divers[0].ascentPressureBar
        }
    }

    val limits = BottomTimeLimits(ndl, oxygen, gasLimit)
    val entries = listOfNotNull(
        BottomTimeLimit.ndl to ndl,
        BottomTimeLimit.oxygen to oxygen,
        gasLimit?.let { BottomTimeLimit.gas to it },
    )
    val minutes = floor(entries.minOf { it.second })
    val binding = entries.filter { floor(it.second) == minutes }.map { it.first }
    return BottomTime.Ok(
        minutes = minutes,
        binding = binding,
        limits = limits,
        ascentPressureBar = ascentPressureBar,
        otu = otu(ppO2AtDepth, minutes),
        ppO2AtDepth = ppO2AtDepth,
        ppO2AboveWorking = ppO2AtDepth > input.ppO2.working + 1e-9,
    )
}
