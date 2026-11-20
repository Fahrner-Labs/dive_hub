package dev.divehub.android

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * The crash hook (ADR 0080 D1): an uncaught exception is recorded in the local diagnostics buffer and then handed to
 * the previous handler, so the app still crashes the way it did before.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class DiveHubApplicationTest {
    private val previous = Thread.getDefaultUncaughtExceptionHandler()

    @After
    fun restoreHandler() {
        Thread.setDefaultUncaughtExceptionHandler(previous)
    }

    @Test
    fun `an uncaught exception is recorded and chained to the previous handler`() {
        val app = RuntimeEnvironment.getApplication() as DiveHubApplication
        app.container.diagnosticsBuffer.clear()
        val chained = mutableListOf<Throwable>()
        Thread.setDefaultUncaughtExceptionHandler { _, throwable -> chained += throwable }
        app.installCrashHandler()
        val error = IllegalStateException("boom")

        Thread.getDefaultUncaughtExceptionHandler()!!.uncaughtException(Thread.currentThread(), error)

        assertEquals(listOf<Throwable>(error), chained)
        val entry = app.container.diagnostics.preview(1).single()
        assertEquals("uncaught_exception", entry.code)
        assertEquals("boom", entry.message)
        assertTrue(entry.stack!!.contains("IllegalStateException"))
        assertEquals(1, app.container.diagnostics.count())
    }
}
