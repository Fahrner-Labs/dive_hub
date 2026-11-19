package dev.divehub.android.ui.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.api.models.ApiToolsGasPlanPost200Response
import dev.divehub.shared.planning.PlanningWater
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.tools.GasPlanToolDiver
import dev.divehub.shared.tools.GasPlanToolInput
import dev.divehub.shared.tools.GasPlanToolLevel
import dev.divehub.shared.tools.MAX_PLAN_DIVERS
import dev.divehub.shared.tools.MAX_DIVE_MINUTES_AT_LEVEL
import dev.divehub.shared.tools.MAX_PLAN_LEVELS
import dev.divehub.shared.tools.PLAN_RULES
import dev.divehub.shared.tools.RULE_ASCENT_PRESSURE
import dev.divehub.shared.tools.RULE_RESERVE
import dev.divehub.shared.tools.ToolDiverSource
import dev.divehub.shared.tools.ToolRanges
import dev.divehub.shared.tools.ToolSources
import dev.divehub.shared.tools.ToolsError
import dev.divehub.shared.tools.assembleToolSources
import dev.divehub.shared.tools.depthFromDisplay
import dev.divehub.shared.tools.gasPlan
import dev.divehub.shared.tools.pressureFromDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** A level's typed text in the User's units; depth is converted for the kernel. */
data class GasPlanLevelInput(val depth: String = "", val minutes: String = "")

/** A Diver's typed text; [diverId] names a synced Diver the User picked, a null one is a guest. */
data class GasPlanDiverInput(
    val diverId: String? = null,
    val name: String = "",
    val volume: String = "",
    val pressure: String = "",
    val sac: String = "",
)

/** The gas plan's inputs, as typed; every value is kept in the User's units and converted before the kernel. */
data class GasPlanInputs(
    val o2: String = "",
    val levels: List<GasPlanLevelInput> = listOf(GasPlanLevelInput()),
    val divers: List<GasPlanDiverInput> = emptyList(),
    val water: PlanningWater = PlanningWater.salt,
    val rule: String? = null,
    val bar: String = "",
    val altitude: String = "",
    val ppo2Contingency: String = "",
    val descentRate: String = "",
    val ascentRate: String = "",
)

/** What the gas plan screen draws; the error is a code the UI translates. */
data class GasPlanUiState(
    val loading: Boolean = true,
    val storeEmpty: Boolean = false,
    val syncedAt: String? = null,
    val storeError: String? = null,
    /** The synced Divers the picker offers, the own Diver first. */
    val knownDivers: List<ToolDiverSource> = emptyList(),
    val inputs: GasPlanInputs = GasPlanInputs(),
    val answer: ApiToolsGasPlanPost200Response? = null,
    val error: String? = null,
)

/**
 * The offline gas plan (FAH-275; ADR 0033). The maths and the answer's shape are in `shared/tools`; this holds the
 * typed levels and Divers, assembles the synced store once, and keeps the last answer. A synced Diver brings their
 * planning SAC when their Dives are on the device; a typed SAC wins.
 */
class GasPlanViewModel(private val store: DiveStore) : ViewModel() {
    private val _state = MutableStateFlow(GasPlanUiState())
    val state: StateFlow<GasPlanUiState> = _state.asStateFlow()

    private var sources = ToolSources()
    private var imperial = false
    private var loadJob: Job? = null

    fun start(imperial: Boolean) {
        this.imperial = imperial
        load()
    }

    fun setUnits(imperial: Boolean) {
        if (this.imperial == imperial) return
        this.imperial = imperial
        recompute()
    }

    fun forget() {
        loadJob?.cancel()
        sources = ToolSources()
        _state.value = GasPlanUiState(loading = false)
    }

    fun update(change: (GasPlanInputs) -> GasPlanInputs) {
        _state.update { it.copy(inputs = change(it.inputs)) }
        recompute()
    }

    fun addLevel() {
        update { inputs ->
            if (inputs.levels.size >= MAX_PLAN_LEVELS) inputs
            else inputs.copy(levels = inputs.levels + GasPlanLevelInput())
        }
    }

