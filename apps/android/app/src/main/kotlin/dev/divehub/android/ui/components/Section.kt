package dev.divehub.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing

/** The brief's `Radius.box`: a box inside the sheet. Not a spacing step, so it stays named here. */
private val BoxCornerRadius = 10.dp

/**
 * A titled part of the sheet, the Android [Panel]: a heading, an optional action and the content, separated from the
 * section before by a hairline with [Spacing.s5] above it. It has no card, outline or elevation, and its horizontal
 * gutter belongs to the screen's content column. The first section on a screen passes `showSeparator = false`.
 *
 * `attention` paints a section that waits for the User with the accent-soft tone, as the brief's box inside the sheet.
 */
@Composable
fun Section(
    title: String,
    modifier: Modifier = Modifier,
    action: (@Composable RowScope.() -> Unit)? = null,
    attention: Boolean = false,
    showSeparator: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier) {
        if (showSeparator) {
            Spacer(Modifier.height(Spacing.s5))
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(Modifier.height(Spacing.s4))
        }
        Column(
            if (attention) {
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(BoxCornerRadius))
                    .padding(horizontal = Spacing.s4, vertical = Spacing.s3)
            } else {
                Modifier
            },
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    title,
                    modifier = Modifier.semantics { heading() },
                    style = DiveHubText.section,
                )
                action?.invoke(this)
            }
            Spacer(Modifier.height(Spacing.s2))
            content()
        }
    }
}
