package dev.divehub.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.LargeTextFontScale
import dev.divehub.android.ui.Spacing

/**
 * The logbook's row: a title in [DiveHubText.bodyStrong] with its facts under it in [DiveHubText.meta], an optional
 * participant slot, and the depth and duration end-aligned on the right so figures line up down the list. At large text
 * the figures move under the facts, so the title never breaks inside a word. The whole row is one target and keeps
 * [Spacing.s3] above and below. Month headings sit above the rows, not inside one.
 *
 * [leading] is the row's number column, [badge] a short state that sits with the facts, and [footer] what sits under
 * them inside the text column (the profile sketch), so a narrow phone never squeezes the title to fit a third column.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiveRow(
    title: String,
    depth: String,
    duration: String,
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    facts: List<String> = emptyList(),
    badge: (@Composable () -> Unit)? = null,
    avatars: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    val stacked = LocalDensity.current.fontScale >= LargeTextFontScale
    val row = modifier
        .fillMaxWidth()
        .clickable(role = Role.Button, onClick = onClick)
        .semantics(mergeDescendants = true) {}
        .padding(vertical = Spacing.s3)
    if (stacked) {
        // The web's narrow layout: the figures sit in the facts cell, directly under the facts, and the participants
        // and sketch follow on their own lines.
        Row(
            row,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
            verticalAlignment = Alignment.Top,
        ) {
            leading?.invoke()
            Column(Modifier.weight(1f)) {
                RowFacts(title, facts, badge)
                Figures(depth, duration, stacked = true, Modifier.padding(top = Spacing.s1))
                avatars?.let {
                    Spacer(Modifier.height(Spacing.s1))
                    it()
                }
                footer?.let {
                    Spacer(Modifier.height(Spacing.s1))
                    it()
                }
            }
        }
    } else {
        Row(
            row,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
            verticalAlignment = Alignment.Top,
        ) {
            leading?.invoke()
            Column(Modifier.weight(1f)) {
                RowFacts(title, facts, badge)
                avatars?.let {
                    Spacer(Modifier.height(Spacing.s1))
                    it()
                }
                footer?.let {
                    Spacer(Modifier.height(Spacing.s1))
                    it()
                }
            }
            Figures(depth, duration)
        }
    }
}

@Composable
private fun RowFacts(title: String, facts: List<String>, badge: (@Composable () -> Unit)?) {
    Column {
        Text(title, style = DiveHubText.bodyStrong)
        if (facts.isNotEmpty() || badge != null) {
            Spacer(Modifier.height(Spacing.s1))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
                verticalArrangement = Arrangement.spacedBy(Spacing.s1),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                if (facts.isNotEmpty()) {
                    Text(
                        facts.joinToString(" · "),
                        modifier = Modifier.clearAndSetSemantics {
                            contentDescription = facts.joinToString(", ")
                        },
                        style = DiveHubText.meta,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                badge?.invoke()
            }
        }
    }
}

@Composable
private fun Figures(depth: String, duration: String, stacked: Boolean = false, modifier: Modifier = Modifier) {
    if (stacked) {
        // Flow, so a duration that no longer fits moves to the next line whole instead of breaking inside "min". The
        // separator trails the depth, so a wrapped duration never starts a line with a middot that reads as a bullet.
        FlowRow(
            modifier.clearAndSetSemantics { contentDescription = "$depth, $duration" },
            horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
        ) {
            Text(
                "$depth\u00A0·",
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                duration.replace(' ', '\u00A0'),
                style = DiveHubText.meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    } else {
        Column(modifier, horizontalAlignment = Alignment.End) {
            Text(depth, style = DiveHubText.body, textAlign = TextAlign.End)
            Text(duration, style = DiveHubText.body, textAlign = TextAlign.End)
        }
    }
}
