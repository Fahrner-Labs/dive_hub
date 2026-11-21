package dev.divehub.android.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.createGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import dev.divehub.android.ui.UiState
import dev.divehub.android.ui.tools.ToolPage

/** The Dive the logbook should scroll to when the User leaves it; ADR 0042 keeps the list where the Dive was. */
internal const val LOGBOOK_DIVE_KEY = "logbook_dive"

/** The Dive's 1-based position in the list; the paged fallback turns it back into a page (ADR 0042). */
internal const val LOGBOOK_POSITION_KEY = "logbook_position"

/**
 * The screens the shell draws for each destination (ADR 0079). `DiveHubApp` binds them to the real screens; tests bind
 * stubs so the graph and its back stack can be exercised without a session or a store.
 */
internal interface AppScreens {
    @Composable
    fun instance()

    @Composable
    fun signIn()

    @Composable
    fun logbook(
        focusDiveId: String?,
        focusPosition: Int?,
        onFocusHandled: () -> Unit,
        onOpenDive: (String) -> Unit,
    )

    @Composable
    fun dive(id: String, onBack: () -> Unit, onOpenDive: (String) -> Unit)

    @Composable
    fun tools(page: ToolPage, onPage: (ToolPage) -> Unit, onOpenDive: (String) -> Unit)

    @Composable
    fun settings(onOpenDebug: () -> Unit)

    @Composable
    fun settingsDebug(owner: ViewModelStoreOwner, onBack: () -> Unit)
}

/**
 * The signed-in/out graph (ADR 0079): `instance` and `signin` at the root, the `main` nested graph with `logbook` as
 * its start destination, `dive/{id}` above it, `tools` and `settings` beside it, and `settings/debug` above settings.
 * The bottom bar exposes the three top-level signed-in places and saves each tab's state; a Dive is pushed above its
 * origin, not into the bar.
 */
