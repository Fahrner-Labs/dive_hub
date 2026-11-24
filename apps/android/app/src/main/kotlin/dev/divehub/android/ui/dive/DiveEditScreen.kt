package dev.divehub.android.ui.dive

import android.text.format.DateFormat
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.LargeTextFontScale
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.EmptyState
import dev.divehub.android.ui.components.FactRow
import dev.divehub.android.ui.components.InkButton
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.Section
import dev.divehub.android.ui.tools.NumberField
import dev.divehub.shared.dive.DiveEditDraft
import dev.divehub.shared.dive.DiveEditStart
import dev.divehub.shared.dive.DiveEditSuit
import dev.divehub.shared.dive.DiveEditWeighting
import dev.divehub.shared.dive.DiveValue
import dev.divehub.shared.dive.RecordedValue
import dev.divehub.shared.dive.timeZoneNote
import dev.divehub.shared.dive.utcOffsetText
import dev.divehub.shared.tools.depthFromDisplay
import dev.divehub.shared.tools.depthIn
import dev.divehub.shared.tools.massFromDisplay
import dev.divehub.shared.tools.massIn
import dev.divehub.shared.tools.temperatureFromDisplay
import dev.divehub.shared.tools.temperatureIn
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val OFFSET_STEP = 900
private const val MIN_OFFSET_SECONDS = -12 * 3600
private const val MAX_OFFSET_SECONDS = 14 * 3600
private const val SHEET_MAX_HEIGHT_DP = 520

/** The editor's route: it only wires the screen to its state holder. */
@Composable
fun DiveEditScreenRoute(
    viewModel: DiveEditViewModel,
    units: String?,
    onBack: () -> Unit,
    onSaved: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.saved) {
        if (state.saved) onSaved()
    }
    DiveEditScreen(
        state = state,
        units = units,
        onBack = onBack,
        onEdit = viewModel::edit,
        onSave = viewModel::save,
        onRetry = viewModel::retry,
    )
}

/**
 * The Dive's scalar editor: Notes, its site, start and UTC offset, the five values set by hand (with the Recording's
 * value and a way back to it), the suit and how the weighting felt. Every change stays on the device through the
 * offline queue; the number is shown, never editable (ADR 0048), and the destructive verbs say why they are not
 * available here.
 */
@Composable
fun DiveEditScreen(
    state: DiveEditUiState,
    units: String?,
    onBack: () -> Unit,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
    onSave: () -> Unit,
    onRetry: () -> Unit,
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val imperial = Formats.imperial(units, locale)
    val draft = state.draft
    var confirmLeave by remember { mutableStateOf(false) }
    // A reset replaces a field's text, so the number fields are keyed by it and re-read the draft afterwards.
    var resetToken by remember { mutableIntStateOf(0) }
    val requestBack: () -> Unit = {
        if (draft?.dirty == true) confirmLeave = true else onBack()
    }
    BackHandler(onBack = requestBack)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DiveHubTopBar(
                title = stringResource(R.string.dive_edit_title),
                onBack = requestBack,
            )
        },
        bottomBar = {
            if (draft != null) {
                SaveBar(
                    draft = draft,
                    state = state,
                    onSave = onSave,
                    onCancel = requestBack,
                )
            }
        },
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding).fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            when {
                state.loading -> Loading()
                state.missing || draft == null -> EmptyState(
                    title = stringResource(R.string.dive_not_found_title),
                    message = stringResource(R.string.dive_error_not_found),
                    action = { InkButton(stringResource(R.string.dive_retry), onRetry) },
                )
                else -> EditForm(
                    draft = draft,
                    sites = state.sites,
                    originalSiteName = state.originalSiteName,
                    online = state.online,
                    imperial = imperial,
                    locale = locale,
                    resetToken = resetToken,
                    onEdit = onEdit,
                    onReset = { field ->
                        resetToken += 1
                        onEdit { it.resetToRecording(field) }
                    },
                )
            }
        }
    }

    if (confirmLeave) {
        AlertDialog(
            onDismissRequest = { confirmLeave = false },
            title = { Text(stringResource(R.string.dive_edit_leave_title)) },
            text = { Text(stringResource(R.string.dive_edit_leave_body)) },
            confirmButton = {
                TextButton(onClick = {
                    confirmLeave = false
                    onBack()
                }) { Text(stringResource(R.string.dive_edit_discard)) }
            },
            dismissButton = {
                TextButton(onClick = { confirmLeave = false }) {
                    Text(stringResource(R.string.dive_edit_keep_editing))
                }
            },
        )
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize().padding(Spacing.s4),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.loading), style = DiveHubText.meta)
    }
}

