import { createHash } from 'node:crypto';
import { gunzipSync } from 'node:zlib';
import { sql } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { backupRun } from '../src/db/schema.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, TEST_ENCRYPTION_KEY, type TestDatabase,
} from './support.js';

function parseTar(archive: Buffer): Map<string, string> {
  const files = new Map<string, string>();
  let offset = 0;
  while (offset + 512 <= archive.length) {
    const header = archive.subarray(offset, offset + 512);
    if (header.every((byte) => byte === 0)) break;
    const name = header.subarray(0, 100).toString('utf8').replace(/\0[\s\S]*$/, '');
    const size = Number.parseInt(header.subarray(124, 136).toString('ascii').replace(/\0/g, '').trim(), 8);
    files.set(name, archive.subarray(offset + 512, offset + 512 + size).toString('utf8'));
    offset += 512 + Math.ceil(size / 512) * 512;
  }
  return files;
}

describe.skipIf(!(await databaseReachable()))('the admin support-bundle API', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let admin: string;
  let member: string;

  const call = (method: 'GET', url: string, cookie?: string) =>
    ctx.app.inject({ method, url, headers: { ...(cookie ? { cookie } : {}), origin: BASE_URL } });

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'admin@example.com', 'admin');
    await createUser(ctx.auth, 'member@example.com', 'user');
    admin = await signIn(ctx.app, 'admin@example.com');
    member = await signIn(ctx.app, 'member@example.com');
    await t.db.execute(sql`select graphile_worker.add_job(
      'support-test-failed', json_build_object('userId', 'user-secret-1', 'path', '/data/private'), max_attempts => 3)`);
    await t.db.execute(sql`update graphile_worker._private_jobs as jobs
      set last_error = 'failed for ops@example.com', attempts = 2
      from graphile_worker._private_tasks as tasks
      where tasks.id = jobs.task_id and tasks.identifier = 'support-test-failed'`);
    await t.db.insert(backupRun).values({ kind: 'manual', status: 'failed', error: 'archive for ops@example.com failed' });
    ctx.logBuffer.write(`${JSON.stringify({ level: 30, time: Date.now(), msg: 'support seed contact ops@example.com from 203.0.113.9' })}\n`);
    ctx.logBuffer.write(`${JSON.stringify({ level: 50, time: Date.now(), msg: 'support seed with token', token: 'raw-token' })}\n`);
  });

  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('answers 401 without a session on both routes', async () => {
    expect((await call('GET', '/api/admin/support-bundle/preview')).statusCode).toBe(401);
    expect((await call('GET', '/api/admin/support-bundle/download')).statusCode).toBe(401);
  });

  it('answers 403 to a signed-in user who is not an admin', async () => {
    expect((await call('GET', '/api/admin/support-bundle/preview', member)).statusCode).toBe(403);
    expect((await call('GET', '/api/admin/support-bundle/download', member)).statusCode).toBe(403);
  });

  it('previews the summary an admin reviews', async () => {
    const preview = await call('GET', '/api/admin/support-bundle/preview', admin);
    expect(preview.statusCode).toBe(200);
    expect(preview.json()).toMatchObject({
      generatedAt: expect.stringMatching(/^\d{4}-\d{2}-\d{2}T/),
      appVersion: 'dev',
      schemaHead: expect.any(String),
      logs: { entries: 2, droppedEntries: 0, oldestAt: expect.any(String), newestAt: expect.any(String), bytes: expect.any(Number) },
      redaction: { keysCensored: expect.any(Number), emailsMasked: expect.any(Number), ipsMasked: expect.any(Number) },
      jobs: { failed: 2 },
      health: { uptimeSec: expect.any(Number), dbLatencyMs: expect.any(Number), workerRunning: false, queueDepth: expect.any(Number) },
      clientDiagnostics: { entries: 0 },
      estimatedBytes: expect.any(Number),
    });
    expect(preview.json().redaction.emailsMasked).toBeGreaterThanOrEqual(2);
    expect(preview.json().redaction.ipsMasked).toBeGreaterThanOrEqual(1);
    expect(preview.json().estimatedBytes).toBeGreaterThan(0);
  });

  it('downloads a gzip archive with the expected headers and members', async () => {
    const download = await call('GET', '/api/admin/support-bundle/download', admin);
    expect(download.statusCode).toBe(200);
    expect(download.headers['content-type']).toBe('application/gzip');
    expect(download.headers['content-disposition']).toMatch(/^attachment; filename="divehub-support-bundle-\d{8}-\d{6}\.tar\.gz"$/);
    const files = parseTar(gunzipSync(download.rawPayload));
    expect([...files.keys()]).toEqual(['manifest.json', 'logs.ndjson', 'config.json', 'jobs.json', 'health.json', 'client-diagnostics.ndjson']);
    const manifest = JSON.parse(files.get('manifest.json')!);
    expect(manifest).toMatchObject({ format: 'divehub-support-bundle', formatVersion: 2, appVersion: 'dev' });
    expect(manifest.sections.map((section: { name: string }) => section.name)).toEqual(['logs.ndjson', 'config.json', 'jobs.json', 'health.json', 'client-diagnostics.ndjson']);
    expect(files.get('client-diagnostics.ndjson')).toBe('');
  });

  it('masks the seeded e-mail, IP and token in the log member', async () => {
    const download = await call('GET', '/api/admin/support-bundle/download', admin);
    const logs = parseTar(gunzipSync(download.rawPayload)).get('logs.ndjson')!;
    const entries = logs.trim().split('\n').map((line) => JSON.parse(line));
    const seeded = entries.find((entry) => typeof entry.msg === 'string' && entry.msg.includes('support seed contact'));
    expect(seeded.msg).toBe('support seed contact ‹email› from ‹ip›');
    const tokenLine = entries.find((entry) => entry.token !== undefined);
    expect(tokenLine.token).toBe('‹redacted›');
    expect(logs).not.toContain('ops@example.com');
    expect(logs).not.toContain('203.0.113.9');
    expect(logs).not.toContain('raw-token');
  });

  it('masks DATABASE_URL credentials and fingerprints the encryption key', async () => {
    const download = await call('GET', '/api/admin/support-bundle/download', admin);
    const configText = parseTar(gunzipSync(download.rawPayload)).get('config.json')!;
    const config = JSON.parse(configText);
    const password = new URL(t.pool.options.connectionString!).password;
    expect(config.DATABASE_URL).toMatch(/^postgres:\/\/‹credentials›@/);
    expect(configText).not.toContain(password);
    expect(config.DIVEHUB_AUTH_SECRET).toBe(process.env.DIVEHUB_AUTH_SECRET ? 'set' : 'generated');
    expect(config.DIVEHUB_CONTACT).toBe(process.env.DIVEHUB_CONTACT ? 'set' : 'unset');
    const fingerprint = createHash('sha256').update(TEST_ENCRYPTION_KEY).digest('hex').slice(0, 16);
    expect(config.DIVEHUB_ENCRYPTION_KEY).toEqual({ sha256: fingerprint, plaintextCredentials: false });
    expect(configText).not.toContain(TEST_ENCRYPTION_KEY.toString('hex'));
  });

  it('includes failed jobs without their payload', async () => {
    const download = await call('GET', '/api/admin/support-bundle/download', admin);
    const jobsText = parseTar(gunzipSync(download.rawPayload)).get('jobs.json')!;
    const jobs = JSON.parse(jobsText);
    expect(jobs.available).toBe(true);
    expect(jobs.jobs).toContainEqual(expect.objectContaining({ task: 'support-test-failed', attempts: 2, maxAttempts: 3 }));
    expect(jobs.jobs[0].lastError).toContain('‹email›');
    expect(jobs.backups).toHaveLength(1);
    expect(jobs.backups[0]).toMatchObject({ kind: 'manual' });
    expect(jobs.backups[0].error).toContain('‹email›');
    expect(jobsText).not.toContain('"payload"');
    expect(jobsText).not.toContain('user-secret-1');
    expect(jobsText).not.toContain('/data/private');
  });

  it('includes redacted client diagnostics and counts them in the preview', async () => {
    const patched = await ctx.app.inject({
      method: 'PATCH', url: '/api/admin/client-diagnostics',
      headers: { cookie: admin, origin: BASE_URL }, payload: { enabled: true },
    });
    expect(patched.statusCode).toBe(200);

    const upload = await ctx.app.inject({
      method: 'POST', url: '/api/client-diagnostics',
      headers: { cookie: member, origin: BASE_URL },
      payload: {
        client: { name: 'web', version: '1.2.3' },
        capturedAt: new Date('2026-11-20T10:00:00.000Z').toISOString(),
        entries: [{
          occurredAt: new Date('2026-11-20T09:59:00.000Z').toISOString(),
          level: 'error', code: 'E_TEST', message: 'failure for ops@example.com from 203.0.113.9',
          context: { token: 'raw-token' },
        }],
      },
    });
    expect(upload.statusCode).toBe(204);

    const preview = await call('GET', '/api/admin/support-bundle/preview', admin);
    expect(preview.json().clientDiagnostics).toEqual({ entries: 1 });

    const download = await call('GET', '/api/admin/support-bundle/download', admin);
    const memberFile = parseTar(gunzipSync(download.rawPayload)).get('client-diagnostics.ndjson')!;
    const entries = memberFile.trim().split('\n').map((line) => JSON.parse(line));
    expect(entries).toHaveLength(1);
    expect(entries[0]).toMatchObject({
      client: { name: 'web', version: '1.2.3' },
      level: 'error', code: 'E_TEST', message: 'failure for ‹email› from ‹ip›',
      context: { token: '‹redacted›' },
    });
    expect(memberFile).not.toContain('ops@example.com');
    expect(memberFile).not.toContain('203.0.113.9');
    expect(memberFile).not.toContain('raw-token');
  });

  it('reports health stats and leaves DB-unavailable fields out of the bundle', async () => {
    const download = await call('GET', '/api/admin/support-bundle/download', admin);
    const health = JSON.parse(parseTar(gunzipSync(download.rawPayload)).get('health.json')!);
    expect(health).toMatchObject({
      available: true, workerRunning: false, queueDepth: expect.any(Number), dbLatencyMs: expect.any(Number), nodeVersion: process.version,
    });
    expect(health.memory).toEqual({ rssBytes: expect.any(Number), heapUsedBytes: expect.any(Number) });
  });
});
