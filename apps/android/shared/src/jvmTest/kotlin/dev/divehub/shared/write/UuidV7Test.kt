package dev.divehub.shared.write

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class UuidV7Test {
    @Test
    fun `a generated id is a lowercase uuid version 7 with the variant bits`() {
        val id = UuidV7.generate(nowMillis = 1_764_000_000_000, random = Random(7))

        assertTrue(Regex("^[0-9a-f]{8}-[0-9a-f]{4}-7[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$").matches(id), id)
        // The timestamp is the first 48 bits, most significant first: 2025-11-23T… in milliseconds.
        assertEquals("019ab697", id.substring(0, 8))
    }

    @Test
    fun `two ids made in the same millisecond differ`() {
        val first = UuidV7.generate(nowMillis = 1_764_000_000_000)
        val second = UuidV7.generate(nowMillis = 1_764_000_000_000)

        assertNotEquals(first, second)
    }
}
