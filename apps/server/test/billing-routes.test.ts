// The billing API (hosted-DiveHub plan §4.5–4.6, ADR 0089): the signed Paddle webhook upserting entitlements, and the
// authenticated checkout/portal links. Runs against the real database; Paddle itself is a stand-in fetch, so no money
// moves and no request leaves the process. The signature is computed exactly as Paddle documents it.
import { createHmac } from 'node:crypto';
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, beforeEach, describe, expect, it } from 'vitest';
import type { PaddleFetch } from '../src/billing/paddle.js';
import { user, userEntitlement } from '../src/db/schema.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

const SECRET = 'pdl_ntfset_test_secret';
const PRICE = 'pri_monthly';
const YEARLY = 'pri_yearly';
const PADDLE_URL = 'https://sandbox-api.paddle.com';
const CHECKOUT_URL = 'https://sandbox-checkout.paddle.com/pay?_ptxn=txn_1';
const PORTAL_URL = 'https://customer-portal.paddle.com/cpl_1';

const sign = (body: string, secret = SECRET, ts = '1764151200') =>
  `ts=${ts};h1=${createHmac('sha256', secret).update(`${ts}:${body}`).digest('hex')}`;

interface EventOptions {
  status?: string;
  userId?: string | null;
  subscriptionId?: string;
  customerId?: string;
  priceId?: string;
  occurredAt?: string;
  periodEnd?: string | null;
}

