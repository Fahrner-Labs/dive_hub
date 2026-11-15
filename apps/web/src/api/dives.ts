import { keepPreviousData, queryOptions } from '@tanstack/react-query';
import { neighbourParams, type LogbookParams } from '../lib/logbook.ts';
import { liveHealthy } from '../lib/live.ts';
import { pollUntilDue } from '../lib/waiting-push.ts';
import { keys } from './keys.ts';
import { api, request, type Ok } from './request.ts';

/** A page of the logbook with its counts, totals and months (ADR 0040). */
export type LogbookPage = Ok<'/api/dives'>;
export type DiveSummary = LogbookPage['dives'][number];
export type DiveView = Ok<'/api/dives/{id}'>;
export type DiveValues = DiveView['values'];
export type OverridableField = DiveView['overrides'][number];
export type RevisionView = Ok<'/api/dives/{id}/revisions'>[number];
export type RecordingSummary = DiveView['recordings'][number]['summary'];
export type CandidateView = Ok<'/api/duplicate-candidates'>[number];
/** A pending Buddy suggestion in the Review inbox, and the shared facts its preview shows (ADR 0070). */
export type BuddySuggestionView = Ok<'/api/buddy-suggestions'>[number];
export type BuddySuggestionPreviewView = Ok<'/api/buddy-suggestions/{id}'>;
export type BuddySuggestionOverlapView = BuddySuggestionPreviewView['overlaps'][number];
export type AcceptedBuddySuggestionView = Ok<'/api/buddy-suggestions/{id}/accept', 'post'>;
type AnyLogbookCheck = Ok<'/api/logbook-checks'>[number];
/** A logbook check about two Dives (ADR 0038). */
export type LogbookCheckView = AnyLogbookCheck & { rule: Exclude<AnyLogbookCheck['rule'], 'short_shallow_dive'>; other: NonNullable<AnyLogbookCheck['other']> };
/** A logbook check about one Dive: probably no dive, to delete or to keep. */
export type DiveCheckView = AnyLogbookCheck & { rule: 'short_shallow_dive'; other: null };
export const isPairCheck = (c: AnyLogbookCheck): c is LogbookCheckView => c.other !== null;
export const isDiveCheck = (c: AnyLogbookCheck): c is DiveCheckView => c.other === null;
export type MergeCandidateView = Ok<'/api/dives/{id}/merge-candidates'>[number];
/** The Dive before this one it may be appended to as its continuation (ADR 0063). */
export type ContinuationCandidateView = NonNullable<Ok<'/api/dives/{id}/continuation-candidate'>>;
export type DeletedDiveView = Ok<'/api/dives/deleted'>[number];
export type ParticipantView = DiveView['participants'][number];
export type ParticipantRole = ParticipantView['role'];
export type UtcOffsetSource = DiveView['utcOffsetSource'];
export type AssessmentView = Ok<'/api/dives/{id}/assessment'>;

export const PAGE_SIZE = 25;

/** One page of the logbook. The previous page stays on screen while the next one loads. */
export const divesQuery = (p: LogbookParams = {}) => queryOptions({
  queryKey: [...keys.dives, { ...p }],
  queryFn: async () => request(api.GET('/api/dives', {
    params: { query: { ...neighbourParams(p), limit: PAGE_SIZE, offset: ((p.page ?? 1) - 1) * PAGE_SIZE } },
  })),
  placeholderData: keepPreviousData,
  // The channel carries new Dives; without it, the list falls back to asking again (ADR 0066). Never while healthy.
  refetchInterval: () => (liveHealthy() ? false : 5_000),
});

/**
 * The logbook's list pages (`['dives', { …params }]`), not a Dive's own queries: for what changes how a row looks (a
 * send, a number, a Waiting push settled) without changing the Dive.
 */
export const diveListPages = { queryKey: keys.dives, predicate: (q: { queryKey: readonly unknown[] }) => typeof q.queryKey[1] === 'object' && q.queryKey[1] !== null };

/**
 * Every query but the given Dives' own (`['dives', id, …]`): for Dives that are gone (deleted, merged away, moved as a copy),
 * whose queries are removed and would only answer "not found", while every list and count changes.
 */
export const allButDive = (...ids: string[]) => ({ predicate: (q: { queryKey: readonly unknown[] }) => !(q.queryKey[0] === keys.dives[0] && ids.includes(q.queryKey[1] as string)) });

/**
 * The User's deleted Dives, to restore them, and which Providers they are still at (ADR 0026, 0027); a delete at a
 * Provider that waits is asked about again when it is due (ADR 0056).
 */
