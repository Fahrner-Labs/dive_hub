// Push notifications, first slice (ADR 0085): the public Firebase client config, the session-scoped push-device rows a
// native client registers, and their removal on sign-out. Hints only; nothing here sends a message.
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import type { FcmConfig } from '../src/config.js';
import { pushDevice, session } from '../src/db/schema.js';
import {
  BASE_URL, PASSWORD, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

type App = Awaited<ReturnType<typeof createTestApp>>['app'];

const FCM: FcmConfig = {
  serviceAccount: {
    projectId: 'divehub-test', clientEmail: 'push@divehub-test.iam.gserviceaccount.com', privateKey: 'private-key',
    raw: { project_id: 'divehub-test', client_email: 'push@divehub-test.iam.gserviceaccount.com', private_key: 'private-key' },
  },
  android: {
    projectId: 'divehub-test', applicationId: 'dev.divehub', apiKey: 'public-api-key', gcmSenderId: '123456789',
  },
};

const register = (app: App, auth: { cookie?: string; token?: string }, payload: object) => app.inject({
  method: 'POST', url: '/api/devices',
  headers: {
    ...(auth.cookie ? { cookie: auth.cookie, origin: BASE_URL } : {}),
    ...(auth.token ? { authorization: `Bearer ${auth.token}` } : {}),
  },
  payload,
});

const remove = (app: App, auth: { cookie?: string; token?: string }, id: string) => app.inject({
  method: 'DELETE', url: `/api/devices/${id}`,
  headers: {
    ...(auth.cookie ? { cookie: auth.cookie, origin: BASE_URL } : {}),
    ...(auth.token ? { authorization: `Bearer ${auth.token}` } : {}),
  },
});

/** Signs in the way a native client does (no Origin) and returns the session token from `set-auth-token`. */
async function signInForToken(app: App, email: string): Promise<string> {
  const response = await app.inject({
    method: 'POST', url: '/api/auth/sign-in/email', payload: { email, password: PASSWORD },
  });
  expect(response.statusCode).toBe(200);
  const token = response.headers['set-auth-token'];
  if (typeof token !== 'string' || token.length === 0) throw new Error('sign-in handed out no set-auth-token');
  return token;
}

describe.skipIf(!(await databaseReachable()))('push notifications', () => {
  let t: TestDatabase;
  let plainDb: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let plain: Awaited<ReturnType<typeof createTestApp>>;
  let member: string;
  let memberId: string;
  let admin: string;
  let adminId: string;

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, { fcm: FCM });
    plainDb = await createTestDatabase();
    plain = await createTestApp(plainDb);
    memberId = (await createUser(ctx.auth, 'push-member@example.com')).id;
    adminId = (await createUser(ctx.auth, 'push-admin@example.com', 'admin')).id;
    member = await signIn(ctx.app, 'push-member@example.com');
    admin = await signIn(ctx.app, 'push-admin@example.com');
  });

  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
    await plain?.app.close();
    await plainDb?.drop();
  });

  it('serves the public Firebase client config without a session', async () => {
    const response = await ctx.app.inject({ method: 'GET', url: '/api/push/config' });
    expect(response.statusCode).toBe(200);
    expect(response.json()).toEqual({ android: FCM.android });
  });

  it('answers an empty object when no FCM credential is configured', async () => {
    const response = await plain.app.inject({ method: 'GET', url: '/api/push/config' });
    expect(response.statusCode).toBe(200);
    expect(response.json()).toEqual({});
  });

  it('registers a device for the signed-in user and session', async () => {
    const response = await register(ctx.app, { cookie: member }, { platform: 'android', pushToken: 'token-member-1' });
    expect(response.statusCode).toBe(200);
    const view = response.json();
    expect(view).toMatchObject({ platform: 'android' });
    expect(view.id).toEqual(expect.any(String));
    expect(view.createdAt).toEqual(expect.any(String));
    expect(view.lastSeenAt).toEqual(expect.any(String));

    const [row] = await t.db.select().from(pushDevice).where(eq(pushDevice.id, view.id));
    expect(row).toMatchObject({ userId: memberId, platform: 'android', pushToken: 'token-member-1' });
    const [sessionRow] = await t.db.select().from(session).where(eq(session.id, row!.sessionId));
    expect(sessionRow?.userId).toBe(memberId);
  });

  it('accepts registration by bearer token, as a native client sends it', async () => {
    const token = await signInForToken(ctx.app, 'push-member@example.com');
    const response = await register(ctx.app, { token }, { platform: 'android', pushToken: 'token-bearer-1' });
    expect(response.statusCode).toBe(200);
    const [row] = await t.db.select().from(pushDevice).where(eq(pushDevice.pushToken, 'token-bearer-1'));
    expect(row?.userId).toBe(memberId);
  });

  it('re-registering the same token refreshes lastSeenAt instead of adding a row', async () => {
    const first = await register(ctx.app, { cookie: member }, { platform: 'android', pushToken: 'token-refresh' });
    const id = first.json().id as string;
    const old = new Date('2020-01-01T00:00:00.000Z');
    await t.db.update(pushDevice).set({ lastSeenAt: old }).where(eq(pushDevice.id, id));

    const again = await register(ctx.app, { cookie: member }, { platform: 'android', pushToken: 'token-refresh' });
    expect(again.statusCode).toBe(200);
    expect(again.json().id).toBe(id);
    const [row] = await t.db.select().from(pushDevice).where(eq(pushDevice.id, id));
    expect(row!.lastSeenAt.getTime()).toBeGreaterThan(old.getTime());
    const rows = await t.db.select().from(pushDevice).where(eq(pushDevice.pushToken, 'token-refresh'));
    expect(rows).toHaveLength(1);
  });

  it('moves a token to the user and session that registered it last', async () => {
    const first = await register(ctx.app, { cookie: member }, { platform: 'android', pushToken: 'token-shared' });
    const id = first.json().id as string;
    const [before] = await t.db.select().from(pushDevice).where(eq(pushDevice.id, id));

    const moved = await register(ctx.app, { cookie: admin }, { platform: 'ios', pushToken: 'token-shared' });
    expect(moved.statusCode).toBe(200);
    expect(moved.json().id).toBe(id);
    const [after] = await t.db.select().from(pushDevice).where(eq(pushDevice.id, id));
    expect(after).toMatchObject({ userId: adminId, platform: 'ios' });
    expect(after!.sessionId).not.toBe(before!.sessionId);
  });

  it('refuses a registration without a session, and a bad platform or empty token', async () => {
    expect((await register(ctx.app, {}, { platform: 'android', pushToken: 'token-no-auth' })).statusCode).toBe(401);
    expect((await register(ctx.app, { cookie: member }, { platform: 'windows', pushToken: 'token-bad-platform' })).statusCode).toBe(400);
    expect((await register(ctx.app, { cookie: member }, { platform: 'android', pushToken: '' })).statusCode).toBe(400);
  });

  it('deletes only the owner\'s device and answers 404 otherwise', async () => {
    const registered = await register(ctx.app, { cookie: member }, { platform: 'android', pushToken: 'token-owner' });
    const id = registered.json().id as string;

    expect((await remove(ctx.app, { cookie: admin }, id)).statusCode).toBe(404);
    expect((await remove(ctx.app, {}, id)).statusCode).toBe(401);
    expect(await t.db.select().from(pushDevice).where(eq(pushDevice.id, id))).toHaveLength(1);

    expect((await remove(ctx.app, { cookie: member }, id)).statusCode).toBe(204);
    expect(await t.db.select().from(pushDevice).where(eq(pushDevice.id, id))).toHaveLength(0);
  });

  it('deletes the row when the session ends, so a signed-out device gets no more pushes', async () => {
    await createUser(ctx.auth, 'push-leaver@example.com');
    const leaver = await signIn(ctx.app, 'push-leaver@example.com');
    const registered = await register(ctx.app, { cookie: leaver }, { platform: 'android', pushToken: 'token-leaver' });
    const id = registered.json().id as string;

    const out = await ctx.app.inject({
      method: 'POST', url: '/api/auth/sign-out', headers: { cookie: leaver, origin: BASE_URL },
    });
    expect(out.statusCode).toBe(200);
    expect(await t.db.select().from(pushDevice).where(eq(pushDevice.id, id))).toHaveLength(0);
  });
});
