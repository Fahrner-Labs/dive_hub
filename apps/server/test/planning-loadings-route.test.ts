// Current loadings over HTTP (ADR 0071): a Diver the User manages, the N₂ estimate with its provenance, CNS and OTU
// with their null-versus-zero rule, the no-fly time, and what another User never sees. The figures themselves are
// tested in planning-loadings.test.ts; here the route reads a real logbook and says what rests on what.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { dive } from '../src/db/schema.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

const HOUR_MS = 3_600_000;

type Loadings = {
  computedAt: string;
  basis: { windowHours: number; divesUsed: number; divesSkippedNoProfile: number; lastDiveEndedAt: string | null };
  n2: { worstCompartmentPercent: number; gfHigh: number } | null;
  cns: { percentNow: number; percentAtLastDiveEnd: number; halfLifeMinutes: number } | null;
  otu: { last24Hours: number } | null;
  noFly: { hours: number; reason: string; until: string; source: { title: string; url: string } } | null;
};

describe.skipIf(!(await databaseReachable()))('current loadings from the logged dives', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  const cookies: Record<string, string> = {};
  const divers: Record<string, string> = {};

  const call = (who: string, diverId: string) => ctx.app.inject({
    method: 'GET', url: `/api/divers/${diverId}/loadings`, headers: { cookie: cookies[who]!, origin: BASE_URL },
  });
  const loadings = async (who: string, diverId: string) => (await call(who, diverId)).json() as Loadings;

  async function user(who: string) {
    await createUser(ctx.auth, `${who}@example.com`);
    cookies[who] = await signIn(ctx.app, `${who}@example.com`);
    divers[who] = ((await (await ctx.app.inject({
      method: 'GET', url: '/api/divers', headers: { cookie: cookies[who]!, origin: BASE_URL },
    })).json()) as { id: string; isOwn: boolean }[]).find((d) => d.isOwn)!.id;
  }

  /** Uploads a dive computer's file, runs its Import, and returns the Dive. */
  async function upload(who: string, options: Parameters<typeof makeSyntheticDive>[0]) {
    const { payload, headers } = multipartFile('dive.fit', makeSyntheticDive(options));
    const imported = await ctx.app.inject({
      method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: cookies[who]! },
    });
    await ctx.imports.processImport(imported.json().id);
    return ((await (await ctx.app.inject({
      method: 'GET', url: `/api/imports/${imported.json().id}`, headers: { cookie: cookies[who]!, origin: BASE_URL },
    })).json()) as { outcome: { diveId: string }[] }).outcome[0]!.diveId;
  }

  /** Sets values in the Recording's summary, as a computer that carries CNS, OTU or a GF would. */
  const summaryOf = (diveId: string, values: object) => t.pool.query(
    'update recording set summary = summary || $2::jsonb where dive_id = $1', [diveId, JSON.stringify(values)],
  );
  const withoutSummary = (diveId: string, keys: readonly string[]) => t.pool.query(
    `update recording set summary = summary ${keys.map((_, i) => `- $${i + 2}`).join(' ')} where dive_id = $1`, [diveId, ...keys],
  );

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    for (const who of ['tim', 'bob', 'recent', 'mixed', 'stale', 'zero']) await user(who);
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('answers no loadings and an empty basis for a Diver with no Dives', async () => {
    const answer = await call('tim', divers.tim!);
    expect(answer.statusCode).toBe(200);
    const body = answer.json() as Loadings;
    expect(body).toMatchObject({
      basis: { windowHours: 72, divesUsed: 0, divesSkippedNoProfile: 0, lastDiveEndedAt: null },
      n2: null, cns: null, otu: null, noFly: null,
    });
    expect(Math.abs(new Date(body.computedAt).getTime() - Date.now())).toBeLessThan(10_000);
  });

  it('does not tell another User about a Diver', async () => {
    const answer = await call('tim', divers.bob!);
    expect(answer.statusCode).toBe(404);
    expect(answer.json()).toMatchObject({ code: 'diver_not_found' });
  });

  it('wants a signed-in User', async () => {
    const answer = await ctx.app.inject({ method: 'GET', url: `/api/divers/${divers.tim}/loadings`, headers: { origin: BASE_URL } });
    expect(answer.statusCode).toBe(401);
  });

  it('replays the last 72 h, decays CNS, sums OTU and names the Dives it used', async () => {
    const start = new Date(Math.floor((Date.now() - 2 * HOUR_MS) / 1000) * 1000);
    const diveId = await upload('recent', { serialNumber: 901, start, durationSeconds: 30 * 60 });
    await summaryOf(diveId, { gfHigh: 70, cnsEnd: 80, otuStart: 2, otuEnd: 12 });

    const body = await loadings('recent', divers.recent!);
    const endMs = start.getTime() + 30 * 60_000;
    expect(body.basis).toEqual({
      windowHours: 72, divesUsed: 1, divesSkippedNoProfile: 0, lastDiveEndedAt: new Date(endMs).toISOString(),
    });
    // 18.5 m for half an hour leaves tissue pressure above clean surface saturation, at the Diver's own GF high.
    expect(body.n2!.gfHigh).toBe(70);
    expect(body.n2!.worstCompartmentPercent).toBeGreaterThan(0);
    // The latest Recording carries CNS 80: the 90-minute half-life brings it down from there.
    expect(body.cns!.percentAtLastDiveEnd).toBe(80);
    expect(body.cns!.halfLifeMinutes).toBe(90);
    const elapsedMin = (new Date(body.computedAt).getTime() - endMs) / 60_000;
    expect(body.cns!.percentNow).toBeCloseTo(80 * 0.5 ** (elapsedMin / 90), 0);
    expect(body.cns!.percentNow).toBeLessThan(body.cns!.percentAtLastDiveEnd);
    expect(body.otu).toEqual({ last24Hours: 10 });
    // DAN's guideline for a single dive that day.
    expect(body.noFly).toMatchObject({ hours: 12, reason: 'single', until: new Date(endMs + 12 * HOUR_MS).toISOString() });
    expect(body.noFly!.source.url).toMatch(/^https:\/\/dan\.org/);
  });

  it('counts the Dives it skipped for having no profile, and takes the no-fly time from the latest Dive', async () => {
    const profiled = new Date(Math.floor((Date.now() - 3 * HOUR_MS) / 1000) * 1000);
    const manualStart = new Date(Math.floor((Date.now() - HOUR_MS) / 1000) * 1000);
    await upload('mixed', { serialNumber: 902, start: profiled, durationSeconds: 30 * 60 });
    const [manual] = await t.db.insert(dive).values({
      diverId: divers.mixed!, startsAt: manualStart, utcOffsetSeconds: 2 * 3600, durationSeconds: 30 * 60, maxDepthM: 12,
    }).returning({ id: dive.id });

    const body = await loadings('mixed', divers.mixed!);
    expect(body.basis.divesUsed).toBe(1);
    expect(body.basis.divesSkippedNoProfile).toBe(1);
    expect(body.n2).not.toBeNull();
    // The manual Dive is the latest and shares its day with the profiled one: DAN's several-dives guideline.
    expect(body.basis.lastDiveEndedAt).toBe(new Date(manualStart.getTime() + 30 * 60_000).toISOString());
    expect(body.noFly).toMatchObject({ hours: 18, reason: 'several' });
    expect(manual!.id).toBeDefined();
  });

  it('is stale after 48 h without a Dive and still says when the last one ended', async () => {
    const start = new Date(Math.floor((Date.now() - 50 * HOUR_MS) / 1000) * 1000);
    await upload('stale', { serialNumber: 903, start, durationSeconds: 30 * 60 });
    const body = await loadings('stale', divers.stale!);
    expect(body.n2).toBeNull();
    expect(body.basis).toMatchObject({ divesUsed: 1, divesSkippedNoProfile: 0 });
    expect(body.basis.lastDiveEndedAt).toBe(new Date(start.getTime() + 30 * 60_000).toISOString());
    // The Recording carries CNS even though it is old, and no Dive ended within OTU's 24 h.
    expect(body.cns).not.toBeNull();
    expect(body.otu).toBeNull();
  });

  it('tells a real zero from no value at all for CNS and OTU', async () => {
    const diveId = await upload('zero', { serialNumber: 904, start: new Date(Math.floor((Date.now() - HOUR_MS) / 1000) * 1000), durationSeconds: 30 * 60 });
    await summaryOf(diveId, { cnsEnd: 0, otuStart: 0, otuEnd: 0 });
    const zero = await loadings('zero', divers.zero!);
    expect(zero.cns).toMatchObject({ percentNow: 0, percentAtLastDiveEnd: 0, halfLifeMinutes: 90 });
    expect(zero.otu).toEqual({ last24Hours: 0 });

    await withoutSummary(diveId, ['cnsEnd', 'otuStart', 'otuEnd']);
    const absent = await loadings('zero', divers.zero!);
    expect(absent.cns).toBeNull();
    expect(absent.otu).toBeNull();
    expect(absent.n2).not.toBeNull();
  });
});