export const deletedDivesQuery = () => queryOptions({
  queryKey: keys.deletedDives, queryFn: async () => request(api.GET('/api/dives/deleted')),
  refetchInterval: (q) => (liveHealthy() ? false : pollUntilDue(q.state.data?.flatMap((d) => d.waiting), Date.now())),
});

/** Pairs of Dives that can't both be right as they stand, or those the User said are two dives (ADR 0038). */
export const logbookChecksQuery = (status: 'open' | 'answered') =>
  queryOptions({ queryKey: keys.logbookChecks(status), queryFn: async () => request(api.GET('/api/logbook-checks', { params: { query: { status } } })) });
/** The Dives of the same Diver at the same time as this one, which it may be merged with (ADR 0038). */
export const mergeCandidatesQuery = (id: string) => queryOptions({
  queryKey: keys.mergeCandidates(id),
  queryFn: async () => request(api.GET('/api/dives/{id}/merge-candidates', { params: { path: { id } } })),
});
/** The Dive before this one it may be appended to as its continuation, or null (ADR 0063). */
export const continuationCandidateQuery = (id: string) => queryOptions({
  queryKey: keys.continuationCandidate(id),
  queryFn: async () => request(api.GET('/api/dives/{id}/continuation-candidate', { params: { path: { id } } })),
});
/** The deleted Dives restoring this one brings back, in start order: itself, or all of its append and later ones (ADR 0063). */
export const restorePreviewQuery = (id: string) => queryOptions({
  queryKey: keys.restorePreview(id),
  queryFn: async () => request(api.GET('/api/dives/{id}/restore-preview', { params: { path: { id } } })),
});
/** Every Dive of the same Diver from the day before to the day after: what a merge by hand chooses from (ADR 0038, amended). */
export const nearbyDivesQuery = (id: string) => queryOptions({
  queryKey: [...keys.mergeCandidates(id), 'nearby'] as const,
  queryFn: async () => request(api.GET('/api/dives/{id}/merge-candidates', { params: { path: { id }, query: { scope: 'nearby' } } })),
});

/** The Dives before and after one in a list's order (ADR 0042); under the Dive, so whatever changes the logbook asks again. */
export const diveNeighboursQuery = (id: string, list: ReturnType<typeof neighbourParams>) => queryOptions({
  queryKey: [...keys.dive(id), 'neighbours', list],
  queryFn: async () => request(api.GET('/api/dives/{id}/neighbours', { params: { path: { id }, query: list } })),
  placeholderData: keepPreviousData,
});
export interface NeighboursView { previous: { id: string } | null; next: { id: string } | null; position: number | null; total: number }
export const diveQuery = (id: string) => queryOptions({ queryKey: keys.dive(id), queryFn: async () => request(api.GET('/api/dives/{id}', { params: { path: { id } } })) });
/** A Dive's findings, the computer's own events and the ascent bands (ADR 0036). */
export const assessmentQuery = (id: string) => queryOptions({ queryKey: keys.assessment(id), queryFn: async () => request(api.GET('/api/dives/{id}/assessment', { params: { path: { id } } })) });

export const revisionsQuery = (id: string) => queryOptions({ queryKey: keys.revisions(id), queryFn: async () => request(api.GET('/api/dives/{id}/revisions', { params: { path: { id } } })) });

export const samplesQuery = (recordingId: string) =>
  queryOptions({
    queryKey: keys.samples(recordingId),
    queryFn: async () =>
      request(api.GET('/api/recordings/{id}/samples', {
        params: { path: { id: recordingId }, query: { channels: 'depth,temperature,tankPressure', maxPoints: 2000 } },
      })),
    staleTime: Infinity,
  });

export const candidatesQuery = (status: 'open' | 'discarded') =>
  queryOptions({ queryKey: keys.candidates(status), queryFn: async () => request(api.GET('/api/duplicate-candidates', { params: { query: { status } } })) });
/** Pending Buddy suggestions for the Divers the User manages now (ADR 0070). */
export const buddySuggestionsQuery = () =>
  queryOptions({ queryKey: keys.buddySuggestions, queryFn: async () => request(api.GET('/api/buddy-suggestions')) });
/** One suggestion's shared facts and the invited Diver's overlapping Dives, for the Review card and its accept dialog. */
export const buddySuggestionQuery = (id: string) =>
  queryOptions({ queryKey: keys.buddySuggestion(id), queryFn: async () => request(api.GET('/api/buddy-suggestions/{id}', { params: { path: { id } } })) });
