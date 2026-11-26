// Paddle's two pure pieces (hosted-DiveHub plan §4.5–4.6, ADR 0089): the webhook signature over the raw body, and the
// subscription event mapped onto the entitlement state machine. No database here; the webhook's write is tested against
// PostgreSQL in billing-routes.test.ts.
import { createHmac } from 'node:crypto';
import { describe, expect, it } from 'vitest';
import { paddleApiUrl, PADDLE_API_URL, PADDLE_SANDBOX_API_URL } from '../src/billing/paddle.js';
import { parseSubscriptionEvent } from '../src/billing/paddle-events.js';
import { verifyPaddleSignature } from '../src/billing/paddle-signature.js';

const SECRET = 'pdl_ntfset_test_secret';
const USER = '01900000-0000-7000-8000-000000000001';
const PRICE = 'pri_monthly';

function sign(body: string, secret = SECRET, ts = '1764151200'): string {
  const h1 = createHmac('sha256', secret).update(`${ts}:${body}`).digest('hex');
  return `ts=${ts};h1=${h1}`;
}

describe('the Paddle webhook signature', () => {
  const body = JSON.stringify({ event_type: 'subscription.created', data: { id: 'sub_1' } });

  it('accepts the header Paddle sends for exactly these bytes', () => {
    expect(verifyPaddleSignature(Buffer.from(body), sign(body), SECRET)).toBe(true);
  });

  it('rejects a changed body, a wrong secret, and a header it cannot read', () => {
    const header = sign(body);
    expect(verifyPaddleSignature(Buffer.from(body.replace('sub_1', 'sub_2')), header, SECRET)).toBe(false);
    expect(verifyPaddleSignature(Buffer.from(body), sign(body, 'another-secret'), SECRET)).toBe(false);
    expect(verifyPaddleSignature(Buffer.from(body), undefined, SECRET)).toBe(false);
    expect(verifyPaddleSignature(Buffer.from(body), 'garbage', SECRET)).toBe(false);
    expect(verifyPaddleSignature(Buffer.from(body), 'ts=1;h1=zz', SECRET)).toBe(false);
  });

  it('accepts any matching h1 while Paddle rotates a secret', () => {
    const good = createHmac('sha256', SECRET).update(`1764151200:${body}`).digest('hex');
    expect(verifyPaddleSignature(Buffer.from(body), `ts=1764151200;h1=00;h1=${good}`, SECRET)).toBe(true);
  });

  it('reads a sandbox key as sandbox and a live key as production', () => {
    expect(paddleApiUrl('pdl_sdbx_apikey_01')).toBe(PADDLE_SANDBOX_API_URL);
    expect(paddleApiUrl('pdl_live_apikey_01')).toBe(PADDLE_API_URL);
  });
});

describe('a Paddle subscription event', () => {
  const event = (overrides: Record<string, unknown> = {}, type = 'subscription.created') => ({
    event_id: 'evt_1',
    event_type: type,
    occurred_at: '2026-11-26T10:00:00.123456789Z',
    data: {
      id: 'sub_1',
      customer_id: 'ctm_1',
      status: 'active',
      custom_data: { userId: USER },
      current_billing_period: { ends_at: '2026-12-26T10:00:00Z' },
      items: [{ price: { id: PRICE }, quantity: 1 }],
      ...overrides,
    },
  });

  it('maps a created active subscription with our price and the checkout passthrough', () => {
    expect(parseSubscriptionEvent(event(), [PRICE])).toEqual({
      eventType: 'subscription.created',
      userId: USER,
      paddleCustomerId: 'ctm_1',
      paddleSubscriptionId: 'sub_1',
      status: 'active',
      currentPeriodEnd: new Date('2026-12-26T10:00:00Z'),
      occurredAt: new Date('2026-11-26T10:00:00.123Z'),
      matchesPrice: true,
    });
  });

  it('takes a trial as active and a paused subscription as canceled (paid until the period ends)', () => {
    expect(parseSubscriptionEvent(event({ status: 'trialing' }), [PRICE])?.status).toBe('active');
    expect(parseSubscriptionEvent(event({ status: 'paused' }, 'subscription.updated'), [PRICE])?.status).toBe('canceled');
  });

  it('keeps past_due, the grace window', () => {
    expect(parseSubscriptionEvent(event({ status: 'past_due' }, 'subscription.updated'), [PRICE])?.status).toBe('past_due');
  });

  it('leaves the User out when the passthrough is missing or not a User ID', () => {
    expect(parseSubscriptionEvent(event({ custom_data: null }), [PRICE])?.userId).toBeNull();
    expect(parseSubscriptionEvent(event({ custom_data: { userId: 'not-a-uuid' } }), [PRICE])?.userId).toBeNull();
  });

  it('ignores another price, another event, and an event with no readable time', () => {
    expect(parseSubscriptionEvent(event({ items: [{ price: { id: 'pri_other' } }] }), [PRICE])).toBeNull();
    expect(parseSubscriptionEvent(event({}, 'transaction.completed'), [PRICE])).toBeNull();
    expect(parseSubscriptionEvent({ ...event(), occurred_at: 'not a date' }, [PRICE])).toBeNull();
  });

  it('still accepts a canceled subscription whose event names no items', () => {
    expect(parseSubscriptionEvent(event({ items: [], status: 'canceled' }, 'subscription.canceled'), [PRICE])?.status).toBe('canceled');
  });

  it('parses a cancellation whose price is no longer configured, so a stored subscription can be revoked', () => {
    const parsed = parseSubscriptionEvent(event({ items: [{ price: { id: 'pri_rotated_out' } }], status: 'canceled' }, 'subscription.canceled'), [PRICE]);
    expect(parsed).toMatchObject({ status: 'canceled', matchesPrice: false });
  });
});
