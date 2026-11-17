// Purging deleted Dives (ADR 0071): a tombstone whose Provider copies are all gone and that is older than the retention
// is hard-deleted with its dependent rows, while its Recordings stay as tombstones so re-imports are still skipped as
// `deleted_earlier`, and its Originals stay. A Dive still at a Provider, or one a Continuation uses, is kept.
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { dive, original, push, recording, revision, waitingPush } from '../src/db/schema.js';
import { PURGE_AFTER_DAYS } from '../src/dives/purge-service.js';
import { createFakeLedger } from './fake-ledger-provider.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type Outcome = { result: string; diveId?: string; recordingId?: string; reason?: string };
type DeletedDive = { id: string; version: number; stillAt: { provider: string }[] };

describe.skipIf(!(await databaseReachable()))('purging deleted Dives', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let connectionId: string;
  let serial = 8_200_000;
  const ledger = createFakeLedger();

  const call = (method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE', url: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie: tim, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE', url: string, payload?: object) =>
    (await call(method, url, payload)).json() as T;
  const upload = async (fileName: string, bytes: Uint8Array): Promise<Outcome> => {
    const { payload, headers } = multipartFile(fileName, bytes);
    const created = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: tim } });
    await ctx.imports.processImport(created.json().id);
    return (await json<{ outcome: Outcome[] }>('GET', `/api/imports/${created.json().id}`)).outcome[0]!;
  };
  const aDive = async (options: Parameters<typeof makeSyntheticDive>[0] = {}) => {
    const placed = await upload('dive.fit', makeSyntheticDive({ serialNumber: serial++, ...options }));
    expect(placed.result).toBe('created');
    return placed.diveId!;
  };
  const version = async (id: string) => (await json<{ version: number }>('GET', `/api/dives/${id}`)).version;
  const removeCurrent = async (id: string, alsoAt: string[] = []) =>
    call('DELETE', `/api/dives/${id}`, { version: await version(id), alsoAt });
  /** Moves the tombstone's `deleted_at` back, as if the Dive had been deleted that long ago. */
  const deletedDaysAgo = async (id: string, days: number) =>
    t.db.update(dive).set({ deletedAt: new Date(Date.now() - days * 24 * 60 * 60 * 1000) }).where(eq(dive.id, id));
  const deletedDives = async () => (await json<{ dives: DeletedDive[] }>('GET', '/api/dives/deleted')).dives;
  const diveRow = async (id: string) => (await t.db.select().from(dive).where(eq(dive.id, id)))[0];

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, { extraProviders: [ledger.adapter] });
    await createUser(ctx.auth, 'tim@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
    const own = (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers')).find((d) => d.isOwn)!.id;
    connectionId = (await json<{ id: string }>('POST', '/api/connections/ledger', { diverId: own, token: 'ledger-token-1', keepSignedIn: false })).id;
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('purges an old tombstone whose Provider copies are gone, keeping its Recording and Original', async () => {
    const file = makeSyntheticDive({ serialNumber: 8_200_001, start: new Date('2026-04-01T09:00:00Z') });
    const id = (await upload('old.fit', file)).diveId!;
    expect((await removeCurrent(id)).statusCode).toBe(200);
    await deletedDaysAgo(id, PURGE_AFTER_DAYS + 1);
    const [rec] = await t.db.select().from(recording).where(eq(recording.diveId, id));
    expect(rec).toBeDefined();

    expect(await ctx.purge.purge()).toBe(1);

    expect(await diveRow(id)).toBeUndefined();
    // The Recording stays, detached: its key still keeps the same file out, as `deleted_earlier`.
    const [kept] = await t.db.select().from(recording).where(eq(recording.id, rec!.id));
    expect(kept).toMatchObject({ diveId: null, deletedAt: expect.any(Date) });
    expect(await t.db.select().from(original).where(eq(original.id, rec!.originalId!))).toHaveLength(1);
    expect(await upload('old-again.fit', file)).toMatchObject({ result: 'skipped', reason: 'deleted_earlier' });
    // Its own history and Pushes went with it, and it is no longer listed.
    expect(await t.db.select().from(push).where(eq(push.diveId, id))).toHaveLength(0);
    expect(await t.db.select().from(revision).where(eq(revision.entityId, id))).toHaveLength(0);
    expect(await deletedDives()).toEqual([]);
  });

  it('keeps a tombstone younger than the retention', async () => {
    const id = await aDive({ start: new Date('2026-04-02T09:00:00Z') });
    await removeCurrent(id);
    await deletedDaysAgo(id, PURGE_AFTER_DAYS - 1);

    expect(await ctx.purge.purge()).toBe(0);
    expect(await diveRow(id)).toBeDefined();
  });

  it('keeps a Dive still at a Provider, and purges it once that copy is gone too', async () => {
    const id = await aDive({ start: new Date('2026-04-03T09:00:00Z') });
    expect(await json('POST', `/api/dives/${id}/providers/ledger`, {})).toMatchObject({ outcome: 'created' });
    // Deleted here but kept at the Provider: the tombstone is the reminder.
    expect((await removeCurrent(id)).json()).toEqual({ providers: [{ provider: 'ledger', copy: 'kept' }] });
    await deletedDaysAgo(id, PURGE_AFTER_DAYS + 1);
    expect(await ctx.purge.purge()).toBe(0);
    expect((await deletedDives()).find((d) => d.id === id)!.stillAt).toEqual([{ provider: 'ledger', remoteNumber: expect.any(Number) }]);

    expect((await call('DELETE', `/api/dives/${id}/providers/ledger`)).statusCode).toBe(200);
    expect(await ctx.purge.purge()).toBe(1);
    expect(await diveRow(id)).toBeUndefined();
  });

  it('keeps a Dive that a Continuation still uses, as a part or as the kept side', async () => {
    const first = await aDive({ serialNumber: 8_200_010, start: new Date('2026-04-04T09:00:00Z'), durationSeconds: 1200 });
    const second = await aDive({ serialNumber: 8_200_010, start: new Date('2026-04-04T09:25:00Z'), durationSeconds: 900 });
    expect((await call('POST', `/api/dives/${first}/continuation`, {
      version: await version(first), dives: [{ id: second, version: await version(second) }],
    })).statusCode).toBe(200);
    // The append deleted the later Dive; age it and the kept one after deleting it here.
    await deletedDaysAgo(second, PURGE_AFTER_DAYS + 1);
    await removeCurrent(first);
    await deletedDaysAgo(first, PURGE_AFTER_DAYS + 1);

    expect(await ctx.purge.purge()).toBe(0);
    expect(await diveRow(first)).toBeDefined();
    expect(await diveRow(second)).toBeDefined();
  });

  it('keeps a Dive whose delete still waits for a Provider', async () => {
    const id = await aDive({ start: new Date('2026-04-05T09:00:00Z') });
    await removeCurrent(id);
    await deletedDaysAgo(id, PURGE_AFTER_DAYS + 1);
    await t.db.insert(waitingPush).values({
      diveId: id, provider: 'ledger', connectionId, action: 'delete', state: 'waiting', options: {},
      dueAt: new Date(), changedAt: new Date(),
    });

    expect(await ctx.purge.purge()).toBe(0);
    expect(await diveRow(id)).toBeDefined();
  });
});
