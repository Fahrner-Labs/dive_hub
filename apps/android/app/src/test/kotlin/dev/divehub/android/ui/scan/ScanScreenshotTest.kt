package dev.divehub.android.ui.scan

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.store.ScanResult
import dev.divehub.shared.store.ScanStatus
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/** The scanner's screenshots (T11): its empty start, a waiting scan, and the two read outcomes with their actions. */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class ScanScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `the scanner starts quiet with the ways to read a code`() {
        render(ScanUiState())

        compose.onNodeWithText("Scan a code").assertExists()
        compose.onNodeWithText("Take a picture").assertExists()
        compose.onNodeWithText("No scans yet.").assertExists()
        capture("scan_empty_en_light.png")
    }

    @Test
    fun `a scan with no signal waits and says so`() {
        render(ScanUiState(pending = 2))

        compose.onNodeWithText("2 scans waiting to sync").assertExists()
        capture("scan_waiting_en_light.png")
    }

    @Test
    fun `a centre's code offers to add it`() {
        render(
            ScanUiState(
                results = listOf(
                    ScanResult(
                        id = 1,
                        at = "2026-11-23T10:00:00Z",
                        status = ScanStatus.Read,
                        kind = "centre",
                        entityId = "01930000-0000-7000-8000-000000000001",
                        detailJson = """{"kind":"centre","centreNumber":"12345","name":"Reef Diving, Sharm","existing":null}""",
                    ),
                ),
            ),
        )

        compose.onNodeWithText("Add Dive centre").assertExists()
        capture("scan_centre_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-night-xxhdpi")
    fun `a buddy's code shows the changes before taking it, in German and dark`() {
        render(
            ScanUiState(
                results = listOf(
                    ScanResult(
                        id = 2,
                        at = "2026-11-23T10:00:00Z",
                        status = ScanStatus.Read,
                        kind = "buddy",
                        entityId = "diver-1",
                        detailJson = """{"kind":"buddy","accountId":"ssi-1","firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","existing":{"id":"diver-1","name":"Ada Lovelace","canEdit":true,"changes":[{"field":"email","from":"old@example.com","to":"ada@example.com"}]},"candidates":[]}""",
                    ),
                ),
            ),
            darkTheme = true,
        )

        compose.onNodeWithText("Ada Lovelace aktualisieren").assertExists()
        capture("scan_buddy_de_dark.png")
    }

    private fun render(state: ScanUiState, darkTheme: Boolean = false) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                ScanScreen(
                    state = state,
                    onBack = {},
                    onCamera = {},
                    onImage = {},
                    onScan = {},
                    onTake = { _, _, _ -> },
                    onCreateCentre = { _, _ -> },
                    onSync = {},
                    onDismiss = {},
                    onClearError = {},
                )
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
