package dev.divehub.android.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** The D8 warning: queued writes block an explicit sign-out and a change of User until the User decides. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class PendingWarningDialogTest {
    @get:Rule
    val compose = createComposeRule()

    private var syncs = 0
    private var discards = 0
    private var cancels = 0

    @Test
    fun `signing out with waiting changes offers sync now or discard`() {
        render(PendingChangeWarning.SignOut(count = 2, changeInstance = false))

        compose.onNodeWithText("2 changes haven’t synced").assertIsDisplayed()
        compose.onNodeWithText(
            "Sync them before you sign out, or discard them. Discarded changes are lost.",
        ).assertIsDisplayed()

        compose.onNodeWithText("Sync now").performClick()
        assertEquals(1, syncs)

        compose.onNodeWithText("Discard and sign out").performClick()
        assertEquals(1, discards)

        compose.onNodeWithText("Cancel").performClick()
        assertEquals(1, cancels)
    }

    @Test
    fun `a queue that still waits says so`() {
        render(PendingChangeWarning.SignOut(count = 2, changeInstance = false, stillWaiting = true))

        compose.onNodeWithText("Some changes still wait. They may need a decision or another try.").assertIsDisplayed()
    }

    @Test
    fun `changing the instance names its own discard action`() {
        render(PendingChangeWarning.SignOut(count = 1, changeInstance = true))

        compose.onNodeWithText("1 change hasn’t synced").assertIsDisplayed()
        compose.onNodeWithText("Discard and change instance").assertIsDisplayed()
    }

    @Test
    fun `signing in as another account offers to discard the previous account's changes`() {
        render(PendingChangeWarning.SignIn(count = 1, email = "other@example.com"))

        compose.onNodeWithText("1 change belongs to another account").assertIsDisplayed()
        compose.onNodeWithText(
            "Signing in as other@example.com discards the changes saved on this phone for the previous account.",
        ).assertIsDisplayed()

        compose.onNodeWithText("Discard and sign in").performClick()
        assertEquals(1, discards)
    }

    private fun render(warning: PendingChangeWarning) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                PendingWarningDialog(
                    warning = warning,
                    onSyncNow = { syncs += 1 },
                    onDiscard = { discards += 1 },
                    onCancel = { cancels += 1 },
                )
            }
        }
    }
}
