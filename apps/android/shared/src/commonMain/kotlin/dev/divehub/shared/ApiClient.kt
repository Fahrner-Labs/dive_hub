package dev.divehub.shared

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.serialization.ContentConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/** A refusal's body: our Problem schema, or Better Auth's `{ code?, message }`. The English text is for logs only. */
@Serializable
data class ApiErrorBody(
    val code: String? = null,
    val error: String? = null,
    val message: String? = null,
)

/**
 * One HTTP layer for the shared core: instance base URL, the bearer token, one retry only for a network failure or a
 * 5xx, and a 401 that ends the session (client contract §2). A caller with its own retry policy (the sync engine)
 * turns that retry off.
 */
class ApiClient(
    engine: HttpClientEngine,
    private val versionName: String,
    private val token: suspend () -> String?,
    private val onUnauthorized: suspend () -> Unit,
    private val json: Json,
    private val onClientTooOld: suspend () -> Unit = {},
) {
    var baseUrl: String = ""

    private val client = HttpClient(engine) {
        expectSuccess = false
        install(ContentNegotiation) { json(json) }
    }

    suspend fun get(path: String, authenticated: Boolean = true, retry: Boolean = true): HttpResponse =
        send(HttpMethod.Get, path, null, authenticated, retry)

    suspend fun post(
        path: String,
        body: String? = null,
        authenticated: Boolean = true,
        retry: Boolean = true,
    ): HttpResponse = send(HttpMethod.Post, path, body, authenticated, retry)

    /** A native write's write (ADR 0083 D2): [ifMatch] is sent as `If-Match: "<version>"`, the 412 contract's version. */
    suspend fun put(
        path: String,
        body: String? = null,
        authenticated: Boolean = true,
        ifMatch: Long? = null,
        retry: Boolean = true,
    ): HttpResponse = send(HttpMethod.Put, path, body, authenticated, retry, ifMatch)

    suspend fun patch(
        path: String,
        body: String? = null,
        authenticated: Boolean = true,
        ifMatch: Long? = null,
        retry: Boolean = true,
    ): HttpResponse = send(HttpMethod.Patch, path, body, authenticated, retry, ifMatch)

    suspend fun delete(
        path: String,
        authenticated: Boolean = true,
        ifMatch: Long? = null,
        retry: Boolean = true,
    ): HttpResponse = send(HttpMethod.Delete, path, null, authenticated, retry, ifMatch)

    /**
     * Open a long-lived response and consume it inside [block] (the live hint stream, ADR 0066). Ordinary responses go
     * through Ktor's `SaveBody` plugin, which reads the whole body into memory before returning it; a live stream would
     * therefore never surface a line. `prepareRequest(...).execute` is the supported way to stream instead. No retry:
     * the caller owns reconnect timing, and a stream that fails must hand back to polling rather than block here.
     */
    suspend fun <T> stream(
        path: String,
        authenticated: Boolean = true,
        block: suspend (HttpResponse) -> T,
    ): T {
        check(baseUrl.isNotEmpty()) { "the instance URL is not set" }
        val authorization = if (authenticated) token() else null
        return try {
            client.prepareRequest("$baseUrl$path") {
                method = HttpMethod.Get
                header(HttpHeaders.UserAgent, "$ANDROID_USER_AGENT_PREFIX$versionName")
                authorization?.let { header(HttpHeaders.Authorization, "Bearer $it") }
            }.execute { response ->
                if (response.status.value == 401 && authenticated) onUnauthorized()
                block(response)
            }
        } catch (error: CancellationException) {
            throw error
        } catch (error: IOException) {
            throw NoAnswerException(error)
        }
    }

    /**
     * Decode a response body. A body that does not decode is a [DecodeException], not a serializer exception escaping
     * the caller's refusal handling (client contract §2); cancellation is never turned into one.
     */
    suspend inline fun <reified T> decode(response: HttpResponse): T = try {
        response.body()
    } catch (error: NoTransformationFoundException) {
        throw DecodeException(error)
    } catch (error: ContentConvertException) {
        val causes = generateSequence<Throwable>(error) { it.cause }.toList()
        causes.firstOrNull { it is CancellationException }?.let { throw it }
        causes.firstOrNull { it is IOException }?.let { throw NoAnswerException(it) }
        throw DecodeException(error)
    } catch (error: IOException) {
        throw NoAnswerException(error)
    }

    /** The body of a refusal, if it has one; never used for a message to the person. */
    suspend fun errorBody(response: HttpResponse): ApiErrorBody? {
        val text = try {
            response.bodyAsText()
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            return null
        }
        if (text.isBlank()) return null
        return try {
            json.decodeFromString<ApiErrorBody>(text)
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            null
        }
    }

    /** A refusal as a code the UI translates; the server's code wins, the status names a local one otherwise. */
    suspend fun failure(response: HttpResponse): DiveHubException {
        val body = errorBody(response)
        val code = body?.code ?: when (response.status.value) {
            401 -> ErrorCodes.SIGN_IN_REQUIRED
            429 -> ErrorCodes.TOO_MANY_ATTEMPTS
            in 500..599 -> ErrorCodes.SERVER_ERROR
            else -> "http_${response.status.value}"
        }
        if (code == ErrorCodes.CLIENT_TOO_OLD) onClientTooOld()
        return DiveHubException(code, response.status.value, body?.message ?: body?.error)
    }

    private suspend fun send(
        method: HttpMethod,
        path: String,
        body: String?,
        authenticated: Boolean,
        retry: Boolean,
        ifMatch: Long? = null,
    ): HttpResponse {
        check(baseUrl.isNotEmpty()) { "the instance URL is not set" }
        var attempts = 0
        while (true) {
            try {
                val response = client.request("$baseUrl$path") {
                    this.method = method
                    header(HttpHeaders.UserAgent, "$ANDROID_USER_AGENT_PREFIX$versionName")
                    if (body != null) {
                        contentType(ContentType.Application.Json)
                        setBody(body)
                    }
                    ifMatch?.let { header("If-Match", "\"$it\"") }
                    if (authenticated) token()?.let { header(HttpHeaders.Authorization, "Bearer $it") }
                }
                if (retry && response.status.value in 500..599 && attempts == 0) {
                    attempts += 1
                    response.bodyAsText()
                    continue
                }
                if (response.status.value == 401 && authenticated) onUnauthorized()
                return response
            } catch (error: CancellationException) {
                throw error
            } catch (error: IOException) {
                if (retry && attempts == 0) {
                    attempts += 1
                    continue
                }
                throw NoAnswerException(error)
            }
        }
    }
}

/** The sign-in response is read from `set-auth-token`; this name keeps the contract's spelling in one place. */
const val SET_AUTH_TOKEN: String = "set-auth-token"

/** The app's identification on every request (ADR 0076); the server logs it and uses it only for a hard break. */
const val ANDROID_USER_AGENT_PREFIX: String = "divehub-android/"
