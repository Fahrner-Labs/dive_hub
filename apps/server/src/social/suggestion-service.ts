// Buddy suggestions (ADR 0070): the row's life on its own, without a Request. A Participant added to a Dive raises a
// pending suggestion for the invited Diver; removing the Participant or soft-deleting the Dive cancels a pending one;
// a declined or cancelled one becomes pending again when the Participant is added back. The audience is never stored:
// every read and every hint asks diver_management who manages the Diver now, so claiming or merging re-targets it.
import { and, desc, eq, gte, inArray, isNull, lte } from 'drizzle-orm';
import type { Db, Queryable, Tx } from '../db/client.js';
import {
  PARTICIPANT_ROLES, buddySuggestion, dive, diveCentre, diveCentreSite, diveSite, diver, diverManagement, jointDive, participant,
} from '../db/schema.js';
import { audienceOfDiver, emitHint } from '../live/emit.js';
import { managementOf } from '../divers/management.js';
import { renumber } from '../divers/dive-numbers.js';
import { DAY_MS, MAX_OFFSET_MS, OVERLAP_TOLERANCE_SECONDS, alignedForMatching, overlapWindow, overlaps } from '../dives/matching.js';
import { writeRevision, type Actor } from '../revisions/revisions.js';

export class SuggestionError extends Error {
  constructor(readonly code:
    | 'suggestion_not_found'
    | 'suggestion_not_pending'
    | 'suggestion_dive_not_found'
    | 'suggestion_dive_not_overlapping'
    | 'suggestion_link_conflict') {
    super(code);
  }
}

type Span = {
  startsAt: Date; durationSeconds: number; maxDepthM: number | null;
  utcOffsetSeconds: number | null; utcOffsetSource: string;
};

/** The time span the suggestion shares, with the Dive's depth treated as unknown for matching. */
const spanOf = (row: Span) => ({
  startsAt: row.startsAt, durationSeconds: row.durationSeconds, maxDepthM: row.maxDepthM ?? undefined,
  utcOffsetSeconds: row.utcOffsetSeconds, utcOffsetSource: row.utcOffsetSource,
});

/**
 * The invited Diver's Dives that meet the suggested Dive's window, with the same tolerance and the same alignment for
 * unknown UTC offsets as Recording matching (`overlaps`, `alignedForMatching`, `OVERLAP_TOLERANCE_SECONDS`). The SQL
 * narrows by instant and widens for an unknown offset, as `imports/placement.ts` does; the ordered rows are what the
 * preview offers to link.
 */
async function overlappingDives(q: Queryable, diverId: string, span: Span) {
  const window = overlapWindow(spanOf(span));
  const rows = await q.select({
    id: dive.id, startsAt: dive.startsAt, utcOffsetSeconds: dive.utcOffsetSeconds, utcOffsetSource: dive.utcOffsetSource,
    durationSeconds: dive.durationSeconds, maxDepthM: dive.maxDepthM, jointDiveId: dive.jointDiveId,
    siteId: diveSite.id, siteName: diveSite.name,
  }).from(dive)
    .leftJoin(diveSite, eq(diveSite.id, dive.siteId))
    .where(and(
      eq(dive.diverId, diverId),
      isNull(dive.deletedAt),
      lte(dive.startsAt, new Date(window.to.getTime() + MAX_OFFSET_MS)),
      gte(dive.startsAt, new Date(window.from.getTime() - DAY_MS - MAX_OFFSET_MS)),
    ))
    .orderBy(dive.startsAt, dive.id);
  const mine = spanOf(span);
  const aligned = alignedForMatching(mine, rows);
  // Filter on the aligned start, but keep the Dive's own start for the view: its offset is its own, not the source's.
  return rows
    .filter((_, index) => overlaps(mine, spanOf(aligned[index]!), OVERLAP_TOLERANCE_SECONDS))
    .sort((a, b) => a.startsAt.getTime() - b.startsAt.getTime() || a.id.localeCompare(b.id));
}

const overlapView = (row: Awaited<ReturnType<typeof overlappingDives>>[number]) => ({
  id: row.id,
  startsAt: row.startsAt.toISOString(),
  utcOffsetSeconds: row.utcOffsetSeconds,
  durationSeconds: row.durationSeconds,
  maxDepthM: row.maxDepthM,
  site: row.siteId && row.siteName !== null ? { id: row.siteId, name: row.siteName } : null,
});

