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
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.SessionState
import dev.divehub.shared.SignOutOutcome
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
)

class MainViewModel(
    private val session: SessionRepository,
    private val startSync: () -> Unit,
    private val stopSync: () -> Unit,
    private val probeInstance: suspend (String) -> InstanceProbeResult = session::probeInstance,
) : ViewModel() {
    constructor(container: AppContainer) : this(container.session, container::startSync, container::stopSync)

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    private var probe: Job? = null
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
                        is SessionState.SignedIn -> it.copy(loading = false, instanceUrl = state.instanceUrl, user = state.user, units = state.preferences.units, updateRequired = false)
                        is SessionState.SignedOut -> it.copy(loading = false, instanceUrl = state.instanceUrl, user = null, units = null, updateRequired = false)
                        is SessionState.UpdateRequired -> it.copy(loading = false, instanceUrl = state.instanceUrl, user = null, units = null, updateRequired = true)
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
            val outcome = session.changeInstance()
            _state.update {
                if (outcome == SignOutOutcome.SignedOut) {
                    it.copy(busy = false, instanceUrl = null, user = null, units = null, error = null, reachability = Reachability.Unknown)
                } else {
                    // Queued writes block the switch until the User decides (the warning UI is T7).
                    it.copy(busy = false, error = null)
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
        viewModelScope.launch {
            _state.update { it.copy(busy = true, error = null) }
            try {
                session.signIn(url, email.trim(), password)
                _state.update { it.copy(busy = false, error = null) }
            } catch (error: DiveHubException) {
                _state.update { it.copy(busy = false, error = error.code) }
            } catch (_: NoAnswerException) {
                _state.update { it.copy(busy = false, error = NO_ANSWER) }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _state.update { it.copy(busy = true, error = null) }
            session.signOut()
            _state.update { it.copy(busy = false, error = null) }
        }
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
