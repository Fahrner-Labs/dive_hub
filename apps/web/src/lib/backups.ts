// The admin backups page's own rules (plan FAH-175): the archive's address, which runs can be downloaded, how a size
// reads, and what the schedule form checks before sending it. The server validates the same things again.

/** Downloading streams the archive as a file; the admin's session cookie authenticates it. */
export function backupDownloadHref(id: string): string {
  return `/api/admin/backups/${encodeURIComponent(id)}/download`;
}

/** Only a finished run has an archive: a queued or running one has none yet, and a failed one never got one. */
export const isBackupDownloadable = (status: string): boolean => status === 'ready';

/** Whether a run still holds the instance's one in-flight slot (the server answers 409 for another). */
export const isBackupActive = (status: string): boolean => status === 'queued' || status === 'running';

/** A size as the UI language writes it: "512 B", "1.5 kB", "24 MB". Null when the run has no archive. */
export function formatBytes(bytes: number | null, locale: string): string {
  if (bytes === null) return '–';
  const units = ['kilobyte', 'megabyte', 'gigabyte', 'terabyte'] as const;
  let value = bytes;
  let unit = -1;
  while (value >= 1000 && unit < units.length - 1) {
    value /= 1000;
    unit += 1;
  }
  // Bytes have no unit name in Intl's short form ("0 byte"): B is the same in every UI language.
  if (unit < 0) return `${new Intl.NumberFormat(locale, { maximumFractionDigits: 0 }).format(value)} B`;
  return new Intl.NumberFormat(locale, {
    style: 'unit', unit: units[unit], unitDisplay: 'short',
    maximumFractionDigits: value < 10 ? 1 : 0,
  }).format(value);
}

/** What the schedule form would need before the server accepts it; the recipient is required only while it is on. */
export type ScheduleProblem = 'interval' | 'retention' | 'recipient';

export function scheduleProblem(values: {
  enabled: boolean; intervalHours: number; retentionCount: number; recipientPublicKey: string;
}): ScheduleProblem | null {
  if (!Number.isInteger(values.intervalHours) || values.intervalHours < 1 || values.intervalHours > 168) return 'interval';
  if (!Number.isInteger(values.retentionCount) || values.retentionCount < 1 || values.retentionCount > 365) return 'retention';
  if (values.enabled && values.recipientPublicKey.trim() === '') return 'recipient';
  return null;
}
