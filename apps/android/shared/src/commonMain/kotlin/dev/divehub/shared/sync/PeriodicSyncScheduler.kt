package dev.divehub.shared.sync

/**
 * The OS-scheduled periodic refresh (WorkManager in `app/`, behind this interface so `shared/` stays Android-free).
 * It re-runs phase 1 on the platform's own cadence when the process is not in the foreground. The interval is the
 * client setting for FCM-less instances; Android's WorkManager rejects anything below [MIN_INTERVAL_MINUTES].
 */
interface PeriodicSyncScheduler {
    /** Schedule (or keep) the periodic run at [intervalMinutes]; a repeat while it runs leaves the existing work. */
    fun schedule(intervalMinutes: Long = MIN_INTERVAL_MINUTES)

    /** Apply a changed setting to existing work, so the next run already uses it. */
    fun reschedule(intervalMinutes: Long) = schedule(intervalMinutes)

    fun cancel()

    companion object {
        /** WorkManager's floor for periodic work. */
        const val MIN_INTERVAL_MINUTES: Long = 15L
    }
}