describe.skipIf(!(await databaseReachable()))('the billing API', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let userId: string;
  let cookie: string;
  const calls: { url: string; body: unknown }[] = [];
  let paddleFailure: 'transactions' | 'portal' | null = null;

  const subscriptionEvent = (type: string, options: EventOptions = {}): string => {
    const {
      status = 'active', userId: eventUserId = userId, subscriptionId = 'sub_1', customerId = 'ctm_1', priceId = PRICE,
      occurredAt = '2026-11-26T10:00:00Z', periodEnd = '2026-12-26T10:00:00Z',
    } = options;
    return JSON.stringify({
      event_id: 'evt_1',
      event_type: type,
      occurred_at: occurredAt,
      data: {
        id: subscriptionId,
        customer_id: customerId,
        status,
        custom_data: eventUserId === null ? null : { userId: eventUserId },
        current_billing_period: { ends_at: periodEnd },
        items: [{ price: { id: priceId }, quantity: 1 }],
      },
    });
  };

  const paddleFetch: PaddleFetch = async (url, init) => {
    calls.push({ url, body: JSON.parse(init.body) as unknown });
    if (paddleFailure === 'transactions' && url.endsWith('/transactions')) {
      return { status: 500, text: async () => JSON.stringify({ error: { detail: 'Paddle is down' } }) };
    }
    if (paddleFailure === 'portal' && url.includes('/portal-sessions')) {
      return { status: 500, text: async () => JSON.stringify({ error: { detail: 'Paddle is down' } }) };
    }
    if (url.endsWith('/transactions')) {
      return { status: 201, text: async () => JSON.stringify({ data: { checkout: { url: CHECKOUT_URL } } }) };
    }
    if (url.includes('/portal-sessions')) {
      return { status: 201, text: async () => JSON.stringify({ data: { urls: { general: { overview: PORTAL_URL } } } }) };
    }
    return { status: 404, text: async () => JSON.stringify({ error: { detail: 'unexpected call' } }) };
  };

  const deliver = (body: string, header = sign(body)) => ctx.app.inject({
    method: 'POST', url: '/api/billing/webhooks',
    headers: { 'content-type': 'application/json', 'paddle-signature': header },
    payload: body,
  });
  const call = (url: string) => ctx.app.inject({ method: 'GET', url, headers: { cookie, origin: BASE_URL } });
  const row = async (id = userId) => (await t.db.select().from(userEntitlement).where(eq(userEntitlement.userId, id)))[0];

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, {
      billing: { provider: 'paddle', apiKey: 'pdl_sdbx_test', webhookSecret: SECRET, priceIds: [PRICE, YEARLY] },
      paddleFetch, paddleUrl: PADDLE_URL,
    });
    userId = (await createUser(ctx.auth, 'ann@example.com')).id;
    cookie = await signIn(ctx.app, 'ann@example.com');
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });
  beforeEach(async () => {
    calls.length = 0;
    paddleFailure = null;
    await t.db.delete(userEntitlement);
  });

  it('upserts the entitlement from a signed subscription.created event', async () => {
    const response = await deliver(subscriptionEvent('subscription.created'));
    expect(response.statusCode).toBe(200);
    expect(await row()).toMatchObject({
      plan: 'pro', status: 'active', paddleCustomerId: 'ctm_1', paddleSubscriptionId: 'sub_1',
    });
    expect((await row())!.currentPeriodEnd).toEqual(new Date('2026-12-26T10:00:00Z'));
    expect(await ctx.entitlements.entitled(userId)).toBe(true);
  });

  it('refuses a missing or malformed signature, and a changed body, writing nothing', async () => {
    const signed = subscriptionEvent('subscription.created');
    expect((await ctx.app.inject({
      method: 'POST', url: '/api/billing/webhooks', headers: { 'content-type': 'application/json' }, payload: signed,
    })).statusCode).toBe(401);
    expect((await deliver(signed, 'ts=1;h1=00')).statusCode).toBe(401);
    expect((await deliver(signed.replace('sub_1', 'sub_2'), sign(signed))).statusCode).toBe(401);
    expect(await row()).toBeUndefined();
  });

  it('answers 200 to a signed body that is not JSON, and writes nothing', async () => {
    const body = 'not json';
    expect((await deliver(body, sign(body))).statusCode).toBe(200);
    expect(await row()).toBeUndefined();
  });

  it('is idempotent on replay and ignores an event older than the stored one', async () => {
    await deliver(subscriptionEvent('subscription.updated', { status: 'past_due', occurredAt: '2026-11-26T12:00:00Z' }));
    // A late, older event must not roll the newer past_due back to active.
    await deliver(subscriptionEvent('subscription.updated', { status: 'active', occurredAt: '2026-11-26T11:00:00Z' }));
    expect((await row())!.status).toBe('past_due');
    // A replay of the newest event changes nothing.
    await deliver(subscriptionEvent('subscription.updated', { status: 'past_due', occurredAt: '2026-11-26T12:00:00Z' }));
    expect((await row())!.status).toBe('past_due');
    expect(await ctx.entitlements.entitled(userId)).toBe(true);
  });

  it('keeps a canceled subscription entitled until its paid period ends, then frees it', async () => {
    await deliver(subscriptionEvent('subscription.canceled', { status: 'canceled', occurredAt: '2026-11-26T10:00:00Z', periodEnd: '2099-01-01T00:00:00Z' }));
    expect((await row())!.status).toBe('canceled');
    expect(await ctx.entitlements.entitled(userId)).toBe(true);
    await deliver(subscriptionEvent('subscription.canceled', { status: 'canceled', occurredAt: '2026-11-27T10:00:00Z', periodEnd: '2000-01-01T00:00:00Z' }));
    expect(await ctx.entitlements.entitled(userId)).toBe(false);
  });

  it('still revokes a stored subscription whose price the operator no longer lists', async () => {
    await deliver(subscriptionEvent('subscription.created'));
    await deliver(subscriptionEvent('subscription.canceled', {
      status: 'canceled', priceId: 'pri_retired', occurredAt: '2026-11-27T10:00:00Z', periodEnd: '2000-01-01T00:00:00Z',
    }));
    expect((await row())!.status).toBe('canceled');
    expect(await ctx.entitlements.entitled(userId)).toBe(false);
  });

  it('finds the User from the stored subscription when the event carries no passthrough', async () => {
    await deliver(subscriptionEvent('subscription.created'));
    await deliver(subscriptionEvent('subscription.updated', {
      status: 'canceled', userId: null, occurredAt: '2026-11-27T10:00:00Z',
    }));
    expect((await row())!.status).toBe('canceled');
  });

  it('does not let a late event for a superseded subscription touch the current one', async () => {
    await deliver(subscriptionEvent('subscription.created'));
    await deliver(subscriptionEvent('subscription.created', {
      subscriptionId: 'sub_2', customerId: 'ctm_2', occurredAt: '2026-11-27T10:00:00Z',
    }));
    // The old subscription's cancellation arrives after the upgrade: the new one stays active.
    await deliver(subscriptionEvent('subscription.canceled', {
      subscriptionId: 'sub_1', status: 'canceled', occurredAt: '2026-11-28T10:00:00Z',
    }));
    expect(await row()).toMatchObject({ paddleSubscriptionId: 'sub_2', paddleCustomerId: 'ctm_2', status: 'active' });
  });

  it('answers 200 but grants nothing for another price or an event it does not handle', async () => {
    expect((await deliver(subscriptionEvent('subscription.created', { priceId: 'pri_other' }))).statusCode).toBe(200);
    expect(await row()).toBeUndefined();
    expect((await deliver(subscriptionEvent('transaction.completed'))).statusCode).toBe(200);
    expect(await row()).toBeUndefined();
  });

  it('acknowledges an event for a User deleted since the checkout, instead of retrying forever', async () => {
    const gone = await createUser(ctx.auth, 'gone@example.com');
    await deliver(subscriptionEvent('subscription.created', { userId: gone.id, subscriptionId: 'sub_gone', customerId: 'ctm_gone' }));
    await t.db.delete(user).where(eq(user.id, gone.id));
    const response = await deliver(subscriptionEvent('subscription.canceled', {
      userId: gone.id, subscriptionId: 'sub_gone', customerId: 'ctm_gone', status: 'canceled', occurredAt: '2026-11-27T10:00:00Z',
    }));
    expect(response.statusCode).toBe(200);
    expect(await row(gone.id)).toBeUndefined();
  });

  it('acknowledges a subscription that already belongs to another User row, instead of retrying forever', async () => {
    await deliver(subscriptionEvent('subscription.created'));
    const other = await createUser(ctx.auth, 'other@example.com');
    const response = await deliver(subscriptionEvent('subscription.created', {
      userId: other.id, subscriptionId: 'sub_1', customerId: 'ctm_other',
    }));
    expect(response.statusCode).toBe(200);
    expect(await row(other.id)).toBeUndefined();
  });

  it('requires a session for the checkout link, and puts the User in Paddle\'s passthrough', async () => {
    expect((await ctx.app.inject({ method: 'GET', url: '/api/billing/checkout' })).statusCode).toBe(401);
    const response = await call('/api/billing/checkout');
    expect(response.statusCode).toBe(200);
    expect(response.json()).toEqual({ url: CHECKOUT_URL });
    expect(calls).toEqual([{
      url: `${PADDLE_URL}/transactions`,
      body: { items: [{ price_id: PRICE, quantity: 1 }], custom_data: { userId } },
    }]);
  });

  it('refuses a second checkout while the User is entitled, so Paddle never charges twice', async () => {
    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'active', paddleCustomerId: 'ctm_1', paddleSubscriptionId: 'sub_1', updatedAt: new Date(),
    });
    const response = await call('/api/billing/checkout');
    expect(response.statusCode).toBe(409);
    expect(response.json()).toMatchObject({ code: 'billing_already_subscribed' });
    expect(calls).toHaveLength(0);
  });

  it('reuses a stored Paddle customer at checkout after the paid period ended', async () => {
    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'canceled', paddleCustomerId: 'ctm_1', paddleSubscriptionId: 'sub_1',
      currentPeriodEnd: new Date('2000-01-01T00:00:00Z'), updatedAt: new Date(),
    });
    expect((await call('/api/billing/checkout')).statusCode).toBe(200);
    expect(calls[0]!.body).toMatchObject({ customer_id: 'ctm_1', custom_data: { userId } });
  });

  it('answers 502 when Paddle will not create the checkout link', async () => {
    paddleFailure = 'transactions';
    const response = await call('/api/billing/checkout');
    expect(response.statusCode).toBe(502);
    expect(response.json()).toMatchObject({ code: 'billing_unavailable' });
  });

  it('opens the customer portal only once there is a Paddle customer', async () => {
    const refused = await call('/api/billing/portal');
    expect(refused.statusCode).toBe(409);
    expect(refused.json()).toMatchObject({ code: 'billing_not_subscribed' });

    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'active', paddleCustomerId: 'ctm_1', paddleSubscriptionId: 'sub_1', updatedAt: new Date(),
    });
    const opened = await call('/api/billing/portal');
    expect(opened.statusCode).toBe(200);
    expect(opened.json()).toEqual({ url: PORTAL_URL });
    expect(calls).toEqual([{ url: `${PADDLE_URL}/customers/ctm_1/portal-sessions`, body: {} }]);
  });

  it('answers 502 when Paddle will not open the portal', async () => {
    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'active', paddleCustomerId: 'ctm_1', paddleSubscriptionId: 'sub_1', updatedAt: new Date(),
    });
    paddleFailure = 'portal';
    const response = await call('/api/billing/portal');
    expect(response.statusCode).toBe(502);
    expect(response.json()).toMatchObject({ code: 'billing_unavailable' });
  });
});

describe.skipIf(!(await databaseReachable()))('an instance without billing', () => {
  it('registers no billing route and keeps every feature free (the dual-track invariant)', async () => {
    const t = await createTestDatabase();
    const ctx = await createTestApp(t);
    try {
      expect((await ctx.app.inject({ method: 'GET', url: '/api/billing/checkout' })).statusCode).toBe(404);
      expect((await ctx.app.inject({
        method: 'POST', url: '/api/billing/webhooks', payload: '{}', headers: { 'content-type': 'application/json' },
      })).statusCode).toBe(404);
      expect(await ctx.entitlements.entitled('any-user')).toBe(true);
    } finally {
      await ctx.app.close();
      await t.drop();
    }
  });
});
