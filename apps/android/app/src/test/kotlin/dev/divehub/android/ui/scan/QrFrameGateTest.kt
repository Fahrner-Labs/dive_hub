package dev.divehub.android.ui.scan

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The live scanner's frame gate: analysis is throttled, and the first code read is the only one reported, so the
 * camera can stop on it. Pure logic, so the camera pipeline itself stays a thin wrapper.
 */
class QrFrameGateTest {
    @Test
    fun `frames inside the interval are skipped so analysis stays cheap`() {
        var now = 0L
        val gate = QrFrameGate(minIntervalMs = 250, clock = { now })

        assertTrue(gate.due())
        now = 249
        assertFalse(gate.due())
        now = 250
        assertTrue(gate.due())
    }

    @Test
    fun `only the first non-blank code is reported and nothing is read after it`() {
        var now = 0L
        val gate = QrFrameGate(minIntervalMs = 250, clock = { now })

        assertNull(gate.firstText(null))
        assertNull(gate.firstText("  "))
        assertEquals("  SSI;buddy;ada  ", gate.firstText("  SSI;buddy;ada  "))
        assertTrue(gate.isFinished)
        assertNull(gate.firstText("SSI;buddy;ada"))
        now = 10_000
        assertFalse(gate.due())
    }
}
