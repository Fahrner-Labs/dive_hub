// The `.divehub-backup` container (plan FAH-175): a manifest, a pg_dump custom-format dump and a gzipped tar of the
// data directory, read as one tar and encrypted as one AES-256-GCM stream. Manual backups derive the key from a
// passphrase (scrypt); scheduled backups derive it from an X25519 recipient, so the server never holds the private key.
import {
  createCipheriv, createDecipheriv, createHash, createPrivateKey, createPublicKey, diffieHellman, generateKeyPairSync,
  hkdfSync, randomBytes, scrypt as scryptCallback, type ScryptOptions,
} from 'node:crypto';
import { execFile, spawn } from 'node:child_process';
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
const KDF_X25519 = 2;
const SALT_BYTES = 16;
const IV_BYTES = 12;
const PUBLIC_KEY_BYTES = 32;
const TAG_BYTES = 16;
const HEADER_PREFIX_BYTES = MAGIC.length + 1 + 1;
const SCRYPT_HEADER_BYTES = HEADER_PREFIX_BYTES + 4 + 4 + 4 + SALT_BYTES + IV_BYTES;
const X25519_HEADER_BYTES = HEADER_PREFIX_BYTES + PUBLIC_KEY_BYTES + SALT_BYTES + IV_BYTES;

/** scrypt's default cost: about 16 MiB and a fraction of a second, enough for an operator-chosen passphrase. */
const SCRYPT = { n: 16_384, r: 8, p: 1 };

/** X25519 keys are stored in the same bech32 form age uses, so operators can generate them with `age-keygen`. */
const RECIPIENT_HRP = 'age';
const RECIPIENT_SECRET_HRP = 'age-secret-key-';
const X25519_INFO = Buffer.from('divehub-backup/x25519/v1', 'utf8');
const X25519_PKCS8_PREFIX = Buffer.from('302e020100300506032b656e04220420', 'hex');

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

export type BackupEncryptionMetadata =
  | {
    scheme: 'aes-256-gcm';
    kdf: 'scrypt';
    n: number;
    r: number;
    p: number;
    salt: string;
  }
  | {
    scheme: 'aes-256-gcm';
    kdf: 'x25519';
    /** The age-style recipient the archive was encrypted to; not a secret. */
    recipient: string;
    /** The ephemeral public key the header carries, so the recipient can derive the key. */
    ephemeralPublic: string;
    salt: string;
  };

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
  encryption: BackupEncryptionMetadata;
}

