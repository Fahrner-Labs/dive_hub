// Fill offers for the shared facts of a Joint dive (FAH-411, ADR 0070). Offers are derived at read time from the
// members' Dives and only dismissals are stored, so accepting, editing a field by hand, or the source changing or going
// away all resolve an offer without any state to keep in step. The fields are the shared facts ADR 0070 already shows in
// a Buddy suggestion's preview: the site, the start with its UTC offset, the duration and the max/average depth. A
// Recording, notes, suit, weighting, cylinders, lead, overrides and provider links are individual and never read here.
import { and, desc, eq, isNotNull, isNull } from 'drizzle-orm';
import type { Db, Queryable, Tx } from '../db/client.js';
import {
  JOINT_FILL_FIELDS, dive, diveSite, diver, diverManagement, jointDive, jointDiveDismissal, type JointFillField,
} from '../db/schema.js';
import { applyToDive } from '../dives/dive-service.js';
import { valuesOfDive } from '../dives/dive-values.js';
import { managesDiveDiver } from '../divers/management.js';
import { audienceOfDiver, emitHint } from '../live/emit.js';
import type { Actor } from '../revisions/revisions.js';

export class OfferError extends Error {
  constructor(readonly code: 'dive_not_found' | 'joint_offer_not_valid') {
    super(code);
  }
}

/** The shared facts of one member of a Joint dive; nothing individual is selected. */
type Member = {
  id: string;
  diverId: string;
  diverName: string;
  startsAt: Date;
  utcOffsetSeconds: number | null;
  durationSeconds: number;
  maxDepthM: number | null;
  avgDepthM: number | null;
  siteId: string | null;
  siteName: string | null;
  updatedAt: Date;
};

/** A member with the fields of one receiving Dive that only it can fill, newest filled value first. */
type Derived = { source: Member; fields: JointFillField[] };

/**
 * Whether the receiving Dive still lacks the field. The start and the duration are not-null columns: a Dive always has a
 * descent's time and length, so those two never qualify. The site and the depths can be unknown (null), which is what a
 * fill offer exists for.
 */
function isEmpty(member: Member, field: JointFillField): boolean {
  switch (field) {
    case 'siteId': return member.siteId === null;
    case 'startsAt': return false;
    case 'durationSeconds': return false;
    case 'maxDepthM': return member.maxDepthM === null;
    case 'avgDepthM': return member.avgDepthM === null;
  }
}

/** The live members of a Joint dive, the one whose value was written most recently first (the offer source wins by age). */
async function membersOf(q: Queryable, jointDiveId: string): Promise<Member[]> {
  return q.select({
    id: dive.id,
    diverId: dive.diverId,
    diverName: diver.name,
    startsAt: dive.startsAt,
    utcOffsetSeconds: dive.utcOffsetSeconds,
    durationSeconds: dive.durationSeconds,
    maxDepthM: dive.maxDepthM,
    avgDepthM: dive.avgDepthM,
    siteId: dive.siteId,
    siteName: diveSite.name,
    updatedAt: dive.updatedAt,
  }).from(dive)
    .innerJoin(diver, eq(diver.id, dive.diverId))
    .leftJoin(diveSite, eq(diveSite.id, dive.siteId))
    .where(and(eq(dive.jointDiveId, jointDiveId), isNull(dive.deletedAt)))
    .orderBy(desc(dive.updatedAt), dive.id);
}

/** The fields this Dive's User said no to for this Joint dive. */
async function dismissedFields(q: Queryable, jointDiveId: string, diveId: string): Promise<Set<JointFillField>> {
  const rows = await q.select({ field: jointDiveDismissal.field }).from(jointDiveDismissal)
    .where(and(eq(jointDiveDismissal.jointDiveId, jointDiveId), eq(jointDiveDismissal.diveId, diveId)));
  return new Set(rows.map((row) => row.field));
}

/**
 * Diffs one member against the others: a field is offered where the member lacks it and another member has a value. The
 * newest filled value is the source, so with more than two members a field's source is whichever member wrote it last.
 * Differing values (both sides filled) are never offered.
 */
