package dev.divehub.android.ui.logbook

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.android.ui.Spacing
import dev.divehub.api.models.DiveListView
import dev.divehub.api.models.DiveListViewCounts
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewDivesItemSite
import dev.divehub.api.models.DiveListViewMonthsItem
import dev.divehub.api.models.DiveListViewTotals
import dev.divehub.shared.logbook.ProviderDirectory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The continuous logbook list (FAH-341): month headings separate the groups, a Dive the User came back from is scrolled
 * into view, and only the server-answered filters keep a pager.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class LogbookScreenTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `dives stand under their month headings`() {
        render(
            state(
                listOf(
                    dive("apr", "2026-04-02T09:00:00Z", "April Reef"),
                    dive("mar", "2026-03-02T09:00:00Z", "March Reef"),
                ),
            ),
        )

        compose.onNodeWithText("April 2026").assertIsDisplayed()
        compose.onNodeWithText("March 2026").assertIsDisplayed()
        compose.onNodeWithText("April Reef").assertIsDisplayed()
        compose.onNodeWithText("March Reef").assertIsDisplayed()
    }

    @Test
    fun `a dive returned to is scrolled into view under its month`() {
        var handled = false
        render(
            state(dives = fortyDives()),
            focusDiveId = "d31",
            onFocusHandled = { handled = true },
        )
        compose.waitForIdle()

        compose.onNodeWithText("Site 31").assertIsDisplayed()
        // The month heading sticks to the top, so the row still says which month it belongs to...
        val header = compose.onNodeWithText("March 2026").getBoundsInRoot()
        // ...and the returned row starts below it instead of under the pinned heading.
        val row = compose.onNodeWithText("Site 31").getBoundsInRoot()
        assertTrue("row top ${row.top} above header bottom ${header.bottom}", row.top >= header.bottom)
        assertTrue(handled)
    }

    @Test
    fun `the list scroll position survives a saved-state restore`() {
        val restoration = StateRestorationTester(compose)
        val state = state(dives = fortyDives())
        restoration.setContent {
            DiveHubTheme(darkTheme = false) {
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
        compose.waitForIdle()
        compose.onNode(hasScrollAction()).performScrollToIndex(20)
        compose.waitForIdle()
        compose.onNodeWithText("Site 1").assertDoesNotExist()

        restoration.emulateSavedInstanceStateRestore()
        compose.waitForIdle()

        compose.onNodeWithText("Site 1").assertDoesNotExist()
    }

    @Test
    fun `a focus the filter does not show is cleared without a scroll`() {
        var handled = false
        render(
            state(listOf(dive("apr", "2026-04-02T09:00:00Z", "April Reef"))),
            focusDiveId = "missing",
            onFocusHandled = { handled = true },
        )
        compose.waitForIdle()

        assertTrue(handled)
    }

    @Test
    fun `a server-answered filter returns to the dive's page`() {
        var handled = false
        var requested: Int? = null
        render(
            state(listOf(dive("apr", "2026-04-02T09:00:00Z", "April Reef")), total = 60, paged = true),
            focusDiveId = "on-page-3",
            focusPage = 3,
            onFocusHandled = { handled = true },
            onPage = { requested = it },
        )
        compose.waitForIdle()

        assertEquals(3, requested)
        assertTrue(handled)
    }

    @Test
    fun `the stored list has no pager`() {
        render(state(listOf(dive("apr", "2026-04-02T09:00:00Z", "April Reef")), total = 60))

        compose.onNodeWithText("Next page").assertDoesNotExist()
    }

    @Test
    fun `a server-answered filter keeps its pager`() {
        render(state(listOf(dive("apr", "2026-04-02T09:00:00Z", "April Reef")), total = 60, paged = true))

        compose.onNodeWithText("Next page").assertIsDisplayed()
        compose.onNodeWithText("Page 1 of 3").assertIsDisplayed()
    }

    @Test
    fun `the pager's three labels keep a full step apart`() {
        render(state(listOf(dive("apr", "2026-04-02T09:00:00Z", "April Reef")), total = 60, paged = true))

        val previous = compose.onNodeWithText("Previous page").getBoundsInRoot()
        val page = compose.onNodeWithText("Page 1 of 3").getBoundsInRoot()
        val next = compose.onNodeWithText("Next page").getBoundsInRoot()

        // The quiet action carries no horizontal padding, so the row owns the separation (FAH-427).
        assertEquals(Spacing.s4.value, (page.left - previous.right).value, 1f)
        assertEquals(Spacing.s4.value, (next.left - page.right).value, 1f)
    }

    private fun render(
        state: LogbookUiState,
        focusDiveId: String? = null,
        focusPage: Int? = null,
        onFocusHandled: () -> Unit = {},
        onPage: (Int) -> Unit = {},
    ) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                LogbookScreen(
                    state = state,
                    units = "metric",
                    onRefresh = {},
                    onRetry = {},
                    onShowAll = {},
                    onPage = onPage,
                    onToggleFilter = {},
                    onOpenDive = {},
                    focusDiveId = focusDiveId,
                    focusPage = focusPage,
                    onFocusHandled = onFocusHandled,
                )
            }
        }
    }

    private fun state(
        dives: List<DiveListViewDivesItem>,
        total: Int = dives.size,
        paged: Boolean = false,
    ): LogbookUiState {
        val months = dives.groupBy { it.startsAt.substring(0, 7) }
            .map { (month, rows) -> DiveListViewMonthsItem(month, rows.size, rows.sumOf { it.durationSeconds }) }
        return LogbookUiState(
            loading = false,
            paged = paged,
            filters = emptySet(),
            data = DiveListView(
                dives = dives,
                total = total,
                counts = DiveListViewCounts(0, 0, 0, 0, 0),
                totals = DiveListViewTotals(
                    dives = total,
                    durationSeconds = dives.sumOf { it.durationSeconds },
                    deepestM = dives.maxOfOrNull { it.maxDepthM ?: 0.0 },
                    lastDiveAt = dives.firstOrNull()?.startsAt,
                ),
                months = months,
            ),
            providers = ProviderDirectory.EMPTY,
        )
    }

    private fun fortyDives(): List<DiveListViewDivesItem> = (1..40).map { index ->
        val april = index <= 20
        val month = if (april) "04" else "03"
        val day = if (april) 21 - index else 41 - index
        dive("d$index", "2026-$month-%02dT09:00:00Z".format(day), "Site $index")
    }

    private fun dive(id: String, startsAt: String, site: String) = DiveListViewDivesItem(
        id = id,
        diverId = "diver",
        number = null,
        startsAt = startsAt,
        utcOffsetSeconds = 0,
        utcOffsetSource = "device",
        durationSeconds = 3_000.0,
        maxDepthM = 20.0,
        avgDepthM = 10.0,
        site = DiveListViewDivesItemSite(id = "site-$id", name = site),
        findings = 0,
        recordings = 1,
        fromProvider = null,
        gases = emptyList(),
        surfaceIntervalSeconds = null,
        participants = emptyList(),
        waiting = emptyList(),
        profile = null,
    )
}
