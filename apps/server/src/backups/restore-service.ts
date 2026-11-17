// The restore core (plan FAH-175), shared by the operator CLI and the first-start setup route: verify an encrypted
// `.divehub-backup` archive, then replace the database and the files under DIVEHUB_DATA_DIR. Streaming throughout:
// the archive is decrypted into tar, pg_restore reads the dump from disk, and tar unpacks the files.
import { execFile } from 'node:child_process';
import { mkdir, readFile, readdir, rm } from 'node:fs/promises';
import { join } from 'node:path';
import { randomUUID } from 'node:crypto';
import { promisify } from 'node:util';
import pg from 'pg';
import {
  BACKUP_SKIPPED_DIRS, BackupArchiveError, encryptionKeyFingerprint, openArchiveStreamingWith, type BackupDecryptionKey, type BackupManifest,
} from './backup-archive.js';
import { assertPgTool, pgCommand, pgFailureMessage, pgToolFailure } from './pg-command.js';

const execFileAsync = promisify(execFile);

/** Why a restore refused: the code the CLI and the setup route report, without leaking any secret. */
export type RestoreRefusalCode =
  | 'backup_key_mismatch'
  | 'backup_schema_newer'
  | 'backup_decrypt_failed'
  | 'backup_restore_failed'
  | 'backup_files_failed';

export class RestoreError extends Error {
  constructor(readonly code: RestoreRefusalCode, message: string, options?: ErrorOptions) {
    super(message, options);
    this.name = 'RestoreError';
  }
}

/** What the restore reports to the operator; the CLI prints it, the setup route logs it. */
export interface RestoreLog {
  info(message: string): void;
  warn(message: string): void;
}

export interface RestoreServiceDeps {
  /** The data volume: the temp work directory lives under `tmp/` and the files are unpacked here. */
  dataDir: string;
  databaseUrl: string;
  migrationsFolder: string;
  encryptionKey: Buffer | undefined;
  log: RestoreLog;
}

export interface RestoreInput {
  archivePath: string;
  /** A manual backup's passphrase. */
  passphrase?: string | undefined;
  /** A scheduled backup's age-style recipient private key (`AGE-SECRET-KEY-1…`). */
  recipientKey?: string | undefined;
}

/** The envelope key the archive's header asks for: the operator supplies either the passphrase or the private key. */
function decryptionKeyOf({ passphrase, recipientKey }: RestoreInput): BackupDecryptionKey {
  return passphrase !== undefined
    ? { kind: 'passphrase', passphrase }
    : { kind: 'recipient', privateKey: recipientKey ?? '' };
}

export interface RestoreResult {
  manifest: BackupManifest;
}

interface Journal {
  entries: { tag: string }[];
}

async function migrationsJournal(migrationsFolder: string): Promise<Journal> {
  return JSON.parse(await readFile(join(migrationsFolder, 'meta', '_journal.json'), 'utf8')) as Journal;
}

/** The Drizzle journal tag of the newest migration this image carries; null when it carries none. */
export async function imageMigrationHead(migrationsFolder: string): Promise<string | null> {
  const journal = await migrationsJournal(migrationsFolder);
  return journal.entries.at(-1)?.tag ?? null;
}

/**
 * Refuses a backup from a newer image. A head this image's journal knows is this image's or older: forward migrations
 * bring it up on the next boot. A head the journal does not know can only be newer (migrations are append-only).
 */
export async function verifySchemaHead(manifestHead: string | null, migrationsFolder: string): Promise<void> {
  if (manifestHead === null) return;
  const journal = await migrationsJournal(migrationsFolder);
  if (!journal.entries.some((entry) => entry.tag === manifestHead)) {
    throw new RestoreError(
      'backup_schema_newer',
      `this backup was made by a newer Dive Hub (schema ${manifestHead}); upgrade this image before restoring it`,
    );
  }
}

/** Refuses when the backup was made with a different DIVEHUB_ENCRYPTION_KEY; the stored secrets would not decrypt. */
export function verifyEncryptionKey(manifest: BackupManifest, encryptionKey: Buffer | undefined): void {
  const wanted = manifest.encryptionKeySha256 ?? null;
  const present = encryptionKeyFingerprint(encryptionKey);
  if (wanted === present) return;
  throw new RestoreError('backup_key_mismatch', wanted === null
    ? 'this backup was made without DIVEHUB_ENCRYPTION_KEY; unset it before restoring (its credentials are plain text)'
    : present === null
      ? 'this backup needs the DIVEHUB_ENCRYPTION_KEY it was made with; set it before restoring'
      : 'DIVEHUB_ENCRYPTION_KEY differs from the one this backup was made with; the stored credentials would be undecryptable');
}

/** Directories a restore must leave in place: the same ones the backup leaves out. */
const KEPT_ON_RESTORE = new Set(BACKUP_SKIPPED_DIRS);

