// Stripe's two pure pieces (hosted-DiveHub plan §4.5–4.6, ADR 0092): the webhook signature over the raw body, and the
// subscription events mapped onto the entitlement state machine. No database here; the webhook's write is tested against
// PostgreSQL in billing-routes.test.ts.
import { createHmac } from 'node:crypto';
import { describe, expect, it } from 'vitest';
import { parseSubscriptionEvent } from '../src/billing/stripe-events.js';
import { verifyStripeSignature } from '../src/billing/stripe-signature.js';

const SECRET = 'whsec_test_secret';
const USER = '01900000-0000-7000-8000-000000000001';
const PRICE = 'price_monthly';
const at = (iso: string) => Date.parse(iso) / 1000;

function sign(body: string, secret = SECRET, timestamp = '1764151200'): string {
  const v1 = createHmac('sha256', secret).update(`${timestamp}.${body}`).digest('hex');
  return `t=${timestamp},v1=${v1}`;
}

describe('the Stripe webhook signature', () => {
  const body = JSON.stringify({ type: 'customer.subscription.created', data: { object: { id: 'sub_1' } } });

  it('accepts the header Stripe sends for exactly these bytes', () => {
    expect(verifyStripeSignature(Buffer.from(body), sign(body), SECRET)).toBe(true);
  });

  it('rejects a changed body, a wrong secret, and a header it cannot read', () => {
    const header = sign(body);
    expect(verifyStripeSignature(Buffer.from(body.replace('sub_1', 'sub_2')), header, SECRET)).toBe(false);
    expect(verifyStripeSignature(Buffer.from(body), sign(body, 'another-secret'), SECRET)).toBe(false);
    expect(verifyStripeSignature(Buffer.from(body), undefined, SECRET)).toBe(false);
    expect(verifyStripeSignature(Buffer.from(body), 'garbage', SECRET)).toBe(false);
    expect(verifyStripeSignature(Buffer.from(body), 't=1,v1=zz', SECRET)).toBe(false);
    expect(verifyStripeSignature(Buffer.from(body), 'v1=00', SECRET)).toBe(false);
  });

  it('accepts any matching v1 while Stripe rotates an endpoint secret', () => {
    const good = createHmac('sha256', SECRET).update(`1764151200.${body}`).digest('hex');
    expect(verifyStripeSignature(Buffer.from(body), `t=1764151200,v1=00,v1=${good}`, SECRET)).toBe(true);
  });
});

