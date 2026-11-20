package dev.divehub.android.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import dev.divehub.android.ui.DiveHubTheme
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
 * The settings actions (FAH-317, FAH-343): the instance row is the only "Change instance" affordance, the Debug entry
 * opens its subscreen, and every row calls the one callback the shell binds to the session or the sync refresher.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SettingsScreenTest {
    @get:Rule
    val compose = createComposeRule()

    private var changes = 0
    private var syncs = 0
    private var signOuts = 0
    private var debugs = 0
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
    fun `the instance row is the only change-instance affordance`() {
        render()

        compose.onNodeWithText("Change instance").assertDoesNotExist()

        compose.onNodeWithText("Instance").performClick()
        compose.onNodeWithText("Change instance?").assertIsDisplayed()
        compose.onNodeWithText("Cancel").performClick()
        assertEquals(0, changes)

        compose.onNodeWithText("Instance").performClick()
        compose.onAllNodesWithText("Change instance").onLast().performClick()

        assertEquals(1, changes)
    }

    @Test
    fun `the debug entry opens the diagnostics subscreen`() {
        render()

        compose.onNodeWithText("Debug").performScrollTo().performClick()

        assertEquals(1, debugs)
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

    private fun render(
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
                    ),
                    email = "elena@example.com",
                    versionName = "0.1.0",
                    versionCode = 1001,
                    onChangeInstance = { changes += 1 },
                    onSyncNow = { syncs += 1 },
                    onSignOut = { signOuts += 1 },
                    onOpenDebug = { debugs += 1 },
                )
            }
        }
    }
}
