package dev.divehub.android.ui.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.SyncLine
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.ReportProblemResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The settings route: the account e-mail and app version come from the session and the build, the rest from the
 * view model. [onChangeInstance] must sign out and wipe before the destination asks for a new instance.
 */
@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel,
    email: String?,
    versionName: String,
    versionCode: Int,
    onChangeInstance: () -> Unit,
    onSignOut: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    // The ViewModel survives a tab switch (ADR 0079 saved tabs): re-read each time the route comes back, so a buffer
    // filled while the User was elsewhere still offers *Report a problem*.
    LaunchedEffect(Unit) { viewModel.refreshDiagnostics() }
    SettingsScreen(
        state = state,
        email = email,
        versionName = versionName,
        versionCode = versionCode,
        onChangeInstance = onChangeInstance,
        onSyncNow = viewModel::syncNow,
        onSignOut = onSignOut,
        onReportProblem = viewModel::reportProblem,
        onShareLogs = {
            viewModel.shareLogs { ndjson ->
                // The export can hold 200 entries: write the file off the main thread, then only the chooser runs here.
                scope.launch {
                    val chooser = withContext(Dispatchers.IO) { logShareChooser(context, ndjson) }
                    context.startActivity(chooser)
                }
            }
        },
    )
}

/**
 * The grouped settings list (M3 list layout): connection, account, sync, about and the quiet Debug group, in that
 * order. It draws only [state], so its EN/DE and light/dark shapes have screenshot tests (ADR 0064, ADR 0079).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    email: String?,
    versionName: String,
    versionCode: Int,
    onChangeInstance: () -> Unit,
    onSyncNow: () -> Unit,
    onSignOut: () -> Unit,
    onReportProblem: () -> Unit,
    onShareLogs: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var confirmingChangeInstance by rememberSaveable { mutableStateOf(false) }
    var confirmingSignOut by rememberSaveable { mutableStateOf(false) }
    var showingLicenses by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val license = remember {
        runCatching {
            context.assets.open("licenses/Poppins-OFL.txt").bufferedReader().use { it.readText() }
        }.getOrDefault("")
    }

    Scaffold(
        modifier = modifier,
        topBar = { DiveHubTopBar(title = stringResource(R.string.settings_title)) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Section(R.string.settings_section_connection)

            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_instance)) },
                supportingContent = { state.instanceUrl?.let { Text(it) } },
            )
            Action(
                title = R.string.instance_change,
                onClick = { confirmingChangeInstance = true },
            )

            HorizontalDivider()

            Section(R.string.settings_section_account)
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_signed_in_as)) },
                supportingContent = { email?.let { Text(it) } },
            )
            Action(
                title = R.string.sign_out,
                supporting = R.string.settings_sign_out_body,
                onClick = { confirmingSignOut = true },
            )

            HorizontalDivider()

            Section(R.string.settings_section_sync)
            if (state.syncedAt == null) {
                Text(
                    stringResource(R.string.settings_sync_never),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                SyncLine(state.syncedAt, state.offline, R.string.settings_sync_offline)
            }
            ListItem(
                modifier = Modifier.clickable(enabled = !state.syncing, onClick = onSyncNow),
                headlineContent = { Text(stringResource(R.string.settings_sync_now)) },
                supportingContent = {
                    if (state.syncing) Text(stringResource(R.string.settings_syncing))
                },
                trailingContent = {
                    if (state.syncing) CircularProgressIndicator(Modifier.size(20.dp))
                },
            )

            HorizontalDivider()

            Section(R.string.settings_section_about)
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_version)) },
                supportingContent = { Text(stringResource(R.string.settings_version_value, versionName, versionCode)) },
            )
            Action(
                title = R.string.settings_licenses,
                onClick = { showingLicenses = true },
            )

            SettingsDebugSection(
                diagnostics = state.diagnostics,
                onReportProblem = onReportProblem,
                onShareLogs = onShareLogs,
            )
        }
    }

    if (confirmingChangeInstance) {
        AlertDialog(
            onDismissRequest = { confirmingChangeInstance = false },
            title = { Text(stringResource(R.string.settings_change_instance_title)) },
            text = { Text(stringResource(R.string.settings_change_instance_body)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmingChangeInstance = false
                        onChangeInstance()
                    },
                ) { Text(stringResource(R.string.instance_change)) }
            },
            dismissButton = {
                TextButton(onClick = { confirmingChangeInstance = false }) {
                    Text(stringResource(R.string.settings_cancel))
                }
            },
        )
    }

    if (confirmingSignOut) {
        AlertDialog(
            onDismissRequest = { confirmingSignOut = false },
            title = { Text(stringResource(R.string.settings_sign_out_title)) },
            text = { Text(stringResource(R.string.settings_sign_out_body)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmingSignOut = false
                        onSignOut()
                    },
                ) { Text(stringResource(R.string.sign_out)) }
            },
            dismissButton = {
                TextButton(onClick = { confirmingSignOut = false }) {
                    Text(stringResource(R.string.settings_cancel))
                }
            },
        )
    }

    if (showingLicenses) {
        AlertDialog(
            onDismissRequest = { showingLicenses = false },
            title = { Text(stringResource(R.string.settings_licenses_title)) },
            text = {
                Box(
                    Modifier
                        .heightIn(max = 420.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(license, style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                TextButton(onClick = { showingLicenses = false }) {
                    Text(stringResource(R.string.settings_close))
                }
            },
        )
    }
}

/** A grouped section's heading; visible text, not just a divider (ADR 0064). The Debug group is quiet, not primary. */
@Composable
private fun Section(@StringRes title: Int, quiet: Boolean = false) {
    Text(
        stringResource(title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 4.dp)
            .semantics { heading() },
        style = MaterialTheme.typography.titleSmall,
        color = if (quiet) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary,
    )
}

