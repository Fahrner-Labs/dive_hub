package dev.divehub.android.notifications

import dev.divehub.shared.store.Dive
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** The FCM payload is a hint in the SSE stream's shape; a malformed or unknown one is ignored (ADR 0085). */
class PushHintTest {
    @Test
    fun `a dive hint in version one is accepted`() {
        assertEquals(PushHint(kind = "dive", id = "dive-1"), PushHint.from(mapOf("v" to "1", "kind" to "dive", "id" to "dive-1")))
    }

    @Test
    fun `another protocol version, kind or a blank id is ignored`() {
        assertNull(PushHint.from(mapOf("v" to "2", "kind" to "dive", "id" to "dive-1")))
        assertNull(PushHint.from(mapOf("v" to "1", "kind" to "site", "id" to "site-1")))
        assertNull(PushHint.from(mapOf("v" to "1", "kind" to "dive", "id" to " ")))
        assertNull(PushHint.from(mapOf("kind" to "dive", "id" to "dive-1")))
    }

    @Test
    fun `only a present, active and changed dive earns a notification`() {
        val first = dive(version = 1)
        val second = dive(version = 2)

        assertTrue(pushFoundNewDive(null, first))
        assertTrue(pushFoundNewDive(first, second))
        assertFalse(pushFoundNewDive(first, first))
        assertFalse(pushFoundNewDive(first, null))
        assertFalse(pushFoundNewDive(first, second.copy(deletedAt = "2026-11-25T10:00:00Z")))
    }

    private fun dive(version: Long) = Dive(
        id = "dive-1",
        diverId = "diver-1",
        version = version,
        startsAt = "2026-11-25T10:00:00Z",
    )
}
