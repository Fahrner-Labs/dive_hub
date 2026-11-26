// Consent at self-serve signup (hosted plan §6.2): the Terms of Service and Privacy Policy a person accepts before
// their account exists. The payload carries the acceptance; the server records the versions in force, so what was
// accepted and when is provable later even after the text changes.

/** The versions in force. Bump one when its text changes; new signups record the new version. */
export const CONSENT_VERSIONS = { terms: '2026-11-26', privacy: '2026-11-26' } as const;

export type ConsentKind = keyof typeof CONSENT_VERSIONS;

export interface AcceptedConsent {
  kind: ConsentKind;
  version: string;
}

const isObject = (value: unknown): value is Record<string, unknown> =>
  typeof value === 'object' && value !== null && !Array.isArray(value);

/**
 * The consent a sign-up payload carries, or null when it is missing or not a clear acceptance of both. The body is
 * `{ consent: { terms: true, privacy: true } }`; booleans only, so a stray string or a half-filled form is refused.
 */
export function consentOf(body: unknown): AcceptedConsent[] | null {
  if (!isObject(body)) return null;
  const consent = body.consent;
  if (!isObject(consent)) return null;
  if (consent.terms !== true || consent.privacy !== true) return null;
  return (Object.keys(CONSENT_VERSIONS) as ConsentKind[]).map((kind) => ({ kind, version: CONSENT_VERSIONS[kind] }));
}
