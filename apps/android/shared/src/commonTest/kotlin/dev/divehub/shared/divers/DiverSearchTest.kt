package dev.divehub.shared.divers

import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private class SearchHarness(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData) {
    private val api = ApiClient(
        engine = MockEngine(handler),
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = {},
        json = Json { ignoreUnknownKeys = true; explicitNulls = false },
    ).apply { baseUrl = "https://dive.example" }
    val search = ApiDiverSearch(api)
}

private fun MockRequestHandleScope.json(body: String): HttpResponseData =
    respond(body, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))

class DiverSearchTest {
    @Test
    fun `the query is trimmed, escaped and capped as the web does`() = runTest {
        var path: String? = null
        val harness = SearchHarness { request ->
            path = request.url.encodedPath + "?" + request.url.encodedQuery
            json("[]")
        }

        harness.search.search("  Ada Byron  ")

        assertEquals("/api/divers/search?q=Ada%20Byron&limit=20", path)
    }

    @Test
    fun `every result carries whether the User manages it or it is external`() = runTest {
        val harness = SearchHarness {
            json(
                """[
                  {"id":"own","name":"Ada","managed":true,"external":false},
                  {"id":"child","name":"Bea","managed":true,"external":false},
                  {"id":"other","name":"Cleo","managed":false,"external":false},
                  {"id":"guest","name":"Dee","managed":false,"external":true}
                ]""",
            )
        }

        val found = harness.search.search("e")

        assertEquals(listOf("Ada", "Bea", "Cleo", "Dee"), found.map { it.name })
        assertTrue(found[0].managed)
        assertTrue(found[1].managed)
        assertFalse(found[2].managed)
        assertTrue(found[3].external)
    }

    @Test
    fun `a refusal is the API's failure, not an empty list`() = runTest {
        val harness = SearchHarness {
            respond(
                """{"code":"too_many_attempts","status":429}""",
                HttpStatusCode.TooManyRequests,
                headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val failure = assertFailsWith<DiveHubException> { harness.search.search("Ada") }

        assertEquals("too_many_attempts", failure.code)
    }
}
