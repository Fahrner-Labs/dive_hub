// First-start restore (plan FAH-175 slice 3): a fresh instance is provisioned from a backup through the setup route,
// the setup token protects it, uploads stream in without the app's upload cap, and the worker is held until setup.
import { execFile } from 'node:child_process';
import { mkdir, readFile, readdir, writeFile } from 'node:fs/promises';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { promisify } from 'node:util';
import { afterAll, beforeAll, describe, expect, it, vi } from 'vitest';
import { generateRecipientKeyPair } from '../src/backups/backup-archive.js';
import { createBackupService } from '../src/backups/backup-service.js';
import { createLocalBlobStore } from '../src/storage/blob-store.js';
import {
  TEST_ENCRYPTION_KEY, createTestApp, createTestAuth, createTestDatabase, createUser, databaseReachable, type TestDatabase,
} from './support.js';

const execFileAsync = promisify(execFile);
const migrationsFolder = fileURLToPath(new URL('../drizzle', import.meta.url));
const silent = { info() {}, warn() {}, error() {} };
const PASSPHRASE = 'a backup passphrase for first start';
const ADMIN_EMAIL = 'restored-admin@example.com';
const ORIGINAL = 'originals/ab/cd/dive-1.fit';

/** CI installs the PostgreSQL client; without pg_dump/pg_restore a restore test must not look like a pass. */
async function restoreToolsAvailable(): Promise<boolean> {
  try {
    await execFileAsync('pg_dump', ['--version']);
    await execFileAsync('pg_restore', ['--version']);
    return true;
  } catch (error) {
    if (process.env.REQUIRE_DATABASE === '1') throw new Error('the setup restore tests need pg_dump and pg_restore on PATH', { cause: error });
    return false;
  }
}

/** A multipart body with fields and one file, as the setup screen sends it. */
function multipart(fields: Record<string, string>, file?: { field: string; name: string; data: Buffer }) {
  const boundary = `----divehub-setup-${Date.now()}-${Math.floor(Math.random() * 1e6)}`;
  const parts: Buffer[] = [];
  for (const [name, value] of Object.entries(fields)) {
    parts.push(Buffer.from(`--${boundary}\r\nContent-Disposition: form-data; name="${name}"\r\n\r\n${value}\r\n`));
  }
  if (file) {
    parts.push(Buffer.from(`--${boundary}\r\nContent-Disposition: form-data; name="${file.field}"; filename="${file.name}"\r\nContent-Type: application/octet-stream\r\n\r\n`));
    parts.push(file.data);
    parts.push(Buffer.from('\r\n'));
  }
  parts.push(Buffer.from(`--${boundary}--\r\n`));
  return { payload: Buffer.concat(parts), headers: { 'content-type': `multipart/form-data; boundary=${boundary}` } };
}

