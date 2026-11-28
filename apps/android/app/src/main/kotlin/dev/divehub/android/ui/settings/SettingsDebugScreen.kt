package dev.divehub.android.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.notifications.PushState
import dev.divehub.android.notifications.PushStatus
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.formatSyncTime
import dev.divehub.android.ui.successColor
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.ReportProblemResult
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The Debug subscreen (FAH-343, folded out of the settings list): diagnostics status, *Report a problem*, *Share
 * local logs* and the permanent *Sync queue* row (FAH-500). It re-reads the diagnostics capability on each entry,
 * as the settings route used to.
 */
@Composable
fun SettingsDebugRoute(
    viewModel: SettingsViewModel,
    onBack: () -> Unit,
    onOpenQueue: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) { viewModel.refreshDiagnostics() }
    SettingsDebugScreen(
        diagnostics = state.diagnostics,
        push = state.push,
        queue = state.queue,
        onBack = onBack,
        onReportProblem = viewModel::reportProblem,
        onOpenQueue = onOpenQueue,
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
 * The diagnostics detail screen (FAH-318, ADR 0080 D1): a read-only status line, a *Push* row with the registered
 * device or the failing step (FAH-557), *Report a problem* for an instance that allows uploads, *Share local logs*,
 * which stays available whatever the instance setting says, and the *Sync queue* row with live queue counts (FAH-500).
 * It only draws [diagnostics], [push] and [queue]; the capture layer and the upload live in `:shared` (FAH-321).
 */
@Composable
fun SettingsDebugScreen(
    diagnostics: SettingsDiagnosticsState,
    queue: SettingsQueueState,
    onBack: () -> Unit,
    onReportProblem: () -> Unit,
    onOpenQueue: () -> Unit,
    onShareLogs: () -> Unit,
    push: PushStatus = PushStatus(),
    modifier: Modifier = Modifier,
) {
    val canReport = diagnostics.enabled == true && diagnostics.count > 0 && !diagnostics.reporting
    Scaffold(
        modifier = modifier,
        topBar = {
            DiveHubTopBar(
                title = stringResource(R.string.settings_section_debug),
                onBack = onBack,
            )
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                // The last row is a plain ListItem: keep the rhythm above the bottom bar (FAH-424).
                .padding(bottom = Spacing.s5)
                .testTag("settings_debug_scroll"),
        ) {
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
                modifier = Modifier.testTag("settings_push_row"),
                headlineContent = { Text(stringResource(R.string.settings_debug_push)) },
                supportingContent = { PushStatusText(push) },
            )

            ListItem(
                modifier = Modifier
                    .clickable(onClick = onOpenQueue)
                    .testTag("settings_sync_queue_row"),
                headlineContent = { Text(stringResource(R.string.settings_debug_sync_queue)) },
                supportingContent = {
                    Text(
                        stringResource(
                            R.string.settings_debug_queue_counts,
                            queue.pending,
                            queue.failed,
                            queue.conflicted,
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )

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
                modifier = Modifier.clickable(onClick = onShareLogs).testTag("settings_share_row"),
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
}

/**
 * The push row's supporting line: a green dot while registered and the outline dot otherwise, so a broken registration
 * never reads like a healthy one (FAH-558 N1, mirroring [ConnectionStatus]).
 */
@Composable
private fun PushStatusText(push: PushStatus) {
    val registered = push.state == PushState.Registered
    val dot = if (registered) successColor() else MaterialTheme.colorScheme.outline
    // Top-aligned and optically centred on the first line: a wrapped failure must not leave the dot between lines.
    val lineHeight = with(LocalDensity.current) { MaterialTheme.typography.bodyMedium.lineHeight.toDp() }
    val dotOffset = ((lineHeight - 8.dp) / 2f).coerceAtLeast(0.dp)
    Row(
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            Modifier
                .padding(top = dotOffset)
                .size(8.dp)
                .clip(CircleShape)
                .background(dot),
        )
        Spacer(Modifier.width(6.dp))
        Text(
            pushText(push),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/** The push row's sentence: a registered device, or the step that stopped registration (FAH-557). */
@Composable
private fun pushText(push: PushStatus): String {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = stringResource(R.string.sync_date_pattern)
    return when (push.state) {
        PushState.Registered -> {
            val lastSeen = push.lastSeenAt
            if (lastSeen.isNullOrBlank()) {
                stringResource(R.string.settings_debug_push_registered_no_seen, push.deviceId.orEmpty())
            } else {
                stringResource(
                    R.string.settings_debug_push_registered,
                    push.deviceId.orEmpty(),
                    // The server's ISO instant in the reader's own zone and date format (FAH-558 B1).
                    formatSyncTime(lastSeen, locale, pattern),
                )
            }
        }
        PushState.Off -> notRegistered(stringResource(R.string.settings_debug_push_off))
        PushState.ConfigFailed -> notRegistered(
            stringResource(R.string.settings_debug_push_config_failed, reasonText(push.reason)),
        )
        PushState.NoPlayServices -> notRegistered(stringResource(R.string.settings_debug_push_no_play_services))
        PushState.InitFailed -> notRegistered(
            stringResource(R.string.settings_debug_push_init_failed, reasonText(push.reason)),
        )
        PushState.PermissionMissing -> notRegistered(stringResource(R.string.settings_debug_push_permission))
        PushState.NotSignedIn -> notRegistered(stringResource(R.string.settings_debug_push_not_signed_in))
        PushState.TokenFailed -> notRegistered(
            stringResource(R.string.settings_debug_push_token_failed, reasonText(push.reason)),
        )
        PushState.RegisterFailed -> notRegistered(
            stringResource(R.string.settings_debug_push_register_failed, reasonText(push.reason)),
        )
        PushState.Unknown -> notRegistered(stringResource(R.string.settings_debug_push_unknown))
    }
}

/** A failure's code or exception class; a blank one still reads as a sentence instead of a dangling colon (N4). */
@Composable
private fun reasonText(reason: String?): String =
    reason?.takeIf { it.isNotBlank() } ?: stringResource(R.string.settings_debug_push_reason_unknown)

@Composable
private fun notRegistered(reason: String): String = stringResource(R.string.settings_debug_push_not_registered, reason)

/** One preview line: what the person could report, without the timestamps that add nothing at this size. */
private fun DiagnosticEntry.label(): String = message?.takeIf { it.isNotBlank() } ?: code ?: level.wire
