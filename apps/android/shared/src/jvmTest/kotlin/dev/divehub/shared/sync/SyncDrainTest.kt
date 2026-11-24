package dev.divehub.shared.sync

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.divehub.shared.ApiClient
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.write.WriteDrain
import dev.divehub.shared.write.WriteReplayResult
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

/** The queue drain on the sync triggers (ADR 0084 D3): a replay that changed the store bumps the screens' revision. */
class SyncDrainTest {
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val store: DiveStore = DiveStore.create(driver)

    @AfterTest
    fun close() = driver.close()

    private fun refresher(drain: WriteDrain): SyncRefresher {
        val api = ApiClient(
            engine = MockEngine { error("the drain test makes no request") },
            versionName = "0.1.0-dev",
            token = { "token" },
            onUnauthorized = {},
            json = Json { ignoreUnknownKeys = true; explicitNulls = false },
        ).apply { baseUrl = "https://dive.example" }
        return SyncRefresher(SyncEngine(api, store), live = null, writes = drain)
    }

    @Test
    fun `draining applied writes bumps the revision`() = runTest {
        val drain = object : WriteDrain {
            override suspend fun drain(): WriteReplayResult =
                WriteReplayResult.Drained(applied = 1, conflicted = 0, dropped = 0, failed = 0, notices = emptyList())
        }
        val refresher = refresher(drain)

        refresher.drainWritesNow()

        assertEquals(1L, refresher.revision.value)
    }

    @Test
    fun `an empty queue leaves the revision alone`() = runTest {
        val refresher = refresher(object : WriteDrain {
            override suspend fun drain(): WriteReplayResult = WriteReplayResult.Empty
        })

        refresher.drainWritesNow()

        assertEquals(0L, refresher.revision.value)
    }

    @Test
    fun `a parked conflict bumps the revision so the decision surface can show`() = runTest {
        val refresher = refresher(object : WriteDrain {
            override suspend fun drain(): WriteReplayResult =
                WriteReplayResult.Drained(applied = 0, conflicted = 1, dropped = 0, failed = 0, notices = emptyList())
        })

        refresher.drainWritesNow()

        assertEquals(1L, refresher.revision.value)
    }
}
