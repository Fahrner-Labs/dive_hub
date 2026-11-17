// Running pg_dump and pg_restore without their child process putting the database password into argv (where an
// execFile error message repeats it) or into a stored/logged failure detail.
import { execFile } from 'node:child_process';
import { promisify } from 'node:util';
import { errorMessage } from '../errors.js';

const execFileAsync = promisify(execFile);

/** The PostgreSQL client tools a backup or restore runs. */
export type PgTool = 'pg_dump' | 'pg_restore';

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
  // Remove just the `:password` part, keeping every other character of the URL: re-serialising an URL without a
  // database path would add a trailing slash and change which database pg connects to.
  const stripped = databaseUrl.replace(/^([a-z][a-z0-9+.-]*:\/\/[^@/]*):[^@/]*@/i, '$1@');
  parsed.password = '';
  return { url: stripped === databaseUrl ? parsed.toString() : stripped, env: { ...process.env, PGPASSWORD: password } };
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

/** The spawn code and stderr of an execFile error, also when an assertPgTool wrapper carries it as its cause. */
function pgErrorDetail(error: unknown): { code: string | undefined; stderr: string | undefined } {
  const own = error as { code?: string; stderr?: unknown; cause?: { code?: string; stderr?: unknown } | undefined };
  return {
    code: own.code ?? own.cause?.code,
    stderr: typeof own.stderr === 'string' ? own.stderr : typeof own.cause?.stderr === 'string' ? own.cause.stderr : undefined,
  };
}

/**
 * A backup or restore failure with the operator's next step first: install the missing client tool, or install the
 * client whose major version matches the server. Anything else stays the child's own redacted detail.
 */
export function pgToolFailure(tool: PgTool, error: unknown, databaseUrl: string): string {
  const { code, stderr } = pgErrorDetail(error);
  if (code === 'ENOENT') {
    return `Backups need ${tool}, but it was not found on this host. The Dive Hub image ships the PostgreSQL client tools; a host that runs the server directly (the development flow) must install them: PostgreSQL 18 client tools — \`apk add postgresql18-client\`, \`apt install postgresql-client-18\`, \`brew install libpq\`, or on Windows \`winget install -e --id PostgreSQL.PostgreSQL.18\` (the EnterpriseDB PostgreSQL 18 installer works too) — and put the PostgreSQL bin directory on PATH. Then try again.`;
  }
  if (stderr !== undefined && stderr.includes('server version mismatch')) {
    return `${tool} refused: the installed client is older than the database server. Install the PostgreSQL client tools whose major version matches the server (PostgreSQL 18 in the development setup) and try again.\n\n${redactPassword(stderr.trim(), databaseUrl)}`;
  }
  return pgFailureMessage(error, databaseUrl);
}

/** Probes a client tool before a run, so a host without it fails with the install guidance instead of a raw ENOENT. */
export async function assertPgTool(tool: PgTool): Promise<void> {
  try {
    await execFileAsync(tool, ['--version']);
  } catch (error) {
    throw new Error(pgToolFailure(tool, error, ''), { cause: error });
  }
}