describe.skipIf(!(await databaseReachable()))('first-start restore', () => {
  let source: TestDatabase;
  let archive: Buffer;
  let recipientArchive: Buffer;
  let recipientPrivateKey: string;

  beforeAll(async () => {
    source = await createTestDatabase();
    if (!(await restoreToolsAvailable())) return;
    const owner = await createUser(createTestAuth(source.db), ADMIN_EMAIL, 'admin');
    await mkdir(join(source.dataDir, 'originals', 'ab', 'cd'), { recursive: true });
    await writeFile(join(source.dataDir, ORIGINAL), 'the original dive file');
    await writeFile(join(source.dataDir, 'auth-secret'), 'the auth secret from the backup');
    const backups = createBackupService({
      db: source.db, blobs: createLocalBlobStore(source.dataDir), dataDir: source.dataDir,
      databaseUrl: source.pool.options.connectionString!, migrationsFolder, appVersion: 'test', encryptionKey: TEST_ENCRYPTION_KEY, log: silent,
    });
    const run = await backups.create({ kind: 'manual', createdBy: owner.id, passphrase: PASSPHRASE });
    await backups.run(run.id);
    expect(await backups.find(run.id)).toMatchObject({ status: 'ready' });
    archive = await readFile(backups.archivePath(run.id));

    const keys = generateRecipientKeyPair();
    recipientPrivateKey = keys.privateKey;
    await backups.saveSettings({ enabled: true, intervalHours: 6, retentionCount: 7, recipientPublicKey: keys.recipient });
    await backups.tick();
    const scheduled = (await backups.list()).find((candidate) => candidate.kind === 'scheduled' && candidate.status === 'queued');
    expect(scheduled).toBeDefined();
    await backups.run(scheduled!.id);
    expect(await backups.find(scheduled!.id)).toMatchObject({ status: 'ready' });
    recipientArchive = await readFile(backups.archivePath(scheduled!.id));
  });

  afterAll(async () => {
    await source?.drop();
  });

  it('restores a fresh instance from a streamed upload, then setup is done', async () => {
    if (!(await restoreToolsAvailable())) return;
    const target = await createTestDatabase();
    let restored = 0;
    // A cap far below the archive's size: the restore route must ignore it (its uploads are disk-bound).
    const ctx = await createTestApp(target, { maxUploadBytes: 64, onRestored: () => { restored += 1; } });
    try {
      expect((await ctx.app.inject({ method: 'GET', url: '/api/setup' })).json()).toEqual({ needed: true });
      const token = await ctx.setup.issue();
      expect(token).toBeTruthy();

      const response = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore',
        ...multipart({ token: token!, passphrase: PASSPHRASE }, { field: 'file', name: 'backup.divehub-backup', data: archive }),
      });
      expect(response.statusCode).toBe(200);
      expect(response.json()).toMatchObject({ restored: true });
      await vi.waitFor(() => expect(restored).toBe(1));

      expect((await ctx.app.inject({ method: 'GET', url: '/api/setup' })).json()).toEqual({ needed: false });
      const users = (await target.pool.query<{ email: string }>('select email from "user" order by email')).rows;
      expect(users.map((row) => row.email)).toContain(ADMIN_EMAIL);
      expect(await readFile(join(target.dataDir, ORIGINAL), 'utf8')).toBe('the original dive file');
      expect(await readFile(join(target.dataDir, 'auth-secret'), 'utf8')).toBe('the auth secret from the backup');
      // The streamed upload was cleaned up, not left in incoming/.
      expect(await readdir(join(target.dataDir, 'incoming')).catch(() => [])).toEqual([]);
    } finally {
      await ctx.app.close();
      await target.drop();
    }
  });

  it('restores a scheduled archive with the recipient private key', async () => {
    if (!(await restoreToolsAvailable())) return;
    const target = await createTestDatabase();
    const ctx = await createTestApp(target);
    try {
      const token = await ctx.setup.issue();
      const response = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore',
        ...multipart({ token: token!, recipientKey: recipientPrivateKey }, { field: 'file', name: 'scheduled.divehub-backup', data: recipientArchive }),
      });
      expect(response.statusCode).toBe(200);
      expect((await ctx.app.inject({ method: 'GET', url: '/api/setup' })).json()).toEqual({ needed: false });
      const users = (await target.pool.query<{ email: string }>('select email from "user"')).rows;
      expect(users.map((row) => row.email)).toContain(ADMIN_EMAIL);
    } finally {
      await ctx.app.close();
      await target.drop();
    }
  });

  it('restores from an archive the operator placed under /data/restore/', async () => {
    if (!(await restoreToolsAvailable())) return;
    const target = await createTestDatabase();
    const ctx = await createTestApp(target);
    try {
      await mkdir(join(target.dataDir, 'restore'), { recursive: true });
      await writeFile(join(target.dataDir, 'restore', 'nightly.divehub-backup'), archive);
      const token = await ctx.setup.issue();

      const listed = await ctx.app.inject({ method: 'GET', url: '/api/setup/restore/archives' });
      expect(listed.json()).toEqual({ archives: [{ name: 'nightly.divehub-backup', bytes: archive.byteLength }] });

      const response = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore',
        ...multipart({ token: token!, passphrase: PASSPHRASE, placed: 'nightly.divehub-backup' }),
      });
      expect(response.statusCode).toBe(200);
      expect((await ctx.app.inject({ method: 'GET', url: '/api/setup' })).json()).toEqual({ needed: false });
      const users = (await target.pool.query<{ email: string }>('select email from "user"')).rows;
      expect(users.map((row) => row.email)).toContain(ADMIN_EMAIL);
    } finally {
      await ctx.app.close();
      await target.drop();
    }
  });

  it('refuses a wrong, expired or missing setup token before restoring anything', async () => {
    const target = await createTestDatabase();
    const ctx = await createTestApp(target);
    try {
      const noToken = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore', ...multipart({ passphrase: PASSPHRASE }),
      });
      expect(noToken.statusCode).toBe(403);
      expect(noToken.json()).toMatchObject({ code: 'setup_token_invalid' });

      const wrong = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore', ...multipart({ token: 'not-the-issued-token-0123456', passphrase: PASSPHRASE }),
      });
      expect(wrong.statusCode).toBe(403);

      // A good token is only usable while it lasts; moving the app's clock past its 24 h expires it.
      const token = await ctx.setup.issue();
      ctx.clock.skip(24 * 3600_000 + 1);
      const expired = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore', ...multipart({ token: token!, passphrase: PASSPHRASE }),
      });
      expect(expired.statusCode).toBe(403);
      expect((await ctx.app.inject({ method: 'GET', url: '/api/setup' })).json()).toEqual({ needed: true });
    } finally {
      await ctx.app.close();
      await target.drop();
    }
  });

  it('answers setup_done once a User exists', async () => {
    const target = await createTestDatabase();
    const ctx = await createTestApp(target);
    try {
      await createUser(createTestAuth(target.db), 'already-set-up@example.com', 'admin');
      const response = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore', ...multipart({ token: 'any-token-long-enough-0123456', passphrase: PASSPHRASE }),
      });
      expect(response.statusCode).toBe(409);
      expect(response.json()).toMatchObject({ code: 'setup_done' });
    } finally {
      await ctx.app.close();
      await target.drop();
    }
  });

  it('keeps the token usable after a missing source, a missing passphrase or a bad archive', async () => {
    const target = await createTestDatabase();
    // A cap below even the junk upload below: the route streams past it, unlike the ordinary upload routes.
    const ctx = await createTestApp(target, { maxUploadBytes: 16 });
    try {
      const token = await ctx.setup.issue();

      const noSource = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore', ...multipart({ token: token!, passphrase: PASSPHRASE }),
      });
      expect(noSource.statusCode).toBe(400);
      expect(noSource.json()).toMatchObject({ code: 'setup_restore_source_missing' });

      const noPassphrase = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore', ...multipart({ token: token!, placed: 'nightly.divehub-backup' }),
      });
      expect(noPassphrase.statusCode).toBe(400);
      expect(noPassphrase.json()).toMatchObject({ code: 'setup_restore_key_missing' });

      const missingArchive = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore', ...multipart({ token: token!, passphrase: PASSPHRASE, placed: 'nowhere.divehub-backup' }),
      });
      expect(missingArchive.statusCode).toBe(400);
      expect(missingArchive.json()).toMatchObject({ code: 'setup_restore_archive_missing' });

      const badArchive = await ctx.app.inject({
        method: 'POST', url: '/api/setup/restore',
        ...multipart({ token: token!, passphrase: PASSPHRASE }, { field: 'file', name: 'broken.divehub-backup', data: Buffer.alloc(1024, 1) }),
      });
      // Not 413: the 16-byte app cap does not apply here. The archive fails to decrypt, and the token goes back.
      expect(badArchive.statusCode).toBe(400);
      expect(badArchive.json()).toMatchObject({ code: 'setup_restore_decrypt_failed' });
      expect(await readdir(join(target.dataDir, 'incoming')).catch(() => [])).toEqual([]);

      // Every refusal put the one token back, so the operator can correct the form and try again.
      expect((await ctx.app.inject({ method: 'GET', url: '/api/setup' })).json()).toEqual({ needed: true });
    } finally {
      await ctx.app.close();
      await target.drop();
    }
  });
});
