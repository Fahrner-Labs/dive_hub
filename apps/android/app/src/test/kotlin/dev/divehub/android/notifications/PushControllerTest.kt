package dev.divehub.android.notifications

import android.Manifest
import android.app.Application
import android.content.Context
import dev.divehub.shared.CachedMeStore
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.TokenStore
import dev.divehub.shared.UserScopedStore
import dev.divehub.shared.diagnostics.DiagnosticCapture
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.DiagnosticLevel
import dev.divehub.shared.push.PushRegistration
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

/**
 * The push path's diagnostics and status (FAH-557): every step lands in the local buffer, the Debug status names the
 * registered device or the failing step, and no token value ever appears in either (ADR 0085, ADR 0080).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class PushControllerTest {
    private lateinit var context: Context
    private lateinit var preferences: PushPreferences

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        shadowOf(context as Application).grantPermissions(Manifest.permission.POST_NOTIFICATIONS)
        preferences = PushPreferences(context)
        preferences.clearConfig()
        preferences.clearRegistration()
    }

    @After
    fun tearDown() {
        preferences.clearConfig()
        preferences.clearRegistration()
    }

    @Test
    fun `a healthy setup logs every step and shows the registered device`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val controller = controller(diagnostics) { request ->
            when (request.url.encodedPath) {
                "/api/push/config" -> respondJson(CONFIG_JSON)
                "/api/devices" -> respondJson(DEVICE_JSON)
                else -> error("unexpected path ${request.url.encodedPath}")
            }
        }

        controller.configure(INSTANCE)
        controller.registerNow()

        assertEquals(PushState.Registered, controller.status.value.state)
        assertEquals("device-1", controller.status.value.deviceId)
        assertEquals("2026-11-28T10:00:00Z", controller.status.value.lastSeenAt)
        val codes = diagnostics.entries.mapNotNull(DiagnosticEntry::code)
        assertTrue(codes.containsAll(listOf(CONFIG_OK, FIREBASE_OK, TOKEN_OK, REGISTER_OK)))
        assertTrue(diagnostics.entries.none { it.message?.contains(TOKEN_VALUE) == true })
        assertTrue(diagnostics.entries.none { it.message?.contains("public-key") == true })
    }

    @Test
    fun `a failed config request shows the HTTP status and registers nothing`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val paths = mutableListOf<String>()
        val controller = controller(diagnostics) { request ->
            paths += request.url.encodedPath
            respond("", HttpStatusCode.InternalServerError)
        }

        controller.configure(INSTANCE)
        advanceUntilIdle()

        assertEquals(PushState.ConfigFailed, controller.status.value.state)
        assertEquals(ErrorCodes.SERVER_ERROR, controller.status.value.reason)
        assertEquals(listOf("/api/push/config"), paths)
        val entry = diagnostics.entries.single { it.code == CONFIG_FAILED }
        assertEquals("500", entry.context["status"])
        assertFalse(diagnostics.entries.any { it.code == REGISTER_OK })
    }

    @Test
    fun `a register attempt without a config keeps the config failure`() = runTest {
        val controller = controller(RecordingDiagnostics()) { request ->
            respond("", HttpStatusCode.InternalServerError)
        }

        controller.configure(INSTANCE)
        assertEquals(PushState.ConfigFailed, controller.status.value.state)

        controller.registerNow(TOKEN_VALUE)

        assertEquals(PushState.ConfigFailed, controller.status.value.state)
    }

    @Test
    fun `a token failure names the error class and message without a token`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val controller = controller(
            diagnostics,
            firebase = FakeFirebase(token = TokenResult.Failed("IllegalStateException", "API key not valid")),
        ) { request ->
            when (request.url.encodedPath) {
                "/api/push/config" -> respondJson(CONFIG_JSON)
                else -> error("unexpected path ${request.url.encodedPath}")
            }
        }

        controller.configure(INSTANCE)
        controller.registerNow()

        assertEquals(PushState.TokenFailed, controller.status.value.state)
        assertEquals("IllegalStateException", controller.status.value.reason)
        val entry = diagnostics.entries.single { it.code == TOKEN_FAILED }
        assertTrue(entry.message!!.contains("API key not valid"))
        assertEquals("IllegalStateException", entry.context["error"])
        assertFalse(diagnostics.entries.any { it.code == REGISTER_OK })
    }

    @Test
    fun `a token-shaped value in an error message is masked before the buffer keeps it`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val controller = controller(
            diagnostics,
            firebase = FakeFirebase(token = TokenResult.Failed("IllegalStateException", "rejected $TOKEN_VALUE")),
        ) { request ->
            when (request.url.encodedPath) {
                "/api/push/config" -> respondJson(CONFIG_JSON)
                else -> error("unexpected path ${request.url.encodedPath}")
            }
        }

        controller.configure(INSTANCE)
        controller.registerNow()

        val entry = diagnostics.entries.single { it.code == TOKEN_FAILED }
        val message = entry.message.orEmpty()
        assertFalse(message.contains(TOKEN_VALUE))
        assertTrue(message.contains("***"))
    }

    @Test
    fun `a refused registration logs the problem code and never the token`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val controller = controller(diagnostics) { request ->
            when (request.url.encodedPath) {
                "/api/push/config" -> respondJson(CONFIG_JSON)
                "/api/devices" -> respondJson("""{"code":"invalid_push_token","message":"refused"}""", HttpStatusCode.BadRequest)
                else -> error("unexpected path ${request.url.encodedPath}")
            }
        }

        controller.configure(INSTANCE)
        controller.registerNow()

        assertEquals(PushState.RegisterFailed, controller.status.value.state)
        assertEquals("invalid_push_token", controller.status.value.reason)
        val entry = diagnostics.entries.single { it.code == REGISTER_FAILED }
        assertEquals("400", entry.context["status"])
        assertTrue(diagnostics.entries.none { it.message?.contains(TOKEN_VALUE) == true })
    }

    @Test
    fun `a Firebase initialization failure is a status with the error class`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val controller = controller(
            diagnostics,
            firebase = FakeFirebase(start = FirebaseStart.Failed("IllegalArgumentException", "bad project")),
        ) { request ->
            when (request.url.encodedPath) {
                "/api/push/config" -> respondJson(CONFIG_JSON)
                else -> error("unexpected path ${request.url.encodedPath}")
            }
        }

        controller.configure(INSTANCE)

        assertEquals(PushState.InitFailed, controller.status.value.state)
        assertEquals("IllegalArgumentException", controller.status.value.reason)
        val entry = diagnostics.entries.single { it.code == "push_firebase_failed" }
        assertEquals("IllegalArgumentException", entry.context["error"])
    }

    @Test
    fun `a device stored from the last process starts as registered`() {
        preferences.saveDevice("device-9", "2026-11-27T10:00:00Z")
        val controller = controller(RecordingDiagnostics()) { error("no request expected") }

        assertEquals(PushState.Registered, controller.status.value.state)
        assertEquals("device-9", controller.status.value.deviceId)
        assertEquals("2026-11-27T10:00:00Z", controller.status.value.lastSeenAt)
    }

    @Test
    fun `registering without a session is a status, not an error entry`() = runTest {
        preferences.saveConfig(INSTANCE, config())
        val diagnostics = RecordingDiagnostics()
        val controller = controller(diagnostics, sessionToken = null) { error("no request expected") }

        controller.registerNow()

        assertEquals(PushState.NotSignedIn, controller.status.value.state)
        assertTrue(diagnostics.entries.isEmpty())
    }

    @Test
    fun `push off on the instance logs the empty config and stays out of Firebase`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val firebase = FakeFirebase()
        val controller = controller(diagnostics, firebase = firebase) {
            respondJson("{}")
        }

        controller.configure(INSTANCE)
        advanceUntilIdle()

        assertEquals(PushState.Off, controller.status.value.state)
        assertTrue(diagnostics.entries.any { it.code == CONFIG_OFF })
        assertEquals(0, firebase.initializations)
    }

    @Test
    fun `a token refresh is logged and re-registers without the value`() = runTest {
        preferences.saveConfig(INSTANCE, config())
        val diagnostics = RecordingDiagnostics()
        val controller = controller(diagnostics) { request ->
            when (request.url.encodedPath) {
                "/api/devices" -> respondJson(DEVICE_JSON)
                else -> error("unexpected path ${request.url.encodedPath}")
            }
        }

        controller.onNewToken(TOKEN_VALUE)
        controller.registerNow(TOKEN_VALUE)

        assertEquals(PushState.Registered, controller.status.value.state)
        assertEquals(TOKEN_VALUE, preferences.lastToken)
        assertTrue(diagnostics.entries.any { it.code == TOKEN_REFRESH })
        assertTrue(diagnostics.entries.any { it.code == REGISTER_OK })
        assertTrue(diagnostics.entries.none { it.message?.contains(TOKEN_VALUE) == true })
    }

    @Test
    fun `no Play services keeps its own reason instead of a generic token failure`() = runTest {
        val diagnostics = RecordingDiagnostics()
        val controller = controller(
            diagnostics,
            firebase = FakeFirebase(start = FirebaseStart.NoPlayServices, token = TokenResult.NotReady),
        ) { request ->
            when (request.url.encodedPath) {
                "/api/push/config" -> respondJson(CONFIG_JSON)
                else -> error("unexpected path ${request.url.encodedPath}")
            }
        }

        controller.configure(INSTANCE)
        controller.registerNow()

        assertEquals(PushState.NoPlayServices, controller.status.value.state)
        assertTrue(diagnostics.entries.any { it.code == "push_no_play_services" })
    }

    @Test
    fun `missing notifications permission is a status, not a registration attempt`() = runTest {
        shadowOf(context as Application).denyPermissions(Manifest.permission.POST_NOTIFICATIONS)
        preferences.saveConfig(INSTANCE, config())
        val diagnostics = RecordingDiagnostics()
        val paths = mutableListOf<String>()
        val controller = controller(diagnostics) { request ->
            paths += request.url.encodedPath
            respondJson(DEVICE_JSON)
        }

        controller.registerNow()

        assertEquals(PushState.PermissionMissing, controller.status.value.state)
        assertTrue(paths.isEmpty())
        assertTrue(diagnostics.entries.isEmpty())
    }

    private fun controller(
        diagnostics: DiagnosticCapture,
        firebase: FakeFirebase = FakeFirebase(),
        sessionToken: String? = "session-token",
        handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
    ): PushController {
        val session = SessionRepository(
            engine = MockEngine(handler),
            tokens = FakeTokenStore(sessionToken),
            instances = FakeInstanceStore(),
            userData = object : UserScopedStore {
                override suspend fun forget() = Unit
            },
            cachedMe = object : CachedMeStore {
                override suspend fun load(): String? = null
                override suspend fun save(meJson: String) = Unit
                override suspend fun clear() = Unit
            },
            versionName = "0.1.0-test",
        )
        return PushController(
            context = context,
            session = session,
            registration = PushRegistration(session.client),
            firebase = firebase,
            preferences = preferences,
            notifications = DiveNotifications(context),
            diagnostics = diagnostics,
            // An isolated scheduler keeps configure()'s fire-and-forget registration out of the way; each test asks
            // for registration explicitly so the assertions are deterministic.
            scope = CoroutineScope(StandardTestDispatcher()),
        )
    }

    private fun MockRequestHandleScope.respondJson(
        body: String,
        status: HttpStatusCode = HttpStatusCode.OK,
    ): HttpResponseData = respond(body, status, headersOf(HttpHeaders.ContentType, "application/json"))

    private fun config() = dev.divehub.api.models.ApiPushConfigGet200ResponseAndroid(
        projectId = "dive-hub-test",
        applicationId = "1:2:android:abc",
        apiKey = "public-key",
        gcmSenderId = "42",
    )

    private class RecordingDiagnostics : DiagnosticCapture {
        val entries = mutableListOf<DiagnosticEntry>()

        override fun record(
            level: DiagnosticLevel,
            code: String?,
            message: String?,
            context: Map<String, String>,
        ) {
            entries += DiagnosticEntry(
                occurredAt = "2026-11-28T12:00:00Z",
                level = level,
                code = code,
                message = message,
                context = context,
            )
        }
    }

    private class FakeFirebase(
        private val start: FirebaseStart = FirebaseStart.Ready,
        private val token: TokenResult = TokenResult.Acquired(TOKEN_VALUE),
    ) : PushFirebase {
        var initializations = 0

        override fun initialize(): FirebaseStart {
            initializations += 1
            return start
        }

        override suspend fun token(): TokenResult = token

        override fun reset() = Unit
    }

    private class FakeTokenStore(private var token: String?) : TokenStore {
        override suspend fun load(): String? = token
        override suspend fun save(token: String) {
            this.token = token
        }
        override suspend fun clear() {
            token = null
        }
    }

    private class FakeInstanceStore : InstanceUrlStore {
        override suspend fun load(): String = INSTANCE
        override suspend fun save(url: String) = Unit
        override suspend fun clear() = Unit
    }

    private companion object {
        const val INSTANCE = "https://dive.example"
        val TOKEN_VALUE = "fcm-secret-token-" + "a".repeat(80)
        const val CONFIG_JSON = """{"android":{"projectId":"dive-hub-test","applicationId":"1:2:android:abc","apiKey":"public-key","gcmSenderId":"42"}}"""
        const val DEVICE_JSON = """{"id":"device-1","platform":"android","createdAt":"2026-11-28T10:00:00Z","lastSeenAt":"2026-11-28T10:00:00Z"}"""
        const val CONFIG_OK = "push_config_ok"
        const val CONFIG_OFF = "push_config_off"
        const val CONFIG_FAILED = "push_config_failed"
        const val FIREBASE_OK = "push_firebase_ok"
        const val TOKEN_OK = "push_token_ok"
        const val TOKEN_FAILED = "push_token_failed"
        const val TOKEN_REFRESH = "push_token_refresh"
        const val REGISTER_OK = "push_register_ok"
        const val REGISTER_FAILED = "push_register_failed"
    }
}
