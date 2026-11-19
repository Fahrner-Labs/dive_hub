package dev.divehub.android.ui.tools

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import dev.divehub.android.ui.DiveHubTheme
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200Response
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseBasis
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseLeftOut
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseMissingItem
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseRange
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseReserve
import dev.divehub.api.models.ApiDiversIdLeadEstimateGet200ResponseWeightCheck
import dev.divehub.api.models.ApiDiversIdLoadingsGet200Response
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseBasis
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseCns
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseN2
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseNoFly
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseNoFlySource
import dev.divehub.api.models.ApiDiversIdLoadingsGet200ResponseOtu
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.tools.WEIGHT_CHECK_STEPS
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
 * The personalized tools' contract-visible states as pictures (FAH-276; client contract, Offline reads and Tools):
 * the staleness label with the profiles-syncing marker, the lead estimate's range, reasons and weight check, and the
 * loadings' missing estimate beside a real zero.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [36], qualifiers = "en-rUS-w411dp-h891dp-xxhdpi")
class PersonalizedScreenshotTest {
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
    fun `the lead estimate carries the staleness label and the profiles marker`() {
        renderLead(
            LeadEstimateUiState(
                loading = false,
                personalized = true,
                syncedAt = "2026-11-18T08:00:00Z",
                profilesComplete = false,
                inputs = LeadInputs(suit = SuitType.wetsuit, thickness = "5", cylinders = listOf(LeadCylinderInput("12", CylinderMaterial.steel))),
                answer = leadAnswer(),
            ),
        )
        compose.onNodeWithText("Based on dives synced as of 18 Nov 2026, 09:00. (profiles still syncing)").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Starting point"))
        compose.onNodeWithText("Starting point").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("8.5 kg"))
        compose.onNodeWithText("8.5 kg").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Range 7.0 kg to 10.0 kg"))
        compose.onNodeWithText("Range 7.0 kg to 10.0 kg").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Confidence: medium"))
        compose.onNodeWithText("Confidence: medium").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Based on 1 of your dives with this suit:"))
        compose.onNodeWithText("Based on 1 of your dives with this suit:").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Weight check"))
        compose.onNodeWithText("Weight check").assertIsDisplayed()
        capture("lead_estimate.png")
    }

    @Test
    fun `no number still shows what is missing and the weight check`() {
        renderLead(
            LeadEstimateUiState(
                loading = false,
                personalized = true,
                syncedAt = "2026-11-18T08:00:00Z",
                profilesComplete = true,
                inputs = LeadInputs(suit = SuitType.wetsuit, thickness = "5", cylinders = listOf(LeadCylinderInput("12", CylinderMaterial.steel))),
                answer = leadAnswer(
                    total = null,
                    range = null,
                    basis = null,
                    confidence = "low",
                    missing = listOf(
                        ApiDiversIdLeadEstimateGet200ResponseMissingItem(kind = "body_weight"),
                        ApiDiversIdLeadEstimateGet200ResponseMissingItem(kind = "suit_thickness"),
                    ),
                    adjustments = emptyList(),
                ),
            ),
        )
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("No number yet"))
        compose.onNodeWithText("No number yet").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("What is missing"))
        compose.onNodeWithText("What is missing").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Float at eye level with an empty BC."))
        compose.onNodeWithText("Float at eye level with an empty BC.").assertIsDisplayed()
        capture("lead_no_number.png")
    }

    @Test
    fun `no number while details sync says so instead of claiming there is no dive`() {
        renderLead(
            LeadEstimateUiState(
                loading = false,
                personalized = true,
                syncedAt = "2026-11-18T08:00:00Z",
                profilesComplete = true,
                detailsComplete = false,
                inputs = LeadInputs(suit = SuitType.wetsuit, thickness = "5", cylinders = listOf(LeadCylinderInput("12", CylinderMaterial.steel))),
                answer = leadAnswer(
                    total = null,
                    range = null,
                    basis = null,
                    confidence = "low",
                    missing = listOf(ApiDiversIdLeadEstimateGet200ResponseMissingItem(kind = "body_weight")),
                    adjustments = emptyList(),
                ),
            ),
        )
        compose.onNodeWithText("Based on dives synced as of 18 Nov 2026, 09:00. (dive details still syncing)").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Dive details still syncing"))
        compose.onNodeWithText("Dive details still syncing").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("This rests on what is already on the device", substring = true))
        compose.onNodeWithText("This rests on what is already on the device", substring = true).assertIsDisplayed()
        compose.onNodeWithText("There is no dive with this suit", substring = true).assertDoesNotExist()
        capture("lead_no_number_syncing.png")
    }

    @Test
    fun `the rule of thumb gives way to the details marker while details sync`() {
        renderLead(
            LeadEstimateUiState(
                loading = false,
                personalized = true,
                syncedAt = "2026-11-18T08:00:00Z",
                profilesComplete = true,
                detailsComplete = false,
                inputs = LeadInputs(suit = SuitType.wetsuit, thickness = "5", cylinders = listOf(LeadCylinderInput("12", CylinderMaterial.steel))),
                answer = leadAnswer(
                    basis = ApiDiversIdLeadEstimateGet200ResponseBasis(kind = "rule_of_thumb", rule = 6.0),
                ),
            ),
        )
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Dive details are still syncing", substring = true))
        compose.onNodeWithText("Dive details are still syncing", substring = true).assertIsDisplayed()
        compose.onNodeWithText("No dive with this suit", substring = true).assertDoesNotExist()
    }

    @Test
    fun `the loadings name their basis and mark the profiles still syncing`() {
        renderLoadings(
            LoadingsUiState(
                loading = false,
                personalized = true,
                syncedAt = "2026-11-18T08:00:00Z",
                profilesComplete = false,
                answer = loadingsAnswer(),
            ),
        )
        compose.onNodeWithText("Based on dives synced as of 18 Nov 2026, 09:00. (profiles still syncing)").assertIsDisplayed()
        compose.onNodeWithText("42.3 % of the limit at GF 85").assertIsDisplayed()
        compose.onNodeWithText("20.0 % now · 40.0 % at the end of the last dive").assertIsDisplayed()
        compose.onNodeWithText("12.5").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Flying after the last dive"))
        compose.onNodeWithText("Not before", substring = true).assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("From 2 logged dives in the last 72 hours", substring = true))
        compose.onNodeWithText("From 2 logged dives in the last 72 hours", substring = true).assertIsDisplayed()
        capture("loadings.png")
    }

    @Test
    fun `a missing estimate is not shown as zero while a real zero is`() {
        renderLoadings(
            LoadingsUiState(
                loading = false,
                personalized = true,
                syncedAt = "2026-11-18T08:00:00Z",
                profilesComplete = true,
                answer = loadingsAnswer(
                    n2 = null,
                    cns = ApiDiversIdLoadingsGet200ResponseCns(percentNow = 0.0, percentAtLastDiveEnd = 0.0, halfLifeMinutes = 90),
                    otu = ApiDiversIdLoadingsGet200ResponseOtu(0.0),
                    noFly = null,
                ),
            ),
        )
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("No estimate:", substring = true))
        compose.onNodeWithText("No estimate:", substring = true).assertIsDisplayed()
        compose.onNodeWithText("0.0 % now · 0.0 % at the end of the last dive").assertIsDisplayed()
        compose.onNodeWithText("0.0").assertIsDisplayed()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("No no-fly time: no logged dive to run it from."))
        compose.onNodeWithText("No no-fly time: no logged dive to run it from.").assertIsDisplayed()
        capture("loadings_missing_zero.png")
    }

    private fun renderLead(state: LeadEstimateUiState) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                LeadEstimateScreen(
                    state = state,
                    imperial = false,
                    onBack = {},
                    onOpenDive = {},
                    onChange = {},
                    onAddCylinder = {},
                    onRemoveCylinder = {},
                    onReload = {},
                )
            }
        }
    }

    private fun renderLoadings(state: LoadingsUiState) {
        compose.setContent {
            DiveHubTheme(darkTheme = false) {
                LoadingsScreen(state = state, onBack = {}, onReload = {})
            }
        }
    }

    private fun capture(name: String) {
        compose.onRoot().captureRoboImage("src/test/screenshots/$name")
    }

    private fun leadAnswer(
        total: Double? = 8.5,
        range: ApiDiversIdLeadEstimateGet200ResponseRange? = ApiDiversIdLeadEstimateGet200ResponseRange(7.0, 10.0),
        basis: ApiDiversIdLeadEstimateGet200ResponseBasis? = ApiDiversIdLeadEstimateGet200ResponseBasis(
            kind = "dives",
            dives = listOf(
                ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem(
                    diveId = "dive-1",
                    startsAt = "2026-11-17T09:00:00.000Z",
                    number = 12,
                    carried = 8.0,
                    needed = 8.0,
                    weight = 2.0,
                ),
            ),
            personal = 5.0,
        ),
        confidence: String = "medium",
        missing: List<ApiDiversIdLeadEstimateGet200ResponseMissingItem> = emptyList(),
        adjustments: List<ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem> = listOf(
            ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem(kind = "cylinder", amount = 1.5),
            ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem(kind = "water", amount = 0.5, water = "salt"),
        ),
    ) = ApiDiversIdLeadEstimateGet200Response(
        unit = "kg",
        total = total,
        range = range,
        confidence = confidence,
        basis = basis,
        adjustments = adjustments,
        missing = missing,
        reserve = ApiDiversIdLeadEstimateGet200ResponseReserve(bar = 50.0, fromDives = false),
        leftOut = ApiDiversIdLeadEstimateGet200ResponseLeftOut(noWaterType = 0),
        weightCheck = ApiDiversIdLeadEstimateGet200ResponseWeightCheck(
            steps = WEIGHT_CHECK_STEPS,
            reserveBar = 50.0,
        ),
    )

    private fun loadingsAnswer(
        n2: ApiDiversIdLoadingsGet200ResponseN2? = ApiDiversIdLoadingsGet200ResponseN2(42.3, 85.0),
        cns: ApiDiversIdLoadingsGet200ResponseCns = ApiDiversIdLoadingsGet200ResponseCns(
            percentNow = 20.0,
            percentAtLastDiveEnd = 40.0,
            halfLifeMinutes = 90,
        ),
        otu: ApiDiversIdLoadingsGet200ResponseOtu = ApiDiversIdLoadingsGet200ResponseOtu(12.5),
        noFly: ApiDiversIdLoadingsGet200ResponseNoFly? = ApiDiversIdLoadingsGet200ResponseNoFly(
            hours = 18,
            reason = "several",
            until = "2026-11-20T06:00:00.000Z",
            source = ApiDiversIdLoadingsGet200ResponseNoFlySource(
                title = "DAN 2002: guidelines for flying after diving",
                url = "https://dan.org/",
            ),
        ),
    ) = ApiDiversIdLoadingsGet200Response(
        computedAt = "2026-11-19T12:00:00.000Z",
        basis = ApiDiversIdLoadingsGet200ResponseBasis(
            windowHours = 72,
            divesUsed = 2,
            divesSkippedNoProfile = 1,
            lastDiveEndedAt = "2026-11-19T09:00:00.000Z",
        ),
        n2 = n2,
        cns = cns,
        otu = otu,
        noFly = noFly,
    )
}
