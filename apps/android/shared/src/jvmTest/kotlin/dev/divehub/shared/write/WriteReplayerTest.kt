package dev.divehub.shared.write

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWriteStatus
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private data class Call(
    val method: String,
    val path: String,
    val ifMatch: String?,
    val body: String?,
)

private sealed interface Reply {
    data class Body(val text: String, val status: HttpStatusCode = HttpStatusCode.OK) : Reply
    data object Offline : Reply
}

private class Harness(store: DiveStore, handler: (Call) -> Reply) {
    val calls = mutableListOf<Call>()
    var unauthorized = 0

    private val api = ApiClient(
        engine = MockEngine { request ->
            val call = Call(
                method = request.method.value,
                path = request.url.encodedPath,
                ifMatch = request.headers["If-Match"],
                body = (request.body as? TextContent)?.text,
            )
            calls += call
            when (val reply = handler(call)) {
                is Reply.Body -> respond(
                    reply.text,
                    reply.status,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
                Reply.Offline -> throw IOException("offline")
            }
        },
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = { unauthorized += 1 },
        json = JSON,
    ).apply { baseUrl = "https://dive.example" }

    val replayer = WriteReplayer(api, store, clock = { NOW })
}

class WriteReplayerTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)
    private val queue = WriteQueue(store, clock = { NOW })

    @AfterTest
    fun close() = driver.close()

    private fun seed(participants: JsonArray = JsonArray(emptyList())) {
        store.putDive(diveObject(participants = participants), version = 3)
    }

    @Test
    fun `a waiting edit replays with If-Match and lands in the store`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Edited"}"""))
        val harness = Harness(store) { call ->
            assertEquals("PATCH", call.method)
            assertEquals("/api/dives/dive-1", call.path)
            assertEquals("\"3\"", call.ifMatch)
            assertTrue(call.body!!.contains("Edited"))
            Reply.Body(diveObject(version = 4, notes = "Edited").toString())
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        assertEquals(1, harness.calls.size)
        assertTrue(store.pendingWrites().isEmpty())
        assertEquals(4L, store.dive("dive-1")!!.version)
        assertEquals("Edited", store.dive("dive-1")!!.notes)
    }

    @Test
    fun `a disjoint 412 change merges quietly and is re-sent with the fresh version`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"set":{"durationSeconds":3600}}"""))
        val harness = Harness(store) { call ->
            if (call.ifMatch == "\"3\"") {
                Reply.Body(
                    """{"code":"dive_changed","dive":${diveObject(version = 5, notes = "Server note")}}""",
                    HttpStatusCode.PreconditionFailed,
                )
            } else {
                Reply.Body(diveObject(version = 6, notes = "Server note", durationSeconds = 3600.0).toString())
            }
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        assertEquals(0, result.conflicted)
        assertEquals(2, harness.calls.size)
        assertEquals("\"3\"", harness.calls[0].ifMatch)
        assertEquals("\"5\"", harness.calls[1].ifMatch)
        val replayed = jsonOf(harness.calls[1].body!!)
        val set = replayed["set"] as JsonObject
        assertEquals(3600.0, (set["durationSeconds"] as JsonPrimitive).content.toDouble())
        assertFalse(replayed.containsKey("notes"))
        assertEquals(6L, store.dive("dive-1")!!.version)
        assertEquals("Server note", store.dive("dive-1")!!.notes)
        assertEquals(3600L, store.dive("dive-1")!!.durationSeconds)
        assertTrue(store.pendingWrites().isEmpty())
    }

    @Test
    fun `a field changed on both sides parks the entry as a conflict`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Local"}"""))
        val harness = Harness(store) {
            Reply.Body(
                """{"code":"dive_changed","dive":${diveObject(version = 5, notes = "Server")}}""",
                HttpStatusCode.PreconditionFailed,
            )
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(0, result.applied)
        assertEquals(1, result.conflicted)
        assertEquals(1, harness.calls.size)
        val entry = store.pendingWrites().single()
        assertEquals(PendingWriteStatus.Conflicted, entry.status)
        val conflictJson = assertNotNull(entry.conflictJson)
        assertTrue(conflictJson.contains("\"notes\""))
        assertEquals("Local", (jsonOf(entry.payloadJson)["notes"] as JsonPrimitive).content)
        assertEquals(3L, store.dive("dive-1")!!.version)
    }

    @Test
    fun `a 404 drops the entry with a notice naming the kept dive`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Local"}"""))
        val harness = Harness(store) {
            Reply.Body(
                """{"code":"dive_not_found","mergedInto":"dive-9"}""",
                HttpStatusCode.NotFound,
            )
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.dropped)
        val notice = result.notices.single()
        assertEquals(WriteDropReason.NotFound, notice.reason)
        assertEquals("dive-9", notice.mergedInto)
        assertTrue(store.pendingWrites().isEmpty())
    }

    @Test
    fun `a 401 stops replay and keeps the queue`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Local"}"""))
        val harness = Harness(store) { Reply.Body("", HttpStatusCode.Unauthorized) }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.SessionEnded>(result)
        assertEquals(1, harness.unauthorized)
        assertEquals(PendingWriteStatus.Pending, store.pendingWrites().single().status)
    }

    @Test
    fun `no answer stops replay and keeps the entry pending`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Local"}"""))
        val harness = Harness(store) { Reply.Offline }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Stopped>(result)
        assertEquals(PendingWriteStatus.Pending, store.pendingWrites().single().status)
    }

    @Test
    fun `a 5xx stops replay and keeps the entry pending`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Local"}"""))
        val harness = Harness(store) { Reply.Body("", HttpStatusCode.InternalServerError) }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Stopped>(result)
        assertEquals(PendingWriteStatus.Pending, store.pendingWrites().single().status)
    }

    @Test
    fun `a 400 marks the entry failed with the server code`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Local"}"""))
        val harness = Harness(store) {
            Reply.Body("""{"code":"dive_invalid"}""", HttpStatusCode.BadRequest)
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.failed)
        val entry = store.pendingWrites().single()
        assertEquals(PendingWriteStatus.Failed, entry.status)
        assertEquals("dive_invalid", entry.lastError)
    }

    @Test
    fun `participants merge element-wise and replay as a PUT`() = runTest {
        seed(participants("a" to "buddy"))
        queue.enqueueDiveParticipants("dive-1", participants("a" to "buddy", "c" to "instructor"))
        val harness = Harness(store) { call ->
            if (call.ifMatch == "\"3\"") {
                Reply.Body(
                    """{"code":"dive_changed","dive":${diveObject(version = 5, participants = participants("a" to "buddy", "b" to "guide"))}}""",
                    HttpStatusCode.PreconditionFailed,
                )
            } else {
                Reply.Body(diveObject(version = 6, participants = participants("a" to "buddy", "c" to "instructor", "b" to "guide")).toString())
            }
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        assertEquals(2, harness.calls.size)
        assertEquals("PUT", harness.calls[0].method)
        assertEquals("/api/dives/dive-1/participants", harness.calls[0].path)
        assertEquals("\"3\"", harness.calls[0].ifMatch)
        assertEquals("\"5\"", harness.calls[1].ifMatch)
        val replayed = jsonOf(harness.calls[1].body!!)["participants"] as JsonArray
        val ids = replayed.map { ((it as JsonObject)["diverId"] as JsonPrimitive).content }
        assertTrue(ids.containsAll(listOf("a", "b", "c")))
        assertEquals(6L, store.dive("dive-1")!!.version)
    }

    @Test
    fun `the same participant role changed on both sides parks a conflict`() = runTest {
        seed(participants("a" to "buddy"))
        queue.enqueueDiveParticipants("dive-1", participants("a" to "guide"))
        val harness = Harness(store) {
            Reply.Body(
                """{"code":"dive_changed","dive":${diveObject(version = 5, participants = participants("a" to "instructor"))}}""",
                HttpStatusCode.PreconditionFailed,
            )
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.conflicted)
        val entry = store.pendingWrites().single()
        assertEquals(PendingWriteStatus.Conflicted, entry.status)
        assertTrue(entry.conflictJson!!.contains("participants.a"))
    }
}
