package dev.divehub.android.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.divehub.android.R

/** The signed-in shell's bottom-bar places (ADR 0079 D2); dive detail is not one of them. */
internal enum class AppTab(
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
) {
    Logbook(R.string.logbook_title, R.drawable.ic_bottom_bar_logbook),
    Tools(R.string.tools_title, R.drawable.ic_bottom_bar_tools),
    Settings(R.string.settings_title, R.drawable.ic_bottom_bar_settings),
}

/** The M3 bottom bar for the three top-level signed-in places; the shell decides when it is visible. */
@Composable
internal fun DiveHubBottomBar(
    selected: AppTab,
    onSelect: (AppTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        AppTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selected,
                onClick = { onSelect(tab) },
                icon = {
                    Icon(
                        painter = painterResource(tab.icon),
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(tab.title)) },
            )
        }
    }
}
