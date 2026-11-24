package dev.divehub.shared.write

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.CachedMeStore
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.PendingWritesException
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.SessionState
import dev.divehub.shared.SET_AUTH_TOKEN
import dev.divehub.shared.TokenStore
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWriteStatus
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * The T12 exit gate as scripts (FAH-458 §5): the four scenarios the initiative ships on, run together rather than as
 * pieces. The unit suites keep the details; this file proves the promises hold end to end in the shared core.
 */

private const val GATE_ME = """{"user":{"id":"u1","email":"ada@example.com","name":"Ada","role":"user","disabled":false,"createdAt":"2026-01-01T00:00:00Z"},"ownDiver":{"id":"diver-1","name":"Ada"},"preferences":{"language":null,"units":null}}"""
private const val GATE_OTHER_ME = """{"user":{"id":"u2","email":"bob@example.com","name":"Bob","role":"user","disabled":false,"createdAt":"2026-01-01T00:00:00Z"},"ownDiver":{"id":"diver-2","name":"Bob"},"preferences":{"language":null,"units":null}}"""

private data class GateCall(val method: String, val path: String, val ifMatch: String?, val body: String?)

/**
 * A stateful server for the race scenarios: one Dive, versioned, patched with `If-Match`. It applies the two top-level
 * fields the scripts use (`notes`, `siteId`) and answers a stale version with 412 and the current view, exactly as the
 * server's conditional-write contract does (ADR 0083 D2).
 */
private class GateServer(initial: JsonObject) {
    private var view: JsonObject = initial
    private var version: Int = (initial["version"] as JsonPrimitive).content.toInt()

    fun handle(call: GateCall): Pair<String, HttpStatusCode> = when (call.method) {
        "GET" -> view.toString() to HttpStatusCode.OK
        "PATCH" -> {
            val expected = call.ifMatch?.trim('"')?.toIntOrNull()
            if (expected != version) {
                """{"code":"dive_changed","dive":$view}""" to HttpStatusCode.PreconditionFailed
            } else {
                apply(jsonOf(assertNotNull(call.body)))
                view.toString() to HttpStatusCode.OK
            }
        }
        // Any other call is not part of the race scripts.
        else -> "" to HttpStatusCode.NotFound
    }

    private fun apply(body: JsonObject) {
        val next = view.toMutableMap()
        (body["notes"] as? JsonPrimitive)?.let { next["notes"] = it }
        (body["siteId"] as? JsonPrimitive)?.let { id ->
            next["site"] = buildJsonObject {
                put("id", id.content)
                put("name", "Site ${id.content}")
            }
        }
        version += 1
        next["version"] = JsonPrimitive(version)
        view = JsonObject(next)
    }
}

/** One simulated client: its own store, queue and replayer, sharing the [GateServer] with the other client. */
private class GateClient(val store: DiveStore, server: GateServer) {
    private val api = ApiClient(
        engine = MockEngine { request ->
            val call = GateCall(
                method = request.method.value,
                path = request.url.encodedPath,
                ifMatch = request.headers["If-Match"],
                body = (request.body as? TextContent)?.text,
            )
            val (body, status) = server.handle(call)
            respond(body, status, headersOf(HttpHeaders.ContentType, "application/json"))
        },
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = {},
        json = JSON,
    ).apply { baseUrl = "https://dive.example" }

    val queue = WriteQueue(store, clock = { NOW })
    val replayer = WriteReplayer(api, store, clock = { NOW })

    fun seed() = store.putDive(diveObject(), version = 3)

    /** The feed's own effect: a hint refetches the Dive through the normal API (ADR 0083 D1). */
    suspend fun refetch(diveId: String = "dive-1") {
        val detail = jsonOf(api.get("/api/dives/$diveId").bodyAsText())
        store.putDive(detail, version = (detail["version"] as JsonPrimitive).content.toLong())
    }

    fun detail(diveId: String = "dive-1"): JsonObject = jsonOf(store.dive(diveId)!!.detailJson!!)
}

