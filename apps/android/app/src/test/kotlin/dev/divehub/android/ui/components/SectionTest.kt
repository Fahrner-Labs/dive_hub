package dev.divehub.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.Spacing
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/** A section's action shares the heading's line while its 48 dp target fits, and starts the next line once it wraps. */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SectionTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `a short action ends on the column's edge while it shares the heading's line`() {
        render(fontScale = null) {
            Section(
                title = "Dive details",
                showSeparator = false,
                action = { QuietButton("Edit", onClick = {}) },
            ) {}
        }

        val title = compose.onNodeWithText("Dive details").getBoundsInRoot()
        val action = compose.onNodeWithText("Edit").getBoundsInRoot()
        val contentRight = compose.onRoot().getBoundsInRoot().right - Spacing.s4

        assertTrue("the action shares the heading's line", action.top < title.bottom)
        assertEquals(contentRight.value, action.right.value, 1f)
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the action starts below the heading when the heading takes the width`() {
        render(fontScale = 2f) {
            Section(
                title = "Water temperature",
                showSeparator = false,
                action = { QuietButton("Edit", onClick = {}) },
            ) {}
        }

        val title = compose.onNodeWithText("Water temperature").getBoundsInRoot()
        val action = compose.onNodeWithText("Edit").getBoundsInRoot()

        assertTrue("the action is below the heading: title=$title action=$action", action.top >= title.bottom - 0.5.dp)
        assertEquals(Spacing.s4.value, action.left.value, 1f)
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `a long action starts below the heading rather than squeezing it`() {
        render(fontScale = null) {
            Section(
                title = "Dive details",
                showSeparator = false,
                action = { QuietButton("Show the whole profile as a table", onClick = {}) },
            ) {}
        }

        val title = compose.onNodeWithText("Dive details").getBoundsInRoot()
        val action = compose.onNodeWithText("Show the whole profile as a table").getBoundsInRoot()

        assertTrue("the wrapped action starts its own line: title=$title action=$action", action.top >= title.bottom - 0.5.dp)
        assertEquals(Spacing.s4.value, action.left.value, 1f)
    }

    @Test
    fun `an action starts its label at the column edge when less than s2 separates it from the heading`() {
        var containerWidth by mutableStateOf(600.dp)
        var alignment by mutableStateOf<Alignment?>(null)
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                Box(Modifier.width(containerWidth)) {
                    Section(
                        title = "Dive details",
                        showSeparator = false,
                        action = {
                            val current = LocalQuietActionAlignment.current
                            SideEffect { alignment = current }
                        },
                    ) {}
                }
            }
        }
        compose.waitForIdle()
        val titleBounds = compose.onNodeWithText("Dive details").getBoundsInRoot()
        val titleWidth = titleBounds.right - titleBounds.left

        // Inside s2 the action is treated as wrapped, so a short label ends on the column's edge (FAH-427).
        compose.runOnIdle { containerWidth = titleWidth + QuietActionTarget + Spacing.s2 - 2.dp }
        compose.waitForIdle()
        assertEquals(Alignment.CenterStart, alignment)

        compose.runOnIdle { containerWidth = titleWidth + QuietActionTarget + Spacing.s2 + 2.dp }
        compose.waitForIdle()
        assertEquals(Alignment.CenterEnd, alignment)
    }

    private fun render(fontScale: Float?, content: @androidx.compose.runtime.Composable () -> Unit) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, fontScale ?: density.fontScale),
                ) {
                    Box(Modifier.fillMaxSize().padding(horizontal = Spacing.s4)) {
                        content()
                    }
                }
            }
        }
    }
}
