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
