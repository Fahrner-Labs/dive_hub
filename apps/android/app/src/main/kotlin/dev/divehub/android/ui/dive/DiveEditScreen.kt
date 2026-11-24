package dev.divehub.android.ui.dive

import android.text.format.DateFormat
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Formats
import dev.divehub.android.ui.LargeTextFontScale
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.DangerButton
import dev.divehub.android.ui.components.EmptyState
import dev.divehub.android.ui.components.FactRow
import dev.divehub.android.ui.components.InkButton
import dev.divehub.android.ui.components.QuietActionTarget
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.Radius
import dev.divehub.android.ui.components.SecondaryButton
import dev.divehub.android.ui.components.Section
import dev.divehub.android.ui.tools.NumberField
import dev.divehub.shared.dive.DiveEditDraft
import dev.divehub.shared.dive.DiveEditParticipant
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

/** The check in a role chip; a fixed icon size, not a spacing step. */
private val RoleChoiceIcon = 18.dp

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
 * The Dive's editor: Notes, its site, start and UTC offset, the five values set by hand (with the Recording's
 * value and a way back to it), the suit and how the weighting felt, and who else was on it, with their roles and the
 * joint dive's union rule. Every change stays on the device through the offline queue; the number is shown, never
 * editable (ADR 0048), and the destructive verbs say why they are not available here.
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
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DiveHubTopBar(
                title = stringResource(R.string.dive_edit_title),
                onBack = requestBack,
                stackedTitle = true,
                titleModifier = Modifier.semantics { heading() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
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
                    divers = state.divers,
                    ownerDiverId = state.ownerDiverId,
                    seriesOptions = state.seriesOptions,
                    originalSiteName = state.originalSiteName,
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
            shape = RoundedCornerShape(Radius.sheet),
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text(stringResource(R.string.dive_edit_leave_title)) },
            text = { Text(stringResource(R.string.dive_edit_leave_body)) },
            confirmButton = {
                DangerButton(
                    stringResource(R.string.dive_edit_discard),
                    onClick = {
                        confirmLeave = false
                        onBack()
                    },
                )
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
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                    style = DiveHubText.body,
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(Modifier.height(Spacing.s1))
            }
            if (draft.dirty && !draft.cylindersFit) {
                Text(
                    stringResource(R.string.dive_edit_cylinders_fix_save),
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                    style = DiveHubText.body,
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(Modifier.height(Spacing.s1))
            }
            val saveText = stringResource(if (state.saving) R.string.dive_edit_saving else R.string.dive_edit_save)
            if (LocalDensity.current.fontScale >= LargeTextFontScale) {
                // At large text the two actions share no line: a squeezed primary button breaks inside its word. The
                // enabled primary action is the bottom-most target.
                QuietButton(stringResource(R.string.dive_edit_cancel), onCancel)
                Spacer(Modifier.height(Spacing.s2))
                InkButton(saveText, onSave, Modifier.fillMaxWidth(), enabled = draft.dirty && draft.cylindersFit && !state.saving)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    QuietButton(stringResource(R.string.dive_edit_cancel), onCancel)
                    Spacer(Modifier.width(Spacing.s4))
                    Spacer(Modifier.weight(1f))
                    InkButton(saveText, onSave, enabled = draft.dirty && draft.cylindersFit && !state.saving)
                }
            }
        }
    }
}

