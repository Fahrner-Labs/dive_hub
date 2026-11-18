// Bearer tokens for native clients (ADR 0074): a native sign-in is an ordinary session, its token authenticates
// /api and /api/live without a cookie or Origin, and ending the session ends the token.
import type { AddressInfo } from 'node:net';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import {
  BASE_URL, PASSWORD, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

type App = Awaited<ReturnType<typeof createTestApp>>['app'];

/** Signs in the way a native client does (no Origin) and returns the session token from `set-auth-token`. */
async function signInForToken(app: App, email: string): Promise<string> {
  const response = await app.inject({
    method: 'POST', url: '/api/auth/sign-in/email',
    payload: { email, password: PASSWORD },
  });
  expect(response.statusCode).toBe(200);
  const token = response.headers['set-auth-token'];
  if (typeof token !== 'string' || token.length === 0) throw new Error('sign-in handed out no set-auth-token');
  return token;
}

const settle = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

/** Opens /api/live and returns its status and first frame (the stream never ends by itself). */
async function firstFrame(base: string, headers: Record<string, string>): Promise<{ status: number; body: string }> {
  const controller = new AbortController();
  const response = await fetch(`${base}/api/live`, { headers, signal: controller.signal });
  try {
    if (response.status !== 200) return { status: response.status, body: await response.text() };
    const { value } = await response.body!.getReader().read();
    return { status: response.status, body: new TextDecoder().decode(value) };
  } finally {
    controller.abort();
  }
}

describe.skipIf(!(await databaseReachable()))('bearer tokens for native clients', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let base: string;

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'native@example.com');
    ctx.live.start();
    await ctx.app.listen({ host: '127.0.0.1', port: 0 });
    base = `http://127.0.0.1:${(ctx.app.server.address() as AddressInfo).port}`;
    for (let i = 0; i < 200 && !ctx.live.ready(); i += 1) await settle(10);
    expect(ctx.live.ready()).toBe(true);
  });

  afterAll(async () => {
    await ctx.live.stop();
    await ctx.app.close();
    await t.drop();
  });

  it('authenticates an ordinary /api route by its bearer token alone, without an Origin', async () => {
    const token = await signInForToken(ctx.app, 'native@example.com');
    const me = await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { authorization: `Bearer ${token}` } });
    expect(me.statusCode).toBe(200);
    expect(me.json()).toMatchObject({ user: { email: 'native@example.com' } });
  });

  it('ends with its session: signing out by bearer token revokes it', async () => {
    const token = await signInForToken(ctx.app, 'native@example.com');
    const out = await ctx.app.inject({
      method: 'POST', url: '/api/auth/sign-out', headers: { authorization: `Bearer ${token}` },
    });
    expect(out.statusCode).toBe(200);
    const after = await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { authorization: `Bearer ${token}` } });
    expect(after.statusCode).toBe(401);
  });

  it('keeps the cookie path unchanged: signing out with a cookie still needs an Origin', async () => {
    const cookie = await signIn(ctx.app, 'native@example.com');
    expect((await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie } })).statusCode).toBe(200);

    const refused = await ctx.app.inject({ method: 'POST', url: '/api/auth/sign-out', headers: { cookie } });
    expect(refused.statusCode).toBe(403);
    const allowed = await ctx.app.inject({
      method: 'POST', url: '/api/auth/sign-out', headers: { cookie, origin: BASE_URL },
    });
    expect(allowed.statusCode).toBe(200);
  });

  it('accepts a bearer token on /api/live without a cookie or Origin, and the cookie with an Origin', async () => {
    const token = await signInForToken(ctx.app, 'native@example.com');
    const bearer = await firstFrame(base, { authorization: `Bearer ${token}` });
    expect(bearer.status).toBe(200);
    expect(bearer.body).toContain('retry:');

    const cookie = await signIn(ctx.app, 'native@example.com');
    const withCookie = await firstFrame(base, { cookie, origin: BASE_URL });
    expect(withCookie.status).toBe(200);

    expect((await firstFrame(base, {})).status).toBe(401);
  });
});
