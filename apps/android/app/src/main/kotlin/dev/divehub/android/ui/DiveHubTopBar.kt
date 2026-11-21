package dev.divehub.android.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.divehub.android.R

/**
 * The one app bar every screen draws (FAH-335): the screen's title and, on a pushed screen, a real back arrow. A
 * top-level place passes no [onBack] because the bottom bar is where it leaves; its subscreens are reached from its
 * own body, never from text buttons in the bar.
 *
 * [centerTitle] is for a screen whose title is the subject of the page rather than a section label; the Dive page
 * centres it between its leading back arrow and its trailing neighbour chevrons (ADR 0018, 2026-11-20 amendment).
 * [titleModifier] carries a screen's own semantics on the title, such as the heading role. [colors] is for a screen
 * that follows the brief's frame tone (the background behind the bars) rather than Material's surface.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiveHubTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    centerTitle: Boolean = false,
    titleModifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    actions: @Composable RowScope.() -> Unit = {},
) {
    val navigationIcon: @Composable () -> Unit = {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.back),
                )
            }
        }
    }
    if (centerTitle) {
        CenterAlignedTopAppBar(
            title = { Text(title, modifier = titleModifier) },
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
        )
    } else {
        TopAppBar(
            title = { Text(title, modifier = titleModifier) },
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
        )
    }
}