    fun removeLevel(index: Int) {
        update { inputs -> inputs.copy(levels = inputs.levels.filterIndexed { i, _ -> i != index }) }
    }

    fun addDiver(diver: ToolDiverSource) {
        update { inputs ->
            if (inputs.divers.size >= MAX_PLAN_DIVERS || inputs.divers.any { it.diverId == diver.id }) inputs
            else inputs.copy(
                divers = inputs.divers + GasPlanDiverInput(
                    diverId = diver.id,
                    name = diver.name,
                    volume = "12",
                    pressure = if (imperial) "2901" else "200",
                ),
            )
        }
    }

    fun addGuest() {
        update { inputs ->
            if (inputs.divers.size >= MAX_PLAN_DIVERS) inputs
            else inputs.copy(
                divers = inputs.divers + GasPlanDiverInput(
                    volume = "12",
                    pressure = if (imperial) "2901" else "200",
                ),
            )
        }
    }

    fun removeDiver(index: Int) {
        update { inputs -> inputs.copy(divers = inputs.divers.filterIndexed { i, _ -> i != index }) }
    }

    fun reload() = load()

    private fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch(Dispatchers.Default) {
            try {
                val read = assembleToolSources(store, System.currentTimeMillis())
                sources = read
                _state.update {
                    it.copy(
                        loading = false,
                        storeEmpty = read.storeEmpty,
                        syncedAt = read.syncedAt,
                        storeError = null,
                        knownDivers = read.divers,
                    )
                }
            } catch (_: Exception) {
                sources = ToolSources()
                _state.update {
                    it.copy(loading = false, storeError = STORE_UNREADABLE, knownDivers = emptyList(), syncedAt = null)
                }
            }
            recompute()
        }
    }

    private fun recompute() {
        val inputs = _state.value.inputs
        val computed = gasPlanAnswer(inputs, imperial, sources)
        _state.update { it.copy(answer = computed.answer, error = computed.error) }
    }

    companion object {
        const val STORE_UNREADABLE = "store_unreadable"

        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { GasPlanViewModel(container.diveStore) }
        }
    }
}

/** What the screen has after a keystroke: an answer, an error code, or neither while the plan is incomplete. */
internal data class GasPlanAnswer(val answer: ApiToolsGasPlanPost200Response? = null, val error: String? = null)

/**
 * Turns typed text into the plan's answer, in the User's units. It is separate from the ViewModel so the blank,
 * partial and out-of-range cases can be tested without the store.
 */
