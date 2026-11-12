// Backup runs against real PostgreSQL (ADR 0068): the row and its job commit together, a run writes a readable
// encrypted archive whose dump restores, and a failure marks the row failed and cleans its temp files.
import { execFile } from 'node:child_process';
import { readdir, rm } from 'node:fs/promises';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { promisify } from 'node:util';
import pg from 'pg';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { openArchive } from '../src/backups/backup-archive.js';
import { BackupError, createBackupService, type BackupService } from '../src/backups/backup-service.js';
import { createLocalBlobStore } from '../src/storage/blob-store.js';
import {
  TEST_ENCRYPTION_KEY, createTestAuth, createTestDatabase, createUser, databaseReachable, type TestDatabase,
} from './support.js';
import { resolveTestDatabaseUrl } from './test-database-url.js';

const execFileAsync = promisify(execFile);
const migrationsFolder = fileURLToPath(new URL('../drizzle', import.meta.url));
const silent = { info() {}, warn() {}, error() {} };

/** Whether pg_dump is here; CI reinstalls the PostgreSQL 18 client, and a missing one must not look like a pass. */
async function dumpToolAvailable(): Promise<boolean> {
  try {
    await execFileAsync('pg_dump', ['--version']);
    return true;
  } catch (error) {
    if (process.env.REQUIRE_DATABASE === '1') throw new Error('the backup tests need pg_dump on PATH', { cause: error });
    return false;
  }
}

describe.skipIf(!(await databaseReachable()))('backup runs', () => {
  let t: TestDatabase;
  let blobs: ReturnType<typeof createLocalBlobStore>;
  let service: BackupService;
  let owner: { id: string; email: string };

  const serviceWith = (databaseUrl: string) => createBackupService({
    db: t.db, blobs, dataDir: t.dataDir, databaseUrl, migrationsFolder, appVersion: 'test',
    encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
  });

  beforeAll(async () => {
    t = await createTestDatabase();
    blobs = createLocalBlobStore(t.dataDir);
    service = serviceWith(t.pool.options.connectionString!);
    owner = await createUser(createTestAuth(t.db), 'keeper@example.com', 'admin');
  });

  afterAll(async () => {
    await t?.drop();
  });

  it('creates the row and its job together, with the passphrase in neither', async () => {
    const passphrase = 'a passphrase only in memory';
    const run = await service.create({ kind: 'manual', createdBy: owner.id, passphrase });
    expect(run).toMatchObject({ kind: 'manual', status: 'queued' });
    const jobs = (await t.pool.query<{ payload: unknown; key: string }>(
      'select payload, key from graphile_worker._private_jobs where key = $1', [`backup-run:${run.id}`],
    )).rows;
    expect(jobs).toHaveLength(1);
    expect(jobs[0]!.payload).toEqual({ backupRunId: run.id });
    expect(JSON.stringify(jobs[0]!.payload)).not.toContain(passphrase);
    const row = (await t.pool.query('select * from backup_runs where id = $1', [run.id])).rows[0];
    expect(JSON.stringify(row)).not.toContain(passphrase);
  });

  it('allows only one in-flight run', async () => {
    await expect(service.create({ kind: 'manual', createdBy: owner.id, passphrase: 'another passphrase' }))
      .rejects.toMatchObject({ code: 'backup_in_progress' });
    expect(await service.list()).toHaveLength(1);
  });

  it('marks an orphaned running row as failed and frees the in-flight slot', async () => {
    const [running] = (await t.pool.query<{ id: string }>('select id from backup_runs where status = $1', ['queued'])).rows;
    await t.pool.query("update backup_runs set status = 'running' where id = $1", [running!.id]);
    expect(await service.reconcileOrphans()).toBe(1);
    const row = (await t.pool.query<{ status: string; error: string }>('select status, error from backup_runs where id = $1', [running!.id])).rows[0];
    expect(row).toMatchObject({ status: 'failed' });
    expect(row!.error).toMatch(/server stopped/);
    expect(await service.reconcileOrphans()).toBe(0);
  });

  it('writes an encrypted archive whose manifest and dump verify', async () => {
    if (!(await dumpToolAvailable())) return;
    const passphrase = 'restore-me-with-this';
    const run = await service.create({ kind: 'manual', createdBy: owner.id, passphrase });
    await service.run(run.id);
    const ready = await service.find(run.id);
    expect(ready).toMatchObject({ status: 'ready' });
    expect(ready!.bytes).toBeGreaterThan(0);
    expect(ready!.sha256).toMatch(/^[0-9a-f]{64}$/);

    const opened = await openArchive(service.archivePath(run.id), passphrase, join(t.dataDir, 'verify'));
    expect(opened.manifest.tableCounts.user).toBeGreaterThanOrEqual(1);
    expect(opened.manifest.migrationHead).toMatch(/^\d{4}_/);
    expect(opened.manifest.encryptionKeySha256).toMatch(/^[0-9a-f]{64}$/);
    expect(opened.manifest.plaintextCredentials).toBe(false);

    // The dump restores into an empty database, users and all, which is what the archive is for.
    const admin = new pg.Client({ connectionString: resolveTestDatabaseUrl() });
    await admin.connect();
    const scratch = `divehub_backup_restore_${process.pid}_${Date.now()}`;
    try {
      await admin.query(`create database ${scratch}`);
      const url = new URL(resolveTestDatabaseUrl());
      url.pathname = `/${scratch}`;
      await execFileAsync('pg_restore', ['--no-owner', '--no-privileges', '-d', url.toString(), opened.databasePath]);
      const restored = new pg.Client({ connectionString: url.toString() });
      await restored.connect();
      try {
        const users = await restored.query<{ email: string }>('select email from "user" order by email');
        expect(users.rows.map((r) => r.email)).toContain('keeper@example.com');
        const dives = await restored.query<{ n: number }>('select count(*)::int as n from dive');
        expect(dives.rows[0]!.n).toBe(opened.manifest.tableCounts.dive);
      } finally {
        await restored.end();
      }
    } finally {
      await admin.query(`drop database if exists ${scratch} with (force)`);
      await admin.end();
      await rm(join(t.dataDir, 'verify'), { recursive: true, force: true });
    }
  });

  it('marks a run failed and removes its temp files when the dump cannot run', async () => {
    const failing = serviceWith('postgres://divehub:wrong@127.0.0.1:1/missing');
    const run = await failing.create({ kind: 'manual', passphrase: 'still only in memory' });
    await failing.run(run.id);
    const row = await failing.find(run.id);
    expect(row).toMatchObject({ status: 'failed' });
    expect(row!.error).toBeTruthy();
    const temp = await readdir(join(t.dataDir, 'tmp')).catch(() => []);
    expect(temp).not.toContain(`backup-${run.id}`);
  });

  it('refuses to delete a run that is still in flight, and deletes a finished one with its archive', async () => {
    const run = await service.create({ kind: 'manual', createdBy: owner.id, passphrase: 'delete-me-passphrase' });
    await expect(service.remove(run.id)).rejects.toBeInstanceOf(BackupError);
    if (!(await dumpToolAvailable())) return;
    await service.run(run.id);
    await service.remove(run.id);
    await expect(service.find(run.id)).resolves.toBeNull();
  });
});
