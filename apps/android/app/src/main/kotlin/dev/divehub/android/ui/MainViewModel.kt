package dev.divehub.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.api.models.UserView
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.InstanceProbeResult
import dev.divehub.shared.InstanceUrl
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.PendingWritesException
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.SessionState
import dev.divehub.shared.SignOutOutcome
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** How onboarding last found the instance it is about to ask credentials for (FAH-332). */
sealed interface Reachability {
    data object Unknown : Reachability
    data object Checking : Reachability
    data object Reachable : Reachability
    /** [code] is a translated [ErrorCodes] value, never the server's English text. */
    data class Unreachable(val code: String) : Reachability
}

/** What the one screen shows; `error` is a code the UI translates, never the server's English text. */
data class UiState(
    val loading: Boolean = true,
    val busy: Boolean = false,
    val instanceUrl: String? = null,
    val user: UserView? = null,
    /** "metric", "imperial" or null: the User's choice, else the device's region. */
    val units: String? = null,
    val error: String? = null,
    /** The onboarding probe's last answer; [Reachability.Unreachable] carries what the instance form or chip shows. */
    val reachability: Reachability = Reachability.Unknown,
    /** The server refuses this app's release (ADR 0076): a terminal screen, never the sign-in form. */
    val updateRequired: Boolean = false,
    /** A sign-out, change of instance or sign-in that would discard queued writes waits for an explicit decision. */
    val pendingWarning: PendingChangeWarning? = null,
)

