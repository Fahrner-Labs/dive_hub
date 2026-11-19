// The client version gate (ADR 0076): the Android User-Agent parser, the route registry, the onRequest hook and the
// request log line. No database: every route here answers without one.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import type { FastifyRequest } from 'fastify';
import { buildApp } from '../src/app.js';
import { auth } from '../src/auth/cli-config.js';
import { createServices } from '../src/compose.js';
import type { Db } from '../src/db/client.js';
import { PROBLEMS, PROBLEM_CODES } from '../src/http/problems.js';
import { createSsiAdapter } from '../src/providers/ssi/ssi-adapter.js';
import type { SsiClient } from '../src/providers/ssi/ssi-client.js';
import { createSecretBox } from '../src/secrets/secret-box.js';
import type { SiteSourceAdapter } from '../src/sites/import/site-source.js';
import type { ImportSource } from '../src/sites/sources.js';
import type { BlobStore } from '../src/storage/blob-store.js';
import type { ClientVersionRegistry } from '../src/version/min-client-versions.js';
import { clientRequestSerializer } from '../src/version/request-log.js';
import { compareSemanticVersions, parseAndroidUserAgent, parseSemanticVersion } from '../src/version/user-agent.js';

/** The services as health.test.ts builds them: none of these routes touches the database. */
const services = () => createServices({
  db: {} as Db, blobs: {} as BlobStore, auth, secrets: createSecretBox(undefined), dataDir: '/tmp', databaseUrl: 'postgres://unused', siteSources: {} as Record<ImportSource, SiteSourceAdapter>,
  adapters: [createSsiAdapter({ client: {} as SsiClient })],
});

const appWith = (clientVersions?: ClientVersionRegistry) => buildApp({
  ...services(), baseUrl: 'http://localhost', maxUploadBytes: 1, ...(clientVersions && { clientVersions }),
});

const version = (text: string) => {
  const parsed = parseSemanticVersion(text);
  if (!parsed) throw new Error(`test version ${text} does not parse`);
  return parsed;
};

describe('parseAndroidUserAgent', () => {
  it('reads exactly divehub-android/<semver>', () => {
    expect(parseAndroidUserAgent('divehub-android/1.2.3')?.text).toBe('1.2.3');
    expect(parseAndroidUserAgent('divehub-android/1.2.3-rc.1')?.text).toBe('1.2.3-rc.1');
    expect(parseAndroidUserAgent('divehub-android/1.2.3-rc.1+build.5')?.text).toBe('1.2.3-rc.1+build.5');
    expect(parseAndroidUserAgent('divehub-android/0.0.1')?.text).toBe('0.0.1');
  });

  it('never recognises a missing, malformed, overlong or different client', () => {
    const garbage = [
      undefined, null, '', 42, 'divehub-android', 'divehub-android/', 'divehub-android/1.2', 'divehub-android/01.2.3',
      'DiveHub-Android/1.2.3', 'divehub-android/1.2.3 (Linux; Android 15)', 'divehub-android/1.2.3\n',
      'Mozilla/5.0 (Linux; Android 15)', `divehub-android/${'1'.repeat(200)}`,
    ];
    for (const userAgent of garbage) expect(parseAndroidUserAgent(userAgent)).toBeNull();
  });
});

describe('compareSemanticVersions', () => {
  it('orders releases and prereleases by SemVer', () => {
    expect(compareSemanticVersions(version('1.10.0'), version('1.9.9'))).toBeGreaterThan(0);
    expect(compareSemanticVersions(version('1.2.3-rc.2'), version('1.2.3-rc.10'))).toBeLessThan(0);
    expect(compareSemanticVersions(version('1.2.3-rc.1'), version('1.2.3'))).toBeLessThan(0);
    expect(compareSemanticVersions(version('1.2.3+build.5'), version('1.2.3'))).toBe(0);
    expect(compareSemanticVersions(version('1.2.3-alpha'), version('1.2.3-alpha.1'))).toBeLessThan(0);
    expect(compareSemanticVersions(version('1.2.3-1'), version('1.2.3-alpha'))).toBeLessThan(0);
    expect(compareSemanticVersions(version('1.2.3-alpha.beta'), version('1.2.3-beta'))).toBeLessThan(0);
  });
});

