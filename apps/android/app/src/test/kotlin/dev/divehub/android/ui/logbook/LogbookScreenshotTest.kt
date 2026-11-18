package dev.divehub.android.ui.logbook

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.assertIsDisplayed
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.ApiDivesGet200Response
import dev.divehub.api.models.ApiDivesGet200ResponseCounts
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItem
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItemGasesItem
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItemParticipantsItem
import dev.divehub.api.models.ApiDivesGet200ResponseDivesItemSite
import dev.divehub.api.models.ApiDivesGet200ResponseMonthsItem
import dev.divehub.api.models.ApiDivesGet200ResponseTotals
import dev.divehub.api.models.DiveProfileView
import dev.divehub.shared.logbook.LogbookFilter
import dev.divehub.shared.logbook.ProviderDirectory
import kotlinx.serialization.json.JsonPrimitive
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

    @Test
    fun `filter counts`() {
        render(page(dives = listOf(reef), filters = emptySet()))
        compose.onNodeWithText("No recording · 3").assertIsDisplayed()
        capture("logbook_filters.png")
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

    private fun render(state: LogbookUiState) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                LogbookScreen(
                    state = state,
                    units = "metric",
                    onRefresh = {},
                    onRetry = {},
                    onShowAll = {},
                    onPage = {},
                    onToggleFilter = {},
                    onSignOut = {},
                )
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun page(dives: List<ApiDivesGet200ResponseDivesItem>, filters: Set<LogbookFilter>) = LogbookUiState(
        loading = false,
        page = 1,
        filters = filters,
        data = ApiDivesGet200Response(
            dives = dives,
            total = 312,
            counts = ApiDivesGet200ResponseCounts(
                noRecording = 3,
                noSite = 1,
                withFindings = 2,
                notAtProvider = 5,
                changedSinceSent = 7,
            ),
            totals = ApiDivesGet200ResponseTotals(
                dives = 312,
                durationSeconds = 648_000.0,
                deepestM = 40.2,
                lastDiveAt = "2026-04-02T09:00:00Z",
            ),
            months = listOf(
                ApiDivesGet200ResponseMonthsItem("2026-04", 9, 27_000.0),
                ApiDivesGet200ResponseMonthsItem("2026-03", 12, 36_000.0),
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
        gases = listOf(ApiDivesGet200ResponseDivesItemGasesItem(o2 = 32.0, he = 0.0)),
        participants = listOf(
            ApiDivesGet200ResponseDivesItemParticipantsItem("diver-2", "Ada Byron", "buddy"),
            ApiDivesGet200ResponseDivesItemParticipantsItem("diver-3", "Bo Chen", "guide"),
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
        gases: List<ApiDivesGet200ResponseDivesItemGasesItem> = emptyList(),
        participants: List<ApiDivesGet200ResponseDivesItemParticipantsItem> = emptyList(),
        profile: DiveProfileView?,
    ) = ApiDivesGet200ResponseDivesItem(
        id = id,
        diverId = "diver",
        number = number,
        startsAt = startsAt,
        utcOffsetSeconds = 7_200,
        utcOffsetSource = "device",
        durationSeconds = 3_000.0,
        maxDepthM = maxDepthM,
        avgDepthM = maxDepthM / 2,
        site = site?.let { ApiDivesGet200ResponseDivesItemSite(id = "site-$id", name = it) },
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
