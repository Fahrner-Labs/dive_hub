// Buddy suggestions (ADR 0070): a Participant who is not the Dive's own Diver raises a pending suggestion for whoever
// manages them; removing the Participant or deleting the Dive cancels it, and a cancelled or declined one comes back as
// pending when the Participant is added again. The audience is computed from diver_management at read and notify time.
import { and, eq } from 'drizzle-orm';
import pg from 'pg';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { buddySuggestion, dive, participant } from '../src/db/schema.js';
import { parseHint } from '../src/live/emit.js';
import { syncSuggestions } from '../src/social/suggestion-service.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type SuggestionView = {
  id: string; status: string; createdAt: string; resolvedAt: string | null;
  diver: { id: string; name: string };
  dive: { id: string; startsAt: string; utcOffsetSeconds: number | null; durationSeconds: number; maxDepthM: number | null; site: { id: string; name: string } | null };
};
type PreviewView = SuggestionView & {
  dive: SuggestionView['dive'] & {
    avgDepthM: number | null;
    owner: { id: string; name: string };
    centres: { id: string; name: string }[];
    participants: { diverId: string; name: string; role: string }[];
  };
};

describe('the buddySuggestion hint', () => {
  it('is a known kind, with a named audience', () => {
    expect(parseHint(JSON.stringify({ v: 1, kind: 'buddySuggestion', id: 'suggestion-1', audience: ['user-1'] })))
      .toEqual({ kind: 'buddySuggestion', id: 'suggestion-1', audience: ['user-1'] });
    expect(parseHint(JSON.stringify({ v: 1, kind: 'buddySuggestion', id: 'suggestion-1', audience: '*' }))).toBeNull();
  });
});