@Composable
private fun EditForm(
    draft: DiveEditDraft,
    sites: List<dev.divehub.shared.store.DiveSite>,
    divers: List<dev.divehub.shared.store.Diver>,
    ownerDiverId: String?,
    seriesOptions: List<dev.divehub.shared.dive.DiveEditSeriesOption>,
    originalSiteName: String?,
    imperial: Boolean,
    locale: Locale,
    resetToken: Int,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
    onReset: (DiveValue) -> Unit,
) {
    var pickingSite by remember { mutableStateOf(false) }
    var addingParticipant by remember { mutableStateOf(false) }
    var changingRole by remember { mutableStateOf<DiveEditParticipant?>(null) }
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
                Spacer(Modifier.height(Spacing.s4))
                NumberValueField(
                    label = stringResource(R.string.dive_duration),
                    unit = stringResource(R.string.dive_edit_unit_minutes),
                    initial = draft.durationMinutes.toString(),
                    resetToken = resetToken,
                    onChange = { text -> onEdit { it.setDurationMinutes(parseEditInt(text)) } },
                )
                EditedHint(draft, DiveValue.DURATION_SECONDS, locale, imperial, onReset)
                Spacer(Modifier.height(Spacing.s4))
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
                Spacer(Modifier.height(Spacing.s4))
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
                Spacer(Modifier.height(Spacing.s4))
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
                SecondaryButton(stringResource(R.string.dive_edit_choose_site), onClick = { pickingSite = true })
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_participants),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                ParticipantsFields(
                    draft = draft,
                    onAdd = { addingParticipant = true },
                    onChangeRole = { changingRole = it },
                    onEdit = onEdit,
                )
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_notes),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                val largeText = LocalDensity.current.fontScale >= LargeTextFontScale
                if (largeText) {
                    Text(
                        stringResource(R.string.dive_edit_notes_hint),
                        style = DiveHubText.label,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(Spacing.s1))
                }
                OutlinedTextField(
                    value = draft.notes,
                    onValueChange = { value -> onEdit { it.setNotes(value) } },
                    modifier = Modifier.fillMaxWidth(),
                    label = if (largeText) {
                        null
                    } else {
                        { Text(stringResource(R.string.dive_edit_notes_hint)) }
                    },
                    shape = RoundedCornerShape(Radius.control),
                    minLines = 4,
                )
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_cylinders),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                CylindersFields(
                    cylinders = draft.cylinders,
                    seriesOptions = seriesOptions,
                    imperial = imperial,
                    locale = locale,
                    onEdit = onEdit,
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
                LeadFields(
                    lead = draft.lead,
                    imperial = imperial,
                    locale = locale,
                    onEdit = onEdit,
                )
                Spacer(Modifier.height(Spacing.s4))
                WeightingFields(draft, imperial, locale, resetToken, onEdit)
            }
        }
        item {
            Section(
                title = stringResource(R.string.dive_edit_more),
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4),
            ) {
                DestructiveActions()
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

    if (addingParticipant) {
        AddParticipantSheet(
            divers = divers,
            excluded = setOfNotNull(ownerDiverId) + draft.participants.map { it.diverId },
            onDismiss = { addingParticipant = false },
            onPick = { diver, role ->
                onEdit { it.addParticipant(diver.id, diver.name, role) }
                addingParticipant = false
            },
        )
    }

    changingRole?.let { person ->
        RoleSheet(
            person = person,
            onDismiss = { changingRole = null },
            onPick = { role ->
                onEdit { it.setParticipantRole(person.diverId, role) }
                changingRole = null
            },
        )
    }
}

