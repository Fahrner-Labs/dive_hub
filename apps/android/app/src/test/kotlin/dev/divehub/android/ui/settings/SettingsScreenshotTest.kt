package dev.divehub.android.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import java.util.TimeZone
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The settings screen's contract-visible shape (FAH-317): the four grouped sections, in both languages and both
 * schemes. Only Material colours and type are used, so the dark frames read as the same list on ink.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class SettingsScreenshotTest {
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
    fun `settings in English and light`() {
        render(darkTheme = false)

        compose.onNodeWithText("Connection").assertIsDisplayed()
        compose.onNodeWithText("Sync now").assertIsDisplayed()
        capture("settings_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `settings in German and light`() {
        render(darkTheme = false)

        compose.onNodeWithText("Verbindung").assertIsDisplayed()
        compose.onNodeWithText("Jetzt synchronisieren").assertIsDisplayed()
        capture("settings_de_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `settings in English and dark`() {
        render(darkTheme = true)

        compose.onNodeWithText("Connection").assertIsDisplayed()
        capture("settings_en_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-night-xxhdpi")
    fun `settings in German and dark`() {
        render(darkTheme = true)

        compose.onNodeWithText("Verbindung").assertIsDisplayed()
        capture("settings_de_dark.png")
    }

    private fun render(darkTheme: Boolean) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                SettingsScreen(
                    state = SettingsUiState(
                        instanceUrl = "https://dive.example",
                        syncedAt = "2026-11-18T12:00:00Z",
                    ),
                    email = "elena@example.com",
                    versionName = "0.1.0",
                    versionCode = 1001,
                    onChangeInstance = {},
                    onSyncNow = {},
                    onSignOut = {},
                )
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
