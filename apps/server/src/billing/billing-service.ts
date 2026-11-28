// Billing (hosted-DiveHub plan §4.5, ADR 0092): the checkout and Customer Portal links a signed-in User asks for, and
// the signed Stripe webhook that is the only writer of entitlements. The webhook is unauthenticated on purpose; its HMAC
// signature, checked over the raw body, stands in for a session (outside Better Auth, amending ADR 0013).
import type { BillingConfig } from '../config.js';
import type { EntitlementService } from '../entitlement/entitlement.js';
import { createStripeClient, type StripeFetch } from './stripe.js';
import { parseSubscriptionEvent, type ParsedSubscription } from './stripe-events.js';
import { verifyStripeSignature } from './stripe-signature.js';

/** A refusal the route can name; anything else from Stripe is an upstream failure the route answers `billing_unavailable`. */
export class BillingError extends Error {
  constructor(readonly code: 'billing_not_subscribed' | 'billing_already_subscribed') {
    super(code === 'billing_already_subscribed'
      ? 'This User already has the Pro plan; manage it in the Stripe Customer Portal'
      : 'This User has no Stripe customer to open the Customer Portal for');
  }
}

/** The bits of a logger the webhook uses; the app's pino logger fits. */
export interface BillingLog {
  warn(detail: object, message: string): void;
}

/** A PostgreSQL error's code, also when drizzle wrapped the driver error as its cause. */
function constraintCode(error: unknown): string | undefined {
  for (let e = error as { code?: unknown; cause?: unknown } | undefined; e; e = e.cause as typeof e) {
    if (typeof e.code === 'string') return e.code;
  }
  return undefined;
}

export function createBillingService(deps: {
  config: BillingConfig;
  entitlements: EntitlementService;
  /** The public origin the hosted pages return to after checkout or the portal. */
  baseUrl: string;
  log?: BillingLog | undefined;
  /** Stripe's API base and transport; only tests pass these. */
  url?: string | undefined;
  fetch?: StripeFetch | undefined;
}) {
  const stripe = createStripeClient({
    secretKey: deps.config.secretKey,
    ...(deps.url && { url: deps.url }),
    ...(deps.fetch && { fetch: deps.fetch }),
  });
  // The web app uses hash routing; this is the Settings' Plan section a User returns to.
  const planUrl = `${deps.baseUrl}/#/settings/plan`;

  /**
   * Who an event applies to. A stored subscription matches on its id alone, so a late event for a User's earlier
   * subscription can never touch the current one. `created` needs the checkout's passthrough (or an already stored
   * subscription, for a replay); an unknown subscription may bootstrap from the passthrough only while the User has no
   * different subscription stored.
   */
  async function userOf(parsed: ParsedSubscription): Promise<string | null> {
    const stored = await deps.entitlements.userOfSubscription(parsed.stripeSubscriptionId);
    // A payment failure only updates a subscription this instance already granted from a lifecycle event; it never
    // creates the row, so a failed first invoice cannot grant the grace state on its own.
    if (parsed.eventType === 'invoice.payment_failed') return stored;
    if (parsed.eventType === 'customer.subscription.created') return parsed.userId ?? stored;
    if (stored) return stored;
    if (!parsed.userId) return null;
    const account = await deps.entitlements.billingAccount(parsed.userId);
    if (account?.subscriptionId && account.subscriptionId !== parsed.stripeSubscriptionId) return null;
    return parsed.userId;
  }

  return {
    /**
     * Handles one webhook delivery. False only when the signature is wrong (the route answers 401); every other answer is
     * true, so Stripe stops retrying an event we cannot use. A database failure still throws, so Stripe retries a write
     * that did not happen.
     */
    async receive(rawBody: Buffer, signature: string | undefined): Promise<boolean> {
      if (!verifyStripeSignature(rawBody, signature, deps.config.webhookSecret)) return false;
      let event: unknown;
      try {
        event = JSON.parse(rawBody.toString('utf8'));
      } catch {
        deps.log?.warn({}, 'a signed Stripe webhook carried no valid JSON; ignored');
        return true;
      }
      const parsed = parseSubscriptionEvent(event, deps.config.priceId);
      if (!parsed) return true;
      const userId = await userOf(parsed);
      if (!userId) {
        deps.log?.warn({ subscription: parsed.stripeSubscriptionId }, 'a Stripe subscription event named no User here; ignored');
        return true;
      }
      try {
        await deps.entitlements.applySubscription({
          userId,
          billingCustomerId: parsed.stripeCustomerId,
          billingSubscriptionId: parsed.stripeSubscriptionId,
          status: parsed.status,
          currentPeriodEnd: parsed.currentPeriodEnd,
          occurredAt: parsed.occurredAt,
        });
      } catch (error) {
        // The User may have been deleted since the checkout (the FK cascades the entitlement away), or the subscription
        // may belong to another row. Neither gets better by retrying, so acknowledge and log (ADR 0092).
        const code = constraintCode(error);
        if (code === '23503' || code === '23505') {
          deps.log?.warn({ subscription: parsed.stripeSubscriptionId, code }, 'a Stripe event could not be applied to a User row; ignored');
          return true;
        }
        throw error;
      }
      return true;
    },

    /** A Stripe Checkout Session link, with this User in the subscription metadata the webhook reads back. */
    async checkout(userId: string): Promise<{ url: string }> {
      // One subscription per User: an entitled User manages or cancels in the portal, and a second checkout would charge
      // them twice (ADR 0092 has no proration logic of our own).
      if (await deps.entitlements.entitled(userId)) throw new BillingError('billing_already_subscribed');
      const account = await deps.entitlements.billingAccount(userId);
      const url = await stripe.createCheckout({
        userId,
        priceId: deps.config.priceId,
        ...(account?.customerId && { customerId: account.customerId }),
        successUrl: planUrl,
        cancelUrl: planUrl,
      });
      return { url };
    },

    /** The authenticated Stripe Customer Portal (invoices, card updates, cancellation); needs a customer from a checkout. */
    async portal(userId: string): Promise<{ url: string }> {
      const account = await deps.entitlements.billingAccount(userId);
      if (!account?.customerId) throw new BillingError('billing_not_subscribed');
      return { url: await stripe.createPortalSession({ customerId: account.customerId, returnUrl: planUrl }) };
    },
  };
}

export type BillingService = ReturnType<typeof createBillingService>;
