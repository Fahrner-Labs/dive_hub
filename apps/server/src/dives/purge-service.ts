// Purging deleted Dives that no longer track anything (ADR 0072): every Provider copy is gone and the tombstone is old
// enough. Recordings and Originals stay, so re-importing an old file is still skipped as `deleted_earlier`.
import { and, eq, inArray, isNotNull, lt } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { continuationAppend, dive, push, recording, revision, waitingPush } from '../db/schema.js';
import type { DivePushes } from './provider-port.js';

/**
 * How long a deleted Dive keeps its tombstone after every Provider copy is gone (ADR 0072). A Dive can be restored
 * within this time; a purged Dive can't, and its Recordings still keep its file out.
 */
export const PURGE_AFTER_DAYS = 30;

export interface PurgeDeps {
  /** Where a Dive still is, from the provider layer (the same rule the deleted list uses). */
  pushes: Pick<DivePushes, 'currentOf'>;
}

export function createPurgeService(db: Db, { pushes }: PurgeDeps) {
  return {
    /**
     * Hard-deletes deleted Dives older than PURGE_AFTER_DAYS that no longer track anything: every Provider copy is gone
     * (deleted there or found absent), no delete still waits for a Provider, and no Continuation uses the Dive as a part
     * or the kept side. Their Recordings are detached and kept (their keys still keep re-imports out), and Originals are
     * left alone because another Dive can share them. Returns how many Dives were purged.
     */
    async purge(now: Date = new Date()): Promise<number> {
      const cutoff = new Date(now.getTime() - PURGE_AFTER_DAYS * 24 * 60 * 60 * 1000);
      const candidates = await db.select({ id: dive.id }).from(dive)
        .where(and(isNotNull(dive.deletedAt), lt(dive.deletedAt, cutoff)));
      if (candidates.length === 0) return 0;
      const ids = candidates.map((row) => row.id);

      const [remote, waitingDeletes, parts, kept] = await Promise.all([
        pushes.currentOf(ids),
        db.select({ id: waitingPush.diveId }).from(waitingPush)
          .where(and(inArray(waitingPush.diveId, ids), eq(waitingPush.action, 'delete'), inArray(waitingPush.state, ['waiting', 'held']))),
        db.select({ id: recording.fromDiveId }).from(recording).where(inArray(recording.fromDiveId, ids)),
        db.select({ id: continuationAppend.keptDiveId }).from(continuationAppend).where(inArray(continuationAppend.keptDiveId, ids)),
      ]);
      const tracked = new Set([
        ...waitingDeletes.map((row) => row.id),
        ...parts.map((row) => row.id).filter((id): id is string => id !== null),
        ...kept.map((row) => row.id),
      ]);

      let purged = 0;
      for (const id of ids) {
        // A copy at any Provider, or a delete that still waits for one, keeps the Dive here.
        if ((remote.get(id)?.size ?? 0) > 0) continue;
        if (tracked.has(id)) continue;
        await db.transaction(async (tx) => {
          // The Recordings stay as tombstones, their keys still reserved; detaching them frees the Dive's row.
          await tx.update(recording).set({ diveId: null }).where(eq(recording.diveId, id));
          await tx.delete(push).where(eq(push.diveId, id));
          await tx.delete(revision).where(and(eq(revision.entityType, 'dive'), eq(revision.entityId, id)));
          // Everything else hangs off the Dive with `on delete cascade`: Participants, findings, leases, conflicts,
          // Waiting pushes, Push-run items, Cylinders and the rest.
          await tx.delete(dive).where(eq(dive.id, id));
        });
        purged += 1;
      }
      return purged;
    },
  };
}

export type PurgeService = ReturnType<typeof createPurgeService>;
