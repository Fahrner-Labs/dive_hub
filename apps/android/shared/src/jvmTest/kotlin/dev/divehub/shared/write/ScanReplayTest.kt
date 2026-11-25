package dev.divehub.shared.write

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWriteStatus
import dev.divehub.shared.store.ScanStatus
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private data class ScanCall(val method: String, val path: String, val ifMatch: String?, val body: String?)

private sealed interface ScanReply {
    data class Body(val text: String, val status: HttpStatusCode = HttpStatusCode.OK) : ScanReply
    data object Offline : ScanReply
}

private class ScanHarness(store: DiveStore, handler: (ScanCall) -> ScanReply) {
    val calls = mutableListOf<ScanCall>()
    var unauthorized = 0

    private val api = ApiClient(
        engine = MockEngine { request ->
            val call = ScanCall(
                method = request.method.value,
                path = request.url.encodedPath,
                ifMatch = request.headers["If-Match"],
                body = (request.body as? TextContent)?.text,
            )
            calls += call
            when (val reply = handler(call)) {
                is ScanReply.Body -> respond(
                    reply.text,
                    reply.status,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
                ScanReply.Offline -> throw IOException("offline")
            }
        },
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = { unauthorized += 1 },
        json = JSON,
    ).apply { baseUrl = "https://dive.example" }

    val replayer = WriteReplayer(api, store, clock = { NOW })
}

private const val CENTRE_ID = "01930000-0000-7000-8000-000000000001"

private fun centreAnswer(existing: Boolean = false): String =
    """{"kind":"centre","centreNumber":"12345","name":"Reef Diving, Sharm","existing":${
        if (existing) """{"id":"centre-1","name":"Reef Diving"}""" else "null"
    }}"""

private fun buddyAnswer(): String =
    """{"kind":"buddy","accountId":"ssi-1","firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","leaderNumber":null,"existing":null,"candidates":[]}"""

private fun diverView(created: Boolean = false): String =
    """{"id":"diver-1","name":"Ada Lovelace","external":true,"canEdit":true,"firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","leaderNumber":null,"accounts":[],"codes":[]}"""

private fun centreView(id: String = CENTRE_ID): String =
    """{"id":"$id","name":"Reef Diving, Sharm","displayName":"Reef Diving","externalIds":[],"sites":[],"verificationCode":null,"version":1,"canDelete":true}"""

class ScanReplayTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)
    private val queue = WriteQueue(store, clock = { NOW })

    @AfterTest
    fun close() = driver.close()

    @Test
    fun `a scanned code replays the raw text to the read route and stores what it is`() = runTest {
        val queued = queue.enqueueScan("SSI;1;centre;12345;Reef Diving, Sharm", centreId = CENTRE_ID)
        assertEquals("scan", queued.entityType)
        assertEquals(CENTRE_ID, queued.entityId)
        assertNull(queued.baseVersion)
        val harness = ScanHarness(store) { call ->
            assertEquals("POST", call.method)
            assertEquals("/api/verification-codes/read", call.path)
            assertNull(call.ifMatch)
            assertEquals("""{"text":"SSI;1;centre;12345;Reef Diving, Sharm"}""", call.body)
            ScanReply.Body(centreAnswer())
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        // The read resolved but the User has not decided: the action waits with its text, never re-read on its own.
        val waiting = store.pendingWrites().single()
        assertEquals(PendingWriteStatus.Awaiting, waiting.status)
        assertEquals("""{"text":"SSI;1;centre;12345;Reef Diving, Sharm"}""", waiting.payloadJson)
        val scan = store.scanResults().single()
        assertEquals(ScanStatus.Read, scan.status)
        assertEquals("centre", scan.kind)
        assertEquals("Reef Diving, Sharm", scan.name)
        assertEquals(CENTRE_ID, scan.entityId)
        assertEquals(waiting.id, scan.actionId)
        assertTrue(scan.detailJson!!.contains("12345"))
    }

    @Test
    fun `a read that awaits the User is not replayed again`() = runTest {
        queue.enqueueScan("SSI;1;centre;12345;Reef Diving, Sharm", centreId = CENTRE_ID)
        var reads = 0
        val harness = ScanHarness(store) {
            reads += 1
            ScanReply.Body(centreAnswer())
        }

        harness.replayer.replay()
        val again = harness.replayer.replay()

        assertIs<WriteReplayResult.Empty>(again)
        assertEquals(1, reads)
    }

    @Test
    fun `a centre create is an action with the client uuid and no If-Match`() = runTest {
        queue.enqueueCreateCentre(id = CENTRE_ID, name = "Reef Diving, Sharm", number = "12345")
        val harness = ScanHarness(store) { call ->
            assertEquals("POST", call.method)
            assertEquals("/api/dive-centres", call.path)
            assertNull(call.ifMatch)
            val body = jsonOf(call.body!!)
            assertEquals(CENTRE_ID, (body["id"] as kotlinx.serialization.json.JsonPrimitive).content)
            assertEquals("Reef Diving, Sharm", (body["name"] as kotlinx.serialization.json.JsonPrimitive).content)
            ScanReply.Body(centreView(), HttpStatusCode.Created)
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        val scan = store.scanResults().single()
        assertEquals(ScanStatus.Created, scan.status)
        assertEquals("Reef Diving", scan.name)
        assertEquals(CENTRE_ID, scan.entityId)
    }

    @Test
    fun `a replayed centre create sends the same id again so the server answers with the centre`() = runTest {
        queue.enqueueCreateCentre(id = CENTRE_ID, name = "Reef Diving", number = "12345")
        val harness = ScanHarness(store) { call -> ScanReply.Body(centreView(), HttpStatusCode.OK) }

        harness.replayer.replay()

        assertEquals(1, harness.calls.size)
        assertTrue(jsonOf(harness.calls.single().body!!).containsKey("id"))
        assertEquals(ScanStatus.Existing, store.scanResults().single().status)
    }

    @Test
    fun `taking a person's code creates the Diver when the server says it is new`() = runTest {
        queue.enqueueTakeCode(buddyAnswer().let { "SSI;buddy;ada" }, create = true)
        val harness = ScanHarness(store) { call ->
            assertEquals("POST", call.method)
            assertEquals("/api/divers/from-code", call.path)
            val body = jsonOf(call.body!!)
            assertEquals("SSI;buddy;ada", (body["text"] as kotlinx.serialization.json.JsonPrimitive).content)
            assertEquals(true, (body["create"] as kotlinx.serialization.json.JsonPrimitive).content.toBoolean())
            ScanReply.Body(diverView(), HttpStatusCode.Created)
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        val scan = store.scanResults().single()
        assertEquals(ScanStatus.Created, scan.status)
        assertEquals("diver-1", scan.entityId)
        assertEquals("Ada Lovelace", scan.name)
    }

    @Test
    fun `taking a code the server already knows is Existing`() = runTest {
        queue.enqueueTakeCode("SSI;buddy;ada", diverId = "diver-1")
        val harness = ScanHarness(store) { call ->
            val body = jsonOf(call.body!!)
            assertEquals("diver-1", (body["diverId"] as kotlinx.serialization.json.JsonPrimitive).content)
            ScanReply.Body(diverView(), HttpStatusCode.OK)
        }

        harness.replayer.replay()

        assertEquals(ScanStatus.Existing, store.scanResults().single().status)
    }

    @Test
    fun `a code the server does not know is a failed result, not a retry`() = runTest {
        queue.enqueueScan("hello")
        val harness = ScanHarness(store) {
            ScanReply.Body("""{"code":"code_not_recognised"}""", HttpStatusCode.BadRequest)
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.dropped)
        assertTrue(store.pendingWrites().isEmpty())
        val scan = store.scanResults().single()
        assertEquals(ScanStatus.Failed, scan.status)
        assertEquals("code_not_recognised", scan.errorCode)
        assertNull(scan.actionId)
    }

    @Test
    fun `a 409 on a centre create adopts the centre that has the number`() = runTest {
        queue.enqueueCreateCentre(id = CENTRE_ID, name = "Reef Diving", number = "12345")
        val harness = ScanHarness(store) {
            ScanReply.Body(
                """{"code":"centre_external_id_taken","centre":${centreView("centre-9")}}""",
                HttpStatusCode.Conflict,
            )
        }

        harness.replayer.replay()

        val scan = store.scanResults().single()
        assertEquals(ScanStatus.Existing, scan.status)
        assertEquals("centre", scan.kind)
        assertEquals("centre-9", scan.entityId)
        assertEquals("Reef Diving, Sharm", scan.name)
        assertNull(scan.errorCode)
        assertNotNull(scan.detailJson)
    }

    @Test
    fun `a 409 centre_external_id_taken is a success, not a refusal notice`() = runTest {
        queue.enqueueCreateCentre(id = CENTRE_ID, name = "Reef Diving", number = "12345")
        val harness = ScanHarness(store) {
            ScanReply.Body(
                """{"code":"centre_external_id_taken","centre":{"id":"centre-9","name":"Reef Diving"}}""",
                HttpStatusCode.Conflict,
            )
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        assertEquals(0, result.dropped)
        assertTrue(result.notices.isEmpty())
        assertTrue(store.pendingWrites().isEmpty())
        assertEquals("Reef Diving", store.scanResults().single().name)
    }

    @Test
    fun `a 401 stops the scan replay and keeps it queued`() = runTest {
        queue.enqueueScan("hello")
        val harness = ScanHarness(store) { ScanReply.Body("", HttpStatusCode.Unauthorized) }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.SessionEnded>(result)
        assertEquals(1, harness.unauthorized)
        assertEquals(1, store.pendingWrites().size)
        assertTrue(store.scanResults().isEmpty())
    }

    @Test
    fun `a rate-limited read keeps the scan queued instead of dropping it`() = runTest {
        queue.enqueueScan("hello")
        val harness = ScanHarness(store) {
            ScanReply.Body("""{"code":"too_many_attempts"}""", HttpStatusCode.TooManyRequests)
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Stopped>(result)
        assertEquals(1, store.pendingWrites().size)
        assertTrue(store.scanResults().isEmpty())
    }

    @Test
    fun `no answer keeps the scan queued for the next trigger`() = runTest {
        queue.enqueueScan("hello")
        val harness = ScanHarness(store) { ScanReply.Offline }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Stopped>(result)
        assertEquals(1, store.pendingWrites().size)
        assertTrue(store.scanResults().isEmpty())
    }

    @Test
    fun `actions replay in enqueue order and one refusal does not stop the next`() = runTest {
        queue.enqueueScan("first")
        queue.enqueueCreateCentre(id = CENTRE_ID, name = "Reef Diving")
        queue.enqueueTakeCode("third", create = true)
        val harness = ScanHarness(store) { call ->
            when {
                jsonOf(call.body!!)["text"]?.toString()?.contains("first") == true ->
                    ScanReply.Body("""{"code":"code_not_recognised"}""", HttpStatusCode.BadRequest)
                call.path == "/api/dive-centres" -> ScanReply.Body(centreView(), HttpStatusCode.Created)
                else -> ScanReply.Body(diverView(), HttpStatusCode.Created)
            }
        }

        harness.replayer.replay()

        assertEquals(listOf("/api/verification-codes/read", "/api/dive-centres", "/api/divers/from-code"), harness.calls.map { it.path })
        // Only the refused read left an action behind, and a refusal drops it; the two writes are done.
        assertTrue(store.pendingWrites(PendingWriteStatus.Pending).isEmpty())
        assertEquals(listOf(ScanStatus.Created, ScanStatus.Created, ScanStatus.Failed), store.scanResults().map { it.status })
    }

    @Test
    fun `a resolved read keeps its text in the queue, never in the result`() = runTest {
        queue.enqueueScan("SSI;buddy;ada", centreId = CENTRE_ID)
        assertEquals(1, store.pendingWrites().size)

        ScanHarness(store) { ScanReply.Body(buddyAnswer()) }.replayer.replay()

        val scan = store.scanResults().single()
        assertNotNull(scan.actionId)
        assertTrue(scan.detailJson!!.contains("Ada"))
        assertEquals(1, store.pendingWrites().size)
        assertEquals(PendingWriteStatus.Awaiting, store.pendingWrites().single().status)
    }
}
