package dev.divehub.android.ui.dive

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.click
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.swipe
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewFromRecording
import dev.divehub.api.models.DiveViewFromRecordingStartsAt
import dev.divehub.api.models.DiveViewParticipantsItem
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemDevice
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewRecordingsItemSummaryGasesItem
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
import java.util.TimeZone
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
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
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        compose.onNodeWithContentDescription("Previous dive (unavailable)").assertIsDisplayed()
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Surface break 4 min"))
        compose.onNodeWithText("Surface break 4 min").assertIsDisplayed()
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Surface break 2 min"))
        compose.onNodeWithText("Surface break 2 min").assertIsDisplayed()
        capture("dive_surface_breaks.png")
    }

    @Test
    fun `scrubbing the profile reads the values at the touched position`() {
        render(state(dive = dive()))
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasTestTag("dive_profile_chart"))
        val before = compose.onNodeWithTag("dive_profile_chart").getBoundsInRoot()
        compose.onNodeWithTag("dive_profile_chart").performTouchInput {
            click(Offset(width * 0.25f, height / 2f))
        }
        assertEquals(before, compose.onNodeWithTag("dive_profile_chart").getBoundsInRoot())
        compose.onNodeWithText("12:40").assertIsDisplayed()
        compose.onNodeWithText("23.1 m").assertIsDisplayed()
        compose.onNodeWithText("19 °C").assertIsDisplayed()
        compose.onNodeWithText("162 bar").assertIsDisplayed()
        capture("dive_profile_scrub.png")
    }

    @Test
    fun `dragging across the profile moves the readout instead of switching dives`() {
        val opened = mutableListOf<String>()
        render(state(dive = dive()), onOpenDive = opened::add)
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasTestTag("dive_profile_chart"))
        compose.onNodeWithTag("dive_profile_chart").performTouchInput {
            swipe(start = Offset(0f, height / 2f), end = Offset(width.toFloat(), height / 2f), durationMillis = 200)
        }
        assertTrue(opened.isEmpty())
        compose.onNodeWithText("50:00").assertIsDisplayed()
        compose.onNodeWithText("0.0 m").assertIsDisplayed()
        compose.onNodeWithText("50 bar").assertIsDisplayed()
    }

    @Test
    fun `swiping to a neighbour opens it and re-anchors the pager on the current dive`() {
        val opened = mutableListOf<String>()
        render(state(dive = dive()), onOpenDive = opened::add)
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.9f, height / 2f), end = Offset(width * 0.1f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertEquals(listOf("d2"), opened)
        compose.onNodeWithText("Dive 42").assertIsDisplayed()

        // A second swipe in the same direction only works when the pager is back on the current dive.
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.9f, height / 2f), end = Offset(width * 0.1f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertEquals(listOf("d2", "d2"), opened)
    }

    @Test
    fun `swiping the other way opens the previous dive`() {
        val opened = mutableListOf<String>()
        render(state(dive = dive()), onOpenDive = opened::add)
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.1f, height / 2f), end = Offset(width * 0.9f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertEquals(listOf("d0"), opened)
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
    }

    @Test
    fun `swiping before the first dive springs back without opening one`() {
        val opened = mutableListOf<String>()
        render(
            state(
                dive = dive(),
                neighbours = NeighboursView(
                    previous = null,
                    next = NeighboursViewNext("d2"),
                    position = 1,
                    total = 312,
                ),
            ),
            onOpenDive = opened::add,
        )
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.1f, height / 2f), end = Offset(width * 0.9f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertTrue(opened.isEmpty())
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
    }

    @Test
    fun `swiping past the last dive springs back without opening one`() {
        val opened = mutableListOf<String>()
        render(
            state(
                dive = dive(),
                neighbours = NeighboursView(
                    previous = NeighboursViewPrevious("d0"),
                    next = null,
                    position = 312,
                    total = 312,
                ),
            ),
            onOpenDive = opened::add,
        )
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.9f, height / 2f), end = Offset(width * 0.1f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertTrue(opened.isEmpty())
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
    }

    @Test
    fun `the header's arrows open the neighbouring dives`() {
        val opened = mutableListOf<String>()
        render(state(dive = dive()), onOpenDive = opened::add)
        compose.onNodeWithContentDescription("Next dive").performClick()
        compose.onNodeWithContentDescription("Previous dive").performClick()
        assertEquals(listOf("d2", "d0"), opened)
    }

    @Test
    fun `the header's arrows are disabled at the ends and the back arrow is there`() {
        render(
            state(
                dive = dive(),
                neighbours = NeighboursView(previous = null, next = null, position = 1, total = 1),
            ),
        )
        compose.onNodeWithContentDescription("Previous dive (unavailable)").assertIsNotEnabled()
        compose.onNodeWithContentDescription("Next dive (unavailable)").assertIsNotEnabled()
        compose.onNodeWithContentDescription("Logbook").assertIsEnabled()
    }

    @Test
    fun `a dive outside the list shows no neighbour arrows`() {
        render(
            state(
                dive = dive(),
                neighbours = NeighboursView(previous = null, next = null, position = null, total = 0),
            ),
        )
        compose.onNodeWithContentDescription("Previous dive (unavailable)").assertDoesNotExist()
        compose.onNodeWithContentDescription("Next dive (unavailable)").assertDoesNotExist()
    }

    @Test
    fun `the header's back arrow returns to the logbook`() {
        var backs = 0
        render(state(dive = dive()), onBack = { backs++ })
        compose.onNodeWithContentDescription("Logbook").performClick()
        assertEquals(1, backs)
    }

    @Test
    fun `the profile table is the text alternative and includes tank pressure`() {
        render(state(dive = dive()))
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Profile as a table"))
        compose.onNodeWithText("Profile as a table").performClick()
        compose.onAllNodesWithText("EAN32, 12 L").onLast().assertExists()
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("170 bar"))
        compose.onNodeWithText("170 bar").assertIsDisplayed()
    }

    @Test
    fun `TalkBack can move the scrub and read the values`() {
        render(state(dive = dive()))
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasTestTag("dive_profile_chart"))
        compose.onNodeWithTag("dive_profile_chart").performSemanticsAction(SemanticsActions.SetProgress) { it(0.25f) }
        compose.onNodeWithText("12:40").assertIsDisplayed()
        compose.onNodeWithText("162 bar").assertIsDisplayed()
    }

    @Test
    fun `several tanks are numbered and keep the size they hold`() {
        render(
            state(
                dive = dive(
                    recordings = listOf(
                        recording(
                            gases = listOf(
                                gas(o2 = 32.0, volumeL = 12.0),
                                gas(o2 = 50.0, volumeL = 11.1, startPressureBar = 150.0, endPressureBar = 80.0),
                            ),
                        ),
                    ),
                ),
                profileSamples = samples(twoTanks = true),
            ),
        )
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Tank 2 (EAN50, 11.1 L)"))
        compose.onNodeWithText("Tank 1 (EAN32, 12 L)").assertIsDisplayed()
        compose.onNodeWithText("Tank 2 (EAN50, 11.1 L)").assertIsDisplayed()
    }

    @Test
    fun `the sync line says when the store was last filled`() {
        render(state(dive = dive(), syncedAt = "2026-11-18T12:00:00Z"))
        compose.onNodeWithText("Last synced 18 Nov 2026, 13:00.").assertIsDisplayed()
        capture("dive_synced.png")
    }

    @Test
    fun `offline with a cached dive shows it and its age`() {
        render(state(dive = dive(), syncedAt = "2026-11-18T12:00:00Z", offline = true))
        compose.onNodeWithText("No connection. Showing this dive synced as of 18 Nov 2026, 13:00.").assertIsDisplayed()
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        capture("dive_offline_cached.png")
    }

    @Test
    fun `offline before the first sync is an empty state, not a stale dive`() {
        render(DiveDetailUiState(loading = false, offline = true))
        compose.onNodeWithText("No connection. Check your connection and try again.").assertIsDisplayed()
        capture("dive_offline_empty.png")
    }

    @Test
    fun `a dive whose profiles are still syncing says so`() {
        render(state(dive = dive(), profileSamples = null, samplesPending = true))
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("The depth profile is still syncing; it appears when the sync finishes."))
        compose.onNodeWithText("The depth profile is still syncing; it appears when the sync finishes.").assertIsDisplayed()
        capture("dive_profile_pending.png")
    }

    @Test
    fun `a failed profile leaves the rest of the page standing`() {
        render(state(dive = dive(), profileSamples = null, samplesError = "internal_error"))
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("The instance had a problem. Try again later."))
        compose.onNodeWithText("The instance had a problem. Try again later.").assertIsDisplayed()
        capture("dive_profile_failed.png")
    }

    private fun render(
        state: DiveDetailUiState,
        onBack: () -> Unit = {},
        onOpenDive: (String) -> Unit = {},
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                DiveDetailScreen(
                    state = state,
                    units = "metric",
                    onBack = onBack,
                    onRetry = {},
                    onOpenDive = onOpenDive,
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
        samplesPending: Boolean = false,
        syncedAt: String? = null,
        offline: Boolean = false,
    ) = DiveDetailUiState(
        diveId = dive.id,
        loading = false,
        dive = dive,
        providers = ProviderDirectory(names = mapOf("ssi" to "SSI")),
        neighbours = neighbours,
        samples = profileSamples,
        samplesError = samplesError,
        samplesPending = samplesPending,
        syncedAt = syncedAt,
        offline = offline,
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
        gases: List<DiveViewRecordingsItemSummaryGasesItem> = listOf(gas()),
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
            gases = gases,
            surfaceBreaks = breaks,
        ),
        channels = listOf("depth", "temperature", "tankPressure") +
            if (gases.size > 1) listOf("tankPressure:2") else emptyList(),
    )

    private fun gas(
        o2: Double = 32.0,
        volumeL: Double = 12.0,
        startPressureBar: Double = 200.0,
        endPressureBar: Double = 50.0,
    ) = DiveViewRecordingsItemSummaryGasesItem(
        o2 = o2,
        he = 0.0,
        tankVolumeL = volumeL,
        startPressureBar = startPressureBar,
        endPressureBar = endPressureBar,
    )

    /** A profile of ten minutes with a dive to 24 m, the same every run so the picture does not change by itself. */
    private fun samples(twoTanks: Boolean = false): RecordingSamplesView {
        val offsets = (0..75).map { it * 40_000 }
        val depths = offsets.mapIndexed { index, _ ->
            when {
                index <= 8 -> index * 2.8
                index <= 40 -> 22.4 + (index - 8) * 0.06
                else -> (24.3 - (index - 40) * 0.7).coerceAtLeast(0.0)
            }
        }
        val series = mutableListOf(
            RecordingSamplesViewSeriesItem("depth", offsets, depths),
            RecordingSamplesViewSeriesItem("temperature", offsets, offsets.map { 18.0 + it / 600_000.0 }),
            RecordingSamplesViewSeriesItem("tankPressure", offsets, offsets.mapIndexed { index, _ -> 200.0 - index * 2.0 }),
        )
        if (twoTanks) {
            series += RecordingSamplesViewSeriesItem("tankPressure:2", offsets, offsets.mapIndexed { index, _ -> 150.0 - index })
        }
        return RecordingSamplesView(
            recordingId = "r1",
            series = series,
        )
    }
}
