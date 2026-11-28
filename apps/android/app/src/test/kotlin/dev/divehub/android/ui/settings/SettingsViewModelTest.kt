package dev.divehub.android.ui.settings

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.android.notifications.PushState
import dev.divehub.android.notifications.PushStatus
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.DiagnosticLevel
import dev.divehub.shared.diagnostics.DiagnosticsAccess
import dev.divehub.shared.diagnostics.ReportProblemResult
import dev.divehub.shared.settings.SettingsRepository
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncState
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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** The settings state holder: one guarded refresh, the sync line following the store, and the Debug group's reads. */
@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var driver: JdbcSqliteDriver

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        Class.forName("org.sqlite.JDBC")
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    }

    @After
    fun tearDown() {
        driver.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `sync now runs the refresher once and resets the busy state`() = runTest(dispatcher) {
        var syncs = 0
        val viewModel = viewModel(refreshSync = { syncs += 1 })

        viewModel.syncNow()
        advanceUntilIdle()

        assertEquals(1, syncs)
        assertEquals(false, viewModel.state.value.syncing)
    }

    @Test
    fun `the sync line follows the store revision and the connection and offline flags`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val revision = MutableStateFlow(0L)
        val connected = MutableStateFlow(false)
        val offline = MutableStateFlow(false)
        val viewModel = viewModel(store = store, revision = revision, connected = connected, offline = offline)
        advanceUntilIdle()
        assertEquals(null, viewModel.state.value.syncedAt)
        assertFalse(viewModel.state.value.connected)
        assertFalse(viewModel.state.value.offline)

        store.saveSyncState(SyncState(lastFullSyncAt = "2026-11-18T12:00:00Z", schemaVersion = DiveStore.schemaVersion))
        revision.value = 1
        advanceUntilIdle()
        assertEquals("2026-11-18T12:00:00Z", viewModel.state.value.syncedAt)

        connected.value = true
        advanceUntilIdle()
        assertTrue(viewModel.state.value.connected)

        offline.value = true
        advanceUntilIdle()
        assertTrue(viewModel.state.value.offline)
    }

    @Test
    fun `the debug group reads the instance status, the count and the preview`() = runTest(dispatcher) {
        val diagnostics = FakeDiagnostics(entries = entries("e0", "e1"))
        val viewModel = viewModel(diagnostics = diagnostics)
        viewModel.refreshDiagnostics()
        advanceUntilIdle()

        val debug = viewModel.state.value.diagnostics
        assertEquals(true, debug.enabled)
        assertEquals(2, debug.count)
        assertEquals(listOf("e1", "e0"), debug.preview.map(DiagnosticEntry::code))
    }

    @Test
    fun `the push row follows the lifecycle status`() = runTest(dispatcher) {
        val push = MutableStateFlow(PushStatus())
        val viewModel = viewModel(pushStatus = push)
        advanceUntilIdle()
        assertEquals(PushState.Unknown, viewModel.state.value.push.state)

        push.value = PushStatus(PushState.Registered, deviceId = "device-1", lastSeenAt = "2026-11-28T10:00:00Z")
        advanceUntilIdle()

        assertEquals(PushState.Registered, viewModel.state.value.push.state)
        assertEquals("device-1", viewModel.state.value.push.deviceId)
        assertEquals("2026-11-28T10:00:00Z", viewModel.state.value.push.lastSeenAt)
    }

    @Test
    fun `the debug queue counts read the snapshot and follow the queue and store revisions`() = runTest(dispatcher) {
        var snapshot = WriteQueueSnapshot(pending = 1)
        val queueRevision = MutableStateFlow(0L)
        val revision = MutableStateFlow(0L)
        val viewModel = viewModel(
            revision = revision,
            queueRevision = queueRevision,
            readSnapshot = { snapshot },
        )
        advanceUntilIdle()

        assertEquals(SettingsQueueState(pending = 1), viewModel.state.value.queue)

        snapshot = WriteQueueSnapshot(pending = 2, failed = 1, conflicted = 3)
        queueRevision.value = 1
        advanceUntilIdle()

        assertEquals(
            SettingsQueueState(pending = 2, failed = 1, conflicted = 3),
            viewModel.state.value.queue,
        )

        // A replay that sends the waiting entries bumps the sync revision, not the queue revision.
        snapshot = WriteQueueSnapshot(failed = 1, conflicted = 3)
        revision.value = 1
        advanceUntilIdle()

        assertEquals(SettingsQueueState(failed = 1, conflicted = 3), viewModel.state.value.queue)
    }

    @Test
    fun `report a problem uploads the buffer and clears the count`() = runTest(dispatcher) {
        val diagnostics = FakeDiagnostics(entries = entries("e0", "e1"))
        val viewModel = viewModel(diagnostics = diagnostics)
        viewModel.refreshDiagnostics()
        advanceUntilIdle()

        viewModel.reportProblem()
        advanceUntilIdle()

        assertEquals(ReportProblemResult.Sent, viewModel.state.value.diagnostics.reportResult)
        assertEquals(0, viewModel.state.value.diagnostics.count)
        assertEquals(1, diagnostics.reports)
        assertTrue(diagnostics.entries.isEmpty())
    }

    @Test
    fun `report a problem stays off while the instance has diagnostics off`() = runTest(dispatcher) {
        val diagnostics = FakeDiagnostics(enabled = false, entries = entries("e0"))
        val viewModel = viewModel(diagnostics = diagnostics)
        viewModel.refreshDiagnostics()
        advanceUntilIdle()

        viewModel.reportProblem()
        advanceUntilIdle()

        assertEquals(false, viewModel.state.value.diagnostics.enabled)
        assertNull(viewModel.state.value.diagnostics.reportResult)
        assertEquals(0, diagnostics.reports)
        assertEquals(1, diagnostics.entries.size)
    }

    @Test
    fun `a 403 turns the status off and keeps the buffer`() = runTest(dispatcher) {
        val diagnostics = FakeDiagnostics(
            entries = entries("e0"),
            result = ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_DISABLED),
        )
        val viewModel = viewModel(diagnostics = diagnostics)
        viewModel.refreshDiagnostics()
        advanceUntilIdle()

        viewModel.reportProblem()
        advanceUntilIdle()

        assertEquals(
            ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_DISABLED),
            viewModel.state.value.diagnostics.reportResult,
        )
        assertEquals(false, viewModel.state.value.diagnostics.enabled)
        assertEquals(1, diagnostics.entries.size)
    }

    @Test
    fun `a 429 keeps the buffer and the instance status`() = runTest(dispatcher) {
        val diagnostics = FakeDiagnostics(
            entries = entries("e0"),
            result = ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_LIMITED),
        )
        val viewModel = viewModel(diagnostics = diagnostics)
        viewModel.refreshDiagnostics()
        advanceUntilIdle()

        viewModel.reportProblem()
        advanceUntilIdle()

        assertEquals(
            ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_LIMITED),
            viewModel.state.value.diagnostics.reportResult,
        )
        assertEquals(true, viewModel.state.value.diagnostics.enabled)
        assertEquals(1, diagnostics.entries.size)
    }

    @Test
    fun `no answer keeps the buffer and reports the network code`() = runTest(dispatcher) {
        val diagnostics = FakeDiagnostics(
            entries = entries("e0"),
            result = ReportProblemResult.Failed(ErrorCodes.NO_ANSWER),
        )
        val viewModel = viewModel(diagnostics = diagnostics)
        viewModel.refreshDiagnostics()
        advanceUntilIdle()

        viewModel.reportProblem()
        advanceUntilIdle()

        assertEquals(ReportProblemResult.Failed(ErrorCodes.NO_ANSWER), viewModel.state.value.diagnostics.reportResult)
        assertEquals(1, diagnostics.entries.size)
    }

    @Test
    fun `an empty buffer reports no entries instead of sharing`() = runTest(dispatcher) {
        val viewModel = viewModel(diagnostics = FakeDiagnostics())
        advanceUntilIdle()
        var shared: String? = null

        viewModel.shareLogs { shared = it }
        advanceUntilIdle()

        assertNull(shared)
        assertTrue(viewModel.state.value.diagnostics.shareEmpty)
    }

    @Test
    fun `share logs hands the NDJSON export to the share sheet`() = runTest(dispatcher) {
        val viewModel = viewModel(diagnostics = FakeDiagnostics(entries = entries("e0")))
        advanceUntilIdle()
        var shared: String? = null

        viewModel.shareLogs { shared = it }
        advanceUntilIdle()

        assertTrue(shared!!.contains("\"e0\""))
        assertFalse(viewModel.state.value.diagnostics.shareEmpty)
    }

    @Test
    fun `choosing a polling interval applies it and updates the row`() = runTest(dispatcher) {
        val applied = mutableListOf<Int>()
        val viewModel = viewModel(applyPollingInterval = { applied += it })
        advanceUntilIdle()
        assertEquals(15, viewModel.state.value.pollingIntervalMinutes)

        viewModel.setPollingInterval(30)
        advanceUntilIdle()

        assertEquals(listOf(30), applied)
        assertEquals(30, viewModel.state.value.pollingIntervalMinutes)
    }

    private fun entries(vararg codes: String): MutableList<DiagnosticEntry> = codes.map { code ->
        DiagnosticEntry(
            occurredAt = "2026-11-20T12:00:00Z",
            level = DiagnosticLevel.ERROR,
            code = code,
            message = "message $code",
        )
    }.toMutableList()

    private fun viewModel(
        store: DiveStore = DiveStore.create(driver),
        revision: MutableStateFlow<Long> = MutableStateFlow(0L),
        connected: MutableStateFlow<Boolean> = MutableStateFlow(false),
        offline: MutableStateFlow<Boolean> = MutableStateFlow(false),
        refreshSync: suspend () -> Unit = {},
        diagnostics: DiagnosticsAccess = FakeDiagnostics(),
        pushStatus: MutableStateFlow<PushStatus> = MutableStateFlow(PushStatus()),
        queueRevision: MutableStateFlow<Long> = MutableStateFlow(0L),
        readSnapshot: () -> WriteQueueSnapshot = { WriteQueueSnapshot() },
        applyPollingInterval: suspend (Int) -> Unit = {},
    ) = SettingsViewModel(
        settings = SettingsRepository(FakeInstances(), store),
        revision = revision,
        connectedState = connected,
        offlineState = offline,
        refreshSync = refreshSync,
        diagnostics = diagnostics,
        pushStatus = pushStatus,
        queueRevision = queueRevision,
        readSnapshot = readSnapshot,
        applyPollingInterval = applyPollingInterval,
        io = dispatcher,
    )
}
