package dev.divehub.android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.Spacing
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The shared components in both schemes (the design-system brief): a Section's hairline and heading, a FactRow's
 * label/value/note line, a DiveRow's aligned figures and an EmptyState's way on. They are rendered from fixed content,
 * so a change to a token, a style or a component shows up as a changed picture.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class ComponentsScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `a section in light`() {
        render(darkTheme = false) {
            Column(Modifier.padding(Spacing.s4)) {
                Section(
                    title = "Dive details",
                    action = { TextButton(onClick = {}) { Text("Edit") } },
                ) {
                    FactRow(label = "Max depth", value = "24.3 m")
                    FactRow(label = "Duration", value = "50 min")
                }
                Section(title = "Notes") {
                    Text("A quiet drift along the wall, back to the same line.", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        compose.onNodeWithText("Dive details").assertIsDisplayed()
        compose.onNodeWithText("24.3 m").assertIsDisplayed()
        capture("components_section_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `a section in dark`() {
        render(darkTheme = true) {
            Column(Modifier.padding(Spacing.s4)) {
                Section(
                    title = "Dive details",
                    action = { TextButton(onClick = {}) { Text("Edit") } },
                ) {
                    FactRow(label = "Max depth", value = "24.3 m")
                    FactRow(label = "Duration", value = "50 min")
                }
                Section(title = "Notes") {
                    Text("A quiet drift along the wall, back to the same line.", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        compose.onNodeWithText("Dive details").assertIsDisplayed()
        capture("components_section_dark.png")
    }

    @Test
    fun `a fact row in light`() {
        render(darkTheme = false) {
            Column(Modifier.padding(Spacing.s4)) {
                FactRow(label = "Max depth", value = "24.3 m")
                FactRow(
                    label = "Start",
                    value = "2 Apr 2026, 11:00",
                    mark = "edited · recording: 10:58",
                    note = "Time zone from the dive site",
                )
                FactRow(label = "Surface interval", value = "1 h 5 min")
            }
        }

        compose.onNodeWithText("Max depth").assertIsDisplayed()
        compose.onNodeWithText("edited · recording: 10:58").assertIsDisplayed()
        capture("components_fact_row_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `a fact row in dark`() {
        render(darkTheme = true) {
            Column(Modifier.padding(Spacing.s4)) {
                FactRow(label = "Max depth", value = "24.3 m")
                FactRow(
                    label = "Start",
                    value = "2 Apr 2026, 11:00",
                    mark = "edited · recording: 10:58",
                    note = "Time zone from the dive site",
                )
                FactRow(label = "Surface interval", value = "1 h 5 min")
            }
        }

        compose.onNodeWithText("Max depth").assertIsDisplayed()
        capture("components_fact_row_dark.png")
    }

    @Test
    fun `a dive row in light`() {
        render(darkTheme = false) {
            Column(Modifier.padding(Spacing.s4)) {
                DiveRow(
                    title = "Lighthouse Reef",
                    depth = "24.3 m",
                    duration = "50 min",
                    facts = listOf("2 Apr 2026", "EAN32", "No recording"),
                    onClick = {},
                )
                DiveRow(
                    title = "Blue Hole",
                    depth = "31.4 m",
                    duration = "46 min",
                    facts = listOf("12 Mar 2026", "Air", "2 findings"),
                    onClick = {},
                )
            }
        }

        compose.onNodeWithText("Lighthouse Reef").assertIsDisplayed()
        compose.onNodeWithText("31.4 m").assertIsDisplayed()
        capture("components_dive_row_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `a dive row in dark`() {
        render(darkTheme = true) {
            Column(Modifier.padding(Spacing.s4)) {
                DiveRow(
                    title = "Lighthouse Reef",
                    depth = "24.3 m",
                    duration = "50 min",
                    facts = listOf("2 Apr 2026", "EAN32", "No recording"),
                    onClick = {},
                )
                DiveRow(
                    title = "Blue Hole",
                    depth = "31.4 m",
                    duration = "46 min",
                    facts = listOf("12 Mar 2026", "Air", "2 findings"),
                    onClick = {},
                )
            }
        }

        compose.onNodeWithText("Lighthouse Reef").assertIsDisplayed()
        capture("components_dive_row_dark.png")
    }

    @Test
    fun `an empty state in light`() {
        render(darkTheme = false) {
            EmptyState(
                title = "No dives yet",
                message = "Import your logbook or record a dive to start this one.",
                action = { TextButton(onClick = {}) { Text("Import dives") } },
            )
        }

        compose.onNodeWithText("No dives yet").assertIsDisplayed()
        compose.onNodeWithText("Import dives").assertIsDisplayed()
        capture("components_empty_state_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `an empty state in dark`() {
        render(darkTheme = true) {
            EmptyState(
                title = "No dives yet",
                message = "Import your logbook or record a dive to start this one.",
                action = { TextButton(onClick = {}) { Text("Import dives") } },
            )
        }

        compose.onNodeWithText("No dives yet").assertIsDisplayed()
        capture("components_empty_state_dark.png")
    }

    private fun render(darkTheme: Boolean, content: @Composable () -> Unit) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
                    content()
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
