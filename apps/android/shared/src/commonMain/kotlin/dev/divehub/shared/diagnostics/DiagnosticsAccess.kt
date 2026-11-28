package dev.divehub.shared.diagnostics

/**
 * The read and report surface the settings Debug group consumes (FAH-318). [Diagnostics] is the only implementation;
 * the port lives here so the UI depends on the capture contract instead of re-declaring it in `:app`.
 */
interface DiagnosticsAccess {
    suspend fun instanceEnabled(): Boolean
    fun count(): Int
    fun preview(limit: Int): List<DiagnosticEntry>
    fun exportNdjson(): String
    suspend fun reportProblem(): ReportProblemResult
}

/**
 * The capture side of the local diagnostics buffer (ADR 0080 D1), so app code outside the diagnostics facade can record
 * a failing path in the same NDJSON export. [Diagnostics] is the only implementation in the app.
 */
interface DiagnosticCapture {
    fun record(
        level: DiagnosticLevel,
        code: String? = null,
        message: String? = null,
        context: Map<String, String> = emptyMap(),
    )
}
