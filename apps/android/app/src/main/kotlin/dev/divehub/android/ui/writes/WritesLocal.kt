package dev.divehub.android.ui.writes

import androidx.compose.runtime.staticCompositionLocalOf

/** The pending indicator's state, provided once around the signed-in graph; screens read it without threading params. */
val LocalWrites = staticCompositionLocalOf { WritesUiState() }

/** What the indicator's action does: open the queue screen, where pending, failed and parked entries all live. */
data class WritesActions(val onReview: () -> Unit)

val LocalWritesActions = staticCompositionLocalOf { WritesActions(onReview = {}) }
