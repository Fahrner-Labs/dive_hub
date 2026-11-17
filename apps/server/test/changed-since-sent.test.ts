// The logbook's "changed since sent" filter and its count (ADR 0040, amended): a Dive with a current remote dive at a
// Provider the User is connected to for the Diver, whose outgoing data (or, with the Connection following numbers, whose
// number) differs from what was sent. Worked out when asked, over the list's Diver and search, like the other filters.
import { and, eq } from 'drizzle-orm';
import { afterAll, beforeAll, beforeEach, describe, expect, it } from 'vitest';
import { dive, diveFingerprint, diverExternalId, push } from '../src/db/schema.js';
import { remember } from '../src/providers/list-entries.js';
import { createFakeLedger } from './fake-ledger-provider.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type List = { dives: { id: string }[]; total: number; counts: { changedSinceSent: number; notAtProvider: number } };

// A year per test: a Dive deleted earlier is not imported again.
let year = 3000;
const day = (n: number) => new Date(Date.UTC(year, 4, n, 9));

describe.skipIf(!(await databaseReachable()))('the logbook list: "changed since sent"', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let ann: string;
  let bob: string;
  let diverId: string;
  let connectionId: string;
  const ledger = createFakeLedger();

  const call = (method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE', url: string, payload?: object, cookie = ann) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE', url: string, payload?: object, cookie = ann) =>
    (await call(method, url, payload, cookie)).json() as T;
  const list = (query = '', cookie = ann) => json<List>('GET', `/api/dives${query}`, undefined, cookie);
  const ids = (l: List) => l.dives.map((d) => d.id);
  const upload = async (start: Date) => {
    const { payload, headers } = multipartFile(`dive-${start.getTime()}.fit`, makeSyntheticDive({ serialNumber: 100, start }));
    const created = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: ann } });
    await ctx.imports.processImport(created.json().id);
    return (await json<{ outcome: { diveId: string }[] }>('GET', `/api/imports/${created.json().id}`)).outcome[0]!.diveId;
  };
  const send = async (id: string) => expect((await call('POST', `/api/dives/${id}/providers/ledger`, {})).statusCode).toBeLessThan(300);
  const version = async (id: string) => (await json<{ version: number }>('GET', `/api/dives/${id}`)).version;
  const edit = async (id: string, notes: string | null) => call('PATCH', `/api/dives/${id}`, { version: await version(id), notes });
  const follow = (on: boolean) => call('PATCH', `/api/connections/${connectionId}`, { diveNumbers: { follow: on } });

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, { extraProviders: [ledger.adapter] });
    await createUser(ctx.auth, 'ann@example.com');
    await createUser(ctx.auth, 'bob@example.com');
    ann = await signIn(ctx.app, 'ann@example.com');
    bob = await signIn(ctx.app, 'bob@example.com');
    diverId = (await json<{ id: string }[]>('GET', '/api/divers'))[0]!.id;
    connectionId = (await json<{ id: string }>('POST', '/api/connections/ledger', { diverId, token: 'ledger-token-1', keepSignedIn: false })).id;
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });
  beforeEach(async () => {
    year++;
    await t.db.delete(push);
    const { dives } = await json<{ dives: { id: string }[] }>('GET', `/api/dives?limit=100&diverId=${diverId}`);
    for (const d of dives) await call('DELETE', `/api/dives/${d.id}`, { version: await version(d.id) });
    ledger.dives.clear();
    if (!ledger.tokens.size) ledger.tokens.set('ledger-token-1', { accountId: 'l-5', label: 'Ledger of Erika' });
    await follow(false);
  });

  it('shows the Dives that changed after they were sent, and not new ones or unchanged ones', async () => {
    const [sent, changed, never] = [await upload(day(1)), await upload(day(2)), await upload(day(3))] as [string, string, string];
    await send(sent);
    await send(changed);
    expect((await list()).counts).toMatchObject({ changedSinceSent: 0 });
    expect((await list('?only=changed-since-sent')).total).toBe(0);

    const before = (await t.db.select({ notes: dive.notes }).from(dive).where(eq(dive.id, changed)))[0]!.notes;
    await edit(changed, 'Turtle');
    const l = await list();
    expect(l.counts.changedSinceSent).toBe(1);
    expect(ids(await list('?only=changed-since-sent'))).toEqual([changed]);
    // Not at the Provider is a different question: the unsent Dive is in it, the changed one is not.
    expect(ids(await list('?only=not-at-provider'))).toEqual([never]);
    // Both filters must fit: changed since sent, and not at the Provider, share no Dive.
    expect((await list('?only=changed-since-sent,not-at-provider')).total).toBe(0);
    // The stored fingerprint is compared, not a dirty flag: changing the notes back makes it up to date again.
    await edit(changed, before);
    expect((await list()).counts.changedSinceSent).toBe(0);
    await edit(changed, 'Turtle');
    // Sending it again makes it up to date.
    await send(changed);
    expect((await list()).counts.changedSinceSent).toBe(0);
    void sent;
  });

  it('pages the filtered list and keeps its count whatever is chosen', async () => {
    const dives = [await upload(day(1)), await upload(day(2)), await upload(day(3))];
    for (const id of dives) await send(id);
    for (const id of dives.slice(0, 2)) await edit(id, `Note ${id.slice(0, 4)}`);
    const first = await list('?only=changed-since-sent&limit=1&offset=0');
    const second = await list('?only=changed-since-sent&limit=1&offset=1');
    expect(first).toMatchObject({ total: 2 });
    expect(first.dives).toHaveLength(1);
    expect(new Set([...ids(first), ...ids(second)])).toEqual(new Set(dives.slice(0, 2)));
    // The count follows the Diver and the search, not the chosen filters: pressed or not, it says 2.
    expect(first.counts.changedSinceSent).toBe(2);
    expect((await list('?only=no-site')).counts.changedSinceSent).toBe(2);
  });

  it('follows the search and the Diver, and shows nothing of another User\'s dives', async () => {
    const [a, b] = [await upload(day(1)), await upload(day(2))] as [string, string];
    await send(a);
    await send(b);
    await edit(a, 'Wreck of the Rozi');
    await edit(b, 'Turtle');
    expect((await list('?q=rozi')).counts.changedSinceSent).toBe(1);
    expect(ids(await list('?q=rozi&only=changed-since-sent'))).toEqual([a]);
    expect((await list('?q=nothing-like-this')).counts.changedSinceSent).toBe(0);
    const lena = (await json<{ id: string }>('POST', '/api/external-divers', { name: 'Lena Meier' })).id;
    const ofLena = await list(`?diverId=${lena}&only=changed-since-sent`);
    expect(ofLena.total).toBe(0);
    expect(ofLena.counts.changedSinceSent).toBe(0);
    expect((await list('?only=changed-since-sent', bob)).total).toBe(0);
  });

  it('leaves a Dive out once it is gone from the Provider, and without a Connection', async () => {
    const id = await upload(day(1));
    await send(id);
    await edit(id, 'Turtle');
    expect((await list()).counts.changedSinceSent).toBe(1);
    await call('DELETE', `/api/dives/${id}/providers/ledger`, {});
    expect((await list()).counts.changedSinceSent).toBe(0);
    // Changed while it has no remote dive there, then sent: the send records what it sent, so it is up to date again.
    await edit(id, 'Turtle while gone');
    expect((await list()).counts.changedSinceSent).toBe(0);
    await send(id);
    expect((await list()).counts.changedSinceSent).toBe(0);
    await edit(id, 'Turtle again');
    expect((await list()).counts.changedSinceSent).toBe(1);
    await call('DELETE', `/api/connections/${connectionId}`);
    expect((await list()).counts.changedSinceSent).toBe(0);
    // Connect again for the next tests; its Pushes stay, tied to the old Connection.
    connectionId = (await json<{ id: string }>('POST', '/api/connections/ledger', { diverId, token: 'ledger-token-1', keepSignedIn: false })).id;
  });

  it('keeps counting after a site change that goes out with the Dive', async () => {
    const id = await upload(day(1));
    const site = await json<{ id: string }>('POST', '/api/dive-sites', { name: 'Water site', waterType: 'salt' });
    await call('PATCH', `/api/dives/${id}`, { version: await version(id), siteId: site.id });
    await send(id);
    expect((await list()).counts.changedSinceSent).toBe(0);
    // The site's water type is in the fingerprint, and a site Revision refreshes the stored one (ADR 0040, amended).
    const before = await json<{ version: number }>('GET', `/api/dive-sites/${site.id}`);
    await call('PATCH', `/api/dive-sites/${site.id}`, { version: before.version, waterType: 'fresh' });
    expect((await list()).counts.changedSinceSent).toBe(1);
    expect(ids(await list('?only=changed-since-sent'))).toEqual([id]);
    const after = await json<{ version: number }>('GET', `/api/dive-sites/${site.id}`);
    await call('PATCH', `/api/dive-sites/${site.id}`, { version: after.version, waterType: 'salt' });
    expect((await list()).counts.changedSinceSent).toBe(0);
  });

  it('counts a Participant whose account changed after the send', async () => {
    const id = await upload(day(1));
    const buddy = await json<{ id: string }>('POST', '/api/external-divers', { name: 'Account Buddy' });
    await call('PUT', `/api/divers/${buddy.id}/external-ids/padi`, { externalId: 'P-100' });
    await call('PUT', `/api/dives/${id}/participants`, { version: await version(id), participants: [{ diverId: buddy.id, role: 'buddy' }] });
    await send(id);
    expect((await list()).counts.changedSinceSent).toBe(0);
    // The account is in the fingerprint but no Dive Revision announces it: the change marks the stored row stale.
    await call('PUT', `/api/divers/${buddy.id}/external-ids/padi`, { externalId: 'P-200' });
    expect((await list()).counts.changedSinceSent).toBe(1);
    expect(ids(await list('?only=changed-since-sent'))).toEqual([id]);
    await call('PUT', `/api/divers/${buddy.id}/external-ids/padi`, { externalId: 'P-100' });
    expect((await list()).counts.changedSinceSent).toBe(0);
  });

  it('marks the stored fingerprints stale when a buddy-list entry changes', async () => {
    const id = await upload(day(1));
    const buddy = await json<{ id: string }>('POST', '/api/external-divers', { name: 'Entry Buddy' });
    await call('PUT', `/api/divers/${buddy.id}/external-ids/padi`, { externalId: 'P-300' });
    await call('PUT', `/api/dives/${id}/participants`, { version: await version(id), participants: [{ diverId: buddy.id, role: 'buddy' }] });
    expect((await call('PUT', `/api/divers/${diverId}/external-ids/ssi`, { externalId: '4700001' })).statusCode).toBe(204);
    await send(id);
    expect((await list()).counts.changedSinceSent).toBe(0);
    const [owner] = await t.db.select({ id: diverExternalId.id }).from(diverExternalId)
      .where(and(eq(diverExternalId.diverId, diverId), eq(diverExternalId.source, 'ssi')));
    await remember(t.db, owner!.id, buddy.id, '77');
    const [marked] = await t.db.select({ version: diveFingerprint.diveVersion }).from(diveFingerprint).where(eq(diveFingerprint.diveId, id));
    expect(marked!.version).toBe(-1);
    // The read works the row out again; the ledger's fingerprint doesn't use the entry, so the Dive stays up to date.
    expect((await list()).counts.changedSinceSent).toBe(0);
    const [refreshed] = await t.db.select({ version: diveFingerprint.diveVersion }).from(diveFingerprint).where(eq(diveFingerprint.diveId, id));
    expect(refreshed!.version).not.toBe(-1);
  });

  it('counts a number that differs only while the Connection follows numbers', async () => {
    const a = await upload(day(2));
    const b = await upload(day(3));
    await send(a);
    await send(b);
    await upload(day(1)); // an older Dive: the sent ones are one number too low if numbers are followed
    expect((await list()).counts.changedSinceSent).toBe(0);
    await follow(true);
    const l = await list('?only=changed-since-sent');
    expect(new Set(ids(l))).toEqual(new Set([a, b]));
    expect(l.counts.changedSinceSent).toBe(2);
    await follow(false);
    expect((await list()).counts.changedSinceSent).toBe(0);
  });

  it('does not let a Push of a Dive that was never confirmed count', async () => {
    const id = await upload(day(1));
    await send(id);
    await t.db.update(push).set({ state: 'failed' }).where(eq(push.diveId, id));
    await edit(id, 'Turtle');
    expect((await list()).counts.changedSinceSent).toBe(0);
  });

  it('fills the stored fingerprints of a database that has none, before it serves', async () => {
    const id = await upload(day(1));
    await send(id);
    expect((await list()).counts.changedSinceSent).toBe(0);
    // A first start after the change has no rows yet: the Dive counts as changed, and the startup pass fills it.
    await t.db.delete(diveFingerprint).where(eq(diveFingerprint.diveId, id));
    expect((await list()).counts.changedSinceSent).toBe(1);
    expect(await ctx.providers.pushes.backfillFingerprints()).toBeGreaterThan(0);
    expect((await list()).counts.changedSinceSent).toBe(0);
  });

  it('works out a large confirmed push set, not only the Dives of the page', async () => {
    const count = 60;
    const made = await t.db.insert(dive).values(Array.from({ length: count }, (_, i) => ({
      diverId, number: i + 1, startsAt: new Date(Date.UTC(year, 5, 1, 0, i)), durationSeconds: 2400,
    }))).returning({ id: dive.id });
    await t.db.insert(push).values(made.map((d, i) => ({
      diveId: d.id, connectionId, provider: 'ledger', mode: 'api' as const, action: 'create' as const,
      state: 'confirmed' as const, remoteId: `L-fake-${i}`, diveVersion: 1, fingerprint: `stale-${i}`,
    })));
    const reads = ledger.fingerprintReads;
    const page = await list();
    // The chip count is one stored comparison: it works out no Dive's fingerprint, however many Pushes there are.
    expect(ledger.fingerprintReads).toBe(reads);
    expect(page.counts.changedSinceSent).toBe(count);
    expect(page.dives).toHaveLength(25);
    const all = await list(`?only=changed-since-sent&limit=${count}`);
    expect(all.total).toBe(count);
    expect(new Set(ids(all))).toEqual(new Set(made.map((d) => d.id)));
  });
});
