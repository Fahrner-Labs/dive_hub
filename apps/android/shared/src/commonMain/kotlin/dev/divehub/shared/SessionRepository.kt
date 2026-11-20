package dev.divehub.shared

import dev.divehub.api.models.ApiMeGet200Response
import dev.divehub.api.models.ApiMeGet200ResponsePreferences
import dev.divehub.api.models.SignInEmailRequest
import dev.divehub.api.models.UserView
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** The answer to the onboarding probe: the instance is a Dive Hub, or why it could not be confirmed (FAH-332). */
sealed interface InstanceProbeResult {
    data object Reachable : InstanceProbeResult
    data class Unreachable(val code: String) : InstanceProbeResult
}

/** The delay after which an instance probe counts as no answer; the sign-in form stays usable. */
const val INSTANCE_PROBE_TIMEOUT_MILLIS: Long = 5_000

/** What the UI shows: still loading, the sign-in screen (with the instance already known or not), or the User. */
sealed interface SessionState {
    data object Loading : SessionState
    data class SignedOut(val instanceUrl: String?) : SessionState
    data class SignedIn(
        val instanceUrl: String,
        val user: UserView,
        val preferences: ApiMeGet200ResponsePreferences,
    ) : SessionState
    /** The server refuses this app's release (ADR 0076): keep the session, show only that an update is required. */
    data class UpdateRequired(val instanceUrl: String?) : SessionState
}

/**
 * Sign-in, sign-out and the stored session (ADR 0074). It owns the one rule that spans calls: a 401 anywhere ends the
 * session, and both sign-in and sign-out forget everything about the previous User (client contract §1 and §2). A 401
 * is not a change of User: the cached store stays readable offline (client contract, Offline reads).
 */
