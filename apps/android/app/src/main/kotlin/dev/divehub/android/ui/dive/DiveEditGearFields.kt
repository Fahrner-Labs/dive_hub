package dev.divehub.android.ui.dive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.LargeTextFontScale
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.SecondaryButton
import dev.divehub.android.ui.tools.NumberField
import dev.divehub.shared.dive.CylinderProblem
import dev.divehub.shared.dive.DiveEditCylinder
import dev.divehub.shared.dive.DiveEditDraft
import dev.divehub.shared.dive.DiveEditLead
import dev.divehub.shared.dive.DiveEditSeriesOption
import dev.divehub.shared.tools.massFromDisplay
import dev.divehub.shared.tools.massIn
import dev.divehub.shared.tools.pressureFromDisplay
import dev.divehub.shared.tools.pressureIn
import java.util.Locale

private val PRESSURE_MATERIALS = listOf("aluminium", "steel", "carbon")
private val LEAD_PLACEMENTS = listOf("belt", "integrated", "trim", "ankle", "backplate", "other")

/** The Dive's Cylinders in its edit form: each Cylinder in its order, the way the API takes the whole list (T8b). */
@Composable
internal fun CylindersFields(
    cylinders: List<DiveEditCylinder>,
    seriesOptions: List<DiveEditSeriesOption>,
    imperial: Boolean,
    locale: Locale,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
) {
    if (cylinders.isEmpty()) {
        Text(
            stringResource(R.string.dive_edit_no_cylinders),
            style = DiveHubText.body,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(Spacing.s3))
    }
    cylinders.forEachIndexed { index, cylinder ->
        CylinderFields(
            index = index,
            cylinder = cylinder,
            seriesOptions = seriesOptions,
            imperial = imperial,
            locale = locale,
            onChange = { changed ->
                onEdit { draft ->
                    draft.setCylinders(draft.cylinders.toMutableList().also { it[index] = changed })
                }
            },
            onRemove = {
                onEdit { draft ->
                    draft.setCylinders(draft.cylinders.toMutableList().also { it.removeAt(index) })
                }
            },
        )
        if (index < cylinders.lastIndex) Spacer(Modifier.height(Spacing.s5))
    }
    if (cylinders.size < MAX_CYLINDERS) {
        Spacer(Modifier.height(Spacing.s4))
        SecondaryButton(
            stringResource(R.string.dive_edit_add_cylinder),
            onClick = { onEdit { it.setCylinders(it.cylinders + DiveEditCylinder()) } },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CylinderFields(
    index: Int,
    cylinder: DiveEditCylinder,
    seriesOptions: List<DiveEditSeriesOption>,
    imperial: Boolean,
    locale: Locale,
    onChange: (DiveEditCylinder) -> Unit,
    onRemove: () -> Unit,
) {
    val pressureUnit = if (imperial) {
        stringResource(R.string.tools_unit_pressure_imperial)
    } else {
        stringResource(R.string.tools_unit_pressure_metric)
    }
    Text(
        stringResource(R.string.dive_cylinder_n, index + 1),
        modifier = Modifier.semantics { heading() },
        style = DiveHubText.bodyStrong,
    )
    if (cylinder.fromPod) {
        Text(
            stringResource(R.string.dive_cylinder_from_pod),
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
    Spacer(Modifier.height(Spacing.s3))
    FieldPair(
        first = {
            GearNumberField(
                rowKey = cylinder.key,
                field = "volume",
                label = stringResource(R.string.dive_edit_cylinder_volume),
                unit = stringResource(R.string.dive_edit_unit_litres),
                value = cylinder.volumeL,
                locale = locale,
                onChange = { onChange(cylinder.copy(volumeL = it)) },
            )
        },
        second = {
            GearNumberField(
                rowKey = cylinder.key,
                field = "workingPressure",
                label = stringResource(R.string.dive_edit_cylinder_working_pressure),
                unit = pressureUnit,
                value = cylinder.workingPressureBar,
                locale = locale,
                display = { pressureIn(it, imperial) },
                parse = { pressureFromDisplay(it, imperial) },
                onChange = { onChange(cylinder.copy(workingPressureBar = it)) },
            )
        },
    )
    Spacer(Modifier.height(Spacing.s2))
    FieldPair(
        first = {
            GearNumberField(
                rowKey = cylinder.key,
                field = "startPressure",
                label = stringResource(R.string.dive_edit_cylinder_start_pressure),
                unit = pressureUnit,
                value = cylinder.startPressureBar,
                locale = locale,
                display = { pressureIn(it, imperial) },
                parse = { pressureFromDisplay(it, imperial) },
                onChange = { onChange(cylinder.copy(startPressureBar = it)) },
            )
        },
        second = {
            GearNumberField(
                rowKey = cylinder.key,
                field = "endPressure",
                label = stringResource(R.string.dive_edit_cylinder_end_pressure),
                unit = pressureUnit,
                value = cylinder.endPressureBar,
                locale = locale,
                display = { pressureIn(it, imperial) },
                parse = { pressureFromDisplay(it, imperial) },
                onChange = { onChange(cylinder.copy(endPressureBar = it)) },
            )
        },
    )
    Spacer(Modifier.height(Spacing.s2))
    FieldPair(
        first = {
            GearNumberField(
                rowKey = cylinder.key,
                field = "oxygen",
                label = stringResource(R.string.dive_edit_cylinder_oxygen),
                unit = stringResource(R.string.dive_edit_unit_percent),
                value = cylinder.o2,
                locale = locale,
                onChange = { onChange(cylinder.copy(o2 = it)) },
            )
        },
        second = {
            GearNumberField(
                rowKey = cylinder.key,
                field = "helium",
                label = stringResource(R.string.dive_edit_cylinder_helium),
                unit = stringResource(R.string.dive_edit_unit_percent),
                value = cylinder.he,
                locale = locale,
                onChange = { onChange(cylinder.copy(he = it)) },
            )
        },
    )
    Spacer(Modifier.height(Spacing.s3))
    Text(
        stringResource(R.string.dive_edit_cylinder_material),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        verticalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        EditChip(
            label = stringResource(R.string.dive_edit_not_known),
            selected = cylinder.material == null,
            onClick = { onChange(cylinder.copy(material = null)) },
        )
        PRESSURE_MATERIALS.forEach { material ->
            EditChip(
                label = stringResource(materialLabel(material)),
                selected = cylinder.material == material,
                onClick = { onChange(cylinder.copy(material = material)) },
            )
        }
    }
    if (seriesOptions.isNotEmpty() || cylinder.series != null) {
        Spacer(Modifier.height(Spacing.s3))
        Text(
            stringResource(R.string.dive_edit_cylinder_series),
            style = DiveHubText.label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
            verticalArrangement = Arrangement.spacedBy(Spacing.s2),
        ) {
            EditChip(
                label = stringResource(R.string.dive_edit_cylinder_no_series),
                selected = cylinder.series == null,
                onClick = { onChange(cylinder.copy(series = null)) },
            )
            seriesOptions.forEach { option ->
                EditChip(
                    label = seriesLabel(option),
                    selected = cylinder.series == option.series,
                    onClick = { onChange(cylinder.copy(series = option.series)) },
                )
            }
        }
    }
    cylinder.problem()?.let { problem ->
        Spacer(Modifier.height(Spacing.s2))
        Text(
            stringResource(problemText(problem)),
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = DiveHubText.body,
            color = MaterialTheme.colorScheme.error,
        )
    }
    Spacer(Modifier.height(Spacing.s2))
    QuietButton(
        stringResource(R.string.dive_edit_remove_cylinder, index + 1),
        onClick = onRemove,
    )
}

/** The Dive's Lead entries in its edit form: the whole list, with an entry left empty saving nothing (T8b). */
@Composable
internal fun LeadFields(
    lead: List<DiveEditLead>,
    imperial: Boolean,
    locale: Locale,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
) {
    val massUnit = if (imperial) {
        stringResource(R.string.dive_edit_unit_pounds)
    } else {
        stringResource(R.string.dive_edit_unit_kilograms)
    }
    Text(
        stringResource(R.string.dive_lead),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(
        stringResource(R.string.dive_edit_lead_help),
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(Modifier.height(Spacing.s2))
    lead.forEachIndexed { index, entry ->
        GearNumberField(
            rowKey = entry.key,
            field = "amount",
            label = if (lead.size > 1) {
                stringResource(R.string.dive_edit_lead_n, index + 1)
            } else {
                stringResource(R.string.dive_lead)
            },
            unit = massUnit,
            value = entry.amountKg,
            locale = locale,
            display = { massIn(it, imperial) },
            parse = { massFromDisplay(it, imperial) },
            onChange = { amount ->
                onEdit { draft ->
                    draft.setLead(draft.lead.toMutableList().also { it[index] = entry.copy(amountKg = amount) })
                }
            },
        )
        Spacer(Modifier.height(Spacing.s2))
        PlacementChips(
            placement = entry.placement,
            onChange = { placement ->
                onEdit { draft ->
                    draft.setLead(draft.lead.toMutableList().also { it[index] = entry.copy(placement = placement) })
                }
            },
        )
        if (lead.size > 1) {
            Spacer(Modifier.height(Spacing.s1))
            QuietButton(
                stringResource(R.string.dive_edit_remove_lead, index + 1),
                onClick = {
                    onEdit { draft ->
                        val next = draft.lead.toMutableList().also { it.removeAt(index) }
                        draft.setLead(next.ifEmpty { listOf(DiveEditLead()) })
                    }
                },
            )
        }
        if (index < lead.lastIndex) Spacer(Modifier.height(Spacing.s4))
    }
    val total = lead.mapNotNull { it.amountKg }.takeIf { it.isNotEmpty() }?.sum()
    if (lead.size > 1 && total != null) {
        Spacer(Modifier.height(Spacing.s1))
        Text(
            stringResource(
                R.string.dive_edit_lead_total,
                "${Formats.decimal(locale, massIn(total, imperial), 1)} $massUnit",
            ),
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
    if (lead.size < MAX_LEAD) {
        Spacer(Modifier.height(Spacing.s2))
        SecondaryButton(
            stringResource(R.string.dive_edit_add_lead),
            onClick = { onEdit { it.setLead(it.lead + DiveEditLead()) } },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PlacementChips(placement: String?, onChange: (String?) -> Unit) {
    Text(
        stringResource(R.string.dive_edit_lead_placement),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        verticalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        EditChip(
            label = stringResource(R.string.dive_edit_not_said),
            selected = placement == null,
            onClick = { onChange(null) },
        )
        LEAD_PLACEMENTS.forEach { where ->
            EditChip(
                label = stringResource(placementLabel(where)),
                selected = placement == where,
                onClick = { onChange(where) },
            )
        }
    }
}

/** Two fields on one line, or one above the other when the text no longer fits beside itself. */
@Composable
private fun FieldPair(first: @Composable () -> Unit, second: @Composable () -> Unit) {
    if (LocalDensity.current.fontScale >= LargeTextFontScale) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s2)) {
            first()
            second()
        }
    } else {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s3)) {
            Box(Modifier.weight(1f)) { first() }
            Box(Modifier.weight(1f)) { second() }
        }
    }
}

@Composable
private fun GearNumberField(
    rowKey: String,
    field: String,
    label: String,
    unit: String,
    value: Double?,
    locale: Locale,
    display: (Double) -> Double = { it },
    parse: (Double) -> Double = { it },
    onChange: (Double?) -> Unit,
) {
    // Keyed by the entry and the field, not the value: a typed decimal point must not be re-read as a number.
    var text by remember(rowKey, field) {
        mutableStateOf(value?.let { editNumber(display(it), locale) }.orEmpty())
    }
    NumberField(
        value = text,
        label = label,
        unit = unit,
        modifier = Modifier.fillMaxWidth(),
        onChange = { typed ->
            text = typed
            onChange(parseEditDouble(typed)?.let(parse))
        },
        labelAboveAtLargeText = true,
    )
}

private fun materialLabel(material: String): Int = when (material) {
    "aluminium" -> R.string.tools_material_aluminium
    "steel" -> R.string.tools_material_steel
    else -> R.string.tools_material_carbon
}

private fun placementLabel(placement: String): Int = when (placement) {
    "belt" -> R.string.dive_placement_belt
    "integrated" -> R.string.dive_placement_integrated
    "trim" -> R.string.dive_placement_trim
    "ankle" -> R.string.dive_placement_ankle
    "backplate" -> R.string.dive_placement_backplate
    else -> R.string.dive_placement_other
}

private fun problemText(problem: CylinderProblem): Int = when (problem) {
    CylinderProblem.END_ABOVE_START -> R.string.dive_edit_cylinder_end_above_start
    CylinderProblem.GAS_OVER_100 -> R.string.dive_edit_cylinder_gas_over_100
}

@Composable
private fun seriesLabel(option: DiveEditSeriesOption): String {
    val base = option.recordingName ?: stringResource(R.string.dive_recording_n, option.recordingIndex + 1)
    return if (option.podCount > 1) {
        "$base, ${stringResource(R.string.dive_edit_series_tank, option.podIndex + 1)}"
    } else {
        base
    }
}

private const val MAX_CYLINDERS = 12
private const val MAX_LEAD = 8
