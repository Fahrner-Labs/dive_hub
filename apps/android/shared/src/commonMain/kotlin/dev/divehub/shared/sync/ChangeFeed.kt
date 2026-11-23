package dev.divehub.shared.sync

import kotlinx.serialization.Serializable

/**
 * One answer of `GET /api/sync/changes` (ADR 0083): the opaque position to send back next, and the hints after the
 * position asked for. A bootstrap (no cursor sent) carries only the cursor. The events name an entity and whether it is
 * gone; they never carry its payload — the engine refetches each one through the normal API.
 */
@Serializable
internal data class ChangeFeedPage(
    val cursor: String,
    val events: List<ChangeFeedEvent> = emptyList(),
)

/**
 * One change hint: which kind of thing changed, which one, and whether it is still there. `seq` orders events within
 * one transaction; the cursor, not this number, orders the feed.
 */
@Serializable
internal data class ChangeFeedEvent(
    val seq: Long = 0,
    val entityType: String,
    val entityId: String,
    val kind: String,
)

/** An entity the feed reported gone; a refetch would answer 404 (or 403 for a scope that shrank). */
internal const val CHANGE_DELETED = "deleted"
