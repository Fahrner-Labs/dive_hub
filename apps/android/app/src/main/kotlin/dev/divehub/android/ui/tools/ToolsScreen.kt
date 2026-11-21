package dev.divehub.android.ui.tools

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.annotation.StringRes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.dive.DiveDetailText
import dev.divehub.android.ui.formatSyncTime
import dev.divehub.api.models.ApiToolsBottomTimeGet200Response
import dev.divehub.api.models.ApiToolsGasGet200Response
import dev.divehub.shared.planning.PlanningWater
import dev.divehub.shared.tools.RULE_ASCENT_PRESSURE
import dev.divehub.shared.tools.RULE_RESERVE
import dev.divehub.shared.tools.RULE_ROCK_BOTTOM
import dev.divehub.shared.tools.ToolsError
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Which tool the Tools route shows; the gas numbers are its home. */
enum class ToolPage { Gas, Plan, Lead, Loadings }

/** The tools route: opens the synced store once so a Diver's values are there before the first keystroke. */
@Composable
fun ToolsRoute(
    viewModel: ToolsViewModel,
    planViewModel: GasPlanViewModel,
    leadViewModel: LeadEstimateViewModel,
    loadingsViewModel: LoadingsViewModel,
    units: String?,
    page: ToolPage,
    onPage: (ToolPage) -> Unit,
    onOpenDive: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = Formats.imperial(units, locale)
    LaunchedEffect(Unit) { viewModel.start(imperial) }
    LaunchedEffect(imperial) { viewModel.setUnits(imperial) }
    when (page) {
        ToolPage.Plan -> GasPlanRoute(
            viewModel = planViewModel,
            units = units,
            onBack = { onPage(ToolPage.Gas) },
        )
        ToolPage.Lead -> LeadEstimateRoute(
            viewModel = leadViewModel,
            units = units,
            onBack = { onPage(ToolPage.Gas) },
            onOpenDive = onOpenDive,
        )
        ToolPage.Loadings -> LoadingsRoute(
            viewModel = loadingsViewModel,
            onBack = { onPage(ToolPage.Gas) },
        )
        ToolPage.Gas -> ToolsScreen(
            state = state,
            imperial = imperial,
            onOpenPlan = { onPage(ToolPage.Plan) },
            onOpenLead = { onPage(ToolPage.Lead) },
            onOpenLoadings = { onPage(ToolPage.Loadings) },
            onChange = viewModel::update,
            onReload = viewModel::reload,
        )
    }
}

/**
 * Gas numbers and a bottom time, fully offline (FAH-274; ADR 0032). The notice is above the inputs and every answer
 * says it again; every answer names what it assumed and which limit binds. The screen draws only [state], so its
 * contract-visible states have screenshot tests.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(
    state: ToolsUiState,
    imperial: Boolean,
    onOpenPlan: () -> Unit,
    onOpenLead: () -> Unit,
    onOpenLoadings: () -> Unit,
    onChange: ((ToolsInputs) -> ToolsInputs) -> Unit,
    onReload: () -> Unit,
) {
    Scaffold(
        topBar = { DiveHubTopBar(title = stringResource(R.string.tools_title)) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            if (!state.loading) {
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
                } else if (state.storeEmpty) {
                    Spacer(Modifier.height(8.dp))
                    Card {
                        Text(
                            stringResource(R.string.tools_empty_store),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            ToolLinks(onOpenPlan, onOpenLead, onOpenLoadings)
            Spacer(Modifier.height(20.dp))
            if (state.loading) {
                Box(
                    Modifier.fillMaxWidth().padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    val label = stringResource(R.string.loading)
                    CircularProgressIndicator(Modifier.semantics { contentDescription = label })
                }
            } else {
                Inputs(state.inputs, imperial, onChange)
                Spacer(Modifier.height(20.dp))
                BottomTimeAnswer(state, imperial)
                Spacer(Modifier.height(20.dp))
                GasAnswer(state, imperial)
                Spacer(Modifier.height(20.dp))
                RuleExplanation(state.inputs.rule ?: RULE_ROCK_BOTTOM)
                Spacer(Modifier.height(12.dp))
                Text(
                    stringResource(R.string.tools_flying),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/**
 * The Tools root is where a Diver moves between tools (FAH-335): the sibling tools are a list here, not text buttons
 * in every subpage's app bar.
 */
