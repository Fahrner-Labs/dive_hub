package dev.divehub.android.ui.scan

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWrite
import dev.divehub.shared.store.PendingWriteKind
import dev.divehub.shared.store.ScanResult
import dev.divehub.shared.store.ScanStatus
import dev.divehub.shared.write.ReadCodes
import dev.divehub.shared.write.WriteQueue
import dev.divehub.shared.write.WriteReplayer
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** The scanner's state holder (T11): a scan queues its raw text, and the write it leads to rides the same queue. */
@OptIn(ExperimentalCoroutinesApi::class)
class ScanViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var driver: JdbcSqliteDriver

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        Class.forName("org.sqlite.JDBC")
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    }

    @After
    fun tearDown() {
        driver.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `a scan with no answer keeps the raw text in the queue`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val viewModel = viewModel(store, offline = true)

        viewModel.scan("  SSI;buddy;ada  ")
        advanceUntilIdle()

        val queued = store.pendingWrites().single()
        assertEquals("scan", queued.entityType)
        assertEquals("""{"text":"  SSI;buddy;ada  "}""", queued.payloadJson)
    }

    @Test
    fun `start reads the waiting scans and their resolutions`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        WriteQueue(store, clock = { "2026-11-23T10:00:00Z" }).enqueueScan("SSI;buddy;ada")
        store.addScanResult(
            ScanResult(0, "2026-11-23T10:00:00Z", ScanStatus.Failed, errorCode = ErrorCodes.NO_ANSWER),
        )
        val viewModel = viewModel(store, offline = true)

        viewModel.start()
        advanceUntilIdle()

        assertEquals(1, viewModel.state.value.pending)
        assertEquals(1, viewModel.state.value.results.size)
    }

    @Test
    fun `a write waiting elsewhere is not counted as a scan waiting here`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        WriteQueue(store, clock = { "2026-11-23T10:00:00Z" }).enqueueScan("SSI;buddy;ada")
        store.addPendingWrite(
            PendingWrite(
                id = 0,
                kind = PendingWriteKind.Patch,
                entityType = "dive",
                entityId = "dive-1",
                payloadJson = """{"notes":"deeper"}""",
                enqueuedAt = "2026-11-23T10:00:00Z",
            ),
        )
        val viewModel = viewModel(store, offline = true)

        viewModel.start()
        advanceUntilIdle()

        assertEquals(1, viewModel.state.value.pending)
    }

    @Test
    fun `creating the centre a read found queues a create with the uuid the scan kept`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val queue = WriteQueue(store, clock = { "2026-11-23T10:00:00Z" })
        val scan = queue.enqueueScan("SSI;1;centre;12345;Reef Diving, Sharm", centreId = CENTRE_ID)
        val read = ScanResult(
            id = 1,
            at = "2026-11-23T10:00:00Z",
            status = ScanStatus.Read,
            kind = "centre",
            entityId = CENTRE_ID,
            detailJson = """{"kind":"centre","centreNumber":"12345","name":"Reef Diving, Sharm","existing":null}""",
            actionId = scan.id,
        )
        store.addScanResult(read)
        val viewModel = viewModel(store, offline = true)

        viewModel.createCentre(read, ReadCodes.parse(read)!!)
        advanceUntilIdle()

        val queued = store.pendingWrites().single()
        assertEquals("centre", queued.entityType)
        val payload = Json.parseToJsonElement(queued.payloadJson) as JsonObject
        assertEquals(CENTRE_ID, (payload["id"] as JsonPrimitive).content)
        assertEquals("Reef Diving, Sharm", (payload["name"] as JsonPrimitive).content)
        // The read it came from is gone with the write queued; its text was only ever in that action.
        assertTrue(store.scanResults().isEmpty())
    }

    @Test
    fun `taking a person's code queues it with the text of the read action`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val scan = WriteQueue(store, clock = { "2026-11-23T10:00:00Z" }).enqueueScan("SSI;buddy;ada")
        val read = ScanResult(
            id = 1,
            at = "2026-11-23T10:00:00Z",
            status = ScanStatus.Read,
            kind = "buddy",
            detailJson = "{}",
            actionId = scan.id,
        )
        store.addScanResult(read)
        val viewModel = viewModel(store, offline = true)

        viewModel.take(read, diverId = "diver-1", create = false)
        advanceUntilIdle()

        val queued = store.pendingWrites().single()
        assertEquals("take_code", queued.entityType)
        val payload = Json.parseToJsonElement(queued.payloadJson) as JsonObject
        assertEquals("SSI;buddy;ada", (payload["text"] as JsonPrimitive).content)
        assertEquals("diver-1", (payload["diverId"] as JsonPrimitive).content)
        assertTrue(store.scanResults().isEmpty())
    }

    @Test
    fun `dismissing a read drops its queued text with it`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val scan = WriteQueue(store, clock = { "2026-11-23T10:00:00Z" }).enqueueScan("SSI;buddy;ada")
        val read = ScanResult(
            id = 1,
            at = "2026-11-23T10:00:00Z",
            status = ScanStatus.Read,
            kind = "buddy",
            detailJson = "{}",
            actionId = scan.id,
        )
        store.addScanResult(read)
        val viewModel = viewModel(store, offline = true)

        viewModel.dismiss(read)
        advanceUntilIdle()

        assertTrue(store.scanResults().isEmpty())
        assertTrue(store.pendingWrites().isEmpty())
    }

    @Test
    fun `a reconnect drops the offline notice once the queued scan is read`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val revision = MutableStateFlow(0L)
        val syncOffline = MutableStateFlow(true)
        val viewModel = viewModel(store, offline = true, revision = revision, syncOffline = syncOffline)

        viewModel.start()
        viewModel.scan("SSI;buddy;ada")
        awaitState { viewModel.state.value.error == ErrorCodes.NO_ANSWER }
        assertEquals(1, viewModel.state.value.pending)

        // The last sync got an answer, but the drain has not yet read the scan: the notice still tells the truth.
        syncOffline.value = false
        revision.value = 1
        advanceTimeBy(300)
        advanceUntilIdle()
        assertEquals(ErrorCodes.NO_ANSWER, viewModel.state.value.error)

        // The drain applied the queued read; the fresh outcome and the empty queue reach the screen together.
        store.transaction {
            deletePendingWrite(pendingWrites().single().id)
            addScanResult(ScanResult(0, "2026-11-23T10:00:00Z", ScanStatus.Read, kind = "buddy"))
        }
        revision.value = 2
        advanceTimeBy(300)
        advanceUntilIdle()

        assertNull(viewModel.state.value.error)
        assertEquals(0, viewModel.state.value.pending)
        assertEquals(1, viewModel.state.value.results.size)
    }

    @Test
    fun `a sync revision bump rereads stale results`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val revision = MutableStateFlow(0L)
        val viewModel = viewModel(store, offline = false, revision = revision)

        viewModel.start()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.results.isEmpty())

        store.addScanResult(ScanResult(0, "2026-11-23T10:00:00Z", ScanStatus.Read, kind = "buddy"))
        revision.value = 1
        advanceTimeBy(300)
        advanceUntilIdle()

        assertEquals(1, viewModel.state.value.results.size)
    }

    @Test
    fun `a revision bump while the last sync got no answer keeps the offline notice`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val revision = MutableStateFlow(0L)
        val viewModel = viewModel(
            store,
            offline = true,
            revision = revision,
            syncOffline = MutableStateFlow(true),
        )

        viewModel.start()
        viewModel.scan("SSI;buddy;ada")
        awaitState { viewModel.state.value.error == ErrorCodes.NO_ANSWER }

        // The queue no longer waits, but the last sync still got no answer: the notice keeps telling the truth.
        store.transaction { deletePendingWrite(pendingWrites().single().id) }
        revision.value = 1
        advanceTimeBy(300)
        advanceUntilIdle()

        assertEquals(ErrorCodes.NO_ANSWER, viewModel.state.value.error)
        assertEquals(0, viewModel.state.value.pending)
    }

    @Test
    fun `a server error notice also clears once the queued scan drains`() = runTest(dispatcher) {
        val store = DiveStore.create(driver)
        val revision = MutableStateFlow(0L)
        val viewModel = viewModel(store, offline = false, serverError = true, revision = revision)

        viewModel.start()
        viewModel.scan("SSI;buddy;ada")
        awaitState { viewModel.state.value.error == ErrorCodes.SERVER_ERROR }
        assertEquals(1, viewModel.state.value.pending)

        // The same notice's other code: the screen words a 5xx as "no connection" too, so a later drain retires it.
        store.transaction {
            deletePendingWrite(pendingWrites().single().id)
            addScanResult(ScanResult(0, "2026-11-23T10:00:00Z", ScanStatus.Read, kind = "buddy"))
        }
        revision.value = 1
        advanceTimeBy(300)
        advanceUntilIdle()

        assertNull(viewModel.state.value.error)
        assertEquals(0, viewModel.state.value.pending)
    }

    private companion object {
        const val CENTRE_ID = "01930000-0000-7000-8000-000000000001"
    }

    /** The API runs off the test dispatcher; poll real time while pumping the test scheduler. */
    private fun TestScope.awaitState(predicate: () -> Boolean) {
        repeat(200) {
            advanceUntilIdle()
            if (predicate()) return
            Thread.sleep(10)
        }
        advanceUntilIdle()
        assertTrue(predicate())
    }

    private fun viewModel(
        store: DiveStore,
        offline: Boolean,
        serverError: Boolean = false,
        revision: MutableStateFlow<Long> = MutableStateFlow(0L),
        syncOffline: MutableStateFlow<Boolean> = MutableStateFlow(false),
    ): ScanViewModel {
        val engine = MockEngine {
            if (offline) throw IOException("offline")
            val status = if (serverError) HttpStatusCode.InternalServerError else HttpStatusCode.OK
            respond("{}", status, headersOf(HttpHeaders.ContentType, "application/json"))
        }
        val api = ApiClient(
            engine = engine,
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true; explicitNulls = false },
        ).apply { baseUrl = "https://dive.example" }
        return ScanViewModel(
            store = store,
            queue = WriteQueue(store, clock = { "2026-11-23T10:00:00Z" }),
            replayer = WriteReplayer(api, store, clock = { "2026-11-23T10:00:00Z" }),
            revision = revision,
            syncOffline = syncOffline,
            ioDispatcher = dispatcher,
        )
    }
}