@Composable
internal fun AppNavHost(
    navController: NavHostController,
    screens: AppScreens,
    destination: Any?,
    diveId: () -> String?,
    divePosition: () -> Int?,
    modifier: Modifier = Modifier,
) {
    // NavHost remembers its graph: rebuild it only when the controller changes, never for a recomposition. The latest
    // screens and callbacks are read through State so a state change updates the content without resetting the stack.
    val currentScreens = rememberUpdatedState(screens)
    val currentDiveId = rememberUpdatedState(diveId)
    val currentDivePosition = rememberUpdatedState(divePosition)
    val graph = remember(navController) {
        navController.createGraph(startDestination = InstanceRoute) {
            composable<InstanceRoute> { currentScreens.value.instance() }
            composable<SignInRoute> { currentScreens.value.signIn() }
            navigation<MainRoute>(startDestination = LogbookRoute) {
                composable<LogbookRoute> { entry ->
                    MainShell(navController) {
                        val focusDive by entry.savedStateHandle
                            .getStateFlow<String?>(LOGBOOK_DIVE_KEY, null)
                            .collectAsState()
                        val focusPosition by entry.savedStateHandle
                            .getStateFlow<Int?>(LOGBOOK_POSITION_KEY, null)
                            .collectAsState()
                        currentScreens.value.logbook(
                            focusDiveId = focusDive,
                            focusPosition = focusPosition,
                            onFocusHandled = {
                                entry.savedStateHandle[LOGBOOK_DIVE_KEY] = null
                                entry.savedStateHandle[LOGBOOK_POSITION_KEY] = null
                            },
                            onOpenDive = { id -> navController.navigate(DiveRoute(id)) { launchSingleTop = true } },
                        )
                    }
                }
                composable<DiveRoute> { entry ->
                    MainShell(
                        navController,
                        // Leaving the Dive for the Logbook tab focuses its row too, as back does.
                        onLeaveDive = {
                            navController.leaveDiveForLogbook(currentDiveId.value(), currentDivePosition.value())
                        },
                    ) {
                        val route = entry.toRoute<DiveRoute>()
                        // ADR 0042: stepping to a neighbour moves the Dive; back still returns to that Dive's row.
                        val onBack: () -> Unit = {
                            navController.leaveDiveForLogbook(currentDiveId.value(), currentDivePosition.value())
                            navController.popBackStack()
                        }
                        BackHandler(onBack = onBack)
                        currentScreens.value.dive(
                            id = route.id,
                            onBack = onBack,
                            onOpenDive = { id ->
                                if (id != route.id) {
                                    navController.navigate(DiveRoute(id)) {
                                        popUpTo<DiveRoute> { inclusive = true }
                                    }
                                }
                            },
                        )
                    }
                }
                composable<ToolsRoute> { entry ->
                    MainShell(navController) {
                        val route = entry.toRoute<ToolsRoute>()
                        currentScreens.value.tools(
                            page = route.page,
                            onPage = { page ->
                                when {
                                    page == route.page -> Unit
                                    page == ToolPage.Gas -> navController.popBackStack()
                                    else -> navController.navigate(ToolsRoute(page))
                                }
                            },
                            onOpenDive = { id -> navController.navigate(DiveRoute(id)) { launchSingleTop = true } },
                        )
                    }
                }
                composable<SettingsRoute> {
                    MainShell(navController) {
                        currentScreens.value.settings(
                            onOpenDebug = {
                                navController.navigate(SettingsDebugRoute) { launchSingleTop = true }
                            },
                        )
                    }
                }
                composable<SettingsDebugRoute> {
                    // The Debug screen shares the settings entry's view model: it only ever sits above that entry,
                    // which owns the state both screens read (FAH-343).
                    val owner = remember { navController.getBackStackEntry<SettingsRoute>() }
                    MainShell(navController) {
                        currentScreens.value.settingsDebug(owner = owner, onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
    // The session state decides the top-level destination; Navigation only renders it (ADR 0079). A null destination
    // (loading, or the update gate) leaves the restored back stack alone.
    LaunchedEffect(destination) {
        if (destination != null && !navController.isShowing(destination)) {
            navController.navigate(destination) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    NavHost(navController = navController, graph = graph, modifier = modifier)
}

/**
 * The signed-in shell around a `main` destination (ADR 0079 D2). The bottom bar keeps the tab that opened the current
 * screen selected, so it stays on a Dive; instance and sign-in never enter this shell.
 */
@Composable
private fun MainShell(
    navController: NavHostController,
    onLeaveDive: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val backStack by navController.currentBackStack.collectAsState()
    val selectedTab = backStack.asReversed().firstNotNullOfOrNull { tabOf(it.destination) }
    Scaffold(
        // The screens draw their own insets; the scaffold only makes room for the bar it draws.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (selectedTab != null) {
                DiveHubBottomBar(
                    selected = selectedTab,
                    onSelect = { tab -> navController.navigateToTab(tab, onLeaveDive) },
                )
            }
        },
    ) { padding ->
        Box(Modifier.padding(padding).consumeWindowInsets(padding).fillMaxSize()) { content() }
    }
}

/** The bottom bar's place for [destination], or null outside the three top-level signed-in screens. */
private fun tabOf(destination: NavDestination): AppTab? = when {
    destination.hasRoute(LogbookRoute::class) -> AppTab.Logbook
    destination.hasRoute(ToolsRoute::class) -> AppTab.Tools
    destination.hasRoute(SettingsRoute::class) -> AppTab.Settings
    destination.hasRoute(SettingsDebugRoute::class) -> AppTab.Settings
    else -> null
}

/**
 * Put the Dive's id and position into the logbook entry below it before leaving, so the list scrolls back to its row
 * (or, with a server-answered filter, pages to it) (ADR 0042). Only a Dive opened from the logbook has such an entry;
 * one opened from a tool keeps its tool page.
 */
private fun NavController.leaveDiveForLogbook(diveId: String?, position: Int?) {
    val previous = previousBackStackEntry
    if (previous?.destination?.hasRoute(LogbookRoute::class) == true) {
        diveId?.let { previous.savedStateHandle[LOGBOOK_DIVE_KEY] = it }
        position?.let { previous.savedStateHandle[LOGBOOK_POSITION_KEY] = it }
    }
}

/**
 * A bottom-bar switch keeps the logbook as the main graph's start destination and saves the tab being left, so a
 * return restores its scroll position and back from the start destination leaves the app instead of showing sign-in.
 */
private fun NavHostController.navigateToTab(tab: AppTab, onLeaveDive: () -> Unit = {}) {
    val active = currentBackStack.value.asReversed().firstNotNullOfOrNull { tabOf(it.destination) }
    if (tab == active) {
        // Reselecting a place returns to its root: pop what sits above it (a Dive, a tool subpage or the Debug
        // subscreen), never the root itself, so the screen keeps its entry and does not replay its enter transition.
        when (tab) {
            AppTab.Logbook -> {
                // A Dive's row must reach the list, as it does on the Dive's own back path (ADR 0042).
                if (currentDestination?.hasRoute(DiveRoute::class) == true) onLeaveDive()
                popBackStack(LogbookRoute, inclusive = false)
            }
            AppTab.Tools -> popBackStack(ToolsRoute(), inclusive = false)
            AppTab.Settings -> popBackStack(SettingsRoute, inclusive = false)
        }
        return
    }
    val options: androidx.navigation.NavOptionsBuilder.() -> Unit = {
        popUpTo<LogbookRoute> { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
    when (tab) {
        AppTab.Logbook -> navigate(LogbookRoute, options)
        AppTab.Tools -> navigate(ToolsRoute(), options)
        AppTab.Settings -> navigate(SettingsRoute, options)
    }
}

/**
 * Drop the saved tab stacks when the User changes, so the next person never restores the previous one's screens.
 *
 * This runs after the session is gone, when the current destination is [InstanceRoute] or [SignInRoute] — outside the
 * `main` graph. The filled-route overloads (`clearBackStack(LogbookRoute)`) look the destination up from the current
 * graph and throw `IllegalStateException` there; the [KClass] overloads clear by destination id and work from any
 * destination (FAH-327).
 */
internal fun NavHostController.clearSavedTabStates() {
    clearBackStack(LogbookRoute::class)
    clearBackStack(ToolsRoute::class)
    clearBackStack(SettingsRoute::class)
    clearBackStack(SettingsDebugRoute::class)
}

/**
 * Which top-level destination the session state asks for; null while loading and while the update gate is up, so the
 * graph keeps its restored back stack (ADR 0079). Navigation renders this state; it never owns it.
 */
internal fun sessionDestination(state: UiState): Any? = when {
    state.loading || state.updateRequired -> null
    state.user != null -> MainRoute
    state.instanceUrl == null -> InstanceRoute
    else -> SignInRoute
}

/** True when the controller already shows [route], including inside the `main` nested graph. */
internal fun NavController.isShowing(route: Any): Boolean =
    currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true
