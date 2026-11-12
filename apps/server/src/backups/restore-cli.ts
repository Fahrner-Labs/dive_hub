// The operator's restore command (plan FAH-175): `node dist/main.js restore-backup <archive> [--force]`.
// It refuses while the app's heartbeat is fresh, unless the operator overrides with --force, and takes the passphrase
// or a scheduled backup's recipient private key from the environment or a file so it never appears in the process
// list or the shell history.
import { readFile } from 'node:fs/promises';
import { errorMessage } from '../errors.js';
import { APP_LOCK_STALE_MS, probeAppLock, type AppLockProbe } from './app-lock.js';
import { BackupArchiveError } from './backup-archive.js';
import { createRestoreService, RestoreError, type RestoreInput, type RestoreResult } from './restore-service.js';

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
  restore?: (input: RestoreInput) => Promise<RestoreResult>;
  probeLock?: (dataDir: string) => Promise<AppLockProbe>;
}

const USAGE = 'usage: restore-backup <archive> [--force] [--passphrase-file <path> | --recipient-key-file <path>]';

interface Arguments {
  archive: string;
  force: boolean;
  passphraseFile?: string | undefined;
  recipientKeyFile?: string | undefined;
}

const FILE_OPTIONS: Record<string, 'passphraseFile' | 'recipientKeyFile'> = {
  '--passphrase-file': 'passphraseFile',
  '--recipient-key-file': 'recipientKeyFile',
};

function parseArguments(argv: string[], io: RestoreCliIo): Arguments | undefined {
  const parsed: { archive?: string; force: boolean; passphraseFile?: string | undefined; recipientKeyFile?: string | undefined } = { force: false };
  for (let index = 0; index < argv.length; index += 1) {
    const argument = argv[index]!;
    if (argument === '--force') {
      parsed.force = true;
      continue;
    }
    if (argument.startsWith('--')) {
      const field = FILE_OPTIONS[argument];
      if (!field) {
        io.err(`restore-backup: unknown option ${argument}\n${USAGE}`);
        return undefined;
      }
      const value = argv[index + 1];
      if (!value) {
        io.err(`restore-backup: ${argument} needs a path\n${USAGE}`);
        return undefined;
      }
      parsed[field] = value;
      index += 1;
      continue;
    }
    if (parsed.archive !== undefined) {
      io.err(`restore-backup: unexpected argument ${argument}\n${USAGE}`);
      return undefined;
    }
    parsed.archive = argument;
  }
  return finishedArguments(parsed, io);
}

/** The parsed options once the loop is done: an archive is required, and only one key option. */
function finishedArguments(
  parsed: { archive?: string; force: boolean; passphraseFile?: string | undefined; recipientKeyFile?: string | undefined },
  io: RestoreCliIo,
): Arguments | undefined {
  if (parsed.archive === undefined) {
    io.err(`restore-backup: ${USAGE}`);
    return undefined;
  }
  if (parsed.passphraseFile !== undefined && parsed.recipientKeyFile !== undefined) {
    io.err(`restore-backup: give one key, not both\n${USAGE}`);
    return undefined;
  }
  return { ...parsed, archive: parsed.archive };
}

/** A file's text with one trailing newline dropped; neither a passphrase nor a key is ever logged. */
async function fileText(path: string): Promise<string | undefined> {
  const text = await readFile(path, 'utf8');
  return text.replace(/\r?\n$/, '') || undefined;
}

type RestoreCredential = { passphrase: string } | { recipientKey: string };

/** The passphrase or recipient private key from a file or the environment. */
async function credentialOf({ passphraseFile, recipientKeyFile }: Arguments, env: NodeJS.ProcessEnv): Promise<RestoreCredential | undefined> {
  let text: string | undefined;
  if (recipientKeyFile !== undefined) {
    text = await fileText(recipientKeyFile);
    return text === undefined ? undefined : { recipientKey: text.trim() };
  }
  if (passphraseFile !== undefined) {
    text = await fileText(passphraseFile);
    return text === undefined ? undefined : { passphrase: text };
  }
  const recipientKey = env.DIVEHUB_BACKUP_RECIPIENT_KEY;
  if (recipientKey !== undefined && recipientKey !== '') return { recipientKey: recipientKey.trim() };
  const passphrase = env.DIVEHUB_BACKUP_PASSPHRASE;
  return passphrase !== undefined && passphrase !== '' ? { passphrase } : undefined;
}

export async function runRestoreCli(argv: string[], deps: RestoreCliDeps): Promise<number> {
  const io = deps.io ?? { out: (text) => console.log(text), err: (text) => console.error(text) };
  const args = parseArguments(argv, io);
  if (args === undefined) return 2;

  let credential: RestoreCredential | undefined;
  try {
    credential = await credentialOf(args, deps.env ?? process.env);
  } catch {
    io.err(`restore-backup: cannot read ${args.passphraseFile ?? args.recipientKeyFile}`);
    return 2;
  }
  if (credential === undefined) {
    io.err('restore-backup: no key; set DIVEHUB_BACKUP_PASSPHRASE or DIVEHUB_BACKUP_RECIPIENT_KEY, or use --passphrase-file / --recipient-key-file');
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
    const result = await restore({ archivePath: args.archive, ...credential });
    io.out(`restore-backup: restored the backup made ${result.manifest.createdAt} (schema ${result.manifest.migrationHead ?? 'none'}).`);
    io.out('restore-backup: start the app again; forward migrations bring the schema up to this image.');
    return 0;
  } catch (error) {
    if (error instanceof RestoreError || error instanceof BackupArchiveError) io.err(`restore-backup: ${error.message}`);
    else io.err(`restore-backup: ${errorMessage(error)}`);
    return 1;
  }
}
