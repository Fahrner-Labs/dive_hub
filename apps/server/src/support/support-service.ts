import { createHash } from 'node:crypto';
import { sql } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { appliedMigrationHead } from '../db/migrate.js';
import {
  LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, type LogBufferStats, type LogRingBuffer,
} from './log-ring-buffer.js';
import { emptyRedactionCounters, redactValue, type RedactionCounters } from './redact.js';
import { buildSupportBundle, supportBundleFilename, type SupportBundle } from './support-bundle.js';

export interface ClientDiagnosticsProvider {
  entries(): Promise<unknown[]> | unknown[];
}

export interface SupportServiceDeps {
  db: Db;
  buffer: LogRingBuffer;
  migrationsFolder: string;
  appVersion: string;
  databaseUrl: string;
  dataDir: string;
  encryptionKey: Buffer | undefined;
  env?: NodeJS.ProcessEnv;
  workerRunning?: () => boolean;
  now?: () => Date;
  clientDiagnostics?: ClientDiagnosticsProvider;
}

interface FailedJob {
  task: string;
  attempts: number;
  maxAttempts: number;
  lastError: string | null;
  runAt: string | null;
}

interface FailedBackup {
  id: string;
  kind: string;
  createdAt: string | null;
  error: string | null;
}

interface JobsSection {
  available: boolean;
  note?: string;
  jobs?: FailedJob[];
  backups?: FailedBackup[];
}

interface HealthSection {
  available: boolean;
  note?: string;
  uptimeSec?: number;
  nodeVersion?: string;
  memory?: { rssBytes: number; heapUsedBytes: number };
  dbLatencyMs?: number;
  workerRunning?: boolean;
  queueDepth?: number;
}

export interface SupportPreview {
  generatedAt: string;
  appVersion: string;
  schemaHead: string | null;
  logs: LogBufferStats;
  redaction: RedactionCounters;
  jobs: { failed: number };
  health: { uptimeSec: number; dbLatencyMs: number | null; workerRunning: boolean; queueDepth: number | null };
  clientDiagnostics: { entries: number };
  estimatedBytes: number;
}

const iso = (value: unknown): string | null => {
  if (value instanceof Date) return value.toISOString();
  if (typeof value === 'string') return value;
  return null;
};

const maskDatabaseUrl = (value: string): string => {
  try {
    const url = new URL(value);
    return `postgres://‹credentials›@${url.hostname}${url.port ? `:${url.port}` : ''}${url.pathname}`;
  } catch {
    return '‹redacted›';
  }
};

