package dev.divehub.android.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dev.divehub.android.DiveHubApplication

/**
 * The background half of a push (ADR 0085). The messaging service has only a moment before the OS may stop it, so it
 * records the hint and this worker fetches through the authenticated API. A notification is posted only when that fetch
 * actually found the hinted Dive new or changed, which is the hint-only spoofing defence: a forged payload can cause a
 * fetch, never a notification about something that did not happen.
 */
internal class PushWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val app = applicationContext as? DiveHubApplication ?: return Result.success()
        return when (inputData.getString(KEY_ACTION)) {
            ACTION_HINT -> {
                val id = inputData.getString(KEY_DIVE_ID) ?: return Result.success()
                val fresh = app.container.refreshForPushHint(id)
                if (fresh) app.container.notifications.showDive(id)
                Result.success()
            }
            ACTION_REGISTER -> {
                app.container.push.registerNow(inputData.getString(KEY_TOKEN))
                Result.success()
            }
            else -> Result.success()
        }
    }

    companion object {
        private const val KEY_ACTION = "action"
        private const val KEY_DIVE_ID = "dive_id"
        private const val KEY_TOKEN = "token"
        private const val ACTION_HINT = "hint"
        private const val ACTION_REGISTER = "register"
        private const val REGISTER_WORK = "divehub-push-register"

        fun enqueueHint(context: Context, diveId: String) {
            val request = OneTimeWorkRequestBuilder<PushWorker>()
                .setInputData(Data.Builder().putString(KEY_ACTION, ACTION_HINT).putString(KEY_DIVE_ID, diveId).build())
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            WorkManager.getInstance(context.applicationContext)
                .enqueueUniqueWork("divehub-push-hint-$diveId", ExistingWorkPolicy.REPLACE, request)
        }

        fun enqueueRegister(context: Context, token: String?) {
            val input = Data.Builder().putString(KEY_ACTION, ACTION_REGISTER)
            token?.let { input.putString(KEY_TOKEN, it) }
            val request = OneTimeWorkRequestBuilder<PushWorker>()
                .setInputData(input.build())
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            WorkManager.getInstance(context.applicationContext)
                .enqueueUniqueWork(REGISTER_WORK, ExistingWorkPolicy.REPLACE, request)
        }
    }
}
