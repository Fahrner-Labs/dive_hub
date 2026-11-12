// The restore core (plan FAH-175), shared by the operator CLI and the first-start setup route: verify an encrypted
// `.divehub-backup` archive, then replace the database and the files under DIVEHUB_DATA_DIR. Streaming throughout:
// the archive is decrypted into tar, pg_restore reads the dump from disk, and tar unpacks the files.
import { execFile } from 'node:child_process';
import { mkdir, readFile, rm } from 'node:fs/promises';
import { join } from 'node:path';
import { randomUUID } from 'node:crypto';
import { promisify } from 'node:util';
import { errorMessage } from '../errors.js';
import {
  BackupArchiveError, encryptionKeyFingerprint, openArchiveStreamingWith, type BackupDecryptionKey, type BackupManifest,
} from './backup-archive.js';

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

/** pg_restore's own message when it has one: the operator needs to know what the database refused. */
function stderrOf(error: unknown): string {
  const stderr = (error as { stderr?: unknown }).stderr;
  return typeof stderr === 'string' && stderr.trim() !== '' ? stderr.trim() : errorMessage(error);
}

export function createRestoreService({ dataDir, databaseUrl, migrationsFolder, encryptionKey, log }: RestoreServiceDeps) {
  async function restore(input: RestoreInput): Promise<RestoreResult> {
    const workDir = join(dataDir, 'tmp', `restore-${randomUUID()}`);
    try {
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

      log.info(`restoring the database from the backup made ${manifest.createdAt}`);
      try {
        await execFileAsync('pg_restore', [
          '--clean', '--if-exists', '--no-owner', '--no-privileges', '--single-transaction', '-d', databaseUrl, databasePath,
        ], { maxBuffer: 1 << 20 });
      } catch (error) {
        throw new RestoreError('backup_restore_failed', `the database could not be restored: ${stderrOf(error)}`, { cause: error });
      }

      await mkdir(dataDir, { recursive: true });
      log.info('restoring the files');
      try {
        await execFileAsync('tar', ['-xzf', filesPath, '-C', dataDir], { maxBuffer: 1 << 20 });
      } catch (error) {
        throw new RestoreError('backup_files_failed', `the files could not be restored: ${stderrOf(error)}`, { cause: error });
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
