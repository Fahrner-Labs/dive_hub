package dev.divehub.shared.tools

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.EstimateCylinder
import dev.divehub.shared.planning.LeadUnit
import dev.divehub.shared.planning.PlannedConditions
import dev.divehub.shared.planning.SiteWaterType
import dev.divehub.shared.planning.Suit
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.planning.SuitUndergarment
import dev.divehub.shared.store.Cylinder
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Diver
import dev.divehub.shared.store.Recording
import dev.divehub.shared.store.RecordingSeries
import dev.divehub.shared.store.SyncState
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant

/**
 * The personalized tools read the synced store (FAH-276; ADR 0031, ADR 0071): the lead estimate's dive history, body
 * weight and site water, and the loadings' player, CNS, OTU and no-fly. The service answers are shaped like the API's.
 */
class PersonalizedToolsTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store = DiveStore.create(driver)
    private val nowMs = Instant.parse("2026-11-19T12:00:00.000Z").toEpochMilliseconds()
    private val today = Instant.fromEpochMilliseconds(nowMs)

    @AfterTest
    fun close() = driver.close()

    private fun ownDiver(id: String = "diver") = store.upsertDiver(
        Diver(id = id, name = "Elena", isOwn = true, external = false, apneaCounts = false, divesBefore = 0),
    )

    private fun site(id: String = "site", water: String? = "salt") = store.upsertDiveSite(
        DiveSite(id = id, name = "Stoney Cove", waterType = water),
    )

    private fun dive(
        id: String,
        startsAt: String,
        durationSeconds: Long = 2_700,
        siteId: String? = "site",
        leadKg: Double? = null,
        suitJson: String? = null,
        feedbackJson: String? = null,
        number: Long? = null,
        maxDepthM: Double? = 20.0,
    ) = store.upsertDive(
        Dive(
            id = id,
            diverId = "diver",
            startsAt = startsAt,
            utcOffsetSeconds = 0,
            durationSeconds = durationSeconds,
            maxDepthM = maxDepthM,
            siteId = siteId,
            leadTotalKg = leadKg,
            suitJson = suitJson,
            weightingFeedbackJson = feedbackJson,
            number = number,
        ),
    )

    private fun recording(
        id: String,
        diveId: String,
        startsAt: String,
        summary: String = "{}",
        channels: String = """["depth"]""",
        isPrimary: Boolean = true,
    ) = store.upsertRecording(
        Recording(
            id = id,
            diveId = diveId,
            isPrimary = isPrimary,
            startsAt = startsAt,
            durationSeconds = 2_700,
            parser = "fit",
            summaryJson = summary,
            channelsJson = channels,
        ),
    )

    private fun series(recordingId: String, channel: String, offsets: String, values: String) =
        store.upsertSeries(RecordingSeries(recordingId, channel, offsets, values, "2026-11-19T12:00:00.000Z"))

    private fun wetsuit(json: String = """{"type":"wetsuit","thicknessMm":5,"hood":true,"undergarment":null}""") = json

    @Test
    fun `an empty store has no personalized sources`() {
        val sources = assemblePersonalizedSources(store, nowMs)
        assertTrue(sources.storeEmpty)
        assertNull(sources.ownDiverId)
        assertNull(sources.loadings)
        assertTrue(sources.profilesComplete)
        assertNull(currentLoadings(sources, nowMs))
    }

    @Test
    fun `the lead estimate rests on the synced dives and names them`() {
        ownDiver()
        site()
        store.upsertBodyWeight(dev.divehub.shared.store.BodyWeight("diver", "2026-11-01", 72.0))
        listOf("2026-11-17T09:00:00.000Z", "2026-11-16T09:00:00.000Z").forEachIndexed { index, startsAt ->
            dive(
                id = "d$index",
                startsAt = startsAt,
                leadKg = 8.0,
                suitJson = wetsuit(),
                feedbackJson = """{"felt":"right","amountKg":null}""",
                number = (10 - index).toLong(),
            )
            store.upsertCylinder(
                Cylinder(
                    diveId = "d$index",
                    position = 0,
                    volumeL = 12.0,
                    material = "steel",
                    o2 = 21.0,
                    he = 0.0,
                    startPressureBar = 200.0,
                    endPressureBar = 50.0,
                ),
            )
        }

        val sources = assemblePersonalizedSources(store, nowMs)
        val answer = leadEstimate(
            PlannedConditions(
                water = SiteWaterType.salt,
                suit = Suit(SuitType.wetsuit, 5.0, true, null),
                cylinders = listOf(EstimateCylinder(12.0, CylinderMaterial.steel)),
            ),
            LeadUnit.kg,
            sources,
            today,
        )

        assertEquals("kg", answer.unit)
        assertNotNull(answer.total)
        assertNotNull(answer.range)
        assertEquals("medium", answer.confidence)
        assertEquals("dives", answer.basis?.kind)
        assertEquals(2, answer.basis?.dives?.size)
        assertEquals("2026-11-17T09:00:00.000Z", answer.basis?.dives?.first()?.startsAt)
        assertEquals(10, answer.basis?.dives?.first()?.number)
        assertEquals(listOf("cylinder", "water"), answer.adjustments.map { it.kind })
        assertEquals(listOf("eye_level_empty_bc", "neutral_at_5m_at_reserve"), answer.weightCheck.steps)
        assertEquals(answer.reserve.bar, answer.weightCheck.reserveBar)
        assertEquals(0, answer.leftOut.noWaterType)
    }

    @Test
    fun `without a body weight or a dive with the suit there is no number but always a weight check`() {
        ownDiver()

        val answer = leadEstimate(
            PlannedConditions(
                water = SiteWaterType.salt,
                suit = Suit(SuitType.wetsuit, 5.0, true, null),
                cylinders = listOf(EstimateCylinder(12.0, CylinderMaterial.steel)),
            ),
            LeadUnit.kg,
            assemblePersonalizedSources(store, nowMs),
            today,
        )

        assertNull(answer.total)
        assertNull(answer.range)
        assertNull(answer.basis)
        assertEquals("low", answer.confidence)
        assertTrue(answer.missing.any { it.kind == "body_weight" })
        assertEquals(WEIGHT_CHECK_STEPS, answer.weightCheck.steps)
        assertEquals(50.0, answer.weightCheck.reserveBar)
    }

    @Test
    fun `a body weight without dives takes the rule of thumb`() {
        ownDiver()
        store.upsertBodyWeight(dev.divehub.shared.store.BodyWeight("diver", "2026-11-01", 80.0))

        val answer = leadEstimate(
            PlannedConditions(
                water = SiteWaterType.salt,
                suit = Suit(SuitType.drysuit, null, null, SuitUndergarment.medium),
                cylinders = listOf(EstimateCylinder(12.0, CylinderMaterial.steel)),
            ),
            LeadUnit.kg,
            assemblePersonalizedSources(store, nowMs),
            today,
        )

        assertNotNull(answer.total)
        assertEquals("low", answer.confidence)
        assertEquals("rule_of_thumb", answer.basis?.kind)
        assertNotNull(answer.basis?.rule)
    }

    @Test
    fun `a dive whose site has no water type is left out and counted`() {
        ownDiver()
        site(water = null)
        store.upsertBodyWeight(dev.divehub.shared.store.BodyWeight("diver", "2026-11-01", 72.0))
        dive(id = "d1", startsAt = "2026-11-17T09:00:00.000Z", leadKg = 8.0, suitJson = wetsuit())

        val answer = leadEstimate(
            PlannedConditions(
                water = SiteWaterType.salt,
                suit = Suit(SuitType.wetsuit, 5.0, true, null),
                cylinders = listOf(EstimateCylinder(12.0, CylinderMaterial.steel)),
            ),
            LeadUnit.kg,
            assemblePersonalizedSources(store, nowMs),
            today,
        )

        assertEquals(1, answer.leftOut.noWaterType)
        assertEquals("rule_of_thumb", answer.basis?.kind)
    }

    @Test
    fun `a suit thickness on a drysuit is refused by code`() {
        ownDiver()
        val error = kotlin.runCatching {
            leadEstimate(
                PlannedConditions(
                    water = SiteWaterType.salt,
                    suit = Suit(SuitType.drysuit, 5.0, null, null),
                    cylinders = listOf(EstimateCylinder(12.0, CylinderMaterial.steel)),
                ),
                LeadUnit.kg,
                assemblePersonalizedSources(store, nowMs),
                today,
            )
        }.exceptionOrNull()
        assertEquals(ToolsError.SUIT_INVALID, (error as? ToolsError)?.code)
    }

    @Test
    fun `loadings keep a missing estimate apart from a real zero`() {
        ownDiver()
        dive(id = "old", startsAt = "2026-11-17T00:00:00.000Z", durationSeconds = 3_600, maxDepthM = 12.0)
        recording("r-old", "old", "2026-11-17T00:00:00.000Z", """{"cnsEnd":0,"otuStart":4,"otuEnd":4}""")
        series("r-old", "depth", "[0,600000]", "[0,12]")

        val stale = assertNotNull(currentLoadings(assemblePersonalizedSources(store, nowMs), nowMs))
        assertNull(stale.n2)
        assertNull(stale.otu)
        assertEquals(0.0, stale.cns?.percentAtLastDiveEnd)
        assertEquals(0.0, stale.cns?.percentNow)
        assertEquals("2026-11-17T01:00:00.000Z", stale.basis.lastDiveEndedAt)
        assertEquals(0, stale.basis.divesSkippedNoProfile)
        assertEquals(1, stale.basis.divesUsed)
    }

    @Test
    fun `a recent profile gives N2 and the 24 h OTU window`() {
        ownDiver()
        dive(id = "recent", startsAt = "2026-11-19T08:00:00.000Z", durationSeconds = 3_600, maxDepthM = 20.0)
        recording("r1", "recent", "2026-11-19T08:00:00.000Z", """{"cnsEnd":40,"otuStart":2,"otuEnd":3,"diveMode":"scuba"}""")
        series("r1", "depth", "[0,3600000]", "[0,20]")

        val answer = assertNotNull(currentLoadings(assemblePersonalizedSources(store, nowMs), nowMs))
        assertNotNull(answer.n2)
        assertTrue(answer.n2.worstCompartmentPercent > 0.0)
        assertNotNull(answer.cns)
        assertTrue(answer.cns.percentNow < 40.0)
        assertEquals(40.0, answer.cns.percentAtLastDiveEnd)
        assertEquals(90, answer.cns.halfLifeMinutes)
        assertEquals(1.0, answer.otu?.last24Hours)
        assertEquals(1, answer.basis.divesUsed)
        assertEquals(0, answer.basis.divesSkippedNoProfile)
    }

    @Test
    fun `no-fly is DAN's guideline after the latest dive, information not advice`() {
        ownDiver()
        dive(id = "deco", startsAt = "2026-11-19T08:00:00.000Z", durationSeconds = 3_600, maxDepthM = 30.0)
        recording("r1", "deco", "2026-11-19T08:00:00.000Z", """{"diveMode":"scuba"}""", channels = """["depth","ndl"]""")
        series("r1", "depth", "[0,3600000]", "[0,30]")
        series("r1", "ndl", "[0,1800000,3600000]", "[10,0,2]")

        val answer = assertNotNull(currentLoadings(assemblePersonalizedSources(store, nowMs), nowMs))
        assertNotNull(answer.noFly)
        assertEquals(24, answer.noFly.hours)
        assertEquals("decompression", answer.noFly.reason)
        assertEquals("DAN 2002: guidelines for flying after diving", answer.noFly.source.title)
    }

    @Test
    fun `profiles are incomplete until every listed channel is in the store`() {
        ownDiver()
        dive(id = "d1", startsAt = "2026-11-19T08:00:00.000Z")
        recording("r1", "d1", "2026-11-19T08:00:00.000Z", channels = """["depth","temperature"]""")
        series("r1", "depth", "[0,60000]", "[0,10]")
        assertFalse(assemblePersonalizedSources(store, nowMs).profilesComplete)

        series("r1", "temperature", "[0,60000]", "[18,17]")
        assertTrue(assemblePersonalizedSources(store, nowMs).profilesComplete)
    }

    @Test
    fun `the last full sync feeds the staleness label`() {
        ownDiver()
        store.saveSyncState(
            SyncState(lastFullSyncAt = "2026-11-19T10:00:00.000Z", schemaVersion = DiveStore.schemaVersion),
        )
        assertEquals("2026-11-19T10:00:00.000Z", assemblePersonalizedSources(store, nowMs).syncedAt)
    }
}
