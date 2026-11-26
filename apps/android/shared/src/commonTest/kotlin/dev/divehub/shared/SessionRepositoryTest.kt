package dev.divehub.shared

import dev.divehub.api.models.ApiMeGet200ResponsePreferences
import dev.divehub.api.models.UserView
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

private class FakeTokens(initial: String? = null) : TokenStore {
    var value: String? = initial
    var saves = 0
    var clears = 0

    override suspend fun load(): String? = value
    override suspend fun save(token: String) {
        value = token
        saves += 1
    }

    override suspend fun clear() {
        value = null
        clears += 1
    }
}

private class FakeInstances(initial: String? = null) : InstanceUrlStore {
    var value: String? = initial
    override suspend fun load(): String? = value
    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}

private class FakeCache : UserScopedStore {
    var forgets = 0
    override suspend fun forget() {
        forgets += 1
    }
}

private class FakeCachedMe(initial: String? = null) : CachedMeStore {
    var value: String? = initial
    var saves = 0
    var clears = 0

    override suspend fun load(): String? = value
    override suspend fun save(meJson: String) {
        value = meJson
        saves += 1
    }

    override suspend fun clear() {
        value = null
        clears += 1
    }
}

private class FakeQueue(var count: Long = 0) : PendingWriteQueue {
    override suspend fun queuedWriteCount(): Long = count
}

private const val USER_JSON = """{"id":"u1","name":"Ada","email":"ada@example.com","emailVerified":true,"createdAt":"2026-01-01T00:00:00Z","updatedAt":"2026-01-01T00:00:00Z","banned":false,"twoFactorEnabled":false}"""
private const val ME_JSON = """{"user":{"id":"u1","email":"ada@example.com","name":"Ada","role":"user","disabled":false,"createdAt":"2026-01-01T00:00:00Z"},"ownDiver":{"id":"d1","name":"Ada"},"preferences":{"language":null,"units":null}}"""
private const val ME_OTHER_USER_JSON = """{"user":{"id":"u2","email":"bob@example.com","name":"Bob","role":"user","disabled":false,"createdAt":"2026-01-01T00:00:00Z"},"ownDiver":{"id":"d2","name":"Bob"},"preferences":{"language":null,"units":null}}"""

private class Harness(
    val tokens: FakeTokens = FakeTokens(),
    val instances: FakeInstances = FakeInstances("https://dive.example"),
    val cache: FakeCache = FakeCache(),
    val cachedMe: FakeCachedMe = FakeCachedMe(),
    val pendingWrites: FakeQueue = FakeQueue(),
    val onSessionEnding: suspend () -> Unit = {},
    handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
) {
    private val engine = MockEngine(handler)
    val session = SessionRepository(
        engine,
        tokens,
        instances,
        cache,
        cachedMe,
        versionName = "0.1.0-dev",
        pendingWrites = pendingWrites,
        onSessionEnding = onSessionEnding,
    )
}

private fun MockRequestHandleScope.json(body: String, status: HttpStatusCode = HttpStatusCode.OK): HttpResponseData =
    respond(body, status, headersOf(HttpHeaders.ContentType, "application/json"))

private const val SIGN_IN_PATH = "/api/auth/sign-in/email"

class SessionRepositoryTest {

    @Test
    fun `hasSession needs both the instance URL and the token`() = runTest {
        val harness = Harness(tokens = FakeTokens(null), instances = FakeInstances("https://dive.example")) {
            error("no request expected")
        }

        assertFalse(harness.session.hasSession())

        harness.tokens.value = "token"
        assertTrue(harness.session.hasSession())

        harness.instances.value = null
        assertFalse(harness.session.hasSession())
    }

