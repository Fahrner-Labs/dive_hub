// The Android push provider (ADR 0085): FCM's HTTP v1 API, with an OAuth2 access token minted from the service
// account by a plain RS256 JWT. No Google SDK: one form post and one message post are all the sender needs.
import { createPrivateKey, createSign } from 'node:crypto';
import type { FcmServiceAccount } from '../config.js';
import { PushTokenGoneError, type PushHint, type PushProvider, type PushTarget } from './push-provider.js';

const DEFAULT_TOKEN_URI = 'https://oauth2.googleapis.com/token';
const FCM_SCOPE = 'https://www.googleapis.com/auth/firebase.messaging';
/** Renew the access token this long before Google expires it, so a send never races the expiry. */
const REFRESH_MARGIN_MS = 60_000;

/** A single POST with a text answer; tests pass a stand-in and production falls through to the global fetch. */
export type Fetch = (url: string, init: {
  method: 'POST';
  headers: Record<string, string>;
  body: string;
}) => Promise<{ status: number; text(): Promise<string> }>;

export interface FcmProviderOptions {
  serviceAccount: FcmServiceAccount;
  fetch?: Fetch;
  /** The clock for token expiry; tests pass a settled one. */
  now?: () => number;
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

/** `privateKey` comes from JSON (escaped newlines) or a mounted PEM file (real ones); accept both. */
const pemOf = (privateKey: string) => (privateKey.includes('\\n') ? privateKey.replace(/\\n/g, '\n') : privateKey);

/** The signed `{header}.{claims}` assertion for Google's OAuth2 token endpoint. */
function signAssertion(claims: Record<string, unknown>, privateKey: string): string {
  const encode = (value: Record<string, unknown>) => Buffer.from(JSON.stringify(value)).toString('base64url');
  const unsigned = `${encode({ alg: 'RS256', typ: 'JWT' })}.${encode(claims)}`;
  const signer = createSign('RSA-SHA256');
  signer.update(unsigned);
  signer.end();
  const signature = signer.sign(createPrivateKey(pemOf(privateKey))).toString('base64url');
  return `${unsigned}.${signature}`;
}

const GONE_TOKEN = /NotRegistered|UNREGISTERED/i;
const goneCode = (value: unknown) => value === 'UNREGISTERED' || value === 'NOT_REGISTERED';

function detailsSayGone(details: unknown): boolean {
  return Array.isArray(details) && details.some((detail) => isObject(detail) && goneCode(detail.errorCode));
}

/** Whether an FCM error body says the token is gone (HTTP v1's `UNREGISTERED`, the old `NotRegistered`). */
function unregistered(body: unknown, text: string): boolean {
  if (!isObject(body) || !isObject(body.error)) return GONE_TOKEN.test(text);
  const { status, message, details } = body.error;
  return status === 'UNREGISTERED' || (typeof message === 'string' && GONE_TOKEN.test(message)) || detailsSayGone(details);
}

/** A short operator-facing reason from an FCM error body; never includes the push token (it is only in the request). */
function reasonOf(body: unknown, text: string): string {
  if (isObject(body) && isObject(body.error) && typeof body.error.message === 'string') return body.error.message;
  return text.slice(0, 200) || 'no response body';
}

export function createFcmProvider({ serviceAccount, fetch: injected, now = () => Date.now() }: FcmProviderOptions): PushProvider {
  const fetchImpl: Fetch = injected ?? ((url, init) => fetch(url, init));
  const tokenUri = typeof serviceAccount.raw.token_uri === 'string' && serviceAccount.raw.token_uri.length > 0
    ? serviceAccount.raw.token_uri
    : DEFAULT_TOKEN_URI;
  const sendUrl = `https://fcm.googleapis.com/v1/projects/${encodeURIComponent(serviceAccount.projectId)}/messages:send`;
  let cached: { value: string; expiresAt: number } | undefined;

  async function accessToken(): Promise<string> {
    const at = now();
    if (cached && cached.expiresAt > at) return cached.value;
    const issuedAt = Math.floor(at / 1000);
    const assertion = signAssertion({
      iss: serviceAccount.clientEmail,
      scope: FCM_SCOPE,
      aud: tokenUri,
      iat: issuedAt,
      exp: issuedAt + 3600,
    }, serviceAccount.privateKey);
    const response = await fetchImpl(tokenUri, {
      method: 'POST',
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({
        grant_type: 'urn:ietf:params:oauth:grant-type:jwt-bearer',
        assertion,
      }).toString(),
    });
    const text = await response.text();
    if (response.status < 200 || response.status >= 300) throw new Error(`FCM token request failed: HTTP ${response.status} ${reasonOf(tryParseJson(text), text)}`);
    const body = tryParseJson(text);
    if (!isObject(body) || typeof body.access_token !== 'string' || body.access_token.length === 0) {
      throw new Error('FCM token request answered no access_token');
    }
    const expiresIn = typeof body.expires_in === 'number' ? body.expires_in : 3600;
    cached = { value: body.access_token, expiresAt: at + Math.max(0, expiresIn * 1000 - REFRESH_MARGIN_MS) };
    return body.access_token;
  }

  return {
    platform: 'android',
    async send(device: PushTarget, hint: PushHint): Promise<void> {
      const token = await accessToken();
      const response = await fetchImpl(sendUrl, {
        method: 'POST',
        headers: { authorization: `Bearer ${token}`, 'content-type': 'application/json' },
        body: JSON.stringify({
          message: {
            token: device.pushToken,
            data: { v: String(hint.v), kind: hint.kind, id: hint.id },
            android: { priority: 'high', collapse_key: `${hint.userId}:${hint.kind}` },
          },
        }),
      });
      const text = await response.text();
      if (response.status >= 200 && response.status < 300) return;
      const body = tryParseJson(text);
      if (unregistered(body, text)) throw new PushTokenGoneError(device.platform, `HTTP ${response.status}`);
      throw new Error(`FCM send failed: HTTP ${response.status} ${reasonOf(body, text)}`);
    },
  };
}
