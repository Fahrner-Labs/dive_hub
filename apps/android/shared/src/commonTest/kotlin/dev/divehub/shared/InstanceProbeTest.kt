package dev.divehub.shared

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals

private const val HEALTH_PATH = "/api/health/live"

private class ProbeTokens : TokenStore {
    override suspend fun load(): String? = null
    override suspend fun save(token: String) = Unit
    override suspend fun clear() = Unit
}

private class ProbeInstances : InstanceUrlStore {
    override suspend fun load(): String? = null
    override suspend fun save(url: String) = Unit
    override suspend fun clear() = Unit
}

private class ProbeCache : UserScopedStore {
    override suspend fun forget() = Unit
}

private class ProbeCachedMe : CachedMeStore {
    override suspend fun load(): String? = null
    override suspend fun save(meJson: String) = Unit
    override suspend fun clear() = Unit
}

private fun probeRepository(
    handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
): SessionRepository = SessionRepository(
    MockEngine(handler),
    ProbeTokens(),
    ProbeInstances(),
    ProbeCache(),
    ProbeCachedMe(),
    versionName = "0.1.0-dev",
)

private fun MockRequestHandleScope.json(body: String, status: HttpStatusCode = HttpStatusCode.OK): HttpResponseData =
    respond(body, status, headersOf(HttpHeaders.ContentType, "application/json"))

private suspend fun probe(repository: SessionRepository, instanceUrl: String = "dive.example"): InstanceProbeResult =
    withContext(Dispatchers.Default) { repository.probeInstance(instanceUrl, timeoutMillis = 2_000) }

class InstanceProbeTest {
    @Test
    fun `a healthy answer makes the instance reachable without a token`() = runTest {
        var authorization: String? = null
        val repository = probeRepository { request ->
            assertEquals(HttpMethod.Get, request.method)
            assertEquals(HEALTH_PATH, request.url.encodedPath)
            authorization = request.headers[HttpHeaders.Authorization]
            json("""{"status":"ok","version":"1.4.2"}""")
        }

        val result = probe(repository)

        assertEquals(InstanceProbeResult.Reachable, result)
        assertEquals(null, authorization)
    }

    @Test
    fun `no route on the host is not a Dive Hub instance`() = runTest {
        val repository = probeRepository { json("""{"message":"Not Found"}""", HttpStatusCode.NotFound) }

        assertEquals(InstanceProbeResult.Unreachable(ErrorCodes.NOT_DIVEHUB), probe(repository))
    }

    @Test
    fun `a page that is not a health answer is not a Dive Hub instance`() = runTest {
        val repository = probeRepository {
            respond(
                "<html><body>hello</body></html>",
                HttpStatusCode.OK,
                headersOf(HttpHeaders.ContentType, "text/html"),
            )
        }

        assertEquals(InstanceProbeResult.Unreachable(ErrorCodes.NOT_DIVEHUB), probe(repository))
    }

    @Test
    fun `an offline phone is no answer, not a wrong address`() = runTest {
        val repository = probeRepository { throw IOException("offline") }

        assertEquals(InstanceProbeResult.Unreachable(ErrorCodes.NO_ANSWER), probe(repository))
    }

    @Test
    fun `a slow instance is no answer after the probe timeout`() = runTest {
        val repository = probeRepository {
            delay(500)
            json("""{"status":"ok","version":"1.4.2"}""")
        }

        assertEquals(
            InstanceProbeResult.Unreachable(ErrorCodes.NO_ANSWER),
            withContext(Dispatchers.Default) { repository.probeInstance("dive.example", timeoutMillis = 50) },
        )
    }

    @Test
    fun `a client too old refusal keeps the update-required state`() = runTest {
        val repository = probeRepository {
            json("""{"code":"client_too_old","error":"Update required"}""", HttpStatusCode.BadRequest)
        }

        assertEquals(InstanceProbeResult.Unreachable(ErrorCodes.CLIENT_TOO_OLD), probe(repository))
        assertEquals(SessionState.UpdateRequired(null), repository.state.value)
    }

    @Test
    fun `an invalid address is refused before any request`() = runTest {
        val repository = probeRepository { error("no request expected") }

        assertEquals(
            InstanceProbeResult.Unreachable(ErrorCodes.INVALID_INSTANCE_URL),
            probe(repository, "not an address"),
        )
    }
}
