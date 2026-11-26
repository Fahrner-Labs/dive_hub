// The configuration from the environment: defaults, and an empty variable counting as unset (as Docker Compose's
// `VAR=` and `${VAR:-}` give one).
import { mkdtemp, rm, writeFile } from 'node:fs/promises';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { describe, expect, it } from 'vitest';
import { loadConfig } from '../src/config.js';

const base = { DATABASE_URL: 'postgres://localhost/dives' };

describe('loadConfig', () => {
  it('uses the defaults when nothing is set', () => {
    const config = loadConfig(base);
    expect(config).toMatchObject({
      port: 3000, host: '127.0.0.1', maxUploadBytes: 512 * 1024 * 1024, logLevel: 'info', inProcessWorker: true,
      baseUrl: 'http://localhost:3000', authSecret: undefined, trustedProxies: [], production: false,
    });
  });

  it('takes an empty variable as unset', () => {
    const config = loadConfig({
      ...base, DIVEHUB_PORT: '', DIVEHUB_MAX_UPLOAD_MB: '', DIVEHUB_HOST: '', DIVEHUB_LOG_LEVEL: '', DIVEHUB_WORKER: '',
      DIVEHUB_BASE_URL: '', DIVEHUB_DATA_DIR: '', DIVEHUB_AUTH_SECRET: '', DIVEHUB_CONTACT: ' ', DIVEHUB_SITE_SOURCES: ' ',
    });
    expect(config).toEqual(loadConfig(base));
  });

  it('reads what is set', () => {
    const config = loadConfig({ ...base, DIVEHUB_PORT: '8080', DIVEHUB_MAX_UPLOAD_MB: '2', DIVEHUB_BASE_URL: 'https://dives.example.com/x', DIVEHUB_TRUSTED_PROXIES: ' 10.0.0.1, ,10.0.0.0/8' });
    expect(config).toMatchObject({ port: 8080, maxUploadBytes: 2 * 1024 * 1024, baseUrl: 'https://dives.example.com', trustedProxies: ['10.0.0.1', '10.0.0.0/8'] });
  });

  it('refuses a number that is none', () => {
    expect(() => loadConfig({ ...base, DIVEHUB_PORT: 'eighty' })).toThrow('DIVEHUB_PORT');
    expect(() => loadConfig({ ...base, DIVEHUB_MAX_UPLOAD_MB: '-1' })).toThrow('DIVEHUB_MAX_UPLOAD_MB');
  });

  it('requires the database and, in production, the public URL, from the environment it is given', () => {
    expect(() => loadConfig({ DATABASE_URL: '' })).toThrow('DATABASE_URL');
    expect(() => loadConfig({ ...base, NODE_ENV: 'production' })).toThrow('DIVEHUB_BASE_URL');
    expect(loadConfig({ ...base, NODE_ENV: 'production', DIVEHUB_BASE_URL: 'https://dives.example.com' }).baseUrl).toBe('https://dives.example.com');
  });
});

describe('loadConfig site sources', () => {
  it('offers every source when the variable is unset, the self-hosted default', () => {
    expect(loadConfig(base).siteSources).toEqual(['osm', 'wikidata', 'ssi']);
  });

  it('takes a subset in the vocabulary order', () => {
    expect(loadConfig({ ...base, DIVEHUB_SITE_SOURCES: 'ssi,wikidata' }).siteSources).toEqual(['wikidata', 'ssi']);
    expect(loadConfig({ ...base, DIVEHUB_SITE_SOURCES: ' wikidata ' }).siteSources).toEqual(['wikidata']);
  });

  it('names the unknown source and the valid ones at boot', () => {
    expect(() => loadConfig({ ...base, DIVEHUB_SITE_SOURCES: 'wikidata,openstreetmap' }))
      .toThrow('DIVEHUB_SITE_SOURCES names unknown source(s): openstreetmap; valid sources are osm, wikidata, ssi');
    expect(() => loadConfig({ ...base, DIVEHUB_SITE_SOURCES: ',' })).toThrow('DIVEHUB_SITE_SOURCES must name at least one source');
  });
});

describe('loadConfig FCM push config', () => {
  const serviceAccount = { project_id: 'divehub-test', client_email: 'push@divehub-test.iam.gserviceaccount.com', private_key: 'private-key' };
  const clientConfig = { projectId: 'divehub-test', applicationId: 'dev.divehub', apiKey: 'public-api-key', gcmSenderId: '123456789' };

  it('is off when neither FCM variable is set', () => {
    expect(loadConfig(base).fcm).toBeUndefined();
  });

  it('reads both values as inline JSON', () => {
    const config = loadConfig({
      ...base,
      DIVEHUB_FCM_SERVICE_ACCOUNT: JSON.stringify(serviceAccount),
      DIVEHUB_FCM_CLIENT_CONFIG: JSON.stringify(clientConfig),
    });
    expect(config.fcm?.android).toEqual(clientConfig);
    expect(config.fcm?.serviceAccount).toMatchObject({
      projectId: 'divehub-test', clientEmail: serviceAccount.client_email, privateKey: 'private-key',
    });
  });

  it('reads both values from files', async () => {
    const dir = await mkdtemp(join(tmpdir(), 'divehub-fcm-'));
    try {
      await writeFile(join(dir, 'account.json'), JSON.stringify(serviceAccount));
      await writeFile(join(dir, 'client.json'), JSON.stringify(clientConfig));
      const config = loadConfig({
        ...base,
        DIVEHUB_FCM_SERVICE_ACCOUNT: join(dir, 'account.json'),
        DIVEHUB_FCM_CLIENT_CONFIG: join(dir, 'client.json'),
      });
      expect(config.fcm?.android).toEqual(clientConfig);
      expect(config.fcm?.serviceAccount.projectId).toBe('divehub-test');
    } finally {
      await rm(dir, { recursive: true, force: true });
    }
  });

  it('refuses only one of the two values, so a half-configured instance does not look ready', () => {
    expect(() => loadConfig({ ...base, DIVEHUB_FCM_SERVICE_ACCOUNT: JSON.stringify(serviceAccount) }))
      .toThrow('DIVEHUB_FCM_CLIENT_CONFIG');
    expect(() => loadConfig({ ...base, DIVEHUB_FCM_CLIENT_CONFIG: JSON.stringify(clientConfig) }))
      .toThrow('DIVEHUB_FCM_SERVICE_ACCOUNT');
  });

  it('refuses a service account and client config from different projects', () => {
    expect(() => loadConfig({
      ...base,
      DIVEHUB_FCM_SERVICE_ACCOUNT: JSON.stringify({ ...serviceAccount, project_id: 'another-project' }),
      DIVEHUB_FCM_CLIENT_CONFIG: JSON.stringify(clientConfig),
    })).toThrow(/different Firebase projects/);
  });

  it('names the variable when a value is not usable', () => {
    expect(() => loadConfig({
      ...base, DIVEHUB_FCM_SERVICE_ACCOUNT: 'not json', DIVEHUB_FCM_CLIENT_CONFIG: JSON.stringify(clientConfig),
    })).toThrow('DIVEHUB_FCM_SERVICE_ACCOUNT');
    expect(() => loadConfig({
      ...base, DIVEHUB_FCM_SERVICE_ACCOUNT: JSON.stringify(serviceAccount), DIVEHUB_FCM_CLIENT_CONFIG: '{}',
    })).toThrow('DIVEHUB_FCM_CLIENT_CONFIG');
  });
});