/**
 * Branch B of accepting (ADR 0070): link the chosen existing Dive into a Joint dive with the suggested one. Nothing is
 * copied and nothing is merged; both Dives keep their own Recording, notes and gear. A Dive belongs to at most one Joint
 * dive, so an existing one is reused; two different Joint dives cannot be joined from here.
 */
async function acceptLinked(tx: Tx, args: {
  id: string; suggestion: typeof buddySuggestion.$inferSelect; source: typeof dive.$inferSelect; chosenDiveId: string;
}) {
  const { suggestion, source } = args;
  const [target] = await tx.select({
    id: dive.id, startsAt: dive.startsAt, durationSeconds: dive.durationSeconds, maxDepthM: dive.maxDepthM,
    utcOffsetSeconds: dive.utcOffsetSeconds, utcOffsetSource: dive.utcOffsetSource, jointDiveId: dive.jointDiveId,
  }).from(dive).where(and(eq(dive.id, args.chosenDiveId), eq(dive.diverId, suggestion.diverId), isNull(dive.deletedAt)))
    // Lock the Dive we link: another acceptance could otherwise pick the same Dive for a different Joint dive.
    .for('update');
  if (!target) throw new SuggestionError('suggestion_dive_not_found');
  const [aligned] = alignedForMatching(spanOf(source), [target]);
  if (!overlaps(spanOf(source), spanOf(aligned!), OVERLAP_TOLERANCE_SECONDS)) throw new SuggestionError('suggestion_dive_not_overlapping');
  if (source.jointDiveId && target.jointDiveId && source.jointDiveId !== target.jointDiveId) {
    throw new SuggestionError('suggestion_link_conflict');
  }

  // Claim the pending row before writing anything: a second manager's accept waits on this lock, then finds it taken.
  const at = new Date();
  const [made] = await tx.update(buddySuggestion).set({ status: 'accepted', resolvedAt: at })
    .where(and(eq(buddySuggestion.id, args.id), eq(buddySuggestion.status, 'pending')))
    .returning({ id: buddySuggestion.id });
  if (!made) throw new SuggestionError('suggestion_not_pending');

  const jointDiveId = source.jointDiveId ?? target.jointDiveId
    ?? (await tx.insert(jointDive).values({}).returning({ id: jointDive.id }))[0]!.id;
  if (source.jointDiveId !== jointDiveId) await tx.update(dive).set({ jointDiveId }).where(eq(dive.id, source.id));
  if (target.jointDiveId !== jointDiveId) await tx.update(dive).set({ jointDiveId }).where(eq(dive.id, target.id));
  await emitSuggestionHints(tx, [{ id: args.id, diverId: suggestion.diverId }]);

  return { id: args.id, status: 'accepted' as const, resolvedAt: at.toISOString(), diveId: target.id, jointDiveId };
}

/** The suggestion's own changes, each with the id of the Diver whose Users hear about it. */
async function emitSuggestionHints(q: Queryable, changed: { id: string; diverId: string }[]): Promise<void> {
  for (const suggestion of changed) {
    await emitHint(q, {
      kind: 'buddySuggestion', id: suggestion.id, audience: await audienceOfDiver(q, suggestion.diverId),
    });
  }
}

/**
 * Raises, re-raises and cancels the Dive's Buddy suggestions from its Participants as they are now. Runs in the caller's
 * transaction, right after the Participants (or the Dive's deletion) were written; idempotent, so every path that writes
 * Participants can rely on the Revision's change listener to call it.
 */
