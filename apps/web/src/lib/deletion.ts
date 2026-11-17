// Deleting a Dive (ADR 0026, 0027): what the confirmation offers about the Dive's copies at Providers, and what the
// logbook shows afterwards (the deleted Dive with "Undo", the reminder about dives still at a Provider, the list of
// deleted dives).
import { useSyncExternalStore } from 'react';
import type { ProviderStatusView } from '../api.ts';

/** A Provider the Dive is at, with its dive number there. */
export interface CopyAt {
  provider: string;
  remoteNumber: number | null;
}

/**
 * What the delete dialog asks: `ask` lists the Providers it can delete the copy at too (its Diver is connected),
 * `cannot` those where the copy stays (not connected any more), `itself` those whose Connection deletes the copy by itself
 * a few minutes later (ADR 0056: not asked, the dialog says so). All empty: one "Delete dive" button.
 */
export interface DeleteChoice {
  ask: CopyAt[];
  cannot: CopyAt[];
  itself: CopyAt[];
}

/** Undefined while the Dive's state at the Providers is loading: the dialog waits before offering anything. */
export function deleteChoice(
  statuses: (Pick<ProviderStatusView, 'provider' | 'connection' | 'current'> & { deletesItself?: boolean })[] | undefined,
  /** Providers that delete by themselves for another reason than the status (a merge's pair says so itself). */
  itself: readonly string[] = [],
): DeleteChoice | undefined {
  if (!statuses) return undefined;
  const at = statuses.filter((s) => s.current);
  const copy = (s: (typeof at)[number]) => ({ provider: s.provider, remoteNumber: s.current!.remoteNumber });
  const byItself = (s: (typeof at)[number]) => !!s.connection && (s.deletesItself === true || itself.includes(s.provider));
  return {
    ask: at.filter((s) => s.connection && !byItself(s)).map(copy), cannot: at.filter((s) => !s.connection).map(copy),
    itself: at.filter(byItself).map(copy),
  };
}

/** The Dive just deleted, for the logbook's notice with "Undo". */
export interface JustDeleted {
  id: string;
  /** "Dive 9", or its time when it has no number. */
  name: string;
  /** What happened to its copy at each Provider it was at (the server's answer), with the Provider's name. */
  copies: { provider: string; name: string; copy: 'deleted' | 'kept'; remoteNumber: number | null }[];
  /** The names of the Providers whose Connection deletes the copy by itself in a few minutes; Undo cancels that. */
  byThemselves: string[];
}

/** The notice's sentence for a Dive just deleted, and the copy it names (deleted ones first). */
export function deletedNotice(copies: JustDeleted['copies']) {
  const deleted = copies.filter((c) => c.copy === 'deleted');
  if (deleted.length > 0) return { key: 'deleted.noticeBoth' as const, copies: deleted };
  const kept = copies.filter((c) => c.copy === 'kept');
  if (kept.length > 0) return { key: 'deleted.noticeKept' as const, copies: kept };
  return { key: 'deleted.notice' as const, copies: [] };
}

interface State {
  justDeleted: JustDeleted | undefined;
  /** The reminder about deleted dives still at a Provider, dismissed for this visit; it comes back on reload. */
  reminderDismissed: boolean;
  /**
   * The deleted Dives' Provider copies the User chose to keep there ("Keep it in SSI"), as `diveId:provider`. Kept on this
   * client (localStorage), so the reminder stays away across visits; the server still lists the copy as still there.
   */
  kept: Set<string>;
}

const KEPT_KEY = 'divehub.deleted-kept-at-provider';

/** The key of one kept copy. */
export const copyKey = (c: { diveId: string; provider: string }) => `${c.diveId}:${c.provider}`;

/** The kept copies from their stored form; an unreadable value keeps none. */
export function parseKeptCopies(raw: string | null | undefined): Set<string> {
  if (!raw) return new Set();
  try {
    const value: unknown = JSON.parse(raw);
    return new Set(Array.isArray(value) ? value.filter((v): v is string => typeof v === 'string') : []);
  } catch {
    return new Set();
  }
}

function readKept(): Set<string> {
  try {
    return parseKeptCopies(localStorage.getItem(KEPT_KEY));
  } catch {
    return new Set();
  }
}

function writeKept(kept: Set<string>) {
  try {
    localStorage.setItem(KEPT_KEY, JSON.stringify([...kept]));
  } catch {
    // A browser that keeps no storage shows the reminder again on the next visit; nothing else depends on it.
  }
}

/** The copies still worth reminding about: those the User did not say to keep at the Provider. */
export const stillToShow = <T extends { diveId: string; provider: string }>(still: readonly T[], kept: ReadonlySet<string>) =>
  still.filter((c) => !kept.has(copyKey(c)));

// Shared by the dive page (which deletes and leaves) and the logbook (which shows the outcome): the page changes in between.
let state: State = { justDeleted: undefined, reminderDismissed: false, kept: readKept() };
const listeners = new Set<() => void>();

export function updateDeletion(change: Partial<State>) {
  state = { ...state, ...change };
  for (const listener of listeners) listener();
}

/** "Keep it in SSI": stop reminding about these copies on this client, also on later visits. */
export function keepCopies(copies: readonly { diveId: string; provider: string }[]) {
  const kept = new Set(state.kept);
  for (const c of copies) kept.add(copyKey(c));
  writeKept(kept);
  updateDeletion({ kept });
}

/** Another User signs in (or nobody): neither the last one's deleted Dive nor its kept copies are theirs. */
export const resetDeletion = () => {
  writeKept(new Set());
  updateDeletion({ justDeleted: undefined, reminderDismissed: false, kept: new Set() });
};

export const deletionState = (): State => state;

export function useDeletion(): State {
  return useSyncExternalStore(
    (listener) => { listeners.add(listener); return () => listeners.delete(listener); },
    deletionState,
  );
}
