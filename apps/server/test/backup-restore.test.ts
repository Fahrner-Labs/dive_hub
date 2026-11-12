// Restoring a `.divehub-backup` archive (plan FAH-175): the roundtrip into a scratch database and data directory, the
// refusals that happen before anything is written, and the CLI's app-lock guard.
import { createHash, randomBytes } from 'node:crypto';
import { execFile } from 'node:child_process';
import { createReadStream } from 'node:fs';
import { mkdir, mkdtemp, readFile, rm, stat, utimes, writeFile } from 'node:fs/promises';
import { tmpdir } from 'node:os';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { promisify } from 'node:util';
import pg from 'pg';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { APP_LOCK_STALE_MS, acquireAppLock, appLockPath, probeAppLock } from '../src/backups/app-lock.js';
import { buildManifest, encryptFile, packContainer, type BackupManifest } from '../src/backups/backup-archive.js';
import { createBackupService, type BackupService } from '../src/backups/backup-service.js';
import { runRestoreCli } from '../src/backups/restore-cli.js';
import { createRestoreService, imageMigrationHead } from '../src/backups/restore-service.js';
import { createLocalBlobStore } from '../src/storage/blob-store.js';
import {
  TEST_ENCRYPTION_KEY, createTestAuth, createTestDatabase, createUser, databaseReachable, type TestDatabase,
} from './support.js';
import { resolveTestDatabaseUrl } from './test-database-url.js';

const execFileAsync = promisify(execFile);
const migrationsFolder = fileURLToPath(new URL('../drizzle', import.meta.url));
const silent = { info() {}, warn() {}, error() {} };

/** Whether pg_dump and pg_restore are here; CI installs the PostgreSQL 18 client, and a missing one must not pass. */
async function restoreToolsAvailable(): Promise<boolean> {
  try {
    await execFileAsync('pg_dump', ['--version']);
    await execFileAsync('pg_restore', ['--version']);
    return true;
  } catch (error) {
    if (process.env.REQUIRE_DATABASE === '1') throw new Error('the restore tests need pg_dump and pg_restore on PATH', { cause: error });
    return false;
  }
}

interface ScratchDatabase {
  url: string;
  drop(): Promise<void>;
}

/** A fresh database next to the test databases, dropped afterwards; its admin connection stays open until then. */
async function createScratchDatabase(): Promise<ScratchDatabase> {
  const admin = new pg.Client({ connectionString: resolveTestDatabaseUrl() });
  await admin.connect();
  const name = `divehub_restore_${process.pid}_${Date.now()}_${Math.floor(Math.random() * 1e6)}`;
  await admin.query(`create database ${name}`);
  const url = new URL(resolveTestDatabaseUrl());
  url.pathname = `/${name}`;
  return {
    url: url.toString(),
    async drop() {
      await admin.query(`drop database if exists ${name} with (force)`);
      await admin.end();
    },
  };
}

async function sha256Of(path: string): Promise<{ sha256: string; bytes: number }> {
  const hash = createHash('sha256');
  for await (const chunk of createReadStream(path)) hash.update(chunk as Buffer);
  return { sha256: hash.digest('hex'), bytes: (await stat(path)).size };
}

/** An archive with a grammar-valid manifest; the refusal tests stop before pg_restore or the file tar read either part. */
async function makeArchive(dir: string, options: { passphrase: string; migrationHead: string | null; key: Buffer | undefined }): Promise<string> {
  const work = await mkdtemp(join(dir, 'refusal-'));
  const database = join(work, 'database.dump');
  const files = join(work, 'files.tar.gz');
  await writeFile(database, 'a dump the refusal happens before pg_restore reads');
  await writeFile(files, 'a file archive the refusal happens before tar reads');
  const salt = randomBytes(16);
  const manifest = buildManifest({
    appVersion: 'test',
    migrationHead: options.migrationHead,
    createdAt: new Date('2026-11-12T10:00:00Z'),
    tableCounts: {},
    files: { sha256: 'a'.repeat(64), count: 0 },
    parts: { database: await sha256Of(database), files: await sha256Of(files) },
    encryptionKey: options.key,
    salt,
  });
  const container = await packContainer(work, manifest);
  const archive = join(dir, `refusal-${randomBytes(8).toString('hex')}.divehub-backup`);
  await encryptFile(container, archive, options.passphrase, { salt });
  return archive;
}

