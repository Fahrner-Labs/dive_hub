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
import dev.divehub.shared.InstanceUrl
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.SessionRepository
import dev.divehub.shared.SessionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** What the one screen shows; `error` is a code the UI translates, never the server's English text. */
data class UiState(
    val loading: Boolean = true,
    val busy: Boolean = false,
    val instanceUrl: String? = null,
    val user: UserView? = null,
    /** "metric", "imperial" or null: the User's choice, else the device's region. */
    val units: String? = null,
    val error: String? = null,
    /** The server refuses this app's release (ADR 0076): a terminal screen, never the sign-in form. */
    val updateRequired: Boolean = false,
)

class MainViewModel(private val container: AppContainer) : ViewModel() {
    private val session: SessionRepository = container.session
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        // A 401 anywhere ends the session in the repository; the screen follows it without a second source of truth.
        viewModelScope.launch {
            session.state.collect { state ->
                if (state is SessionState.SignedIn) container.startSync() else container.stopSync()
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

    fun useInstance(raw: String) {
        val url = InstanceUrl.normalize(raw)
        if (url == null) {
            _state.update { it.copy(error = ErrorCodes.INVALID_INSTANCE_URL) }
            return
        }
        _state.update { it.copy(instanceUrl = url, error = null) }
    }

    fun changeInstance() {
        _state.update { it.copy(instanceUrl = null, error = null) }
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
