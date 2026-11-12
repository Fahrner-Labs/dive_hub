// A heartbeat in the data volume (plan FAH-175): the running app refreshes it, and the restore CLI reads it from its own
// container to refuse restoring onto a live instance. A container's PID means nothing outside it, so the mtime is the
// signal; an app that was killed leaves a stale lock that the CLI may override with --force.
import { mkdir, readFile, rm, stat, utimes, writeFile } from 'node:fs/promises';
import { dirname, join } from 'node:path';

export const APP_LOCK_DIR = 'run';
export const APP_LOCK_NAME = 'app.lock';

/** How often the running app refreshes the lock's mtime. */
export const APP_LOCK_HEARTBEAT_MS = 5_000;
/** A lock older than this belongs to an app that stopped or crashed; the CLI may restore without --force. */
export const APP_LOCK_STALE_MS = 30_000;

export type AppLockProbe =
  | { running: false; reason: 'missing' | 'stale'; ageMs?: number }
  | { running: true; ageMs: number; pid: number | null; startedAt: string | null };

export interface AppLock {
  path: string;
  /** Removes the lock at once; the app calls it on a clean shutdown. */
  release(): Promise<void>;
}

export const appLockPath = (dataDir: string): string => join(dataDir, APP_LOCK_DIR, APP_LOCK_NAME);

/** Writes the lock and keeps its mtime fresh until `release`; the interval is unref'd so it never holds a process open. */
export async function acquireAppLock(dataDir: string, now = Date.now()): Promise<AppLock> {
  const path = appLockPath(dataDir);
  await mkdir(dirname(path), { recursive: true });
  await writeFile(path, `${JSON.stringify({ pid: process.pid, startedAt: new Date(now).toISOString() })}\n`);
  const heartbeat = setInterval(() => {
    const at = new Date();
    void utimes(path, at, at).catch(() => {});
  }, APP_LOCK_HEARTBEAT_MS);
  heartbeat.unref();
  return {
    path,
    async release() {
      clearInterval(heartbeat);
      await rm(path, { force: true });
    },
  };
}

/** Whether the app's heartbeat is fresh enough to mean a running stack, and what it says when it is. */
export async function probeAppLock(dataDir: string, now = Date.now()): Promise<AppLockProbe> {
  const path = appLockPath(dataDir);
  const info = await stat(path).catch(() => null);
  if (!info) return { running: false, reason: 'missing' };
  const ageMs = now - info.mtimeMs;
  if (ageMs > APP_LOCK_STALE_MS) return { running: false, reason: 'stale', ageMs };
  let pid: number | null = null;
  let startedAt: string | null = null;
  try {
    const parsed = JSON.parse(await readFile(path, 'utf8')) as { pid?: unknown; startedAt?: unknown };
    if (typeof parsed.pid === 'number') pid = parsed.pid;
    if (typeof parsed.startedAt === 'string') startedAt = parsed.startedAt;
  } catch {
    // A heartbeat caught mid-write still means the app is there; the age is what decides.
  }
  return { running: true, ageMs, pid, startedAt };
}
