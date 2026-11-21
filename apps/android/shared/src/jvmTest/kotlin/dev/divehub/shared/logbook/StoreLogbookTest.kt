package dev.divehub.shared.logbook

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.api.models.DiveListViewCounts
import dev.divehub.api.models.DiveListViewDivesItem
import dev.divehub.api.models.DiveListViewDivesItemSite
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiveViewRecordingsItem
import dev.divehub.api.models.DiveViewRecordingsItemSummary
import dev.divehub.api.models.DiveViewValues
import dev.divehub.api.models.DiveViewValuesStartsAt
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Recording
import dev.divehub.shared.store.RecordingSeries
import dev.divehub.shared.store.SyncCheckpoint
import dev.divehub.shared.store.SyncState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }

class StoreLogbookTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store = DiveStore.create(driver)
    private val logbook = StoreLogbook(store)

    @AfterTest
    fun close() = driver.close()

    @Test
    fun `the whole stored list is read newest first`() {
        for (index in 1..30) {
            insert(
                diveId = "d$index",
                startsAt = "2026-11-%02dT10:00:00.000Z".format(index),
                list = item("d$index", startsAt = "2026-11-%02dT10:00:00.000Z".format(index)),
            )
        }

        val list = logbook.list(emptySet())

        assertEquals(30, list.data.total)
        assertEquals(30, list.data.dives.size)
        assertEquals("d30", list.data.dives.first().id)
        assertEquals("d1", list.data.dives.last().id)
    }

    @Test
    fun `filters narrow the stored rows while the counts keep every chip's number`() {
        insert("typed", item("typed", recordings = 0, findings = 0, site = null))
        insert("findings", item("findings", recordings = 1, findings = 2, site = "Blue Hole"))
        insert("plain", item("plain", recordings = 1, findings = 0, site = "Reef"))
        store.saveLogbookCounts(
            JSON.encodeToString(
                DiveListViewCounts(
                    noRecording = 1, noSite = 1, withFindings = 1, notAtProvider = 3, changedSinceSent = 4,
                ),
            ),
        )

        val typed = logbook.list(setOf(LogbookFilter.NO_RECORDING))
        val noSite = logbook.list(setOf(LogbookFilter.NO_SITE))
        val findings = logbook.list(setOf(LogbookFilter.WITH_FINDINGS))

        assertEquals(listOf("typed"), typed.data.dives.map { it.id })
        assertEquals(listOf("typed"), noSite.data.dives.map { it.id })
        assertEquals(listOf("findings"), findings.data.dives.map { it.id })
        assertEquals(3, typed.data.counts.notAtProvider)
        assertEquals(4, typed.data.counts.changedSinceSent)
    }

    @Test
    fun `totals cover the whole logbook and months count every matching dive`() {
        insert("a", item("a", startsAt = "2026-11-02T10:00:00.000Z"), duration = 3_000, depth = 20.0)
        insert("b", item("b", startsAt = "2026-11-12T10:00:00.000Z"), duration = 2_000, depth = 30.0)
        insert("c", item("c", startsAt = "2026-10-02T10:00:00.000Z"), duration = 1_000, depth = 10.0)

        val list = logbook.list(emptySet())

        assertEquals(3, list.data.totals.dives)
        assertEquals(6_000.0, list.data.totals.durationSeconds)
        assertEquals(30.0, list.data.totals.deepestM)
        assertEquals("2026-11-12T10:00:00.000Z", list.data.totals.lastDiveAt)
        assertEquals(listOf("2026-11", "2026-10"), list.data.months.map { it.month })
        assertEquals(2, list.data.months.first().dives)
        assertEquals(5_000.0, list.data.months.first().durationSeconds)
    }

    @Test
    fun `the sync line comes from sync state and the profiles marker from per-dive checkpoints`() {
        insert("synced", item("synced"))
        insert("pending", item("pending"))
        insert("no-recording", item("no-recording", recordings = 0))
        store.upsertRecording(recording("synced"))
        store.upsertRecording(recording("pending"))
        store.saveCheckpoint(SyncCheckpoint("profiles:synced", "profiles", done = true, updatedAt = NOW))
        store.saveSyncState(SyncState(lastFullSyncAt = NOW, schemaVersion = DiveStore.schemaVersion))

        val list = logbook.list(emptySet())

        assertEquals(NOW, list.syncedAt)
        assertTrue(list.hasSync)
        assertEquals(setOf("pending"), list.profilePending)
    }

    @Test
    fun `one dive decodes its stored detail payload`() {
        val detail = dive("dive-1", number = 42, startsAt = "2026-11-01T10:00:00.000Z")
        insert("dive-1", item("dive-1"), detail = detail)

        val stored = logbook.dive("dive-1")

        assertNotNull(stored)
        assertEquals(detail, stored.dive)
        assertFalse(stored.hasSync)
    }

    @Test
    fun `a dive without profiles says so`() {
        insert("dive-1", item("dive-1"))
        store.upsertRecording(recording("dive-1"))

        assertTrue(logbook.dive("dive-1")!!.profilePending)

        store.saveCheckpoint(SyncCheckpoint("profiles:dive-1", "profiles", done = true, updatedAt = NOW))

        assertFalse(logbook.dive("dive-1")!!.profilePending)
    }

    @Test
    fun `samples come from the stored series`() {
        store.upsertSeries(
            RecordingSeries("recording-1", "depth", "[0,1000]", "[0,12.5]", NOW),
        )
        store.upsertSeries(
            RecordingSeries("recording-1", "temperature", "[0,1000]", "[21.0,20.5]", NOW),
        )

        val samples = logbook.samples("recording-1")

        assertNotNull(samples)
        assertEquals(listOf("depth", "temperature"), samples.series.map { it.channel })
        assertEquals(listOf(0, 1000), samples.series.first().offsetsMs)
        assertEquals(listOf(0.0, 12.5), samples.series.first().values)
        assertNull(logbook.samples("missing"))
    }

    @Test
    fun `neighbours follow the stored order and the applied filter`() {
        insert("new", item("new", startsAt = "2026-11-03T10:00:00.000Z"))
        insert("middle", item("middle", startsAt = "2026-11-02T10:00:00.000Z"))
        insert("old", item("old", startsAt = "2026-11-01T10:00:00.000Z", recordings = 0))

        val all = logbook.neighbours("middle", emptySet())
        val typedOnly = logbook.neighbours("old", setOf(LogbookFilter.NO_RECORDING))
        val absent = logbook.neighbours("middle", setOf(LogbookFilter.NO_RECORDING))

        assertEquals("new", all.previous?.id)
        assertEquals("old", all.next?.id)
        assertEquals(2, all.position)
        assertEquals(3, all.total)
        assertNull(typedOnly.previous)
        assertNull(typedOnly.next)
        assertEquals(1, typedOnly.position)
        assertNull(absent.position)
    }

    @Test
    fun `server-only filters are named as needing the API`() {
        assertTrue(logbook.canAnswer(emptySet()))
        assertTrue(logbook.canAnswer(setOf(LogbookFilter.NO_RECORDING, LogbookFilter.WITH_FINDINGS)))
        assertFalse(logbook.canAnswer(setOf(LogbookFilter.CHANGED_SINCE_SENT)))
        assertFalse(logbook.canAnswer(setOf(LogbookFilter.NOT_AT_PROVIDER)))
    }

    private fun insert(
        diveId: String,
        list: DiveListViewDivesItem,
        detail: DiveView? = null,
        startsAt: String = list.startsAt,
        duration: Long = list.durationSeconds.toLong(),
        depth: Double? = list.maxDepthM,
    ) {
        val listRow = list.copy(durationSeconds = duration.toDouble())
        store.upsertDive(
            Dive(
                id = diveId,
                diverId = "diver-1",
                version = detail?.version?.toLong(),
                startsAt = startsAt,
                durationSeconds = duration,
                maxDepthM = depth,
                siteId = listRow.site?.id,
                utcOffsetSeconds = listRow.utcOffsetSeconds?.toLong(),
                utcOffsetSource = listRow.utcOffsetSource,
                listJson = JSON.encodeToString(DiveListViewDivesItem.serializer(), listRow),
                detailJson = detail?.let { JSON.encodeToString(DiveView.serializer(), it) },
            ),
        )
    }

    private fun item(
        id: String,
        startsAt: String = "2026-11-01T10:00:00.000Z",
        recordings: Int = 1,
        findings: Int = 0,
        site: String? = "Reef",
    ) = DiveListViewDivesItem(
        id = id,
        diverId = "diver-1",
        number = 1,
        startsAt = startsAt,
        utcOffsetSeconds = 3_600,
        utcOffsetSource = "device",
        durationSeconds = 3_000.0,
        maxDepthM = 20.0,
        avgDepthM = 12.0,
        site = site?.let { DiveListViewDivesItemSite("site-1", it) },
        findings = findings,
        recordings = recordings,
        fromProvider = null,
        gases = emptyList(),
        surfaceIntervalSeconds = null,
        participants = emptyList(),
        waiting = emptyList(),
        profile = null,
    )

    private fun dive(id: String, number: Int, startsAt: String) = DiveView(
        id = id,
        diverId = "diver-1",
        version = 3,
        values = DiveViewValues(
            number = number,
            startsAt = DiveViewValuesStartsAt(startsAt, 3_600),
            durationSeconds = 3_000.0,
            maxDepthM = 20.0,
            avgDepthM = 12.0,
            waterTemperatureC = 18.0,
        ),
        overrides = listOf("maxDepthM"),
        utcOffsetSource = "device",
        fromProvider = null,
        fromRecording = null,
        notes = "A good dive",
        site = null,
        waterType = "salt",
        waterMismatch = null,
        position = null,
        recordings = emptyList(),
        cylinders = emptyList(),
        lead = emptyList(),
        leadTotalKg = null,
        weightingFeedback = null,
        suit = null,
        sac = null,
        sacMissing = null,
        participants = emptyList(),
        verificationCodes = emptyList(),
    )

    private fun recording(diveId: String) = Recording(
        id = "recording-$diveId",
        diveId = diveId,
        isPrimary = true,
        startsAt = "2026-11-01T10:00:00.000Z",
        durationSeconds = 3_000,
        parser = "fit",
        summaryJson = JSON.encodeToString(DiveViewRecordingsItemSummary.serializer(), DiveViewRecordingsItemSummary(gfHigh = 80.0)),
        channelsJson = """["depth"]""",
    )

    private companion object {
        const val NOW = "2026-11-18T12:00:00.000Z"
    }
}
