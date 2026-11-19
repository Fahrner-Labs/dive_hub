// The Android app identifies itself with `User-Agent: divehub-android/<version>` (ADR 0076). The value is for
// diagnostics and the hard-break gate only: it is never authentication or a trust signal.
//
// The parser is deliberately narrow and defensive: only the exact prefix and a SemVer 2.0.0 version are recognised.
// A missing, malformed or overlong header is simply not an Android client, never an exception and never a gate.

/** Longer than any real `divehub-android/<version>`; a larger header is garbage, rejected before the regex runs. */
const MAX_USER_AGENT_LENGTH = 128;

const ANDROID_PREFIX = 'divehub-android/';

const SEMVER = /^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[A-Za-z-][0-9A-Za-z-]*)(?:\.(?:0|[1-9]\d*|\d*[A-Za-z-][0-9A-Za-z-]*))*))?(?:\+([0-9A-Za-z-]+(?:\.[0-9A-Za-z-]+)*))?$/;

/** A SemVer 2.0.0 version, with its numbers kept as canonical decimal strings so no value can overflow. */
export interface SemanticVersion {
  /** The version as the app sent it, including any prerelease and build metadata. */
  readonly text: string;
  readonly major: string;
  readonly minor: string;
  readonly patch: string;
  /** Dot-separated prerelease identifiers; empty for a release. Build metadata does not take part in ordering. */
  readonly prerelease: readonly string[];
}

/** Parses exactly a SemVer 2.0.0 version, or null for anything else. */
export function parseSemanticVersion(text: string): SemanticVersion | null {
  const match = SEMVER.exec(text);
  if (!match) return null;
  return {
    text,
    major: match[1]!,
    minor: match[2]!,
    patch: match[3]!,
    prerelease: match[4] === undefined ? [] : match[4].split('.'),
  };
}

/** Compares two decimal strings of digits without converting them to a number. */
function compareNumeric(a: string, b: string): number {
  if (a.length !== b.length) return a.length < b.length ? -1 : 1;
  return a < b ? -1 : a > b ? 1 : 0;
}

/** Compares prerelease identifiers by SemVer's rule: numeric before alphanumeric, each by its own order. */
function compareIdentifier(a: string, b: string): number {
  const numericA = /^\d+$/.test(a);
  const numericB = /^\d+$/.test(b);
  if (numericA && numericB) return compareNumeric(a, b);
  if (numericA) return -1;
  if (numericB) return 1;
  return a < b ? -1 : a > b ? 1 : 0;
}

/** Negative when `a` is older than `b`, zero when they have the same precedence, positive when newer. */
export function compareSemanticVersions(a: SemanticVersion, b: SemanticVersion): number {
  for (const [left, right] of [[a.major, b.major], [a.minor, b.minor], [a.patch, b.patch]] as const) {
    const compared = compareNumeric(left, right);
    if (compared !== 0) return compared;
  }
  if (a.prerelease.length === 0 || b.prerelease.length === 0) {
    return a.prerelease.length === b.prerelease.length ? 0 : a.prerelease.length === 0 ? 1 : -1;
  }
  for (let i = 0; i < Math.min(a.prerelease.length, b.prerelease.length); i += 1) {
    const compared = compareIdentifier(a.prerelease[i]!, b.prerelease[i]!);
    if (compared !== 0) return compared;
  }
  return Math.sign(a.prerelease.length - b.prerelease.length);
}

/**
 * The version of a request's `User-Agent` when it is exactly `divehub-android/<semver>`; null for any other client,
 * including a missing, overlong or malformed header.
 */
export function parseAndroidUserAgent(userAgent: unknown): SemanticVersion | null {
  if (typeof userAgent !== 'string' || userAgent.length > MAX_USER_AGENT_LENGTH) return null;
  if (!userAgent.startsWith(ANDROID_PREFIX)) return null;
  return parseSemanticVersion(userAgent.slice(ANDROID_PREFIX.length));
}
