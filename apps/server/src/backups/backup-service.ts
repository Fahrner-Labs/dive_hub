// Manual and scheduled backups (ADR 0069, plan FAH-175): the `backup_runs` row, the queued worker job, and the run
// that dumps the database, tars the data directory and writes one encrypted archive.
import { createHash } from 'node:crypto';
import { execFile } from 'node:child_process';
import { createReadStream } from 'node:fs';
import { mkdir, readFile, readdir, rm, stat } from 'node:fs/promises';
import { join } from 'node:path';
import { promisify } from 'node:util';
import { and, desc, eq, gt, inArray, sql } from 'drizzle-orm';
import type { Db, Tx } from '../db/client.js';
import { backupRun, backupSettings } from '../db/schema.js';
import { PROBLEMS } from '../http/problems.js';
import type { BlobStore } from '../storage/blob-store.js';
import {
  BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, buildManifest, encryptionKeyFingerprint, fileTreeChecksum, isRecipient,
  packContainer, passphraseEncryption, recipientEncryption, tarDirectory, type BackupEncryption, type BackupPart,
} from './backup-archive.js';
import { createRestoreService, RestoreError, type RestoreLog } from './restore-service.js';

const execFileAsync = promisify(execFile);

/** The graphile-worker task that writes one backup; the payload carries the row's id. */
export const BACKUP_RUN_TASK = 'backup-run';

export type BackupRunRow = typeof backupRun.$inferSelect;

/** The scheduled-backup setting as the routes and the scheduler see it. */
export interface BackupSettingsValues {
  enabled: boolean;
  intervalHours: number;
  retentionCount: number;
  recipientPublicKey: string | null;
}

/** What a client sends: a missing or empty recipient clears it (null in the stored view). */
export interface BackupSettingsInput {
  enabled: boolean;
  intervalHours: number;
  retentionCount: number;
  recipientPublicKey?: string | null | undefined;
}

export const DEFAULT_BACKUP_SETTINGS: BackupSettingsValues = {
  enabled: false, intervalHours: 24, retentionCount: 7, recipientPublicKey: null,
};

export class BackupError extends Error {
  constructor(readonly code:
    | 'backup_not_found'
    | 'backup_in_progress'
    | 'backup_running'
    | 'backup_not_ready'
    | 'backup_file_missing'
    | 'backup_passphrase_missing'
    | 'backup_settings_invalid'
    | 'backup_recipient_missing'
    | 'backup_recipient_invalid'
  ) {
    super(PROBLEMS[code]);
  }
}

/** What a backup run logs (the app's logger, or a silent one where no backup can run). */
export interface BackupLog {
  info(detail: object, message: string): void;
  warn(detail: object, message: string): void;
  error(detail: object, message: string): void;
}

export interface BackupServiceDeps {
  db: Db;
  blobs: BlobStore;
  dataDir: string;
  databaseUrl: string;
  migrationsFolder: string;
  appVersion: string;
  encryptionKey: Buffer | undefined;
  log: BackupLog;
  /** The scheduler's clock (default: real time), so a test need not wait for an interval. */
  now?: (() => number) | undefined;
}

const isUniqueViolation = (error: unknown): boolean => {
  const code = (error as { code?: string }).code ?? (error as { cause?: { code?: string } }).cause?.code;
  return code === '23505';
};

/** The SHA-256 and size of a finished file, as the `backup_runs` row records them. */
async function fileDigest(path: string): Promise<BackupPart> {
  const hash = createHash('sha256');
  for await (const chunk of createReadStream(path)) hash.update(chunk as Buffer);
  return { sha256: hash.digest('hex'), bytes: (await stat(path)).size };
}

