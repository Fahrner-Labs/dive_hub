// The liveness route reports the release's version (D4): the release workflow passes DIVEHUB_VERSION as a build ARG,
// the Dockerfile turns it into ENV, and the server reads it. Plain `docker build .` and compose.dev.yaml stay on 'dev'.
import { afterAll, afterEach, beforeAll, describe, expect, it } from 'vitest';
import { buildApp } from '../src/app.js';
import { auth } from '../src/auth/cli-config.js';
import { createServices } from '../src/compose.js';
import type { Db } from '../src/db/client.js';
import { createSsiAdapter } from '../src/providers/ssi/ssi-adapter.js';
import type { SsiClient } from '../src/providers/ssi/ssi-client.js';
import { createSecretBox } from '../src/secrets/secret-box.js';
import type { SiteSourceAdapter } from '../src/sites/import/site-source.js';
import type { ImportSource } from '../src/sites/sources.js';
import type { BlobStore } from '../src/storage/blob-store.js';

/** The services as write-openapi.ts builds them, over no database: this route touches none. */
const services = () => createServices({
  db: {} as Db, blobs: {} as BlobStore, auth, secrets: createSecretBox(undefined), dataDir: '/tmp', databaseUrl: 'postgres://unused', siteSources: {} as Record<ImportSource, SiteSourceAdapter>,
  adapters: [createSsiAdapter({ client: {} as SsiClient })],
});

describe('GET /api/health/live', () => {
  let app: Awaited<ReturnType<typeof buildApp>>;

  beforeAll(async () => {
    app = await buildApp({ ...services(), baseUrl: 'http://localhost', maxUploadBytes: 1 });
  });
  afterAll(async () => {
    await app?.close();
  });
  afterEach(() => {
    delete process.env.DIVEHUB_VERSION;
  });

  it("answers the default 'dev' when DIVEHUB_VERSION is unset", async () => {
    delete process.env.DIVEHUB_VERSION;
    const response = await app.inject({ method: 'GET', url: '/api/health/live' });
    expect(response.statusCode).toBe(200);
    expect(response.json()).toEqual({ status: 'ok', version: 'dev' });
  });

  it('answers the DIVEHUB_VERSION the image was built with', async () => {
    process.env.DIVEHUB_VERSION = '0.0.0-test';
    const response = await app.inject({ method: 'GET', url: '/api/health/live' });
    expect(response.statusCode).toBe(200);
    expect(response.json()).toEqual({ status: 'ok', version: '0.0.0-test' });
  });
});
