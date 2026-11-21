package dev.divehub.android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing

/**
 * Why a screen is empty and the way on: an optional title in [DiveHubText.title], the reason in [DiveHubText.body]
 * and the action below it. The action is required because an empty state has no dead end; the caller supplies the
 * design-system button so its role (a primary [androidx.compose.material3.Button] or a quiet text button) is the
 * caller's choice.
 */
@Composable
fun EmptyState(
    message: String,
    action: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.s5, vertical = Spacing.s7),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        title?.let {
            Text(
                it,
                modifier = Modifier.semantics { heading() },
                style = DiveHubText.title,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(Spacing.s2))
        }
        Text(
            message,
            style = DiveHubText.body,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.s5))
        action()
    }
}
