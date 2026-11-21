package dev.divehub.android.ui.logbook

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.unit.Density
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.DiveListViewCounts
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewDivesItemGasesItem
import dev.divehub.api.models.DiveListViewDivesItemParticipantsItem
import dev.divehub.api.models.DiveListViewDivesItemSite
import dev.divehub.api.models.DiveListViewMonthsItem
import dev.divehub.api.models.DiveListViewTotals
import dev.divehub.api.models.DiveProfileView
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.sync.SyncProgress
import kotlinx.serialization.json.JsonPrimitive
import java.util.TimeZone
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The contract-visible logbook states as pictures (ADR 0040): the "Show only" counts, a row without a Recording, and
 * the findings mark. They are rendered from a fixed state, so a drawing change shows up as a changed image.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class LogbookScreenshotTest {
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
    fun `filter counts`() {
        render(page(dives = listOf(reef, noRecording), filters = emptySet()))
        compose.onNodeWithText("Show only").assertIsDisplayed()
        compose.onNodeWithText("No recording").assertIsDisplayed()
        capture("logbook_filters.png")
    }

    @Test
    fun `a row without a recording`() {
        render(page(dives = listOf(noRecording), filters = setOf(LogbookFilter.NO_RECORDING)))
        compose.onNodeWithText("No recording").assertIsSelected()
        compose.onNodeWithContentDescription("No recording", substring = true, useUnmergedTree = true).assertIsDisplayed()
        compose.onNodeWithContentDescription("from SSI", substring = true, useUnmergedTree = true).assertIsDisplayed()
        capture("logbook_no_recording_row.png")
    }

    @Test
    fun `the findings mark`() {
        render(page(dives = listOf(withFindings), filters = setOf(LogbookFilter.WITH_FINDINGS)))
        compose.onNodeWithText("2 findings").assertIsDisplayed()
        capture("logbook_findings_mark.png")
    }

    @Test
    fun `the sync line says when the store was last filled`() {
        render(page(dives = listOf(reef), filters = emptySet()).copy(syncedAt = "2026-11-18T12:00:00Z"))
        compose.onNodeWithText("Last synced 18 Nov 2026, 13:00.").assertIsDisplayed()
        capture("logbook_synced.png")
    }

    @Test
    fun `while connected the sync line stays quiet`() {
        render(
            page(dives = listOf(reef), filters = emptySet()).copy(
                syncedAt = "2026-11-18T12:00:00Z",
                connected = true,
            ),
        )
        compose.onNodeWithText("Last synced", substring = true).assertDoesNotExist()
    }

    @Test
    fun `offline with a cache shows the stored dives and their age`() {
        render(page(dives = listOf(reef), filters = emptySet()).copy(syncedAt = "2026-11-18T12:00:00Z", offline = true))
        compose.onNodeWithText("No connection. Showing dives synced as of 18 Nov 2026, 13:00.").assertIsDisplayed()
        compose.onNodeWithText("Lighthouse Reef").assertIsDisplayed()
        capture("logbook_offline_cached.png")
    }

    @Test
    fun `offline before the first sync is an empty state, not a stale list`() {
        render(LogbookUiState(loading = false, offline = true))
        compose.onNodeWithText("No connection. Check your connection and try again.").assertIsDisplayed()
        capture("logbook_offline_empty.png")
    }

    @Test
    fun `the pre-sync loading state counts the dives being synced`() {
        render(
            LogbookUiState(
                loading = true,
                syncProgress = SyncProgress.EMPTY.copy(storedDives = 130, detailedDives = 42),
            ),
        )
        compose.onNodeWithText("Syncing your dives… 42 of 130").assertIsDisplayed()
        compose.mainClock.advanceTimeBy(250)
        capture("logbook_sync_progress.png")
    }

    @Test
    fun `a dive whose profiles are still syncing carries its own marker`() {
        render(page(dives = listOf(reef), filters = emptySet()).copy(profilePending = setOf(reef.id)))
        compose.onNodeWithContentDescription("Profile syncing", substring = true, useUnmergedTree = true).assertIsDisplayed()
        capture("logbook_profile_pending.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the logbook in dark`() {
        render(page(dives = listOf(reef), filters = emptySet()), darkTheme = true)
        compose.onNodeWithText("Lighthouse Reef").assertIsDisplayed()
        capture("logbook_dark.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the logbook at 320 dp and 200 percent`() {
        render(page(dives = crowded, filters = emptySet()), fontScale = 2f)
        compose.onNodeWithText("Tauchplatz an der Steilküste von Gozo").assertIsDisplayed()
        compose.onNodeWithText("Show only").assertIsDisplayed()
        capture("logbook_narrow_2x.png")
    }

    @Test
    fun `dive numbers and their figures line up down the list`() {
        render(page(dives = numbers, filters = emptySet()))
        compose.onNodeWithText("1000").assertIsDisplayed()
        capture("logbook_numbers.png")
    }

    @Test
    fun `an empty logbook offers the way on`() {
        render(emptyLogbook())
        compose.onNodeWithText("No dives here.").assertIsDisplayed()
        compose.onNodeWithText("Refresh").assertIsDisplayed()
        capture("logbook_empty.png")
    }

    @Test
    fun `a server error says what happened`() {
        render(LogbookUiState(loading = false, error = ErrorCodes.SERVER_ERROR))
        compose.onNodeWithText("The instance had a problem. Try again later.").assertIsDisplayed()
        capture("logbook_error.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the dark logbook keeps the selected filter and the findings mark`() {
        render(
            page(dives = listOf(withFindings), filters = setOf(LogbookFilter.WITH_FINDINGS)),
            darkTheme = true,
        )
        compose.onNodeWithText("2 findings").assertIsDisplayed()
        capture("logbook_dark_findings.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the dark error state`() {
        render(LogbookUiState(loading = false, error = ErrorCodes.SERVER_ERROR), darkTheme = true)
        compose.onNodeWithText("The instance had a problem. Try again later.").assertIsDisplayed()
        capture("logbook_dark_error.png")
    }

    @Test
    @Config(qualifiers = "de-rDE-w320dp-h891dp-xxhdpi")
    fun `the german logbook at 320 dp and 200 percent with crowded names`() {
        render(page(dives = crowded, filters = emptySet()), fontScale = 2f)
        compose.onNodeWithText("Nur anzeigen").assertIsDisplayed()
        compose.onNodeWithText("Tauchplatz an der Steilküste von Gozo").assertIsDisplayed()
        capture("logbook_de_narrow_2x.png")
    }

    @Test
    @Config(qualifiers = "en-rUS-w320dp-h891dp-xxhdpi")
    fun `the 320 dp 200 percent logbook reaches its pager`() {
        render(page(dives = numbers, filters = emptySet()), fontScale = 2f)
        compose.onNode(hasScrollToIndexAction()).performScrollToNode(hasText("Next page"))
        compose.onNodeWithText("Page 1 of 13").assertIsDisplayed()
        capture("logbook_narrow_2x_scrolled.png")
    }

    @Test
    fun `the refresh spinner is drawn only for the user gesture`() {
        render(page(dives = listOf(reef), filters = emptySet()).copy(refreshing = true))
        compose.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertExists()
    }

    @Test
    fun `a sync write does not put the refresh spinner on screen`() {
        render(page(dives = listOf(reef), filters = emptySet()))
        compose.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertDoesNotExist()
    }

    private fun render(state: LogbookUiState, darkTheme: Boolean = false, fontScale: Float? = null) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density, fontScale ?: density.fontScale),
                ) {
                    LogbookScreen(
                        state = state,
                        units = "metric",
                        onRefresh = {},
                        onRetry = {},
                        onShowAll = {},
                        onPage = {},
                        onToggleFilter = {},
                        onOpenDive = {},
                    )
                }
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun numberDive(
        id: String,
        number: Int,
        startsAt: String,
        site: String,
        maxDepthM: Double,
        durationSeconds: Double,
    ) = dive(
        id = id,
        number = number,
        startsAt = startsAt,
        site = site,
        maxDepthM = maxDepthM,
        durationSeconds = durationSeconds,
        profile = DiveProfileView(
            depthsM = listOf(0.0, maxDepthM / 2, maxDepthM, maxDepthM / 3, 0.0),
            spanSeconds = durationSeconds,
            ascentBands = emptyList(),
        ),
    )

    private fun emptyLogbook() = LogbookUiState(
        loading = false,
        data = DiveListView(
            dives = emptyList(),
            total = 0,
            counts = DiveListViewCounts(
                noRecording = 0,
                noSite = 0,
                withFindings = 0,
                notAtProvider = 0,
                changedSinceSent = 0,
            ),
            totals = DiveListViewTotals(dives = 0, durationSeconds = 0.0, deepestM = null, lastDiveAt = null),
            months = emptyList(),
        ),
    )

    private fun page(dives: List<DiveListViewDivesItem>, filters: Set<LogbookFilter>) = LogbookUiState(
        loading = false,
        page = 1,
        filters = filters,
        data = DiveListView(
            dives = dives,
            total = 312,
            counts = DiveListViewCounts(
                noRecording = 3,
                noSite = 1,
                withFindings = 2,
                notAtProvider = 5,
                changedSinceSent = 7,
            ),
            totals = DiveListViewTotals(
                dives = 312,
                durationSeconds = 648_000.0,
                deepestM = 40.2,
                lastDiveAt = "2026-04-02T09:00:00Z",
            ),
            months = listOf(
                DiveListViewMonthsItem("2026-04", 9, 27_000.0),
                DiveListViewMonthsItem("2026-03", 12, 36_000.0),
            ),
        ),
        providers = ProviderDirectory(names = mapOf("ssi" to "SSI"), connected = listOf("SSI")),
    )

    private val reef = dive(
        id = "reef",
        number = 42,
        startsAt = "2026-04-02T09:00:00Z",
        site = "Lighthouse Reef",
        maxDepthM = 24.3,
        gases = listOf(DiveListViewDivesItemGasesItem(o2 = 32.0, he = 0.0)),
        participants = listOf(
            DiveListViewDivesItemParticipantsItem("diver-2", "Ada Byron", "buddy"),
            DiveListViewDivesItemParticipantsItem("diver-3", "Bo Chen", "guide"),
        ),
        profile = DiveProfileView(
            depthsM = listOf(0.0, 5.0, 14.0, 24.3, 18.0, 9.0, 3.0, 0.0),
            spanSeconds = 3_000.0,
            ascentBands = listOf(
                listOf(JsonPrimitive(2_400.0), JsonPrimitive(2_700.0), JsonPrimitive(2)),
            ),
        ),
    )

    private val noRecording = dive(
        id = "typed",
        number = 9,
        startsAt = "2026-03-30T11:00:00Z",
        site = null,
        recordings = 0,
        fromProvider = "ssi",
        maxDepthM = 12.0,
        profile = null,
    )

    private val numbers = listOf(
        numberDive("nine", 9, "2026-04-02T09:00:00Z", "Lighthouse Reef", 8.4, 1_500.0),
        numberDive("forty-two", 42, "2026-04-10T10:30:00Z", "Blue Hole", 12.0, 3_000.0),
        numberDive("seventy-seven", 77, "2026-04-18T14:00:00Z", "Lighthouse Reef", 24.3, 4_200.0),
        dive(
            id = "thousand",
            number = 1_000,
            startsAt = "2026-04-26T16:15:00Z",
            site = "Blue Hole",
            maxDepthM = 40.2,
            durationSeconds = 5_400.0,
            participants = listOf(
                DiveListViewDivesItemParticipantsItem("diver-2", "Anneliese Bergström", "buddy"),
                DiveListViewDivesItemParticipantsItem("diver-3", "Maximilian von Hohenstein", "guide"),
                DiveListViewDivesItemParticipantsItem("diver-4", "Charlotte Wilhelmine", "buddy"),
                DiveListViewDivesItemParticipantsItem("diver-5", "Friedrich-Alexander", "buddy"),
            ),
            profile = DiveProfileView(
                depthsM = listOf(0.0, 20.0, 40.2, 18.0, 0.0),
                spanSeconds = 5_400.0,
                ascentBands = emptyList(),
            ),
        ),
    )

    private val crowded = listOf(
        dive(
            id = "crowded",
            number = 1_000,
            startsAt = "2026-04-02T09:00:00Z",
            site = "Tauchplatz an der Steilküste von Gozo",
            maxDepthM = 40.2,
            durationSeconds = 5_400.0,
            participants = listOf(
                DiveListViewDivesItemParticipantsItem("diver-2", "Anneliese Bergström", "buddy"),
                DiveListViewDivesItemParticipantsItem("diver-3", "Maximilian von Hohenstein", "guide"),
                DiveListViewDivesItemParticipantsItem("diver-4", "Charlotte Wilhelmine", "buddy"),
                DiveListViewDivesItemParticipantsItem("diver-5", "Friedrich-Alexander", "buddy"),
            ),
            profile = DiveProfileView(
                depthsM = listOf(0.0, 12.0, 28.0, 40.2, 20.0, 6.0, 0.0),
                spanSeconds = 5_400.0,
                ascentBands = emptyList(),
            ),
        ),
    )

    private val withFindings = dive(
        id = "findings",
        number = 77,
        startsAt = "2026-03-12T14:30:00Z",
        site = "Blue Hole",
        maxDepthM = 31.4,
        findings = 2,
        surfaceIntervalSeconds = 1_800.0,
        profile = DiveProfileView(
            depthsM = listOf(0.0, 10.0, 20.0, 31.4, 12.0, 0.0),
            spanSeconds = 2_400.0,
            ascentBands = emptyList(),
        ),
    )

    private fun dive(
        id: String,
        number: Int,
        startsAt: String,
        site: String?,
        maxDepthM: Double,
        recordings: Int = 1,
        durationSeconds: Double = 3_000.0,
        fromProvider: String? = null,
        findings: Int = 0,
        surfaceIntervalSeconds: Double? = null,
        gases: List<DiveListViewDivesItemGasesItem> = emptyList(),
        participants: List<DiveListViewDivesItemParticipantsItem> = emptyList(),
        profile: DiveProfileView?,
    ) = DiveListViewDivesItem(
        id = id,
        diverId = "diver",
        number = number,
        startsAt = startsAt,
        utcOffsetSeconds = 7_200,
        utcOffsetSource = "device",
        durationSeconds = durationSeconds,
        maxDepthM = maxDepthM,
        avgDepthM = maxDepthM / 2,
        site = site?.let { DiveListViewDivesItemSite(id = "site-$id", name = it) },
        findings = findings,
        recordings = recordings,
        fromProvider = fromProvider,
        gases = gases,
        surfaceIntervalSeconds = surfaceIntervalSeconds,
        participants = participants,
        waiting = emptyList(),
        profile = profile,
    )
}
