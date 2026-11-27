// The billing API (hosted-DiveHub plan §4.5–4.6, ADR 0092): the signed Stripe webhook upserting entitlements, and the
// authenticated checkout/portal links. Runs against the real database; Stripe itself is a stand-in fetch, so no money
// moves and no request leaves the process. The signature is computed exactly as Stripe documents it.
import { createHmac } from 'node:crypto';
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, beforeEach, describe, expect, it } from 'vitest';
import type { StripeFetch } from '../src/billing/stripe.js';
import { user, userEntitlement } from '../src/db/schema.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

const SECRET = 'whsec_test_secret';
const PRICE = 'price_monthly';
const STRIPE_URL = 'https://stripe.test';
const CHECKOUT_URL = 'https://checkout.stripe.com/c/pay/cs_test_1';
const PORTAL_URL = 'https://billing.stripe.com/p/session/test_1';
const at = (iso: string) => Math.floor(Date.parse(iso) / 1000);

const sign = (body: string, secret = SECRET, timestamp = '1764151200') =>
  `t=${timestamp},v1=${createHmac('sha256', secret).update(`${timestamp}.${body}`).digest('hex')}`;

interface EventOptions {
  status?: string;
  userId?: string | null;
  subscriptionId?: string;
  customerId?: string;
  priceId?: string;
  occurredAt?: string;
  periodEnd?: string | null;
  cancelAtPeriodEnd?: boolean;
}

