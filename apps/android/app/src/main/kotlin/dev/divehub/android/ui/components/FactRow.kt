package dev.divehub.android.ui.components

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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing

/** At 150 % and above the value goes under the label: a short label never breaks inside a word to keep a column. */
private const val StackedLayoutFontScale = 1.5f

/**
 * A label–value line: the label in [DiveHubText.label] and muted, the value in [DiveHubText.body] and end-aligned so
 * figures line up down the column. An edited mark and its notes sit under the value in [DiveHubText.meta]. It keeps
 * [Spacing.s3] above and below, the one comfortable density. [notes] is for a value explained over several lines, such
 * as a water mismatch or a set of pressures, so each line stays its own readable text; a mark is one note too.
 *
 * At large text the label takes the full width above the value, so "Duration" or "Water temperature" wraps at its
 * words instead of breaking inside one; the row grows, it is never squeezed to fit.
 */
@Composable
fun FactRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    mark: String? = null,
    notes: List<String> = emptyList(),
) {
    val row = modifier
        .fillMaxWidth()
        .padding(vertical = Spacing.s3)
        .semantics(mergeDescendants = true) {}
    if (LocalDensity.current.fontScale >= StackedLayoutFontScale) {
        Column(row) {
            Text(
                label,
                style = DiveHubText.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(Spacing.s1))
            Text(value, style = DiveHubText.body)
            mark?.let { StackedNoteText(it) }
            notes.forEach { StackedNoteText(it) }
        }
    } else {
        Row(
            row,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s4),
        ) {
            Text(
                label,
                modifier = Modifier.weight(0.4f),
                style = DiveHubText.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Column(Modifier.weight(0.6f), horizontalAlignment = Alignment.End) {
                Text(value, style = DiveHubText.body, textAlign = TextAlign.End)
                mark?.let { NoteText(it) }
                notes.forEach { NoteText(it) }
            }
        }
    }
}

@Composable
private fun NoteText(text: String) {
    Text(
        text,
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.End,
    )
}

@Composable
private fun StackedNoteText(text: String) {
    Text(
        text,
        style = DiveHubText.meta,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
