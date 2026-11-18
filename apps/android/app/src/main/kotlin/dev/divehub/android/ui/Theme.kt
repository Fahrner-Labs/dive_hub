package dev.divehub.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// The tonal-surfaces palette (ADR 0019, ADR 0064): white sheets on a deeper page tone, night dive blue-black.
private val LightColors = lightColorScheme(
    primary = Color(0xFF0B6076),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB9E7F3),
    onPrimaryContainer = Color(0xFF001F28),
    background = Color(0xFFE9EEF2),
    onBackground = Color(0xFF12212E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF12212E),
    surfaceVariant = Color(0xFFDDE7EC),
    onSurfaceVariant = Color(0xFF3E4C55),
    outline = Color(0xFF6F7D86),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7FD3E8),
    onPrimary = Color(0xFF00363F),
    primaryContainer = Color(0xFF004E5C),
    onPrimaryContainer = Color(0xFFB9E7F3),
    background = Color(0xFF061522),
    onBackground = Color(0xFFE2EDF2),
    surface = Color(0xFF0E2338),
    onSurface = Color(0xFFE2EDF2),
    surfaceVariant = Color(0xFF1B354B),
    onSurfaceVariant = Color(0xFFB9CBD6),
    outline = Color(0xFF8899A4),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
)

@Composable
fun DiveHubTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColors else LightColors, content = content)
}
