package dev.divehub.android.ui.writes

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.write.WriteCapability
import dev.divehub.shared.write.WriteDropReason
import dev.divehub.shared.write.WriteNotice
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** The pending indicator is a state on the screen, and a pre-feed server hides it (ADR 0084 D4, ADR 0083 D7). */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class WriteQueueLineTest {
    @get:Rule
    val compose = createComposeRule()

    private var reviews = 0

    @Test
    fun `waiting writes say how many and open the list of what waits`() {
        render(WritesUiState(capability = WriteCapability.Writeable, pending = 2))

        compose.onNodeWithText("2 changes waiting to sync").assertIsDisplayed()
        compose.onNodeWithText("View").performClick()

        assertEquals(1, reviews)
    }

    @Test
    fun `a parked conflict needs the user and offers review`() {
        render(WritesUiState(capability = WriteCapability.Writeable, conflicted = 1))

        compose.onNodeWithText("1 change needs your decision").assertIsDisplayed()
        compose.onNodeWithText("Review").performClick()

        assertEquals(1, reviews)
    }

    @Test
    fun `a failed replay is a state that opens the queue`() {
        render(WritesUiState(capability = WriteCapability.Writeable, failed = 1))

        compose.onNodeWithText("1 change couldn’t be sent").assertIsDisplayed()
        compose.onNodeWithText("View").performClick()

        assertEquals(1, reviews)
    }

    @Test
    fun `a failure is not hidden by writes that still wait`() {
        render(WritesUiState(capability = WriteCapability.Writeable, pending = 2, failed = 1))

        compose.onNodeWithText("1 change couldn’t be sent").assertIsDisplayed()
        compose.onNodeWithText("2 changes waiting to sync").assertDoesNotExist()
    }

    @Test
    fun `a dropped change remains a visible state with a way to see it`() {
        render(
            WritesUiState(
                capability = WriteCapability.Writeable,
                notices = listOf(
                    WriteNotice(
                        entityType = "dive",
                        entityId = "dive-1",
                        status = 404,
                        reason = WriteDropReason.NotFound,
                    ),
                ),
            ),
        )

        compose.onNodeWithText("1 change couldn’t be applied").assertIsDisplayed()
        compose.onNodeWithText("View").performClick()

        assertEquals(1, reviews)
    }

    @Test
    fun `a pre-feed instance hides the indicator even with saved writes`() {
        render(WritesUiState(capability = WriteCapability.ReadOnly, pending = 2))

        compose.onNodeWithText("2 changes waiting to sync").assertDoesNotExist()
    }

    @Test
    fun `an unknown capability keeps the saved writes visible from an offline start`() {
        render(WritesUiState(capability = WriteCapability.Unknown, pending = 1))

        compose.onNodeWithText("1 change waiting to sync").assertIsDisplayed()
    }

    @Test
    fun `an empty queue draws nothing`() {
        render(WritesUiState(capability = WriteCapability.Writeable))

        compose.onNodeWithText("waiting to sync", substring = true).assertDoesNotExist()
        compose.onNodeWithText("needs your decision", substring = true).assertDoesNotExist()
    }

    private fun render(state: WritesUiState) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                CompositionLocalProvider(
                    LocalWrites provides state,
                    LocalWritesActions provides WritesActions(onReview = { reviews += 1 }),
                ) {
                    WriteQueueLine()
                }
            }
        }
    }
}
