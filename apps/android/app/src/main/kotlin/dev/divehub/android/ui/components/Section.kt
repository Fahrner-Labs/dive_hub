package dev.divehub.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import dev.divehub.android.ui.DiveHubColors
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
 * The heading and its action wrap: at 320 dp or 200% font scale the action moves below the heading instead of
 * squeezing it.
 */
@OptIn(ExperimentalLayoutApi::class)
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
            HorizontalDivider(color = DiveHubColors.border)
            Spacer(Modifier.height(Spacing.s4))
        }
        Column(
            if (attention) {
                val shape = RoundedCornerShape(BoxCornerRadius)
                Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = Spacing.s5, vertical = Spacing.s4)
            } else {
                Modifier
            },
        ) {
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                // A short action lines up with the column's outer edge: End while it shares the heading's line,
                // Start once the action wraps below it. Guessing the action's 48 dp target is enough because a
                // longer label is as wide as its own box, so the edge cannot inset it.
                val titleWidth = rememberTextMeasurer()
                    .measure(AnnotatedString(title), style = DiveHubText.section).size.width
                val density = LocalDensity.current
                val gap = with(density) { Spacing.s2.roundToPx() }
                val target = with(density) { QuietActionTarget.roundToPx() }
                val actionAtEnd = titleWidth + gap + target <= constraints.maxWidth
                FlowRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.spacedBy(Spacing.s2),
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        title,
                        modifier = Modifier.semantics { heading() },
                        style = DiveHubText.section,
                    )
                    CompositionLocalProvider(
                        LocalQuietActionAlignment provides
                            if (actionAtEnd) Alignment.CenterEnd else Alignment.CenterStart,
                    ) {
                        action?.invoke(this)
                    }
                }
            }
            Spacer(Modifier.height(Spacing.s2))
            content()
        }
    }
}
