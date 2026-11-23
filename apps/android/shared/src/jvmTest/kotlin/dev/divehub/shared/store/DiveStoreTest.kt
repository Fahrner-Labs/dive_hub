package dev.divehub.shared.store

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.store.db.DiveHubDatabase
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFailsWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DiveStoreTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)

    @AfterTest
    fun close() = driver.close()

    private fun newStore(): DiveStore = DiveStore.create(driver)

    private fun testDive(
        id: String = "dive-1",
        diverId: String = "diver-1",
        startsAt: String = "2026-11-01T10:00:00.000Z",
        version: Long? = 1,
        deletedAt: String? = null,
    ) = Dive(
        id = id,
        diverId = diverId,
        version = version,
        deletedAt = deletedAt,
        startsAt = startsAt,
        utcOffsetSeconds = 3_600,
        utcOffsetSource = "device",
        durationSeconds = 2_700,
        maxDepthM = 31.4,
        avgDepthM = 17.2,
        siteId = "site-1",
        waterType = "salt",
        fromProvider = "ssi",
        notes = "A good dive",
        suitJson = """{"type":"wetsuit","thicknessMm":5}""",
        weightingFeedbackJson = """{"felt":"right"}""",
        leadJson = """[{"amountKg":4,"placement":"belt"}]""",
        leadTotalKg = 4.0,
        sacLitresPerMinute = 16.5,
        sacBarPerMinute = 1.8,
        sacMissing = null,
        number = 42,
        findingsCount = 2,
        summaryJson = """{"diveMode":"scuba"}""",
    )

    @Test
    fun `schema has every table`() {
        assertEquals(
            setOf(
                "body_weight", "cylinder", "diagnosticEntry", "dive", "dive_site", "diver", "pending_write",
                "recording", "recording_series", "sync_checkpoint", "sync_state",
            ),
            DiveHubDatabase.allTableNames().toSet(),
        )
    }

    @Test
    fun `dive round trips every column`() {
        val store = newStore()
        val expected = testDive()

        store.upsertDive(expected)

        assertEquals(expected, store.dive(expected.id))
    }

    @Test
    fun `dive upsert replaces the row wholesale when its version changes`() {
        val store = newStore()
        store.upsertDive(testDive(version = 1))

        store.upsertDive(testDive(version = 2, startsAt = "2026-11-01T11:00:00.000Z").copy(notes = "Edited"))

        val row = store.dive("dive-1")!!
        assertEquals(2L, row.version)
        assertEquals("Edited", row.notes)
        assertEquals("2026-11-01T11:00:00.000Z", row.startsAt)
        assertEquals(1L, store.diveCount())
    }

    @Test
    fun `the logbook page is newest first, paged, and hides tombstones`() {
        val store = newStore()
        store.upsertDive(testDive(id = "old", startsAt = "2026-10-01T10:00:00.000Z"))
        store.upsertDive(testDive(id = "new", startsAt = "2026-11-02T10:00:00.000Z"))
        store.upsertDive(testDive(id = "middle", startsAt = "2026-10-20T10:00:00.000Z"))
        store.upsertDive(
            testDive(id = "gone", startsAt = "2026-11-03T10:00:00.000Z", deletedAt = "2026-11-04T10:00:00.000Z"),
        )

        assertEquals(listOf("new", "middle"), store.dives(limit = 2).map(Dive::id))
        assertEquals(listOf("old"), store.dives(limit = 2, offset = 2).map(Dive::id))
        assertEquals(listOf("gone", "new", "middle", "old"), store.allDives().map(Dive::id))
    }

    @Test
    fun `dives with the same start time break ties by id descending`() {
        val store = newStore()
        store.upsertDive(testDive(id = "a", startsAt = "2026-11-01T10:00:00.000Z"))
        store.upsertDive(testDive(id = "b", startsAt = "2026-11-01T10:00:00.000Z"))

        assertEquals(listOf("b", "a"), store.dives().map(Dive::id))
        assertEquals(listOf("b", "a"), store.allDives().map(Dive::id))
    }

    @Test
    fun `counts separate live rows from tombstones`() {
        val store = newStore()
        store.upsertDive(testDive(id = "live"))
        store.upsertDive(testDive(id = "gone", deletedAt = "2026-11-04T10:00:00.000Z"))

        assertEquals(1L, store.activeDiveCount())
        assertEquals(2L, store.diveCount())
        assertNull(store.dive("gone"))
        assertNotNull(store.diveIncludingDeleted("gone"))
    }

    @Test
    fun `recordings list the primary one first`() {
        val store = newStore()
        store.upsertRecording(recording(id = "part", isPrimary = false, startsAt = "2026-11-01T10:30:00.000Z"))
        store.upsertRecording(recording(id = "primary", isPrimary = true))

        assertEquals(listOf("primary", "part"), store.recordings("dive-1").map(Recording::id))
    }

    @Test
    fun `recordings hide tombstones from the logbook read`() {
        val store = newStore()
        store.upsertRecording(recording(id = "gone").copy(deletedAt = "2026-11-04T10:00:00.000Z"))
        store.upsertRecording(recording(id = "live"))

        assertEquals(listOf("live"), store.recordings("dive-1").map(Recording::id))
        assertNull(store.recording("gone"))
        assertNotNull(store.recordingIncludingDeleted("gone"))
        assertEquals(2, store.allRecordings().size)
    }

    @Test
    fun `recording upsert replaces the row and keeps its JSON payloads`() {
        val store = newStore()
        store.upsertRecording(recording())
        store.upsertRecording(
            recording().copy(parser = "subsurface", channelsJson = """["depth","temperature"]"""),
        )

        val row = store.recording("recording-1")!!
        assertEquals("subsurface", row.parser)
        assertEquals("""["depth","temperature"]""", row.channelsJson)
        assertEquals(1, store.allRecordings().size)
    }

    @Test
    fun `series round trip per channel and replace the same channel`() {
        val store = newStore()
        store.upsertSeries(series("depth", """[0,1000]""", """[0,1500]"""))
        store.upsertSeries(series("temperature", """[0,1000]""", """[2100,2090]"""))

        assertEquals(listOf("depth", "temperature"), store.series("recording-1").map(RecordingSeries::channel))
        assertEquals("""[0,1500]""", store.series("recording-1", "depth")!!.valuesJson)

        store.upsertSeries(series("depth", """[0,2000]""", """[0,1400]"""))

        assertEquals(2, store.allSeries().size)
        assertEquals("""[0,1400]""", store.series("recording-1", "depth")!!.valuesJson)
        assertNull(store.series("recording-1", "pressure"))
    }

    @Test
    fun `cylinders keep their order`() {
        val store = newStore()
        store.upsertCylinder(cylinder(position = 2, volumeL = 11.1))
        store.upsertCylinder(cylinder(position = 1, volumeL = 12.0, fromPod = true))

        assertEquals(listOf(1L, 2L), store.cylinders("dive-1").map(Cylinder::position))
        assertTrue(store.cylinders("dive-1").first().fromPod)
    }

    @Test
    fun `dive sites round trip, with live rows separate from tombstones`() {
        val store = newStore()
        val site = DiveSite(
            id = "site-1",
            version = 7,
            name = "Vobster Quay",
            waterType = "fresh",
            positionJson = """{"lat":51.2,"lng":-2.4}""",
            countryCode = "GB",
            mergedInto = "site-2",
            deletedAt = "2026-11-04T10:00:00.000Z",
        )
        val live = DiveSite(id = "site-2", name = "Live Site")

        store.upsertDiveSite(site)
        store.upsertDiveSite(live)

        assertEquals(site, store.diveSiteIncludingDeleted(site.id))
        assertNull(store.diveSite(site.id))
        assertEquals(listOf(live), store.diveSites())
        assertEquals(listOf(live, site), store.allDiveSites())
    }

    @Test
    fun `divers round trip their flags, with live rows separate from tombstones`() {
        val store = newStore()
        val diver = Diver(
            id = "diver-1",
            name = "Ada",
            isOwn = true,
            external = false,
            apneaCounts = true,
            divesBefore = 120,
            mergedInto = "diver-2",
            deletedAt = "2026-11-04T10:00:00.000Z",
        )
        val live = Diver(
            id = "diver-2",
            name = "Bob",
            isOwn = false,
            external = false,
            apneaCounts = false,
            divesBefore = 0,
        )

        store.upsertDiver(diver)
        store.upsertDiver(live)

        assertEquals(diver, store.diverIncludingDeleted(diver.id))
        assertNull(store.diver(diver.id))
        assertEquals(listOf(live), store.divers())
        assertEquals(listOf(diver, live), store.allDivers())
    }

    @Test
    fun `body weights round trip in date order`() {
        val store = newStore()
        store.upsertBodyWeight(BodyWeight("diver-1", "2026-11-02", 74.5))
        store.upsertBodyWeight(BodyWeight("diver-1", "2026-10-01", 75.2))
        store.upsertBodyWeight(BodyWeight("diver-1", "2026-10-01", 75.0))

        assertEquals(listOf(75.0, 74.5), store.bodyWeights("diver-1").map(BodyWeight::kg))
        assertEquals(2, store.allBodyWeights().size)
    }

    @Test
    fun `checkpoints resume per entity and phase`() {
        val store = newStore()
        store.saveCheckpoint(
            SyncCheckpoint("dives", "meta", pageOffset = 400, updatedAt = "2026-11-18T12:00:00.000Z"),
        )
        store.saveCheckpoint(
            SyncCheckpoint("dives", "details", cursor = "dive-40", done = true, updatedAt = "2026-11-18T12:01:00.000Z"),
        )

        assertEquals(400L, store.checkpoint("dives", "meta")!!.pageOffset)
        assertTrue(store.checkpoint("dives", "details")!!.done)
        assertNull(store.checkpoint("sites", "meta"))
        assertEquals(2, store.checkpoints().size)
    }

    @Test
    fun `sync state defaults to the schema version and then persists`() {
        val store = newStore()

        assertEquals(SyncState(schemaVersion = DiveStore.schemaVersion), store.syncState())

        store.saveSyncState(
            SyncState(
                lastFullSyncAt = "2026-11-18T12:00:00.000Z",
                lastHintAt = "2026-11-18T12:05:00.000Z",
                schemaVersion = DiveStore.schemaVersion,
            ),
        )

        assertEquals("2026-11-18T12:00:00.000Z", store.syncState().lastFullSyncAt)
    }

    @Test
    fun `the feed cursor persists beside the rest of the sync state and a wipe clears it`() {
        val store = newStore()
        store.saveSyncState(
            SyncState(
                lastFullSyncAt = "2026-11-18T12:00:00.000Z",
                schemaVersion = DiveStore.schemaVersion,
                feedCursor = "42:7",
            ),
        )

        assertEquals("42:7", store.syncState().feedCursor)

        store.wipe()

        assertNull(store.syncState().feedCursor)
    }

    @Test
    fun `site and Diver tombstones hide the row without removing it`() {
        val store = newStore()
        store.upsertDiveSite(DiveSite(id = "site-1", name = "Site"))
        store.upsertDiver(Diver("diver-1", "Ada", isOwn = true, external = false, apneaCounts = true, divesBefore = 0))

        store.markDiveSiteDeleted("site-1", "2026-11-18T12:00:00.000Z")
        store.markDiverDeleted("diver-1", "2026-11-18T12:00:00.000Z")

        assertEquals(0, store.diveSites().size)
        assertEquals("2026-11-18T12:00:00.000Z", store.diveSiteIncludingDeleted("site-1")!!.deletedAt)
        assertEquals(0, store.divers().size)
        assertEquals("2026-11-18T12:00:00.000Z", store.diverIncludingDeleted("diver-1")!!.deletedAt)
    }

    @Test
    fun `wipe clears every table and resets the sync state`() {
        val store = newStore()
        store.upsertDive(testDive())
        store.upsertRecording(recording())
        store.upsertSeries(series("depth", "[]", "[]"))
        store.upsertCylinder(cylinder(position = 1))
        store.upsertDiveSite(DiveSite(id = "site-1", name = "Site"))
        store.upsertDiver(Diver("diver-1", "Ada", isOwn = true, external = false, apneaCounts = true, divesBefore = 0))
        store.upsertBodyWeight(BodyWeight("diver-1", "2026-11-02", 74.5))
        store.saveCheckpoint(SyncCheckpoint("dives", "meta", pageOffset = 200, updatedAt = "2026-11-18T12:00:00.000Z"))
        store.saveSyncState(SyncState("2026-11-18T12:00:00.000Z", null, DiveStore.schemaVersion))

        store.wipe()

        assertEquals(0L, store.diveCount())
        assertEquals(0, store.allDives().size)
        assertEquals(0, store.allRecordings().size)
        assertEquals(0, store.allSeries().size)
        assertEquals(0, store.allCylinders().size)
        assertEquals(0, store.diveSites().size)
        assertEquals(0, store.divers().size)
        assertEquals(0, store.allBodyWeights().size)
        assertEquals(0, store.checkpoints().size)
        assertEquals(SyncState(schemaVersion = DiveStore.schemaVersion), store.syncState())
    }

    @Test
    fun `a transaction writes a page and its checkpoint, and rolls both back on failure`() {
        val store = newStore()

        store.transaction {
            upsertDive(testDive(id = "page-1"))
            saveCheckpoint(
                SyncCheckpoint("dives", "meta", pageOffset = 200, updatedAt = "2026-11-18T12:00:00.000Z"),
            )
        }
        assertEquals(1L, store.diveCount())
        assertEquals(200L, store.checkpoint("dives", "meta")!!.pageOffset)

        assertFailsWith<IllegalStateException> {
            store.transaction {
                upsertDive(testDive(id = "page-2"))
                saveCheckpoint(
                    SyncCheckpoint("dives", "meta", pageOffset = 400, updatedAt = "2026-11-18T12:01:00.000Z"),
                )
                throw IllegalStateException("simulated crash before the checkpoint")
            }
        }

        assertEquals(1L, store.diveCount())
        assertEquals(200L, store.checkpoint("dives", "meta")!!.pageOffset)
    }

    @Test
    fun `the store keeps the schema version in sync state, not the caller`() {
        val store = newStore()

        store.saveSyncState(SyncState(lastFullSyncAt = "2026-11-18T12:00:00.000Z", schemaVersion = 999))

        assertEquals(DiveStore.schemaVersion, store.syncState().schemaVersion)
    }

    @Test
    fun `opening a store reconciles a stale schema version in sync state`() {
        val store = newStore()
        store.saveSyncState(SyncState(lastFullSyncAt = "2026-11-18T12:00:00.000Z", schemaVersion = DiveStore.schemaVersion))
        driver.execute(null, "UPDATE sync_state SET schemaVersion = 999", 0)

        val reopened = DiveStore.open(driver)

        assertEquals(DiveStore.schemaVersion, reopened.syncState().schemaVersion)
    }

    @Test
    fun `migration from v1 adds the API payload columns and keeps the store usable`() {
        assertEquals(5L, DiveStore.schemaVersion)
        assertContains(DiveHubDatabase.allTableNames(), "sync_checkpoint")

        val old = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        try {
            old.execute(
                null,
                """CREATE TABLE dive (
                    id TEXT NOT NULL PRIMARY KEY,
                    diverId TEXT NOT NULL,
                    version INTEGER,
                    deletedAt TEXT,
                    startsAt TEXT NOT NULL,
                    utcOffsetSeconds INTEGER,
                    utcOffsetSource TEXT,
                    durationSeconds INTEGER,
                    maxDepthM REAL,
                    avgDepthM REAL,
                    siteId TEXT,
                    waterType TEXT,
                    fromProvider TEXT,
                    notes TEXT,
                    suitJson TEXT,
                    weightingFeedbackJson TEXT,
                    leadJson TEXT,
                    leadTotalKg REAL,
                    sacLitresPerMinute REAL,
                    sacBarPerMinute REAL,
                    sacMissing TEXT,
                    number INTEGER,
                    findingsCount INTEGER,
                    summaryJson TEXT
                )""",
                0,
            )
            old.execute(
                null,
                "CREATE TABLE sync_state (id INTEGER NOT NULL PRIMARY KEY, lastFullSyncAt TEXT, lastHintAt TEXT, schemaVersion INTEGER NOT NULL)",
                0,
            )
            old.execute(
                null,
                "INSERT INTO dive(id, diverId, startsAt, durationSeconds) VALUES ('dive-1', 'diver-1', '2026-11-01T10:00:00.000Z', 2700)",
                0,
            )
            old.execute(null, "INSERT INTO sync_state(id, lastFullSyncAt, lastHintAt, schemaVersion) VALUES (1, NULL, NULL, 1)", 0)

            DiveStore.migrate(old, oldVersion = 1, newVersion = DiveStore.schemaVersion)
            val migrated = DiveStore.open(old)

            assertEquals("dive-1", migrated.dive("dive-1")!!.id)
            assertNull(migrated.dive("dive-1")!!.listJson)
            assertEquals(DiveStore.schemaVersion, migrated.syncState().schemaVersion)
            assertEquals(0L, migrated.pendingWriteCount())
        } finally {
            old.close()
        }
    }

    private fun recording(
        id: String = "recording-1",
        isPrimary: Boolean = true,
        startsAt: String = "2026-11-01T10:00:00.000Z",
    ) = Recording(
        id = id,
        diveId = "dive-1",
        isPrimary = isPrimary,
        deviceJson = """{"manufacturer":"Suunto","product":"EON Steel","serialNumber":"123"}""",
        startsAt = startsAt,
        durationSeconds = 2_700,
        maxDepthM = 31.4,
        parser = "suunto",
        partOf = null,
        summaryJson = """{"gfHigh":80,"cnsEnd":12,"otuEnd":34,"sacLpm":16.5,"waterType":"salt"}""",
        channelsJson = """["depth"]""",
    )

    private fun series(channel: String, offsets: String, values: String) = RecordingSeries(
        recordingId = "recording-1",
        channel = channel,
        offsetsMsJson = offsets,
        valuesJson = values,
        fetchedAt = "2026-11-18T12:00:00.000Z",
    )

    private fun cylinder(position: Long, volumeL: Double? = 12.0, fromPod: Boolean = false) = Cylinder(
        diveId = "dive-1",
        position = position,
        volumeL = volumeL,
        material = "steel",
        o2 = 21.0,
        he = 0.0,
        startPressureBar = 200.0,
        endPressureBar = 70.0,
        fromPod = fromPod,
        seriesJson = null,
    )
}
