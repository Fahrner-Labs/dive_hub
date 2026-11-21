package dev.divehub.android.ui

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The shared app bar (FAH-335): a pushed screen gets a real back arrow with a spoken label, and a top-level screen
 * has none because the bottom bar is where it leaves.
 */
@RunWith(RobolectricTestRunner::class)
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
    fun `actions stay in the app bar`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveHubTopBar(title = "Tools", actions = { Text("Action") })
            }
        }

        compose.onNodeWithText("Action").assertIsDisplayed()
    }
}
