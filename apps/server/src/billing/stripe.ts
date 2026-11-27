// Stripe's billing API, the little of it Dive Hub needs (hosted-DiveHub plan §4.5, ADR 0092): create a Checkout Session
// and hand back its URL, and open a Customer Portal session. No SDK, like Resend and FCM: a provider seam plus fetch
// keeps it testable, and Stripe owns the card form and the portal.
export const STRIPE_API_URL = 'https://api.stripe.com';

/** A hung Stripe must not hold an HTTP request open forever (a checkout click or the portal link). */
export const STRIPE_TIMEOUT_MS = 10_000;

/** A single form-encoded request with a text answer; tests pass a stand-in and production falls through to the global fetch. */
export type StripeFetch = (url: string, init: {
  method: 'POST';
  headers: Record<string, string>;
  body: string;
  signal?: AbortSignal;
}) => Promise<{ status: number; text(): Promise<string> }>;

export interface StripeOptions {
  secretKey: string;
  /** Stripe's API base; only tests point it elsewhere (test and live keys share one endpoint). */
  url?: string;
  fetch?: StripeFetch;
}

const isObject = (value: unknown): value is Record<string, unknown> =>
  typeof value === 'object' && value !== null && !Array.isArray(value);

function tryParseJson(text: string): unknown {
  try {
    return JSON.parse(text) as unknown;
  } catch {
    return undefined;
  }
}

/** A short operator-facing reason from Stripe's error body; never includes the secret key (it is only in the request). */
function reasonOf(body: unknown, text: string): string {
  if (isObject(body) && isObject(body.error) && typeof body.error.message === 'string' && body.error.message.length > 0) {
    return body.error.message;
  }
  return text.slice(0, 200) || 'no response body';
}

/** A string at `path` in a parsed answer, or undefined when the answer is not the shape Stripe documents. */
function stringAt(value: unknown, ...path: string[]): string | undefined {
  let node: unknown = value;
  for (const key of path) {
    if (!isObject(node)) return undefined;
    node = node[key];
  }
  return typeof node === 'string' && node.length > 0 ? node : undefined;
}

export interface StripeCheckoutInput {
  /** The signed-in User, carried on the subscription's metadata so a webhook can find them. */
  userId: string;
  priceId: string;
  /** The Stripe customer from an earlier checkout, so a resubscribe reuses one customer per User. */
  customerId?: string;
  successUrl: string;
  cancelUrl: string;
}

export interface StripeClient {
  /** A Stripe-hosted Checkout Session URL for this User and price, with automatic tax on. */
  createCheckout(input: StripeCheckoutInput): Promise<string>;
  /** The authenticated Customer Portal URL (invoices, card updates, cancellation) for an existing customer. */
  createPortalSession(input: { customerId: string; returnUrl: string }): Promise<string>;
}

export function createStripeClient({ secretKey, url, fetch: injected }: StripeOptions): StripeClient {
  const base = url ?? STRIPE_API_URL;
  const fetchImpl: StripeFetch = injected ?? ((target, init) => fetch(target, init));

  async function post(path: string, params: URLSearchParams): Promise<unknown> {
    const response = await fetchImpl(`${base}${path}`, {
      method: 'POST',
      headers: {
        authorization: `Bearer ${secretKey}`,
        'content-type': 'application/x-www-form-urlencoded',
      },
      body: params.toString(),
      signal: AbortSignal.timeout(STRIPE_TIMEOUT_MS),
    });
    const answer = await response.text();
    if (response.status < 200 || response.status >= 300) {
      throw new Error(`Stripe ${path} failed: HTTP ${response.status} ${reasonOf(tryParseJson(answer), answer)}`);
    }
    return tryParseJson(answer);
  }

  return {
    async createCheckout({ userId, priceId, customerId, successUrl, cancelUrl }: StripeCheckoutInput): Promise<string> {
      // `subscription_data[metadata][userId]` is copied onto the subscription Stripe creates, which is how a later
      // webhook finds the User (plan §4.5). `client_reference_id` also names the User on the session for operators.
      const params = new URLSearchParams({
        mode: 'subscription',
        'line_items[0][price]': priceId,
        'line_items[0][quantity]': '1',
        client_reference_id: userId,
        'subscription_data[metadata][userId]': userId,
        // Stripe Tax (ADR 0092): the dashboard's threshold monitoring sees clean data; Dive Hub builds no tax logic.
        'automatic_tax[enabled]': 'true',
        billing_address_collection: 'required',
        success_url: successUrl,
        cancel_url: cancelUrl,
      });
      // An existing customer gets the address collected at checkout written back, which Stripe Tax needs.
      if (customerId) {
        params.set('customer', customerId);
        params.set('customer_update[address]', 'auto');
      }
      const answer = await post('/v1/checkout/sessions', params);
      const checkout = stringAt(answer, 'url');
      if (!checkout) throw new Error('Stripe created no Checkout Session URL');
      return checkout;
    },

    async createPortalSession({ customerId, returnUrl }: { customerId: string; returnUrl: string }): Promise<string> {
      const answer = await post('/v1/billing_portal/sessions', new URLSearchParams({
        customer: customerId,
        return_url: returnUrl,
      }));
      const portal = stringAt(answer, 'url');
      if (!portal) throw new Error('Stripe opened no Customer Portal URL for this customer');
      return portal;
    },
  };
}