internal fun gasPlanAnswer(inputs: GasPlanInputs, imperial: Boolean, sources: ToolSources): GasPlanAnswer {
    val levels = inputs.levels.map { level ->
        val depth = level.depth.number()?.let { depthFromDisplay(it, imperial) }
        val minutes = level.minutes.number()
        Triple(depth, minutes, level)
    }
    val o2 = inputs.o2.number() ?: if (inputs.o2.isBlank()) 21.0 else null
    val altitude = inputs.altitude.number()?.let { depthFromDisplay(it, imperial) }
    val bar = inputs.bar.number()?.let { pressureFromDisplay(it, imperial) }
    val descent = inputs.descentRate.number()?.let { depthFromDisplay(it, imperial) }
    val ascent = inputs.ascentRate.number()?.let { depthFromDisplay(it, imperial) }
    val contingency = inputs.ppo2Contingency.number()
    val divers = inputs.divers.map { diver ->
        GasPlanDiverInputValues(
            diver = diver,
            volume = diver.volume.number(),
            pressure = diver.pressure.number()?.let { pressureFromDisplay(it, imperial) },
            sac = diver.sac.number(),
        )
    }

    val unreadable = inputs.o2.isNotBlank() && inputs.o2.number() == null ||
        inputs.altitude.isNotBlank() && inputs.altitude.number() == null ||
        inputs.bar.isNotBlank() && inputs.bar.number() == null ||
        inputs.descentRate.isNotBlank() && inputs.descentRate.number() == null ||
        inputs.ascentRate.isNotBlank() && inputs.ascentRate.number() == null ||
        inputs.ppo2Contingency.isNotBlank() && inputs.ppo2Contingency.number() == null ||
        levels.any { (depth, minutes, raw) ->
            (raw.depth.isNotBlank() && depth == null) || (raw.minutes.isNotBlank() && minutes == null)
        } ||
        divers.any {
            it.diver.volume.isNotBlank() && it.volume == null ||
                it.diver.pressure.isNotBlank() && it.pressure == null ||
                it.diver.sac.isNotBlank() && it.sac == null
        }

    val outOfRange = listOfNotNull(
        o2?.takeUnless { it in ToolRanges.O2 },
        altitude?.takeUnless { it in ToolRanges.ALTITUDE_M },
        bar?.takeUnless { it in ToolRanges.RULE_BAR },
        descent?.takeUnless { it in ToolRanges.DESCENT_RATE_MPM },
        ascent?.takeUnless { it in ToolRanges.ASCENT_RATE_MPM },
        contingency?.takeUnless { it in ToolRanges.PPO2 },
        levels.mapNotNull { (depth, _, _) -> depth?.takeUnless { it in ToolRanges.DEPTH_M } }.firstOrNull(),
        levels.mapNotNull { (_, minutes, _) -> minutes?.takeUnless { it in 0.0..MAX_DIVE_MINUTES_AT_LEVEL } }.firstOrNull(),
        divers.mapNotNull { it.volume?.takeUnless { it in ToolRanges.VOLUME_L } }.firstOrNull(),
        divers.mapNotNull { it.pressure?.takeUnless { it in ToolRanges.PRESSURE_BAR } }.firstOrNull(),
        divers.mapNotNull { it.sac?.takeUnless { it in ToolRanges.SAC_LPM } }.firstOrNull(),
    ).isNotEmpty()
    if (unreadable || outOfRange) return GasPlanAnswer(error = ToolsError.INVALID_INPUT)

    val filled = levels.filter { (_, _, raw) -> raw.depth.isNotBlank() || raw.minutes.isNotBlank() }
    if (filled.isEmpty() || divers.isEmpty()) return GasPlanAnswer()
    if (filled.any { (depth, minutes, _) -> depth == null || minutes == null }) {
        return GasPlanAnswer(error = ToolsError.INVALID_INPUT)
    }
    if (divers.any { it.volume == null || it.pressure == null }) return GasPlanAnswer(error = ToolsError.INVALID_INPUT)
    if (inputs.rule != null && inputs.rule !in PLAN_RULES) return GasPlanAnswer(error = ToolsError.INVALID_INPUT)
    if ((inputs.rule == RULE_RESERVE || inputs.rule == RULE_ASCENT_PRESSURE) && bar == null) {
        return GasPlanAnswer(error = ToolsError.INVALID_INPUT)
    }
    if (o2 == null) return GasPlanAnswer(error = ToolsError.INVALID_INPUT)

    return try {
        GasPlanAnswer(
            answer = gasPlan(
                GasPlanToolInput(
                    o2 = o2,
                    levels = filled.map { (depth, minutes, _) -> GasPlanToolLevel(depth!!, minutes!!) },
                    divers = divers.map {
                        GasPlanToolDiver(
                            name = it.diver.name,
                            volumeL = it.volume!!,
                            startPressureBar = it.pressure!!,
                            sacLpm = it.sac,
                            diverId = it.diver.diverId,
                        )
                    },
                    rule = inputs.rule,
                    bar = bar,
                    water = inputs.water,
                    altitudeM = altitude,
                    ppo2Contingency = contingency,
                    descentRateMPerMin = descent,
                    ascentRateMPerMin = ascent,
                ),
                sources,
            ),
        )
    } catch (error: ToolsError) {
        GasPlanAnswer(error = error.code)
    }
}

private data class GasPlanDiverInputValues(
    val diver: GasPlanDiverInput,
    val volume: Double?,
    val pressure: Double?,
    val sac: Double?,
)

private fun String.number(): Double? = trim().toDoubleOrNull()
