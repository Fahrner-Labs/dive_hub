package dev.divehub.android.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import dev.divehub.android.R

val PoppinsFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
)

/** The design system's type scale: Poppins at 400, 500 and 600, from the web's six sizes plus the meta pair. */
object DiveHubText {
    private val largeHeadingTracking = (-0.01f).em

    private fun style(
        size: Int,
        lineHeight: Int,
        weight: FontWeight,
        letterSpacing: TextUnit = TextUnit.Unspecified,
    ): TextStyle = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = size.sp,
        lineHeight = lineHeight.sp,
        fontWeight = weight,
        letterSpacing = letterSpacing,
    )

    /** A Dive's headline facts, a screen's one big number. */
    val hero = style(32, 40, FontWeight.SemiBold, largeHeadingTracking)

    /** A screen title where there is no top app bar; an empty-state title. */
    val title = style(28, 36, FontWeight.SemiBold, largeHeadingTracking)

    /** A section heading, a top app bar title. */
    val section = style(20, 28, FontWeight.SemiBold, largeHeadingTracking)

    /** Body, inputs, fact values. */
    val body = style(16, 24, FontWeight.Normal)

    /** A row title, an emphasised value. */
    val bodyStrong = style(16, 24, FontWeight.Medium)

    /** Buttons, field labels, chips. */
    val label = style(14, 20, FontWeight.Medium)

    /** Counts, badges, a short state. */
    val metaStrong = style(12, 16, FontWeight.Medium)

    /** Dates, supporting text, secondary detail. */
    val meta = style(12, 16, FontWeight.Normal)
}

/**
 * Numbers in data (depths, durations, times, counts, temperatures, pressures, SAC and profile samples) line up by
 * being end-aligned in their column, never by a monospace font. Poppins, the one family, has no tabular (`tnum`)
 * feature in its 400/500/600 builds, so `fontFeatureSettings = "tnum"` would be a no-op; there is no tabular helper
 * until a build that carries the feature is bundled.
 */

/** Every Material slot is set from the semantic scale, so no slot falls back to a Material default. */
val DiveHubTypography = Typography(
    displayLarge = DiveHubText.hero,
    displayMedium = DiveHubText.hero,
    displaySmall = DiveHubText.hero,
    headlineLarge = DiveHubText.title,
    headlineMedium = DiveHubText.title,
    headlineSmall = DiveHubText.section,
    titleLarge = DiveHubText.section,
    titleMedium = DiveHubText.bodyStrong,
    titleSmall = DiveHubText.bodyStrong,
    bodyLarge = DiveHubText.body,
    bodyMedium = DiveHubText.body,
    bodySmall = DiveHubText.meta,
    labelLarge = DiveHubText.label,
    labelMedium = DiveHubText.metaStrong,
    labelSmall = DiveHubText.meta,
)