@Composable
private fun SaveBar(
    draft: DiveEditDraft,
    state: DiveEditUiState,
    onSave: () -> Unit,
    onCancel: () -> Unit,
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = Spacing.s4, vertical = Spacing.s3),
        ) {
            if (state.failed) {
                Text(
                    stringResource(R.string.dive_edit_failed),
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(Modifier.height(Spacing.s1))
            }
            val saveText = stringResource(if (state.saving) R.string.dive_edit_saving else R.string.dive_edit_save)
            if (LocalDensity.current.fontScale >= LargeTextFontScale) {
                // At large text the two actions share no line: a squeezed primary button breaks inside its word.
                InkButton(saveText, onSave, Modifier.fillMaxWidth(), enabled = draft.dirty && !state.saving)
                QuietButton(stringResource(R.string.dive_edit_cancel), onCancel)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    QuietButton(stringResource(R.string.dive_edit_cancel), onCancel)
                    Spacer(Modifier.width(Spacing.s4))
                    Spacer(Modifier.weight(1f))
                    InkButton(saveText, onSave, enabled = draft.dirty && !state.saving)
                }
            }
        }
    }
}

@Composable
private fun EditForm(
    draft: DiveEditDraft,
    sites: List<dev.divehub.shared.store.DiveSite>,
    originalSiteName: String?,
    online: Boolean,
    imperial: Boolean,
    locale: Locale,
    resetToken: Int,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
    onReset: (DiveValue) -> Unit,
) {
    var pickingSite by remember { mutableStateOf(false) }
    LazyColumn(
        Modifier.fillMaxSize().testTag("dive_edit_scroll"),
        contentPadding = PaddingValues(bottom = Spacing.s6),
    ) {
        item {
            Section(
                title = stringResource(R.string.dive_edit_values),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
                showSeparator = false,
            ) {
                FactRow(
                    label = stringResource(R.string.dive_number),
                    value = draft.number?.let { Formats.number(locale, it) } ?: "–",
                    notes = listOf(stringResource(R.string.dive_edit_number_note)),
                )
                StartField(
                    start = draft.start,
                    timeZoneSource = draft.utcOffsetSource,
                    locale = locale,
                    edited = draft.isEdited(DiveValue.STARTS_AT),
                    recorded = draft.recordedValue(DiveValue.STARTS_AT),
                    imperial = imperial,
                    onChange = { value -> onEdit { it.setStart(value) } },
                    onReset = { onReset(DiveValue.STARTS_AT) },
                )
                NumberValueField(
                    label = stringResource(R.string.dive_duration),
                    unit = stringResource(R.string.dive_edit_unit_minutes),
                    initial = draft.durationMinutes.toString(),
                    resetToken = resetToken,
                    onChange = { text -> onEdit { it.setDurationMinutes(parseEditInt(text)) } },
                )
                EditedHint(draft, DiveValue.DURATION_SECONDS, locale, imperial, onReset)
                NumberValueField(
                    label = stringResource(R.string.dive_max_depth),
                    unit = if (imperial) {
                        stringResource(R.string.dive_edit_unit_feet)
                    } else {
                        stringResource(R.string.dive_edit_unit_metres)
                    },
                    initial = draft.maxDepthM?.let { editNumber(depthIn(it, imperial), locale) }.orEmpty(),
                    resetToken = resetToken,
                    onChange = { text ->
                        onEdit { d ->
                            d.setMaxDepthM(parseEditDouble(text)?.let { depthFromDisplay(it, imperial) })
                        }
                    },
                )
                EditedHint(draft, DiveValue.MAX_DEPTH_M, locale, imperial, onReset)
                NumberValueField(
                    label = stringResource(R.string.dive_avg_depth),
                    unit = if (imperial) {
                        stringResource(R.string.dive_edit_unit_feet)
                    } else {
                        stringResource(R.string.dive_edit_unit_metres)
                    },
                    initial = draft.avgDepthM?.let { editNumber(depthIn(it, imperial), locale) }.orEmpty(),
                    resetToken = resetToken,
                    onChange = { text ->
                        onEdit { d ->
                            d.setAvgDepthM(parseEditDouble(text)?.let { depthFromDisplay(it, imperial) })
                        }
                    },
                )
                EditedHint(draft, DiveValue.AVG_DEPTH_M, locale, imperial, onReset)
                NumberValueField(
                    label = stringResource(R.string.dive_water_temperature),
                    unit = if (imperial) {
                        stringResource(R.string.dive_edit_unit_fahrenheit)
                    } else {
                        stringResource(R.string.dive_edit_unit_celsius)
                    },
                    initial = draft.waterTemperatureC
                        ?.let { editNumber(temperatureIn(it, imperial), locale) }
                        .orEmpty(),
                    resetToken = resetToken,
                    onChange = { text ->
                        onEdit { d ->
                            d.setWaterTemperatureC(
                                parseEditDouble(text)?.let { temperatureFromDisplay(it, imperial) },
                            )
                        }
                    },
                )
                EditedHint(draft, DiveValue.WATER_TEMPERATURE_C, locale, imperial, onReset)
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_edit_site),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                val name = draft.siteId?.let { id ->
                    sites.firstOrNull { it.id == id }?.name ?: originalSiteName
                } ?: stringResource(R.string.dive_edit_no_site)
                FactRow(label = stringResource(R.string.dive_edit_current_site), value = name)
                QuietButton(stringResource(R.string.dive_edit_choose_site), onClick = { pickingSite = true })
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_notes),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                OutlinedTextField(
                    value = draft.notes,
                    onValueChange = { value -> onEdit { it.setNotes(value) } },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.dive_notes)) },
                    minLines = 4,
                )
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_weighting),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                SuitFields(draft, locale, resetToken, onEdit)
                Spacer(Modifier.height(Spacing.s4))
                WeightingFields(draft, imperial, locale, resetToken, onEdit)
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_edit_more),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                DestructiveActions(online)
            }
        }
    }

    if (pickingSite) {
        SitePickerSheet(
            sites = sites,
            selected = draft.siteId,
            onDismiss = { pickingSite = false },
            onPick = { id ->
                onEdit { it.setSite(id) }
                pickingSite = false
            },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun StartField(
    start: DiveEditStart,
    timeZoneSource: String,
    locale: Locale,
    edited: Boolean,
    recorded: RecordedValue?,
    imperial: Boolean,
    onChange: (DiveEditStart) -> Unit,
    onReset: () -> Unit,
) {
    val context = LocalContext.current
    var showDate by remember { mutableStateOf(false) }
    var showTime by remember { mutableStateOf(false) }
    val local = start.local
    Text(
        stringResource(R.string.dive_start),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(Modifier.height(Spacing.s1))
    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s2)) {
        OutlinedButton(
            onClick = { showDate = true },
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = Spacing.s3, vertical = Spacing.s2),
        ) {
            Text(dateText(local, context))
        }
        OutlinedButton(
            onClick = { showTime = true },
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = Spacing.s3, vertical = Spacing.s2),
        ) {
            Text(timeText(local, context))
        }
    }
    Spacer(Modifier.height(Spacing.s2))
    Text(
        stringResource(R.string.dive_edit_utc_offset),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        val decrease = stringResource(R.string.dive_edit_offset_decrease)
        val increase = stringResource(R.string.dive_edit_offset_increase)
        TextButton(
            onClick = { onChange(start.copy(utcOffsetSeconds = start.utcOffsetSeconds - OFFSET_STEP)) },
            enabled = start.utcOffsetSeconds > MIN_OFFSET_SECONDS,
            modifier = Modifier.semantics { contentDescription = decrease },
        ) { Text("−") }
        Text(utcOffsetText(start.utcOffsetSeconds).orEmpty(), style = DiveHubText.body)
        TextButton(
            onClick = { onChange(start.copy(utcOffsetSeconds = start.utcOffsetSeconds + OFFSET_STEP)) },
            enabled = start.utcOffsetSeconds < MAX_OFFSET_SECONDS,
            modifier = Modifier.semantics { contentDescription = increase },
        ) { Text("+") }
    }
    timeZoneNote(timeZoneSource, edited)?.let { origin ->
        Text(
            timeZoneText(context, origin),
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
    EditedHint(
        edited = edited,
        field = DiveValue.STARTS_AT,
        recorded = recorded,
        locale = locale,
        imperial = imperial,
        onReset = onReset,
    )

    if (showDate) {
        val picker = rememberDatePickerState(
            initialSelectedDateMillis = local.toInstant(TimeZone.UTC).toEpochMilliseconds(),
        )
        DatePickerDialog(
            onDismissRequest = { showDate = false },
            confirmButton = {
                TextButton(onClick = {
                    picker.selectedDateMillis?.let { millis ->
                        val date = kotlin.time.Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.UTC)
                        onChange(
                            start.copy(
                                local = LocalDateTime(
                                    date.year,
                                    date.monthNumber,
                                    date.dayOfMonth,
                                    local.hour,
                                    local.minute,
                                ),
                            ),
                        )
                    }
                    showDate = false
                }) { Text(stringResource(R.string.dive_edit_set)) }
            },
            dismissButton = {
                TextButton(onClick = { showDate = false }) {
                    Text(stringResource(R.string.dive_edit_cancel))
                }
            },
        ) {
            DatePicker(state = picker)
        }
    }
    if (showTime) {
        val picker = rememberTimePickerState(
            initialHour = local.hour,
            initialMinute = local.minute,
            is24Hour = DateFormat.is24HourFormat(context),
        )
        AlertDialog(
            onDismissRequest = { showTime = false },
            title = { Text(stringResource(R.string.dive_edit_time)) },
            text = { TimePicker(state = picker) },
            confirmButton = {
                TextButton(onClick = {
                    onChange(
                        start.copy(
                            local = LocalDateTime(
                                local.year,
                                local.monthNumber,
                                local.dayOfMonth,
                                picker.hour,
                                picker.minute,
                            ),
                        ),
                    )
                    showTime = false
                }) { Text(stringResource(R.string.dive_edit_set)) }
            },
            dismissButton = {
                TextButton(onClick = { showTime = false }) {
                    Text(stringResource(R.string.dive_edit_cancel))
                }
            },
        )
    }
}

