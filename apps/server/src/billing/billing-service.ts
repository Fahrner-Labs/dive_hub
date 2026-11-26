// Billing (hosted-DiveHub plan §4.5, ADR 0089): the checkout and customer-portal links a signed-in User asks for, and the
// signed Paddle webhook that is the only writer of entitlements. The webhook is unauthenticated on purpose; its HMAC
// signature, checked over the raw body, stands in for a session (outside Better Auth, amending ADR 0013).
import type { BillingConfig } from '../config.js';
import type { EntitlementService } from '../entitlement/entitlement.js';
import { createPaddleClient, type PaddleFetch } from './paddle.js';
import { parseSubscriptionEvent, type ParsedSubscription } from './paddle-events.js';
import { verifyPaddleSignature } from './paddle-signature.js';

/** A refusal the route can name; anything else from Paddle is an upstream failure the route answers `billing_unavailable`. */
export class BillingError extends Error {
  constructor(readonly code: 'billing_not_subscribed' | 'billing_already_subscribed') {
    super(code === 'billing_already_subscribed'
      ? 'This User already has the Pro plan; manage it in the Paddle portal'
      : 'This User has no Paddle customer to open the portal for');
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
  log?: BillingLog | undefined;
  /** Paddle's API base and transport; only tests pass these. */
  url?: string | undefined;
  fetch?: PaddleFetch | undefined;
}) {
  if (deps.config.priceIds.length === 0) throw new Error('billing is configured without a Paddle price ID');
  const paddle = createPaddleClient({
    apiKey: deps.config.apiKey,
    ...(deps.url && { url: deps.url }),
    ...(deps.fetch && { fetch: deps.fetch }),
  });

  /**
   * Who an event applies to. A stored subscription matches on its id alone, so a late event for a User's earlier
   * subscription can never touch the current one. `created` needs the checkout's passthrough (or an already stored
   * subscription, for a replay); an unknown subscription may bootstrap from the passthrough only while the User has no
   * different subscription stored.
   */
  async function userOf(parsed: ParsedSubscription): Promise<string | null> {
    const stored = await deps.entitlements.userOfSubscription(parsed.paddleSubscriptionId);
    if (parsed.eventType === 'subscription.created') return parsed.userId ?? stored;
    if (stored) return stored;
    if (!parsed.userId) return null;
    const account = await deps.entitlements.paddleAccount(parsed.userId);
    if (account?.subscriptionId && account.subscriptionId !== parsed.paddleSubscriptionId) return null;
    return parsed.userId;
  }

  return {
    /**
     * Handles one webhook delivery. False only when the signature is wrong (the route answers 401); every other answer is
     * true, so Paddle stops retrying an event we cannot use. A database failure still throws, so Paddle retries a write
     * that did not happen.
     */
    async receive(rawBody: Buffer, signature: string | undefined): Promise<boolean> {
      if (!verifyPaddleSignature(rawBody, signature, deps.config.webhookSecret)) return false;
      let event: unknown;
      try {
        event = JSON.parse(rawBody.toString('utf8'));
      } catch {
        deps.log?.warn({}, 'a signed Paddle webhook carried no valid JSON; ignored');
        return true;
      }
      const parsed = parseSubscriptionEvent(event, deps.config.priceIds);
      if (!parsed) return true;
      const userId = await userOf(parsed);
      if (!userId) {
        deps.log?.warn({ subscription: parsed.paddleSubscriptionId }, 'a Paddle subscription event named no User here; ignored');
        return true;
      }
      try {
        await deps.entitlements.applySubscription({ ...parsed, userId });
      } catch (error) {
        // The User may have been deleted since the checkout (the FK cascades the entitlement away), or the subscription
        // may belong to another row. Neither gets better by retrying, so acknowledge and log (ADR 0089).
        const code = constraintCode(error);
        if (code === '23503' || code === '23505') {
          deps.log?.warn({ subscription: parsed.paddleSubscriptionId, code }, 'a Paddle event could not be applied to a User row; ignored');
          return true;
        }
        throw error;
      }
      return true;
    },

    /** A signed Paddle checkout link, with this User in the passthrough the webhook reads back. */
    async checkout(userId: string): Promise<{ url: string }> {
      // One subscription per User: an entitled User manages or cancels in the portal, and a second checkout would charge
      // them twice (ADR 0089 has no proration logic of our own).
      if (await deps.entitlements.entitled(userId)) throw new BillingError('billing_already_subscribed');
      const account = await deps.entitlements.paddleAccount(userId);
      const url = await paddle.createCheckout({
        userId,
        // The first configured price is the plan checkout offers; any of them counts as Pro in the webhook.
        priceId: deps.config.priceIds[0]!,
        ...(account?.customerId && { customerId: account.customerId }),
      });
      return { url };
    },

    /** The authenticated Paddle customer portal (invoices, card updates, cancellation); needs a customer from a checkout. */
    async portal(userId: string): Promise<{ url: string }> {
      const account = await deps.entitlements.paddleAccount(userId);
      if (!account?.customerId) throw new BillingError('billing_not_subscribed');
      return { url: await paddle.createPortalSession(account.customerId) };
    },
  };
}

export type BillingService = ReturnType<typeof createBillingService>;
