package dev.divehub.android.ui.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.android.ui.Formats
import dev.divehub.api.models.ApiToolsBottomTimeGet200Response
import dev.divehub.api.models.ApiToolsGasGet200Response
import dev.divehub.shared.planning.PlanningWater
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.tools.BottomTimeToolInput
import dev.divehub.shared.tools.GasNumbersInput
import dev.divehub.shared.tools.ToolSources
import dev.divehub.shared.tools.ToolsError
import dev.divehub.shared.tools.assembleToolSources
import dev.divehub.shared.tools.bottomTime
import dev.divehub.shared.tools.gasNumbers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** What the tools screen holds as typed text, in the User's units (metres and bar are converted for the kernels). */
data class ToolsInputs(
    val o2: String = "",
    val helium: String = "",
    val depth: String = "",
    val volume: String = "",
    val pressure: String = "",
    val sac: String = "",
    val gf: String = "",
    val altitude: String = "",
    val ppo2Working: String = "",
    val ppo2Contingency: String = "",
    val bar: String = "",
    val water: PlanningWater = PlanningWater.salt,
    val rule: String? = null,
    val oxygenNarcotic: Boolean = true,
)

/** What the tools screen draws; the errors are codes the UI translates, never the server's English text. */
data class ToolsUiState(
    val loading: Boolean = true,
    /** No Dive has synced yet: an empty state, not an error (client contract §2). */
    val storeEmpty: Boolean = false,
    val hasOwnDiver: Boolean = false,
    val inputs: ToolsInputs = ToolsInputs(),
    val gas: ApiToolsGasGet200Response? = null,
    val gasError: String? = null,
    val bottom: ApiToolsBottomTimeGet200Response? = null,
    val bottomError: String? = null,
)

/**
 * The offline tools (FAH-274; ADR 0032). All maths is in `shared/tools` and `shared/planning`; this only holds the
 * typed inputs, assembles the synced Diver's values once, and keeps the last answer.
 */
class ToolsViewModel(private val store: DiveStore) : ViewModel() {
    private val _state = MutableStateFlow(ToolsUiState())
    val state: StateFlow<ToolsUiState> = _state.asStateFlow()

    private var sources = ToolSources()
    private var imperial = false
    private var loadJob: Job? = null

    /** Reads the store each time the screen opens: a sync behind it changes what the Diver brings. */
    fun start(imperial: Boolean) {
        this.imperial = imperial
        load()
    }

    fun setUnits(imperial: Boolean) {
        if (this.imperial == imperial) return
        this.imperial = imperial
        recompute()
    }

    /** Forgets the previous User's typed values when the session ends (client contract §1 Security). */
    fun forget() {
        loadJob?.cancel()
        sources = ToolSources()
        _state.value = ToolsUiState(loading = false)
    }

    fun update(change: (ToolsInputs) -> ToolsInputs) {
        _state.update { it.copy(inputs = change(it.inputs)) }
        recompute()
    }

