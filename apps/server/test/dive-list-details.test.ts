// The logbook list's batched dive-details mode (FAH-297): `include=details` answers each row as the DiveView of
// `GET /api/dives/{id}`, so a native client's initial sync reads the logbook a page at a time instead of one dive
// at a time. Same scoping, filters, totals and order; a lower page cap.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type Page = {
  dives: ({ id: string } & Record<string, unknown>)[];
  total: number;
  counts: Record<string, number>;
  totals: Record<string, unknown>;
  months: unknown[];
};
type DiveView = { id: string; version: number; suit: object | null; leadTotalKg: number | null; cylinders: object[] };

const SUMMARY_KEYS = [
  'avgDepthM', 'diverId', 'durationSeconds', 'findings', 'fromProvider', 'gases', 'id', 'maxDepthM', 'number',
  'participants', 'profile', 'recordings', 'site', 'startsAt', 'surfaceIntervalSeconds', 'utcOffsetSeconds',
  'utcOffsetSource', 'waiting',
].sort();

describe.skipIf(!(await databaseReachable()))('the logbook list\'s batched dive-details mode', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let ann: string;
  let other: string;
  let richer: string;
  let deleted: string;
  let otherDive: string;

  const call = (method: 'GET' | 'PATCH' | 'POST' | 'DELETE', url: string, cookie = ann, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const list = async (query = '', cookie = ann) => (await call('GET', `/api/dives${query}`, cookie)).json() as Page;
  const getDive = async (id: string, cookie = ann) => (await call('GET', `/api/dives/${id}`, cookie)).json() as DiveView;
  const ids = (page: Page) => page.dives.map((d) => d.id);
  const versionOf = async (id: string) => (await getDive(id)).version;

  async function upload(fileName: string, data: Uint8Array, cookie = ann) {
    const { payload, headers } = multipartFile(fileName, data);
    const response = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie } });
    await ctx.imports.processImport(response.json().id);
    return (await call('GET', `/api/imports/${response.json().id}`, cookie)).json().outcome[0] as { diveId: string };
  }

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'ann@example.com');
    await createUser(ctx.auth, 'other@example.com');
    ann = await signIn(ctx.app, 'ann@example.com');
    other = await signIn(ctx.app, 'other@example.com');
    // Four Dives of ann's Diver and one of other's: 1 is the rich one, 4 is deleted below.
    const annDives: string[] = [];
    for (let day = 1; day <= 4; day++) {
      annDives.push((await upload(`ann-${day}.fit`, makeSyntheticDive({
        serialNumber: 100, start: new Date(Date.UTC(2026, 3, day, 9)), diveNumber: day,
      }))).diveId);
    }
    otherDive = (await upload('other.fit', makeSyntheticDive({
      serialNumber: 200, start: new Date(Date.UTC(2026, 3, 5, 9)), diveNumber: 1,
    }), other)).diveId;
    richer = annDives[0]!;
    deleted = annDives[3]!;

    const site = (await call('POST', '/api/dive-sites', ann, {
      name: 'Hausreef', waterType: 'salt',
    })).json() as { id: string };
    const rich = await call('PATCH', `/api/dives/${richer}`, ann, {
      version: await versionOf(richer),
      notes: 'Turtle',
      siteId: site.id,
      suit: { type: 'drysuit', undergarment: 'heavy' },
      lead: [{ amountKg: 6, placement: 'belt' }, { amountKg: 1, placement: 'trim' }],
      weightingFeedback: { felt: 'too_heavy', amountKg: 1 },
      cylinders: [{ volumeL: 12, workingPressureBar: 232, material: 'steel', gas: { o2: 21, he: 0 } }],
    });
    expect(rich.statusCode).toBe(200);

    expect((await call('DELETE', `/api/dives/${deleted}`, ann, { version: await versionOf(deleted) })).statusCode).toBe(200);
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('answers each row as the Dive of GET /api/dives/{id}', async () => {
    const page = await list('?include=details&limit=50');
    expect(page.total).toBe(3);
    for (const row of page.dives) {
      expect(row).toEqual(await getDive(row.id));
    }
    const richRow = page.dives.find((d) => d.id === richer)!;
    expect(richRow).toMatchObject({
      notes: 'Turtle',
      waterType: 'salt',
      suit: { type: 'drysuit', undergarment: 'heavy' },
      leadTotalKg: 7,
      weightingFeedback: { felt: 'too_heavy', amountKg: 1 },
      cylinders: [{ volumeL: 12, gas: { o2: 21, he: 0 } }],
    });
  });

  it('keeps the default mode a summary row, shaped as before', async () => {
    const plain = await list();
    expect(plain.dives).toHaveLength(3);
    for (const row of plain.dives) expect(Object.keys(row).sort()).toEqual(SUMMARY_KEYS);
    expect(plain.dives.find((d) => d.id === richer)).not.toHaveProperty('cylinders');
  });

  it('pages, sorts and totals exactly as the default mode does', async () => {
    const plain = await list('?limit=1&offset=1&sort=startsAt&order=asc');
    const details = await list('?include=details&limit=1&offset=1&sort=startsAt&order=asc');
    expect(details.total).toBe(plain.total);
    expect(details.counts).toEqual(plain.counts);
    expect(details.totals).toEqual(plain.totals);
    expect(details.months).toEqual(plain.months);
    expect(ids(details)).toEqual(ids(plain));
  });

  it('shows only the managed Divers\' live Dives', async () => {
    const all = ids(await list('?include=details&limit=50'));
    expect(all).not.toContain(otherDive);
    expect(all).not.toContain(deleted);
    expect(ids(await list('?include=details&limit=50', other))).toEqual([otherDive]);
  });

  it('caps a details page at 50, leaving the default mode\'s 200', async () => {
    expect((await call('GET', '/api/dives?include=details&limit=50')).statusCode).toBe(200);
    const tooMany = await call('GET', '/api/dives?include=details&limit=51');
    expect(tooMany.statusCode).toBe(400);
    expect(tooMany.json()).toMatchObject({ code: 'invalid_input' });
    expect((await call('GET', '/api/dives?limit=200')).statusCode).toBe(200);
    expect((await call('GET', '/api/dives?include=everything')).statusCode).toBe(400);
  });
});
