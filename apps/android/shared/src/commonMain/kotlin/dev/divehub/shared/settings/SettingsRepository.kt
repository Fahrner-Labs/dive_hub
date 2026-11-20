package dev.divehub.shared.settings

import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.SyncState

/**
 * What the settings screen reads outside Compose: the instance address the app was set up with and the sync state the
 * store records. The screen binds these values; it never reads preferences or the database itself (ADR 0073).
 */
class SettingsRepository(
    private val instances: InstanceUrlStore,
    private val store: DiveStore,
) {
    /** The current instance address; null while the app asks for one, as on a first start. */
    suspend fun instanceUrl(): String? = instances.load()

    /** The same sync state the logbook's "Last synced …" line reads (FAH-296). */
    fun syncState(): SyncState = store.syncState()
}
