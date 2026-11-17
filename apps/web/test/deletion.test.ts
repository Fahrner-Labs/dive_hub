// What the delete dialog offers about the Dive's copies at Providers, and the notice after deleting (ADR 0026, 0027).
import { describe, expect, it } from 'vitest';
import {
  copyKey, deleteChoice, deletedNotice, deletionState, keepCopies, parseKeptCopies, resetDeletion, stillToShow, updateDeletion,
} from '../src/lib/deletion.ts';

const connection = { id: 'c1', state: 'active' as const, accountLabel: 'erika@example.com' };
const current = { remoteId: '9', remoteNumber: 8, sentAt: '2026-10-05T10:00:00Z', upToDate: true, updateRemovesVerification: false, number: null };

describe('deleteChoice', () => {
  it('waits while the Dive\'s state at the Providers loads', () => {
    expect(deleteChoice(undefined)).toBeUndefined();
  });

  it('asks nothing about a Provider the Dive isn\'t at', () => {
    expect(deleteChoice([{ provider: 'ssi', connection, current: null }])).toEqual({ ask: [], cannot: [], itself: [] });
    expect(deleteChoice([])).toEqual({ ask: [], cannot: [], itself: [] });
  });

  it('asks whether to delete it there too while its Diver is connected', () => {
    expect(deleteChoice([{ provider: 'ssi', connection, current }])).toEqual({ ask: [{ provider: 'ssi', remoteNumber: 8 }], cannot: [], itself: [] });
    // Signing in again may still work (a kept password); the server says if not.
    expect(deleteChoice([{ provider: 'ssi', connection: { ...connection, state: 'needs_sign_in' }, current }])?.ask).toHaveLength(1);
  });

  it('says it stays there when its Diver is no longer connected, per Provider', () => {
    expect(deleteChoice([
      { provider: 'ssi', connection: null, current: { ...current, remoteNumber: null } },
      { provider: 'padi', connection, current },
    ])).toEqual({ ask: [{ provider: 'padi', remoteNumber: 8 }], cannot: [{ provider: 'ssi', remoteNumber: null }], itself: [] });
  });

  it('does not ask about a Provider whose Connection deletes by itself, by its status or by a merge pair', () => {
    expect(deleteChoice([{ provider: 'ssi', connection, current, deletesItself: true }])).toEqual({
      ask: [], cannot: [], itself: [{ provider: 'ssi', remoteNumber: 8 }],
    });
    expect(deleteChoice([{ provider: 'ssi', connection, current }], ['ssi'])?.ask).toEqual([]);
    expect(deleteChoice([{ provider: 'ssi', connection, current }], ['padi'])?.ask).toHaveLength(1);
  });
});

describe('deletedNotice', () => {
  const copy = (copy: 'deleted' | 'kept', provider = 'ssi') => ({ provider, name: provider.toUpperCase(), copy, remoteNumber: 8 });

  it('says what happened to the copies, deleted ones first', () => {
    expect(deletedNotice([])).toEqual({ key: 'deleted.notice', copies: [] });
    expect(deletedNotice([copy('kept')])).toEqual({ key: 'deleted.noticeKept', copies: [copy('kept')] });
    expect(deletedNotice([copy('deleted')])).toEqual({ key: 'deleted.noticeBoth', copies: [copy('deleted')] });
    expect(deletedNotice([copy('kept', 'padi'), copy('deleted')]).copies).toEqual([copy('deleted')]);
  });
});

describe('keeping a deleted Dive in a Provider', () => {
  const copy = (diveId: string, provider: string) => ({ diveId, provider, remoteNumber: 8 });

  it('keeps copies on the client, so a later visit knows them', () => {
    expect(parseKeptCopies(null)).toEqual(new Set());
    expect(parseKeptCopies('not json')).toEqual(new Set());
    expect(parseKeptCopies('{"a":1}')).toEqual(new Set());
    expect(parseKeptCopies(JSON.stringify([copyKey(copy('d1', 'ssi')), 7]))).toEqual(new Set(['d1:ssi']));
  });

  it('leaves out the copies the User kept there, for that Dive and Provider only', () => {
    const still = [copy('d1', 'ssi'), copy('d1', 'padi'), copy('d2', 'ssi')];
    expect(stillToShow(still, new Set())).toEqual(still);
    expect(stillToShow(still, new Set(['d1:ssi']))).toEqual([copy('d1', 'padi'), copy('d2', 'ssi')]);
  });

  it('forgets them when another User signs in', () => {
    keepCopies([copy('d3', 'ssi')]);
    expect(deletionState().kept.has('d3:ssi')).toBe(true);
    resetDeletion();
    expect(deletionState().kept.has('d3:ssi')).toBe(false);
  });
});

describe('the logbook\'s deletion state', () => {
  it('starts empty again for the next User', () => {
    updateDeletion({ justDeleted: { id: 'd1', name: 'Dive 9', copies: [], byThemselves: [] } });
    resetDeletion();
    expect(deletionState().justDeleted).toBeUndefined();
  });
});
