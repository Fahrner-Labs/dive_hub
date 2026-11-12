import { describe, expect, it } from 'vitest';
import { backupDownloadHref, formatBytes, isBackupActive, isBackupDownloadable, scheduleProblem } from '../src/lib/backups.ts';

describe('a backup run', () => {
  it('downloads only when ready, and holds the slot while queued or running', () => {
    expect(isBackupDownloadable('ready')).toBe(true);
    for (const status of ['queued', 'running', 'failed']) expect(isBackupDownloadable(status)).toBe(false);
    expect(isBackupActive('queued')).toBe(true);
    expect(isBackupActive('running')).toBe(true);
    for (const status of ['ready', 'failed']) expect(isBackupActive(status)).toBe(false);
  });

  it('downloads from the authenticated route in the app', () => {
    expect(backupDownloadHref('0a1b')).toBe('/api/admin/backups/0a1b/download');
    expect(backupDownloadHref('a b')).toBe('/api/admin/backups/a%20b/download');
  });
});

describe('a backup size', () => {
  it('reads in the UI language, from bytes to gigabytes', () => {
    expect(formatBytes(null, 'en')).toBe('–');
    expect(formatBytes(0, 'en')).toMatch(/^0\s*B$/);
    expect(formatBytes(1500, 'en')).toMatch(/^1\.5\s*kB$/);
    expect(formatBytes(24_000_000, 'en')).toMatch(/^24\s*MB$/);
  });
});

describe('a schedule before it is sent', () => {
  const valid = { enabled: false, intervalHours: 24, retentionCount: 7, recipientPublicKey: '' };

  it('needs whole numbers in the ranges the server allows', () => {
    expect(scheduleProblem(valid)).toBeNull();
    for (const intervalHours of [0, 1.5, 169, Number.NaN]) expect(scheduleProblem({ ...valid, intervalHours })).toBe('interval');
    for (const retentionCount of [0, 1.5, 366, Number.NaN]) expect(scheduleProblem({ ...valid, retentionCount })).toBe('retention');
  });

  it('needs a recipient while scheduled backups are on, and none to keep them off', () => {
    expect(scheduleProblem({ ...valid, enabled: true })).toBe('recipient');
    expect(scheduleProblem({ ...valid, enabled: true, recipientPublicKey: '   ' })).toBe('recipient');
    expect(scheduleProblem({ ...valid, enabled: true, recipientPublicKey: 'age1test' })).toBeNull();
    expect(scheduleProblem({ ...valid, enabled: false, recipientPublicKey: 'age1test' })).toBeNull();
  });
});
