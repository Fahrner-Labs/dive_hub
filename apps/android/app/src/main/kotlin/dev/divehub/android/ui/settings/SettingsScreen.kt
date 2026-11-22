package dev.divehub.android.ui.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubTopBar
import dev.divehub.android.ui.formatSyncTime
import dev.divehub.android.ui.successColor
import java.util.Locale

/**
 * The settings route: the account e-mail and app version come from the session and the build, the rest from the
 * view model. [onChangeInstance] must sign out and wipe before the destination asks for a new instance. The Debug
 * details live one tap away in [SettingsDebugRoute] (FAH-343).
 */
@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel,
    email: String?,
    versionName: String,
    versionCode: Int,
    onChangeInstance: () -> Unit,
    onSignOut: () -> Unit,
    onOpenDebug: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(
        state = state,
        email = email,
        versionName = versionName,
        versionCode = versionCode,
        onChangeInstance = onChangeInstance,
        onSyncNow = viewModel::syncNow,
        onSignOut = onSignOut,
        onOpenDebug = onOpenDebug,
    )
}

/**
 * The grouped settings list (M3 list layout): connection (with the sync action), account (with sign out), about and
 * the Debug entry, in that order. It draws only [state], so its EN/DE and light/dark shapes have screenshot tests
 * (ADR 0064, ADR 0079).
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
    onOpenDebug: () -> Unit,
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
                modifier = Modifier.clickable { confirmingChangeInstance = true },
                headlineContent = { Text(stringResource(R.string.settings_instance)) },
                supportingContent = {
                    Column {
                        state.instanceUrl?.let { Text(it) }
                        ConnectionStatus(connected = state.connected, syncedAt = state.syncedAt)
                    }
                },
                trailingContent = {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.instance_change),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )
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

            Section(R.string.settings_section_account)
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_signed_in_as)) },
                supportingContent = { email?.let { Text(it) } },
                trailingContent = {
                    IconButton(onClick = { confirmingSignOut = true }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(R.string.sign_out),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
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

            HorizontalDivider()

            ListItem(
                modifier = Modifier.clickable(onClick = onOpenDebug),
                headlineContent = { Text(stringResource(R.string.settings_section_debug)) },
                supportingContent = { Text(stringResource(R.string.settings_debug_summary)) },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
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

/**
 * The instance row's permanent connection state (FAH-342): a green dot and "Connected" while the live stream is
 * delivering, or a muted dot and "Offline — last synced …" when it is down. Only the stream state counts; a reachable
 * network is not evidence.
 */
@Composable
internal fun ConnectionStatus(connected: Boolean, syncedAt: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = stringResource(R.string.sync_date_pattern)
    val text = when {
        connected -> stringResource(R.string.settings_connection_connected)
        syncedAt != null -> stringResource(
            R.string.settings_connection_offline,
            formatSyncTime(syncedAt, locale, pattern),
        )
        else -> stringResource(R.string.settings_connection_never)
    }
    val dot = if (connected) {
        successColor()
    } else {
        MaterialTheme.colorScheme.outline
    }
    Row(
        modifier = modifier.semantics { liveRegion = LiveRegionMode.Polite },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(dot))
        Spacer(Modifier.width(6.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/** A grouped section's heading; visible text, not just a divider (ADR 0064). */
@Composable
private fun Section(@StringRes title: Int) {
    Text(
        stringResource(title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 4.dp)
            .semantics { heading() },
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
    )
}

/** A settings row whose whole width is the action. */
@Composable
private fun Action(@StringRes title: Int, @StringRes supporting: Int? = null, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(stringResource(title)) },
        supportingContent = { supporting?.let { Text(stringResource(it)) } },
    )
}
