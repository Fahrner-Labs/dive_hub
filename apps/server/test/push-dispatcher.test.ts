// The push dispatcher (ADR 0085), second slice: the FCM sender's OAuth2 token and hints-only high-priority message,
// the unconfigured no-op, and a token FCM no longer knows taking its device row with it. Real PostgreSQL for the
// dispatcher's fan-out; the FCM HTTP is a stand-in, never the network.
import { generateKeyPairSync, randomUUID, verify } from 'node:crypto';
import { eq, sql } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import type { FcmConfig } from '../src/config.js';
import { session } from '../src/db/auth-schema.js';
import { pushDevice } from '../src/db/schema.js';
import type { Fetch } from '../src/notifications/fcm-provider.js';
import { createFcmProvider } from '../src/notifications/fcm-provider.js';
import { createPushDispatcher, SEND_PUSH_TASK } from '../src/notifications/push-dispatcher.js';
import { PushTokenGoneError } from '../src/notifications/push-provider.js';
import {
  createTestAuth, createTestDatabase, createUser, databaseReachable, type TestDatabase,
} from './support.js';

const { publicKey, privateKey } = generateKeyPairSync('rsa', {
  modulusLength: 2048,
  publicKeyEncoding: { type: 'spki', format: 'pem' },
  privateKeyEncoding: { type: 'pkcs8', format: 'pem' },
});

const TOKEN_URI = 'https://oauth2.example/token';

const SERVICE_ACCOUNT = {
  projectId: 'divehub-test',
  clientEmail: 'push@divehub-test.iam.gserviceaccount.com',
  privateKey,
  raw: {
    project_id: 'divehub-test',
    client_email: 'push@divehub-test.iam.gserviceaccount.com',
    private_key: privateKey,
    token_uri: TOKEN_URI,
  },
};

const FCM = (): FcmConfig => ({
  serviceAccount: SERVICE_ACCOUNT,
  android: { projectId: 'divehub-test', applicationId: 'dev.divehub', apiKey: 'public-api-key', gcmSenderId: '123456789' },
});

interface Answer { status: number; body: string }
interface Call { url: string; headers: Record<string, string>; body: string }

/** A fetch stand-in with scripted answers, recording every request it is given. */
function scriptedFetch(answers: Answer[]) {
  const calls: Call[] = [];
  const fetch: Fetch = async (url, init) => {
    calls.push({ url, headers: init.headers, body: init.body });
    const answer = answers.shift();
    if (!answer) throw new Error(`unexpected fetch to ${url}`);
    return { status: answer.status, text: async () => answer.body };
  };
  return { calls, fetch };
}

const tokenAnswer: Answer = { status: 200, body: JSON.stringify({ access_token: 'ya29.test', expires_in: 3600 }) };
const sentAnswer: Answer = { status: 200, body: '{}' };

describe('the FCM provider', () => {
  it('mints an OAuth2 token and sends a high-priority hints-only data message with the collapse key', async () => {
    const { calls, fetch } = scriptedFetch([tokenAnswer, sentAnswer]);
    const provider = createFcmProvider({ serviceAccount: SERVICE_ACCOUNT, fetch });
    const device = { id: randomUUID(), platform: 'android' as const, pushToken: 'fcm-token-1' };

    await provider.send(device, { v: 1, kind: 'dive', id: 'dive-1', userId: 'user-1' });

    expect(calls).toHaveLength(2);
    expect(calls[0]!.url).toBe(TOKEN_URI);
    const assertion = new URLSearchParams(calls[0]!.body).get('assertion')!;
    const [header, claimsPart, signature] = assertion.split('.');
    expect(JSON.parse(Buffer.from(header!, 'base64url').toString())).toEqual({ alg: 'RS256', typ: 'JWT' });
    expect(JSON.parse(Buffer.from(claimsPart!, 'base64url').toString())).toMatchObject({
      iss: SERVICE_ACCOUNT.clientEmail, aud: TOKEN_URI, scope: 'https://www.googleapis.com/auth/firebase.messaging',
    });
    expect(verify('RSA-SHA256', Buffer.from(`${header}.${claimsPart}`), publicKey, Buffer.from(signature!, 'base64url'))).toBe(true);

    expect(calls[1]!.url).toBe('https://fcm.googleapis.com/v1/projects/divehub-test/messages:send');
    expect(calls[1]!.headers.authorization).toBe('Bearer ya29.test');
    const message = (JSON.parse(calls[1]!.body) as { message: Record<string, unknown> }).message;
    expect(message.token).toBe('fcm-token-1');
    expect(message.data).toEqual({ v: '1', kind: 'dive', id: 'dive-1' });
    expect(message.android).toEqual({ priority: 'high', collapse_key: 'user-1:dive' });
    expect(message).not.toHaveProperty('notification');
  });

  it('reuses the access token until it is near expiry', async () => {
    const { calls, fetch } = scriptedFetch([tokenAnswer, sentAnswer, sentAnswer]);
    const provider = createFcmProvider({ serviceAccount: SERVICE_ACCOUNT, fetch, now: () => 1_000_000 });
    const device = { id: randomUUID(), platform: 'android' as const, pushToken: 'fcm-token-2' };
    const hint = { v: 1 as const, kind: 'dive' as const, id: 'dive-2', userId: 'user-2' };

    await provider.send(device, hint);
    await provider.send(device, hint);

    expect(calls.filter((call) => call.url === TOKEN_URI)).toHaveLength(1);
    expect(calls.filter((call) => call.url.endsWith(':send'))).toHaveLength(2);
  });

  it('turns UNREGISTERED into a gone token and leaves other FCM errors alone', async () => {
    const gone = scriptedFetch([tokenAnswer, {
      status: 404,
      body: JSON.stringify({ error: { status: 'NOT_FOUND', details: [{ errorCode: 'UNREGISTERED' }] } }),
    }]);
    const provider = createFcmProvider({ serviceAccount: SERVICE_ACCOUNT, fetch: gone.fetch });
    await expect(provider.send({ id: randomUUID(), platform: 'android', pushToken: 'dead' }, {
      v: 1, kind: 'dive', id: 'dive-3', userId: 'user-3',
    })).rejects.toBeInstanceOf(PushTokenGoneError);

    const busy = scriptedFetch([tokenAnswer, { status: 503, body: JSON.stringify({ error: { message: 'unavailable' } }) }]);
    const other = createFcmProvider({ serviceAccount: SERVICE_ACCOUNT, fetch: busy.fetch });
    const failure = other.send({ id: randomUUID(), platform: 'android', pushToken: 'busy' }, {
      v: 1, kind: 'dive', id: 'dive-4', userId: 'user-4',
    }).catch((error: unknown) => error);
    await expect(failure).resolves.not.toBeInstanceOf(PushTokenGoneError);
    await expect(failure).resolves.toMatchObject({ message: expect.stringContaining('HTTP 503') });
  });
});