@Composable
private fun ToolLinks(onOpenPlan: () -> Unit, onOpenLead: () -> Unit, onOpenLoadings: () -> Unit) {
    Heading(stringResource(R.string.tools_more_tools))
    Spacer(Modifier.height(4.dp))
    ToolLink(stringResource(R.string.tools_plan_title), onOpenPlan)
    ToolLink(stringResource(R.string.tools_lead_title), onOpenLead)
    ToolLink(stringResource(R.string.tools_loadings_title), onOpenLoadings)
}

@Composable
private fun ToolLink(title: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        trailingContent = {
            Icon(painterResource(R.drawable.ic_chevron_right), contentDescription = null)
        },
        modifier = Modifier.clickable(onClick = onClick),
    )
}

@Composable
internal fun Notice(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text,
            modifier = Modifier.padding(12.dp).semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
internal fun Card(content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(12.dp), content = { content() })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Inputs(inputs: ToolsInputs, imperial: Boolean, onChange: ((ToolsInputs) -> ToolsInputs) -> Unit) {
    Heading(stringResource(R.string.tools_inputs_title))
    Spacer(Modifier.height(8.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        NumberField(
            value = inputs.o2,
            label = stringResource(R.string.tools_oxygen),
            unit = stringResource(R.string.tools_unit_percent),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(o2 = value) } },
        )
        NumberField(
            value = inputs.helium,
            label = stringResource(R.string.tools_helium),
            unit = stringResource(R.string.tools_unit_percent),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(helium = value) } },
        )
    }
    NumberField(
        value = inputs.depth,
        label = stringResource(R.string.tools_depth),
        unit = depthUnit(imperial),
        modifier = Modifier.fillMaxWidth(),
        onChange = { value -> onChange { it.copy(depth = value) } },
    )

    Spacer(Modifier.height(12.dp))
    Text(stringResource(R.string.tools_cylinder_title), style = MaterialTheme.typography.titleSmall)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        NumberField(
            value = inputs.volume,
            label = stringResource(R.string.tools_volume),
            unit = stringResource(R.string.tools_unit_litres),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(volume = value) } },
        )
        NumberField(
            value = inputs.pressure,
            label = stringResource(R.string.tools_start_pressure),
            unit = pressureUnit(imperial),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(pressure = value) } },
        )
    }

    Spacer(Modifier.height(16.dp))
    Heading(stringResource(R.string.tools_settings_title))
    Spacer(Modifier.height(4.dp))
    Text(stringResource(R.string.tools_water), style = MaterialTheme.typography.labelLarge)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        WaterChip("fresh", inputs, onChange)
        WaterChip("salt", inputs, onChange)
        WaterChip("en13319", inputs, onChange)
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
        listOf(RULE_ROCK_BOTTOM, RULE_RESERVE, RULE_ASCENT_PRESSURE).forEach { rule ->
            FilterChip(
                selected = (inputs.rule ?: RULE_ROCK_BOTTOM) == rule,
                // Rock bottom is the route's default: leaving it unset keeps its source `default`, not `request`.
                onClick = { onChange { it.copy(rule = rule.takeUnless { name -> name == RULE_ROCK_BOTTOM }, bar = "") } },
                label = { Text(ruleName(rule)) },
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
            value = inputs.sac,
            label = stringResource(R.string.tools_sac),
            unit = stringResource(R.string.tools_unit_sac),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(sac = value) } },
        )
        NumberField(
            value = inputs.gf,
            label = stringResource(R.string.tools_gf_high),
            unit = stringResource(R.string.tools_unit_percent),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(gf = value) } },
        )
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        NumberField(
            value = inputs.ppo2Working,
            label = stringResource(R.string.tools_ppo2_working),
            unit = stringResource(R.string.tools_unit_pressure_metric),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(ppo2Working = value) } },
        )
        NumberField(
            value = inputs.ppo2Contingency,
            label = stringResource(R.string.tools_ppo2_contingency),
            unit = stringResource(R.string.tools_unit_pressure_metric),
            modifier = Modifier.weight(1f),
            onChange = { value -> onChange { it.copy(ppo2Contingency = value) } },
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = inputs.oxygenNarcotic,
            onCheckedChange = { value -> onChange { it.copy(oxygenNarcotic = value) } },
        )
        Text(stringResource(R.string.tools_oxygen_narcotic), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun WaterChip(name: String, inputs: ToolsInputs, onChange: ((ToolsInputs) -> ToolsInputs) -> Unit) {
    FilterChip(
        selected = inputs.water.name == name,
        onClick = { onChange { it.copy(water = PlanningWater.valueOf(name)) } },
        label = {
            Text(
                when (name) {
                    "fresh" -> stringResource(R.string.water_fresh)
                    "en13319" -> stringResource(R.string.water_en13319)
                    else -> stringResource(R.string.water_salt)
                },
            )
        },
    )
}

@Composable
internal fun NumberField(
    value: String,
    label: String,
    unit: String,
    modifier: Modifier,
    onChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        label = { Text(if (unit.isBlank()) label else "$label ($unit)") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    )
}

@Composable
private fun BottomTimeAnswer(state: ToolsUiState, imperial: Boolean) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    Heading(stringResource(R.string.tools_bottom_time))
    when {
        state.bottomError != null -> ErrorText(state.bottomError)
        state.bottom == null -> Muted(stringResource(R.string.tools_bottom_empty))
        else -> BottomTimeBody(state.bottom, state.personalized, state.syncedAt, imperial, locale)
    }
}