@Composable
private fun ParticipantsFields(
    draft: DiveEditDraft,
    onAdd: () -> Unit,
    onChangeRole: (DiveEditParticipant) -> Unit,
    onEdit: ((DiveEditDraft) -> DiveEditDraft) -> Unit,
) {
    if (draft.participants.isEmpty()) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Radius.box))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(Spacing.s3),
        ) {
            Text(
                stringResource(R.string.dive_edit_no_participants),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    } else {
        draft.participants.forEach { person ->
            ParticipantRow(
                person = person,
                onChangeRole = { onChangeRole(person) },
                onRemove = { onEdit { it.removeParticipant(person.diverId) } },
            )
        }
    }
    Spacer(Modifier.height(Spacing.s2))
    SecondaryButton(stringResource(R.string.dive_edit_add_participant), onAdd)
    Spacer(Modifier.height(Spacing.s3))
    // ADR 0082: the union runs on the server and arrives through the feed; a removal stays on this Dive.
    Text(
        stringResource(R.string.dive_edit_participants_joint),
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ParticipantRow(
    person: DiveEditParticipant,
    onChangeRole: () -> Unit,
    onRemove: () -> Unit,
) {
    val role = roleLabel(person.role)
    val changeRole = stringResource(R.string.dive_edit_change_role, person.name)
    val remove = stringResource(R.string.dive_edit_remove_person, person.name)
    if (LocalDensity.current.fontScale >= LargeTextFontScale) {
        // At 200 % "Tauchlehrer" and "Entfernen" need more than the column has: the actions wrap instead of clipping.
        Column(Modifier.fillMaxWidth().padding(vertical = Spacing.s3)) {
            Text(person.name, style = DiveHubText.bodyStrong)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
                verticalArrangement = Arrangement.spacedBy(Spacing.s2),
            ) {
                RoleButton(role = role, changeRole = changeRole, onClick = onChangeRole)
                RemoveButton(remove = remove, onClick = onRemove)
            }
        }
    } else {
        Row(
            Modifier.fillMaxWidth().padding(vertical = Spacing.s3),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(person.name, modifier = Modifier.weight(1f), style = DiveHubText.bodyStrong)
            RoleButton(role = role, changeRole = changeRole, onClick = onChangeRole)
            Spacer(Modifier.width(Spacing.s2))
            RemoveButton(remove = remove, onClick = onRemove)
        }
    }
}

/**
 * The role looks like a picker, not a bare value: the chevron says it opens a choice. The role stays the accessible
 * name, and [changeRole] is the action label, so TalkBack names both the value and what a tap does.
 */
@Composable
private fun RoleButton(role: String, changeRole: String, onClick: () -> Unit) {
    QuietButton(
        text = role,
        onClick = onClick,
        expanded = false,
        onClickLabel = changeRole,
    )
}

@Composable
private fun RemoveButton(remove: String, onClick: () -> Unit) {
    QuietButton(
        text = stringResource(R.string.dive_edit_remove),
        onClick = onClick,
        modifier = Modifier.semantics { contentDescription = remove },
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun AddParticipantSheet(
    divers: List<dev.divehub.shared.store.Diver>,
    excluded: Set<String>,
    onDismiss: () -> Unit,
    onPick: (dev.divehub.shared.store.Diver, String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var query by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("buddy") }
    val candidates = remember(divers, excluded) {
        divers.filterNot { it.id in excluded }.sortedBy { it.name.lowercase() }
    }
    val matching = remember(query, candidates) {
        if (query.isBlank()) {
            candidates
        } else {
            candidates.filter { it.name.contains(query.trim(), ignoreCase = true) }
        }
    }
    val largeText = LocalDensity.current.fontScale >= LargeTextFontScale
    val asLabel = stringResource(R.string.dive_edit_as_role)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = Radius.sheet, topEnd = Radius.sheet),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        // One scroll: the role, the field and the list move together, so the sheet never runs past a 200 % viewport.
        LazyColumn(
            Modifier.fillMaxWidth().testTag("dive_edit_participant_add_scroll"),
            contentPadding = PaddingValues(Spacing.s5),
        ) {
            item {
                Text(
                    stringResource(R.string.dive_edit_add_participant),
                    modifier = Modifier.semantics { heading() },
                    style = DiveHubText.section,
                )
            }
            if (candidates.isEmpty()) {
                item {
                    Spacer(Modifier.height(Spacing.s2))
                    Text(
                        stringResource(R.string.dive_edit_no_other_divers),
                        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                        style = DiveHubText.meta,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                item {
                    Spacer(Modifier.height(Spacing.s3))
                    Text(
                        asLabel,
                        style = DiveHubText.label,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(Spacing.s1))
                    FlowRow(
                        Modifier.selectableGroup().semantics { contentDescription = asLabel },
                        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
                        verticalArrangement = Arrangement.spacedBy(Spacing.s2),
                    ) {
                        PARTICIPANT_ROLES.forEach { choice ->
                            RoleChoiceChip(
                                label = roleLabel(choice),
                                selected = role == choice,
                                onClick = { role = choice },
                            )
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(Spacing.s4))
                    if (largeText) {
                        Text(
                            stringResource(R.string.dive_edit_find_diver),
                            style = DiveHubText.label,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(Modifier.height(Spacing.s1))
                    }
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth().testTag("dive_edit_participant_search"),
                        label = if (largeText) {
                            null
                        } else {
                            { Text(stringResource(R.string.dive_edit_find_diver)) }
                        },
                        shape = RoundedCornerShape(Radius.control),
                        singleLine = true,
                        trailingIcon = if (query.isNotBlank()) {
                            {
                                IconButton(onClick = { query = "" }) {
                                    Icon(
                                        Icons.Filled.Close,
                                        contentDescription = stringResource(R.string.dive_edit_clear_search),
                                    )
                                }
                            }
                        } else {
                            null
                        },
                    )
                    Spacer(Modifier.height(Spacing.s1))
                    Text(
                        stringResource(R.string.dive_edit_divers_shared),
                        style = DiveHubText.meta,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                if (query.isNotBlank() && matching.isEmpty()) {
                    item {
                        Spacer(Modifier.height(Spacing.s2))
                        Text(
                            stringResource(R.string.dive_edit_no_diver_match),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            style = DiveHubText.meta,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                itemsIndexed(matching, key = { _, diver -> diver.id }) { index, diver ->
                    DiverRow(
                        name = diver.name,
                        detail = diverDetail(diver),
                        query = query.trim(),
                        showDivider = index < matching.lastIndex,
                        onClick = { onPick(diver, role) },
                    )
                }
            }
        }
    }
}

/** The role chip: one choice of a radio group, pressed with a check mark and `selected`, never colour alone. */
@Composable
private fun RoleChoiceChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(Radius.control)
    val fill = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    Box(Modifier.heightIn(min = QuietActionTarget), contentAlignment = Alignment.CenterStart) {
        Row(
            Modifier
                .clip(shape)
                .background(fill)
                .border(1.dp, if (selected) fill else MaterialTheme.colorScheme.outline, shape)
                .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
                .semantics(mergeDescendants = true) {}
                .padding(horizontal = Spacing.s3, vertical = Spacing.s2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (selected) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(RoleChoiceIcon),
                )
                Spacer(Modifier.width(Spacing.s1))
            }
            Text(label, style = DiveHubText.label)
        }
    }
}

/** What the roster says about a Diver beside the name: whose it is, as the web's add list does. */
@Composable
private fun diverDetail(diver: dev.divehub.shared.store.Diver): String? = when {
    diver.isOwn -> stringResource(R.string.dive_edit_diver_yours)
    diver.external -> stringResource(R.string.dive_edit_diver_external)
    else -> stringResource(R.string.dive_edit_diver_other_user)
}

@Composable
private fun DiverRow(
    name: String,
    detail: String?,
    query: String,
    showDivider: Boolean,
    onClick: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Radius.control))
                .clickable(role = Role.Button, onClick = onClick)
                .semantics(mergeDescendants = true) {}
                .padding(vertical = Spacing.s3)
                .heightIn(min = QuietActionTarget),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(markedName(name, query), style = DiveHubText.body)
                detail?.let {
                    Text(it, style = DiveHubText.meta, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        if (showDivider) {
            HorizontalDivider(color = DiveHubColors.border)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoleSheet(
    person: DiveEditParticipant,
    onDismiss: () -> Unit,
    onPick: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = Radius.sheet, topEnd = Radius.sheet),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.s5),
        ) {
            Text(
                stringResource(R.string.dive_edit_role_title, person.name),
                modifier = Modifier.semantics { heading() },
                style = DiveHubText.section,
            )
            Spacer(Modifier.height(Spacing.s2))
            HorizontalDivider(color = DiveHubColors.border)
            Column(Modifier.selectableGroup()) {
                PARTICIPANT_ROLES.forEach { role ->
                    RoleRow(
                        label = roleLabel(role),
                        selected = person.role == role,
                        onClick = { onPick(role) },
                    )
                }
            }
        }
    }
}

/** One role in the picker: the whole 48 dp row is the target, the fill and the check mark the current role. */
@Composable
private fun RoleRow(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.control))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .semantics(mergeDescendants = true) {}
            .padding(vertical = Spacing.s3)
            .heightIn(min = QuietActionTarget),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
            if (selected) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
            }
        }
        Spacer(Modifier.width(Spacing.s2))
        Text(label, style = DiveHubText.body)
    }
}

