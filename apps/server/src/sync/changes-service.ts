// The change feed's read side and retention (ADR 0083): a client follows the append-only change_event log by its
// (xid, seq) position. Events are written in the same transaction as every logbook write (revisions/revisions.ts);
// this module only reads them, bootstraps a cursor, and sweeps what the 180-day retention no longer needs. It carries
// IDs and kinds only: the client refetches each entity through the normal API, so permissions never leak through the feed.
import { sql } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import type { RevisionEntityType } from '../revisions/revisions.js';

/** How long the feed keeps an event (ADR 0083). A cursor older than the stored floor answers 410. */
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
  /** Opaque position (`<xid>:<seq>`) to send back, or the one sent when nothing was new. */
  cursor: string;
  /** In feed order; empty for a bootstrap (no cursor sent). */
  events: ChangeEventView[];
}

/**
 * A position in the feed: the transaction's `xid8` (so it does not wrap at 2^32) and the hint's `seq` within it. Two
 * events of one transaction share the xid and are ordered by seq. Kept as bigints because `xid8` can exceed 2^53.
 */
interface FeedPosition {
  xid: bigint;
  seq: bigint;
}

const CURSOR_PATTERN = /^(\d+):(\d+)$/;

function decodeCursor(value: string): FeedPosition {
  const match = CURSOR_PATTERN.exec(value);
  if (!match) throw new Error(`not a change-feed cursor: ${value}`);
  return { xid: BigInt(match[1]!), seq: BigInt(match[2]!) };
}

const encodeCursor = ({ xid, seq }: FeedPosition): string => `${xid}:${seq}`;

const before = (a: FeedPosition, b: FeedPosition): boolean =>
  a.xid < b.xid || (a.xid === b.xid && a.seq < b.seq);

/** The floor: the last event the sweep deleted. A cursor before it has lost events the retention already removed. */
const floorOf = (row: { floor_xid: string; floor_seq: string }): FeedPosition => ({
  xid: BigInt(row.floor_xid), seq: BigInt(row.floor_seq),
});

/**
 * The safe high-water xid, shared by the feed and the sweep so both agree on which events are settled.
 *
 * An event from a transaction at or above xmin may commit after a client passed its position, so nothing from it is
 * handed out yet. The bound is database-scoped where the role may see other backends' `backend_xid` (only a
 * transaction connected to this database can write this log, so a long transaction in another database must not stall
 * the feed or the sweep). A role that may not see them falls back to the cluster-wide xmin, which is conservative but
 * safe; that is why the fallback is not `xmax` (which holds nothing back). `greatest(..., cluster_xmin)` also keeps a
 * 32-bit `backend_xid` correct if the cluster has advanced past its first xid epoch.
 */
const XMIN_BOUND = sql`case
  when not current_setting('track_activities')::boolean
    then pg_snapshot_xmin(pg_current_snapshot())::text::bigint
  when not (current_setting('is_superuser')::boolean
            or pg_has_role(current_user, 'pg_read_all_stats', 'member'))
    then pg_snapshot_xmin(pg_current_snapshot())::text::bigint
  else greatest(
    coalesce(
      (select min(backend_xid::text::bigint) from pg_stat_activity
       where datname = current_database() and backend_xid is not null),
      pg_snapshot_xmax(pg_current_snapshot())::text::bigint),
    pg_snapshot_xmin(pg_current_snapshot())::text::bigint)
end`;

export function createSyncService(db: Db) {
  /** The safe high-water xid and the retention floor, read together. */
  async function feedBounds(): Promise<{ xmin: bigint; floor: FeedPosition }> {
    const [row] = (await db.execute(sql`
      select (${XMIN_BOUND})::text as xmin,
             coalesce((select xid::text from change_feed_floor where id), '0') as floor_xid,
             coalesce((select seq::text from change_feed_floor where id), '0') as floor_seq
    `)).rows as { xmin: string; floor_xid: string; floor_seq: string }[];
    return { xmin: BigInt(row!.xmin), floor: floorOf(row!) };
  }

  return {
    /**
     * The events after `cursor`, in feed order, filtered by the xmin high-water mark. Without a cursor it only reads
     * the bootstrap high-water position: a fresh or upgrading install full-syncs from there (D6), and an installed app
     * probes this endpoint once per session (404 on an old server means read-only).
     */
    async changes({ cursor, limit = CHANGES_DEFAULT_LIMIT }: { cursor?: string; limit?: number } = {}): Promise<ChangeFeedPage> {
      const { xmin, floor } = await feedBounds();

      if (cursor === undefined) {
        const [high] = (await db.execute(sql`
          select xid::text as xid, seq from change_event
          where xid < ${xmin.toString()}::xid8
          order by xid desc, seq desc
          limit 1
        `)).rows as { xid: string; seq: string }[];
        const position = high ? { xid: BigInt(high.xid), seq: BigInt(high.seq) } : floor;
        return { cursor: encodeCursor(before(position, floor) ? floor : position), events: [] };
      }

      const from = decodeCursor(cursor);
      if (before(from, floor)) throw new SyncError('sync_resync_required');

      const rows = (await db.execute(sql`
        select xid::text as xid, seq, entity_type, entity_id, kind
        from change_event
        where xid < ${xmin.toString()}::xid8
          and (xid, seq) > (${from.xid.toString()}::xid8, ${from.seq.toString()}::bigint)
        order by xid, seq
        limit ${limit}
      `)).rows as { xid: string; seq: string; entity_type: RevisionEntityType; entity_id: string; kind: ChangeKind }[];
      const events = rows.map((row) => ({
        seq: Number(row.seq), entityType: row.entity_type, entityId: row.entity_id, kind: row.kind,
      }));
      const last = rows.at(-1);
      return {
        cursor: encodeCursor(last ? { xid: BigInt(last.xid), seq: BigInt(last.seq) } : from),
        events,
      };
    },

    /**
     * The worker's daily sweep: deletes a feed-order prefix of events older than the retention and moves the floor past
     * it, in one statement so a crash cannot leave the floor disagreeing with the log. Sweeping by `at` alone could
     * delete a higher position while keeping a lower one (`at` is the transaction's start, the position its first
     * write), leaving a hole a cursor cannot see; only a prefix keeps the retained log a suffix.
     */
    async purge(now: Date = new Date()): Promise<number> {
      const cutoff = new Date(now.getTime() - CHANGE_EVENT_RETENTION_DAYS * 24 * 60 * 60 * 1000);
      const removed = await db.execute(sql`
        with horizon as (
          select (${XMIN_BOUND})::text::xid8 as xmin
        ), ordered as (
          select xid, seq,
                 bool_and(at < ${cutoff}) over (
                   order by xid, seq rows between unbounded preceding and current row
                 ) as all_old
          from change_event
          where xid < (select xmin from horizon)
        ), doomed as (
          select xid, seq from ordered where all_old
        ), next_floor as (
          select xid, seq from doomed order by xid desc, seq desc limit 1
        ), bumped as (
          update change_feed_floor f
          set xid = n.xid, seq = n.seq, updated_at = now()
          from next_floor n
          where f.id and (f.xid, f.seq) < (n.xid, n.seq)
          returning 1
        )
        delete from change_event c
        using doomed d
        where c.xid = d.xid and c.seq = d.seq
        returning c.seq
      `);
      return removed.rows.length;
    },
  };
}

export type SyncService = ReturnType<typeof createSyncService>;
