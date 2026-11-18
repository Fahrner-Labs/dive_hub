package dev.divehub.android.ui.tools

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.planning.PlanningSacResult
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.tools.BottomTimeToolInput
import dev.divehub.shared.tools.GasNumbersInput
import dev.divehub.shared.tools.ToolSources
import dev.divehub.shared.tools.bottomTime
import dev.divehub.shared.tools.gasNumbers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The tools' contract-visible states as pictures (FAH-274; client contract, Tools): the notice before any input, an
 * answer's assumptions with their sources and `basedOn`, the binding limit, `outside_limits` with its reason and MOD,
 * and the empty store said as an empty state.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class ToolsScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `the notice is above the inputs`() {
        render(ToolsUiState(loading = false))
        compose.onNodeWithText(
            "An estimate, not advice: the dive computer and your training govern. No model guarantees safety.",
        ).assertIsDisplayed()
        compose.onNodeWithText("The dive").assertIsDisplayed()
        capture("tools_notice.png")
    }

    @Test
    fun `an answer names its assumptions with sources and basedOn`() {
        val sources = ToolSources(
            ownDiverId = "diver",
            gfHigh = 80.0,
            planningSac = PlanningSacResult(17.5, 12, PlanningSacSource.logbook),
            syncedAt = "2026-11-18T08:00:00Z",
        )
        render(state(inputs(o2 = "32", depth = "30", volume = "12", pressure = "200"), sources))
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("GF high 80, from your latest recording."))
        compose.onNodeWithText("Based on dives synced as of 18 Nov 2026, 08:00.").assertIsDisplayed()
        compose.onNodeWithText("GF high 80, from your latest recording.").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("SAC 17.5, from your logbook, based on 12 dives."))
        compose.onNodeWithText("SAC 17.5, from your logbook, based on 12 dives.").assertIsDisplayed()
        capture("tools_assumptions.png")
    }

    @Test
    fun `an answer names the limit that binds`() {
        render(state(inputs(o2 = "32", depth = "30", volume = "8", pressure = "200")))
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Limited by", substring = true))
        compose.onNodeWithText("Limited by", substring = true).assertIsDisplayed()
        capture("tools_binding.png")
    }

    @Test
    fun `outside limits show the reason and the MOD, never a number`() {
        render(state(inputs(o2 = "32", depth = "40")))
        compose.onNode(hasScrollAction()).performScrollToNode(
            hasText("The plan is beyond the MOD of 39.6 m at the contingency ppO2."),
        )
        compose.onNodeWithText("No bottom time.").assertIsDisplayed()
        compose.onNodeWithText("The plan is beyond the MOD of 39.6 m at the contingency ppO2.").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("MOD 39.6 m"))
        compose.onNodeWithText("MOD 39.6 m").assertIsDisplayed()
        capture("tools_outside_limits.png")
    }

    @Test
    fun `an empty store is an empty state, not an error`() {
        render(ToolsUiState(loading = false, storeEmpty = true))
        compose.onNodeWithText(
            "No dives are synced on this device yet. The numbers use only what you type; your logbook syncs when you are online.",
        ).assertIsDisplayed()
        capture("tools_offline_empty.png")
    }

    private fun render(state: ToolsUiState) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                ToolsScreen(state = state, imperial = false, onBack = {}, onChange = {}, onReload = {})
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun inputs(
        o2: String = "",
        depth: String = "",
        volume: String = "",
        pressure: String = "",
    ) = ToolsInputs(o2 = o2, depth = depth, volume = volume, pressure = pressure)

    private fun state(inputs: ToolsInputs, sources: ToolSources = ToolSources()) = ToolsUiState(
        loading = false,
        syncedAt = sources.syncedAt,
        personalized = sources.ownDiverId != null,
        inputs = inputs,
        gas = gasNumbers(
            GasNumbersInput(
                o2 = inputs.o2.toDouble(),
                depthM = inputs.depth.toDoubleOrNull(),
            ),
        ),
        bottom = bottomTime(
            BottomTimeToolInput(
                depthM = inputs.depth.toDouble(),
                o2 = inputs.o2.toDouble(),
                volumeL = inputs.volume.toDoubleOrNull(),
                pressureBar = inputs.pressure.toDoubleOrNull(),
            ),
            sources,
        ),
    )
}