export function createSupportService(deps: SupportServiceDeps) {
  const env = deps.env ?? process.env;
  const now = deps.now ?? (() => new Date());
  const workerRunning = deps.workerRunning ?? (() => false);

  const setting = (name: string): string | undefined => env[name]?.trim() || undefined;

  function configSection(): Record<string, unknown> {
    const port = setting('DIVEHUB_PORT') ?? '3000';
    const keyFingerprint = deps.encryptionKey === undefined
      ? null
      : createHash('sha256').update(deps.encryptionKey).digest('hex').slice(0, 16);
    return {
      DIVEHUB_HOST: setting('DIVEHUB_HOST') ?? '127.0.0.1',
      DIVEHUB_PORT: port,
      DIVEHUB_LOG_LEVEL: setting('DIVEHUB_LOG_LEVEL') ?? 'info',
      DIVEHUB_WORKER: setting('DIVEHUB_WORKER') ?? 'in-process',
      DIVEHUB_MAX_UPLOAD_MB: setting('DIVEHUB_MAX_UPLOAD_MB') ?? '512',
      DIVEHUB_BASE_URL: setting('DIVEHUB_BASE_URL') ?? `http://localhost:${port}`,
      DIVEHUB_TRUSTED_PROXIES: setting('DIVEHUB_TRUSTED_PROXIES') ?? null,
      DIVEHUB_DATA_DIR: setting('DIVEHUB_DATA_DIR') ?? deps.dataDir,
      DIVEHUB_OVERPASS_URL: setting('DIVEHUB_OVERPASS_URL') ?? null,
      DIVEHUB_WIKIDATA_SPARQL_URL: setting('DIVEHUB_WIKIDATA_SPARQL_URL') ?? null,
      DIVEHUB_SSI_SITES_URL: setting('DIVEHUB_SSI_SITES_URL') ?? null,
      DATABASE_URL: maskDatabaseUrl(deps.databaseUrl),
      DIVEHUB_AUTH_SECRET: setting('DIVEHUB_AUTH_SECRET') ? 'set' : 'generated',
      DIVEHUB_CONTACT: setting('DIVEHUB_CONTACT') ? 'set' : 'unset',
      DIVEHUB_ENCRYPTION_KEY: { sha256: keyFingerprint, plaintextCredentials: deps.encryptionKey === undefined },
    };
  }

  async function failedJobs(): Promise<JobsSection> {
    try {
      const jobs = (await deps.db.execute(sql`
        select task_identifier as task, attempts, max_attempts as "maxAttempts", last_error as "lastError", run_at as "runAt"
        from graphile_worker.jobs where last_error is not null order by run_at desc limit 50`)).rows as {
          task: string; attempts: number; maxAttempts: number; lastError: string | null; runAt: unknown;
        }[];
      const backups = (await deps.db.execute(sql`
        select id, kind, created_at as "createdAt", error
        from backup_runs where status = 'failed' order by created_at desc limit 20`)).rows as {
          id: string; kind: string; createdAt: unknown; error: string | null;
        }[];
      return {
        available: true,
        jobs: jobs.map((job) => ({
          task: job.task,
          attempts: job.attempts,
          maxAttempts: job.maxAttempts,
          lastError: job.lastError,
          runAt: iso(job.runAt),
        })),
        backups: backups.map((backup) => ({
          id: backup.id,
          kind: backup.kind,
          createdAt: iso(backup.createdAt),
          error: backup.error,
        })),
      };
    } catch {
      return { available: false, note: 'The database query failed; failed jobs are unavailable.' };
    }
  }

  async function healthSection(): Promise<HealthSection> {
    const uptimeSec = Math.round(process.uptime());
    const base = {
      uptimeSec,
      nodeVersion: process.version,
      memory: { rssBytes: process.memoryUsage().rss, heapUsedBytes: process.memoryUsage().heapUsed },
      workerRunning: workerRunning(),
    };
    try {
      const started = performance.now();
      await deps.db.execute(sql`select 1`);
      const dbLatencyMs = Math.max(0, Math.round(performance.now() - started));
      const [row] = (await deps.db.execute(sql`
        select count(*)::int as n from graphile_worker.jobs where locked_at is null`)).rows as { n: number }[];
      return { available: true, ...base, dbLatencyMs, queueDepth: row?.n ?? 0 };
    } catch {
      return { available: false, note: 'The database probe failed; health stats are unavailable.' };
    }
  }

  async function clientDiagnostics(counters: RedactionCounters): Promise<unknown[] | undefined> {
    if (!deps.clientDiagnostics) return undefined;
    try {
      const entries = await deps.clientDiagnostics.entries();
      return entries.map((entry) => redactValue(entry, undefined, counters));
    } catch {
      // Once wired, every v2 bundle carries the member: a failed read makes it empty, not absent.
      return [];
    }
  }

  async function assemble(): Promise<SupportBundle> {
    const stats = deps.buffer.stats();
    const counters: RedactionCounters = { ...emptyRedactionCounters(), ...deps.buffer.redactionCounters() };
    const config = redactValue(configSection(), undefined, counters, { keyAllowlist: new Set(['DIVEHUB_AUTH_SECRET', 'plaintextCredentials']) });
    const jobs = redactValue(await failedJobs(), undefined, counters);
    const health = await healthSection();
    const diagnostics = await clientDiagnostics(counters);
    const createdAt = now();
    return buildSupportBundle({
      createdAt,
      appVersion: deps.appVersion,
      schemaHead: await appliedMigrationHead(deps.db, deps.migrationsFolder).catch(() => null),
      logLevel: setting('DIVEHUB_LOG_LEVEL') ?? 'info',
      buffer: {
        capacityEntries: LOG_BUFFER_CAPACITY_ENTRIES,
        capacityBytes: LOG_BUFFER_CAPACITY_BYTES,
        entries: stats.entries,
        droppedEntries: stats.droppedEntries,
        oldestAt: stats.oldestAt,
        newestAt: stats.newestAt,
      },
      redaction: counters,
      logs: deps.buffer.snapshot(),
      config,
      jobs,
      health,
      ...(diagnostics !== undefined && { clientDiagnostics: diagnostics }),
    });
  }

  return {
    async buildPreview(): Promise<SupportPreview> {
      const bundle = await assemble();
      const stats = deps.buffer.stats();
      const jobs = bundle.members.find((member) => member.name === 'jobs.json');
      const jobRows = jobs ? JSON.parse(jobs.data) as JobsSection : { available: false };
      const healthMember = bundle.members.find((member) => member.name === 'health.json');
      const health = healthMember ? JSON.parse(healthMember.data) as HealthSection : { available: false };
      const diagnosticsMember = bundle.members.find((member) => member.name === 'client-diagnostics.ndjson');
      const diagnosticsEntries = diagnosticsMember && diagnosticsMember.data.trim() !== ''
        ? diagnosticsMember.data.trim().split('\n').length
        : 0;
      const failed = jobRows.available ? (jobRows.jobs?.length ?? 0) + (jobRows.backups?.length ?? 0) : 0;
      return {
        generatedAt: bundle.manifest.createdAt,
        appVersion: bundle.manifest.appVersion,
        schemaHead: bundle.manifest.schemaHead,
        logs: stats,
        redaction: bundle.manifest.redaction,
        jobs: { failed },
        health: {
          uptimeSec: health.uptimeSec ?? Math.round(process.uptime()),
          dbLatencyMs: health.available ? (health.dbLatencyMs ?? null) : null,
          workerRunning: health.workerRunning ?? workerRunning(),
          queueDepth: health.available ? (health.queueDepth ?? null) : null,
        },
        clientDiagnostics: { entries: diagnosticsEntries },
        estimatedBytes: bundle.archive.length,
      };
    },

    async buildBundle(): Promise<{ filename: string; bytes: Buffer }> {
      const bundle = await assemble();
      return { filename: supportBundleFilename(new Date(bundle.manifest.createdAt)), bytes: bundle.archive };
    },
  };
}

export type SupportService = ReturnType<typeof createSupportService>;
