// The Stripe subscription events Dive Hub acts on (hosted-DiveHub plan §4.6, ADR 0092), mapped from Stripe's vocabulary
// to the entitlement state machine. Pure: the route has already checked the signature and the caller writes the row; an
// event it cannot use is simply null (the webhook still answers 200).
import type { EntitlementStatus } from '../entitlement/entitlement.js';

/**
 * Stripe's subscription statuses, settled onto the states the entitlement gate understands. A trial runs the paid
 * features; `past_due` and `unpaid` are the grace window in which Stripe retries the card; the rest grant no new access.
 */
const STATUS: Record<string, EntitlementStatus> = {
  active: 'active',
  trialing: 'active',
  past_due: 'past_due',
  unpaid: 'past_due',
  canceled: 'canceled',
  incomplete: 'canceled',
  incomplete_expired: 'canceled',
  paused: 'canceled',
};

const UUID = /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i;

const isObject = (value: unknown): value is Record<string, unknown> =>
  typeof value === 'object' && value !== null && !Array.isArray(value);
const stringOf = (value: unknown): string | undefined => (typeof value === 'string' && value.length > 0 ? value : undefined);

/** A Stripe timestamp (Unix seconds) as a Date; null when absent or unreadable. */
function unixDate(value: unknown): Date | null {
  if (typeof value !== 'number' || !Number.isFinite(value)) return null;
  const date = new Date(value * 1000);
  return Number.isNaN(date.getTime()) ? null : date;
}

/** The subscription's own period end, or the latest one on its items in API versions that moved the fields there. */
function periodEndOf(subscription: Record<string, unknown>): Date | null {
  const direct = unixDate(subscription.current_period_end);
  if (direct) return direct;
  if (isObject(subscription.current_period) && unixDate(subscription.current_period.end)) {
    return unixDate(subscription.current_period.end);
  }
  const items = isObject(subscription.items) && Array.isArray(subscription.items.data) ? subscription.items.data : [];
  let latest: Date | null = null;
  for (const item of items) {
    if (!isObject(item)) continue;
    const end = unixDate(item.current_period_end);
    if (end && (!latest || end > latest)) latest = end;
  }
  return latest;
}

/** A User id from `metadata.userId`, the passthrough our checkout sets on the subscription. */
function userIdOf(metadataOwner: Record<string, unknown>): string | null {
  const metadata = isObject(metadataOwner.metadata) ? metadataOwner.metadata : null;
  const id = metadata ? stringOf(metadata.userId) : undefined;
  return id && UUID.test(id) ? id : null;
}

/** The subscription an invoice's payment_failed belongs to; Stripe's old and new invoice shapes carry it differently. */
function invoiceSubscriptionId(invoice: Record<string, unknown>): string | undefined {
  const direct = stringOf(invoice.subscription);
  if (direct) return direct;
  if (!isObject(invoice.parent)) return undefined;
  const details = isObject(invoice.parent.subscription_details) ? invoice.parent.subscription_details : null;
  return details ? stringOf(details.subscription) : undefined;
}

/** The subscription metadata an invoice carries, so a payment_failed event can still name the User. */
function invoiceMetadataOwner(invoice: Record<string, unknown>): Record<string, unknown> {
  if (isObject(invoice.parent) && isObject(invoice.parent.subscription_details)) return invoice.parent.subscription_details;
  return invoice.subscription_details && isObject(invoice.subscription_details) ? invoice.subscription_details : {};
}

/** The paid period an invoice covers, when Stripe includes it. */
function invoicePeriodEnd(invoice: Record<string, unknown>): Date | null {
  const direct = unixDate(invoice.period_end);
  if (direct) return direct;
  const lines = isObject(invoice.lines) && Array.isArray(invoice.lines.data) ? invoice.lines.data : [];
  for (const line of lines) {
    if (!isObject(line) || !isObject(line.period)) continue;
    const end = unixDate(line.period.end);
    if (end) return end;
  }
  return null;
}

