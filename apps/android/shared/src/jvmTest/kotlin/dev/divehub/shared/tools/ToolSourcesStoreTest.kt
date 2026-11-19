package dev.divehub.shared.tools

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Cylinder
import dev.divehub.shared.store.Diver
import dev.divehub.shared.store.SyncState
import dev.divehub.shared.store.Recording
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/** What the tools read from the synced store (FAH-274): the own Diver's GF, planning SAC and recent Dive. */
class ToolSourcesStoreTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store = DiveStore.create(driver)

    @AfterTest
    fun close() = driver.close()

    private fun ownDiver() = store.upsertDiver(
        Diver(id = "diver", name = "Elena", isOwn = true, external = false, apneaCounts = false, divesBefore = 0),
    )

    private fun dive(id: String, startsAt: String, sacLpm: Double? = null, diverId: String = "diver") = store.upsertDive(
        Dive(
            id = id,
            diverId = diverId,
            startsAt = startsAt,
            durationSeconds = 2_700,
            avgDepthM = 18.0,
            sacLitresPerMinute = sacLpm,
        ),
    )

    @Test
    fun `an empty store says so`() {
        val sources = assembleToolSources(store, nowMs = 1_700_000_000_000)
        assertTrue(sources.storeEmpty)
        assertNull(sources.ownDiverId)
        assertNull(sources.gfHigh)
        assertNull(sources.planningSac)
        assertNull(sources.recentDiveEndedAt)
    }

    @Test
    fun `the own Diver's latest Recording supplies the GF`() {
        ownDiver()
        dive("d1", "2026-11-18T09:00:00.000Z")
        store.upsertRecording(
            Recording(
                id = "r1",
                diveId = "d1",
                isPrimary = true,
                startsAt = "2026-11-18T09:00:00.000Z",
                durationSeconds = 2_700,
                parser = "fit",
                summaryJson = """{"gfHigh":80}""",
            ),
        )

        val sources = assembleToolSources(store, nowMs = 1_700_000_000_000)
        assertFalse(sources.storeEmpty)
        assertEquals("diver", sources.ownDiverId)
        assertEquals(80.0, sources.gfHigh)
    }

    @Test
    fun `the planning SAC comes from the last Dives with how many it rests on`() {
        ownDiver()
        listOf(10.0, 12.0, 14.0, 16.0, 18.0).forEachIndexed { index, sac ->
            dive("d$index", "2026-11-${(18 - index).toString().padStart(2, '0')}T09:00:00.000Z", sac)
        }

        val sources = assembleToolSources(store, nowMs = 1_700_000_000_000)
        val sac = assertNotNull(sources.planningSac)
        assertEquals(PlanningSacSource.logbook, sac.source)
        assertEquals(5, sac.basedOn)
        assertEquals(16.8, sac.lpm)
    }

    @Test
    fun `the last full sync is carried for the staleness line`() {
        ownDiver()
        dive("d1", "2026-11-18T09:00:00.000Z", 18.0)
        store.saveSyncState(
            SyncState(lastFullSyncAt = "2026-11-18T08:00:00.000Z", schemaVersion = DiveStore.schemaVersion),
        )

        val sources = assembleToolSources(store, nowMs = 1_700_000_000_000)
        assertEquals("2026-11-18T08:00:00.000Z", sources.syncedAt)
    }

    @Test
    fun `a Dive without its own SAC falls back to its Cylinders`() {
        ownDiver()
        (0 until 5).forEach { index ->
            val id = "d$index"
            dive(id, "2026-11-${(18 - index).toString().padStart(2, '0')}T09:00:00.000Z")
            store.upsertCylinder(
                Cylinder(
                    diveId = id,
                    position = 0,
                    volumeL = 12.0,
                    o2 = 21.0,
                    he = 0.0,
                    startPressureBar = 200.0,
                    endPressureBar = 100.0,
                ),
            )
        }

        val sac = assertNotNull(assembleToolSources(store, nowMs = 1_700_000_000_000).planningSac)
        assertEquals(PlanningSacSource.logbook, sac.source)
        assertTrue(sac.lpm > 0.0)
    }

    @Test
    fun `a Dive without Cylinders falls back to its Recording's SAC`() {
        ownDiver()
        (0 until 5).forEach { index ->
            val id = "d$index"
            dive(id, "2026-11-${(18 - index).toString().padStart(2, '0')}T09:00:00.000Z")
            store.upsertRecording(
                Recording(
                    id = "r$index",
                    diveId = id,
                    isPrimary = true,
                    startsAt = "2026-11-${(18 - index).toString().padStart(2, '0')}T09:00:00.000Z",
                    durationSeconds = 2_700,
                    parser = "fit",
                    summaryJson = """{"sacLpm":16}""",
                ),
            )
        }

        val sac = assertNotNull(assembleToolSources(store, nowMs = 1_700_000_000_000).planningSac)
        assertEquals(PlanningSacSource.logbook, sac.source)
        assertEquals(16.0, sac.lpm)
    }

    @Test
    fun `every synced Diver is named, with a planning SAC where their Dives allow`() {
        ownDiver()
        store.upsertDiver(
            Diver(id = "buddy", name = "Bob", isOwn = false, external = false, apneaCounts = false, divesBefore = 0),
        )
        store.upsertDiver(
            Diver(id = "quiet", name = "Quiet", isOwn = false, external = false, apneaCounts = false, divesBefore = 0),
        )
        listOf(10.0, 12.0, 14.0, 16.0, 18.0).forEachIndexed { index, sac ->
            dive("own$index", "2026-11-${(18 - index).toString().padStart(2, '0')}T09:00:00.000Z", sac)
            dive("buddy$index", "2026-11-${(18 - index).toString().padStart(2, '0')}T10:00:00.000Z", sac + 2, diverId = "buddy")
        }

        val sources = assembleToolSources(store, nowMs = 1_700_000_000_000)
        val own = assertNotNull(sources.divers.firstOrNull { it.id == "diver" })
        val buddy = assertNotNull(sources.divers.firstOrNull { it.id == "buddy" })
        val quiet = assertNotNull(sources.divers.firstOrNull { it.id == "quiet" })
        assertTrue(own.isOwn)
        assertFalse(buddy.isOwn)
        assertEquals("Bob", buddy.name)
        assertEquals(PlanningSacSource.logbook, own.planningSac?.source)
        assertEquals(PlanningSacSource.logbook, buddy.planningSac?.source)
        assertEquals(16.8, own.planningSac?.lpm)
        assertEquals(18.8, buddy.planningSac?.lpm)
        assertNull(quiet.planningSac)
    }

    @Test
    fun `a Dive within 48 hours is named`() {
        ownDiver()
        dive("recent", "2026-11-18T09:00:00.000Z")
        val start = kotlinx.datetime.Instant.parse("2026-11-18T09:00:00.000Z").toEpochMilliseconds()
        // The store's row ends 45 minutes after it starts; put "now" a little after that.
        val sources = assembleToolSources(store, nowMs = start + 45 * 60_000)
        assertNotNull(sources.recentDiveEndedAt)
        assertTrue(sources.recentDiveEndedAt!!.startsWith("2026-11-18T09:45:00"))
        // Far later the same Dive no longer counts.
        assertNull(assembleToolSources(store, nowMs = start + 49 * 3_600_000).recentDiveEndedAt)
    }
}