/** The three roles ADR 0028 defines; the order the web's picker uses. */
private val PARTICIPANT_ROLES = listOf("buddy", "guide", "instructor")

@Composable
private fun roleLabel(role: String): String = Formats.role(LocalContext.current, role)

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
    val date = dateText(local, context)
    val time = timeText(local, context)
    if (LocalDensity.current.fontScale >= LargeTextFontScale) {
        // Text takes the full width at large text: a two-column pair wraps inside a date.
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing.s2)) {
            SecondaryButton(date, { showDate = true }, Modifier.fillMaxWidth())
            SecondaryButton(time, { showTime = true }, Modifier.fillMaxWidth())
        }
    } else {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s2)) {
            SecondaryButton(date, { showDate = true }, Modifier.weight(1f))
            SecondaryButton(time, { showTime = true }, Modifier.weight(1f))
        }
    }
    Spacer(Modifier.height(Spacing.s3))
    Text(
        stringResource(R.string.dive_edit_utc_offset),
        style = DiveHubText.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(Modifier.height(Spacing.s1))
    Row(
        Modifier
            .clip(RoundedCornerShape(Radius.control))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(Radius.control)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val decrease = stringResource(R.string.dive_edit_offset_decrease)
        val increase = stringResource(R.string.dive_edit_offset_increase)
        val ink = MaterialTheme.colorScheme.onSurface
        val disabled = ink.copy(alpha = 0.38f)
        IconButton(
            onClick = { onChange(start.copy(utcOffsetSeconds = start.utcOffsetSeconds - OFFSET_STEP)) },
            enabled = start.utcOffsetSeconds > MIN_OFFSET_SECONDS,
            modifier = Modifier.semantics { contentDescription = decrease },
        ) {
            Text(
                "−",
                style = DiveHubText.body,
                color = if (start.utcOffsetSeconds > MIN_OFFSET_SECONDS) ink else disabled,
            )
        }
        Text(
            utcOffsetText(start.utcOffsetSeconds).orEmpty(),
            modifier = Modifier.widthIn(min = 64.dp),
            style = DiveHubText.body,
            textAlign = TextAlign.Center,
        )
        IconButton(
            onClick = { onChange(start.copy(utcOffsetSeconds = start.utcOffsetSeconds + OFFSET_STEP)) },
            enabled = start.utcOffsetSeconds < MAX_OFFSET_SECONDS,
            modifier = Modifier.semantics { contentDescription = increase },
        ) {
            Text(
                "+",
                style = DiveHubText.body,
                color = if (start.utcOffsetSeconds < MAX_OFFSET_SECONDS) ink else disabled,
            )
        }
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
            shape = RoundedCornerShape(Radius.sheet),
            colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
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
            DatePicker(
                state = picker,
                colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
            )
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
            shape = RoundedCornerShape(Radius.sheet),
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text(stringResource(R.string.dive_edit_time)) },
            text = {
                TimePicker(
                    state = picker,
                    colors = TimePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
                )
            },
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
    Spacer(Modifier.height(Spacing.s1))
    Text(
        recordedText?.let { context.getString(R.string.dive_edited_value, it) }
            ?: stringResource(R.string.dive_edit_edited),
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    if (recordedText != null) {
        Spacer(Modifier.height(Spacing.s2))
        QuietButton(
            stringResource(R.string.dive_edit_use_recording),
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
        labelAboveAtLargeText = true,
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
        verticalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        EditChip(
            label = stringResource(R.string.dive_edit_not_known),
            selected = suit == null,
            onClick = { onEdit { it.setSuit(null) } },
        )
        SuitType.entries.forEach { type ->
            EditChip(
                label = stringResource(type.label),
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
                labelAboveAtLargeText = true,
            )
        }
        if (current.worn) {
            Spacer(Modifier.height(Spacing.s2))
            Text(
                stringResource(R.string.dive_edit_hood),
                style = DiveHubText.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
                verticalArrangement = Arrangement.spacedBy(Spacing.s2),
            ) {
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
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
                verticalArrangement = Arrangement.spacedBy(Spacing.s2),
            ) {
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
    EditChip(label = stringResource(label), selected = selected, onClick = onClick)
}

@Composable
private fun UndergarmentChip(label: Int, selected: Boolean, onClick: () -> Unit) {
    EditChip(label = stringResource(label), selected = selected, onClick = onClick)
}

/** The form's chip: pressed with a check mark and `selected`, never colour alone (accent-soft nearly vanishes in dark). */
@Composable
internal fun EditChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = if (selected) {
            {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        } else {
            null
        },
    )
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
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        verticalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        EditChip(
            label = stringResource(R.string.dive_edit_not_said),
            selected = weighting == null,
            onClick = { onEdit { it.setWeighting(null) } },
        )
        EditChip(
            label = stringResource(R.string.dive_edit_felt_right),
            selected = weighting?.felt == "right",
            onClick = { onEdit { it.setWeighting(DiveEditWeighting(felt = "right")) } },
        )
        EditChip(
            label = stringResource(R.string.dive_edit_felt_too_heavy),
            selected = weighting?.felt == "too_heavy",
            onClick = {
                onEdit { it.setWeighting(DiveEditWeighting(felt = "too_heavy", amountKg = weighting?.amountKg)) }
            },
        )
        EditChip(
            label = stringResource(R.string.dive_edit_felt_too_light),
            selected = weighting?.felt == "too_light",
            onClick = {
                onEdit { it.setWeighting(DiveEditWeighting(felt = "too_light", amountKg = weighting?.amountKg)) }
            },
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
            labelAboveAtLargeText = true,
        )
    }
}

@Composable
private fun DestructiveActions() {
    // The verbs are not implemented yet, so they stay dead in both states: blaming the connection would send the User
    // to fix something that would not help. The contract's online-only wording comes with the implementation.
    Text(
        stringResource(R.string.dive_edit_more_online),
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
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = Radius.sheet, topEnd = Radius.sheet),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(Modifier.fillMaxWidth().padding(Spacing.s5)) {
            Text(
                stringResource(R.string.dive_edit_site_title),
                modifier = Modifier.semantics { heading() },
                style = DiveHubText.section,
            )
            Spacer(Modifier.height(Spacing.s3))
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.dive_edit_find_site)) },
                shape = RoundedCornerShape(Radius.control),
                singleLine = true,
                trailingIcon = if (query.isNotBlank()) {
                    {
                        IconButton(onClick = { query = "" }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = stringResource(R.string.dive_edit_clear_search),
                            )
                        }
                    }
                } else {
                    null
                },
            )
            if (query.isNotBlank() && matching.isEmpty()) {
                Spacer(Modifier.height(Spacing.s2))
                Text(
                    stringResource(R.string.dive_edit_no_match),
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.height(Spacing.s2))
            LazyColumn(Modifier.fillMaxWidth().heightIn(max = SHEET_MAX_HEIGHT_DP.dp)) {
                if (query.isBlank()) {
                    item {
                        SiteRow(
                            name = stringResource(R.string.dive_edit_no_site),
                            query = "",
                            selected = selected == null,
                            showDivider = matching.isNotEmpty(),
                            onClick = { onPick(null) },
                        )
                    }
                }
                itemsIndexed(matching, key = { _, site -> site.id }) { index, site ->
                    SiteRow(
                        name = site.name,
                        query = query.trim(),
                        selected = selected == site.id,
                        showDivider = index < matching.lastIndex,
                        onClick = { onPick(site.id) },
                    )
                }
            }
        }
    }
}

