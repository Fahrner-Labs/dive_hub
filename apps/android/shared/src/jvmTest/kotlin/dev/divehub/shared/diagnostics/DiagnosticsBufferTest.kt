package dev.divehub.shared.diagnostics

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.store.db.DiveHubDatabase
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class DiagnosticsBufferTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val buffer = newBuffer()

    @AfterTest
    fun close() = driver.close()

    private fun newBuffer(): DiagnosticsBuffer {
        DiveHubDatabase.Schema.create(driver)
        return DiagnosticsBuffer(DiveHubDatabase(driver))
    }

    private fun entry(index: Int) = DiagnosticEntry(
        occurredAt = "2026-11-20T10:00:00.000Z",
        level = DiagnosticLevel.ERROR,
        code = "e$index",
        message = "message $index",
    )

    @Test
    fun `the buffer keeps the newest 200 entries and evicts the oldest`() {
        repeat(205) { buffer.capture(entry(it)) }

        assertEquals(DiagnosticsBuffer.CAP, buffer.count())
        assertEquals("e5", buffer.oldest(1).single().code)
        assertEquals("e204", buffer.newest(1).single().code)
    }

    @Test
    fun `newest is newest first and oldest is oldest first`() {
        repeat(3) { buffer.capture(entry(it)) }

        assertEquals(listOf("e2", "e1", "e0"), buffer.newest(3).map(DiagnosticEntry::code))
        assertEquals(listOf("e0", "e1", "e2"), buffer.oldest(3).map(DiagnosticEntry::code))
    }

    @Test
    fun `a change of User wipes the buffer`() = runTest {
        repeat(3) { buffer.capture(entry(it)) }

        buffer.forget()

        assertEquals(0, buffer.count())
        assertTrue(buffer.newest(10).isEmpty())
    }

    @Test
    fun `a failed wipe surfaces instead of silently keeping the previous user's entries`() = runTest {
        val closed = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        DiveHubDatabase.Schema.create(closed)
        val failing = DiagnosticsBuffer(DiveHubDatabase(closed))
        failing.capture(entry(1))
        closed.close()

        assertFailsWith<Exception> { failing.forget() }
    }

    @Test
    fun `message and stack are truncated to the contract limits`() {
        buffer.capture(
            DiagnosticEntry(
                occurredAt = "2026-11-20T10:00:00.000Z",
                level = DiagnosticLevel.WARN,
                message = "m".repeat(DiagnosticsBuffer.MESSAGE_LIMIT + 1),
                stack = "s".repeat(DiagnosticsBuffer.STACK_LIMIT + 1),
            ),
        )

        val stored = buffer.newest(1).single()
        assertEquals(DiagnosticsBuffer.MESSAGE_LIMIT, stored.message!!.length)
        assertEquals(DiagnosticsBuffer.STACK_LIMIT, stored.stack!!.length)
        assertEquals(DiagnosticLevel.WARN, stored.level)
    }

    @Test
    fun `a context survives as a string map, and an empty one is not stored`() {
        buffer.capture(entry(1).copy(context = mapOf("screen" to "logbook", "state" to "offline")))
        buffer.capture(entry(2))

        assertEquals(mapOf("screen" to "logbook", "state" to "offline"), buffer.newest(2).last().context)
        assertTrue(buffer.newest(1).single().context.isEmpty())
    }

    @Test
    fun `migration 2 creates the diagnostics table`() {
        val migrated = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        try {
            // A v2 store already has sync_state (with the counts column from v1→v2); later migrations alter it.
            migrated.execute(
                null,
                "CREATE TABLE sync_state (id INTEGER NOT NULL PRIMARY KEY, lastFullSyncAt TEXT, lastHintAt TEXT, " +
                    "schemaVersion INTEGER NOT NULL, countsJson TEXT)",
                0,
            )
            DiveHubDatabase.Schema.migrate(migrated, 2, DiveHubDatabase.Schema.version)
            val migratedBuffer = DiagnosticsBuffer(DiveHubDatabase(migrated))

            migratedBuffer.capture(entry(1))

            assertEquals(1, migratedBuffer.count())
            assertEquals("e1", migratedBuffer.newest(1).single().code)
        } finally {
            migrated.close()
        }
    }
}
