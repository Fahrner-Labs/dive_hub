// The offline gas plan (FAH-275; ADR 0033, slice 22). It calls the ported kernel and shapes the answer exactly like the
// API's gas-plan service: `used`/`source`, where each Diver's SAC came from, the controlling Diver, the route's
// rounding, and `outside_limits`. The generated OpenAPI models are the contract; nothing here is stored.
package dev.divehub.shared.tools

import dev.divehub.api.models.ApiToolsGasPlanPost200Response
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseDiversItem
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseDiversItemSacLpm
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseGas
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseRule
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseSegmentsItem
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseUsed
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseUsedAltitudeM
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseUsedAscentRateMPerMin
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseUsedDescentRateMPerMin
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseUsedGasRule
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseUsedGasRuleValue
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseUsedWater
import dev.divehub.shared.planning.ASCENT_RATE_M_PER_MIN
import dev.divehub.shared.planning.Conditions
import dev.divehub.shared.planning.DESCENT_RATE_M_PER_MIN
import dev.divehub.shared.planning.DEFAULT_PLANNING_SAC_LPM
import dev.divehub.shared.planning.DiverPlan
import dev.divehub.shared.planning.GasMix
import dev.divehub.shared.planning.GasPlan
import dev.divehub.shared.planning.GasPlanDiver
import dev.divehub.shared.planning.GasPlanInput
import dev.divehub.shared.planning.GasPlanLevel
import dev.divehub.shared.planning.GasRule
import dev.divehub.shared.planning.PlanningSacResult
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.planning.PlanningWater
import dev.divehub.shared.planning.TurnRule
import dev.divehub.shared.planning.planGas
import dev.divehub.shared.planning.roundTo

const val RULE_HALVES = "halves"
const val RULE_THIRDS = "thirds"
const val RULE_SIXTHS = "sixths"

/** Every rule the route accepts; the first three are Bottom time's too. */
val PLAN_RULES = listOf(RULE_ROCK_BOTTOM, RULE_RESERVE, RULE_ASCENT_PRESSURE, RULE_HALVES, RULE_THIRDS, RULE_SIXTHS)

const val MAX_PLAN_LEVELS = 8
const val MAX_PLAN_DIVERS = 8
const val MAX_DIVE_MINUTES_AT_LEVEL = 600.0
const val MAX_DIVER_NAME = 80

data class GasPlanToolLevel(val depthM: Double, val minutes: Double)

/** A Diver of the plan: a synced Diver by [diverId], or a guest who exists only in this plan. */
data class GasPlanToolDiver(
    val name: String,
    val volumeL: Double,
    val startPressureBar: Double,
    val sacLpm: Double? = null,
    val diverId: String? = null,
)

data class GasPlanToolInput(
    val o2: Double,
    val he: Double? = null,
    val levels: List<GasPlanToolLevel>,
    val divers: List<GasPlanToolDiver>,
    val rule: String? = null,
    val bar: Double? = null,
    val water: PlanningWater? = null,
    val altitudeM: Double? = null,
    val ppo2Contingency: Double? = null,
    val descentRateMPerMin: Double? = null,
    val ascentRateMPerMin: Double? = null,
)

private data class SacAnswer(val value: Double, val source: String, val basedOn: Int? = null)

private data class ResolvedDiver(val input: GasPlanToolDiver, val sac: SacAnswer, val recentDiveEndedAt: String?)

/**
 * A gas plan for one Diver or a group, shaped like `POST /api/tools/gas-plan`. A synced Diver's planning SAC and recent
 * Dive are used when their Dives are in the store; a typed SAC wins, and everyone else plans with 20 L/min.
 */
