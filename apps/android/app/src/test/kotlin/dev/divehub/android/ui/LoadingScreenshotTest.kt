package dev.divehub.android.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The branded loading screen before the session is resolved (FAH-315): the Waypoint tile, the `DiveHub` wordmark and
 * the slogan, in both schemes and both languages. The tile and the text take their colours from the Material scheme,
 * so a dark system start is an ink screen with the reversed mark, never a white flash.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class LoadingScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `the loading screen is branded in English and light`() {
        render(darkTheme = false)

        compose.onNodeWithText("DiveHub").assertIsDisplayed()
        compose.onNodeWithText("The central hub for your diving life").assertIsDisplayed()
        compose.onNodeWithContentDescription("Loading…").assertIsDisplayed()
        capture("loading_brand_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `the loading screen is branded in German and light`() {
        render(darkTheme = false)

        compose.onNodeWithText("DiveHub").assertIsDisplayed()
        compose.onNodeWithText("Die zentrale Anlaufstelle für dein Tauchleben").assertIsDisplayed()
        capture("loading_brand_de_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the loading screen is branded in English and dark`() {
        render(darkTheme = true)

        compose.onNodeWithText("DiveHub").assertIsDisplayed()
        compose.onNodeWithText("The central hub for your diving life").assertIsDisplayed()
        compose.onNodeWithContentDescription("Loading…").assertIsDisplayed()
        capture("loading_brand_en_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-night-xxhdpi")
    fun `the loading screen is branded in German and dark`() {
        render(darkTheme = true)

        compose.onNodeWithText("DiveHub").assertIsDisplayed()
        compose.onNodeWithText("Die zentrale Anlaufstelle für dein Tauchleben").assertIsDisplayed()
        capture("loading_brand_de_dark.png")
    }

    private fun render(darkTheme: Boolean) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoadingScreen()
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
