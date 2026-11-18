// The stored side of "changed since sent" (ADR 0040, amended 2026-11-17): what a Dive fingerprints as now at each
// Provider, kept where the Dive changes. The logbook's chip count compares it with the current Push's sent fingerprint
// in SQL; the filter itself still works the answer out afresh when asked. The cost moves from every list request to
// the Dive's own writes, which are far rarer.
import { and, desc, eq, inArray, isNotNull, isNull, notInArray, or, sql, type SQL } from 'drizzle-orm';
import type { Db, Queryable } from '../../db/client.js';
import { connection, dive, diveFingerprint, diverManagement, participant, push } from '../../db/schema.js';
import { currentRemote, type PushRow } from '../current-remote.js';
import { isExporting } from '../provider.js';
import type { ProviderRegistry } from '../registry.js';
import { fingerprintNow } from '../remote-state.js';
import { loadOutgoingDive } from './outgoing-dive.js';

/** The providers whose dives this instance exports: only they have a fingerprint. */
const exporting = (registry: ProviderRegistry) => registry.list().filter((a) => isExporting(a) && a.capabilities.data.dives?.export);

/** A stored fingerprint that some input outside a Dive Revision changed: the next logbook read works it out again. */
const STALE = -1;
/** How many Dives' fingerprints a batch works out at once (a first start, a stale read): enough to hide the round trips. */
const AT_ONCE = 8;

async function eachAtMost<T>(items: T[], fn: (item: T) => Promise<void>): Promise<void> {
  for (let i = 0; i < items.length; i += AT_ONCE) await Promise.all(items.slice(i, i + AT_ONCE).map(fn));
}

/**
 * Remembers what the Provider would fingerprint the Dive as now. A Push records the fingerprint it sent with the Dive
 * version it read: that is the current one unless the Dive changed meanwhile, and then its newer version keeps the row
 * changed. So the send may overwrite an older row, which a delete at the Provider could have left behind.
 */
export async function rememberFingerprint(q: Queryable, value: {
  diveId: string; provider: string; diveVersion: number; fingerprint: string;
}): Promise<void> {
  const at = new Date();
  await q.insert(diveFingerprint).values({ ...value, updatedAt: at })
    .onConflictDoUpdate({
      target: [diveFingerprint.diveId, diveFingerprint.provider],
      set: { diveVersion: value.diveVersion, fingerprint: value.fingerprint, updatedAt: at },
      // A stale row may come from a change after the send read this Dive: leave it for the next read to work out.
      setWhere: sql`${diveFingerprint.diveVersion} <> ${STALE}`,
    });
}

/**
 * Recomputes the stored fingerprint for every Provider where the Dive has a current remote dive (a change that a Push
 * confirms makes no difference). Runs inside the change's transaction, so a list never sees a stale row for a change
 * that committed. A deleted Dive keeps no live chip; a restored one is refreshed by its own Revision.
 */
export async function refreshFingerprints(q: Queryable, registry: ProviderRegistry, diveId: string, options: { clearStale?: boolean } = {}): Promise<void> {
  const [target] = await q.select({ version: dive.version, diverId: dive.diverId, deletedAt: dive.deletedAt }).from(dive).where(eq(dive.id, diveId));
  // A deleted Dive keeps no live chip; its rows would only grow. A restore writes its own Revision.
  if (!target || target.deletedAt) {
    await q.delete(diveFingerprint).where(eq(diveFingerprint.diveId, diveId));
    return;
  }
  const pushes = await q.select().from(push).where(eq(push.diveId, diveId)).orderBy(desc(push.createdAt), desc(push.id));
  const current = new Map<string, PushRow>();
  for (const provider of new Set(pushes.map((p) => p.provider))) {
    const row = currentRemote(pushes.filter((p) => p.provider === provider));
    if (row) current.set(provider, row);
  }
  if (current.size === 0) {
    // No remote dive is left there (deleted or found gone): its stored fingerprint would never be used nor refreshed.
    await q.delete(diveFingerprint).where(eq(diveFingerprint.diveId, diveId));
    return;
  }
  // The outgoing Dive is the Diver's, not a User's: any manager may read it.
  const [manager] = await q.select({ userId: diverManagement.userId }).from(diverManagement).where(eq(diverManagement.diverId, target.diverId)).limit(1);
  if (!manager) return;
  const loaded = await loadOutgoingDive(q, manager.userId, diveId);
  const adapters = new Map(exporting(registry).map((a) => [a.id, a]));
  const at = new Date();
  const kept: string[] = [];
  for (const provider of current.keys()) {
    const adapter = adapters.get(provider);
    if (!adapter) continue;
    const fingerprint = fingerprintNow(adapter, loaded);
    if (fingerprint === null) continue;
    await q.insert(diveFingerprint).values({ diveId, provider, diveVersion: target.version, fingerprint, updatedAt: at })
      .onConflictDoUpdate({
        target: [diveFingerprint.diveId, diveFingerprint.provider],
        set: { diveVersion: target.version, fingerprint, updatedAt: at },
        ...(options.clearStale ? {} : { setWhere: sql`${diveFingerprint.diveVersion} <> ${STALE}` }),
      });
    kept.push(provider);
  }
  // A row at a Provider without a current remote dive is never compared nor refreshed; left behind, a stale one would
  // make every later list reload this Dive. Drop what the current remotes don't cover (a later Push writes its own).
  await q.delete(diveFingerprint).where(and(
    eq(diveFingerprint.diveId, diveId),
    kept.length === 0 ? undefined : notInArray(diveFingerprint.provider, kept),
  ));
}

