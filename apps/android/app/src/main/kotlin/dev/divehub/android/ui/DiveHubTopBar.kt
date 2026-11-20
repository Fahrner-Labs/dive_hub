package dev.divehub.android.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.divehub.android.R

/**
 * The one app bar every screen draws (FAH-335): the screen's title and, on a pushed screen, a real back arrow. A
 * top-level place passes no [onBack] because the bottom bar is where it leaves; its subscreens are reached from its
 * own body, never from text buttons in the bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiveHubTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.back),
                    )
                }
            }
        },
        actions = actions,
    )
}
