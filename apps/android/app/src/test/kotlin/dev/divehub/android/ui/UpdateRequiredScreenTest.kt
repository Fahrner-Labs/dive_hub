package dev.divehub.android.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
 * A server that hard-breaks this app says "update", in the User's language, and never offers sign-in: the session is
 * fine, the app is old (ADR 0076).
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class UpdateRequiredScreenTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `an old app is told to update and is not offered sign-in`() {
        render()

        compose.onNodeWithText("Update DiveHub").assertIsDisplayed()
        compose.onNodeWithText("This server needs a newer version of the app. Update DiveHub to keep using it.").assertIsDisplayed()
        compose.onNodeWithText("Sign in").assertDoesNotExist()
        capture("update_required.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `the update state is translated into German`() {
        render()

        compose.onNodeWithText("DiveHub aktualisieren").assertIsDisplayed()
        compose.onNodeWithText("Dieser Server braucht eine neuere Version der App. Aktualisiere DiveHub, um sie weiter zu nutzen.").assertIsDisplayed()
        compose.onNodeWithText("Anmelden").assertDoesNotExist()
        capture("update_required_de.png")
    }

    private fun render() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                UpdateRequiredScreen()
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
