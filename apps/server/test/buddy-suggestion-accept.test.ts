// Accepting a Buddy suggestion (ADR 0070, branch A): the invited Diver gets a Recording-less Dive with the shared facts
// only, both Dives join one Joint dive, and the acceptance is a system Revision. The copy is an ordinary Dive: a later
// Recording attaches to it, a conflicting one becomes a Duplicate candidate, and it takes part in the logbook checks.
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { buddySuggestion, dive } from '../src/db/schema.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type DiveView = {
  id: string; diverId: string; version: number; overrides: string[];
  values: {
    startsAt: { at: string; utcOffsetSeconds: number | null };
    durationSeconds: number; maxDepthM: number | null; avgDepthM: number | null; waterTemperatureC: number | null;
  };
  notes: string | null; site: { id: string; name: string } | null;
  fromProvider: string | null;
  fromRecording: { maxDepthM: number | null; avgDepthM: number | null } | null;
  recordings: { id: string; isPrimary: boolean }[];
  cylinders: unknown[]; lead: unknown[]; weightingFeedback: { felt: string } | null; suit: { type: string } | null;
  participants: { diverId: string; name: string; role: string }[];
};
type Accepted = { id: string; status: string; resolvedAt: string; diveId: string; jointDiveId: string };
type RevisionView = { cause: string; actor: { type: string; id: string }; changes: Record<string, unknown> };
type Candidate = { id: string; reason: string; dives: { id: string }[] };
type Check = { rule: string; dive: { id: string }; other: { id: string } | null };
type Outcome = { result: string; diveId?: string; recordingId: string; reason?: string };

const START = new Date('2026-01-15T09:00:00Z');
const DURATION = 30 * 60;

