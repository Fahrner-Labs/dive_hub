// Fill offers for a Joint dive's shared facts (FAH-411): derived at read time from empty-versus-filled fields, accepted
// through the normal Revision machinery, declined as a stored dismissal, and cleaned up when a member is soft-deleted.
// Individual facts (notes, suit, ...) never reach an offer response.
import { and, eq, inArray } from 'drizzle-orm';
import pg from 'pg';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { dive, diveSite, jointDive, jointDiveDismissal, revision } from '../src/db/schema.js';
import { BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase } from './support.js';

type Facts = {
  startsAt: string; utcOffsetSeconds: number | null; durationSeconds: number;
  maxDepthM: number | null; avgDepthM: number | null; site: { id: string; name: string } | null;
};
type Offer = { dive: Facts & { id: string }; source: Facts & { diveId: string; diver: { id: string; name: string } }; fields: string[] };
type Accepted = { diveId: string; fields: string[]; version: number; values: Facts };

const DURATION = 30 * 60;

describe.skipIf(!(await databaseReachable()))('Joint dive fill offers', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let anna: string;
  let kid: string;
  let timDiver: string;
  let annaDiver: string;
  let kidDiver: string;
  let timUserId: string;
  let annaUserId: string;

  const call = (method: 'GET' | 'POST' | 'DELETE', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Parameters<typeof call>[0], url: string, cookie: string, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;
  const ownDiver = async (cookie: string) =>
    (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers', cookie)).find((d) => d.isOwn)!.id;
  const makeDive = async (diverId: string, start: Date, extra: Partial<typeof dive.$inferInsert> = {}) =>
    (await ctx.db.insert(dive).values({ diverId, startsAt: start, durationSeconds: DURATION, ...extra }).returning({ id: dive.id }))[0]!.id;
  const join = async (...diveIds: string[]) => {
    const [joint] = await ctx.db.insert(jointDive).values({}).returning({ id: jointDive.id });
    await ctx.db.update(dive).set({ jointDiveId: joint!.id }).where(inArray(dive.id, diveIds));
    return joint!.id;
  };
  const offers = (cookie: string) => json<Offer[]>('GET', '/api/joint-dives/offers', cookie);
  const offersOf = (id: string, cookie: string) => json<Offer[]>('GET', `/api/dives/${id}/joint-offers`, cookie);
  const makeSite = async (name: string) => (await ctx.db.insert(diveSite).values({ name }).returning({ id: diveSite.id }))[0]!;
  const versionOf = async (id: string, cookie: string) => (await json<{ version: number }>('GET', `/api/dives/${id}`, cookie)).version;

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    timUserId = (await createUser(ctx.auth, 'tim@example.com')).id;
    annaUserId = (await createUser(ctx.auth, 'anna@example.com')).id;
    await createUser(ctx.auth, 'kid@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
    anna = await signIn(ctx.app, 'anna@example.com');
    kid = await signIn(ctx.app, 'kid@example.com');
    timDiver = await ownDiver(tim);
    annaDiver = await ownDiver(anna);
    kidDiver = await ownDiver(kid);
  });
  afterAll(async () => {
    await ctx?.app?.close();
    await t?.drop();
  });

  it('offers only empty fields with a value on another member, never a differing filled one', async () => {
    const site = await makeSite('Offer site');
    const start = new Date('2026-06-01T09:00:00Z');
    const source = await makeDive(timDiver, start, {
      siteId: site.id, maxDepthM: 30, avgDepthM: 18, notes: 'a private note', suitType: 'drysuit',
    });
    const target = await makeDive(annaDiver, new Date(start.getTime() + 2 * 60_000), { maxDepthM: 22 });
    await join(source, target);

    const found = (await offers(anna)).filter((offer) => offer.dive.id === target);
    expect(found).toHaveLength(1);
    expect(found[0]!.dive.id).toBe(target);
    expect(found[0]!.source.diveId).toBe(source);
    expect(found[0]!.source.diver.id).toBe(timDiver);
    expect(found[0]!.fields).toEqual(['siteId', 'avgDepthM']);
    // Differing max depths are both filled: never an offer.
    expect(found[0]!.fields).not.toContain('maxDepthM');
    expect(found[0]!.fields).not.toContain('startsAt');
    expect(found[0]!.fields).not.toContain('durationSeconds');

    // The per-dive route answers the same; the source has every shared field, so it has no offer of its own.
    expect(await offersOf(target, anna)).toEqual(found);
    expect(await offersOf(source, tim)).toEqual([]);
    // Another User's Dive is not readable.
    expect((await call('GET', `/api/dives/${source}/joint-offers`, anna)).statusCode).toBe(404);
  });

  it('never exposes individual facts in any offer response', async () => {
    const source = await makeDive(timDiver, new Date('2026-06-02T09:00:00Z'), {
      maxDepthM: 25, notes: 'secret source note', suitType: 'wetsuit', suitThicknessMm: 5, weightingAmountKg: 8,
    });
    const target = await makeDive(annaDiver, new Date('2026-06-02T09:02:00Z'));
    await join(source, target);

    const response = await call('GET', '/api/joint-dives/offers', anna);
    expect(response.statusCode).toBe(200);
    expect(response.body).not.toContain('secret source note');
    expect(response.body).not.toContain('wetsuit');
    expect(response.body).not.toContain('weighting');
    for (const item of response.json() as Offer[]) {
      expect(Object.keys(item.dive).sort()).toEqual(['avgDepthM', 'durationSeconds', 'id', 'maxDepthM', 'site', 'startsAt', 'utcOffsetSeconds']);
      expect(item).not.toHaveProperty('notes');
    }
  });

  it('picks the newest filled value as the source when a Joint dive has more than two members', async () => {
    const older = await makeDive(timDiver, new Date('2026-06-03T09:00:00Z'), { maxDepthM: 30, updatedAt: new Date('2026-06-03T10:00:00Z') });
    const newer = await makeDive(kidDiver, new Date('2026-06-03T09:01:00Z'), { maxDepthM: 40, updatedAt: new Date('2026-06-03T12:00:00Z') });
    const target = await makeDive(annaDiver, new Date('2026-06-03T09:02:00Z'));
    await join(older, newer, target);

    const found = (await offersOf(target, anna)).find((offer) => offer.dive.id === target);
    expect(found!.fields).toEqual(['maxDepthM']);
    expect(found!.source.diveId).toBe(newer);
    expect(found!.source.maxDepthM).toBe(40);
  });

  it('accepts fields through a system Revision with cause joint-dive-fill and the offer disappears', async () => {
    const site = await makeSite('Fill site');
    const source = await makeDive(timDiver, new Date('2026-06-04T09:00:00Z'), {
      siteId: site.id, maxDepthM: 33, avgDepthM: 19,
    });
    const target = await makeDive(annaDiver, new Date('2026-06-04T09:02:00Z'));
    await join(source, target);

    const accepted = await json<Accepted>('POST', '/api/joint-dives/offers/accept', anna, {
      diveId: target, fields: ['siteId', 'maxDepthM'],
    });
    expect(accepted).toMatchObject({
      diveId: target, fields: ['siteId', 'maxDepthM'], version: 2,
      values: { maxDepthM: 33, site: { id: site.id, name: 'Fill site' } },
    });
    expect(accepted.values.avgDepthM).toBeNull();

    const [row] = await ctx.db.select().from(dive).where(eq(dive.id, target));
    expect(row!.siteId).toBe(site.id);
    expect(row!.maxDepthM).toBe(33);
    expect(row!.overrides).toContain('maxDepthM');

    const written = await ctx.db.select().from(revision)
      .where(and(eq(revision.entityId, target), eq(revision.cause, 'joint-dive-fill')));
    expect(written).toHaveLength(1);
    expect(written[0]!.actorType).toBe('system');
    expect(written[0]!.changes).toMatchObject({ maxDepthM: { from: null, to: 33 }, overrides: { from: [], to: ['maxDepthM'] } });

    // The filled fields are no longer empty, so only the untouched average depth is still offered.
    expect((await offersOf(target, anna)).map((offer) => offer.fields)).toEqual([['avgDepthM']]);
  });

  it('copies the source\'s current value, not a preview taken earlier', async () => {
    const source = await makeDive(timDiver, new Date('2026-06-05T09:00:00Z'), { maxDepthM: 30 });
    const target = await makeDive(annaDiver, new Date('2026-06-05T09:02:00Z'));
    await join(source, target);
    expect((await offersOf(target, anna))[0]!.source.maxDepthM).toBe(30);

    await ctx.db.update(dive).set({ maxDepthM: 36 }).where(eq(dive.id, source));
    const accepted = await json<Accepted>('POST', '/api/joint-dives/offers/accept', anna, { diveId: target, fields: ['maxDepthM'] });
    expect(accepted.values.maxDepthM).toBe(36);
    expect((await ctx.db.select({ maxDepthM: dive.maxDepthM }).from(dive).where(eq(dive.id, target)))[0]!.maxDepthM).toBe(36);
  });

  it('refuses an accept when the field was filled concurrently with 409 joint_offer_not_valid', async () => {
    const source = await makeDive(timDiver, new Date('2026-06-06T09:00:00Z'), { maxDepthM: 30 });
    const target = await makeDive(annaDiver, new Date('2026-06-06T09:02:00Z'));
    await join(source, target);
    expect((await offersOf(target, anna))[0]!.fields).toContain('maxDepthM');

    await ctx.db.update(dive).set({ maxDepthM: 24 }).where(eq(dive.id, target));
    const response = await call('POST', '/api/joint-dives/offers/accept', anna, { diveId: target, fields: ['maxDepthM'] });
    expect(response.statusCode).toBe(409);
    expect(response.json()).toMatchObject({ code: 'joint_offer_not_valid' });
  });

  it('keeps a declined field out of later derivations, also after the source changes', async () => {
    const first = await makeSite('Declined site');
    const second = await makeSite('Changed site');
    const source = await makeDive(timDiver, new Date('2026-06-07T09:00:00Z'), { siteId: first.id });
    const target = await makeDive(annaDiver, new Date('2026-06-07T09:02:00Z'));
    await join(source, target);

    expect((await json<{ diveId: string; fields: string[] }>('POST', '/api/joint-dives/offers/decline', anna, {
      diveId: target, fields: ['siteId'],
    }))).toEqual({ diveId: target, fields: ['siteId'] });
    expect(await offersOf(target, anna)).toEqual([]);

    await ctx.db.update(dive).set({ siteId: second.id }).where(eq(dive.id, source));
    expect(await offersOf(target, anna)).toEqual([]);
    const dismissals = await ctx.db.select().from(jointDiveDismissal).where(eq(jointDiveDismissal.diveId, target));
    expect(dismissals.map((d) => d.field)).toEqual(['siteId']);
  });

  it('hints every member\'s Users when a shared field changes', async () => {
    const site = await makeSite('Hinted site');
    const source = await makeDive(timDiver, new Date('2026-06-09T09:00:00Z'));
    const target = await makeDive(annaDiver, new Date('2026-06-09T09:01:00Z'));
    const jointDiveId = await join(source, target);
    const listened = new pg.Client({ connectionString: t.pool.options.connectionString! });
    await listened.connect();
    const hints: { kind: string; id: string; audience: string[] }[] = [];
    listened.on('notification', (message) => hints.push(JSON.parse(message.payload!)));
    await listened.query('listen divehub_hints');
    try {
      const response = await call('PATCH', `/api/dives/${source}`, tim, { version: await versionOf(source, tim), siteId: site.id });
      expect(response.statusCode).toBe(200);
      const started = Date.now();
      let hint: { kind: string; id: string; audience: string[] } | undefined;
      while (!hint && Date.now() - started < 5000) {
        await new Promise((resolve) => setTimeout(resolve, 25));
        hint = hints.find((candidate) => candidate.kind === 'jointDiveOffer' && candidate.id === jointDiveId);
      }
      expect(hint).toBeDefined();
      expect(new Set(hint!.audience)).toEqual(new Set([timUserId, annaUserId]));
    } finally {
      await listened.end();
    }
  });

  it('clears the link on soft delete, deletes a Joint dive left with fewer than two members, and cascades dismissals', async () => {
    const [a, b, c] = await Promise.all([
      makeDive(timDiver, new Date('2026-06-08T09:00:00Z'), { maxDepthM: 20 }),
      makeDive(annaDiver, new Date('2026-06-08T09:01:00Z')),
      makeDive(kidDiver, new Date('2026-06-08T09:02:00Z')),
    ]);
    const jointDiveId = await join(a!, b!, c!);
    await ctx.db.insert(jointDiveDismissal).values([
      { jointDiveId, diveId: b!, field: 'maxDepthM' },
      { jointDiveId, diveId: c!, field: 'maxDepthM' },
      { jointDiveId, diveId: c!, field: 'siteId' },
    ]);

    const remove = async (id: string, cookie: string) =>
      call('DELETE', `/api/dives/${id}`, cookie, { version: await versionOf(id, cookie) });

    expect((await remove(a!, tim)).statusCode).toBe(200);
    expect((await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, a!)))[0]!.jointDiveId).toBeNull();
    expect(await ctx.db.select({ id: jointDive.id }).from(jointDive).where(eq(jointDive.id, jointDiveId))).toHaveLength(1);
    expect((await ctx.db.select().from(jointDiveDismissal).where(eq(jointDiveDismissal.jointDiveId, jointDiveId)))
      .map((d) => d.diveId)).not.toContain(a);

    expect((await remove(b!, anna)).statusCode).toBe(200);
    expect(await ctx.db.select({ id: jointDive.id }).from(jointDive).where(eq(jointDive.id, jointDiveId))).toEqual([]);
    expect((await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, c!)))[0]!.jointDiveId).toBeNull();
    expect(await ctx.db.select().from(jointDiveDismissal).where(eq(jointDiveDismissal.jointDiveId, jointDiveId))).toEqual([]);
  });
});
