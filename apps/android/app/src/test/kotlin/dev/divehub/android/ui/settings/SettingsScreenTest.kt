package dev.divehub.android.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.diagnostics.ReportProblemResult
import java.util.TimeZone
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The settings actions (FAH-317): the confirmation gates "Change instance", and every row calls the one callback the
 * shell binds to the session or the sync refresher.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SettingsScreenTest {
    @get:Rule
    val compose = createComposeRule()

    private var changes = 0
    private var syncs = 0
    private var signOuts = 0
    private var reports = 0
    private var shares = 0
    private var previousZone: TimeZone = TimeZone.getDefault()

    @Before
    fun pinZone() {
        previousZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"))
    }

    @After
    fun restoreZone() {
        TimeZone.setDefault(previousZone)
    }

    @Test
    fun `the screen shows the instance, the account, the app version and the connection state`() {
        render()

        compose.onNodeWithText("https://dive.example").assertIsDisplayed()
        compose.onNodeWithText("Connected").assertIsDisplayed()
        compose.onNodeWithText("elena@example.com").assertIsDisplayed()
        compose.onNodeWithText("0.1.0 (1001)").assertIsDisplayed()
        compose.onNodeWithText("Last synced", substring = true).assertDoesNotExist()
    }

    @Test
    fun `offline shows the last sync beside the instance`() {
        render(connected = false)

        compose.onNodeWithText("Offline — last synced 18 Nov 2026, 13:00.").assertIsDisplayed()
        compose.onNodeWithText("Last synced 18 Nov 2026, 13:00.").assertDoesNotExist()
    }

    @Test
    fun `offline before the first sync says so beside the instance`() {
        render(connected = false, syncedAt = null)

        compose.onNodeWithText("Offline — not synced yet.").assertIsDisplayed()
    }

    @Test
    fun `changing the instance asks first and only the confirmation leaves`() {
        render()

        compose.onNodeWithText("Change instance").performClick()
        compose.onNodeWithText("Change instance?").assertIsDisplayed()
        compose.onNodeWithText("Cancel").performClick()
        assertEquals(0, changes)

        compose.onNodeWithText("Change instance").performClick()
        compose.onAllNodesWithText("Change instance").onLast().performClick()

        assertEquals(1, changes)
    }

    @Test
    fun `sync now triggers one refresh`() {
        render()

        compose.onNodeWithText("Sync now").performClick()

        assertEquals(1, syncs)
    }

    @Test
    fun `sign out asks first and only the confirmation ends the session`() {
        render()

        compose.onNodeWithText("Sign out").performClick()
        compose.onNodeWithText("Sign out?").assertIsDisplayed()
        compose.onNodeWithText("Cancel").performClick()
        assertEquals(0, signOuts)

        compose.onNodeWithText("Sign out").performClick()
        compose.onAllNodesWithText("Sign out").onLast().performClick()

        assertEquals(1, signOuts)
    }

    @Test
    fun `the licenses entry shows the bundled Poppins licence`() {
        render()

        compose.onNodeWithText("Open-source licenses").performClick()
        compose.onNodeWithText("Poppins (SIL Open Font License 1.1)").assertIsDisplayed()
        compose.onNodeWithText("SIL OPEN FONT LICENSE", substring = true).assertIsDisplayed()
        compose.onNodeWithText("Close").performClick()
    }

    @Test
    fun `report a problem calls the upload flow when the instance allows it`() {
        render(diagnostics = SettingsDiagnosticsState(enabled = true, count = 2))

        compose.onNodeWithText("Report a problem").performScrollTo().performClick()

        assertEquals(1, reports)
    }

    @Test
    fun `share local logs calls the export flow`() {
        render(diagnostics = SettingsDiagnosticsState(enabled = true, count = 2))

        compose.onNodeWithText("Share local logs").performScrollTo().performClick()

        assertEquals(1, shares)
    }

    @Test
    fun `diagnostics off disables report a problem and says why`() {
        render(diagnostics = SettingsDiagnosticsState(enabled = false, count = 2))

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
        render(diagnostics = SettingsDiagnosticsState(enabled = true, count = 0, shareEmpty = true))

        compose.onNodeWithText("There are no entries to share yet.").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `a sent report thanks the person`() {
        render(
            diagnostics = SettingsDiagnosticsState(
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
            diagnostics = SettingsDiagnosticsState(
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
            diagnostics = SettingsDiagnosticsState(
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
            diagnostics = SettingsDiagnosticsState(
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
        connected: Boolean = true,
        syncedAt: String? = "2026-11-18T12:00:00Z",
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                SettingsScreen(
                    state = SettingsUiState(
                        instanceUrl = "https://dive.example",
                        syncedAt = syncedAt,
                        connected = connected,
                        diagnostics = diagnostics,
                    ),
                    email = "elena@example.com",
                    versionName = "0.1.0",
                    versionCode = 1001,
                    onChangeInstance = { changes += 1 },
                    onSyncNow = { syncs += 1 },
                    onSignOut = { signOuts += 1 },
                    onReportProblem = { reports += 1 },
                    onShareLogs = { shares += 1 },
                )
            }
        }
    }
}
