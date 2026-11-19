package dev.divehub.android.ui.tools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.formatSyncTime
import dev.divehub.api.models.ApiToolsGasPlanPost200Response
import dev.divehub.api.models.ApiToolsGasPlanPost200ResponseDiversItem
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.planning.PlanningWater
import dev.divehub.shared.tools.MAX_PLAN_LEVELS
import dev.divehub.shared.tools.PLAN_RULES
import dev.divehub.shared.tools.RULE_ASCENT_PRESSURE
import dev.divehub.shared.tools.RULE_RESERVE
import dev.divehub.shared.tools.RULE_ROCK_BOTTOM
import dev.divehub.shared.tools.ToolDiverSource
import dev.divehub.shared.tools.depthFromDisplay
import dev.divehub.shared.tools.depthIn
import java.util.Locale
import kotlin.math.floor

/** The gas plan route; it reads the synced store once so a Diver's planning SAC is there before the first keystroke. */
@Composable
fun GasPlanRoute(
    viewModel: GasPlanViewModel,
    units: String?,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = Formats.imperial(units, locale)
    LaunchedEffect(Unit) { viewModel.start(imperial) }
    LaunchedEffect(imperial) { viewModel.setUnits(imperial) }
    GasPlanScreen(
        state = state,
        imperial = imperial,
        onBack = onBack,
        onChange = viewModel::update,
        onAddLevel = viewModel::addLevel,
        onRemoveLevel = viewModel::removeLevel,
        onAddDiver = viewModel::addDiver,
        onAddGuest = viewModel::addGuest,
        onRemoveDiver = viewModel::removeDiver,
        onReload = viewModel::reload,
    )
}

