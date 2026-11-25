package dev.divehub.android.ui.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import dev.divehub.android.ui.DiveHubTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** The live scanner's torch: only a camera with a flash offers it, and its state is the icon and the TalkBack state. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class CameraScannerTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `only a camera with a flash offers the torch`() {
        assertFalse(torchState(hasFlashUnit = false).available)
        assertTrue(torchState(hasFlashUnit = true).available)
        assertFalse(torchState(hasFlashUnit = true).on)
    }

    @Test
    fun `toggling flips the torch and a camera without a flash never lights it`() {
        val off = torchState(hasFlashUnit = true)
        assertEquals(TorchState(available = true, on = true), off.toggled())
        assertEquals(TorchState(available = true, on = false), off.toggled().toggled())
        assertEquals(TorchState(available = false, on = false), torchState(hasFlashUnit = false).toggled())
    }

    @Test
    fun `the live frame hides the torch on a camera without a flash`() {
        render(torch = torchState(hasFlashUnit = false))

        compose.onNodeWithContentDescription("Torch").assertDoesNotExist()
    }

    @Test
    fun `the live frame offers the torch off and asks to toggle it`() {
        var toggled = false
        render(torch = torchState(hasFlashUnit = true), onToggleTorch = { toggled = true })

        compose.onNodeWithContentDescription("Torch")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Off"))
            .assertIsDisplayed()
            .performClick()

        assertTrue(toggled)
    }

    @Test
    fun `the live frame says the torch is on when it is`() {
        render(torch = TorchState(available = true, on = true))

        compose.onNodeWithContentDescription("Torch")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "On"))
            .assertIsDisplayed()
    }

    private fun render(torch: TorchState, onToggleTorch: () -> Unit = {}) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                CameraScannerFrame(
                    onClose = {},
                    torch = torch,
                    onToggleTorch = onToggleTorch,
                    preview = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color(0xFF25303A)),
                        )
                    },
                )
            }
        }
    }
}