describe('client version gate', () => {
  const registry: ClientVersionRegistry = {
    '/api/health/live': { minimum: '2.0.0', reason: 'a test break', since: '2026-11-19' },
    '/api/dives/:id': { minimum: '2.0.0', reason: 'a test break', since: '2026-11-19' },
  };
  let emptyApp: Awaited<ReturnType<typeof buildApp>>;
  let gatedApp: Awaited<ReturnType<typeof buildApp>>;
  const android = (v: string) => ({ 'user-agent': `divehub-android/${v}` });
  const diveId = '00000000-0000-4000-8000-000000000000';

  beforeAll(async () => {
    emptyApp = await appWith();
    gatedApp = await appWith(registry);
  });
  afterAll(async () => {
    await emptyApp?.close();
    await gatedApp?.close();
  });

  it('changes nothing while the production registry is empty', async () => {
    const response = await emptyApp.inject({ method: 'GET', url: '/api/health/live', headers: android('1.0.0') });
    expect(response.statusCode).toBe(200);
  });

  it('answers 400 client_too_old only to an affected recognised version', async () => {
    const response = await gatedApp.inject({ method: 'GET', url: '/api/health/live', headers: android('1.9.9') });
    expect(response.statusCode).toBe(400);
    expect(response.json()).toEqual({ code: 'client_too_old', error: PROBLEMS.client_too_old });
    for (const v of ['2.0.0', '2.0.1', '2.1.0-rc.1']) {
      expect((await gatedApp.inject({ method: 'GET', url: '/api/health/live', headers: android(v) })).statusCode).toBe(200);
    }
  });

  it('never gates an unrecognised or missing User-Agent', async () => {
    for (const headers of [{}, { 'user-agent': 'Mozilla/5.0 (Linux; Android 15)' }]) {
      expect((await gatedApp.inject({ method: 'GET', url: '/api/health/live', headers })).statusCode).toBe(200);
    }
  });

  it('matches the route template, not the raw URL, and leaves unlisted routes alone', async () => {
    const affected = await gatedApp.inject({ method: 'GET', url: `/api/dives/${diveId}`, headers: android('1.9.9') });
    expect(affected.statusCode).toBe(400);
    expect(affected.json().code).toBe('client_too_old');
    // Without the gate this route is an ordinary 401: an unaffected recognised client is not bypassed by the UA.
    const ordinary = await gatedApp.inject({ method: 'GET', url: `/api/dives/${diveId}`, headers: android('2.0.0') });
    expect(ordinary.statusCode).toBe(401);
    expect(ordinary.json().code).toBe('sign_in_required');
    expect((await gatedApp.inject({ method: 'GET', url: '/api/openapi.json', headers: android('1.0.0') })).statusCode).toBe(200);
  });

  it('adds client_too_old to the problem codes and the OpenAPI document', async () => {
    expect(PROBLEM_CODES).toContain('client_too_old');
    const doc = (await emptyApp.inject({ method: 'GET', url: '/api/openapi.json' })).json() as {
      components: { schemas: { Problem: { properties: { code: { enum: string[] } } } } };
    };
    expect(doc.components.schemas.Problem.properties.code.enum).toContain('client_too_old');
  });
});

describe('clientRequestSerializer', () => {
  const request = (headers: Record<string, string>) => ({
    method: 'GET', url: '/api/health/live', headers, host: 'dive.example', ip: '192.0.2.7', socket: { remotePort: 1234 },
  }) as unknown as FastifyRequest;

  it('logs the parsed Android client beside the usual request fields', () => {
    const serialized = clientRequestSerializer(request({
      'user-agent': 'divehub-android/1.2.3-rc.1', 'accept-version': '1',
      authorization: 'Bearer secret-token', cookie: 'session=secret-cookie',
    }));
    expect(serialized).toEqual({
      method: 'GET', url: '/api/health/live', version: '1', host: 'dive.example', remoteAddress: '192.0.2.7', remotePort: 1234,
      client: { kind: 'android', version: '1.2.3-rc.1' },
    });
    expect(JSON.stringify(serialized)).not.toContain('secret-token');
    expect(JSON.stringify(serialized)).not.toContain('secret-cookie');
  });

  it('leaves the client field out for another or missing User-Agent', () => {
    expect(clientRequestSerializer(request({ 'user-agent': 'Mozilla/5.0' }))).not.toHaveProperty('client');
    expect(clientRequestSerializer(request({}))).not.toHaveProperty('client');
  });
});
