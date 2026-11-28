// Stripe's webhook signature (ADR 0092): the `Stripe-Signature` header is `t=<unix>,v1=<hex>[,v1=<hex>]`, and `v1` is
// the HMAC-SHA256 of `<t>.<raw body>` with the endpoint's signing secret. The raw bytes, never the parsed JSON, are
// signed. Stripe sends several v1 values while an endpoint secret is rotated; any match accepts the event.
import { createHmac, timingSafeEqual } from 'node:crypto';

/** The header's timestamp and signatures, or null when it is missing or not the shape Stripe documents. */
function signatureParts(header: string | undefined): { timestamp: string; signatures: string[] } | null {
  if (!header) return null;
  let timestamp: string | undefined;
  const signatures: string[] = [];
  for (const part of header.split(',')) {
    const at = part.indexOf('=');
    if (at < 0) continue;
    const key = part.slice(0, at).trim();
    const value = part.slice(at + 1).trim();
    if (key === 't' && value) timestamp = value;
    if (key === 'v1' && value) signatures.push(value);
  }
  return timestamp && signatures.length > 0 ? { timestamp, signatures } : null;
}

/**
 * Whether `header` really signs `rawBody` with `secret`. Timing-safe. The timestamp is part of the signed payload, so it
 * cannot be changed. An old timestamp is not rejected: replays are harmless because the entitlement upsert is idempotent
 * and ignores an event older than the stored one (ADR 0092), so the window would only add a clock dependency.
 */
export function verifyStripeSignature(rawBody: Buffer, header: string | undefined, secret: string): boolean {
  const parts = signatureParts(header);
  if (!parts) return false;
  const expected = createHmac('sha256', secret).update(`${parts.timestamp}.`).update(rawBody).digest();
  for (const signature of parts.signatures) {
    const given = Buffer.from(signature, 'hex');
    if (given.length === expected.length && timingSafeEqual(given, expected)) return true;
  }
  return false;
}