fun gasPlan(input: GasPlanToolInput, sources: ToolSources = ToolSources()): ApiToolsGasPlanPost200Response {
    val gas = GasMix(input.o2, input.he ?: 0.0)
    if (gas.o2 !in ToolRanges.O2 || gas.he !in ToolRanges.HELIUM || gas.o2 + gas.he > 100) {
        throw ToolsError(ToolsError.INVALID_INPUT)
    }
    if (input.levels.size !in 1..MAX_PLAN_LEVELS || input.divers.size !in 1..MAX_PLAN_DIVERS) {
        throw ToolsError(ToolsError.INVALID_INPUT)
    }
    if (input.levels.any { it.depthM !in ToolRanges.DEPTH_M || it.minutes !in 0.0..MAX_DIVE_MINUTES_AT_LEVEL }) {
        throw ToolsError(ToolsError.INVALID_INPUT)
    }
    for (diver in input.divers) {
        if (diver.name.length > MAX_DIVER_NAME) throw ToolsError(ToolsError.INVALID_INPUT)
        if (diver.volumeL !in ToolRanges.VOLUME_L || diver.startPressureBar !in ToolRanges.PRESSURE_BAR) {
            throw ToolsError(ToolsError.INVALID_INPUT)
        }
        if (diver.sacLpm != null && diver.sacLpm !in ToolRanges.SAC_LPM) throw ToolsError(ToolsError.INVALID_INPUT)
    }
    val ruleName = input.rule ?: RULE_ROCK_BOTTOM
    if (ruleName !in PLAN_RULES) throw ToolsError(ToolsError.INVALID_INPUT)
    if ((ruleName == RULE_RESERVE || ruleName == RULE_ASCENT_PRESSURE) && input.bar == null) {
        throw ToolsError(ToolsError.INVALID_INPUT)
    }
    input.bar?.let { if (it !in ToolRanges.RULE_BAR) throw ToolsError(ToolsError.INVALID_INPUT) }
    input.altitudeM?.let { if (it !in ToolRanges.ALTITUDE_M) throw ToolsError(ToolsError.INVALID_INPUT) }
    input.ppo2Contingency?.let { if (it !in ToolRanges.PPO2) throw ToolsError(ToolsError.INVALID_INPUT) }
    input.descentRateMPerMin?.let { if (it !in ToolRanges.DESCENT_RATE_MPM) throw ToolsError(ToolsError.INVALID_INPUT) }
    input.ascentRateMPerMin?.let { if (it !in ToolRanges.ASCENT_RATE_MPM) throw ToolsError(ToolsError.INVALID_INPUT) }

    val rule = ruleOf(ruleName, input.bar)
    val water = input.water ?: PlanningWater.salt
    val altitudeM = input.altitudeM ?: 0.0
    val descentRate = input.descentRateMPerMin ?: DESCENT_RATE_M_PER_MIN
    val ascentRate = input.ascentRateMPerMin ?: ASCENT_RATE_M_PER_MIN
    val used = ApiToolsGasPlanPost200ResponseUsed(
        water = ApiToolsGasPlanPost200ResponseUsedWater(
            value = water.name,
            source = if (input.water != null) SOURCE_REQUEST else SOURCE_DEFAULT,
        ),
        altitudeM = ApiToolsGasPlanPost200ResponseUsedAltitudeM(
            value = altitudeM,
            source = if (input.altitudeM != null) SOURCE_REQUEST else SOURCE_DEFAULT,
        ),
        gasRule = ApiToolsGasPlanPost200ResponseUsedGasRule(
            value = ApiToolsGasPlanPost200ResponseUsedGasRuleValue(
                kind = ruleName,
                bar = input.bar.takeIf { ruleName == RULE_RESERVE || ruleName == RULE_ASCENT_PRESSURE },
            ),
            source = if (input.rule == null) SOURCE_DEFAULT else SOURCE_REQUEST,
        ),
        descentRateMPerMin = ApiToolsGasPlanPost200ResponseUsedDescentRateMPerMin(
            value = descentRate,
            source = if (input.descentRateMPerMin != null) SOURCE_REQUEST else SOURCE_DEFAULT,
        ),
        ascentRateMPerMin = ApiToolsGasPlanPost200ResponseUsedAscentRateMPerMin(
            value = ascentRate,
            source = if (input.ascentRateMPerMin != null) SOURCE_REQUEST else SOURCE_DEFAULT,
        ),
    )
    val common = ApiToolsGasPlanPost200Response(
        status = STATUS_OK,
        gas = ApiToolsGasPlanPost200ResponseGas(gas.o2, gas.he),
        used = used,
        rule = ApiToolsGasPlanPost200ResponseRule(
            kind = used.gasRule.value.kind,
            bar = used.gasRule.value.bar,
        ),
    )

    val divers = input.divers.map { diver ->
        val synced = diver.diverId?.let { id -> sources.divers.firstOrNull { it.id == id } }
        val logbook = synced?.planningSac
        val sac = when {
            diver.sacLpm != null -> SacAnswer(diver.sacLpm, SOURCE_REQUEST)
            logbook?.source == PlanningSacSource.logbook -> SacAnswer(logbook.lpm, SOURCE_LOGBOOK, logbook.basedOn)
            logbook != null -> SacAnswer(DEFAULT_PLANNING_SAC_LPM, SOURCE_DEFAULT, logbook.basedOn)
            else -> SacAnswer(DEFAULT_PLANNING_SAC_LPM, SOURCE_DEFAULT)
        }
        ResolvedDiver(diver, sac, synced?.recentDiveEndedAt)
    }

    val result = planGas(
        GasPlanInput(
            gas = gas,
            conditions = Conditions(water, altitudeM),
            levels = input.levels.map { GasPlanLevel(it.depthM, it.minutes) },
            divers = divers.map { GasPlanDiver(it.input.name, it.input.volumeL, it.input.startPressureBar, it.sac.value) },
            rule = rule,
            ppO2Contingency = input.ppo2Contingency ?: DEFAULT_PPO2_CONTINGENCY,
            descentRateMPerMin = descentRate,
            ascentRateMPerMin = ascentRate,
        ),
    )
    return when (result) {
        is GasPlan.OutsideLimits -> common.copy(
            status = STATUS_OUTSIDE,
            reason = result.reason.name,
            levelIndex = result.levelIndex,
        )
        is GasPlan.Ok -> common.copy(
            runtimeMinutes = roundTo(result.runtimeMinutes, 1),
            segments = result.segments.map { segment ->
                ApiToolsGasPlanPost200ResponseSegmentsItem(
                    kind = segment.kind.name,
                    fromDepthM = segment.fromDepthM,
                    depthM = segment.depthM,
                    minutes = roundTo(segment.minutes, 1),
                    ambientBar = roundTo(segment.ambientBar, 3),
                )
            },
            divers = result.divers.mapIndexed { index, plan -> planOf(plan, divers[index]) },
            controllingIndex = result.controllingIndex,
            fits = result.fits,
            leaveLastLevelAtMinute = roundTo(result.leaveLastLevelAtMinute, 1),
        )
    }
}

