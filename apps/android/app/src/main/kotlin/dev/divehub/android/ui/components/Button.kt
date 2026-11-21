package dev.divehub.android.ui.components

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import dev.divehub.android.ui.DiveHubColors
import dev.divehub.android.ui.Spacing

/** The brief's `Radius.control`: a button is not Material's pill. */
private val ControlCorner = 8.dp

/** The quiet action's disclosure chevron; not a spacing step. */
private val QuietActionIcon = 18.dp

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
 * a link, focus or data and these are navigation or disclosure. Its target stays 48 dp through [TextButton].
 *
 * The label sits on the caller's content column: Material's 12 dp text inset is removed, so a quiet action under a
 * section heading lines up with the section's facts instead of floating right of them (FAH-366 critique). [expanded]
 * draws a quiet disclosure chevron after the label; pass `null` for a plain action.
 */
@Composable
fun QuietButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    expanded: Boolean? = null,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = Spacing.s2),
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
    ) {
        Text(text)
        if (expanded != null) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = Spacing.s1)
                    .size(QuietActionIcon)
                    .rotate(if (expanded) 180f else 0f),
            )
        }
    }
}
