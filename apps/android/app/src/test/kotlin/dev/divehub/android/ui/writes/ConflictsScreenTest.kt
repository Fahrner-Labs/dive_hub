package dev.divehub.android.ui.writes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.shared.write.ConflictFieldView
import dev.divehub.shared.write.ConflictSide
import dev.divehub.shared.write.QueuedWriteView
import dev.divehub.shared.write.WriteDropReason
import dev.divehub.shared.write.WriteNotice
import dev.divehub.shared.write.WriteCapability
import dev.divehub.shared.write.WriteConflictView
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.Locale

/** The device-vs-hub decision screen (ADR 0084 D3): both values, this phone preselected, nothing decided silently. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class ConflictsScreenTest {
    @get:Rule
    val compose = createComposeRule()

    private val resolved = mutableListOf<Pair<Long, Map<String, ConflictSide>>>()
    private var retries = 0

    @Test
    fun `conflict values read in the logbook's units`() {
        val context = RuntimeEnvironment.getApplication()

        assertEquals("45 min", conflictValueText(context, "set.durationSeconds", JsonPrimitive(2700), Locale.US, false))
        assertEquals("1 h 5 min", conflictValueText(context, "set.durationSeconds", JsonPrimitive(3900), Locale.US, false))
        assertEquals("24.8 m", conflictValueText(context, "set.maxDepthM", JsonPrimitive(24.8), Locale.US, false))
        assertEquals("81.4 ft", conflictValueText(context, "set.maxDepthM", JsonPrimitive(24.8), Locale.US, true))
        assertEquals("18 °C", conflictValueText(context, "set.waterTemperatureC", JsonPrimitive(18), Locale.US, false))
        assertEquals("Buddy", conflictValueText(context, "participants.diver-1", JsonPrimitive("buddy"), Locale.US, false))
    }

    @Test
    fun `a start conflict reads as the dive page reads it`() {
        val context = RuntimeEnvironment.getApplication()
        val start = buildJsonObject {
            put("at", JsonPrimitive("2026-11-24T06:00:00Z"))
            put("utcOffsetSeconds", JsonPrimitive(7200))
        }

        assertEquals(
            "Tue 24 Nov 2026, 08:00 (UTC+2)",
            conflictValueText(context, "set.startsAt", start, Locale.US, false),
        )
    }

    @Test
    fun `an unknown field keeps the API's own value`() {
        val context = RuntimeEnvironment.getApplication()

        assertEquals(
            "some value",
            conflictValueText(context, "newField", JsonPrimitive("some value"), Locale.US, false),
        )
    }

    @Test
    fun `the screen names the dive and shows both sides of a field`() {
        render()

        compose.onNodeWithText("Changes on this phone").assertIsDisplayed()
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        compose.onNodeWithText("Notes").assertIsDisplayed()
        compose.onAllNodesWithText("This phone").onFirst().assertIsDisplayed()
        compose.onAllNodesWithText("The server").onFirst().assertIsDisplayed()
        compose.onNodeWithText("Local note").assertIsDisplayed()
        compose.onNodeWithText("Server note").assertIsDisplayed()
    }

    @Test
    fun `keeping the selection without a change keeps this phone`() {
        render()

        compose.onNodeWithText("Keep selected").performClick()

        assertEquals(1, resolved.size)
        assertEquals(ConflictSide.ThisPhone, resolved.single().second["notes"])
        assertEquals(ConflictSide.ThisPhone, resolved.single().second["suit"])
    }

    @Test
    fun `choosing the server for one field keeps this phone for the other`() {
        render()

        compose.onNodeWithTag("conflict_notes_server").performClick()
        compose.onNodeWithText("Keep selected").performClick()

        val choices = resolved.single().second
        assertEquals(ConflictSide.Server, choices["notes"])
        assertEquals(ConflictSide.ThisPhone, choices["suit"])
    }

    @Test
    fun `use the server for everything decides every field at once`() {
        render()

        compose.onNodeWithText("Use the server’s for everything").performClick()

        val choices = resolved.single().second
        assertEquals(ConflictSide.Server, choices["notes"])
        assertEquals(ConflictSide.Server, choices["suit"])
    }

    @Test
    fun `a waiting queue lists the thing and the fields it carries`() {
        render(
            WritesUiState(
                capability = WriteCapability.Writeable,
                pending = 1,
                queued = listOf(
                    QueuedWriteView(
                        entryId = 8,
                        fields = listOf("notes", "set.durationSeconds"),
                        failed = false,
                        diveNumber = 43,
                    ),
                ),
            ),
        )

        compose.onNodeWithText("Waiting to sync").assertIsDisplayed()
        compose.onNodeWithText("Dive 43").assertIsDisplayed()
        compose.onNodeWithText("Notes, Duration").assertIsDisplayed()
    }

    @Test
    fun `a failed queue entry is named and offers a retry`() {
        render(
            WritesUiState(
                capability = WriteCapability.Writeable,
                failed = 1,
                queued = listOf(
                    QueuedWriteView(
                        entryId = 9,
                        fields = listOf("notes"),
                        failed = true,
                        diveNumber = 44,
                    ),
                ),
            ),
        )

        compose.onNodeWithText("Couldn’t be sent").assertIsDisplayed()
        compose.onNodeWithText("Try again").performClick()

        assertEquals(1, retries)
    }

    @Test
    fun `a dropped change is named and can be dismissed`() {
        var dismissed = 0
        render(
            state = WritesUiState(
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
            onDismissNotices = { dismissed += 1 },
        )

        compose.onNodeWithText("Changes not applied").assertIsDisplayed()
        compose.onNodeWithText("A dive was deleted on the server. Your change was not applied.")
            .assertIsDisplayed()
        compose.onNodeWithText("Dismiss").performClick()

        assertEquals(1, dismissed)
    }

    @Test
    fun `a merged dive is named and can be opened from the notice`() {
        var opened: String? = null
        render(
            state = WritesUiState(
                capability = WriteCapability.Writeable,
                notices = listOf(
                    WriteNotice(
                        entityType = "dive",
                        entityId = "dive-1",
                        status = 404,
                        reason = WriteDropReason.NotFound,
                        mergedInto = "dive-9",
                        mergedIntoNumber = 9,
                    ),
                ),
            ),
            onOpenDive = { opened = it },
        )

        compose.onNodeWithText("This dive was merged into Dive 9. Your change was not applied.")
            .assertIsDisplayed()
        compose.onNodeWithText("Open dive").performClick()

        assertEquals("dive-9", opened)
    }

    @Test
    fun `an empty queue explains that nothing needs a decision`() {
        render(WritesUiState(capability = WriteCapability.Writeable))

        compose.onNodeWithText("Nothing to sync or decide").assertIsDisplayed()
        compose.onNodeWithText("No change on this phone is waiting to sync or for a decision.").assertIsDisplayed()
    }

    private fun render(
        state: WritesUiState = WritesUiState(
            capability = WriteCapability.Writeable,
            conflicted = 1,
            conflicts = listOf(conflict()),
        ),
        onDismissNotices: () -> Unit = {},
        onOpenDive: (String) -> Unit = {},
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                ConflictsScreen(
                    state = state,
                    units = "metric",
                    onBack = {},
                    onResolve = { entryId, choices -> resolved += entryId to choices },
                    onRetry = { retries += 1 },
                    onDismissNotices = onDismissNotices,
                    onOpenDive = onOpenDive,
                )
            }
        }
    }

    private fun conflict() = WriteConflictView(
        entryId = 7,
        entityType = "dive",
        entityId = "dive-1",
        serverVersion = 5,
        diveNumber = 42,
        fields = listOf(
            ConflictFieldView(
                field = "notes",
                base = JsonPrimitive("Base note"),
                server = JsonPrimitive("Server note"),
                local = JsonPrimitive("Local note"),
            ),
            ConflictFieldView(
                field = "suit",
                base = JsonPrimitive("wet"),
                server = JsonPrimitive("dry"),
                local = JsonPrimitive("semi-dry"),
                localAction = "value",
            ),
        ),
    )
}
