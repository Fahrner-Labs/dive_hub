package dev.divehub.shared.sync

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWriteStatus
import dev.divehub.shared.write.WriteQueue
import dev.divehub.shared.write.WriteReplayResult
import dev.divehub.shared.write.WriteReplayer
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
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

/** One hint in the fake change feed; its position is its index in [RefreshData.feedEntries]. */
private data class FeedEntry(val entityType: String, val entityId: String, val kind: String = "changed")

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

    /** Whether the fake server answers the change feed (ADR 0083); false models a pre-feed server. */
    var feedAvailable = false

    /** The feed's log; the cursor is `c<count>` and each entry's index is its position. */
    val feedEntries = mutableListOf<FeedEntry>()

    /** A per-request refusal for the feed page starting at that count (to model a 410 or a failure). */
    val feedStatus = mutableMapOf<Int, HttpStatusCode>()

    /** Per-entity refusals for the normal refetch routes. */
    val diveStatus = mutableMapOf<String, HttpStatusCode>()
    val siteStatus = mutableMapOf<String, HttpStatusCode>()
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
    RefreshData.feedAvailable = false
    RefreshData.feedEntries.clear()
    RefreshData.feedStatus.clear()
    RefreshData.diveStatus.clear()
    RefreshData.siteStatus.clear()
}

private fun refreshPathId(path: String): String = path.substringAfterLast('/').substringBefore('?')

private fun refreshDiveId(listItem: String): String =
    Regex("\"id\":\"([^\"]+)\"").find(listItem)?.groupValues?.get(1) ?: error("no id in $listItem")

private fun refreshDiveStartsAt(listItem: String): String =
    Regex("\"startsAt\":\"([^\"]+)\"").find(listItem)?.groupValues?.get(1) ?: error("no startsAt in $listItem")

private fun refreshDetailsPage(items: List<String>): String {
    val details = items.map { dive ->
        val id = refreshDiveId(dive)
        RefreshData.details[id] ?: refreshDetailJson(id, refreshDiveStartsAt(dive))
    }
    return """{"dives":[${details.joinToString(",")}],"total":${RefreshData.dives.size},
    "counts":{"noRecording":0,"noSite":0,"withFindings":0,"notAtProvider":0,"changedSinceSent":0},
    "totals":{"dives":${RefreshData.dives.size},"durationSeconds":0,"deepestM":null,"lastDiveAt":null},"months":[]}"""
}

/** The fake feed: a bootstrap without a cursor, else the entries after the requested position, page-limited. */
private fun refreshFeedPage(path: String): RefreshReply {
    if (!RefreshData.feedAvailable) return RefreshReply.Status(HttpStatusCode.NotFound)
    val cursor = refreshQuery(path, "cursor")
    if (cursor == null) {
        return refreshJson("""{"cursor":"c${RefreshData.feedEntries.size}","events":[]}""")
    }
    val from = cursor.removePrefix("c").toInt()
    RefreshData.feedStatus[from]?.let { return RefreshReply.Status(it) }
    val limit = refreshQuery(path, "limit")?.toInt() ?: 200
    val slice = RefreshData.feedEntries.drop(from).take(limit)
    val next = from + slice.size
    val events = slice.mapIndexed { index, entry ->
        """{"seq":${from + index + 1},"entityType":"${entry.entityType}","entityId":"${entry.entityId}","kind":"${entry.kind}"}"""
    }
    return refreshJson("""{"cursor":"c$next","events":[${events.joinToString(",")}]}""")
}

