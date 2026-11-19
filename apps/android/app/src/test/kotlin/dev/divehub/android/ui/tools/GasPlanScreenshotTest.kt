package dev.divehub.android.ui.tools

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.planning.PlanningSacResult
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.tools.GasPlanToolDiver
import dev.divehub.shared.tools.GasPlanToolInput
import dev.divehub.shared.tools.GasPlanToolLevel
import dev.divehub.shared.tools.RULE_THIRDS
import dev.divehub.shared.tools.ToolDiverSource
import dev.divehub.shared.tools.ToolSources
import dev.divehub.shared.tools.gasPlan
import java.util.TimeZone
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The gas plan's contract-visible states as pictures (FAH-275; client contract, Gas plans): the notice before any
 * input, a plan that fits with the controlling Diver and rock bottom per level, one that does not fit, a rule that
 * leaves less than rock bottom, `outside_limits`, and a turn rule's briefing.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class GasPlanScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    private var previousZone: TimeZone = TimeZone.getDefault()

    @Before
    fun pinZone() {
        previousZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"))
    }

    @After
    fun restoreZone() {
        TimeZone.setDefault(previousZone)
    }

    @Test
    fun `the notice is above the inputs and an empty plan says what it needs`() {
        render(GasPlanUiState(loading = false))
        compose.onNodeWithText(
            "An estimate, not advice: the dive computer and your training govern. No model guarantees safety.",
        ).assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Add a level and a Diver to see the plan."))
        compose.onNodeWithText("Add a level and a Diver to see the plan.").assertIsDisplayed()
        capture("gas_plan_empty.png")
    }

    @Test
    fun `a plan that fits marks the controlling Diver and rock bottom per level`() {
        val state = state(
            levels = listOf(GasPlanToolLevel(18.0, 20.0), GasPlanToolLevel(30.0, 10.0)),
            divers = listOf(
                GasPlanToolDiver("Elena", 15.0, 240.0, diverId = "diver"),
                GasPlanToolDiver("Bob", 15.0, 240.0, sacLpm = 18.0),
            ),
            sources = ToolSources(
                divers = listOf(
                    ToolDiverSource(
                        id = "diver",
                        name = "Elena",
                        isOwn = true,
                        planningSac = PlanningSacResult(16.0, 12, PlanningSacSource.logbook),
                    ),
                ),
            ),
            syncedAt = "2026-11-18T08:00:00Z",
        )
        render(state)
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("The plan fits.", substring = true))
        compose.onNodeWithText("The plan fits.", substring = true).assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Controls the plan"))
        compose.onAllNodesWithText("Controls the plan").onFirst().assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Rock bottom at each level", substring = true))
        compose.onNodeWithText("Rock bottom at each level", substring = true).assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Elena: gas use 16.0 L/min (from your logbook, 12 dives)"))
        compose.onNodeWithText("Elena: gas use 16.0 L/min (from your logbook, 12 dives)").assertIsDisplayed()
        capture("gas_plan_fits.png")
    }

    @Test
    fun `a plan that does not fit says when to leave the last level`() {
        val state = state(
            levels = listOf(GasPlanToolLevel(30.0, 40.0)),
            divers = listOf(GasPlanToolDiver("Elena", 3.0, 50.0, sacLpm = 18.0)),
        )
        render(state)
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("The plan does not fit.", substring = true))
        compose.onNodeWithText("The plan does not fit.", substring = true).assertIsDisplayed()
        compose.onNodeWithText("Leave 30.0 m by minute", substring = true).assertIsDisplayed()
        capture("gas_plan_does_not_fit.png")
    }

    @Test
    fun `a rule that leaves less than rock bottom is said`() {
        val state = state(
            levels = listOf(GasPlanToolLevel(30.0, 10.0)),
            divers = listOf(GasPlanToolDiver("Elena", 12.0, 200.0, sacLpm = 18.0)),
            rule = "reserve",
            bar = "30",
        )
        render(state)
        compose.onNode(hasScrollAction()).performScrollToNode(
            hasText("For at least one Diver this rule leaves less than rock bottom."),
        )
        compose.onNodeWithText("For at least one Diver this rule leaves less than rock bottom.").assertIsDisplayed()
        capture("gas_plan_below_rock_bottom.png")
    }

    @Test
    fun `outside limits name the reason and no figures`() {
        val state = state(
            levels = listOf(GasPlanToolLevel(20.0, 10.0)),
            divers = listOf(GasPlanToolDiver("Elena", 12.0, 200.0, sacLpm = 18.0)),
            o2 = 50.0,
        )
        render(state)
        compose.onNode(hasScrollAction()).performScrollToNode(
            hasText("Only air and nitrox 21 to 40 % are planned here."),
        )
        compose.onNodeWithText("Only air and nitrox 21 to 40 % are planned here.").assertIsDisplayed()
        capture("gas_plan_outside.png")
    }

    @Test
    fun `a turn rule briefs the group to turn together`() {
        val state = state(
            levels = listOf(GasPlanToolLevel(20.0, 20.0)),
            divers = listOf(
                GasPlanToolDiver("Elena", 12.0, 200.0, sacLpm = 18.0),
                GasPlanToolDiver("Bob", 12.0, 200.0, sacLpm = 20.0),
            ),
            rule = RULE_THIRDS,
        )
        render(state)
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("we turn when anyone", substring = true))
        compose.onNodeWithText("we turn when anyone", substring = true).assertIsDisplayed()
        capture("gas_plan_turn.png")
    }

    private fun render(state: GasPlanUiState) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                GasPlanScreen(
                    state = state,
                    imperial = false,
                    onBack = {},
                    onChange = {},
                    onAddLevel = {},
                    onRemoveLevel = {},
                    onAddDiver = {},
                    onAddGuest = {},
                    onRemoveDiver = {},
                    onReload = {},
                )
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun state(
        levels: List<GasPlanToolLevel>,
        divers: List<GasPlanToolDiver>,
        o2: Double = 21.0,
        rule: String? = null,
        bar: String = "",
        sources: ToolSources = ToolSources(),
        syncedAt: String? = null,
    ): GasPlanUiState {
        val inputs = GasPlanInputs(
            o2 = if (o2 == 21.0) "" else o2.toString(),
            levels = levels.map { GasPlanLevelInput(it.depthM.toString(), it.minutes.toString()) },
            divers = divers.map {
                GasPlanDiverInput(
                    diverId = it.diverId,
                    name = it.name,
                    volume = it.volumeL.toString(),
                    pressure = it.startPressureBar.toString(),
                    sac = it.sacLpm?.toString() ?: "",
                )
            },
            rule = rule,
            bar = bar,
        )
        return GasPlanUiState(
            loading = false,
            syncedAt = syncedAt,
            knownDivers = sources.divers,
            inputs = inputs,
            answer = gasPlan(
                GasPlanToolInput(
                    o2 = o2,
                    levels = levels,
                    divers = divers,
                    rule = rule,
                    bar = bar.toDoubleOrNull(),
                ),
                sources,
            ),
        )
    }
}
