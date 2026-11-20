package dev.divehub.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
