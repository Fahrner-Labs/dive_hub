// The three Paddle subscription events Dive Hub acts on (hosted-DiveHub plan §4.6, ADR 0089), mapped from Paddle's
// vocabulary to the entitlement state machine. Pure: the route has already checked the signature and the caller writes
// the row; an event it cannot use is simply null (the webhook still answers 200).
import type { EntitlementStatus } from '../entitlement/entitlement.js';

/** Paddle's subscription statuses, settled onto the states the entitlement gate understands. */
const STATUS: Record<string, EntitlementStatus> = {
  active: 'active',
  // A trial is a subscription that works without payment yet: the paid features run during it.
  trialing: 'active',
  // The card failed and Paddle retries: the grace window keeps automation running.
  past_due: 'past_due',
  // Canceled at the period end; `current_period_end` keeps access until then.
  canceled: 'canceled',
  // Paused means Paddle stopped billing but the subscription was not canceled. Treat it like a canceled one: keep the
  // paid period that was paid for, grant nothing after it.
  paused: 'canceled',
};

const UUID = /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i;

const isObject = (value: unknown): value is Record<string, unknown> =>
  typeof value === 'object' && value !== null && !Array.isArray(value);
const stringOf = (value: unknown): string | undefined => (typeof value === 'string' && value.length > 0 ? value : undefined);

/** A Paddle timestamp (RFC 3339, sometimes with nanoseconds) as a Date; null when absent or unreadable. */
function dateOf(value: unknown): Date | null {
  if (typeof value !== 'string') return null;
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? null : date;
}

/** One subscription state to apply, plus the User from the checkout's passthrough when the event carries it. */
export interface ParsedSubscription {
  eventType: 'subscription.created' | 'subscription.updated' | 'subscription.canceled';
  userId: string | null;
  paddleCustomerId: string;
  paddleSubscriptionId: string;
  status: EntitlementStatus;
  currentPeriodEnd: Date | null;
  occurredAt: Date;
  /**
   * Whether the subscription's items include one of this instance's prices. `created` requires it (grant only our plan);
   * `updated`/`canceled` do not, because a stored subscription must still be revoked after the operator changes
   * `DIVEHUB_PADDLE_PRICE_IDS`.
   */
  matchesPrice: boolean;
}

/**
 * Reads a `subscription.created`, `subscription.updated` or `subscription.canceled` event. Null for any other event, an
 * event with no usable time, or a `created` subscription that is not one of this instance's prices: those are not ours
 * to grant. `data.custom_data.userId` is the passthrough our checkout sets; a later event without it is resolved by the
 * caller from the stored subscription id.
 */
export function parseSubscriptionEvent(event: unknown, priceIds: readonly string[]): ParsedSubscription | null {
  if (!isObject(event)) return null;
  const type = event.event_type;
  if (type !== 'subscription.created' && type !== 'subscription.updated' && type !== 'subscription.canceled') return null;
  const occurredAt = dateOf(event.occurred_at);
  if (!occurredAt) return null;
  if (!isObject(event.data)) return null;

  const data = event.data;
  const subscriptionId = stringOf(data.id);
  const customerId = stringOf(data.customer_id);
  const status = STATUS[stringOf(data.status) ?? ''];
  if (!subscriptionId || !customerId || !status) return null;

  const items = Array.isArray(data.items) ? data.items : [];
  const matchesPrice = items.some((item) => isObject(item) && isObject(item.price) && priceIds.includes(stringOf(item.price.id) ?? ''));
  // A new subscription that does not include one of our prices is not the Pro plan, so it grants nothing. An update or
  // cancellation still parses: if the subscription is already stored it must be applied (a price rotation must not leave
  // a User entitled), and the caller decides whether it is ours by the stored subscription id.
  if (type === 'subscription.created' && !matchesPrice) return null;

  const custom = isObject(data.custom_data) ? data.custom_data : null;
  const userId = custom && typeof custom.userId === 'string' && UUID.test(custom.userId) ? custom.userId : null;
  const currentPeriodEnd = isObject(data.current_billing_period) ? dateOf(data.current_billing_period.ends_at) : null;

  return {
    eventType: type, userId, paddleCustomerId: customerId, paddleSubscriptionId: subscriptionId, status, currentPeriodEnd,
    occurredAt, matchesPrice,
  };
}
