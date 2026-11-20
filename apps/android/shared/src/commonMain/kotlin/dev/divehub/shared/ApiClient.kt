package dev.divehub.shared

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
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
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
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

    suspend fun post(path: String, body: String? = null, authenticated: Boolean = true): HttpResponse =
        send(HttpMethod.Post, path, body, authenticated, retry = true)

    /**
     * Open a long-lived response without reading it (the live hint stream, ADR 0066). No retry: the caller owns
     * reconnect timing, and a stream that fails must hand back to polling rather than block here.
     */
    suspend fun stream(path: String, authenticated: Boolean = true): HttpResponse =
        send(HttpMethod.Get, path, null, authenticated, retry = false)

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

    /**
     * The body as text. A transport failure while the body is read is [NoAnswerException], like a failed request;
     * cancellation is not swallowed.
     */
    suspend fun bodyText(response: HttpResponse): String = try {
        response.bodyAsText()
    } catch (error: CancellationException) {
        throw error
    } catch (error: IOException) {
        throw NoAnswerException(error)
    }

    /**
     * Decode a body the caller has already read, for a response whose shape decides the next step, with the same
     * JSON configuration as [decode].
     */
    fun <T> decode(body: String, serializer: DeserializationStrategy<T>): T = try {
        json.decodeFromString(serializer, body)
    } catch (error: SerializationException) {
        throw DecodeException(error)
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
