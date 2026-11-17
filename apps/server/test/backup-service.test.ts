// Backup runs against real PostgreSQL (ADR 0069): the row and its job commit together, a run writes a readable
// encrypted archive whose dump restores, and a failure marks the row failed and cleans its temp files.
import { execFile } from 'node:child_process';
import { mkdtemp, readdir, rm } from 'node:fs/promises';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { promisify } from 'node:util';
import pg from 'pg';
import { afterAll, beforeAll, beforeEach, describe, expect, it, vi } from 'vitest';
import { generateRecipientKeyPair, openArchive, openArchiveWith } from '../src/backups/backup-archive.js';
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

  it('marks a queued row whose job is gone as failed, and leaves one whose job is still queued', async () => {
    await t.pool.query('delete from graphile_worker._private_jobs');
    await t.pool.query('delete from backup_runs');
    // A queued row with no job can never start: the state a throw before the status transition used to leave behind.
    const stale = (await t.pool.query<{ id: string }>(
      "insert into backup_runs (kind, status) values ('manual', 'queued') returning id",
    )).rows[0]!;
    expect(await service.reconcileOrphans()).toBe(1);
    expect(await service.find(stale.id)).toMatchObject({ status: 'failed', error: 'the queued backup never started' });

    // A queued run whose job is still there is the worker's, not an orphan.
    const live = await service.create({ kind: 'manual', passphrase: 'a queued passphrase' });
    expect(await service.reconcileOrphans()).toBe(0);
    expect(await service.find(live.id)).toMatchObject({ status: 'queued' });
    await service.remove(live.id);
  });

  it('marks the row failed when the status transition throws, instead of leaving it queued', async () => {
    await t.pool.query('delete from graphile_worker._private_jobs');
    await t.pool.query('delete from backup_runs');
    const run = await service.create({ kind: 'manual', createdBy: owner.id, passphrase: 'a transition passphrase' });
    await t.pool.query(`
      create function backup_runs_block_running() returns trigger as $$
      begin
        if new.status = 'running' and old.status = 'queued' then
          raise exception 'the transition failed';
        end if;
        return new;
      end;
      $$ language plpgsql;
      create trigger backup_runs_block_running before update on backup_runs
        for each row execute function backup_runs_block_running();
    `);
    try {
      await service.run(run.id);
    } finally {
      await t.pool.query('drop trigger if exists backup_runs_block_running on backup_runs');
      await t.pool.query('drop function if exists backup_runs_block_running()');
    }
    const row = await service.find(run.id);
    expect(row).toMatchObject({ status: 'failed' });
    expect(row!.error).toMatch(/transition failed/);
    // The in-flight slot is free: a queued row would have kept every later run out through the unique index.
    const next = await service.create({ kind: 'manual', passphrase: 'after the failed transition' });
    await service.remove(next.id);
  });

  it('fails a queued manual run whose passphrase outlived its lifetime', async () => {
    await t.pool.query('delete from graphile_worker._private_jobs');
    await t.pool.query('delete from backup_runs');
    const clock = { now: 1_000 };
    const expiring = createBackupService({
      db: t.db, blobs, dataDir: t.dataDir, databaseUrl: t.pool.options.connectionString!,
      migrationsFolder, appVersion: 'test', encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
      now: () => clock.now, passphraseTtlMs: 60_000,
    });
    const run = await expiring.create({ kind: 'manual', passphrase: 'a passphrase that waits too long' });
    clock.now += 61_000;
    await expiring.run(run.id);
    const row = await expiring.find(run.id);
    expect(row).toMatchObject({ status: 'failed' });
    expect(row!.error).toMatch(/lost or expired/);
    await t.pool.query('delete from graphile_worker._private_jobs where key = $1', [`backup-run:${run.id}`]);
  });

  it('rolls back the row and forgets the passphrase when its job cannot be queued', async () => {
    await t.pool.query('delete from graphile_worker._private_jobs');
    await t.pool.query('delete from backup_runs');
    await t.pool.query(`
      create function backup_jobs_block_queue() returns trigger as $$
      begin
        raise exception 'add_job failed';
      end;
      $$ language plpgsql;
      create trigger backup_jobs_block_queue before insert on graphile_worker._private_jobs
        for each row execute function backup_jobs_block_queue();
    `);
    try {
      await expect(service.create({ kind: 'manual', passphrase: 'a passphrase with no job' })).rejects.toThrow();
    } finally {
      await t.pool.query('drop trigger if exists backup_jobs_block_queue on graphile_worker._private_jobs');
      await t.pool.query('drop function if exists backup_jobs_block_queue()');
    }
    expect((await t.pool.query<{ n: number }>('select count(*)::int as n from backup_runs')).rows[0]!.n).toBe(0);
    // With the map entry gone too, the next queued run owns the in-flight slot and can still be created.
    const next = await service.create({ kind: 'manual', passphrase: 'the next passphrase' });
    await service.remove(next.id);
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
    admin.on('error', (error) => console.error('PostgreSQL connection error', error));
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
    const logged: string[] = [];
    const password = 'a-database-password-that-must-not-leak';
    const failing = createBackupService({
      db: t.db, blobs, dataDir: t.dataDir, databaseUrl: `postgres://divehub:${password}@127.0.0.1:1/missing`,
      migrationsFolder, appVersion: 'test', encryptionKey: TEST_ENCRYPTION_KEY,
      log: { info() {}, warn() {}, error(detail) { logged.push(JSON.stringify(detail)); } },
    });
    const run = await failing.create({ kind: 'manual', passphrase: 'still only in memory' });
    await failing.run(run.id);
    const row = await failing.find(run.id);
    expect(row).toMatchObject({ status: 'failed' });
    expect(row!.error).toBeTruthy();
    // pg_dump's execFile message repeats its command line; the stored and logged one must not carry the password.
    expect(row!.error).not.toContain(password);
    expect(logged.join('\n')).not.toContain(password);
    const temp = await readdir(join(t.dataDir, 'tmp')).catch(() => []);
    expect(temp).not.toContain(`backup-${run.id}`);
  });

  it('marks a run failed with install guidance when pg_dump is missing from PATH', async () => {
    const run = await service.create({ kind: 'manual', createdBy: owner.id, passphrase: 'a missing-tool passphrase' });
    const empty = await mkdtemp(join(tmpdir(), 'no-pg-tools-'));
    try {
      vi.stubEnv('PATH', empty);
      await service.run(run.id);
    } finally {
      vi.unstubAllEnvs();
      await rm(empty, { recursive: true, force: true });
    }
    const row = await service.find(run.id);
    expect(row).toMatchObject({ status: 'failed' });
    expect(row!.error).toContain('pg_dump');
    expect(row!.error).toContain('PostgreSQL 18');
    expect(row!.error).toContain('PATH');
    expect(row!.error).not.toContain('ENOENT');
    await t.pool.query('delete from graphile_worker._private_jobs where key = $1', [`backup-run:${run.id}`]);
    await t.pool.query('delete from backup_runs where id = $1', [run.id]);
  });

  it('cancels a queued run with its job, refuses to delete a running one, and deletes a finished one with its archive', async () => {
    const queued = await service.create({ kind: 'manual', createdBy: owner.id, passphrase: 'cancel-me-passphrase' });
    await service.remove(queued.id);
    await expect(service.find(queued.id)).resolves.toBeNull();
    const jobs = await t.pool.query('select 1 from graphile_worker.jobs where key = $1', [`backup-run:${queued.id}`]);
    expect(jobs.rowCount).toBe(0);

    const running = await service.create({ kind: 'manual', createdBy: owner.id, passphrase: 'still running' });
    await t.pool.query("update backup_runs set status = 'running' where id = $1", [running.id]);
    await expect(service.remove(running.id)).rejects.toBeInstanceOf(BackupError);
    await t.pool.query("update backup_runs set status = 'failed' where id = $1", [running.id]);

    const done = await service.create({ kind: 'manual', createdBy: owner.id, passphrase: 'delete-me-passphrase' });
    if (!(await dumpToolAvailable())) return;
    await service.run(done.id);
    await service.remove(done.id);
    await expect(service.find(done.id)).resolves.toBeNull();
  });
});

