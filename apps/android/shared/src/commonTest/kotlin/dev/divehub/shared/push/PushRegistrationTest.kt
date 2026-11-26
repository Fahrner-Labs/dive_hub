package dev.divehub.shared.push

import dev.divehub.shared.ApiClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

private const val CONFIG_JSON = """{"android":{"projectId":"dive-hub-test","applicationId":"1:2:android:abc","apiKey":"public-key","gcmSenderId":"42"}}"""

class PushRegistrationTest {
    @Test
    fun `the android config comes from the unauthenticated config route`() = runTest {
        var authorized: String? = null
        val registration = registration { request ->
            authorized = request.headers[HttpHeaders.Authorization]
            respond(CONFIG_JSON, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }

        val config = registration.androidConfig()

        assertEquals("dive-hub-test", config?.projectId)
        assertEquals("1:2:android:abc", config?.applicationId)
        assertEquals("public-key", config?.apiKey)
        assertEquals("42", config?.gcmSenderId)
        assertNull(authorized)
    }

    @Test
    fun `an empty config and an older server both mean push is off`() = runTest {
        val empty = registration { respond("{}", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json")) }
        assertNull(empty.androidConfig())

        val older = registration { respond("", HttpStatusCode.NotFound) }
        assertNull(older.androidConfig())
    }

    @Test
    fun `a malformed config is off rather than a failed start`() = runTest {
        val registration = registration {
            respond("""{"android":{"projectId":12}}""", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }

        assertNull(registration.androidConfig())
    }

    @Test
    fun `registration posts the platform and token and keeps the returned row id`() = runTest {
        var body: String? = null
        var method: HttpMethod? = null
        val registration = registration { request ->
            method = request.method
            body = (request.body as TextContent).text
            respond(
                """{"id":"device-1","platform":"android","createdAt":"2026-11-25T10:00:00Z","lastSeenAt":"2026-11-25T10:00:00Z"}""",
                HttpStatusCode.OK,
                headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val id = registration.register("fcm-token")

        assertEquals("device-1", id)
        assertEquals(HttpMethod.Post, method)
        assertEquals("""{"platform":"android","pushToken":"fcm-token"}""", body)
    }

    @Test
    fun `unregister deletes the row and accepts one already gone`() = runTest {
        val paths = mutableListOf<String>()
        val registration = registration { request ->
            paths += request.url.encodedPath
            if (paths.size == 1) respond("", HttpStatusCode.NoContent) else respond("", HttpStatusCode.NotFound)
        }

        registration.unregister("device-1")
        registration.unregister("device-2")

        assertEquals(listOf("/api/devices/device-1", "/api/devices/device-2"), paths)
    }

    private fun registration(
        handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
    ): PushRegistration {
        val api = ApiClient(
            engine = MockEngine(handler),
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true; explicitNulls = false },
        )
        api.baseUrl = "https://dive.example"
        return PushRegistration(api)
    }
}
