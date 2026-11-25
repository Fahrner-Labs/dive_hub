import { readFileSync } from 'node:fs';
import { resolve } from 'node:path';
import { parseEncryptionKey } from './secrets/secret-box.js';

/**
 * The public Firebase client config a native client initializes Firebase with (ADR 0085). None of these values is a
 * secret: they ship inside every Firebase app binary.
 */
export interface FcmClientConfig {
  projectId: string;
  applicationId: string;
  apiKey: string;
  gcmSenderId: string;
}

/** The service-account credential FCM sending uses (ADR 0085). It never leaves the server. */
export interface FcmServiceAccount {
  projectId: string;
  clientEmail: string;
  privateKey: string;
  /** The whole parsed JSON, because the HTTP v1 sender needs the remaining OAuth fields (task 2). */
  raw: Record<string, unknown>;
}

/** FCM push for native clients (ADR 0085): the send credential and the public client config of the same project. */
export interface FcmConfig {
  serviceAccount: FcmServiceAccount;
  android: FcmClientConfig;
}

export interface Config {
  databaseUrl: string;
  dataDir: string;
  host: string;
  port: number;
  /** Run the background worker inside this process (default) or not (separate worker service). */
  inProcessWorker: boolean;
  /** Directory with the built web client, served as static files when present. */
  webDir: string | undefined;
  maxUploadBytes: number;
  logLevel: string;
  production: boolean;
  /** Public URL users open in the browser, e.g. https://dives.example.com (ADR 0011). */
  baseUrl: string;
  /** Secret for signing auth cookies; generated into the data directory when not set. */
  authSecret: string | undefined;
  /** Reverse proxies (IPs or CIDR ranges) whose X-Forwarded-For is trusted for the client IP. */
  trustedProxies: string[];
  /** Site import (ADR 0021): the operator's contact for the User-Agent, and other service endpoints (e.g. a paid Overpass). */
  contact: string | undefined;
  overpassUrl: string | undefined;
  wikidataSparqlUrl: string | undefined;
  /** Where SSI's site list is downloaded (DIVEHUB_SSI_SITES_URL, ADR 0025). */
  ssiSitesUrl: string | undefined;
  /** Key for secrets kept for Targets (ADR 0024), e.g. SSI passwords; without it passwords are never kept. */
  encryptionKey: Buffer | undefined;
  /** SSI's app API; another endpoint only for tests. */
  ssiUrl: string | undefined;
  /** Garmin's Personal API; another endpoint only for tests. */
  garminUrl: string | undefined;
  /** FCM push for native clients (ADR 0085); undefined means push is off and clients keep polling. */
  fcm: FcmConfig | undefined;
}

/** A JSON object given inline or as the path to a file, with the variable's name in every error (ADR 0085). */
function readJsonObject(name: string, value: string): Record<string, unknown> {
  let text = value;
  if (!value.startsWith('{')) {
    try {
      text = readFileSync(resolve(value), 'utf8');
    } catch (error) {
      throw new Error(`${name} is neither inline JSON nor a readable JSON file: ${(error as Error).message}`, { cause: error });
    }
  }
  let parsed: unknown;
  try {
    parsed = JSON.parse(text);
  } catch {
    throw new Error(`${name} is not valid JSON`);
  }
  if (parsed === null || typeof parsed !== 'object' || Array.isArray(parsed)) throw new Error(`${name} must be a JSON object`);
  return parsed as Record<string, unknown>;
}

const requiredString = (name: string, value: Record<string, unknown>, field: string): string => {
  const found = value[field];
  if (typeof found !== 'string' || found.length === 0) throw new Error(`${name} needs a non-empty "${field}"`);
  return found;
};

/**
 * Read FCM configuration (ADR 0085): both variables or neither. A service account without the client config (or the
 * reverse) is an operator mistake, not "push off", so it stops the app with the missing variable's name.
 */
