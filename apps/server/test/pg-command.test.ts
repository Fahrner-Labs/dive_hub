// pg_dump/pg_restore run through helpers that keep the database password out of argv and out of stored errors.
import { execFile } from 'node:child_process';
import { mkdtemp, rm } from 'node:fs/promises';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { promisify } from 'node:util';
import { describe, expect, it, vi } from 'vitest';
import { assertPgTool, pgCommand, pgFailureMessage, pgToolFailure, redactPassword } from '../src/backups/pg-command.js';

const execFileAsync = promisify(execFile);

/** Whether pg_dump is here; CI installs the PostgreSQL 18 client, and a missing one must not look like a pass. */
async function dumpToolAvailable(): Promise<boolean> {
  try {
    await execFileAsync('pg_dump', ['--version']);
    return true;
  } catch {
    return false;
  }
}

const pgDumpAvailable = await dumpToolAvailable();

describe('running a PostgreSQL client command', () => {
  it('takes the password out of the URL and passes it as PGPASSWORD', () => {
    const { url, env } = pgCommand('postgres://divehub:s3cret-p%40ss@db.example:5432/divehub?sslmode=require');
    expect(url).toBe('postgres://divehub@db.example:5432/divehub?sslmode=require');
    expect(url).not.toContain('s3cret');
    expect(env.PGPASSWORD).toBe('s3cret-p@ss');
  });

  it('leaves a URL without a password alone', () => {
    const { url, env } = pgCommand('postgres://divehub@db.example:5432/divehub');
    expect(url).toBe('postgres://divehub@db.example:5432/divehub');
    expect(env.PGPASSWORD).toBeUndefined();
  });

  it('keeps the rest of the URL byte for byte, including a missing database path', () => {
    const { url } = pgCommand('postgres://divehub:s3cret@db.example:5432');
    expect(url).toBe('postgres://divehub@db.example:5432');
  });

  it('redacts a password a URL or a libpq string repeats', () => {
    expect(redactPassword('Command failed: pg_dump postgres://u:hunter2@db/dive\nboom', 'postgres://u:hunter2@db/dive'))
      .toBe('Command failed: pg_dump postgres://u:***@db/dive\nboom');
    expect(redactPassword('connection to server failed: password=hunter2 host=db'))
      .toBe('connection to server failed: password=*** host=db');
  });

  it('stores the child stderr with the password gone', () => {
    const error = Object.assign(new Error('Command failed: pg_dump postgres://u:hunter2@db/dive'), {
      stderr: 'pg_dump: error: connection to "db" failed for postgres://u:hunter2@db/dive',
    });
    const message = pgFailureMessage(error, 'postgres://u:hunter2@db/dive');
    expect(message).toContain('connection to');
    expect(message).not.toContain('hunter2');
  });

  it('names the missing tool and how to install it, without the raw spawn error', () => {
    const message = pgToolFailure('pg_dump', Object.assign(new Error('spawn pg_dump ENOENT'), { code: 'ENOENT' }), '');
    expect(message).toContain('pg_dump');
    expect(message).toContain('PostgreSQL 18');
    expect(message).toContain('winget install -e --id PostgreSQL.PostgreSQL.18');
    expect(message).toContain('PATH');
    expect(message).not.toContain('ENOENT');
  });

  it('leads a version mismatch with the fix and keeps the redacted stderr detail', () => {
    const error = Object.assign(new Error('Command failed: pg_dump postgres://u:hunter2@db/dive'), {
      stderr: 'pg_dump: error: aborting because of server version mismatch\nconnection postgres://u:hunter2@db/dive refused',
    });
    const message = pgToolFailure('pg_dump', error, 'postgres://u:hunter2@db/dive');
    expect(message).toContain('older than the database server');
    expect(message).toContain('PostgreSQL 18');
    expect(message).toContain('server version mismatch');
    expect(message).not.toContain('hunter2');
  });

  it.skipIf(!pgDumpAvailable)('probes the tool and rejects when PATH cannot find it', async () => {
    await expect(assertPgTool('pg_dump')).resolves.toBeUndefined();
    const empty = await mkdtemp(join(tmpdir(), 'no-pg-tools-'));
    try {
      vi.stubEnv('PATH', empty);
      await expect(assertPgTool('pg_dump')).rejects.toThrow(/pg_dump.*not found/);
    } finally {
      vi.unstubAllEnvs();
      await rm(empty, { recursive: true, force: true });
    }
  });
});
