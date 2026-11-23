// The change feed's read side and retention (ADR 0083): a client follows the append-only change_event log by seq.
// Events are written in the same transaction as every logbook write (revisions/revisions.ts); this module only reads
// them, bootstraps a cursor, and sweeps what the 180-day retention no longer needs. It carries IDs and kinds only:
// the client refetches each entity through the normal API, so permissions never leak through the feed.
import { sql } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import type { RevisionEntityType } from '../revisions/revisions.js';

/** How long the feed keeps an event (ADR 0083). A cursor older than the oldest retained event answers 410. */
export const CHANGE_EVENT_RETENTION_DAYS = 180;

/** One page of events unless the client asks for another size. */
export const CHANGES_DEFAULT_LIMIT = 200;
export const CHANGES_MAX_LIMIT = 1000;

/** A cursor the feed can no longer replay: the client resyncs from a bootstrap cursor and rebases its queue (ADR 0083). */
export class SyncError extends Error {
  constructor(readonly code: 'sync_resync_required') { super(code); }
}

export type ChangeKind = 'changed' | 'deleted';

/** One hint: which thing changed, and whether it is gone. No payload — the client refetches it through the API. */
export interface ChangeEventView {
  seq: number;
  entityType: RevisionEntityType;
  entityId: string;
  kind: ChangeKind;
}

export interface ChangeFeedPage {
  /** What the client sends next: the last event's seq, or the one it sent when nothing was new. */
  cursor: number;
  /** In seq order; empty for a bootstrap (no cursor sent). */
  events: ChangeEventView[];
}

export function createSyncService(db: Db) {
  /**
   * The snapshot's safe high-water xid and the oldest retained seq, read together. An event from a transaction on this
   * database at or above xmin may commit after a client passed its seq, so nothing from it is handed out yet (research
   * §3.3). The bound is database-scoped: only a transaction connected to this database can write this log, so a long
   * transaction in another database on the same PostgreSQL server must not stall the feed. With `track_activities`
   * off the in-progress list is empty, so fall back to the cluster-wide xmin, which is safe but conservative.
   */
  async function feedBounds(): Promise<{ xmin: string; oldest: number | null }> {
    const [row] = (await db.execute(sql`
      with snapshot as (
        select pg_snapshot_xmin(pg_current_snapshot()) as cluster_xmin,
               pg_snapshot_xmax(pg_current_snapshot()) as xmax
      ), active as (
        select min(backend_xid::text::bigint) as xid
        from pg_stat_activity
        where datname = current_database() and backend_xid is not null
      )
      select case when not current_setting('track_activities')::boolean
                  then (select cluster_xmin::text from snapshot)
                  else coalesce((select xid from active), (select xmax::text::bigint from snapshot))::text
             end as xmin,
             (select min(seq) from change_event) as oldest
    `)).rows as { xmin: string; oldest: string | null }[];
    return { xmin: row!.xmin, oldest: row!.oldest === null ? null : Number(row!.oldest) };
  }

  return {
    /**
     * The events after `cursor`, in seq order, filtered by the xmin high-water mark. Without a cursor it only reads
     * the bootstrap high-water cursor: a fresh or upgrading install full-syncs from there (D6), and an installed app
     * probes this endpoint once per session (404 on an old server means read-only).
     */
    async changes({ cursor, limit = CHANGES_DEFAULT_LIMIT }: { cursor?: number; limit?: number } = {}): Promise<ChangeFeedPage> {
      const { xmin, oldest } = await feedBounds();

      if (cursor === undefined) {
        const [row] = (await db.execute(sql`
          select coalesce(max(seq), 0) as seq from change_event where xid < ${xmin}::xid8
        `)).rows as { seq: string }[];
        return { cursor: Number(row!.seq), events: [] };
      }

      // A cursor before the oldest event still in the log has lost changes the retention already swept. An empty log
      // has nothing to miss, so it stays valid (the client's cursor is at its last event, which was also swept).
      if (oldest !== null && cursor < oldest - 1) throw new SyncError('sync_resync_required');

      const rows = (await db.execute(sql`
        select seq, entity_type, entity_id, kind
        from change_event
        where seq > ${cursor} and xid < ${xmin}::xid8
        order by seq
        limit ${limit}
      `)).rows as { seq: string; entity_type: RevisionEntityType; entity_id: string; kind: ChangeKind }[];
      const events = rows.map((row) => ({
        seq: Number(row.seq), entityType: row.entity_type, entityId: row.entity_id, kind: row.kind,
      }));
      return { cursor: events.at(-1)?.seq ?? cursor, events };
    },

    /** The worker's daily sweep: drops events older than the retention and returns how many. */
    async purge(now: Date = new Date()): Promise<number> {
      const cutoff = new Date(now.getTime() - CHANGE_EVENT_RETENTION_DAYS * 24 * 60 * 60 * 1000);
      const removed = await db.execute(sql`delete from change_event where at < ${cutoff} returning seq`);
      return removed.rows.length;
    },
  };
}

export type SyncService = ReturnType<typeof createSyncService>;
