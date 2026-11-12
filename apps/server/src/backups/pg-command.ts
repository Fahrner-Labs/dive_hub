// Running pg_dump and pg_restore without their child process putting the database password into argv (where an
// execFile error message repeats it) or into a stored/logged failure detail.
import { errorMessage } from '../errors.js';

/** The connection URL without its password, and the child env that carries the password as PGPASSWORD instead. */
export function pgCommand(databaseUrl: string): { url: string; env: NodeJS.ProcessEnv } {
  let parsed: URL;
  try {
    parsed = new URL(databaseUrl);
  } catch {
    // Not a URL (a libpq key=value string): leave it to pg's own parsing; the redaction below still covers it.
    return { url: databaseUrl, env: process.env };
  }
  if (parsed.password === '') return { url: databaseUrl, env: process.env };
  const password = decodeURIComponent(parsed.password);
  parsed.password = '';
  return { url: parsed.toString(), env: { ...process.env, PGPASSWORD: password } };
}

/** Removes a database password from text that may repeat a connection URL or libpq key/value string. */
export function redactPassword(text: string, databaseUrl?: string): string {
  let redacted = text
    .replace(/([a-z][a-z0-9+.-]*:\/\/[^\s/:@]+:)[^\s/@]+(@)/gi, '$1***$2')
    .replace(/\bpassword\s*=\s*('[^']*'|\S+)/gi, 'password=***');
  if (databaseUrl) {
    try {
      const parsed = new URL(databaseUrl);
      for (const secret of new Set([parsed.password, decodeURIComponent(parsed.password)])) {
        if (secret !== '') redacted = redacted.split(secret).join('***');
      }
    } catch {
      // Not a URL; the patterns above already cover a password=… form.
    }
  }
  return redacted;
}

/** A failure detail safe to store or log: the child's own message, last lines first, with any database password gone. */
export function pgFailureMessage(error: unknown, databaseUrl: string): string {
  const stderr = (error as { stderr?: unknown }).stderr;
  const cause = (error as { cause?: unknown }).cause;
  // A driver or Drizzle wrapper repeats the query, not what the database said; its cause has the real message.
  const detail = typeof stderr === 'string' && stderr.trim() !== ''
    ? stderr.trim()
    : cause !== undefined && cause !== null ? errorMessage(cause) : errorMessage(error);
  return redactPassword(detail, databaseUrl).slice(-2000);
}
