// Paddle's billing API, the little of it Dive Hub needs (hosted-DiveHub plan §4.5, ADR 0089): create a transaction and
// hand back its checkout link, and open a customer-portal session. No SDK, like Resend and FCM: a provider seam plus
// fetch keeps it testable, and Paddle owns the money UI.
export const PADDLE_API_URL = 'https://api.paddle.com';
export const PADDLE_SANDBOX_API_URL = 'https://sandbox-api.paddle.com';

/** Sandbox API keys contain `_sdbx`; the same image then talks to the sandbox, live keys to production (ADR 0089). */
export function paddleApiUrl(apiKey: string): string {
  return apiKey.includes('_sdbx') ? PADDLE_SANDBOX_API_URL : PADDLE_API_URL;
}

/** A hung Paddle must not hold an HTTP request open forever (a checkout click or the portal link). */
export const PADDLE_TIMEOUT_MS = 10_000;

/** A single JSON request with a text answer; tests pass a stand-in and production falls through to the global fetch. */
export type PaddleFetch = (url: string, init: {
  method: 'POST';
  headers: Record<string, string>;
  body: string;
  signal?: AbortSignal;
}) => Promise<{ status: number; text(): Promise<string> }>;

export interface PaddleOptions {
  apiKey: string;
  /** Paddle's API base; only tests point it elsewhere (production derives sandbox/live from the key). */
  url?: string;
  fetch?: PaddleFetch;
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

/** A short operator-facing reason from Paddle's error body; never includes the API key (it is only in the request). */
function reasonOf(body: unknown, text: string): string {
  if (isObject(body) && isObject(body.error) && typeof body.error.detail === 'string' && body.error.detail.length > 0) {
    return body.error.detail;
  }
  return text.slice(0, 200) || 'no response body';
}

/** A string at `path` in a parsed answer, or undefined when the answer is not the shape Paddle documents. */
function stringAt(value: unknown, ...path: string[]): string | undefined {
  let node: unknown = value;
  for (const key of path) {
    if (!isObject(node)) return undefined;
    node = node[key];
  }
  return typeof node === 'string' && node.length > 0 ? node : undefined;
}

export interface PaddleClient {
  /** A Paddle-hosted checkout link for this User and price; the User travels in `custom_data` to the subscription. */
  createCheckout(input: { userId: string; priceId: string; customerId?: string }): Promise<string>;
  /** The authenticated customer-portal URL (invoices, card updates, cancellation) for an existing customer. */
  createPortalSession(customerId: string): Promise<string>;
}

export function createPaddleClient({ apiKey, url, fetch: injected }: PaddleOptions): PaddleClient {
  const base = url ?? paddleApiUrl(apiKey);
  const fetchImpl: PaddleFetch = injected ?? ((target, init) => fetch(target, init));

  async function post(path: string, body: object): Promise<unknown> {
    const response = await fetchImpl(`${base}${path}`, {
      method: 'POST',
      headers: { authorization: `Bearer ${apiKey}`, 'content-type': 'application/json' },
      body: JSON.stringify(body),
      signal: AbortSignal.timeout(PADDLE_TIMEOUT_MS),
    });
    const answer = await response.text();
    if (response.status < 200 || response.status >= 300) {
      throw new Error(`Paddle ${path} failed: HTTP ${response.status} ${reasonOf(tryParseJson(answer), answer)}`);
    }
    return tryParseJson(answer);
  }

  return {
    async createCheckout({ userId, priceId, customerId }: { userId: string; priceId: string; customerId?: string }): Promise<string> {
      // The transaction's custom_data is copied onto the subscription Paddle creates, which is how a later webhook finds
      // the User (plan §4.5). Reusing a known customer keeps one Paddle customer per User across resubscribes.
      const answer = await post('/transactions', {
        items: [{ price_id: priceId, quantity: 1 }],
        custom_data: { userId },
        ...(customerId && { customer_id: customerId }),
      });
      const checkout = stringAt(answer, 'data', 'checkout', 'url');
      if (!checkout) throw new Error('Paddle created no checkout URL for this transaction');
      return checkout;
    },

    async createPortalSession(customerId: string): Promise<string> {
      const answer = await post(`/customers/${encodeURIComponent(customerId)}/portal-sessions`, {});
      const portal = stringAt(answer, 'data', 'urls', 'general', 'overview');
      if (!portal) throw new Error('Paddle opened no customer-portal URL for this customer');
      return portal;
    },
  };
}