function offersFor(members: Member[], mine: Member, dismissed: Set<JointFillField>): Derived[] {
  const others = members.filter((member) => member.id !== mine.id);
  const bySource = new Map<string, Derived>();
  for (const field of JOINT_FILL_FIELDS) {
    if (!isEmpty(mine, field) || dismissed.has(field)) continue;
    const source = others.find((member) => !isEmpty(member, field));
    if (!source) continue;
    const derived = bySource.get(source.id) ?? { source, fields: [] };
    derived.fields.push(field);
    bySource.set(source.id, derived);
  }
  return [...bySource.values()].sort((a, b) =>
    a.source.diverName.localeCompare(b.source.diverName) || a.source.id.localeCompare(b.source.id));
}

const siteOf = (member: Member) => (member.siteId && member.siteName !== null ? { id: member.siteId, name: member.siteName } : null);

/** The shared facts as an offer shows them; the individual list above is deliberately absent. */
const factsOf = (member: Member) => ({
  startsAt: member.startsAt.toISOString(),
  utcOffsetSeconds: member.utcOffsetSeconds,
  durationSeconds: member.durationSeconds,
  maxDepthM: member.maxDepthM,
  avgDepthM: member.avgDepthM,
  site: siteOf(member),
});

const offerView = (mine: Member, derived: Derived) => ({
  dive: { id: mine.id, ...factsOf(mine) },
  source: { diveId: derived.source.id, diver: { id: derived.source.diverId, name: derived.source.diverName }, ...factsOf(derived.source) },
  fields: derived.fields,
});

/**
 * Tells every member's Users that the Joint dive's offers may have changed (a shared field was edited, a member left,
 * or a Dive joined). The hint names the Joint dive only; clients refetch the offers as every hint does (ADR 0066).
 */
export async function emitJointOfferHint(q: Queryable, jointDiveId: string): Promise<void> {
  const rows = await q.select({ diverId: dive.diverId }).from(dive)
    .where(and(eq(dive.jointDiveId, jointDiveId), isNull(dive.deletedAt)));
  if (rows.length < 2) return;
  const audience = (await Promise.all(rows.map((row) => audienceOfDiver(q, row.diverId)))).flat();
  await emitHint(q, { kind: 'jointDiveOffer', id: jointDiveId, audience });
}

/**
 * A soft-deleted Dive leaves its Joint dive (FAH-411): it keeps no dangling link, its dismissals go, and a Joint dive
 * left with fewer than two live members is deleted, taking the remaining member's link and every dismissal with it.
 * Returns the Joint dive's id when it still has two members, else null (no offers to announce).
 */
export async function detachDeletedJointDive(tx: Tx, diveId: string): Promise<string | null> {
  const [row] = await tx.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, diveId));
  if (!row?.jointDiveId) return null;
  const jointDiveId = row.jointDiveId;
  await tx.update(dive).set({ jointDiveId: null }).where(eq(dive.id, diveId));
  await tx.delete(jointDiveDismissal)
    .where(and(eq(jointDiveDismissal.jointDiveId, jointDiveId), eq(jointDiveDismissal.diveId, diveId)));
  const remaining = await tx.select({ id: dive.id }).from(dive)
    .where(and(eq(dive.jointDiveId, jointDiveId), isNull(dive.deletedAt)));
  if (remaining.length >= 2) return jointDiveId;
  if (remaining.length === 1) {
    await tx.update(dive).set({ jointDiveId: null }).where(eq(dive.id, remaining[0]!.id));
  }
  await tx.delete(jointDive).where(eq(jointDive.id, jointDiveId));
  return null;
}

