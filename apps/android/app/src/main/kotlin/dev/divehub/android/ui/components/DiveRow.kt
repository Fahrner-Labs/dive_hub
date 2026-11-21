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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing

/** At 150 % and above the figures go under the facts: the title keeps the width instead of breaking inside a word. */
private const val StackedFiguresFontScale = 1.5f

/**
 * The logbook's row: a title in [DiveHubText.bodyStrong] with its facts under it in [DiveHubText.meta], an optional
 * participant slot, and the depth and duration end-aligned on the right so figures line up down the list. At large text
 * the figures move under the facts, so the title never breaks inside a word. The whole row is one target and keeps
 * [Spacing.s3] above and below. Month headings sit above the rows, not inside one.
 *
 * [leading] is the row's number column, [footer] what sits under the facts inside the text column (the logbook's
 * profile sketch and its findings mark), so a narrow phone never squeezes the title to fit a third column.
 */
@Composable
fun DiveRow(
    title: String,
    depth: String,
    duration: String,
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    facts: List<String> = emptyList(),
    avatars: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    val stacked = LocalDensity.current.fontScale >= StackedFiguresFontScale
    val row = modifier
        .fillMaxWidth()
        .clickable(role = Role.Button, onClick = onClick)
        .semantics(mergeDescendants = true) {}
        .padding(vertical = Spacing.s3)
    if (stacked) {
        Column(row) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
                verticalAlignment = Alignment.Top,
            ) {
                leading?.invoke()
                RowText(title, facts, avatars, footer, Modifier.weight(1f))
            }
            Figures(depth, duration, Modifier.fillMaxWidth().padding(top = Spacing.s1))
        }
    } else {
        Row(
            row,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
            verticalAlignment = Alignment.Top,
        ) {
            leading?.invoke()
            RowText(title, facts, avatars, footer, Modifier.weight(1f))
            Figures(depth, duration)
        }
    }
}

@Composable
private fun RowText(
    title: String,
    facts: List<String>,
    avatars: (@Composable () -> Unit)?,
    footer: (@Composable () -> Unit)?,
    modifier: Modifier,
) {
    Column(modifier) {
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
        footer?.let {
            Spacer(Modifier.height(Spacing.s1))
            it()
        }
    }
}

@Composable
private fun Figures(depth: String, duration: String, modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.End) {
        Text(depth, style = DiveHubText.body, textAlign = TextAlign.End)
        Text(duration, style = DiveHubText.body, textAlign = TextAlign.End)
    }
}
