package dev.divehub.android.ui.settings

import dev.divehub.shared.InstanceUrlStore
import dev.divehub.shared.diagnostics.DiagnosticEntry
import dev.divehub.shared.diagnostics.DiagnosticsAccess
import dev.divehub.shared.diagnostics.ReportProblemResult

/** One test double for FAH-321's capture facade; the real buffer and upload are covered in `:shared`. */
class FakeDiagnostics(
    val enabled: Boolean = true,
    val entries: MutableList<DiagnosticEntry> = mutableListOf(),
    var result: ReportProblemResult = ReportProblemResult.Sent,
) : DiagnosticsAccess {
    var reports = 0
    var statusReads = 0

    override suspend fun instanceEnabled(): Boolean {
        statusReads += 1
        return enabled
    }

    override fun count(): Int = entries.size

    override fun preview(limit: Int): List<DiagnosticEntry> = entries.asReversed().take(limit)

    override fun exportNdjson(): String = entries.joinToString("") { entry ->
        """{"code":"${entry.code}"}""" + "\n"
    }

    override suspend fun reportProblem(): ReportProblemResult {
        reports += 1
        if (result == ReportProblemResult.Sent) entries.clear()
        return result
    }
}

/** The stored instance address the settings repository reads. */
class FakeInstances(private var value: String? = "https://dive.example") : InstanceUrlStore {
    override suspend fun load(): String? = value

    override suspend fun save(url: String) {
        value = url
    }

    override suspend fun clear() {
        value = null
    }
}
