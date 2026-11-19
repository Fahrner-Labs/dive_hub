package dev.divehub.android.ui.tools

import androidx.activity.compose.BackHandler
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
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200Response
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseMissingItem
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.SiteWaterType
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.planning.SuitUndergarment
import java.util.Locale

/** The lead estimate route; the owned Diver's history is read from the synced store when the screen opens. */
@Composable
fun LeadEstimateRoute(
    viewModel: LeadEstimateViewModel,
    units: String?,
    onBack: () -> Unit,
    onOpenDive: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = Formats.imperial(units, locale)
    LaunchedEffect(Unit) { viewModel.start(imperial) }
    LaunchedEffect(imperial) { viewModel.setUnits(imperial) }
    BackHandler { onBack() }
    LeadEstimateScreen(
        state = state,
        imperial = imperial,
        onBack = onBack,
        onOpenDive = onOpenDive,
        onChange = viewModel::update,
        onAddCylinder = viewModel::addCylinder,
        onRemoveCylinder = viewModel::removeCylinder,
        onReload = viewModel::reload,
    )
}

/**
 * The lead estimate against the synced store (FAH-276; ADR 0031): the conditions, the answer with its range, reasons
 * and confidence in words, the weight check in every state, and the staleness label on every personalized result.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadEstimateScreen(
    state: LeadEstimateUiState,
    imperial: Boolean,
    onBack: () -> Unit,
    onOpenDive: (String) -> Unit,
    onChange: ((LeadInputs) -> LeadInputs) -> Unit,
    onAddCylinder: () -> Unit,
    onRemoveCylinder: (Int) -> Unit,
    onReload: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tools_lead_title)) },
                navigationIcon = { TextButton(onClick = onBack) { Text(stringResource(R.string.tools_tools_back)) } },
            )
        },
    ) { padding ->
        if (state.loading) {
            Column(
                Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                val label = stringResource(R.string.loading)
                CircularProgressIndicator(Modifier.semantics { contentDescription = label })
            }
            return@Scaffold
        }
        Column(
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
            Spacer(Modifier.height(16.dp))
            Conditions(state.inputs, imperial, onChange, onAddCylinder, onRemoveCylinder)
            Spacer(Modifier.height(20.dp))
            Heading(stringResource(R.string.tools_lead_answer))
            if (state.personalized) {
                StalenessLabel(state.syncedAt, state.profilesComplete, R.string.tools_not_fully_synced)
            }
            when {
                state.error != null -> ErrorText(state.error)
                state.answer == null -> Muted(stringResource(R.string.tools_lead_empty))
                else -> Answer(state.answer, imperial, onOpenDive)
            }
            Spacer(Modifier.height(20.dp))
            WeightCheck(state.answer, imperial)
            Spacer(Modifier.height(12.dp))
            Muted(stringResource(R.string.tools_estimate_short))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Conditions(
    inputs: LeadInputs,
    imperial: Boolean,
    onChange: ((LeadInputs) -> LeadInputs) -> Unit,
    onAddCylinder: () -> Unit,
    onRemoveCylinder: (Int) -> Unit,
) {
    Heading(stringResource(R.string.tools_lead_conditions))
    Spacer(Modifier.height(4.dp))
    Text(stringResource(R.string.tools_water), style = MaterialTheme.typography.labelLarge)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SiteWaterType.entries.forEach { water ->
            FilterChip(
                selected = inputs.water == water,
                onClick = { onChange { it.copy(water = water) } },
                label = { Text(waterName(water)) },
            )
        }
    }
    Spacer(Modifier.height(8.dp))
    Text(stringResource(R.string.tools_lead_suit), style = MaterialTheme.typography.labelLarge)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SuitType.entries.forEach { suit ->
            FilterChip(
                selected = inputs.suit == suit,
                onClick = {
                    onChange {
                        it.copy(
                            suit = suit,
                            thickness = it.thickness.takeIf { _ -> suit == SuitType.wetsuit || suit == SuitType.semidry }.orEmpty(),
                            hood = it.hood.takeIf { _ -> suit != SuitType.none },
                            undergarment = it.undergarment.takeIf { _ -> suit == SuitType.drysuit },
                        )
                    }
                },
                label = { Text(suitName(suit)) },
            )
        }
    }
    if (inputs.suit == SuitType.wetsuit || inputs.suit == SuitType.semidry) {
        NumberField(
            value = inputs.thickness,
            label = stringResource(R.string.tools_lead_thickness),
            unit = "mm",
            modifier = Modifier.fillMaxWidth(),
            onChange = { value -> onChange { it.copy(thickness = value) } },
        )
    }
    if (inputs.suit == SuitType.drysuit) {
        Text(stringResource(R.string.tools_lead_undergarment), style = MaterialTheme.typography.labelLarge)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(null, SuitUndergarment.light, SuitUndergarment.medium, SuitUndergarment.heavy).forEach { undergarment ->
                FilterChip(
                    selected = inputs.undergarment == undergarment,
                    onClick = { onChange { it.copy(undergarment = undergarment) } },
                    label = { Text(undergarmentName(undergarment)) },
                )
            }
        }
    }
    if (inputs.suit != SuitType.none) {
        Text(stringResource(R.string.tools_lead_hood), style = MaterialTheme.typography.labelLarge)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(null, true, false).forEach { hood ->
                FilterChip(
                    selected = inputs.hood == hood,
                    onClick = { onChange { it.copy(hood = hood) } },
                    label = { Text(hoodName(hood)) },
                )
            }
        }
    }

    Spacer(Modifier.height(16.dp))
    Heading(stringResource(R.string.tools_lead_cylinders))
    inputs.cylinders.forEachIndexed { index, cylinder ->
        Card {
            Text(
                stringResource(R.string.tools_lead_cylinder_n, index + 1),
                style = MaterialTheme.typography.titleSmall,
            )
            NumberField(
                value = cylinder.volume,
                label = stringResource(R.string.tools_lead_volume),
                unit = stringResource(R.string.tools_unit_litres),
                modifier = Modifier.fillMaxWidth(),
                onChange = { value ->
                    onChange { it.copy(cylinders = it.cylinders.mapIndexed { i, row -> if (i == index) row.copy(volume = value) else row }) }
                },
            )
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(null, CylinderMaterial.aluminium, CylinderMaterial.steel, CylinderMaterial.carbon).forEach { material ->
                    FilterChip(
                        selected = cylinder.material == material,
                        onClick = {
                            onChange {
                                it.copy(cylinders = it.cylinders.mapIndexed { i, row -> if (i == index) row.copy(material = material) else row })
                            }
                        },
                        label = { Text(materialName(material)) },
                    )
                }
            }
            if (inputs.cylinders.size > 1) {
                TextButton(onClick = { onRemoveCylinder(index) }) {
                    Text(stringResource(R.string.tools_lead_remove_cylinder, index + 1))
                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
    if (inputs.cylinders.size < 4) {
        TextButton(onClick = onAddCylinder) { Text(stringResource(R.string.tools_lead_add_cylinder)) }
    }
}

@Composable
private fun Answer(answer: ApiDiversIdLeadEstimateGet200Response, imperial: Boolean, onOpenDive: (String) -> Unit) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val unit = answer.unit
    val total = answer.total
    val range = answer.range
    if (total == null || range == null) {
        Card {
            Text(stringResource(R.string.tools_lead_no_number), style = MaterialTheme.typography.titleSmall)
            Text(
                stringResource(R.string.tools_lead_no_number_body),
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    } else {
        Text(stringResource(R.string.tools_lead_starting_point), style = MaterialTheme.typography.labelLarge)
        Text(
            amount(locale, total, unit),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            stringResource(
                R.string.tools_lead_range,
                amount(locale, range.low, unit),
                amount(locale, range.high, unit),
            ),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            stringResource(R.string.tools_lead_confidence, levelName(answer.confidence)),
            style = MaterialTheme.typography.bodyMedium,
        )
        Muted(stringResource(R.string.tools_lead_not_certain))
    }

    val basis = answer.basis
    val basisDives = basis?.dives
    val rule = basis?.rule
    val personalOffset = basis?.personalOffset
    val offsetFromSuits = basis?.offsetFromSuits
    if (basis?.kind == "dives" && basisDives != null) {
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.tools_lead_basis_dives, basisDives.size), style = MaterialTheme.typography.bodyMedium)
        basisDives.forEach { dive ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { onOpenDive(dive.diveId) }) {
                    val number = dive.number
                    Text(
                        if (number != null) {
                            stringResource(R.string.tools_lead_dive_label, number, dayDate(dive.startsAt))
                        } else {
                            dayDate(dive.startsAt)
                        },
                    )
                }
                Muted(
                    stringResource(
                        R.string.tools_lead_dive_line,
                        detail(locale, dive.carried, unit),
                        detail(locale, dive.needed, unit),
                        Formats.decimal(locale, dive.weight, 2),
                    ),
                )
            }
        }
    } else if (basis?.kind == "rule_of_thumb" && rule != null) {
        Spacer(Modifier.height(8.dp))
        Text(
            stringResource(R.string.tools_lead_basis_rule, amount(locale, rule, unit)),
            style = MaterialTheme.typography.bodyMedium,
        )
        if (personalOffset != null && offsetFromSuits != null) {
            Text(
                context.resources.getQuantityString(
                    R.plurals.tools_lead_basis_offset,
                    offsetFromSuits,
                    signed(locale, personalOffset, unit),
                    offsetFromSuits,
                ),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }

    if (total != null && answer.adjustments.isNotEmpty()) {
        Spacer(Modifier.height(8.dp))
        HeadingSmall(stringResource(R.string.tools_lead_adjustments))
        answer.adjustments.forEach { adjustment -> Adjustment(adjustment, unit) }
    }

    Spacer(Modifier.height(8.dp))
    Muted(
        stringResource(
            R.string.tools_lead_reserve,
            Formats.pressure(context, answer.reserve.bar, locale, imperial),
            stringResource(if (answer.reserve.fromDives) R.string.tools_lead_reserve_dives else R.string.tools_lead_reserve_assumed),
        ),
    )

    Missing(answer.missing, skipBodyWeight = total == null)
    if (answer.leftOut.noWaterType > 0) {
        Spacer(Modifier.height(8.dp))
        Notice(
            context.resources.getQuantityString(
                R.plurals.tools_lead_left_out,
                answer.leftOut.noWaterType,
                answer.leftOut.noWaterType,
            ),
        )
    }
}

@Composable
private fun Adjustment(adjustment: ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, unit: String) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    Text(
        when (adjustment.kind) {
            "cylinder" -> stringResource(
                R.string.tools_lead_cylinder_adjustment,
                signed(locale, adjustment.amount, unit),
            ) + " " + stringResource(R.string.tools_lead_cylinder_reason)
            "water" -> stringResource(
                R.string.tools_lead_water_adjustment,
                waterInSentence(adjustment.water),
                signed(locale, adjustment.amount, unit),
            ) + " " + stringResource(R.string.tools_lead_water_reason)
            else -> stringResource(
                R.string.tools_lead_offset_adjustment,
                signed(locale, adjustment.amount, unit),
            ) + " " + stringResource(R.string.tools_lead_offset_reason)
        },
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
private fun Missing(missing: List<ApiDiversIdLeadEstimateGet200ResponseMissingItem>, skipBodyWeight: Boolean) {
    val context = LocalContext.current
    val items = if (skipBodyWeight) missing.filter { it.kind != "body_weight" } else missing
    if (items.isEmpty()) return
    Spacer(Modifier.height(8.dp))
    HeadingSmall(stringResource(R.string.tools_lead_missing))
    items.forEach { item ->
        Text(
            when (item.kind) {
                "body_weight" -> stringResource(R.string.tools_lead_missing_body_weight)
                "cylinder_material" -> if (item.planned == true) {
                    stringResource(R.string.tools_lead_missing_material_planned)
                } else {
                    context.resources.getQuantityString(
                        R.plurals.tools_lead_missing_material_dives,
                        item.diveIds?.size ?: 0,
                        item.diveIds?.size ?: 0,
                    )
                }
                "cylinder" -> context.resources.getQuantityString(
                    R.plurals.tools_lead_missing_cylinder,
                    item.diveIds?.size ?: 0,
                    item.diveIds?.size ?: 0,
                )
                "suit_thickness" -> stringResource(R.string.tools_lead_missing_thickness)
                else -> stringResource(R.string.tools_lead_missing_undergarment)
            },
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun WeightCheck(answer: ApiDiversIdLeadEstimateGet200Response?, imperial: Boolean) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val reserveBar = answer?.weightCheck?.reserveBar ?: 50.0
    Heading(stringResource(R.string.tools_lead_check))
    Text(stringResource(R.string.tools_lead_check_intro), style = MaterialTheme.typography.bodyMedium)
    Text(
        stringResource(R.string.tools_lead_step_eye),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
    )
    Text(
        stringResource(
            R.string.tools_lead_step_neutral,
            Formats.pressure(context, reserveBar, locale, imperial),
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
private fun waterName(water: SiteWaterType): String = stringResource(
    when (water) {
        SiteWaterType.fresh -> R.string.water_fresh
        SiteWaterType.brackish -> R.string.water_brackish
        SiteWaterType.salt -> R.string.water_salt
    },
)

@Composable
private fun waterInSentence(water: String?): String = stringResource(
    when (water) {
        "fresh" -> R.string.water_in_sentence_fresh
        "brackish" -> R.string.water_in_sentence_brackish
        else -> R.string.water_in_sentence_salt
    },
)

@Composable
private fun suitName(suit: SuitType): String = stringResource(
    when (suit) {
        SuitType.none -> R.string.tools_suit_none
        SuitType.skin -> R.string.tools_suit_skin
        SuitType.wetsuit -> R.string.tools_suit_wetsuit
        SuitType.semidry -> R.string.tools_suit_semidry
        SuitType.drysuit -> R.string.tools_suit_drysuit
    },
)

@Composable
private fun undergarmentName(undergarment: SuitUndergarment?): String = stringResource(
    when (undergarment) {
        SuitUndergarment.light -> R.string.tools_undergarment_light
        SuitUndergarment.medium -> R.string.tools_undergarment_medium
        SuitUndergarment.heavy -> R.string.tools_undergarment_heavy
        null -> R.string.tools_lead_not_said
    },
)

@Composable
private fun hoodName(hood: Boolean?): String = stringResource(
    when (hood) {
        true -> R.string.tools_lead_hood_yes
        false -> R.string.tools_lead_hood_no
        null -> R.string.tools_lead_not_said
    },
)

@Composable
private fun materialName(material: CylinderMaterial?): String = stringResource(
    when (material) {
        CylinderMaterial.aluminium -> R.string.tools_material_aluminium
        CylinderMaterial.steel -> R.string.tools_material_steel
        CylinderMaterial.carbon -> R.string.tools_material_carbon
        null -> R.string.tools_lead_not_said
    },
)

@Composable
private fun levelName(level: String): String = stringResource(
    when (level) {
        "high" -> R.string.tools_lead_level_high
        "medium" -> R.string.tools_lead_level_medium
        else -> R.string.tools_lead_level_low
    },
)

/** Amounts are already in the answer's unit and rounded: show them, never convert again. */
private fun amount(locale: Locale, value: Double, unit: String): String =
    "${Formats.decimal(locale, value, if (unit == "lb") 0 else 1)} ${unitLabel(unit)}"

private fun detail(locale: Locale, value: Double, unit: String): String =
    "${Formats.decimal(locale, value, 1)} ${unitLabel(unit)}"

private fun signed(locale: Locale, value: Double, unit: String): String {
    val sign = when {
        value > 0 -> "+"
        value < 0 -> "−"
        else -> ""
    }
    return "$sign${detail(locale, kotlin.math.abs(value), unit)}"
}

private fun unitLabel(unit: String): String = if (unit == "lb") "lb" else "kg"
