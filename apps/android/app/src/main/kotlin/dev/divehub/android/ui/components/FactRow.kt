package dev.divehub.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing

/**
 * A label–value line: the label in [DiveHubText.label] and muted, the value in [DiveHubText.body] and end-aligned so
 * figures line up down the column. An edited mark and a note sit under the value in [DiveHubText.meta]. It keeps
 * [Spacing.s3] above and below, the one comfortable density.
 */
@Composable
fun FactRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    mark: String? = null,
    note: String? = null,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.s3)
            .semantics(mergeDescendants = true) {},
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
            mark?.let {
                Text(
                    it,
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                )
            }
            note?.let {
                Text(
                    it,
                    style = DiveHubText.meta,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}
