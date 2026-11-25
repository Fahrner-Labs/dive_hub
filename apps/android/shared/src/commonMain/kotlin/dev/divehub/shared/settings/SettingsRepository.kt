package dev.divehub.shared.settings

import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.PollingIntervalStore
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncState
import dev.divehub.shared.sync.PeriodicSyncScheduler

/**
 * What the settings screen reads outside Compose: the instance address the app was set up with, the sync state the
 * store records, and the background polling interval. The screen binds these values; it never reads preferences or the
 * database itself (ADR 0073).
 */
class SettingsRepository(
    private val instances: InstanceUrlStore,
    private val store: DiveStore,
    private val polling: PollingIntervalStore? = null,
) {
    /** The current instance address; null while the app asks for one, as on a first start. */
    suspend fun instanceUrl(): String? = instances.load()

    /** The same sync state the logbook's "Last synced …" line reads (FAH-296). */
    fun syncState(): SyncState = store.syncState()

    /** The background refresh cadence in minutes, clamped to what WorkManager accepts. */
    suspend fun pollingIntervalMinutes(): Int =
        (polling?.load() ?: DEFAULT_POLLING_INTERVAL_MINUTES).coerceIn(
            PeriodicSyncScheduler.MIN_INTERVAL_MINUTES.toInt(),
            MAX_POLLING_INTERVAL_MINUTES,
        )

    suspend fun setPollingIntervalMinutes(minutes: Int) {
        polling?.save(
            minutes.coerceIn(PeriodicSyncScheduler.MIN_INTERVAL_MINUTES.toInt(), MAX_POLLING_INTERVAL_MINUTES),
        )
    }

    companion object {
        const val DEFAULT_POLLING_INTERVAL_MINUTES: Int = PeriodicSyncScheduler.MIN_INTERVAL_MINUTES.toInt()
        const val MAX_POLLING_INTERVAL_MINUTES: Int = 24 * 60
    }
}
