package dev.divehub.android.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.divehub.android.ui.tools.ToolPage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The app shell's wiring (ADR 0079): the destinations resolve, the back stack keeps ADR 0042's "the logbook returns
 * on the page the Dive is on" and "a Dive opened from a tool returns to that tool page", and Navigation restores the
 * route arguments after process death.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AppNavHostTest {
    @get:Rule
    val compose = createComposeRule()

    private lateinit var controller: NavHostController
    private val screens = FakeScreens()
    private val logbookPages = mutableListOf<Int>()

    @Test
    fun `the graph resolves the logbook, tools and dive destinations`() {
        setContent()
        compose.runOnIdle { controller.navigate(MainRoute) }
        assertRoute<LogbookRoute>()

        compose.runOnIdle { screens.logbookOpenTools!!.invoke() }
        assertRoute<ToolsRoute>()

        compose.runOnIdle { screens.toolsOpenDive!!.invoke("dive-1") }
        assertRoute<DiveRoute>()
    }

    @Test
    fun `back from a dive returns to the logbook page the dive is on`() {
        setContent(divePage = 2)
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-1"))
        }
        compose.waitForIdle()
        assertEquals(1, screens.logbookPage)

        compose.runOnIdle { screens.diveBack!!.invoke() }

        assertRoute<LogbookRoute>()
        compose.waitForIdle()
        assertEquals(2, screens.logbookPage)
        assertEquals(listOf(2), logbookPages)
    }

    @Test
    fun `a dive opened from a tool returns to that tool page`() {
        setContent()
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ToolsRoute(ToolPage.Lead))
        }
        compose.waitForIdle()

        compose.runOnIdle { screens.toolsOpenDive!!.invoke("dive-1") }
        assertRoute<DiveRoute>()
        compose.runOnIdle { screens.diveBack!!.invoke() }

        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Lead, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
    }

    @Test
    fun `a tool page pushes and pops without losing the gas page underneath`() {
        setContent()
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.runOnIdle { screens.logbookOpenTools!!.invoke() }
        compose.runOnIdle { screens.toolsOnPage!!.invoke(ToolPage.Plan) }
        compose.runOnIdle { assertEquals(ToolPage.Plan, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }

        compose.runOnIdle { screens.toolsOnPage!!.invoke(ToolPage.Gas) }
        compose.runOnIdle { assertEquals(ToolPage.Gas, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }

        compose.runOnIdle { screens.toolsBack!!.invoke() }
        assertRoute<LogbookRoute>()
    }

    @Test
    fun `the session destination replaces the top level and clears the stack`() {
        val destination = mutableStateOf<Any?>(InstanceRoute)
        compose.setContent { start(destination = destination.value) }

        compose.runOnIdle { destination.value = MainRoute }
        assertRoute<LogbookRoute>()

        compose.runOnIdle { controller.navigate(DiveRoute("dive-1")) }
        compose.runOnIdle { destination.value = SignInRoute }
        assertRoute<SignInRoute>()
        compose.runOnIdle { assertFalse(controller.popBackStack()) }
    }

    @Test
    fun `process death restores the dive route and its id`() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { start(destination = null) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-7"))
        }
        compose.waitForIdle()

        restoration.emulateSavedInstanceStateRestore()

        assertRoute<DiveRoute>()
        compose.runOnIdle { assertEquals("dive-7", controller.currentBackStackEntry?.toRoute<DiveRoute>()?.id) }
        compose.waitForIdle()
        assertEquals("dive-7", screens.diveId)
    }

    @Test
    fun `stepping to a neighbour keeps one dive and the logbook page of the current one`() {
        setContent(divePage = 2)
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-1"))
        }
        compose.waitForIdle()

        compose.runOnIdle { screens.diveOpenDive!!.invoke("dive-2") }
        assertRoute<DiveRoute>()
        compose.runOnIdle {
            assertEquals("dive-2", controller.currentBackStackEntry?.toRoute<DiveRoute>()?.id)
            assertEquals(1, controller.currentBackStack.value.count { it.destination.hasRoute(DiveRoute::class) })
        }

        compose.runOnIdle { screens.diveBack!!.invoke() }
        assertRoute<LogbookRoute>()
        compose.waitForIdle()
        assertEquals(2, screens.logbookPage)
    }

    @Test
    fun `a neighbour opened from a tool returns to that tool page`() {
        setContent()
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ToolsRoute(ToolPage.Lead))
        }
        compose.runOnIdle { screens.toolsOpenDive!!.invoke("dive-1") }
        compose.runOnIdle { screens.diveOpenDive!!.invoke("dive-2") }
        compose.runOnIdle { screens.diveBack!!.invoke() }

        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Lead, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
    }

    @Test
    fun `a screens change does not reset the back stack`() {
        val replacement = FakeScreens()
        val active = mutableStateOf<AppScreens>(screens)
        compose.setContent { start(screens = active.value, destination = null) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-1"))
        }
        assertRoute<DiveRoute>()

        compose.runOnIdle { active.value = replacement }

        assertRoute<DiveRoute>()
        compose.waitForIdle()
        assertEquals("dive-1", replacement.diveId)
    }

    @Test
    fun `process death restores the logbook page`() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.runOnIdle { screens.logbookOnPage!!.invoke(3) }
        compose.waitForIdle()
        assertEquals(3, screens.logbookPage)

        restoration.emulateSavedInstanceStateRestore()

        assertRoute<LogbookRoute>()
        compose.waitForIdle()
        assertEquals(3, screens.logbookPage)
    }

    @Test
    fun `process death restores the tool page`() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { start(destination = null) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ToolsRoute(ToolPage.Loadings))
        }
        compose.waitForIdle()

        restoration.emulateSavedInstanceStateRestore()

        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Loadings, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
        compose.waitForIdle()
        assertEquals(ToolPage.Loadings, screens.toolsPage)
    }

    private fun setContent(divePage: Int? = null) {
        compose.setContent { start(divePage) }
    }

    @Composable
    private fun start(
        divePage: Int? = null,
        destination: Any? = InstanceRoute,
        screens: AppScreens = this.screens,
    ) {
        controller = rememberNavController()
        AppNavHost(
            navController = controller,
            screens = screens,
            destination = destination,
            divePage = { divePage },
            onLogbookPage = { logbookPages += it },
        )
    }

    private inline fun <reified T : Any> assertRoute() {
        compose.waitForIdle()
        compose.runOnIdle {
            assertTrue(
                "current destination is ${controller.currentDestination?.route}, not ${T::class}",
                controller.currentDestination?.hasRoute(T::class) == true,
            )
        }
    }

    /** Stubs that record what the shell asked for; the real screens are bound in `DiveHubApp`. */
    private class FakeScreens : AppScreens {
        var logbookPage = 1
        var diveId: String? = null
        var toolsPage: ToolPage? = null
        var logbookOnPage: ((Int) -> Unit)? = null
        var logbookOpenTools: (() -> Unit)? = null
        var logbookOpenDive: ((String) -> Unit)? = null
        var diveBack: (() -> Unit)? = null
        var diveOpenDive: ((String) -> Unit)? = null
        var toolsOnPage: ((ToolPage) -> Unit)? = null
        var toolsBack: (() -> Unit)? = null
        var toolsOpenDive: ((String) -> Unit)? = null

        @Composable
        override fun instance() {
            Text("instance")
        }

        @Composable
        override fun signIn() {
            Text("signin")
        }

        @Composable
        override fun logbook(
            page: Int,
            onPage: (Int) -> Unit,
            onOpenTools: () -> Unit,
            onOpenDive: (String) -> Unit,
        ) {
            logbookPage = page
            logbookOnPage = onPage
            logbookOpenTools = onOpenTools
            logbookOpenDive = onOpenDive
            Text("logbook:$page")
        }

        @Composable
        override fun dive(id: String, onBack: () -> Unit, onOpenDive: (String) -> Unit) {
            diveId = id
            diveBack = onBack
            diveOpenDive = onOpenDive
            Text("dive:$id")
        }

        @Composable
        override fun tools(
            page: ToolPage,
            onPage: (ToolPage) -> Unit,
            onBack: () -> Unit,
            onOpenDive: (String) -> Unit,
        ) {
            toolsPage = page
            toolsOnPage = onPage
            toolsBack = onBack
            toolsOpenDive = onOpenDive
            Text("tools:${page.name}")
        }
    }
}
