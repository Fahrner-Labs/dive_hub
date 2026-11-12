// The admin backups API (ADR 0069): the 401/403 matrix, creating a manual run, its download, and delete.
import { execFile } from 'node:child_process';
import { promisify } from 'node:util';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { generateRecipientKeyPair } from '../src/backups/backup-archive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

const execFileAsync = promisify(execFile);

async function dumpToolAvailable(): Promise<boolean> {
  try {
    await execFileAsync('pg_dump', ['--version']);
    return true;
  } catch (error) {
    if (process.env.REQUIRE_DATABASE === '1') throw new Error('the backup route test needs pg_dump on PATH', { cause: error });
    return false;
  }
}

describe.skipIf(!(await databaseReachable()))('the admin backups API', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let admin: string;
  let member: string;

  const call = (method: 'GET' | 'POST' | 'PUT' | 'DELETE', url: string, cookie?: string, payload?: object) =>
    ctx.app.inject({
      method, url,
      headers: { ...(cookie ? { cookie } : {}), origin: BASE_URL },
      ...(payload ? { payload } : {}),
    });

  const backup = {
    id: '00000000-0000-7000-8000-000000000000',
    passphrase: 'a passphrase for the archive',
  };

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'admin@example.com', 'admin');
    await createUser(ctx.auth, 'member@example.com', 'user');
    admin = await signIn(ctx.app, 'admin@example.com');
    member = await signIn(ctx.app, 'member@example.com');
  });

  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('answers 401 without a session on every admin backup route', async () => {
    expect((await call('POST', '/api/admin/backups', undefined, { passphrase: backup.passphrase })).statusCode).toBe(401);
    expect((await call('GET', '/api/admin/backups')).statusCode).toBe(401);
    expect((await call('GET', `/api/admin/backups/${backup.id}/download`)).statusCode).toBe(401);
    expect((await call('DELETE', `/api/admin/backups/${backup.id}`)).statusCode).toBe(401);
    expect((await call('GET', '/api/admin/backups/settings')).statusCode).toBe(401);
    expect((await call('PUT', '/api/admin/backups/settings', undefined, { enabled: false, intervalHours: 24, retentionCount: 7, recipientPublicKey: null })).statusCode).toBe(401);
  });

  it('answers 403 to a signed-in user who is not an admin', async () => {
    expect((await call('POST', '/api/admin/backups', member, { passphrase: backup.passphrase })).statusCode).toBe(403);
    expect((await call('GET', '/api/admin/backups', member)).statusCode).toBe(403);
    expect((await call('GET', `/api/admin/backups/${backup.id}/download`, member)).statusCode).toBe(403);
    expect((await call('DELETE', `/api/admin/backups/${backup.id}`, member)).statusCode).toBe(403);
    expect((await call('GET', '/api/admin/backups/settings', member)).statusCode).toBe(403);
    expect((await call('PUT', '/api/admin/backups/settings', member, { enabled: false, intervalHours: 24, retentionCount: 7, recipientPublicKey: null })).statusCode).toBe(403);
  });

  it('reads the quiet defaults and validates a schedule before saving it', async () => {
    const initial = await call('GET', '/api/admin/backups/settings', admin);
    expect(initial.statusCode).toBe(200);
    expect(initial.json()).toEqual({ enabled: false, intervalHours: 24, retentionCount: 7, recipientPublicKey: null });

    const keys = generateRecipientKeyPair();
    const missing = await call('PUT', '/api/admin/backups/settings', admin, { enabled: true, intervalHours: 24, retentionCount: 7, recipientPublicKey: null });
    expect(missing.statusCode).toBe(400);
    expect(missing.json()).toMatchObject({ code: 'backup_recipient_missing' });
    const invalid = await call('PUT', '/api/admin/backups/settings', admin, { enabled: true, intervalHours: 24, retentionCount: 7, recipientPublicKey: 'age1nope' });
    expect(invalid.statusCode).toBe(400);
    expect(invalid.json()).toMatchObject({ code: 'backup_recipient_invalid' });
    expect((await call('PUT', '/api/admin/backups/settings', admin, { enabled: false, intervalHours: 0, retentionCount: 7, recipientPublicKey: null })).statusCode).toBe(400);
    expect((await call('PUT', '/api/admin/backups/settings', admin, { enabled: false, intervalHours: 24, retentionCount: 0, recipientPublicKey: null })).statusCode).toBe(400);

    const saved = await call('PUT', '/api/admin/backups/settings', admin, { enabled: true, intervalHours: 6, retentionCount: 3, recipientPublicKey: keys.recipient });
    expect(saved.statusCode).toBe(200);
    expect(saved.json()).toEqual({ enabled: true, intervalHours: 6, retentionCount: 3, recipientPublicKey: keys.recipient });
    expect((await call('GET', '/api/admin/backups/settings', admin)).json()).toEqual(saved.json());
  });

  it('needs a passphrase and refuses a second run while one is in flight', async () => {
    expect((await call('POST', '/api/admin/backups', admin, {})).statusCode).toBe(400);
    // The app's password minimum: an archive stored anywhere needs offline-crack resistance.
    expect((await call('POST', '/api/admin/backups', admin, { passphrase: 'fourteen-chars' })).statusCode).toBe(400);
    const created = await call('POST', '/api/admin/backups', admin, { passphrase: backup.passphrase });
    expect(created.statusCode).toBe(201);
    const run = created.json();
    expect(run).toMatchObject({ kind: 'manual', status: 'queued' });
    expect(JSON.stringify(run)).not.toContain(backup.passphrase);
    const second = await call('POST', '/api/admin/backups', admin, { passphrase: backup.passphrase });
    expect(second.statusCode).toBe(409);
    expect(second.json()).toMatchObject({ code: 'backup_in_progress' });
    const list = await call('GET', '/api/admin/backups', admin);
    expect(list.statusCode).toBe(200);
    expect(list.json()).toContainEqual(expect.objectContaining({ id: run.id, status: 'queued' }));
    expect((await call('GET', `/api/admin/backups/${run.id}/download`, admin)).statusCode).toBe(409);
  });

  it('downloads a ready archive and deletes it afterwards', async () => {
    if (!(await dumpToolAvailable())) return;
    const run = (await call('GET', '/api/admin/backups', admin)).json()[0] as { id: string };
    await ctx.backups.run(run.id);
    const ready = (await call('GET', '/api/admin/backups', admin)).json() as { id: string; status: string; sha256: string }[];
    expect(ready.find((r) => r.id === run.id)).toMatchObject({ status: 'ready', sha256: expect.stringMatching(/^[0-9a-f]{64}$/) });
    const download = await call('GET', `/api/admin/backups/${run.id}/download`, admin);
    expect(download.statusCode).toBe(200);
    expect(download.headers['content-type']).toBe('application/octet-stream');
    expect(download.headers['content-disposition']).toMatch(/attachment; filename="divehub-.*\.divehub-backup"/);
    expect(download.rawPayload.length).toBeGreaterThan(0);
    expect((await call('DELETE', `/api/admin/backups/${run.id}`, admin)).statusCode).toBe(204);
    expect((await call('DELETE', `/api/admin/backups/${run.id}`, admin)).statusCode).toBe(404);
  });
});
