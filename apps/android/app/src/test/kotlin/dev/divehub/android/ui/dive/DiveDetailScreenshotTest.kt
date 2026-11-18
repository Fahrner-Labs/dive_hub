package dev.divehub.android.ui.dive

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewFromRecording
import dev.divehub.api.models.DiveViewFromRecordingStartsAt
import dev.divehub.api.models.DiveViewParticipantsItem
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemDevice
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewRecordingsItemSummarySurfaceBreaksItem
import dev.divehub.api.models.DiveViewValues
import dev.divehub.api.models.DiveViewValuesStartsAt
import dev.divehub.api.models.DiveViewWaterMismatch
import dev.divehub.api.models.NeighboursView
import dev.divehub.api.models.NeighboursViewNext
import dev.divehub.api.models.NeighboursViewPrevious
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.api.models.RecordingSamplesViewSeriesItem
import dev.divehub.shared.logbook.ProviderDirectory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The contract-visible dive-page states as pictures (client contract, Dives): an edited value with what the recording
 * says, the time-zone note, a Dive without a Recording, the water mismatch, and Surface breaks in the profile. They
 * are rendered from a fixed state, so a drawing change shows up as a changed image.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class DiveDetailScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `an edited value says what the recording had`() {
        render(
            state(
                dive = dive(
                    overrides = listOf("maxDepthM", "startsAt"),
                    fromRecording = fromRecording(maxDepthM = 20.0),
                ),
            ),
        )
        compose.onNodeWithText("edited · recording: 20.0 m").assertIsDisplayed()
        capture("dive_edited_mark.png")
    }

    @Test
    fun `the time zone note says where the offset came from`() {
        render(state(dive = dive(source = "position")))
        compose.onNodeWithText("Time zone from where the dive was").assertIsDisplayed()
        capture("dive_time_zone_note.png")
    }

    @Test
    fun `a dive without a recording names the provider and what comes next`() {
        render(
            state(
                dive = dive(recordings = emptyList(), fromProvider = "ssi", waterType = null),
                neighbours = null,
            ),
        )
        compose.onNodeWithText(
            "No recording yet: this dive comes from your SSI logbook, typed in by hand.",
        ).assertIsDisplayed()
        compose.onNodeWithText(
            "When you import the dive computer’s file, its recording is added, and its values replace these.",
        ).assertIsDisplayed()
        capture("dive_no_recording.png")
    }

    @Test
    fun `a water mismatch names both waters and how the depths read`() {
        render(
            state(
                dive = dive(
                    waterType = "fresh",
                    waterMismatch = DiveViewWaterMismatch(computer = "salt", site = "fresh", depthPercent = -2.0),
                ),
            ),
        )
        compose.onNodeWithText("Your computer was set to salt water; this site is fresh water.").assertIsDisplayed()
        compose.onNodeWithText("Its depths read about 2 % shallow.").assertIsDisplayed()
        capture("dive_water_mismatch.png")
    }

    @Test
    fun `surface breaks are a band in the profile with each break's length`() {
        render(
            state(
                dive = dive(
                    recordings = listOf(
                        recording(
                            breaks = listOf(
                                DiveViewRecordingsItemSummarySurfaceBreaksItem(30.0, 270.0),
                                DiveViewRecordingsItemSummarySurfaceBreaksItem(420.0, 540.0),
                            ),
                        ),
                    ),
                ),
                neighbours = NeighboursView(
                    previous = null,
                    next = NeighboursViewNext("d2"),
                    position = 1,
                    total = 312,
                ),
            ),
        )
        compose.onNodeWithText("Dive 1 of 312").assertIsDisplayed()
        compose.onNodeWithContentDescription("Previous dive (unavailable)").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Surface break 4 min"))
        compose.onNodeWithText("Surface break 4 min").assertIsDisplayed()
        compose.onNodeWithText("Surface break 2 min").assertIsDisplayed()
        capture("dive_surface_breaks.png")
    }

    @Test
    fun `a failed profile leaves the rest of the page standing`() {
        render(state(dive = dive(), profileSamples = null, samplesError = "internal_error"))
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("The instance had a problem. Try again later."))
        compose.onNodeWithText("The instance had a problem. Try again later.").assertIsDisplayed()
        capture("dive_profile_failed.png")
    }

    private fun render(state: DiveDetailUiState) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveDetailScreen(
                    state = state,
                    units = "metric",
                    onBack = {},
                    onRetry = {},
                    onOpenDive = {},
                )
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun state(
        dive: DiveView,
        neighbours: NeighboursView? = NeighboursView(
            previous = NeighboursViewPrevious("d0"),
            next = NeighboursViewNext("d2"),
            position = 12,
            total = 312,
        ),
        profileSamples: RecordingSamplesView? = samples(),
        samplesError: String? = null,
    ) = DiveDetailUiState(
        diveId = dive.id,
        loading = false,
        dive = dive,
        providers = ProviderDirectory(names = mapOf("ssi" to "SSI")),
        neighbours = neighbours,
        samples = profileSamples,
        samplesError = samplesError,
    )

    private fun dive(
        number: Int? = 42,
        overrides: List<String> = emptyList(),
        source: String = "device",
        recordings: List<DiveViewRecordingsItem> = listOf(recording()),
        fromProvider: String? = null,
        waterType: String? = "salt",
        waterMismatch: DiveViewWaterMismatch? = null,
        fromRecording: DiveViewFromRecording? = fromRecording(),
    ) = DiveView(
        id = "d1",
        diverId = "diver",
        version = 3,
        values = DiveViewValues(
            number = number,
            startsAt = DiveViewValuesStartsAt("2026-04-02T09:00:00Z", 7200),
            durationSeconds = 3_000.0,
            maxDepthM = 24.3,
            avgDepthM = 12.0,
            waterTemperatureC = 18.0,
        ),
        overrides = overrides,
        utcOffsetSource = source,
        fromProvider = fromProvider,
        fromRecording = fromRecording,
        notes = "Drifted along the wall and met a turtle.",
        site = if (fromProvider == null) dev.divehub.api.models.DiveViewSite("site-1", "Lighthouse Reef") else null,
        waterType = waterType,
        waterMismatch = waterMismatch,
        position = null,
        recordings = recordings,
        cylinders = emptyList(),
        lead = emptyList(),
        leadTotalKg = null,
        weightingFeedback = null,
        suit = null,
        sac = null,
        sacMissing = null,
        participants = listOf(
            DiveViewParticipantsItem("diver-2", "Ada Byron", "buddy"),
            DiveViewParticipantsItem("diver-3", "Bo Chen", "guide"),
        ),
        verificationCodes = emptyList(),
    )

    private fun fromRecording(maxDepthM: Double = 20.0) = DiveViewFromRecording(
        startsAt = DiveViewFromRecordingStartsAt("2026-04-02T09:00:00Z", 7200),
        durationSeconds = 3_000.0,
        maxDepthM = maxDepthM,
        avgDepthM = 12.0,
        waterTemperatureC = 18.0,
    )

    private fun recording(
        breaks: List<DiveViewRecordingsItemSummarySurfaceBreaksItem>? = null,
    ) = DiveViewRecordingsItem(
        id = "r1",
        isPrimary = true,
        device = DiveViewRecordingsItemDevice("garmin", "Descent Mk3", "777"),
        startsAt = "2026-04-02T09:00:00Z",
        durationSeconds = 3_000.0,
        maxDepthM = 24.3,
        parser = "fit",
        partOf = null,
        summary = DiveViewRecordingsItemSummary(
            diveNumber = 57,
            diveMode = "open_circuit",
            waterType = "salt",
            surfaceBreaks = breaks,
        ),
        channels = listOf("depth", "temperature"),
    )

    /** A profile of ten minutes with a dive to 24 m, the same every run so the picture does not change by itself. */
    private fun samples(): RecordingSamplesView {
        val offsets = (0..75).map { it * 40_000 }
        val depths = offsets.mapIndexed { index, _ ->
            when {
                index <= 8 -> index * 2.8
                index <= 40 -> 22.4 + (index - 8) * 0.06
                else -> (24.3 - (index - 40) * 0.7).coerceAtLeast(0.0)
            }
        }
        return RecordingSamplesView(
            recordingId = "r1",
            series = listOf(
                RecordingSamplesViewSeriesItem("depth", offsets, depths),
                RecordingSamplesViewSeriesItem("temperature", offsets, offsets.map { 18.0 + it / 600_000.0 }),
            ),
        )
    }
}
