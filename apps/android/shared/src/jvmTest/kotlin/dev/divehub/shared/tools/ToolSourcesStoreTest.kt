package dev.divehub.shared.tools

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.planning.PlanningSacSource
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Diver
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

    private fun dive(id: String, startsAt: String, sacLpm: Double? = null) = store.upsertDive(
        Dive(
            id = id,
            diverId = "diver",
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
