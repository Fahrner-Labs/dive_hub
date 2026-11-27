package dev.divehub.android.ui

import dev.divehub.shared.CachedMeStore
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.InstanceProbeResult
import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.TokenStore
import dev.divehub.shared.UserScopedStore
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private class FakeTokens : TokenStore {
    override suspend fun load(): String? = null
    override suspend fun save(token: String) = Unit
    override suspend fun clear() = Unit
}

private class FakeInstances(var value: String? = null) : InstanceUrlStore {
    override suspend fun load(): String? = value
    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}

private class FakeCache : UserScopedStore {
    override suspend fun forget() = Unit
}

private class FakeCachedMe : CachedMeStore {
    override suspend fun load(): String? = null
    override suspend fun save(meJson: String) = Unit
    override suspend fun clear() = Unit
}

/** Onboarding asks for credentials only after the instance answers (FAH-332). */
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `a reachable instance advances to sign-in`() = runTest(dispatcher) {
        var probed: String? = null
        val viewModel = viewModel { url ->
            probed = url
            InstanceProbeResult.Reachable
        }
        advanceUntilIdle()

        viewModel.useInstance("dive.example")

        assertEquals(Reachability.Checking, viewModel.state.value.reachability)
        assertNull(viewModel.state.value.instanceUrl)
        advanceUntilIdle()
        assertEquals("https://dive.example", probed)
        assertEquals("https://dive.example", viewModel.state.value.instanceUrl)
        assertEquals(Reachability.Reachable, viewModel.state.value.reachability)
    }

    @Test
    fun `an unreachable instance stays on the instance form`() = runTest(dispatcher) {
        val viewModel = viewModel { InstanceProbeResult.Unreachable(ErrorCodes.NO_ANSWER) }
        advanceUntilIdle()

        viewModel.useInstance("https://dive.example")
        advanceUntilIdle()

        assertNull(viewModel.state.value.instanceUrl)
        assertEquals(Reachability.Unreachable(ErrorCodes.NO_ANSWER), viewModel.state.value.reachability)
    }

    @Test
    fun `a host that is not a Dive Hub stays on the instance form`() = runTest(dispatcher) {
        val viewModel = viewModel { InstanceProbeResult.Unreachable(ErrorCodes.NOT_DIVEHUB) }
        advanceUntilIdle()

        viewModel.useInstance("https://example.com")
        advanceUntilIdle()

        assertNull(viewModel.state.value.instanceUrl)
        assertEquals(Reachability.Unreachable(ErrorCodes.NOT_DIVEHUB), viewModel.state.value.reachability)
    }

    @Test
    fun `an invalid address is refused without a probe`() = runTest(dispatcher) {
        var probes = 0
        val viewModel = viewModel {
            probes += 1
            InstanceProbeResult.Reachable
        }
        advanceUntilIdle()

        viewModel.useInstance("not an address")
        advanceUntilIdle()

        assertEquals(0, probes)
        assertNull(viewModel.state.value.instanceUrl)
        assertEquals(Reachability.Unreachable(ErrorCodes.INVALID_INSTANCE_URL), viewModel.state.value.reachability)
    }

    @Test
    fun `the sign-in screen rechecks the known instance`() = runTest(dispatcher) {
        var probes = 0
        val instances = FakeInstances("https://dive.example")
        val viewModel = viewModel(instances) {
            probes += 1
            InstanceProbeResult.Reachable
        }
        advanceUntilIdle()
        assertEquals("https://dive.example", viewModel.state.value.instanceUrl)

        viewModel.checkInstance()
        advanceUntilIdle()

        assertEquals(1, probes)
        assertEquals(Reachability.Reachable, viewModel.state.value.reachability)
    }

    @Test
    fun `a failed recheck keeps the address and the sign-in screen`() = runTest(dispatcher) {
        val instances = FakeInstances("https://dive.example")
        val viewModel = viewModel(instances) { InstanceProbeResult.Unreachable(ErrorCodes.NO_ANSWER) }
        advanceUntilIdle()

        viewModel.checkInstance()
        advanceUntilIdle()

        assertEquals("https://dive.example", viewModel.state.value.instanceUrl)
        assertEquals(Reachability.Unreachable(ErrorCodes.NO_ANSWER), viewModel.state.value.reachability)
    }

    @Test
    fun `a client too old answer is the probe's own state`() = runTest(dispatcher) {
        val viewModel = viewModel { InstanceProbeResult.Unreachable(ErrorCodes.CLIENT_TOO_OLD) }
        advanceUntilIdle()

        viewModel.useInstance("https://dive.example")
        advanceUntilIdle()

        assertNull(viewModel.state.value.instanceUrl)
        assertEquals(Reachability.Unreachable(ErrorCodes.CLIENT_TOO_OLD), viewModel.state.value.reachability)
    }

    @Test
    fun `a late answer cannot revive an address that was left`() = runTest(dispatcher) {
        val answer = CompletableDeferred<InstanceProbeResult>()
        val instances = FakeInstances("https://dive.example")
        val viewModel = viewModel(instances) { answer.await() }
        advanceUntilIdle()

        viewModel.checkInstance()
        viewModel.connectToSelfHosted()
        answer.complete(InstanceProbeResult.Reachable)
        advanceUntilIdle()

        assertEquals("https://dive.example", viewModel.state.value.instanceUrl)
        assertTrue(viewModel.state.value.showInstanceForm)
        assertEquals(Reachability.Unknown, viewModel.state.value.reachability)
    }

    @Test
    fun `the build default opens sign-in with no address typed`() = runTest(dispatcher) {
        val viewModel = viewModel(defaultInstanceUrl = "https://divehub.privatenode.de") {
            InstanceProbeResult.Reachable
        }
        advanceUntilIdle()

        assertEquals("https://divehub.privatenode.de", viewModel.state.value.instanceUrl)
        assertFalse(viewModel.state.value.showInstanceForm)
    }

    @Test
    fun `a stored instance beats the build default`() = runTest(dispatcher) {
        val instances = FakeInstances("https://dive.example")
        val viewModel = viewModel(instances, defaultInstanceUrl = "https://divehub.privatenode.de") {
            InstanceProbeResult.Reachable
        }
        advanceUntilIdle()

        assertEquals("https://dive.example", viewModel.state.value.instanceUrl)
    }

    @Test
    fun `the self-hosted action keeps the default for going back`() = runTest(dispatcher) {
        val viewModel = viewModel(defaultInstanceUrl = "https://divehub.privatenode.de") {
            InstanceProbeResult.Reachable
        }
        advanceUntilIdle()

        viewModel.connectToSelfHosted()

        assertTrue(viewModel.state.value.showInstanceForm)
        assertEquals("https://divehub.privatenode.de", viewModel.state.value.instanceUrl)

        viewModel.cancelInstanceForm()

        assertFalse(viewModel.state.value.showInstanceForm)
        assertEquals("https://divehub.privatenode.de", viewModel.state.value.instanceUrl)
    }

    @Test
    fun `a reachable self-hosted address replaces the default and closes the form`() = runTest(dispatcher) {
        val viewModel = viewModel(defaultInstanceUrl = "https://divehub.privatenode.de") {
            InstanceProbeResult.Reachable
        }
        advanceUntilIdle()

        viewModel.connectToSelfHosted()
        viewModel.useInstance("dive.example")
        advanceUntilIdle()

        assertEquals("https://dive.example", viewModel.state.value.instanceUrl)
        assertFalse(viewModel.state.value.showInstanceForm)
        assertEquals(Reachability.Reachable, viewModel.state.value.reachability)
    }

    private fun viewModel(
        instances: FakeInstances = FakeInstances(),
        defaultInstanceUrl: String? = null,
        probeInstance: suspend (String) -> InstanceProbeResult,
    ): MainViewModel {
        val session = SessionRepository(
            OkHttp.create(),
            FakeTokens(),
            instances,
            FakeCache(),
            FakeCachedMe(),
            versionName = "0.1.0-dev",
        )
        return MainViewModel(
            session,
            startSync = {},
            stopSync = {},
            probeInstance = probeInstance,
            defaultInstanceUrl = defaultInstanceUrl,
        )
    }
}
