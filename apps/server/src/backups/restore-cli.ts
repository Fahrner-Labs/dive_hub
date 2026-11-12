// The operator's restore command (plan FAH-175): `node dist/main.js restore-backup <archive> [--force]`.
// It refuses while the app's heartbeat is fresh, unless the operator overrides with --force, and takes the passphrase
// from DIVEHUB_BACKUP_PASSPHRASE or a file so it never appears in the process list or the shell history.
import { readFile } from 'node:fs/promises';
import { errorMessage } from '../errors.js';
import { APP_LOCK_STALE_MS, probeAppLock, type AppLockProbe } from './app-lock.js';
import { BackupArchiveError } from './backup-archive.js';
import { createRestoreService, RestoreError, type RestoreResult } from './restore-service.js';

export interface RestoreCliIo {
  out(text: string): void;
  err(text: string): void;
}

export interface RestoreCliDeps {
  dataDir: string;
  databaseUrl: string;
  migrationsFolder: string;
  encryptionKey: Buffer | undefined;
  env?: NodeJS.ProcessEnv;
  io?: RestoreCliIo;
  /** The restore itself; tests replace it so the refusal paths need no database. */
  restore?: (input: { archivePath: string; passphrase: string }) => Promise<RestoreResult>;
  probeLock?: (dataDir: string) => Promise<AppLockProbe>;
}

const USAGE = 'usage: restore-backup <archive> [--force] [--passphrase-file <path>]';

interface Arguments {
  archive: string;
  force: boolean;
  passphraseFile?: string | undefined;
}

function parseArguments(argv: string[], io: RestoreCliIo): Arguments | undefined {
  const parsed: { archive?: string; force: boolean; passphraseFile?: string | undefined } = { force: false };
  for (let index = 0; index < argv.length; index += 1) {
    const argument = argv[index]!;
    if (argument === '--force') {
      parsed.force = true;
    } else if (argument === '--passphrase-file') {
      parsed.passphraseFile = argv[index + 1];
      index += 1;
      if (!parsed.passphraseFile) {
        io.err(`restore-backup: --passphrase-file needs a path\n${USAGE}`);
        return undefined;
      }
    } else if (argument.startsWith('--')) {
      io.err(`restore-backup: unknown option ${argument}\n${USAGE}`);
      return undefined;
    } else if (parsed.archive === undefined) {
      parsed.archive = argument;
    } else {
      io.err(`restore-backup: unexpected argument ${argument}\n${USAGE}`);
      return undefined;
    }
  }
  if (parsed.archive === undefined) {
    io.err(`restore-backup: ${USAGE}`);
    return undefined;
  }
  return { ...parsed, archive: parsed.archive };
}

/** The passphrase from a file (one trailing newline dropped) or the environment; neither is ever logged. */
async function passphraseOf({ passphraseFile }: Arguments, env: NodeJS.ProcessEnv): Promise<string | undefined> {
  if (passphraseFile !== undefined) {
    const text = await readFile(passphraseFile, 'utf8');
    return text.replace(/\r?\n$/, '') || undefined;
  }
  const value = env.DIVEHUB_BACKUP_PASSPHRASE;
  return value !== undefined && value !== '' ? value : undefined;
}

export async function runRestoreCli(argv: string[], deps: RestoreCliDeps): Promise<number> {
  const io = deps.io ?? { out: (text) => console.log(text), err: (text) => console.error(text) };
  const args = parseArguments(argv, io);
  if (args === undefined) return 2;

  let passphrase: string | undefined;
  try {
    passphrase = await passphraseOf(args, deps.env ?? process.env);
  } catch {
    io.err(`restore-backup: cannot read ${args.passphraseFile}`);
    return 2;
  }
  if (passphrase === undefined) {
    io.err('restore-backup: the passphrase is empty; set DIVEHUB_BACKUP_PASSPHRASE or use --passphrase-file');
    return 2;
  }

  const lock = await (deps.probeLock ?? probeAppLock)(deps.dataDir);
  if (lock.running) {
    const running = lock.pid === null
      ? 'the app is running'
      : `the app is running (pid ${lock.pid}, heartbeat ${Math.max(1, Math.round(lock.ageMs / 1000))} s old)`;
    if (!args.force) {
      io.err(`restore-backup: refusing: ${running}; stop the stack first. If it is already stopped (a stale lock), wait ${Math.round(APP_LOCK_STALE_MS / 1000)} s or pass --force.`);
      return 1;
    }
    io.err(`restore-backup: WARNING: ${running}; --force restores over it`);
  }

  const restore = deps.restore ?? createRestoreService({
    dataDir: deps.dataDir,
    databaseUrl: deps.databaseUrl,
    migrationsFolder: deps.migrationsFolder,
    encryptionKey: deps.encryptionKey,
    log: { info: (message) => io.out(`restore-backup: ${message}`), warn: (message) => io.err(`restore-backup: ${message}`) },
  }).restore;

  io.out(`restore-backup: restoring ${args.archive}`);
  try {
    const result = await restore({ archivePath: args.archive, passphrase });
    io.out(`restore-backup: restored the backup made ${result.manifest.createdAt} (schema ${result.manifest.migrationHead ?? 'none'}).`);
    io.out('restore-backup: start the app again; forward migrations bring the schema up to this image.');
    return 0;
  } catch (error) {
    if (error instanceof RestoreError || error instanceof BackupArchiveError) io.err(`restore-backup: ${error.message}`);
    else io.err(`restore-backup: ${errorMessage(error)}`);
    return 1;
  }
}