export interface ManifestInput {
  appVersion: string;
  migrationHead: string | null;
  createdAt: Date;
  tableCounts: Record<string, number>;
  files: { sha256: string; count: number };
  parts: { database: BackupPart; files: BackupPart };
  encryptionKey: Buffer | undefined;
  /** The salt the passphrase envelope used; ignored when `encryption` says how the archive is protected. */
  salt: Buffer;
  /** Set by a scheduled backup's recipient envelope; otherwise the passphrase envelope is recorded from `salt`. */
  encryption?: BackupEncryptionMetadata;
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
    encryption: input.encryption ?? { scheme: 'aes-256-gcm', kdf: 'scrypt', ...SCRYPT, salt: input.salt.toString('base64') },
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
  const encryption = manifest.encryption;
  if (encryption.scheme !== 'aes-256-gcm') throw new BackupArchiveError('unsupported backup encryption');
  if (encryption.kdf === 'scrypt') {
    if (!Number.isSafeInteger(encryption.n) || !Number.isSafeInteger(encryption.r) || !Number.isSafeInteger(encryption.p)) {
      throw new BackupArchiveError('the manifest has no valid backup key derivation');
    }
  } else if (encryption.kdf === 'x25519') {
    if (typeof encryption.recipient !== 'string' || !isRecipient(encryption.recipient)
      || typeof encryption.ephemeralPublic !== 'string' || Buffer.from(encryption.ephemeralPublic, 'base64').length !== PUBLIC_KEY_BYTES
      || typeof encryption.salt !== 'string') {
      throw new BackupArchiveError('the manifest has no valid recipient key');
    }
  } else {
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

interface HeaderScrypt {
  kdf: 'scrypt';
  n: number;
  r: number;
  p: number;
  salt: Buffer;
  iv: Buffer;
}

interface HeaderX25519 {
  kdf: 'x25519';
  ephemeralPublic: Buffer;
  salt: Buffer;
  iv: Buffer;
}

type Header = HeaderScrypt | HeaderX25519;

function headerOf(header: Header): Buffer {
  const size = header.kdf === 'scrypt' ? SCRYPT_HEADER_BYTES : X25519_HEADER_BYTES;
  const fixed = Buffer.alloc(size);
  let at = 0;
  at += MAGIC.copy(fixed, at);
  fixed.writeUInt8(BACKUP_FORMAT_VERSION, at);
  at += 1;
  if (header.kdf === 'scrypt') {
    fixed.writeUInt8(KDF_SCRYPT, at);
    at += 1;
    fixed.writeUInt32BE(header.n, at);
    at += 4;
    fixed.writeUInt32BE(header.r, at);
    at += 4;
    fixed.writeUInt32BE(header.p, at);
    at += 4;
  } else {
    fixed.writeUInt8(KDF_X25519, at);
    at += 1;
    at += header.ephemeralPublic.copy(fixed, at);
  }
  at += header.salt.copy(fixed, at);
  header.iv.copy(fixed, at);
  return fixed;
}

/** How long the header at the start of the file is; a prefix is enough to tell the two envelopes apart. */
async function headerLengthOf(file: Awaited<ReturnType<typeof open>>): Promise<number> {
  const prefix = Buffer.alloc(HEADER_PREFIX_BYTES);
  const { bytesRead } = await file.read(prefix, 0, HEADER_PREFIX_BYTES, 0);
  if (bytesRead < HEADER_PREFIX_BYTES || !prefix.subarray(0, MAGIC.length).equals(MAGIC)) {
    throw new BackupArchiveError('this is not a Dive Hub backup');
  }
  const version = prefix.readUInt8(MAGIC.length);
  if (version !== BACKUP_FORMAT_VERSION) throw new BackupArchiveError(`unsupported backup format version ${version}`);
  const kdf = prefix.readUInt8(MAGIC.length + 1);
  if (kdf === KDF_SCRYPT) return SCRYPT_HEADER_BYTES;
  if (kdf === KDF_X25519) return X25519_HEADER_BYTES;
  throw new BackupArchiveError('unsupported backup key derivation');
}

function parseHeader(header: Buffer): Header {
  if (header.length < HEADER_PREFIX_BYTES || !header.subarray(0, MAGIC.length).equals(MAGIC)) {
    throw new BackupArchiveError('this is not a Dive Hub backup');
  }
  let at = MAGIC.length;
  const version = header.readUInt8(at);
  at += 1;
  if (version !== BACKUP_FORMAT_VERSION) throw new BackupArchiveError(`unsupported backup format version ${version}`);
  const kdf = header.readUInt8(at);
  at += 1;
  if (kdf === KDF_SCRYPT) {
    if (header.length !== SCRYPT_HEADER_BYTES) throw new BackupArchiveError('the backup header is the wrong size');
    const n = header.readUInt32BE(at);
    at += 4;
    const r = header.readUInt32BE(at);
    at += 4;
    const p = header.readUInt32BE(at);
    at += 4;
    const salt = header.subarray(at, at + SALT_BYTES);
    at += SALT_BYTES;
    const iv = header.subarray(at, at + IV_BYTES);
    return { kdf: 'scrypt', n, r, p, salt: Buffer.from(salt), iv: Buffer.from(iv) };
  }
  if (kdf === KDF_X25519) {
    if (header.length !== X25519_HEADER_BYTES) throw new BackupArchiveError('the backup header is the wrong size');
    const ephemeralPublic = header.subarray(at, at + PUBLIC_KEY_BYTES);
    at += PUBLIC_KEY_BYTES;
    const salt = header.subarray(at, at + SALT_BYTES);
    at += SALT_BYTES;
    const iv = header.subarray(at, at + IV_BYTES);
    return { kdf: 'x25519', ephemeralPublic: Buffer.from(ephemeralPublic), salt: Buffer.from(salt), iv: Buffer.from(iv) };
  }
  throw new BackupArchiveError('unsupported backup key derivation');
}

async function passphraseKeyOf(passphrase: string, header: HeaderScrypt): Promise<Buffer> {
  // scrypt's memory is 128 * n * r; cap it so a hostile header cannot make a restore allocate without bound.
  const maxmem = 128 * header.n * header.r * 2;
  return scrypt(passphrase.normalize('NFKC'), header.salt, 32, {
    N: header.n, r: header.r, p: header.p, maxmem,
  });
}

const jwkOf = (key: ReturnType<typeof createPrivateKey> | ReturnType<typeof createPublicKey>) =>
  key.export({ format: 'jwk' }) as { x?: string; d?: string };

const rawPublicOf = (key: ReturnType<typeof createPublicKey>) => Buffer.from(jwkOf(key).x!, 'base64url');
const rawPrivateOf = (key: ReturnType<typeof createPrivateKey>) => Buffer.from(jwkOf(key).d!, 'base64url');

const x25519PublicKey = (raw: Buffer) =>
  createPublicKey({ key: { kty: 'OKP', crv: 'X25519', x: raw.toString('base64url') }, format: 'jwk' });

const x25519PrivateKey = (raw: Buffer) =>
  createPrivateKey({ key: Buffer.concat([X25519_PKCS8_PREFIX, raw]), format: 'der', type: 'pkcs8' });

const x25519KeyOf = (sharedSecret: Buffer, salt: Buffer): Buffer =>
  Buffer.from(hkdfSync('sha256', sharedSecret, salt, X25519_INFO, 32));

/** What an archive is encrypted with; the salt and, for a recipient, the ephemeral key it records. */
export interface EncryptedStream {
  stream: Readable;
  salt: Buffer;
  iv: Buffer;
}

/** How one archive is protected: what the manifest records, and the stream that writes it. */
export interface BackupEncryption {
  metadata: BackupEncryptionMetadata;
  encrypt(inputPath: string): Promise<EncryptedStream>;
}

async function encryptedStream(inputPath: string, header: Header, key: Buffer): Promise<EncryptedStream> {
  const prefix = headerOf(header);
  const cipher = createCipheriv('aes-256-gcm', key, header.iv);
  cipher.setAAD(prefix);
  async function* chunks() {
    yield prefix;
    for await (const chunk of createReadStream(inputPath)) yield cipher.update(chunk as Buffer);
    yield cipher.final();
    yield cipher.getAuthTag();
  }
  return { stream: Readable.from(chunks()), salt: header.salt, iv: header.iv };
}

/** A manual backup's envelope: a passphrase through scrypt, as before. */
export function passphraseEncryption(passphrase: string, options: { salt?: Buffer; iv?: Buffer } = {}): BackupEncryption {
  const header: Header = {
    kdf: 'scrypt', ...SCRYPT,
    salt: options.salt ?? randomBytes(SALT_BYTES), iv: options.iv ?? randomBytes(IV_BYTES),
  };
  return {
    metadata: { scheme: 'aes-256-gcm', kdf: 'scrypt', ...SCRYPT, salt: header.salt.toString('base64') },
    encrypt: async (inputPath) => encryptedStream(inputPath, header, await passphraseKeyOf(passphrase, header)),
  };
}

/** A scheduled backup's envelope: an ephemeral X25519 key agrees one with the operator's recipient. */
export function recipientEncryption(recipient: string, options: { salt?: Buffer; iv?: Buffer } = {}): BackupEncryption {
  const recipientKey = decodeRecipient(recipient);
  const ephemeral = generateKeyPairSync('x25519');
  const ephemeralPublic = rawPublicOf(ephemeral.publicKey);
  const header: Header = {
    kdf: 'x25519', ephemeralPublic,
    salt: options.salt ?? randomBytes(SALT_BYTES), iv: options.iv ?? randomBytes(IV_BYTES),
  };
  const shared = diffieHellman({ privateKey: ephemeral.privateKey, publicKey: x25519PublicKey(recipientKey) });
  return {
    metadata: {
      scheme: 'aes-256-gcm', kdf: 'x25519', recipient,
      ephemeralPublic: ephemeralPublic.toString('base64'), salt: header.salt.toString('base64'),
    },
    encrypt: async (inputPath) => encryptedStream(inputPath, header, x25519KeyOf(shared, header.salt)),
  };
}

/** The encrypted archive as one stream: header, AES-256-GCM ciphertext, tag. */
export async function encryptStream(inputPath: string, passphrase: string, options: { salt?: Buffer; iv?: Buffer } = {}): Promise<EncryptedStream> {
  return passphraseEncryption(passphrase, options).encrypt(inputPath);
}

/** Encrypts `inputPath` into `outputPath`; the caller hashes the file when it needs the archive's size and SHA-256. */
export async function encryptFile(inputPath: string, outputPath: string, passphrase: string, options: { salt?: Buffer; iv?: Buffer } = {}) {
  const { stream, salt } = await encryptStream(inputPath, passphrase, options);
  await pipeline(stream, createWriteStream(outputPath, { flags: 'w' }));
  return { salt };
}

/** Encrypts a file to a recipient; the tests and the docs use this to draw a scheduled archive without a database. */
export async function encryptFileToRecipient(inputPath: string, outputPath: string, recipient: string, options: { salt?: Buffer; iv?: Buffer } = {}) {
  const encryption = recipientEncryption(recipient, options);
  const { stream, salt } = await encryption.encrypt(inputPath);
  await pipeline(stream, createWriteStream(outputPath, { flags: 'w' }));
  return { salt, encryption: encryption.metadata };
}

/** What a restore presents: the manual backup's passphrase, or the scheduled backup's recipient private key. */
export type BackupDecryptionKey = { kind: 'passphrase'; passphrase: string } | { kind: 'recipient'; privateKey: string };

/** Decrypts `archivePath` into `outputPath` with a passphrase; a wrong one or a changed byte fails the GCM check. */
export async function decryptFile(archivePath: string, outputPath: string, passphrase: string): Promise<void> {
  await decryptFileWith(archivePath, outputPath, { kind: 'passphrase', passphrase });
}

/** Decrypts `archivePath` into `outputPath` with either kind of key. */
export async function decryptFileWith(archivePath: string, outputPath: string, key: BackupDecryptionKey): Promise<void> {
  await pipeline(await decryptStreamWith(archivePath, key), createWriteStream(outputPath, { flags: 'w' }));
}

async function decipherOf(archivePath: string, key: BackupDecryptionKey): Promise<{ decipher: ReturnType<typeof createDecipheriv>; input: Readable }> {
  const size = (await stat(archivePath)).size;
  const file = await open(archivePath, 'r');
  let header: Header;
  let tag: Buffer;
  try {
    const headerBytes = await headerLengthOf(file);
    if (size < headerBytes + TAG_BYTES) throw new BackupArchiveError('the backup archive is truncated');
    const prefix = Buffer.alloc(headerBytes);
    await file.read(prefix, 0, headerBytes, 0);
    header = parseHeader(prefix);
    tag = Buffer.alloc(TAG_BYTES);
    await file.read(tag, 0, TAG_BYTES, size - TAG_BYTES);
  } finally {
    await file.close();
  }
  let derived: Buffer;
  if (header.kdf === 'scrypt') {
    if (key.kind !== 'passphrase') throw new BackupArchiveError('this backup is protected with a passphrase');
    derived = await passphraseKeyOf(key.passphrase, header);
  } else {
    if (key.kind !== 'recipient') throw new BackupArchiveError('this backup is protected with a recipient key');
    const shared = diffieHellman({ privateKey: x25519PrivateKey(decodeRecipientPrivateKey(key.privateKey)), publicKey: x25519PublicKey(header.ephemeralPublic) });
    derived = x25519KeyOf(shared, header.salt);
  }
  const decipher = createDecipheriv('aes-256-gcm', derived, header.iv);
  const headerBytes = headerOf(header);
  decipher.setAAD(headerBytes);
  decipher.setAuthTag(tag);
  const body = size - headerBytes.length - TAG_BYTES;
  const input = body === 0
    ? Readable.from([])
    : createReadStream(archivePath, { start: headerBytes.length, end: size - TAG_BYTES - 1 });
  return { decipher, input };
}

/** The decrypted container as one stream, for a caller that unpacks it without a second copy on disk. */
export async function decryptStreamWith(archivePath: string, key: BackupDecryptionKey): Promise<Readable> {
  const { decipher, input } = await decipherOf(archivePath, key);
  async function* chunks() {
    for await (const chunk of input) yield decipher.update(chunk as Buffer);
    yield decipher.final();
  }
  return Readable.from(chunks());
}

/** The decrypted container as one stream, for a passphrase-protected archive. */
export async function decryptStream(archivePath: string, passphrase: string): Promise<Readable> {
  return decryptStreamWith(archivePath, { kind: 'passphrase', passphrase });
}

const BECH32_CHARSET = 'qpzry9x8gf2tvdw0s3jn54khce6mua7l';
const BECH32_GENERATOR = [0x3b6a57b2, 0x26508e6d, 0x1ea119fa, 0x3d4233dd, 0x2a1462b3];

function bech32Polymod(values: number[]): number {
  let checksum = 1;
  for (const value of values) {
    const top = checksum >>> 25;
    checksum = ((checksum & 0x1ffffff) << 5) ^ value;
    for (let bit = 0; bit < 5; bit += 1) if ((top >>> bit) & 1) checksum ^= BECH32_GENERATOR[bit]!;
  }
  return checksum >>> 0;
}

const bech32HrpExpand = (hrp: string): number[] => [
  ...[...hrp].map((character) => character.charCodeAt(0) >> 5),
  0,
  ...[...hrp].map((character) => character.charCodeAt(0) & 31),
];

function bech32Checksum(hrp: string, data: number[]): number[] {
  const mod = bech32Polymod([...bech32HrpExpand(hrp), ...data, 0, 0, 0, 0, 0, 0]) ^ 1;
  return Array.from({ length: 6 }, (_, index) => (mod >>> (5 * (5 - index))) & 31);
}

function convertBits(data: number[], from: number, to: number, pad: boolean): number[] {
  let accumulator = 0;
  let bits = 0;
  const result: number[] = [];
  const maxValue = (1 << to) - 1;
  for (const value of data) {
    if (value < 0 || value >> from !== 0) throw new BackupArchiveError('a backup key has an invalid character');
    accumulator = (accumulator << from) | value;
    bits += from;
    while (bits >= to) {
      bits -= to;
      result.push((accumulator >> bits) & maxValue);
    }
  }
  if (pad) {
    if (bits > 0) result.push((accumulator << (to - bits)) & maxValue);
  } else if (bits >= from || ((accumulator << (to - bits)) & maxValue) !== 0) {
    throw new BackupArchiveError('a backup key has invalid padding');
  }
  return result;
}

function bech32Encode(hrp: string, data: number[]): string {
  const values = [...data, ...bech32Checksum(hrp, data)];
  return `${hrp}1${values.map((value) => BECH32_CHARSET[value]!).join('')}`;
}

function bech32Decode(text: string): { hrp: string; data: number[] } {
  if (text.length < 8 || text.length > 90) throw new BackupArchiveError('a backup key has the wrong length');
  const lower = text.toLowerCase();
  if (text !== lower && text !== text.toUpperCase()) throw new BackupArchiveError('a backup key mixes upper and lower case');
  const separator = lower.lastIndexOf('1');
  if (separator < 1 || separator + 7 > lower.length) throw new BackupArchiveError('a backup key has no checksum');
  const hrp = lower.slice(0, separator);
  const values = [...lower.slice(separator + 1)].map((character) => BECH32_CHARSET.indexOf(character));
  if (values.some((value) => value === -1)) throw new BackupArchiveError('a backup key has an invalid character');
  if (bech32Polymod([...bech32HrpExpand(hrp), ...values]) !== 1) throw new BackupArchiveError('a backup key has a bad checksum');
  return { hrp, data: values.slice(0, -6) };
}

const keyBytes = (hrp: string, data: number[], expectedHrp: string): Buffer => {
  if (hrp !== expectedHrp) throw new BackupArchiveError('a backup key has the wrong type');
  const bytes = Buffer.from(convertBits(data, 5, 8, false));
  if (bytes.length !== PUBLIC_KEY_BYTES) throw new BackupArchiveError('a backup key has the wrong length');
  return bytes;
};

/** The bech32 text an operator pastes for a scheduled backup: an age X25519 recipient (`age1…`). */
export const encodeRecipient = (publicKey: Buffer): string =>
  bech32Encode(RECIPIENT_HRP, convertBits([...publicKey], 8, 5, true));

/** The private key's age text (`AGE-SECRET-KEY-1…`), for the operator to keep off the server. */
export const encodeRecipientPrivateKey = (privateKey: Buffer): string =>
  bech32Encode(RECIPIENT_SECRET_HRP, convertBits([...privateKey], 8, 5, true)).toUpperCase();

/** The 32 bytes behind an `age1…` recipient; throws for anything that is not one. */
export const decodeRecipient = (recipient: string): Buffer => {
  const { hrp, data } = bech32Decode(recipient);
  return keyBytes(hrp, data, RECIPIENT_HRP);
};

/** The 32 bytes behind an `AGE-SECRET-KEY-1…` private key; throws for anything that is not one. */
export const decodeRecipientPrivateKey = (secretKey: string): Buffer => {
  const { hrp, data } = bech32Decode(secretKey);
  return keyBytes(hrp, data, RECIPIENT_SECRET_HRP);
};

/** Whether settings may keep this as the recipient public key. */
export const isRecipient = (value: string): boolean => {
  try {
    decodeRecipient(value);
    return true;
  } catch {
    return false;
  }
};

/** A fresh X25519 keypair in age's text form: the operator generates one off the server, and pastes only the recipient. */
export function generateRecipientKeyPair(): { recipient: string; privateKey: string } {
  const pair = generateKeyPairSync('x25519');
  return {
    recipient: encodeRecipient(rawPublicOf(pair.publicKey)),
    privateKey: encodeRecipientPrivateKey(rawPrivateOf(pair.privateKey)),
  };
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

/** Reads the manifest a decrypted container left in `workDir` and verifies it and both part checksums. */
export async function readUnpackedArchive(workDir: string): Promise<OpenedArchive> {
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

/** Decrypts and unpacks an archive into `workDir` with the decrypted container going straight into tar. */
export async function openArchiveStreamingWith(archivePath: string, key: BackupDecryptionKey, workDir: string): Promise<OpenedArchive> {
  await mkdir(workDir, { recursive: true });
  await unpackTarStream(await decryptStreamWith(archivePath, key), workDir);
  return readUnpackedArchive(workDir);
}

/** Decrypts and unpacks an archive into `workDir` with a passphrase. */
export async function openArchiveStreaming(archivePath: string, passphrase: string, workDir: string): Promise<OpenedArchive> {
  return openArchiveStreamingWith(archivePath, { kind: 'passphrase', passphrase }, workDir);
}

/** Decrypts and unpacks an archive into `workDir`, verifying its manifest and both part checksums. */
export async function openArchive(archivePath: string, passphrase: string, workDir: string): Promise<OpenedArchive> {
  return openArchiveWith(archivePath, { kind: 'passphrase', passphrase }, workDir);
}

/** Decrypts and unpacks an archive into `workDir` with either kind of key. */
export async function openArchiveWith(archivePath: string, key: BackupDecryptionKey, workDir: string): Promise<OpenedArchive> {
  await mkdir(workDir, { recursive: true });
  const container = join(workDir, CONTAINER_NAME);
  await decryptFileWith(archivePath, container, key);
  await execFileAsync('tar', ['-xf', container, '-C', workDir], { maxBuffer: 1 << 20 });
  return readUnpackedArchive(workDir);
}

/** Waits until a writable is ready, or gone: a closed pipe must not hold the decryption stream open. */
function drained(writable: NodeJS.WritableStream): Promise<void> {
  return new Promise((resolve) => {
    const done = () => {
      writable.off('drain', done);
      writable.off('close', done);
      writable.off('error', done);
      resolve();
    };
    writable.once('drain', done);
    writable.once('close', done);
    writable.once('error', done);
  });
}

/**
 * Feeds a tar stream to `tar` directly: no intermediate container file, constant memory however large the archive.
 * tar stops at the end-of-archive marker and closes its stdin while the stream still has block padding, so the whole
 * ciphertext is drained even after that; the GCM tag is checked only at its end, and a closed pipe must not skip it.
 */
async function unpackTarStream(stream: Readable, workDir: string): Promise<void> {
  const child = spawn('tar', ['-xf', '-', '-C', workDir], { stdio: ['pipe', 'ignore', 'pipe'] });
  const stdin = child.stdin!;
  let stderr = '';
  child.stderr.on('data', (chunk: Buffer) => {
    stderr = `${stderr}${chunk.toString()}`.slice(-4096);
  });
  let writeError: unknown = null;
  stdin.on('error', (error) => { writeError = error; });
  const exited = new Promise<number>((resolve, reject) => {
    child.once('error', reject);
    child.once('close', (code) => resolve(code ?? 1));
  });
  try {
    for await (const chunk of stream) {
      if (writeError !== null || stdin.destroyed || !stdin.writable) continue;
      try {
        if (!stdin.write(chunk as Buffer)) await drained(stdin);
      } catch (error) {
        writeError = error;
      }
    }
    if (writeError === null && !stdin.destroyed && stdin.writable) stdin.end();
  } catch (error) {
    stdin.destroy();
    throw error;
  }
  const code = await exited;
  if (code !== 0) throw new BackupArchiveError(`the backup container could not be unpacked${stderr.trim() ? `: ${stderr.trim()}` : ''}`);
}

export { DATABASE_NAME, FILES_NAME, MANIFEST_NAME };
