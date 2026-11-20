package dev.divehub.shared.diagnostics

import dev.divehub.api.models.ApiClientDiagnosticsPostRequest
import dev.divehub.api.models.ApiClientDiagnosticsPostRequestClient
import dev.divehub.api.models.ApiClientDiagnosticsPostRequestEntriesItem
import dev.divehub.api.models.ApiClientDiagnosticsStatusGet200Response
import dev.divehub.shared.ApiClient
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.NoAnswerException
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlin.time.Clock
import kotlinx.coroutines.CancellationException
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** What a *Report a problem* upload did: sent (buffer cleared) or refused by `code` (buffer kept). */
sealed interface ReportProblemResult {
    data object Sent : ReportProblemResult
    data class Failed(val code: String) : ReportProblemResult
}

/**
 * The Android side of client diagnostics (ADR 0080 D1/D2): capture into the local buffer, export that buffer, ask
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
) : DiagnosticsAccess {
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
                message = message?.let(DiagnosticHygiene::redact),
                stack = stack?.let(DiagnosticHygiene::redact),
                context = DiagnosticHygiene.context(context),
            ),
        )
    }

    override fun count(): Int = buffer.count()

    /** The newest [limit] entries, newest first (the settings preview; T4 consumes it). */
    override fun preview(limit: Int): List<DiagnosticEntry> = buffer.newest(limit)

    /** Every held entry as NDJSON, oldest first (the *Share local logs* accessor; T4 consumes it). */
    override fun exportNdjson(): String = buffer.oldest().joinToString("") { entry ->
        json.encodeToString(entry.toPayload()) + "\n"
    }

    /** Whether the instance's admin has enabled client diagnostics (ADR 0080 D1). Any failure reads as off. */
    override suspend fun instanceEnabled(): Boolean = try {
        val response = client.get(STATUS_PATH)
        response.status.isSuccess() && client.decode<ApiClientDiagnosticsStatusGet200Response>(response).enabled
    } catch (error: CancellationException) {
        throw error
    } catch (_: Exception) {
        false
    }

    /**
     * Upload the newest [MAX_UPLOAD] entries. A 204 means the server stored them, so the local buffer is cleared. A
     * 403/429, a 401 or no answer keeps the buffer and returns the code (ADR 0080 D1/D2).
     */
    override suspend fun reportProblem(): ReportProblemResult {
        val entries = buffer.newest(MAX_UPLOAD).reversed()
        val body = json.encodeToString(
            ApiClientDiagnosticsPostRequest(
                client = ApiClientDiagnosticsPostRequestClient(name = CLIENT_NAME, version = versionName),
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
        }
        if (response.status.value == HttpStatusCode.NoContent.value) {
            buffer.clear()
            return ReportProblemResult.Sent
        }
        return ReportProblemResult.Failed(client.failure(response).code)
    }

    private fun DiagnosticEntry.toPayload(): ApiClientDiagnosticsPostRequestEntriesItem =
        ApiClientDiagnosticsPostRequestEntriesItem(
            occurredAt = occurredAt,
            level = level.wire,
            code = code,
            message = message.orEmpty().take(DiagnosticsBuffer.MESSAGE_LIMIT),
            stack = stack?.take(DiagnosticsBuffer.STACK_LIMIT),
            context = context.takeIf { it.isNotEmpty() },
        )

    companion object {
        /** The ingest contract's per-upload cap (ADR 0080 D2). */
        const val MAX_UPLOAD: Int = 50
        const val CLIENT_NAME: String = "android"

        const val STATUS_PATH: String = "/api/client-diagnostics/status"
        const val CLIENT_DIAGNOSTICS_PATH: String = "/api/client-diagnostics"
    }
}

/** Client-side hygiene before anything reaches the local buffer or the share sheet (ADR 0080 D1/D2). */
internal object DiagnosticHygiene {
    private val queryInText = Regex("""((?:https?://|/|\.{1,2}/|[^\s?#/]+/)[^\s?#]*)\?[^\s#)\]]*""")
    private val queryInContext = Regex("""^([^\s?#]+)\?[^\s#]*""")
    private val secretKey = Regex("""(?i)token|secret|passphrase|password|credential|authorization|cookie|api[_-]?key""")

    /** Drop query strings from URLs: parameters can carry tokens or other people's data. */
    fun redact(text: String): String = queryInText.replace(text, "$1")

    /** Keep only context a crash report needs; a key that names a credential is never captured. */
    fun context(values: Map<String, String>): Map<String, String> = values
        .filterKeys { !secretKey.containsMatchIn(it) }
        .mapValues { (_, value) -> queryInContext.replace(redact(value), "$1") }
}
