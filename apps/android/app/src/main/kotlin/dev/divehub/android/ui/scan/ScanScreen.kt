package dev.divehub.android.ui.scan

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.LargeTextFontScale
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.InkButton
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.Radius
import dev.divehub.android.ui.components.Section
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.store.ScanResult
import dev.divehub.shared.store.ScanStatus
import dev.divehub.shared.write.ReadChange
import dev.divehub.shared.write.ReadCode
import dev.divehub.shared.write.ReadCodes
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** The waiting line's spinner; a fixed dimension, not a spacing step. */
private val ScanSpinnerSize = 20.dp

/** The change list's bullet column, so a wrapped line hangs under the change, not under the bullet. */
private val ScanBulletWidth = 12.dp

/**
 * The scanner (T11): a code in front of the camera, a picture of one, or its text pasted. A code is never parsed here:
 * it is queued as the server's to read, so a scan with no signal waits and resolves when the server answers. Taking a
 * person's code and creating a Dive centre from its code are writes, so they ride the same queue (ADR 0084).
 */
@Composable
fun ScanRoute(viewModel: ScanViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var live by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { viewModel.start() }

    fun readPicture(uri: Uri) {
        scope.launch {
            val text = withContext(Dispatchers.IO) {
                runCatching { context.contentResolver.openInputStream(uri)?.use(QrImageDecoder::decode) }.getOrNull()
            }
            if (text.isNullOrBlank()) viewModel.pictureWithoutCode() else viewModel.scan(text)
        }
    }

    val camera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { taken ->
        val uri = photoUri
        if (taken && uri != null) readPicture(uri)
    }
    val pictures = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) readPicture(uri)
    }
    val permission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        when {
            granted -> live = true
            // Android still asks again after a soft denial; only a permanent one needs the system settings.
            context.findActivity()?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(it, Manifest.permission.CAMERA)
            } == true -> viewModel.cameraDenied()
            else -> viewModel.cameraBlocked()
        }
    }

    fun openLiveScanner() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            live = true
        } else {
            permission.launch(Manifest.permission.CAMERA)
        }
    }

    fun takePhoto() {
        try {
            val directory = File(context.cacheDir, "scan").apply { mkdirs() }
            val file = File(directory, "code.jpg")
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.diagnostics", file)
            photoUri = uri
            camera.launch(uri)
        } catch (_: ActivityNotFoundException) {
            viewModel.noCamera()
        } catch (_: IllegalArgumentException) {
            viewModel.noCamera()
        }
    }

    if (live) {
        // The camera owns the screen until it closes: the scanner is not composed behind it, so TalkBack has one
        // screen. Back leaves the preview, not the scanner.
        BackHandler { live = false }
        CameraScanner(
            onCode = { text ->
                live = false
                viewModel.scan(text)
            },
            onFailure = { failure ->
                live = false
                when (failure) {
                    CameraFailure.Blocked -> viewModel.cameraBlocked()
                    CameraFailure.Busy -> viewModel.cameraBusy()
                    CameraFailure.Missing -> viewModel.noCameraAvailable()
                }
            },
            onClose = { live = false },
        )
        return
    }

    ScanScreen(
        state = state,
        onBack = onBack,
        onLiveScan = ::openLiveScanner,
        onCamera = ::takePhoto,
        onImage = { pictures.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        onScan = viewModel::scan,
        onTake = viewModel::take,
        onCreateCentre = viewModel::createCentre,
        onSync = viewModel::syncNow,
        onDismiss = viewModel::dismiss,
        onClearError = viewModel::clearError,
    )
}

