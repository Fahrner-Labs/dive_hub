package dev.divehub.android.ui

import androidx.compose.ui.unit.dp

/**
 * The 4 dp grid: every padding, margin, gap and spacer is a step on this scale. The numbers are the web's
 * `--space-*` numbers, so a value is found the same way in both codebases. Fixed dimensions that are not spacing (an
 * icon, a chart height, a label column) stay named constants in their own component.
 */
object Spacing {
    /** Hairline gaps, icon-to-label, tight pairs. */
    val s1 = 4.dp

    /** Heading to content, chip gaps, between adjacent targets. */
    val s2 = 8.dp

    /** Row vertical padding, control inner padding. */
    val s3 = 12.dp

    /** Screen gutter, field gaps, section padding on a phone. */
    val s4 = 16.dp

    /** Above a section hairline, dialog and sheet padding, empty states. */
    val s5 = 24.dp

    /** Large separation, tablet gutter. */
    val s6 = 32.dp

    /** A page's largest separation, empty-state blocks. */
    val s7 = 48.dp
}
