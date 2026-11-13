// Claim activation (ADR 0070): a suggestion raised for an external Diver waits with no audience until a User claims that
// Diver through a Provider account (ADR 0028), then its new managers see it and hear the hint. A Diver merged away by an
// admin takes its pending suggestions along to the surviving Diver; accepted and declined ones stay where they are.
import { eq } from 'drizzle-orm';
import pg from 'pg';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { buddySuggestion } from '../src/db/schema.js';
import { createFakeSsi } from './fake-ssi.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

const TIM_SSI = 5_012_047;
const BOB_SSI = 4_109_908;

type SuggestionView = {
  id: string; status: string;
  diver: { id: string; name: string };
  dive: { id: string };
};
type Hint = { kind: string; id: string; audience: string[] };

describe.skipIf(!(await databaseReachable()))('Buddy suggestions on claim and merge', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let listened: pg.Client;
  let tim: string;
  let bob: string;
  let anna: string;
  let admin: string;
  let bobUserId: string;
  let annaUserId: string;
  let timDiver: string;
  let bobDiver: string;
  let annaDiver: string;
  let diveOne: string;
  let diveTwo: string;
  let diveThree: string;
  let diveFour: string;
  let bobExternal: string;
  let claimSuggestionId: string;
  const hints: Hint[] = [];

  const call = (method: 'GET' | 'POST' | 'PUT', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Parameters<typeof call>[0], url: string, cookie: string, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;
  const ownDiver = async (cookie: string) => (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers', cookie)).find((d) => d.isOwn)!.id;
  const versionOf = async (id: string) => (await json<{ version: number }>('GET', `/api/dives/${id}`, tim)).version;
  const setParticipants = async (id: string, participants: { diverId: string; role: string }[]) =>
    call('PUT', `/api/dives/${id}/participants`, tim, { version: await versionOf(id), participants });
  const rowsOf = (diverId?: string) => (diverId
    ? ctx.db.select().from(buddySuggestion).where(eq(buddySuggestion.diverId, diverId))
    : ctx.db.select().from(buddySuggestion)).orderBy(buddySuggestion.createdAt);

  const waitFor = async <T>(find: () => T | undefined, timeoutMs = 3_000): Promise<T> => {
    const started = Date.now();
    for (;;) {
      const found = find();
      if (found !== undefined) return found;
      if (Date.now() - started > timeoutMs) throw new Error(`nothing within ${timeoutMs} ms`);
      await new Promise((resolve) => setTimeout(resolve, 10));
    }
  };
  const hintFor = (id: string) => waitFor(() => hints.find((h) => h.kind === 'buddySuggestion' && h.id === id));

  async function upload(start: string, serialNumber: number): Promise<string> {
    const { payload, headers } = multipartFile(`${start}.fit`, makeSyntheticDive({ serialNumber, start: new Date(start) }));
    const created = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: tim } });
    await ctx.imports.processImport(created.json().id);
    return (await json<{ outcome: { diveId: string }[] }>('GET', `/api/imports/${created.json().id}`, tim)).outcome[0]!.diveId;
  }

  beforeAll(async () => {
    t = await createTestDatabase();
    // Listen before anything is raised: a claim's hint is sent inside its transaction, on commit.
    listened = new pg.Client({ connectionString: t.pool.options.connectionString! });
    await listened.connect();
    listened.on('notification', (message) => hints.push(JSON.parse(message.payload!) as Hint));
    await listened.query('listen divehub_hints');

    ctx = await createTestApp(t, {
      fakeSsi: createFakeSsi({
        accounts: [
          { email: 'tim@example.com', password: 'ssi-password', accountId: TIM_SSI },
          { email: 'bob@example.com', password: 'ssi-password', accountId: BOB_SSI },
        ],
        buddies: [{
          owner: TIM_SSI, id: 2_271_970, buddy_master_id: BOB_SSI, firstname: 'Bob', lastname: 'Miller',
          email: 'bob@example.com', dob: '1990-01-01', phone: '0', city: 'x',
        }],
      }),
    });
    await createUser(ctx.auth, 'tim@example.com');
    bobUserId = (await createUser(ctx.auth, 'bob@example.com')).id;
    annaUserId = (await createUser(ctx.auth, 'anna@example.com')).id;
    await createUser(ctx.auth, 'admin@example.com', 'admin');
    tim = await signIn(ctx.app, 'tim@example.com');
    bob = await signIn(ctx.app, 'bob@example.com');
    anna = await signIn(ctx.app, 'anna@example.com');
    admin = await signIn(ctx.app, 'admin@example.com');
    timDiver = await ownDiver(tim);
    bobDiver = await ownDiver(bob);
    annaDiver = await ownDiver(anna);

    diveOne = await upload('2026-01-15T09:00:00Z', 111);
    diveTwo = await upload('2026-01-16T09:00:00Z', 112);
    diveThree = await upload('2026-01-17T09:00:00Z', 113);
    diveFour = await upload('2026-01-18T09:00:00Z', 114);

    // Tim's SSI buddy Bob is an external Diver; putting him on the Dive raises a suggestion no User can see yet.
    const connection = (await json<{ id: string }>('POST', '/api/connections/ssi', tim, {
      diverId: timDiver, login: 'tim@example.com', password: 'ssi-password', keepSignedIn: false,
    })).id;
    bobExternal = (await json<{ buddies: { diver: { id: string } }[] }>(
      'POST', `/api/connections/${connection}/buddies/import`, tim, { accounts: [String(BOB_SSI)],
    })).buddies[0]!.diver.id;
    expect((await setParticipants(diveOne, [{ diverId: bobExternal, role: 'buddy' }])).statusCode).toBe(200);
    claimSuggestionId = (await rowsOf(bobExternal))[0]!.id;
  });
  afterAll(async () => {
    await listened?.end();
    await ctx?.app.close();
    await t?.drop();
  });

  it('waits with no audience for a Diver no User manages', async () => {
    expect((await rowsOf(bobExternal))[0]).toMatchObject({ id: claimSuggestionId, diveId: diveOne, status: 'pending', resolvedAt: null });
    // Bob's User does not manage the external Diver yet, and Tim manages neither of them.
    for (const cookie of [tim, bob]) {
      expect((await json<SuggestionView[]>('GET', '/api/buddy-suggestions', cookie)).map((s) => s.id)).not.toContain(claimSuggestionId);
    }
  });

  it('gives the suggestion to the new manager on claim, with a hint, and it can be accepted', async () => {
    expect((await call('POST', '/api/connections/ssi', bob, {
      diverId: bobDiver, login: 'bob@example.com', password: 'ssi-password', keepSignedIn: false, claim: true,
    })).statusCode).toBe(201);

    // The external Diver merged into Bob's own: the pending suggestion follows to it.
    expect(await rowsOf(bobExternal)).toEqual([]);
    expect((await rowsOf(bobDiver)).find((s) => s.id === claimSuggestionId))
      .toMatchObject({ diveId: diveOne, status: 'pending', resolvedAt: null });

    const listed = await json<SuggestionView[]>('GET', '/api/buddy-suggestions', bob);
    expect(listed).toContainEqual(expect.objectContaining({
      id: claimSuggestionId, status: 'pending', diver: { id: bobDiver, name: 'bob' }, dive: expect.objectContaining({ id: diveOne }),
    }));

    // The claim gave the pending suggestion its first audience: Bob's User hears it.
    expect((await hintFor(claimSuggestionId)).audience).toEqual([bobUserId]);

    const response = await call('POST', `/api/buddy-suggestions/${claimSuggestionId}/accept`, bob);
    expect(response.statusCode).toBe(200);
    expect(response.json()).toMatchObject({ id: claimSuggestionId, status: 'accepted', diveId: expect.any(String) });
  });

  it('takes a merged-away Diver\'s pending suggestions to the surviving Diver\'s manager', async () => {
    const carla = (await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Carla Nowak' })).id;
    expect((await setParticipants(diveOne, [{ diverId: bobDiver, role: 'buddy' }, { diverId: carla, role: 'buddy' }])).statusCode).toBe(200);
    expect((await setParticipants(diveTwo, [{ diverId: carla, role: 'guide' }])).statusCode).toBe(200);
    const acceptedOnOne = (await rowsOf(carla)).find((s) => s.diveId === diveOne)!;
    await ctx.db.update(buddySuggestion).set({ status: 'accepted', resolvedAt: new Date() }).where(eq(buddySuggestion.id, acceptedOnOne.id));
    const pendingOnTwo = (await rowsOf(carla)).find((s) => s.diveId === diveTwo)!;

    expect((await call('POST', `/api/admin/divers/${carla}/merge`, admin, { into: annaDiver })).statusCode).toBe(200);

    // The pending one follows to Anna's Diver; the accepted one stays on the merged-away Diver.
    expect((await rowsOf(annaDiver)).find((s) => s.id === pendingOnTwo.id))
      .toMatchObject({ diveId: diveTwo, status: 'pending', resolvedAt: null });
    expect(await rowsOf(carla)).toContainEqual(expect.objectContaining({ id: acceptedOnOne.id, status: 'accepted', diveId: diveOne }));

    const listed = await json<SuggestionView[]>('GET', '/api/buddy-suggestions', anna);
    expect(listed).toContainEqual(expect.objectContaining({
      id: pendingOnTwo.id, diver: { id: annaDiver, name: 'anna' }, dive: expect.objectContaining({ id: diveTwo }),
    }));
    expect(listed.map((s) => s.id)).not.toContain(acceptedOnOne.id);
    expect((await hintFor(pendingOnTwo.id)).audience).toEqual([annaUserId]);
    expect(hints.some((h) => h.kind === 'buddySuggestion' && h.id === acceptedOnOne.id)).toBe(false);
  });

  it('leaves a declined suggestion where it is when its Diver is merged away', async () => {
    const eve = (await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Eve Lanter' })).id;
    expect((await setParticipants(diveFour, [{ diverId: eve, role: 'buddy' }])).statusCode).toBe(200);
    const declined = (await rowsOf(eve))[0]!;
    await ctx.db.update(buddySuggestion).set({ status: 'declined', resolvedAt: new Date() }).where(eq(buddySuggestion.id, declined.id));

    expect((await call('POST', `/api/admin/divers/${eve}/merge`, admin, { into: annaDiver })).statusCode).toBe(200);

    expect(await rowsOf(eve)).toContainEqual(expect.objectContaining({ id: declined.id, status: 'declined', diveId: diveFour }));
    expect((await json<SuggestionView[]>('GET', '/api/buddy-suggestions', anna)).map((s) => s.id)).not.toContain(declined.id);
    expect(hints.some((h) => h.kind === 'buddySuggestion' && h.id === declined.id)).toBe(false);
  });

  it('keeps one suggestion for a Dive the surviving Diver was already invited to', async () => {
    const dana = (await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Dana Rios' })).id;
    // Both are on the Dive: each has a pending suggestion, though only Anna's has an audience.
    expect((await setParticipants(diveThree, [{ diverId: annaDiver, role: 'buddy' }, { diverId: dana, role: 'buddy' }])).statusCode).toBe(200);
    const annaOnThree = (await rowsOf(annaDiver)).find((s) => s.diveId === diveThree)!;
    const danaOnThree = (await rowsOf(dana))[0]!;

    expect((await call('POST', `/api/admin/divers/${dana}/merge`, admin, { into: annaDiver })).statusCode).toBe(200);

    // The surviving Diver's own row wins; the merged-away one is cancelled rather than duplicated.
    expect((await rowsOf(annaDiver)).find((s) => s.id === annaOnThree.id)).toMatchObject({ status: 'pending', diveId: diveThree });
    expect(await rowsOf(dana)).toContainEqual(expect.objectContaining({ id: danaOnThree.id, status: 'cancelled', diveId: diveThree }));
    const listed = (await json<SuggestionView[]>('GET', '/api/buddy-suggestions', anna)).filter((s) => s.dive.id === diveThree);
    expect(listed.map((s) => s.id)).toEqual([annaOnThree.id]);
  });

  it('cancels a pending suggestion when the Diver it follows becomes the Dive\'s own', async () => {
    const ownDive = await upload('2026-01-19T09:00:00Z', 115);
    const erik = (await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Erik Voss' })).id;
    expect((await setParticipants(ownDive, [{ diverId: erik, role: 'buddy' }])).statusCode).toBe(200);
    const pending = (await rowsOf(erik)).find((s) => s.diveId === ownDive)!;
    expect(pending).toMatchObject({ status: 'pending' });

    // Claimed as Tim himself: the Dive is Tim's own Diver's, so the invitation cannot name that Diver.
    expect((await call('POST', `/api/admin/divers/${erik}/merge`, admin, { into: timDiver })).statusCode).toBe(200);

    expect(await rowsOf(erik)).toContainEqual(expect.objectContaining({ id: pending.id, status: 'cancelled', diveId: ownDive }));
    expect((await rowsOf(timDiver)).find((s) => s.id === pending.id)).toBeUndefined();
    expect((await json<SuggestionView[]>('GET', '/api/buddy-suggestions', tim)).map((s) => s.id)).not.toContain(pending.id);
  });
});
