// The T12 exit gate at the HTTP boundary (FAH-458 §5 scenario 1): two sessions of one Diver edit the same Dive.
// Disjoint fields converge after exactly one 412-with-the-current-view; the same field answers one 412 and is decided
// by the client (here: the server's side, which is no further write). The shared-core twin runs in
// apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/write/WriteExitGateTest.kt.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { dive } from '../src/db/schema.js';
import { BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase } from './support.js';

type DiveView = {
  id: string;
  version: number;
  notes: string | null;
  values: { maxDepthM: number };
};

describe.skipIf(!(await databaseReachable()))('two-client edit race (exit gate)', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let phone: string;
  let web: string;
  let diverId: string;

  const inject = (
    method: 'GET' | 'PATCH',
    url: string,
    cookie: string,
    payload?: object,
    headers?: Record<string, string>,
  ) => ctx.app.inject({
    method, url, headers: { cookie, origin: BASE_URL, ...headers }, ...(payload !== undefined && { payload }),
  });
  const ifMatch = (version: number) => ({ 'if-match': `"${version}"` });
  const read = async (cookie: string, id: string) =>
    (await inject('GET', `/api/dives/${id}`, cookie)).json() as DiveView;

  const newDive = async () => {
    const [made] = await t.db.insert(dive).values({
      diverId, startsAt: new Date('2026-02-01T10:00:00Z'), utcOffsetSeconds: 0, durationSeconds: 3600,
    }).returning();
    return made!.id;
  };

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'tim@example.com');
    // Two sessions of the same User, as the phone and the web client would hold them.
    phone = await signIn(ctx.app, 'tim@example.com');
    web = await signIn(ctx.app, 'tim@example.com');
    const divers = (await inject('GET', '/api/divers', phone)).json() as { id: string; isOwn: boolean }[];
    diverId = divers.find((d) => d.isOwn)!.id;
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('converges after disjoint edits from two sessions', async () => {
    const id = await newDive();
    const phoneRead = await read(phone, id);
    const webRead = await read(web, id);
    expect(phoneRead.version).toBe(webRead.version);

    const phoneEdit = await inject('PATCH', `/api/dives/${id}`, phone, { notes: 'from the phone' }, ifMatch(phoneRead.version));
    expect(phoneEdit.statusCode).toBe(200);

    // The web client still holds the old version: the one round trip carries the current Dive for its merge.
    const webStale = await inject(
      'PATCH', `/api/dives/${id}`, web, { set: { maxDepthM: 30 } }, ifMatch(webRead.version),
    );
    expect(webStale.statusCode).toBe(412);
    const current = (webStale.json() as { code: string; dive: DiveView });
    expect(current.code).toBe('dive_changed');
    expect(current.dive).toMatchObject({ id, notes: 'from the phone', version: phoneRead.version + 1 });

    // Disjoint fields: the merged write goes against the fresh version.
    const webMerged = await inject(
      'PATCH', `/api/dives/${id}`, web, { set: { maxDepthM: 30 } }, ifMatch(current.dive.version),
    );
    expect(webMerged.statusCode).toBe(200);

    const converged = await read(phone, id);
    expect(converged).toMatchObject({ notes: 'from the phone', values: { maxDepthM: 30 } });
    expect(await read(web, id)).toEqual(converged);
  });

  it('parks exactly one conflict when both sessions edit the same field', async () => {
    const id = await newDive();
    const start = await read(phone, id);

    const phoneEdit = await inject('PATCH', `/api/dives/${id}`, phone, { notes: 'from the phone' }, ifMatch(start.version));
    expect(phoneEdit.statusCode).toBe(200);

    const webStale = await inject('PATCH', `/api/dives/${id}`, web, { notes: 'from the web' }, ifMatch(start.version));
    expect(webStale.statusCode).toBe(412);
    expect((webStale.json() as { dive: DiveView }).dive).toMatchObject({ notes: 'from the phone' });

    // Choosing the server side sends nothing; the phone's value stands and both sessions read one state.
    const converged = await read(phone, id);
    expect(converged.notes).toBe('from the phone');
    expect(await read(web, id)).toEqual(converged);
  });
});