@Composable
private fun BottomTimeBody(
    answer: ApiToolsBottomTimeGet200Response,
    personalized: Boolean,
    syncedAt: String?,
    imperial: Boolean,
    locale: Locale,
) {
    val context = LocalContext.current
    val datePattern = stringResource(R.string.tools_date_pattern)
    val outside = answer.status == "outside_limits"
    if (outside) {
        Notice(stringResource(R.string.tools_bottom_outside))
        Spacer(Modifier.height(8.dp))
        Text(
            when (answer.reason) {
                "mod" -> stringResource(R.string.tools_bottom_reason_mod, answer.modM?.let { Formats.depth(context, it, locale, imperial) } ?: "–")
                "gas" -> stringResource(R.string.tools_bottom_reason_gas)
                else -> stringResource(R.string.tools_bottom_reason_depth)
            },
            style = MaterialTheme.typography.bodyLarge,
        )
        if (answer.modM != null) {
            Text(
                stringResource(R.string.tools_bottom_mod, Formats.depth(context, answer.modM!!, locale, imperial)),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    } else {
        answer.recentDiveEndedAt?.let {
            Notice(stringResource(R.string.tools_bottom_recent, dateTime(it, locale, datePattern)))
            Spacer(Modifier.height(8.dp))
        }
        Text(
            stringResource(R.string.tools_bottom_minutes, answer.minutes ?: 0),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
        )
        answer.binding?.takeIf { it.isNotEmpty() }?.let { binding ->
            Text(
                stringResource(
                    R.string.tools_bottom_limited_by,
                    binding.map { limitWord(it, context) }.reduce { left, right ->
                        "$left ${context.getString(R.string.tools_bottom_and)} $right"
                    },
                ),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        if (answer.ppO2AboveWorking == true) {
            Notice(stringResource(R.string.tools_bottom_above_working, Formats.decimal(locale, answer.ppO2AtDepth ?: 0.0, 2)))
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.tools_limits_title), style = MaterialTheme.typography.titleSmall)
        LimitRow(stringResource(R.string.tools_limit_name_ndl), answer.limits?.ndl)
        LimitRow(stringResource(R.string.tools_limit_name_oxygen), answer.limits?.oxygen)
        LimitRow(
            stringResource(R.string.tools_limit_name_gas),
            answer.limits?.gas,
            noLimit = stringResource(R.string.tools_limit_no_cylinder),
        )
        answer.ascentPressureBar?.let {
            Muted(
                stringResource(
                    if (answer.used.gasRule.value.kind == RULE_ROCK_BOTTOM) {
                        R.string.tools_bottom_ascent_rock_bottom
                    } else {
                        R.string.tools_bottom_ascent
                    },
                    Formats.pressure(context, it, locale, imperial),
                ),
            )
        }
        answer.otu?.let { Muted(stringResource(R.string.tools_bottom_otu, Math.round(it).toInt())) }
    }
    Spacer(Modifier.height(8.dp))
    Assumptions(answer, personalized, syncedAt, imperial, locale)
    Spacer(Modifier.height(8.dp))
    Muted(stringResource(R.string.tools_estimate_short))
}

@Composable
private fun LimitRow(name: String, value: Double?, noLimit: String = stringResource(R.string.tools_limit_none)) {
    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Text(
            value?.let { stringResource(R.string.tools_bottom_minutes, it.toInt()) } ?: noLimit,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun Assumptions(
    answer: ApiToolsBottomTimeGet200Response,
    personalized: Boolean,
    syncedAt: String?,
    imperial: Boolean,
    locale: Locale,
) {
    val context = LocalContext.current
    val used = answer.used
    HeadingSmall(stringResource(R.string.tools_assumptions))
    if (personalized) {
        val pattern = stringResource(R.string.tools_date_pattern)
        Muted(
            if (syncedAt != null) {
                stringResource(R.string.tools_synced_at, formatSyncTime(syncedAt, locale, pattern))
            } else {
                stringResource(R.string.tools_not_synced)
            },
        )
    }
    Text(
        stringResource(R.string.tools_assumed_gf, Formats.decimal(locale, used.gfHigh.value, 0), source(used.gfHigh.source)),
        style = MaterialTheme.typography.bodyMedium,
    )
    val sac = used.sacLpm
    Text(
        if (sac.basedOn != null) {
            context.resources.getQuantityString(
                R.plurals.tools_assumed_sac_based,
                sac.basedOn!!,
                Formats.decimal(locale, sac.value, 1),
                source(sac.source),
                sac.basedOn!!,
            )
        } else {
            context.getString(
                R.string.tools_assumed_sac,
                Formats.decimal(locale, sac.value, 1),
                source(sac.source),
            )
        },
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        stringResource(
            R.string.tools_assumed_water,
            DiveDetailText.waterInSentence(context, used.water.value),
            source(used.water.source),
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        if (used.altitudeM.source == "default" && used.altitudeM.value == 0.0) {
            stringResource(R.string.tools_assumed_no_altitude)
        } else {
            stringResource(
                R.string.tools_assumed_altitude,
                Formats.depth(context, used.altitudeM.value, locale, imperial),
                source(used.altitudeM.source),
            )
        },
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        stringResource(
            R.string.tools_assumed_rule,
            ruleName(used.gasRule.value.kind),
            source(used.gasRule.source),
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(stringResource(R.string.tools_assumed_model), style = MaterialTheme.typography.bodyMedium)
}

@Composable
private fun GasAnswer(state: ToolsUiState, imperial: Boolean) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    Heading(stringResource(R.string.tools_gas_numbers))
    when {
        state.gasError != null -> ErrorText(state.gasError)
        state.gas == null -> Muted(stringResource(R.string.tools_gas_empty))
        else -> GasBody(state.gas, imperial, locale)
    }
}

@Composable
private fun GasBody(answer: ApiToolsGasGet200Response, imperial: Boolean, locale: Locale) {
    val context = LocalContext.current
    if (answer.hypoxic) {
        Notice(stringResource(R.string.tools_gas_hypoxic))
        Spacer(Modifier.height(8.dp))
    }
    Fact(
        stringResource(R.string.tools_gas_mod_working, Formats.decimal(locale, answer.ppO2Limits.working, 2)),
        Formats.depth(context, answer.modWorkingM, locale, imperial),
    )
    Fact(
        stringResource(R.string.tools_gas_mod_contingency, Formats.decimal(locale, answer.ppO2Limits.contingency, 2)),
        Formats.depth(context, answer.modContingencyM, locale, imperial),
    )
    answer.atDepth?.let { atDepth ->
        Spacer(Modifier.height(4.dp))
        Fact(
            stringResource(R.string.tools_gas_ppo2, Formats.depth(context, atDepth.depthM, locale, imperial)),
            stringResource(R.string.tools_pressure_bar, Formats.decimal(locale, atDepth.ppO2, 2)),
        )
        Fact(
            stringResource(R.string.tools_gas_best_mix, Formats.depth(context, atDepth.depthM, locale, imperial)),
            stringResource(R.string.tools_percent_value, atDepth.bestMixPercent.toString()),
        )
        Fact(
            stringResource(R.string.tools_gas_ead),
            Formats.depth(context, atDepth.eadM, locale, imperial),
        )
        Fact(
            stringResource(R.string.tools_gas_end),
            Formats.depth(context, atDepth.endM, locale, imperial),
        )
    }
    Spacer(Modifier.height(8.dp))
    Muted(stringResource(R.string.tools_gas_rounded))
    Muted(stringResource(R.string.tools_estimate_short))
}

@Composable
internal fun Fact(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun RuleExplanation(rule: String) {
    Heading(stringResource(R.string.tools_rule_title))
    Text(
        stringResource(
            when (rule) {
                RULE_RESERVE -> R.string.tools_rule_reserve_explanation
                RULE_ASCENT_PRESSURE -> R.string.tools_rule_ascent_explanation
                else -> R.string.tools_rule_rock_bottom_explanation
            },
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(Modifier.height(4.dp))
    Muted(stringResource(R.string.tools_rule_rock_bottom_always))
}

@Composable
internal fun ErrorText(code: String) {
    Text(
        stringResource(
            when (code) {
                ToolsError.INVALID_INPUT -> R.string.tools_error_invalid_input
                ToolsError.GAS_NOT_BREATHABLE -> R.string.tools_error_gas_not_breathable
                ToolsError.SUIT_INVALID -> R.string.tools_error_suit_invalid
                else -> R.string.tools_error_unknown
            },
        ),
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Assertive },
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
internal fun Heading(text: String) {
    Text(
        text,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
internal fun HeadingSmall(text: String) {
    Text(
        text,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
internal fun Muted(text: String) {
    Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
internal fun depthUnit(imperial: Boolean): String = stringResource(
    if (imperial) R.string.tools_unit_depth_imperial else R.string.tools_unit_depth_metric,
)

@Composable
internal fun pressureUnit(imperial: Boolean): String = stringResource(
    if (imperial) R.string.tools_unit_pressure_imperial else R.string.tools_unit_pressure_metric,
)

@Composable
private fun ruleName(rule: String): String = stringResource(
    when (rule) {
        RULE_RESERVE -> R.string.tools_rule_reserve
        RULE_ASCENT_PRESSURE -> R.string.tools_rule_ascent_pressure
        else -> R.string.tools_rule_rock_bottom
    },
)

private fun limitWord(limit: String, context: Context): String = context.getString(
    when (limit) {
        "ndl" -> R.string.tools_limit_ndl
        "oxygen" -> R.string.tools_limit_oxygen
        else -> R.string.tools_limit_gas
    },
)

@Composable
internal fun source(source: String): String = stringResource(
    when (source) {
        "request" -> R.string.tools_source_request
        "recording" -> R.string.tools_source_recording
        "logbook" -> R.string.tools_source_logbook
        "site" -> R.string.tools_source_site
        else -> R.string.tools_source_default
    },
)

/** "18 Nov 2026" in UTC, for a Dive's own date. */
@Composable
internal fun dayDate(iso: String): String {
    val locale = LocalContext.current.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = stringResource(R.string.tools_day_pattern)
    return try {
        Instant.parse(iso).atZone(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(pattern, locale))
    } catch (_: Exception) {
        iso
    }
}

/**
 * "Based on dives synced as of <date-time>", with "(dive details still syncing)" while the details phase is
 * incomplete and "(profiles still syncing)" while the profiles phase is (client contract, Offline reads; FAH-276,
 * FAH-299). Every personalized result carries it.
 */
@Composable
internal fun StalenessLabel(
    syncedAt: String?,
    profilesComplete: Boolean,
    @StringRes notSynced: Int = R.string.tools_not_synced,
    detailsComplete: Boolean = true,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = stringResource(R.string.tools_date_pattern)
    val text = if (syncedAt != null) {
        stringResource(R.string.tools_synced_at, formatSyncTime(syncedAt, locale, pattern))
    } else {
        stringResource(notSynced)
    }
    val markers = buildList {
        if (!detailsComplete) add(stringResource(R.string.tools_details_syncing))
        if (!profilesComplete) add(stringResource(R.string.tools_profiles_syncing))
    }
    Muted(if (markers.isEmpty()) text else "$text ${markers.joinToString(" ")}")
}

/**
 * "18 Nov 2026, 09:45" in UTC, for the dive-time instants these pages show; a sync timestamp reads in the device's
 * zone instead ([formatSyncTime]).
 */
internal fun dateTime(iso: String, locale: Locale, contextPattern: String): String = try {
    val formatter = DateTimeFormatter.ofPattern(contextPattern, locale)
    Instant.parse(iso).atZone(ZoneOffset.UTC).format(formatter)
} catch (_: Exception) {
    iso
}
