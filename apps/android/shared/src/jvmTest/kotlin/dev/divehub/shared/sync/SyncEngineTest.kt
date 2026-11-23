package dev.divehub.shared.sync

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DecodeException
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.planning.CylinderMaterial
import dev.divehub.shared.planning.EstimateCylinder
import dev.divehub.shared.planning.LeadUnit
import dev.divehub.shared.planning.PlannedConditions
import dev.divehub.shared.planning.SiteWaterType
import dev.divehub.shared.planning.Suit
import dev.divehub.shared.planning.SuitType
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncState
import dev.divehub.shared.tools.assemblePersonalizedSources
import dev.divehub.shared.tools.leadEstimate
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteChannel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant

private const val NOW = "2026-11-19T10:00:00Z"
private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }

private sealed interface Reply {
    data class Body(val text: String, val status: HttpStatusCode = HttpStatusCode.OK) : Reply
    data class Status(val status: HttpStatusCode) : Reply
    data object Offline : Reply
    data object BrokenBody : Reply
}

private fun json(text: String): Reply = Reply.Body(text)
private fun status(code: HttpStatusCode): Reply = Reply.Status(code)

private class Harness(val store: DiveStore, handler: (String) -> Reply) {
    val paths = mutableListOf<String>()
    val delays = mutableListOf<Int>()
    var unauthorized = 0

