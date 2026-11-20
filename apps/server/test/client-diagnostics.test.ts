import { eq, sql } from 'drizzle-orm';
import type { LightMyRequestResponse } from 'fastify';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { clientDiagnostic, user } from '../src/db/schema.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

type Call = (method: 'GET' | 'POST' | 'PATCH', url: string, cookie?: string, payload?: object) => Promise<LightMyRequestResponse>;

const entry = (overrides: Record<string, unknown> = {}) => ({
  occurredAt: new Date().toISOString(),
  level: 'error',
  code: 'E_TEST',
  message: 'something failed',
  ...overrides,
});

const upload = (overrides: Record<string, unknown> = {}) => ({
  client: { name: 'web', version: '1.2.3' },
  capturedAt: new Date().toISOString(),
  entries: [entry()],
  ...overrides,
});

describe.skipIf(!(await databaseReachable()))('client diagnostics', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let admin: string;
  let member: string;
  let memberId: string;

  const call: Call = (method, url, cookie, payload) => ctx.app.inject({
    method, url,
    headers: { ...(cookie ? { cookie } : {}), origin: BASE_URL },
    ...(payload !== undefined && { payload }),
  });

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'admin@example.com', 'admin');
    const memberUser = await createUser(ctx.auth, 'member@example.com', 'user');
    memberId = memberUser.id;
    admin = await signIn(ctx.app, 'admin@example.com');
    member = await signIn(ctx.app, 'member@example.com');
  });

  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('answers 401 without a session on every route', async () => {
    expect((await call('GET', '/api/client-diagnostics/status')).statusCode).toBe(401);
    expect((await call('POST', '/api/client-diagnostics', undefined, upload())).statusCode).toBe(401);
    expect((await call('GET', '/api/admin/client-diagnostics')).statusCode).toBe(401);
    expect((await call('PATCH', '/api/admin/client-diagnostics', undefined, { enabled: true })).statusCode).toBe(401);
  });

  it('is off by default and refuses an upload with 403', async () => {
    expect((await call('GET', '/api/client-diagnostics/status', member)).json()).toEqual({ enabled: false });
    const refused = await call('POST', '/api/client-diagnostics', member, upload());
    expect(refused.statusCode).toBe(403);
    expect(refused.json().code).toBe('client_diagnostics_disabled');
  });

  it('answers 403 to a signed-in user who is not an admin', async () => {
    expect((await call('GET', '/api/admin/client-diagnostics', member)).statusCode).toBe(403);
    expect((await call('PATCH', '/api/admin/client-diagnostics', member, { enabled: true })).statusCode).toBe(403);
  });

  it('reads the setting and flips it as an admin', async () => {
    expect((await call('GET', '/api/admin/client-diagnostics', admin)).json()).toEqual({ enabled: false });
    const patched = await call('PATCH', '/api/admin/client-diagnostics', admin, { enabled: true });
    expect(patched.statusCode).toBe(200);
    expect(patched.json()).toEqual({ enabled: true });
    expect((await call('GET', '/api/client-diagnostics/status', member)).json()).toEqual({ enabled: true });
  });

  it('accepts an upload with 204 and persists it', async () => {
    const capturedAt = new Date('2026-11-20T10:00:00.000Z');
    const occurredAt = new Date('2026-11-20T09:59:00.000Z');
    const accepted = await call('POST', '/api/client-diagnostics', member, upload({ capturedAt: capturedAt.toISOString(), entries: [entry({ occurredAt: occurredAt.toISOString() })] }));
    expect(accepted.statusCode).toBe(204);

    const [row] = await t.db.select().from(clientDiagnostic);
    expect(row).toMatchObject({
      userId: memberId,
      clientName: 'web',
      clientVersion: '1.2.3',
      level: 'error',
      code: 'E_TEST',
      message: 'something failed',
    });
    expect(row!.capturedAt.toISOString()).toBe(capturedAt.toISOString());
    expect(row!.occurredAt.toISOString()).toBe(occurredAt.toISOString());
  });

  it('caps an upload at 50 entries', async () => {
    const fifty = Array.from({ length: 50 }, (_, index) => entry({ code: `E_${index}` }));
    const tooMany = await call('POST', '/api/client-diagnostics', member, upload({ entries: [...fifty, entry({ code: 'E_51' })] }));
    expect(tooMany.statusCode).toBe(400);

    const accepted = await call('POST', '/api/client-diagnostics', member, upload({ entries: fifty }));
    expect(accepted.statusCode).toBe(204);
    const [count] = (await t.db.execute(sql`select count(*)::int as n from client_diagnostic where user_id = ${memberId}`)).rows as { n: number }[];
    expect(count!.n).toBe(51);
  });

  it('truncates long fields and redacts e-mail addresses and token keys before persisting', async () => {
    const accepted = await call('POST', '/api/client-diagnostics', member, upload({
      entries: [entry({
        code: 'E_LONG',
        message: 'm'.repeat(1200),
        stack: 's'.repeat(4100),
        context: { token: 'raw-token', note: 'contact ops@example.com' },
      })],
    }));
    expect(accepted.statusCode).toBe(204);

    const [row] = await t.db.select().from(clientDiagnostic).where(eq(clientDiagnostic.code, 'E_LONG'));
    expect(row!.message).toHaveLength(1000);
    expect(row!.message).toBe('m'.repeat(1000));
    expect(row!.stack).toHaveLength(4000);
    expect(row!.contextJson).toEqual({ token: '‹redacted›', note: 'contact ‹email›' });
  });

  it('refuses the sixth upload within an hour with 429', async () => {
    for (let index = 0; index < 2; index += 1) {
      expect((await call('POST', '/api/client-diagnostics', member, upload({ entries: [entry({ code: `E_RATE_${index}` })] }))).statusCode).toBe(204);
    }
    const limited = await call('POST', '/api/client-diagnostics', member, upload({ entries: [entry({ code: 'E_RATE_LIMITED' })] }));
    expect(limited.statusCode).toBe(429);
    expect(limited.json().code).toBe('client_diagnostics_limited');
  });

  it('evicts the oldest row when the 500 newest cap is exceeded', async () => {
    await t.db.delete(clientDiagnostic);
    const old = new Date(Date.now() - 2 * 60 * 60 * 1000);
    const seeded = await t.db.insert(clientDiagnostic).values(Array.from({ length: 500 }, (_, index) => ({
      userId: memberId,
      clientName: 'android',
      clientVersion: '2.0.0',
      capturedAt: old,
      occurredAt: old,
      level: 'warn' as const,
      code: `E_SEED_${index}`,
      message: 'seeded',
      createdAt: new Date(old.getTime() + index),
    }))).returning({ id: clientDiagnostic.id, code: clientDiagnostic.code });
    const oldest = seeded.find((row) => row.code === 'E_SEED_0')!;

    const accepted = await call('POST', '/api/client-diagnostics', member, upload({ entries: [entry({ code: 'E_NEWEST' })] }));
    expect(accepted.statusCode).toBe(204);

    const [count] = (await t.db.execute(sql`select count(*)::int as n from client_diagnostic`)).rows as { n: number }[];
    expect(count!.n).toBe(500);
    const codes = (await t.db.select({ code: clientDiagnostic.code }).from(clientDiagnostic)).map((row) => row.code);
    expect(codes).toContain('E_NEWEST');
    expect(codes).not.toContain(oldest.code);
  });

  it('purges rows older than 90 days', async () => {
    await t.db.delete(clientDiagnostic);
    const now = new Date('2026-11-20T12:00:00.000Z');
    const [oldUser] = await t.db.select({ id: user.id }).from(user).where(eq(user.email, 'member@example.com'));
    await t.db.insert(clientDiagnostic).values([
      {
        userId: oldUser!.id, clientName: 'web', clientVersion: '1.0.0', capturedAt: new Date('2026-08-01T00:00:00.000Z'),
        occurredAt: new Date('2026-08-01T00:00:00.000Z'), level: 'error', code: 'E_OLD', message: 'old',
        createdAt: new Date('2026-08-01T00:00:00.000Z'),
      },
      {
        userId: oldUser!.id, clientName: 'web', clientVersion: '1.0.0', capturedAt: new Date('2026-11-19T00:00:00.000Z'),
        occurredAt: new Date('2026-11-19T00:00:00.000Z'), level: 'error', code: 'E_FRESH', message: 'fresh',
        createdAt: new Date('2026-11-19T00:00:00.000Z'),
      },
    ]);

    expect(await ctx.clientDiagnostics.purge(now)).toBe(1);
    const codes = (await t.db.select({ code: clientDiagnostic.code }).from(clientDiagnostic)).map((row) => row.code);
    expect(codes).toEqual(['E_FRESH']);
  });
});
