// Accepting a Buddy suggestion by linking one of the invited Diver's existing overlapping Dives (branch B), and
// declining a suggestion (ADR 0070). A link copies nothing: both Dives keep their own data and only join one Joint dive.
// Declining resolves the one suggestion row for every User who manages the invited Diver and creates no artefacts.
import { and, eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { buddySuggestion, dive, diverManagement, jointDive, participant } from '../src/db/schema.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type DiveView = {
  id: string; diverId: string; version: number; notes: string | null; overrides: string[];
  values: { startsAt: { at: string; utcOffsetSeconds: number | null }; durationSeconds: number; maxDepthM: number | null };
  recordings: { id: string; isPrimary: boolean }[];
  participants: { diverId: string; name: string; role: string }[];
};
type PreviewView = {
  id: string; status: string;
  overlaps: { id: string; startsAt: string; utcOffsetSeconds: number | null; durationSeconds: number; maxDepthM: number | null; site: { id: string; name: string } | null }[];
};
type Accepted = { id: string; status: string; resolvedAt: string; diveId: string; jointDiveId: string };
type Declined = { id: string; status: string; resolvedAt: string };

const START = new Date('2026-04-01T09:00:00Z');
const DURATION = 30 * 60;

describe.skipIf(!(await databaseReachable()))('Buddy suggestions: link an existing Dive and decline', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let anna: string;
  let annaUserId: string;
  let timDiver: string;
  let annaDiver: string;

  const call = (method: 'GET' | 'POST' | 'PUT' | 'PATCH', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Parameters<typeof call>[0], url: string, cookie: string, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;
  const ownDiver = async (cookie: string) => (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers', cookie)).find((d) => d.isOwn)!.id;
  const getDive = (id: string, cookie = tim) => json<DiveView>('GET', `/api/dives/${id}`, cookie);
  const versionOf = async (id: string, cookie = tim) => (await getDive(id, cookie)).version;
  const setParticipants = async (id: string, participants: { diverId: string; role: string }[], cookie = tim) =>
    call('PUT', `/api/dives/${id}/participants`, cookie, { version: await versionOf(id, cookie), participants });
  const list = (cookie: string) => json<{ id: string; status: string }[]>('GET', '/api/buddy-suggestions', cookie);
  const accept = (id: string, cookie: string, body?: object) =>
    call('POST', `/api/buddy-suggestions/${id}/accept`, cookie, body);
  const decline = (id: string, cookie: string) => call('POST', `/api/buddy-suggestions/${id}/decline`, cookie);
  const pendingFor = async (diveId: string, diverId: string) =>
    (await ctx.db.select().from(buddySuggestion)
      .where(and(eq(buddySuggestion.diveId, diveId), eq(buddySuggestion.diverId, diverId))))[0];

  const makeSource = async (start: Date, notes: string | null = null) =>
    (await ctx.db.insert(dive).values({ diverId: timDiver, startsAt: start, durationSeconds: DURATION, maxDepthM: 24, avgDepthM: 16, notes })
      .returning({ id: dive.id }))[0]!.id;
  const makeDive = async (diverId: string, values: Partial<typeof dive.$inferInsert>) =>
    (await ctx.db.insert(dive).values({ diverId, startsAt: START, durationSeconds: DURATION, ...values }).returning({ id: dive.id }))[0]!.id;
  const manage = (userId: string, diverId: string) =>
    ctx.db.insert(diverManagement).values({ userId, diverId, isOwn: false });
  const invite = async (sourceId: string, diverId: string) => {
    expect((await setParticipants(sourceId, [{ diverId, role: 'buddy' }])).statusCode).toBe(200);
    return (await pendingFor(sourceId, diverId))!;
  };
  const jointCount = async () => (await ctx.db.select({ id: jointDive.id }).from(jointDive)).length;

  async function upload(fileName: string, data: Uint8Array, cookie = tim): Promise<{ diveId: string }> {
    const { payload, headers } = multipartFile(fileName, data);
    const response = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie } });
    await ctx.imports.processImport(response.json().id);
    return (await json<{ outcome: { diveId: string }[] }>('GET', `/api/imports/${response.json().id}`, cookie)).outcome[0]!;
  }

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'tim@example.com');
    annaUserId = (await createUser(ctx.auth, 'anna@example.com')).id;
    tim = await signIn(ctx.app, 'tim@example.com');
    anna = await signIn(ctx.app, 'anna@example.com');
    timDiver = await ownDiver(tim);
    annaDiver = await ownDiver(anna);
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('previews every one of the invited Diver\'s Dives that overlaps the suggested time window, and only those', async () => {
    const source = await makeSource(START);
    const kid = await json<{ id: string }>('POST', '/api/divers', tim, { name: 'Kid Overlaps' });
    const near = await makeDive(kid.id, { startsAt: new Date(START.getTime() + 5 * 60_000), durationSeconds: 20 * 60, maxDepthM: 18 });
    // A long Dive that starts before the window but is still going when it opens.
    const edge = await makeDive(kid.id, { startsAt: new Date(START.getTime() - 40 * 60_000), durationSeconds: 80 * 60, maxDepthM: 30 });
    const far = await makeDive(kid.id, { startsAt: new Date(START.getTime() + 3 * 3600_000), durationSeconds: DURATION });
    await makeDive(kid.id, { startsAt: new Date(START.getTime() + 2 * 60_000), durationSeconds: DURATION, deletedAt: new Date() });
    const suggestion = await invite(source, kid.id);

    const preview = await json<PreviewView>('GET', `/api/buddy-suggestions/${suggestion.id}`, tim);
    expect(preview.overlaps.map((o) => o.id)).toEqual([edge, near]);
    expect(preview.overlaps[0]).toMatchObject({
      id: edge, startsAt: new Date(START.getTime() - 40 * 60_000).toISOString(), utcOffsetSeconds: null, durationSeconds: 80 * 60, maxDepthM: 30, site: null,
    });
    expect(preview.overlaps.map((o) => o.id)).not.toContain(far);

    // Accepting one that no longer overlaps, or one that is not the invited Diver's, is refused cleanly.
    const outside = await accept(suggestion.id, tim, { diveId: far });
    expect(outside.statusCode).toBe(409);
    expect(outside.json()).toMatchObject({ code: 'suggestion_dive_not_overlapping' });
    const stranger = await accept(suggestion.id, tim, { diveId: '00000000-0000-4000-8000-000000000000' });
    expect(stranger.statusCode).toBe(404);
    expect(stranger.json()).toMatchObject({ code: 'suggestion_dive_not_found' });
    expect((await pendingFor(source, kid.id))!.status).toBe('pending');
  });

  it('links the chosen Dive into one Joint dive without copying or merging either Dive', async () => {
    const source = await makeSource(new Date('2026-04-02T09:00:00Z'), 'Tim\'s own notes');
    const imported = await upload('anna-link.fit', makeSyntheticDive({
      serialNumber: 7101, start: new Date('2026-04-02T09:03:00Z'), durationSeconds: DURATION, maxDepthM: 18.5,
    }), anna);
    const annaDive = imported.diveId;
    const notes = await call('PATCH', `/api/dives/${annaDive}`, anna, { version: await versionOf(annaDive, anna), notes: 'Anna\'s own notes' });
    expect(notes.statusCode).toBe(200);
    const annaBefore = await getDive(annaDive, anna);
    const sourceBefore = await getDive(source);
    const suggestion = await invite(source, annaDiver);
    const annaDivesBefore = (await ctx.db.select({ id: dive.id }).from(dive).where(eq(dive.diverId, annaDiver))).length;

    const preview = await json<PreviewView>('GET', `/api/buddy-suggestions/${suggestion.id}`, anna);
    expect(preview.overlaps.map((o) => o.id)).toContain(annaDive);

    const response = await accept(suggestion.id, anna, { diveId: annaDive });
    expect(response.statusCode).toBe(200);
    const body = response.json() as Accepted;
    expect(body).toMatchObject({ id: suggestion.id, status: 'accepted', diveId: annaDive, jointDiveId: expect.any(String) });

    // Nothing was copied: no new Dive, and neither Dive's own facts changed.
    expect((await ctx.db.select({ id: dive.id }).from(dive).where(eq(dive.diverId, annaDiver))).length).toBe(annaDivesBefore);
    const annaAfter = await getDive(annaDive, anna);
    expect(annaAfter).toMatchObject({
      notes: 'Anna\'s own notes', overrides: [],
      values: { startsAt: annaBefore.values.startsAt, durationSeconds: annaBefore.values.durationSeconds, maxDepthM: annaBefore.values.maxDepthM },
      recordings: [expect.objectContaining({ isPrimary: true })],
    });
    expect(annaAfter.participants).toEqual([]);
    expect(await getDive(source)).toMatchObject({
      notes: 'Tim\'s own notes', participants: [{ diverId: annaDiver, name: 'anna', role: 'buddy' }],
      values: { startsAt: sourceBefore.values.startsAt, durationSeconds: sourceBefore.values.durationSeconds, maxDepthM: 24 },
    });

    const [sourceRow] = await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, source));
    const [annaRow] = await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, annaDive));
    expect(sourceRow!.jointDiveId).toBe(body.jointDiveId);
    expect(annaRow!.jointDiveId).toBe(body.jointDiveId);

    // Accepted once: a second answer is a clean conflict, not a second link.
    expect((await accept(suggestion.id, anna, { diveId: annaDive })).statusCode).toBe(409);
    // Removing the Participant afterwards leaves the acceptance and the Joint dive as they are.
    expect((await setParticipants(source, [])).statusCode).toBe(200);
    expect((await pendingFor(source, annaDiver))!.status).toBe('accepted');
    expect((await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, annaDive)))[0]!.jointDiveId).toBe(body.jointDiveId);
  });

  it('declines without creating anything, keeps the Participant, and is pending again after a re-add', async () => {
    const source = await makeSource(new Date('2026-04-03T09:00:00Z'));
    const suggestion = await invite(source, annaDiver);
    const annaDivesBefore = (await ctx.db.select({ id: dive.id }).from(dive).where(eq(dive.diverId, annaDiver))).length;
    const jointsBefore = await jointCount();

    const response = await decline(suggestion.id, anna);
    expect(response.statusCode).toBe(200);
    const body = response.json() as Declined;
    expect(body).toMatchObject({ id: suggestion.id, status: 'declined', resolvedAt: expect.any(String) });

    // No artefacts: no Dive, no Joint dive, and the inviter's Participant stays as it was.
    expect((await ctx.db.select({ id: dive.id }).from(dive).where(eq(dive.diverId, annaDiver))).length).toBe(annaDivesBefore);
    expect(await jointCount()).toBe(jointsBefore);
    expect(await ctx.db.select().from(participant).where(and(eq(participant.diveId, source), eq(participant.diverId, annaDiver))))
      .toHaveLength(1);
    // The inviter who does not manage Anna never saw it, before or after.
    expect((await list(tim)).map((s) => s.id)).not.toContain(suggestion.id);
    expect((await list(anna)).map((s) => s.id)).not.toContain(suggestion.id);

    // A second answer on the same suggestion is a clean conflict.
    expect((await decline(suggestion.id, anna)).statusCode).toBe(409);
    expect((await accept(suggestion.id, anna)).statusCode).toBe(409);

    // Removing the Participant leaves the decline; adding her again raises hers anew (slice 1 behaviour).
    expect((await setParticipants(source, [])).statusCode).toBe(200);
    expect((await pendingFor(source, annaDiver))!.status).toBe('declined');
    expect((await setParticipants(source, [{ diverId: annaDiver, role: 'buddy' }])).statusCode).toBe(200);
    expect(await pendingFor(source, annaDiver)).toMatchObject({ status: 'pending', resolvedAt: null });
  });

  it('lets two managing Users answer one suggestion: the first accept wins, the second gets a conflict', async () => {
    const source = await makeSource(new Date('2026-04-04T09:00:00Z'));
    const kid = await json<{ id: string }>('POST', '/api/divers', tim, { name: 'Kid Two Managers' });
    const overlapping = await makeDive(kid.id, { startsAt: new Date('2026-04-04T09:02:00Z'), durationSeconds: 25 * 60, maxDepthM: 12 });
    await manage(annaUserId, kid.id);
    const suggestion = await invite(source, kid.id);

    expect((await list(tim)).map((s) => s.id)).toContain(suggestion.id);
    expect((await list(anna)).map((s) => s.id)).toContain(suggestion.id);

    const first = await accept(suggestion.id, anna, { diveId: overlapping });
    expect(first.statusCode).toBe(200);
    const second = await accept(suggestion.id, tim, { diveId: overlapping });
    expect(second.statusCode).toBe(409);
    expect(second.json()).toMatchObject({ code: 'suggestion_not_pending' });

    // One Joint dive and no second Dive were made for the invited Diver.
    expect((await ctx.db.select({ id: dive.id }).from(dive).where(eq(dive.diverId, kid.id)))).toHaveLength(1);
    expect((await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, overlapping)))[0]!.jointDiveId)
      .toBe((first.json() as Accepted).jointDiveId);
    expect((await list(tim)).map((s) => s.id)).not.toContain(suggestion.id);
  });

  it('resolves a decline for every User who manages the invited Diver', async () => {
    const source = await makeSource(new Date('2026-04-05T09:00:00Z'));
    const kid = await json<{ id: string }>('POST', '/api/divers', tim, { name: 'Kid Shared Decline' });
    await manage(annaUserId, kid.id);
    const suggestion = await invite(source, kid.id);
    expect((await list(tim)).map((s) => s.id)).toContain(suggestion.id);
    expect((await list(anna)).map((s) => s.id)).toContain(suggestion.id);

    // One suggestion row: declining is per suggestion, so it resolves for both managers.
    expect((await decline(suggestion.id, tim)).statusCode).toBe(200);
    expect((await list(tim)).map((s) => s.id)).not.toContain(suggestion.id);
    expect((await list(anna)).map((s) => s.id)).not.toContain(suggestion.id);
    expect((await accept(suggestion.id, anna)).statusCode).toBe(409);
    expect((await decline(suggestion.id, anna)).statusCode).toBe(409);
    expect(await ctx.db.select({ id: dive.id }).from(dive).where(eq(dive.diverId, kid.id))).toHaveLength(0);
  });
});
