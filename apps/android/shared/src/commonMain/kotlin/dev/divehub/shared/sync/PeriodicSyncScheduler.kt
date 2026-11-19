package dev.divehub.shared.sync

/**
 * The OS-scheduled periodic refresh (WorkManager in `app/`, behind this interface so `shared/` stays Android-free).
 * It re-runs phase 1 on the platform's own cadence when the process is not in the foreground.
 */
interface PeriodicSyncScheduler {
    fun schedule()
    fun cancel()
}