/** Marks these Dives' stored fingerprints as needing a fresh comparison. */
async function markDivesStale(q: Queryable, diveIds: string[]): Promise<void> {
  if (diveIds.length === 0) return;
  await q.update(diveFingerprint).set({ diveVersion: STALE, updatedAt: new Date() })
    .where(inArray(diveFingerprint.diveId, diveIds));
}

/** Marks the Dives that carry these Divers as changed (a Diver's account or an account's list entry changed). */
export async function markFingerprintsStale(q: Queryable, diverIds: string[]): Promise<void> {
  if (diverIds.length === 0) return;
  const dives = await q.selectDistinct({ id: dive.id }).from(dive)
    .leftJoin(participant, eq(participant.diveId, dive.id))
    .where(and(isNull(dive.deletedAt), or(inArray(dive.diverId, diverIds), inArray(participant.diverId, diverIds))));
  await markDivesStale(q, dives.map((d) => d.id));
}

/** Marks every Dive at the site stale (a private ID that its sends use changed). */
export async function markSiteFingerprintsStale(q: Queryable, siteId: string): Promise<void> {
  const dives = await q.select({ id: dive.id }).from(dive).where(and(eq(dive.siteId, siteId), isNull(dive.deletedAt)));
  await markDivesStale(q, dives.map((d) => d.id));
}

/** Works out the stored fingerprints a stale mark left for these Divers' Dives (the list asks before its counts). */
export async function refreshStaleFingerprints(q: Queryable, registry: ProviderRegistry, diverIds: string[]): Promise<void> {
  if (diverIds.length === 0) return;
  const stale = await q.selectDistinct({ id: diveFingerprint.diveId }).from(diveFingerprint)
    .innerJoin(dive, eq(dive.id, diveFingerprint.diveId))
    .where(and(eq(diveFingerprint.diveVersion, STALE), inArray(dive.diverId, diverIds), isNull(dive.deletedAt)));
  await eachAtMost(stale, ({ id }) => refreshFingerprints(q, registry, id, { clearStale: true }));
}

/** Every Dive at the site: a site Revision can change what goes out (water type, its IDs), so refresh them all. */
export async function refreshSiteFingerprints(q: Queryable, registry: ProviderRegistry, siteId: string): Promise<void> {
  const dives = await q.select({ id: dive.id }).from(dive).where(and(eq(dive.siteId, siteId), isNull(dive.deletedAt)));
  await eachAtMost(dives, ({ id }) => refreshFingerprints(q, registry, id));
}

/**
 * Fills the stored fingerprints an older database starts without (a first start after this change), before the list
 * serves. Cheap when nothing is missing; a Dive whose row is missing counts as changed until this has run for it.
 */
export async function backfillFingerprints(db: Db, registry: ProviderRegistry): Promise<number> {
  const providers = exporting(registry).map((a) => a.id);
  if (providers.length === 0) return 0;
  const newest = sql`(select p2.id from ${push} p2 where p2.dive_id = ${push.diveId} and p2.provider = ${push.provider}
    and (p2.remote_gone or (p2.state = 'confirmed' and (p2.action = 'delete' or p2.remote_id is not null)))
    order by p2.created_at desc, p2.id desc limit 1)`;
  const missing = await db.selectDistinct({ id: dive.id }).from(dive)
    .innerJoin(push, and(eq(push.diveId, dive.id), eq(push.state, 'confirmed'), isNotNull(push.remoteId), inArray(push.provider, providers)))
    .leftJoin(diveFingerprint, and(eq(diveFingerprint.diveId, dive.id), eq(diveFingerprint.provider, push.provider)))
    .where(and(isNull(dive.deletedAt), isNull(diveFingerprint.diveId), sql`${push.id} = ${newest}`, sql`not ${push.remoteGone}`, sql`${push.action} <> 'delete'`));
  await eachAtMost(missing, ({ id }) => refreshFingerprints(db, registry, id));
  return missing.length;
}

/**
 * The chip's "changed since sent" as SQL (ADR 0040): the Dive has a current remote dive at a Provider the User is
 * connected to for its Diver, and the stored fingerprint differs from that Push's, the number differs and an update can
 * set it, or the stored row is missing or older than the Dive (a change its refresh did not see).
 */
export function changedSinceSentCount(registry: ProviderRegistry, userId: string): SQL {
  const providers = exporting(registry).map((a) => a.id);
  if (providers.length === 0) return sql`false`;
  const settable = exporting(registry).filter((a) => a.capabilities.data.dives?.export?.numbers?.set.includes('update')).map((a) => a.id);
  const numbers = settable.length === 0 ? sql`` : sql`or (c.follow_numbers and p.provider in (${sql.join(settable.map((id) => sql`${id}`), sql`, `)})
    and ${dive.number} is not null and p.remote_number is distinct from ${dive.number})`;
  return sql`exists (select 1 from ${push} p
    inner join ${connection} c on c.user_id = ${userId}::uuid and c.diver_id = ${dive.diverId} and c.provider = p.provider
    left join ${diveFingerprint} f on f.dive_id = ${dive.id} and f.provider = p.provider
    where p.dive_id = ${dive.id} and p.provider in (${sql.join(providers.map((id) => sql`${id}`), sql`, `)})
      and p.id = (select p2.id from ${push} p2 where p2.dive_id = p.dive_id and p2.provider = p.provider
        and (p2.remote_gone or (p2.state = 'confirmed' and (p2.action = 'delete' or p2.remote_id is not null)))
        order by p2.created_at desc, p2.id desc limit 1)
      and not p.remote_gone and p.action <> 'delete'
      and (f.fingerprint is null or f.dive_version <> ${dive.version} or f.fingerprint is distinct from p.fingerprint
        ${numbers}))`;
}
