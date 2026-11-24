package dev.divehub.android.ui.scan

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.Density
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

/**
 * The scanner's screenshots (T11): its empty start, a waiting scan, the read outcomes with their actions, a refusal and
 * the ambiguous person who must be chosen for deliberately.
 */
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
        compose.onNodeWithText("Paste a code instead").assertExists()
        compose.onNodeWithText("No scans yet.").assertExists()
        capture("scan_empty_en_light.png")
    }

    @Test
    fun `a scan with no signal waits in the scans section and says so`() {
        render(ScanUiState(pending = 2))

        compose.onNodeWithText("2 changes waiting to sync").assertExists()
        compose.onNodeWithText("No scans yet.").assertDoesNotExist()
        capture("scan_waiting_en_light.png")
    }

    @Test
    fun `reading with nothing waiting is a quiet line, not a waiting box`() {
        render(ScanUiState(busy = true))

        compose.onNodeWithText("Reading…").assertExists()
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
    @Config(qualifiers = "de-rDE-w320dp-h891dp-night-xxhdpi")
    fun `a buddy's code shows the changes at 320 dp and 200 percent in German and dark`() {
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
            fontScale = 2f,
        )

        compose.onNodeWithText("Ada Lovelace aktualisieren").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("E-Mail: old@example.com → ada@example.com", substring = true).assertIsDisplayed()
        capture("scan_buddy_de_dark.png")
    }

    @Test
    fun `a refusal is a designed state in English and light`() {
        render(ScanUiState(results = listOf(refusal())))

        compose.onNodeWithText("Not added: that is not a code Dive Hub knows.").assertExists()
        capture("scan_refused_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `a refusal is a designed state in German and light`() {
        render(ScanUiState(results = listOf(refusal())))

        compose.onNodeWithText("Nicht hinzugefügt: Dive Hub kennt diesen Code nicht.").assertExists()
        capture("scan_refused_de_light.png")
    }

    @Test
    fun `an ambiguous person starts on the new Diver rather than a candidate`() {
        render(ScanUiState(results = listOf(candidateRead())))

        compose.onNodeWithText("Add Ada Lovelace as a new Diver").assertExists()
        capture("scan_candidates_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `an ambiguous person in German starts on the new Diver too`() {
        render(ScanUiState(results = listOf(candidateRead())))

        compose.onNodeWithText("Ada Lovelace als neuen Taucher anlegen").assertExists()
        capture("scan_candidates_de_light.png")
    }

    @Test
    fun `a created and an existing outcome both offer Dismiss`() {
        render(
            ScanUiState(
                results = listOf(
                    ScanResult(7, "2026-11-23T10:00:00Z", ScanStatus.Created, kind = "diver", name = "Ada Lovelace"),
                    ScanResult(8, "2026-11-23T10:00:00Z", ScanStatus.Existing, kind = "centre", name = "Reef Diving"),
                ),
            ),
        )

        compose.onNodeWithText("Diver added: Ada Lovelace.").assertExists()
        compose.onNodeWithText("Already here: Reef Diving.").assertExists()
        compose.onAllNodesWithText("Dismiss").assertCountEquals(2)
        capture("scan_outcomes_en_light.png")
    }

    private fun refusal() = ScanResult(
        id = 3,
        at = "2026-11-23T10:00:00Z",
        status = ScanStatus.Failed,
        errorCode = "code_not_recognised",
    )

    /** A code that belongs to a Diver here, but the server could not tell which one: two candidates, no choice made. */
    private fun candidateRead() = ScanResult(
        id = 6,
        at = "2026-11-23T10:00:00Z",
        status = ScanStatus.Read,
        kind = "buddy",
        entityId = null,
        detailJson = """{"kind":"buddy","accountId":"ssi-1","firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","existing":null,"candidates":[{"id":"diver-9","name":"Ada Lovelace"},{"id":"diver-10","name":"Ada B. Lovelace"}]}""",
    )

    private fun render(
        state: ScanUiState,
        darkTheme: Boolean = false,
        fontScale: Float? = null,
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, fontScale ?: density.fontScale),
                ) {
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
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }
}