export async function syncSuggestions(q: Queryable, diveId: string): Promise<void> {
  const [current] = await q.select({ diverId: dive.diverId, deletedAt: dive.deletedAt })
    .from(dive).where(eq(dive.id, diveId));
  if (!current) return;
  const existing = await q.select().from(buddySuggestion).where(eq(buddySuggestion.diveId, diveId));

  // A deleted Dive's pending suggestions are cancelled; accepted and declined ones stay as they are.
  if (current.deletedAt) {
    const cancelled = await q.update(buddySuggestion).set({ status: 'cancelled', resolvedAt: new Date() })
      .where(and(eq(buddySuggestion.diveId, diveId), eq(buddySuggestion.status, 'pending')))
      .returning({ id: buddySuggestion.id, diverId: buddySuggestion.diverId });
    await emitSuggestionHints(q, cancelled);
    return;
  }

  const people = await q.select({ diverId: participant.diverId }).from(participant).where(eq(participant.diveId, diveId));
  const invited = new Set(people.map((p) => p.diverId).filter((id) => id !== current.diverId));
  const known = new Map(existing.map((s) => [s.diverId, s]));

  for (const diverId of invited) {
    const row = known.get(diverId);
    if (!row) {
      // The Dive row is locked by every Participant write; a conflict here is another process's identical raise.
      const [created] = await q.insert(buddySuggestion).values({ diveId, diverId }).onConflictDoNothing()
        .returning({ id: buddySuggestion.id, diverId: buddySuggestion.diverId });
      if (created) await emitSuggestionHints(q, [created]);
    } else if (row.status === 'declined' || row.status === 'cancelled') {
      const [raised] = await q.update(buddySuggestion).set({ status: 'pending', resolvedAt: null })
        .where(and(eq(buddySuggestion.id, row.id), inArray(buddySuggestion.status, ['declined', 'cancelled'])))
        .returning({ id: buddySuggestion.id, diverId: buddySuggestion.diverId });
      if (raised) await emitSuggestionHints(q, [raised]);
    }
  }

  const gone = existing.filter((s) => s.status === 'pending' && !invited.has(s.diverId)).map((s) => s.diverId);
  if (gone.length > 0) {
    const cancelled = await q.update(buddySuggestion).set({ status: 'cancelled', resolvedAt: new Date() })
      .where(and(eq(buddySuggestion.diveId, diveId), eq(buddySuggestion.status, 'pending'), inArray(buddySuggestion.diverId, gone)))
      .returning({ id: buddySuggestion.id, diverId: buddySuggestion.diverId });
    await emitSuggestionHints(q, cancelled);
  }
}

/** What the Review inbox shows: the invited Diver and enough of the Dive's shared facts to recognise it. */
const summaryOf = (row: {
  id: string; status: (typeof buddySuggestion.$inferSelect)['status']; createdAt: Date; resolvedAt: Date | null;
  diverId: string; diverName: string; diveId: string; startsAt: Date; utcOffsetSeconds: number | null;
  durationSeconds: number; maxDepthM: number | null; siteId: string | null; siteName: string | null;
}) => ({
  id: row.id,
  status: row.status,
  createdAt: row.createdAt.toISOString(),
  resolvedAt: row.resolvedAt?.toISOString() ?? null,
  diver: { id: row.diverId, name: row.diverName },
  dive: {
    id: row.diveId,
    startsAt: row.startsAt.toISOString(),
    utcOffsetSeconds: row.utcOffsetSeconds,
    durationSeconds: row.durationSeconds,
    maxDepthM: row.maxDepthM,
    site: row.siteId && row.siteName !== null ? { id: row.siteId, name: row.siteName } : null,
  },
});

