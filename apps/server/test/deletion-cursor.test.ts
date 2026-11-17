// The deleted Dives' paging cursor (ADR 0017's order: deletedAt, then id): opaque to clients, and only a cursor we made
// counts.
import { describe, expect, it } from 'vitest';
import { decodeDeletedCursor, encodeDeletedCursor } from '../src/dives/deletion-view.js';

const opaque = (value: unknown) => Buffer.from(JSON.stringify(value), 'utf8').toString('base64url');

describe('the deleted Dives cursor', () => {
  it('round-trips the last Dive of a page', () => {
    const cursor = { deletedAt: new Date('2026-10-05T10:00:00.123Z'), id: '5f1e2c4a-0000-4000-8000-000000000001' };
    expect(decodeDeletedCursor(encodeDeletedCursor(cursor))).toEqual(cursor);
  });

  it('refuses anything that is not a cursor it made', () => {
    expect(decodeDeletedCursor('')).toBeNull();
    expect(decodeDeletedCursor('not-base64url!')).toBeNull();
    expect(decodeDeletedCursor(Buffer.from('not json').toString('base64url'))).toBeNull();
    expect(decodeDeletedCursor(opaque({ at: 'not a date', id: 'x' }))).toBeNull();
    expect(decodeDeletedCursor(opaque({ at: '2026-10-05T10:00:00.000Z' }))).toBeNull();
    expect(decodeDeletedCursor(opaque({ id: 'x' }))).toBeNull();
    expect(decodeDeletedCursor(opaque(null))).toBeNull();
  });
});
