import { gunzipSync } from 'node:zlib';
import { describe, expect, it } from 'vitest';
import {
  buildSupportBundle, supportBundleFilename, SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION,
} from '../src/support/support-bundle.js';

function parseTar(archive: Buffer): Map<string, string> {
  const files = new Map<string, string>();
  let offset = 0;
  while (offset + 512 <= archive.length) {
    const header = archive.subarray(offset, offset + 512);
    if (header.every((byte) => byte === 0)) break;
    const name = header.subarray(0, 100).toString('utf8').replace(/\0[\s\S]*$/, '');
    const size = Number.parseInt(header.subarray(124, 136).toString('ascii').replace(/\0/g, '').trim(), 8);
    files.set(name, archive.subarray(offset + 512, offset + 512 + size).toString('utf8'));
    offset += 512 + Math.ceil(size / 512) * 512;
  }
  return files;
}

const input = () => ({
  createdAt: new Date('2026-11-20T12:34:56.000Z'),
  appVersion: '1.2.3',
  schemaHead: '0013_support',
  logLevel: 'info',
  buffer: {
    capacityEntries: 2048, capacityBytes: 2 * 1024 * 1024,
    entries: 2, droppedEntries: 1, oldestAt: '2026-11-20T12:00:00.000Z', newestAt: '2026-11-20T12:30:00.000Z',
  },
  redaction: { keysCensored: 2, emailsMasked: 3, ipsMasked: 4 },
  logs: [{ msg: 'one', time: 1 }, { msg: 'two', time: 2 }],
  config: { DIVEHUB_HOST: '127.0.0.1' },
  jobs: { available: true, jobs: [], backups: [] },
  health: { available: true, uptimeSec: 10 },
});

describe('the support bundle archive', () => {
  it('writes a gzip of five USTAR members whose manifest counts match them', () => {
    const bundle = buildSupportBundle(input());
    const files = parseTar(gunzipSync(bundle.archive));
    expect([...files.keys()]).toEqual(['manifest.json', 'logs.ndjson', 'config.json', 'jobs.json', 'health.json']);
    const manifest = JSON.parse(files.get('manifest.json')!);
    expect(manifest).toMatchObject({
      format: SUPPORT_BUNDLE_FORMAT,
      formatVersion: SUPPORT_BUNDLE_FORMAT_VERSION,
      createdAt: '2026-11-20T12:34:56.000Z',
      appVersion: '1.2.3',
      schemaHead: '0013_support',
      logLevel: 'info',
      buffer: input().buffer,
      redaction: input().redaction,
    });
    for (const section of manifest.sections) {
      expect(Buffer.byteLength(files.get(section.name)!)).toBe(section.bytes);
    }
    expect(files.get('logs.ndjson')).toBe(`${JSON.stringify(input().logs[0])}\n${JSON.stringify(input().logs[1])}\n`);
  });

  it('tolerates an empty log buffer', () => {
    const bundle = buildSupportBundle({ ...input(), logs: [], buffer: { ...input().buffer, entries: 0 } });
    const files = parseTar(gunzipSync(bundle.archive));
    expect(files.get('logs.ndjson')).toBe('');
  });

  it('omits client diagnostics unless a provider supplied them', () => {
    const without = parseTar(gunzipSync(buildSupportBundle(input()).archive));
    expect(without.has('client-diagnostics.ndjson')).toBe(false);
    const withDiagnostics = parseTar(gunzipSync(buildSupportBundle({ ...input(), clientDiagnostics: [{ code: 'E1' }] }).archive));
    expect(withDiagnostics.get('client-diagnostics.ndjson')).toBe(`${JSON.stringify({ code: 'E1' })}\n`);
    const manifest = JSON.parse(withDiagnostics.get('manifest.json')!);
    expect(manifest.sections.map((section: { name: string }) => section.name)).toContain('client-diagnostics.ndjson');
  });

  it('names the file with the UTC date and time', () => {
    expect(supportBundleFilename(new Date('2026-11-20T12:34:56.000Z'))).toBe('divehub-support-bundle-20261120-123456.tar.gz');
  });
});
