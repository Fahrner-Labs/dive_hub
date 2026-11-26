// The admin's Source purge (FAH-528), the pre-agreed remedy when a Source objects to its site data: delete every
// External ID of one Source, then the import-created sites that no other data-providing ID and no Dive holds.
// Sites a Dive still names are reported for the admin to resolve, never deleted. It runs as a worker job like a
// Site import, and never beside one.
import { and, asc, desc, eq, inArray, isNotNull, sql } from 'drizzle-orm';
import type { Db, Tx } from '../../db/client.js';
import { dive, diveSite, diveSiteExternalId, siteImport, sitePurge } from '../../db/schema.js';
import type { SitePurgeProgress } from '../../db/import-types.js';
import { errorMessage } from '../../errors.js';
import type { ProblemCode } from '../../http/problems.js';
import { emitHint } from '../../live/emit.js';
import { isUniqueViolation } from '../site-error.js';
import type { ImportSource } from '../sources.js';
import { SITE_JOB_LOCK } from './site-jobs.js';
import { classifyPurge, type PurgeSite } from './site-purge-plan.js';

export const PURGE_SITE_SOURCE_TASK = 'purge_site_source';

export class SitePurgeError extends Error {
  constructor(readonly code: 'site_import_running' | 'site_purge_running' | 'site_purge_not_found' | 'site_source_disabled') {
    super(code);
  }
}

/** What a Source purge needs: the database and the Sources this instance offers (DIVEHUB_SITE_SOURCES). */
export interface SitePurgeDeps {
  db: Db;
  sources: Partial<Record<ImportSource, unknown>>;
}

/** Sites deleted per transaction: a big SSI purge shows progress and does not hold one long lock. */
const BATCH = 1000;

