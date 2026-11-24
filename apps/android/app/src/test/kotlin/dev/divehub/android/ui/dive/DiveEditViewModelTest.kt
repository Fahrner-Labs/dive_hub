package dev.divehub.android.ui.dive

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.dive.DiveValue
import dev.divehub.shared.logbook.StoreLogbook
import dev.divehub.shared.store.Dive
import dev.divehub.shared.store.DiveSite
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.Diver
import dev.divehub.shared.write.WriteQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** The editor's state holder: it reads the store, saves through the queue, and never sends a direct request. */
@OptIn(ExperimentalCoroutinesApi::class)
class DiveEditViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `opening reads the Dive and the cached sites from the store`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive())
            store.upsertDiveSite(DiveSite(id = "site-1", name = "Lighthouse Reef"))
            store.upsertDiver(diver("diver-2", "Bea"))
            val viewModel = viewModel(store)

            viewModel.open("dive-1")
            advanceUntilIdle()

            assertFalse(viewModel.state.value.loading)
            assertEquals("Lighthouse Reef", viewModel.state.value.originalSiteName)
            assertEquals(listOf("Lighthouse Reef"), viewModel.state.value.sites.map { it.name })
            assertEquals(listOf("Bea"), viewModel.state.value.divers.map { it.name })
            assertEquals("diver-1", viewModel.state.value.ownerDiverId)
            assertEquals("A good dive", viewModel.state.value.draft?.notes)
            assertEquals(42, viewModel.state.value.draft?.number)
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a Participants edit queues its own PUT and shows it at once`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive())
            store.upsertDiver(diver("diver-2", "Bea"))
            var reread = 0
            val viewModel = viewModel(store, onStoreWrite = { reread += 1 })
            viewModel.open("dive-1")
            advanceUntilIdle()

            viewModel.edit { it.addParticipant("diver-2", "Bea", "buddy") }
            assertEquals(listOf("Bea"), viewModel.state.value.draft?.participants?.map { it.name })
            viewModel.save()
            advanceUntilIdle()

            assertEquals(1, reread)
            assertTrue(viewModel.state.value.saved)
            val queued = store.pendingWrites().single()
            assertEquals("dive_participants", queued.entityType)
            assertTrue(queued.payloadJson.contains("diver-2"))
            assertFalse(queued.payloadJson.contains("Bea"))
            assertTrue(store.dive("dive-1")?.detailJson?.contains("Bea") == true)
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a roster edit queues against the roster and version the form opened with`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive())
            store.upsertDiver(diver("diver-2", "Bea"))
            val viewModel = viewModel(store)
            viewModel.open("dive-1")
            advanceUntilIdle()

            // A joint dive's union lands after the form opened (ADR 0082): the row moves to version 4 and holds Cleo.
            store.upsertDive(dive(participants = listOf("diver-3" to "Cleo")).copy(version = 4))
            viewModel.edit { it.addParticipant("diver-2", "Bea", "buddy") }
            viewModel.save()
            advanceUntilIdle()

            val queued = store.pendingWrites().single { it.entityType == "dive_participants" }
            // The base is the form's own roster and version, not the feed's: the merge can tell a feed removal or role
            // change from the User's edit (ADR 0084 D3).
            assertEquals(3L, queued.baseVersion)
            assertFalse(queued.baseJson!!.contains("diver-3"))
            // The person the feed added is still carried, so the PUT does not read as a removal.
            assertTrue(queued.payloadJson.contains("diver-2"))
            assertTrue(queued.payloadJson.contains("diver-3"))
        } finally {
            driver.close()
        }
    }

    @Test
    fun `one Save queues the scalars and the roster as two entries`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive())
            store.upsertDiver(diver("diver-2", "Bea"))
            val viewModel = viewModel(store)
            viewModel.open("dive-1")
            advanceUntilIdle()

            viewModel.edit { it.setNotes("Wreck log").addParticipant("diver-2", "Bea", "guide") }
            viewModel.save()
            advanceUntilIdle()

            assertEquals(
                listOf("dive", "dive_participants"),
                store.pendingWrites().map { it.entityType },
            )
        } finally {
            driver.close()
        }
    }

    @Test
    fun `saving queues one patch, shows it at once and asks screens to reread`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive())
            var reread = 0
            val viewModel = viewModel(store, onStoreWrite = { reread += 1 })
            viewModel.open("dive-1")
            advanceUntilIdle()

            viewModel.edit { it.setNotes("Wreck log") }
            viewModel.edit { it.setMaxDepthM(31.0) }
            viewModel.save()
            advanceUntilIdle()

            assertEquals(1, store.pendingWriteCount())
            assertEquals(1, reread)
            assertTrue(viewModel.state.value.saved)
            val queued = store.pendingWrites().single()
            assertTrue(queued.payloadJson.contains("Wreck log"))
            assertTrue(queued.payloadJson.contains("maxDepthM"))
            // "Write as if online": the store already shows the edit behind the editor.
            assertTrue(store.dive("dive-1")?.detailJson?.contains("Wreck log") == true)
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a draft with nothing changed saves without a queued write`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive())
            var reread = 0
            val viewModel = viewModel(store, onStoreWrite = { reread += 1 })
            viewModel.open("dive-1")
            advanceUntilIdle()

            viewModel.save()
            advanceUntilIdle()

            assertEquals(0, store.pendingWriteCount())
            assertEquals(0, reread)
            assertTrue(viewModel.state.value.saved)
        } finally {
            driver.close()
        }
    }

    @Test
    fun `a reset is queued as a reset, not as a set`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive(overrides = listOf("maxDepthM"), withRecording = true))
            val viewModel = viewModel(store)
            viewModel.open("dive-1")
            advanceUntilIdle()

            viewModel.edit { it.setMaxDepthM(31.0) }
            viewModel.edit { it.resetToRecording(DiveValue.MAX_DEPTH_M) }
            viewModel.save()
            advanceUntilIdle()

            val queued = store.pendingWrites().single()
            assertTrue(queued.payloadJson.contains("\"reset\":[\"maxDepthM\"]"))
            assertFalse(queued.payloadJson.contains("\"set\""))
        } finally {
            driver.close()
        }
    }

    @Test
    fun `the online state follows the sync`() = runTest(dispatcher) {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val store = DiveStore.create(driver)
        try {
            store.upsertDive(dive())
            val online = MutableStateFlow(true)
            val viewModel = viewModel(store, online = online)
            viewModel.open("dive-1")
            advanceUntilIdle()
            assertTrue(viewModel.state.value.online)

            online.value = false
            advanceUntilIdle()

            assertFalse(viewModel.state.value.online)
        } finally {
            driver.close()
        }
    }

    private fun viewModel(
        store: DiveStore,
        onStoreWrite: () -> Unit = {},
        online: MutableStateFlow<Boolean> = MutableStateFlow(true),
    ) = DiveEditViewModel(
        store = StoreLogbook(store),
        queue = WriteQueue(store),
        online = online,
        onStoreWrite = onStoreWrite,
        ioDispatcher = dispatcher,
    )

    private fun diver(id: String, name: String) = Diver(
        id = id,
        name = name,
        isOwn = false,
        external = false,
        apneaCounts = false,
        divesBefore = 0,
    )

    private fun dive(
        overrides: List<String> = emptyList(),
        withRecording: Boolean = true,
        participants: List<Pair<String, String>> = emptyList(),
    ): Dive {
        val detail: JsonObject = buildJsonObject {
                put("id", "dive-1")
                put("diverId", "diver-1")
                put("version", 3)
                put(
                    "values",
                    buildJsonObject {
                        put("number", 42)
                        put(
                            "startsAt",
                            buildJsonObject {
                                put("at", "2026-11-01T10:00:00Z")
                                put("utcOffsetSeconds", 3600)
                            },
                        )
                        put("durationSeconds", 3000.0)
                        put("maxDepthM", 24.3)
                        put("avgDepthM", 12.0)
                        put("waterTemperatureC", 18.0)
                    },
                )
                put("overrides", JsonArray(overrides.map(::JsonPrimitive)))
                put("utcOffsetSource", "device")
                put("fromProvider", JsonNull)
                if (withRecording) {
                    put(
                        "fromRecording",
                        buildJsonObject {
                            put(
                                "startsAt",
                                buildJsonObject {
                                    put("at", "2026-11-01T09:00:00Z")
                                    put("utcOffsetSeconds", 3600)
                                },
                            )
                            put("durationSeconds", 3000.0)
                            put("maxDepthM", 20.0)
                            put("avgDepthM", 12.0)
                            put("waterTemperatureC", 18.0)
                        },
                    )
                } else {
                    put("fromRecording", JsonNull)
                }
                put("notes", "A good dive")
                put(
                    "site",
                    buildJsonObject {
                        put("id", "site-1")
                        put("name", "Lighthouse Reef")
                    },
                )
                put("waterType", "salt")
                put("waterMismatch", JsonNull)
                put("position", JsonNull)
                put("recordings", JsonArray(emptyList()))
                put("cylinders", JsonArray(emptyList()))
                put("lead", JsonArray(emptyList()))
                put("leadTotalKg", JsonNull)
                put("weightingFeedback", JsonNull)
                put("suit", JsonNull)
                put("sac", JsonNull)
                put("sacMissing", JsonNull)
                put(
                    "participants",
                    JsonArray(
                        participants.map { (diverId, name) ->
                            buildJsonObject {
                                put("diverId", diverId)
                                put("name", name)
                                put("role", "buddy")
                            }
                        },
                    ),
                )
                put("verificationCodes", JsonArray(emptyList()))
            }
        return Dive(
            id = "dive-1",
            diverId = "diver-1",
            version = 3,
            startsAt = "2026-11-01T10:00:00Z",
            utcOffsetSeconds = 3600,
            utcOffsetSource = "device",
            durationSeconds = 3000,
            maxDepthM = 24.3,
            avgDepthM = 12.0,
            siteId = "site-1",
            notes = "A good dive",
            number = 42,
            detailJson = detail.toString(),
        )
    }
}
