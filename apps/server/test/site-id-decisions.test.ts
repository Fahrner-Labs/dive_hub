// Offers to move an account's dives from a private SSI site ID to the site's public counterpart (FAH-121): one entry per
// site and account, never another account's; keep leaves the dives private and silences the offer; move removes only
// Dive Hub's private ID, so the next send uses the public one. A typed public ID that differs stays beside the private
// one instead of replacing it.
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { dive, diveSiteExternalId } from '../src/db/schema.js';
import { loadOutgoingDive } from '../src/providers/push/outgoing-dive.js';
import { accountOf, storeId } from '../src/sites/site-ids.js';
import { createFakeSsi } from './fake-ssi.js';
import { BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase } from './support.js';

const ACCOUNT_A = 5_012_047;
const ACCOUNT_B = 6_100_001;

type Decision = {
  siteId: string; siteName: string; source: string; providerName: string;
  diverId: string; diverName: string; privateExternalId: string; publicExternalId: string;
};

describe.skipIf(!(await databaseReachable()))('offers to move dives to a public site ID', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  const users = { a: '', b: '' };
  const cookies = { a: '', b: '' };
  const divers = { a: '', b: '' };
  const accounts = { a: '', b: '' };
  let connectionA = '';
  let siteS = '';
  let siteT = '';
  let siteU = '';
  let siteV = '';
  let siteW = '';
  let diveAtT = '';

  const call = (method: 'GET' | 'PUT' | 'POST', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const decisions = async (who: 'a' | 'b') => (await call('GET', '/api/dive-site-decisions', cookies[who])).json() as Decision[];
  const decide = (who: 'a' | 'b', siteId: string, diverId: string, privateExternalId: string, choice: 'move' | 'keep', source = 'ssi') =>
    call('POST', `/api/dive-site-decisions/${siteId}`, cookies[who], { source, diverId, privateExternalId, choice });
  const rowsOf = (siteId: string) => t.db.select({ externalId: diveSiteExternalId.externalId, accountId: diveSiteExternalId.accountId })
    .from(diveSiteExternalId).where(eq(diveSiteExternalId.siteId, siteId));
  const store = (siteId: string, externalId: string, accountId: string | null) => t.db.transaction((tx) => storeId(tx, {
    siteId, source: 'ssi', externalId, accountId, actor: null,
  }));
  const newSite = async (cookie: string, name: string, latitude: number) =>
    ((await call('POST', '/api/dive-sites', cookie, { name, position: { latitude, longitude: 20 } })).json() as { id: string }).id;

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, {
      fakeSsi: createFakeSsi({
        accounts: [
          { email: 'a@example.com', password: 'ssi-password', accountId: ACCOUNT_A },
          { email: 'b@example.com', password: 'ssi-password', accountId: ACCOUNT_B },
        ],
        sites: [{ odin_dive_sites_id: 502, odin_dive_sites_name: 'Now public', odin_dive_sites_lat: 12, odin_dive_sites_lon: 20, odin_dive_sites_is_private: 0, odin_countries_code_iso: 'EGY' }],
      }),
    });
    for (const who of ['a', 'b'] as const) {
      users[who] = (await createUser(ctx.auth, `${who}@example.com`)).id;
      cookies[who] = await signIn(ctx.app, `${who}@example.com`);
      divers[who] = ((await call('GET', '/api/divers', cookies[who])).json() as { id: string; isOwn: boolean }[]).find((d) => d.isOwn)!.id;
      const connection = (await call('POST', '/api/connections/ssi', cookies[who], {
        diverId: divers[who], login: `${who}@example.com`, password: 'ssi-password', keepSignedIn: false,
      })).json() as { id: string };
      if (who === 'a') connectionA = connection.id;
      accounts[who] = (await accountOf(t.db, divers[who], 'ssi'))!;
    }
    siteS = await newSite(cookies.a, 'Ess Reef', 10);
    siteT = await newSite(cookies.a, 'Tee Reef', 11);
    siteU = await newSite(cookies.b, 'You Reef', 12);
    siteV = await newSite(cookies.a, 'Vee Reef', 13);
    siteW = await newSite(cookies.a, 'Double Vee Reef', 14);
    await store(siteS, '200', accounts.a);
    await store(siteS, '100', null);
    await store(siteT, '300', accounts.a);
    await store(siteT, '301', null);
    await store(siteU, '400', accounts.b);
    await store(siteU, '401', null);
    await store(siteV, '500', accounts.a);
    await store(siteW, '600', accounts.a);
    await store(siteW, '601', null);
    const [d] = await t.db.insert(dive).values({
      diverId: divers.a, startsAt: new Date('2026-05-01T08:00:00Z'), durationSeconds: 2400, siteId: siteT,
    }).returning({ id: dive.id });
    diveAtT = d!.id;
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('offers one entry per site and account, never another account\'s', async () => {
    const mine = await decisions('a');
    expect(mine.filter((d) => d.siteId === siteS)).toEqual([expect.objectContaining({
      siteName: 'Ess Reef', source: 'ssi', providerName: 'SSI', diverId: divers.a,
      privateExternalId: '200', publicExternalId: '100',
    })]);
    expect(mine.filter((d) => d.siteId === siteT)).toHaveLength(1);
    expect(mine.map((d) => d.siteId)).not.toContain(siteU);
    const theirs = await decisions('b');
    expect(theirs.map((d) => d.siteId)).toContain(siteU);
    expect(theirs.map((d) => d.siteId)).not.toContain(siteS);
  });

  it('keeps the private ID and does not ask again', async () => {
    expect((await decide('a', siteS, divers.a, '200', 'keep')).statusCode).toBe(204);
    expect((await decisions('a')).map((d) => d.siteId)).not.toContain(siteS);
    expect(await rowsOf(siteS)).toEqual(expect.arrayContaining([
      { externalId: '200', accountId: accounts.a }, { externalId: '100', accountId: null },
    ]));
  });

  it('moves only Dive Hub\'s side: the private ID goes, the next send uses the public one', async () => {
    expect((await loadOutgoingDive(t.db, users.a, diveAtT, { remoteSiteIds: { ssi: '300' } })).outgoing.siteIds.ssi).toBe('300');
    expect((await decide('a', siteT, divers.a, '300', 'move')).statusCode).toBe(204);
    expect(await rowsOf(siteT)).toEqual([{ externalId: '301', accountId: null }]);
    expect((await decisions('a')).map((d) => d.siteId)).not.toContain(siteT);
    expect((await loadOutgoingDive(t.db, users.a, diveAtT, { remoteSiteIds: { ssi: '300' } })).outgoing.siteIds.ssi).toBe('301');
  });

  it('refuses another account\'s offer without changing it', async () => {
    expect((await decide('b', siteW, divers.a, '600', 'move')).statusCode).toBe(409);
    expect(await rowsOf(siteW)).toEqual(expect.arrayContaining([
      { externalId: '600', accountId: accounts.a }, { externalId: '601', accountId: null },
    ]));
    expect((await decisions('a')).map((d) => d.siteId)).toContain(siteW);
  });

  it('refuses a decision about a private ID that is no longer the offer', async () => {
    expect((await decide('a', siteW, divers.a, '999', 'move')).statusCode).toBe(409);
    expect(await rowsOf(siteW)).toEqual(expect.arrayContaining([
      { externalId: '600', accountId: accounts.a }, { externalId: '601', accountId: null },
    ]));
    expect((await decisions('a')).map((d) => d.siteId)).toContain(siteW);
  });

  it('keeps a typed public ID beside the private one and offers to move', async () => {
    const typed = await call('PUT', `/api/connections/${connectionA}/dive-sites/${siteV}`, cookies.a, { externalId: '502' });
    expect(typed.statusCode).toBe(200);
    expect(typed.json()).toEqual({ externalId: '502', private: false });
    expect(await rowsOf(siteV)).toEqual(expect.arrayContaining([
      { externalId: '500', accountId: accounts.a }, { externalId: '502', accountId: null },
    ]));
    const offer = (await decisions('a')).find((d) => d.siteId === siteV);
    expect(offer).toMatchObject({ privateExternalId: '500', publicExternalId: '502' });
    expect((await decide('a', siteV, divers.a, '500', 'move')).statusCode).toBe(204);
    expect(await rowsOf(siteV)).toEqual([{ externalId: '502', accountId: null }]);
  });
});
