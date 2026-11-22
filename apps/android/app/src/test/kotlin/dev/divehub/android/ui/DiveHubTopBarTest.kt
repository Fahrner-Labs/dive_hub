package dev.divehub.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The shared app bar (FAH-335): a pushed screen gets a real back arrow with a spoken label, and a top-level screen
 * has none because the bottom bar is where it leaves.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36])
class DiveHubTopBarTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `a pushed screen shows a back arrow that goes back`() {
        var backs = 0
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveHubTopBar(title = "Gas plan", onBack = { backs++ })
            }
        }

        compose.onNodeWithText("Gas plan").assertIsDisplayed()
        compose.onNodeWithContentDescription("Back").assertIsDisplayed().performClick()

        assertEquals(1, backs)
    }

    @Test
    fun `a top-level screen has no back arrow`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveHubTopBar(title = "Tools")
            }
        }

        compose.onNodeWithText("Tools").assertIsDisplayed()
        compose.onNodeWithContentDescription("Back").assertDoesNotExist()
    }

    @Test
    fun `the dive screen centres its title between the arrows`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveHubTopBar(
                    title = "Dive 42",
                    onBack = {},
                    centerTitle = true,
                    actions = { Text("Next dive") },
                )
            }
        }

        val root = compose.onRoot().getBoundsInRoot()
        val title = compose.onNodeWithText("Dive 42").getBoundsInRoot()
        val rootCentre = (root.left + root.right) / 2
        val titleCentre = (title.left + title.right) / 2

        assertEquals(rootCentre.value, titleCentre.value, 0.5f)
        compose.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    @Test
    fun `the title skeleton waits 150 ms before it appears`() {
        compose.mainClock.autoAdvance = false
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveHubTopBar(title = "Dive 42", centerTitle = true, loading = true)
            }
        }

        compose.onNodeWithTag(TopBarTitleSkeletonTag).assertDoesNotExist()
        compose.mainClock.advanceTimeBy(149)
        compose.onNodeWithTag(TopBarTitleSkeletonTag).assertDoesNotExist()
        compose.mainClock.advanceTimeBy(2)
        compose.onNodeWithTag(TopBarTitleSkeletonTag).assertExists()
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `a wrapped title does not change the stacked bar's height`() {
        var loading by mutableStateOf(true)
        var title by mutableStateOf("")
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, 2f),
                ) {
                    Column {
                        DiveHubTopBar(
                            title = title,
                            centerTitle = true,
                            stackedTitle = true,
                            loading = loading,
                        )
                        Box(Modifier.size(1.dp).testTag("below_the_bar"))
                    }
                }
            }
        }

        compose.mainClock.advanceTimeBy(200)
        val loadingBarBottom = compose.onNodeWithTag("below_the_bar").getBoundsInRoot().top
        compose.runOnIdle {
            loading = false
            title = "Thu 2 Apr 2026, 11:00 (UTC+2)"
        }
        compose.waitForIdle()
        val loadedTitle = compose.onNodeWithText(title).getBoundsInRoot()
        val loadedBarBottom = compose.onNodeWithTag("below_the_bar").getBoundsInRoot().top

        assertTrue("the title wrapped: $loadedTitle", loadedTitle.bottom - loadedTitle.top > 60.dp)
        assertEquals(loadingBarBottom.value, loadedBarBottom.value, 0.5f)
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `a one-line stacked title keeps the bar's height`() {
        var loading by mutableStateOf(true)
        var title by mutableStateOf("")
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, 2f),
                ) {
                    Column {
                        DiveHubTopBar(
                            title = title,
                            centerTitle = true,
                            stackedTitle = true,
                            loading = loading,
                        )
                        Box(Modifier.size(1.dp).testTag("below_the_bar"))
                    }
                }
            }
        }

        compose.mainClock.advanceTimeBy(200)
        val loadingBarBottom = compose.onNodeWithTag("below_the_bar").getBoundsInRoot().top
        compose.runOnIdle {
            loading = false
            title = "Dive 42"
        }
        compose.waitForIdle()
        val loadedBarBottom = compose.onNodeWithTag("below_the_bar").getBoundsInRoot().top

        assertEquals(loadingBarBottom.value, loadedBarBottom.value, 0.5f)
    }

    @Test
    fun `actions stay in the app bar`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveHubTopBar(title = "Tools", actions = { Text("Action") })
            }
        }

        compose.onNodeWithText("Action").assertIsDisplayed()
    }
}