describe.skipIf(!(await databaseReachable()))('the billing API', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let userId: string;
  let cookie: string;
  const calls: { url: string; params: URLSearchParams }[] = [];
  let stripeFailure: 'checkout' | 'portal' | null = null;

  const subscriptionEvent = (type: string, options: EventOptions = {}): string => {
    const {
      status = 'active', userId: eventUserId = userId, subscriptionId = 'sub_1', customerId = 'cus_1', priceId = PRICE,
      occurredAt = '2026-11-26T10:00:00Z', periodEnd = '2026-12-26T10:00:00Z', cancelAtPeriodEnd,
    } = options;
    return JSON.stringify({
      id: 'evt_1',
      type,
      created: at(occurredAt),
      data: {
        object: {
          id: subscriptionId,
          customer: customerId,
          status,
          metadata: eventUserId === null ? null : { userId: eventUserId },
          ...(periodEnd && { current_period_end: at(periodEnd) }),
          ...(cancelAtPeriodEnd !== undefined && { cancel_at_period_end: cancelAtPeriodEnd }),
          items: { data: [{ price: { id: priceId } }] },
        },
      },
    });
  };

  const paymentFailedEvent = (subscriptionId = 'sub_1', customerId = 'cus_1'): string => JSON.stringify({
    id: 'evt_2',
    type: 'invoice.payment_failed',
    created: at('2026-11-27T10:00:00Z'),
    data: { object: { id: 'in_1', customer: customerId, subscription: subscriptionId } },
  });

  const stripeFetch: StripeFetch = async (url, init) => {
    const params = new URLSearchParams(init.body);
    calls.push({ url, params });
    if (stripeFailure === 'checkout' && url.endsWith('/v1/checkout/sessions')) {
      return { status: 500, text: async () => JSON.stringify({ error: { message: 'Stripe is down' } }) };
    }
    if (stripeFailure === 'portal' && url.endsWith('/v1/billing_portal/sessions')) {
      return { status: 500, text: async () => JSON.stringify({ error: { message: 'Stripe is down' } }) };
    }
    if (url.endsWith('/v1/checkout/sessions')) {
      return { status: 200, text: async () => JSON.stringify({ id: 'cs_test_1', url: CHECKOUT_URL }) };
    }
    if (url.endsWith('/v1/billing_portal/sessions')) {
      return { status: 200, text: async () => JSON.stringify({ id: 'bps_1', url: PORTAL_URL }) };
    }
    return { status: 404, text: async () => JSON.stringify({ error: { message: 'unexpected call' } }) };
  };

  const deliver = (body: string, header = sign(body)) => ctx.app.inject({
    method: 'POST', url: '/api/billing/webhooks',
    headers: { 'content-type': 'application/json', 'stripe-signature': header },
    payload: body,
  });
  const call = (url: string) => ctx.app.inject({ method: 'GET', url, headers: { cookie, origin: BASE_URL } });
  const row = async (id = userId) => (await t.db.select().from(userEntitlement).where(eq(userEntitlement.userId, id)))[0];

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, {
      billing: { provider: 'stripe', secretKey: 'sk_test_1', webhookSecret: SECRET, priceId: PRICE },
      stripeFetch, stripeUrl: STRIPE_URL,
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
    stripeFailure = null;
    await t.db.delete(userEntitlement);
  });

  it('upserts the entitlement from a signed customer.subscription.created event', async () => {
    const response = await deliver(subscriptionEvent('customer.subscription.created'));
    expect(response.statusCode).toBe(200);
    expect(await row()).toMatchObject({
      plan: 'pro', status: 'active', billingCustomerId: 'cus_1', billingSubscriptionId: 'sub_1',
    });
    expect((await row())!.currentPeriodEnd).toEqual(new Date('2026-12-26T10:00:00Z'));
    expect(await ctx.entitlements.entitled(userId)).toBe(true);
  });

  it('refuses a missing or malformed signature, and a changed body, writing nothing', async () => {
    const signed = subscriptionEvent('customer.subscription.created');
    expect((await ctx.app.inject({
      method: 'POST', url: '/api/billing/webhooks', headers: { 'content-type': 'application/json' }, payload: signed,
    })).statusCode).toBe(401);
    expect((await deliver(signed, 't=1,v1=00')).statusCode).toBe(401);
    expect((await deliver(signed.replace('sub_1', 'sub_2'), sign(signed))).statusCode).toBe(401);
    expect(await row()).toBeUndefined();
  });

  it('answers 200 to a signed body that is not JSON, and writes nothing', async () => {
    const body = 'not json';
    expect((await deliver(body, sign(body))).statusCode).toBe(200);
    expect(await row()).toBeUndefined();
  });

  it('is idempotent on replay and ignores an event older than the stored one', async () => {
    await deliver(subscriptionEvent('customer.subscription.updated', { status: 'past_due', occurredAt: '2026-11-26T12:00:00Z' }));
    // A late, older event must not roll the newer past_due back to active.
    await deliver(subscriptionEvent('customer.subscription.updated', { status: 'active', occurredAt: '2026-11-26T11:00:00Z' }));
    expect((await row())!.status).toBe('past_due');
    // A replay of the newest event changes nothing.
    await deliver(subscriptionEvent('customer.subscription.updated', { status: 'past_due', occurredAt: '2026-11-26T12:00:00Z' }));
    expect((await row())!.status).toBe('past_due');
    expect(await ctx.entitlements.entitled(userId)).toBe(true);
  });

  it('keeps a canceled subscription entitled until its paid period ends, then frees it', async () => {
    await deliver(subscriptionEvent('customer.subscription.deleted', { status: 'canceled', occurredAt: '2026-11-26T10:00:00Z', periodEnd: '2099-01-01T00:00:00Z' }));
    expect((await row())!.status).toBe('canceled');
    expect(await ctx.entitlements.entitled(userId)).toBe(true);
    await deliver(subscriptionEvent('customer.subscription.deleted', { status: 'canceled', occurredAt: '2026-11-27T10:00:00Z', periodEnd: '2000-01-01T00:00:00Z' }));
    expect(await ctx.entitlements.entitled(userId)).toBe(false);
  });

  it('still revokes a stored subscription whose price the operator no longer lists', async () => {
    await deliver(subscriptionEvent('customer.subscription.created'));
    await deliver(subscriptionEvent('customer.subscription.deleted', {
      status: 'canceled', priceId: 'price_retired', occurredAt: '2026-11-27T10:00:00Z', periodEnd: '2000-01-01T00:00:00Z',
    }));
    expect((await row())!.status).toBe('canceled');
    expect(await ctx.entitlements.entitled(userId)).toBe(false);
  });

  it('finds the User from the stored subscription when the event carries no metadata', async () => {
    await deliver(subscriptionEvent('customer.subscription.created'));
    await deliver(subscriptionEvent('customer.subscription.updated', {
      status: 'canceled', userId: null, occurredAt: '2026-11-27T10:00:00Z',
    }));
    expect((await row())!.status).toBe('canceled');
  });

  it('applies invoice.payment_failed to the stored subscription as the grace state', async () => {
    await deliver(subscriptionEvent('customer.subscription.created'));
    await deliver(paymentFailedEvent());
    expect((await row())!.status).toBe('past_due');
    expect(await ctx.entitlements.entitled(userId)).toBe(true);
  });

  it('ignores a payment failure for a subscription this instance never granted', async () => {
    expect((await deliver(paymentFailedEvent('sub_unknown', 'cus_unknown'))).statusCode).toBe(200);
    expect(await row()).toBeUndefined();
  });

  it('does not let a late event for a superseded subscription touch the current one', async () => {
    await deliver(subscriptionEvent('customer.subscription.created'));
    await deliver(subscriptionEvent('customer.subscription.created', {
      subscriptionId: 'sub_2', customerId: 'cus_2', occurredAt: '2026-11-27T10:00:00Z',
    }));
    // The old subscription's cancellation arrives after the upgrade: the new one stays active.
    await deliver(subscriptionEvent('customer.subscription.deleted', {
      subscriptionId: 'sub_1', status: 'canceled', occurredAt: '2026-11-28T10:00:00Z',
    }));
    expect(await row()).toMatchObject({ billingSubscriptionId: 'sub_2', billingCustomerId: 'cus_2', status: 'active' });
  });

  it('answers 200 but grants nothing for another price or an event it does not handle', async () => {
    expect((await deliver(subscriptionEvent('customer.subscription.created', { priceId: 'price_other' }))).statusCode).toBe(200);
    expect(await row()).toBeUndefined();
    expect((await deliver(subscriptionEvent('invoice.paid'))).statusCode).toBe(200);
    expect(await row()).toBeUndefined();
  });

  it('acknowledges an event for a User deleted since the checkout, instead of retrying forever', async () => {
    const gone = await createUser(ctx.auth, 'gone@example.com');
    await deliver(subscriptionEvent('customer.subscription.created', { userId: gone.id, subscriptionId: 'sub_gone', customerId: 'cus_gone' }));
    await t.db.delete(user).where(eq(user.id, gone.id));
    const response = await deliver(subscriptionEvent('customer.subscription.deleted', {
      userId: gone.id, subscriptionId: 'sub_gone', customerId: 'cus_gone', status: 'canceled', occurredAt: '2026-11-27T10:00:00Z',
    }));
    expect(response.statusCode).toBe(200);
    expect(await row(gone.id)).toBeUndefined();
  });

  it('acknowledges a subscription that already belongs to another User row, instead of retrying forever', async () => {
    await deliver(subscriptionEvent('customer.subscription.created'));
    const other = await createUser(ctx.auth, 'other@example.com');
    const response = await deliver(subscriptionEvent('customer.subscription.created', {
      userId: other.id, subscriptionId: 'sub_1', customerId: 'cus_other',
    }));
    expect(response.statusCode).toBe(200);
    expect(await row(other.id)).toBeUndefined();
  });

  it('requires a session for the checkout link, and puts the User in the subscription metadata', async () => {
    expect((await ctx.app.inject({ method: 'GET', url: '/api/billing/checkout' })).statusCode).toBe(401);
    const response = await call('/api/billing/checkout');
    expect(response.statusCode).toBe(200);
    expect(response.json()).toEqual({ url: CHECKOUT_URL });
    expect(calls).toHaveLength(1);
    expect(calls[0]!.url).toBe(`${STRIPE_URL}/v1/checkout/sessions`);
    const params = calls[0]!.params;
    expect(params.get('mode')).toBe('subscription');
    expect(params.get('line_items[0][price]')).toBe(PRICE);
    expect(params.get('line_items[0][quantity]')).toBe('1');
    expect(params.get('client_reference_id')).toBe(userId);
    expect(params.get('subscription_data[metadata][userId]')).toBe(userId);
    expect(params.get('automatic_tax[enabled]')).toBe('true');
    expect(params.get('success_url')).toBe(`${BASE_URL}/#/settings/plan`);
    expect(params.get('cancel_url')).toBe(`${BASE_URL}/#/settings/plan`);
    expect(params.get('customer')).toBeNull();
  });

  it('refuses a second checkout while the User is entitled, so Stripe never charges twice', async () => {
    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'active', billingCustomerId: 'cus_1', billingSubscriptionId: 'sub_1', updatedAt: new Date(),
    });
    const response = await call('/api/billing/checkout');
    expect(response.statusCode).toBe(409);
    expect(response.json()).toMatchObject({ code: 'billing_already_subscribed' });
    expect(calls).toHaveLength(0);
  });

  it('reuses a stored Stripe customer at checkout after the paid period ended', async () => {
    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'canceled', billingCustomerId: 'cus_1', billingSubscriptionId: 'sub_1',
      currentPeriodEnd: new Date('2000-01-01T00:00:00Z'), updatedAt: new Date(),
    });
    expect((await call('/api/billing/checkout')).statusCode).toBe(200);
    expect(calls[0]!.params.get('customer')).toBe('cus_1');
    expect(calls[0]!.params.get('customer_update[address]')).toBe('auto');
  });

  it('answers 502 when Stripe will not create the checkout link', async () => {
    stripeFailure = 'checkout';
    const response = await call('/api/billing/checkout');
    expect(response.statusCode).toBe(502);
    expect(response.json()).toMatchObject({ code: 'billing_unavailable' });
  });

  it('opens the Customer Portal only once there is a Stripe customer', async () => {
    const refused = await call('/api/billing/portal');
    expect(refused.statusCode).toBe(409);
    expect(refused.json()).toMatchObject({ code: 'billing_not_subscribed' });

    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'active', billingCustomerId: 'cus_1', billingSubscriptionId: 'sub_1', updatedAt: new Date(),
    });
    const opened = await call('/api/billing/portal');
    expect(opened.statusCode).toBe(200);
    expect(opened.json()).toEqual({ url: PORTAL_URL });
    expect(calls).toHaveLength(1);
    expect(calls[0]!.url).toBe(`${STRIPE_URL}/v1/billing_portal/sessions`);
    expect(calls[0]!.params.get('customer')).toBe('cus_1');
    expect(calls[0]!.params.get('return_url')).toBe(`${BASE_URL}/#/settings/plan`);
  });

  it('answers 502 when Stripe will not open the portal', async () => {
    await t.db.insert(userEntitlement).values({
      userId, plan: 'pro', status: 'active', billingCustomerId: 'cus_1', billingSubscriptionId: 'sub_1', updatedAt: new Date(),
    });
    stripeFailure = 'portal';
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
      // The client hides the Plan section from the server's answer, not from a build flag (ADR 0087).
      await createUser(ctx.auth, 'solo@example.com');
      const cookie = await signIn(ctx.app, 'solo@example.com');
      const me = await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie, origin: BASE_URL } });
      expect(me.json().billing).toMatchObject({ configured: false, plan: 'free', status: 'none', entitled: true });
    } finally {
      await ctx.app.close();
      await t.drop();
    }
  });
});
