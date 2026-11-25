package dev.divehub.shared.settings

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.PollingIntervalStore
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncState
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/** What the settings screen reads: the stored instance address and the store's sync state, nothing else. */
class SettingsRepositoryTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)

    @AfterTest
    fun close() = driver.close()

    @Test
    fun `the instance address comes from the instance store`() = runTest {
        val repository = SettingsRepository(FakeInstances("https://dive.example"), DiveStore.create(driver))

        assertEquals("https://dive.example", repository.instanceUrl())
    }

    @Test
    fun `an unset instance address reads as null`() = runTest {
        val repository = SettingsRepository(FakeInstances(null), DiveStore.create(driver))

        assertNull(repository.instanceUrl())
    }

    @Test
    fun `the polling interval defaults to the WorkManager floor`() = runTest {
        val repository = SettingsRepository(FakeInstances("https://dive.example"), DiveStore.create(driver))

        assertEquals(15, repository.pollingIntervalMinutes())
    }

    @Test
    fun `the polling interval is stored and clamped to the floor`() = runTest {
        val polling = FakePolling()
        val repository = SettingsRepository(FakeInstances("https://dive.example"), DiveStore.create(driver), polling)

        repository.setPollingIntervalMinutes(30)
        assertEquals(30, repository.pollingIntervalMinutes())

        repository.setPollingIntervalMinutes(1)
        assertEquals(15, polling.value)
    }

    @Test
    fun `the sync state is the store's last full sync`() {
        val store = DiveStore.create(driver)
        store.saveSyncState(SyncState(lastFullSyncAt = "2026-11-18T12:00:00Z", schemaVersion = DiveStore.schemaVersion))
        val repository = SettingsRepository(FakeInstances("https://dive.example"), store)

        assertEquals("2026-11-18T12:00:00Z", repository.syncState().lastFullSyncAt)
    }
}

private class FakeInstances(initial: String? = null) : InstanceUrlStore {
    private var value: String? = initial

    override suspend fun load(): String? = value

    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}

private class FakePolling(var value: Int? = null) : PollingIntervalStore {
    override suspend fun load(): Int = value ?: 15

    override suspend fun save(minutes: Int) {
        value = minutes
    }
}