/** One subscription state to apply, plus the User from the checkout's passthrough when the event carries it. */
export interface ParsedSubscription {
  eventType: 'customer.subscription.created' | 'customer.subscription.updated' | 'customer.subscription.deleted'
    | 'invoice.payment_failed';
  userId: string | null;
  stripeCustomerId: string;
  stripeSubscriptionId: string;
  status: EntitlementStatus;
  /** The period that was paid for; `canceled` keeps it so the User stays entitled until it ends. */
  currentPeriodEnd: Date | null;
  occurredAt: Date;
  /**
   * Whether the subscription's items include this instance's price. `created` requires it (grant only our plan);
   * `updated`/`deleted`/`payment_failed` do not, because a stored subscription must still be revoked after the operator
   * changes `DIVEHUB_STRIPE_PRICE_ID`.
   */
  matchesPrice: boolean;
}

type LifecycleEvent = 'customer.subscription.created' | 'customer.subscription.updated' | 'customer.subscription.deleted';

function parsePaymentFailed(invoice: Record<string, unknown>, occurredAt: Date): ParsedSubscription | null {
  const subscriptionId = invoiceSubscriptionId(invoice);
  const customerId = stringOf(invoice.customer);
  if (!subscriptionId || !customerId) return null;
  return {
    eventType: 'invoice.payment_failed',
    userId: userIdOf(invoiceMetadataOwner(invoice)),
    stripeCustomerId: customerId,
    stripeSubscriptionId: subscriptionId,
    status: 'past_due',
    currentPeriodEnd: invoicePeriodEnd(invoice),
    occurredAt,
    matchesPrice: false,
  };
}

function parseLifecycle(
  type: LifecycleEvent, subscription: Record<string, unknown>, occurredAt: Date, priceId: string,
): ParsedSubscription | null {
  const subscriptionId = stringOf(subscription.id);
  const customerId = stringOf(subscription.customer);
  const stripeStatus = stringOf(subscription.status) ?? '';
  const status = STATUS[stripeStatus];
  if (!subscriptionId || !customerId || !status) return null;

  const items = isObject(subscription.items) && Array.isArray(subscription.items.data) ? subscription.items.data : [];
  const matchesPrice = items.some((item) =>
    isObject(item) && isObject(item.price) && stringOf(item.price.id) === priceId);
  // A new subscription that does not include our price is not the Pro plan, so it grants nothing. An update or deletion
  // still parses: if the subscription is already stored it must be applied (a price change must not leave a User
  // entitled), and the caller decides whether it is ours by the stored subscription id.
  if (type === 'customer.subscription.created' && !matchesPrice) return null;

  // A subscription canceled at the period end still reports `active` until the period ends; show it as canceled so the
  // client says "paid until" instead of "renews automatically". A resubscribe flips it back.
  const scheduledToCancel = subscription.cancel_at_period_end === true
    || (typeof subscription.cancel_at === 'number' && subscription.cancel_at > 0);
  const effectiveStatus: EntitlementStatus = type === 'customer.subscription.deleted' || (status === 'active' && scheduledToCancel)
    ? 'canceled'
    : status;
  // An incomplete subscription has not been paid for (the checkout was abandoned or its first charge failed): it must
  // grant nothing, even if Stripe put a future period end on it.
  const uninvoiced = stripeStatus === 'incomplete' || stripeStatus === 'incomplete_expired';

  return {
    eventType: type,
    userId: userIdOf(subscription),
    stripeCustomerId: customerId,
    stripeSubscriptionId: subscriptionId,
    status: effectiveStatus,
    currentPeriodEnd: uninvoiced ? null : periodEndOf(subscription),
    occurredAt,
    matchesPrice,
  };
}

/**
 * Reads a `customer.subscription.created`, `customer.subscription.updated`, `customer.subscription.deleted` or
 * `invoice.payment_failed` event. Null for any other event, an event with no usable time, or a new subscription that is
 * not this instance's price: those are not ours to grant. `metadata.userId` is the passthrough our checkout sets; a
 * later event without it is resolved by the caller from the stored subscription id.
 */
export function parseSubscriptionEvent(event: unknown, priceId: string): ParsedSubscription | null {
  if (!isObject(event)) return null;
  const type = event.type;
  const lifecycle = type === 'customer.subscription.created' || type === 'customer.subscription.updated'
    || type === 'customer.subscription.deleted';
  if (!lifecycle && type !== 'invoice.payment_failed') return null;
  const occurredAt = unixDate(event.created);
  if (!occurredAt) return null;
  if (!isObject(event.data) || !isObject(event.data.object)) return null;

  const object = event.data.object;
  if (type === 'invoice.payment_failed') return parsePaymentFailed(object, occurredAt);
  return parseLifecycle(type, object, occurredAt, priceId);
}