    private val api = ApiClient(
        engine = MockEngine { request ->
            val path = request.pathWithQuery()
            paths += path
            when (val reply = handler(path)) {
                is Reply.Body -> respond(
                    reply.text,
                    reply.status,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
                is Reply.Status -> respond(
                    "",
                    reply.status,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
                Reply.Offline -> throw IOException("offline")
                Reply.BrokenBody -> {
                    val channel = ByteChannel(autoFlush = false)
                    channel.cancel(IOException("body read failed"))
                    respond(channel, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
                }
            }
        },
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = { unauthorized += 1 },
        json = JSON,
    ).apply { baseUrl = "https://dive.example" }

    val engine = SyncEngine(api, store, clock = { NOW }, backoff = { delays += it })
}

private fun HttpRequestData.pathWithQuery(): String {
    val query = url.encodedQuery
    return url.encodedPath + if (query.isEmpty()) "" else "?$query"
}

private fun query(path: String, name: String): String? =
    path.substringAfter('?', "").split('&').firstOrNull { it.startsWith("$name=") }?.substringAfter('=')

private fun diverJson(id: String = "diver-1", name: String = "Ada", isOwn: Boolean = true): String =
    """{"id":"$id","name":"$name","isOwn":$isOwn,"divesBefore":0,"apneaCounts":true,"diveCount":1,
    "deviceCount":0,"mutedRules":[],"certifications":{"count":0,"newest":null,"expired":0,"expiringSoon":0}}"""

private fun weightsJson(): String = """[{"date":"2026-01-01","kg":75.5},{"date":"2026-02-01","kg":76.0}]"""

private fun siteJson(id: String = "site-1", name: String = "Reef"): String =
    """{"id":"$id","name":"$name","position":{"latitude":54.1,"longitude":10.2},"country":"DE",
    "waterBody":"Baltic","description":null,"maxDepthM":30,"waterType":"salt","externalIds":[],
    "version":1,"diveCount":1,"inUse":true,"canDelete":false,"mergedInto":null}"""

private fun diveListItem(
    id: String = "dive-1",
    startsAt: String = "2026-11-01T10:00:00Z",
    number: Int = 1,
    findings: Int = 2,
): String =
    """{"id":"$id","diverId":"diver-1","number":$number,"startsAt":"$startsAt","utcOffsetSeconds":3600,
    "utcOffsetSource":"device","durationSeconds":2700,"maxDepthM":31.4,"avgDepthM":17.2,
    "site":{"id":"site-1","name":"Reef"},"findings":$findings,"recordings":1,"fromProvider":"ssi",
    "gases":[],"surfaceIntervalSeconds":null,"participants":[],"waiting":[],"profile":null}"""

private fun divePage(items: List<String>, total: Int): String =
    """{"dives":[${items.joinToString(",")}],"total":$total,
    "counts":{"noRecording":0,"noSite":0,"withFindings":0,"notAtProvider":0,"changedSinceSent":0},
    "totals":{"dives":$total,"durationSeconds":0,"deepestM":null,"lastDiveAt":null},"months":[]}"""

private fun sitePage(items: List<String>, total: Int): String =
    """{"sites":[${items.joinToString(",")}],"total":$total}"""

private fun detailJson(id: String = "dive-1", startsAt: String = "2026-11-01T10:00:00Z", version: Int = 3): String =
    """{"id":"$id","diverId":"diver-1","version":$version,
    "values":{"number":42,"startsAt":{"at":"$startsAt","utcOffsetSeconds":3600},
    "durationSeconds":3000,"maxDepthM":24.3,"avgDepthM":12,"waterTemperatureC":18},
    "overrides":[],"utcOffsetSource":"device","fromProvider":"ssi","notes":"A good dive",
    "site":{"id":"site-1","name":"Reef"},"waterType":"salt",
    "recordings":[{"id":"recording-$id","isPrimary":true,
      "device":{"manufacturer":"Garmin","product":"Descent","serialNumber":"777"},
      "startsAt":"2026-11-01T10:00:00Z","durationSeconds":3000,"maxDepthM":24.3,"parser":"fit","partOf":null,
      "summary":{"gfHigh":80,"cnsEnd":12,"otuEnd":34,"sacLpm":16.5,"waterType":"salt"},
      "channels":["depth","temperature"]}],
    "cylinders":[{"volumeL":12,"workingPressureBar":232,"material":"steel","gas":{"o2":21,"he":0},
      "startPressureBar":200,"endPressureBar":50,"fromPod":false,"series":null}],
    "lead":[{"amountKg":4,"placement":"belt"}],"leadTotalKg":4,
    "weightingFeedback":{"felt":"too_heavy","amountKg":4},
    "suit":{"type":"wetsuit","thicknessMm":5.0,"hood":true,"undergarment":null},
    "participants":[],"verificationCodes":[]}"""

private fun diveIdOf(listItem: String): String =
    Regex("\"id\":\"([^\"]+)\"").find(listItem)?.groupValues?.get(1) ?: error("no id in $listItem")

private fun diveStartsAtOf(listItem: String): String =
    Regex("\"startsAt\":\"([^\"]+)\"").find(listItem)?.groupValues?.get(1) ?: error("no startsAt in $listItem")

private fun diveDetailsPage(ids: List<Pair<String, String>>, total: Int): String =
    """{"dives":[${ids.joinToString(",") { (id, startsAt) -> detailJson(id, startsAt) }}],"total":$total,
    "counts":{"noRecording":0,"noSite":0,"withFindings":0,"notAtProvider":0,"changedSinceSent":0},
    "totals":{"dives":$total,"durationSeconds":0,"deepestM":null,"lastDiveAt":null},"months":[]}"""

private class RouteData(
    val divers: List<String> = listOf(diverJson()),
    val weights: (String) -> Reply = { json(weightsJson()) },
    val sites: List<String> = listOf(siteJson()),
    val dives: List<String> = listOf(diveListItem()),
    val detail: (String, String) -> Reply = { id, startsAt -> json(detailJson(id, startsAt)) },
)

private fun routes(data: RouteData = RouteData()): (String) -> Reply = { path ->
    when {
        path == "/api/divers" -> json("[${data.divers.joinToString(",")}]")
        path.startsWith("/api/divers/") && path.endsWith("/body-weights") ->
            data.weights(path.removePrefix("/api/divers/").removeSuffix("/body-weights"))
        path.startsWith("/api/dive-sites?") -> {
            val offset = query(path, "offset")?.toInt() ?: 0
            val limit = query(path, "limit")?.toInt() ?: 200
            json(sitePage(data.sites.drop(offset).take(limit), data.sites.size))
        }
        path.startsWith("/api/dives?") -> {
            val offset = query(path, "offset")?.toInt() ?: 0
            val limit = query(path, "limit")?.toInt() ?: 200
            if (query(path, "include") == "details") {
                val rows = data.dives.drop(offset).take(limit)
                json(diveDetailsPage(rows.map { diveIdOf(it) to diveStartsAtOf(it) }, data.dives.size))
            } else {
                json(divePage(data.dives.drop(offset).take(limit), data.dives.size))
            }
        }
        path.startsWith("/api/dives/") -> {
            val id = path.removePrefix("/api/dives/")
            val startsAt = data.dives.firstOrNull { it.contains("\"id\":\"$id\"") }
                ?.let { Regex("\"startsAt\":\"([^\"]+)\"").find(it)?.groupValues?.get(1) }
                ?: "2026-11-01T10:00:00Z"
            data.detail(id, startsAt)
        }
        // These tests run the paged sync; a pre-feed server is the default here (feed tests live in SyncRefreshTest).
        path.startsWith("/api/sync/changes") -> status(HttpStatusCode.NotFound)
        else -> error("unexpected $path")
    }
}

private fun manyDives(count: Int, prefix: String = "dive"): List<String> = (0 until count).map { index ->
    val minute = (index % 60).toString().padStart(2, '0')
    val hour = (index / 60 % 24).toString().padStart(2, '0')
    val day = (1 + index / (60 * 24)).toString().padStart(2, '0')
    diveListItem(
        id = "$prefix-$index",
        startsAt = "2026-10-${day}T$hour:$minute:00Z",
        number = index + 1,
    )
}

private fun manySites(count: Int): List<String> = (0 until count).map { index -> siteJson(id = "site-$index", name = "Site $index") }

class SyncEngineTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)

    @AfterTest
    fun close() = driver.close()

    /** Runs [block] in the app's scope shape (a SupervisorJob), recording anything that escapes the sync. */
    private suspend fun runInAppScope(block: suspend () -> Unit) {
        val escaped = mutableListOf<Throwable>()
        val scope = CoroutineScope(SupervisorJob() + CoroutineExceptionHandler { _, error -> escaped += error })
        scope.launch { block() }.join()
        scope.cancel()
        assertTrue(escaped.isEmpty(), "sync threw into the app scope: $escaped")
    }

    @Test
    fun `phase one makes the logbook list readable and phase two the dive page`() = runTest {
        val harness = Harness(store, routes())

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertEquals(listOf("Ada"), store.divers().map { it.name })
        assertEquals(2, store.bodyWeights("diver-1").size)
        assertEquals(listOf("Reef"), store.diveSites().map { it.name })
        assertEquals(listOf("dive-1"), store.dives().map { it.id })
        assertEquals(2L, store.dive("dive-1")!!.findingsCount)
        assertEquals(3L, store.dive("dive-1")!!.version)

        val recording = store.recordings("dive-1").single()
        assertEquals("recording-dive-1", recording.id)
        assertTrue(recording.isPrimary)
        assertTrue(recording.summaryJson.contains("\"gfHigh\":80"))
        assertTrue(recording.channelsJson.contains("depth"))
        assertEquals(listOf(21.0), store.cylinders("dive-1").map { it.o2 })
        assertEquals(4.0, store.dive("dive-1")!!.leadTotalKg)
        assertNotNull(store.dive("dive-1")!!.listJson)
        assertNotNull(store.dive("dive-1")!!.detailJson)
        assertNotNull(store.syncState().countsJson)
        assertEquals(NOW, store.syncState().lastFullSyncAt)
        assertTrue(harness.paths.contains("/api/dives?include=details&limit=50&offset=0"))
        assertTrue(harness.paths.none { it.startsWith("/api/dives/") })
        assertEquals("salt", store.dive("dive-1")!!.waterType)
        assertTrue(store.dive("dive-1")!!.suitJson!!.contains("\"type\":\"wetsuit\""))
    }

    @Test
    fun `a store migrated from v1 backfills its API payloads on the next sync`() = runTest {
        store.saveSyncState(SyncState(lastFullSyncAt = NOW, schemaVersion = DiveStore.schemaVersion))
        store.upsertDive(
            Dive(
                id = "dive-1",
                diverId = "diver-1",
                version = 3,
                startsAt = "2026-11-01T10:00:00Z",
                durationSeconds = 2_700,
            ),
        )
        val base = routes()
        val harness = Harness(store) { path ->
            if (path.startsWith("/api/dives/deleted?")) {
                json("""{"dives":[],"total":0,"stillAt":[]}""")
            } else {
                base(path)
            }
        }

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertNotNull(store.dive("dive-1")!!.listJson)
        assertNotNull(store.dive("dive-1")!!.detailJson)
        assertNotNull(store.syncState().countsJson)
    }

    @Test
    fun `sites page by offset and stop at the total`() = runTest {
        val harness = Harness(store, routes(RouteData(sites = manySites(250), dives = emptyList())))

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertEquals(250, store.diveSites().size)
        assertTrue(harness.paths.contains("/api/dive-sites?limit=200&offset=0"))
        assertTrue(harness.paths.contains("/api/dive-sites?limit=200&offset=200"))
        assertTrue(harness.paths.none { it.startsWith("/api/dive-sites?") && query(it, "offset") == "400" })
    }

    @Test
    fun `the dive list pages by offset and stops at the total`() = runTest {
        val dives = manyDives(201)
        val harness = Harness(store, routes(RouteData(dives = dives)))

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertEquals(201, store.diveCount())
        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=0"))
        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=200"))
        assertTrue(harness.paths.none { it.startsWith("/api/dives?") && query(it, "offset") == "400" })
        assertTrue(harness.paths.contains("/api/dives?include=details&limit=50&offset=0"))
        assertTrue(harness.paths.contains("/api/dives?include=details&limit=50&offset=200"))
        assertTrue(harness.paths.none { it.startsWith("/api/dives/") })
    }

    @Test
    fun `a failed page keeps its checkpoint and the next trigger resumes at the offset`() = runTest {
        val dives = manyDives(201)
        var failSecondPage = 2
        val routes = routes(RouteData(dives = dives))
        val harness = Harness(store) { path ->
            if (path.startsWith("/api/dives?") && query(path, "include") == null && query(path, "offset") == "200" && failSecondPage > 0) {
                failSecondPage -= 1
                status(HttpStatusCode.InternalServerError)
            } else {
                routes(path)
            }
        }

        val first = harness.engine.sync()

        assertIs<SyncResult.Stopped>(first)
        assertEquals(SyncPhase.Meta, first.phase)
        assertEquals(200, store.diveCount())
        assertEquals(200L, store.checkpoint("dives", "meta")!!.pageOffset)
        assertEquals(listOf(1), harness.delays)

        harness.paths.clear()
        failSecondPage = 0
        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertEquals(201, store.diveCount())
        assertTrue(harness.paths.none { it.startsWith("/api/dives?") && query(it, "include") == null && query(it, "offset") == "0" })
        assertTrue(harness.paths.contains("/api/dives?limit=200&offset=200"))
    }

    @Test
    fun `a failed details page keeps the checkpoint and resumes at its offset`() = runTest {
        val dives = manyDives(120)
        var failSecondPage = 2
        val routes = routes(RouteData(dives = dives))
        val harness = Harness(store) { path ->
            if (path.startsWith("/api/dives?") && query(path, "include") == "details" &&
                query(path, "offset") == "50" && failSecondPage > 0
            ) {
                failSecondPage -= 1
                status(HttpStatusCode.InternalServerError)
            } else {
                routes(path)
            }
        }

        val first = harness.engine.sync()

        assertIs<SyncResult.Stopped>(first)
        assertEquals(SyncPhase.Details, first.phase)
        assertEquals(50L, store.checkpoint("dive_details", "details")!!.pageOffset)
        assertEquals(50, store.dives().count { it.version != null })
        assertNull(store.syncState().lastFullSyncAt)

        harness.paths.clear()
        failSecondPage = 0
        assertEquals(SyncResult.Complete, harness.engine.sync())
        assertEquals(120, store.dives().count { it.version != null })
        assertTrue(harness.paths.none { it.startsWith("/api/dives?") && query(it, "include") == "details" && query(it, "offset") == "0" })
        assertTrue(harness.paths.contains("/api/dives?include=details&limit=50&offset=50"))
    }

    @Test
    fun `a summary list answer to include details stops the sync without falling back to per-Dive requests`() = runTest {
        val dives = listOf(
            diveListItem(id = "dive-1", startsAt = "2026-11-03T10:00:00Z"),
            diveListItem(id = "dive-2", startsAt = "2026-11-02T10:00:00Z"),
        )
        val base = routes(RouteData(dives = dives))
        val harness = Harness(store) { path ->
            if (path.startsWith("/api/dives?") && query(path, "include") == "details") {
                // A pre-FAH-297 server answers the summary list, which does not decode as DiveDetailsListView, so the
                // details phase stops with a decode failure instead of falling back (FAH-324 scope change).
                json(divePage(dives, dives.size))
            } else {
                base(path)
            }
        }
        var result: SyncResult? = null

        runInAppScope { result = harness.engine.sync() }

        val stopped = assertIs<SyncResult.Stopped>(result)
        assertEquals(SyncPhase.Details, stopped.phase)
        assertIs<DecodeException>(stopped.error)
        assertTrue(harness.paths.none { it.startsWith("/api/dives/") })
        assertNull(store.dive("dive-1")!!.version)
        assertNull(store.syncState().lastFullSyncAt)
    }

    @Test
    fun `a decode failure in the meta phase stops the sync without escaping`() = runTest {
        val harness = Harness(store) { path ->
            if (path == "/api/divers") json("{}") else error("unexpected $path")
        }
        var result: SyncResult? = null

        runInAppScope { result = harness.engine.sync() }

        val stopped = assertIs<SyncResult.Stopped>(result)
        assertEquals(SyncPhase.Meta, stopped.phase)
        assertIs<DecodeException>(stopped.error)
    }

    @Test
    fun `a failed body read in a fetchJson site is a no-answer, not an escape`() = runTest {
        val harness = Harness(store) { path ->
            if (path == "/api/divers") Reply.BrokenBody else error("unexpected $path")
        }
        var result: SyncResult? = null

        runInAppScope { result = harness.engine.sync() }

        val stopped = assertIs<SyncResult.Stopped>(result)
        assertEquals(SyncPhase.Meta, stopped.phase)
        assertIs<NoAnswerException>(stopped.error)
    }

    @Test
    fun `a decode failure in the per-dive repair stops the sync without escaping`() = runTest {
        val dives = listOf(
            diveListItem(id = "dive-1", startsAt = "2026-11-03T10:00:00Z"),
            diveListItem(id = "dive-2", startsAt = "2026-11-02T10:00:00Z"),
        )
        val base = routes(RouteData(dives = dives))
        val harness = Harness(store) { path ->
            when {
                // The details page covers dive-1; dive-2 is left for the per-Dive repair path.
                path.startsWith("/api/dives?") && query(path, "include") == "details" ->
                    json(diveDetailsPage(listOf("dive-1" to "2026-11-03T10:00:00Z"), total = 1))
                path == "/api/dives/dive-2" -> json("{}")
                else -> base(path)
            }
        }
        var result: SyncResult? = null

        runInAppScope { result = harness.engine.sync() }

        val stopped = assertIs<SyncResult.Stopped>(result)
        assertEquals(SyncPhase.Details, stopped.phase)
        assertIs<DecodeException>(stopped.error)
        assertTrue(harness.paths.contains("/api/dives/dive-2"))
    }

    @Test
    fun `a decode failure in the profiles phase stops the profile sync without escaping`() = runTest {
        val base = routes()
        val harness = Harness(store) { path ->
            if (path.startsWith("/api/recordings/") && path.contains("/samples?")) json("{}") else base(path)
        }
        assertEquals(SyncResult.Complete, harness.engine.sync())
        var result: SyncResult? = null

        runInAppScope { result = harness.engine.syncProfiles() }

        val stopped = assertIs<SyncResult.Stopped>(result)
        assertEquals(SyncPhase.Profiles, stopped.phase)
        assertIs<DecodeException>(stopped.error)
    }

    @Test
    fun `a decode failure in the tombstones refresh stops the refresh without escaping`() = runTest {
        val base = routes()
        val harness = Harness(store) { path ->
            if (path.startsWith("/api/dives/deleted?")) json("{}") else base(path)
        }
        assertEquals(SyncResult.Complete, harness.engine.sync())
        var result: SyncResult? = null

        runInAppScope { result = harness.engine.refresh() }

        val stopped = assertIs<SyncResult.Stopped>(result)
        assertEquals(SyncPhase.Meta, stopped.phase)
        assertIs<DecodeException>(stopped.error)
    }

    @Test
    fun `body weights resume at the next Diver`() = runTest {
        val divers = listOf(diverJson("diver-1", "Ada"), diverJson("diver-2", "Bob", isOwn = false))
        var failSecondDiver = 2
        val routes = routes(RouteData(divers = divers, dives = emptyList(), sites = emptyList()))
        val harness = Harness(store) { path ->
            if (path == "/api/divers/diver-2/body-weights" && failSecondDiver > 0) {
                failSecondDiver -= 1
                status(HttpStatusCode.InternalServerError)
            } else {
                routes(path)
            }
        }

        assertIs<SyncResult.Stopped>(harness.engine.sync())
        assertEquals(2, store.bodyWeights("diver-1").size)
        assertEquals(0, store.bodyWeights("diver-2").size)

        harness.paths.clear()
        failSecondDiver = 0
        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertEquals(2, store.bodyWeights("diver-2").size)
        assertTrue(harness.paths.none { it == "/api/divers/diver-1/body-weights" })
        assertTrue(harness.paths.contains("/api/divers/diver-2/body-weights"))
    }

    @Test
    fun `a 401 ends the session and keeps the cached store`() = runTest {
        store.upsertDive(
            dev.divehub.shared.store.Dive(
                id = "cached",
                diverId = "diver-1",
                startsAt = "2026-10-01T10:00:00Z",
            ),
        )
        val harness = Harness(store) { path ->
            if (path == "/api/divers") status(HttpStatusCode.Unauthorized) else error("unexpected $path")
        }

        assertEquals(SyncResult.SessionEnded, harness.engine.sync())

        assertEquals(1, harness.unauthorized)
        assertNotNull(store.dive("cached"))
        assertEquals(listOf("/api/divers"), harness.paths)
        assertEquals(emptyList(), harness.delays)
    }

    @Test
    fun `a 401 during the details keeps the phase-one dives`() = runTest {
        val harness = Harness(store) { path ->
            if (path.startsWith("/api/dives?") && query(path, "include") == "details") {
                status(HttpStatusCode.Unauthorized)
            } else {
                routes()(path)
            }
        }

        assertEquals(SyncResult.SessionEnded, harness.engine.sync())

        assertEquals(1, harness.unauthorized)
        assertEquals(listOf("dive-1"), store.dives().map { it.id })
        assertNull(store.dive("dive-1")!!.version)
        assertNull(store.syncState().lastFullSyncAt)
        assertNull(store.checkpoint("dive_details", "details"))
    }

    @Test
    fun `a network failure is retried once with backoff`() = runTest {
        var calls = 0
        val harness = Harness(store) { path ->
            if (path == "/api/divers" && calls++ == 0) Reply.Offline else routes()(path)
        }

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertEquals(listOf(1), harness.delays)
        assertEquals(2, harness.paths.count { it == "/api/divers" })
    }

    @Test
    fun `a 5xx is retried once with backoff`() = runTest {
        var calls = 0
        val harness = Harness(store) { path ->
            if (path == "/api/divers" && calls++ == 0) status(HttpStatusCode.BadGateway) else routes()(path)
        }

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertEquals(listOf(1), harness.delays)
        assertEquals(2, harness.paths.count { it == "/api/divers" })
    }

    @Test
    fun `a second 5xx fails the phase without a third attempt`() = runTest {
        val harness = Harness(store) { path ->
            if (path == "/api/divers") status(HttpStatusCode.InternalServerError) else error("unexpected $path")
        }

        val result = harness.engine.sync()

        assertIs<SyncResult.Stopped>(result)
        assertEquals(SyncPhase.Meta, result.phase)
        assertEquals(2, harness.paths.count { it == "/api/divers" })
        assertEquals(listOf(1), harness.delays)
    }

    @Test
    fun `a 4xx is not retried`() = runTest {
        val harness = Harness(store) { path ->
            if (path == "/api/divers") status(HttpStatusCode.BadRequest) else error("unexpected $path")
        }

        assertIs<SyncResult.Stopped>(harness.engine.sync())

        assertEquals(1, harness.paths.count { it == "/api/divers" })
        assertEquals(emptyList(), harness.delays)
    }

    @Test
    fun `phases run in order divers, weights, sites, dives, then details`() = runTest {
        val harness = Harness(store, routes())

        assertEquals(SyncResult.Complete, harness.engine.sync())

        val divers = harness.paths.indexOf("/api/divers")
        val weights = harness.paths.indexOf("/api/divers/diver-1/body-weights")
        val sites = harness.paths.indexOfFirst { it.startsWith("/api/dive-sites?") }
        val dives = harness.paths.indexOfFirst { it.startsWith("/api/dives?") }
        val details = harness.paths.indexOfFirst { it.startsWith("/api/dives?") && query(it, "include") == "details" }
        assertTrue(divers < weights && weights < sites && sites < dives && dives < details)
    }

    @Test
    fun `a completed sync is a no-op until the refresh slice`() = runTest {
        val harness = Harness(store, routes())
        assertEquals(SyncResult.Complete, harness.engine.sync())
        harness.paths.clear()

        assertEquals(SyncResult.Complete, harness.engine.sync())
        assertEquals(emptyList(), harness.paths)
    }

    @Test
    fun `a dive deleted after the list becomes a tombstone and the phase completes`() = runTest {
        val dives = listOf(
            diveListItem(id = "dive-1", startsAt = "2026-11-03T10:00:00Z"),
            diveListItem(id = "dive-2", startsAt = "2026-11-02T10:00:00Z"),
        )
        val base = routes(RouteData(dives = dives))
        val harness = Harness(store) { path ->
            when {
                path.startsWith("/api/dives?") && query(path, "include") == "details" ->
                    json(diveDetailsPage(listOf("dive-2" to "2026-11-02T10:00:00Z"), total = 1))
                path == "/api/dives/dive-1" -> status(HttpStatusCode.NotFound)
                else -> base(path)
            }
        }

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertNull(store.dive("dive-1"))
        assertNotNull(store.diveIncludingDeleted("dive-1")!!.deletedAt)
        assertEquals(3L, store.dive("dive-2")!!.version)
        assertTrue(store.checkpoint("dive_details", "details")!!.done)
        assertTrue(harness.paths.contains("/api/dives/dive-1"))
        assertTrue(harness.paths.none { it == "/api/dives/dive-2" })
    }

    @Test
    fun `a stale v1 detail the pages no longer carry is tombstoned by the repair path`() = runTest {
        store.upsertDive(
            Dive(
                id = "dive-1",
                diverId = "diver-1",
                version = 3,
                startsAt = "2026-10-01T10:00:00Z",
            ),
        )
        val base = routes(RouteData(dives = listOf(diveListItem(id = "dive-2", startsAt = "2026-11-02T10:00:00Z"))))
        val harness = Harness(store) { path ->
            if (path == "/api/dives/dive-1") status(HttpStatusCode.NotFound) else base(path)
        }

        assertEquals(SyncResult.Complete, harness.engine.sync())

        assertNull(store.dive("dive-1"))
        assertNotNull(store.diveIncludingDeleted("dive-1")!!.deletedAt)
        assertEquals(3L, store.dive("dive-2")!!.version)
        assertTrue(harness.paths.contains("/api/dives/dive-1"))
    }

    @Test
    fun `batched details map suit lead and water type like the per-dive repair path`() = runTest {
        val data = RouteData(dives = listOf(diveListItem()))
        assertEquals(SyncResult.Complete, Harness(store, routes(data)).engine.sync())
        val batched = store.dive("dive-1")!!

        val hintDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        try {
            val hintStore = DiveStore.create(hintDriver)
            val hint = Harness(hintStore, routes(data))

            assertEquals(SyncResult.Complete, hint.engine.applyDiveHint("dive-1"))

            val perDive = hintStore.dive("dive-1")!!
            assertEquals(perDive.waterType, batched.waterType)
            assertEquals(perDive.suitJson, batched.suitJson)
            assertEquals(perDive.leadJson, batched.leadJson)
            assertEquals(perDive.leadTotalKg, batched.leadTotalKg)
            assertEquals(perDive.weightingFeedbackJson, batched.weightingFeedbackJson)
            assertTrue(hint.paths.contains("/api/dives/dive-1"))
        } finally {
            hintDriver.close()
        }
    }

    @Test
    fun `progress reports the per-phase counts and the last full sync`() = runTest {
        val harness = Harness(store, routes(RouteData(dives = manyDives(3))))

        assertEquals(SyncResult.Complete, harness.engine.sync())

        val progress = harness.engine.progress()
        assertEquals(NOW, progress.lastFullSyncAt)
        assertTrue(progress.metaDone)
        assertTrue(progress.detailsDone)
        assertEquals(1L, progress.storedDivers)
        assertEquals(2L, progress.storedBodyWeights)
        assertEquals(1L, progress.storedDiveSites)
        assertEquals(3L, progress.storedDives)
        assertEquals(3L, progress.detailedDives)
        assertEquals(3L, progress.divesAwaitingProfiles)
    }

    @Test
    fun `a synced dive's details feed the offline lead estimate`() = runTest {
        val harness = Harness(store, routes())

        assertEquals(SyncResult.Complete, harness.engine.sync())

        val sources = assemblePersonalizedSources(store, Instant.parse("2026-11-19T12:00:00Z").toEpochMilliseconds())
        val answer = leadEstimate(
            PlannedConditions(
                water = SiteWaterType.salt,
                suit = Suit(SuitType.wetsuit, 5.0, true, null),
                cylinders = listOf(EstimateCylinder(12.0, CylinderMaterial.steel)),
            ),
            LeadUnit.kg,
            sources,
            Instant.parse("2026-11-19T12:00:00Z"),
        )

        assertEquals("dives", answer.basis?.kind)
        assertEquals(listOf("dive-1"), answer.basis?.dives?.map { it.diveId })
        assertNotNull(answer.total)
    }
}
