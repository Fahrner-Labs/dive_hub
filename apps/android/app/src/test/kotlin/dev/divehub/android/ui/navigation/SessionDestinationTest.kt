package dev.divehub.android.ui.navigation

import dev.divehub.android.ui.Reachability
import dev.divehub.android.ui.UiState
import dev.divehub.api.models.UserView
import dev.divehub.shared.ErrorCodes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** Top-level selection stays driven by [UiState] (ADR 0079): Navigation renders the session, it does not own it. */
class SessionDestinationTest {
    @Test
    fun `loading keeps the graph in place`() {
        assertNull(sessionDestination(UiState(loading = true)))
    }

    @Test
    fun `an outdated app is gated above the graph`() {
        assertNull(sessionDestination(UiState(loading = false, instanceUrl = "https://dive.example", updateRequired = true)))
    }

    @Test
    fun `no instance yet opens the instance form`() {
        assertEquals(InstanceRoute, sessionDestination(UiState(loading = false)))
    }

    @Test
    fun `an instance that did not answer never opens sign-in`() {
        val state = UiState(loading = false, reachability = Reachability.Unreachable(ErrorCodes.NO_ANSWER))
        assertEquals(InstanceRoute, sessionDestination(state))
    }

    @Test
    fun `an instance without a session opens sign-in`() {
        assertEquals(SignInRoute, sessionDestination(UiState(loading = false, instanceUrl = "https://dive.example")))
    }

    @Test
    fun `a signed-in user opens the main graph`() {
        val state = UiState(
            loading = false,
            instanceUrl = "https://dive.example",
            user = UserView(
                id = "user-1",
                email = "elena@example.com",
                name = "Elena",
                role = "user",
                disabled = false,
                createdAt = "2026-11-20T10:00:00Z",
            ),
        )
        assertEquals(MainRoute, sessionDestination(state))
    }
}
