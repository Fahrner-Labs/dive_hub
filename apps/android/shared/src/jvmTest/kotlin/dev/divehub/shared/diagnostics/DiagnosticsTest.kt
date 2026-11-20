package dev.divehub.shared.diagnostics

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.store.db.DiveHubDatabase
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private val FIXED_NOW = Instant.parse("2026-11-20T12:00:00Z")

class DiagnosticsTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false; encodeDefaults = false }
    private val buffer = newBuffer()

    @AfterTest
    fun close() = driver.close()

    private fun newBuffer(): DiagnosticsBuffer {
        DiveHubDatabase.Schema.create(driver)
        return DiagnosticsBuffer(DiveHubDatabase(driver))
    }

    private fun diagnostics(engine: MockEngine, version: String = "1.2.3"): Diagnostics {
        val client = ApiClient(
            engine = engine,
            versionName = version,
            token = { "token" },
            onUnauthorized = {},
            json = json,
        ).also { it.baseUrl = "https://dive.example" }
        return Diagnostics(buffer, client, versionName = version, json = json, clock = { FIXED_NOW })
    }

    private fun capture(index: Int) = buffer.capture(
        DiagnosticEntry(
            occurredAt = FIXED_NOW.toString(),
            level = DiagnosticLevel.ERROR,
            code = "e$index",
            message = "message $index",
            stack = "at example.Kt.run(Kt.kt:1)",
            context = mapOf("where" to "logbook"),
        ),
    )

    @Test
    fun `exportNdjson writes one parseable entry per line, oldest first`() {
        capture(1)
        capture(2)

        val lines = diagnostics(MockEngine { error("no request expected") }).exportNdjson().trimEnd('\n').lines()

        assertEquals(2, lines.size)
        val first = Json.parseToJsonElement(lines.first()).jsonObject
        assertEquals("e1", first["code"]!!.jsonPrimitive.content)
        assertEquals("error", first["level"]!!.jsonPrimitive.content)
        assertEquals("message 1", first["message"]!!.jsonPrimitive.content)
        assertEquals("logbook", first["context"]!!.jsonObject["where"]!!.jsonPrimitive.content)
        assertEquals("e2", Json.parseToJsonElement(lines.last()).jsonObject["code"]!!.jsonPrimitive.content)
    }

    @Test
    fun `an empty buffer exports nothing`() {
        assertEquals("", diagnostics(MockEngine { error("no request expected") }).exportNdjson())
    }

    @Test
    fun `preview shows the newest entries first`() {
        capture(1)
        capture(2)
        capture(3)

        assertEquals(listOf("e3", "e2"), diagnostics(MockEngine { error("no request expected") }).preview(2).map(DiagnosticEntry::code))
    }

    @Test
    fun `a 204 upload sends at most 50 entries and clears the buffer`() = runTest {
        var body: String? = null
        val engine = MockEngine { request ->
            assertEquals("/api/client-diagnostics", request.url.encodedPath)
            body = (request.body as TextContent).text
            respond("", HttpStatusCode.NoContent)
        }
        repeat(60) { capture(it) }

        val result = diagnostics(engine).reportProblem()

        assertEquals(ReportProblemResult.Sent, result)
        assertEquals(0, buffer.count())
        val payload = Json.parseToJsonElement(body!!).jsonObject
        assertEquals("android", payload["client"]!!.jsonObject["name"]!!.jsonPrimitive.content)
        assertEquals("1.2.3", payload["client"]!!.jsonObject["version"]!!.jsonPrimitive.content)
        assertEquals("2026-11-20T12:00:00Z", payload["capturedAt"]!!.jsonPrimitive.content)
        val entries = payload["entries"]!!.jsonArray
        assertEquals(Diagnostics.MAX_UPLOAD, entries.size)
        assertEquals("e10", entries.first().jsonObject["code"]!!.jsonPrimitive.content)
        assertEquals("e59", entries.last().jsonObject["code"]!!.jsonPrimitive.content)
    }

    @Test
    fun `a 403 keeps the buffer and returns the code`() = runTest {
        repeat(2) { capture(it) }
        val engine = MockEngine {
            respond(
                """{"code":"client_diagnostics_disabled","error":"off"}""",
                HttpStatusCode.Forbidden,
                headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val result = diagnostics(engine).reportProblem()

        assertEquals(ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_DISABLED), result)
        assertEquals(2, buffer.count())
    }

    @Test
    fun `a 429 keeps the buffer and returns the code`() = runTest {
        capture(1)
        val engine = MockEngine {
            respond(
                """{"code":"client_diagnostics_limited","error":"later"}""",
                HttpStatusCode.TooManyRequests,
                headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val result = diagnostics(engine).reportProblem()

        assertEquals(ReportProblemResult.Failed(ErrorCodes.CLIENT_DIAGNOSTICS_LIMITED), result)
        assertEquals(1, buffer.count())
    }

    @Test
    fun `no answer keeps the buffer and returns the network code`() = runTest {
        capture(1)
        val engine = MockEngine { throw IOException("offline") }

        val result = diagnostics(engine).reportProblem()

        assertEquals(ReportProblemResult.Failed(ErrorCodes.NO_ANSWER), result)
        assertEquals(1, buffer.count())
    }

    @Test
    fun `an upload truncates the fields to the contract limits`() = runTest {
        buffer.capture(
            DiagnosticEntry(
                occurredAt = FIXED_NOW.toString(),
                level = DiagnosticLevel.WARN,
                message = "m".repeat(DiagnosticsBuffer.MESSAGE_LIMIT + 1),
                stack = "s".repeat(DiagnosticsBuffer.STACK_LIMIT + 1),
            ),
        )
        var body: String? = null
        val engine = MockEngine { request ->
            body = (request.body as TextContent).text
            respond("", HttpStatusCode.NoContent)
        }

        diagnostics(engine).reportProblem()

        val entry = Json.parseToJsonElement(body!!).jsonObject["entries"]!!.jsonArray.single().jsonObject
        assertEquals(DiagnosticsBuffer.MESSAGE_LIMIT, entry["message"]!!.jsonPrimitive.content.length)
        assertEquals(DiagnosticsBuffer.STACK_LIMIT, entry["stack"]!!.jsonPrimitive.content.length)
    }

    @Test
    fun `the status endpoint says whether the instance allows uploads`() = runTest {
        val engine = MockEngine { request ->
            assertEquals("/api/client-diagnostics/status", request.url.encodedPath)
            respond("""{"enabled":true}""", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }

        assertTrue(diagnostics(engine).instanceEnabled())
    }

    @Test
    fun `an unavailable status reads as off`() = runTest {
        val engine = MockEngine { throw IOException("offline") }

        assertFalse(diagnostics(engine).instanceEnabled())
    }
}
