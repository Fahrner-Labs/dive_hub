package dev.divehub.android.ui.settings

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.settings.SettingsRepository
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncState
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** The settings state holder: one guarded refresh, and the sync line following the store's revision and offline flag. */
@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var driver: JdbcSqliteDriver

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
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
    fun `the sync line follows the store revision and the offline flag`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val revision = MutableStateFlow(0L)
        val offline = MutableStateFlow(false)
        val viewModel = viewModel(store = store, revision = revision, offline = offline)
        advanceUntilIdle()
        assertEquals(null, viewModel.state.value.syncedAt)

        store.saveSyncState(SyncState(lastFullSyncAt = "2026-11-18T12:00:00Z", schemaVersion = DiveStore.schemaVersion))
        revision.value = 1
        advanceUntilIdle()
        assertEquals("2026-11-18T12:00:00Z", viewModel.state.value.syncedAt)

        offline.value = true
        advanceUntilIdle()
        assertTrue(viewModel.state.value.offline)
    }

    private fun viewModel(
        store: DiveStore = DiveStore.create(driver),
        revision: MutableStateFlow<Long> = MutableStateFlow(0L),
        offline: MutableStateFlow<Boolean> = MutableStateFlow(false),
        refreshSync: suspend () -> Unit = {},
    ) = SettingsViewModel(
        settings = SettingsRepository(FakeInstances(), store),
        revision = revision,
        offlineState = offline,
        refreshSync = refreshSync,
        io = dispatcher,
    )
}

private class FakeInstances : InstanceUrlStore {
    private var value: String? = "https://dive.example"

    override suspend fun load(): String? = value

    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}
