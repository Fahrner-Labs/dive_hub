// The `.divehub-backup` container (ADR 0068, plan FAH-175): a manifest, a pg_dump custom-format dump and a gzipped tar
// of the data directory, read as one tar and encrypted as one AES-256-GCM stream under a passphrase-derived key.
import { createCipheriv, createDecipheriv, createHash, randomBytes, scrypt as scryptCallback, type ScryptOptions } from 'node:crypto';
import { execFile } from 'node:child_process';
import { createReadStream, createWriteStream } from 'node:fs';
import { mkdir, open, readdir, readFile, rm, stat, writeFile } from 'node:fs/promises';
import { join, relative, sep } from 'node:path';
import { Readable } from 'node:stream';
import { promisify } from 'node:util';
import { pipeline } from 'node:stream/promises';

const scrypt = promisify(scryptCallback) as (password: string | Buffer, salt: Buffer, keylen: number, options: ScryptOptions) => Promise<Buffer>;
const execFileAsync = promisify(execFile);

/** Bumped when the container or manifest changes in a way an older reader cannot take. */
export const BACKUP_FORMAT_VERSION = 1;
export const BACKUP_EXTENSION = '.divehub-backup';

const MANIFEST_NAME = 'manifest.json';
const DATABASE_NAME = 'database.dump';
const FILES_NAME = 'files.tar.gz';
const CONTAINER_NAME = 'container.tar';

const MAGIC = Buffer.from('DIVEHUB BACKUP\n', 'ascii');
const KDF_SCRYPT = 1;
const SALT_BYTES = 16;
const IV_BYTES = 12;
const TAG_BYTES = 16;
const HEADER_BYTES = MAGIC.length + 1 + 1 + 4 + 4 + 4 + SALT_BYTES + IV_BYTES;

/** scrypt's default cost: about 16 MiB and a fraction of a second, enough for an operator-chosen passphrase. */
const SCRYPT = { n: 16_384, r: 8, p: 1 };

export class BackupArchiveError extends Error {
  constructor(message: string, options?: ErrorOptions) {
    super(message, options);
    this.name = 'BackupArchiveError';
  }
}

export interface BackupPart {
  sha256: string;
  bytes: number;
}

export interface BackupManifest {
  formatVersion: number;
  appVersion: string;
  /** Drizzle journal tag of the newest migration the dumped database has applied. */
  migrationHead: string | null;
  createdAt: string;
  /** Rows per public base table at dump time; the restore's spot check. */
  tableCounts: Record<string, number>;
  /** A hash over the data directory's relative paths and sizes (never their contents; files.tar.gz covers those). */
  files: { sha256: string; count: number };
  parts: { database: BackupPart; files: BackupPart };
  /** SHA-256 of DIVEHUB_ENCRYPTION_KEY, never the key; null when the operator set none. */
  encryptionKeySha256: string | null;
  /** True when the instance keeps Provider credentials as plain text because DIVEHUB_ENCRYPTION_KEY is unset. */
  plaintextCredentials: boolean;
  encryption: {
    scheme: 'aes-256-gcm';
    kdf: 'scrypt';
    n: number;
    r: number;
    p: number;
    salt: string;
  };
}

export interface ManifestInput {
  appVersion: string;
  migrationHead: string | null;
  createdAt: Date;
  tableCounts: Record<string, number>;
  files: { sha256: string; count: number };
  parts: { database: BackupPart; files: BackupPart };
  encryptionKey: Buffer | undefined;
  salt: Buffer;
}

/** The manifest a new archive carries; the encryption salt is repeated here so a decrypted container explains itself. */
export function buildManifest(input: ManifestInput): BackupManifest {
  return {
    formatVersion: BACKUP_FORMAT_VERSION,
    appVersion: input.appVersion,
    migrationHead: input.migrationHead,
    createdAt: input.createdAt.toISOString(),
    tableCounts: input.tableCounts,
    files: input.files,
    parts: input.parts,
    encryptionKeySha256: encryptionKeyFingerprint(input.encryptionKey),
    plaintextCredentials: input.encryptionKey === undefined,
    encryption: { scheme: 'aes-256-gcm', kdf: 'scrypt', ...SCRYPT, salt: input.salt.toString('base64') },
  };
}

/** The SHA-256 of DIVEHUB_ENCRYPTION_KEY as the manifest carries it: a fingerprint, never the key. */
export const encryptionKeyFingerprint = (key: Buffer | undefined): string | null =>
  key === undefined ? null : createHash('sha256').update(key).digest('hex');

