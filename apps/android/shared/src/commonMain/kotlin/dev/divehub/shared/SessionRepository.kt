package dev.divehub.shared

import dev.divehub.api.models.ApiMeGet200Response
import dev.divehub.api.models.SignInEmailRequest
import dev.divehub.api.models.UserView
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** What the UI shows: still loading, the sign-in screen (with the instance already known or not), or the User. */
sealed interface SessionState {
    data object Loading : SessionState
    data class SignedOut(val instanceUrl: String?) : SessionState
    data class SignedIn(val instanceUrl: String, val user: UserView) : SessionState
}

/**
 * Sign-in, sign-out and the stored session (ADR 0074). It owns the one rule that spans calls: a 401 anywhere ends the
 * session, and both sign-in and sign-out forget everything about the previous User (client contract §1 and §2).
 */
class SessionRepository(
    engine: HttpClientEngine,
    private val tokens: TokenStore,
    private val instances: InstanceUrlStore,
    private val userData: UserScopedStore,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }
    private val api = ApiClient(engine, token = { tokens.load() }, onUnauthorized = { forgetUser() }, json = json)

    private val _state = MutableStateFlow<SessionState>(SessionState.Loading)
    val state: StateFlow<SessionState> = _state.asStateFlow()

    /** The instance URL the sign-in screen starts from, if one was used before. */
    suspend fun knownInstance(): String? = instances.load()

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
            SessionState.SignedIn(url, me.user).also { _state.value = it }
        } catch (error: DiveHubException) {
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
        // The previous User's cached responses go before anything of the new one arrives (client contract §1).
        userData.forget()
        tokens.clear()

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
            tokens.clear()
            userData.forget()
            throw error
        } catch (error: NoAnswerException) {
            tokens.clear()
            userData.forget()
            throw error
        }

        instances.save(url)
        return me.user.also { _state.value = SessionState.SignedIn(url, it) }
    }

    /**
     * Ends the session here whatever the server answers; the server call is best effort. The instance URL stays, so
     * the next person sees the sign-in form rather than the first-start question again.
     */
    suspend fun signOut() {
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
        val code = when {
            response.status.value == 429 -> ErrorCodes.TOO_MANY_ATTEMPTS
            body?.code == "BANNED_USER" -> ErrorCodes.USER_DISABLED
            response.status.value in 400..499 -> ErrorCodes.INVALID_CREDENTIALS
            else -> body?.code ?: ErrorCodes.SERVER_ERROR
        }
        return DiveHubException(code, response.status.value, body?.message ?: body?.error)
    }

    private suspend fun forgetUser() {
        tokens.clear()
        userData.forget()
        _state.value = SessionState.SignedOut(instances.load())
    }
}