    @Test
    fun `signing in stores the token from set-auth-token and proves it with me`() = runTest {
    var meAuthorization: String? = null
    var signInAuthorization: String? = null
    val harness = Harness { request ->
        when (request.url.encodedPath) {
            SIGN_IN_PATH -> {
                signInAuthorization = request.headers[HttpHeaders.Authorization]
                assertEquals("""{"email":"ada@example.com","password":"correct horse battery"}""", (request.body as TextContent).text)
                respond(
                    """{"redirect":false,"token":"body-token","user":$USER_JSON}""",
                    headers = headersOf(
                        HttpHeaders.ContentType to listOf("application/json"),
                        SET_AUTH_TOKEN to listOf("header-token"),
                    ),
                )
            }
            "/api/me" -> {
                meAuthorization = request.headers[HttpHeaders.Authorization]
                json(ME_JSON)
            }
            else -> error("unexpected ${request.method} ${request.url}")
        }
    }

    val user = harness.session.signIn("https://dive.example", "ada@example.com", "correct horse battery")

    assertEquals("Ada", user.name)
    assertEquals("header-token", harness.tokens.value)
    assertNull(signInAuthorization)
    assertEquals("Bearer header-token", meAuthorization)
    assertTrue(harness.cache.forgets >= 1)
    assertEquals(1, harness.cachedMe.clears)
    assertEquals(1, harness.cachedMe.saves)
    assertEquals("https://dive.example", harness.instances.value)
    assertEquals(
        SessionState.SignedIn("https://dive.example", user, ApiMeGet200ResponsePreferences(language = null, units = null)),
        harness.session.state.value,
    )
}

@Test
fun `signing in forgets the previous User before the new token is stored`() = runTest {
    val events = mutableListOf<String>()
    val tokens = object : TokenStore {
        var value: String? = "old-token"
        override suspend fun load(): String? = value
        override suspend fun save(token: String) {
            events += "save"
            value = token
        }

        override suspend fun clear() {
            events += "clear-token"
            value = null
        }
    }
    val cache = object : UserScopedStore {
        override suspend fun forget() {
            events += "forget-cache"
        }
    }
    val engine = MockEngine { request ->
        when (request.url.encodedPath) {
            SIGN_IN_PATH -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "new-token"))
            "/api/me" -> json(ME_JSON)
            else -> error("unexpected ${request.url}")
        }
    }
    val session = SessionRepository(engine, tokens, FakeInstances("https://dive.example"), cache, FakeCachedMe(), versionName = "0.1.0-dev")

    session.signIn("https://dive.example", "ada@example.com", "correct horse battery")

    assertEquals(listOf("clear-token", "forget-cache", "save"), events)
}

@Test
fun `too many attempts is its own code`() = runTest {
    val harness = Harness { json("""{"code":"TOO_MANY_REQUESTS","message":"Too many"}""", HttpStatusCode.TooManyRequests) }

    val error = assertFailsWith<DiveHubException> {
        harness.session.signIn("https://dive.example", "ada@example.com", "wrong")
    }

    assertEquals(ErrorCodes.TOO_MANY_ATTEMPTS, error.code)
    assertNull(harness.tokens.value)
}

@Test
fun `a disabled account is its own code`() = runTest {
    val harness = Harness { json("""{"code":"BANNED_USER","message":"Banned"}""", HttpStatusCode.Forbidden) }

    val error = assertFailsWith<DiveHubException> {
        harness.session.signIn("https://dive.example", "ada@example.com", "correct horse battery")
    }

    assertEquals(ErrorCodes.USER_DISABLED, error.code)
}

@Test
fun `a wrong e-mail or password never says which one`() = runTest {
    val harness = Harness { json("""{"message":"Invalid email or password"}""", HttpStatusCode.Unauthorized) }

    val error = assertFailsWith<DiveHubException> {
        harness.session.signIn("https://dive.example", "ada@example.com", "wrong")
    }

    assertEquals(ErrorCodes.INVALID_CREDENTIALS, error.code)
}

@Test
fun `signing out clears the token and the cache but keeps the instance`() = runTest {
    val harness = Harness(tokens = FakeTokens("old-token"), cachedMe = FakeCachedMe(ME_JSON)) { _ -> json("""{"success":true}""") }

    harness.session.signOut()

    assertNull(harness.tokens.value)
    assertEquals(1, harness.cache.forgets)
    assertEquals(1, harness.cachedMe.clears)
    assertNull(harness.cachedMe.value)
    assertEquals("https://dive.example", harness.instances.value)
    assertIs<SessionState.SignedOut>(harness.session.state.value)
}

