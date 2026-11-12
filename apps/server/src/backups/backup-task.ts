// The worker task of a backup run (ADR 0069, plan FAH-175): the row was queued in the request's transaction, so this
// only reads the id and lets the service do the dump, tar and encryption.
import type { BackupService } from './backup-service.js';

export function backupTask(backups: Pick<BackupService, 'run'>, log: { info(detail: object, message: string): void }) {
  return async (payload: unknown) => {
    const backupRunId = (payload as { backupRunId?: unknown } | null)?.backupRunId;
    if (typeof backupRunId !== 'string' || backupRunId === '') throw new Error('the job payload has no backupRunId');
    log.info({ backupRunId }, 'writing backup');
    await backups.run(backupRunId);
  };
}
