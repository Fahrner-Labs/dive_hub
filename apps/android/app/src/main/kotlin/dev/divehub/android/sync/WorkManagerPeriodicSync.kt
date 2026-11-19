package dev.divehub.android.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dev.divehub.android.DiveHubApplication
import dev.divehub.shared.sync.PeriodicSyncScheduler
import java.util.concurrent.TimeUnit

/**
 * The OS-scheduled periodic refresh (the plan's polling fallback). WorkManager stays in `app/`; `shared/` only sees
 * [PeriodicSyncScheduler]. It re-runs phase 1 through the same engine the foreground uses.
 */
class WorkManagerPeriodicSync(context: Context) : PeriodicSyncScheduler {
    private val workManager = WorkManager.getInstance(context.applicationContext)

    override fun schedule() {
        val request = PeriodicWorkRequestBuilder<SyncWorker>(REFRESH_INTERVAL_MINUTES, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
        workManager.enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, request)
    }

    override fun cancel() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    private companion object {
        const val WORK_NAME = "divehub-sync"
        const val REFRESH_INTERVAL_MINUTES = 15L
    }
}

/** One periodic run: phase 1 and the tombstones, but only when a session is stored on this device. */
class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val app = applicationContext as? DiveHubApplication ?: return Result.success()
        app.container.refreshNow()
        return Result.success()
    }
}
