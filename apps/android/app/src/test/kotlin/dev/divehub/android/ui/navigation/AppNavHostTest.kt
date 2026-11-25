package dev.divehub.android.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.divehub.android.ui.tools.ToolPage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The app shell's wiring (ADR 0079): the destinations resolve, the back stack keeps ADR 0042's "the logbook returns
 * on the Dive's row" and "a Dive opened from a tool returns to that tool page", and Navigation restores the route
 * arguments after process death.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AppNavHostTest {
    @get:Rule
    val compose = createComposeRule()

    private lateinit var controller: NavHostController
    private val screens = FakeScreens()

    @Test
    fun `the graph resolves the logbook, tools and dive destinations`() {
        setContent()
        compose.runOnIdle { controller.navigate(MainRoute) }
        assertRoute<LogbookRoute>()

        compose.onNodeWithText("Tools").performClick()
        assertRoute<ToolsRoute>()

        compose.runOnIdle { screens.toolsOpenDive!!.invoke("dive-1") }
        assertRoute<DiveRoute>()
    }

    @Test
    fun `back from a dive focuses its row in the logbook`() {
        setContent(diveId = "dive-2", divePosition = 26)
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-1"))
        }
        compose.waitForIdle()
        assertNull(screens.logbookFocus)

        compose.runOnIdle { screens.diveBack!!.invoke() }

        assertRoute<LogbookRoute>()
        compose.waitForIdle()
        assertEquals("dive-2", screens.logbookFocus)
        assertEquals(26, screens.logbookFocusPosition)

        compose.runOnIdle { screens.logbookFocusHandled!!.invoke() }
        compose.waitForIdle()
        assertNull(screens.logbookFocus)
        assertNull(screens.logbookFocusPosition)
    }

    @Test
    fun `a dive opened from a tool returns to that tool page`() {
        setContent()
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ToolsRoute())
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
    fun `a tool page pushes and pops without losing the index underneath`() {
        setContent()
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        compose.runOnIdle { assertEquals(ToolPage.Index, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
        compose.runOnIdle { screens.toolsOnPage!!.invoke(ToolPage.Gas) }
        compose.runOnIdle { assertEquals(ToolPage.Gas, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }

        compose.runOnIdle { screens.toolsOnPage!!.invoke(ToolPage.Index) }
        compose.runOnIdle { assertEquals(ToolPage.Index, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }

        compose.runOnIdle { assertTrue(controller.popBackStack()) }
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
    fun `the restored stack survives the session becoming ready`() {
        val restoration = StateRestorationTester(compose)
        val destination = mutableStateOf<Any?>(null)
        restoration.setContent { start(destination = destination.value) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-9"))
        }
        compose.waitForIdle()

        restoration.emulateSavedInstanceStateRestore()
        compose.runOnIdle { destination.value = MainRoute }

        assertRoute<DiveRoute>()
        compose.runOnIdle { assertEquals("dive-9", controller.currentBackStackEntry?.toRoute<DiveRoute>()?.id) }
        compose.waitForIdle()
        assertEquals("dive-9", screens.diveId)
    }

    @Test
    fun `the dive's edit action opens the editor and its back returns to the dive`() {
        setContent()
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-1"))
        }
        compose.waitForIdle()

        compose.runOnIdle { screens.diveEdit!!.invoke("dive-1") }

        assertRoute<DiveEditRoute>()
        compose.waitForIdle()
        assertEquals("dive-1", screens.diveEditId)
        compose.runOnIdle { screens.diveEditBack!!.invoke() }
        assertRoute<DiveRoute>()
    }

    @Test
    fun `stepping to a neighbour focuses the current dive on the way back`() {
        setContent(diveId = "dive-2", divePosition = 26)
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
        assertEquals("dive-2", screens.logbookFocus)
        assertEquals(26, screens.logbookFocusPosition)
    }

    @Test
    fun `a neighbour opened from a tool returns to that tool page`() {
        setContent()
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ToolsRoute())
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
    fun `process death keeps the dive the logbook returns to`() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { start(destination = null, diveId = "dive-3", divePosition = 51) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(DiveRoute("dive-1"))
        }
        compose.waitForIdle()
        compose.runOnIdle { screens.diveBack!!.invoke() }
        compose.waitForIdle()
        assertEquals("dive-3", screens.logbookFocus)
        assertEquals(51, screens.logbookFocusPosition)

        restoration.emulateSavedInstanceStateRestore()

        assertRoute<LogbookRoute>()
        compose.waitForIdle()
        assertEquals("dive-3", screens.logbookFocus)
        assertEquals(51, screens.logbookFocusPosition)
    }

    @Test
    fun `process death restores the tool page`() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { start(destination = null) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ToolsRoute())
            controller.navigate(ToolsRoute(ToolPage.Loadings))
        }
        compose.waitForIdle()

        restoration.emulateSavedInstanceStateRestore()

        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Loadings, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
        compose.waitForIdle()
        assertEquals(ToolPage.Loadings, screens.toolsPage)
    }

    @Test
    fun `the bottom bar opens settings and keeps it after process death`() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.waitForIdle()

        compose.onNodeWithText("Settings").performClick()
        assertRoute<SettingsRoute>()

        restoration.emulateSavedInstanceStateRestore()

        assertRoute<SettingsRoute>()
    }

    @Test
    fun `back from a tool tab returns to the logbook and leaves no sign-in below it`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        assertRoute<ToolsRoute>()

        compose.runOnIdle { assertTrue(controller.popBackStack()) }

        assertRoute<LogbookRoute>()
        compose.runOnIdle {
            assertFalse(controller.currentBackStack.value.any { it.destination.hasRoute(SignInRoute::class) })
        }
    }

    @Test
    fun `the bar stays on a dive with the tab that opened it selected`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        compose.runOnIdle { screens.toolsOpenDive!!.invoke("dive-1") }
        assertRoute<DiveRoute>()

        compose.onNodeWithText("Tools").assertIsSelected()
    }

    @Test
    fun `the debug entry pushes the diagnostics screen and back returns to settings`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Settings").performClick()
        assertRoute<SettingsRoute>()

        compose.runOnIdle { screens.settingsOpenDebug!!.invoke() }
        assertRoute<SettingsDebugRoute>()

        compose.runOnIdle { screens.settingsDebugBack!!.invoke() }
        assertRoute<SettingsRoute>()
    }

    @Test
    fun `the debug queue opens the change queue and back returns to debug`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Settings").performClick()
        compose.runOnIdle { screens.settingsOpenDebug!!.invoke() }
        assertRoute<SettingsDebugRoute>()

        compose.runOnIdle { screens.settingsDebugOpenQueue!!.invoke() }
        assertRoute<ConflictsRoute>()

        compose.runOnIdle { screens.conflictsBack!!.invoke() }
        assertRoute<SettingsDebugRoute>()
    }

    @Test
    fun `the scan entry pushes the scanner and back returns to settings`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Settings").performClick()
        assertRoute<SettingsRoute>()

        compose.runOnIdle { screens.settingsOpenScan!!.invoke() }
        assertRoute<ScanRoute>()
        assertTrue(screens.scanShown)

        compose.runOnIdle { screens.scanBack!!.invoke() }
        assertRoute<SettingsRoute>()
    }

    @Test
    fun `the settings tab stays selected on debug and reselecting it returns to the root`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Settings").performClick()
        compose.runOnIdle { screens.settingsOpenDebug!!.invoke() }
        assertRoute<SettingsDebugRoute>()

        compose.onNodeWithText("Settings").assertIsSelected()
        compose.onNodeWithText("Settings").performClick()

        assertRoute<SettingsRoute>()
    }

    @Test
    fun `the bar is absent outside the main graph`() {
        val destination = mutableStateOf<Any?>(InstanceRoute)
        compose.setContent { start(destination = destination.value) }
        compose.onNodeWithText("Settings").assertDoesNotExist()

        compose.runOnIdle { destination.value = SignInRoute }
        compose.onNodeWithText("Settings").assertDoesNotExist()
    }

    @Test
    fun `tapping the selected Tools tab leaves a dive for the index`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        compose.runOnIdle { screens.toolsOpenDive!!.invoke("dive-1") }
        assertRoute<DiveRoute>()

        compose.onNodeWithText("Tools").performClick()

        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Index, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
    }

    @Test
    fun `tapping the selected Tools tab on its root keeps the same entry`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        assertRoute<ToolsRoute>()
        val root = compose.runOnIdle { controller.currentBackStackEntry }

        compose.onNodeWithText("Tools").performClick()

        assertRoute<ToolsRoute>()
        compose.runOnIdle {
            assertEquals(root?.id, controller.currentBackStackEntry?.id)
            assertEquals(ToolPage.Index, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page)
        }
    }

    @Test
    fun `tapping the selected Tools tab on a tool page returns to the same root entry`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        compose.runOnIdle { screens.toolsOnPage!!.invoke(ToolPage.Plan) }
        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Plan, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
        val root = compose.runOnIdle { controller.currentBackStack.value.first { it.destination.hasRoute(ToolsRoute::class) } }

        compose.onNodeWithText("Tools").performClick()

        assertRoute<ToolsRoute>()
        compose.runOnIdle {
            assertEquals(ToolPage.Index, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page)
            assertEquals(root.id, controller.currentBackStackEntry?.id)
            assertEquals(root.id, controller.currentBackStack.value.first { it.destination.hasRoute(ToolsRoute::class) }.id)
        }
    }

    @Test
    fun `tapping the selected Logbook tab leaves a dive for the list`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.runOnIdle { screens.logbookOpenDive!!.invoke("dive-1") }
        assertRoute<DiveRoute>()

        compose.onNodeWithText("Logbook").performClick()

        assertRoute<LogbookRoute>()
    }

    @Test
    fun `tapping the selected Logbook tab while on a dive focuses its row`() {
        setContent(diveId = "dive-4", divePosition = 76)
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.runOnIdle { screens.logbookOpenDive!!.invoke("dive-1") }
        assertRoute<DiveRoute>()

        compose.onNodeWithText("Logbook").performClick()

        assertRoute<LogbookRoute>()
        compose.waitForIdle()
        assertEquals("dive-4", screens.logbookFocus)
        assertEquals(76, screens.logbookFocusPosition)
    }

    @Test
    fun `a fresh start clears saved tab states without touching the graph`() {
        compose.setContent { start(destination = null) }

        compose.runOnIdle { controller.clearSavedTabStates() }

        assertRoute<InstanceRoute>()
    }

    @Test
    fun `clearing saved tab states from outside the main graph drops the previous tool page`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        compose.runOnIdle { screens.toolsOnPage!!.invoke(ToolPage.Plan) }
        compose.onNodeWithText("Logbook").performClick()
        // A sign-out leaves the main graph; the wipe must not need the destination it is clearing to be reachable.
        compose.runOnIdle {
            controller.navigate(InstanceRoute) { popUpTo(controller.graph.id) { inclusive = true } }
        }
        compose.waitForIdle()
        assertRoute<InstanceRoute>()

        compose.runOnIdle { controller.clearSavedTabStates() }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()

        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Index, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
    }

    @Test
    fun `clearing saved tab states drops the previous tool page`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle { controller.navigate(MainRoute) }
        compose.onNodeWithText("Tools").performClick()
        compose.runOnIdle { screens.toolsOnPage!!.invoke(ToolPage.Plan) }
        compose.onNodeWithText("Logbook").performClick()

        compose.runOnIdle { controller.clearSavedTabStates() }
        compose.onNodeWithText("Tools").performClick()

        assertRoute<ToolsRoute>()
        compose.runOnIdle { assertEquals(ToolPage.Index, controller.currentBackStackEntry?.toRoute<ToolsRoute>()?.page) }
    }

    @Test
    fun `a merged dive in the conflict screen opens the dive`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ConflictsRoute)
        }
        assertRoute<ConflictsRoute>()

        compose.runOnIdle { screens.conflictsOpenDive!!.invoke("dive-9") }

        assertRoute<DiveRoute>()
        compose.runOnIdle { assertEquals("dive-9", controller.currentBackStackEntry?.toRoute<DiveRoute>()?.id) }
    }

    @Test
    fun `the conflict screen opens above the logbook and back returns to it`() {
        compose.setContent { start(destination = null) }
        compose.runOnIdle {
            controller.navigate(MainRoute)
            controller.navigate(ConflictsRoute)
        }
        assertRoute<ConflictsRoute>()

        compose.runOnIdle { screens.conflictsBack!!.invoke() }

        assertRoute<LogbookRoute>()
    }

    private fun setContent(diveId: String? = null, divePosition: Int? = null) {
        compose.setContent { start(diveId = diveId, divePosition = divePosition) }
    }

    @Composable
    private fun start(
        diveId: String? = null,
        divePosition: Int? = null,
        destination: Any? = InstanceRoute,
        screens: AppScreens = this.screens,
    ) {
        controller = rememberNavController()
        AppNavHost(
            navController = controller,
            screens = screens,
            destination = destination,
            diveId = { diveId },
            divePosition = { divePosition },
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
        var logbookFocus: String? = null
        var logbookFocusPosition: Int? = null
        var diveId: String? = null
        var toolsPage: ToolPage? = null
        var logbookFocusHandled: (() -> Unit)? = null
        var logbookOpenDive: ((String) -> Unit)? = null
        var diveBack: (() -> Unit)? = null
        var diveOpenDive: ((String) -> Unit)? = null
        var diveEdit: ((String) -> Unit)? = null
        var diveEditId: String? = null
        var diveEditBack: (() -> Unit)? = null
        var diveEditSaved: (() -> Unit)? = null
        var toolsOnPage: ((ToolPage) -> Unit)? = null
        var toolsOpenDive: ((String) -> Unit)? = null
        var settingsShown = false
        var settingsOpenDebug: (() -> Unit)? = null
        var settingsOpenScan: (() -> Unit)? = null
        var settingsDebugShown = false
        var settingsDebugBack: (() -> Unit)? = null
        var settingsDebugOpenQueue: (() -> Unit)? = null
        var conflictsShown = false
        var conflictsBack: (() -> Unit)? = null
        var conflictsOpenDive: ((String) -> Unit)? = null
        var scanShown = false
        var scanBack: (() -> Unit)? = null

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
            focusDiveId: String?,
            focusPosition: Int?,
            onFocusHandled: () -> Unit,
            onOpenDive: (String) -> Unit,
        ) {
            logbookFocus = focusDiveId
            logbookFocusPosition = focusPosition
            logbookFocusHandled = onFocusHandled
            logbookOpenDive = onOpenDive
            Text("logbook")
        }

        @Composable
        override fun dive(id: String, onBack: () -> Unit, onOpenDive: (String) -> Unit, onEdit: (String) -> Unit) {
            diveId = id
            diveBack = onBack
            diveOpenDive = onOpenDive
            diveEdit = onEdit
            Text("dive:$id")
        }

        @Composable
        override fun diveEdit(id: String, onBack: () -> Unit, onSaved: () -> Unit) {
            diveEditId = id
            diveEditBack = onBack
            diveEditSaved = onSaved
            Text("dive edit:$id")
        }

        @Composable
        override fun tools(
            page: ToolPage,
            onPage: (ToolPage) -> Unit,
            onOpenDive: (String) -> Unit,
        ) {
            toolsPage = page
            toolsOnPage = onPage
            toolsOpenDive = onOpenDive
            Text("tools:${page.name}")
        }

        @Composable
        override fun settings(onOpenDebug: () -> Unit, onOpenScan: () -> Unit) {
            settingsShown = true
            settingsOpenDebug = onOpenDebug
            settingsOpenScan = onOpenScan
            Text("settings")
        }

        @Composable
        override fun scan(onBack: () -> Unit) {
            scanShown = true
            scanBack = onBack
            Text("scan")
        }

        @Composable
        override fun settingsDebug(owner: ViewModelStoreOwner, onBack: () -> Unit, onOpenQueue: () -> Unit) {
            settingsDebugShown = true
            settingsDebugBack = onBack
            settingsDebugOpenQueue = onOpenQueue
            Text("settings debug")
        }

        @Composable
        override fun conflicts(onBack: () -> Unit, onOpenDive: (String) -> Unit) {
            conflictsShown = true
            conflictsBack = onBack
            conflictsOpenDive = onOpenDive
            Text("conflicts")
        }
    }
}
