package dev.divehub.android.ui.components

import android.provider.Settings
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** The skeleton bar's fixed height; not a spacing step. Its corner is the brief's [Radius.control]. */
private val SkeletonBarHeight = 12.dp

private const val SkeletonPulseMs = 1_600
private const val SkeletonPulseAlpha = 0.7f
private const val AnimatorScaleDisabled = 0f

/** ADR 0064's pause before a skeleton replaces a blank slot; every screen's skeleton waits the same 150 ms. */
internal const val SkeletonDelayMs = 150L

/** ADR 0064's content arrival: the skeleton gives way to the page over the same 150 ms, instantly with motion off. */
internal const val ContentRevealMs = 150

/**
 * ADR 0064's reduced motion: the system's animations are off, so the skeleton stands still and content arrives
 * without a fade. The shared reading for the screens that use it, so none of them invents its own test.
 */
@Composable
fun rememberReducedMotion(): Boolean {
    val context = LocalContext.current
    return remember(context) {
        Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) ==
            AnimatorScaleDisabled
    }
}

/** The skeleton's gentle pulse, or a still 1f when the system's animations are off. */
@Composable
fun rememberSkeletonAlpha(label: String): Float {
    if (rememberReducedMotion()) return 1f
    return rememberInfiniteTransition(label = label).animateFloat(
        initialValue = 1f,
        targetValue = SkeletonPulseAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(SkeletonPulseMs, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "$label bar alpha",
    ).value
}

/** One flat bar of a skeleton, in the screen's final layout; decoration only, so TalkBack reads the screen's label. */
@Composable
fun SkeletonBar(modifier: Modifier, height: Dp = SkeletonBarHeight, alpha: Float = 1f) {
    Box(
        modifier
            .height(height)
            .alpha(alpha)
            .clip(RoundedCornerShape(Radius.control))
            // A visible tone, not surfaceVariant: 1.08:1 on the sheet read as an empty page (FAH-366 critique).
            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.32f)),
    )
}
