import { gzipSync } from 'node:zlib';
import type { RedactionCounters } from './redact.js';

export const SUPPORT_BUNDLE_FORMAT = 'divehub-support-bundle';
export const SUPPORT_BUNDLE_FORMAT_VERSION = 1;

const BLOCK = 512;

export interface SupportBundleMember {
  name: string;
  data: string;
}

export interface SupportBufferManifest {
  capacityEntries: number;
  capacityBytes: number;
  entries: number;
  droppedEntries: number;
  oldestAt: string | null;
  newestAt: string | null;
}

export interface SupportBundleInput {
  createdAt: Date;
  appVersion: string;
  schemaHead: string | null;
  logLevel: string;
  buffer: SupportBufferManifest;
  redaction: RedactionCounters;
  logs: unknown[];
  config: unknown;
  jobs: unknown;
  health: unknown;
  clientDiagnostics?: unknown[] | undefined;
}

export interface SupportBundleManifest {
  format: typeof SUPPORT_BUNDLE_FORMAT;
  formatVersion: number;
  createdAt: string;
  appVersion: string;
  schemaHead: string | null;
  logLevel: string;
  buffer: SupportBufferManifest;
  redaction: RedactionCounters;
  sections: { name: string; bytes: number }[];
}

export interface SupportBundle {
  manifest: SupportBundleManifest;
  members: SupportBundleMember[];
  archive: Buffer;
}

const octal = (value: number, length: number): string => `${value.toString(8).padStart(length - 1, '0').slice(-(length - 1))}\0`;

function ustarHeader(name: string, size: number, mtime: number): Buffer {
  const header = Buffer.alloc(BLOCK, 0);
  header.write(name, 0, 100, 'utf8');
  header.write(octal(0o644, 8), 100, 8, 'ascii');
  header.write(octal(0, 8), 108, 8, 'ascii');
  header.write(octal(0, 8), 116, 8, 'ascii');
  header.write(octal(size, 12), 124, 12, 'ascii');
  header.write(octal(mtime, 12), 136, 12, 'ascii');
  header.fill(' ', 148, 156);
  header.write('0', 156, 1, 'ascii');
  header.write('ustar\0', 257, 6, 'ascii');
  header.write('00', 263, 2, 'ascii');
  const checksum = header.reduce((sum, byte) => sum + byte, 0);
  header.write(`${checksum.toString(8).padStart(6, '0').slice(-6)}\0 `, 148, 8, 'ascii');
  return header;
}

export function buildTarGz(members: SupportBundleMember[], mtimeSeconds: number): Buffer {
  const chunks: Buffer[] = [];
  for (const member of members) {
    const data = Buffer.from(member.data, 'utf8');
    chunks.push(ustarHeader(member.name, data.length, mtimeSeconds), data);
    const padding = (BLOCK - (data.length % BLOCK)) % BLOCK;
    if (padding > 0) chunks.push(Buffer.alloc(padding, 0));
  }
  chunks.push(Buffer.alloc(BLOCK * 2, 0));
  return gzipSync(Buffer.concat(chunks));
}

const json = (value: unknown): string => `${JSON.stringify(value, null, 2)}\n`;
const ndjson = (entries: unknown[]): string => (entries.length === 0 ? '' : `${entries.map((entry) => JSON.stringify(entry)).join('\n')}\n`);

export function supportBundleFilename(at: Date): string {
  const stamp = at.toISOString().slice(0, 19).replaceAll('-', '').replaceAll(':', '').replace('T', '');
  return `divehub-support-bundle-${stamp.slice(0, 8)}-${stamp.slice(8)}.tar.gz`;
}

export function buildSupportBundle(input: SupportBundleInput): SupportBundle {
  const sections: SupportBundleMember[] = [
    { name: 'logs.ndjson', data: ndjson(input.logs) },
    { name: 'config.json', data: json(input.config) },
    { name: 'jobs.json', data: json(input.jobs) },
    { name: 'health.json', data: json(input.health) },
  ];
  if (input.clientDiagnostics !== undefined) {
    sections.push({ name: 'client-diagnostics.ndjson', data: ndjson(input.clientDiagnostics) });
  }
  const manifest: SupportBundleManifest = {
    format: SUPPORT_BUNDLE_FORMAT,
    formatVersion: SUPPORT_BUNDLE_FORMAT_VERSION,
    createdAt: input.createdAt.toISOString(),
    appVersion: input.appVersion,
    schemaHead: input.schemaHead,
    logLevel: input.logLevel,
    buffer: input.buffer,
    redaction: input.redaction,
    sections: sections.map((section) => ({ name: section.name, bytes: Buffer.byteLength(section.data) })),
  };
  const members: SupportBundleMember[] = [{ name: 'manifest.json', data: json(manifest) }, ...sections];
  return {
    manifest,
    members,
    archive: buildTarGz(members, Math.floor(input.createdAt.getTime() / 1000)),
  };
}
