package dev.divehub.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// The overhaul palette (ADR 0064): a near-neutral framed sheet, ink text and buttons, water blue only for links,
// focus and data. It supersedes ADR 0019's navy tones.
private val LightColors = lightColorScheme(
    primary = Color(0xFF0F5E8C),          // --color-accent
    onPrimary = Color(0xFFFFFFFF),        // --color-on-accent
    primaryContainer = Color(0xFFE1EEF6), // --color-accent-soft
    secondaryContainer = Color(0xFFE1EEF6), // selected chips keep the accent-soft tone
    tertiary = Color(0xFFC8102E),         // --color-brand-flag: the Waypoint mark only, never a UI accent
    onSecondaryContainer = Color(0xFF0E1A23),
    onPrimaryContainer = Color(0xFF0E1A23),
    background = Color(0xFFEEF3F6),       // --color-bg
    onBackground = Color(0xFF0E1A23),     // --color-text
    surface = Color(0xFFFFFFFF),          // --color-surface
    onSurface = Color(0xFF0E1A23),
    surfaceVariant = Color(0xFFF4F7F9),   // --color-surface-sunken
    onSurfaceVariant = Color(0xFF52626F), // --color-text-muted
    outline = Color(0xFF6E8497),          // --color-border-strong
    error = Color(0xFFB3122B),            // --color-danger
    onError = Color(0xFFFFFFFF),          // --color-on-danger
    errorContainer = Color(0xFFFBE9EC),   // --color-danger-soft
    onErrorContainer = Color(0xFFB3122B),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF62B6E6),
    onPrimary = Color(0xFF0B0D0F),
    primaryContainer = Color(0xFF14303F),
    secondaryContainer = Color(0xFF14303F),
    tertiary = Color(0xFFC8102E),         // --color-brand-flag: the Waypoint mark only, never a UI accent
    onSecondaryContainer = Color(0xFFE9EEF2),
    onPrimaryContainer = Color(0xFFE9EEF2),
    background = Color(0xFF0B0D0F),
    onBackground = Color(0xFFE9EEF2),
    surface = Color(0xFF131619),
    onSurface = Color(0xFFE9EEF2),
    surfaceVariant = Color(0xFF1A1E22),
    onSurfaceVariant = Color(0xFFA1ACB5),
    outline = Color(0xFF6B7782),
    error = Color(0xFFFF7A7A),
    onError = Color(0xFF0B0D0F),
    errorContainer = Color(0xFF3A1620),
    onErrorContainer = Color(0xFFFF7A7A),
)

@Composable
fun DiveHubTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = DiveHubTypography,
        content = content,
    )
}

/** The design system's success colour (kelp); Material 3 has no success slot, so it follows the background's scheme. */
@Composable
internal fun successColor(): Color =
    if (MaterialTheme.colorScheme.background.luminance() < 0.5f) Color(0xFF5CC593) else Color(0xFF2A7150)

@Composable
private fun isDarkScheme(): Boolean = MaterialTheme.colorScheme.background.luminance() < 0.5f

/**
 * Semantic colours Material 3 has no role for. They follow the scheme the theme is painting, so a component reads the
 * same name in light and dark. The depth and ascent values are the web's data tokens, not new colours.
 */
object DiveHubColors {
    /** The decorative hairline (1 dp of `border`), never a control boundary. */
    val border: Color
        @Composable get() = if (isDarkScheme()) Color(0xFF22272C) else Color(0xFFE1E7EC)

    /** The ink primary button until `ColorScheme.primary` carries ink: text-ink in the light, its inverse in the dark. */
    val ink: Color
        @Composable get() = if (isDarkScheme()) Color(0xFFE9EEF2) else Color(0xFF0E1A23)

    /** The text on the ink button. */
    val onInk: Color
        @Composable get() = if (isDarkScheme()) Color(0xFF0B0D0F) else Color(0xFFFFFFFF)

    /** The water column's shallow end in the profile sketch. */
    val depthShallow: Color
        @Composable get() = if (isDarkScheme()) Color(0xFF62B6E6) else Color(0xFF8FD3E8)

    /** The water column's deep end in the profile sketch. */
    val depthDeep: Color
        @Composable get() = if (isDarkScheme()) Color(0xFF1B4867) else Color(0xFF0F5E8C)

    /** Ascent by speed, slowest band first; faster is also drawn thicker. */
    val ascent1: Color
        @Composable get() = if (isDarkScheme()) Color(0xFFE6C04A) else Color(0xFF9A7400)

    val ascent2: Color
        @Composable get() = if (isDarkScheme()) Color(0xFFF29A4A) else Color(0xFFC2570C)

    val ascent3: Color
        @Composable get() = if (isDarkScheme()) Color(0xFFFFB9A3) else Color(0xFF8A2F0A)
}
