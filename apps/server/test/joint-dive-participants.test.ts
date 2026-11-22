// Participants of the Dives in one Joint dive stay a union (FAH-410): linking two Dives copies every member's people
// onto every other member (its own Diver excluded, roles kept), and a Participant added to one member later follows to
// all the others. Removals never propagate, and replaying an add is a no-op.
import { and, eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { buddySuggestion, dive, diverManagement, participant } from '../src/db/schema.js';
import { BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase } from './support.js';

type DiveView = { id: string; version: number; participants: { diverId: string; name: string; role: string }[] };
type Accepted = { id: string; status: string; diveId: string; jointDiveId: string };

const DURATION = 30 * 60;

describe.skipIf(!(await databaseReachable()))('Joint dive Participant sync', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let anna: string;
  let kid: string;
  let timUserId: string;
  let timDiver: string;
  let annaDiver: string;
  let kidDiver: string;
  let nextPerson = 0;

  const call = (method: 'GET' | 'POST' | 'PUT' | 'PATCH', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Parameters<typeof call>[0], url: string, cookie: string, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;
  const ownDiver = async (cookie: string) =>
    (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers', cookie)).find((d) => d.isOwn)!.id;
  const getDive = (id: string, cookie = tim) => json<DiveView>('GET', `/api/dives/${id}`, cookie);
  const versionOf = async (id: string, cookie = tim) => (await getDive(id, cookie)).version;
  const setParticipants = async (id: string, participants: { diverId: string; role: string }[], cookie = tim) =>
    call('PUT', `/api/dives/${id}/participants`, cookie, { version: await versionOf(id, cookie), participants });
  const people = async (id: string, cookie = tim) => (await getDive(id, cookie)).participants;
  const person = async (name: string) =>
    (await json<{ id: string }>('POST', '/api/external-divers', tim, { name: `${name} ${nextPerson++}` })).id;
  const makeDive = async (diverId: string, start: Date, extra: Partial<typeof dive.$inferInsert> = {}) =>
    (await ctx.db.insert(dive).values({ diverId, startsAt: start, durationSeconds: DURATION, ...extra }).returning({ id: dive.id }))[0]!.id;
  const manage = (userId: string, diverId: string) => ctx.db.insert(diverManagement).values({ userId, diverId, isOwn: false });
  const pendingFor = async (diveId: string, diverId: string) =>
    (await ctx.db.select().from(buddySuggestion)
      .where(and(eq(buddySuggestion.diveId, diveId), eq(buddySuggestion.diverId, diverId))))[0];
  const invite = async (sourceId: string, diverId: string) => {
    const current = (await people(sourceId)).map((p) => ({ diverId: p.diverId, role: p.role }));
    expect((await setParticipants(sourceId, [...current, { diverId, role: 'buddy' }])).statusCode).toBe(200);
    return (await pendingFor(sourceId, diverId))!;
  };
  const link = async (suggestionId: string, cookie: string, diveId: string) => {
    const response = await call('POST', `/api/buddy-suggestions/${suggestionId}/accept`, cookie, { diveId });
    expect(response.statusCode).toBe(200);
    return response.json() as Accepted;
  };
  /** A linked source/target pair with a fresh Joint dive, plus one Participant already on both sides. */
  const linkedPair = async (start: Date) => {
    const source = await makeDive(timDiver, start, { maxDepthM: 24 });
    const target = await makeDive(annaDiver, new Date(start.getTime() + 2 * 60_000));
    const shared = await person('Shared');
    const suggestion = await invite(source, annaDiver);
    await ctx.db.insert(participant).values([
      { diveId: source, diverId: shared, role: 'guide' },
      { diveId: target, diverId: shared, role: 'guide' },
    ]);
    const accepted = await link(suggestion.id, anna, target);
    return { source, target, shared, jointDiveId: accepted.jointDiveId };
  };

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    timUserId = (await createUser(ctx.auth, 'tim@example.com')).id;
    await createUser(ctx.auth, 'anna@example.com');
    await createUser(ctx.auth, 'kid@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
    anna = await signIn(ctx.app, 'anna@example.com');
    kid = await signIn(ctx.app, 'kid@example.com');
    timDiver = await ownDiver(tim);
    annaDiver = await ownDiver(anna);
    kidDiver = await ownDiver(kid);
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('unions the people of two linked Dives in both directions, keeping roles and skipping duplicates', async () => {
    const start = new Date('2026-05-01T09:00:00Z');
    const source = await makeDive(timDiver, start, { maxDepthM: 24 });
    const target = await makeDive(annaDiver, new Date(start.getTime() + 2 * 60_000));
    const bob = await person('Bob');
    const carla = await person('Carla');
    // Bob is on both Dives already; Carla only on Anna's, Tim (source's own Diver) nowhere as a Participant.
    await ctx.db.insert(participant).values([
      { diveId: source, diverId: bob, role: 'guide' },
      { diveId: target, diverId: bob, role: 'guide' },
      { diveId: target, diverId: carla, role: 'instructor' },
    ]);
    const suggestion = await invite(source, annaDiver);

    const accepted = await link(suggestion.id, anna, target);

    const sourcePeople = await people(source);
    expect(sourcePeople.map((p) => [p.diverId, p.role])).toEqual(expect.arrayContaining([
      [annaDiver, 'buddy'], [bob, 'guide'], [carla, 'instructor'],
    ]));
    // Anna's Dive gains Tim, the source's own Diver, as a buddy and keeps every role it had.
    const targetPeople = await people(target, anna);
    expect(targetPeople.map((p) => [p.diverId, p.role])).toEqual(expect.arrayContaining([
      [timDiver, 'buddy'], [bob, 'guide'], [carla, 'instructor'],
    ]));
    // Never its own Diver, and never a duplicate of a Participant both Dives already had.
    expect(targetPeople.map((p) => p.diverId)).not.toContain(annaDiver);
    expect(sourcePeople.filter((p) => p.diverId === bob)).toHaveLength(1);
    expect(targetPeople.filter((p) => p.diverId === bob)).toHaveLength(1);
    expect(accepted.jointDiveId).toEqual(expect.any(String));
  });

  it('gives a third linked Dive the whole union, across all members of the Joint dive', async () => {
    const start = new Date('2026-05-02T09:00:00Z');
    const source = await makeDive(timDiver, start, { maxDepthM: 24 });
    const annaDive = await makeDive(annaDiver, new Date(start.getTime() + 2 * 60_000));
    const annaSuggestion = await invite(source, annaDiver);
    await link(annaSuggestion.id, anna, annaDive);

    // Kid joins the same Joint dive through a second link.
    await manage(timUserId, kidDiver);
    const kidDive = await makeDive(kidDiver, new Date(start.getTime() + 4 * 60_000));
    const kidSuggestion = await invite(source, kidDiver);
    await link(kidSuggestion.id, tim, kidDive);

    expect((await people(kidDive, kid)).map((p) => [p.diverId, p.role])).toEqual(expect.arrayContaining([
      [timDiver, 'buddy'], [annaDiver, 'buddy'],
    ]));
    expect((await people(kidDive, kid)).map((p) => p.diverId)).not.toContain(kidDiver);
    // The earlier members see Kid as well.
    expect((await people(source)).map((p) => p.diverId)).toEqual(expect.arrayContaining([annaDiver, kidDiver]));
    expect((await people(annaDive, anna)).map((p) => p.diverId)).toEqual(expect.arrayContaining([timDiver, kidDiver]));
  });

  it('propagates a Participant added to one member to every other, skipping its own Diver and keeping the role', async () => {
    const { source, target, jointDiveId } = await linkedPair(new Date('2026-05-03T09:00:00Z'));
    const miguel = await person('Miguel');

    // Anna adds Miguel to her own Dive; Tim's Dive learns him with the same role.
    expect((await setParticipants(target, [...await people(target, anna), { diverId: miguel, role: 'instructor' }], anna)).statusCode).toBe(200);
    expect((await people(source)).find((p) => p.diverId === miguel)).toMatchObject({ role: 'instructor' });

    // Kid's Dive is a third member; adding Kid to Anna's Dive must not put Kid on his own Dive.
    const kidDive = await makeDive(kidDiver, new Date('2026-05-03T09:04:00Z'), { jointDiveId });
    expect((await setParticipants(target, [...await people(target, anna), { diverId: kidDiver, role: 'buddy' }], anna)).statusCode).toBe(200);
    expect((await people(source)).map((p) => p.diverId)).toContain(kidDiver);
    expect((await people(kidDive, kid)).map((p) => p.diverId)).not.toContain(kidDiver);
  });

  it('never propagates a removal', async () => {
    const { source, target } = await linkedPair(new Date('2026-05-04T09:00:00Z'));
    const removed = await person('Removed');

    expect((await setParticipants(target, [...await people(target, anna), { diverId: removed, role: 'buddy' }], anna)).statusCode).toBe(200);
    expect((await people(source)).map((p) => p.diverId)).toContain(removed);

    // Taking him off Anna's Dive leaves him on Tim's, where the union put him.
    expect((await setParticipants(target, (await people(target, anna)).filter((p) => p.diverId !== removed), anna)).statusCode).toBe(200);
    expect((await people(target, anna)).map((p) => p.diverId)).not.toContain(removed);
    expect((await people(source)).map((p) => p.diverId)).toContain(removed);
  });

  it('treats a replayed add as a no-op', async () => {
    const { source, target } = await linkedPair(new Date('2026-05-05T09:00:00Z'));
    const replayed = await person('Replayed');
    const base = await people(target, anna);
    expect((await setParticipants(target, [...base, { diverId: replayed, role: 'buddy' }], anna)).statusCode).toBe(200);
    expect((await people(source)).filter((p) => p.diverId === replayed)).toHaveLength(1);

    // Removing him from Tim's Dive (not propagated) and adding him back replays the very same sync.
    expect((await setParticipants(source, (await people(source)).filter((p) => p.diverId !== replayed))).statusCode).toBe(200);
    expect((await setParticipants(source, [...await people(source), { diverId: replayed, role: 'buddy' }])).statusCode).toBe(200);
    expect((await people(target, anna)).filter((p) => p.diverId === replayed)).toHaveLength(1);
  });
});
