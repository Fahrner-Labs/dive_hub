package dev.divehub.android.ui.dive

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.Density
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewFromRecording
import dev.divehub.api.models.DiveViewFromRecordingStartsAt
import dev.divehub.api.models.DiveViewSite
import dev.divehub.api.models.DiveViewValues
import dev.divehub.api.models.DiveViewValuesStartsAt
import dev.divehub.shared.dive.DiveEditDraft
import dev.divehub.shared.store.DiveSite
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The edit screen's contract-visible states as pictures: a synced Dive being edited, the site picker, an edited value
 * with its "use the recording's value" way back, the destructive verbs' not-available note, dark at the top and at the
 * weighting and more actions, and German at 200 % on a narrow phone both at the top and scrolled to the foot.
 * Rendered from a fixed state, so a drawing change shows up as a changed image.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class DiveEditScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `a synced dive opens for editing`() {
        render(state(draft = DiveEditDraft.of(dive())))

        compose.onNodeWithText("Edit dive").assertExists()
        compose.onNodeWithText("Lighthouse Reef").assertExists()
        capture("dive_edit_light.png")
    }

    @Test
    fun `an edited value says what the recording had and offers its value back`() {
        render(state(draft = DiveEditDraft.of(dive(overrides = listOf("maxDepthM")))))

        compose.onNodeWithText("edited · recording: 20.0 m").assertExists()
        compose.onNodeWithText("Use the recording’s value").assertExists()
        capture("dive_edit_edited.png")
    }

    @Test
    fun `the site picker lists the cached sites and the current one`() {
        render(state(draft = DiveEditDraft.of(dive())))

        compose.onNodeWithText("Choose a dive site").performClick()

        compose.onNodeWithText("Blue Hole").assertExists()
        compose.onNode(hasText("Lighthouse Reef") and hasClickAction()).assertIsSelected()
        capture("dive_edit_site_picker.png")
    }

    @Test
    fun `the destructive verbs say they are not available yet`() {
        render(state(draft = DiveEditDraft.of(dive()), online = false))

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("More actions"))
        compose.onNodeWithText("Deleting, merging, moving and appending aren’t available on this device yet.")
            .assertExists()
        capture("dive_edit_offline_actions.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the editor follows the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive())), darkTheme = true)

        capture("dive_edit_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the weighting and the more actions follow the dark theme`() {
        render(state(draft = DiveEditDraft.of(dive(overrides = listOf("maxDepthM")))), darkTheme = true)

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("More actions"))
        capture("dive_edit_dark_actions.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the editor at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive())), fontScale = 2f)

        compose.onNodeWithText("Tauchgang bearbeiten").assertExists()
        capture("dive_edit_de_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the lower half at two hundred percent in german`() {
        render(state(draft = DiveEditDraft.of(dive())), fontScale = 2f)

        compose.onNodeWithTag("dive_edit_scroll").performScrollToNode(hasText("Weitere Aktionen"))
        capture("dive_edit_de_narrow_2x_scrolled.png")
    }

    private fun render(state: DiveEditUiState, darkTheme: Boolean = false, fontScale: Float? = null) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, fontScale ?: density.fontScale),
                ) {
                    DiveEditScreen(
                        state = state,
                        units = "metric",
                        onBack = {},
                        onEdit = {},
                        onSave = {},
                        onRetry = {},
                    )
                }
            }
        }
    }

    private fun state(draft: DiveEditDraft, online: Boolean = true) = DiveEditUiState(
        loading = false,
        draft = draft,
        originalSiteName = "Lighthouse Reef",
        sites = listOf(
            DiveSite(id = "site-1", name = "Lighthouse Reef"),
            DiveSite(id = "site-2", name = "Blue Hole"),
        ),
        online = online,
    )

    private fun dive(overrides: List<String> = emptyList()) = DiveView(
        id = "d1",
        diverId = "diver",
        version = 3,
        values = DiveViewValues(
            number = 42,
            startsAt = DiveViewValuesStartsAt("2026-04-02T09:00:00Z", 7200),
            durationSeconds = 3_000.0,
            maxDepthM = 24.3,
            avgDepthM = 12.0,
            waterTemperatureC = 18.0,
        ),
        overrides = overrides,
        utcOffsetSource = "device",
        fromProvider = null,
        fromRecording = DiveViewFromRecording(
            startsAt = DiveViewFromRecordingStartsAt("2026-04-02T09:00:00Z", 7200),
            durationSeconds = 3_000.0,
            maxDepthM = 20.0,
            avgDepthM = 12.0,
            waterTemperatureC = 18.0,
        ),
        notes = "Two barracuda on the wreck.",
        site = DiveViewSite("site-1", "Lighthouse Reef"),
        waterType = "salt",
        waterMismatch = null,
        position = null,
        recordings = emptyList(),
        cylinders = emptyList(),
        lead = emptyList(),
        leadTotalKg = null,
        weightingFeedback = null,
        suit = null,
        sac = null,
        sacMissing = null,
        participants = emptyList(),
        verificationCodes = emptyList(),
    )

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