export function createSuggestionService(db: Db) {
  return {
    /** The pending suggestions for the Divers this User manages now, newest first. */
    async list(userId: string) {
      const rows = await db.select({
        id: buddySuggestion.id, status: buddySuggestion.status, createdAt: buddySuggestion.createdAt,
        resolvedAt: buddySuggestion.resolvedAt, diverId: buddySuggestion.diverId, diverName: diver.name,
        diveId: dive.id, startsAt: dive.startsAt, utcOffsetSeconds: dive.utcOffsetSeconds,
        durationSeconds: dive.durationSeconds, maxDepthM: dive.maxDepthM, siteId: diveSite.id, siteName: diveSite.name,
      }).from(buddySuggestion)
        .innerJoin(diverManagement, and(eq(diverManagement.diverId, buddySuggestion.diverId), eq(diverManagement.userId, userId)))
        .innerJoin(dive, and(eq(dive.id, buddySuggestion.diveId), isNull(dive.deletedAt)))
        .innerJoin(diver, and(eq(diver.id, buddySuggestion.diverId), isNull(diver.deletedAt)))
        .leftJoin(diveSite, eq(diveSite.id, dive.siteId))
        .where(eq(buddySuggestion.status, 'pending'))
        .orderBy(desc(buddySuggestion.createdAt), buddySuggestion.id);
      return rows.map(summaryOf);
    },

    /** One suggestion with the facts a later slice may copy or show: never a Recording, notes, suit or anything under overrides. */
    async preview(userId: string, id: string) {
      const [row] = await db.select({
        suggestion: buddySuggestion, dive, owner: { id: diver.id, name: diver.name },
      }).from(buddySuggestion)
        .innerJoin(dive, and(eq(dive.id, buddySuggestion.diveId), isNull(dive.deletedAt)))
        .innerJoin(diver, eq(diver.id, dive.diverId))
        .where(eq(buddySuggestion.id, id));
      if (!row) throw new SuggestionError('suggestion_not_found');
      if (!(await managementOf(db, userId, row.suggestion.diverId))) throw new SuggestionError('suggestion_not_found');

      const [invited] = await db.select({ name: diver.name }).from(diver)
        .where(and(eq(diver.id, row.suggestion.diverId), isNull(diver.deletedAt)));
      if (!invited) throw new SuggestionError('suggestion_not_found');
      const [site] = row.dive.siteId
        ? await db.select({ id: diveSite.id, name: diveSite.name }).from(diveSite).where(eq(diveSite.id, row.dive.siteId))
        : [];
      const people = await db.select({ diverId: participant.diverId, name: diver.name, role: participant.role })
        .from(participant).innerJoin(diver, eq(diver.id, participant.diverId))
        .where(and(eq(participant.diveId, row.dive.id), isNull(diver.deletedAt)));
      people.sort((a, b) => PARTICIPANT_ROLES.indexOf(a.role) - PARTICIPANT_ROLES.indexOf(b.role)
        || a.name.localeCompare(b.name) || a.diverId.localeCompare(b.diverId));
      const centres = row.dive.siteId
        ? (await db.select({ id: diveCentre.id, name: diveCentre.name }).from(diveCentreSite)
          .innerJoin(diveCentre, eq(diveCentre.id, diveCentreSite.centreId))
          .where(and(eq(diveCentreSite.siteId, row.dive.siteId), isNull(diveCentre.deletedAt))))
          .sort((a, b) => a.name.localeCompare(b.name) || a.id.localeCompare(b.id))
        : [];
      const overlapsFound = await overlappingDives(db, row.suggestion.diverId, row.dive);

      return {
        ...summaryOf({
          id: row.suggestion.id, status: row.suggestion.status, createdAt: row.suggestion.createdAt,
          resolvedAt: row.suggestion.resolvedAt, diverId: row.suggestion.diverId, diverName: invited.name,
          diveId: row.dive.id, startsAt: row.dive.startsAt, utcOffsetSeconds: row.dive.utcOffsetSeconds,
          durationSeconds: row.dive.durationSeconds, maxDepthM: row.dive.maxDepthM,
          siteId: site?.id ?? null, siteName: site?.name ?? null,
        }),
        dive: {
          id: row.dive.id,
          startsAt: row.dive.startsAt.toISOString(),
          utcOffsetSeconds: row.dive.utcOffsetSeconds,
          durationSeconds: row.dive.durationSeconds,
          maxDepthM: row.dive.maxDepthM,
          avgDepthM: row.dive.avgDepthM,
          site: site ?? null,
          owner: row.owner,
          centres,
          participants: people,
        },
        overlaps: overlapsFound.map(overlapView),
      };
    },

    /**
     * Accepting (ADR 0070), in one of two ways. With `chosenDiveId` it is branch B: link one of the invited Diver's
     * existing overlapping Dives and the suggested one into one Joint dive, copying and merging nothing. Without it, branch
     * A: copy the shared facts into a new Dive for the invited Diver. The copy is an ordinary, Recording-less Dive like a
     * Provider entry: its depths are plain own values (never Overrides), so a later Recording wins through the existing
     * `followPrimary` path.
     */
    async accept(userId: string, id: string, chosenDiveId?: string) {
      return db.transaction(async (tx) => {
        const [row] = await tx.select({ suggestion: buddySuggestion, source: dive })
          .from(buddySuggestion)
          .innerJoin(dive, and(eq(dive.id, buddySuggestion.diveId), isNull(dive.deletedAt)))
          .where(eq(buddySuggestion.id, id))
          .for('update', { of: [buddySuggestion, dive] });
        if (!row || !(await managementOf(tx, userId, row.suggestion.diverId))) throw new SuggestionError('suggestion_not_found');
        if (row.suggestion.status !== 'pending') throw new SuggestionError('suggestion_not_pending');
        const [invited] = await tx.select({ id: diver.id }).from(diver)
          .where(and(eq(diver.id, row.suggestion.diverId), isNull(diver.deletedAt)));
        if (!invited) throw new SuggestionError('suggestion_not_found');

        if (chosenDiveId) {
          return acceptLinked(tx, { id, suggestion: row.suggestion, source: row.source, chosenDiveId });
        }

        // Claim the pending row before writing anything: a second manager's accept waits on this lock, then finds it taken.
        const at = new Date();
        const [made] = await tx.update(buddySuggestion).set({ status: 'accepted', resolvedAt: at })
          .where(and(eq(buddySuggestion.id, id), eq(buddySuggestion.status, 'pending')))
          .returning({ id: buddySuggestion.id });
        if (!made) throw new SuggestionError('suggestion_not_pending');

        // A Dive belongs to at most one Joint dive: a second acceptance on the same source joins the one already there.
        const jointDiveId = row.source.jointDiveId
          ?? (await tx.insert(jointDive).values({}).returning({ id: jointDive.id }))[0]!.id;
        if (!row.source.jointDiveId) await tx.update(dive).set({ jointDiveId }).where(eq(dive.id, row.source.id));

        const [created] = await tx.insert(dive).values({
          diverId: row.suggestion.diverId,
          startsAt: row.source.startsAt,
          utcOffsetSeconds: row.source.utcOffsetSeconds,
          utcOffsetSource: row.source.utcOffsetSource,
          durationSeconds: row.source.durationSeconds,
          maxDepthM: row.source.maxDepthM,
          avgDepthM: row.source.avgDepthM,
          siteId: row.source.siteId,
          jointDiveId,
        }).returning({ id: dive.id });

        // Everyone on the source Dive becomes a Participant of the copy, its own Diver included (on the copy they are all
        // just Divers); the invited Diver is the copy's own and stays out.
        const sourcePeople = await tx.select({ diverId: participant.diverId, role: participant.role })
          .from(participant).where(eq(participant.diveId, row.source.id));
        const people = new Map<string, { diverId: string; role: (typeof PARTICIPANT_ROLES)[number] }>();
        if (row.source.diverId !== row.suggestion.diverId) {
          people.set(row.source.diverId, { diverId: row.source.diverId, role: 'buddy' });
        }
        for (const person of sourcePeople) if (person.diverId !== row.suggestion.diverId) people.set(person.diverId, person);
        if (people.size > 0) {
          await tx.insert(participant).values([...people.values()].map((person) => ({ diveId: created!.id, ...person })));
        }
        const names = people.size === 0 ? [] : await tx.select({ id: diver.id, name: diver.name }).from(diver)
          .where(inArray(diver.id, [...people.keys()]));

        // The acceptance is the hub's, not a User's (docs/spec/data-model.md): the shared facts were copied, not typed.
        const actor: Actor = { type: 'system', id: 'buddy-suggestion-acceptance' };
        await writeRevision(tx, 'dive', created!.id, actor, 'buddy-accept', {
          ...(people.size > 0 && {
            participants: {
              from: [],
              to: [...people.values()].map((person) => ({ ...person, name: names.find((n) => n.id === person.diverId)!.name })),
            },
          }),
        });
        await renumber(tx, [row.suggestion.diverId]);
        await emitSuggestionHints(tx, [{ id, diverId: row.suggestion.diverId }]);

        return { id, status: 'accepted' as const, resolvedAt: at.toISOString(), diveId: created!.id, jointDiveId };
      });
    },

    /** Declining (ADR 0070): the one suggestion row becomes declined for every User who manages the invited Diver now. */
    async decline(userId: string, id: string) {
      return db.transaction(async (tx) => {
        const [row] = await tx.select({ suggestion: buddySuggestion })
          .from(buddySuggestion)
          .innerJoin(dive, and(eq(dive.id, buddySuggestion.diveId), isNull(dive.deletedAt)))
          .where(eq(buddySuggestion.id, id))
          .for('update', { of: [buddySuggestion, dive] });
        if (!row || !(await managementOf(tx, userId, row.suggestion.diverId))) throw new SuggestionError('suggestion_not_found');
        if (row.suggestion.status !== 'pending') throw new SuggestionError('suggestion_not_pending');

        const at = new Date();
        const [declined] = await tx.update(buddySuggestion).set({ status: 'declined', resolvedAt: at })
          .where(and(eq(buddySuggestion.id, id), eq(buddySuggestion.status, 'pending')))
          .returning({ id: buddySuggestion.id });
        if (!declined) throw new SuggestionError('suggestion_not_pending');
        await emitSuggestionHints(tx, [{ id, diverId: row.suggestion.diverId }]);

        return { id, status: 'declined' as const, resolvedAt: at.toISOString() };
      });
    },
  };
}

export type SuggestionService = ReturnType<typeof createSuggestionService>;
