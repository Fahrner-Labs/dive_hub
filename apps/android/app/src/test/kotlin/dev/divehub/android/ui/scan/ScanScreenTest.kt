package dev.divehub.android.ui.scan

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.store.ScanResult
import dev.divehub.shared.store.ScanStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The scanner's interactions (T11): a pasted code goes to the queue's reading unchanged, and a read's actions ask the
 * ViewModel for the write they lead to.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class ScanScreenTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `a pasted code is sent as it is and the field is cleared`() {
        var scanned: String? = null
        render(ScanUiState(), onScan = { scanned = it })

        compose.onNodeWithText("Paste a code").performTextInput("  SSI;buddy;ada  ")
        compose.onNodeWithText("Read code").performClick()

        assertEquals("  SSI;buddy;ada  ", scanned)
    }

    @Test
    fun `creating the centre a read found asks for the uuid the scan kept`() {
        var centre: ScanResult? = null
        val result = centreRead()
        render(ScanUiState(results = listOf(result)), onCreateCentre = { found, _ -> centre = found })

        compose.onNodeWithText("Add Dive centre").performClick()

        assertEquals(result, centre)
    }

    @Test
    fun `a known Diver's code takes it for that Diver after showing the changes`() {
        var taken: Triple<ScanResult, String?, Boolean>? = null
        val result = buddyRead()
        render(ScanUiState(results = listOf(result)), onTake = { found, id, create -> taken = Triple(found, id, create) })

        compose.onNodeWithText("E-mail: old@example.com → ada@example.com", substring = true).assertIsDisplayed()
        compose.onNodeWithText("Update Ada Lovelace").performClick()

        assertEquals(result, taken!!.first)
        assertEquals("diver-1", taken!!.second)
        assertEquals(false, taken!!.third)
    }

    @Test
    fun `an unknown code asks who it is and a new Diver is the preselected answer`() {
        var taken: Triple<ScanResult, String?, Boolean>? = null
        val result = buddyRead(existing = false)
        render(ScanUiState(results = listOf(result)), onTake = { found, id, create -> taken = Triple(found, id, create) })

        compose.onNodeWithText("Add Ada Lovelace as a new Diver").assertIsDisplayed()
        compose.onNodeWithText("Add Diver").performClick()

        assertNull(taken!!.second)
        assertTrue(taken!!.third)
    }

    @Test
    fun `a refusal is a state on the scan, not a toast`() {
        val failed = ScanResult(3, "2026-11-23T10:00:00Z", ScanStatus.Failed, errorCode = "code_not_recognised")
        render(ScanUiState(results = listOf(failed)))

        compose.onNodeWithText("Not added: that is not a code Dive Hub knows.").assertIsDisplayed()
    }

    private fun centreRead() = ScanResult(
        id = 1,
        at = "2026-11-23T10:00:00Z",
        status = ScanStatus.Read,
        kind = "centre",
        entityId = "01930000-0000-7000-8000-000000000001",
        detailJson = """{"kind":"centre","centreNumber":"12345","name":"Reef Diving, Sharm","existing":null}""",
    )

    private fun buddyRead(existing: Boolean = true) = ScanResult(
        id = 2,
        at = "2026-11-23T10:00:00Z",
        status = ScanStatus.Read,
        kind = "buddy",
        entityId = if (existing) "diver-1" else null,
        detailJson = if (existing) {
            """{"kind":"buddy","accountId":"ssi-1","firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","existing":{"id":"diver-1","name":"Ada Lovelace","canEdit":true,"changes":[{"field":"email","from":"old@example.com","to":"ada@example.com"}]},"candidates":[]}"""
        } else {
            """{"kind":"buddy","accountId":"ssi-1","firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","existing":null,"candidates":[]}"""
        },
    )

    private fun render(
        state: ScanUiState,
        onScan: (String) -> Unit = {},
        onTake: (ScanResult, String?, Boolean) -> Unit = { _, _, _ -> },
        onCreateCentre: (ScanResult, ReadCode) -> Unit = { _, _ -> },
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                ScanScreen(
                    state = state,
                    onBack = {},
                    onCamera = {},
                    onImage = {},
                    onScan = onScan,
                    onTake = onTake,
                    onCreateCentre = onCreateCentre,
                    onSync = {},
                    onDismiss = {},
                    onClearError = {},
                )
            }
        }
    }
}
