package dev.divehub.android.notifications

import android.content.Context
import android.content.pm.PackageManager
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine

/** Why runtime Firebase is (not) ready; the push path records it in diagnostics and shows it in Settings → Debug. */
internal sealed interface FirebaseStart {
    data object Ready : FirebaseStart
    data object NoPlayServices : FirebaseStart
    data object NoConfig : FirebaseStart
    data class Failed(val errorClass: String, val message: String?) : FirebaseStart
}

/** The FCM token result: the value is kept for registration, never logged or shown. */
internal sealed interface TokenResult {
    data class Acquired(val token: String) : TokenResult
    data object NotReady : TokenResult
    data class Failed(val errorClass: String, val message: String?) : TokenResult
}

/** What [PushController] needs from Firebase; an interface keeps the push lifecycle testable without FCM. */
internal interface PushFirebase {
    fun initialize(): FirebaseStart
    suspend fun token(): TokenResult
    fun reset()
}

/**
 * Firebase is configured at runtime from the instance's public config, never from a bundled `google-services.json`
 * (ADR 0085), so one binary works with every self-hoster's project. FCM's only public messaging accessor is the default
 * app, so the runtime app is the default one; the stored instance URL is what keys it, and a change deletes and rebuilds
 * it rather than mixing two projects.
 */
internal class FirebaseRuntime(
    private val context: Context,
    private val preferences: PushPreferences,
) : PushFirebase {
    /** The initialized app for the stored instance, or why push cannot work here (ADR 0085). */
    override fun initialize(): FirebaseStart {
        if (!hasPlayServices(context)) return FirebaseStart.NoPlayServices
        val instanceUrl = preferences.instanceUrl ?: return FirebaseStart.NoConfig
        val config = preferences.config() ?: return FirebaseStart.NoConfig
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
            if (same) return FirebaseStart.Ready
            // Another instance's project: delete first, or initializeApp would keep the stale default app.
            runCatching { existing.delete() }
        }
        // The stored instance URL keys the config; a change was cleared and reset before this runs.
        return try {
            FirebaseApp.initializeApp(context, options)
            FirebaseStart.Ready
        } catch (error: Exception) {
            FirebaseStart.Failed(error.javaClass.simpleName, error.message)
        }
    }

    /** The current FCM token through the stored instance's app, or why it could not be obtained. */
    override suspend fun token(): TokenResult {
        if (initialize() != FirebaseStart.Ready) return TokenResult.NotReady
        return try {
            suspendCancellableCoroutine { continuation ->
                FirebaseMessaging.getInstance().token
                    .addOnCompleteListener { task ->
                        if (continuation.isActive) continuation.resume(task.asTokenResult())
                    }
            }
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            TokenResult.Failed(error.javaClass.simpleName, error.message)
        }
    }

    /** Forget the runtime-initialized app: another instance or a push-off server must not keep the old project. */
    override fun reset() {
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

/** A Task's FCM token or its failure; reading `result` on a failed Task throws, so it is never the token on failure. */
internal fun Task<String>.asTokenResult(): TokenResult = if (isSuccessful) {
    val value = result
    if (value.isNullOrBlank()) {
        TokenResult.Failed("FirebaseMessaging", "empty token")
    } else {
        TokenResult.Acquired(value)
    }
} else {
    val error = exception
    TokenResult.Failed(error?.javaClass?.simpleName ?: "FirebaseMessaging", error?.message)
}

private const val PLAY_SERVICES_PACKAGE = "com.google.android.gms"
