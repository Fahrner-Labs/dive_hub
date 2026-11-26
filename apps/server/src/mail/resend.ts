// Resend (hosted plan §6.1): one JSON POST per message. No SDK; the sender is small enough that a provider seam plus
// fetch keeps it testable, like the FCM sender. Postmark is the documented fallback behind the same interface.
import type { EmailConfig } from '../config.js';
import type { MailMessage, MailProvider } from './mail.js';

export const RESEND_URL = 'https://api.resend.com/emails';

/** A single POST with a text answer; tests pass a stand-in and production falls through to the global fetch. */
export type MailFetch = (url: string, init: {
  method: 'POST';
  headers: Record<string, string>;
  body: string;
}) => Promise<{ status: number; text(): Promise<string> }>;

export interface ResendOptions {
  apiKey: string;
  from: string;
  /** Resend's endpoint; only tests point it elsewhere. */
  url?: string;
  fetch?: MailFetch;
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

/** A short operator-facing reason from Resend's error body; never includes the API key (it is only in the request). */
function reasonOf(body: unknown, text: string): string {
  if (isObject(body) && typeof body.message === 'string' && body.message.length > 0) return body.message;
  return text.slice(0, 200) || 'no response body';
}

export function createResendMailProvider({ apiKey, from, url = RESEND_URL, fetch: injected }: ResendOptions): MailProvider {
  const fetchImpl: MailFetch = injected ?? ((target, init) => fetch(target, init));
  return {
    async sendMail({ to, subject, text }: MailMessage): Promise<void> {
      const response = await fetchImpl(url, {
        method: 'POST',
        headers: { authorization: `Bearer ${apiKey}`, 'content-type': 'application/json' },
        body: JSON.stringify({ from, to: [to], subject, text }),
      });
      const answer = await response.text();
      if (response.status >= 200 && response.status < 300) return;
      throw new Error(`Resend send failed: HTTP ${response.status} ${reasonOf(tryParseJson(answer), answer)}`);
    },
  };
}

/** Choose the configured provider; a new provider (e.g. Postmark) joins this switch without changing any call site. */
export function createMailProvider(
  config: EmailConfig, options: { fetch?: MailFetch; url?: string } = {},
): MailProvider {
  switch (config.provider) {
    case 'resend':
      return createResendMailProvider({
        apiKey: config.apiKey,
        from: config.from,
        ...(options.fetch && { fetch: options.fetch }),
        ...(options.url && { url: options.url }),
      });
    default:
      throw new Error(`no mail provider for ${String((config as { provider: string }).provider)}`);
  }
}
