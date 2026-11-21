package dev.divehub.android.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.divehub.android.R
import java.util.Locale

/**
 * The list's and the dive page's sync line (FAH-277): quiet while the live stream is connected; otherwise
 * "Last synced …" while the store is fresh, or the cached state's "No connection. Showing … synced as of …" when the
 * last sync got no answer. Nothing before the first sync, where the screen's own loading or pre-sync state speaks.
 */
@Composable
internal fun SyncLine(
    syncedAt: String?,
    connected: Boolean,
    offline: Boolean,
    @StringRes offlineText: Int,
    modifier: Modifier = Modifier,
) {
    if (connected || syncedAt == null) return
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
    val pattern = stringResource(R.string.sync_date_pattern)
    val time = formatSyncTime(syncedAt, locale, pattern)
    val text = if (offline) {
        stringResource(offlineText, time)
    } else {
        stringResource(R.string.sync_last, time)
    }
    Text(
        text,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .semantics { liveRegion = LiveRegionMode.Polite },
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
