package dev.divehub.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing

/**
 * The logbook's row: a title in [DiveHubText.bodyStrong] with its facts under it in [DiveHubText.meta], an optional
 * participant slot, and the depth and duration end-aligned on the right so figures line up down the list. The whole
 * row is one target and keeps [Spacing.s3] above and below. Month headings sit above the rows, not inside one.
 */
@Composable
fun DiveRow(
    title: String,
    depth: String,
    duration: String,
    modifier: Modifier = Modifier,
    facts: List<String> = emptyList(),
    avatars: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .semantics(mergeDescendants = true) {}
            .padding(vertical = Spacing.s3),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s4),
        verticalAlignment = Alignment.Top,
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = DiveHubText.bodyStrong)
            if (facts.isNotEmpty()) {
                Spacer(Modifier.height(Spacing.s1))
                Text(
                    facts.joinToString(" · "),
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = facts.joinToString(", ")
                    },
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            avatars?.let {
                Spacer(Modifier.height(Spacing.s1))
                it()
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(depth, style = DiveHubText.body, textAlign = TextAlign.End)
            Text(duration, style = DiveHubText.body, textAlign = TextAlign.End)
        }
    }
}
