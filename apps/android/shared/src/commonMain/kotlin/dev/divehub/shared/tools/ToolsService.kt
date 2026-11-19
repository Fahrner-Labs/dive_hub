// The offline gas numbers and bottom time (FAH-274; ADR 0032, slice 21). They call the ported kernels and shape the
// answer exactly like the API's service shells: the same defaults, `used`/`source`, `binding`, `outside_limits` and
// rounding. The generated OpenAPI models are the contract; nothing here is stored.
package dev.divehub.shared.tools

import dev.divehub.api.models.ApiToolsBottomTimeGet200Response
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseGas
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseLimits
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseUsed
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseUsedAltitudeM
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseUsedGasRule
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseUsedGasRuleValue
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseUsedGfHigh
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseUsedSacLpm
import dev.divehub.api.models.ApiToolsBottomTimeGet200ResponseUsedWater
import dev.divehub.api.models.ApiToolsGasGet200Response
import dev.divehub.api.models.ApiToolsGasGet200ResponseAtDepth
import dev.divehub.api.models.ApiToolsGasGet200ResponseConditions
import dev.divehub.api.models.ApiToolsGasGet200ResponseGas
import dev.divehub.api.models.ApiToolsGasGet200ResponsePpO2Limits
import dev.divehub.shared.planning.BottomTime
import dev.divehub.shared.planning.BottomTimeCylinder
import dev.divehub.shared.planning.BottomTimeInput
import dev.divehub.shared.planning.BottomTimeLimit
import dev.divehub.shared.planning.BottomTimeOutside
import dev.divehub.shared.planning.Conditions
import dev.divehub.shared.planning.DEFAULT_PLANNING_SAC_LPM
import dev.divehub.shared.planning.GasMix
import dev.divehub.shared.planning.GasRule
import dev.divehub.shared.planning.PlanningSacResult
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.planning.PlanningWater
import dev.divehub.shared.planning.PpO2Limits
import dev.divehub.shared.planning.ambientPressureBar
import dev.divehub.shared.planning.bestMixPercent
import dev.divehub.shared.planning.bottomTime
import dev.divehub.shared.planning.eadMetres
import dev.divehub.shared.planning.endMetres
import dev.divehub.shared.planning.isHypoxic
import dev.divehub.shared.planning.modMetres
import dev.divehub.shared.planning.roundTo
import kotlin.math.floor

/** Defaults that no source overrides (ADR 0032), shared with the route. */
const val DEFAULT_PPO2_WORKING = 1.4
const val DEFAULT_PPO2_CONTINGENCY = 1.6
const val DEFAULT_GF_HIGH = 85.0

const val RULE_ROCK_BOTTOM = "rock_bottom"
const val RULE_RESERVE = "reserve"
const val RULE_ASCENT_PRESSURE = "ascent_pressure"

const val SOURCE_REQUEST = "request"
const val SOURCE_RECORDING = "recording"
const val SOURCE_LOGBOOK = "logbook"
const val SOURCE_DEFAULT = "default"

const val STATUS_OK = "ok"
const val STATUS_OUTSIDE = "outside_limits"

/** A refusal the screen translates by `code`, never by the server's English text. */
class ToolsError(val code: String) : Exception(code) {
    companion object {
        const val INVALID_INPUT = "invalid_input"
        const val GAS_NOT_BREATHABLE = "gas_not_breathable"
        const val SUIT_INVALID = "suit_invalid"
    }
}

data class GasNumbersInput(
    val o2: Double,
    val he: Double? = null,
    val depthM: Double? = null,
    val water: PlanningWater? = null,
    val altitudeM: Double? = null,
    val ppo2Working: Double? = null,
    val ppo2Contingency: Double? = null,
    val oxygenNarcotic: Boolean? = null,
)

/** The typed bottom-time inputs; everything null is what the store or the stated default supplies. */
data class BottomTimeToolInput(
    val depthM: Double,
    val o2: Double,
    val he: Double? = null,
    val volumeL: Double? = null,
    val pressureBar: Double? = null,
    val sacLpm: Double? = null,
    val rule: String? = null,
    val bar: Double? = null,
    val gfHigh: Double? = null,
    val water: PlanningWater? = null,
    val altitudeM: Double? = null,
    val ppo2Working: Double? = null,
    val ppo2Contingency: Double? = null,
)

/**
 * A Diver the synced store knows, for the plan's picker: the User's own first. Their planning SAC and recent Dive are
 * present only when Dives of theirs are synced; the API reads the same values of a Diver the User manages.
 */
data class ToolDiverSource(
    val id: String,
    val name: String,
    val isOwn: Boolean,
    val planningSac: PlanningSacResult? = null,
    val recentDiveEndedAt: String? = null,
)

