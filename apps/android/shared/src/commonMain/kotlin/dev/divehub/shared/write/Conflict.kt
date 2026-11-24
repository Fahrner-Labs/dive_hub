package dev.divehub.shared.write

import kotlinx.serialization.json.JsonElement

/**
 * The side a User can keep for one both-sides-changed field (ADR 0084 D3). [ThisPhone] is the User's own fresh
 * edit and the preselected side; [Server] takes what the hub holds now.
 */
enum class ConflictSide { ThisPhone, Server }

/**
 * One field changed on both sides, as the resolution surface shows it: both values are present at once and nothing
 * has been decided yet (ADR 0084 D3).
 *
 * [field] is the API's field name (`notes`, `set.durationSeconds`, `reset.maxDepthM`, `participants.<diverId>`), so
 * the screen can name the thing that changed. [localAction] says what "this phone" means for an override or a
 * participant removal, where a plain JSON value cannot express the intent.
 */
data class ConflictFieldView(
    val field: String,
    val base: JsonElement? = null,
    val server: JsonElement? = null,
    val local: JsonElement? = null,
    val localAction: String? = null,
) {
    /** ADR 0084 D3: the User's own fresh edit is preselected, never the server's value. */
    val preselected: ConflictSide get() = ConflictSide.ThisPhone
}

/**
 * A parked queue entry as a first-class, user-decidable conflict record (ADR 0084 D3, D8). The entry stays in the
 * queue until every field has a decision; resolving it puts the kept values back into the queue against the fresh
 * server version.
 */
data class WriteConflictView(
    val entryId: Long,
    val entityType: String,
    val entityId: String,
    /** The version the parked 412 answered with; the resolution re-queues against it. */
    val serverVersion: Long,
    val fields: List<ConflictFieldView>,
    /** The stored Dive's server-computed number, when this device has one; the screen names the thing that waits. */
    val diveNumber: Int? = null,
)

/**
 * The queue's three states as the pending indicator shows them (ADR 0084 D4). [pending] waits for the connection,
 * [conflicted] waits for the User's decision, and [failed] needs a retry or the User's attention — each is a state,
 * never a toast.
 */
data class WriteQueueSnapshot(
    val pending: Int = 0,
    val conflicted: Int = 0,
    val failed: Int = 0,
)

/**
 * One waiting entry as the queue screen shows it (client contract, Offline writes): which thing it edits and the
 * fields it carries, so "2 changes waiting to sync" has a way to see what waits. A parked conflict is not here; it
 * is a [WriteConflictView] with a decision.
 */
data class QueuedWriteView(
    val entryId: Long,
    val fields: List<String>,
    val failed: Boolean,
    val diveNumber: Int? = null,
)

/** What [WriteReplayer.resolve] did with a User's decision. */
sealed interface ConflictResolution {
    /** The kept "this phone" fields are queued again against the fresh server version; replay can drain them. */
    data class Requeued(val entryId: Long) : ConflictResolution

    /** Every field takes the server's value: the fresh view is in the store and there is nothing left to send. */
    data class KeptServer(val entityType: String, val entityId: String) : ConflictResolution
}

/** The replay side as the sync loop sees it (ADR 0084 D3): drain the queue whenever the store may be fresh. */
fun interface WriteDrain {
    suspend fun drain(): WriteReplayResult
}
