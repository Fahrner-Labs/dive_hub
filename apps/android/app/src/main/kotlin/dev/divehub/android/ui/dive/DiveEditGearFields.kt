package dev.divehub.android.ui.dive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    val repeatedSeries = cylinders.mapIndexedNotNull { index, cylinder ->
        cylinder.series?.let { index to it }
    }.groupBy { it.second }.values.flatMap { entries -> entries.drop(1).map { it.first } }.toSet()
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
            seriesTwice = index in repeatedSeries,
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
    } else {
        Spacer(Modifier.height(Spacing.s2))
        Text(
            stringResource(R.string.dive_edit_cylinders_max, MAX_CYLINDERS),
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
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
    seriesTwice: Boolean,
    onChange: (DiveEditCylinder) -> Unit,
    onRemove: () -> Unit,
) {
    val pressureUnit = if (imperial) {
        stringResource(R.string.tools_unit_pressure_imperial)
    } else {
        stringResource(R.string.tools_unit_pressure_metric)
    }
    GearEntryHeading(
        title = stringResource(R.string.dive_cylinder_n, index + 1),
        removeLabel = stringResource(R.string.dive_edit_remove_cylinder, index + 1),
        onRemove = onRemove,
    )
    if (cylinder.fromPod && cylinder.series != null) {
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
    cylinder.pressureProblem?.let { CylinderProblemText(it) }
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
    cylinder.gasProblem?.let { CylinderProblemText(it) }
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
                onClick = { onChange(cylinder.copy(series = null, fromPod = false)) },
            )
            seriesOptions.forEach { option ->
                EditChip(
                    label = seriesLabel(option),
                    selected = cylinder.series == option.series,
                    onClick = {
                        onChange(
                            cylinder.copy(
                                series = option.series,
                                // The pod note only holds while the Cylinder is still tied to the series it came from.
                                fromPod = cylinder.fromPod && option.series == cylinder.series,
                            ),
                        )
                    },
                )
            }
        }
        if (seriesTwice) CylinderProblemText(CylinderProblem.SERIES_TWICE)
    }
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
        GearEntryHeading(
            title = stringResource(R.string.dive_edit_lead_n, index + 1),
            removeLabel = if (lead.size > 1) {
                stringResource(R.string.dive_edit_remove_lead, index + 1)
            } else {
                null
            },
            onRemove = if (lead.size > 1) {
                {
                    onEdit { draft ->
                        val next = draft.lead.toMutableList().also { it.removeAt(index) }
                        draft.setLead(next.ifEmpty { listOf(DiveEditLead()) })
                    }
                }
            } else {
                null
            },
        )
        Spacer(Modifier.height(Spacing.s2))
        GearNumberField(
            rowKey = entry.key,
            field = "amount",
            label = stringResource(R.string.dive_edit_lead_amount),
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
        if (index < lead.lastIndex) Spacer(Modifier.height(Spacing.s4))
    }
    val total = lead.mapNotNull { it.amountKg }.takeIf { it.isNotEmpty() }?.sum()
    // A partial total would quietly leave an entry out: only the complete list is worth summing on screen.
    if (lead.size > 1 && total != null && lead.all { it.amountKg != null }) {
        Spacer(Modifier.height(Spacing.s2))
        Text(
            stringResource(
                R.string.dive_edit_lead_total,
                "${Formats.decimal(locale, massIn(total, imperial), 1)} $massUnit",
            ),
            modifier = Modifier.fillMaxWidth(),
            style = DiveHubText.body,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
        )
    }
    if (lead.size < MAX_LEAD) {
        Spacer(Modifier.height(Spacing.s2))
        SecondaryButton(
            stringResource(R.string.dive_edit_add_lead),
            onClick = { onEdit { it.setLead(it.lead + DiveEditLead()) } },
        )
    } else {
        Spacer(Modifier.height(Spacing.s2))
        Text(
            stringResource(R.string.dive_edit_lead_max, MAX_LEAD),
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
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

/** A field's minimum width: below it the floating labels wrap over the outline at 320 and 360 dp. */
private val MinPairFieldWidth = 168.dp

/** Two fields on one line, or one above the other when the text no longer fits beside itself (font scale or width). */
@Composable
private fun FieldPair(first: @Composable () -> Unit, second: @Composable () -> Unit) {
    val largeText = LocalDensity.current.fontScale >= LargeTextFontScale
    BoxWithConstraints {
        if (!largeText && maxWidth >= MinPairFieldWidth * 2 + Spacing.s3) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s3)) {
                Box(Modifier.weight(1f)) { first() }
                Box(Modifier.weight(1f)) { second() }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.s2)) {
                first()
                second()
            }
        }
    }
}

/**
 * An entry's heading with its quiet Remove trailing it, so the action names its entry and keeps the s2 target gap.
 * At large text the label and action do not share a line: the action moves to its own end-aligned row.
 */
@Composable
private fun GearEntryHeading(title: String, removeLabel: String?, onRemove: (() -> Unit)?) {
    val largeText = LocalDensity.current.fontScale >= LargeTextFontScale
    if (removeLabel == null || onRemove == null) {
        Text(
            title,
            modifier = Modifier.semantics { heading() },
            style = DiveHubText.bodyStrong,
        )
    } else if (largeText) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                title,
                modifier = Modifier.semantics { heading() },
                style = DiveHubText.bodyStrong,
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                QuietButton(removeLabel, onRemove)
            }
        }
    } else {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                title,
                modifier = Modifier.weight(1f).semantics { heading() },
                style = DiveHubText.bodyStrong,
            )
            Spacer(Modifier.width(Spacing.s3))
            QuietButton(removeLabel, onRemove)
        }
    }
}

/** The server's reason for refusing this pair, directly under the fields it names. */
@Composable
private fun CylinderProblemText(problem: CylinderProblem) {
    Spacer(Modifier.height(Spacing.s1))
    Text(
        stringResource(problemText(problem)),
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
        style = DiveHubText.body,
        color = MaterialTheme.colorScheme.error,
    )
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
    "belt" -> R.string.dive_edit_placement_belt
    "integrated" -> R.string.dive_edit_placement_integrated
    "trim" -> R.string.dive_edit_placement_trim
    "ankle" -> R.string.dive_edit_placement_ankle
    "backplate" -> R.string.dive_edit_placement_backplate
    else -> R.string.dive_edit_placement_other
}

private fun problemText(problem: CylinderProblem): Int = when (problem) {
    CylinderProblem.END_ABOVE_START -> R.string.dive_edit_cylinder_end_above_start
    CylinderProblem.GAS_OVER_100 -> R.string.dive_edit_cylinder_gas_over_100
    CylinderProblem.SERIES_TWICE -> R.string.dive_edit_cylinder_series_twice
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
