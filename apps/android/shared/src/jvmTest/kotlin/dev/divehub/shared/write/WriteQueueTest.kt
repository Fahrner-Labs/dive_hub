package dev.divehub.shared.write

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWriteStatus
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WriteQueueTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)
    private val queue = WriteQueue(store, clock = { NOW })

    @AfterTest
    fun close() = driver.close()

    private fun seedDive(): JsonObject {
        val detail = diveObject()
        store.putDive(detail, version = 3)
        return detail
    }

    @Test
    fun `repeated edits to one entity coalesce into one entry with the original base version`() {
        seedDive()

        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"First"}"""))
        queue.enqueueDivePatch("dive-1", jsonOf("""{"siteId":"site-2"}"""))

        val pending = store.pendingWrites().single()
        assertEquals(3L, pending.baseVersion)
        assertEquals(1L, store.pendingWriteCount())
        val payload = jsonOf(pending.payloadJson)
        assertEquals("First", (payload["notes"] as JsonPrimitive).content)
        assertEquals("site-2", (payload["siteId"] as JsonPrimitive).content)
    }

    @Test
    fun `a later set and reset for one field replace the earlier choice`() {
        seedDive()

        queue.enqueueDivePatch("dive-1", jsonOf("""{"set":{"durationSeconds":3600}}"""))
        queue.enqueueDivePatch("dive-1", jsonOf("""{"reset":["durationSeconds"]}"""))

        val payload = jsonOf(store.pendingWrites().single().payloadJson)
        assertNull(payload["set"])
        assertEquals(
            listOf("durationSeconds"),
            (payload["reset"] as JsonArray).map { (it as JsonPrimitive).content },
        )
    }

    @Test
    fun `an explicit null is kept as a value`() {
        seedDive()

        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":null}"""))

        val payload = jsonOf(store.pendingWrites().single().payloadJson)
        assertTrue(payload.containsKey("notes"))
        assertEquals("null", payload["notes"].toString())
    }

    @Test
    fun `edits to different entities are separate entries in enqueue order`() {
        seedDive()
        val other = diveObject(id = "dive-2")
        store.putDive(other, version = 7)

        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"One"}"""))
        queue.enqueueDivePatch("dive-2", jsonOf("""{"notes":"Two"}"""))

        assertEquals(listOf("dive-1", "dive-2"), store.pendingWrites().map { it.entityId })
        assertEquals(listOf(3L, 7L), store.pendingWrites().map { it.baseVersion })
    }

    @Test
    fun `an edit after a parked conflict makes the entry pending again`() {
        seedDive()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"First"}"""))
        val parked = store.pendingWrites().single().copy(
            status = PendingWriteStatus.Conflicted,
            conflictJson = """{"serverVersion":5}""",
        )
        store.updatePendingWrite(parked)

        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Second"}"""))

        val entry = store.pendingWrites().single()
        assertEquals(PendingWriteStatus.Pending, entry.status)
        assertNull(entry.conflictJson)
        assertEquals("Second", (jsonOf(entry.payloadJson)["notes"] as JsonPrimitive).content)
        assertEquals(3L, entry.baseVersion)
    }

    @Test
    fun `the queue survives reopening the store`() {
        seedDive()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Waiting"}"""))

        val reopened = DiveStore.open(driver)

        assertEquals(1, reopened.pendingWrites().size)
        assertEquals("Waiting", (jsonOf(reopened.pendingWrites().single().payloadJson)["notes"] as JsonPrimitive).content)
    }

    @Test
    fun `an action is stored in global enqueue order and never coalesces`() {
        seedDive()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Waiting"}"""))
        queue.enqueueAction("scan", "code-1", jsonOf("""{"code":"abc"}"""))
        queue.enqueueAction("scan", "code-1", jsonOf("""{"code":"def"}"""))

        assertEquals(3, store.pendingWrites().size)
        assertEquals("scan", store.pendingWrites().last().entityType)
    }

    @Test
    fun `a wipe takes the queue with it`() {
        seedDive()
        queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"Waiting"}"""))

        store.wipe()

        assertEquals(0L, store.pendingWriteCount())
    }

    @Test
    fun `a dive without a server version cannot be queued against`() {
        store.upsertDive(
            dev.divehub.shared.store.Dive(
                id = "dive-1",
                diverId = "diver-1",
                startsAt = "2026-11-01T10:00:00Z",
                detailJson = diveObject().toString(),
            ),
        )

        val error = runCatching { queue.enqueueDivePatch("dive-1", jsonOf("""{"notes":"x"}""")) }.exceptionOrNull()

        assertNotNull(error)
        assertTrue(error is IllegalArgumentException)
    }
    @Test
    fun `an edit applies to the store at once and keeps the base version`() {
        seedDive()

        queue.editDive("dive-1", jsonOf("""{"notes":"Local","set":{"durationSeconds":3600}}"""))

        val dive = store.dive("dive-1")!!
        assertEquals("Local", dive.notes)
        assertEquals(3600L, dive.durationSeconds)
        assertEquals(3L, dive.version)
        val detail = jsonOf(dive.detailJson!!)
        val values = detail["values"] as JsonObject
        assertEquals(3600.0, (values["durationSeconds"] as JsonPrimitive).content.toDouble())
        assertEquals(listOf("durationSeconds"), (detail["overrides"] as JsonArray).map { (it as JsonPrimitive).content })
    }

    @Test
    fun `a reset drops the override at once and leaves the value for the server to recompute`() {
        store.putDive(diveObject(maxDepthM = 30.0, overrides = listOf("maxDepthM")), version = 3)

        queue.editDive("dive-1", jsonOf("""{"reset":["maxDepthM"]}"""))

        val dive = store.dive("dive-1")!!
        assertEquals(30.0, dive.maxDepthM)
        assertEquals(3L, dive.version)
        assertTrue((jsonOf(dive.detailJson!!)["overrides"] as JsonArray).isEmpty())
    }

    @Test
    fun `a participants edit applies at once with names from the store`() {
        seedDive()
        store.upsertDiver(
            dev.divehub.shared.store.Diver(
                id = "a",
                name = "Ada",
                isOwn = false,
                external = false,
                apneaCounts = false,
                divesBefore = 0,
            ),
        )

        queue.editDiveParticipants("dive-1", participants("a" to "guide", "c" to "buddy"))

        val detail = jsonOf(store.dive("dive-1")!!.detailJson!!)
        val roster = (detail["participants"] as JsonArray).map { it as JsonObject }
        assertEquals(listOf("a", "c"), roster.map { (it["diverId"] as JsonPrimitive).content })
        assertEquals(listOf("Ada", "c"), roster.map { (it["name"] as JsonPrimitive).content })
        val queued = jsonOf(store.pendingWrites().single().payloadJson)["participants"]!!
        assertFalse(queued.toString().contains("name"))
    }

}
