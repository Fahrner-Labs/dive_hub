package dev.divehub.android.notifications

import android.content.Context
import android.os.Build
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.push.PushRegistration
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * The app's push lifecycle (ADR 0085): fetch the instance's public config after the URL is known, initialize Firebase
 * at runtime, register the FCM token for the signed-in session, and remove it on sign-out. Everything is best effort
 * and optional: no Play services, no push config or a denied notification permission leaves polling and the foreground
 * SSE stream untouched.
 */
internal class PushController(
    private val context: Context,
    private val session: SessionRepository,
    private val registration: PushRegistration,
    private val firebase: FirebaseRuntime,
    private val preferences: PushPreferences,
    private val notifications: DiveNotifications,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
) {
    private val mutex = Mutex()
    private val _permissionRequest = MutableStateFlow(false)

    /** The shell asks for POST_NOTIFICATIONS when this turns true; it does so after the first successful sync. */
    val permissionRequest: StateFlow<Boolean> = _permissionRequest.asStateFlow()

    /** Initialize Firebase from the stored config at process start, so the FCM service works after a push woke us. */
    fun initialize() {
        firebase.initialize()
    }

    /** The instance URL is known (first start or a restored session): fetch its config and prepare Firebase. */
    fun onInstanceKnown(instanceUrl: String) {
        scope.launch { configure(instanceUrl) }
    }

    /** The suspend form, so a starting sync can finish configuring before it asks for a token. */
    suspend fun configure(instanceUrl: String) {
        mutex.withLock {
            if (preferences.instanceUrl != null && preferences.instanceUrl != instanceUrl) {
                // Another server: its config and row must not survive on this device.
                preferences.clearConfig()
                preferences.clearRegistration()
                firebase.reset()
            }
            session.client.baseUrl = instanceUrl
            val config = try {
                registration.androidConfig()
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                // A transient failure keeps the previous config if it belongs to this same instance; polling covers the gap.
                return@withLock
            }
            if (config == null) {
                preferences.clearConfig()
                firebase.reset()
                return@withLock
            }
            preferences.saveConfig(instanceUrl, config)
            firebase.initialize()
        }
        registerIfPossible()
    }

    /** Called after the first sync of a session: ask for the permission once, or register if it is already allowed. */
    fun onInitialSyncFinished() {
        if (preferences.config() == null) return
        if (!notificationsAllowed(context)) {
            // A row registered before the User switched notifications off must stop receiving hints.
            if (preferences.deviceId != null) unregisterInBackground()
            // Only Android 13+ has the runtime grant to ask for; below it the system setting is the User's own.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !preferences.permissionAsked) {
                _permissionRequest.value = true
            }
            return
        }
        if (preferences.deviceId != null) return
        registerIfPossible()
    }

    private fun unregisterInBackground() {
        scope.launch { unregister() }
    }

    fun onPermissionResult(granted: Boolean) {
        preferences.markPermissionAsked()
        _permissionRequest.value = false
        if (granted) registerIfPossible()
    }

    /** A refreshed FCM token arrives through the messaging service: keep it and move the row to it. */
    fun onNewToken(token: String) {
        preferences.saveToken(token)
        registerIfPossible(token)
    }

    /** Register the current token for the signed-in session; safe to call repeatedly, the server upserts by token. */
    fun registerIfPossible(token: String? = null) {
        scope.launch { registerNow(token) }
    }

    /** The worker's suspend form; the same checks as [registerIfPossible] without the background scope. */
    suspend fun registerNow(token: String? = null) {
        mutex.withLock {
            if (preferences.config() == null) return@withLock
            if (!notificationsAllowed(context)) return@withLock
            val instanceUrl = session.knownInstance() ?: return@withLock
            if (!session.hasSession()) return@withLock
            session.client.baseUrl = instanceUrl
            val pushToken = token ?: preferences.lastToken ?: firebase.token() ?: return@withLock
            preferences.saveToken(pushToken)
            val id = try {
                registration.register(pushToken)
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                // Offline or refused: the next start, hint or token refresh tries again.
                return@withLock
            }
            preferences.saveDeviceId(id)
        }
    }

    /** Sign-out: remove this session's row while its token still works, then forget it locally. */
    suspend fun unregister() {
        mutex.withLock {
            val id = preferences.deviceId ?: return@withLock
            try {
                val instanceUrl = session.knownInstance()
                if (instanceUrl != null && session.hasSession()) {
                    session.client.baseUrl = instanceUrl
                    registration.unregister(id)
                }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                // The server's session/fk cascade removes it anyway; never block signing out.
            } finally {
                preferences.clearDeviceId()
            }
        }
    }

    /** The session ended here; a later sign-in registers again. */
    fun onSignedOut() {
        _permissionRequest.value = false
        notifications.cancelAll()
    }
}
