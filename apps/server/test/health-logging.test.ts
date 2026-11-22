// The health probes are polled every few seconds by the deployment: their request logs would crowd the support
// bundle's ring buffer out of the incident window (FAH-450), so they log nothing while ordinary requests still do.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { buildApp, createServer } from '../src/app.js';
import { auth } from '../src/auth/cli-config.js';
import { createServices } from '../src/compose.js';
import type { Db } from '../src/db/client.js';
import { createSsiAdapter } from '../src/providers/ssi/ssi-adapter.js';
import type { SsiClient } from '../src/providers/ssi/ssi-client.js';
import { createSecretBox } from '../src/secrets/secret-box.js';
import type { SiteSourceAdapter } from '../src/sites/import/site-source.js';
import type { ImportSource } from '../src/sites/sources.js';
import type { BlobStore } from '../src/storage/blob-store.js';
import { createLogRingBuffer } from '../src/support/log-ring-buffer.js';

/** The readiness probe asks the database; the liveness probe touches no service, so one stub answers both. */
const probeDb = { execute: async () => [] } as unknown as Db;

const services = (db: Db) => createServices({
  db, blobs: {} as BlobStore, auth, secrets: createSecretBox(undefined), dataDir: '/tmp', databaseUrl: 'postgres://unused',
  siteSources: {} as Record<ImportSource, SiteSourceAdapter>,
  adapters: [createSsiAdapter({ client: {} as SsiClient })],
});

describe('health probes and request logging', () => {
  const logBuffer = createLogRingBuffer({ stdout: { write: () => true } });
  let app: Awaited<ReturnType<typeof buildApp>>;

  beforeAll(async () => {
    const server = createServer({ logger: { level: 'info', stream: logBuffer } });
    app = await buildApp({ ...services(probeDb), baseUrl: 'http://localhost', maxUploadBytes: 1 }, server);
  });
  afterAll(async () => {
    await app?.close();
  });

  it('logs nothing for the liveness probe', async () => {
    const before = logBuffer.stats().entries;
    const response = await app.inject({ method: 'GET', url: '/api/health/live' });
    expect(response.statusCode).toBe(200);
    expect(logBuffer.stats().entries).toBe(before);
  });

  it('logs nothing for the readiness probe', async () => {
    const before = logBuffer.stats().entries;
    const response = await app.inject({ method: 'GET', url: '/api/health/ready' });
    expect(response.statusCode).toBe(200);
    expect(logBuffer.stats().entries).toBe(before);
  });

  it('still logs an ordinary request, so the probe assertions are not vacuous', async () => {
    const before = logBuffer.stats().entries;
    await app.inject({ method: 'GET', url: '/api/openapi.json' });
    expect(logBuffer.stats().entries).toBeGreaterThan(before);
  });
});