@Composable
private fun EditedHint(
    draft: DiveEditDraft,
    field: DiveValue,
    locale: Locale,
    imperial: Boolean,
    onReset: (DiveValue) -> Unit,
) {
    EditedHint(
        edited = draft.isEdited(field),
        field = field,
        recorded = draft.recordedValue(field),
        locale = locale,
        imperial = imperial,
        onReset = { onReset(field) },
    )
}

@Composable
private fun EditedHint(
    edited: Boolean,
    field: DiveValue,
    recorded: RecordedValue?,
    locale: Locale,
    imperial: Boolean,
    onReset: () -> Unit,
) {
    if (!edited) return
    val context = LocalContext.current
    val recordedText = recorded?.let { DiveDetailText.recordedText(context, field, it, locale, imperial) }
    Text(
        recordedText?.let { context.getString(R.string.dive_edited_value, it) }
            ?: stringResource(R.string.dive_edit_edited),
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    if (recordedText != null) {
        QuietButton(
            stringResource(R.string.dive_edit_use_recording, recordedText),
            onReset,
        )
    }
}

@Composable
private fun NumberValueField(
    label: String,
    unit: String,
    initial: String,
    resetToken: Int,
    onChange: (String) -> Unit,
) {
    var text by remember(resetToken) { mutableStateOf(initial) }
    NumberField(
        value = text,
        label = label,
        unit = unit,
        modifier = Modifier.fillMaxWidth(),
        onChange = {
            text = it
            onChange(it)
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SuitFields(
    draft: DiveEditDraft,
    locale: Locale,
    resetToken: Int,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
) {
    val suit = draft.suit
    Text(
        stringResource(R.string.dive_suit),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        verticalArrangement = Arrangement.spacedBy(Spacing.s1),
    ) {
        FilterChip(
            selected = suit == null,
            onClick = { onEdit { it.setSuit(null) } },
            label = { Text(stringResource(R.string.dive_edit_not_known)) },
        )
        SuitType.entries.forEach { type ->
            FilterChip(
                selected = suit?.type == type.wire,
                onClick = {
                    onEdit {
                        it.setSuit(
                            DiveEditSuit(
                                type = type.wire,
                                thicknessMm = suit?.thicknessMm.takeIf { type.hasThickness },
                                hood = suit?.hood.takeIf { type.worn },
                                undergarment = suit?.undergarment.takeIf { type.wire == "drysuit" },
                            ),
                        )
                    }
                },
                label = { Text(stringResource(type.label)) },
            )
        }
    }
    suit?.let { current ->
        if (current.hasThickness) {
            Spacer(Modifier.height(Spacing.s2))
            var text by remember(resetToken, current.type) {
                mutableStateOf(current.thicknessMm?.let { editNumber(it, locale) }.orEmpty())
            }
            NumberField(
                value = text,
                label = stringResource(R.string.dive_edit_thickness),
                unit = stringResource(R.string.dive_edit_unit_mm),
                modifier = Modifier.fillMaxWidth(),
                onChange = { value ->
                    text = value
                    onEdit { it.setSuit(current.copy(thicknessMm = parseEditDouble(value))) }
                },
            )
        }
        if (current.worn) {
            Spacer(Modifier.height(Spacing.s2))
            Text(
                stringResource(R.string.dive_edit_hood),
                style = DiveHubText.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s2)) {
                HoodChip(R.string.dive_edit_not_said, current.hood == null) {
                    onEdit { it.setSuit(current.copy(hood = null)) }
                }
                HoodChip(R.string.dive_edit_hood_yes, current.hood == true) {
                    onEdit { it.setSuit(current.copy(hood = true)) }
                }
                HoodChip(R.string.dive_edit_hood_no, current.hood == false) {
                    onEdit { it.setSuit(current.copy(hood = false)) }
                }
            }
        }
        if (current.type == "drysuit") {
            Spacer(Modifier.height(Spacing.s2))
            Text(
                stringResource(R.string.dive_edit_undergarment),
                style = DiveHubText.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s2)) {
                UndergarmentChip(R.string.dive_edit_not_said, current.undergarment == null) {
                    onEdit { it.setSuit(current.copy(undergarment = null)) }
                }
                UndergarmentChip(R.string.dive_undergarment_light, current.undergarment == "light") {
                    onEdit { it.setSuit(current.copy(undergarment = "light")) }
                }
                UndergarmentChip(R.string.dive_undergarment_medium, current.undergarment == "medium") {
                    onEdit { it.setSuit(current.copy(undergarment = "medium")) }
                }
                UndergarmentChip(R.string.dive_undergarment_heavy, current.undergarment == "heavy") {
                    onEdit { it.setSuit(current.copy(undergarment = "heavy")) }
                }
            }
        }
    }
}

@Composable
private fun HoodChip(label: Int, selected: Boolean, onClick: () -> Unit) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(stringResource(label)) })
}

