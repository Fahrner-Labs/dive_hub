package dev.divehub.shared.write

import dev.divehub.api.models.DiveView
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWrite
import dev.divehub.shared.store.PendingWriteKind
import dev.divehub.shared.store.PendingWriteStatus
import dev.divehub.shared.sync.queuedProfileCheckpoint
import dev.divehub.shared.sync.writeDiveDetail
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.encodeURLParameter
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.time.Clock

/** Why a queued write left the queue without being applied: the thing it edits is gone. */
enum class WriteDropReason { Forbidden, NotFound, Gone }

/** A notice naming what happened to a dropped write, so the UI can say it rather than let the edit vanish (D3 step 4). */
data class WriteNotice(
    val entityType: String,
    val entityId: String,
    val status: Int,
    val reason: WriteDropReason,
    /** The Dive a merge kept, when the server named it. */
    val mergedInto: String? = null,
)

/** What one [WriteReplayer.replay] call did. */
sealed interface WriteReplayResult {
    /** Nothing was waiting. */
    data object Empty : WriteReplayResult

    /** Replay reached the end of the queue; entries may still be parked as conflicts or failed. */
    data class Drained(
        val applied: Int,
        val conflicted: Int,
        val dropped: Int,
        val failed: Int,
        val notices: List<WriteNotice>,
    ) : WriteReplayResult

    /** A 401 ended the session: replay stopped and every waiting entry stays queued (D3 step 5, D8). */
    data object SessionEnded : WriteReplayResult

    /** A transient failure (no answer, a 5xx): replay stopped and the entry stays pending for the next trigger. */
    data class Stopped(val error: Throwable) : WriteReplayResult
}

/**
 * The replay side of the offline write queue (ADR 0084 D3): for each waiting entry, send the queued patch with the
 * base version as `If-Match`, store a 2xx answer and drop the entry, and on a 412 merge field by field against the
 * fresh server view. Fields changed on one side only merge quietly and are re-sent with the fresh version; fields
 * changed on both sides park the entry as `conflicted`. A 404/403/410 drops the entry with a notice; a 401 stops
 * replay and keeps the whole queue (D8).
 *
 * A parked conflict is a first-class record: [conflicts] shows both values and [resolve] puts the User's decisions
 * back into the queue against the fresh version. The Android decision screen itself is T7.
 */
