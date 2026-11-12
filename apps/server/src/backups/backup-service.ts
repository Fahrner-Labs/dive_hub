// Manual and scheduled backups (ADR 0068, plan FAH-175): the `backup_runs` row, the queued worker job, and the run
// that dumps the database, tars the data directory and writes one encrypted archive.
import { createHash, randomBytes } from 'node:crypto';
import { execFile } from 'node:child_process';
import { createReadStream } from 'node:fs';
import { mkdir, readFile, rm, stat } from 'node:fs/promises';
import { join } from 'node:path';
import { promisify } from 'node:util';
import { desc, eq, inArray, sql } from 'drizzle-orm';
import type { Db, Tx } from '../db/client.js';
import { backupRun } from '../db/schema.js';
import { PROBLEMS } from '../http/problems.js';
import type { BlobStore } from '../storage/blob-store.js';
import {
  BACKUP_EXTENSION, buildManifest, encryptStream, encryptionKeyFingerprint, fileTreeChecksum, packContainer, tarDirectory,
  type BackupPart,
} from './backup-archive.js';

const execFileAsync = promisify(execFile);

/** The graphile-worker task that writes one backup; the payload carries the row's id. */
export const BACKUP_RUN_TASK = 'backup-run';

/** Directories the file tar leaves out: archives, our own temp files, a restore's uploads, and the app's ephemeral run files. */
export const BACKUP_SKIPPED_DIRS = ['backups', 'tmp', 'restore', 'run'];

export type BackupRunRow = typeof backupRun.$inferSelect;

export class BackupError extends Error {
  constructor(readonly code: 'backup_not_found' | 'backup_in_progress' | 'backup_running' | 'backup_not_ready' | 'backup_file_missing' | 'backup_passphrase_missing') {
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

export function createBackupService({ db, blobs, dataDir, databaseUrl, migrationsFolder, appVersion, encryptionKey, log }: BackupServiceDeps) {
  // A manual backup's passphrase travels only in this process's memory, from the request to the in-process worker;
  // it is never written to a table, a job payload or a log, and a restart therefore fails the run instead of leaking it.
  const passphrases = new Map<string, string>();

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
      const passphrase = passphrases.get(id);
      passphrases.delete(id);
      if (passphrase === undefined) {
        await fail(id, 'the passphrase was lost when the server restarted');
        return;
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
        // The manifest needs the salt the encryption header will carry, so it is chosen before packing.
        const salt = randomBytes(16);
        const manifest = buildManifest({
          appVersion, migrationHead: head, createdAt: new Date(), tableCounts: counts,
          files: filesTree, parts: { database: databasePart, files: filesPart }, encryptionKey, salt,
        });
        const container = await packContainer(workDir, manifest);
        const encrypted = await encryptStream(container, passphrase, { salt });
        const stored = await blobs.put(keyOfRun(id), encrypted.stream);
        await db.update(backupRun)
          .set({ status: 'ready', bytes: stored.sizeBytes, sha256: stored.sha256, error: null, updatedAt: new Date(), finishedAt: new Date() })
          .where(eq(backupRun.id, id));
        log.info({ backupRunId: id, bytes: stored.sizeBytes, encryptionKeySha256: encryptionKeyFingerprint(encryptionKey) }, 'backup ready');
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