/** Refuses a manifest this reader cannot trust, before anything from the archive is used. */
export function verifyManifest(manifest: BackupManifest): BackupManifest {
  if (manifest.formatVersion !== BACKUP_FORMAT_VERSION) {
    throw new BackupArchiveError(`unsupported backup format version ${manifest.formatVersion}`);
  }
  if (manifest.encryption.scheme !== 'aes-256-gcm' || manifest.encryption.kdf !== 'scrypt') {
    throw new BackupArchiveError('unsupported backup encryption');
  }
  if (!/^[0-9a-f]{64}$/.test(manifest.parts.database.sha256) || !/^[0-9a-f]{64}$/.test(manifest.parts.files.sha256)) {
    throw new BackupArchiveError('the manifest has no valid part checksums');
  }
  if (!Number.isSafeInteger(manifest.parts.database.bytes) || !Number.isSafeInteger(manifest.parts.files.bytes)) {
    throw new BackupArchiveError('the manifest has no valid part sizes');
  }
  return manifest;
}

export interface Header {
  n: number;
  r: number;
  p: number;
  salt: Buffer;
  iv: Buffer;
}

function headerOf({ n, r, p, salt, iv }: Header): Buffer {
  const fixed = Buffer.alloc(HEADER_BYTES);
  let at = 0;
  at += MAGIC.copy(fixed, at);
  fixed.writeUInt8(BACKUP_FORMAT_VERSION, at);
  at += 1;
  fixed.writeUInt8(KDF_SCRYPT, at);
  at += 1;
  fixed.writeUInt32BE(n, at);
  at += 4;
  fixed.writeUInt32BE(r, at);
  at += 4;
  fixed.writeUInt32BE(p, at);
  at += 4;
  at += salt.copy(fixed, at);
  iv.copy(fixed, at);
  return fixed;
}

function parseHeader(header: Buffer): Header {
  if (header.length !== HEADER_BYTES || !header.subarray(0, MAGIC.length).equals(MAGIC)) {
    throw new BackupArchiveError('this is not a Dive Hub backup');
  }
  let at = MAGIC.length;
  const version = header.readUInt8(at);
  at += 1;
  if (version !== BACKUP_FORMAT_VERSION) throw new BackupArchiveError(`unsupported backup format version ${version}`);
  if (header.readUInt8(at) !== KDF_SCRYPT) throw new BackupArchiveError('unsupported backup key derivation');
  at += 1;
  const n = header.readUInt32BE(at);
  at += 4;
  const r = header.readUInt32BE(at);
  at += 4;
  const p = header.readUInt32BE(at);
  at += 4;
  const salt = header.subarray(at, at + SALT_BYTES);
  at += SALT_BYTES;
  const iv = header.subarray(at, at + IV_BYTES);
  return { n, r, p, salt: Buffer.from(salt), iv: Buffer.from(iv) };
}

async function keyOf(passphrase: string, header: Header): Promise<Buffer> {
  // scrypt's memory is 128 * n * r; cap it so a hostile header cannot make a restore allocate without bound.
  const maxmem = 128 * header.n * header.r * 2;
  return scrypt(passphrase.normalize('NFKC'), header.salt, 32, {
    N: header.n, r: header.r, p: header.p, maxmem,
  });
}

export interface EncryptedStream {
  stream: Readable;
  /** The salt the manifest records; pass it in when the manifest was built first. */
  salt: Buffer;
  iv: Buffer;
}

/** The encrypted archive as one stream: header, AES-256-GCM ciphertext, tag. */
export async function encryptStream(inputPath: string, passphrase: string, options: { salt?: Buffer; iv?: Buffer } = {}): Promise<EncryptedStream> {
  const full: Header = {
    n: SCRYPT.n, r: SCRYPT.r, p: SCRYPT.p,
    salt: options.salt ?? randomBytes(SALT_BYTES), iv: options.iv ?? randomBytes(IV_BYTES),
  };
  const prefix = headerOf(full);
  const cipher = createCipheriv('aes-256-gcm', await keyOf(passphrase, full), full.iv);
  cipher.setAAD(prefix);
  async function* chunks() {
    yield prefix;
    for await (const chunk of createReadStream(inputPath)) yield cipher.update(chunk as Buffer);
    yield cipher.final();
    yield cipher.getAuthTag();
  }
  return { stream: Readable.from(chunks()), salt: full.salt, iv: full.iv };
}

/** Encrypts `inputPath` into `outputPath`; the caller hashes the file when it needs the archive's size and SHA-256. */
export async function encryptFile(inputPath: string, outputPath: string, passphrase: string, options: { salt?: Buffer; iv?: Buffer } = {}) {
  const { stream, salt } = await encryptStream(inputPath, passphrase, options);
  await pipeline(stream, createWriteStream(outputPath, { flags: 'w' }));
  return { salt };
}

