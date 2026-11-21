package dev.divehub.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.Spacing
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The shared components in both schemes (the design-system brief): a Section's first-section, hairline and attention
 * states, a FactRow's label/value/note line, a DiveRow's aligned figures and participant slot, and an EmptyState's way
 * on. They are rendered from fixed content, so a change to a token, a style or a component shows up as a changed
 * picture.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class ComponentsScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `a section in light`() {
        render(darkTheme = false) { SectionContent() }

        compose.onNodeWithText("Dive details").assertIsDisplayed()
        compose.onNodeWithText("24.3 m").assertIsDisplayed()
        compose.onNodeWithText("Waiting for you").assertIsDisplayed()
        capture("components_section_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `a section in dark`() {
        render(darkTheme = true) { SectionContent() }

        compose.onNodeWithText("Dive details").assertIsDisplayed()
        compose.onNodeWithText("Waiting for you").assertIsDisplayed()
        capture("components_section_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `a section and a fact row at 320 dp and 200 percent`() {
        render(darkTheme = false, fontScale = 2f) {
            Column(Modifier.padding(Spacing.s4)) {
                Section(
                    title = "Water temperature",
                    showSeparator = false,
                    action = { TextButton(onClick = {}) { Text("Edit") } },
                ) {
                    FactRow(label = "Surface interval", value = "1 h 5 min")
                }
                FactRow(
                    label = "Surface interval",
                    value = "1 h 5 min",
                    note = "Time zone from the dive site",
                )
            }
        }

        compose.onNodeWithText("Water temperature").assertIsDisplayed()
        compose.onNodeWithText("Edit").assertIsDisplayed()
        capture("components_narrow_2x_light.png")
    }

    @Test
    fun `a fact row in light`() {
        render(darkTheme = false) { FactRowContent() }

        compose.onNodeWithText("Max depth").assertIsDisplayed()
        compose.onNodeWithText("edited · recording: 10:58").assertIsDisplayed()
        capture("components_fact_row_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `a fact row in dark`() {
        render(darkTheme = true) { FactRowContent() }

        compose.onNodeWithText("Max depth").assertIsDisplayed()
        capture("components_fact_row_dark.png")
    }

    @Test
    fun `a dive row in light`() {
        var clicks = 0
        render(darkTheme = false) {
            Column(Modifier.padding(Spacing.s4)) {
                DiveRow(
                    title = "Lighthouse Reef",
                    depth = "11.1 m",
                    duration = "50 min",
                    facts = listOf("2 Apr 2026", "EAN32", "No recording"),
                    avatars = {
                        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s1)) {
                            AvatarStandIn("AM")
                            AvatarStandIn("JD")
                        }
                    },
                    onClick = { clicks++ },
                )
                DiveRow(
                    title = "Blue Hole",
                    depth = "18.8 m",
                    duration = "46 min",
                    facts = listOf("12 Mar 2026", "Air", "2 findings"),
                    onClick = { clicks++ },
                )
            }
        }

        compose.onNodeWithText("Lighthouse Reef").assertIsDisplayed().assertHasClickAction()
        compose.onNodeWithContentDescription("2 Apr 2026, EAN32, No recording").assertExists()
        compose.onNodeWithText("Lighthouse Reef").performClick()
        assertEquals(1, clicks)
        capture("components_dive_row_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `a dive row in dark`() {
        render(darkTheme = true) {
            Column(Modifier.padding(Spacing.s4)) {
                DiveRow(
                    title = "Lighthouse Reef",
                    depth = "11.1 m",
                    duration = "50 min",
                    facts = listOf("2 Apr 2026", "EAN32", "No recording"),
                    onClick = {},
                )
                DiveRow(
                    title = "Blue Hole",
                    depth = "18.8 m",
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
        render(darkTheme = false) { EmptyStateContent() }

        compose.onNodeWithText("No dives yet").assertIsDisplayed()
        compose.onNodeWithText("Import dives").assertIsDisplayed()
        capture("components_empty_state_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `an empty state in dark`() {
        render(darkTheme = true) { EmptyStateContent() }

        compose.onNodeWithText("No dives yet").assertIsDisplayed()
        capture("components_empty_state_dark.png")
    }

    @Composable
    private fun SectionContent() {
        Column(Modifier.padding(Spacing.s4)) {
            Section(
                title = "Dive details",
                showSeparator = false,
                action = { TextButton(onClick = {}) { Text("Edit") } },
            ) {
                FactRow(label = "Max depth", value = "24.3 m")
                FactRow(label = "Duration", value = "50 min")
            }
            Section(title = "Notes") {
                Text("A quiet drift along the wall, back to the same line.", style = MaterialTheme.typography.bodyLarge)
            }
            Section(
                title = "Waiting for you",
                attention = true,
                action = { TextButton(onClick = {}) { Text("Open") } },
            ) {
                Text("This Dive has a finding to review.", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

    @Composable
    private fun FactRowContent() {
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

    @Composable
    private fun EmptyStateContent() {
        EmptyState(
            title = "No dives yet",
            message = "Import your logbook or record a dive to start this one.",
            action = { TextButton(onClick = {}) { Text("Import dives") } },
        )
    }

    @Composable
    private fun AvatarStandIn(initials: String) {
        Box(
            Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Text(initials, style = DiveHubText.metaStrong)
        }
    }

    private fun render(darkTheme: Boolean, fontScale: Float? = null, content: @Composable () -> Unit) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, fontScale ?: density.fontScale),
                ) {
                    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
                        content()
                    }
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
