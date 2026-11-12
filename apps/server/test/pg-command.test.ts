// pg_dump/pg_restore run through helpers that keep the database password out of argv and out of stored errors.
import { describe, expect, it } from 'vitest';
import { pgCommand, pgFailureMessage, redactPassword } from '../src/backups/pg-command.js';

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
});
