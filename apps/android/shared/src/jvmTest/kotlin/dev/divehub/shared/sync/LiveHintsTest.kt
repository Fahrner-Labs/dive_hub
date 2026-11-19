package dev.divehub.shared.sync

import dev.divehub.shared.ApiClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }

class LiveHintsTest {
    private fun api(body: String, status: HttpStatusCode = HttpStatusCode.OK): ApiClient = ApiClient(
        engine = MockEngine {
            respond(body, status, headersOf(HttpHeaders.ContentType, "text/event-stream"))
        },
        token = { "token" },
        onUnauthorized = {},
        json = JSON,
    ).apply { baseUrl = "https://dive.example" }

    @Test
    fun `a connect emits connected first and then each hint frame`() = runTest {
        val body = """
            :ok

            event: hint
            data: {"v":1,"kind":"dive","id":"dive-1"}

            :hb

            event: hint
            data: {"v":1,"kind":"site","id":"site-1"}

        """.trimIndent()

        val events = SseLiveHints(api(body)).events().toList()

        assertEquals(3, events.size)
        assertIs<LiveEvent.Connected>(events[0])
        assertEquals(LiveHint("dive", "dive-1"), (events[1] as LiveEvent.Hint).hint)
        assertEquals(LiveHint("site", "site-1"), (events[2] as LiveEvent.Hint).hint)
    }

    @Test
    fun `a malformed or newer frame is skipped rather than ending the stream`() = runTest {
        val body = """
            event: hint
            data: not json

            event: hint
            data: {"v":2,"kind":"dive","id":"ignored"}

            event: hint
            data: {"v":1,"kind":"dive","id":"kept"}

        """.trimIndent()

        val events = SseLiveHints(api(body)).events().toList()

        assertEquals(listOf(LiveEvent.Connected, LiveEvent.Hint(LiveHint("dive", "kept"))), events)
    }
}
