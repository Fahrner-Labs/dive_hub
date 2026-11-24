package dev.divehub.shared.write

import dev.divehub.shared.ApiClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

private val JSON = Json { ignoreUnknownKeys = true; explicitNulls = false }

private fun probeOf(status: HttpStatusCode? = null, fail: Throwable? = null): ServerWriteCapabilityProbe {
    val engine = MockEngine {
        if (fail != null) throw fail
        respond("{}", status ?: HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
    }
    val api = ApiClient(
        engine = engine,
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = {},
        json = JSON,
    ).apply { baseUrl = "https://dive.example" }
    return ServerWriteCapabilityProbe(api)
}

class WriteCapabilityProbeTest {
    @Test
    fun `a feed answer proves the write contract`() = runTest {
        assertEquals(WriteCapability.Writeable, probeOf(HttpStatusCode.OK).probe())
    }

    @Test
    fun `a missing feed is a pre-feed server and hides write UI`() = runTest {
        assertEquals(WriteCapability.ReadOnly, probeOf(HttpStatusCode.NotFound).probe())
    }

    @Test
    fun `a server error proves nothing`() = runTest {
        assertEquals(WriteCapability.Unknown, probeOf(HttpStatusCode.InternalServerError).probe())
    }

    @Test
    fun `no answer leaves the capability unknown rather than read-only`() = runTest {
        assertEquals(WriteCapability.Unknown, probeOf(fail = IOException("offline")).probe())
    }
}