export function createBackupService({ db, blobs, dataDir, databaseUrl, migrationsFolder, appVersion, encryptionKey, log, now = Date.now }: BackupServiceDeps) {
  // A manual backup's passphrase travels only in this process's memory, from the request to the in-process worker;
  // it is never written to a table, a job payload or a log, and a restart therefore fails the run instead of leaking it.
  const passphrases = new Map<string, string>();

  // The setup screen's restore path (plan FAH-175 slice 3): the same core the operator CLI runs, with the app's logger.
  const restoreLog: RestoreLog = {
    info: (message) => log.info({}, message),
    warn: (message) => log.warn({}, message),
  };
  const restoreCore = createRestoreService({ dataDir, databaseUrl, migrationsFolder, encryptionKey, log: restoreLog });
  const restoreDir = join(dataDir, 'restore');
  /** A bare file name ending in the archive extension; nothing that could leave the restore directory. */
  const isPlacedName = (name: string) => name.endsWith(BACKUP_EXTENSION) && !name.includes('/') && !name.includes('\\') && name !== '.' && name !== '..';

  const keyOfRun = (id: string) => `backups/${id}${BACKUP_EXTENSION}`;

  /** Rows per public table: the manifest's spot check after a restore. */
  async function tableCounts(tx: Db | Tx): Promise<Record<string, number>> {
    const tables = (await tx.execute(sql`select table_name from information_schema.tables
      where table_schema = 'public' and table_type = 'BASE TABLE' order by table_name`)).rows as { table_name: string }[];
    const counts: Record<string, number> = {};
    for (const { table_name: name } of tables) {
      const quoted = `"${name.replaceAll('"', '""')}"`;
      const [count] = (await tx.execute(sql`select count(*)::int as n from ${sql.raw(`public.${quoted}`)}`)).rows as { n: number }[];
      counts[name] = count?.n ?? 0;
    }
    return counts;
  }

  /** The Drizzle journal tag of the newest migration the database has applied. */
  async function migrationHead(): Promise<string | null> {
    const [row] = (await db.execute(sql`select count(*)::int as n from drizzle.__drizzle_migrations`)).rows as { n: number }[];
    const applied = row?.n ?? 0;
    if (applied === 0) return null;
    const journal = JSON.parse(await readFile(join(migrationsFolder, 'meta', '_journal.json'), 'utf8')) as { entries: { tag: string }[] };
    return journal.entries[applied - 1]?.tag ?? null;
  }

  async function fail(id: string, error: string): Promise<void> {
    await db.update(backupRun)
      .set({ status: 'failed', error, updatedAt: new Date(), finishedAt: new Date() })
      .where(eq(backupRun.id, id));
  }

  const settingsOf = (row: typeof backupSettings.$inferSelect): BackupSettingsValues => ({
    enabled: row.enabled,
    intervalHours: row.intervalHours,
    retentionCount: row.retentionCount,
    recipientPublicKey: row.recipientPublicKey,
  });

  /** The one settings row, or the quiet defaults before an admin saved one. */
  async function readSettings(): Promise<BackupSettingsValues> {
    const [row] = await db.select().from(backupSettings).where(eq(backupSettings.id, true));
    return row ? settingsOf(row) : { ...DEFAULT_BACKUP_SETTINGS };
  }

  function checkSettings(input: BackupSettingsValues): void {
    if (!Number.isInteger(input.intervalHours) || input.intervalHours < 1 || input.intervalHours > 168) {
      throw new BackupError('backup_settings_invalid');
    }
    if (!Number.isInteger(input.retentionCount) || input.retentionCount < 1 || input.retentionCount > 365) {
      throw new BackupError('backup_settings_invalid');
    }
    if (input.recipientPublicKey !== null && input.recipientPublicKey !== undefined && !isRecipient(input.recipientPublicKey)) {
      throw new BackupError('backup_recipient_invalid');
    }
    if (input.enabled && (input.recipientPublicKey === null || input.recipientPublicKey === undefined || input.recipientPublicKey === '')) {
      throw new BackupError('backup_recipient_missing');
    }
  }

  async function saveSettings(input: BackupSettingsInput): Promise<BackupSettingsValues> {
    // A client that leaves the field out, sends null, or sends an empty string means no recipient.
    const normalized: BackupSettingsValues = {
      enabled: input.enabled,
      intervalHours: input.intervalHours,
      retentionCount: input.retentionCount,
      recipientPublicKey: input.recipientPublicKey ? input.recipientPublicKey : null,
    };
    checkSettings(normalized);
    const values = { ...normalized, updatedAt: new Date() };
    const [row] = await db.insert(backupSettings).values({ id: true, ...values })
      .onConflictDoUpdate({ target: backupSettings.id, set: values })
      .returning();
    log.info({ enabled: row!.enabled, intervalHours: row!.intervalHours, retentionCount: row!.retentionCount }, 'backup settings saved');
    return settingsOf(row!);
  }

  /**
   * Keeps the newest `retentionCount` scheduled runs and never the last successful one. Manual runs are never selected,
   * so the admin keeps every archive they asked for; an in-flight scheduled run is left for its worker.
   */
  async function sweepRetention(): Promise<number> {
    const { retentionCount } = await readSettings();
    const runs = await db.select({ id: backupRun.id, status: backupRun.status, createdAt: backupRun.createdAt })
      .from(backupRun).where(eq(backupRun.kind, 'scheduled')).orderBy(desc(backupRun.createdAt), desc(backupRun.id));
    const lastSuccessful = runs.find((run) => run.status === 'ready')?.id ?? null;
    let removed = 0;
    for (const [index, run] of runs.entries()) {
      if (index < retentionCount || run.id === lastSuccessful) continue;
      if (run.status === 'queued' || run.status === 'running') continue;
      await db.delete(backupRun).where(eq(backupRun.id, run.id));
      await blobs.delete(keyOfRun(run.id)).catch((error) => log.warn({ err: error, backupRunId: run.id }, 'could not remove a retained backup archive'));
      removed += 1;
    }
    if (removed > 0) log.info({ removed }, 'removed old scheduled backups');
    return removed;
  }

  /** A scheduled run is due when scheduling is on and no scheduled run started within the interval. */
  async function due(settings: BackupSettingsValues): Promise<boolean> {
    if (!settings.enabled || settings.recipientPublicKey === null) return false;
    const cutoff = new Date(now() - settings.intervalHours * 3_600_000);
    const [recent] = await db.select({ id: backupRun.id }).from(backupRun)
      .where(and(eq(backupRun.kind, 'scheduled'), gt(backupRun.createdAt, cutoff))).limit(1);
    return recent === undefined;
  }

  /**
   * Queues one scheduled run if it is due. The in-flight unique index makes two ticks agree on one row, and the row and
   * its job commit together (ADR 0004), as a manual run's do.
   */
  async function enqueueScheduled(): Promise<BackupRunRow | null> {
    const settings = await readSettings();
    if (!(await due(settings))) return null;
    let run: BackupRunRow | undefined;
    try {
      run = await db.transaction(async (tx) => {
        const [row] = await tx.insert(backupRun).values({ kind: 'scheduled' }).returning();
        await tx.execute(sql`select graphile_worker.add_job(
          ${BACKUP_RUN_TASK}, ${JSON.stringify({ backupRunId: row!.id })}::json, ${null}::text, ${null}::timestamptz,
          ${1}::integer, ${`backup-run:${row!.id}`}, ${0}::integer)`);
        return row!;
      });
    } catch (error) {
      // Another run is in flight (manual or scheduled): the tick has nothing to add.
      if (isUniqueViolation(error)) return null;
      throw error;
    }
    log.info({ backupRunId: run.id, intervalHours: settings.intervalHours }, 'scheduled backup queued');
    return run;
  }

  return {
    async create(input: { kind: BackupRunRow['kind']; createdBy?: string | undefined; passphrase: string }): Promise<BackupRunRow> {
      if (input.passphrase.length === 0) throw new BackupError('backup_passphrase_missing');
      let run: BackupRunRow | undefined;
      try {
        run = await db.transaction(async (tx) => {
          const [row] = await tx.insert(backupRun).values({ kind: input.kind, createdBy: input.createdBy ?? null }).returning();
          // The passphrase is available before the job can run, and the row plus its job commit together (ADR 0004).
          passphrases.set(row!.id, input.passphrase);
          await tx.execute(sql`select graphile_worker.add_job(
            ${BACKUP_RUN_TASK}, ${JSON.stringify({ backupRunId: row!.id })}::json, ${null}::text, ${null}::timestamptz,
            ${1}::integer, ${`backup-run:${row!.id}`}, ${0}::integer)`);
          return row!;
        });
      } catch (error) {
        if (run) passphrases.delete(run.id);
        if (isUniqueViolation(error)) throw new BackupError('backup_in_progress');
        throw error;
      }
      log.info({ backupRunId: run.id, kind: run.kind }, 'backup queued');
      return run;
    },

    /** The archives the operator placed in `/data/restore/`, for the setup screen's picker. */
    async placedArchives(): Promise<{ name: string; bytes: number }[]> {
      const entries = await readdir(restoreDir, { withFileTypes: true }).catch(() => []);
      const archives = await Promise.all(entries
        .filter((entry) => entry.isFile() && isPlacedName(entry.name))
        .map(async (entry) => ({ name: entry.name, bytes: (await stat(join(restoreDir, entry.name))).size })));
      return archives.sort((a, b) => a.name.localeCompare(b.name));
    },

    /** The absolute path of a placed archive; null for anything that is not a plain archive file there. */
    async placedArchive(name: string): Promise<string | null> {
      if (!isPlacedName(name)) return null;
      const path = join(restoreDir, name);
      return (await stat(path).catch(() => null))?.isFile() ? path : null;
    },

    /** The setup screen's restore: the shared core, reported as a refusal instead of an exception (users can't see it). */
    async restore({ archivePath, passphrase, recipientKey }: { archivePath: string; passphrase?: string | undefined; recipientKey?: string | undefined }) {
      try {
        const { manifest } = await restoreCore.restore({
          archivePath,
          ...(passphrase !== undefined ? { passphrase } : { recipientKey: recipientKey ?? '' }),
        });
        return { ok: true as const, manifest: { createdAt: manifest.createdAt, migrationHead: manifest.migrationHead } };
      } catch (error) {
        if (error instanceof RestoreError) return { ok: false as const, code: error.code, message: error.message };
        // An archive that will not decrypt or unpack is the same refusal to the operator as a wrong key.
        if (error instanceof BackupArchiveError) return { ok: false as const, code: 'backup_decrypt_failed' as const, message: error.message };
        throw error;
      }
    },

    /** The scheduled-backup setting; the quiet defaults before an admin saves one. */
    settings: readSettings,

    /** Saves the one settings row, after the route's and the key's own validation. */
    saveSettings,

    /** Keeps the newest scheduled runs; the tests call it directly, the worker after each successful scheduled run. */
    sweepRetention,

    /** The minute tick's part: queue a scheduled run when the setting says one is due. */
    async tick(): Promise<void> {
      await enqueueScheduled();
    },

    /** Every run, newest first. */
    async list(): Promise<BackupRunRow[]> {
      return db.select().from(backupRun).orderBy(desc(backupRun.createdAt), desc(backupRun.id));
    },

    async find(id: string): Promise<BackupRunRow | null> {
      const [row] = await db.select().from(backupRun).where(eq(backupRun.id, id));
      return row ?? null;
    },

    /** Where the archive is on disk; the download route streams it from here. */
    archivePath(id: string): string {
      return blobs.pathOf(keyOfRun(id));
    },

    async remove(id: string): Promise<void> {
      const row = await this.find(id);
      if (!row) throw new BackupError('backup_not_found');
      if (row.status === 'queued' || row.status === 'running') throw new BackupError('backup_running');
      await db.delete(backupRun).where(eq(backupRun.id, id));
      await blobs.delete(keyOfRun(id)).catch((error) => log.warn({ err: error, backupRunId: id }, 'could not remove a backup archive'));
    },

    /** A run cut off by a stopped process: its temp files are gone and its passphrase with them. */
    async reconcileOrphans(): Promise<number> {
      const reconciled = await db.update(backupRun)
        .set({ status: 'failed', error: 'the server stopped while this backup was running', updatedAt: new Date(), finishedAt: new Date() })
        .where(inArray(backupRun.status, ['running']))
        .returning({ id: backupRun.id });
      if (reconciled.length > 0) log.warn({ backupRunIds: reconciled.map((r) => r.id) }, 'marked interrupted backups as failed');
      return reconciled.length;
    },

    /** Writes one archive; the worker task calls this with the row it queued. */
    async run(id: string): Promise<void> {
      const row = await db.select().from(backupRun).where(eq(backupRun.id, id)).then((rows) => rows[0]);
      // A deleted row, or one already reconciled after a restart, has nothing to do.
      if (!row || row.status !== 'queued') return;
      let encryption: BackupEncryption;
      if (row.kind === 'scheduled') {
        const settings = await readSettings();
        if (settings.recipientPublicKey === null) {
          await fail(id, 'no recipient public key is configured for scheduled backups');
          return;
        }
        encryption = recipientEncryption(settings.recipientPublicKey);
      } else {
        const passphrase = passphrases.get(id);
        passphrases.delete(id);
        if (passphrase === undefined) {
          await fail(id, 'the passphrase was lost when the server restarted');
          return;
        }
        encryption = passphraseEncryption(passphrase);
      }
      await db.update(backupRun).set({ status: 'running', startedAt: new Date(), updatedAt: new Date() }).where(eq(backupRun.id, id));
      const workDir = join(dataDir, 'tmp', `backup-${id}`);
      try {
        await mkdir(workDir, { recursive: true });
        const databasePath = join(workDir, 'database.dump');
        await execFileAsync('pg_dump', ['-Fc', '--file', databasePath, databaseUrl], { maxBuffer: 1 << 20 });
        const filesPath = join(workDir, 'files.tar.gz');
        await tarDirectory(dataDir, filesPath, BACKUP_SKIPPED_DIRS);
        const [databasePart, filesPart, filesTree, counts, head] = await Promise.all([
          fileDigest(databasePath), fileDigest(filesPath), fileTreeChecksum(dataDir, BACKUP_SKIPPED_DIRS), tableCounts(db), migrationHead(),
        ]);
        // The manifest records the envelope the encryption will write; its salt and ephemeral key are chosen there.
        const manifest = buildManifest({
          appVersion, migrationHead: head, createdAt: new Date(), tableCounts: counts,
          files: filesTree, parts: { database: databasePart, files: filesPart }, encryptionKey,
          salt: Buffer.from(encryption.metadata.salt, 'base64'), encryption: encryption.metadata,
        });
        const container = await packContainer(workDir, manifest);
        const encrypted = await encryption.encrypt(container);
        const stored = await blobs.put(keyOfRun(id), encrypted.stream);
        await db.update(backupRun)
          .set({ status: 'ready', bytes: stored.sizeBytes, sha256: stored.sha256, error: null, updatedAt: new Date(), finishedAt: new Date() })
          .where(eq(backupRun.id, id));
        log.info({ backupRunId: id, bytes: stored.sizeBytes, encryptionKeySha256: encryptionKeyFingerprint(encryptionKey) }, 'backup ready');
        // Retention follows a successful scheduled run; a fault there must not turn the backup into a failure.
        if (row.kind === 'scheduled') {
          await sweepRetention().catch((error) => log.warn({ err: error, backupRunId: id }, 'could not sweep old scheduled backups'));
        }
      } catch (error) {
        log.error({ err: error, backupRunId: id }, 'backup failed');
        await fail(id, error instanceof Error ? error.message : 'the backup failed');
      } finally {
        await rm(workDir, { recursive: true, force: true }).catch(() => {});
      }
    },
  };
}

export type BackupService = ReturnType<typeof createBackupService>;
