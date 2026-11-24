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
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private data class ConflictCall(val method: String, val path: String, val ifMatch: String?, val body: String?)

private sealed interface ConflictReply {
    data class Body(val text: String, val status: HttpStatusCode = HttpStatusCode.OK) : ConflictReply
}

private class ConflictHarness(store: DiveStore, handler: (ConflictCall) -> ConflictReply) {
    val calls = mutableListOf<ConflictCall>()

    private val api = ApiClient(
        engine = MockEngine { request ->
            val call = ConflictCall(
                method = request.method.value,
                path = request.url.encodedPath,
                ifMatch = request.headers["If-Match"],
                body = (request.body as? TextContent)?.text,
            )
            calls += call
            when (val reply = handler(call)) {
                is ConflictReply.Body -> respond(
                    reply.text,
                    reply.status,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }
        },
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = {},
        json = JSON,
    ).apply { baseUrl = "https://dive.example" }

    val replayer = WriteReplayer(api, store, clock = { NOW })
}

class WriteConflictResolutionTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)
    private val queue = WriteQueue(store, clock = { NOW })

    @AfterTest
    fun close() = driver.close()

    private fun seed(notes: String = "A good dive", participants: JsonArray = JsonArray(emptyList()), suit: JsonObject? = null) {
        store.putDive(diveObject(notes = notes, participants = participants, suit = suit), version = 3)
    }

    private suspend fun parkNotes(local: String = "Local", server: String = "Server"): WriteConflictView {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"$local"}"""))
        val harness = ConflictHarness(store) {
            ConflictReply.Body(
                """{"code":"dive_changed","dive":${diveObject(version = 5, notes = server)}}""",
                HttpStatusCode.PreconditionFailed,
            )
        }
        assertIs<WriteReplayResult.Drained>(harness.replayer.replay())
        return harness.replayer.conflicts().single()
    }

    @Test
    fun `a parked conflict is readable with both values and this phone preselected`() = runTest {
        val conflict = parkNotes()

        assertEquals("dive", conflict.entityType)
        assertEquals("dive-1", conflict.entityId)
        assertEquals(5L, conflict.serverVersion)
        val field = conflict.fields.single()
        assertEquals("notes", field.field)
        assertEquals("A good dive", (field.base as JsonPrimitive).content)
        assertEquals("Server", (field.server as JsonPrimitive).content)
        assertEquals("Local", (field.local as JsonPrimitive).content)
        assertEquals(ConflictSide.ThisPhone, field.preselected)
        assertEquals(42, conflict.diveNumber)
    }

    @Test
    fun `the queue snapshot counts what waits and what needs a decision`() = runTest {
        seed()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Local"}"""))
        val harness = ConflictHarness(store) {
            ConflictReply.Body(
                """{"code":"dive_changed","dive":${diveObject(version = 5, notes = "Server")}}""",
                HttpStatusCode.PreconditionFailed,
            )
        }
        assertEquals(WriteQueueSnapshot(pending = 1), harness.replayer.snapshot())

        harness.replayer.replay()

        assertEquals(WriteQueueSnapshot(conflicted = 1), harness.replayer.snapshot())
    }

    @Test
    fun `keeping this phone re-queues against the fresh version and shows the edit at once`() = runTest {
        val conflict = parkNotes(server = "Server")

        val resolution = ConflictHarness(store) { ConflictReply.Body("") }.replayer
            .resolve(conflict.entryId, mapOf("notes" to ConflictSide.ThisPhone))

        assertIs<ConflictResolution.Requeued>(resolution)
        val replayed = store.pendingWrites().single()
        assertEquals(PendingWriteStatus.Pending, replayed.status)
        assertEquals(5L, replayed.baseVersion)
        assertNull(replayed.conflictJson)
        assertTrue(replayed.baseJson!!.contains("\"version\":5"))
        assertEquals("Local", (jsonOf(replayed.payloadJson)["notes"] as JsonPrimitive).content)

        val detail = jsonOf(store.dive("dive-1")!!.detailJson!!)
        assertEquals("Local", (detail["notes"] as JsonPrimitive).content)
        assertEquals(5L, store.dive("dive-1")!!.version)
    }

    @Test
    fun `a resolved this-phone edit replays with the fresh version`() = runTest {
        val conflict = parkNotes(server = "Server")
        ConflictHarness(store) { ConflictReply.Body("") }.replayer
            .resolve(conflict.entryId, mapOf("notes" to ConflictSide.ThisPhone))

        val harness = ConflictHarness(store) { call ->
            assertEquals("PATCH", call.method)
            assertEquals("\"5\"", call.ifMatch)
            ConflictReply.Body(diveObject(version = 6, notes = "Local").toString())
        }

        val result = harness.replayer.replay()

        assertIs<WriteReplayResult.Drained>(result)
        assertEquals(1, result.applied)
        assertTrue(store.pendingWrites().isEmpty())
        assertEquals(6L, store.dive("dive-1")!!.version)
        assertEquals("Local", store.dive("dive-1")!!.notes)
    }

    @Test
    fun `keeping the server settles the entry and leaves the server value`() = runTest {
        val conflict = parkNotes(server = "Server")

        val resolution = ConflictHarness(store) { ConflictReply.Body("") }.replayer
            .resolve(conflict.entryId, mapOf("notes" to ConflictSide.Server))

        assertIs<ConflictResolution.KeptServer>(resolution)
        assertTrue(store.pendingWrites().isEmpty())
        assertEquals("Server", store.dive("dive-1")!!.notes)
        assertEquals(5L, store.dive("dive-1")!!.version)
    }

    @Test
    fun `unchosen fields keep the preselect this-phone side`() = runTest {
        val conflict = parkNotes(server = "Server")

        val resolution = ConflictHarness(store) { ConflictReply.Body("") }.replayer
            .resolve(conflict.entryId, emptyMap())

        assertIs<ConflictResolution.Requeued>(resolution)
        val payload = jsonOf(store.pendingWrites().single().payloadJson)
        assertEquals("Local", (payload["notes"] as JsonPrimitive).content)
    }

    @Test
    fun `mixed choices keep only the this-phone fields in the re-queued patch`() = runTest {
        seed()
        val localSuit = buildJsonObject {
            put("type", "wet")
            put("thicknessMm", 7)
            put("hood", JsonNull)
            put("undergarment", JsonNull)
        }
        val serverSuit = buildJsonObject {
            put("type", "wet")
            put("thicknessMm", 5)
            put("hood", JsonNull)
            put("undergarment", JsonNull)
        }
        queue.enqueueDivePatch(
            "dive-1",
            buildJsonObject {
                put("notes", "Local")
                put("suit", localSuit)
            },
        )
        val server = diveObject(version = 5, notes = "Server", suit = serverSuit)
        val harness = ConflictHarness(store) {
            ConflictReply.Body(
                """{"code":"dive_changed","dive":$server}""",
                HttpStatusCode.PreconditionFailed,
            )
        }
        harness.replayer.replay()
        val conflict = harness.replayer.conflicts().single()
        assertTrue(conflict.fields.map { it.field }.containsAll(listOf("notes", "suit")))

        val resolution = harness.replayer.resolve(
            conflict.entryId,
            mapOf("notes" to ConflictSide.ThisPhone, "suit" to ConflictSide.Server),
        )

        assertIs<ConflictResolution.Requeued>(resolution)
        val payload = jsonOf(store.pendingWrites().single().payloadJson)
        assertEquals("Local", (payload["notes"] as JsonPrimitive).content)
        assertFalse(payload.containsKey("suit"))
        val detail = jsonOf(store.dive("dive-1")!!.detailJson!!)
        assertEquals("Local", (detail["notes"] as JsonPrimitive).content)
        assertEquals(5, ((detail["suit"] as JsonObject)["thicknessMm"] as JsonPrimitive).content.toDouble().toInt())
    }

    @Test
    fun `a participant role conflict keeps this phone's role and re-queues the roster`() = runTest {
        seed(participants = participants("a" to "buddy"))
        queue.enqueueDiveParticipants("dive-1", participants("a" to "guide"))
        val harness = ConflictHarness(store) {
            ConflictReply.Body(
                """{"code":"dive_changed","dive":${diveObject(version = 5, participants = participants("a" to "instructor"))}}""",
                HttpStatusCode.PreconditionFailed,
            )
        }
        harness.replayer.replay()
        val conflict = harness.replayer.conflicts().single()
        assertEquals("participants.a", conflict.fields.single().field)

        val resolution = harness.replayer.resolve(conflict.entryId, mapOf("participants.a" to ConflictSide.ThisPhone))

        assertIs<ConflictResolution.Requeued>(resolution)
        val entry = store.pendingWrites().single()
        assertEquals(WriteEntities.DIVE_PARTICIPANTS, entry.entityType)
        assertEquals(5L, entry.baseVersion)
        val roster = jsonOf(entry.payloadJson)["participants"] as JsonArray
        assertEquals("guide", ((roster.single() as JsonObject)["role"] as JsonPrimitive).content)
    }

    @Test
    fun `a participant role conflict settled with the server leaves the server's roster`() = runTest {
        seed(participants = participants("a" to "buddy"))
        queue.enqueueDiveParticipants("dive-1", participants("a" to "guide"))
        val harness = ConflictHarness(store) {
            ConflictReply.Body(
                """{"code":"dive_changed","dive":${diveObject(version = 5, participants = participants("a" to "instructor"))}}""",
                HttpStatusCode.PreconditionFailed,
            )
        }
        harness.replayer.replay()
        val conflict = harness.replayer.conflicts().single()

        val resolution = harness.replayer.resolve(conflict.entryId, mapOf("participants.a" to ConflictSide.Server))

        assertIs<ConflictResolution.KeptServer>(resolution)
        assertTrue(store.pendingWrites().isEmpty())
        val detail = jsonOf(store.dive("dive-1")!!.detailJson!!)
        val roster = detail["participants"] as JsonArray
        assertEquals("instructor", ((roster.single() as JsonObject)["role"] as JsonPrimitive).content)
    }

    @Test
    fun `resolving an unknown entry fails loudly rather than dropping a write`() = runTest {
        val harness = ConflictHarness(store) { ConflictReply.Body("") }

        val error = runCatching { harness.replayer.resolve(404, emptyMap()) }.exceptionOrNull()

        assertNotNull(error)
        assertIs<IllegalArgumentException>(error)
    }
}
