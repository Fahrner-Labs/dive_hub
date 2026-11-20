package dev.divehub.shared.diagnostics

import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.NoAnswerException
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlin.time.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** What a *Report a problem* upload did: sent (buffer cleared) or refused by `code` (buffer kept). */
sealed interface ReportProblemResult {
    data object Sent : ReportProblemResult
    data class Failed(val code: String) : ReportProblemResult
}

/**
 * The Android side of client diagnostics (ADR 0080 D2/D10): capture into the local buffer, export that buffer, ask
 * whether the instance allows uploads, and upload on an explicit user action. There is no automatic egress.
 */
class Diagnostics(
    private val buffer: DiagnosticsBuffer,
    private val client: ApiClient,
    private val versionName: String,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    },
    private val clock: () -> Instant = { Clock.System.now() },
) {
    /** Record one entry locally. Best effort: a full or broken store must never break the app. */
    fun capture(
        level: DiagnosticLevel,
        code: String? = null,
        message: String? = null,
        stack: String? = null,
        context: Map<String, String> = emptyMap(),
    ) {
        buffer.capture(
            DiagnosticEntry(
                occurredAt = clock().toString(),
                level = level,
                code = code,
                message = message,
                stack = stack,
                context = context,
            ),
        )
    }

    fun count(): Int = buffer.count()

    /** The newest [limit] entries, newest first (the settings preview; T4 consumes it). */
    fun preview(limit: Int): List<DiagnosticEntry> = buffer.newest(limit)

    /** Every held entry as NDJSON, oldest first (the *Share local logs* accessor; T4 consumes it). */
    fun exportNdjson(): String = buffer.oldest().joinToString("") { entry ->
        json.encodeToString(entry.toPayload()) + "\n"
    }

    /** Whether the instance's admin has enabled client diagnostics (ADR 0080 D2). Any failure reads as off. */
    suspend fun instanceEnabled(): Boolean = try {
        val response = client.get(STATUS_PATH)
        response.status.isSuccess() && client.decode<StatusResponse>(response).enabled
    } catch (error: CancellationException) {
        throw error
    } catch (_: Exception) {
        false
    }

    /**
     * Upload the newest [MAX_UPLOAD] entries. A 204 means the server stored them, so the local buffer is cleared. A
     * 403/429, a 401 or no answer keeps the buffer and returns the code (ADR 0080 D1/D3).
     */
    suspend fun reportProblem(): ReportProblemResult {
        val entries = buffer.newest(MAX_UPLOAD).reversed()
        val body = json.encodeToString(
            Upload(
                client = ClientInfo(name = CLIENT_NAME, version = versionName),
                capturedAt = clock().toString(),
                entries = entries.map { it.toPayload() },
            ),
        )
        val response = try {
            client.post(CLIENT_DIAGNOSTICS_PATH, body)
        } catch (error: CancellationException) {
            throw error
        } catch (_: NoAnswerException) {
            return ReportProblemResult.Failed(ErrorCodes.NO_ANSWER)
        } catch (error: DiveHubException) {
            return ReportProblemResult.Failed(error.code)
        }
        if (response.status.isSuccess()) {
            buffer.clear()
            return ReportProblemResult.Sent
        }
        return ReportProblemResult.Failed(client.failure(response).code)
    }

    private fun DiagnosticEntry.toPayload(): DiagnosticEntryPayload = DiagnosticEntryPayload(
        occurredAt = occurredAt,
        level = level.wire,
        code = code,
        message = message.orEmpty().take(DiagnosticsBuffer.MESSAGE_LIMIT),
        stack = stack?.take(DiagnosticsBuffer.STACK_LIMIT),
        context = context.takeIf { it.isNotEmpty() },
    )

    companion object {
        /** The ingest contract's per-upload cap (ADR 0080 D3). */
        const val MAX_UPLOAD: Int = 50
        const val CLIENT_NAME: String = "android"

        const val STATUS_PATH: String = "/api/client-diagnostics/status"
        const val CLIENT_DIAGNOSTICS_PATH: String = "/api/client-diagnostics"
    }
}

@Serializable
private data class StatusResponse(val enabled: Boolean)

@Serializable
private data class ClientInfo(val name: String, val version: String)

@Serializable
private data class Upload(
    val client: ClientInfo,
    val capturedAt: String,
    val entries: List<DiagnosticEntryPayload>,
)

@Serializable
private data class DiagnosticEntryPayload(
    val occurredAt: String,
    val level: String,
    val code: String? = null,
    val message: String,
    val stack: String? = null,
    val context: Map<String, String>? = null,
)
