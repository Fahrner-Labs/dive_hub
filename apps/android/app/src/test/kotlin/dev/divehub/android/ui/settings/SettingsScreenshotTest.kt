package dev.divehub.android.ui.settings

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.Spacing
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.DiagnosticLevel
import java.util.TimeZone
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The settings screen's and Debug subscreen's contract-visible shapes (FAH-317, FAH-343): the grouped sections and the
 * folded Debug detail, in both languages and both schemes. Only Material colours and type are used, so the dark frames
 * read as the same list on ink.
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

    @Test
    fun `the scanner entry sits in the logbook section while its feature flag is on`() {
        render(darkTheme = false, showScan = true)

        compose.onNodeWithText("Logbook").assertIsDisplayed()
        compose.onNodeWithText("Scan a code").assertIsDisplayed()
        capture("settings_scan_en_light.png")
    }

    @Test
    fun `settings polling shows the state and age beside the instance`() {
        render(darkTheme = false, connected = false)

        compose.onNodeWithText("Polling · 18 Nov 2026, 13:00").assertIsDisplayed()
        capture("settings_polling_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `settings polling in German and light`() {
        render(darkTheme = false, connected = false)

        compose.onNodeWithText("Polling · 18. Nov. 2026, 13:00").assertIsDisplayed()
        capture("settings_polling_de_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `settings polling in English and dark`() {
        render(darkTheme = true, connected = false)

        compose.onNodeWithText("Polling · 18 Nov 2026, 13:00").assertIsDisplayed()
        capture("settings_polling_en_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `settings polling at 320 dp and 200 percent keeps the dot on the first line`() {
        render(darkTheme = false, connected = false)

        compose.onNodeWithText("Polling", substring = true).assertIsDisplayed()
        capture("settings_polling_narrow_2x.png")
    }

    @Test
    fun `settings offline shows the state and age beside the instance`() {
        render(darkTheme = false, connected = false, offline = true)

        compose.onNodeWithText("Offline · 18 Nov 2026, 13:00").assertIsDisplayed()
        capture("settings_offline_en_light.png")
    }

    @Test
    fun `debug screen with diagnostics on in English and light`() {
        renderDebug(darkTheme = false, enabled = true)

        compose.onNodeWithText("This Dive Hub accepts diagnostics.").assertIsDisplayed()
        compose.onNodeWithText("Sync queue").assertIsDisplayed()
        capture("settings_debug_on_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `debug screen with diagnostics off in German and light`() {
        renderDebug(darkTheme = false, enabled = false)

        compose.onNodeWithText("Die Client-Diagnose ist auf diesem Dive Hub ausgeschaltet.").assertIsDisplayed()
        compose.onNodeWithText("Synchronisierungswarteschlange").assertIsDisplayed()
        capture("settings_debug_off_de_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `debug screen with diagnostics on in English and dark`() {
        renderDebug(darkTheme = true, enabled = true)

        compose.onNodeWithText("This Dive Hub accepts diagnostics.").assertIsDisplayed()
        compose.onNodeWithText("Sync queue").assertIsDisplayed()
        capture("settings_debug_on_en_dark.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-night-xxhdpi")
    fun `debug screen with diagnostics off in German and dark`() {
        renderDebug(darkTheme = true, enabled = false)

        compose.onNodeWithText("Die Client-Diagnose ist auf diesem Dive Hub ausgeschaltet.").assertIsDisplayed()
        compose.onNodeWithText("Synchronisierungswarteschlange").assertIsDisplayed()
        capture("settings_debug_off_de_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `the last settings row keeps the section rhythm above the bottom bar`() {
        render(darkTheme = false)
        compose.onNodeWithText("Debug").performScrollTo()
        compose.onNodeWithTag("settings_scroll").performTouchInput {
            swipe(start = Offset(width / 2f, height * 0.9f), end = Offset(width / 2f, 0f), durationMillis = 200)
        }
        compose.waitForIdle()
        val gap = compose.onRoot().getBoundsInRoot().bottom -
            compose.onNodeWithTag("settings_debug_row").getBoundsInRoot().bottom
        assertEquals(Spacing.s5.value, gap.value, 0.5f)
        capture("settings_bottom_spacing.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `the last debug row keeps the section rhythm above the bottom bar`() {
        renderDebug(darkTheme = false, enabled = true, previews = 10)
        compose.onNodeWithText("Share local logs").performScrollTo()
        compose.onNodeWithTag("settings_debug_scroll").performTouchInput {
            swipe(start = Offset(width / 2f, height * 0.9f), end = Offset(width / 2f, 0f), durationMillis = 200)
        }
        compose.waitForIdle()
        val gap = compose.onRoot().getBoundsInRoot().bottom -
            compose.onNodeWithTag("settings_share_row").getBoundsInRoot().bottom
        assertEquals(Spacing.s5.value, gap.value, 0.5f)
        capture("settings_debug_bottom_spacing.png")
    }

    private fun render(
        darkTheme: Boolean,
        connected: Boolean = true,
        offline: Boolean = false,
        showScan: Boolean = false,
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                SettingsScreen(
                    state = SettingsUiState(
                        instanceUrl = "https://dive.example",
                        syncedAt = "2026-11-18T12:00:00Z",
                        connected = connected,
                        offline = offline,
                    ),
                    email = "elena@example.com",
                    versionName = "0.1.0",
                    versionCode = 1001,
                    onChangeInstance = {},
                    onSyncNow = {},
                    onSignOut = {},
                    onOpenDebug = {},
                    showScan = showScan,
                    onOpenScan = {},
                )
            }
        }
    }

    private fun renderDebug(darkTheme: Boolean, enabled: Boolean, previews: Int = 2) {
        val diagnostics = SettingsDiagnosticsState(
            enabled = enabled,
            count = previews,
            preview = List(previews) { index ->
                val error = index % 2 == 0
                DiagnosticEntry(
                    occurredAt = "2026-11-18T12:00:00Z",
                    level = if (error) DiagnosticLevel.ERROR else DiagnosticLevel.WARN,
                    code = if (error) "http_500" else "offline",
                    message = if (error) "Server error on /api/dives" else "No answer while syncing",
                )
            },
        )
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                SettingsDebugScreen(
                    diagnostics = diagnostics,
                    queue = SettingsQueueState(pending = 2, failed = 1, conflicted = 1),
                    onBack = {},
                    onReportProblem = {},
                    onShareLogs = {},
                    onOpenQueue = {},
                )
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
