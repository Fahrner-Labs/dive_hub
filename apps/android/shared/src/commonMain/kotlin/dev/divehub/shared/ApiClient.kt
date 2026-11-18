package dev.divehub.shared

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestTimeoutException
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
 * 5xx, and a 401 that ends the session (client contract §2).
 */
class ApiClient(
    engine: HttpClientEngine,
    private val token: suspend () -> String?,
    private val onUnauthorized: suspend () -> Unit,
    private val json: Json,
) {
    var baseUrl: String = ""

    private val client = HttpClient(engine) {
        expectSuccess = false
        install(ContentNegotiation) { json(json) }
    }

    suspend fun get(path: String, authenticated: Boolean = true): HttpResponse =
        send(HttpMethod.Get, path, null, authenticated)

    suspend fun post(path: String, body: String? = null, authenticated: Boolean = true): HttpResponse =
        send(HttpMethod.Post, path, body, authenticated)

    suspend inline fun <reified T> decode(response: HttpResponse): T = response.body()

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

    /** A refusal as a code the UI translates; `fallback` names one the caller knows better than the status does. */
    suspend fun failure(response: HttpResponse, fallback: String? = null): DiveHubException {
        val body = errorBody(response)
        val code = body?.code ?: fallback ?: when (response.status.value) {
            401 -> ErrorCodes.SIGN_IN_REQUIRED
            429 -> ErrorCodes.TOO_MANY_ATTEMPTS
            in 500..599 -> ErrorCodes.SERVER_ERROR
            else -> "http_${response.status.value}"
        }
        return DiveHubException(code, response.status.value, body?.message ?: body?.error)
    }

    private suspend fun send(method: HttpMethod, path: String, body: String?, authenticated: Boolean): HttpResponse {
        check(baseUrl.isNotEmpty()) { "the instance URL is not set" }
        var attempts = 0
        while (true) {
            try {
                val response = client.request("$baseUrl$path") {
                    this.method = method
                    if (body != null) {
                        contentType(ContentType.Application.Json)
                        setBody(body)
                    }
                    if (authenticated) token()?.let { header(HttpHeaders.Authorization, "Bearer $it") }
                }
                if (response.status.value in 500..599 && attempts == 0) {
                    attempts += 1
                    response.bodyAsText()
                    continue
                }
                if (response.status.value == 401 && authenticated) onUnauthorized()
                return response
            } catch (error: CancellationException) {
                throw error
            } catch (error: HttpRequestTimeoutException) {
                if (attempts == 0) {
                    attempts += 1
                    continue
                }
                throw NoAnswerException(error)
            } catch (error: IOException) {
                if (attempts == 0) {
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
