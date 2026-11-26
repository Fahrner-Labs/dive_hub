package dev.divehub.android.notifications

import android.content.Context
import android.content.pm.PackageManager
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Firebase is configured at runtime from the instance's public config, never from a bundled `google-services.json`
 * (ADR 0085), so one binary works with every self-hoster's project. FCM's only public messaging accessor is the default
 * app, so the runtime app is the default one; the stored instance URL is what keys it, and a change deletes and rebuilds
 * it rather than mixing two projects.
 */
internal class FirebaseRuntime(
    private val context: Context,
    private val preferences: PushPreferences,
) {
    /** The initialized app for the stored instance, or null when push is off or Play services is missing. */
    fun initialize(): FirebaseApp? {
        if (!hasPlayServices(context)) return null
        val instanceUrl = preferences.instanceUrl ?: return null
        val config = preferences.config() ?: return null
        val options = FirebaseOptions.Builder()
            .setProjectId(config.projectId)
            .setApplicationId(config.applicationId)
            .setApiKey(config.apiKey)
            .setGcmSenderId(config.gcmSenderId)
            .build()
        val existing = FirebaseApp.getApps(context).firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }
        if (existing != null) {
            val current = existing.options
            val same = current.applicationId == options.applicationId &&
                current.projectId == options.projectId &&
                current.gcmSenderId == options.gcmSenderId
            if (same) return existing
            // Another instance's project: delete first, or initializeApp would keep the stale default app.
            runCatching { existing.delete() }
        }
        // The stored instance URL keys the config; a change was cleared and reset before this runs.
        return runCatching { FirebaseApp.initializeApp(context, options) }.getOrNull()
    }

    /** The current FCM token through the stored instance's app; null when registration cannot work here. */
    suspend fun token(): String? {
        if (initialize() == null) return null
        return suspendCancellableCoroutine { continuation ->
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (continuation.isActive) continuation.resume(task.resultOrNull())
                }
        }
    }

    /** Forget the runtime-initialized app: another instance or a push-off server must not keep the old project. */
    fun reset() {
        FirebaseApp.getApps(context)
            .filter { it.name == FirebaseApp.DEFAULT_APP_NAME || it.name.startsWith(LEGACY_NAME_PREFIX) }
            .forEach { runCatching { it.delete() } }
    }

    private companion object {
        /** Named apps from the first cut of this code; kept so an upgraded install drops them too. */
        const val LEGACY_NAME_PREFIX = "divehub-push-"
    }
}

/** Play services is a hard requirement for FCM; without it the app keeps polling and stays fully functional. */
internal fun hasPlayServices(context: Context): Boolean = try {
    context.packageManager.getPackageInfo(PLAY_SERVICES_PACKAGE, 0)
    true
} catch (_: PackageManager.NameNotFoundException) {
    false
}

/** A Task's value, or null when it failed: reading `result` on a failed Task throws. */
internal fun <T> Task<T>.resultOrNull(): T? = if (isSuccessful) result else null

private const val PLAY_SERVICES_PACKAGE = "com.google.android.gms"