/**
 * What the synced store knows about the User's own Diver: the values the route reads from the logbook. Absent when
 * there is no own Diver or nothing has synced yet, which is what the tools screen says as an empty state.
 */
data class ToolSources(
    val ownDiverId: String? = null,
    /** The Diver's latest Recording with a gradient factor. */
    val gfHigh: Double? = null,
    /** The planning SAC of the Diver's synced Dives, with how many Dives it rests on. */
    val planningSac: PlanningSacResult? = null,
    /** A synced Dive that ended within the last 48 hours, as the API's `recentDiveEndedAt`. */
    val recentDiveEndedAt: String? = null,
    /** No Dive has synced yet: the tools still answer from typed input, and say so. */
    val storeEmpty: Boolean = true,
    /** The store's last full sync, for the staleness line (client contract, Offline reads). */
    val syncedAt: String? = null,
    /** Every synced Diver, for the plan's picker; the own Diver comes first. */
    val divers: List<ToolDiverSource> = emptyList(),
)

/** Gas numbers for a mix: MOD, ppO2, best mix, EAD and END (ADR 0032). */
fun gasNumbers(input: GasNumbersInput): ApiToolsGasGet200Response {
    val gas = GasMix(input.o2, input.he ?: 0.0)
    if (gas.o2 + gas.he > 100) throw ToolsError(ToolsError.INVALID_INPUT)
    // Below 0.16 bar of oxygen at the surface a mix is not breathable.
    if (gas.o2 < 16) throw ToolsError(ToolsError.GAS_NOT_BREATHABLE)
    val conditions = Conditions(input.water ?: PlanningWater.salt, input.altitudeM ?: 0.0)
    val ppO2 = PpO2Limits(
        input.ppo2Working ?: DEFAULT_PPO2_WORKING,
        input.ppo2Contingency ?: DEFAULT_PPO2_CONTINGENCY,
    )
    val oxygenNarcotic = input.oxygenNarcotic ?: true
    val depth = input.depthM
    return ApiToolsGasGet200Response(
        gas = ApiToolsGasGet200ResponseGas(gas.o2, gas.he),
        hypoxic = isHypoxic(gas),
        ppO2Limits = ApiToolsGasGet200ResponsePpO2Limits(ppO2.working, ppO2.contingency),
        modWorkingM = floorTenth(modMetres(gas, ppO2.working, conditions)),
        modContingencyM = floorTenth(modMetres(gas, ppO2.contingency, conditions)),
        atDepth = depth?.let {
            ApiToolsGasGet200ResponseAtDepth(
                depthM = it,
                ppO2 = roundTo((gas.o2 / 100) * ambientPressureBar(it, conditions), 2),
                bestMixPercent = bestMixPercent(it, ppO2.working, conditions),
                eadM = roundTo(eadMetres(gas, it, conditions), 1),
                endM = roundTo(endMetres(gas, it, conditions, oxygenNarcotic), 1),
            )
        },
        conditions = ApiToolsGasGet200ResponseConditions(conditions.water.name, conditions.altitudeM),
        oxygenNarcotic = oxygenNarcotic,
    )
}

/**
 * Bottom time with its defaults from the synced logbook (ADR 0032): the shortest of the no-decompression, oxygen and
 * gas limits, and which bind. Residual nitrogen is not seeded offline in this slice; a recent Dive is still said.
 */
