package dev.divehub.shared.sync

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.TokenStore
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveStore
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private const val ME = """{"user":{"id":"u1","email":"ada@example.com","name":"Ada","role":"user","disabled":false,"createdAt":"2026-01-01T00:00:00Z"},"ownDiver":{"id":"diver-1","name":"Ada"},"preferences":{"language":null,"units":null}}"""

private class FakeTokens(initial: String? = null) : TokenStore {
    var value: String? = initial
    override suspend fun load(): String? = value
    override suspend fun save(token: String) {
        value = token
    }

    override suspend fun clear() {
        value = null
    }
}

private class FakeInstances(initial: String? = "https://dive.example") : InstanceUrlStore {
    var value: String? = initial
    override suspend fun load(): String? = value
    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}

class SyncAuthTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)

    @AfterTest
    fun close() = driver.close()

    private fun seedStore() {
        store.upsertDive(Dive(id = "cached", diverId = "diver-1", startsAt = "2026-10-01T10:00:00Z"))
    }

    private fun session(tokens: FakeTokens) = SessionRepository(
        engine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/api/auth/sign-in/email" -> respond(
                    """{"redirect":false,"token":"body-token","user":{"id":"u1"}}""",
                    HttpStatusCode.OK,
                    headersOf(
                        HttpHeaders.ContentType to listOf("application/json"),
                        dev.divehub.shared.SET_AUTH_TOKEN to listOf("header-token"),
                    ),
                )
                "/api/me" -> respond(
                    ME,
                    HttpStatusCode.OK,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
                "/api/auth/sign-out" -> respond(
                    """{"success":true}""",
                    HttpStatusCode.OK,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
                else -> error("unexpected ${request.url.encodedPath}")
            }
        },
        tokens = tokens,
        instances = FakeInstances(),
        userData = store,
        versionName = "0.1.0-dev",
    )

    @Test
    fun `signing in wipes the previous User's store first`() = runTest {
        seedStore()

        session(FakeTokens()).signIn("https://dive.example", "ada@example.com", "correct horse battery")

        assertEquals(0, store.diveCount())
        assertNull(store.dive("cached"))
    }

    @Test
    fun `signing out wipes the store`() = runTest {
        seedStore()
        val tokens = FakeTokens("old-token")

        session(tokens).signOut()

        assertNull(tokens.value)
        assertEquals(0, store.diveCount())
    }

    @Test
    fun `a 401 keeps the store readable`() = runTest {
        seedStore()
        val tokens = FakeTokens("expired")
        val session = SessionRepository(
            engine = MockEngine {
                respond(
                    """{"code":"sign_in_required","error":"Sign in required"}""",
                    HttpStatusCode.Unauthorized,
                    headersOf(HttpHeaders.ContentType, "application/json"),
                )
            },
            tokens = tokens,
            instances = FakeInstances(),
            userData = store,
            versionName = "0.1.0-dev",
        )

        session.restore()

        assertNull(tokens.value)
        assertNotNull(store.dive("cached"))
    }
}
