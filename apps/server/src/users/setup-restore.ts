// The first-start restore seam (plan FAH-175 slice 3): users owns the setup screen, backups owns the restore core,
// and ADR 0053 keeps them from importing each other. compose.ts injects the implementation.

/** Why a restore was refused: a Problem code the setup route answers, without any secret. */
export type SetupRestoreFailure =
  | 'backup_key_mismatch'
  | 'backup_schema_newer'
  | 'backup_decrypt_failed'
  | 'backup_restore_failed'
  | 'backup_files_failed';

/** What the setup route needs from a restore; the manifest fields let it log what was restored. */
export type SetupRestoreOutcome =
  | { ok: true; manifest: { createdAt: string; migrationHead: string | null } }
  | { ok: false; code: SetupRestoreFailure; message: string };

/**
 * A backup archive the operator placed in `/data/restore/`, to avoid uploading multi-GB files in a browser.
 */
export interface PlacedArchive {
  name: string;
  bytes: number;
}

/** Declared here and implemented by BackupService (backups/ must not be imported by users/, ADR 0053). */
export interface SetupRestore {
  /** The archives waiting in the data volume's `restore/` directory. */
  placedArchives(): Promise<PlacedArchive[]>;
  /** The absolute path of a placed archive, or null when the name is not a plain file in that directory. */
  placedArchive(name: string): Promise<string | null>;
  /** Replace the database and files from an archive; never throws a refusal, it answers one. */
  restore(input: { archivePath: string; passphrase: string }): Promise<SetupRestoreOutcome>;
}
