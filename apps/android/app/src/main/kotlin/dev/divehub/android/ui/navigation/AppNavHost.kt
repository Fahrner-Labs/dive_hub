package dev.divehub.android.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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

/** The logbook page saved on its back stack entry; ADR 0042 recomputes it from a Dive's position before popping. */
internal const val LOGBOOK_PAGE_KEY = "logbook_page"

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
    fun logbook(page: Int, onPage: (Int) -> Unit, onOpenTools: () -> Unit, onOpenDive: (String) -> Unit)

    @Composable
    fun dive(id: String, onBack: () -> Unit, onOpenDive: (String) -> Unit)

    @Composable
    fun tools(page: ToolPage, onPage: (ToolPage) -> Unit, onBack: () -> Unit, onOpenDive: (String) -> Unit)
}

/**
 * The signed-in/out graph (ADR 0079): `instance` and `signin` at the root, the `main` nested graph with `logbook` as
 * its start destination, `dive/{id}` above it, and `tools` beside it. Adding the settings destination in T3 adds one
 * destination here; it does not change the shell.
 */
@Composable
internal fun AppNavHost(
    navController: NavHostController,
    screens: AppScreens,
    destination: Any?,
    divePage: () -> Int?,
    onLogbookPage: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // NavHost remembers its graph: rebuild it only when the controller changes, never for a recomposition. The latest
    // screens and callbacks are read through State so a state change updates the content without resetting the stack.
    val currentScreens = rememberUpdatedState(screens)
    val currentDivePage = rememberUpdatedState(divePage)
    val currentOnLogbookPage = rememberUpdatedState(onLogbookPage)
    val graph = remember(navController) {
        navController.createGraph(startDestination = InstanceRoute) {
            composable<InstanceRoute> { currentScreens.value.instance() }
            composable<SignInRoute> { currentScreens.value.signIn() }
            navigation<MainRoute>(startDestination = LogbookRoute) {
                composable<LogbookRoute> { entry ->
                    val page by entry.savedStateHandle.getStateFlow(LOGBOOK_PAGE_KEY, 1).collectAsState()
                    currentScreens.value.logbook(
                        page = page,
                        onPage = { entry.savedStateHandle[LOGBOOK_PAGE_KEY] = it },
                        onOpenTools = { navController.navigate(ToolsRoute()) },
                        onOpenDive = { id -> navController.navigate(DiveRoute(id)) },
                    )
                }
                composable<DiveRoute> { entry ->
                    val route = entry.toRoute<DiveRoute>()
                    // ADR 0042: stepping to a neighbour moves the Dive; back still returns to the list on that Dive's page.
                    val onBack: () -> Unit = {
                        val previous = navController.previousBackStackEntry
                        if (previous?.destination?.hasRoute(LogbookRoute::class) == true) {
                            currentDivePage.value()?.let { page ->
                                previous.savedStateHandle[LOGBOOK_PAGE_KEY] = page
                                currentOnLogbookPage.value(page)
                            }
                        }
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
                composable<ToolsRoute> { entry ->
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
                        onBack = { navController.popBackStack() },
                        onOpenDive = { id -> navController.navigate(DiveRoute(id)) },
                    )
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
            }
        }
    }
    NavHost(navController = navController, graph = graph, modifier = modifier)
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
