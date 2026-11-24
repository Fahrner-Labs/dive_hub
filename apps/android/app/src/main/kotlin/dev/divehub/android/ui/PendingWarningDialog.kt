package dev.divehub.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import dev.divehub.android.R
import dev.divehub.android.ui.components.Radius

/** A session change that would discard queued writes: it waits for an explicit decision (ADR 0084 D8). */
sealed interface PendingChangeWarning {
    /** Sign-out or *Change instance* with [count] entries waiting; [changeInstance] picks the wording and the target. */
    data class SignOut(
        val count: Long,
        val changeInstance: Boolean,
        val syncing: Boolean = false,
        val stillWaiting: Boolean = false,
    ) : PendingChangeWarning

    /** Signing in as another User would discard the previous User's queued writes. */
    data class SignIn(val count: Long, val email: String) : PendingChangeWarning
}

/**
 * The blocking warning of D8: "N changes haven't synced", with *Sync now* or *Discard and sign out*. The discard is a
 * destructive action and sits in the body, below the explanation; it is never the dialog's primary button.
 */
@Composable
fun PendingWarningDialog(
    warning: PendingChangeWarning,
    onSyncNow: () -> Unit,
    onDiscard: () -> Unit,
    onCancel: () -> Unit,
) {
    when (warning) {
        is PendingChangeWarning.SignOut -> SignOutWarningDialog(warning, onSyncNow, onDiscard, onCancel)
        is PendingChangeWarning.SignIn -> SignInWarningDialog(warning, onDiscard, onCancel)
    }
}

@Composable
private fun SignOutWarningDialog(
    warning: PendingChangeWarning.SignOut,
    onSyncNow: () -> Unit,
    onDiscard: () -> Unit,
    onCancel: () -> Unit,
) {
    val count = warning.count.toInt()
    AlertDialog(
        onDismissRequest = { if (!warning.syncing) onCancel() },
        shape = RoundedCornerShape(Radius.sheet),
        containerColor = MaterialTheme.colorScheme.surface,
        title = {
            Text(pluralStringResource(R.plurals.pending_changes_block_sign_out, count, warning.count))
        },
        text = {
            Column {
                Text(
                    if (warning.changeInstance) {
                        pluralStringResource(R.plurals.pending_change_instance_body, count, warning.count)
                    } else {
                        pluralStringResource(R.plurals.pending_sign_out_body, count, warning.count)
                    },
                )
                if (warning.stillWaiting) {
                    Spacer(Modifier.height(Spacing.s2))
                    Text(
                        stringResource(R.string.pending_still_waiting),
                        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                Spacer(Modifier.height(Spacing.s3))
                TextButton(
                    onClick = onDiscard,
                    enabled = !warning.syncing,
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                ) {
                    Text(
                        stringResource(
                            if (warning.changeInstance) {
                                R.string.pending_discard_change_instance
                            } else {
                                R.string.pending_discard_sign_out
                            },
                        ),
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onSyncNow, enabled = !warning.syncing) {
                if (warning.syncing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                    )
                    Spacer(Modifier.width(Spacing.s2))
                    Text(stringResource(R.string.settings_syncing))
                } else {
                    Text(stringResource(R.string.settings_sync_now))
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel, enabled = !warning.syncing) {
                Text(stringResource(R.string.settings_cancel))
            }
        },
    )
}

@Composable
private fun SignInWarningDialog(
    warning: PendingChangeWarning.SignIn,
    onDiscard: () -> Unit,
    onCancel: () -> Unit,
) {
    val count = warning.count.toInt()
    AlertDialog(
        onDismissRequest = onCancel,
        shape = RoundedCornerShape(Radius.sheet),
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text(pluralStringResource(R.plurals.pending_sign_in_title, count, warning.count)) },
        text = { Text(stringResource(R.string.pending_sign_in_body, warning.email)) },
        confirmButton = {
            TextButton(
                onClick = onDiscard,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
            ) {
                Text(stringResource(R.string.pending_discard_sign_in))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) { Text(stringResource(R.string.settings_cancel)) }
        },
    )
}
