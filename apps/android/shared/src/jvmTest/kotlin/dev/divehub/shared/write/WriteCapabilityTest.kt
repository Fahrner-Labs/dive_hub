package dev.divehub.shared.write

import dev.divehub.shared.ApiClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WriteCapabilityTest {
    private fun api(status: HttpStatusCode): ApiClient = ApiClient(
        engine = MockEngine { respond("{}", status, headersOf("Content-Type", "application/json")) },
        versionName = "0.1.0-dev",
        token = { "token" },
        onUnauthorized = {},
        json = Json { ignoreUnknownKeys = true },
    ).apply { baseUrl = "https://dive.example" }

    @Test
    fun `a feed answer means the instance is writeable`() = runTest {
        val capability = WriteCapability(api(HttpStatusCode.OK))

        capability.probe()

        assertEquals(true, capability.writeable.value)
    }

    @Test
    fun `a missing feed means a read-only instance`() = runTest {
        val capability = WriteCapability(api(HttpStatusCode.NotFound))

        capability.probe()

        assertEquals(false, capability.writeable.value)
    }

    @Test
    fun `no answer leaves the capability unknown`() = runTest {
        val api = ApiClient(
            engine = MockEngine { throw IOException("offline") },
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true },
        ).apply { baseUrl = "https://dive.example" }
        val capability = WriteCapability(api)

        capability.probe()

        assertNull(capability.writeable.value)
    }

    @Test
    fun `a server error is not evidence of a read-only instance`() = runTest {
        val capability = WriteCapability(api(HttpStatusCode.InternalServerError))

        capability.probe()

        assertNull(capability.writeable.value)
    }

    @Test
    fun `signing out forgets a probed answer`() = runTest {
        val capability = WriteCapability(api(HttpStatusCode.OK))
        capability.probe()

        capability.reset()

        assertNull(capability.writeable.value)
    }
}
