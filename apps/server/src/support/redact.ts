export interface RedactionCounters {
  keysCensored: number;
  emailsMasked: number;
  ipsMasked: number;
}

export interface RedactOptions {
  keyAllowlist?: ReadonlySet<string>;
}

const REDACTED = '‹redacted›';
const EMAIL = '‹email›';
const IP = '‹ip›';

const DENIED_KEY_PARTS = [
  'token', 'secret', 'password', 'authorization', 'cookie', 'passphrase', 'apikey', 'credential',
];

const EMAIL_PATTERN = /(?<![A-Za-z0-9._%+-])[A-Za-z0-9._%+-]{1,64}@(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\.){1,16}[A-Za-z]{2,24}(?![A-Za-z0-9-])/g;
const IPV4_PATTERN = /(?<![\d.])(?:25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(?:\.(?:25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}(?![\d.])/g;
const IPV6_PATTERN = /(?<![0-9a-f:])(?:(?:[0-9a-f]{1,4}:){7}[0-9a-f]{1,4}|(?:[0-9a-f]{1,4}:){1,7}:|(?:[0-9a-f]{1,4}:){1,6}:[0-9a-f]{1,4}|(?:[0-9a-f]{1,4}:){1,5}(?::[0-9a-f]{1,4}){1,2}|(?:[0-9a-f]{1,4}:){1,4}(?::[0-9a-f]{1,4}){1,3}|(?:[0-9a-f]{1,4}:){1,3}(?::[0-9a-f]{1,4}){1,4}|(?:[0-9a-f]{1,4}:){1,2}(?::[0-9a-f]{1,4}){1,5}|[0-9a-f]{1,4}:(?::[0-9a-f]{1,4}){1,6}|:(?:(?::[0-9a-f]{1,4}){1,7}|:))(?![0-9a-f:])/gi;

export const emptyRedactionCounters = (): RedactionCounters => ({ keysCensored: 0, emailsMasked: 0, ipsMasked: 0 });

const isDeniedKey = (key: string): boolean => {
  const normalized = key.toLowerCase().replaceAll('-', '').replaceAll('_', '');
  return DENIED_KEY_PARTS.some((part) => normalized.includes(part));
};

function maskString(value: string, counters: RedactionCounters): string {
  let masked = value;
  if (masked.includes('@')) {
    masked = masked.replace(EMAIL_PATTERN, () => {
      counters.emailsMasked += 1;
      return EMAIL;
    });
  }
  if (masked.includes(':') || /[0-9]/.test(masked)) {
    masked = masked.replace(IPV6_PATTERN, () => {
      counters.ipsMasked += 1;
      return IP;
    }).replace(IPV4_PATTERN, () => {
      counters.ipsMasked += 1;
      return IP;
    });
  }
  return masked;
}

function redactInner(value: unknown, key: string | undefined, counters: RedactionCounters, options: RedactOptions): unknown {
  if (key !== undefined && !options.keyAllowlist?.has(key) && isDeniedKey(key)) {
    if (value !== REDACTED) counters.keysCensored += 1;
    return REDACTED;
  }
  if (typeof value === 'string') return maskString(value, counters);
  if (Array.isArray(value)) return value.map((item) => redactInner(item, undefined, counters, options));
  if (value instanceof Date) return value.toISOString();
  if (value !== null && typeof value === 'object') {
    const out: Record<string, unknown> = {};
    for (const [childKey, childValue] of Object.entries(value)) {
      out[childKey] = redactInner(childValue, childKey, counters, options);
    }
    return out;
  }
  return value;
}

export function redactValue<T>(
  value: T,
  key: string | undefined = undefined,
  counters: RedactionCounters = emptyRedactionCounters(),
  options: RedactOptions = {},
): T {
  return redactInner(value, key, counters, options) as T;
}

export function redactLogObject<T extends Record<string, unknown>>(
  value: T,
  counters: RedactionCounters = emptyRedactionCounters(),
  options: RedactOptions = {},
): T {
  return redactValue(value, undefined, counters, options);
}