    private fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch(Dispatchers.Default) {
            val read = assembleToolSources(store, System.currentTimeMillis())
            sources = read
            _state.update { it.copy(loading = false, storeEmpty = read.storeEmpty, hasOwnDiver = read.ownDiverId != null) }
            recompute()
        }
    }

    private fun recompute() {
        val inputs = _state.value.inputs
        val answers = answers(inputs, sources, imperial)
        _state.update {
            it.copy(
                gas = answers.gas,
                gasError = answers.gasError,
                bottom = answers.bottom,
                bottomError = answers.bottomError,
            )
        }
    }

    private data class Answers(
        val gas: ApiToolsGasGet200Response? = null,
        val gasError: String? = null,
        val bottom: ApiToolsBottomTimeGet200Response? = null,
        val bottomError: String? = null,
    )

    private fun answers(inputs: ToolsInputs, sources: ToolSources, imperial: Boolean): Answers {
        val o2 = inputs.o2.number()
        val depth = inputs.depth.number()?.let { Formats.depthFromDisplay(it, imperial) }
        val helium = inputs.helium.number()
        val volume = inputs.volume.number()
        val pressure = inputs.pressure.number()?.let { Formats.pressureFromDisplay(it, imperial) }
        val sac = inputs.sac.number()
        val gf = inputs.gf.number()
        val altitude = inputs.altitude.number()?.let { Formats.depthFromDisplay(it, imperial) }
        val ppo2Working = inputs.ppo2Working.number()
        val ppo2Contingency = inputs.ppo2Contingency.number()
        val bar = inputs.bar.number()?.let { Formats.pressureFromDisplay(it, imperial) }

        val unreadable = listOf(
            inputs.o2, inputs.helium, inputs.depth, inputs.volume, inputs.pressure, inputs.sac, inputs.gf,
            inputs.altitude, inputs.ppo2Working, inputs.ppo2Contingency, inputs.bar,
        ).any { it.isNotBlank() && it.number() == null }
        val outOfRange = listOfNotNull(
            o2?.takeUnless { it in 1.0..100.0 },
            helium?.takeUnless { it in 0.0..100.0 },
            depth?.takeUnless { it in 1.0..300.0 },
            volume?.takeUnless { it in 1.0..60.0 },
            pressure?.takeUnless { it in 1.0..350.0 },
            sac?.takeUnless { it in 1.0..100.0 },
            gf?.takeUnless { it in 20.0..100.0 },
            altitude?.takeUnless { it in 0.0..5000.0 },
            ppo2Working?.takeUnless { it in 1.0..2.0 },
            ppo2Contingency?.takeUnless { it in 1.0..2.0 },
            bar?.takeUnless { it in 0.0..350.0 },
        ).isNotEmpty()

        val invalid = unreadable || outOfRange
        var gasFailure: String? = null
        val gasAnswer = if (o2 == null) null else if (invalid) null else try {
            gasNumbers(
                GasNumbersInput(
                    o2 = o2,
                    he = helium,
                    depthM = depth,
                    water = inputs.water,
                    altitudeM = altitude,
                    ppo2Working = ppo2Working,
                    ppo2Contingency = ppo2Contingency,
                    oxygenNarcotic = inputs.oxygenNarcotic,
                ),
            )
        } catch (error: ToolsError) {
            gasFailure = error.code
            null
        }
        val gasError = when {
            o2 == null && inputs.o2.isBlank() -> null
            o2 == null -> ToolsError.INVALID_INPUT
            invalid -> ToolsError.INVALID_INPUT
            gasAnswer == null -> gasFailure ?: ToolsError.INVALID_INPUT
            else -> null
        }

        var bottomFailure: String? = null
        val bottomAnswer = if (o2 == null || depth == null || invalid) null else try {
            bottomTime(
                BottomTimeToolInput(
                    depthM = depth,
                    o2 = o2,
                    he = helium,
                    volumeL = volume,
                    pressureBar = pressure,
                    sacLpm = sac,
                    rule = inputs.rule,
                    bar = bar,
                    gfHigh = gf,
                    water = inputs.water,
                    altitudeM = altitude,
                    ppo2Working = ppo2Working,
                    ppo2Contingency = ppo2Contingency,
                ),
                sources,
            )
        } catch (error: ToolsError) {
            bottomFailure = error.code
            null
        }
        val bottomError = when {
            o2 == null || inputs.depth.isBlank() -> null
            depth == null || invalid -> ToolsError.INVALID_INPUT
            bottomAnswer == null -> bottomFailure ?: ToolsError.INVALID_INPUT
            else -> null
        }
        return Answers(gas = gasAnswer, gasError = gasError, bottom = bottomAnswer, bottomError = bottomError)
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { ToolsViewModel(container.diveStore) }
        }
    }
}

private fun String.number(): Double? = trim().toDoubleOrNull()