function readFcmConfig(get: (name: string) => string | undefined): FcmConfig | undefined {
  const accountValue = get('DIVEHUB_FCM_SERVICE_ACCOUNT');
  const clientValue = get('DIVEHUB_FCM_CLIENT_CONFIG');
  if (!accountValue && !clientValue) return undefined;
  if (!clientValue) throw new Error('DIVEHUB_FCM_SERVICE_ACCOUNT is set but DIVEHUB_FCM_CLIENT_CONFIG is not; set both or neither');
  if (!accountValue) throw new Error('DIVEHUB_FCM_CLIENT_CONFIG is set but DIVEHUB_FCM_SERVICE_ACCOUNT is not; set both or neither');

  const account = readJsonObject('DIVEHUB_FCM_SERVICE_ACCOUNT', accountValue);
  const client = readJsonObject('DIVEHUB_FCM_CLIENT_CONFIG', clientValue);
  return {
    serviceAccount: {
      projectId: requiredString('DIVEHUB_FCM_SERVICE_ACCOUNT', account, 'project_id'),
      clientEmail: requiredString('DIVEHUB_FCM_SERVICE_ACCOUNT', account, 'client_email'),
      privateKey: requiredString('DIVEHUB_FCM_SERVICE_ACCOUNT', account, 'private_key'),
      raw: account,
    },
    android: {
      projectId: requiredString('DIVEHUB_FCM_CLIENT_CONFIG', client, 'projectId'),
      applicationId: requiredString('DIVEHUB_FCM_CLIENT_CONFIG', client, 'applicationId'),
      apiKey: requiredString('DIVEHUB_FCM_CLIENT_CONFIG', client, 'apiKey'),
      gcmSenderId: requiredString('DIVEHUB_FCM_CLIENT_CONFIG', client, 'gcmSenderId'),
    },
  };
}

export function loadConfig(env: NodeJS.ProcessEnv = process.env): Config {
  // An empty variable counts as unset (Compose's `VAR=` gives one).
  const get = (name: string): string | undefined => env[name]?.trim() || undefined;
  const required = (name: string): string => {
    const value = get(name);
    if (!value) throw new Error(`Missing required environment variable ${name}`);
    return value;
  };
  const count = (name: string, fallback: number): number => {
    const value = Number(get(name) ?? fallback);
    if (!Number.isInteger(value) || value < 0) throw new Error(`${name} must be a whole number, not ${env[name]}`);
    return value;
  };
  const production = env.NODE_ENV === 'production';
  const port = count('DIVEHUB_PORT', 3000);
  const baseUrl = get('DIVEHUB_BASE_URL') ?? (production ? required('DIVEHUB_BASE_URL') : `http://localhost:${port}`);
  const webDir = get('DIVEHUB_WEB_DIR');
  return {
    databaseUrl: required('DATABASE_URL'),
    dataDir: resolve(get('DIVEHUB_DATA_DIR') ?? './data'),
    host: get('DIVEHUB_HOST') ?? '127.0.0.1',
    port,
    inProcessWorker: (get('DIVEHUB_WORKER') ?? 'in-process') === 'in-process',
    webDir: webDir ? resolve(webDir) : undefined,
    maxUploadBytes: count('DIVEHUB_MAX_UPLOAD_MB', 512) * 1024 * 1024,
    logLevel: get('DIVEHUB_LOG_LEVEL') ?? 'info',
    production,
    baseUrl: new URL(baseUrl).origin,
    authSecret: get('DIVEHUB_AUTH_SECRET'),
    trustedProxies: (get('DIVEHUB_TRUSTED_PROXIES') ?? '').split(',').map((s) => s.trim()).filter(Boolean),
    contact: get('DIVEHUB_CONTACT'),
    overpassUrl: get('DIVEHUB_OVERPASS_URL'),
    wikidataSparqlUrl: get('DIVEHUB_WIKIDATA_SPARQL_URL'),
    ssiSitesUrl: get('DIVEHUB_SSI_SITES_URL'),
    encryptionKey: parseEncryptionKey(env.DIVEHUB_ENCRYPTION_KEY),
    ssiUrl: get('DIVEHUB_SSI_URL'),
    garminUrl: get('DIVEHUB_GARMIN_URL'),
    fcm: readFcmConfig(get),
  };
}
