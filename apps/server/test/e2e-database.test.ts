// The browser tests' template databases (ADR 0023): the key follows the migrations, and each e2e server's database is
// cloned from the migrated template instead of being migrated again.
import { appendFile, cp, mkdtemp, rm } from 'node:fs/promises';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';
import pg from 'pg';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { createE2eDatabase, migrationKey, templateDatabaseName, type E2eDatabase } from './e2e-database.js';
import { databaseReachable } from './support.js';
import { resolveTestDatabaseUrl } from './test-database-url.js';

const migrations = fileURLToPath(new URL('../drizzle', import.meta.url));

describe('the template key', () => {
  it('is stable for the same migrations and changes when one changes', async () => {
    const copy = await mkdtemp(join(tmpdir(), 'divehub-e2e-key-'));
    try {
      await cp(migrations, copy, { recursive: true });
      const before = migrationKey(copy);
      expect(migrationKey(copy)).toBe(before);
      await appendFile(join(copy, '0002_password_reset.sql'), '\n-- an edit made after the fact\n');
      expect(migrationKey(copy)).not.toBe(before);
    } finally {
      await rm(copy, { recursive: true, force: true });
    }
  });

  it('names the template by the key, in a plain identifier', () => {
    expect(templateDatabaseName('a'.repeat(64))).toBe(`divehub_e2e_template_${'a'.repeat(32)}`);
    expect(() => templateDatabaseName('a'.repeat(64), 'not plain')).toThrow(/must be plain/);
  });
});

describe.skipIf(!(await databaseReachable()))('a template database', () => {
  const baseUrl = resolveTestDatabaseUrl();
  const prefix = `divehub_test_tpl_${process.pid}`;
  const first = `divehub_test_clone_${process.pid}_a`;
  const second = `divehub_test_clone_${process.pid}_b`;
  const third = `divehub_test_clone_${process.pid}_c`;
  let admin: pg.Client;
  let one: E2eDatabase;
  let two: E2eDatabase;

  beforeAll(async () => {
    admin = new pg.Client({ connectionString: baseUrl });
    await admin.connect();
    one = await createE2eDatabase({ databaseUrl: baseUrl, databaseName: first, migrationsFolder: migrations, templatePrefix: prefix, runId: 'first-run' });
    two = await createE2eDatabase({ databaseUrl: baseUrl, databaseName: second, migrationsFolder: migrations, templatePrefix: prefix, runId: 'first-run' });
  }, 60_000);

  afterAll(async () => {
    for (const name of [first, second, third, one?.template, two?.template]) {
      if (name) await admin.query(`drop database if exists ${name} with (force)`).catch(() => {});
    }
    await admin?.end();
  });

  it('is built once, reused for the next clone, and carries the migrations', async () => {
    expect(one.reusedTemplate).toBe(false);
    expect(two.reusedTemplate).toBe(true);
    expect(two.template).toBe(one.template);
    expect(await migrationRows(one.url)).toBeGreaterThan(0);
    expect(await migrationRows(two.url)).toBe(await migrationRows(one.url));
  });

  it('checks the migrations against the template again in another run', async () => {
    const again = await createE2eDatabase({
      databaseUrl: baseUrl, databaseName: third, migrationsFolder: migrations, templatePrefix: prefix, runId: 'second-run',
    });
    expect(again.reusedTemplate).toBe(true);
    expect(again.template).toBe(one.template);
    expect(await migrationRows(again.url)).toBe(await migrationRows(one.url));
  });

  it('gives each worker a database of its own', async () => {
    const client = new pg.Client({ connectionString: one.url });
    await client.connect();
    try {
      await client.query('create table only_in_the_first_clone (id int)');
    } finally {
      await client.end();
    }
    const other = new pg.Client({ connectionString: two.url });
    await other.connect();
    try {
      expect((await other.query(`select to_regclass('public.only_in_the_first_clone') as name`)).rows[0].name).toBeNull();
    } finally {
      await other.end();
    }
  });
});

async function migrationRows(databaseUrl: string): Promise<number> {
  const client = new pg.Client({ connectionString: databaseUrl });
  await client.connect();
  try {
    return ((await client.query<{ n: number }>('select count(*)::int as n from drizzle.__drizzle_migrations')).rows[0]!).n;
  } finally {
    await client.end();
  }
}