/** A site the User can pick: the whole 48 dp row is the target, the check is an icon and `selected` is on the row. */
@Composable
private fun SiteRow(
    name: String,
    query: String,
    selected: Boolean,
    showDivider: Boolean,
    onClick: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Radius.control))
                .background(if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                .selectable(selected = selected, role = Role.Button, onClick = onClick)
                .semantics(mergeDescendants = true) {}
                .padding(horizontal = Spacing.s2, vertical = Spacing.s3)
                .heightIn(min = QuietActionTarget),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                if (selected) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Spacer(Modifier.width(Spacing.s2))
            Text(markedName(name, query), style = DiveHubText.body)
        }
        if (showDivider) {
            HorizontalDivider(color = DiveHubColors.border)
        }
    }
}

private fun markedName(name: String, query: String): AnnotatedString {
    val start = name.indexOf(query, ignoreCase = true)
    if (query.isEmpty() || start < 0) return AnnotatedString(name)
    return buildAnnotatedString {
        append(name.substring(0, start))
        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append(name.substring(start, start + query.length))
        }
        append(name.substring(start + query.length))
    }
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
internal fun editNumber(value: Double, locale: Locale): String =
    if (value == value.toLong().toDouble()) {
        Formats.number(locale, value.toInt())
    } else {
        Formats.decimal(locale, value, 1)
    }

internal fun parseEditDouble(text: String): Double? =
    text.trim().replace(',', '.').toDoubleOrNull()

private fun parseEditInt(text: String): Int =
    text.trim().toIntOrNull() ?: 0