@Test
fun `signing out unregisters while the old token still works`() = runTest {
    val tokens = FakeTokens("old-token")
    val events = mutableListOf<String>()
    val harness = Harness(
        tokens = tokens,
        cachedMe = FakeCachedMe(ME_JSON),
        onSessionEnding = {
            events += "unregister"
            assertEquals("old-token", tokens.value)
        },
    ) { _ -> json("""{"success":true}""") }

    harness.session.signOut()

    assertEquals(listOf("unregister"), events)
    assertNull(tokens.value)
}

@Test
fun `a failed unregister never blocks the sign-out`() = runTest {
    val harness = Harness(
        tokens = FakeTokens("old-token"),
        cachedMe = FakeCachedMe(ME_JSON),
        onSessionEnding = { throw IOException("offline") },
    ) { _ -> json("""{"success":true}""") }

    harness.session.signOut()

    assertNull(harness.tokens.value)
    assertIs<SessionState.SignedOut>(harness.session.state.value)
}

@Test
fun `signing in again unregisters the previous session's row before clearing its token`() = runTest {
    val events = mutableListOf<String>()
    val tokens = object : TokenStore {
        var value: String? = "old-token"
        override suspend fun load(): String? = value
        override suspend fun save(token: String) { value = token }
        override suspend fun clear() {
            events += "clear"
            value = null
        }
    }
    val engine = MockEngine { request ->
        when (request.url.encodedPath) {
            SIGN_IN_PATH -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "new-token"))
            "/api/me" -> json(ME_JSON)
            else -> error("unexpected ${request.url}")
        }
    }
    val session = SessionRepository(
        engine,
        tokens,
        FakeInstances("https://dive.example"),
        FakeCache(),
        FakeCachedMe(),
        versionName = "0.1.0-dev",
        onSessionEnding = {
            events += "unregister"
            assertEquals("old-token", tokens.value)
        },
    )

    session.signIn("https://dive.example", "ada@example.com", "correct horse battery")

    assertEquals(listOf("unregister", "clear"), events)
}

@Test
fun `changing the instance signs out, wipes and forgets the address`() = runTest {
    val harness = Harness(tokens = FakeTokens("old-token"), cachedMe = FakeCachedMe(ME_JSON)) { _ -> json("""{"success":true}""") }

    harness.session.changeInstance()

    assertNull(harness.tokens.value)
    assertEquals(1, harness.cache.forgets)
    assertEquals(1, harness.cachedMe.clears)
    assertNull(harness.cachedMe.value)
    assertNull(harness.instances.value)
    val state = harness.session.state.value
    assertIs<SessionState.SignedOut>(state)
    assertNull(state.instanceUrl)
}

@Test
fun `a 401 anywhere ends the session and keeps the cached store`() = runTest {
    val harness = Harness(tokens = FakeTokens("expired"), cachedMe = FakeCachedMe(ME_JSON)) { request ->
        when (request.url.encodedPath) {
            "/api/me" -> json("""{"code":"sign_in_required","error":"Sign in required"}""", HttpStatusCode.Unauthorized)
            else -> error("unexpected ${request.url}")
        }
    }

    val state = harness.session.restore()

    assertIs<SessionState.SignedOut>(state)
    assertNull(harness.tokens.value)
    assertEquals(0, harness.cache.forgets)
    assertEquals(0, harness.cachedMe.clears)
    assertEquals(ME_JSON, harness.cachedMe.value)
}

@Test
fun `a stored token is proven by me on start`() = runTest {
    val harness = Harness(tokens = FakeTokens("stored")) { json(ME_JSON) }

    val state = harness.session.restore()

    assertIs<SessionState.SignedIn>(state)
    assertEquals("Ada", state.user.name)
    assertEquals(1, harness.cachedMe.saves)
}

