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
import dev.divehub.shared.ErrorCodes
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * First start with ADR 0090: the sign-in form is the default screen, pointed at the build's cloud default, and the
 * self-hosted address form is the secondary path. The goldens keep the app's light/dark and German shapes.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class OnboardingScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `sign-in against the cloud default in English and light`() {
        renderSignIn(darkTheme = false)

        compose.onNodeWithText(BuildConfig.DEFAULT_INSTANCE_URL).assertIsDisplayed()
        compose.onNodeWithText("Connect to a self-hosted instance").assertIsDisplayed()
        capture("onboarding_signin_en_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `sign-in against the cloud default in English and dark`() {
        renderSignIn(darkTheme = true)

        compose.onNodeWithText(BuildConfig.DEFAULT_INSTANCE_URL).assertIsDisplayed()
        capture("onboarding_signin_en_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `sign-in against the cloud default in German`() {
        renderSignIn(darkTheme = false)

        compose.onNodeWithText(BuildConfig.DEFAULT_INSTANCE_URL).assertIsDisplayed()
        compose.onNodeWithText("Mit einer selbst gehosteten Instanz verbinden").assertIsDisplayed()
        capture("onboarding_signin_de_light.png")
    }

    @Test
    fun `sign-in with an unreachable cloud default`() {
        renderSignIn(darkTheme = false, reachability = Reachability.Unreachable(ErrorCodes.NO_ANSWER))

        compose.onNodeWithText("Not reachable").assertIsDisplayed()
        capture("onboarding_signin_unreachable_en_light.png")
    }

    @Test
    fun `the self-hosted address form in English and light`() {
        renderInstance(darkTheme = false)

        compose.onNodeWithText("Enter the address of your self-hosted Dive Hub instance.").assertIsDisplayed()
        compose.onNodeWithText("Back").assertIsDisplayed()
        capture("onboarding_instance_en_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the self-hosted address form in English and dark`() {
        renderInstance(darkTheme = true)

        compose.onNodeWithText("Enter the address of your self-hosted Dive Hub instance.").assertIsDisplayed()
        capture("onboarding_instance_en_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `the self-hosted address form in German`() {
        renderInstance(darkTheme = false)

        compose.onNodeWithText("Gib die Adresse deiner selbst gehosteten Dive-Hub-Instanz ein.").assertIsDisplayed()
        compose.onNodeWithText("Zurück").assertIsDisplayed()
        capture("onboarding_instance_de_light.png")
    }

    private fun renderSignIn(darkTheme: Boolean, reachability: Reachability = Reachability.Reachable) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SignInForm(
                        instanceUrl = BuildConfig.DEFAULT_INSTANCE_URL,
                        isDefaultInstance = true,
                        busy = false,
                        reachability = reachability,
                        error = null,
                        onChangeInstance = {},
                        onCheckInstance = {},
                        onSignIn = { _, _ -> },
                    )
                }
            }
        }
    }

    private fun renderInstance(darkTheme: Boolean) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    InstanceForm(
                        checking = false,
                        error = null,
                        onContinue = {},
                        onUseDefault = {},
                    )
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
