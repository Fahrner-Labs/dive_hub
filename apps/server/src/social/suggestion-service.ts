// Buddy suggestions (ADR 0070): the row's life on its own, without a Request. A Participant added to a Dive raises a
// pending suggestion for the invited Diver; removing the Participant or soft-deleting the Dive cancels a pending one;
// a declined or cancelled one becomes pending again when the Participant is added back. The audience is never stored:
// every read and every hint asks diver_management who manages the Diver now, so claiming or merging re-targets it.
import { and, desc, eq, inArray, isNull } from 'drizzle-orm';
import type { Db, Queryable } from '../db/client.js';
import {
  PARTICIPANT_ROLES, buddySuggestion, dive, diveCentre, diveCentreSite, diveSite, diver, diverManagement, participant,
} from '../db/schema.js';
import { audienceOfDiver, emitHint } from '../live/emit.js';
import { managementOf } from '../divers/management.js';

export class SuggestionError extends Error {
  constructor(readonly code: 'suggestion_not_found') {
    super(code);
  }
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
      };
    },
  };
}

export type SuggestionService = ReturnType<typeof createSuggestionService>;
