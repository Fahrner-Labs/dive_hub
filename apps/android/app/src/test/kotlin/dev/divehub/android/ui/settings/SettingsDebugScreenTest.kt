package dev.divehub.android.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.diagnostics.ReportProblemResult
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The Debug subscreen (FAH-343): the diagnostics status, *Report a problem* only when the instance allows it, and
 * *Share local logs*, which stays available whatever the instance setting says.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SettingsDebugScreenTest {
    @get:Rule
    val compose = createComposeRule()

    private var reports = 0
    private var shares = 0
    private var backs = 0
    private var queueOpens = 0

    @Test
    fun `the screen shows the diagnostics status`() {
        render(SettingsDiagnosticsState(enabled = true, count = 2))

        compose.onNodeWithText("Diagnostics").assertIsDisplayed()
        compose.onNodeWithText("This Dive Hub accepts diagnostics.").assertIsDisplayed()
        compose.onNodeWithText("Local capture is on. Entries kept on this device: 2.").assertIsDisplayed()
    }

    @Test
    fun `the back arrow returns to settings`() {
        render()

        compose.onNodeWithContentDescription("Back").assertIsDisplayed().performClick()
        compose.onNodeWithText("Back").assertDoesNotExist()

        assertEquals(1, backs)
    }

    @Test
    fun `report a problem calls the upload flow when the instance allows it`() {
        render(SettingsDiagnosticsState(enabled = true, count = 2))

        compose.onNodeWithText("Report a problem").performScrollTo().performClick()

        assertEquals(1, reports)
    }

    @Test
    fun `share local logs calls the export flow`() {
        render(SettingsDiagnosticsState(enabled = true, count = 2))

        compose.onNodeWithText("Share local logs").performScrollTo().performClick()

        assertEquals(1, shares)
    }

    @Test
    fun `the sync queue row shows the live counts and opens the queue`() {
        render(queue = SettingsQueueState(pending = 2, failed = 1, conflicted = 3))

        compose.onNodeWithText("Sync queue").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Waiting: 2 · Failed: 1 · To decide: 3").assertIsDisplayed()
        compose.onNodeWithText("Sync queue").performClick()

        assertEquals(1, queueOpens)
    }

    @Test
    fun `the sync queue row stays reachable when the queue is empty`() {
        render()

        compose.onNodeWithText("Sync queue").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Waiting: 0 · Failed: 0 · To decide: 0").assertIsDisplayed()
        compose.onNodeWithText("Sync queue").performClick()

        assertEquals(1, queueOpens)
    }

    @Test
    fun `diagnostics off disables report a problem and says why`() {
        render(SettingsDiagnosticsState(enabled = false, count = 2))

        compose.onNodeWithText("Client diagnostics are switched off on this Dive Hub.")
            .performScrollTo()
            .assertIsDisplayed()
        compose.onNodeWithText("An admin can switch diagnostics on. Errors stay on this device.")
            .performScrollTo()
            .assertIsDisplayed()
        compose.onNodeWithText("Report a problem").performScrollTo().assertIsNotEnabled()

        assertEquals(0, reports)
        compose.onNodeWithText("Share local logs").performScrollTo().assertIsEnabled().performClick()
        assertEquals(1, shares)
    }

    @Test
    fun `an empty buffer says there is nothing to share`() {
        render(SettingsDiagnosticsState(enabled = true, count = 0, shareEmpty = true))

        compose.onNodeWithText("There are no entries to share yet.").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `a sent report thanks the person`() {
        render(
            SettingsDiagnosticsState(
                enabled = true,
                count = 0,
                reportResult = ReportProblemResult.Sent,
            ),
        )

        compose.onNodeWithText("Diagnostics sent. Thank you.").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `a disabled refusal explains the instance switched diagnostics off`() {
        render(
            SettingsDiagnosticsState(
                enabled = false,
                count = 2,
                reportResult = ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_DISABLED),
            ),
        )

        compose.onNodeWithText("Client diagnostics were switched off meanwhile. Errors stay on this device.")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun `a rate limit asks to try again later`() {
        render(
            SettingsDiagnosticsState(
                enabled = true,
                count = 2,
                reportResult = ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_LIMITED),
            ),
        )

        compose.onNodeWithText("Too many reports from this account. Try again later.")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun `a network refusal asks to try again later`() {
        render(
            SettingsDiagnosticsState(
                enabled = true,
                count = 2,
                reportResult = ReportProblemResult.Failed(ErrorCodes.NO_ANSWER),
            ),
        )

        compose.onNodeWithText("No connection to this Dive Hub. Try again later.")
            .performScrollTo()
            .assertIsDisplayed()
    }

    private fun render(
        diagnostics: SettingsDiagnosticsState = SettingsDiagnosticsState(),
        queue: SettingsQueueState = SettingsQueueState(),
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                SettingsDebugScreen(
                    diagnostics = diagnostics,
                    queue = queue,
                    onBack = { backs += 1 },
                    onReportProblem = { reports += 1 },
                    onShareLogs = { shares += 1 },
                    onOpenQueue = { queueOpens += 1 },
                )
            }
        }
    }
}
