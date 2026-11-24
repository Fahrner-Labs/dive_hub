package dev.divehub.android.ui.writes

import dev.divehub.shared.write.ConflictResolution
import dev.divehub.shared.write.ConflictSide
import dev.divehub.shared.write.WriteConflictView
import dev.divehub.shared.write.QueuedWriteView
import dev.divehub.shared.write.WriteDropReason
import dev.divehub.shared.write.WriteNotice
import dev.divehub.shared.write.WriteQueueSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** The pending indicator's state holder (ADR 0084 D4): counts, capabilities and the two user actions. */
@OptIn(ExperimentalCoroutinesApi::class)
class WritesViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val writeable = MutableStateFlow<Boolean?>(null)
    private val revision = MutableStateFlow(0L)
    private val notices = MutableStateFlow(emptyList<WriteNotice>())
    private val queueRevision = MutableStateFlow(0L)
    private var dismissals = 0
    private var snapshot = WriteQueueSnapshot()
    private var conflicts = emptyList<WriteConflictView>()
    private var queued = emptyList<QueuedWriteView>()
    private var resolutions = mutableListOf<Pair<Long, Map<String, ConflictSide>>>()
    private var drains = 0
    private var retries = 0

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `the state reads the queue and its parked conflicts`() = runTest(dispatcher) {
        snapshot = WriteQueueSnapshot(pending = 2, conflicted = 1)
        conflicts = listOf(conflict())
        queued = listOf(queuedEntry())
        val viewModel = viewModel()
        advanceUntilIdle()

        assertEquals(2, viewModel.state.value.pending)
        assertEquals(1, viewModel.state.value.conflicted)
        assertEquals(1, viewModel.state.value.conflicts.size)
        assertTrue(viewModel.state.value.visible)
        assertTrue(viewModel.state.value.canReview)
        assertEquals(1, viewModel.state.value.queued.size)
    }

    @Test
    fun `a pre-feed server hides a non-empty queue rather than disabling it`() = runTest(dispatcher) {
        writeable.value = false
        snapshot = WriteQueueSnapshot(pending = 2, conflicted = 1)
        conflicts = listOf(conflict())
        queued = listOf(queuedEntry())
        val viewModel = viewModel()
        advanceUntilIdle()

        assertFalse(viewModel.state.value.visible)
        assertFalse(viewModel.state.value.canReview)
    }

    @Test
    fun `an unknown capability still shows saved writes from an offline start`() = runTest(dispatcher) {
        snapshot = WriteQueueSnapshot(pending = 1)
        val viewModel = viewModel()
        advanceUntilIdle()

        assertTrue(viewModel.state.value.visible)
    }

    @Test
    fun `resolving re-queues the entry and drains it`() = runTest(dispatcher) {
        snapshot = WriteQueueSnapshot(conflicted = 1)
        conflicts = listOf(conflict())
        val viewModel = viewModel()
        advanceUntilIdle()

        snapshot = WriteQueueSnapshot(pending = 1)
        conflicts = emptyList()
        viewModel.resolve(7, mapOf("notes" to ConflictSide.Server))
        advanceUntilIdle()

        assertEquals(1, resolutions.size)
        assertEquals(7L, resolutions.single().first)
        assertEquals(ConflictSide.Server, resolutions.single().second["notes"])
        assertEquals(1, drains)
        assertEquals(1, viewModel.state.value.pending)
        assertTrue(viewModel.state.value.conflicts.isEmpty())
    }

    @Test
    fun `a failed resolve is a state on the screen, not a lost entry`() = runTest(dispatcher) {
        snapshot = WriteQueueSnapshot(conflicted = 1)
        conflicts = listOf(conflict())
        val viewModel = WritesViewModel(
            writeable = writeable,
            revision = revision,
            queueRevision = queueRevision,
            readSnapshot = { snapshot },
            readConflicts = { conflicts },
            readQueued = { queued },
            notices = notices,
            resolveConflict = { _, _ -> throw IllegalArgumentException("unreadable") },
            drain = { drains += 1 },
            retryQueue = { retries += 1 },
            clearNotices = {
                dismissals += 1
                notices.value = emptyList()
            },
            io = dispatcher,
        )
        advanceUntilIdle()

        viewModel.resolve(7, emptyMap())
        advanceUntilIdle()

        assertTrue(viewModel.state.value.actionFailed)
        assertFalse(viewModel.state.value.busy)
        assertEquals(0, drains)
    }

    @Test
    fun `a dropped change keeps the indicator visible and can be dismissed`() = runTest(dispatcher) {
        notices.value = listOf(
            WriteNotice(
                entityType = "dive",
                entityId = "dive-1",
                status = 404,
                reason = WriteDropReason.NotFound,
            ),
        )
        val viewModel = viewModel()
        advanceUntilIdle()

        assertTrue(viewModel.state.value.visible)
        assertEquals(1, viewModel.state.value.notices.size)

        viewModel.dismissNotices()
        advanceUntilIdle()

        assertEquals(1, dismissals)
    }

    @Test
    fun `retry failed puts failed entries back in line and reads the result`() = runTest(dispatcher) {
        snapshot = WriteQueueSnapshot(failed = 1)
        val viewModel = viewModel()
        advanceUntilIdle()

        snapshot = WriteQueueSnapshot()
        viewModel.retryFailed()
        advanceUntilIdle()

        assertEquals(1, retries)
        assertEquals(0, viewModel.state.value.failed)
    }

    private fun viewModel() = WritesViewModel(
        writeable = writeable,
        revision = revision,
        queueRevision = queueRevision,
        readSnapshot = { snapshot },
        readConflicts = { conflicts },
        readQueued = { queued },
        notices = notices,
        resolveConflict = { entryId, choices ->
            resolutions += entryId to choices
            ConflictResolution.Requeued(entryId)
        },
        drain = { drains += 1 },
        retryQueue = { retries += 1 },
        clearNotices = {
            dismissals += 1
            notices.value = emptyList()
        },
        io = dispatcher,
    )

    private fun queuedEntry() = QueuedWriteView(
        entryId = 8,
        fields = listOf("notes"),
        failed = false,
        diveNumber = 43,
    )

    private fun conflict() = WriteConflictView(
        entryId = 7,
        entityType = "dive",
        entityId = "dive-1",
        serverVersion = 5,
        fields = emptyList(),
        diveNumber = 42,
    )
}
