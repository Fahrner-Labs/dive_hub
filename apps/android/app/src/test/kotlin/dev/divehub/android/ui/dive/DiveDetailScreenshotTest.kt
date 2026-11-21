package dev.divehub.android.ui.dive

import android.provider.Settings
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
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
import androidx.compose.ui.unit.Density
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewCylindersItem
import dev.divehub.api.models.DiveViewCylindersItemGas
import dev.divehub.api.models.DiveViewFromRecording
import dev.divehub.api.models.DiveViewFromRecordingStartsAt
import dev.divehub.api.models.DiveViewLeadItem
import dev.divehub.api.models.DiveViewParticipantsItem
import dev.divehub.api.models.DiveViewSac
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemDevice
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewRecordingsItemSummaryGasesItem
import dev.divehub.api.models.DiveViewRecordingsItemSummarySurfaceBreaksItem
import dev.divehub.api.models.DiveViewSuit
import dev.divehub.api.models.DiveViewValues
import dev.divehub.api.models.DiveViewValuesStartsAt
import dev.divehub.api.models.DiveViewWaterMismatch
import dev.divehub.api.models.DiveViewWeightingFeedback
import dev.divehub.api.models.NeighboursView
import dev.divehub.api.models.NeighboursViewNext
import dev.divehub.api.models.NeighboursViewPrevious
import dev.divehub.api.models.RecordingSamplesView
import dev.divehub.api.models.RecordingSamplesViewSeriesItem
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.logbook.ProviderDirectory
import kotlinx.serialization.json.JsonPrimitive
import java.util.TimeZone
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
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
        // A still skeleton: the pulse is ADR 0064 motion, and a moving bar is not a stable golden.
        Settings.Global.putFloat(
            RuntimeEnvironment.getApplication().contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            0f,
        )
    }

    @After
    fun restoreZone() {
        TimeZone.setDefault(previousZone)
        Settings.Global.putFloat(
            RuntimeEnvironment.getApplication().contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f,
        )
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
        compose.onNodeWithText("Time zone from the dive’s position").assertIsDisplayed()
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
        compose.onNodeWithText(
            "The water type comes from the dive site. Choose a site to set it.",
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
    fun `swiping to a neighbour opens it and re-anchors on the new dive`() {
        val opened = mutableListOf<String>()
        val first = state(dive = dive())
        val second = state(
            dive = dive(number = 43).copy(id = "d2"),
            neighbours = NeighboursView(
                previous = NeighboursViewPrevious("d1"),
                next = NeighboursViewNext("d3"),
                position = 13,
                total = 312,
            ),
        )
        compose.setContent {
            var current by remember { mutableStateOf(first) }
            DiveHubTheme(darkTheme = false) {
                DiveDetailScreen(
                    state = current,
                    units = "metric",
                    onBack = {},
                    onRetry = {},
                    onOpenDive = { id ->
                        opened += id
                        if (id == "d2") current = second
                    },
                )
            }
        }
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.9f, height / 2f), end = Offset(width * 0.1f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertEquals(listOf("d2"), opened)
        compose.onNodeWithText("Dive 43").assertIsDisplayed()

        // The second swipe uses the new Dive's neighbours: it opens d3, not d2 again.
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.9f, height / 2f), end = Offset(width * 0.1f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertEquals(listOf("d2", "d3"), opened)
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
        compose.onNodeWithTag("dive_scroll").assertIsDisplayed()
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
        compose.onNodeWithTag("dive_scroll").assertIsDisplayed()
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
        compose.onNodeWithTag("dive_scroll").assertIsDisplayed()
    }

    @Test
    fun `a valid swipe still opens a neighbour after an end-stop springs back`() {
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
        // The start-of-list end-stop: swipe right, let it spring back.
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.1f, height / 2f), end = Offset(width * 0.9f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertTrue(opened.isEmpty())
        compose.onNodeWithTag("dive_scroll").assertIsDisplayed()

        // The collector survived the spring-back: the valid direction still opens the next Dive.
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.9f, height / 2f), end = Offset(width * 0.1f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertEquals(listOf("d2"), opened)
    }

    @Test
    fun `a swipe that interrupts the end-stop spring-back still re-springs onto the dive`() {
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
        compose.mainClock.autoAdvance = false
        // The start-of-list end-stop; stop the clock while its spring-back is under way.
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.1f, height / 2f), end = Offset(width * 0.9f, height / 2f), durationMillis = 200)
        }
        compose.mainClock.advanceTimeBy(250)
        // A second end-stop drag interrupts the spring-back and settles back on the same empty page.
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.1f, height / 2f), end = Offset(width * 0.9f, height / 2f), durationMillis = 200)
        }
        compose.mainClock.autoAdvance = true
        compose.waitForIdle()
        assertTrue(opened.isEmpty())
        compose.onNodeWithTag("dive_scroll").assertIsDisplayed()

        // The interrupted spring-back still leaves the gesture listener alive.
        compose.onNodeWithTag("dive_pager").performTouchInput {
            swipe(start = Offset(width * 0.9f, height / 2f), end = Offset(width * 0.1f, height / 2f), durationMillis = 200)
        }
        compose.waitForIdle()
        assertEquals(listOf("d2"), opened)
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
        compose.onNodeWithContentDescription("Back").assertIsEnabled()
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
        compose.onNodeWithContentDescription("Back").performClick()
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
    fun `while connected the sync line stays quiet`() {
        render(state(dive = dive(), syncedAt = "2026-11-18T12:00:00Z", connected = true))
        compose.onNodeWithText("Last synced", substring = true).assertDoesNotExist()
        capture("dive_connected.png")
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
        compose.onNodeWithText("No connection").assertIsDisplayed()
        compose.onNodeWithText("Check your connection and try again.").assertIsDisplayed()
        capture("dive_offline_empty.png")
    }

    @Test
    fun `a server error says what happened and offers a retry`() {
        render(DiveDetailUiState(loading = false, error = ErrorCodes.SERVER_ERROR))
        compose.onNodeWithText("Something went wrong").assertIsDisplayed()
        compose.onNodeWithText("The instance had a problem. Try again later.").assertIsDisplayed()
        capture("dive_error.png")
    }

    @Test
    fun `a missing dive says it is not there`() {
        render(DiveDetailUiState(loading = false, error = "not_found"))
        compose.onNodeWithText("Dive not found").assertIsDisplayed()
        compose.onNodeWithText("This dive doesn’t exist or isn’t yours.").assertIsDisplayed()
    }

    @Test
    fun `the first load is the page's skeleton, not a spinner`() {
        render(DiveDetailUiState(loading = true))
        compose.onNodeWithContentDescription("Loading…").assertExists()
        compose.mainClock.advanceTimeBy(250)
        capture("dive_loading.png")
    }

    @Test
    fun `the page skeleton waits 150 ms so a fast load does not flash`() {
        compose.mainClock.autoAdvance = false
        render(DiveDetailUiState(loading = true))
        compose.onNodeWithTag("dive_skeleton_bars").assertDoesNotExist()
        compose.mainClock.advanceTimeBy(149)
        compose.onNodeWithTag("dive_skeleton_bars").assertDoesNotExist()
        compose.mainClock.advanceTimeBy(2)
        compose.onNodeWithTag("dive_skeleton_bars").assertExists()
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
        compose.onNodeWithText("Try again").assertIsDisplayed()
        capture("dive_profile_failed.png")
    }

    @Test
    fun `the synced gear, weighting and notes are shown`() {
        render(
            state(
                dive = dive(
                    notes = "Drifted along the wall and met a turtle.",
                    cylinders = listOf(
                        DiveViewCylindersItem(
                            volumeL = 12.0,
                            workingPressureBar = 232.0,
                            material = "steel",
                            gas = DiveViewCylindersItemGas(o2 = 21.0, he = 0.0),
                            startPressureBar = 200.0,
                            endPressureBar = 50.0,
                            fromPod = false,
                            series = null,
                        ),
                    ),
                    sac = DiveViewSac(litresPerMinute = 16.5, barPerMinute = 11.0),
                    suit = DiveViewSuit(type = "wetsuit", thicknessMm = 5.0, hood = JsonPrimitive(true), undergarment = null),
                    lead = listOf(
                        DiveViewLeadItem(amountKg = 4.0, placement = "belt"),
                        DiveViewLeadItem(amountKg = 1.0, placement = "trim"),
                    ),
                    leadTotalKg = 5.0,
                    weightingFeedback = DiveViewWeightingFeedback(felt = "too_heavy", amountKg = 1.0),
                ),
            ),
        )
        val list = compose.onNodeWithTag("dive_scroll")
        list.performScrollToNode(hasText("Cylinder 1"))
        compose.onNodeWithText("12 L · Steel · 232 bar working pressure · Air").assertIsDisplayed()
        compose.onNodeWithText("200 bar → 50 bar (150 bar used)").assertIsDisplayed()
        compose.onNodeWithText("16.5 L/min").assertIsDisplayed()
        compose.onNodeWithText("11.0 bar/min").assertIsDisplayed()
        list.performScrollToNode(hasText("Weighting"))
        compose.onNodeWithText("Wetsuit, 5 mm, with hood").assertIsDisplayed()
        compose.onNodeWithText("4.0 kg on a belt\n1.0 kg trim\nTotal 5.0 kg").assertIsDisplayed()
        compose.onNodeWithText("Too heavy by 1.0 kg").assertIsDisplayed()
        list.performScrollToNode(hasText("Notes"))
        compose.onNodeWithText("Drifted along the wall and met a turtle.").assertIsDisplayed()
        capture("dive_gear_weighting_notes.png")
    }

    @Test
    fun `the SAC rate is whole psi and a decimal in bar`() {
        render(
            state(
                dive = dive(
                    cylinders = listOf(
                        DiveViewCylindersItem(
                            volumeL = 12.0,
                            workingPressureBar = 232.0,
                            material = "steel",
                            gas = DiveViewCylindersItemGas(o2 = 21.0, he = 0.0),
                            startPressureBar = 200.0,
                            endPressureBar = 50.0,
                            fromPod = false,
                            series = null,
                        ),
                    ),
                    sac = DiveViewSac(litresPerMinute = 16.5, barPerMinute = 11.0),
                ),
            ),
            units = "imperial",
        )
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("160 psi/min"))
        compose.onNodeWithText("160 psi/min").assertIsDisplayed()
    }

    @Test
    fun `a too short dive says why there is no SAC without repeating the label`() {
        render(
            state(
                dive = dive(
                    cylinders = listOf(
                        DiveViewCylindersItem(
                            volumeL = 12.0,
                            workingPressureBar = 232.0,
                            material = "steel",
                            gas = DiveViewCylindersItemGas(o2 = 21.0, he = 0.0),
                            startPressureBar = 200.0,
                            endPressureBar = 50.0,
                            fromPod = false,
                            series = null,
                        ),
                    ),
                    durationSeconds = 600.0,
                    sacMissing = "too_short",
                ),
            ),
        )
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("The dive is shorter than 15 minutes."))
        compose.onNodeWithText("SAC").assertTextEquals("SAC", "The dive is shorter than 15 minutes.")
        compose.onNodeWithText("The dive is shorter than 15 minutes.").assertIsDisplayed()
        capture("dive_sac_missing.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the dive page in the dark scheme`() {
        render(
            state(dive = dive(notes = "Drifted along the wall and met a turtle.")),
            darkTheme = true,
        )
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        capture("dive_dark.png")
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasTestTag("dive_profile_chart"))
        capture("dive_profile_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the dive page at 320 dp and 200 percent`() {
        render(
            state(
                dive = dive(
                    waterMismatch = DiveViewWaterMismatch(computer = "salt", site = "fresh", depthPercent = -2.0),
                ),
            ),
            fontScale = 2f,
        )
        compose.onNodeWithText("Dive 42").assertIsDisplayed()
        capture("dive_narrow_2x.png")
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Its depths read about 2 % shallow."))
        capture("dive_narrow_2x_water.png")
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasTestTag("dive_profile_chart"))
        capture("dive_narrow_2x_profile.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi", fontScale = 2f)
    fun `the german dive page at 320 dp and 200 percent with crowded data`() {
        render(
            state(
                dive = dive(
                    notes = "Entlang der Steilwand gedriftet und eine Schildkröte getroffen.",
                    participants = listOf(
                        DiveViewParticipantsItem("diver-2", "Anastasia von Hohenberg", "buddy"),
                        DiveViewParticipantsItem("diver-3", "Bo Chen", "guide"),
                    ),
                ),
            ),
            fontScale = 2f,
        )
        compose.onNodeWithText("Tauchgang 42").assertIsDisplayed()
        capture("dive_de_narrow_2x.png")
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Aufzeichnungen"))
        capture("dive_de_narrow_2x_recordings.png")
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Anastasia von Hohenberg", substring = true))
        compose.onNodeWithText("Anastasia von Hohenberg", substring = true).assertIsDisplayed()
        capture("dive_de_narrow_2x_participants.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the profile table is readable at 320 dp and 200 percent`() {
        render(state(dive = dive()), fontScale = 2f)
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Profile as a table"))
        compose.onNodeWithText("Profile as a table").performClick()
        compose.onNodeWithTag("dive_profile_table").assertExists()
        compose.onNodeWithTag("dive_scroll").performTouchInput {
            swipe(start = Offset(width / 2f, height * 0.8f), end = Offset(width / 2f, height * 0.2f), durationMillis = 200)
        }
        capture("dive_table_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the german profile table is readable at 320 dp and 200 percent`() {
        render(state(dive = dive()), fontScale = 2f)
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Profil als Tabelle"))
        compose.onNodeWithText("Profil als Tabelle").performClick()
        compose.onNodeWithTag("dive_profile_table").assertExists()
        compose.onNodeWithTag("dive_scroll").performTouchInput {
            swipe(start = Offset(width / 2f, height * 0.8f), end = Offset(width / 2f, height * 0.2f), durationMillis = 200)
        }
        capture("dive_table_de_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-night-xxhdpi")
    fun `the dark dive page at 320 dp and 200 percent`() {
        render(
            state(dive = dive(notes = "Drifted along the wall and met a turtle.")),
            darkTheme = true,
            fontScale = 2f,
        )
        capture("dive_dark_narrow_2x.png")
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasTestTag("dive_profile_chart"))
        capture("dive_dark_narrow_2x_profile.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w360dp-h891dp-xxhdpi")
    fun `the dive page at 360 dp`() {
        render(state(dive = dive()))
        capture("dive_360.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w390dp-h891dp-xxhdpi")
    fun `the dive page at 390 dp`() {
        render(state(dive = dive()))
        capture("dive_390.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the offline empty state at 320 dp and 200 percent`() {
        render(DiveDetailUiState(loading = false, offline = true), fontScale = 2f)
        compose.onNodeWithText("No connection").assertIsDisplayed()
        capture("dive_offline_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the error empty state at 320 dp and 200 percent`() {
        render(DiveDetailUiState(loading = false, error = ErrorCodes.SERVER_ERROR), fontScale = 2f)
        compose.onNodeWithText("Something went wrong").assertIsDisplayed()
        capture("dive_error_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the first load in the dark`() {
        render(DiveDetailUiState(loading = true), darkTheme = true)
        compose.onNodeWithContentDescription("Loading…").assertExists()
        compose.mainClock.advanceTimeBy(250)
        capture("dive_loading_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the first load at 320 dp and 200 percent`() {
        render(DiveDetailUiState(loading = true), fontScale = 2f)
        compose.onNodeWithContentDescription("Loading…").assertExists()
        compose.mainClock.advanceTimeBy(250)
        capture("dive_loading_narrow_2x.png")
    }

    @Test
    fun `the primary recording is marked`() {
        render(state(dive = dive()))
        compose.onNodeWithTag("dive_scroll").performScrollToNode(hasText("Primary"))
        compose.onNodeWithText("Primary").assertIsDisplayed()
    }

    private fun render(
        state: DiveDetailUiState,
        units: String = "metric",
        onBack: () -> Unit = {},
        onOpenDive: (String) -> Unit = {},
        darkTheme: Boolean = false,
        fontScale: Float? = null,
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, fontScale ?: density.fontScale),
                ) {
                    DiveDetailScreen(
                        state = state,
                        units = units,
                        onBack = onBack,
                        onRetry = {},
                        onOpenDive = onOpenDive,
                    )
                }
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
        connected: Boolean = false,
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
        connected = connected,
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
        durationSeconds: Double = 3_000.0,
        notes: String? = null,
        cylinders: List<DiveViewCylindersItem> = emptyList(),
        sac: DiveViewSac? = null,
        sacMissing: String? = null,
        suit: DiveViewSuit? = null,
        lead: List<DiveViewLeadItem> = emptyList(),
        leadTotalKg: Double? = null,
        weightingFeedback: DiveViewWeightingFeedback? = null,
        participants: List<DiveViewParticipantsItem> = listOf(
            DiveViewParticipantsItem("diver-2", "Ada Byron", "buddy"),
            DiveViewParticipantsItem("diver-3", "Bo Chen", "guide"),
        ),
    ) = DiveView(
        id = "d1",
        diverId = "diver",
        version = 3,
        values = DiveViewValues(
            number = number,
            startsAt = DiveViewValuesStartsAt("2026-04-02T09:00:00Z", 7200),
            durationSeconds = durationSeconds,
            maxDepthM = 24.3,
            avgDepthM = 12.0,
            waterTemperatureC = 18.0,
        ),
        overrides = overrides,
        utcOffsetSource = source,
        fromProvider = fromProvider,
        fromRecording = fromRecording,
        notes = notes,
        site = if (fromProvider == null) dev.divehub.api.models.DiveViewSite("site-1", "Lighthouse Reef") else null,
        waterType = waterType,
        waterMismatch = waterMismatch,
        position = null,
        recordings = recordings,
        cylinders = cylinders,
        lead = lead,
        leadTotalKg = leadTotalKg,
        weightingFeedback = weightingFeedback,
        suit = suit,
        sac = sac,
        sacMissing = sacMissing,
        participants = participants,
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