@Composable
private fun UndergarmentChip(label: Int, selected: Boolean, onClick: () -> Unit) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(stringResource(label)) })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WeightingFields(
    draft: DiveEditDraft,
    imperial: Boolean,
    locale: Locale,
    resetToken: Int,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
) {
    val weighting = draft.weighting
    Text(
        stringResource(R.string.dive_feedback),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s2)) {
        FilterChip(
            selected = weighting == null,
            onClick = { onEdit { it.setWeighting(null) } },
            label = { Text(stringResource(R.string.dive_edit_not_said)) },
        )
        FilterChip(
            selected = weighting?.felt == "right",
            onClick = { onEdit { it.setWeighting(DiveEditWeighting(felt = "right")) } },
            label = { Text(stringResource(R.string.dive_edit_felt_right)) },
        )
        FilterChip(
            selected = weighting?.felt == "too_heavy",
            onClick = {
                onEdit { it.setWeighting(DiveEditWeighting(felt = "too_heavy", amountKg = weighting?.amountKg)) }
            },
            label = { Text(stringResource(R.string.dive_edit_felt_too_heavy)) },
        )
        FilterChip(
            selected = weighting?.felt == "too_light",
            onClick = {
                onEdit { it.setWeighting(DiveEditWeighting(felt = "too_light", amountKg = weighting?.amountKg)) }
            },
            label = { Text(stringResource(R.string.dive_edit_felt_too_light)) },
        )
    }
    val felt = weighting?.felt
    if (felt != null && felt != "right") {
        Spacer(Modifier.height(Spacing.s2))
        var text by remember(resetToken, felt) {
            mutableStateOf(weighting.amountKg?.let { editNumber(massIn(it, imperial), locale) }.orEmpty())
        }
        NumberField(
            value = text,
            label = stringResource(R.string.dive_edit_felt_amount),
            unit = if (imperial) {
                stringResource(R.string.dive_edit_unit_pounds)
            } else {
                stringResource(R.string.dive_edit_unit_kilograms)
            },
            modifier = Modifier.fillMaxWidth(),
            onChange = { value ->
                text = value
                onEdit {
                    it.setWeighting(
                        DiveEditWeighting(
                            felt = felt,
                            amountKg = parseEditDouble(value)?.let { amount -> massFromDisplay(amount, imperial) },
                        ),
                    )
                }
            },
        )
    }
}

