package dev.divehub.android.notifications

import android.content.Context
import android.os.Build
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.diagnostics.DiagnosticCapture
import dev.divehub.shared.diagnostics.DiagnosticLevel
import dev.divehub.shared.push.PushRegistration
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * The app's push lifecycle (ADR 0085): fetch the instance's public config after the URL is known, initialize Firebase
 * at runtime, register the FCM token for the signed-in session, and remove it on sign-out. Everything is best effort
 * and optional: no Play services, no push config or a denied notification permission leaves polling and the foreground
 * SSE stream untouched.
 *
 * Every step also records a status and a diagnostics entry (FAH-557), so a broken registration is visible in Settings →
 * Debug and in the local NDJSON export instead of only in adb logcat. No token value ever reaches either.
 */
internal class PushController(
    private val context: Context,
    private val session: SessionRepository,
    private val registration: PushRegistration,
    private val firebase: PushFirebase,
    private val preferences: PushPreferences,
    private val notifications: DiveNotifications,
    private val diagnostics: DiagnosticCapture,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
) {
    private val mutex = Mutex()
    private val _permissionRequest = MutableStateFlow(false)
    private val _status = MutableStateFlow(
        preferences.deviceId?.let { id ->
            PushStatus(PushState.Registered, deviceId = id, lastSeenAt = preferences.lastSeenAt)
        } ?: PushStatus(),
    )

    /** The shell asks for POST_NOTIFICATIONS when this turns true; it does so after the first successful sync. */
    val permissionRequest: StateFlow<Boolean> = _permissionRequest.asStateFlow()

    /** What Settings → Debug shows: registered device, or the reason it is not registered (FAH-557). */
    val status: StateFlow<PushStatus> = _status.asStateFlow()

    /** Initialize Firebase from the stored config at process start, so the FCM service works after a push woke us. */
    fun initialize() {
        // Without a stored config there is nothing to initialize; do not turn a push-off instance into a warning.
        if (preferences.config() == null) return
        logFirebase(firebase.initialize())
    }

    /** The instance URL is known (first start or a restored session): fetch its config and prepare Firebase. */
    fun onInstanceKnown(instanceUrl: String) {
        scope.launch { configure(instanceUrl) }
    }

    /** The suspend form, so a starting sync can finish configuring before it asks for a token. */
    suspend fun configure(instanceUrl: String) {
        val shouldRegister = mutex.withLock {
            if (preferences.instanceUrl != null && preferences.instanceUrl != instanceUrl) {
                // Another server: its config and row must not survive on this device.
                preferences.clearConfig()
                preferences.clearRegistration()
                firebase.reset()
                _status.value = PushStatus()
            }
            session.client.baseUrl = instanceUrl
            val config = try {
                registration.androidConfig()
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                recordFailure(CONFIG_FAILED, "Push config request failed", error)
                _status.update { it.copy(state = PushState.ConfigFailed, reason = reasonOf(error)) }
                // A transient failure keeps the previous config if it belongs to this same instance; polling covers the gap.
                return@withLock preferences.config() != null
            }
            if (config == null) {
                preferences.clearConfig()
                firebase.reset()
                diagnostics.record(DiagnosticLevel.WARN, CONFIG_OFF, "This Dive Hub has push off")
                _status.value = PushStatus(PushState.Off)
                return@withLock false
            }
            preferences.saveConfig(instanceUrl, config)
            diagnostics.record(
                DiagnosticLevel.WARN,
                CONFIG_OK,
                "Push config fetched",
                mapOf("status" to "200"),
            )
            logFirebase(firebase.initialize())
            true
        }
        if (shouldRegister) registerIfPossible()
    }

    /** Called after the first sync of a session: ask for the permission once, or register if it is already allowed. */
    fun onInitialSyncFinished() {
        if (preferences.config() == null) return
        if (!notificationsAllowed(context)) {
            // A row registered before the User switched notifications off must stop receiving hints.
            if (preferences.deviceId != null) unregisterInBackground()
            _status.value = PushStatus(PushState.PermissionMissing)
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
        if (granted) {
            registerIfPossible()
        } else {
            _status.value = PushStatus(PushState.PermissionMissing)
        }
    }

    /** A refreshed FCM token arrives through the messaging service: keep it and move the row to it. */
    fun onNewToken(token: String) {
        // The token itself is never logged: only that a refresh happened.
        diagnostics.record(DiagnosticLevel.WARN, TOKEN_REFRESH, "FCM token refreshed")
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
            if (preferences.config() == null) {
                _status.value = PushStatus(PushState.Off)
                return@withLock
            }
            if (!notificationsAllowed(context)) {
                _status.value = PushStatus(PushState.PermissionMissing)
                return@withLock
            }
            val instanceUrl = session.knownInstance()
            if (instanceUrl == null || !session.hasSession()) {
                _status.value = PushStatus(PushState.NotSignedIn)
                return@withLock
            }
            session.client.baseUrl = instanceUrl
            val pushToken = token ?: preferences.lastToken ?: when (val result = firebase.token()) {
                is TokenResult.Acquired -> {
                    diagnostics.record(DiagnosticLevel.WARN, TOKEN_OK, "FCM token acquired")
                    result.token
                }
                TokenResult.NotReady -> {
                    // configure() already named no Play services or a failed init; only fill in a bare status.
                    _status.update { current ->
                        if (current.state == PushState.Unknown) {
                            PushStatus(PushState.InitFailed, reason = FIREBASE_NOT_READY)
                        } else {
                            current
                        }
                    }
                    return@withLock
                }
                is TokenResult.Failed -> {
                    recordTokenFailure(result)
                    _status.value = PushStatus(PushState.TokenFailed, reason = result.errorClass)
                    return@withLock
                }
            }
            preferences.saveToken(pushToken)
            val device = try {
                registration.register(pushToken)
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                recordFailure(REGISTER_FAILED, "Push registration failed", error)
                _status.update { it.copy(state = PushState.RegisterFailed, reason = reasonOf(error)) }
                // Offline or refused: the next start, hint or token refresh tries again.
                return@withLock
            }
            preferences.saveDevice(device.id, device.lastSeenAt)
            diagnostics.record(
                DiagnosticLevel.WARN,
                REGISTER_OK,
                "Push device registered",
                mapOf("device_id" to device.id, "last_seen_at" to device.lastSeenAt),
            )
            _status.value = PushStatus(PushState.Registered, deviceId = device.id, lastSeenAt = device.lastSeenAt)
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
            } catch (error: Exception) {
                // The server's session/fk cascade removes it anyway; never block signing out.
                recordFailure(UNREGISTER_FAILED, "Push unregister failed", error)
            } finally {
                preferences.clearDeviceId()
            }
        }
    }

    /** The session ended here; a later sign-in registers again. */
    fun onSignedOut() {
        _permissionRequest.value = false
        _status.value = PushStatus()
        notifications.cancelAll()
    }

    /** Record what runtime Firebase said; a missing config at process start is not yet a failure worth an entry. */
    private fun logFirebase(start: FirebaseStart) {
        when (start) {
            FirebaseStart.Ready -> {
                diagnostics.record(DiagnosticLevel.WARN, FIREBASE_OK, "Firebase initialized")
                if (_status.value.deviceId == null && _status.value.state != PushState.Off) {
                    _status.update { it.copy(state = PushState.Unknown, reason = null) }
                }
            }
            FirebaseStart.NoPlayServices -> {
                diagnostics.record(DiagnosticLevel.WARN, NO_PLAY_SERVICES, "Google Play services is missing")
                _status.value = PushStatus(PushState.NoPlayServices)
            }
            FirebaseStart.NoConfig -> Unit
            is FirebaseStart.Failed -> {
                diagnostics.record(
                    DiagnosticLevel.WARN,
                    FIREBASE_FAILED,
                    "Firebase initialization failed: ${scrub(describe(start.errorClass, start.message))}",
                    mapOf("error" to start.errorClass),
                )
                _status.value = PushStatus(PushState.InitFailed, reason = start.errorClass)
            }
        }
    }

    private fun recordTokenFailure(result: TokenResult.Failed) {
        diagnostics.record(
            DiagnosticLevel.WARN,
            TOKEN_FAILED,
            "FCM token request failed: ${scrub(describe(result.errorClass, result.message))}",
            mapOf("error" to result.errorClass),
        )
    }

    private fun recordFailure(code: String, action: String, error: Exception) {
        diagnostics.record(
            DiagnosticLevel.WARN,
            code,
            "$action: ${scrub(describe(error))}",
            buildMap {
                put("error", error.javaClass.simpleName)
                if (error is DiveHubException) {
                    error.status?.let { put("status", it.toString()) }
                }
            },
        )
    }

    /** A token-shaped run in an error text is evidence of a leak, not information: mask it before the buffer sees it. */
    private fun scrub(text: String): String = LONG_VALUE.replace(text, REDACTED)

    /** A code or class name for the status row; never a server message and never a token. */
    private fun reasonOf(error: Exception): String = when (error) {
        is DiveHubException -> error.code
        is NoAnswerException -> ErrorCodes.NO_ANSWER
        else -> error.javaClass.simpleName
    }

    private fun describe(error: Exception): String = when (error) {
        is DiveHubException -> buildString {
            append(error.code)
            error.status?.let { append(" (HTTP ").append(it).append(')') }
        }
        is NoAnswerException -> ErrorCodes.NO_ANSWER
        else -> describe(error.javaClass.simpleName, error.message)
    }

    private fun describe(errorClass: String, message: String?): String =
        if (message.isNullOrBlank()) errorClass else "$errorClass: $message"

    private companion object {
        const val CONFIG_OK = "push_config_ok"
        const val CONFIG_OFF = "push_config_off"
        const val CONFIG_FAILED = "push_config_failed"
        const val FIREBASE_OK = "push_firebase_ok"
        const val FIREBASE_FAILED = "push_firebase_failed"
        const val NO_PLAY_SERVICES = "push_no_play_services"
        const val TOKEN_OK = "push_token_ok"
        const val TOKEN_FAILED = "push_token_failed"
        const val TOKEN_REFRESH = "push_token_refresh"
        const val REGISTER_OK = "push_register_ok"
        const val REGISTER_FAILED = "push_register_failed"
        const val UNREGISTER_FAILED = "push_unregister_failed"
        const val FIREBASE_NOT_READY = "firebase_not_ready"
        const val REDACTED = "***"
        val LONG_VALUE = Regex("""[A-Za-z0-9_\-:.]{60,}""")
    }
}