/**
 * The Debug group at the bottom of settings (FAH-318, ADR 0080 D1): a read-only status line, *Report a problem* for an
 * instance that allows uploads, and *Share local logs*, which stays available whatever the instance setting says. It
 * only draws [diagnostics]; the capture layer and the upload live in `:shared` (FAH-321).
 */
@Composable
internal fun SettingsDebugSection(
    diagnostics: SettingsDiagnosticsState,
    onReportProblem: () -> Unit,
    onShareLogs: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val canReport = diagnostics.enabled == true && diagnostics.count > 0 && !diagnostics.reporting
    Column(modifier.fillMaxWidth()) {
        Section(R.string.settings_section_debug, quiet = true)

        ListItem(
            headlineContent = { Text(stringResource(R.string.settings_debug_diagnostics)) },
            supportingContent = {
                Column {
                    Text(
                        stringResource(
                            when (diagnostics.enabled) {
                                null -> R.string.settings_debug_checking
                                true -> R.string.settings_debug_on
                                false -> R.string.settings_debug_off
                            },
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        stringResource(R.string.settings_debug_capture, diagnostics.count),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
        )

        diagnostics.preview.forEach { entry ->
            Text(
                entry.label(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        ListItem(
            modifier = Modifier.clickable(enabled = canReport, onClick = onReportProblem),
            headlineContent = { Text(stringResource(R.string.settings_debug_report)) },
            supportingContent = {
                Text(
                    stringResource(
                        when {
                            diagnostics.reporting -> R.string.settings_debug_reporting
                            diagnostics.enabled == false -> R.string.settings_debug_report_off
                            diagnostics.count == 0 -> R.string.settings_debug_report_nothing
                            else -> R.string.settings_debug_report_body
                        },
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            trailingContent = {
                if (diagnostics.reporting) CircularProgressIndicator(Modifier.size(20.dp))
            },
        )

        diagnostics.reportResult?.let { result ->
            Text(
                stringResource(
                    when (result) {
                        ReportProblemResult.Sent -> R.string.settings_debug_sent
                        is ReportProblemResult.Failed -> when (result.code) {
                            ErrorCodes.CLIENT_DIAGNOSTICS_DISABLED -> R.string.settings_debug_report_disabled
                            ErrorCodes.CLIENT_DIAGNOSTICS_LIMITED -> R.string.settings_debug_report_limited
                            ErrorCodes.NO_ANSWER -> R.string.settings_debug_report_offline
                            else -> R.string.settings_debug_report_failed
                        }
                    },
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall,
                color = if (result == ReportProblemResult.Sent) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
            )
        }

        ListItem(
            modifier = Modifier.clickable(onClick = onShareLogs),
            headlineContent = { Text(stringResource(R.string.settings_debug_share)) },
            supportingContent = {
                Text(
                    stringResource(
                        if (diagnostics.shareEmpty) {
                            R.string.settings_debug_share_empty
                        } else {
                            R.string.settings_debug_share_body
                        },
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
    }
}

/** One preview line: what the person could report, without the timestamps that add nothing at this size. */
private fun DiagnosticEntry.label(): String = message?.takeIf { it.isNotBlank() } ?: code ?: level.wire

/** A settings row whose whole width is the action. */
@Composable
private fun Action(@StringRes title: Int, @StringRes supporting: Int? = null, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(stringResource(title)) },
        supportingContent = { supporting?.let { Text(stringResource(it)) } },
    )
}