@Test
fun `without a stored token or instance the sign-in form shows and no request is made`() = runTest {
    var calls = 0
    val harness = Harness(instances = FakeInstances(null)) {
        calls += 1
        json(ME_JSON)
    }

    assertEquals(SessionState.SignedOut(null), harness.session.restore())
    assertEquals(0, calls)

    val withInstance = Harness(tokens = FakeTokens(null), instances = FakeInstances("https://dive.example")) {
        calls += 1
        json(ME_JSON)
    }
    assertEquals(SessionState.SignedOut("https://dive.example"), withInstance.session.restore())
    assertEquals(0, calls)
}

    @Test
    fun `signing in to the default instance stores it`() = runTest {
        val default = "https://cloud.divehub.example"
        val harness = Harness(tokens = FakeTokens(null), instances = FakeInstances(null)) { request ->
            when (request.url.encodedPath) {
                SIGN_IN_PATH -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "token"))
                "/api/me" -> json(ME_JSON)
                else -> error("unexpected ${request.method} ${request.url}")
            }
        }

        assertNull(harness.session.knownInstance())
        assertFalse(harness.session.hasSession())

        harness.session.signIn(default, "ada@example.com", "correct horse battery")

        assertEquals(default, harness.session.knownInstance())
        assertEquals(default, harness.instances.value)
    }

@Test
fun `a 5xx is retried once`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored")) {
        calls += 1
        if (calls == 1) json("""{"code":"internal_error","error":"boom"}""", HttpStatusCode.InternalServerError) else json(ME_JSON)
    }

    val state = harness.session.restore()

    assertIs<SessionState.SignedIn>(state)
    assertEquals(2, calls)
}

@Test
fun `a second 5xx fails by its code`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored")) {
        calls += 1
        json("""{"code":"internal_error","error":"boom"}""", HttpStatusCode.InternalServerError)
    }

    val error = assertFailsWith<DiveHubException> { harness.session.restore() }

    assertEquals(ErrorCodes.SERVER_ERROR, error.code)
    assertEquals(2, calls)
    assertEquals("stored", harness.tokens.value)
    assertIs<SessionState.SignedOut>(harness.session.state.value)
}

@Test
fun `a second 5xx restores the cached session instead of signing out`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored"), cachedMe = FakeCachedMe(ME_JSON)) {
        calls += 1
        json("""{"code":"internal_error","error":"boom"}""", HttpStatusCode.InternalServerError)
    }

    val state = harness.session.restore()

    assertIs<SessionState.SignedIn>(state)
    assertEquals("Ada", state.user.name)
    assertEquals(2, calls)
    assertEquals("stored", harness.tokens.value)
    assertIs<SessionState.SignedIn>(harness.session.state.value)
}

@Test
fun `a 4xx is not retried`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored")) {
        calls += 1
        json("""{"code":"dive_not_found","error":"No such dive"}""", HttpStatusCode.NotFound)
    }

    val error = assertFailsWith<DiveHubException> { harness.session.restore() }

    assertEquals("dive_not_found", error.code)
    assertEquals(1, calls)
}

@Test
fun `a network failure is retried once and then says there was no answer`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored")) {
        calls += 1
        throw IOException("offline")
    }

    assertFailsWith<NoAnswerException> { harness.session.restore() }
    assertEquals(2, calls)
    assertEquals("stored", harness.tokens.value)
    assertIs<SessionState.SignedOut>(harness.session.state.value)
    assertEquals(0, harness.cachedMe.clears)
}

@Test
fun `an offline cold start restores the cached session instead of signing out`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored"), cachedMe = FakeCachedMe(ME_JSON)) {
        calls += 1
        throw IOException("offline")
    }

    val state = harness.session.restore()

    assertIs<SessionState.SignedIn>(state)
    assertEquals("https://dive.example", state.instanceUrl)
    assertEquals("Ada", state.user.name)
    assertEquals("stored", harness.tokens.value)
    assertEquals(2, calls)
    assertEquals(0, harness.cachedMe.clears)
    assertIs<SessionState.SignedIn>(harness.session.state.value)
}

@Test
fun `a network failure followed by an answer succeeds`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored")) {
        calls += 1
        if (calls == 1) throw IOException("offline") else json(ME_JSON)
    }

    assertIs<SessionState.SignedIn>(harness.session.restore())
    assertEquals(2, calls)
}

