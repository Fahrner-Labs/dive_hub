// The browser tests' databases (ADR 0023): one migrated template per migration state, cloned for each e2e server
// (`CREATE DATABASE ... TEMPLATE ...`) instead of migrating every worker. The template's name carries the key, so a
// new or changed migration builds a new template and can never be hidden by an old one.
import { createHash } from 'node:crypto';
import { readFileSync } from 'node:fs';
import { createRequire } from 'node:module';
import { dirname, join } from 'node:path';
import { readMigrationFiles } from 'drizzle-orm/migrator';
import pg from 'pg';
import { createDb } from '../src/db/client.js';
import { findChangedMigrations, migrateDatabase } from '../src/db/migrate.js';

/** Bumped when this setup changes in a way the migrations do not show; a cached template is then rebuilt. */
const TEMPLATE_FORMAT = 1;
/** A PostgreSQL advisory lock for building and cloning the templates; not the app's migration lock. */
const TEMPLATE_LOCK = 4_242_002;
const TEMPLATE_PREFIX = 'divehub_e2e_template';

/** A database name that needs no quoting. */
function identifier(name: string): string {
  if (!/^[a-z0-9_]+$/.test(name)) throw new Error(`a database name must be plain: ${name}`);
  return name;
}

/** Graphile Worker migrates its own schema when the template is built; a new version must build a new template. */
function workerVersion(): string {
  const require = createRequire(import.meta.url);
  const packageFile = join(dirname(require.resolve('graphile-worker')), '..', 'package.json');
  return (JSON.parse(readFileSync(packageFile, 'utf8')) as { version: string }).version;
}

/**
 * What a template can be reused for: the migrations' own hashes (a changed file changes its hash), this setup's format
 * and Graphile Worker's version, whose schema is migrated too.
 */
export function migrationKey(migrationsFolder: string): string {
  const hash = createHash('sha256');
  hash.update(`template:${TEMPLATE_FORMAT}\nworker:${workerVersion()}\n`);
  for (const migration of readMigrationFiles({ migrationsFolder })) hash.update(`${migration.folderMillis}:${migration.hash}\n`);
  return hash.digest('hex');
}

/** `divehub_e2e_template_<first 32 hex chars>`: PostgreSQL identifiers stop at 63 characters. */
export function templateDatabaseName(key: string, prefix = TEMPLATE_PREFIX): string {
  return identifier(`${prefix}_${key.slice(0, 32)}`);
}

const urlWithDatabase = (databaseUrl: string, name: string): string => {
  const url = new URL(databaseUrl);
  url.pathname = `/${name}`;
  return url.toString();
};

export interface E2eDatabase {
  /** The per-worker database, cloned from the template. */
  url: string;
  template: string;
  /** True when an earlier run built this template; false when this call built it. */
  reusedTemplate: boolean;
}

/**
 * Creates (or reuses) the migrated template and clones `databaseName` from it. Every caller takes the same advisory
 * lock, so concurrent e2e servers build the template once, run the migrations once for the run, and clone in turn.
 * A template whose key does not match is dropped and built again. The clone is checked against the migration files
 * before it is handed out.
 */
export async function createE2eDatabase(options: {
  databaseUrl: string;
  databaseName: string;
  migrationsFolder: string;
  /** Only tests change it, to keep their template apart. */
  templatePrefix?: string;
  /** The browser-test run the e2e servers share; a template's migrations run once per run. Defaults to the process. */
  runId?: string;
}): Promise<E2eDatabase> {
  const databaseName = identifier(options.databaseName);
  const runId = options.runId ?? String(process.pid);
  if (!/^[A-Za-z0-9_-]+$/.test(runId)) throw new Error(`a run id must be plain: ${runId}`);
  const key = migrationKey(options.migrationsFolder);
  const template = templateDatabaseName(key, options.templatePrefix);
  const admin = new pg.Client({ connectionString: options.databaseUrl });
  admin.on('error', (error) => console.error('PostgreSQL connection error', error));
  await admin.connect();
  try {
    await admin.query('select pg_advisory_lock($1)', [TEMPLATE_LOCK]);
    try {
      const reusedTemplate = await ensureTemplate(admin, options.databaseUrl, template, key, runId, options.migrationsFolder);
      await admin.query(`drop database if exists ${databaseName} with (force)`);
      await admin.query(`create database ${databaseName} template ${template}`);
      const url = urlWithDatabase(options.databaseUrl, databaseName);
      await checkClone(url, options.migrationsFolder);
      return { url, template, reusedTemplate };
    } finally {
      await admin.query('select pg_advisory_unlock($1)', [TEMPLATE_LOCK]).catch(() => {});
    }
  } finally {
    await admin.end();
  }
}

/**
 * Builds the template when it is missing or from other migrations. A template from this run is reused as it is; one
 * from an earlier run gets the migrations once more (a no-op unless the database drifted), so a run never trusts a
 * template it did not check. The comment is written only after every migration passed, so a run that stopped midway
 * leaves a template without the key and the next run rebuilds it.
 */
async function ensureTemplate(
  admin: pg.Client, databaseUrl: string, template: string, key: string, runId: string, migrationsFolder: string,
): Promise<boolean> {
  const { rows } = await admin.query<{ key: string | null }>(
    `select shobj_description(oid, 'pg_database') as key from pg_database where datname = $1`, [template]);
  const [builtFor, ranBy] = (rows[0]?.key ?? '').split(' ');
  if (builtFor === key && ranBy !== undefined) {
    if (ranBy !== runId) await migrateTemplate(databaseUrl, template, migrationsFolder, key, runId, admin);
    return true;
  }
  await admin.query(`drop database if exists ${template} with (force)`);
  await admin.query(`create database ${template}`);
  await migrateTemplate(databaseUrl, template, migrationsFolder, key, runId, admin);
  return false;
}

/** Runs the migrations once for this run, then records the key and run in the template's comment. */
async function migrateTemplate(
  databaseUrl: string, template: string, migrationsFolder: string, key: string, runId: string, admin: pg.Client,
): Promise<void> {
  const { db, pool } = createDb(urlWithDatabase(databaseUrl, template));
  try {
    await migrateDatabase(db, pool, migrationsFolder);
  } catch (error) {
    await pool.end().catch(() => {});
    await admin.query(`drop database if exists ${template} with (force)`).catch(() => {});
    throw error;
  }
  await pool.end();
  await admin.query(`comment on database ${template} is '${key} ${runId}'`);
}

/** A template must carry exactly the migrations on disk; a changed file would otherwise stop at the template. */
async function checkClone(databaseUrl: string, migrationsFolder: string): Promise<void> {
  const { pool } = createDb(databaseUrl);
  try {
    const changed = await findChangedMigrations(pool, migrationsFolder);
    if (changed.length > 0) throw new Error(`the e2e template is out of date: ${changed.join(', ')}`);
  } finally {
    await pool.end();
  }
}