export function createOfferService(db: Db) {
  return {
    /** The fill offers for every Dive of a Joint dive the signed-in User manages, one per source Dive. */
    async list(userId: string) {
      const rows = await db.select({ id: dive.id, jointDiveId: dive.jointDiveId }).from(dive)
        .innerJoin(diverManagement, and(eq(diverManagement.diverId, dive.diverId), eq(diverManagement.userId, userId)))
        .where(and(isNotNull(dive.jointDiveId), isNull(dive.deletedAt)))
        .orderBy(dive.startsAt, dive.id);
      const views = [];
      for (const row of rows) {
        const members = await membersOf(db, row.jointDiveId!);
        const mine = members.find((member) => member.id === row.id);
        if (!mine || members.length < 2) continue;
        const dismissed = await dismissedFields(db, row.jointDiveId!, row.id);
        views.push(...offersFor(members, mine, dismissed).map((derived) => offerView(mine, derived)));
      }
      return views;
    },

    /** The fill offers for one Dive the signed-in User manages; another User's Dive answers `dive_not_found`. */
    async forDive(userId: string, diveId: string) {
      const [row] = await db.select({ jointDiveId: dive.jointDiveId }).from(dive)
        .innerJoin(diverManagement, managesDiveDiver(userId))
        .where(and(eq(dive.id, diveId), isNull(dive.deletedAt)));
      if (!row) throw new OfferError('dive_not_found');
      if (!row.jointDiveId) return [];
      const members = await membersOf(db, row.jointDiveId);
      const mine = members.find((member) => member.id === diveId);
      if (!mine || members.length < 2) return [];
      const dismissed = await dismissedFields(db, row.jointDiveId, diveId);
      return offersFor(members, mine, dismissed).map((derived) => offerView(mine, derived));
    },

    /**
     * Takes the current values of the named fields from the offering members (never a snapshot) and writes them through
     * the normal Revision machinery as the hub's `joint-dive-fill`. The Dive is locked first: a field filled meanwhile, a
     * Dive no longer in a Joint dive, or a source that no longer has a value is `joint_offer_not_valid` (409).
     */
    async accept(userId: string, diveId: string, fields: JointFillField[]) {
      return db.transaction(async (tx) => {
        const [row] = await tx.select({ d: dive }).from(dive)
          .innerJoin(diverManagement, managesDiveDiver(userId))
          .where(and(eq(dive.id, diveId), isNull(dive.deletedAt)))
          .for('update', { of: dive });
        if (!row) throw new OfferError('dive_not_found');
        const current = row.d;
        if (!current.jointDiveId) throw new OfferError('joint_offer_not_valid');
        const members = await membersOf(tx, current.jointDiveId);
        const mine = members.find((member) => member.id === diveId);
        if (!mine || members.length < 2) throw new OfferError('joint_offer_not_valid');
        const others = members.filter((member) => member.id !== diveId);

        const values = valuesOfDive(current);
        const overrides = new Set(current.overrides);
        let siteId = current.siteId;
        let siteName = mine.siteName;
        for (const field of fields) {
          if (!isEmpty(mine, field)) throw new OfferError('joint_offer_not_valid');
          const source = others.find((member) => !isEmpty(member, field));
          if (!source) throw new OfferError('joint_offer_not_valid');
          switch (field) {
            case 'siteId':
              siteId = source.siteId!;
              siteName = source.siteName;
              break;
            case 'startsAt':
              values.startsAt = { at: source.startsAt, utcOffsetSeconds: source.utcOffsetSeconds };
              overrides.add('startsAt');
              break;
            case 'durationSeconds':
              values.durationSeconds = source.durationSeconds;
              overrides.add('durationSeconds');
              break;
            case 'maxDepthM':
              values.maxDepthM = source.maxDepthM;
              overrides.add('maxDepthM');
              break;
            case 'avgDepthM':
              values.avgDepthM = source.avgDepthM;
              overrides.add('avgDepthM');
              break;
          }
        }

        const actor: Actor = { type: 'system', id: 'joint-dive-fill' };
        await applyToDive(tx, current, {
          values, overrides: [...overrides], ...(siteId !== current.siteId && { siteId }),
        }, actor, 'joint-dive-fill');

        return {
          diveId,
          fields,
          version: current.version + 1,
          values: {
            startsAt: values.startsAt.at.toISOString(),
            utcOffsetSeconds: values.startsAt.utcOffsetSeconds,
            durationSeconds: values.durationSeconds,
            maxDepthM: values.maxDepthM,
            avgDepthM: values.avgDepthM,
            site: siteId && siteName !== null ? { id: siteId, name: siteName } : null,
          },
        };
      });
    },

    /** Says no to these fields for this Dive of the Joint dive: a stored dismissal the derivation then leaves out. */
    async decline(userId: string, diveId: string, fields: JointFillField[]) {
      return db.transaction(async (tx) => {
        const [row] = await tx.select({ jointDiveId: dive.jointDiveId }).from(dive)
          .innerJoin(diverManagement, managesDiveDiver(userId))
          .where(and(eq(dive.id, diveId), isNull(dive.deletedAt)))
          .for('update', { of: dive });
        if (!row) throw new OfferError('dive_not_found');
        if (!row.jointDiveId) throw new OfferError('joint_offer_not_valid');
        await tx.insert(jointDiveDismissal)
          .values(fields.map((field) => ({ jointDiveId: row.jointDiveId!, diveId, field })))
          .onConflictDoNothing();
        return { diveId, fields };
      });
    },
  };
}

export type OfferService = ReturnType<typeof createOfferService>;
