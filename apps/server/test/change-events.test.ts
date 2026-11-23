// The change feed's hint log (ADR 0083): every Revision writes one change_event in the same transaction, naming the
// entity and whether it changed or is gone. Divers also gain a version now, moved by every change so a client can name
// the state an edit started from (ADR 0083, optimistic locking).
import { randomUUID } from 'node:crypto';
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { changeEvent, diver, revision } from '../src/db/schema.js';
import { userActor, writeRevision } from '../src/revisions/revisions.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type DiveView = { id: string; version: number };

describe.skipIf(!(await databaseReachable()))('the change feed log', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let admin: string;

  const call = (method: 'GET' | 'POST' | 'PATCH' | 'DELETE', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Parameters<typeof call>[0], url: string, cookie: string, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;

  /** The hint rows for one entity, in cursor order. */
  const eventsFor = (entityId: string) =>
    t.db.select().from(changeEvent).where(eq(changeEvent.entityId, entityId)).orderBy(changeEvent.seq);
  const revisionCount = async (entityId: string) =>
    (await t.db.select().from(revision).where(eq(revision.entityId, entityId))).length;

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'tim@example.com');
    await createUser(ctx.auth, 'admin@example.com', 'admin');
    tim = await signIn(ctx.app, 'tim@example.com');
    admin = await signIn(ctx.app, 'admin@example.com');
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('logs a changed hint with every Revision: a create and an edit', async () => {
    const bob = await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Bob Miller' });
    const created = await eventsFor(bob.id);
    expect(created).toHaveLength(1);
    expect(created[0]).toMatchObject({ entityType: 'diver', entityId: bob.id, kind: 'changed' });
    expect(created[0]!.at).toBeInstanceOf(Date);
    expect(await revisionCount(bob.id)).toBe(1);

    const edited = await call('PATCH', `/api/divers/${bob.id}/details`, tim, { firstName: 'Bob' });
    expect(edited.statusCode).toBe(200);
    const afterEdit = await eventsFor(bob.id);
    expect(afterEdit).toHaveLength(2);
    expect(afterEdit[1]).toMatchObject({ kind: 'changed' });
    expect(afterEdit[1]!.seq).toBeGreaterThan(afterEdit[0]!.seq);
    expect(await revisionCount(bob.id)).toBe(2);
  });

  it('marks a deleted Dive deleted', async () => {
    const { payload, headers } = multipartFile('main.fit', makeSyntheticDive({ serialNumber: 460 }));
    const uploaded = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: tim } });
    await ctx.imports.processImport(uploaded.json().id);
    const outcome = await json<{ outcome: { diveId: string }[] }>('GET', `/api/imports/${uploaded.json().id}`, tim);
    const diveId = outcome.outcome[0]!.diveId;

    const dive = await json<DiveView>('GET', `/api/dives/${diveId}`, tim);
    const removed = await call('DELETE', `/api/dives/${diveId}`, tim, { version: dive.version });
    expect(removed.statusCode).toBe(200);

    const events = await eventsFor(diveId);
    expect(events.at(-1)).toMatchObject({ entityType: 'dive', entityId: diveId, kind: 'deleted' });
  });

  it('marks a merged-away Diver deleted and the Diver it went into changed', async () => {
    const from = await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Merged Away' });
    const into = await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Kept Diver' });
    const merged = await call('POST', `/api/admin/divers/${from.id}/merge`, admin, { into: into.id });
    expect(merged.statusCode).toBe(200);

    expect((await eventsFor(from.id)).at(-1)).toMatchObject({ kind: 'deleted' });
    expect((await eventsFor(into.id)).at(-1)).toMatchObject({ kind: 'changed' });
  });

  it('rolls the hint back with its Revision, never leaving one without the other', async () => {
    const centreId = randomUUID();
    await expect(t.db.transaction(async (tx) => {
      await writeRevision(tx, 'dive_centre', centreId, userActor(randomUUID()), 'create', {});
      throw new Error('roll back');
    })).rejects.toThrow('roll back');

    expect(await eventsFor(centreId)).toHaveLength(0);
    expect(await revisionCount(centreId)).toBe(0);
  });

  it('moves a Diver\'s version on every change and leaves it alone on a no-op', async () => {
    const bob = await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Version Bob' });
    const versionOf = async () => (await t.db.select({ version: diver.version }).from(diver).where(eq(diver.id, bob.id)))[0]!.version;
    expect(await versionOf()).toBe(1);

    await call('PATCH', `/api/divers/${bob.id}/details`, tim, { firstName: 'Bob' });
    expect(await versionOf()).toBe(2);

    // Same values: no Revision, no hint, no version bump.
    const events = await eventsFor(bob.id);
    await call('PATCH', `/api/divers/${bob.id}/details`, tim, { firstName: 'Bob' });
    expect(await versionOf()).toBe(2);
    expect(await eventsFor(bob.id)).toHaveLength(events.length);

    await call('PATCH', `/api/external-divers/${bob.id}`, tim, { name: 'Version Robert' });
    expect(await versionOf()).toBe(3);

    await call('DELETE', `/api/external-divers/${bob.id}`, tim);
    expect(await versionOf()).toBe(4);
  });

  it('covers managed Divers too: creating, renaming and removing one each tell the feed', async () => {
    const kid = await json<{ id: string }>('POST', '/api/divers', tim, { name: 'Kid' });
    expect((await eventsFor(kid.id)).map((e) => e.kind)).toEqual(['changed']);

    const renamed = await call('PATCH', `/api/divers/${kid.id}`, tim, { name: 'Kid Renamed' });
    expect(renamed.statusCode).toBe(200);
    expect((await eventsFor(kid.id)).map((e) => e.kind)).toEqual(['changed', 'changed']);
    expect((await t.db.select({ version: diver.version }).from(diver).where(eq(diver.id, kid.id)))[0]!.version).toBe(2);

    const removed = await call('DELETE', `/api/divers/${kid.id}`, tim);
    expect(removed.statusCode).toBe(204);
    expect((await eventsFor(kid.id)).map((e) => e.kind)).toEqual(['changed', 'changed', 'deleted']);
  });
});