private fun refreshRoutes(path: String): RefreshReply = when {
    path.startsWith("/api/sync/changes") -> refreshFeedPage(path)
    path == "/api/divers" -> refreshJson("[${RefreshData.divers.joinToString(",")}]")
    path.startsWith("/api/divers/") && path.endsWith("/body-weights") -> refreshJson(refreshWeightsJson())
    path.startsWith("/api/dive-sites?") -> {
        val offset = refreshQuery(path, "offset")?.toInt() ?: 0
        val limit = refreshQuery(path, "limit")?.toInt() ?: 200
        refreshJson(refreshSitePage(RefreshData.sites.drop(offset).take(limit)))
    }
    path.startsWith("/api/dive-sites/") -> {
        val id = refreshPathId(path)
        RefreshData.siteStatus[id]?.let { return RefreshReply.Status(it) }
        refreshJson(RefreshData.sites.first { it.contains("\"id\":\"$id\"") })
    }
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
        if (refreshQuery(path, "include") == "details") {
            refreshJson(refreshDetailsPage(RefreshData.dives.drop(offset).take(limit)))
        } else {
            refreshJson(refreshDivePage(RefreshData.dives.drop(offset).take(limit)))
        }
    }
    path.startsWith("/api/recordings/") && path.contains("/samples?") -> {
        val id = path.removePrefix("/api/recordings/").substringBefore('/')
        val channels = refreshQuery(path, "channels")?.split(',')?.filter { it.isNotEmpty() }.orEmpty()
        refreshJson(refreshSamplesJson(id, channels, RefreshData.samplePeak[id] ?: 31.4))
    }
    path.startsWith("/api/dives/") -> {
        val id = refreshPathId(path)
        RefreshData.diveStatus[id]?.let { return RefreshReply.Status(it) }
        refreshJson(RefreshData.details[id] ?: refreshDetailJson(id))
    }
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

    /** A seeded store whose server answers the change feed, with the cursor at the log's start. */
    private suspend fun seededWithFeed(): RefreshHarness {
        RefreshData.feedAvailable = true
        val harness = seeded()
        store.saveSyncState(store.syncState().copy(feedCursor = "c0"))
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
            when {
                // The batched page does not return dive-2, so only the per-Dive repair path can bring it in.
                path.startsWith("/api/dives?") && refreshQuery(path, "include") == "details" -> {
                    val offset = refreshQuery(path, "offset")?.toInt() ?: 0
                    val rows = if (offset == 0) listOf(RefreshData.details["dive-1"]!!) else emptyList()
                    refreshJson("""{"dives":[${rows.joinToString(",")}],"total":${RefreshData.dives.size},
                    "counts":{"noRecording":0,"noSite":0,"withFindings":0,"notAtProvider":0,"changedSinceSent":0},
                    "totals":{"dives":${RefreshData.dives.size},"durationSeconds":0,"deepestM":null,"lastDiveAt":null},"months":[]}""")
                }
                path == "/api/dives/dive-2" && detailFails -> RefreshReply.Status(HttpStatusCode.InternalServerError)
                else -> refreshRoutes(path)
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
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun `a stream that ends turns the connection off`() = runTest {
        val harness = seeded()
        val gate = CompletableDeferred<Unit>()
        val live = LiveHints {
            flow {
                emit(LiveEvent.Connected)
                gate.await()
            }
        }
        val refresher = SyncRefresher(harness.engine, live)
        assertFalse(refresher.streamHealthy.value)

        val job = launch { refresher.runLive() }

        advanceUntilIdle()
        assertTrue(refresher.streamHealthy.value)
        gate.complete(Unit)
        job.join()
        assertFalse(refresher.streamHealthy.value)
    }

    @Test
    fun `a stream failure turns the connection off`() = runTest {
        val harness = seeded()
        val live = LiveHints {
            flow {
                emit(LiveEvent.Connected)
                throw NoAnswerException()
            }
        }
        val refresher = SyncRefresher(harness.engine, live)

        assertFailsWith<NoAnswerException> { refresher.runLive() }

        assertFalse(refresher.streamHealthy.value)
    }

    @Test
    fun `a refused stream keeps polling and stays out of the offline state`() = runTest {
        val harness = seeded()
        val live = LiveHints {
            flow<LiveEvent> { throw DiveHubException("live_unavailable", 503) }
        }
        val refresher = SyncRefresher(harness.engine, live)

        assertFailsWith<DiveHubException> { refresher.runLive() }
        assertFalse(refresher.streamHealthy.value)
        assertFalse(refresher.offline.value)

        harness.paths.clear()
        refresher.pollOnce()

        // The poll still ran (the store is fresh) and did not turn the refusal into "offline".
        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=0"))
        assertFalse(refresher.offline.value)
    }

    @Test
    fun `a stream refusal is reported once per state change`() = runTest {
        val harness = seeded()
        val failures = mutableListOf<StreamFailure>()
        var code = "live_unavailable"
        var status = 503
        val live = LiveHints {
            flow<LiveEvent> { throw DiveHubException(code, status) }
        }
        val refresher = SyncRefresher(harness.engine, live, onStreamFailure = { failures += it })

        assertFailsWith<DiveHubException> { refresher.runLive() }
        assertFailsWith<DiveHubException> { refresher.runLive() }

        assertEquals(1, failures.size)
        assertEquals("live_unavailable", failures.single().code)
        assertEquals(503, failures.single().status)

        code = "too_many_live_streams"
        status = 429
        assertFailsWith<DiveHubException> { refresher.runLive() }

        assertEquals(2, failures.size)
        assertEquals("too_many_live_streams", failures.last().code)
        assertEquals(429, failures.last().status)
    }

    @Test
    fun `a reconnect resets the reported stream failure`() = runTest {
        val harness = seeded()
        val failures = mutableListOf<StreamFailure>()
        val live = LiveHints {
            flow {
                emit(LiveEvent.Connected)
                throw DiveHubException("live_unavailable", 503)
            }
        }
        val refresher = SyncRefresher(harness.engine, live, onStreamFailure = { failures += it })

        assertFailsWith<DiveHubException> { refresher.runLive() }
        assertEquals(1, failures.size)

        // A new connection lets the same refusal be reported again when the fresh stream drops.
        assertFailsWith<DiveHubException> { refresher.runLive() }

        assertEquals(2, failures.size)
    }

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun `a refused stream backs off instead of reconnecting on every interval`() = runTest {
        val harness = seeded()
        val attempts = mutableListOf<Long>()
        val live = LiveHints {
            flow<LiveEvent> {
                attempts += testScheduler.currentTime
                throw DiveHubException("live_unavailable", 503)
            }
        }
        val refresher = SyncRefresher(
            harness.engine,
            live,
            reconnectDelayMs = 1_000,
            maxReconnectDelayMs = 8_000,
            pollIntervalMs = 1_000_000,
        )

        refresher.start(backgroundScope)
        runCurrent()
        advanceTimeBy(30_000)
        runCurrent()

        assertEquals(listOf(0L, 1_000L, 3_000L, 7_000L, 15_000L, 23_000L), attempts)
    }

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun `a no-answer stream keeps the base reconnect interval`() = runTest {
        val harness = seeded()
        val attempts = mutableListOf<Long>()
        val live = LiveHints {
            flow<LiveEvent> {
                attempts += testScheduler.currentTime
                throw NoAnswerException()
            }
        }
        val refresher = SyncRefresher(
            harness.engine,
            live,
            reconnectDelayMs = 1_000,
            pollIntervalMs = 1_000_000,
        )

        refresher.start(backgroundScope)
        runCurrent()
        advanceTimeBy(3_500)
        runCurrent()

        assertEquals(listOf(0L, 1_000L, 2_000L, 3_000L), attempts)
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

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun `the progress flow follows the initial sync from phase one to the details`() = runTest {
        val harness = RefreshHarness(store, ::refreshRoutes)
        val seen = mutableListOf<SyncProgress>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            harness.refresher.progress.collect { seen += it }
        }

        assertEquals(SyncResult.Complete, harness.engine.sync())

        // The first page lands before any detail, so the logbook can say how many dives it is syncing.
        assertTrue(seen.contains(SyncProgress.EMPTY), "the flow starts empty")
        assertTrue(
            seen.any { it.storedDives == 1L && it.detailedDives == 0L && !it.detailsDone },
            "phase one reports the dives before their details",
        )
        assertTrue(
            seen.any { it.storedDives == 1L && it.detailedDives == 1L && it.detailsDone },
            "the details phase advances the count",
        )
        assertEquals(RefreshNow, seen.last().lastFullSyncAt)
        assertEquals(seen.last(), harness.refresher.progress.value)
    }
    @Test
    fun `a store with no cursor bootstraps and full-syncs before it follows`() = runTest {
        RefreshData.feedAvailable = true
        val harness = seeded()
        RefreshData.dives += refreshDiveListItem(id = "dive-2", startsAt = "2026-10-01T10:00:00Z", number = 2)
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z")
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        val bootstrap = harness.paths.indexOf("/api/sync/changes")
        val pages = harness.paths.indexOfFirst { it.startsWith("/api/dives?limit=200&offset=0") }
        assertTrue(bootstrap >= 0 && pages > bootstrap, "bootstrap before the pages: ${harness.paths}")
        assertTrue(harness.paths.any { it.startsWith("/api/sync/changes?cursor=c0") })
        assertEquals("c0", store.syncState().feedCursor)
        assertNotNull(store.dive("dive-2"))
    }

    @Test
    fun `a refresh drains the feed and refetches only what changed`() = runTest {
        val harness = seededWithFeed()
        RefreshData.details["dive-1"] = refreshDetailJson(version = 4, notes = "Changed elsewhere")
        RefreshData.dives[0] = refreshDiveListItem(number = 9, findings = 3)
        RefreshData.feedEntries += FeedEntry("dive", "dive-1")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertEquals(4L, store.dive("dive-1")!!.version)
        assertEquals("Changed elsewhere", store.dive("dive-1")!!.notes)
        assertEquals(3L, store.dive("dive-1")!!.findingsCount)
        assertEquals("c1", store.syncState().feedCursor)
        assertEquals(RefreshNow, store.syncState().lastFullSyncAt)
        assertTrue(harness.paths.contains("/api/sync/changes?cursor=c0&limit=200"))
        assertTrue(harness.paths.contains("/api/dives/dive-1"))
        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=0"))
        assertTrue(harness.paths.none { it.contains("include=details") })
    }

    @Test
    fun `a deleted event tombstones a Dive without refetching it`() = runTest {
        val harness = seededWithFeed()
        RefreshData.dives.clear()
        RefreshData.feedEntries += FeedEntry("dive", "dive-1", "deleted")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertNull(store.dive("dive-1"))
        assertNotNull(store.diveIncludingDeleted("dive-1")!!.deletedAt)
        assertTrue(harness.paths.none { it == "/api/dives/dive-1" })
        assertEquals("c1", store.syncState().feedCursor)
    }

    @Test
    fun `a 403 on a Dive refetch tombstones it`() = runTest {
        val harness = seededWithFeed()
        RefreshData.dives.clear()
        RefreshData.diveStatus["dive-1"] = HttpStatusCode.Forbidden
        RefreshData.feedEntries += FeedEntry("dive", "dive-1")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertNull(store.dive("dive-1"))
        assertNotNull(store.diveIncludingDeleted("dive-1")!!.deletedAt)
    }

    @Test
    fun `deleted site and Diver events tombstone locally`() = runTest {
        val harness = seededWithFeed()
        RefreshData.feedEntries += FeedEntry("dive_site", "site-1", "deleted")
        RefreshData.feedEntries += FeedEntry("diver", "diver-1", "deleted")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertTrue(store.diveSites().isEmpty())
        assertNotNull(store.diveSiteIncludingDeleted("site-1")!!.deletedAt)
        assertTrue(store.divers().isEmpty())
        assertNotNull(store.diverIncludingDeleted("diver-1")!!.deletedAt)
        assertEquals("c2", store.syncState().feedCursor)
    }

    @Test
    fun `a changed site refetches through the API and a 404 tombstones it`() = runTest {
        val harness = seededWithFeed()
        RefreshData.sites[0] = refreshSiteJson(name = "New Reef")
        RefreshData.feedEntries += FeedEntry("dive_site", "site-1")

        assertEquals(SyncResult.Complete, harness.engine.refresh())
        assertEquals(listOf("New Reef"), store.diveSites().map { it.name })

        RefreshData.siteStatus["site-1"] = HttpStatusCode.NotFound
        RefreshData.feedEntries += FeedEntry("dive_site", "site-1")
        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertTrue(store.diveSites().isEmpty())
        assertNotNull(store.diveSiteIncludingDeleted("site-1")!!.deletedAt)
    }

    @Test
    fun `a changed Diver refetches the Diver list`() = runTest {
        val harness = seededWithFeed()
        RefreshData.divers[0] = refreshDiverJson(name = "Ada Lovelace")
        RefreshData.feedEntries += FeedEntry("diver", "diver-1")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertEquals(listOf("Ada Lovelace"), store.divers().map { it.name })
        assertTrue(harness.paths.contains("/api/divers"))
    }

    @Test
    fun `hints for entities this store does not hold are ignored`() = runTest {
        val harness = seededWithFeed()
        RefreshData.feedEntries += FeedEntry("device", "device-1")
        RefreshData.feedEntries += FeedEntry("dive_centre", "centre-1")
        RefreshData.feedEntries += FeedEntry("equipment_item", "item-1")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertEquals("c3", store.syncState().feedCursor)
        // Only the feed and the paged body-weight gap (ADR 0083): no entity refetch and no list page.
        assertEquals(
            listOf("/api/sync/changes?cursor=c0&limit=200", "/api/divers/diver-1/body-weights"),
            harness.paths,
        )
    }

    @Test
    fun `a Recording hint refetches its parent Dive when the store holds it`() = runTest {
        val harness = seededWithFeed()
        RefreshData.feedEntries += FeedEntry("recording", "recording-dive-1")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertTrue(harness.paths.contains("/api/dives/dive-1"))
    }

    @Test
    fun `an interrupted drain resumes at the last complete page`() = runTest {
        val harness = seededWithFeed()
        repeat(200) { RefreshData.feedEntries += FeedEntry("dive", "dive-1") }
        RefreshData.details["dive-2"] = refreshDetailJson(id = "dive-2", startsAt = "2026-10-01T10:00:00Z")
        RefreshData.feedEntries += FeedEntry("dive", "dive-2")
        RefreshData.feedStatus[200] = HttpStatusCode.InternalServerError

        val first = harness.engine.refresh()

        assertIs<SyncResult.Stopped>(first)
        assertEquals("c200", store.syncState().feedCursor)
        assertNotNull(store.dive("dive-1"))
        assertNull(store.dive("dive-2"))

        harness.paths.clear()
        RefreshData.feedStatus.clear()
        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertEquals("c201", store.syncState().feedCursor)
        assertNotNull(store.dive("dive-2"))
        assertTrue(harness.paths.any { it.startsWith("/api/sync/changes?cursor=c200") })
        assertTrue(harness.paths.none { it.startsWith("/api/sync/changes?cursor=c0") })
    }

    @Test
    fun `a 410 resyncs through the paged machinery without wiping the store`() = runTest {
        val harness = seededWithFeed()
        store.upsertDive(
            Dive(
                id = "local-only",
                diverId = "diver-1",
                version = 5,
                startsAt = "2026-09-01T10:00:00Z",
                listJson = "{}",
                detailJson = "{}",
            ),
        )
        RefreshData.feedStatus[0] = HttpStatusCode.Gone
        RefreshData.feedEntries += FeedEntry("dive", "dive-1")

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=0"))
        assertEquals("c1", store.syncState().feedCursor)
        // Nothing was wiped: a local row the pages do not carry stays (a schema-v5 write queue lives beside it).
        assertNotNull(store.dive("local-only"))
        assertNotNull(store.dive("dive-1"))
    }

    @Test
    fun `the exit gate - a 410 resync keeps a queued edit and rebases it against the fresh state`() = runTest {
        var refusedOnce = false
        val harness = seeded { path ->
            if (!refusedOnce && path.startsWith("/api/sync/changes?cursor=c0")) {
                refusedOnce = true
                RefreshReply.Status(HttpStatusCode.Gone)
            } else {
                refreshRoutes(path)
            }
        }
        RefreshData.feedAvailable = true
        store.saveSyncState(store.syncState().copy(feedCursor = "c0"))
        harness.paths.clear()
        WriteQueue(store, clock = { RefreshNow }).enqueueDivePatch(
            "dive-1",
            RefreshJson.parseToJsonElement("""{"notes":"From the boat"}""") as JsonObject,
        )

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        // The resync rebuilt the store but did not discard the User's queued write or its base version.
        val waiting = store.pendingWrites(PendingWriteStatus.Pending).single()
        assertEquals(3L, waiting.baseVersion)
        assertNotNull(store.dive("dive-1"))

        // Replay answers the stale base version with 412 and the fresh view: the edit is rebased, not lost.
        val api = ApiClient(
            engine = MockEngine {
                respond(
                    """{"code":"dive_changed","dive":${refreshDetailJson(version = 7, notes = "Server edit")}}""",
                    HttpStatusCode.PreconditionFailed,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
            },
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = RefreshJson,
        ).apply { baseUrl = "https://dive.example" }
        val replayer = WriteReplayer(api, store, clock = { RefreshNow })
        val result = replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.conflicted)
        val conflict = replayer.conflicts().single()
        assertEquals(7L, conflict.serverVersion)
        assertEquals("A good dive", (conflict.fields.single().base as JsonPrimitive).content)
        assertEquals("Server edit", (conflict.fields.single().server as JsonPrimitive).content)
        assertEquals("From the boat", (conflict.fields.single().local as JsonPrimitive).content)
        assertEquals(PendingWriteStatus.Conflicted, store.pendingWrites().single().status)
    }

    @Test
    fun `a pre-feed server keeps the paged refresh and leaves no cursor`() = runTest {
        RefreshData.feedAvailable = false
        val harness = seeded()
        RefreshData.divers[0] = refreshDiverJson(name = "Ada Lovelace")
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.refresh())

        assertNull(store.syncState().feedCursor)
        assertEquals(listOf("Ada Lovelace"), store.divers().map { it.name })
        assertTrue(harness.paths.contains("/api/sync/changes"))
        assertTrue(harness.paths.any { it.startsWith("/api/dives?limit=200&offset=0") })
    }

    @Test
    fun `a bootstrap whose paged reload fails is retried, never followed`() = runTest {
        RefreshData.feedAvailable = true
        var failDivers = 2
        val harness = RefreshHarness(store, handler = { path ->
            if (path == "/api/divers" && failDivers > 0) {
                failDivers -= 1
                RefreshReply.Status(HttpStatusCode.InternalServerError)
            } else {
                refreshRoutes(path)
            }
        })

        assertIs<SyncResult.Stopped>(harness.engine.refresh())
        assertNull(store.syncState().feedCursor)

        assertEquals(SyncResult.Complete, harness.engine.refresh())
        assertNotNull(store.syncState().feedCursor)
        assertEquals(2, harness.paths.count { it == "/api/sync/changes" })
    }

    @Test
    fun `a hint drains the feed when the server has one`() = runTest {
        val harness = seededWithFeed()
        RefreshData.details["dive-2"] = refreshDetailJson(
            id = "dive-2",
            startsAt = "2026-10-01T10:00:00Z",
            notes = "Elsewhere",
        )
        RefreshData.feedEntries += FeedEntry("dive", "dive-2")

        harness.refresher.handle(LiveEvent.Hint(LiveHint("dive", "dive-1")))

        assertEquals("Elsewhere", store.dive("dive-2")!!.notes)
        assertTrue(harness.paths.any { it.startsWith("/api/sync/changes?cursor=c0") })
        assertTrue(harness.paths.contains("/api/dives/dive-2"))
        assertTrue(harness.paths.none { it == "/api/dives/dive-1" })
    }

    @Test
    fun `a hint against a pre-feed server refetches only the hinted thing`() = runTest {
        RefreshData.feedAvailable = false
        val harness = seeded()
        RefreshData.details["dive-1"] = refreshDetailJson(version = 4)
        harness.paths.clear()

        harness.refresher.handle(LiveEvent.Hint(LiveHint("dive", "dive-1")))

        assertEquals(4L, store.dive("dive-1")!!.version)
        assertTrue(harness.paths.none { it.startsWith("/api/sync/changes") })
        assertTrue(harness.paths.contains("/api/dives/dive-1"))
    }

}
