package dev.divehub.shared.diagnostics

import dev.divehub.shared.UserScopedStore
import dev.divehub.shared.store.db.DiagnosticEntry as DiagnosticEntryRow
import dev.divehub.shared.store.db.DiveHubDatabase
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** The levels the server's ingest contract accepts (ADR 0080 D3); it has no "info". */
enum class DiagnosticLevel(val wire: String) {
    ERROR("error"),
    WARN("warn"),
    ;

    companion object {
        fun fromWire(value: String): DiagnosticLevel = entries.firstOrNull { it.wire == value } ?: ERROR
    }
}

/** One entry as the app and the export see it. The buffer owns the arrival order, so it is not part of the model. */
data class DiagnosticEntry(
    val occurredAt: String,
    val level: DiagnosticLevel,
    val code: String? = null,
    val message: String? = null,
    val stack: String? = null,
    val context: Map<String, String> = emptyMap(),
)

/**
 * The always-on local diagnostics ring buffer (ADR 0080 D1): a SQLDelight table in the offline store, capped FIFO at
 * [CAP] entries, wiped when the signed-in User changes. Capture must never break the app, so a failed write is
 * swallowed; the log is a debugging aid, not a product feature.
 */
class DiagnosticsBuffer(private val database: DiveHubDatabase) : UserScopedStore {
    private val queries = database.diveHubQueries
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }

    /** Append one entry, then evict the oldest beyond [CAP]. Best effort: storage failure leaves the app running. */
    fun capture(entry: DiagnosticEntry) {
        runCatching {
            database.transaction {
                queries.insertDiagnosticEntry(
                    occurredAt = entry.occurredAt,
                    level = entry.level.wire,
                    code = entry.code,
                    message = entry.message?.take(MESSAGE_LIMIT),
                    stack = entry.stack?.take(STACK_LIMIT),
                    contextJson = entry.context.takeIf { it.isNotEmpty() }?.let { json.encodeToString(it) },
                )
                queries.trimDiagnosticEntries(CAP.toLong())
            }
        }
    }

    /** How many entries are held now. */
    fun count(): Int = runCatching { queries.countDiagnosticEntries().executeAsOne().toInt() }.getOrDefault(0)

    /** The newest [limit] entries, newest first: what the settings preview shows. */
    fun newest(limit: Int): List<DiagnosticEntry> {
        if (limit <= 0) return emptyList()
        return runCatching {
            queries.selectNewestDiagnosticEntries(limit.coerceAtMost(CAP).toLong())
                .executeAsList()
                .map { it.toModel() }
        }.getOrDefault(emptyList())
    }

    /** The oldest [limit] entries, oldest first: the order an NDJSON export and an upload use. */
    fun oldest(limit: Int = CAP): List<DiagnosticEntry> {
        if (limit <= 0) return emptyList()
        return runCatching {
            queries.selectOldestDiagnosticEntries(limit.coerceAtMost(CAP).toLong())
                .executeAsList()
                .map { it.toModel() }
        }.getOrDefault(emptyList())
    }

    /** Drop every entry best-effort: the upload's success path must not fail because the clear did. */
    fun clear() {
        runCatching { queries.deleteAllDiagnosticEntries() }
    }

    /**
     * The client contract's wipe-on-user-change (client contract §1 Security). Unlike [clear], a failure is not
     * swallowed: leaving the previous User's diagnostic text behind is a privacy failure, not a lost debugging aid.
     */
    override suspend fun forget() {
        queries.deleteAllDiagnosticEntries()
    }

    private fun DiagnosticEntryRow.toModel(): DiagnosticEntry = DiagnosticEntry(
        occurredAt = occurredAt,
        level = DiagnosticLevel.fromWire(level),
        code = code,
        message = message,
        stack = stack,
        context = contextJson?.let { raw ->
            runCatching { json.decodeFromString<Map<String, String>>(raw) }.getOrDefault(emptyMap())
        } ?: emptyMap(),
    )

    companion object {
        /** The FIFO cap (ADR 0080 D1). */
        const val CAP: Int = 200
        const val MESSAGE_LIMIT: Int = 1_000
        const val STACK_LIMIT: Int = 4_000
    }
}