describe('a Stripe subscription event', () => {
  const event = (object: Record<string, unknown> = {}, type = 'customer.subscription.created') => ({
    id: 'evt_1',
    type,
    created: at('2026-11-26T10:00:00Z'),
    data: {
      object: {
        id: 'sub_1',
        customer: 'cus_1',
        status: 'active',
        metadata: { userId: USER },
        current_period_end: at('2026-12-26T10:00:00Z'),
        items: { data: [{ price: { id: PRICE }, current_period_end: at('2026-12-26T10:00:00Z') }] },
        ...object,
      },
    },
  });

  it('maps a created active subscription with our price and the checkout metadata', () => {
    expect(parseSubscriptionEvent(event(), PRICE)).toEqual({
      eventType: 'customer.subscription.created',
      userId: USER,
      stripeCustomerId: 'cus_1',
      stripeSubscriptionId: 'sub_1',
      status: 'active',
      currentPeriodEnd: new Date('2026-12-26T10:00:00Z'),
      occurredAt: new Date('2026-11-26T10:00:00Z'),
      matchesPrice: true,
    });
  });

  it('takes a trial as active, past due or unpaid as the grace window, and paused as canceled', () => {
    expect(parseSubscriptionEvent(event({ status: 'trialing' }), PRICE)?.status).toBe('active');
    expect(parseSubscriptionEvent(event({ status: 'past_due' }), PRICE)?.status).toBe('past_due');
    expect(parseSubscriptionEvent(event({ status: 'unpaid' }), PRICE)?.status).toBe('past_due');
    expect(parseSubscriptionEvent(event({ status: 'paused' }), PRICE)?.status).toBe('canceled');
  });

  it('grants nothing for an incomplete subscription, even when Stripe writes a future period end', () => {
    expect(parseSubscriptionEvent(event({ status: 'incomplete' }), PRICE)).toMatchObject({
      status: 'canceled', currentPeriodEnd: null,
    });
    expect(parseSubscriptionEvent(event({ status: 'incomplete_expired' }), PRICE)?.currentPeriodEnd).toBeNull();
  });

  it('shows a subscription canceled at the period end as canceled, so the client says paid until', () => {
    expect(parseSubscriptionEvent(event({ cancel_at_period_end: true }), PRICE)).toMatchObject({
      status: 'canceled', currentPeriodEnd: new Date('2026-12-26T10:00:00Z'),
    });
    expect(parseSubscriptionEvent(event({ cancel_at: at('2026-12-20T10:00:00Z') }), PRICE)?.status).toBe('canceled');
    expect(parseSubscriptionEvent(event({ status: 'canceled' }, 'customer.subscription.deleted'), PRICE)).toMatchObject({
      eventType: 'customer.subscription.deleted', status: 'canceled',
    });
  });

  it('reads the period end from the items in API versions that moved it there', () => {
    const parsed = parseSubscriptionEvent(event({ current_period_end: undefined }), PRICE);
    expect(parsed?.currentPeriodEnd).toEqual(new Date('2026-12-26T10:00:00Z'));
  });

  it('leaves the User out when the metadata is missing or not a User ID', () => {
    expect(parseSubscriptionEvent(event({ metadata: null }), PRICE)?.userId).toBeNull();
    expect(parseSubscriptionEvent(event({ metadata: { userId: 'not-a-uuid' } }), PRICE)?.userId).toBeNull();
  });

  it('ignores another price, another event, and an event with no readable time', () => {
    expect(parseSubscriptionEvent(event({ items: { data: [{ price: { id: 'price_other' } }] } }), PRICE)).toBeNull();
    expect(parseSubscriptionEvent(event({}, 'invoice.paid'), PRICE)).toBeNull();
    expect(parseSubscriptionEvent({ ...event(), created: 'not a timestamp' }, PRICE)).toBeNull();
  });

  it('still accepts a canceled subscription whose event names no items', () => {
    expect(parseSubscriptionEvent(event({ items: { data: [] }, status: 'canceled' }, 'customer.subscription.deleted'), PRICE)?.status).toBe('canceled');
  });

  it('parses a cancellation whose price is no longer configured, so a stored subscription can be revoked', () => {
    const parsed = parseSubscriptionEvent(event({ items: { data: [{ price: { id: 'price_rotated_out' } }] }, status: 'canceled' }, 'customer.subscription.deleted'), PRICE);
    expect(parsed).toMatchObject({ status: 'canceled', matchesPrice: false });
  });

  it('maps invoice.payment_failed to the grace state for the subscription it names', () => {
    const paymentFailed = {
      id: 'evt_2',
      type: 'invoice.payment_failed',
      created: at('2026-11-27T10:00:00Z'),
      data: {
        object: {
          id: 'in_1', customer: 'cus_1', subscription: 'sub_1', period_end: at('2026-12-26T10:00:00Z'),
          subscription_details: { metadata: { userId: USER } },
        },
      },
    };
    expect(parseSubscriptionEvent(paymentFailed, PRICE)).toEqual({
      eventType: 'invoice.payment_failed',
      userId: USER,
      stripeCustomerId: 'cus_1',
      stripeSubscriptionId: 'sub_1',
      status: 'past_due',
      currentPeriodEnd: new Date('2026-12-26T10:00:00Z'),
      occurredAt: new Date('2026-11-27T10:00:00Z'),
      matchesPrice: false,
    });
  });

  it('finds the subscription on an invoice in the newer nested shape', () => {
    const parsed = parseSubscriptionEvent({
      id: 'evt_2', type: 'invoice.payment_failed', created: at('2026-11-27T10:00:00Z'),
      data: { object: { id: 'in_1', customer: 'cus_1', parent: { subscription_details: { subscription: 'sub_1', metadata: { userId: USER } } } } },
    }, PRICE);
    expect(parsed).toMatchObject({ stripeSubscriptionId: 'sub_1', userId: USER, status: 'past_due' });
  });
});