class MainViewModel(
    private val session: SessionRepository,
    private val startSync: () -> Unit,
    private val stopSync: () -> Unit,
    private val drainWrites: suspend () -> Unit = {},
    private val probeInstance: suspend (String) -> InstanceProbeResult = session::probeInstance,
) : ViewModel() {
    constructor(container: AppContainer) : this(
        container.session,
        container::startSync,
        container::stopSync,
        container::drainWrites,
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    private var probe: Job? = null
    /** A sign-in refused for another account's queued writes is retried with these after *Discard and sign in*. */
    private var pendingSignIn: Pair<String, String>? = null
    /** Bumped by every check and by leaving the address, so a late answer never revives a superseded one. */
    private var probeGeneration = 0

    init {
        // A 401 anywhere ends the session in the repository; the screen follows it without a second source of truth.
        viewModelScope.launch {
            session.state.collect { state ->
                if (state is SessionState.SignedIn) {
                    cancelProbe()
                    startSync()
                } else {
                    stopSync()
                }
                _state.update {
                    when (state) {
                        is SessionState.SignedIn -> it.copy(loading = false, instanceUrl = state.instanceUrl, user = state.user, units = state.preferences.units, updateRequired = false, pendingWarning = null)
                        is SessionState.SignedOut -> it.copy(loading = false, instanceUrl = state.instanceUrl, user = null, units = null, updateRequired = false, pendingWarning = null)
                        is SessionState.UpdateRequired -> it.copy(loading = false, instanceUrl = state.instanceUrl, user = null, units = null, updateRequired = true, pendingWarning = null)
                        SessionState.Loading -> it
                    }
                }
            }
        }
        viewModelScope.launch { restore() }
    }

    /** Probe the address first; only an instance that answers lets the shell move on to sign-in (FAH-332). */
    fun useInstance(raw: String) {
        val url = InstanceUrl.normalize(raw)
        if (url == null) {
            cancelProbe()
            _state.update { it.copy(reachability = Reachability.Unreachable(ErrorCodes.INVALID_INSTANCE_URL)) }
            return
        }
        probeAddress(url)
    }

    /** The sign-in screen's instance line checks again whenever the screen is shown. */
    fun checkInstance() {
        _state.value.instanceUrl?.let(::probeAddress)
    }

    private fun probeAddress(url: String) {
        val generation = ++probeGeneration
        probe?.cancel()
        _state.update { it.copy(reachability = Reachability.Checking) }
        probe = viewModelScope.launch {
            val result = probeInstance(url)
            if (generation != probeGeneration) return@launch
            _state.update { current ->
                // A newer address or "Change instance" won the race; leave its state alone.
                if (current.instanceUrl != null && current.instanceUrl != url) {
                    current
                } else {
                    when (result) {
                        is InstanceProbeResult.Reachable ->
                            current.copy(instanceUrl = url, reachability = Reachability.Reachable)
                        is InstanceProbeResult.Unreachable ->
                            current.copy(reachability = Reachability.Unreachable(result.code))
                    }
                }
            }
        }
    }

    /** The sign-in form's "Change instance": asks for a new address; there is no session to end there. */
    fun changeInstance() {
        cancelProbe()
        _state.update { it.copy(instanceUrl = null, error = null, reachability = Reachability.Unknown) }
    }

    private fun cancelProbe() {
        probeGeneration += 1
        probe?.cancel()
    }

    /** Settings' "Change instance": end the session, wipe its local data, forget the address, then ask for one. */
    fun signOutAndChangeInstance() {
        cancelProbe()
        viewModelScope.launch {
            _state.update { it.copy(busy = true, error = null) }
            when (val outcome = session.changeInstance()) {
                SignOutOutcome.SignedOut -> _state.update {
                    it.copy(
                        busy = false,
                        instanceUrl = null,
                        user = null,
                        units = null,
                        error = null,
                        reachability = Reachability.Unknown,
                        pendingWarning = null,
                    )
                }
                is SignOutOutcome.PendingChanges -> _state.update {
                    it.copy(
                        busy = false,
                        error = null,
                        pendingWarning = PendingChangeWarning.SignOut(outcome.count, changeInstance = true),
                    )
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        val url = _state.value.instanceUrl
        if (url == null) {
            _state.update { it.copy(error = ErrorCodes.INVALID_INSTANCE_URL) }
            return
        }
        signIn(url, email.trim(), password, discardPending = false)
    }

    private fun signIn(url: String, email: String, password: String, discardPending: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(busy = true, error = null) }
            try {
                session.signIn(url, email, password, discardPending = discardPending)
                pendingSignIn = null
                _state.update { it.copy(busy = false, error = null, pendingWarning = null) }
            } catch (error: PendingWritesException) {
                // The previous User's queued writes belong to them; signing in as another is an explicit decision (D8).
                pendingSignIn = email to password
                _state.update {
                    it.copy(busy = false, error = null, pendingWarning = PendingChangeWarning.SignIn(error.count, email))
                }
            } catch (error: DiveHubException) {
                pendingSignIn = null
                _state.update { it.copy(busy = false, error = error.code) }
            } catch (_: NoAnswerException) {
                pendingSignIn = null
                _state.update { it.copy(busy = false, error = NO_ANSWER) }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _state.update { it.copy(busy = true, error = null) }
            when (val outcome = session.signOut()) {
                SignOutOutcome.SignedOut -> _state.update { it.copy(busy = false, error = null, pendingWarning = null) }
                is SignOutOutcome.PendingChanges -> _state.update {
                    it.copy(
                        busy = false,
                        error = null,
                        pendingWarning = PendingChangeWarning.SignOut(outcome.count, changeInstance = false),
                    )
                }
            }
        }
    }

    /** *Sync now* on the sign-out warning: drain the queue and stay signed in; a sign-out is a fresh choice after. */
    fun syncPendingWarning() {
        val warning = _state.value.pendingWarning as? PendingChangeWarning.SignOut ?: return
        if (warning.syncing) return
        val syncing = warning.copy(syncing = true, stillWaiting = false)
        _state.update { it.copy(pendingWarning = syncing) }
        viewModelScope.launch {
            try {
                drainWrites()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                // The count below is the truth either way.
            }
            // Cancelled, dismissed or replaced while the replay ran: its answer must not revive a closed warning.
            if (_state.value.pendingWarning !== syncing) return@launch
            // A 401 during the replay already ended the session; the warning is moot then.
            if (_state.value.user == null) {
                _state.update { it.copy(pendingWarning = null) }
                return@launch
            }
            val remaining = try {
                session.pendingWriteCount()
            } catch (_: Exception) {
                warning.count
            }
            if (remaining > 0) {
                _state.update {
                    it.copy(
                        pendingWarning = syncing.copy(count = remaining, syncing = false, stillWaiting = true),
                    )
                }
                return@launch
            }
            // D8: *Sync now* stays signed in and drains. A sign-out the User still wants is a fresh, explicit tap;
            // the warning closes and the session (and the store) stay exactly as they were.
            _state.update { it.copy(pendingWarning = null) }
        }
    }

    /** *Discard and sign out* / *Discard and change instance*: the User accepted losing the queued writes (D8). */
    fun discardPendingAndLeave() {
        val warning = _state.value.pendingWarning as? PendingChangeWarning.SignOut ?: return
        viewModelScope.launch {
            _state.update { it.copy(busy = true) }
            if (warning.changeInstance) session.changeInstanceDiscardingPending() else session.signOutDiscardingPending()
            _state.update {
                if (warning.changeInstance) {
                    it.copy(
                        busy = false,
                        instanceUrl = null,
                        user = null,
                        units = null,
                        error = null,
                        reachability = Reachability.Unknown,
                        pendingWarning = null,
                    )
                } else {
                    it.copy(busy = false, error = null, pendingWarning = null)
                }
            }
        }
    }

    /** *Discard and sign in*: the other account accepts losing the previous User's queued writes (D8). */
    fun discardPendingAndSignIn() {
        val warning = _state.value.pendingWarning as? PendingChangeWarning.SignIn ?: return
        val credentials = pendingSignIn ?: return
        val url = _state.value.instanceUrl ?: return
        signIn(url, credentials.first, credentials.second, discardPending = true)
    }

    /** Close the warning without changing anything; the session and the queue stay exactly as they were. */
    fun cancelPendingWarning() {
        if (_state.value.pendingWarning is PendingChangeWarning.SignIn) pendingSignIn = null
        _state.update { it.copy(pendingWarning = null) }
    }

    private suspend fun restore() {
        try {
            session.restore()
        } catch (_: NoAnswerException) {
            _state.update { it.copy(error = NO_ANSWER) }
        } catch (error: DiveHubException) {
            _state.update { it.copy(error = error.code) }
        }
    }

    companion object {
        const val NO_ANSWER = "no_answer"

        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { MainViewModel(container) }
        }
    }
}
