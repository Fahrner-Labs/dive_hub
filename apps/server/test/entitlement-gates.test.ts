// The paywall's enforcement points (hosted-DiveHub plan §4.4): a Connection's automation writes and the Push runs refuse a
// User without the paid plan with `entitlement_required` and an offer; the worker skips a downgraded User's Connections.
// Tests run against the real database and the test ledger; the worker's jobs are run as the worker runs them.
import { eq, sql } from 'drizzle-orm';
import { afterAll, beforeAll, beforeEach, describe, expect, it } from 'vitest';
import { connection, importJob, userEntitlement, waitingPush } from '../src/db/schema.js';
import { SCHEDULED_FETCH_TASK } from '../src/providers/dive-import/scheduled-fetch.js';
import { createFakeLedger } from './fake-ledger-provider.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

// A year per test: a Dive deleted earlier is not imported again.
let year = 3600;

describe.skipIf(!(await databaseReachable()))('the entitlement gates', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let ann: string;
  let annId: string;
  let diverId: string;
  let connectionId: string;
  let diveId: string;
  const ledger = createFakeLedger({ imports: true });

  const call = (method: 'GET' | 'POST' | 'PATCH' | 'DELETE', url: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie: ann, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(method: 'GET' | 'POST' | 'PATCH' | 'DELETE', url: string, payload?: object) =>
    (await call(method, url, payload)).json() as T;
  const grant = (plan: 'free' | 'pro', status: 'none' | 'active' | 'past_due' | 'canceled', end: Date | null = null) =>
    t.db.insert(userEntitlement).values({ userId: annId, plan, status, currentPeriodEnd: end, updatedAt: new Date(ctx.clock.now()) })
      .onConflictDoUpdate({ target: userEntitlement.userId, set: { plan, status, currentPeriodEnd: end, updatedAt: new Date(ctx.clock.now()) } });
  const revoke = () => t.db.delete(userEntitlement).where(eq(userEntitlement.userId, annId));
  const importsOf = async () => (await t.db.select().from(importJob).where(eq(importJob.connectionId, connectionId))).length;
  const fetchJobs = async () => (await t.db.execute(sql`select key from graphile_worker.jobs where task_identifier = ${SCHEDULED_FETCH_TASK}`))
    .rows.map((r) => (r as { key: string }).key);
  const dropFetchJobs = () => t.db.execute(sql`select graphile_worker.remove_job(key) from graphile_worker.jobs where task_identifier = ${SCHEDULED_FETCH_TASK}`);

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, {
      extraProviders: [ledger.adapter],
      // Only the paywall is under test; these Paddle values never reach the network because no billing route is called.
      billing: { provider: 'paddle', apiKey: 'pdl_sdbx_test', webhookSecret: 'pdl_ntfset_test', priceIds: ['pri_test'] },
    });
    await createUser(ctx.auth, 'ann@example.com');
    ann = await signIn(ctx.app, 'ann@example.com');
    annId = ((await t.db.execute(sql`select id from "user" where email = 'ann@example.com'`)).rows[0] as { id: string }).id;
    diverId = (await json<{ id: string; isOwn: boolean }[]>('GET', '/api/divers')).find((d) => d.isOwn)!.id;
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });
  beforeEach(async () => {
    year++;
    await dropFetchJobs();
    await t.db.delete(waitingPush);
    await t.db.delete(connection);
    await revoke();
    ledger.dives.clear();
    ledger.tokens.clear();
    ledger.tokens.set('ledger-token-1', { accountId: 'l-5', label: 'Ledger of Erika' });
    connectionId = (await json<{ id: string }>('POST', '/api/connections/ledger', { diverId, token: 'ledger-token-1', keepSignedIn: false })).id;
    const start = new Date(Date.UTC(year, 4, 1, 9));
    const { payload, headers } = multipartFile(`dive-${start.getTime()}.fit`, makeSyntheticDive({ serialNumber: 100, start }));
    const created = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: ann } });
    await ctx.imports.processImport(created.json().id);
    diveId = (await json<{ outcome: { diveId: string }[] }>('GET', `/api/imports/${created.json().id}`)).outcome[0]!.diveId;
  });

  it('refuses turning automation on without the plan, carrying the offer, and still allows turning it off', async () => {
    const refused = await call('PATCH', `/api/connections/${connectionId}`, { sendChanges: true });
    expect(refused.statusCode).toBe(402);
    expect(refused.json()).toMatchObject({
      code: 'entitlement_required',
      offer: { plan: 'pro', features: expect.arrayContaining(['automation']) },
    });
    expect((await call('PATCH', `/api/connections/${connectionId}`, { fetchEveryMinutes: 60 })).statusCode).toBe(402);
    // Turning off is not using the paid feature: a downgraded User can always stop it.
    expect((await call('PATCH', `/api/connections/${connectionId}`, { sendChanges: false })).statusCode).toBe(200);

    await grant('pro', 'active');
    expect((await call('PATCH', `/api/connections/${connectionId}`, { sendChanges: true })).statusCode).toBe(200);
  });

  it('refuses a Push run\'s preview and start without the plan', async () => {
    const body = { dives: { ids: [diveId] } };
    const preview = await call('POST', `/api/connections/${connectionId}/push-runs/preview`, body);
    expect(preview.statusCode).toBe(402);
    expect(preview.json()).toMatchObject({ code: 'entitlement_required', offer: { plan: 'pro', features: expect.arrayContaining(['push_runs']) } });
    expect((await call('POST', `/api/connections/${connectionId}/push-runs`, body)).statusCode).toBe(402);

    await grant('pro', 'active');
    expect((await call('POST', `/api/connections/${connectionId}/push-runs/preview`, body)).statusCode).toBe(200);
    expect((await call('POST', `/api/connections/${connectionId}/push-runs`, body)).statusCode).toBe(202);
  });

  it('skips a downgraded User\'s scheduled fetch and Waiting pushes, and picks them up again once re-entitled', async () => {
    await t.db.update(connection).set({ fetchEveryMinutes: 60, importMode: 'create', lastFetchedAt: null }).where(eq(connection.id, connectionId));
    const now = new Date(ctx.clock.now());
    await t.db.insert(waitingPush).values({
      diveId, provider: 'ledger', connectionId, action: 'send', state: 'waiting',
      dueAt: new Date(now.getTime() - 60_000), changedAt: new Date(now.getTime() - 60_000),
    });

    // No plan: the worker queues and does nothing, and never reads the Provider.
    await ctx.providers.scheduledFetch.tick();
    expect(await fetchJobs()).toEqual([]);
    expect(await ctx.providers.scheduledFetch.fetch(connectionId)).toBeUndefined();
    expect(await importsOf()).toBe(0);
    const remoteBefore = ledger.dives.size;
    expect(await ctx.providers.waitingPushes.step(connectionId)).toBeNull();
    expect(ledger.dives.size).toBe(remoteBefore);

    // Re-entitled: the tick queues the fetch and the Waiting push goes through.
    await grant('pro', 'active');
    await ctx.providers.scheduledFetch.tick();
    expect(await fetchJobs()).toEqual([`fetch:${connectionId}`]);
    await ctx.providers.scheduledFetch.fetch(connectionId);
    expect(await importsOf()).toBe(1);
    await ctx.providers.waitingPushes.step(connectionId);
    expect(ledger.dives.size).toBe(remoteBefore + 1);
  });

  it('keeps the fetch on opening free: a User without the plan still fetches when they start the app', async () => {
    await t.db.update(connection).set({ fetchEveryMinutes: 60, importMode: 'create', lastFetchedAt: null }).where(eq(connection.id, connectionId));
    expect(await importsOf()).toBe(0);
    await ctx.providers.scheduledFetch.fetch(connectionId, { userInitiated: true });
    expect(await importsOf()).toBe(1);
  });
});
