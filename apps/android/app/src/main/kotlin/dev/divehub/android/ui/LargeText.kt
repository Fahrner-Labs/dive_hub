package dev.divehub.android.ui

/**
 * ADR 0018's large-text layout: at 150 % font scale and above the Android client gives text the full width instead of
 * keeping the narrow-screen geometry. The top bar stacks its title, a `FactRow` puts its value under its label and a
 * logbook row moves its figures under its facts.
 */
internal const val LargeTextFontScale = 1.5f
