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
import kotlinx.serialization.decodeFromString
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
    private val cachedMe: CachedMeStore,
    versionName: String,
    private val pendingWrites: PendingWriteQueue? = null,
    /**
     * A best-effort cleanup before the token is cleared (push registration, ADR 0085). It runs while the old session
     * still authenticates, and a failure never blocks the sign-out: the server cascade removes the row anyway.
     */
    private val onSessionEnding: suspend () -> Unit = {},
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

    /**
     * True while a sign-in is proving the new User. [hasSession] stays false so a background worker cannot start a
     * sync with the new token before the previous User's store is either kept (same User) or wiped (ADR 0084 D8).
     */
    private var signingIn = false

    private val _state = MutableStateFlow<SessionState>(SessionState.Loading)
    val state: StateFlow<SessionState> = _state.asStateFlow()

    /** The instance URL the sign-in screen starts from, if one was used before. */
    suspend fun knownInstance(): String? = instances.load()

    /** How many writes are still waiting to reach the server; sign-out and a change of User must not drop them silently. */
    suspend fun pendingWriteCount(): Long = pendingWrites?.queuedWriteCount() ?: 0

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
    suspend fun hasSession(): Boolean = !signingIn && instances.load() != null && tokens.load() != null

    /**
     * A stored token is proven by GET /api/me; without one the sign-in screen shows. With one but no answer (offline,
     * unreachable) or a server error, the last successful answer restores the session instead of declaring the User
     * signed out (FAH-419); only a 401 ends it. Without a cached answer the sign-in screen stays correct.
     */
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
            rememberMe(me)
            SessionState.SignedIn(url, me.user, me.preferences).also { _state.value = it }
        } catch (error: DiveHubException) {
            if (error.code == ErrorCodes.CLIENT_TOO_OLD) return _state.value
            // A 401 already ended the session in onUnauthorized; the token is gone and the state is SignedOut.
            if (error.status == 401) return _state.value
            cachedOrSignedOut(url, error)
        } catch (error: NoAnswerException) {
            cachedOrSignedOut(url, error)
        }
    }

    /**
     * Sign in and prove the token with `GET /api/me`. The previous User's queued writes survive a lost session: when
     * the same User signs back in they are kept and replay resumes (ADR 0084 D8). A different User is a switch, and
     * when that switch would discard queued writes it must be an explicit decision — the caller retries with
     * [discardPending] after the warning. A first sign-in with no remembered User still wipes first, before the new
     * token is stored, so no background sync can write the old rows back (client contract §1).
     */
    suspend fun signIn(
        instanceUrl: String,
        email: String,
        password: String,
        discardPending: Boolean = false,
    ): UserView {
        val url = InstanceUrl.normalize(instanceUrl)
            ?: throw DiveHubException(ErrorCodes.INVALID_INSTANCE_URL)
        api.baseUrl = url
        // The last successful answer survives a 401, so it still names the User the queued writes belong to.
        val previousUserId = cachedMe.load()?.let(::cachedUserId)
        val queuedAtStart = pendingWriteCount()

        // A same-User re-sign-in or a switch both end the old session's push row first, while its token still works
        // (ADR 0085). No token means nothing to unregister.
        if (tokens.load() != null) notifySessionEnding()
        signingIn = true
        try {
            // The previous User's token goes first, so a background sync waiting on the wipe cannot refresh with it.
            tokens.clear()
            // With no remembered User there is no same-User resume to protect: wipe before the new token is stored.
            // A queue present in that state belongs to a User we cannot identify, so it waits for an explicit decision.
            if (previousUserId == null && queuedAtStart == 0L) {
                userData.forget()
                cachedMe.clear()
            }

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
                if (error.code != ErrorCodes.CLIENT_TOO_OLD) tokens.clear()
                throw error
            } catch (error: NoAnswerException) {
                tokens.clear()
                throw error
            }

            if (previousUserId == null) {
                if (queuedAtStart > 0 && !discardPending) {
                    tokens.clear()
                    throw PendingWritesException(queuedAtStart)
                }
                if (queuedAtStart > 0) {
                    userData.forget()
                    cachedMe.clear()
                }
            } else if (previousUserId != me.user.id) {
                val queued = pendingWriteCount()
                if (queued > 0 && !discardPending) {
                    // Do not leave the new User's token usable over the previous User's store.
                    tokens.clear()
                    throw PendingWritesException(queued)
                }
                userData.forget()
                cachedMe.clear()
            }

            instances.save(url)
            rememberMe(me)
            return me.user.also { _state.value = SessionState.SignedIn(url, it, me.preferences) }
        } finally {
            signingIn = false
        }
    }

    /**
     * Ends the session here whatever the server answers; the server call is best effort. The instance URL stays, so
     * the next person sees the sign-in form rather than the first-start question again.
     *
     * A non-empty queue blocks the sign-out: the caller warns ("N changes haven't synced") and offers *Sync now* or
     * *Discard and sign out* (ADR 0084 D8). [signOutDiscardingPending] is the confirmed discard.
     */
    suspend fun signOut(): SignOutOutcome = endSessionGuarded(keepAddress = true)

    /** *Discard and sign out*: the User accepted losing the queued writes, so the wipe proceeds (ADR 0084 D8). */
    suspend fun signOutDiscardingPending(): SignOutOutcome {
        endSession(keepAddress = true)
        return SignOutOutcome.SignedOut
    }

    /**
     * The settings screen's "Change instance": sign out (which wipes the previous User's local data) and forget the
     * address too, so the next start asks for an instance instead of falling back to the old one. A non-empty queue
     * blocks it exactly like [signOut].
     */
    suspend fun changeInstance(): SignOutOutcome = endSessionGuarded(keepAddress = false)

    /** *Discard and sign out* for a change of instance (ADR 0084 D8). */
    suspend fun changeInstanceDiscardingPending(): SignOutOutcome {
        endSession(keepAddress = false)
        return SignOutOutcome.SignedOut
    }

    private suspend fun endSessionGuarded(keepAddress: Boolean): SignOutOutcome {
        val queued = pendingWriteCount()
        if (queued > 0) return SignOutOutcome.PendingChanges(queued)
        endSession(keepAddress)
        return SignOutOutcome.SignedOut
    }

    private suspend fun endSession(keepAddress: Boolean) {
        notifySessionEnding()
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

    /** Clean up what belongs to the ending session; a best-effort callback must never block getting out. */
    private suspend fun notifySessionEnding() {
        try {
            onSessionEnding()
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            // The session still ends locally; the server's foreign-key cascade removes the push row.
        }
    }

    private suspend fun me(): ApiMeGet200Response {
        val response = api.get("/api/me")
        if (!response.status.isSuccess()) throw api.failure(response)
        return api.decode(response)
    }

    /** The User the last successful answer named; a cache from another shape is no answer. */
    private fun cachedUserId(cached: String): String? = try {
        json.decodeFromString<ApiMeGet200Response>(cached).user.id
    } catch (_: Exception) {
        null
    }

    /** Keep the last successful answer for a cold start with no network; a write failure only costs that fallback. */
    private suspend fun rememberMe(me: ApiMeGet200Response) {
        try {
            cachedMe.save(json.encodeToString(me))
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            // The session is already good; only the offline cold start loses its remembered answer.
        }
    }

    /**
     * A failed `me` with no 401 (offline, unreachable, server down) restores the last successful answer if there is
     * one. Without it the sign-in screen is correct, and the failure still reaches the caller as before.
     */
    private suspend fun cachedOrSignedOut(url: String, error: Throwable): SessionState {
        val stored = cachedMe.load()
        if (stored != null) {
            try {
                val me = json.decodeFromString<ApiMeGet200Response>(stored)
                return SessionState.SignedIn(url, me.user, me.preferences).also { _state.value = it }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                // A cache from another shape is no answer; drop it rather than fail the cold start again.
                cachedMe.clear()
            }
        }
        SessionState.SignedOut(url).also { _state.value = it }
        throw error
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
        cachedMe.clear()
        _state.value = SessionState.SignedOut(instances.load())
    }
}

/** `GET /api/health/live`'s shape (FAH-332): only the status proves the host is a Dive Hub. */
@Serializable
private data class HealthView(val status: String)

/** What a sign-out or change-of-User did: it ended the session, or it needs the User's decision first (ADR 0084 D8). */
sealed interface SignOutOutcome {
    data object SignedOut : SignOutOutcome

    /** The queue is non-empty; the session stays until the caller offers *Sync now* or *Discard and sign out*. */
    data class PendingChanges(val count: Long) : SignOutOutcome
}

/** A sign-in as another User would discard queued writes: an explicit decision is required (ADR 0084 D8). */
class PendingWritesException(val count: Long) : DiveHubException(
    code = ErrorCodes.PENDING_WRITES,
    serverMessage = "$count queued writes belong to the previous User",
)

private const val HEALTH_LIVE_PATH = "/api/health/live"
private const val HEALTHY_STATUS = "ok"