@Test
fun `the instance URL takes a scheme and an origin only`() {
    assertEquals("https://dive.example", InstanceUrl.normalize("dive.example"))
    assertEquals("https://dive.example", InstanceUrl.normalize("  https://dive.example/  "))
    assertEquals("http://127.0.0.1:3000", InstanceUrl.normalize("http://127.0.0.1:3000"))
    assertNull(InstanceUrl.normalize(""))
    assertNull(InstanceUrl.normalize("ftp://dive.example"))
    assertNull(InstanceUrl.normalize("https://dive.example/api"))
    assertNull(InstanceUrl.normalize("https://user@dive.example"))
}

@Test
fun `an unusable instance URL is refused before any request`() = runTest {
    var calls = 0
    val harness = Harness { calls += 1; json(ME_JSON) }

    val error = assertFailsWith<DiveHubException> {
        harness.session.signIn("https://dive.example/path", "ada@example.com", "correct horse battery")
    }

    assertEquals(ErrorCodes.INVALID_INSTANCE_URL, error.code)
    assertEquals(0, calls)
}

@Test
fun `a sign-in without set-auth-token is refused, not stored`() = runTest {
    val harness = Harness { json("""{"redirect":false,"token":"body-only","user":$USER_JSON}""") }

    val error = assertFailsWith<DiveHubException> {
        harness.session.signIn("https://dive.example", "ada@example.com", "correct horse battery")
    }

    assertEquals(ErrorCodes.MISSING_TOKEN, error.code)
    assertNull(harness.tokens.value)
}

@Test
fun `client_too_old on restore is a terminal update state that keeps the token`() = runTest {
    var calls = 0
    val harness = Harness(tokens = FakeTokens("stored")) {
        calls += 1
        json("""{"code":"client_too_old","error":"Update required"}""", HttpStatusCode.BadRequest)
    }

    val state = harness.session.restore()

    assertIs<SessionState.UpdateRequired>(state)
    assertEquals("stored", harness.tokens.value)
    assertEquals(1, calls)
}

@Test
fun `client_too_old on sign-in is its own code, not a wrong password`() = runTest {
    var calls = 0
    val harness = Harness { request ->
        calls += 1
        when (request.url.encodedPath) {
            SIGN_IN_PATH -> json("""{"code":"client_too_old","error":"Update required"}""", HttpStatusCode.BadRequest)
            else -> error("unexpected ${request.url}")
        }
    }

    val error = assertFailsWith<DiveHubException> {
        harness.session.signIn("https://dive.example", "ada@example.com", "correct horse battery")
    }

    assertEquals(ErrorCodes.CLIENT_TOO_OLD, error.code)
    assertIs<SessionState.UpdateRequired>(harness.session.state.value)
    assertNull(harness.tokens.value)
    assertEquals(1, calls)
}

@Test
fun `the generated user model keeps an unknown role and a refusal keeps an unknown code`() {
    val json = Json { ignoreUnknownKeys = true }
    val user = json.decodeFromString<UserView>(
        """{"id":"u1","email":"a@example.com","name":"A","role":"future-role","disabled":false,"createdAt":"2026-01-01T00:00:00Z"}""",
    )
    assertEquals("future-role", user.role)

    val problem = json.decodeFromString<ApiErrorBody>(
        """{"code":"a_code_from_a_newer_server","error":"English text for logs"}""",
    )
    assertEquals("a_code_from_a_newer_server", problem.code)
}

@Test
fun `signing out with queued writes blocks and keeps the session`() = runTest {
    val harness = Harness(tokens = FakeTokens("old-token"), pendingWrites = FakeQueue(2)) {
        error("no request expected while the queue blocks sign-out")
    }

    val outcome = harness.session.signOut()

    assertEquals(SignOutOutcome.PendingChanges(2), outcome)
    assertEquals("old-token", harness.tokens.value)
    assertEquals(0, harness.cache.forgets)
    assertIs<SessionState.Loading>(harness.session.state.value)
}

@Test
fun `discarding queued writes signs out and wipes`() = runTest {
    val harness = Harness(
        tokens = FakeTokens("old-token"),
        cachedMe = FakeCachedMe(ME_JSON),
        pendingWrites = FakeQueue(2),
    ) { json("""{"success":true}""") }

    val outcome = harness.session.signOutDiscardingPending()

    assertEquals(SignOutOutcome.SignedOut, outcome)
    assertNull(harness.tokens.value)
    assertEquals(1, harness.cache.forgets)
    assertEquals(1, harness.cachedMe.clears)
    assertIs<SessionState.SignedOut>(harness.session.state.value)
}