describe.skipIf(!(await databaseReachable()))('Buddy suggestions', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let anna: string;
  let timUserId: string;
  let annaUserId: string;
  let timDiver: string;
  let annaDiver: string;
  let diveId: string;

  const call = (method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Parameters<typeof call>[0], url: string, cookie: string, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;
  const ownDiver = async (cookie: string) => (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers', cookie)).find((d) => d.isOwn)!.id;
  const versionOf = async () => (await json<{ version: number }>('GET', `/api/dives/${diveId}`, tim)).version;
  const setParticipants = async (participants: { diverId: string; role: string }[], cookie = tim) =>
    call('PUT', `/api/dives/${diveId}/participants`, cookie, { version: await versionOf(), participants });
  const suggestionsOf = () => ctx.db.select().from(buddySuggestion).orderBy(buddySuggestion.createdAt);
  const suggestionFor = async (diverId: string) => (await suggestionsOf()).find((s) => s.diverId === diverId);

  const waitFor = async <T>(find: () => T | undefined, timeoutMs = 3_000): Promise<T> => {
    const started = Date.now();
    for (;;) {
      const found = find();
      if (found !== undefined) return found;
      if (Date.now() - started > timeoutMs) throw new Error(`nothing within ${timeoutMs} ms`);
      await new Promise((resolve) => setTimeout(resolve, 10));
    }
  };

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    timUserId = (await createUser(ctx.auth, 'tim@example.com')).id;
    annaUserId = (await createUser(ctx.auth, 'anna@example.com')).id;
    tim = await signIn(ctx.app, 'tim@example.com');
    anna = await signIn(ctx.app, 'anna@example.com');
    timDiver = await ownDiver(tim);
    annaDiver = await ownDiver(anna);
    const { payload, headers } = multipartFile('main.fit', makeSyntheticDive({ serialNumber: 111 }));
    const imported = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: tim } });
    await ctx.imports.processImport(imported.json().id);
    diveId = (await json<{ outcome: { diveId: string }[] }>('GET', `/api/imports/${imported.json().id}`, tim)).outcome[0]!.diveId;
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('raises a pending suggestion when another User\'s Diver is added as a Participant', async () => {
    expect((await setParticipants([{ diverId: annaDiver, role: 'buddy' }])).statusCode).toBe(200);
    const raised = await suggestionFor(annaDiver);
    expect(raised).toMatchObject({ diveId, diverId: annaDiver, status: 'pending', resolvedAt: null });

    // The audience is read from diver_management now: Anna manages her Diver and sees it; Tim does not.
    const hers = await json<SuggestionView[]>('GET', '/api/buddy-suggestions', anna);
    expect(hers).toHaveLength(1);
    expect(hers[0]).toMatchObject({ id: raised!.id, status: 'pending', diver: { id: annaDiver, name: 'anna' }, dive: { id: diveId } });
    expect(await json<SuggestionView[]>('GET', '/api/buddy-suggestions', tim)).toEqual([]);
  });

  it('raises nothing for the Dive\'s own Diver', async () => {
    await ctx.db.insert(participant).values({ diveId, diverId: timDiver, role: 'buddy' });
    await syncSuggestions(ctx.db, diveId);
    expect(await suggestionFor(timDiver)).toBeUndefined();
    await ctx.db.delete(participant).where(and(eq(participant.diveId, diveId), eq(participant.diverId, timDiver)));
  });

  it('raises a pending row for an external Diver, and nobody gets a hint or a list entry', async () => {
    const bob = await json<{ id: string; name: string }>('POST', '/api/external-divers', tim, { name: 'Bob Miller' });
    expect((await setParticipants([{ diverId: annaDiver, role: 'buddy' }, { diverId: bob.id, role: 'buddy' }])).statusCode).toBe(200);
    expect(await suggestionFor(bob.id)).toMatchObject({ status: 'pending' });
    // Bob is managed by no User: neither Tim nor Anna sees a suggestion for him.
    for (const cookie of [tim, anna]) {
      expect((await json<SuggestionView[]>('GET', '/api/buddy-suggestions', cookie)).map((s) => s.diver.id)).not.toContain(bob.id);
    }
  });

  it('cancels a pending one when the Participant is removed, and re-adding after a decline resets it to pending', async () => {
    const annas = (await suggestionFor(annaDiver))!;
    await ctx.db.update(buddySuggestion).set({ status: 'declined', resolvedAt: new Date() }).where(eq(buddySuggestion.id, annas.id));
    const bob = (await suggestionsOf()).find((s) => s.diverId !== annaDiver)!;

    // Taking Anna off leaves her declined (only pending ones are cancelled); Bob stays on, so his pending one stays.
    await setParticipants([{ diverId: bob.diverId, role: 'buddy' }]);
    expect(await suggestionFor(annaDiver)).toMatchObject({ status: 'declined' });
    expect(await suggestionFor(bob.diverId)).toMatchObject({ status: 'pending' });

    // Adding Anna again raises hers again, and cancels Bob's pending one as he comes off.
    await setParticipants([{ diverId: annaDiver, role: 'buddy' }]);
    expect(await suggestionFor(annaDiver)).toMatchObject({ status: 'pending', resolvedAt: null });
    expect(await suggestionFor(bob.diverId)).toMatchObject({ status: 'cancelled', resolvedAt: expect.any(Date) });

    // Removing her cancels the pending one.
    await setParticipants([]);
    expect(await suggestionFor(annaDiver)).toMatchObject({ status: 'cancelled' });

    // A cancelled one comes back as pending when she is added again.
    await setParticipants([{ diverId: annaDiver, role: 'buddy' }]);
    expect(await suggestionFor(annaDiver)).toMatchObject({ status: 'pending', resolvedAt: null });
  });

  it('has one suggestion per Dive and invited Diver', async () => {
    await expect(ctx.db.insert(buddySuggestion).values({ diveId, diverId: annaDiver })).rejects.toThrow();
  });

  it('previews shared facts only, for the User who manages the invited Diver', async () => {
    const raised = (await suggestionFor(annaDiver))!;
    const preview = await json<PreviewView>('GET', `/api/buddy-suggestions/${raised.id}`, anna);
    expect(preview).toMatchObject({
      id: raised.id, status: 'pending', diver: { id: annaDiver, name: 'anna' },
      dive: { id: diveId, startsAt: expect.any(String), utcOffsetSeconds: expect.anything(), durationSeconds: expect.any(Number) },
    });
    // The facts a copy may take, and nothing of the Recording, notes, suit, weighting, cylinders, lead or overrides.
    expect(Object.keys(preview.dive).sort()).toEqual([
      'avgDepthM', 'centres', 'durationSeconds', 'id', 'maxDepthM', 'owner', 'participants', 'site', 'startsAt', 'utcOffsetSeconds',
    ]);
    expect(preview.dive.owner).toEqual({ id: timDiver, name: 'tim' });
    // Tim manages neither Anna nor the suggestion, so it stays invisible to him.
    expect((await call('GET', `/api/buddy-suggestions/${raised.id}`, tim)).statusCode).toBe(404);
  });

  it('tells the Users who manage the Diver, and nobody for an unmanaged one', async () => {
    const listened = new pg.Client({ connectionString: t.pool.options.connectionString! });
    await listened.connect();
    const hints: { kind: string; id: string; audience: string[] }[] = [];
    listened.on('notification', (message) => hints.push(JSON.parse(message.payload!)));
    await listened.query('listen divehub_hints');
    try {
      const raised = (await suggestionFor(annaDiver))!;
      // Removing a pending suggestion cancels it and tells Anna's User; nobody manages Bob.
      expect((await setParticipants([{ diverId: (await suggestionsOf()).find((s) => s.diverId !== annaDiver)!.diverId, role: 'buddy' }])).statusCode).toBe(200);
      const hint = await waitFor(() => hints.find((h) => h.kind === 'buddySuggestion' && h.id === raised.id));
      expect(hint.audience).toEqual([annaUserId]);
      expect(hints.some((h) => h.kind === 'buddySuggestion' && h.audience.length === 0)).toBe(false);
    } finally {
      await listened.end();
    }
  });

  it('cancels pending suggestions when the Dive is deleted; accepted ones stay', async () => {
    const bob = await json<{ id: string }>('POST', '/api/external-divers', tim, { name: 'Bob Again' });
    await setParticipants([{ diverId: annaDiver, role: 'buddy' }, { diverId: bob.id, role: 'buddy' }]);
    const annas = (await suggestionFor(annaDiver))!;
    await ctx.db.update(buddySuggestion).set({ status: 'accepted', resolvedAt: new Date() }).where(eq(buddySuggestion.id, annas.id));

    const { version } = await ctx.db.select({ version: dive.version }).from(dive).where(eq(dive.id, diveId)).then((rows) => rows[0]!);
    await ctx.dives.remove(timUserId, diveId, version);

    expect(await suggestionFor(annaDiver)).toMatchObject({ status: 'accepted' });
    expect(await suggestionFor(bob.id)).toMatchObject({ status: 'cancelled' });
  });
});
