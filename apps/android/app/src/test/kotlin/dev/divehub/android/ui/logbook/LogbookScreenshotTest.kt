package dev.divehub.android.ui.logbook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.ProgressBarRangeInfo
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
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.ProviderDirectory
import dev.divehub.shared.logbook.toggleFilter
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
        render(page(dives = listOf(reef), filters = emptySet()))
        compose.onNodeWithText("No recording · 3").assertDoesNotExist()
        compose.onNodeWithContentDescription("Filters").performClick()
        compose.onNodeWithText("No recording · 3").assertIsDisplayed()
        capture("logbook_filters.png")
    }

    @Test
    fun `an applied filter is visible while the sheet is closed`() {
        render(
            page(
                dives = listOf(withFindings),
                filters = setOf(LogbookFilter.WITH_FINDINGS, LogbookFilter.CHANGED_SINCE_SENT),
            ),
        )
        compose.onNodeWithText("With findings · 2").assertDoesNotExist()
        compose.onNodeWithContentDescription("Filters, 2 active").assertIsDisplayed()
        capture("logbook_filter_badge.png")
    }

    @Test
    fun `an applied filter is checked in the sheet`() {
        render(page(dives = listOf(noRecording), filters = setOf(LogbookFilter.NO_RECORDING)))
        compose.onNodeWithContentDescription("Filters, 1 active").performClick()
        compose.onNodeWithText("No recording · 3").assertIsSelected()
        capture("logbook_filters_applied.png")
    }

    @Test
    fun `the offline filter note lives in the sheet`() {
        render(
            page(dives = listOf(reef), filters = setOf(LogbookFilter.CHANGED_SINCE_SENT))
                .copy(filterUnavailable = true),
        )
        val note = "This filter needs a connection. Showing the dives synced on this device."
        compose.onNodeWithText(note).assertDoesNotExist()
        compose.onNodeWithContentDescription("Filters, 1 active").performClick()
        compose.onNodeWithText(note).assertIsDisplayed()
        capture("logbook_filters_offline.png")
    }

    @Test
    @Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-night-xxhdpi")
    fun `the action and the sheet in the dark scheme`() {
        val state = page(
            dives = listOf(withFindings),
            filters = setOf(LogbookFilter.WITH_FINDINGS, LogbookFilter.CHANGED_SINCE_SENT),
        )
        render(state, darkTheme = true)
        compose.onNodeWithContentDescription("Filters, 2 active").assertIsDisplayed()
        capture("logbook_filter_badge_dark.png")
        compose.onNodeWithContentDescription("Filters, 2 active").performClick()
        compose.onNodeWithText("With findings · 2").assertIsSelected()
        capture("logbook_filters_dark.png")
    }

    @Test
    @Config(sdk = [36], qualifiers = "de-rDE-w320dp-h640dp-xxhdpi", fontScale = 2f)
    fun `the sheet at 320 dp and 200 percent text in German`() {
        render(page(dives = listOf(reef), filters = setOf(LogbookFilter.CHANGED_SINCE_SENT)))
        compose.onNodeWithContentDescription("Filter, 1 aktiv").assertIsDisplayed()
        capture("logbook_filter_badge_narrow_de.png")
        compose.onNodeWithContentDescription("Filter, 1 aktiv").performClick()
        val chip = compose.onNodeWithText("Seit dem Senden geändert · 7")
        chip.performScrollTo().assertIsDisplayed().assertIsSelected()
        chip.assertHeightIsAtLeast(48.dp)
        capture("logbook_filters_narrow_de.png")
    }

    @Test
    fun `the sheet stays open while a filter read clears the page`() {
        var state by mutableStateOf(page(dives = listOf(reef), filters = emptySet()))
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                LogbookScreen(
                    state = state,
                    units = "metric",
                    onRefresh = {},
                    onRetry = {},
                    onShowAll = {},
                    onPage = {},
                    onToggleFilter = { filter ->
                        state = state.copy(
                            loading = false,
                            data = null,
                            filters = toggleFilter(state.filters, filter),
                        )
                    },
                    onOpenDive = {},
                )
            }
        }
        compose.onNodeWithContentDescription("Filters").performClick()
        compose.onNodeWithText("No recording · 3").performClick()
        compose.onNodeWithText("Show only").assertIsDisplayed()
        compose.onNodeWithContentDescription("Filters, 1 active").assertExists()
    }

    @Test
    fun `a row without a recording`() {
        render(page(dives = listOf(noRecording), filters = setOf(LogbookFilter.NO_RECORDING)))
        compose.onNodeWithText("No recording").assertIsDisplayed()
        compose.onNodeWithText("from SSI").assertIsDisplayed()
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
        capture("logbook_connected.png")
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
        capture("logbook_sync_progress.png")
    }

    @Test
    fun `a dive whose profiles are still syncing carries its own marker`() {
        render(page(dives = listOf(reef), filters = emptySet()).copy(profilePending = setOf(reef.id)))
        compose.onNodeWithText("Profile syncing").assertIsDisplayed()
        capture("logbook_profile_pending.png")
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

    private fun render(state: LogbookUiState, darkTheme: Boolean = false) {
        compose.setContent {
            DiveHubTheme(darkTheme = darkTheme) {
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

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun page(dives: List<DiveListViewDivesItem>, filters: Set<LogbookFilter>): LogbookUiState {
        val data = DiveListView(
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
        )
        return LogbookUiState(
            loading = false,
            page = 1,
            filters = filters,
            data = data,
            lastCounts = data.counts,
            providers = ProviderDirectory(names = mapOf("ssi" to "SSI"), connected = listOf("SSI")),
        )
    }

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
        durationSeconds = 3_000.0,
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