private class GateTokens(var value: String? = null) : TokenStore {
    override suspend fun load(): String? = value
    override suspend fun save(token: String) {
        value = token
    }

    override suspend fun clear() {
        value = null
    }
}

private class GateInstances(var value: String? = "https://dive.example") : InstanceUrlStore {
    override suspend fun load(): String? = value
    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}

private class GateCachedMe(var value: String? = null) : CachedMeStore {
    override suspend fun load(): String? = value
    override suspend fun save(meJson: String) {
        value = meJson
    }

    override suspend fun clear() {
        value = null
    }
}

class WriteExitGateTest {
    private val drivers = mutableListOf<JdbcSqliteDriver>()

    private fun newStore(): DiveStore {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        drivers += driver
        return DiveStore.create(driver)
    }

    @AfterTest
    fun close() = drivers.forEach { it.close() }

    @Test
    fun `exit gate - a two-client edit race converges, disjoint fields merge quietly`() = runTest {
        val server = GateServer(diveObject())
        val phone = GateClient(newStore(), server).apply { seed() }
        val web = GateClient(newStore(), server).apply { seed() }

        phone.queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"From the phone"}"""))
        web.queue.enqueueDivePatch("dive-1", jsonOf("""{"siteId":"site-2"}"""))

        val phoneResult = phone.replayer.replay()
        assertIs<WriteReplayResult.Drained>(phoneResult)
        assertEquals(1, phoneResult.applied)

        // The web client replays against the version it started from: one 412, one quiet merge, one fresh write.
        val webResult = web.replayer.replay()
        assertIs<WriteReplayResult.Drained>(webResult)
        assertEquals(1, webResult.applied)
        assertEquals(0, webResult.conflicted)

        assertEquals("From the phone", (web.detail()["notes"] as JsonPrimitive).content)
        assertEquals("site-2", ((web.detail()["site"] as JsonObject)["id"] as JsonPrimitive).content)

        // The phone follows the feed's hint and converges on the same Dive.
        phone.refetch()
        assertEquals("From the phone", (phone.detail()["notes"] as JsonPrimitive).content)
        assertEquals("site-2", ((phone.detail()["site"] as JsonObject)["id"] as JsonPrimitive).content)
    }

    @Test
    fun `exit gate - the same field in two clients is exactly one conflict, and the loser resolves it`() = runTest {
        val server = GateServer(diveObject())
        val phone = GateClient(newStore(), server).apply { seed() }
        val web = GateClient(newStore(), server).apply { seed() }

        phone.queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"From the phone"}"""))
        web.queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"From the web"}"""))

        assertIs<WriteReplayResult.Drained>(phone.replayer.replay())
        val webResult = web.replayer.replay()
        assertIs<WriteReplayResult.Drained>(webResult)
        assertEquals(1, webResult.conflicted)

        val conflict = web.replayer.conflicts().single()
        assertEquals("notes", conflict.fields.single().field)
        assertEquals("From the phone", (conflict.fields.single().server as JsonPrimitive).content)
        assertEquals("From the web", (conflict.fields.single().local as JsonPrimitive).content)
        assertEquals(ConflictSide.ThisPhone, conflict.fields.single().preselected)

        // Keeping this phone's edit re-queues it against the fresh version and lands it.
        assertIs<ConflictResolution.Requeued>(
            web.replayer.resolve(conflict.entryId, mapOf("notes" to ConflictSide.ThisPhone)),
        )
        assertEquals(1, web.store.pendingWrites(PendingWriteStatus.Pending).size)
        assertIs<WriteReplayResult.Drained>(web.replayer.replay())

        phone.refetch()
        assertEquals("From the web", (phone.detail()["notes"] as JsonPrimitive).content)
        assertEquals("From the web", (web.detail()["notes"] as JsonPrimitive).content)
    }

    @Test
    fun `exit gate - a revoked session keeps the queue and a same-user sign-in resumes it`() = runTest {
        val store = newStore()
        GateClient(store, GateServer(diveObject())).apply { seed() }
        val queue = WriteQueue(store, clock = { NOW })
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"From the boat"}"""))

        // Replay mid-queue gets a 401: the session ended, the queued write is the User's data and stays.
        val expired = ApiClient(
            engine = MockEngine { respond("""{"code":"sign_in_required"}""", HttpStatusCode.Unauthorized, headersOf(HttpHeaders.ContentType, "application/json")) },
            versionName = "0.1.0-dev",
            token = { "expired" },
            onUnauthorized = {},
            json = JSON,
        ).apply { baseUrl = "https://dive.example" }
        assertIs<WriteReplayResult.SessionEnded>(WriteReplayer(expired, store, clock = { NOW }).replay())
        assertEquals(1, store.pendingWrites(PendingWriteStatus.Pending).size)

        // The same User signs back in: nothing of the store or the queue is forgotten (ADR 0084 D8).
        val tokens = GateTokens("expired")
        val session = SessionRepository(
            engine = MockEngine { request ->
                when (request.url.encodedPath) {
                    "/api/auth/sign-in/email" -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "new-token"))
                    "/api/me" -> respond(GATE_ME, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
                    else -> error("unexpected ${request.url.encodedPath}")
                }
            },
            tokens = tokens,
            instances = GateInstances(),
            userData = store,
            cachedMe = GateCachedMe(GATE_ME),
            versionName = "0.1.0-dev",
            pendingWrites = store,
        )
        assertEquals("u1", session.signIn("https://dive.example", "ada@example.com", "correct horse battery").id)
        assertIs<SessionState.SignedIn>(session.state.value)
        assertEquals("new-token", tokens.value)
        assertEquals(1, store.pendingWrites(PendingWriteStatus.Pending).size)

        // Replay resumes on the next trigger and the edit lands.
        val resumed = GateClient(store, GateServer(diveObject()))
        val result = resumed.replayer.replay()
        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        assertTrue(store.pendingWrites().isEmpty())
        assertEquals("From the boat", (resumed.detail()["notes"] as JsonPrimitive).content)
    }

    @Test
    fun `exit gate - a different user is a switch and the warning keeps the queue until the decision`() = runTest {
        val store = newStore()
        GateClient(store, GateServer(diveObject())).apply { seed() }
        WriteQueue(store, clock = { NOW }).enqueueDivePatch("dive-1", jsonOf("""{"notes":"From the boat"}"""))

        val session = SessionRepository(
            engine = MockEngine { request ->
                when (request.url.encodedPath) {
                    "/api/auth/sign-in/email" -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "other-token"))
                    "/api/me" -> respond(GATE_OTHER_ME, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
                    else -> error("unexpected ${request.url.encodedPath}")
                }
            },
            tokens = GateTokens(),
            instances = GateInstances(),
            userData = store,
            cachedMe = GateCachedMe(GATE_ME),
            versionName = "0.1.0-dev",
            pendingWrites = store,
        )

        val warning = assertFailsWith<PendingWritesException> {
            session.signIn("https://dive.example", "bob@example.com", "correct horse battery")
        }
        assertEquals(1, warning.count)
        assertEquals(1, store.pendingWrites(PendingWriteStatus.Pending).size)
        assertNotNull(store.dive("dive-1"))
    }

    @Test
    fun `exit gate - the same APK is read-only on a pre-feed server and writeable after the upgrade`() = runTest {
        var answer = HttpStatusCode.NotFound
        val capability = WriteCapability(
            ApiClient(
                engine = MockEngine { respond("{}", answer, headersOf(HttpHeaders.ContentType, "application/json")) },
                versionName = "0.1.0-dev",
                token = { "token" },
                onUnauthorized = {},
                json = JSON,
            ).apply { baseUrl = "https://dive.example" },
        )

        // A server without the change feed: no write UI is offered for this session.
        capability.probe()
        assertEquals(false, capability.writeable.value)

        // The same client, same session: the server upgrade answers the probe with the feed.
        answer = HttpStatusCode.OK
        capability.probe()
        assertEquals(true, capability.writeable.value)
    }
}