/**
 * A gas plan for one Diver or a group, fully offline (FAH-275; ADR 0033). The notice is above the inputs; the answer
 * marks the controlling Diver and shows every Diver's figures, rock bottom at every level, the way between the levels
 * in words, what its SACs rest on, and that it does not check the no-decompression limit. It draws only [state], so
 * its contract-visible states have screenshot tests.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GasPlanScreen(
    state: GasPlanUiState,
    imperial: Boolean,
    onBack: () -> Unit,
    onChange: ((GasPlanInputs) -> GasPlanInputs) -> Unit,
    onAddLevel: () -> Unit,
    onRemoveLevel: (Int) -> Unit,
    onAddDiver: (ToolDiverSource) -> Unit,
    onAddGuest: () -> Unit,
    onRemoveDiver: (Int) -> Unit,
    onReload: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tools_plan_title)) },
                navigationIcon = { TextButton(onClick = onBack) { Text(stringResource(R.string.tools_plan_back)) } },
            )
        },
    ) { padding ->
        when {
            state.loading -> Column(
                Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                val label = stringResource(R.string.loading)
                CircularProgressIndicator(Modifier.semantics { contentDescription = label })
            }
            else -> Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Notice(stringResource(R.string.tools_notice))
                if (state.storeError != null) {
                    Spacer(Modifier.height(8.dp))
                    Card {
                        Text(
                            stringResource(R.string.tools_store_unreadable),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        TextButton(onClick = onReload) { Text(stringResource(R.string.logbook_retry)) }
                    }
                } else if (state.knownDivers.isEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Card {
                        Text(
                            stringResource(R.string.tools_plan_no_divers_synced),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                PlanInputs(state, imperial, onChange, onAddLevel, onRemoveLevel, onAddDiver, onAddGuest, onRemoveDiver)
                Spacer(Modifier.height(20.dp))
                PlanAnswer(state, imperial)
                Spacer(Modifier.height(20.dp))
                PlanRuleExplanation(state.inputs.rule ?: RULE_ROCK_BOTTOM)
                Spacer(Modifier.height(12.dp))
                Muted(stringResource(R.string.tools_flying))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PlanInputs(
    state: GasPlanUiState,
    imperial: Boolean,
    onChange: ((GasPlanInputs) -> GasPlanInputs) -> Unit,
    onAddLevel: () -> Unit,
    onRemoveLevel: (Int) -> Unit,
    onAddDiver: (ToolDiverSource) -> Unit,
    onAddGuest: () -> Unit,
    onRemoveDiver: (Int) -> Unit,
) {
    val inputs = state.inputs
    Heading(stringResource(R.string.tools_plan_inputs_title))
    Spacer(Modifier.height(4.dp))
    Muted(stringResource(R.string.tools_plan_lead))
    Spacer(Modifier.height(8.dp))
    NumberField(
        value = inputs.o2,
        label = stringResource(R.string.tools_plan_mix),
        unit = stringResource(R.string.tools_unit_percent),
        modifier = Modifier.fillMaxWidth(),
        onChange = { value -> onChange { it.copy(o2 = value) } },
    )
    Muted(stringResource(R.string.tools_plan_mix_hint))

    Spacer(Modifier.height(16.dp))
    HeadingSmall(stringResource(R.string.tools_plan_levels))
    inputs.levels.forEachIndexed { index, level ->
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            NumberField(
                value = level.depth,
                label = stringResource(R.string.tools_plan_level_n, index + 1),
                unit = depthUnit(imperial),
                modifier = Modifier.weight(1f),
                onChange = { value -> onChange { it.copy(levels = it.levels.mapIndexed { i, row -> if (i == index) row.copy(depth = value) else row }) } },
            )
            NumberField(
                value = level.minutes,
                label = stringResource(R.string.tools_plan_time),
                unit = stringResource(R.string.tools_plan_unit_minutes),
                modifier = Modifier.weight(1f),
                onChange = { value -> onChange { it.copy(levels = it.levels.mapIndexed { i, row -> if (i == index) row.copy(minutes = value) else row }) } },
            )
            val removeLabel = stringResource(R.string.tools_plan_remove_level, index + 1)
            TextButton(
                onClick = { onRemoveLevel(index) },
                modifier = Modifier.semantics { contentDescription = removeLabel },
            ) { Text(stringResource(R.string.tools_plan_remove_short)) }
        }
    }
    Muted(stringResource(R.string.tools_plan_time_hint))
    if (inputs.levels.size < MAX_PLAN_LEVELS) {
        TextButton(onClick = onAddLevel) { Text(stringResource(R.string.tools_plan_add_level)) }
    }

    Spacer(Modifier.height(16.dp))
    HeadingSmall(stringResource(R.string.tools_plan_divers))
    if (state.knownDivers.isNotEmpty()) {
        Muted(stringResource(R.string.tools_plan_pick))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            state.knownDivers.forEach { diver ->
                val added = inputs.divers.any { it.diverId == diver.id }
                FilterChip(
                    selected = added,
                    enabled = !added,
                    onClick = { onAddDiver(diver) },
                    label = {
                        Text(
                            if (diver.planningSac?.source == PlanningSacSource.logbook) {
                                stringResource(R.string.tools_plan_pick_with_logbook, diver.name)
                            } else {
                                diver.name
                            },
                        )
                    },
                )
            }
        }
    }
    inputs.divers.forEachIndexed { index, diver ->
        PlanDiverCard(index, diver, state, imperial, onChange, onRemoveDiver)
    }
    TextButton(onClick = onAddGuest) { Text(stringResource(R.string.tools_plan_add_guest)) }

    Spacer(Modifier.height(16.dp))
    Heading(stringResource(R.string.tools_settings_title))
    Spacer(Modifier.height(4.dp))
    Text(stringResource(R.string.tools_water), style = MaterialTheme.typography.labelLarge)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf(PlanningWater.fresh, PlanningWater.salt, PlanningWater.en13319).forEach { water ->
            FilterChip(
                selected = inputs.water == water,
                onClick = { onChange { it.copy(water = water) } },
                label = { Text(waterName(water)) },
            )
        }
    }
    NumberField(
        value = inputs.altitude,
        label = stringResource(R.string.tools_altitude),
        unit = depthUnit(imperial),
        modifier = Modifier.fillMaxWidth(),
        onChange = { value -> onChange { it.copy(altitude = value) } },
    )
    Text(stringResource(R.string.tools_gas_rule), style = MaterialTheme.typography.labelLarge)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        PLAN_RULES.forEach { rule ->
            FilterChip(
                selected = (inputs.rule ?: RULE_ROCK_BOTTOM) == rule,
                onClick = { onChange { it.copy(rule = rule.takeUnless { name -> name == RULE_ROCK_BOTTOM }, bar = "") } },
                label = { Text(planRuleName(rule)) },
            )
        }
    }
    if (inputs.rule == RULE_RESERVE || inputs.rule == RULE_ASCENT_PRESSURE) {
        NumberField(
            value = inputs.bar,
            label = if (inputs.rule == RULE_RESERVE) {
                stringResource(R.string.tools_rule_bar_reserve)
            } else {
                stringResource(R.string.tools_rule_bar_ascent)
            },
            unit = pressureUnit(imperial),
            modifier = Modifier.fillMaxWidth(),
            onChange = { value -> onChange { it.copy(bar = value) } },
        )
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        NumberField(
            value = inputs.descentRate,
            label = stringResource(R.string.tools_plan_descent_rate),
            unit = rateUnit(imperial),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(descentRate = value) } },
        )
        NumberField(
            value = inputs.ascentRate,
            label = stringResource(R.string.tools_plan_ascent_rate),
            unit = rateUnit(imperial),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(ascentRate = value) } },
        )
    }
    NumberField(
        value = inputs.ppo2Contingency,
        label = stringResource(R.string.tools_ppo2_contingency),
        unit = stringResource(R.string.tools_unit_pressure_metric),
        modifier = Modifier.fillMaxWidth(),
        onChange = { value -> onChange { it.copy(ppo2Contingency = value) } },
    )
}

@Composable
private fun PlanDiverCard(
    index: Int,
    diver: GasPlanDiverInput,
    state: GasPlanUiState,
    imperial: Boolean,
    onChange: ((GasPlanInputs) -> GasPlanInputs) -> Unit,
    onRemove: (Int) -> Unit,
) {
    val synced = diver.diverId?.let { id -> state.knownDivers.firstOrNull { it.id == id } }
    val fallbackName = stringResource(R.string.tools_plan_diver_n, index + 1)
    val name = diver.name.ifBlank { fallbackName }
    val answer = state.answer?.divers?.getOrNull(index)
    Card {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
            if (answer?.controlling == true) {
                Text(
                    stringResource(R.string.tools_plan_controls),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        if (diver.diverId == null) {
            NumberField(
                value = diver.name,
                label = stringResource(R.string.tools_plan_name),
                unit = "",
                modifier = Modifier.fillMaxWidth(),
                onChange = { value -> onChange { it.copy(divers = it.divers.mapIndexed { i, row -> if (i == index) row.copy(name = value) else row }) } },
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberField(
                value = diver.volume,
                label = stringResource(R.string.tools_volume),
                unit = stringResource(R.string.tools_unit_litres),
                modifier = Modifier.weight(1f),
                onChange = { value -> onChange { it.copy(divers = it.divers.mapIndexed { i, row -> if (i == index) row.copy(volume = value) else row }) } },
            )
            NumberField(
                value = diver.pressure,
                label = stringResource(R.string.tools_start_pressure),
                unit = pressureUnit(imperial),
                modifier = Modifier.weight(1f),
                onChange = { value -> onChange { it.copy(divers = it.divers.mapIndexed { i, row -> if (i == index) row.copy(pressure = value) else row }) } },
            )
        }
        NumberField(
            value = diver.sac,
            label = stringResource(R.string.tools_sac),
            unit = stringResource(R.string.tools_unit_sac),
            modifier = Modifier.fillMaxWidth(),
            onChange = { value -> onChange { it.copy(divers = it.divers.mapIndexed { i, row -> if (i == index) row.copy(sac = value) else row }) } },
        )
        when {
            synced == null -> Muted(stringResource(R.string.tools_plan_sac_guest))
            synced.planningSac?.source == PlanningSacSource.logbook -> Muted(stringResource(R.string.tools_plan_sac_logbook))
            synced.planningSac != null -> Muted(stringResource(R.string.tools_plan_sac_default))
            synced.isOwn -> Muted(stringResource(R.string.tools_plan_sac_no_dives))
            else -> Muted(stringResource(R.string.tools_plan_sac_other))
        }
        TextButton(onClick = { onRemove(index) }) { Text(stringResource(R.string.tools_plan_remove_diver, name)) }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun PlanAnswer(state: GasPlanUiState, imperial: Boolean) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val datePattern = stringResource(R.string.tools_date_pattern)
    HeadingSmall(stringResource(R.string.tools_plan_result_caption))
    when {
        state.error != null -> ErrorText(state.error)
        state.answer == null -> Muted(stringResource(R.string.tools_plan_empty))
        else -> PlanBody(state, imperial, locale, datePattern)
    }
}

@Composable
private fun PlanBody(state: GasPlanUiState, imperial: Boolean, locale: Locale, datePattern: String) {
    val context = LocalContext.current
    val answer = state.answer!!
    val levels = state.inputs.levels.mapNotNull { level ->
        val depth = level.depth.trim().toDoubleOrNull() ?: return@mapNotNull null
        depthFromDisplay(depth, imperial)
    }
    if (answer.status == "outside_limits") {
        val depth = answer.levelIndex?.let { levels.getOrNull(it) }
        Notice(
            stringResource(
                when (answer.reason) {
                    "mod" -> R.string.tools_plan_outside_mod
                    "gas" -> R.string.tools_plan_outside_gas
                    else -> R.string.tools_plan_outside_depth
                },
                depth?.let { Formats.depth(context, it, locale, imperial) } ?: "",
            ),
        )
        return
    }
    if (answer.divers.orEmpty().any { it.recentDiveEndedAt != null }) {
        Notice(stringResource(R.string.tools_plan_recent))
        Spacer(Modifier.height(8.dp))
    }
    Notice(stringResource(R.string.tools_plan_no_ndl))
    Spacer(Modifier.height(8.dp))
    val controlling = answer.divers?.getOrNull(answer.controllingIndex ?: 0)
    if (answer.fits == true) {
        Text(
            stringResource(
                R.string.tools_plan_fits_until,
                Math.max(0, floor(answer.leaveLastLevelAtMinute ?: 0.0).toInt()),
            ),
            style = MaterialTheme.typography.bodyLarge,
        )
    } else {
        Notice(
            stringResource(
                R.string.tools_plan_leave_by,
                levels.lastOrNull()?.let { Formats.depth(context, it, locale, imperial) } ?: "",
                Math.max(0, floor(answer.leaveLastLevelAtMinute ?: 0.0).toInt()),
            ),
        )
        Spacer(Modifier.height(8.dp))
    }
    val controllingName = controlling?.name?.takeIf { it.isNotBlank() }
        ?: context.getString(R.string.tools_plan_diver_n, (answer.controllingIndex ?: 0) + 1)
    Text(
        stringResource(
            if (answer.divers.orEmpty().any { it.turnPressureBar != null }) {
                R.string.tools_plan_briefing_turn
            } else {
                R.string.tools_plan_briefing
            },
            controllingName,
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(Modifier.height(8.dp))
    val turn = answer.divers.orEmpty().any { it.turnPressureBar != null }
    answer.divers.orEmpty().forEachIndexed { index, diver ->
        PlanDiverResult(index, diver, turn, imperial, locale)
        Spacer(Modifier.height(8.dp))
    }
    if (answer.divers.orEmpty().any { it.belowRockBottom }) {
        Notice(stringResource(R.string.tools_plan_below_rock_bottom))
        Spacer(Modifier.height(8.dp))
    }
    if (levels.size > 1 && controlling != null) {
        val perLevel = controlling.rockBottom.mapIndexedNotNull { index, point ->
            val depth = levels.getOrNull(index) ?: return@mapIndexedNotNull null
            "${Formats.depth(context, depth, locale, imperial)}: ${Formats.pressure(context, point.pressureBar, locale, imperial)}"
        }
        if (perLevel.isNotEmpty()) {
            Muted(stringResource(R.string.tools_plan_rock_bottom_levels, perLevel.joinToString(", ")))
        }
    }

    Spacer(Modifier.height(16.dp))
    HeadingSmall(stringResource(R.string.tools_assumptions))
    answer.divers.orEmpty().forEachIndexed { index, diver ->
        val sac = diver.sacLpm
        val basedOn = sac.basedOn
        Text(
            if (basedOn != null) {
                context.resources.getQuantityString(
                    R.plurals.tools_plan_assumed_sac_based,
                    basedOn,
                    diver.name.ifBlank { context.getString(R.string.tools_plan_diver_n, index + 1) },
                    Formats.decimal(locale, sac.value, 1),
                    source(sac.source),
                    basedOn,
                )
            } else {
                context.getString(
                    R.string.tools_plan_assumed_sac,
                    diver.name.ifBlank { context.getString(R.string.tools_plan_diver_n, index + 1) },
                    Formats.decimal(locale, sac.value, 1),
                    source(sac.source),
                )
            },
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    val mix = answer.gas.o2
    Text(
        if (mix == 21.0) {
            stringResource(R.string.tools_plan_assumed_air, source(if (state.inputs.o2.isBlank()) "default" else "request"))
        } else {
            stringResource(
                R.string.tools_plan_assumed_nitrox,
                Formats.decimal(locale, mix, 0),
                source(if (state.inputs.o2.isBlank()) "default" else "request"),
            )
        },
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        stringResource(R.string.tools_assumed_rule, planRuleName(answer.used.gasRule.value.kind), source(answer.used.gasRule.source)),
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        stringResource(
            R.string.tools_assumed_water,
            waterInSentence(answer.used.water.value),
            source(answer.used.water.source),
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        stringResource(
            R.string.tools_plan_assumed_rates,
            Formats.decimal(locale, depthIn(answer.used.descentRateMPerMin.value, imperial), 0),
            rateUnit(imperial),
            source(answer.used.descentRateMPerMin.source),
            Formats.decimal(locale, depthIn(answer.used.ascentRateMPerMin.value, imperial), 0),
            rateUnit(imperial),
            source(answer.used.ascentRateMPerMin.source),
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(stringResource(R.string.tools_plan_assumed_model), style = MaterialTheme.typography.bodyMedium)

    answer.segments.orEmpty().let { segments ->
        if (segments.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            HeadingSmall(stringResource(R.string.tools_plan_segments_title))
            var runTime = 0.0
            segments.forEach { segment ->
                runTime += segment.minutes
                val place = when (segment.kind) {
                    "level" -> stringResource(R.string.tools_plan_level_at, Formats.depth(context, segment.depthM, locale, imperial))
                    else -> stringResource(
                        R.string.tools_plan_move,
                        stringResource(segmentName(segment.kind)),
                        Formats.depth(context, segment.fromDepthM, locale, imperial),
                        Formats.depth(context, segment.depthM, locale, imperial),
                    )
                }
                Text(
                    stringResource(
                        R.string.tools_plan_segment_line,
                        place,
                        Formats.decimal(locale, segment.minutes, 1),
                        Math.max(0, floor(runTime).toInt()),
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
    Spacer(Modifier.height(8.dp))
    if (state.syncedAt != null) {
        Muted(stringResource(R.string.tools_synced_at, formatSyncTime(state.syncedAt, locale, datePattern)))
    }
    Muted(stringResource(R.string.tools_estimate_short))
}

@Composable
private fun PlanDiverResult(index: Int, diver: ApiToolsGasPlanPost200ResponseDiversItem, turn: Boolean, imperial: Boolean, locale: Locale) {
    val context = LocalContext.current
    val fallbackName = stringResource(R.string.tools_plan_diver_n, index + 1)
    val name = diver.name.ifBlank { fallbackName }
    Card {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            if (diver.controlling) {
                Text(
                    stringResource(R.string.tools_plan_controls),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        Fact(stringResource(R.string.tools_plan_col_gas), stringResource(R.string.tools_plan_litres, Math.round(diver.litres).toString()))
        Fact(stringResource(R.string.tools_plan_col_end), Formats.pressure(context, diver.endPressureBar, locale, imperial))
        Fact(
            stringResource(R.string.tools_plan_col_rock_bottom),
            Formats.pressure(context, diver.rockBottom.maxOfOrNull { it.pressureBar } ?: 0.0, locale, imperial),
        )
        if (turn) {
            Fact(
                stringResource(R.string.tools_plan_col_turn),
                diver.turnPressureBar?.let { Formats.pressure(context, it, locale, imperial) } ?: "–",
            )
        }
        Fact(stringResource(R.string.tools_plan_col_ascend), Formats.pressure(context, diver.ascentPressureBar, locale, imperial))
        Fact(stringResource(R.string.tools_plan_col_margin), Formats.pressure(context, diver.marginBar, locale, imperial))
        if (diver.belowRockBottom) Muted(stringResource(R.string.tools_plan_below_rock_bottom_diver))
    }
}

@Composable
private fun PlanRuleExplanation(rule: String) {
    Heading(stringResource(R.string.tools_rule_title))
    Text(
        stringResource(
            when (rule) {
                RULE_RESERVE -> R.string.tools_plan_rule_reserve_about
                RULE_ASCENT_PRESSURE -> R.string.tools_plan_rule_ascent_about
                "halves" -> R.string.tools_plan_rule_halves_about
                "thirds" -> R.string.tools_plan_rule_thirds_about
                "sixths" -> R.string.tools_plan_rule_sixths_about
                else -> R.string.tools_plan_rule_rock_bottom_about
            },
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(Modifier.height(4.dp))
    Text(
        stringResource(
            when (rule) {
                RULE_RESERVE -> R.string.tools_plan_rule_reserve_limits
                RULE_ASCENT_PRESSURE -> R.string.tools_plan_rule_ascent_limits
                "halves" -> R.string.tools_plan_rule_halves_limits
                "thirds", "sixths" -> R.string.tools_plan_rule_overhead_limits
                else -> R.string.tools_plan_rule_rock_bottom_limits
            },
        ),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(Modifier.height(4.dp))
    Muted(stringResource(R.string.tools_rule_rock_bottom_always))
}

@Composable
private fun waterName(water: PlanningWater): String = stringResource(
    when (water) {
        PlanningWater.fresh -> R.string.water_fresh
        PlanningWater.en13319 -> R.string.water_en13319
        else -> R.string.water_salt
    },
)

@Composable
private fun waterInSentence(name: String): String = stringResource(
    when (name) {
        "fresh" -> R.string.water_in_sentence_fresh
        "en13319" -> R.string.water_in_sentence_en13319
        else -> R.string.water_in_sentence_salt
    },
)

@Composable
private fun planRuleName(rule: String): String = stringResource(
    when (rule) {
        RULE_RESERVE -> R.string.tools_rule_reserve
        RULE_ASCENT_PRESSURE -> R.string.tools_rule_ascent_pressure
        "halves" -> R.string.tools_plan_rule_halves
        "thirds" -> R.string.tools_plan_rule_thirds
        "sixths" -> R.string.tools_plan_rule_sixths
        else -> R.string.tools_rule_rock_bottom
    },
)

private fun segmentName(kind: String): Int = when (kind) {
    "descent" -> R.string.tools_plan_segment_descent
    "ascent" -> R.string.tools_plan_segment_ascent
    "safety_stop" -> R.string.tools_plan_segment_safety_stop
    else -> R.string.tools_plan_segment_descent
}

@Composable
private fun rateUnit(imperial: Boolean): String = stringResource(
    if (imperial) R.string.tools_plan_unit_rate_imperial else R.string.tools_plan_unit_rate_metric,
)
