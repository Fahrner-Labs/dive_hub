package dev.divehub.android.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.divehub.android.AppContainer
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWriteStatus
import dev.divehub.shared.store.ScanResult
import dev.divehub.shared.write.WriteQueue
import dev.divehub.shared.write.WriteReplayResult
import dev.divehub.shared.write.WriteReplayer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** What the scanner screen draws; [error] is a code the screen translates, never the server's English text. */
data class ScanUiState(
    val pending: Int = 0,
    val busy: Boolean = false,
    val results: List<ScanResult> = emptyList(),
    val error: String? = null,
)

/** An error the screen itself reports, beside the server's problem codes. */
object ScanErrors {
    const val NO_CODE_IN_PICTURE = "no_code_in_picture"
    const val NO_CAMERA = "no_camera"
}

/**
 * The scanner screen's state (T11): a scanned code becomes a queued `action`, so it is read when the server answers,
 * and every write it leads to (taking a code, creating a centre) rides the same queue. Nothing here parses a code's
 * format — that is the server's.
 */
class ScanViewModel(
    private val store: DiveStore,
    private val queue: WriteQueue,
    private val replayer: WriteReplayer,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _state = MutableStateFlow(ScanUiState())
    val state: StateFlow<ScanUiState> = _state.asStateFlow()

    private var started: Job? = null

    /** Re-read the queue and the resolved scans; the screen calls it once it is shown. */
    fun start() {
        if (started?.isActive == true) return
        started = viewModelScope.launch(ioDispatcher) { reload() }
    }

    /** Queue a raw code and try to resolve it now; with no answer it waits in the queue. */
    fun scan(text: String) {
        val code = text.trim()
        if (code.isEmpty()) return
        viewModelScope.launch(ioDispatcher) {
            queue.enqueueScan(code)
            replayNow()
        }
    }

    /**
     * Take a person's code for [diverId], or create a new external Diver when [create]. The raw text comes from the
     * queued read the result points back at; taking it consumes that read.
     */
    fun take(result: ScanResult, diverId: String?, create: Boolean) {
        val action = result.actionId ?: return
        val text = queue.pendingScanText(action) ?: return
        viewModelScope.launch(ioDispatcher) {
            queue.enqueueTakeCode(text, diverId = diverId, create = create)
            store.transaction {
                deletePendingWrite(action)
                deleteScanResult(result.id)
            }
            replayNow()
        }
    }

    /** Create the Dive centre a read found, with the client uuid the scan kept as its replay key. */
    fun createCentre(result: ScanResult, read: ReadCode) {
        val id = result.entityId ?: return
        val name = read.name ?: return
        val action = result.actionId ?: return
        viewModelScope.launch(ioDispatcher) {
            queue.enqueueCreateCentre(id = id, name = name, number = read.centreNumber)
            store.transaction {
                deletePendingWrite(action)
                deleteScanResult(result.id)
            }
            replayNow()
        }
    }

    /** Dismissing a read drops its queued text with it; a finished outcome has no action left. */
    fun dismiss(result: ScanResult) {
        viewModelScope.launch(ioDispatcher) {
            store.transaction {
                result.actionId?.let { deletePendingWrite(it) }
                deleteScanResult(result.id)
            }
            reload()
        }
    }

    fun syncNow() {
        viewModelScope.launch(ioDispatcher) { replayNow() }
    }

    fun pictureWithoutCode() = _state.update { it.copy(error = ScanErrors.NO_CODE_IN_PICTURE) }

    fun noCamera() = _state.update { it.copy(error = ScanErrors.NO_CAMERA) }

    fun clearError() = _state.update { it.copy(error = null) }

    /** Sign-out wipes the User's data; the screen must not keep drawing it (client contract §1 Security). */
    fun forget() {
        started?.cancel()
        started = null
        _state.value = ScanUiState()
    }

    private suspend fun replayNow() {
        _state.update { it.copy(busy = true, error = null) }
        val outcome = try {
            replayer.replay()
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            WriteReplayResult.Stopped(error)
        }
        val error = when (outcome) {
            is WriteReplayResult.Stopped ->
                if (outcome.error is NoAnswerException) ErrorCodes.NO_ANSWER else ErrorCodes.SERVER_ERROR
            else -> null
        }
        _state.update { it.copy(busy = false, error = error) }
        reload()
    }

    private fun reload() {
        _state.update {
            it.copy(
                pending = store.pendingWriteCount(PendingWriteStatus.Pending).toInt(),
                results = store.scanResults(),
            )
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ScanViewModel(store = container.diveStore, queue = container.writeQueue, replayer = container.writeReplayer)
            }
        }
    }
}