@Composable
fun ScanScreen(
    state: ScanUiState,
    onBack: () -> Unit,
    onLiveScan: () -> Unit,
    onCamera: () -> Unit,
    onImage: () -> Unit,
    onScan: (String) -> Unit,
    onTake: (ScanResult, String?, Boolean) -> Unit,
    onCreateCentre: (ScanResult, ReadCode) -> Unit,
    onSync: () -> Unit,
    onDismiss: (ScanResult) -> Unit,
    onClearError: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var pasted by rememberSaveable { mutableStateOf("") }
    var pasteOpen by rememberSaveable { mutableStateOf(false) }
    fun readPasted() {
        if (pasted.isNotBlank()) {
            onScan(pasted)
            pasted = ""
            pasteOpen = false
        }
    }
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DiveHubTopBar(
                title = stringResource(R.string.scan_title),
                onBack = onBack,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
    ) { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.s4)
                    .padding(top = Spacing.s4, bottom = Spacing.s5)
                    .testTag("scan_scroll"),
            ) {
                if (state.error != null) {
                    // The failure replaces the intro: it is the message now, and the primary and its fallbacks must
                    // stay in view with it at 200 % (FAH-496 critique).
                    Text(
                        scanErrorText(state.error),
                        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                    QuietButton(stringResource(R.string.scan_error_dismiss), onClick = onClearError)
                } else {
                    Text(
                        stringResource(R.string.scan_intro),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(Modifier.height(Spacing.s4))
                Section(title = stringResource(R.string.scan_section_read), showSeparator = false) {
                    // The live camera is the one primary; a picture, the system camera and pasted text are fallbacks.
                    InkButton(stringResource(R.string.scan_live), onClick = onLiveScan, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(Spacing.s2))
                    QuietButton(stringResource(R.string.scan_camera), onClick = onCamera)
                    Spacer(Modifier.height(Spacing.s2))
                    QuietButton(stringResource(R.string.scan_image), onClick = onImage)
                    Spacer(Modifier.height(Spacing.s2))
                    QuietButton(
                        text = stringResource(R.string.scan_paste_show),
                        onClick = { pasteOpen = !pasteOpen },
                        expanded = pasteOpen,
                    )
                    if (pasteOpen) {
                        Spacer(Modifier.height(Spacing.s1))
                        OutlinedTextField(
                            value = pasted,
                            onValueChange = { pasted = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(stringResource(R.string.scan_paste_label)) },
                            singleLine = false,
                            maxLines = 4,
                            shape = RoundedCornerShape(Radius.control),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { readPasted() }),
                        )
                        Spacer(Modifier.height(Spacing.s1))
                        QuietButton(stringResource(R.string.scan_read), onClick = { readPasted() })
                    }
                }

                Section(title = stringResource(R.string.scan_section_results)) {
                    if (state.pending > 0 || state.busy) {
                        Column(Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (state.pending > 0) {
                                        pluralStringResource(R.plurals.scan_waiting, state.pending, state.pending)
                                    } else {
                                        stringResource(R.string.scan_syncing)
                                    },
                                    // The line wraps at large text; the action stacks beneath it rather than being
                                    // clipped by a Row that the label has filled. The live region belongs on the
                                    // text, so a screen reader has something to announce (FAH-474).
                                    modifier = Modifier
                                        .weight(1f, fill = false)
                                        .semantics { liveRegion = LiveRegionMode.Polite },
                                    style = DiveHubText.meta,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                if (state.busy) {
                                    CircularProgressIndicator(
                                        Modifier
                                            .padding(start = Spacing.s3)
                                            .size(ScanSpinnerSize),
                                    )
                                }
                            }
                            if (state.pending > 0 && !state.busy) {
                                QuietButton(stringResource(R.string.scan_sync_now), onClick = onSync)
                            }
                        }
                        Spacer(Modifier.height(Spacing.s3))
                    }
                    if (state.results.isEmpty() && state.pending == 0 && !state.busy) {
                        Text(
                            stringResource(R.string.scan_empty),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    state.results.forEachIndexed { index, result ->
                        if (index > 0) HorizontalDivider(Modifier.padding(vertical = Spacing.s3))
                        ScanResultBlock(result, onTake = onTake, onCreateCentre = onCreateCentre, onDismiss = onDismiss)
                    }
                }
            }
        }
    }
}

