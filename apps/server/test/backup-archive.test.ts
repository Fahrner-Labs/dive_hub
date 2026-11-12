// The `.divehub-backup` container (ADR 0068): manifest rules, the passphrase envelope, and pack/open roundtrips.
import { createHash, randomBytes } from 'node:crypto';
import { createReadStream } from 'node:fs';
import { mkdir, mkdtemp, readFile, rename, rm, stat, writeFile } from 'node:fs/promises';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import {
  BACKUP_FORMAT_VERSION, BackupArchiveError, buildManifest, decryptFile, encryptFile, encryptionKeyFingerprint,
  fileTreeChecksum, openArchive, packContainer, verifyManifest, type BackupManifest,
} from '../src/backups/backup-archive.js';

const sha256 = async (path: string) => {
  const hash = createHash('sha256');
  for await (const chunk of createReadStream(path)) hash.update(chunk as Buffer);
  return hash.digest('hex');
};

const partOf = async (path: string) => ({ sha256: await sha256(path), bytes: (await stat(path)).size });

const manifestFor = (parts: { database: { sha256: string; bytes: number }; files: { sha256: string; bytes: number } }, key?: Buffer): BackupManifest =>
  buildManifest({
    appVersion: '9.9.9', migrationHead: '0055_sweet_rocket_raccoon', createdAt: new Date('2026-11-12T10:00:00Z'),
    tableCounts: { dive: 2, user: 1 }, files: { sha256: 'a'.repeat(64), count: 3 }, parts,
    encryptionKey: key, salt: randomBytes(16),
  });

describe('the backup manifest', () => {
  const key = Buffer.alloc(32, 4);
  const parts = { database: { sha256: 'b'.repeat(64), bytes: 12 }, files: { sha256: 'c'.repeat(64), bytes: 34 } };

  it('records the format, the parts, and only a fingerprint of the encryption key', () => {
    const manifest = manifestFor(parts, key);
    expect(manifest.formatVersion).toBe(BACKUP_FORMAT_VERSION);
    expect(manifest.parts).toEqual(parts);
    expect(manifest.encryptionKeySha256).toBe(createHash('sha256').update(key).digest('hex'));
    expect(JSON.stringify(manifest)).not.toContain(key.toString('base64'));
    expect(manifest.plaintextCredentials).toBe(false);
    expect(encryptionKeyFingerprint(key)).toBe(manifest.encryptionKeySha256);
    expect(verifyManifest(manifest)).toBe(manifest);
  });

  it('flags an instance without DIVEHUB_ENCRYPTION_KEY as keeping credentials in plain text', () => {
    const manifest = manifestFor(parts);
    expect(manifest.encryptionKeySha256).toBeNull();
    expect(encryptionKeyFingerprint(undefined)).toBeNull();
    expect(manifest.plaintextCredentials).toBe(true);
  });

  it('refuses an unknown format, an unknown cipher and broken checksums', () => {
    const manifest = manifestFor(parts, key);
    expect(() => verifyManifest({ ...manifest, formatVersion: 99 })).toThrow(BackupArchiveError);
    expect(() => verifyManifest({ ...manifest, encryption: { ...manifest.encryption, scheme: 'another-cipher' } as unknown as BackupManifest['encryption'] }))
      .toThrow(/encryption/);
    expect(() => verifyManifest({ ...manifest, parts: { ...manifest.parts, files: { ...manifest.parts.files, sha256: 'nope' } } }))
      .toThrow(/checksums/);
  });
});

