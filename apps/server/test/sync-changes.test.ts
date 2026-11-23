// The change feed endpoint (ADR 0083): a client follows the append-only change_event log by (xid, seq) position,
// bootstraps without a cursor, never sees an event whose transaction is still running, and is told to resync (410) once
// its cursor is older than the feed's retention — including when the sweep has emptied the log. The feed carries IDs and
// kinds only; this file checks the wire, not the write paths (T2's change-events.test.ts covers those).
import { randomUUID } from 'node:crypto';
import { sql } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { changeEvent } from '../src/db/schema.js';
import { CHANGES_MAX_LIMIT } from '../src/sync/changes-service.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

type ChangeKind = 'changed' | 'deleted';
type FeedEvent = { seq: number; entityType: string; entityId: string; kind: ChangeKind };
type FeedPage = { cursor: string; events: FeedEvent[] };

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

  /** The cursor for a row's (xid, seq) position, read straight from the log. */
  const position = async (seq: number | string): Promise<string> => {
    const [row] = (await t.db.execute(
      sql`select xid::text as xid, seq::text as seq from change_event where seq = ${String(seq)}`,
    )).rows as { xid: string; seq: string }[];
    return `${row!.xid}:${row!.seq}`;
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

  it('bootstraps without a cursor: the high-water position and no events', async () => {
    await insertEvent();
    const second = await insertEvent();

    const answer = await page();
    expect(answer.events).toEqual([]);
    expect(answer.cursor).toBe(await position(second.seq));
  });

  it('answers the events after the cursor in feed order, with the entity and kind', async () => {
    const start = (await page()).cursor;
    const changed = await insertEvent({ entityType: 'dive', kind: 'changed' });
    const removed = await insertEvent({ entityType: 'diver', kind: 'deleted' });

    const answer = await page(`?cursor=${start}`);
    expect(answer.events).toEqual([
      { seq: changed.seq, entityType: 'dive', entityId: changed.entityId, kind: 'changed' },
      { seq: removed.seq, entityType: 'diver', entityId: removed.entityId, kind: 'deleted' },
    ]);
    // The cursor is the last event's position, so the next call resumes after it.
    expect(answer.cursor).toBe(await position(removed.seq));
    expect((await page(`?cursor=${answer.cursor}`)).events).toEqual([]);
  });

  it('paginates with limit and resumes from the returned cursor', async () => {
    const start = (await page()).cursor;
    const written = [];
    for (let i = 0; i < 5; i += 1) written.push(await insertEvent());

    const first = await page(`?cursor=${start}&limit=2`);
    expect(first.events.map((event) => event.seq)).toEqual([written[0]!.seq, written[1]!.seq]);
    expect(first.cursor).toBe(await position(written[1]!.seq));

    const second = await page(`?cursor=${first.cursor}&limit=2`);
    expect(second.events.map((event) => event.seq)).toEqual([written[2]!.seq, written[3]!.seq]);
    expect(second.cursor).toBe(await position(written[3]!.seq));

    const third = await page(`?cursor=${second.cursor}&limit=2`);
    expect(third.events.map((event) => event.seq)).toEqual([written[4]!.seq]);
    expect(third.cursor).toBe(await position(written[4]!.seq));
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
      expect(answered.cursor).toBe(await position(later.seq));
    } finally {
      if (!committed) await client.query('rollback').catch(() => {});
      client.release();
    }
  });

  it('does not hand out a cursor past an in-flight transaction that holds the higher xid', async () => {
    // The dangerous inverse of the previous test: the transaction still open took the *higher* xid, but inserted its
    // hint before the one that commits. A seq cursor would pass it; the (xid, seq) cursor must not.
    const lower = await t.pool.connect();
    const higher = await t.pool.connect();
    const higherEntity = randomUUID();
    let higherCommitted = false;
    try {
      await lower.query('begin');
      await lower.query('select pg_current_xact_id()'); // the lower xid
      await higher.query('begin');
      await higher.query('select pg_current_xact_id()'); // the higher xid

      const higherRow = await higher.query<{ seq: string }>(
        `insert into change_event (entity_type, entity_id, kind) values ('dive', $1, 'changed') returning seq`,
        [higherEntity],
      );
      const lowerRow = await lower.query<{ seq: string }>(
        `insert into change_event (entity_type, entity_id, kind) values ('dive', $1, 'changed') returning seq`,
        [randomUUID()],
      );
      await lower.query('commit');

      // The lower xid committed (with the higher seq); the higher xid is still open. The safe cursor is the lower
      // xid's position, never one that would step over the in-flight higher-xid event.
      const boot = await page();
      expect(boot.cursor).toBe(await position(lowerRow.rows[0]!.seq));

      await higher.query('commit');
      higherCommitted = true;

      const after = await page(`?cursor=${boot.cursor}`);
      const received = after.events.find((event) => event.entityId === higherEntity);
      expect(received?.seq).toBe(Number(higherRow.rows[0]!.seq));
    } finally {
      if (!higherCommitted) await higher.query('rollback').catch(() => {});
      higher.release();
      lower.release();
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
      expect(answer.cursor).toBe(await position(event.seq));
    } finally {
      await client.query('rollback').catch(() => {});
      client.release();
      await other.drop();
    }
  });

  it('answers 410 from the persisted floor even after the sweep has emptied the log', async () => {
    // Empty the log, then sweep one more event whose position moves the floor. A cursor from before it has lost an
    // event that is no longer in the table at all, so `min(seq)` alone could not notice.
    await t.db.update(changeEvent).set({ at: daysAgo(200) });
    expect(await ctx.sync.purge()).toBeGreaterThanOrEqual(1);
    const start = (await page()).cursor;

    const doomed = await insertEvent({ at: daysAgo(200) });
    const doomedPosition = await position(doomed.seq);
    expect(await ctx.sync.purge()).toBe(1);

    const gone = await get(`?cursor=${start}`);
    expect(gone.statusCode).toBe(410);
    expect(gone.json()).toMatchObject({ code: 'sync_resync_required' });

    // Bootstrap on the now-empty log resumes at the floor, not 0, so following it straight away is valid.
    const boot = await page();
    expect(boot.cursor).toBe(doomedPosition);
    expect((await get(`?cursor=${boot.cursor}`)).statusCode).toBe(200);
  });

  it('sweeps only a feed-order prefix, so the retained log never has a hole above the floor', async () => {
    await t.db.update(changeEvent).set({ at: daysAgo(200) });
    await ctx.sync.purge();
    const start = (await page()).cursor;

    const recentFirst = await insertEvent({ at: daysAgo(1) });
    const oldSecond = await insertEvent({ at: daysAgo(200) });

    // The low position is recent, so it blocks the prefix even though the higher position is old: deleting by `at`
    // alone would leave a hole the cursor cannot see. Nothing goes.
    expect(await ctx.sync.purge()).toBe(0);
    const both = await t.db.select({ seq: changeEvent.seq }).from(changeEvent)
      .orderBy(changeEvent.xid, changeEvent.seq);
    expect(both.map((row) => row.seq)).toEqual([recentFirst.seq, oldSecond.seq]);

    // Once the blocker ages, the whole prefix goes and the floor moves past it.
    await t.db.update(changeEvent).set({ at: daysAgo(200) });
    expect(await ctx.sync.purge()).toBe(2);
    expect(await t.db.select({ seq: changeEvent.seq }).from(changeEvent)).toEqual([]);
    expect((await get(`?cursor=${start}`)).statusCode).toBe(410);
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
    expect((await get('?cursor=1')).statusCode).toBe(400);
    expect((await get('?cursor=not-a-cursor')).statusCode).toBe(400);
    expect((await get('?limit=0')).statusCode).toBe(400);
    expect((await get(`?limit=${CHANGES_MAX_LIMIT + 1}`)).statusCode).toBe(400);
  });
});
