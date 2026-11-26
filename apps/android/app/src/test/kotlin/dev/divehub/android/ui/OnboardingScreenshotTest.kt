package dev.divehub.android.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.BuildConfig
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The first-start instance screen with the build's default prefilled (FAH-537, ADR 0090): the address is already
 * there, the copy still says a custom instance can be entered, and the screen keeps the app's light/dark and German
 * shapes.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class OnboardingScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `the instance form is prefilled in English and light`() {
        render(darkTheme = false)

        compose.onNodeWithText(BuildConfig.DEFAULT_INSTANCE_URL).assertIsDisplayed()
        compose.onNodeWithText("Connect to Dive Hub, or enter the address of your own instance.").assertIsDisplayed()
        capture("onboarding_instance_en_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the instance form is prefilled in English and dark`() {
        render(darkTheme = true)

        compose.onNodeWithText(BuildConfig.DEFAULT_INSTANCE_URL).assertIsDisplayed()
        capture("onboarding_instance_en_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `the instance form is prefilled in German`() {
        render(darkTheme = false)

        compose.onNodeWithText(BuildConfig.DEFAULT_INSTANCE_URL).assertIsDisplayed()
        compose.onNodeWithText("Verbinde dich mit Dive Hub oder gib die Adresse deiner eigenen Instanz ein.").assertIsDisplayed()
        capture("onboarding_instance_de_light.png")
    }

    private fun render(darkTheme: Boolean) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    InstanceForm(
                        checking = false,
                        error = null,
                        onContinue = {},
                    )
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
