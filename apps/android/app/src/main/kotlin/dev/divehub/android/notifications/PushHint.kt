package dev.divehub.android.notifications

import dev.divehub.shared.store.Dive

/**
 * One FCM data message, the same `{ v, kind, id }` shape as the SSE stream (ADR 0066, ADR 0085). It is a hint only:
 * the payload names a thing, never a name, site or count. Anything malformed or of an unknown kind is ignored.
 */
internal data class PushHint(val kind: String, val id: String) {
    companion object {
        fun from(data: Map<String, String>): PushHint? {
            if (data["v"] != "1") return null
            if (data["kind"] != "dive") return null
            val id = data["id"]?.takeIf { it.isNotBlank() } ?: return null
            return PushHint(kind = "dive", id = id)
        }
    }
}

/**
 * The hint-only spoofing defence (ADR 0085): a notification is earned only when the authenticated fetch left the hinted
 * Dive present and changed. Absent, tombstoned or unchanged means the push was stale or forged; the request was the
 * only cost.
 */
internal fun pushFoundNewDive(before: Dive?, after: Dive?): Boolean =
    after != null && after.deletedAt == null && (before == null || before.version != after.version)