fun bottomTime(input: BottomTimeToolInput, sources: ToolSources = ToolSources()): ApiToolsBottomTimeGet200Response {
    val gas = GasMix(input.o2, input.he ?: 0.0)
    if (gas.o2 + gas.he > 100) throw ToolsError(ToolsError.INVALID_INPUT)
    if ((input.volumeL == null) != (input.pressureBar == null)) throw ToolsError(ToolsError.INVALID_INPUT)
    // Reserve and ascent pressure need their bar.
    if ((input.rule == RULE_RESERVE || input.rule == RULE_ASCENT_PRESSURE) && input.bar == null) {
        throw ToolsError(ToolsError.INVALID_INPUT)
    }
    if (input.rule != null && input.rule != RULE_ROCK_BOTTOM &&
        input.rule != RULE_RESERVE && input.rule != RULE_ASCENT_PRESSURE
    ) {
        throw ToolsError(ToolsError.INVALID_INPUT)
    }

    val planningSac = sources.planningSac
    val used = ApiToolsBottomTimeGet200ResponseUsed(
        gfHigh = ApiToolsBottomTimeGet200ResponseUsedGfHigh(
            value = input.gfHigh ?: sources.gfHigh ?: DEFAULT_GF_HIGH,
            source = when {
                input.gfHigh != null -> SOURCE_REQUEST
                sources.gfHigh != null -> SOURCE_RECORDING
                else -> SOURCE_DEFAULT
            },
        ),
        sacLpm = ApiToolsBottomTimeGet200ResponseUsedSacLpm(
            value = input.sacLpm ?: planningSac?.takeIf { it.source == PlanningSacSource.logbook }?.lpm
                ?: DEFAULT_PLANNING_SAC_LPM,
            source = when {
                input.sacLpm != null -> SOURCE_REQUEST
                planningSac?.source == PlanningSacSource.logbook -> SOURCE_LOGBOOK
                else -> SOURCE_DEFAULT
            },
            // How many Dives the logbook's planning SAC rests on, also when it is too few and the default stands.
            basedOn = planningSac?.basedOn?.takeIf { input.sacLpm == null },
        ),
        water = ApiToolsBottomTimeGet200ResponseUsedWater(
            value = (input.water ?: PlanningWater.salt).name,
            source = if (input.water != null) SOURCE_REQUEST else SOURCE_DEFAULT,
        ),
        altitudeM = ApiToolsBottomTimeGet200ResponseUsedAltitudeM(
            value = input.altitudeM ?: 0.0,
            source = if (input.altitudeM != null) SOURCE_REQUEST else SOURCE_DEFAULT,
        ),
        gasRule = ApiToolsBottomTimeGet200ResponseUsedGasRule(
            value = ApiToolsBottomTimeGet200ResponseUsedGasRuleValue(
                kind = input.rule ?: RULE_ROCK_BOTTOM,
                bar = if (input.rule == null || input.rule == RULE_ROCK_BOTTOM) null else input.bar,
            ),
            source = if (input.rule == null) SOURCE_DEFAULT else SOURCE_REQUEST,
        ),
    )

    val result = bottomTime(
        BottomTimeInput(
            gas = gas,
            depthM = input.depthM,
            gfHigh = used.gfHigh.value,
            conditions = Conditions(PlanningWater.valueOf(used.water.value), used.altitudeM.value),
            cylinder = input.volumeL?.let { BottomTimeCylinder(it, input.pressureBar!!) },
            sacLpm = used.sacLpm.value,
            rule = when (used.gasRule.value.kind) {
                RULE_RESERVE -> GasRule.Reserve(used.gasRule.value.bar!!)
                RULE_ASCENT_PRESSURE -> GasRule.AscentPressure(used.gasRule.value.bar!!)
                else -> GasRule.RockBottom
            },
            ppO2 = PpO2Limits(
                input.ppo2Working ?: DEFAULT_PPO2_WORKING,
                input.ppo2Contingency ?: DEFAULT_PPO2_CONTINGENCY,
            ),
        ),
    )
    val common = CommonAnswer(
        used = used,
        gas = gas,
        depthM = input.depthM,
        recentDiveEndedAt = sources.recentDiveEndedAt,
    )
    return when (result) {
        is BottomTime.OutsideLimits -> common.outside(result)
        is BottomTime.Ok -> common.ok(result)
    }
}

private data class CommonAnswer(
    val used: ApiToolsBottomTimeGet200ResponseUsed,
    val gas: GasMix,
    val depthM: Double,
    val recentDiveEndedAt: String?,
) {
    private fun base(status: String) = ApiToolsBottomTimeGet200Response(
        status = status,
        used = used,
        gas = ApiToolsBottomTimeGet200ResponseGas(gas.o2, gas.he),
        depthM = depthM,
        recentDiveEndedAt = recentDiveEndedAt,
        residualN2 = null,
    )

    fun outside(result: BottomTime.OutsideLimits) = base(STATUS_OUTSIDE).copy(
        reason = result.reason.name,
        modM = result.modM?.let(::floorTenth),
    )

    fun ok(result: BottomTime.Ok) = base(STATUS_OK).copy(
        minutes = result.minutes.toInt(),
        binding = result.binding.map(BottomTimeLimit::name),
        limits = ApiToolsBottomTimeGet200ResponseLimits(
            ndl = finite(result.limits.ndl),
            oxygen = finite(result.limits.oxygen),
            gas = result.limits.gas?.let(::finite),
        ),
        otu = roundTo(result.otu, 1),
        ppO2AtDepth = roundTo(result.ppO2AtDepth, 2),
        ppO2AboveWorking = result.ppO2AboveWorking,
        ascentPressureBar = result.ascentPressureBar?.let { roundTo(it, 1) },
    )
}

/** Depth limits are rounded down: a figure may be shallower than the exact one, never deeper. */
private fun floorTenth(n: Double): Double = floor(n * 10 + 1e-9) / 10

/** The route's `finite`: an infinite limit is no limit. */
private fun finite(n: Double): Double? = if (n.isFinite()) roundTo(n, 1) else null
