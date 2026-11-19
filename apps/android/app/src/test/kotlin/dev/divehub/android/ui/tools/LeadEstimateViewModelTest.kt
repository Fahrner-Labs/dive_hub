package dev.divehub.android.ui.tools

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.store.Cylinder
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Diver
import dev.divehub.shared.sync.SyncProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** The lead screen rereads the store when a sync writes it (FAH-299): one reload per revision bump, never a poll. */
@OptIn(ExperimentalCoroutinesApi::class)
class LeadEstimateViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `a revision bump rereads the store when a dive's details arrive`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDiver(Diver(id = "diver", name = "Elena", isOwn = true, external = false, apneaCounts = false, divesBefore = 0))
            store.upsertDiveSite(DiveSite(id = "site", name = "Stoney Cove", waterType = "salt"))
            store.upsertDive(dive(leadKg = null, suitJson = null))

            val revision = MutableStateFlow(0L)
            val progress = MutableStateFlow(
                SyncProgress.EMPTY.copy(metaDone = true, storedDives = 1, detailedDives = 0, detailsDone = false),
            )
            val viewModel = LeadEstimateViewModel(store, revision, progress, dispatcher)
            viewModel.start(imperial = false)
            viewModel.update {
                it.copy(
                    suit = SuitType.wetsuit,
                    thickness = "5",
                    hood = true,
                    cylinders = listOf(LeadCylinderInput("12", CylinderMaterial.steel)),
                )
            }
            advanceUntilIdle()

            assertTrue(viewModel.state.value.personalized)
            assertFalse(viewModel.state.value.detailsComplete)
            assertNull(viewModel.state.value.answer?.total)

            store.upsertDive(
                dive(
                    leadKg = 8.0,
                    suitJson = """{"type":"wetsuit","thicknessMm":5,"hood":true,"undergarment":null}""",
                    feedbackJson = """{"felt":"right","amountKg":null}""",
                ),
            )
            store.upsertCylinder(
                Cylinder(
                    diveId = "dive-1",
                    position = 0,
                    volumeL = 12.0,
                    material = "steel",
                    o2 = 21.0,
                    he = 0.0,
                    startPressureBar = 200.0,
                    endPressureBar = 50.0,
                ),
            )
            progress.value = progress.value.copy(detailedDives = 1, detailsDone = true)
            revision.value = 1
            advanceUntilIdle()

            val state = viewModel.state.value
            assertTrue(state.detailsComplete)
            assertNotNull(state.answer?.total)
            assertEquals("dives", state.answer?.basis?.kind)

            viewModel.forget()
            advanceUntilIdle()
        } finally {
            driver.close()
        }
    }

    private fun dive(leadKg: Double?, suitJson: String?, feedbackJson: String? = null) = Dive(
        id = "dive-1",
        diverId = "diver",
        startsAt = "2026-11-17T09:00:00.000Z",
        utcOffsetSeconds = 0,
        durationSeconds = 2_700,
        maxDepthM = 20.0,
        siteId = "site",
        leadTotalKg = leadKg,
        suitJson = suitJson,
        weightingFeedbackJson = feedbackJson,
        number = 12,
    )
}
