// The admin's Source purge through the HTTP API (FAH-528): the Source's External IDs go, import-created sites
// with no data and no Dive go, and sites a Dive still names are reported. After the admin moves the Dive, a second
// run completes clean. Sites a User made and sites another Source still feeds stay, without the purged IDs.
import { afterAll, beforeAll, beforeEach, describe, expect, it } from 'vitest';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import type { ImportArea, ImportedValues, SiteSourceAdapter, SourceSite } from '../src/sites/import/site-source.js';
import { createSitePurgeService } from '../src/sites/import/site-purge-service.js';
import type { ImportSource } from '../src/sites/sources.js';
import { BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase } from './support.js';

type Site = { id: string; name: string; version: number; externalIds: { source: string; externalId: string; providesData: boolean }[] };
type Dive = { id: string; version: number; site: { id: string } | null };
type SiteImport = { id: string; status: string };
type SitePurge = {
  id: string; status: string;
  result: { sourceIdsDeleted: number; sitesDeleted: number; referencedSites: { id: string; name: string; dives: number }[] } | null;
};

const POSITION = { latitude: 28.4950, longitude: 34.5160 };
const ELSEWHERE = { latitude: 28.6, longitude: 34.7 };
const EGYPT: ImportArea = { kind: 'country', country: 'EG' };

const values = (v: Partial<ImportedValues>): ImportedValues => ({
  name: 'House Reef', position: POSITION, country: 'EG', waterBody: null, description: null, maxDepthM: null, waterType: null, ...v,
});

/** A Source that answers with what the test put there this run. */
function standIn(source: ImportSource) {
  let answer: SourceSite[] = [];
  const adapter: SiteSourceAdapter = { source, fetch: async () => answer };
  return {
    adapter,
    answers(sites: { id: string; v?: Partial<ImportedValues> }[]) {
      answer = sites.map((s) => ({ source, externalId: s.id, values: values(s.v ?? {}), sameAs: {} }));
    },
    empty() { answer = []; },
  };
}

