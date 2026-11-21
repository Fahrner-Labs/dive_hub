package dev.divehub.android.ui.components

import androidx.compose.ui.unit.dp

/**
 * The design brief's shape tokens (design-system-android.md, "Radius by hierarchy"): fixed radii, not spacing steps.
 * A screen or component reads these instead of repeating a `8.dp` or a `16.dp`.
 */
object Radius {
    /** Small marks, the brand mark's inner detail, hairline ends. */
    val sm = 2.dp

    /** Buttons, text fields, selects, segmented controls, the profile chart's frame. */
    val control = 8.dp

    /** Boxes inside the sheet: a notice, attention, an inset block. */
    val box = 10.dp

    /** A sheet's visible corner, dialogs and bottom sheets. */
    val sheet = 16.dp
}
