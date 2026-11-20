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
