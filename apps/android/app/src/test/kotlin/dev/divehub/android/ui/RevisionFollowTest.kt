package dev.divehub.android.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/** One sync's phases bump the store revision many times; open screens must still reread it once (FAH-333). */
@OptIn(ExperimentalCoroutinesApi::class)
class RevisionFollowTest {
    @Test
    fun `a burst of store writes rereads once`() = runTest {
        val revision = MutableStateFlow(0L)
        val seen = mutableListOf<Long>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            revision.followStoreWrites().collect { seen += it }
        }

        for (value in 1L..5L) revision.value = value
        advanceTimeBy(WRITE_BURST_MS + 1)
        runCurrent()

        assertEquals(listOf(5L), seen)
    }

    @Test
    fun `a write after the burst settles rereads again`() = runTest {
        val revision = MutableStateFlow(0L)
        val seen = mutableListOf<Long>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            revision.followStoreWrites().collect { seen += it }
        }

        revision.value = 1
        advanceTimeBy(WRITE_BURST_MS + 1)
        runCurrent()
        revision.value = 2
        advanceTimeBy(WRITE_BURST_MS + 1)
        runCurrent()

        assertEquals(listOf(1L, 2L), seen)
    }
}
