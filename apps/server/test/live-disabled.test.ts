// A server with no LISTEN connection must not offer the live channel (FAH-45): mounted, it would report healthy
// streams while no hint can arrive, and clients would suspend the polling that is their fallback (ADR 0066).
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
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

/** The services as write-openapi.ts builds them, over no database: the routes are described, none of them runs. */
const services = (live?: { connectionString: string }) => createServices({
  db: {} as Db, blobs: {} as BlobStore, auth, secrets: createSecretBox(undefined), siteSources: {} as Record<ImportSource, SiteSourceAdapter>,
  adapters: [createSsiAdapter({ client: {} as SsiClient })],
  ...(live && { live }),
});

describe('a server without a LISTEN connection', () => {
  let built: ReturnType<typeof services>;
  let app: Awaited<ReturnType<typeof buildApp>>;

  beforeAll(async () => {
    built = services();
    app = await buildApp({ ...built, baseUrl: 'http://localhost', maxUploadBytes: 1 });
  });
  afterAll(async () => {
    await app?.close();
  });

  it('does not mount GET /api/live, so clients keep polling', async () => {
    expect(built.live.enabled).toBe(false);
    expect(built.live.ready()).toBe(false);
    expect(app.hasRoute({ method: 'GET', url: '/api/live' })).toBe(false);
    const response = await app.inject({ method: 'GET', url: '/api/live' });
    expect(response.statusCode).toBe(404);
    expect(response.json()).toMatchObject({ code: 'not_found' });
  });
});

describe('a server with a LISTEN connection', () => {
  let built: ReturnType<typeof services>;
  let app: Awaited<ReturnType<typeof buildApp>>;

  beforeAll(async () => {
    built = services({ connectionString: 'postgres://divehub@127.0.0.1:5432/divehub' });
    app = await buildApp({ ...built, baseUrl: 'http://localhost', maxUploadBytes: 1 });
  });
  afterAll(async () => {
    await built.live.stop();
    await app?.close();
  });

  it('mounts the stream route, still refusing while LISTEN is not up', () => {
    expect(built.live.enabled).toBe(true);
    expect(built.live.ready()).toBe(false);
    expect(app.hasRoute({ method: 'GET', url: '/api/live' })).toBe(true);
  });
});
