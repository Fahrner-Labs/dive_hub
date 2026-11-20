package dev.divehub.shared

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private const val OK_JSON = """{"status":"ok"}"""

class ApiClientTest {

    @Test
    fun `every request carries the app version as its user agent`() = runTest {
        val agents = mutableListOf<String?>()
        val api = ApiClient(
            engine = MockEngine { request ->
                agents += request.headers[HttpHeaders.UserAgent]
                respond(OK_JSON, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
            },
            versionName = "1.4.2",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true },
        )
        api.baseUrl = "https://dive.example"

        api.get("/api/me")
        api.post("/api/tools/gas-plan", "{}")
        api.get("/api/providers", authenticated = false)

        assertEquals(
            listOf<String?>("divehub-android/1.4.2", "divehub-android/1.4.2", "divehub-android/1.4.2"),
            agents,
        )
    }

    @Test
    fun `a body that does not decode is a refusal, not an escaped serializer exception`() = runTest {
        val api = ApiClient(
            engine = MockEngine { respond(OK_JSON, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json")) },
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true },
        )
        api.baseUrl = "https://dive.example"
        val response = api.get("/api/me")

        val error = assertFailsWith<DecodeException> { api.decode<List<Int>>(response) }

        assertEquals(ErrorCodes.SERVER_ERROR, error.code)
    }

    @Test
    fun `a 200 without a JSON body is a decode failure`() = runTest {
        val api = ApiClient(
            engine = MockEngine { respond("not json", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "text/plain")) },
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true },
        )
        api.baseUrl = "https://dive.example"
        val response = api.get("/api/me")

        val error = assertFailsWith<DecodeException> { api.decode<List<Int>>(response) }

        assertEquals(ErrorCodes.SERVER_ERROR, error.code)
    }

    @Test
    fun `a retried request carries the user agent on both attempts`() = runTest {
        val agents = mutableListOf<String?>()
        val api = ApiClient(
            engine = MockEngine { request ->
                agents += request.headers[HttpHeaders.UserAgent]
                if (agents.size == 1) {
                    respond("""{"code":"internal_error","error":"boom"}""", HttpStatusCode.InternalServerError)
                } else {
                    respond(OK_JSON, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
                }
            },
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true },
        )
        api.baseUrl = "https://dive.example"

        api.get("/api/me")

        assertEquals(listOf<String?>("divehub-android/0.1.0-dev", "divehub-android/0.1.0-dev"), agents)
    }
}
