// The change feed endpoint (ADR 0083): a client follows the append-only change_event log by seq, bootstraps without a
// cursor, never sees an event whose transaction is still running, and is told to resync (410) once its cursor is older
// than the feed's retention. The feed carries IDs and kinds only; this file checks the wire, not the write paths (T2's
// change-events.test.ts covers those).
import { randomUUID } from 'node:crypto';
import { inArray, lte } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { changeEvent } from '../src/db/schema.js';
import { CHANGES_MAX_LIMIT } from '../src/sync/changes-service.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

type ChangeKind = 'changed' | 'deleted';
type FeedEvent = { seq: number; entityType: string; entityId: string; kind: ChangeKind };
type FeedPage = { cursor: number; events: FeedEvent[] };

const DAY_MS = 24 * 60 * 60 * 1000;
const daysAgo = (days: number) => new Date(Date.now() - days * DAY_MS);

describe.skipIf(!(await databaseReachable()))('GET /api/sync/changes', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;

  const get = (query = '') => ctx.app.inject({
    method: 'GET', url: `/api/sync/changes${query}`, headers: { cookie: tim, origin: BASE_URL },
  });
  const page = async (query = '') => (await get(query)).json() as FeedPage;

  /** A hint row as a write transaction would leave it, without needing a whole write path. */
  const insertEvent = async (over: { entityType?: string; entityId?: string; kind?: ChangeKind; at?: Date } = {}) => {
    const row = {
      entityType: over.entityType ?? 'dive',
      entityId: over.entityId ?? randomUUID(),
      kind: over.kind ?? 'changed',
      ...(over.at && { at: over.at }),
    };
    const [written] = await t.db.insert(changeEvent).values(row).returning({ seq: changeEvent.seq });
    return { ...row, seq: written!.seq };
  };

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'tim@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('bootstraps without a cursor: the high-water cursor and no events', async () => {
    const first = await insertEvent();
    const second = await insertEvent();

    const answer = await page();
    expect(answer.events).toEqual([]);
    expect(answer.cursor).toBe(second.seq);
    expect(answer.cursor).toBeGreaterThan(first.seq);
  });

  it('answers the events after the cursor in seq order, with the entity and kind', async () => {
    const start = (await page()).cursor;
    const changed = await insertEvent({ entityType: 'dive', kind: 'changed' });
    const removed = await insertEvent({ entityType: 'diver', kind: 'deleted' });

    const answer = await page(`?cursor=${start}`);
    expect(answer.events).toEqual([
      { seq: changed.seq, entityType: 'dive', entityId: changed.entityId, kind: 'changed' },
      { seq: removed.seq, entityType: 'diver', entityId: removed.entityId, kind: 'deleted' },
    ]);
    // The cursor is the last event's seq, so the next call resumes after it.
    expect(answer.cursor).toBe(removed.seq);
    expect((await page(`?cursor=${answer.cursor}`)).events).toEqual([]);
  });

  it('paginates with limit and resumes from the returned cursor', async () => {
    const start = (await page()).cursor;
    const written = [];
    for (let i = 0; i < 5; i += 1) written.push(await insertEvent());

    const first = await page(`?cursor=${start}&limit=2`);
    expect(first.events.map((event) => event.seq)).toEqual([written[0]!.seq, written[1]!.seq]);
    expect(first.cursor).toBe(written[1]!.seq);

    const second = await page(`?cursor=${first.cursor}&limit=2`);
    expect(second.events.map((event) => event.seq)).toEqual([written[2]!.seq, written[3]!.seq]);
    expect(second.cursor).toBe(written[3]!.seq);

    const third = await page(`?cursor=${second.cursor}&limit=2`);
    expect(third.events.map((event) => event.seq)).toEqual([written[4]!.seq]);
    expect(third.cursor).toBe(written[4]!.seq);
  });

  it('holds back an event whose transaction is still in flight, so a cursor never passes it', async () => {
    const start = (await page()).cursor;
    const client = await t.pool.connect();
    let committed = false;
    try {
      await client.query('begin');
      // The long transaction takes the lower xid and inserts first: its hint is invisible until it commits.
      const inFlight = await client.query<{ seq: string }>(
        `insert into change_event (entity_type, entity_id, kind) values ('dive', $1, 'changed') returning seq`,
        [randomUUID()],
      );
      const inFlightSeq = Number(inFlight.rows[0]!.seq);
      // A transaction that started after it commits a later event; xmin still holds both back.
      const later = await insertEvent();

      const held = await page(`?cursor=${start}`);
      expect(held.events).toEqual([]);
      expect(held.cursor).toBe(start);

      await client.query('commit');
      committed = true;

      const answered = await page(`?cursor=${start}`);
      expect(answered.events.map((event) => event.seq)).toEqual([inFlightSeq, later.seq]);
      expect(answered.cursor).toBe(later.seq);
    } finally {
      if (!committed) await client.query('rollback').catch(() => {});
      client.release();
    }
  });

  it('is not held back by a long transaction in another database on the same server', async () => {
    const start = (await page()).cursor;
    // Every test file has its own database on the test server; a long transaction there must not stall this feed.
    const other = await createTestDatabase();
    const client = await other.pool.connect();
    try {
      await client.query('begin');
      await client.query('create table probe (x int)');
      await client.query('insert into probe values (1)');

      const event = await insertEvent();
      const answer = await page(`?cursor=${start}`);
      expect(answer.events.map((seen) => seen.seq)).toEqual([event.seq]);
      expect(answer.cursor).toBe(event.seq);
    } finally {
      await client.query('rollback').catch(() => {});
      client.release();
      await other.drop();
    }
  });

  it('answers 410 when the cursor predates the oldest retained event, and keeps the cursor at its edge', async () => {
    const start = (await page()).cursor;
    // Let every event so far age out, so the log that survives the sweep starts at the event inserted next.
    await t.db.update(changeEvent).set({ at: daysAgo(200) }).where(lte(changeEvent.seq, start));
    const kept = await insertEvent();

    expect(await ctx.sync.purge()).toBeGreaterThanOrEqual(1);

    const gone = await get(`?cursor=${Math.max(0, start - 1)}`);
    expect(gone.statusCode).toBe(410);
    expect(gone.json()).toMatchObject({ code: 'sync_resync_required' });

    // A cursor of oldest - 1 missed nothing that is still in the log.
    const edge = await get(`?cursor=${kept.seq - 1}`);
    expect(edge.statusCode).toBe(200);
    expect((edge.json() as FeedPage).events.map((event) => event.seq)).toEqual([kept.seq]);
  });

  it('sweeps only the entries older than the 180-day retention', async () => {
    const old = await insertEvent({ at: daysAgo(181) });
    const recent = await insertEvent({ at: daysAgo(179) });

    expect(await ctx.sync.purge()).toBe(1);

    const left = await t.db.select({ seq: changeEvent.seq }).from(changeEvent)
      .where(inArray(changeEvent.seq, [old.seq, recent.seq]));
    expect(left.map((row) => row.seq)).toEqual([recent.seq]);
  });

  it('needs a session, and lives under /api so an older server answers 404 rather than the web app', async () => {
    const unauthenticated = await ctx.app.inject({ method: 'GET', url: '/api/sync/changes' });
    expect(unauthenticated.statusCode).toBe(401);

    // The writeable probe looks for 404 on an old server; a non-/api GET would be the SPA's index.html instead.
    const outsideApi = await ctx.app.inject({ method: 'GET', url: '/sync/changes' });
    expect(outsideApi.statusCode).toBe(404);
  });

  it('refuses a cursor or limit outside what it accepts', async () => {
    expect((await get('?cursor=-1')).statusCode).toBe(400);
    expect((await get('?limit=0')).statusCode).toBe(400);
    expect((await get(`?limit=${CHANGES_MAX_LIMIT + 1}`)).statusCode).toBe(400);
  });
});