/** Decrypts `archivePath` into `outputPath`; a wrong passphrase or a changed byte fails the GCM check. */
export async function decryptFile(archivePath: string, outputPath: string, passphrase: string): Promise<void> {
  const size = (await stat(archivePath)).size;
  if (size < HEADER_BYTES + TAG_BYTES) throw new BackupArchiveError('the backup archive is truncated');
  const file = await open(archivePath, 'r');
  let header: Header;
  let tag: Buffer;
  try {
    const prefix = Buffer.alloc(HEADER_BYTES);
    await file.read(prefix, 0, HEADER_BYTES, 0);
    header = parseHeader(prefix);
    tag = Buffer.alloc(TAG_BYTES);
    await file.read(tag, 0, TAG_BYTES, size - TAG_BYTES);
  } finally {
    await file.close();
  }
  const decipher = createDecipheriv('aes-256-gcm', await keyOf(passphrase, header), header.iv);
  decipher.setAAD(headerOf(header));
  decipher.setAuthTag(tag);
  const body = size - HEADER_BYTES - TAG_BYTES;
  const input = body === 0
    ? Readable.from([])
    : createReadStream(archivePath, { start: HEADER_BYTES, end: size - TAG_BYTES - 1 });
  await pipeline(input, decipher, createWriteStream(outputPath, { flags: 'w' }));
}


async function sha256File(path: string): Promise<string> {
  const hash = createHash('sha256');
  for await (const chunk of createReadStream(path)) hash.update(chunk as Buffer);
  return hash.digest('hex');
}

/** Throws unless a part's bytes are what the manifest recorded. */
export async function verifyPart(path: string, part: BackupPart): Promise<void> {
  const info = await stat(path);
  if (info.size !== part.bytes) throw new BackupArchiveError(`a backup part has ${info.size} bytes, the manifest says ${part.bytes}`);
  if ((await sha256File(path)) !== part.sha256) throw new BackupArchiveError('a backup part does not match its manifest checksum');
}

/** One file-tree checksum: sorted relative paths and sizes, NUL-separated, excluding `skip` top-level names. */
export async function fileTreeChecksum(root: string, skip: string[]): Promise<{ sha256: string; count: number }> {
  const hash = createHash('sha256');
  let count = 0;
  const walk = async (dir: string) => {
    for (const entry of await readdir(dir, { withFileTypes: true })) {
      const path = join(dir, entry.name);
      const rel = relative(root, path).split(sep).join('/');
      if (dir === root && skip.includes(entry.name)) continue;
      if (entry.isDirectory()) {
        await walk(path);
      } else if (entry.isFile()) {
        const info = await stat(path);
        hash.update(`${rel}\0${info.size}\n`);
        count += 1;
      }
    }
  };
  await walk(root);
  return { sha256: hash.digest('hex'), count };
}

/**
 * A gzipped tar of everything in `root` except the named top-level directories (backups, tmp, restore). The patterns
 * go through `-X`, which both GNU tar (a developer machine) and BusyBox tar (the Alpine image) understand.
 */
export async function tarDirectory(root: string, outputPath: string, skip: string[]): Promise<void> {
  const excludes = `${outputPath}.exclude`;
  await writeFile(excludes, `${skip.flatMap((name) => [`./${name}`, `./${name}/*`]).join('\n')}\n`);
  try {
    await execFileAsync('tar', ['-czf', outputPath, '-X', excludes, '-C', root, '.'], { maxBuffer: 1 << 20 });
  } finally {
    await rm(excludes, { force: true });
  }
}

/** Packs the three parts into the container tar whose bytes are then encrypted. */
export async function packContainer(workDir: string, manifest: BackupManifest): Promise<string> {
  const manifestPath = join(workDir, MANIFEST_NAME);
  await writeFile(manifestPath, `${JSON.stringify(manifest, null, 2)}\n`);
  const container = join(workDir, CONTAINER_NAME);
  await execFileAsync('tar', ['-cf', container, '-C', workDir, MANIFEST_NAME, DATABASE_NAME, FILES_NAME], { maxBuffer: 1 << 20 });
  return container;
}

export interface OpenedArchive {
  manifest: BackupManifest;
  databasePath: string;
  filesPath: string;
}

/** Decrypts and unpacks an archive into `workDir`, verifying its manifest and both part checksums. */
export async function openArchive(archivePath: string, passphrase: string, workDir: string): Promise<OpenedArchive> {
  await mkdir(workDir, { recursive: true });
  const container = join(workDir, CONTAINER_NAME);
  await decryptFile(archivePath, container, passphrase);
  await execFileAsync('tar', ['-xf', container, '-C', workDir], { maxBuffer: 1 << 20 });
  let manifest: BackupManifest;
  try {
    manifest = JSON.parse(await readFile(join(workDir, MANIFEST_NAME), 'utf8')) as BackupManifest;
  } catch (error) {
    throw new BackupArchiveError('the backup archive has no readable manifest', { cause: error });
  }
  verifyManifest(manifest);
  const databasePath = join(workDir, DATABASE_NAME);
  const filesPath = join(workDir, FILES_NAME);
  await verifyPart(databasePath, manifest.parts.database);
  await verifyPart(filesPath, manifest.parts.files);
  return { manifest, databasePath, filesPath };
}

export { DATABASE_NAME, FILES_NAME, MANIFEST_NAME };