class SessionRepository(
    engine: HttpClientEngine,
    private val tokens: TokenStore,
    private val instances: InstanceUrlStore,
    private val userData: UserScopedStore,
    versionName: String,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }
    private val api = ApiClient(
        engine,
        versionName = versionName,
        token = { tokens.load() },
        onUnauthorized = { endSession() },
        json = json,
        onClientTooOld = { clientTooOld() },
    )

    /** The signed-in User's HTTP layer, for repositories that read their data (the logbook). */
    val client: ApiClient get() = api

    private val _state = MutableStateFlow<SessionState>(SessionState.Loading)
    val state: StateFlow<SessionState> = _state.asStateFlow()

    /** The instance URL the sign-in screen starts from, if one was used before. */
    suspend fun knownInstance(): String? = instances.load()

    /**
     * Probe `GET /api/health/live` before asking for credentials (FAH-332). The route is unauthenticated, so a typo,
     * an offline phone or a host that is not a Dive Hub is answered here rather than as a misleading sign-in failure.
     * The client-version gate participates too: a `client_too_old` refusal leaves the app's update-required state.
     * Timeout and network errors are [ErrorCodes.NO_ANSWER]; an answer of the wrong shape or status is
     * [ErrorCodes.NOT_DIVEHUB]. [timeoutMillis] is for tests; the app uses the 5 s default.
     */
    suspend fun probeInstance(
        instanceUrl: String,
        timeoutMillis: Long = INSTANCE_PROBE_TIMEOUT_MILLIS,
    ): InstanceProbeResult {
        val url = InstanceUrl.normalize(instanceUrl)
            ?: return InstanceProbeResult.Unreachable(ErrorCodes.INVALID_INSTANCE_URL)
        api.baseUrl = url
        val result = try {
            withTimeoutOrNull(timeoutMillis) {
                val response = api.get(HEALTH_LIVE_PATH, authenticated = false, retry = false)
                if (!response.status.isSuccess()) {
                    val refusal = api.failure(response)
                    val code = when {
                        refusal.code == ErrorCodes.CLIENT_TOO_OLD -> ErrorCodes.CLIENT_TOO_OLD
                        (refusal.status ?: 0) in 500..599 -> ErrorCodes.SERVER_ERROR
                        else -> ErrorCodes.NOT_DIVEHUB
                    }
                    return@withTimeoutOrNull InstanceProbeResult.Unreachable(code)
                }
                val health = try {
                    api.decode<HealthView>(response)
                } catch (_: DecodeException) {
                    return@withTimeoutOrNull InstanceProbeResult.Unreachable(ErrorCodes.NOT_DIVEHUB)
                }
                if (health.status == HEALTHY_STATUS) {
                    InstanceProbeResult.Reachable
                } else {
                    InstanceProbeResult.Unreachable(ErrorCodes.NOT_DIVEHUB)
                }
            }
        } catch (_: NoAnswerException) {
            InstanceProbeResult.Unreachable(ErrorCodes.NO_ANSWER)
        }
        return result ?: InstanceProbeResult.Unreachable(ErrorCodes.NO_ANSWER)
    }

    /** Whether this device holds both an instance and a session token: what a background sync needs before a request. */
    suspend fun hasSession(): Boolean = instances.load() != null && tokens.load() != null

    /** A stored token is proven by GET /api/me; without one (or after a 401) the sign-in screen shows. */
    suspend fun restore(): SessionState {
        val url = instances.load()
        if (url == null) {
            _state.value = SessionState.SignedOut(null)
            return _state.value
        }
        api.baseUrl = url
        if (tokens.load() == null) {
            _state.value = SessionState.SignedOut(url)
            return _state.value
        }
        return try {
            val me = me()
            SessionState.SignedIn(url, me.user, me.preferences).also { _state.value = it }
        } catch (error: DiveHubException) {
            if (error.code == ErrorCodes.CLIENT_TOO_OLD) return _state.value
            SessionState.SignedOut(url).also { _state.value = it }
            if (error.status != 401) throw error
            _state.value
        } catch (error: NoAnswerException) {
            SessionState.SignedOut(url).also { _state.value = it }
            throw error
        }
    }

    suspend fun signIn(instanceUrl: String, email: String, password: String): UserView {
        val url = InstanceUrl.normalize(instanceUrl)
            ?: throw DiveHubException(ErrorCodes.INVALID_INSTANCE_URL)
        api.baseUrl = url
        // The previous User's token goes first, so a background sync waiting on the wipe cannot refresh with it and
        // write the old rows back after the store is cleared (client contract §1).
        tokens.clear()
        userData.forget()

        val body = json.encodeToString(SignInEmailRequest(email = email, password = password))
        val response = api.post("/api/auth/sign-in/email", body, authenticated = false)
        if (!response.status.isSuccess()) throw signInFailure(response)

        // The sign-in response carries the session token in `set-auth-token`, never as a cookie (ADR 0074).
        val token = response.headers[SET_AUTH_TOKEN]
            ?: throw DiveHubException(ErrorCodes.MISSING_TOKEN, response.status.value)
        tokens.save(token)

        val me = try {
            me()
        } catch (error: DiveHubException) {
            if (error.code != ErrorCodes.CLIENT_TOO_OLD) {
                tokens.clear()
                userData.forget()
            }
            throw error
        } catch (error: NoAnswerException) {
            tokens.clear()
            userData.forget()
            throw error
        }

        instances.save(url)
        return me.user.also { _state.value = SessionState.SignedIn(url, it, me.preferences) }
    }

    /**
     * Ends the session here whatever the server answers; the server call is best effort. The instance URL stays, so
     * the next person sees the sign-in form rather than the first-start question again.
     */
    suspend fun signOut() = endSession(keepAddress = true)

    /**
     * The settings screen's "Change instance": sign out (which wipes the previous User's local data) and forget the
     * address too, so the next start asks for an instance instead of falling back to the old one.
     */
    suspend fun changeInstance() = endSession(keepAddress = false)

    private suspend fun endSession(keepAddress: Boolean) {
        try {
            val url = instances.load()
            if (url != null && tokens.load() != null) {
                api.baseUrl = url
                api.post("/api/auth/sign-out", "{}")
            }
        } catch (error: CancellationException) {
            throw error
        } catch (_: DiveHubException) {
            // The local session still ends; the server's copy expires or is ended from another session.
        } catch (_: NoAnswerException) {
            // Offline: forget the token here anyway.
        } finally {
            if (!keepAddress) instances.clear()
            forgetUser()
        }
    }

    private suspend fun me(): ApiMeGet200Response {
        val response = api.get("/api/me")
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode(response)
    }

    private suspend fun signInFailure(response: io.ktor.client.statement.HttpResponse): DiveHubException {
        val body = api.errorBody(response)
        if (body?.code == ErrorCodes.CLIENT_TOO_OLD) {
            clientTooOld()
            return DiveHubException(ErrorCodes.CLIENT_TOO_OLD, response.status.value, body.message ?: body.error)
        }
        val code = when {
            response.status.value == 429 -> ErrorCodes.TOO_MANY_ATTEMPTS
            body?.code == "BANNED_USER" -> ErrorCodes.USER_DISABLED
            response.status.value in 400..499 -> ErrorCodes.INVALID_CREDENTIALS
            else -> body?.code ?: ErrorCodes.SERVER_ERROR
        }
        return DiveHubException(code, response.status.value, body?.message ?: body?.error)
    }

    /** A hard break: the app must be updated. The token and the instance stay; only the screen changes. */
    private suspend fun clientTooOld() {
        _state.value = SessionState.UpdateRequired(instances.load())
    }

    /** A 401: only the expired token goes; the read store stays readable until the next User change. */
    private suspend fun endSession() {
        tokens.clear()
        _state.value = SessionState.SignedOut(instances.load())
    }

    private suspend fun forgetUser() {
        tokens.clear()
        userData.forget()
        _state.value = SessionState.SignedOut(instances.load())
    }
}

/** `GET /api/health/live`'s shape (FAH-332): only the status proves the host is a Dive Hub. */
@Serializable
private data class HealthView(val status: String)

private const val HEALTH_LIVE_PATH = "/api/health/live"
private const val HEALTHY_STATUS = "ok"
