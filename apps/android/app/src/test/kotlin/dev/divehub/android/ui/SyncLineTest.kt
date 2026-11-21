package dev.divehub.android.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.divehub.android.R
import java.util.TimeZone
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** The sync line is quiet while the live stream is connected; otherwise it says how old the store is (FAH-342). */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SyncLineTest {
    @get:Rule
    val compose = createComposeRule()

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
    fun `connected shows no sync line`() {
        render(syncedAt = "2026-11-18T12:00:00Z", connected = true)

        compose.onNodeWithText("Last synced", substring = true).assertDoesNotExist()
        compose.onNodeWithText("No connection", substring = true).assertDoesNotExist()
    }

    @Test
    fun `disconnected says when the store was last filled`() {
        render(syncedAt = "2026-11-18T12:00:00Z", connected = false)

        compose.onNodeWithText("Last synced 18 Nov 2026, 13:00.").assertIsDisplayed()
    }

    @Test
    fun `disconnected offline keeps the cached wording`() {
        render(syncedAt = "2026-11-18T12:00:00Z", connected = false, offline = true)

        compose.onNodeWithText("No connection. Showing this dive synced as of 18 Nov 2026, 13:00.")
            .assertIsDisplayed()
    }

    private fun render(syncedAt: String?, connected: Boolean, offline: Boolean = false) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                SyncLine(
                    syncedAt = syncedAt,
                    connected = connected,
                    offline = offline,
                    offlineText = R.string.dive_offline_cached,
                )
            }
        }
    }
}