describe.skipIf(!(await databaseReachable()))('restoring a backup', () => {
  let t: TestDatabase;
  let backups: BackupService;
  let owner: { id: string; email: string };

  beforeAll(async () => {
    t = await createTestDatabase();
    backups = createBackupService({
      db: t.db, blobs: createLocalBlobStore(t.dataDir), dataDir: t.dataDir, databaseUrl: t.pool.options.connectionString!,
      migrationsFolder, appVersion: 'test', encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
    });
    owner = await createUser(createTestAuth(t.db), 'restore-me@example.com', 'admin');
    await mkdir(join(t.dataDir, 'originals', 'ab', 'cd'), { recursive: true });
    await writeFile(join(t.dataDir, 'originals', 'ab', 'cd', 'dive-1.fit'), 'the original dive file');
    await writeFile(join(t.dataDir, 'auth-secret'), 'the auth secret from the backup');
  });

  afterAll(async () => {
    await t?.drop();
  });

  it('restores a backup into a scratch database and data directory', async () => {
    if (!(await restoreToolsAvailable())) return;
    const passphrase = 'roundtrip-passphrase';
    const run = await backups.create({ kind: 'manual', createdBy: owner.id, passphrase });
    await backups.run(run.id);
    expect(await backups.find(run.id)).toMatchObject({ status: 'ready' });

    const scratch = await createScratchDatabase();
    const targetDir = await mkdtemp(join(tmpdir(), 'divehub-restore-target-'));
    try {
      const restore = createRestoreService({
        dataDir: targetDir, databaseUrl: scratch.url, migrationsFolder, encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
      });
      // A restore replaces the managed files: a file the archive does not hold must not survive; archives stay.
      await mkdir(join(targetDir, 'backups'), { recursive: true });
      await writeFile(join(targetDir, 'backups', 'older.divehub-backup'), 'an older archive');
      await mkdir(join(targetDir, 'stale-dir'), { recursive: true });
      await writeFile(join(targetDir, 'stale-dir', 'old.txt'), 'not in the backup');
      await writeFile(join(targetDir, 'stale-file.txt'), 'not in the backup');
      const result = await restore.restore({ archivePath: backups.archivePath(run.id), passphrase });
      expect(result.manifest.tableCounts.user).toBeGreaterThanOrEqual(1);
      expect(result.manifest.migrationHead).toMatch(/^\d{4}_/);

      const client = new pg.Client({ connectionString: scratch.url });
      await client.connect();
      try {
        const users = await client.query<{ email: string }>('select email from "user" order by email');
        expect(users.rows.map((row) => row.email)).toContain('restore-me@example.com');
        const dives = await client.query<{ n: number }>('select count(*)::int as n from dive');
        expect(dives.rows[0]!.n).toBe(result.manifest.tableCounts.dive ?? 0);
      } finally {
        await client.end();
      }

      expect(await readFile(join(targetDir, 'originals', 'ab', 'cd', 'dive-1.fit'), 'utf8')).toBe('the original dive file');
      expect(await readFile(join(targetDir, 'auth-secret'), 'utf8')).toBe('the auth secret from the backup');
      expect(await stat(join(targetDir, 'stale-file.txt')).catch(() => null)).toBeNull();
      expect(await stat(join(targetDir, 'stale-dir')).catch(() => null)).toBeNull();
      expect(await readFile(join(targetDir, 'backups', 'older.divehub-backup'), 'utf8')).toBe('an older archive');
    } finally {
      await scratch.drop();
      await rm(targetDir, { recursive: true, force: true });
    }
  });

  it('refuses a backup made with another DIVEHUB_ENCRYPTION_KEY before touching the instance', async () => {
    const passphrase = 'refusal-passphrase';
    const migrationHead = await imageMigrationHead(migrationsFolder);
    const archive = await makeArchive(t.dataDir, { passphrase, migrationHead, key: Buffer.alloc(32, 9) });
    const targetDir = await mkdtemp(join(tmpdir(), 'divehub-restore-target-'));
    try {
      const restore = createRestoreService({
        dataDir: targetDir, databaseUrl: 'postgres://divehub:nowhere@127.0.0.1:1/never',
        migrationsFolder, encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
      });
      await expect(restore.restore({ archivePath: archive, passphrase }))
        .rejects.toMatchObject({ name: 'RestoreError', code: 'backup_key_mismatch' });
    } finally {
      await rm(targetDir, { recursive: true, force: true });
    }
  });

  it('refuses a backup whose schema is newer than the image', async () => {
    const passphrase = 'refusal-passphrase';
    const archive = await makeArchive(t.dataDir, { passphrase, migrationHead: '9999_a_future_migration', key: TEST_ENCRYPTION_KEY });
    const targetDir = await mkdtemp(join(tmpdir(), 'divehub-restore-target-'));
    try {
      const restore = createRestoreService({
        dataDir: targetDir, databaseUrl: 'postgres://divehub:nowhere@127.0.0.1:1/never',
        migrationsFolder, encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
      });
      await expect(restore.restore({ archivePath: archive, passphrase }))
        .rejects.toMatchObject({ name: 'RestoreError', code: 'backup_schema_newer' });
    } finally {
      await rm(targetDir, { recursive: true, force: true });
    }
  });

  it('refuses an archive whose tag was changed, even though tar stops before the last block', async () => {
    const passphrase = 'tamper-passphrase';
    const archive = await makeArchive(t.dataDir, {
      passphrase, migrationHead: await imageMigrationHead(migrationsFolder), key: TEST_ENCRYPTION_KEY,
    });
    const bytes = await readFile(archive);
    bytes[bytes.length - 1] = bytes[bytes.length - 1]! ^ 0xff;
    const tampered = join(t.dataDir, 'tampered.divehub-backup');
    await writeFile(tampered, bytes);
    const targetDir = await mkdtemp(join(tmpdir(), 'divehub-restore-target-'));
    try {
      const restore = createRestoreService({
        dataDir: targetDir, databaseUrl: 'postgres://divehub:nowhere@127.0.0.1:1/never',
        migrationsFolder, encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
      });
      // The decrypt error, not a part or pg_restore failure: tar closing early must not skip the GCM check.
      await expect(restore.restore({ archivePath: tampered, passphrase }))
        .rejects.toMatchObject({ name: 'RestoreError', code: 'backup_decrypt_failed' });
    } finally {
      await rm(targetDir, { recursive: true, force: true });
    }
  });

  it('refuses while the app lock is fresh, and --force overrides it', async () => {
    const archive = join(t.dataDir, 'unused.divehub-backup');
    await writeFile(archive, 'not opened when the lock refuses');
    const lock = await acquireAppLock(t.dataDir);
    const calls: string[] = [];
    const deps = {
      dataDir: t.dataDir,
      databaseUrl: 'postgres://divehub:nowhere@127.0.0.1:1/never',
      migrationsFolder,
      encryptionKey: TEST_ENCRYPTION_KEY,
      env: { DIVEHUB_BACKUP_PASSPHRASE: 'a passphrase' },
      io: { out() {}, err() {} },
      restore: async ({ archivePath }: { archivePath: string }) => {
        calls.push(archivePath);
        return {
          manifest: { createdAt: '2026-11-12T10:00:00.000Z', migrationHead: '0056_keen_maelstrom' } as BackupManifest,
        };
      },
    };
    try {
      expect(await runRestoreCli([archive], deps)).toBe(1);
      expect(calls).toHaveLength(0);
      expect(await runRestoreCli([archive, '--force'], deps)).toBe(0);
      expect(calls).toEqual([archive]);
    } finally {
      await lock.release();
    }
    expect(await probeAppLock(t.dataDir)).toMatchObject({ running: false, reason: 'missing' });
  });

  it('takes a scheduled archive key from a recipient-key file', async () => {
    const archive = join(t.dataDir, 'scheduled.divehub-backup');
    await writeFile(archive, 'not opened by the stubbed restore');
    const keyFile = join(t.dataDir, 'recipient-key.txt');
    const privateKey = 'AGE-SECRET-KEY-1QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ';
    await writeFile(keyFile, `${privateKey}\n`);
    let seen: { archivePath: string; passphrase?: string | undefined; recipientKey?: string | undefined } | undefined;
    const deps = {
      dataDir: t.dataDir,
      databaseUrl: 'postgres://divehub:nowhere@127.0.0.1:1/never',
      migrationsFolder,
      encryptionKey: TEST_ENCRYPTION_KEY,
      env: {},
      io: { out() {}, err() {} },
      restore: async (input: { archivePath: string; passphrase?: string | undefined; recipientKey?: string | undefined }) => {
        seen = input;
        return { manifest: { createdAt: '2026-11-12T10:00:00.000Z', migrationHead: '0056_keen_maelstrom' } as BackupManifest };
      },
    };
    expect(await runRestoreCli([archive, '--recipient-key-file', keyFile], deps)).toBe(0);
    expect(seen).toEqual({ archivePath: archive, recipientKey: privateKey });
  });

  it('treats an old heartbeat as a stopped app', async () => {
    const dir = await mkdtemp(join(tmpdir(), 'divehub-app-lock-'));
    try {
      const path = appLockPath(dir);
      await mkdir(dirname(path), { recursive: true });
      await writeFile(path, JSON.stringify({ pid: 1, startedAt: new Date().toISOString() }));
      const old = new Date(Date.now() - APP_LOCK_STALE_MS - 1_000);
      await utimes(path, old, old);
      expect(await probeAppLock(dir)).toMatchObject({ running: false, reason: 'stale' });
    } finally {
      await rm(dir, { recursive: true, force: true });
    }
  });
});
