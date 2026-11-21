package dev.divehub.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import dev.divehub.android.R
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.Spacing

/** The brief's `Radius.control`: a button is not Material's pill. */
private val ControlCorner = 8.dp

/** The quiet action's disclosure chevron; not a spacing step. */
private val QuietActionIcon = 18.dp

/** The brief's 48 × 48 dp target: a quiet action may look smaller, but its target never does. */
internal val QuietActionTarget = 48.dp

/**
 * The edge a quiet action's label takes inside its 48 dp target. The content column starts at [Alignment.CenterStart];
 * a Section's action slot asks for [Alignment.CenterEnd] when its heading and action share a line, so a short label
 * such as "Edit" ends on the column's right edge instead of centring in a minimum-width box (FAH-366 critique).
 */
internal val LocalQuietActionAlignment = staticCompositionLocalOf<Alignment> { Alignment.CenterStart }

/**
 * The brief's ink primary button: ink in the light, the inverted ink in the dark, with the control radius. One
 * primary action per section; [modifier] carries the caller's layout.
 */
@Composable
fun InkButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(ControlCorner),
        colors = ButtonDefaults.buttonColors(
            containerColor = DiveHubColors.ink,
            contentColor = DiveHubColors.onInk,
        ),
    ) {
        Text(text)
    }
}

/**
 * A text-like action (paging, "Show all dives", retry, a disclosure): no fill and no accent, because the accent means
 * a link, focus or data and these are navigation or disclosure.
 *
 * The label sits on the edge [LocalQuietActionAlignment] names, flush with the caller's content column, while the
 * 48 dp target grows away from that edge. Material's `TextButton` cannot do this: its 58 dp minimum width centres a
 * short label, so "Edit" floated inside the column (FAH-366 critique). [expanded] draws a quiet disclosure chevron
 * after the label and puts the state on the button for TalkBack; pass `null` for a plain action.
 */
@Composable
fun QuietButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    expanded: Boolean? = null,
) {
    val contentColor = MaterialTheme.colorScheme.onSurface
    val state = expanded?.let {
        stringResource(if (it) R.string.action_expanded else R.string.action_collapsed)
    }
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = QuietActionTarget, minHeight = QuietActionTarget)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            // The old TextButton's vertical padding, kept so a large-text label does not shrink the row.
            .padding(vertical = Spacing.s2)
            .semantics(mergeDescendants = true) {
                if (state != null) stateDescription = state
            },
        contentAlignment = LocalQuietActionAlignment.current,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text,
                style = MaterialTheme.typography.labelLarge,
                color = if (enabled) contentColor else contentColor.copy(alpha = DisabledContentAlpha),
            )
            if (expanded != null) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = if (enabled) contentColor else contentColor.copy(alpha = DisabledContentAlpha),
                    modifier = Modifier
                        .padding(start = Spacing.s1)
                        .size(QuietActionIcon)
                        .rotate(if (expanded) 180f else 0f),
                )
            }
        }
    }
}

/** Material's disabled content alpha; the quiet action invents no second reading. */
private const val DisabledContentAlpha = 0.38f
