package dev.divehub.android.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import dev.divehub.android.BuildConfig
import dev.divehub.shared.ErrorCodes
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** The two onboarding screens say whether the instance answers before credentials are asked (FAH-332). */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class OnboardingScreenTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `the instance form checks while the button waits`() {
        renderInstance(checking = true, error = null)

        compose.onNodeWithContentDescription("Loading…").assertIsDisplayed()
        compose.onNodeWithText("Continue").assertIsNotEnabled()
    }

    @Test
    fun `the instance form starts from the build's default address`() {
        renderBuildDefault()

        compose.onNode(hasSetTextAction()).assertTextContains(BuildConfig.DEFAULT_INSTANCE_URL)
        compose.onNodeWithText("Continue").assertIsEnabled()
    }

    @Test
    fun `the prefilled default is handed to the check without editing`() {
        var entered: String? = null
        renderInstance(checking = false, error = null) { entered = it }

        compose.onNodeWithText("Continue").performClick()

        assertEquals(DEFAULT_INSTANCE, entered)
    }

    @Test
    fun `an unreachable instance shows the reason beside the address`() {
        renderInstance(checking = false, error = ErrorCodes.NO_ANSWER)

        compose.onNodeWithText("No answer from the instance. Check the address and your connection.").assertIsDisplayed()
        compose.onNode(hasSetTextAction()).performTextReplacement("dive.example")
        compose.onNodeWithText("Continue").assertIsEnabled()
    }

    @Test
    fun `a host that answers without a health route says it is not a Dive Hub`() {
        renderInstance(checking = false, error = ErrorCodes.NOT_DIVEHUB)

        compose.onNodeWithText("That address answered, but it is not a Dive Hub instance.").assertIsDisplayed()
    }

    @Test
    fun `a custom instance replaces the default and is handed to the check`() {
        var entered: String? = null
        renderInstance(checking = false, error = null) { entered = it }

        compose.onNode(hasSetTextAction()).performTextReplacement("dive.example")
        compose.onNodeWithText("Continue").performClick()

        assertEquals("dive.example", entered)
    }

    @Test
    fun `the sign-in form says the instance is connected`() {
        renderSignIn(Reachability.Reachable)

        compose.onNodeWithText("https://dive.example").assertIsDisplayed()
        compose.onNodeWithText("Connected").assertIsDisplayed()
    }

    @Test
    fun `the sign-in form says it is checking again`() {
        renderSignIn(Reachability.Checking)

        compose.onNodeWithText("Checking…").assertIsDisplayed()
    }

    @Test
    fun `an unreachable instance is a status, never the password error`() {
        renderSignIn(Reachability.Unreachable(ErrorCodes.NO_ANSWER))

        compose.onNodeWithText("Not reachable").assertIsDisplayed()
        compose.onNodeWithText("No answer from the instance. Check the address and your connection.").assertDoesNotExist()
    }

    @Test
    fun `tapping the status checks the instance again`() {
        var checks = 0
        renderSignIn(Reachability.Unreachable(ErrorCodes.NO_ANSWER)) { checks += 1 }

        compose.onNodeWithText("Not reachable").performClick()

        assertEquals(1, checks)
    }

    private fun renderInstance(
        checking: Boolean,
        error: String?,
        defaultAddress: String = DEFAULT_INSTANCE,
        onContinue: (String) -> Unit = {},
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    InstanceForm(
                        checking = checking,
                        error = error,
                        onContinue = onContinue,
                        defaultAddress = defaultAddress,
                    )
                }
            }
        }
    }

    private fun renderBuildDefault() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    InstanceForm(checking = false, error = null, onContinue = {})
                }
            }
        }
    }

    private fun renderSignIn(reachability: Reachability, onCheck: () -> Unit = {}) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SignInForm(
                        instanceUrl = "https://dive.example",
                        busy = false,
                        reachability = reachability,
                        error = null,
                        onChangeInstance = {},
                        onCheckInstance = onCheck,
                        onSignIn = { _, _ -> },
                    )
                }
            }
        }
    }
}

private const val DEFAULT_INSTANCE = "https://cloud.divehub.example"