class WriteReplayer(
    private val api: ApiClient,
    private val store: DiveStore,
    private val clock: () -> String = { Clock.System.now().toString() },
) : WriteDrain {
    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    /** One replay at a time: the live stream, the foreground poll and WorkManager share the queue. */
    private val lock = Mutex()

    override suspend fun drain(): WriteReplayResult = replay()

    suspend fun replay(): WriteReplayResult = lock.withLock { replayLocked() }

    private suspend fun replayLocked(): WriteReplayResult {
        val entries = store.pendingWrites(PendingWriteStatus.Pending)
        if (entries.isEmpty()) return WriteReplayResult.Empty

        var applied = 0
        var conflicted = 0
        var dropped = 0
        var failed = 0
        val notices = mutableListOf<WriteNotice>()

        for (entry in entries) {
            // Deferred actions are Phase C (scans, creates); T6a only stores them.
            if (entry.kind != PendingWriteKind.Patch) continue
            when (val outcome = replayEntry(entry)) {
                EntryOutcome.Applied -> applied += 1
                EntryOutcome.Conflicted -> conflicted += 1
                is EntryOutcome.Dropped -> {
                    dropped += 1
                    notices += outcome.notice
                }
                EntryOutcome.Failed -> failed += 1
                EntryOutcome.SessionEnded -> return WriteReplayResult.SessionEnded
                is EntryOutcome.RetryLater -> return WriteReplayResult.Stopped(outcome.error)
            }
        }
        return WriteReplayResult.Drained(applied, conflicted, dropped, failed, notices)
    }

    /**
     * Every parked conflict as the UI's decision records, oldest first (ADR 0084 D3). An entry whose stored state
     * cannot be read is left out rather than shown half-decoded; the queue keeps it for a later replay attempt.
     */
    fun conflicts(): List<WriteConflictView> =
        store.pendingWrites(PendingWriteStatus.Conflicted).mapNotNull { entry ->
            val state = decodeConflict(entry.conflictJson) ?: return@mapNotNull null
            WriteConflictView(
                entryId = entry.id,
                entityType = entry.entityType,
                entityId = entry.entityId,
                serverVersion = state.serverVersion,
                fields = state.fields.map {
                    ConflictFieldView(
                        field = it.field,
                        base = it.base,
                        server = it.server,
                        local = it.local,
                        localAction = it.localAction,
                    )
                },
            )
        }

    /**
     * Apply the User's choices to a parked entry (ADR 0084 D3). The fresh server view lands in the store at once, the
     * fields kept as "this phone" go back into the queue against [WriteConflict.serverVersion], and replay can drain
     * them. A field without a choice keeps the preselected "this phone" side.
     *
     * When every field takes the server's value there is nothing left to send: the entry leaves the queue and the
     * store holds the server's view.
     */
    fun resolve(entryId: Long, choices: Map<String, ConflictSide>): ConflictResolution {
        val entry = store.pendingWrites(PendingWriteStatus.Conflicted).firstOrNull { it.id == entryId }
            ?: throw IllegalArgumentException("No parked conflict with id $entryId")
        val state = decodeConflict(entry.conflictJson)
            ?: throw IllegalArgumentException("Pending write $entryId has no readable conflict state")
        val base = parseObject(entry.baseJson)
            ?: throw IllegalArgumentException("Pending write $entryId has no base payload")
        val patch = parseObject(entry.payloadJson)
            ?: throw IllegalArgumentException("Pending write $entryId has no queued payload")
        val serverView = decodeDive(state.server.toString())
            ?: throw IllegalArgumentException("Pending write $entryId has no server view")

        val resolved = when (entry.entityType) {
            WriteEntities.DIVE -> resolveDive(base, state.server, patch, choices)
            WriteEntities.DIVE_PARTICIPANTS -> resolveParticipants(base, state.server, patch, choices)
            else -> throw IllegalArgumentException("Unsupported entity type for pending write $entryId")
        }

        // The decision is visible at once: the fresh server view, then the kept "this phone" values on top.
        store.transaction { writeDiveDetail(serverView) }
        if (resolved.isNotEmpty()) applyResolved(entry, resolved)

        if (resolved.isEmpty()) {
            store.deletePendingWrite(entry.id)
            return ConflictResolution.KeptServer(entry.entityType, entry.entityId)
        }
        store.updatePendingWrite(
            entry.copy(
                payloadJson = resolved.toString(),
                baseVersion = state.serverVersion,
                baseJson = state.server.toString(),
                status = PendingWriteStatus.Pending,
                lastError = null,
                conflictJson = null,
            ),
        )
        return ConflictResolution.Requeued(entry.id)
    }

    private fun resolveDive(
        base: JsonObject,
        server: JsonObject,
        patch: JsonObject,
        choices: Map<String, ConflictSide>,
    ): JsonObject {
        val merge = DiveMerge.mergeDive(base, server, patch)
        val merged = merge.patch.toMutableMap()
        val set = (merged["set"] as? JsonObject)?.toMutableMap() ?: linkedMapOf()
        val reset = (merged["reset"] as? JsonArray)
            ?.mapNotNull { (it as? JsonPrimitive)?.takeIf { primitive -> primitive.isString }?.content }
            ?.toMutableSet()
            ?: linkedSetOf()

        for (conflict in merge.conflicts) {
            if ((choices[conflict.field] ?: ConflictSide.ThisPhone) != ConflictSide.ThisPhone) continue
            when (conflict.localAction) {
                "set" -> {
                    val field = conflict.field.removePrefix("set.")
                    set[field] = conflict.local ?: JsonNull
                    reset.remove(field)
                }
                "reset" -> {
                    val field = conflict.field.removePrefix("reset.")
                    reset.add(field)
                    set.remove(field)
                }
                else -> merged[conflict.field] = conflict.local ?: JsonNull
            }
        }

        if (set.isEmpty()) merged.remove("set") else merged["set"] = JsonObject(set)
        if (reset.isEmpty()) merged.remove("reset") else merged["reset"] = JsonArray(reset.map(::JsonPrimitive))
        return JsonObject(merged)
    }

    private fun resolveParticipants(
        base: JsonObject,
        server: JsonObject,
        patch: JsonObject,
        choices: Map<String, ConflictSide>,
    ): JsonObject {
        val local = patch["participants"] as? JsonArray ?: JsonArray(emptyList())
        val merge = DiveMerge.mergeParticipants(base, server, local)
        val resolved = LinkedHashMap(roles(merge.participants))

        for (conflict in merge.conflicts) {
            val diverId = conflict.field.removePrefix("participants.")
            when (choices[conflict.field] ?: ConflictSide.ThisPhone) {
                ConflictSide.ThisPhone -> when (conflict.localAction) {
                    "remove" -> resolved.remove(diverId)
                    else -> (conflict.local as? JsonPrimitive)
                        ?.takeIf { primitive -> primitive.isString }
                        ?.content
                        ?.let { resolved[diverId] = it }
                }
                ConflictSide.Server -> {
                    val role = (conflict.server as? JsonPrimitive)
                        ?.takeIf { primitive -> primitive.isString }
                        ?.content
                    if (role == null) resolved.remove(diverId) else resolved[diverId] = role
                }
            }
        }

        // Nothing local survived: the server's roster is already in the store, so the entry has nothing to send.
        if (resolved == roles(server["participants"])) return JsonObject(emptyMap())
        return JsonObject(
            mapOf(
                "participants" to JsonArray(
                    resolved.map { (diverId, role) ->
                        buildJsonObject {
                            put("diverId", diverId)
                            put("role", role)
                        }
                    },
                ),
            ),
        )
    }

    private fun applyResolved(entry: PendingWrite, resolved: JsonObject) {
        if (entry.entityType == WriteEntities.DIVE_PARTICIPANTS) {
            val roster = resolved["participants"] as? JsonArray ?: return
            DiveLocalEdit.applyParticipants(store, entry.entityId, roster)
        } else {
            DiveLocalEdit.apply(store, entry.entityId, resolved)
        }
    }

    private fun roles(element: JsonElement?): Map<String, String> {
        val array = element as? JsonArray ?: return emptyMap()
        val result = LinkedHashMap<String, String>()
        for (item in array) {
            val entry = item as? JsonObject ?: continue
            val diverId = (entry["diverId"] as? JsonPrimitive)
                ?.takeIf { primitive -> primitive.isString }
                ?.content
                ?: continue
            val role = (entry["role"] as? JsonPrimitive)
                ?.takeIf { primitive -> primitive.isString }
                ?.content
                ?: continue
            result[diverId] = role
        }
        return result
    }

    private fun decodeConflict(text: String?): WriteConflict? {
        if (text.isNullOrBlank()) return null
        return runCatching { json.decodeFromString(WriteConflict.serializer(), text) }.getOrNull()
    }

    private suspend fun replayEntry(entry: PendingWrite): EntryOutcome {
        val baseVersion = entry.baseVersion ?: return fail(entry, "missing_base_version")
        val base = parseObject(entry.baseJson) ?: return fail(entry, "missing_base")
        val participants = entry.entityType == WriteEntities.DIVE_PARTICIPANTS
        if (!participants && entry.entityType != WriteEntities.DIVE) return fail(entry, "unsupported_entity")

        var currentBase = base
        var currentVersion = baseVersion
        var currentPatch = parseObject(entry.payloadJson) ?: return fail(entry, "bad_payload")

        repeat(MAX_MERGE_ROUNDS) {
            val response = try {
                send(entry, currentPatch, currentVersion)
            } catch (error: CancellationException) {
                throw error
            } catch (error: NoAnswerException) {
                return EntryOutcome.RetryLater(error)
            } catch (error: Exception) {
                return EntryOutcome.RetryLater(error)
            }

            val status = response.status.value
            when {
                response.status.isSuccess() -> {
                    val detail = decodeDiveBody(response) ?: return fail(entry, ErrorCodes.SERVER_ERROR)
                    applyAndDrop(entry, detail)
                    return EntryOutcome.Applied
                }
                status == 401 -> return EntryOutcome.SessionEnded
                status == 412 -> {
                    val server = parseServerDive(response.bodyTextOrNull()) ?: return fail(entry, "bad_412")
                    val serverVersion = longOf(server["version"]) ?: (currentVersion + 1)
                    val merge = if (participants) {
                        val local = currentPatch["participants"] as? JsonArray ?: JsonArray(emptyList())
                        val result = DiveMerge.mergeParticipants(currentBase, server, local)
                        if (result.hasConflicts) {
                            park(entry, serverVersion, server, result.conflicts)
                            return EntryOutcome.Conflicted
                        }
                        JsonObject(mapOf("participants" to result.participants))
                    } else {
                        val result = DiveMerge.mergeDive(currentBase, server, currentPatch)
                        if (result.hasConflicts) {
                            park(entry, serverVersion, server, result.conflicts)
                            return EntryOutcome.Conflicted
                        }
                        result.patch
                    }
                    if (!participants && merge.isEmpty()) {
                        // Every local change is already on the server: take the fresh view and stop.
                        val detail = decodeDive(server.toString()) ?: return fail(entry, "bad_412")
                        applyAndDrop(entry, detail)
                        return EntryOutcome.Applied
                    }
                    currentBase = server
                    currentVersion = serverVersion
                    currentPatch = merge
                }
                status == 403 || status == 404 || status == 410 -> {
                    val text = response.bodyTextOrNull()
                    store.deletePendingWrite(entry.id)
                    return EntryOutcome.Dropped(
                        WriteNotice(
                            entityType = entry.entityType,
                            entityId = entry.entityId,
                            status = status,
                            reason = when (status) {
                                403 -> WriteDropReason.Forbidden
                                410 -> WriteDropReason.Gone
                                else -> WriteDropReason.NotFound
                            },
                            mergedInto = mergedInto(text),
                        ),
                    )
                }
                status in 500..599 -> return EntryOutcome.RetryLater(
                    DiveHubException(ErrorCodes.SERVER_ERROR, status),
                )
                else -> {
                    val failure = api.failure(response)
                    return fail(entry, failure.code)
                }
            }
        }
        return fail(entry, "merge_rounds_exceeded")
    }

    private suspend fun send(entry: PendingWrite, patch: JsonObject, version: Long): HttpResponse {
        val path = "/api/dives/${entry.entityId.encodeURLParameter()}"
        return when (entry.entityType) {
            WriteEntities.DIVE -> api.patch(path, patch.toString(), ifMatch = version, retry = false)
            WriteEntities.DIVE_PARTICIPANTS -> api.put(
                "$path/participants",
                patch.toString(),
                ifMatch = version,
                retry = false,
            )
            else -> throw DiveHubException("unsupported_entity")
        }
    }

    private fun applyAndDrop(entry: PendingWrite, detail: DiveView) {
        val now = clock()
        store.transaction {
            writeDiveDetail(detail)
            saveCheckpoint(queuedProfileCheckpoint(detail.id, now))
            deletePendingWrite(entry.id)
        }
    }

    private fun park(entry: PendingWrite, serverVersion: Long, server: JsonObject, fields: List<ConflictField>) {
        val state = WriteConflict(serverVersion = serverVersion, server = server, fields = fields)
        store.updatePendingWrite(
            entry.copy(
                status = PendingWriteStatus.Conflicted,
                lastError = null,
                conflictJson = json.encodeToString(state),
            ),
        )
    }

    private fun fail(entry: PendingWrite, code: String): EntryOutcome {
        store.updatePendingWrite(
            entry.copy(status = PendingWriteStatus.Failed, lastError = code),
        )
        return EntryOutcome.Failed
    }

    private suspend fun decodeDiveBody(response: HttpResponse): DiveView? = decodeDive(response.bodyTextOrNull())

    private fun decodeDive(text: String?): DiveView? {
        if (text.isNullOrBlank()) return null
        return try {
            json.decodeFromString(DiveView.serializer(), text)
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            null
        }
    }

    private fun parseServerDive(text: String?): JsonObject? {
        if (text.isNullOrBlank()) return null
        val body = runCatching { Json.parseToJsonElement(text) as? JsonObject }.getOrNull() ?: return null
        return body["dive"] as? JsonObject
    }

    private fun parseObject(text: String?): JsonObject? {
        if (text.isNullOrBlank()) return null
        return runCatching { Json.parseToJsonElement(text) as? JsonObject }.getOrNull()
    }

    private suspend fun HttpResponse.bodyTextOrNull(): String? = try {
        bodyAsText()
    } catch (error: CancellationException) {
        throw error
    } catch (_: Exception) {
        null
    }

    private fun mergedInto(text: String?): String? {
        if (text.isNullOrBlank()) return null
        val body = runCatching { Json.parseToJsonElement(text) as? JsonObject }.getOrNull() ?: return null
        return (body["mergedInto"] as? JsonPrimitive)?.content
            ?: ((body["dive"] as? JsonObject)?.get("mergedInto") as? JsonPrimitive)?.content
    }

    private fun longOf(element: kotlinx.serialization.json.JsonElement?): Long? =
        (element as? JsonPrimitive)?.content?.toLongOrNull()

    private sealed interface EntryOutcome {
        data object Applied : EntryOutcome
        data object Conflicted : EntryOutcome
        data object Failed : EntryOutcome
        data object SessionEnded : EntryOutcome
        data class Dropped(val notice: WriteNotice) : EntryOutcome
        data class RetryLater(val error: Throwable) : EntryOutcome
    }

    private companion object {
        /** A server that keeps changing under us during one replay: give up loudly rather than loop forever. */
        const val MAX_MERGE_ROUNDS = 3
    }
}

/** The parked conflict state the resolution surface reads (T6b): the fresh server view and each decided field. */
@Serializable
internal data class WriteConflict(
    val serverVersion: Long,
    val server: JsonObject,
    val fields: List<ConflictField>,
)
