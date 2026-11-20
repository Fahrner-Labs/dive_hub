package dev.divehub.android.ui.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200Response
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.EstimateCylinder
import dev.divehub.shared.planning.LeadUnit
import dev.divehub.shared.planning.PlannedConditions
import dev.divehub.shared.planning.SiteWaterType
import dev.divehub.shared.planning.Suit
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.planning.SuitUndergarment
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.sync.SyncProgress
import dev.divehub.shared.tools.MAX_CYLINDER_LITRES
import dev.divehub.shared.tools.MAX_PLAN_CYLINDERS
import dev.divehub.shared.tools.PersonalizedSources
import dev.divehub.shared.tools.ToolsError
import dev.divehub.shared.tools.assemblePersonalizedSources
import dev.divehub.shared.tools.leadEstimate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Instant

/** One planned cylinder as typed; the volume is in litres (the API's unit). */
data class LeadCylinderInput(val volume: String = "", val material: CylinderMaterial? = null)

/** The lead calculator's conditions as typed. Thickness is millimetres in both unit systems. */
data class LeadInputs(
    val water: SiteWaterType = SiteWaterType.salt,
    val suit: SuitType = SuitType.none,
    val thickness: String = "",
    val hood: Boolean? = null,
    val undergarment: SuitUndergarment? = null,
    val cylinders: List<LeadCylinderInput> = listOf(LeadCylinderInput()),
)

/** What the lead screen draws; the error is a code the UI translates. */
data class LeadEstimateUiState(
    val loading: Boolean = true,
    val storeEmpty: Boolean = false,
    /** An own Diver is in the store, so the answer rests on synced data and must say how old it is. */
    val personalized: Boolean = false,
    val syncedAt: String? = null,
    /** Whether the profiles phase has every listed channel; the staleness label says it is still syncing. */
    val profilesComplete: Boolean = true,
    /** Whether the details phase has every Dive's detail; until then a no-number answer must not claim there is none. */
    val detailsComplete: Boolean = true,
    val storeError: String? = null,
    val inputs: LeadInputs = LeadInputs(),
    val answer: ApiDiversIdLeadEstimateGet200Response? = null,
    val error: String? = null,
)

/**
 * The offline lead estimate (FAH-276; ADR 0031): conditions typed here, the Diver's dive history, body weight and
 * sites read from the synced store. The answer is the API's shape, so the screen can hold its whole contract.
 */
class LeadEstimateViewModel(
    private val store: DiveStore,
    private val revision: StateFlow<Long>,
    private val progress: StateFlow<SyncProgress>,
    private val computeDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {
    private val _state = MutableStateFlow(LeadEstimateUiState())
    val state: StateFlow<LeadEstimateUiState> = _state.asStateFlow()

    private var sources = PersonalizedSources()
    private var unit = LeadUnit.kg
    private var loadJob: Job? = null
    private var revisionJob: Job? = null
    private var progressJob: Job? = null

    fun start(imperial: Boolean) {
        unit = if (imperial) LeadUnit.lb else LeadUnit.kg
        observe()
        load()
    }

    fun setUnits(imperial: Boolean) {
        val next = if (imperial) LeadUnit.lb else LeadUnit.kg
        if (unit == next) return
        unit = next
        recompute()
    }

    fun forget() {
        loadJob?.cancel()
        revisionJob?.cancel()
        progressJob?.cancel()
        sources = PersonalizedSources()
        _state.value = LeadEstimateUiState(loading = false)
    }

    fun update(change: (LeadInputs) -> LeadInputs) {
        _state.update { it.copy(inputs = change(it.inputs)) }
        recompute()
    }

    fun addCylinder() {
        update { inputs ->
            if (inputs.cylinders.size >= MAX_PLAN_CYLINDERS) inputs
            else inputs.copy(cylinders = inputs.cylinders + LeadCylinderInput())
        }
    }

    fun removeCylinder(index: Int) {
        update { inputs -> inputs.copy(cylinders = inputs.cylinders.filterIndexed { i, _ -> i != index }) }
    }

    fun reload() = load()

    /** A sync that writes the store bumps the revision; reread once per bump, never on a timer. */
    private fun observe() {
        revisionJob?.cancel()
        revisionJob = viewModelScope.launch {
            var last = revision.value
            revision.collect { value ->
                if (value != last) {
                    last = value
                    load()
                }
            }
        }
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            progress.collect { report ->
                _state.update {
                    it.copy(detailsComplete = report == SyncProgress.EMPTY || (report.metaDone && report.detailsDone))
                }
            }
        }
    }

    private fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch(computeDispatcher) {
            try {
                val read = assemblePersonalizedSources(store, System.currentTimeMillis())
                sources = read
                _state.update {
                    it.copy(
                        loading = false,
                        storeEmpty = read.storeEmpty,
                        personalized = read.ownDiverId != null,
                        syncedAt = read.syncedAt,
                        profilesComplete = read.profilesComplete,
                        storeError = null,
                    )
                }
            } catch (_: Exception) {
                sources = PersonalizedSources()
                _state.update {
                    it.copy(
                        loading = false,
                        personalized = false,
                        syncedAt = null,
                        profilesComplete = true,
                        storeError = STORE_UNREADABLE,
                    )
                }
            }
            recompute()
        }
    }

    private fun recompute() {
        val computed = leadAnswer(_state.value.inputs, unit, sources, Instant.fromEpochMilliseconds(System.currentTimeMillis()))
        _state.update { it.copy(answer = computed.answer, error = computed.error) }
    }

    companion object {
        const val STORE_UNREADABLE = "store_unreadable"

        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LeadEstimateViewModel(
                    store = container.diveStore,
                    revision = container.syncRevision,
                    progress = container.syncProgress,
                )
            }
        }
    }
}