/**
 * The non-system schemas a restore replaces. Graphile Worker recreates its own schema on start and is held during a
 * first-start restore, so nothing survives there that the archive needs to keep. `pg_dump` never recreates `public`
 * by itself on PostgreSQL 15+, hence the explicit drop and create.
 */
const REPLACED_SCHEMAS = ['graphile_worker', 'drizzle', 'public'] as const;

/** Wipes the instance's schema state, so the archive's dump lands without colliding with a newer image's objects. */
async function resetSchemas(databaseUrl: string, log: RestoreLog): Promise<void> {
  const client = new pg.Client({ connectionString: databaseUrl });
  // A lost connection must reach the awaited query (and its RestoreError), not the process as an uncaught exception.
  client.on('error', (error) => log.warn(`the restore database connection failed: ${error.message}`));
  await client.connect();
  try {
    for (const schema of REPLACED_SCHEMAS) await client.query(`drop schema if exists ${schema} cascade`);
    await client.query('create schema public');
  } finally {
    await client.end().catch(() => {});
  }
}

/** Replaces the managed data directory: remove what the archive does not own, then unpack its files over it. */
async function replaceFiles(dataDir: string, filesPath: string): Promise<void> {
  await mkdir(dataDir, { recursive: true });
  const entries = await readdir(dataDir, { withFileTypes: true });
  await Promise.all(entries
    .filter((entry) => !KEPT_ON_RESTORE.has(entry.name))
    .map((entry) => rm(join(dataDir, entry.name), { recursive: true, force: true })));
  await execFileAsync('tar', ['-xzf', filesPath, '-C', dataDir], { maxBuffer: 1 << 20 });
}

export function createRestoreService({ dataDir, databaseUrl, migrationsFolder, encryptionKey, log }: RestoreServiceDeps) {
  async function restore(input: RestoreInput): Promise<RestoreResult> {
    const workDir = join(dataDir, 'tmp', `restore-${randomUUID()}`);
    try {
      // Probe before anything is opened or replaced: a host without the client tool refuses with install guidance,
      // and a failure leaves the instance's database and files untouched.
      try {
        await assertPgTool('pg_restore');
      } catch (error) {
        throw new RestoreError('backup_restore_failed', pgToolFailure('pg_restore', error, databaseUrl), { cause: error });
      }
      let opened;
      try {
        opened = await openArchiveStreamingWith(input.archivePath, decryptionKeyOf(input), workDir);
      } catch (error) {
        if (error instanceof BackupArchiveError) throw error;
        throw new RestoreError(
          'backup_decrypt_failed',
          'the archive could not be decrypted: the passphrase or private key is wrong, or the archive is damaged',
          { cause: error },
        );
      }
      const { manifest, databasePath, filesPath } = opened;
      // Both refusals happen before anything is written: the instance stays as it was.
      verifyEncryptionKey(manifest, encryptionKey);
      await verifySchemaHead(manifest.migrationHead, migrationsFolder);
      if (manifest.plaintextCredentials) {
        log.warn('WARNING: this backup was made without DIVEHUB_ENCRYPTION_KEY; its stored Provider credentials are plain text in the restored database');
      }

      // Files first: if unpacking fails, the database is untouched and a first-start retry still works.
      log.info('restoring the files');
      try {
        await replaceFiles(dataDir, filesPath);
      } catch (error) {
        throw new RestoreError('backup_files_failed', `the files could not be restored: ${pgFailureMessage(error, databaseUrl)}`, { cause: error });
      }

      log.info(`restoring the database from the backup made ${manifest.createdAt}`);
      try {
        // Replace the schema state rather than clean individual objects: --clean only drops what the archive holds,
        // so a newer image's surviving tables and FK dependencies would break the restore. A failed run leaves the
        // database wiped; both paths restore into a disposable or explicitly replaced instance (ADR 0069 D5).
        await resetSchemas(databaseUrl, log);
        // As for pg_dump, the password goes through PGPASSWORD rather than a URL on the command line.
        const restore = pgCommand(databaseUrl);
        await execFileAsync('pg_restore', [
          '--no-owner', '--no-privileges', '--single-transaction', '-d', restore.url, databasePath,
        ], { maxBuffer: 1 << 20, env: restore.env });
      } catch (error) {
        throw new RestoreError('backup_restore_failed', `the database could not be restored: ${pgToolFailure('pg_restore', error, databaseUrl)}`, { cause: error });
      }

      log.info(`restore finished: ${manifest.files.count} file(s), schema ${manifest.migrationHead ?? 'none'}`);
      return { manifest };
    } finally {
      await rm(workDir, { recursive: true, force: true }).catch(() => {});
    }
  }

  return { restore };
}

export type RestoreService = ReturnType<typeof createRestoreService>;