@Composable
private fun ScanResultBlock(
    result: ScanResult,
    onTake: (ScanResult, String?, Boolean) -> Unit,
    onCreateCentre: (ScanResult, ReadCode) -> Unit,
    onDismiss: (ScanResult) -> Unit,
) {
    val read = ReadCodes.parse(result)
    when (result.status) {
        ScanStatus.Read -> if (read != null) {
            ReadResult(result, read, onTake = onTake, onCreateCentre = onCreateCentre, onDismiss = onDismiss)
        } else {
            OutcomeText(stringResource(R.string.scan_error_generic))
            Dismiss(result, onDismiss)
        }
        ScanStatus.Created -> {
            OutcomeText(
                stringResource(
                    if (result.kind == "centre") R.string.scan_result_created_centre else R.string.scan_result_created_diver,
                    result.name ?: "",
                ),
            )
            Dismiss(result, onDismiss)
        }
        ScanStatus.Existing -> {
            OutcomeText(
                stringResource(
                    if (result.kind == "centre") R.string.scan_result_existing_centre else R.string.scan_result_existing_diver,
                    result.name ?: "",
                ),
            )
            Dismiss(result, onDismiss)
        }
        ScanStatus.Failed -> {
            OutcomeText(stringResource(R.string.scan_result_failed, scanErrorText(result.errorCode)))
            Dismiss(result, onDismiss)
        }
    }
}

