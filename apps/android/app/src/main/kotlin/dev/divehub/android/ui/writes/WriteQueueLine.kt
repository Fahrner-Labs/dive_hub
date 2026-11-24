package dev.divehub.android.ui.writes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.QuietButton
import dev.divehub.android.ui.components.Radius

/**
 * The queue as a state on the screen, not a toast (client contract, Offline writes; ADR 0084 D4): "2 changes waiting
 * to sync", "1 change needs your decision" with *Review*, or "1 change couldn't be sent" with *Try again*. It sits
 * beside the sync line on the logbook and a Dive; an entry waiting for the User takes the attention tone.
 *
 * Nothing is drawn when the queue is empty or when the instance answered that it is pre-feed (`ReadOnly`): there is
 * no write surface to explain then, and D7 says hide it rather than disable it.
 */
@Composable
fun WriteQueueLine(modifier: Modifier = Modifier) {
    val state = LocalWrites.current
    val actions = LocalWritesActions.current
    if (!state.visible) return
    val attention = state.conflicted > 0 || state.failed > 0
    val text = when {
        state.conflicted > 0 -> pluralStringResource(
            R.plurals.pending_changes_need_decision,
            state.conflicted,
            state.conflicted,
        )
        state.failed > 0 -> pluralStringResource(R.plurals.pending_changes_not_sent, state.failed, state.failed)
        state.pending > 0 -> pluralStringResource(
            R.plurals.pending_changes_waiting,
            state.pending,
            state.pending,
        )
        else -> pluralStringResource(R.plurals.write_notices, state.notices.size, state.notices.size)
    }
    val action = if (state.conflicted > 0) {
        stringResource(R.string.pending_changes_review) to actions.onReview
    } else {
        stringResource(R.string.pending_changes_view) to actions.onReview
    }
    val tone = if (attention) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    Box(modifier.fillMaxWidth().padding(horizontal = Spacing.s4, vertical = Spacing.s2)) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Radius.box))
                .background(tone)
                .padding(start = Spacing.s4, end = Spacing.s1, top = Spacing.s1, bottom = Spacing.s1),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text,
                modifier = Modifier
                    .weight(1f)
                    .semantics { liveRegion = LiveRegionMode.Polite },
                style = DiveHubText.bodyStrong,
                color = MaterialTheme.colorScheme.onSurface,
            )
            val (label, onClick) = action
            Spacer(Modifier.width(Spacing.s2))
            QuietButton(label, enabled = !state.busy, onClick = onClick)
        }
    }
}