@Test
fun `changing the instance with queued writes blocks and keeps the address`() = runTest {
    val harness = Harness(tokens = FakeTokens("old-token"), pendingWrites = FakeQueue(1)) {
        error("no request expected while the queue blocks the change")
    }

    val outcome = harness.session.changeInstance()

    assertEquals(SignOutOutcome.PendingChanges(1), outcome)
    assertEquals("https://dive.example", harness.instances.value)
    assertEquals("old-token", harness.tokens.value)
    assertEquals(0, harness.cache.forgets)
}

@Test
fun `the same user signing in after a 401 keeps the store and resumes the queue`() = runTest {
    val harness = Harness(
        tokens = FakeTokens(null),
        cachedMe = FakeCachedMe(ME_JSON),
        pendingWrites = FakeQueue(2),
    ) { request ->
        when (request.url.encodedPath) {
            SIGN_IN_PATH -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "new-token"))
            "/api/me" -> json(ME_JSON)
            else -> error("unexpected ${request.url}")
        }
    }

    val user = harness.session.signIn("https://dive.example", "ada@example.com", "correct horse battery")

    assertEquals("u1", user.id)
    assertEquals("new-token", harness.tokens.value)
    assertEquals(0, harness.cache.forgets)
    assertEquals(0, harness.cachedMe.clears)
    assertEquals(2L, harness.session.pendingWriteCount())
    assertIs<SessionState.SignedIn>(harness.session.state.value)
}

@Test
fun `signing in as another user with queued writes needs an explicit decision`() = runTest {
    val harness = Harness(
        tokens = FakeTokens(null),
        cachedMe = FakeCachedMe(ME_JSON),
        pendingWrites = FakeQueue(2),
    ) { request ->
        when (request.url.encodedPath) {
            SIGN_IN_PATH -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "other-token"))
            "/api/me" -> json(ME_OTHER_USER_JSON)
            else -> error("unexpected ${request.url}")
        }
    }

    val error = assertFailsWith<PendingWritesException> {
        harness.session.signIn("https://dive.example", "bob@example.com", "correct horse battery")
    }

    assertEquals(2, error.count)
    assertNull(harness.tokens.value)
    assertEquals(0, harness.cache.forgets)
    assertEquals(ME_JSON, harness.cachedMe.value)
    assertEquals(2L, harness.session.pendingWriteCount())
}

@Test
fun `discarding the previous user's queued writes completes the switch`() = runTest {
    val harness = Harness(
        tokens = FakeTokens(null),
        cachedMe = FakeCachedMe(ME_JSON),
        pendingWrites = FakeQueue(2),
    ) { request ->
        when (request.url.encodedPath) {
            SIGN_IN_PATH -> respond("", HttpStatusCode.OK, headersOf(SET_AUTH_TOKEN, "other-token"))
            "/api/me" -> json(ME_OTHER_USER_JSON)
            else -> error("unexpected ${request.url}")
        }
    }

    val user = harness.session.signIn(
        "https://dive.example",
        "bob@example.com",
        "correct horse battery",
        discardPending = true,
    )

    assertEquals("u2", user.id)
    assertEquals("other-token", harness.tokens.value)
    assertEquals(1, harness.cache.forgets)
    assertEquals(1, harness.cachedMe.clears)
    assertIs<SessionState.SignedIn>(harness.session.state.value)
}

@Test
fun `a 401 keeps the queued writes for a same-user resume`() = runTest {
    val harness = Harness(
        tokens = FakeTokens("expired"),
        cachedMe = FakeCachedMe(ME_JSON),
        pendingWrites = FakeQueue(3),
    ) { request ->
        when (request.url.encodedPath) {
            "/api/me" -> json("""{"code":"sign_in_required","error":"Sign in required"}""", HttpStatusCode.Unauthorized)
            else -> error("unexpected ${request.url}")
        }
    }

    harness.session.restore()

    assertEquals(3L, harness.session.pendingWriteCount())
    assertEquals(0, harness.cache.forgets)
    assertEquals(0, harness.cachedMe.clears)
}
}