@Composable
private fun ReadResult(
    result: ScanResult,
    read: ReadCode,
    onTake: (ScanResult, String?, Boolean) -> Unit,
    onCreateCentre: (ScanResult, ReadCode) -> Unit,
    onDismiss: (ScanResult) -> Unit,
) {
    if (read.isCentre) {
        Text(
            stringResource(R.string.scan_centre_result),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            read.name.orEmpty(),
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.titleMedium,
        )
        read.centreNumber?.let { number ->
            Text(
                stringResource(R.string.scan_centre_number, number),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        val existing = read.existing
        if (existing != null) {
            Text(stringResource(R.string.scan_centre_here, existing.name), style = MaterialTheme.typography.bodyMedium)
        } else {
            Spacer(Modifier.height(Spacing.s2))
            InkButton(stringResource(R.string.scan_add_centre), onClick = { onCreateCentre(result, read) })
        }
        Dismiss(result, onDismiss)
        return
    }

    Text(
        stringResource(if (read.kind == "professional") R.string.scan_professional_result else R.string.scan_buddy_result),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(
        read.personName.ifEmpty { read.name.orEmpty() },
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
        style = MaterialTheme.typography.titleMedium,
    )
    val existing = read.existing
    if (existing != null) {
        if (existing.changes.isEmpty()) {
            Text(stringResource(R.string.scan_nothing_to_change, existing.name), style = MaterialTheme.typography.bodyMedium)
        } else {
            Text(stringResource(R.string.scan_changes), style = MaterialTheme.typography.bodyMedium)
            existing.changes.forEach { change -> ChangeLine(change) }
            if (existing.canEdit) {
                Spacer(Modifier.height(Spacing.s2))
                InkButton(
                    text = stringResource(R.string.scan_update_diver, existing.name),
                    onClick = { onTake(result, existing.id, false) },
                )
            } else {
                Text(
                    stringResource(R.string.scan_not_yours, existing.name),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Dismiss(result, onDismiss)
        return
    }

    Text(
        stringResource(if (read.candidates.isEmpty()) R.string.scan_who else R.string.scan_who_candidates),
        style = MaterialTheme.typography.bodyMedium,
    )
    // No candidate is preselected: a code that belongs to someone here must not be taken with one tap by accident.
    // The new-Diver option starts selected, so the fallback is the explicit choice about this person.
    var choice by remember(result.id) { mutableStateOf<String?>(null) }
    read.candidates.forEach { candidate ->
        ChooseRow(
            label = stringResource(R.string.scan_give_to, candidate.name),
            selected = choice == candidate.id,
            onSelect = { choice = candidate.id },
        )
    }
    ChooseRow(
        label = stringResource(R.string.scan_new_diver, read.personName),
        selected = choice == null,
        onSelect = { choice = null },
    )
    Spacer(Modifier.height(Spacing.s2))
    InkButton(
        text = if (choice == null) stringResource(R.string.scan_add_diver) else stringResource(R.string.scan_add),
        onClick = { onTake(result, choice, choice == null) },
    )
    Dismiss(result, onDismiss)
}

@Composable
private fun ChooseRow(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onSelect)
            .padding(vertical = Spacing.s1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = Spacing.s2))
    }
}

@Composable
private fun OutcomeText(text: String) {
    // A scan may close the camera with Scans below the fold; the outcome announces itself once (FAH-496 critique).
    Text(
        text,
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
private fun Dismiss(result: ScanResult, onDismiss: (ScanResult) -> Unit) {
    QuietButton(stringResource(R.string.scan_dismiss), onClick = { onDismiss(result) })
}

/**
 * One change: the compact `field: from → to` line at normal text, and one fact per line at 150 % and above. There a
 * 15-character address already fills almost the whole 320 dp column at 200 %, so the arrow gets its own line: keeping
 * it beside either address split that address mid-word (FAH-474). The values take the full width for the same reason.
 */
@Composable
private fun ChangeLine(change: ReadChange) {
    val from = change.from ?: stringResource(R.string.scan_not_set)
    if (LocalDensity.current.fontScale < LargeTextFontScale) {
        Row(Modifier.fillMaxWidth()) {
            ChangeBullet()
            Text(
                stringResource(R.string.scan_change_line, changeField(change.field), from, change.to),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        return
    }
    Column(Modifier.fillMaxWidth()) {
        Row {
            ChangeBullet()
            Text(
                changeField(change.field),
                modifier = Modifier.padding(start = Spacing.s1),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(from, style = MaterialTheme.typography.bodyMedium)
        Text(
            stringResource(R.string.scan_change_arrow),
            style = DiveHubText.meta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(change.to, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun ChangeBullet() {
    Text(
        "•",
        modifier = Modifier.width(ScanBulletWidth),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun changeField(field: String): String = when (field) {
    "firstName" -> stringResource(R.string.scan_field_first_name)
    "lastName" -> stringResource(R.string.scan_field_last_name)
    "email" -> stringResource(R.string.scan_field_email)
    "leaderNumber" -> stringResource(R.string.scan_field_leader_number)
    else -> field
}

/** The Activity behind Compose's context, for the permission question and the camera's system bars. */
internal tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
private fun scanErrorText(code: String?): String = when (code) {
    "code_not_recognised" -> stringResource(R.string.scan_error_not_recognised)
    "centre_external_id_taken" -> stringResource(R.string.scan_error_centre_taken)
    "diver_choice_needed" -> stringResource(R.string.scan_error_choice)
    "diver_has_other_account" -> stringResource(R.string.scan_error_other_account)
    "diver_external_id_taken" -> stringResource(R.string.scan_error_taken_by)
    "code_not_a_person" -> stringResource(R.string.scan_error_not_person)
    ErrorCodes.NO_ANSWER, ErrorCodes.SERVER_ERROR -> stringResource(R.string.scan_error_offline)
    ScanErrors.NO_CODE_IN_PICTURE -> stringResource(R.string.scan_error_no_code)
    ScanErrors.NO_CAMERA -> stringResource(R.string.scan_error_no_camera)
    ScanErrors.CAMERA_MISSING -> stringResource(R.string.scan_error_camera_missing)
    ScanErrors.CAMERA_DENIED -> stringResource(R.string.scan_error_camera_denied)
    ScanErrors.CAMERA_BLOCKED -> stringResource(R.string.scan_error_camera_blocked)
    ScanErrors.CAMERA_BUSY -> stringResource(R.string.scan_error_camera_busy)
    else -> stringResource(R.string.scan_error_generic)
}