describe.skipIf(!(await databaseReachable()))('the push dispatcher', () => {
  let t: TestDatabase;
  let auth: ReturnType<typeof createTestAuth>;
  const sendPushJobs = async () => (await t.db.execute(
    sql`select count(*)::int as n from graphile_worker.jobs where task_identifier = ${SEND_PUSH_TASK}`,
  )).rows[0] as { n: number };

  beforeAll(async () => {
    t = await createTestDatabase();
    auth = createTestAuth(t.db);
  });

  afterAll(async () => {
    await t?.drop();
  });

  /** A User with one session and one Android push registration, as the API would have made them. */
  async function deviceFor(email: string, pushToken: string) {
    const user = await createUser(auth, email);
    const [signedIn] = await t.db.insert(session)
      .values({ token: randomUUID(), userId: user.id, expiresAt: new Date(Date.now() + 60 * 60_000) })
      .returning({ id: session.id });
    const [device] = await t.db.insert(pushDevice)
      .values({ userId: user.id, sessionId: signedIn!.id, platform: 'android', pushToken })
      .returning();
    return { user, device: device! };
  }

  it('queues nothing and sends nothing when FCM is not configured', async () => {
    const push = createPushDispatcher({ db: t.db, fcm: undefined });
    expect(push.enabled).toBe(false);
    await t.db.transaction(async (tx) => {
      await push.enqueue(tx, { kind: 'dive', id: randomUUID(), audience: [randomUUID()] });
    });
    expect((await sendPushJobs()).n).toBe(0);
    await expect(push.send({ userId: randomUUID(), kind: 'dive', id: randomUUID() })).resolves.toBeUndefined();
  });

  it('deletes the device row when FCM says its token is gone', async () => {
    const { user, device } = await deviceFor('gone@example.com', 'token-gone');
    const { fetch } = scriptedFetch([tokenAnswer, {
      status: 404,
      body: JSON.stringify({ error: { status: 'NOT_FOUND', details: [{ errorCode: 'UNREGISTERED' }] } }),
    }]);
    const push = createPushDispatcher({ db: t.db, fcm: FCM(), fetch });

    await push.send({ userId: user.id, kind: 'dive', id: 'dive-gone' });

    expect(await t.db.select().from(pushDevice).where(eq(pushDevice.id, device.id))).toHaveLength(0);
  });

  it('keeps the registration and fails the job on an ordinary FCM error, so graphile retries', async () => {
    const { user, device } = await deviceFor('retry@example.com', 'token-retry');
    const { fetch } = scriptedFetch([tokenAnswer, { status: 500, body: JSON.stringify({ error: { message: 'internal' } }) }]);
    const push = createPushDispatcher({ db: t.db, fcm: FCM(), fetch });

    await expect(push.send({ userId: user.id, kind: 'dive', id: 'dive-retry' })).rejects.toThrow('FCM send failed');
    expect(await t.db.select().from(pushDevice).where(eq(pushDevice.id, device.id))).toHaveLength(1);
  });
});