/** What the screen has after a keystroke: an answer, an error code, or neither while the conditions are incomplete. */
internal data class LeadAnswer(val answer: ApiDiversIdLeadEstimateGet200Response? = null, val error: String? = null)

/**
 * Turns the typed conditions into the estimate, separate from the ViewModel so the blank and out-of-range cases can
 * be tested without a store. A blank cylinder size means "not chosen yet" (no answer), never a zero.
 */
internal fun leadAnswer(
    inputs: LeadInputs,
    unit: LeadUnit,
    sources: PersonalizedSources,
    today: Instant,
): LeadAnswer {
    val volumes = inputs.cylinders.map { row -> row.volume.trim().takeIf { it.isNotEmpty() }?.toDoubleOrNull() to row }
    if (volumes.any { (volume, row) -> row.volume.isNotBlank() && volume == null }) {
        return LeadAnswer(error = ToolsError.INVALID_INPUT)
    }
    val chosen = volumes.mapNotNull { (volume, row) -> volume?.let { it to row } }
    if (chosen.isEmpty()) return LeadAnswer()
    if (chosen.any { (volume, _) -> volume <= 0.0 || volume > MAX_CYLINDER_LITRES }) {
        return LeadAnswer(error = ToolsError.INVALID_INPUT)
    }
    val thickness = inputs.thickness.trim().takeIf { it.isNotEmpty() }?.toDoubleOrNull()
    val usesThickness = inputs.suit == SuitType.wetsuit || inputs.suit == SuitType.semidry
    if (inputs.thickness.isNotBlank() && (thickness == null || !usesThickness || thickness < 0.0 || thickness > 15.0)) {
        return LeadAnswer(error = if (inputs.thickness.isNotBlank() && thickness != null && !usesThickness) {
            ToolsError.SUIT_INVALID
        } else {
            ToolsError.INVALID_INPUT
        })
    }
    if (inputs.undergarment != null && inputs.suit != SuitType.drysuit) {
        return LeadAnswer(error = ToolsError.SUIT_INVALID)
    }
    val plan = PlannedConditions(
        water = inputs.water,
        suit = Suit(
            type = inputs.suit,
            thicknessMm = thickness.takeIf { usesThickness },
            hood = inputs.hood.takeIf { inputs.suit != SuitType.none },
            undergarment = inputs.undergarment.takeIf { inputs.suit == SuitType.drysuit },
        ),
        cylinders = chosen.map { (volume, row) -> EstimateCylinder(volume, row.material) },
    )
    return try {
        LeadAnswer(answer = leadEstimate(plan, unit, sources, today))
    } catch (error: ToolsError) {
        LeadAnswer(error = error.code)
    }
}