describe('the passphrase envelope', () => {
  let dir: string;
  beforeAll(async () => { dir = await mkdtemp(join(tmpdir(), 'divehub-backup-envelope-')); });
  afterAll(async () => { await rm(dir, { recursive: true, force: true }); });

  it('roundtrips a file and refuses another passphrase', async () => {
    const plain = join(dir, 'plain.bin');
    const archive = join(dir, 'archive.bin');
    const opened = join(dir, 'opened.bin');
    const data = randomBytes(64 * 1024);
    await writeFile(plain, data);
    await encryptFile(plain, archive, 'correct horse battery staple');
    await decryptFile(archive, opened, 'correct horse battery staple');
    expect(await readFile(opened)).toEqual(data);
    await expect(decryptFile(archive, join(dir, 'wrong.bin'), 'another passphrase')).rejects.toThrow();
  });

  it('refuses a changed byte and something that is not a backup', async () => {
    const plain = join(dir, 'plain2.bin');
    const archive = join(dir, 'archive2.bin');
    await writeFile(plain, 'the dive logbook');
    await encryptFile(plain, archive, 'a passphrase');
    const bytes = await readFile(archive);
    bytes[bytes.length - 20] = bytes[bytes.length - 20]! ^ 0xff;
    const tampered = join(dir, 'tampered.bin');
    await writeFile(tampered, bytes);
    await expect(decryptFile(tampered, join(dir, 'x.bin'), 'a passphrase')).rejects.toThrow();
    const junk = join(dir, 'junk.bin');
    await writeFile(junk, randomBytes(128));
    await expect(decryptFile(junk, join(dir, 'y.bin'), 'a passphrase')).rejects.toThrow(/not a Dive Hub backup/);
  });

  it('takes the salt the caller chose so the manifest can name it', async () => {
    const plain = join(dir, 'plain3.bin');
    await writeFile(plain, 'x');
    const archive = join(dir, 'archive3.bin');
    const salt = randomBytes(16);
    await encryptFile(plain, archive, 'a passphrase', { salt });
    const manifest = buildManifest({
      appVersion: 'dev', migrationHead: null, createdAt: new Date(), tableCounts: {}, files: { sha256: 'a'.repeat(64), count: 0 },
      parts: { database: { sha256: 'd'.repeat(64), bytes: 1 }, files: { sha256: 'e'.repeat(64), bytes: 1 } },
      encryptionKey: undefined, salt,
    });
    expect(manifest.encryption.salt).toBe(salt.toString('base64'));
    expect(manifest.encryption.n).toBe(16_384);
  });
});

describe('packing and opening an archive', () => {
  let dir: string;
  beforeAll(async () => { dir = await mkdtemp(join(tmpdir(), 'divehub-backup-pack-')); });
  afterAll(async () => { await rm(dir, { recursive: true, force: true }); });

  it('verifies both parts and hands them back after decryption', async () => {
    const work = join(dir, 'work');
    const out = join(dir, 'out');
    await rm(work, { recursive: true, force: true });
    await rm(out, { recursive: true, force: true });
    await mkdir(work, { recursive: true });
    const database = join(work, 'database.dump');
    const files = join(work, 'files.tar.gz');
    await writeFile(database, 'custom-format dump');
    await writeFile(files, 'gzipped tar');
    const manifest = manifestFor({ database: await partOf(database), files: await partOf(files) }, Buffer.alloc(32, 2));
    const container = await packContainer(work, manifest);
    const archive = join(dir, 'full.divehub-backup');
    await encryptFile(container, archive, 'a passphrase');
    const opened = await openArchive(archive, 'a passphrase', out);
    expect(opened.manifest.tableCounts).toEqual({ dive: 2, user: 1 });
    expect(await readFile(opened.databasePath, 'utf8')).toBe('custom-format dump');
    expect(await readFile(opened.filesPath, 'utf8')).toBe('gzipped tar');
  });

  it('refuses an archive whose part does not match its manifest', async () => {
    const work = join(dir, 'work2');
    const out = join(dir, 'out2');
    await mkdir(work, { recursive: true });
    const database = join(work, 'database.dump');
    const files = join(work, 'files.tar.gz');
    await writeFile(database, 'dump');
    await writeFile(files, 'files');
    const manifest = manifestFor({ database: await partOf(database), files: await partOf(files) });
    // Pack a dump that is not the one the manifest describes, as a corrupted archive would have it.
    await writeFile(database, 'other dump');
    await packContainer(work, manifest);
    await rename(join(work, 'container.tar'), join(dir, 'corrupt.tar'));
    const archive = join(dir, 'corrupt.divehub-backup');
    await encryptFile(join(dir, 'corrupt.tar'), archive, 'a passphrase');
    await expect(openArchive(archive, 'a passphrase', out)).rejects.toThrow(/backup part/);
  });
});

describe('the file-tree checksum', () => {
  it('counts files and skips the excluded top-level directories', async () => {
    const dir = await mkdtemp(join(tmpdir(), 'divehub-backup-tree-'));
    try {
      await mkdir(join(dir, 'originals', 'ab'), { recursive: true });
      await mkdir(join(dir, 'backups'), { recursive: true });
      await writeFile(join(dir, 'auth-secret'), 'secret');
      await writeFile(join(dir, 'originals', 'ab', 'cd'), 'blob');
      await writeFile(join(dir, 'backups', 'old.divehub-backup'), 'archive');
      const tree = await fileTreeChecksum(dir, ['backups', 'tmp', 'restore']);
      expect(tree.count).toBe(2);
      const again = await fileTreeChecksum(dir, ['backups', 'tmp', 'restore']);
      expect(again.sha256).toBe(tree.sha256);
    } finally {
      await rm(dir, { recursive: true, force: true });
    }
  });
});