private fun ruleOf(name: String, bar: Double?): GasRule = when (name) {
    RULE_RESERVE -> GasRule.Reserve(bar!!)
    RULE_ASCENT_PRESSURE -> GasRule.AscentPressure(bar!!)
    RULE_HALVES -> GasRule.Turn(TurnRule.halves)
    RULE_THIRDS -> GasRule.Turn(TurnRule.thirds)
    RULE_SIXTHS -> GasRule.Turn(TurnRule.sixths)
    else -> GasRule.RockBottom
}

private fun planOf(plan: DiverPlan, diver: ResolvedDiver) = ApiToolsGasPlanPost200ResponseDiversItem(
    name = plan.name,
    diverId = diver.input.diverId,
    sacLpm = ApiToolsGasPlanPost200ResponseDiversItemSacLpm(
        value = diver.sac.value,
        source = diver.sac.source,
        basedOn = diver.sac.basedOn,
    ),
    volumeL = diver.input.volumeL,
    startPressureBar = diver.input.startPressureBar,
    litres = roundTo(plan.litres, 1),
    endPressureBar = roundTo(plan.endPressureBar, 1),
    rockBottom = plan.rockBottom.map {
        ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem(it.depthM, roundTo(it.pressureBar, 1))
    },
    ascentPressureBar = roundTo(plan.ascentPressureBar, 1),
    turnPressureBar = plan.turnPressureBar?.let { roundTo(it, 1) },
    belowRockBottom = plan.belowRockBottom,
    atAscentStartBar = roundTo(plan.atAscentStartBar, 1),
    marginBar = roundTo(plan.marginBar, 1),
    marginMinutes = roundTo(plan.marginMinutes, 1),
    controlling = plan.controlling,
    recentDiveEndedAt = diver.recentDiveEndedAt,
    pressuresBar = plan.pressuresBar.map { roundTo(it, 1) },
)
