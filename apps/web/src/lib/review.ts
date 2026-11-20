// The Review page (UI redesign 4): what waits for the User, in the logbook's one line and the navigation's count, and
// the page's parts, kept in the address like the logbook's settings (ADR 0017).
import { useInfiniteQuery, useQuery } from '@tanstack/react-query';
import {
  buddySuggestionsQuery, candidatesQuery, deletedDivesQuery, deletedDivesTotal, goneDivesQuery, isPairCheck, logbookChecksQuery, providerConflictsQuery,
  siteIdDecisionsQuery,
} from '../api.ts';

export const REVIEW_TABS = ['decide', 'imports', 'decided', 'deleted'] as const;
export type ReviewTab = (typeof REVIEW_TABS)[number];

export function reviewTab(params: URLSearchParams): ReviewTab {
  const tab = params.get('tab');
  return REVIEW_TABS.find((t) => t === tab) ?? 'decide';
}

export function reviewHref(tab: ReviewTab): string {
  return tab === 'decide' ? '#/review' : `#/review?tab=${tab}`;
}

/**
 * What waits for a decision: Buddy suggestions over a shared Dive (ADR 0070), Recordings that don't clearly belong to a
 * Dive (ADR 0016), pairs of Dives at the same time and Dives that are probably no dive (ADR 0038), Dives cut by a
 * surface break (ADR 0063), Dives gone at a Provider (ADR 0051), counted per Provider, and Dives a run left with values
 * changed in both places, or that the Provider already has a dive for (ADR 0056).
 */
export function useToDecideCounts() {
  const suggestions = useQuery(buddySuggestionsQuery()).data?.length ?? 0;
  const recordings = useQuery(candidatesQuery('open')).data?.length ?? 0;
  const checks = useQuery(logbookChecksQuery('open')).data ?? [];
  const surfaceBreaks = checks.filter((c) => c.rule === 'surface_break').length;
  const pairs = checks.filter(isPairCheck).length - surfaceBreaks;
  const shortDives = checks.length - pairs - surfaceBreaks;
  const goneAt: Record<string, number> = {};
  for (const g of useQuery(goneDivesQuery()).data ?? []) goneAt[g.provider] = (goneAt[g.provider] ?? 0) + g.dives.length;
  const gone = Object.values(goneAt).reduce((a, b) => a + b, 0);
  const entries = (useQuery(providerConflictsQuery()).data ?? []).flatMap((g) => g.entries);
  const conflicts = entries.filter((e) => e.kind === 'conflict').length;
  // Dives the Provider already has a dive for: the send waits for the User's choice.
  const exists = entries.length - conflicts;
  // Sites whose private IDs gained a public counterpart: one offer per site and account (FAH-121).
  const siteMoves = useQuery(siteIdDecisionsQuery()).data?.length ?? 0;
  return {
    suggestions, recordings, pairs, surfaceBreaks, shortDives, goneAt, gone, conflicts, exists, siteMoves,
    total: suggestions + recordings + checks.length + gone + conflicts + exists + siteMoves,
  };
}

/** Counts that go into the tabs and the links under the logbook: what was decided and what was deleted. */
export function useReviewCounts() {
  const decided = (useQuery(candidatesQuery('discarded')).data?.length ?? 0) + (useQuery(logbookChecksQuery('answered')).data?.length ?? 0);
  const deleted = deletedDivesTotal(useInfiniteQuery(deletedDivesQuery()).data);
  return { decided, deleted };
}

/**
 * Whether one comparison row agrees: every candidate's formatted text is the same. A value one candidate has and
 * another lacks differs; when all lack it, the row isn't shown at all (UI redesign 4.5).
 */
export function valuesAgree(values: (string | undefined)[]): boolean {
  return values.every((value) => value === values[0]);
}
