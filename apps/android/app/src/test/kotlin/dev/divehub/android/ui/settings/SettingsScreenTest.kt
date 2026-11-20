package dev.divehub.android.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
    fun `the screen shows the instance, the account and the app version`() {
        render()

        compose.onNodeWithText("https://dive.example").assertIsDisplayed()
        compose.onNodeWithText("elena@example.com").assertIsDisplayed()
        compose.onNodeWithText("0.1.0 (1001)").assertIsDisplayed()
        compose.onNodeWithText("Last synced 18 Nov 2026, 13:00.").assertIsDisplayed()
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

    private fun render() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                SettingsScreen(
                    state = SettingsUiState(
                        instanceUrl = "https://dive.example",
                        syncedAt = "2026-11-18T12:00:00Z",
                    ),
                    email = "elena@example.com",
                    versionName = "0.1.0",
                    versionCode = 1001,
                    onChangeInstance = { changes += 1 },
                    onSyncNow = { syncs += 1 },
                    onSignOut = { signOuts += 1 },
                )
            }
        }
    }
}
