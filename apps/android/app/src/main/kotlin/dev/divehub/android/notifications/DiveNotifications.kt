package dev.divehub.android.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dev.divehub.android.MainActivity
import dev.divehub.android.R

/**
 * The app's local dive notifications (ADR 0085). A push only names a Dive; the worker fetches it first, and this
 * presenter draws the result. A burst of several new dives becomes one summary, so a first sync does not stack twenty
 * notifications. The channel's importance is fixed at creation: changing behaviour means a new channel id, never an
 * edit to this one.
 */
internal class DiveNotifications(private val context: Context) {
    private val preferences = context.getSharedPreferences("divehub.push.burst", Context.MODE_PRIVATE)

    /** Idempotent; called from Application.onCreate so the channel exists even before the first hint. */
    fun createChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        val channel = NotificationChannel(
            CHANNEL_DIVES,
            context.getString(R.string.notification_channel_dives_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = context.getString(R.string.notification_channel_dives_description)
        }
        manager.createNotificationChannel(channel)
    }

    /** Show one new Dive, or fold it into the current burst's summary. The tap opens the dive, or the logbook. */
    fun showDive(id: String) {
        if (!notificationsAllowed(context)) return
        val now = System.currentTimeMillis()
        val previous = preferences.getLong(KEY_BURST_AT, 0L)
        val ids = if (now - previous > BURST_WINDOW_MILLIS) {
            linkedSetOf()
        } else {
            preferences.getString(KEY_BURST_IDS, null)
                ?.split('\n')
                ?.filter { it.isNotBlank() }
                ?.toCollection(linkedSetOf())
                ?: linkedSetOf()
        }
        ids += id
        preferences.edit()
            .putString(KEY_BURST_IDS, ids.joinToString("\n"))
            .putLong(KEY_BURST_AT, now)
            .apply()

        val count = ids.size
        val single = count == 1
        val title = if (single) {
            context.getString(R.string.notification_new_dive_title)
        } else {
            context.resources.getQuantityString(R.plurals.notification_new_dives, count, count)
        }
        val text = context.getString(
            if (single) R.string.notification_new_dive_body else R.string.notification_new_dives_body,
        )
        val intent = if (single) diveIntent(id) else appIntent()
        val notification = NotificationCompat.Builder(context, CHANNEL_DIVES)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()
        try {
            NotificationManagerCompat.from(context).notify(DIVE_NOTIFICATION_ID, notification)
        } catch (_: SecurityException) {
            // The permission was revoked between the check and the post; polling still keeps the app current.
        }
    }

    /** A new burst starts fresh when the app comes to the foreground; the previous summary stays until tapped. */
    fun clearBurst() {
        preferences.edit().clear().apply()
    }

    /** Sign-out removes the Dive notices with the session, so the next person sees nothing of the previous one. */
    fun cancelAll() {
        clearBurst()
        try {
            NotificationManagerCompat.from(context).cancel(DIVE_NOTIFICATION_ID)
        } catch (_: SecurityException) {
            // Nothing was shown without the permission; nothing to cancel.
        }
    }

    private fun diveIntent(id: String): PendingIntent = PendingIntent.getActivity(
        context,
        id.hashCode(),
        Intent(context, MainActivity::class.java)
            .putExtra(EXTRA_PUSH_DIVE_ID, id)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    private fun appIntent(): PendingIntent = PendingIntent.getActivity(
        context,
        0,
        Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    private companion object {
        const val CHANNEL_DIVES = "dives"
        const val DIVE_NOTIFICATION_ID = 1001
        const val KEY_BURST_IDS = "burst_ids"
        const val KEY_BURST_AT = "burst_at"
        const val BURST_WINDOW_MILLIS = 30_000L
    }
}

/** The Dive id a notification tap carries; MainActivity turns it into the Dive route (ADR 0085). */
internal const val EXTRA_PUSH_DIVE_ID = "dev.divehub.android.push.DIVE_ID"

/** Whether this install may post a notification; Android 13+ needs the runtime grant. */
internal fun notificationsAllowed(context: Context): Boolean =
    NotificationManagerCompat.from(context).areNotificationsEnabled() &&
        (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED)