@Composable
private fun DestructiveActions(online: Boolean) {
    Text(
        stringResource(if (online) R.string.dive_edit_more_online else R.string.error_no_answer),
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(Modifier.height(Spacing.s2))
    Column {
        QuietButton(stringResource(R.string.dive_edit_delete), {}, enabled = false)
        QuietButton(stringResource(R.string.dive_edit_merge), {}, enabled = false)
        QuietButton(stringResource(R.string.dive_edit_move), {}, enabled = false)
        QuietButton(stringResource(R.string.dive_edit_append), {}, enabled = false)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SitePickerSheet(
    sites: List<dev.divehub.shared.store.DiveSite>,
    selected: String?,
    onDismiss: () -> Unit,
    onPick: (String?) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var query by remember { mutableStateOf("") }
    val matching = remember(query, sites) {
        if (query.isBlank()) {
            sites
        } else {
            sites.filter { it.name.contains(query.trim(), ignoreCase = true) }
        }
    }
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(Modifier.fillMaxWidth().padding(horizontal = Spacing.s4)) {
            Text(stringResource(R.string.dive_edit_site_title), style = DiveHubText.section)
            Spacer(Modifier.height(Spacing.s2))
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.dive_edit_find_site)) },
                singleLine = true,
            )
            Spacer(Modifier.height(Spacing.s2))
            LazyColumn(Modifier.fillMaxWidth().heightIn(max = SHEET_MAX_HEIGHT_DP.dp)) {
                item {
                    SiteRow(
                        name = stringResource(R.string.dive_edit_no_site),
                        selected = selected == null,
                        onClick = { onPick(null) },
                    )
                }
                items(matching, key = { it.id }) { site ->
                    SiteRow(
                        name = site.name,
                        selected = selected == site.id,
                        onClick = { onPick(site.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SiteRow(name: String, selected: Boolean, onClick: () -> Unit) {
    QuietButton(
        text = if (selected) "✓ $name" else name,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    )
}

private enum class SuitType(
    val wire: String,
    val label: Int,
    val worn: Boolean,
    val hasThickness: Boolean,
) {
    NONE("none", R.string.tools_suit_none, false, false),
    SKIN("skin", R.string.tools_suit_skin, true, false),
    WETSUIT("wetsuit", R.string.tools_suit_wetsuit, true, true),
    SEMIDRY("semidry", R.string.tools_suit_semidry, true, true),
    DRYSUIT("drysuit", R.string.tools_suit_drysuit, true, false),
}

private fun dateText(local: LocalDateTime, context: android.content.Context): String {
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = context.getString(R.string.dive_edit_date_pattern)
    return java.time.LocalDateTime
        .of(local.year, local.monthNumber, local.dayOfMonth, local.hour, local.minute)
        .format(DateTimeFormatter.ofPattern(pattern, locale))
}

private fun timeText(local: LocalDateTime, context: android.content.Context): String {
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = context.getString(R.string.dive_edit_time_pattern)
    return java.time.LocalDateTime
        .of(local.year, local.monthNumber, local.dayOfMonth, local.hour, local.minute)
        .format(DateTimeFormatter.ofPattern(pattern, locale))
}

/** A number the way an edit field shows it: no trailing zero, at most one decimal, the UI language's separator. */
private fun editNumber(value: Double, locale: Locale): String =
    if (value == value.toLong().toDouble()) {
        Formats.number(locale, value.toInt())
    } else {
        Formats.decimal(locale, value, 1)
    }

private fun parseEditDouble(text: String): Double? =
    text.trim().replace(',', '.').toDoubleOrNull()

private fun parseEditInt(text: String): Int =
    text.trim().toIntOrNull() ?: 0

