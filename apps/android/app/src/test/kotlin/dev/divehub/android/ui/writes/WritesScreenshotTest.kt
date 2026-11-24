package dev.divehub.android.ui.writes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.PendingChangeWarning
import dev.divehub.android.ui.PendingWarningDialog
import dev.divehub.android.ui.Spacing
import dev.divehub.shared.write.ConflictFieldView
import dev.divehub.shared.write.QueuedWriteView
import dev.divehub.shared.write.WriteDropReason
import dev.divehub.shared.write.WriteNotice
import dev.divehub.shared.write.WriteCapability
import dev.divehub.shared.write.WriteConflictView
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The write surface's contract-visible shapes (FAH-466): the pending indicator, the device-vs-hub decision screen
 * and the D8 sign-out warning, in both languages and both schemes.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class WritesScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `conflicts in English and light`() {
        renderConflicts(darkTheme = false)

        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        // The logbook's words, not the API's numbers: a duration, a depth with its unit and a temperature (FAH-480).
        compose.onNodeWithText("45 min").assertIsDisplayed()
        compose.onNodeWithText("24.8 m").assertIsDisplayed()
        compose.onNodeWithText("18 °C").assertIsDisplayed()
        capture("conflicts_en_light.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w411dp-h891dp-xxhdpi")
    fun `conflicts in German and light`() {
        renderConflicts(darkTheme = false)

        compose.onNodeWithText("Tauchgang 42").assertIsDisplayed()
        capture("conflicts_de_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `conflicts in English and dark`() {
        renderConflicts(darkTheme = true)

        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        capture("conflicts_en_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `conflicts at 320 dp and 200 percent`() {
        renderConflicts(darkTheme = false)

        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        capture("conflicts_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `conflicts in German at 320 dp and 200 percent`() {
        renderConflicts(darkTheme = false)

        compose.onNodeWithText("Tauchgang 42").assertIsDisplayed()
        capture("conflicts_de_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `a start conflict in English at 320 dp and 200 percent`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                ConflictsScreen(
                    state = WritesUiState(
                        capability = WriteCapability.Writeable,
                        conflicted = 1,
                        conflicts = listOf(startConflict()),
                    ),
                    units = "metric",
                    onBack = {},
                    onResolve = { _, _ -> },
                    onRetry = {},
                    onDismissNotices = {},
                    onOpenDive = {},
                )
            }
        }

        // The merge's `set.startsAt` reads as "Start" and as the Dive page's wall-clock start, wrapped at 320 dp.
        compose.onNodeWithText("Start").assertIsDisplayed()
        compose.onAllNodesWithText("Tue 24 Nov 2026, 08:00 (UTC+2)").onFirst().assertIsDisplayed()
        capture("conflicts_start_narrow_2x.png")
    }

    @Test
    fun `waiting queue in English and light`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                ConflictsScreen(
                    state = WritesUiState(
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
                    units = "metric",
                    onBack = {},
                    onResolve = { _, _ -> },
                    onRetry = {},
                    onDismissNotices = {},
                    onOpenDive = {},
                )
            }
        }

        compose.onNodeWithText("Waiting to sync").assertIsDisplayed()
        capture("writes_queue_en_light.png")
    }

    @Test
    fun `dropped changes in English and light`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                ConflictsScreen(
                    state = WritesUiState(
                        capability = WriteCapability.Writeable,
                        notices = listOf(
                            WriteNotice(
                                entityType = "dive",
                                entityId = "dive-1",
                                status = 404,
                                reason = WriteDropReason.NotFound,
                                mergedInto = "dive-9",
                            ),
                        ),
                    ),
                    units = "metric",
                    onBack = {},
                    onResolve = { _, _ -> },
                    onRetry = {},
                    onDismissNotices = {},
                    onOpenDive = {},
                )
            }
        }

        compose.onNodeWithText("Changes not applied").assertIsDisplayed()
        capture("writes_notices_en_light.png")
    }

    @Test
    fun `conflict screen with nothing to decide`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                ConflictsScreen(
                    state = WritesUiState(capability = WriteCapability.Writeable),
                    units = "metric",
                    onBack = {},
                    onResolve = { _, _ -> },
                    onRetry = {},
                    onDismissNotices = {},
                    onOpenDive = {},
                )
            }
        }

        compose.onNodeWithText("Nothing to sync or decide").assertIsDisplayed()
        capture("conflicts_empty_en_light.png")
    }

    @Test
    fun `waiting indicator in English and light`() {
        renderLine(WritesUiState(capability = WriteCapability.Writeable, pending = 2))

        compose.onNodeWithText("2 changes waiting to sync").assertIsDisplayed()
        capture("writes_waiting_en_light.png")
    }

    @Test
    fun `failed indicator in English and light`() {
        renderLine(WritesUiState(capability = WriteCapability.Writeable, failed = 1))

        compose.onNodeWithText("1 change couldn’t be sent").assertIsDisplayed()
        capture("writes_failed_en_light.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `conflict indicator in English and dark`() {
        renderLine(WritesUiState(capability = WriteCapability.Writeable, conflicted = 1), darkTheme = true)

        compose.onNodeWithText("1 change needs your decision").assertIsDisplayed()
        capture("writes_conflict_en_dark.png")
    }

    @Test
    fun `sign-out warning in English and light`() {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                PendingWarningDialog(
                    warning = PendingChangeWarning.SignOut(count = 2, changeInstance = false),
                    onSyncNow = {},
                    onDiscard = {},
                    onCancel = {},
                )
            }
        }

        compose.onNodeWithText("2 changes haven’t synced").assertIsDisplayed()
        compose.onNode(isDialog()).captureRoboImage("src/test/screenshots/pending_sign_out_en_light.png")
    }

    private fun renderConflicts(darkTheme: Boolean) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                ConflictsScreen(
                    state = WritesUiState(
                        capability = WriteCapability.Writeable,
                        conflicted = 1,
                        conflicts = listOf(conflict()),
                    ),
                    units = "metric",
                    onBack = {},
                    onResolve = { _, _ -> },
                    onRetry = {},
                    onDismissNotices = {},
                    onOpenDive = {},
                )
            }
        }
    }

    private fun renderLine(state: WritesUiState, darkTheme: Boolean = false) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
                    Box(Modifier.padding(top = Spacing.s4)) {
                        CompositionLocalProvider(LocalWrites provides state) { WriteQueueLine() }
                    }
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun conflict() = WriteConflictView(
        entryId = 7,
        entityType = "dive",
        entityId = "dive-1",
        serverVersion = 5,
        diveNumber = 42,
        fields = listOf(
            ConflictFieldView(
                field = "set.durationSeconds",
                base = JsonPrimitive(2640),
                server = JsonPrimitive(2820),
                local = JsonPrimitive(2700),
                localAction = "set",
            ),
            ConflictFieldView(
                field = "set.maxDepthM",
                base = JsonPrimitive(24.3),
                server = JsonPrimitive(24.6),
                local = JsonPrimitive(24.8),
                localAction = "set",
            ),
            ConflictFieldView(
                field = "set.waterTemperatureC",
                base = JsonPrimitive(17),
                server = JsonPrimitive(19),
                local = JsonPrimitive(18),
                localAction = "set",
            ),
            ConflictFieldView(
                field = "notes",
                base = JsonPrimitive("A good dive"),
                server = JsonPrimitive("A great dive"),
                local = JsonPrimitive("A very good dive"),
            ),
        ),
    )

    private fun startConflict() = WriteConflictView(
        entryId = 11,
        entityType = "dive",
        entityId = "dive-2",
        serverVersion = 3,
        diveNumber = 45,
        fields = listOf(
            ConflictFieldView(
                field = "set.startsAt",
                base = startAt("2026-11-24T05:00:00Z", 3600),
                server = startAt("2026-11-24T06:00:00Z", 7200),
                local = startAt("2026-11-24T06:00:00Z", 7200),
                localAction = "set",
            ),
        ),
    )

    private fun startAt(at: String, offsetSeconds: Int) = buildJsonObject {
        put("at", JsonPrimitive(at))
        put("utcOffsetSeconds", JsonPrimitive(offsetSeconds))
    }
}