export function createSitePurgeService({ db, sources }: SitePurgeDeps) {
  const setProgress = (id: string, progress: SitePurgeProgress) =>
    db.update(sitePurge).set({ progress }).where(eq(sitePurge.id, id));

  /** The sites and what the purge must know about them, after the Source's own IDs are gone. `ids`: just these. */
  async function loadSites(tx: Tx, ids?: string[]): Promise<PurgeSite[]> {
    const only = ids ? inArray(diveSite.id, ids) : undefined;
    const [sites, externalIds, dives] = await Promise.all([
      tx.select({ id: diveSite.id, name: diveSite.name, createdBy: diveSite.createdBy, mergedInto: diveSite.mergedInto }).from(diveSite)
        .where(only).orderBy(asc(diveSite.name), asc(diveSite.id)),
      tx.select({ siteId: diveSiteExternalId.siteId, source: diveSiteExternalId.source, providesData: diveSiteExternalId.providesData })
        .from(diveSiteExternalId).where(ids ? inArray(diveSiteExternalId.siteId, ids) : undefined),
      tx.select({ siteId: dive.siteId, dives: sql<number>`count(*)`.mapWith(Number) }).from(dive)
        .where(ids ? and(isNotNull(dive.siteId), inArray(dive.siteId, ids)) : isNotNull(dive.siteId)).groupBy(dive.siteId),
    ]);
    const bySite = new Map<string, PurgeSite['externalIds']>();
    for (const id of externalIds) {
      const list = bySite.get(id.siteId) ?? [];
      list.push({ source: id.source, providesData: id.providesData });
      bySite.set(id.siteId, list);
    }
    const divesOf = new Map(dives.map((d) => [d.siteId!, d.dives]));
    return sites.map((s) => ({
      ...s,
      dives: divesOf.get(s.id) ?? 0,
      externalIds: bySite.get(s.id) ?? [],
    }));
  }

  /** Deletes the given eligible sites' remaining External IDs and the sites themselves; returns how many went. */
  async function deleteBatch(tx: Tx, ids: string[]): Promise<number> {
    if (ids.length === 0) return 0;
    // A site's remaining IDs are references of other Sources only (the Source's are gone); they go with it.
    await tx.delete(diveSiteExternalId).where(inArray(diveSiteExternalId.siteId, ids));
    const gone = await tx.delete(diveSite).where(inArray(diveSite.id, ids)).returning({ id: diveSite.id });
    // No Site Revision for a purged site (ADR 0091), so announce each removal here: the normal wildcard site hint
    // refetches every open site list, as a user's site delete does through its Revision.
    for (const site of gone) await emitHint(tx, { kind: 'site', id: site.id, audience: '*' });
    return gone.length;
  }

  return {
    /** Queues a purge (admins only, checked by the route); one at a time, and never beside a Site import. */
    async start(userId: string, source: ImportSource) {
      if (!(source in sources)) throw new SitePurgeError('site_source_disabled');
      try {
        return await db.transaction(async (tx) => {
          await tx.execute(SITE_JOB_LOCK);
          const [importing] = await tx.select({ id: siteImport.id }).from(siteImport)
            .where(inArray(siteImport.status, ['queued', 'running'])).limit(1);
          if (importing) throw new SitePurgeError('site_import_running');
          const [row] = await tx.insert(sitePurge).values({ startedBy: userId, source }).returning();
          // One attempt: a failed purge says why and is started again by the admin, not retried behind their back.
          await tx.execute(sql`select graphile_worker.add_job(${PURGE_SITE_SOURCE_TASK}, json_build_object('sitePurgeId', ${row!.id}::text), max_attempts => 1)`);
          await emitHint(tx, { kind: 'sitePurge', id: row!.id, audience: [userId] });
          return row!;
        });
      } catch (error) {
        if (isUniqueViolation(error, 'site_purge_one_active_uq')) throw new SitePurgeError('site_purge_running');
        throw error;
      }
    },

    async get(id: string) {
      const [row] = await db.select().from(sitePurge).where(eq(sitePurge.id, id));
      if (!row) throw new SitePurgeError('site_purge_not_found');
      return row;
    },

    /** The latest Source purges, newest first. */
    async list(limit = 10) {
      return db.select().from(sitePurge).orderBy(desc(sitePurge.createdAt), desc(sitePurge.id)).limit(limit);
    },

    /** Runs a queued purge (the worker's job). The result lists the sites a Dive still names. */
    async run(id: string) {
      const [row] = await db.update(sitePurge).set({ status: 'running' })
        .where(and(eq(sitePurge.id, id), eq(sitePurge.status, 'queued'))).returning();
      if (!row) return;
      await emitHint(db, { kind: 'sitePurge', id, audience: row.startedBy ? [row.startedBy] : [] });
      try {
        await setProgress(id, { step: 'ids', done: 0, total: 0 });
        const sourceIdsDeleted = await db.transaction(async (tx) =>
          (await tx.delete(diveSiteExternalId).where(eq(diveSiteExternalId.source, row.source)).returning({ id: diveSiteExternalId.id })).length);

        const plan = await db.transaction(async (tx) => classifyPurge(row.source, await loadSites(tx)));
        const total = plan.deleteIds.length;
        await setProgress(id, { step: 'sites', done: 0, total });

        let sitesDeleted = 0;
        const referenced = new Map(plan.referenced.map((site) => [site.id, site]));
        for (let i = 0; i < total; i += BATCH) {
          const batch = plan.deleteIds.slice(i, i + BATCH);
          sitesDeleted += await db.transaction(async (tx) => {
            // A User may have moved a Dive or added a Source ID since the plan: delete only what is still eligible.
            const sites = await loadSites(tx, batch);
            const now = classifyPurge(row.source, sites);
            for (const site of now.referenced) referenced.set(site.id, site);
            // Tombstones first: a target's self-reference would otherwise still be there when it goes.
            const deleting = new Set(now.deleteIds);
            const tombstones = sites.filter((site) => site.mergedInto !== null && deleting.has(site.id)).map((site) => site.id);
            const rest = now.deleteIds.filter((id) => !tombstones.includes(id));
            return (await deleteBatch(tx, tombstones)) + (await deleteBatch(tx, rest));
          });
          await setProgress(id, { step: 'sites', done: Math.min(i + BATCH, total), total });
        }

        const result = { sourceIdsDeleted, sitesDeleted, referencedSites: [...referenced.values()] };
        await db.update(sitePurge).set({
          status: 'done', progress: { step: 'sites', done: total, total }, result, finishedAt: new Date(),
        }).where(eq(sitePurge.id, id));
        await emitHint(db, { kind: 'sitePurge', id, audience: row.startedBy ? [row.startedBy] : [] });
      } catch (error) {
        const code: ProblemCode = 'internal_error';
        await db.update(sitePurge).set({
          status: 'failed', errorCode: code, errorDetail: errorMessage(error).slice(0, 500), finishedAt: new Date(),
        }).where(eq(sitePurge.id, id));
        await emitHint(db, { kind: 'sitePurge', id, audience: row.startedBy ? [row.startedBy] : [] });
        throw error;
      }
    },

    /** At worker start: a purge still running was cut off by a stop or crash. */
    async failInterrupted() {
      const rows = await db.update(sitePurge).set({ status: 'failed', errorCode: 'site_import_interrupted', finishedAt: new Date() })
        .where(inArray(sitePurge.status, ['running'])).returning({ id: sitePurge.id, startedBy: sitePurge.startedBy });
      for (const row of rows) await emitHint(db, { kind: 'sitePurge', id: row.id, audience: row.startedBy ? [row.startedBy] : [] });
    },
  };
}

export type SitePurgeService = ReturnType<typeof createSitePurgeService>;