describe.skipIf(!(await databaseReachable()))('Source purge', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  const osm = standIn('osm');
  const wikidata = standIn('wikidata');
  const ssi = standIn('ssi');
  let admin: string;
  let tim: string;

  type Method = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
  const call = (method: Method, url: string, cookie = admin, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: Method, url: string, cookie = admin, payload?: object) =>
    (await call(method, url, cookie, payload)).json() as T;

  const siteNamed = async (name: string, cookie = admin) =>
    (await json<{ sites: Site[] }>('GET', `/api/dive-sites?q=${encodeURIComponent(name)}`, cookie)).sites.find((s) => s.name === name);

  async function runSiteImport(body: object) {
    const started = await call('POST', '/api/admin/site-imports', admin, body);
    expect(started.statusCode).toBe(202);
    await ctx.siteImports.run(started.json().id);
    return json<SiteImport>('GET', `/api/admin/site-imports/${started.json().id}`);
  }

  async function uploadDive() {
    const { payload, headers } = multipartFile('reef.fit', makeSyntheticDive({
      serialNumber: 7_300_001, start: new Date('2026-03-01T08:00:00Z'), exit: ELSEWHERE,
    }));
    const started = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: tim } });
    await ctx.imports.processImport(started.json().id);
    return (await json<{ outcome: { diveId: string }[] }>('GET', `/api/imports/${started.json().id}`, tim)).outcome[0]!.diveId;
  }

  async function startPurge(source = 'ssi') {
    const response = await call('POST', '/api/admin/site-imports/purge', admin, { source });
    return response;
  }

  async function runPurge(source = 'ssi') {
    const started = await startPurge(source);
    expect(started.statusCode).toBe(202);
    await ctx.sitePurges.run(started.json().id);
    return json<SitePurge>('GET', `/api/admin/site-imports/purges/${started.json().id}`);
  }

  /** The raw live hints on this database: what an open page would hear (ADR 0066). */
  async function listenHints() {
    const listener = await t.pool.connect();
    const seen: { kind: string; id: string; audience: unknown }[] = [];
    listener.on('notification', (message) => {
      if (message.channel === 'divehub_hints' && message.payload) seen.push(JSON.parse(message.payload) as never);
    });
    await listener.query('listen divehub_hints');
    return {
      async waitFor(predicate: (hint: { kind: string; id: string }) => boolean) {
        for (let i = 0; i < 200; i++) {
          const found = seen.find(predicate);
          if (found) return found;
          await new Promise((resolve) => setTimeout(resolve, 10));
        }
        throw new Error(`no matching hint; got ${JSON.stringify(seen)}`);
      },
      async close() {
        await listener.query('unlisten divehub_hints');
        listener.release();
      },
    };
  }

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, { siteSources: { osm: osm.adapter, wikidata: wikidata.adapter, ssi: ssi.adapter } });
    await createUser(ctx.auth, 'admin@example.com', 'admin');
    await createUser(ctx.auth, 'tim@example.com');
    admin = await signIn(ctx.app, 'admin@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });
  beforeEach(() => {
    osm.empty();
    wikidata.empty();
    ssi.empty();
  });

  it('is for admins only', async () => {
    const refused = await call('POST', '/api/admin/site-imports/purge', tim, { source: 'ssi' });
    expect(refused.statusCode).toBe(403);
    expect(refused.json().code).toBe('admins_only');
    expect((await call('GET', '/api/admin/site-imports/purges', tim)).statusCode).toBe(403);
  });

  it('runs one at a time', async () => {
    const first = await startPurge();
    expect(first.statusCode).toBe(202);
    const second = await startPurge();
    expect(second.statusCode).toBe(409);
    expect(second.json().code).toBe('site_purge_running');
    await ctx.sitePurges.run(first.json().id);
  });

  it('never runs beside a Site import', async () => {
    ssi.answers([]);
    const started = await call('POST', '/api/admin/site-imports', admin, { sources: ['ssi'], area: EGYPT, language: 'en', confirmSsi: true });
    expect(started.statusCode).toBe(202);
    const refused = await startPurge();
    expect(refused.statusCode).toBe(409);
    expect(refused.json().code).toBe('site_import_running');
    await ctx.siteImports.run(started.json().id);

    // The other way round: a queued purge refuses a Site import.
    const queued = await startPurge();
    expect(queued.statusCode).toBe(202);
    const imported = await call('POST', '/api/admin/site-imports', admin, { sources: ['ssi'], area: EGYPT, language: 'en', confirmSsi: true });
    expect(imported.statusCode).toBe(409);
    expect(imported.json().code).toBe('site_import_running');
    await ctx.sitePurges.run(queued.json().id);
  });

  it('refuses a source this deployment switched off', async () => {
    const onlySsi = createSitePurgeService({ db: ctx.db, sources: { ssi: {} } });
    await expect(onlySsi.start('00000000-0000-7000-8000-000000000000', 'osm')).rejects.toMatchObject({ code: 'site_source_disabled' });
  });

  it('deletes the Source\'s IDs and the sites they leave empty, and reports the sites Dives still name', async () => {
    // An SSI import creates four sites: one unused, one used by a Dive, one a User made (below), one also fed by
    // Wikidata.
    ssi.answers([
      { id: '1001', v: { name: 'Unused Reef', position: { latitude: 27.0, longitude: 33.0 } } },
      { id: '1002', v: { name: 'Used Reef', position: { latitude: 28.0, longitude: 34.0 } } },
      { id: '1003', v: { name: 'Both Reef', position: { latitude: 29.0, longitude: 35.0 } } },
    ]);
    await runSiteImport({ sources: ['ssi'], area: EGYPT, language: 'en', confirmSsi: true });

    // Wikidata also feeds Both Reef: the site keeps its data after the SSI IDs go.
    wikidata.answers([{ id: 'Q1003', v: { name: 'Both Reef', position: { latitude: 29.0, longitude: 35.0 } } }]);
    await runSiteImport({ sources: ['wikidata'], area: EGYPT, language: 'en' });

    const unused = (await siteNamed('Unused Reef'))!;
    const used = (await siteNamed('Used Reef'))!;
    const both = (await siteNamed('Both Reef'))!;
    expect(both.externalIds.map((e) => e.source).sort()).toEqual(['ssi', 'wikidata']);

    // A Site a User made with an SSI ID typed on it: its ID goes, the site stays.
    const mine = await json<Site>('POST', '/api/dive-sites', tim, { name: 'My Reef', position: { latitude: 30.0, longitude: 36.0 } });
    expect((await call('PUT', `/api/dive-sites/${mine.id}/external-ids/ssi`, tim, { externalId: '1004' })).statusCode).toBe(200);

    // The Dive names Used Reef, so the purge must not delete it.
    const diveId = await uploadDive();
    const dive = await json<Dive>('GET', `/api/dives/${diveId}`, tim);
    expect((await call('PATCH', `/api/dives/${diveId}`, tim, { version: dive.version, siteId: used.id })).statusCode).toBe(200);

    const hints = await listenHints();
    const purge = await runPurge();
    expect(purge.status).toBe('done');
    expect(purge.result!.sourceIdsDeleted).toBe(4);
    expect(purge.result!.sitesDeleted).toBe(1);
    expect(purge.result!.referencedSites).toEqual([{ id: used.id, name: 'Used Reef', dives: 1 }]);

    // Removing a site is shared data: the wildcard site hint reaches every open list, like a User's site delete.
    const gone = await hints.waitFor((hint) => hint.kind === 'site' && hint.id === unused.id);
    expect(gone.audience).toBe('*');
    await hints.close();

    // The unused import site and every SSI ID are gone; the Site a User made stays without the ID.
    expect((await call('GET', `/api/dive-sites/${unused.id}`)).statusCode).toBe(404);
    expect((await json<Site>('GET', `/api/dive-sites/${used.id}`)).externalIds).toEqual([]);
    expect((await json<Site>('GET', `/api/dive-sites/${mine.id}`)).externalIds).toEqual([]);
    expect((await json<Site>('GET', `/api/dive-sites/${both.id}`)).externalIds).toEqual([expect.objectContaining({ source: 'wikidata', providesData: true })]);

    // The admin moves the Dive to the site Wikidata still feeds; the second run completes clean.
    const before = await json<Dive>('GET', `/api/dives/${diveId}`, tim);
    expect((await call('PATCH', `/api/dives/${diveId}`, tim, { version: before.version, siteId: both.id })).statusCode).toBe(200);
    const second = await runPurge();
    expect(second.status).toBe('done');
    expect(second.result).toEqual({ sourceIdsDeleted: 0, sitesDeleted: 1, referencedSites: [] });
    expect((await call('GET', `/api/dive-sites/${used.id}`)).statusCode).toBe(404);
  });
});