describe.skipIf(!(await databaseReachable()))('accepting a Buddy suggestion', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let anna: string;
  let timDiver: string;
  let annaDiver: string;
  let sourceDive: string;
  let site: { id: string; name: string };
  let suggestionId: string;
  let sourceBeforeAccept: DiveView;
  let copiedDive: string;
  let jointDiveId: string;
  let bob: { id: string; name: string };

  const call = (method: 'GET' | 'POST' | 'PUT' | 'PATCH', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Parameters<typeof call>[0], url: string, cookie: string, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;
  const ownDiver = async (cookie: string) => (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers', cookie)).find((d) => d.isOwn)!.id;
  const getDive = (id: string, cookie = tim) => json<DiveView>('GET', `/api/dives/${id}`, cookie);
  const versionOf = async (id: string, cookie = tim) => (await getDive(id, cookie)).version;
  const setParticipants = async (id: string, participants: { diverId: string; role: string }[], cookie = tim) =>
    call('PUT', `/api/dives/${id}/participants`, cookie, { version: await versionOf(id, cookie), participants });
  const pendingFor = async (diverId: string) =>
    (await ctx.db.select().from(buddySuggestion).where(eq(buddySuggestion.diverId, diverId)))
      .find((s) => s.status === 'pending');
  const accepted = (id: string, cookie = anna) => call('POST', `/api/buddy-suggestions/${id}/accept`, cookie);

  async function upload(fileName: string, data: Uint8Array, cookie = tim): Promise<Outcome> {
    const { payload, headers } = multipartFile(fileName, data);
    const response = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie } });
    await ctx.imports.processImport(response.json().id);
    return (await json<{ outcome: Outcome[] }>('GET', `/api/imports/${response.json().id}`, cookie)).outcome[0]!;
  }

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'tim@example.com');
    await createUser(ctx.auth, 'anna@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
    anna = await signIn(ctx.app, 'anna@example.com');
    timDiver = await ownDiver(tim);
    annaDiver = await ownDiver(anna);
    sourceDive = (await upload('tim.fit', makeSyntheticDive({ serialNumber: 111 }))).diveId!;
    site = await json<{ id: string; name: string }>('POST', '/api/dive-sites', tim, {
      name: 'House Reef', position: { latitude: 28.495, longitude: 34.516 },
    });
    // Private values and hand-set depths: the copy takes the values, never the Overrides, notes or gear.
    const edited = await call('PATCH', `/api/dives/${sourceDive}`, tim, {
      version: await versionOf(sourceDive),
      siteId: site.id, notes: 'Our dive, calm water',
      set: { maxDepthM: 21, avgDepthM: 15 },
      cylinders: [{ volumeL: 12, workingPressureBar: 232, material: 'steel', gas: { o2: 32, he: 0 }, startPressureBar: 205, endPressureBar: 60 }],
      lead: [{ amountKg: 4, placement: 'belt' }],
      weightingFeedback: { felt: 'right' },
      suit: { type: 'wetsuit', thicknessMm: 5, hood: false },
    });
    expect(edited.statusCode).toBe(200);
    bob = await json<{ id: string; name: string }>('POST', '/api/external-divers', tim, { name: 'Bob Miller' });
    expect((await setParticipants(sourceDive, [{ diverId: annaDiver, role: 'buddy' }, { diverId: bob.id, role: 'guide' }])).statusCode).toBe(200);
    suggestionId = (await pendingFor(annaDiver))!.id;
    sourceBeforeAccept = await getDive(sourceDive);
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('creates a Dive with exactly the shared facts, links both into one Joint dive, and records the system actor', async () => {
    // Only a User who manages the invited Diver may accept.
    expect((await accepted(suggestionId, tim)).statusCode).toBe(404);

    const response = await accepted(suggestionId);
    expect(response.statusCode).toBe(200);
    const body = response.json() as Accepted;
    expect(body).toMatchObject({ id: suggestionId, status: 'accepted', diveId: expect.any(String), jointDiveId: expect.any(String) });
    expect(body.resolvedAt).toEqual(expect.any(String));
    copiedDive = body.diveId;
    jointDiveId = body.jointDiveId;

    expect((await ctx.db.select().from(buddySuggestion).where(eq(buddySuggestion.id, suggestionId)))[0])
      .toMatchObject({ status: 'accepted', resolvedAt: expect.any(Date) });

    const copied = await getDive(copiedDive, anna);
    expect(copied.diverId).toBe(annaDiver);
    // Site, start time with its offset, duration and the depths as plain own values.
    expect(copied.site).toEqual(sourceBeforeAccept.site);
    expect(copied.values.startsAt).toEqual(sourceBeforeAccept.values.startsAt);
    expect(copied.values.durationSeconds).toBe(sourceBeforeAccept.values.durationSeconds);
    expect(copied.values.maxDepthM).toBe(21);
    expect(copied.values.avgDepthM).toBe(15);
    expect(copied.overrides).toEqual([]);
    expect(copied.fromProvider).toBeNull();
    // Nothing private: no Recording, notes, gear, weighting or suit; water temperature is not a shared fact either.
    expect(copied.recordings).toEqual([]);
    expect(copied.notes).toBeNull();
    expect(copied.cylinders).toEqual([]);
    expect(copied.lead).toEqual([]);
    expect(copied.weightingFeedback).toBeNull();
    expect(copied.suit).toBeNull();
    expect(copied.values.waterTemperatureC).toBeNull();
    // The people on the descent, as Divers in the invited logbook: the source's owner and the other Participants, never the invitee.
    expect(copied.participants).toEqual(expect.arrayContaining([
      { diverId: timDiver, name: 'tim', role: 'buddy' },
      { diverId: bob.id, name: 'Bob Miller', role: 'guide' },
    ]));
    expect(copied.participants.map((p) => p.diverId)).not.toContain(annaDiver);
    // Copying the people onto the invitee's Dive raises no suggestions back to them.
    expect(await ctx.db.select().from(buddySuggestion).where(eq(buddySuggestion.diveId, copiedDive))).toEqual([]);

    const [sourceRow] = await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, sourceDive));
    const [copiedRow] = await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, copiedDive));
    expect(sourceRow!.jointDiveId).toBe(jointDiveId);
    expect(copiedRow!.jointDiveId).toBe(jointDiveId);

    // Provenance: the copy is the hub's, not a User's.
    const history = await json<RevisionView[]>('GET', `/api/dives/${copiedDive}/revisions`, anna);
    expect(history).toHaveLength(1);
    expect(history[0]).toMatchObject({ cause: 'buddy-accept', actor: { type: 'system', id: 'buddy-suggestion-acceptance' } });

    // Accepted once: the second answer says it is no longer pending.
    const again = await accepted(suggestionId);
    expect(again.statusCode).toBe(409);
    expect(again.json()).toMatchObject({ code: 'suggestion_not_pending' });
  });

  it('creates the Dive when the actor manages the invited Diver themselves, in the same Joint dive', async () => {
    const kid = await json<{ id: string }>('POST', '/api/divers', tim, { name: 'Kid' });
    const current = (await json<{ participants: { diverId: string; role: string }[] }>('GET', `/api/dives/${sourceDive}`, tim)).participants;
    expect((await setParticipants(sourceDive, [...current, { diverId: kid.id, role: 'buddy' }])).statusCode).toBe(200);
    const raised = await pendingFor(kid.id);
    expect(raised).toMatchObject({ diveId: sourceDive, status: 'pending' });
    // It is in Tim's own Review inbox, because he manages the invited Diver too.
    expect((await json<{ id: string }[]>('GET', '/api/buddy-suggestions', tim)).map((s) => s.id)).toContain(raised!.id);

    const response = await accepted(raised!.id, tim);
    expect(response.statusCode).toBe(200);
    const body = response.json() as Accepted;
    const copied = await getDive(body.diveId, tim);
    expect(copied.diverId).toBe(kid.id);
    expect(copied.participants).toEqual(expect.arrayContaining([
      { diverId: timDiver, name: 'tim', role: 'buddy' }, { diverId: annaDiver, name: 'anna', role: 'buddy' }, { diverId: bob.id, name: 'Bob Miller', role: 'guide' },
    ]));
    expect(copied.participants.map((p) => p.diverId)).not.toContain(kid.id);
    // The source was already in a Joint dive: the new member joins it rather than a second one.
    expect(body.jointDiveId).toBe(jointDiveId);
    expect((await ctx.db.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, body.diveId)))[0]!.jointDiveId).toBe(jointDiveId);
  });

  it('lets the invitee\'s later Recording attach to the copy and win on the depths, without Overrides', async () => {
    const before = await getDive(sourceDive);
    const result = await upload('anna.fit', makeSyntheticDive({ serialNumber: 998, start: START, durationSeconds: DURATION, maxDepthM: 18.5 }), anna);
    expect(result.result).toBe('attached');
    expect(result.diveId).toBe(copiedDive);

    const attached = await getDive(copiedDive, anna);
    expect(attached.recordings).toHaveLength(1);
    expect(attached.recordings[0]).toMatchObject({ id: result.recordingId, isPrimary: true });
    expect(attached.overrides).toEqual([]);
    expect(attached.values.maxDepthM).toBeCloseTo(before.fromRecording!.maxDepthM!, 2);
    expect(attached.values.avgDepthM).toBeCloseTo(before.fromRecording!.avgDepthM!, 2);
    // The source keeps its own values and Recording.
    expect(await getDive(sourceDive)).toMatchObject({ values: { maxDepthM: 21, avgDepthM: 15 }, recordings: [expect.any(Object)] });
  });

  it('takes part in Duplicate candidates and the logbook checks like any other Dive', async () => {
    const odd = await upload('anna-backup.fit', makeSyntheticDive({ serialNumber: 999, start: START, durationSeconds: DURATION, maxDepthM: 30 }), anna);
    expect(odd).toMatchObject({ result: 'duplicate-candidate', reason: 'max_depth_differs' });
    const candidates = await json<Candidate[]>('GET', '/api/duplicate-candidates', anna);
    expect(candidates.find((c) => c.dives.some((d) => d.id === copiedDive))).toBeDefined();

    // A second suggestion at the same time gives the Diver another Dive; the checks pair the two, as for any Dives.
    const secondSourceUpload = await upload('tim-second.fit', makeSyntheticDive({ serialNumber: 222, start: START, durationSeconds: DURATION, maxDepthM: 30 }), tim);
    expect(secondSourceUpload.result).toBe('duplicate-candidate');
    const timCandidate = (await json<Candidate[]>('GET', '/api/duplicate-candidates', tim)).find((c) => c.dives.some((d) => d.id === sourceDive))!;
    const secondSource = (await json<{ diveId: string }>('POST', `/api/duplicate-candidates/${timCandidate.id}/new-dive`, tim)).diveId;
    expect((await setParticipants(secondSource, [{ diverId: annaDiver, role: 'buddy' }], tim)).statusCode).toBe(200);
    const secondSuggestion = await pendingFor(annaDiver);
    expect(secondSuggestion).toMatchObject({ diveId: secondSource, status: 'pending' });
    const secondCopy = (await accepted(secondSuggestion!.id, anna)).json() as Accepted;

    const checks = await json<Check[]>('GET', '/api/logbook-checks', anna);
    const pair = checks.find((c) => [c.dive.id, c.other?.id].sort().join() === [copiedDive, secondCopy.diveId].sort().join());
    expect(pair, JSON.stringify(checks.map((c) => ({ rule: c.rule, dive: c.dive.id, other: c.other?.id })))).toBeDefined();
  });
});
