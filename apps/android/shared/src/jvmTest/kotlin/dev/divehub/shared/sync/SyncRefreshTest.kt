package dev.divehub.shared.sync

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.store.DiveStore
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private const val RefreshNow = "2026-11-19T10:00:00Z"
private val RefreshJson = Json { ignoreUnknownKeys = true; explicitNulls = false }

private sealed interface RefreshReply {
    data class Body(val text: String, val status: HttpStatusCode = HttpStatusCode.OK) : RefreshReply
    data class Status(val status: HttpStatusCode) : RefreshReply
    data object Offline : RefreshReply
    data class Hold(val gate: CompletableDeferred<Unit>) : RefreshReply
}

private fun refreshJson(text: String): RefreshReply = RefreshReply.Body(text)

private class RefreshHarness(
    val store: DiveStore,
    private val handler: (String) -> RefreshReply,
    val clock: () -> String = { RefreshNow },
) {
    val paths = mutableListOf<String>()
    val delays = mutableListOf<Int>()
    var unauthorized = 0

    private val api = ApiClient(
        engine = MockEngine { request ->
            val path = request.refreshPathWithQuery()
            paths += path
            when (val reply = handler(path)) {
                is RefreshReply.Body -> respond(reply.text, reply.status, headersOf(HttpHeaders.ContentType, "application/json"))
                is RefreshReply.Status -> respond("", reply.status, headersOf(HttpHeaders.ContentType, "application/json"))
                RefreshReply.Offline -> throw kotlinx.io.IOException("offline")
                is RefreshReply.Hold -> {
                    reply.gate.await()
                    respond("", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
                }
            }
        },
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = { unauthorized += 1 },
        json = RefreshJson,
    ).apply { baseUrl = "https://dive.example" }

    val engine = SyncEngine(api, store, clock = clock, backoff = { delays += it })
    val refresher = SyncRefresher(engine, live = null)
}

private fun io.ktor.client.request.HttpRequestData.refreshPathWithQuery(): String {
    val query = url.encodedQuery
    return url.encodedPath + if (query.isEmpty()) "" else "?$query"
}

private fun refreshQuery(path: String, name: String): String? =
    path.substringAfter('?', "").split('&').firstOrNull { it.startsWith("$name=") }?.substringAfter('=')

private fun refreshDiverJson(id: String = "diver-1", name: String = "Ada"): String =
    """{"id":"$id","name":"$name","isOwn":true,"divesBefore":0,"apneaCounts":true,"diveCount":1,
    "deviceCount":0,"mutedRules":[],"certifications":{"count":0,"newest":null,"expired":0,"expiringSoon":0}}"""

private fun refreshWeightsJson(): String = """[{"date":"2026-01-01","kg":75.5}]"""

private fun refreshSiteJson(id: String = "site-1", name: String = "Reef"): String =
    """{"id":"$id","name":"$name","position":null,"country":"DE","waterBody":null,"description":null,
    "maxDepthM":30,"waterType":"salt","externalIds":[],"version":1,"diveCount":1,"inUse":true,
    "canDelete":false,"mergedInto":null}"""

private fun refreshDiveListItem(
    id: String = "dive-1",
    startsAt: String = "2026-11-01T10:00:00Z",
    number: Int = 1,
    findings: Int = 1,
): String =
    """{"id":"$id","diverId":"diver-1","number":$number,"startsAt":"$startsAt","utcOffsetSeconds":3600,
    "utcOffsetSource":"device","durationSeconds":2700,"maxDepthM":31.4,"avgDepthM":17.2,
    "site":{"id":"site-1","name":"Reef"},"findings":$findings,"recordings":1,"fromProvider":"ssi",
    "gases":[],"surfaceIntervalSeconds":null,"participants":[],"waiting":[],"profile":null}"""

private fun refreshDivePage(items: List<String>): String =
    """{"dives":[${items.joinToString(",")}],"total":${items.size},
    "counts":{"noRecording":0,"noSite":0,"withFindings":0,"notAtProvider":0,"changedSinceSent":0},
    "totals":{"dives":${items.size},"durationSeconds":0,"deepestM":null,"lastDiveAt":null},"months":[]}"""

private fun refreshSitePage(items: List<String>): String =
    """{"sites":[${items.joinToString(",")}],"total":${items.size}}"""

private fun refreshRecordingJson(id: String, primary: Boolean = true, channels: List<String> = listOf("depth", "temperature")): String =
    """{"id":"$id","isPrimary":$primary,
    "device":{"manufacturer":"Garmin","product":"Descent","serialNumber":"777"},
    "startsAt":"2026-11-01T10:00:00Z","durationSeconds":3000,"maxDepthM":24.3,"parser":"fit","partOf":null,
    "summary":{"gfHigh":80,"cnsEnd":12,"otuEnd":34,"sacLpm":16.5,"waterType":"salt"},
    "channels":${channels.joinToString(",", "[", "]") { "\"$it\"" }}}"""

private fun refreshDetailJson(
    id: String = "dive-1",
    startsAt: String = "2026-11-01T10:00:00Z",
    version: Int = 3,
    notes: String = "A good dive",
    recordings: List<String> = listOf(refreshRecordingJson("recording-$id")),
): String =
    """{"id":"$id","diverId":"diver-1","version":$version,
    "values":{"number":42,"startsAt":{"at":"$startsAt","utcOffsetSeconds":3600},
    "durationSeconds":3000,"maxDepthM":24.3,"avgDepthM":12,"waterTemperatureC":18},
    "overrides":[],"utcOffsetSource":"device","fromProvider":"ssi","notes":"$notes",
    "recordings":[${recordings.joinToString(",")}],
    "cylinders":[{"volumeL":12,"workingPressureBar":232,"material":"steel","gas":{"o2":21,"he":0},
      "startPressureBar":200,"endPressureBar":50,"fromPod":false,"series":null}],
    "lead":[{"amountKg":4,"placement":"belt"}],"leadTotalKg":4,
    "participants":[],"verificationCodes":[]}"""

private fun refreshSamplesJson(recordingId: String, channels: List<String>, peak: Double = 31.4): String =
    """{"recordingId":"$recordingId","series":[${channels.joinToString(",") {
        """{"channel":"$it","offsetsMs":[0,2000,4000],"values":[0.0,${peak},0.0]}"""
    }}]}"""

private fun refreshDeletedJson(id: String, deletedAt: String = "2026-11-19T09:00:00Z"): String =
    """{"id":"$id","diverId":"diver-1","version":4,"number":1,"startsAt":"2026-11-01T10:00:00Z",
    "utcOffsetSeconds":3600,"utcOffsetSource":"device","durationSeconds":2700,"maxDepthM":31.4,
    "site":null,"deletedAt":"$deletedAt","mergedInto":null,"movedTo":null,"continuedInto":null,
    "appendedWith":[],"stillAt":[],"waiting":[]}"""

private object RefreshData {
    val divers = mutableListOf(refreshDiverJson())
    val dives = mutableListOf(refreshDiveListItem())
    val sites = mutableListOf(refreshSiteJson())
    val details = mutableMapOf("dive-1" to refreshDetailJson())
    val samplePeak = mutableMapOf("recording-dive-1" to 31.4)
    var deletedPages: List<Pair<List<String>, String?>> = emptyList()
}

private fun resetRefreshData() {
    RefreshData.divers.clear()
    RefreshData.divers += refreshDiverJson()
    RefreshData.dives.clear()
    RefreshData.dives += refreshDiveListItem()
    RefreshData.sites.clear()
    RefreshData.sites += refreshSiteJson()
    RefreshData.details.clear()
    RefreshData.details["dive-1"] = refreshDetailJson()
    RefreshData.samplePeak.clear()
    RefreshData.samplePeak["recording-dive-1"] = 31.4
    RefreshData.deletedPages = emptyList()
}

private fun refreshPathId(path: String): String = path.substringAfterLast('/').substringBefore('?')

private fun refreshRoutes(path: String): RefreshReply = when {
    path == "/api/divers" -> refreshJson("[${RefreshData.divers.joinToString(",")}]")
    path.startsWith("/api/divers/") && path.endsWith("/body-weights") -> refreshJson(refreshWeightsJson())
    path.startsWith("/api/dive-sites?") -> {
        val offset = refreshQuery(path, "offset")?.toInt() ?: 0
        val limit = refreshQuery(path, "limit")?.toInt() ?: 200
        refreshJson(refreshSitePage(RefreshData.sites.drop(offset).take(limit)))
    }
    path.startsWith("/api/dive-sites/") -> refreshJson(RefreshData.sites.first { it.contains("\"id\":\"${refreshPathId(path)}\"") })
    path.startsWith("/api/dives/deleted?") -> {
        val cursor = refreshQuery(path, "cursor")
        val index = if (cursor == null) 0 else cursor.removePrefix("c").toInt()
        val (ids, next) = RefreshData.deletedPages.getOrNull(index) ?: (emptyList<String>() to null)
        refreshJson("""{"dives":[${ids.joinToString(",") { refreshDeletedJson(it) }}],"total":${ids.size},""" +
            (next?.let { "\"nextCursor\":\"$it\"," } ?: "") + """"stillAt":[]}""")
    }
    path.startsWith("/api/dives?") -> {
        val offset = refreshQuery(path, "offset")?.toInt() ?: 0
        val limit = refreshQuery(path, "limit")?.toInt() ?: 200
        refreshJson(refreshDivePage(RefreshData.dives.drop(offset).take(limit)))
    }
    path.startsWith("/api/recordings/") && path.contains("/samples?") -> {
        val id = path.removePrefix("/api/recordings/").substringBefore('/')
        val channels = refreshQuery(path, "channels")?.split(',')?.filter { it.isNotEmpty() }.orEmpty()
        refreshJson(refreshSamplesJson(id, channels, RefreshData.samplePeak[id] ?: 31.4))
    }
    path.startsWith("/api/dives/") -> refreshJson(RefreshData.details[refreshPathId(path)] ?: refreshDetailJson(refreshPathId(path)))
    else -> error("unexpected $path")
}

class SyncRefreshTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)

    @AfterTest
    fun close() {
        driver.close()
        resetRefreshData()
    }

    private suspend fun seeded(handler: (String) -> RefreshReply = ::refreshRoutes): RefreshHarness {
        val harness = RefreshHarness(store, handler)
        harness.engine.sync()
        harness.paths.clear()
        return harness
    }

    @Test
    fun `an opened Dive's refresh rereads its detail and marks its profiles pending`() = runTest {
        val harness = seeded()
        RefreshData.details["dive-1"] = refreshDetailJson(version = 4, notes = "Changed elsewhere")

        harness.refresher.refreshDiveOnce("dive-1")

        assertEquals(4L, store.dive("dive-1")!!.version)
        assertEquals("Changed elsewhere", store.dive("dive-1")!!.notes)
        assertFalse(harness.engine.profileSynced("dive-1"))
    }

    @Test
    fun `a no-answer refresh marks offline and a later success clears it`() = runTest {
        var offline = false
        val harness = seeded { path -> if (offline) RefreshReply.Offline else refreshRoutes(path) }

        offline = true
        harness.refresher.refreshOnce()
        assertTrue(harness.refresher.offline.value)

        offline = false
        harness.refresher.refreshOnce()
        assertFalse(harness.refresher.offline.value)
    }

    @Test
    fun `profiles fetch every listed channel with the explicit cap`() = runTest {
        RefreshData.details["dive-1"] = refreshDetailJson(recordings = listOf(refreshRecordingJson("recording-dive-1", channels = listOf("depth", "temperature", "tankPressure"))))
        val harness = seeded()

        assertEquals(SyncResult.Complete, harness.engine.syncProfiles())

        assertEquals(
            listOf("/api/recordings/recording-dive-1/samples?channels=depth,temperature,tankPressure&maxPoints=20000"),
            harness.paths,
        )
        assertEquals(listOf("depth", "tankPressure", "temperature"), store.series("recording-dive-1").map { it.channel })
        assertTrue(store.series("recording-dive-1", "depth")!!.offsetsMsJson.contains("2000"))
        assertTrue(harness.engine.profileSynced("dive-1"))
        val progress = harness.engine.progress()
        assertTrue(progress.profilesDone)
        assertEquals(0L, progress.divesAwaitingProfiles)
    }

    @Test
    fun `profiles resume at the next recording after a failure`() = runTest {
        RefreshData.details["dive-1"] = refreshDetailJson(
            recordings = listOf(refreshRecordingJson("recording-a"), refreshRecordingJson("recording-b", primary = false)),
        )
        var failing = 2
        val harness = seeded { path ->
            if (path.startsWith("/api/recordings/recording-b/samples") && failing > 0) {
                failing -= 1
                RefreshReply.Status(HttpStatusCode.InternalServerError)
            } else {
                refreshRoutes(path)
            }
        }

        val first = harness.engine.syncProfiles()

        assertIs<SyncResult.Stopped>(first)
        assertEquals(SyncPhase.Profiles, first.phase)
        assertNotNull(store.series("recording-a"))
        assertTrue(store.series("recording-b").isEmpty())
        assertEquals(1L, store.checkpoint("profiles:dive-1", "profiles")!!.pageOffset)
        assertFalse(harness.engine.profileSynced("dive-1"))

        harness.paths.clear()
        assertEquals(SyncResult.Complete, harness.engine.syncProfiles())

        assertTrue(harness.paths.none { it.startsWith("/api/recordings/recording-a/samples") })
        assertTrue(harness.paths.any { it.startsWith("/api/recordings/recording-b/samples") })
        assertNotNull(store.series("recording-b"))
        assertTrue(harness.engine.profileSynced("dive-1"))
    }

    @Test
    fun `a dive without its detail is not marked profiles done`() = runTest {
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(
            id = "dive-2",
            startsAt = "2026-10-01T10:00:00Z",
            recordings = listOf(refreshRecordingJson("recording-dive-2")),
        )
        var detailFails = true
        val harness = RefreshHarness(store = store, handler = { path ->
            if (path == "/api/dives/dive-2" && detailFails) {
                RefreshReply.Status(HttpStatusCode.InternalServerError)
            } else {
                refreshRoutes(path)
            }
        })

        assertIs<SyncResult.Stopped>(harness.engine.sync())
        assertEquals(SyncResult.Complete, harness.engine.syncProfiles())
        assertFalse(harness.engine.profileSynced("dive-2"))
        assertTrue(harness.paths.none { it.startsWith("/api/recordings/recording-dive-2/samples") })

        detailFails = false
        harness.paths.clear()
        assertEquals(SyncResult.Complete, harness.engine.sync())
        assertEquals(SyncResult.Complete, harness.engine.syncProfiles())

        assertTrue(harness.paths.any { it.startsWith("/api/recordings/recording-dive-2/samples") })
        assertTrue(harness.engine.profileSynced("dive-2"))
    }

    @Test
    fun `profiles resume at the next dive after a failure`() = runTest {
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", recordings = listOf(refreshRecordingJson("recording-dive-2")))
        var failing = 2
        val harness = seeded { path ->
            if (path.startsWith("/api/recordings/recording-dive-2/samples") && failing > 0) {
                failing -= 1
                RefreshReply.Status(HttpStatusCode.InternalServerError)
            } else {
                refreshRoutes(path)
            }
        }

        assertIs<SyncResult.Stopped>(harness.engine.syncProfiles())
        assertTrue(harness.engine.profileSynced("dive-1"))
        assertFalse(harness.engine.profileSynced("dive-2"))

        harness.paths.clear()
        assertEquals(SyncResult.Complete, harness.engine.syncProfiles())

        assertTrue(harness.paths.none { it.startsWith("/api/recordings/recording-dive-1/samples") })
        assertTrue(harness.paths.any { it.startsWith("/api/recordings/recording-dive-2/samples") })
        assertTrue(harness.engine.profileSynced("dive-2"))
    }

    @Test
    fun `cancelling the profiles phase keeps the dives already done`() = runTest {
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", recordings = listOf(refreshRecordingJson("recording-dive-2")))
        val gate = CompletableDeferred<Unit>()
        val secondRequested = CompletableDeferred<Unit>()
        val harness = seeded { path ->
            if (path.startsWith("/api/recordings/recording-dive-2/samples")) {
                secondRequested.complete(Unit)
                RefreshReply.Hold(gate)
            } else {
                refreshRoutes(path)
            }
        }

        val job = launch { harness.engine.syncProfiles() }
        secondRequested.await()
        job.cancel(CancellationException("cancelled"))
        job.join()

        assertTrue(harness.engine.profileSynced("dive-1"))
        assertFalse(harness.engine.profileSynced("dive-2"))
        assertNotNull(store.series("recording-dive-1"))
        assertTrue(store.series("recording-dive-2").isEmpty())
    }

    @Test
    fun `deleted dives page by nextCursor into tombstones and are never hard-deleted`() = runTest {
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z")
        RefreshData.deletedPages = listOf(
            listOf("dive-1") to "c1",
            listOf("dive-2") to null,
        )
        val harness = seeded()

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertTrue(harness.paths.contains("/api/dives/deleted?limit=200"))
        assertTrue(harness.paths.contains("/api/dives/deleted?limit=200&cursor=c1"))
        assertEquals(2, store.allDives().size)
        assertEquals(0, store.dives().size)
        assertEquals("2026-11-19T09:00:00Z", store.diveIncludingDeleted("dive-1")!!.deletedAt)
        assertEquals("2026-11-19T09:00:00Z", store.diveIncludingDeleted("dive-2")!!.deletedAt)
        assertNotNull(store.recordings("dive-1"))
    }

    @Test
    fun `a failed tombstone page keeps its cursor for the next refresh`() = runTest {
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z")
        RefreshData.deletedPages = listOf(
            listOf("dive-1") to "c1",
            listOf("dive-2") to null,
        )
        var failing = 2
        val harness = seeded { path ->
            if (path.contains("/api/dives/deleted?") && refreshQuery(path, "cursor") == "c1" && failing > 0) {
                failing -= 1
                RefreshReply.Status(HttpStatusCode.InternalServerError)
            } else {
                refreshRoutes(path)
            }
        }

        assertIs<SyncResult.Stopped>(harness.engine.refresh())

        assertNotNull(store.diveIncludingDeleted("dive-1")!!.deletedAt)
        assertNull(store.diveIncludingDeleted("dive-2")!!.deletedAt)
        assertEquals("c1", store.checkpoint("deleted_dives", "tombstones")!!.cursor)
        assertFalse(store.checkpoint("deleted_dives", "tombstones")!!.done)

        harness.paths.clear()
        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertTrue(harness.paths.none { it.contains("/api/dives/deleted?") && refreshQuery(it, "cursor") == null })
        assertNotNull(store.diveIncludingDeleted("dive-2")!!.deletedAt)
    }

    @Test
    fun `a server without the deleted-dives route still refreshes the logbook`() = runTest {
        val harness = seeded { path ->
            if (path.startsWith("/api/dives/deleted?")) {
                RefreshReply.Status(HttpStatusCode.NotFound)
            } else {
                refreshRoutes(path)
            }
        }
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertTrue(harness.paths.any { it.startsWith("/api/dives/deleted?") })
        assertNotNull(store.dive("dive-2"))
    }

    @Test
    fun `a dive hint refetches list page one and the detail and marks profiles stale`() = runTest {
        val harness = seeded()
        harness.engine.syncProfiles()
        RefreshData.details["dive-1"] = refreshDetailJson(version = 4, notes = "Changed elsewhere")
        RefreshData.dives[0] = refreshDiveListItem(number = 9, findings = 3)
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.applyDiveHint("dive-1"))

        assertEquals(listOf("/api/dives?limit=200&offset=0", "/api/dives/dive-1"), harness.paths)
        assertEquals(4L, store.dive("dive-1")!!.version)
        assertEquals(42L, store.dive("dive-1")!!.number)
        assertEquals("Changed elsewhere", store.dive("dive-1")!!.notes)
        assertFalse(harness.engine.profileSynced("dive-1"))
        assertEquals(RefreshNow, store.syncState().lastHintAt)
        assertNotNull(store.series("recording-dive-1"))
    }

    @Test
    fun `a restored Dive on the server clears its local tombstone`() = runTest {
        val harness = seeded()
        store.markDiveDeleted("dive-1", "2026-11-18T00:00:00Z")
        assertEquals(0, store.dives().size)
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertNotNull(store.dive("dive-1"))
        assertNull(store.dive("dive-1")!!.deletedAt)
        assertEquals(1, store.dives().size)
    }

    @Test
    fun `a dive hint makes the profiles phase fetch that Dive again`() = runTest {
        val harness = seeded()
        harness.engine.syncProfiles()
        RefreshData.samplePeak["recording-dive-1"] = 40.0

        assertEquals(SyncResult.Complete, harness.engine.applyDiveHint("dive-1"))
        assertFalse(harness.engine.profileSynced("dive-1"))

        harness.paths.clear()
        harness.refresher.profilesOnce()

        assertTrue(harness.paths.any { it.startsWith("/api/recordings/recording-dive-1/samples") })
        assertTrue(store.series("recording-dive-1", "depth")!!.valuesJson.contains("40.0"))
        assertTrue(harness.engine.profileSynced("dive-1"))
    }

    @Test
    fun `a completed tombstone walk stops at the first unchanged page`() = runTest {
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z")
        RefreshData.deletedPages = listOf(
            listOf("dive-1") to "c1",
            listOf("dive-2") to null,
        )
        val harness = seeded()
        harness.engine.refresh()
        RefreshData.dives.clear()
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertEquals(1, harness.paths.count { it.startsWith("/api/dives/deleted?") })
        assertTrue(harness.paths.contains("/api/dives/deleted?limit=200"))
    }

    @Test
    fun `a detail refetch drops a Recording the server no longer lists`() = runTest {
        RefreshData.details["dive-1"] = refreshDetailJson(
            recordings = listOf(refreshRecordingJson("recording-a"), refreshRecordingJson("recording-b", primary = false)),
        )
        val harness = seeded()
        harness.engine.syncProfiles()
        assertNotNull(store.series("recording-b"))
        RefreshData.details["dive-1"] = refreshDetailJson(version = 4, recordings = listOf(refreshRecordingJson("recording-a")))

        assertEquals(SyncResult.Complete, harness.engine.applyDiveHint("dive-1"))

        assertNull(store.recordingIncludingDeleted("recording-b"))
        assertTrue(store.series("recording-b").isEmpty())
        assertNotNull(store.recordingIncludingDeleted("recording-a"))
    }

    @Test
    fun `a site hint refetches that site`() = runTest {
        val harness = seeded()
        RefreshData.sites[0] = refreshSiteJson(name = "New Reef")
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.applySiteHint("site-1"))

        assertEquals(listOf("/api/dive-sites/site-1"), harness.paths)
        assertEquals(listOf("New Reef"), store.diveSites().map { it.name })
        assertEquals(RefreshNow, store.syncState().lastHintAt)
    }

    @Test
    fun `refetch on connect reruns phase one and fetches details for new dives`() = runTest {
        val harness = seeded()
        RefreshData.divers[0] = refreshDiverJson(name = "Ada Lovelace")
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z")
        harness.paths.clear()

        harness.refresher.handle(LiveEvent.Connected)

        assertTrue(harness.paths.contains("/api/divers"))
        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=0"))
        assertTrue(harness.paths.contains("/api/dives/dive-2"))
        assertEquals(listOf("Ada Lovelace"), store.divers().map { it.name })
        assertEquals(3L, store.dive("dive-2")!!.version)
        assertEquals(RefreshNow, store.syncState().lastFullSyncAt)
        assertTrue(harness.refresher.streamHealthy.value)
        assertEquals(1L, harness.refresher.revision.value)
    }

    @Test
    fun `the polling fallback refreshes phase one while the stream is down`() = runTest {
        val harness = seeded()
        harness.paths.clear()

        harness.refresher.pollOnce()

        assertTrue(harness.paths.contains("/api/divers"))
        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=0"))
        assertEquals(1L, harness.refresher.revision.value)

        harness.refresher.handle(LiveEvent.Connected)
        harness.paths.clear()
        harness.refresher.pollOnce()

        assertEquals(emptyList(), harness.paths)
    }

    @Test
    fun `a refresh keeps what only the detail pass carried`() = runTest {
        val harness = seeded()
        RefreshData.dives[0] = refreshDiveListItem(number = 99, findings = 5)
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        val dive = store.dive("dive-1")!!
        assertEquals(3L, dive.version)
        assertEquals("A good dive", dive.notes)
        assertEquals(99L, dive.number)
        assertEquals(5L, dive.findingsCount)
        assertTrue(harness.paths.none { it == "/api/dives/dive-1" })
    }

    @Test
    fun `an unknown hint kind is ignored`() = runTest {
        val harness = seeded()

        harness.refresher.handle(LiveEvent.Hint(LiveHint("waitingPush", "push-1")))

        assertEquals(emptyList(), harness.paths)
        assertEquals(0L, harness.refresher.revision.value)
        assertNull(store.syncState().lastHintAt)
    }

    @Test
    fun `progress exposes the hint time and the pending profiles`() = runTest {
        val harness = seeded()

        val before = harness.engine.progress()
        assertEquals(1L, before.divesAwaitingProfiles)
        assertFalse(before.profilesDone)
        assertNull(before.lastHintAt)

        harness.engine.applySiteHint("site-1")
        harness.engine.syncProfiles()

        val after = harness.engine.progress()
        assertEquals(0L, after.divesAwaitingProfiles)
        assertTrue(after.profilesDone)
        assertEquals(RefreshNow, after.lastHintAt)
    }
}
