package dev.divehub.shared.write

import dev.divehub.api.models.CentreView
import dev.divehub.api.models.DiveView
import dev.divehub.api.models.DiverDetailsView
import dev.divehub.shared.ApiClient
import dev.divehub.shared.DiveHubException
import dev.divehub.shared.ErrorCodes
import dev.divehub.shared.NoAnswerException
import dev.divehub.shared.store.DiveStore
import dev.divehub.shared.store.PendingWrite
import dev.divehub.shared.store.PendingWriteKind
import dev.divehub.shared.store.PendingWriteStatus
import dev.divehub.shared.store.ScanResult
import dev.divehub.shared.store.ScanStatus
import dev.divehub.shared.sync.queuedProfileCheckpoint
import dev.divehub.shared.sync.writeDiveDetail
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.encodeURLParameter
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.time.Clock

/** Why a queued write left the queue without being applied: the thing it edits is gone, or the server refused it. */
enum class WriteDropReason { Forbidden, NotFound, Gone, Refused }

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
 * T6a stops at parking: the conflict state is stored (`conflictJson`) but the resolution surface is T6b/T7.
 */
class WriteReplayer(
    private val api: ApiClient,
    private val store: DiveStore,
    private val clock: () -> String = { Clock.System.now().toString() },
) {
    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    suspend fun replay(): WriteReplayResult {
        val entries = store.pendingWrites(PendingWriteStatus.Pending)
        if (entries.isEmpty()) return WriteReplayResult.Empty

        var applied = 0
        var conflicted = 0
        var dropped = 0
        var failed = 0
        val notices = mutableListOf<WriteNotice>()

        for (entry in entries) {
            // Deferred actions replay verbatim, in the same global enqueue order as patches; they never merge.
            val outcome = when (entry.kind) {
                PendingWriteKind.Action -> replayAction(entry)
                PendingWriteKind.Patch -> replayEntry(entry)
            }
            when (outcome) {
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
     * Replay one deferred action (T11). The read route's answer is stored as a [ScanResult] for the scanner screen; a
     * take or a create answers with the Diver or centre it made. A refusal drops the action with its problem code in
     * the result; a 401 or a transient failure keeps it queued (ADR 0084 D3/D8).
     */
    private suspend fun replayAction(entry: PendingWrite): EntryOutcome {
        val path = when (entry.entityType) {
            ScanEntities.SCAN -> "/api/verification-codes/read"
            ScanEntities.TAKE_CODE -> "/api/divers/from-code"
            ScanEntities.CENTRE -> "/api/dive-centres"
            else -> return fail(entry, "unsupported_action")
        }
        val response = try {
            api.post(path, entry.payloadJson, retry = false)
        } catch (error: CancellationException) {
            throw error
        } catch (error: NoAnswerException) {
            return EntryOutcome.RetryLater(error)
        } catch (error: Exception) {
            return EntryOutcome.RetryLater(error)
        }

        val status = response.status.value
        val text = response.bodyTextOrNull()
        return when {
            response.status.isSuccess() -> {
                if (entry.entityType == ScanEntities.SCAN) recordRead(entry, text) else recordWrite(entry, status, text)
                EntryOutcome.Applied
            }
            status == 401 -> EntryOutcome.SessionEnded
            status in 500..599 -> EntryOutcome.RetryLater(DiveHubException(ErrorCodes.SERVER_ERROR, status))
            else -> {
                recordFailure(entry, status, text)
                EntryOutcome.Dropped(
                    WriteNotice(
                        entityType = entry.entityType,
                        entityId = entry.entityId,
                        status = status,
                        reason = when (status) {
                            403 -> WriteDropReason.Forbidden
                            404 -> WriteDropReason.NotFound
                            410 -> WriteDropReason.Gone
                            else -> WriteDropReason.Refused
                        },
                    ),
                )
            }
        }
    }

    /** A read's answer: what the code is, and what the screen may next do with it. */
    private fun recordRead(entry: PendingWrite, text: String?) {
        val body = parseObject(text)
        val kind = stringOf(body?.get("kind"))
        val existing = body?.get("existing") as? JsonObject
        val result = when (kind) {
            "centre" -> ScanResult(
                id = 0,
                at = clock(),
                status = ScanStatus.Read,
                kind = kind,
                name = stringOf(body?.get("name")),
                // The centre here, or the id this create will carry when the User confirms it.
                entityId = stringOf(existing?.get("id")) ?: entry.entityId,
                detailJson = text,
                actionId = entry.id,
            )
            else -> ScanResult(
                id = 0,
                at = clock(),
                status = ScanStatus.Read,
                kind = kind,
                name = pairName(stringOf(body?.get("firstName")), stringOf(body?.get("lastName"))),
                entityId = stringOf(existing?.get("id")),
                detailJson = text,
                actionId = entry.id,
            )
        }
        store.transaction {
            // The action stays as the User's pending data: taking a person's code needs its text again, and this
            // result points back at it (ADR 0043: the text is never copied into this table).
            updatePendingWrite(entry.copy(status = PendingWriteStatus.Awaiting, lastError = null))
            addScanResult(result)
        }
    }

    /** A take's or a create's answer: the Diver or centre it made, or the one the server found already here. */
    private fun recordWrite(entry: PendingWrite, status: Int, text: String?) {
        val isCentre = entry.entityType == ScanEntities.CENTRE
        val body = parseObject(text)
        val centre = if (isCentre) text?.let { body -> decoded { json.decodeFromString(CentreView.serializer(), body) } } else null
        val diver = if (isCentre) null else text?.let { body -> decoded { json.decodeFromString(DiverDetailsView.serializer(), body) } }
        val result = ScanResult(
            id = 0,
            at = clock(),
            status = if (status == 201) ScanStatus.Created else ScanStatus.Existing,
            kind = when {
                isCentre -> "centre"
                diver?.leaderNumber != null || stringOf(body?.get("leaderNumber")) != null -> "professional"
                else -> "buddy"
            },
            name = centre?.displayName ?: diver?.name
                ?: stringOf(body?.get("displayName")) ?: stringOf(body?.get("name")),
            entityId = centre?.id ?: diver?.id ?: stringOf(body?.get("id")),
        )
        store.transaction {
            addScanResult(result)
            deletePendingWrite(entry.id)
        }
    }

    private fun <T> decoded(block: () -> T): T? = try {
        block()
    } catch (error: CancellationException) {
        throw error
    } catch (_: Exception) {
        null
    }

    /** A refusal is a state on the scan, never a silent loss: keep the answer and its problem code. */
    private fun recordFailure(entry: PendingWrite, status: Int, text: String?) {
        val body = parseObject(text)
        val result = ScanResult(
            id = 0,
            at = clock(),
            status = ScanStatus.Failed,
            kind = if (entry.entityType == ScanEntities.CENTRE) "centre" else null,
            entityId = stringOf((body?.get("centre") as? JsonObject)?.get("id")),
            detailJson = text,
            errorCode = stringOf(body?.get("code")) ?: "http_$status",
        )
        store.transaction {
            addScanResult(result)
            deletePendingWrite(entry.id)
        }
    }

    private fun pairName(first: String?, last: String?): String? {
        val name = listOfNotNull(first, last).joinToString(" ").trim()
        return name.ifEmpty { null }
    }

    private fun stringOf(element: kotlinx.serialization.json.JsonElement?): String? =
        (element as? JsonPrimitive)?.takeIf { it.isString }?.content

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