describe.skipIf(!(await databaseReachable()))('scheduled backups', () => {
  let t: TestDatabase;
  let blobs: ReturnType<typeof createLocalBlobStore>;
  let service: BackupService;
  let nowMs: number;

  beforeAll(async () => {
    t = await createTestDatabase();
    blobs = createLocalBlobStore(t.dataDir);
    nowMs = Date.now();
    service = createBackupService({
      db: t.db, blobs, dataDir: t.dataDir, databaseUrl: t.pool.options.connectionString!,
      migrationsFolder, appVersion: 'test', encryptionKey: TEST_ENCRYPTION_KEY, log: silent, now: () => nowMs,
    });
  });

  afterAll(async () => {
    await t?.drop();
  });

  beforeEach(async () => {
    await t.pool.query('delete from graphile_worker._private_jobs');
    await t.pool.query('delete from backup_runs');
  });

  it('starts quiet and validates the settings before saving them', async () => {
    const keys = generateRecipientKeyPair();
    expect(await service.settings()).toEqual({ enabled: false, intervalHours: 24, retentionCount: 7, recipientPublicKey: null });
    await expect(service.saveSettings({ enabled: true, intervalHours: 24, retentionCount: 7, recipientPublicKey: null }))
      .rejects.toMatchObject({ code: 'backup_recipient_missing' });
    await expect(service.saveSettings({ enabled: true, intervalHours: 24, retentionCount: 7, recipientPublicKey: 'age1nope' }))
      .rejects.toMatchObject({ code: 'backup_recipient_invalid' });
    await expect(service.saveSettings({ enabled: false, intervalHours: 0, retentionCount: 7, recipientPublicKey: null }))
      .rejects.toMatchObject({ code: 'backup_settings_invalid' });
    await expect(service.saveSettings({ enabled: false, intervalHours: 24, retentionCount: 0, recipientPublicKey: null }))
      .rejects.toMatchObject({ code: 'backup_settings_invalid' });
    const saved = await service.saveSettings({ enabled: true, intervalHours: 6, retentionCount: 3, recipientPublicKey: keys.recipient });
    expect(saved).toEqual({ enabled: true, intervalHours: 6, retentionCount: 3, recipientPublicKey: keys.recipient });
    expect(await service.settings()).toEqual(saved);
  });

  it('queues one scheduled run per interval, not one per tick', async () => {
    const keys = generateRecipientKeyPair();
    await service.saveSettings({ enabled: true, intervalHours: 6, retentionCount: 7, recipientPublicKey: keys.recipient });
    await service.tick();
    await service.tick();
    const scheduled = (await service.list()).filter((run) => run.kind === 'scheduled');
    expect(scheduled).toHaveLength(1);
    expect(scheduled[0]).toMatchObject({ status: 'queued' });
    const jobs = await t.pool.query<{ payload: unknown }>('select payload from graphile_worker._private_jobs where key = $1', [`backup-run:${scheduled[0]!.id}`]);
    expect(jobs.rows).toHaveLength(1);
    expect(jobs.rows[0]!.payload).toEqual({ backupRunId: scheduled[0]!.id });

    // A manual backup in flight keeps the scheduler from writing over it; the next interval can try again.
    await t.pool.query("update backup_runs set status = 'ready' where id = $1", [scheduled[0]!.id]);
    nowMs += 7 * 3_600_000;
    const manual = await service.create({ kind: 'manual', passphrase: 'a manual passphrase' });
    await service.tick();
    expect((await service.list()).filter((run) => run.kind === 'scheduled')).toHaveLength(1);
    await t.pool.query("update backup_runs set status = 'failed' where id = $1", [manual.id]);
    await service.tick();
    expect((await service.list()).filter((run) => run.kind === 'scheduled')).toHaveLength(2);
  });

  it('keeps the newest scheduled runs, never a manual run, and always the last success', async () => {
    const insert = async (kind: 'manual' | 'scheduled', status: string, at: Date): Promise<string> => {
      const [row] = (await t.pool.query<{ id: string }>(
        'insert into backup_runs (kind, status, created_at, updated_at) values ($1, $2, $3, now()) returning id',
        [kind, status, at],
      )).rows;
      return row!.id;
    };
    const day = 86_400_000;
    const at = new Date(nowMs);
    const manual = await insert('manual', 'ready', new Date(at.getTime() - 10 * day));
    const ready = [];
    for (const days of [6, 5, 4, 3, 2]) ready.push(await insert('scheduled', 'ready', new Date(at.getTime() - days * day)));
    const failed = await insert('scheduled', 'failed', new Date(at.getTime() - day));
    await service.saveSettings({ enabled: false, intervalHours: 24, retentionCount: 3, recipientPublicKey: null });
    expect(await service.sweepRetention()).toBe(3);
    const left = (await service.list()).map((run) => run.id);
    expect(left).toContain(manual);
    expect(left).toEqual(expect.arrayContaining([failed, ready[3]!, ready[4]!]));
    for (const removed of ready.slice(0, 3)) expect(left).not.toContain(removed);

    // With room for one, the newest run may be a failure; the last successful run is still never deleted.
    await t.pool.query('delete from backup_runs');
    const olderSuccess = await insert('scheduled', 'ready', new Date(at.getTime() - 2 * 3_600_000));
    const newerFailure = await insert('scheduled', 'failed', new Date(at.getTime() - 3_600_000));
    await service.saveSettings({ enabled: false, intervalHours: 24, retentionCount: 1, recipientPublicKey: null });
    expect(await service.sweepRetention()).toBe(0);
    const kept = (await service.list()).map((run) => run.id);
    expect(kept).toEqual(expect.arrayContaining([olderSuccess, newerFailure]));
  });

  it('writes a scheduled archive to the recipient, and a failing dump marks the run failed', async () => {
    if (!(await dumpToolAvailable())) return;
    const keys = generateRecipientKeyPair();
    await service.saveSettings({ enabled: true, intervalHours: 6, retentionCount: 7, recipientPublicKey: keys.recipient });
    await service.tick();
    const queued = (await service.list()).find((run) => run.kind === 'scheduled' && run.status === 'queued');
    expect(queued).toBeDefined();
    await service.run(queued!.id);
    expect(await service.find(queued!.id)).toMatchObject({ status: 'ready' });
    const opened = await openArchiveWith(service.archivePath(queued!.id), { kind: 'recipient', privateKey: keys.privateKey }, join(t.dataDir, 'verify-recipient'));
    expect(opened.manifest.encryption).toMatchObject({ kdf: 'x25519', recipient: keys.recipient });
    expect(opened.manifest.tableCounts.user).toBeGreaterThanOrEqual(0);

    const [row] = (await t.pool.query<{ id: string }>(
      "insert into backup_runs (kind, status) values ('scheduled', 'queued') returning id",
    )).rows;
    const failing = createBackupService({
      db: t.db, blobs, dataDir: t.dataDir, databaseUrl: 'postgres://divehub:wrong@127.0.0.1:1/missing',
      migrationsFolder, appVersion: 'test', encryptionKey: TEST_ENCRYPTION_KEY, log: silent, now: () => nowMs,
    });
    await failing.run(row!.id);
    expect(await failing.find(row!.id)).toMatchObject({ status: 'failed' });
    expect((await failing.find(row!.id))!.error).toBeTruthy();
  });
});
