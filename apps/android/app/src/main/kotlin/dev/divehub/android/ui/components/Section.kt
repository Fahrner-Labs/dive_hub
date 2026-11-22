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
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing

/**
 * A titled part of the sheet, the Android [Panel]: a heading, an optional action and the content, separated from the
 * section before by a hairline with [Spacing.s5] above it. It has no card, outline or elevation, and its horizontal
 * gutter belongs to the screen's content column. The first section on a screen passes `showSeparator = false`.
 *
 * `attention` paints a section that waits for the User with the accent-soft tone, as the brief's box inside the sheet.
 * The heading and its action wrap: when they no longer fit together, the action moves below the heading instead of
 * squeezing it.
 *
 * The action is end-aligned only while it shares the heading's line; once it wraps it starts the next line. The rule
 * compares the heading's measured width with the action's minimum 48 dp target. That is exact for this brief's quiet
 * action, not a guess: the target is [QuietActionTarget] wide at its narrowest, and a label longer than the target is
 * as wide as its own box, so a wrapped label is already flush with the line's start edge and the internal alignment
 * cannot inset it.
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
                val shape = RoundedCornerShape(Radius.box)
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
                // End while the action's 48 dp target fits beside the heading, Start once it wraps. See the KDoc:
                // the target's minimum width is the only width the internal alignment can inset a label by, so the
                // answer cannot be wrong for a longer label.
                val titleWidth = rememberTextMeasurer()
                    .measure(AnnotatedString(title), style = DiveHubText.section).size.width
                val density = LocalDensity.current
                val gap = with(density) { Spacing.s2.roundToPx() }
                val target = with(density) { QuietActionTarget.roundToPx() }
                // Keep s2 between the heading and the target, so an action that only just fits shares the line with
                // air around it instead of landing flush against the heading (FAH-427).
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
