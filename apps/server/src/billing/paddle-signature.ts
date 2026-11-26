// Paddle's webhook signature (ADR 0089): the `Paddle-Signature` header is `ts=<unix>;h1=<hex>`, and `h1` is the
// HMAC-SHA256 of `<ts>:<raw body>` with the destination's secret. The raw bytes, never the parsed JSON, are signed.
import { createHmac, timingSafeEqual } from 'node:crypto';

/** The header's parts, or null when it is missing or not the shape Paddle documents. */
function signatureParts(header: string | undefined): { ts: string; signatures: string[] } | null {
  if (!header) return null;
  let ts: string | undefined;
  const signatures: string[] = [];
  for (const part of header.split(';')) {
    const at = part.indexOf('=');
    if (at < 0) continue;
    const key = part.slice(0, at).trim();
    const value = part.slice(at + 1).trim();
    if (key === 'ts' && value) ts = value;
    // Paddle may send more than one h1 while a secret is rotated; any match accepts the event.
    if (key === 'h1' && value) signatures.push(value);
  }
  return ts && signatures.length > 0 ? { ts, signatures } : null;
}

/**
 * Whether `header` really signs `rawBody` with `secret`. Timing-safe, and tolerant of the extra `h1` values Paddle sends
 * during a secret rotation. The timestamp is part of the signed payload, so it cannot be changed; we do not reject an old
 * one, because replays are already harmless (the upsert is idempotent and ignores older event times, ADR 0089).
 */
export function verifyPaddleSignature(rawBody: Buffer, header: string | undefined, secret: string): boolean {
  const parts = signatureParts(header);
  if (!parts) return false;
  const expected = createHmac('sha256', secret).update(`${parts.ts}:`).update(rawBody).digest();
  for (const signature of parts.signatures) {
    const given = Buffer.from(signature, 'hex');
    if (given.length === expected.length && timingSafeEqual(given, expected)) return true;
  }
  return false;
}
