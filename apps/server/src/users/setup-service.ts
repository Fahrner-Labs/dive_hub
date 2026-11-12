import { timingSafeEqual } from 'node:crypto';
import { count } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { user } from '../db/schema.js';
import { newToken, sha256 } from './tokens.js';

const SETUP_TOKEN_TTL_MS = 24 * 3600_000;

/** PostgreSQL's `undefined_table`, however deep the driver and Drizzle wrapped it. */
function isMissingRelation(error: unknown): boolean {
  for (let e = error as { code?: string; cause?: unknown } | undefined; e; e = e.cause as typeof e) {
    if (e.code === '42P01') return true;
  }
  return false;
}

/**
 * First-admin bootstrap (ADR 0012). While no User exists, the server holds one setup token in memory
 * (hashed) and prints it to the log; whoever has it may create the first admin. A restart issues a new one.
 */
export function createSetupService(db: Db, now: () => number = Date.now) {
  let current: { hash: Buffer; expiresAt: number } | null = null;
  // The worker waits until setup is done (plan FAH-175): start.ts registers how to start it, the setup route calls it.
  let doneListener: (() => void | Promise<void>) | undefined;
  let done = false;

  // A restore that fails after `resetSchemas` leaves no `"user"` table. That is setup still needed, not a server
  // error: the operator can retry the archive (ADR 0069 D5), and the retry's reset is idempotent.
  const isNeeded = async () => {
    try {
      return (await db.select({ n: count() }).from(user))[0]!.n === 0;
    } catch (error) {
      if (isMissingRelation(error)) return true;
      throw error;
    }
  };

  return {
    isNeeded,

    /** A fresh setup token if no User exists yet, else null. Invalidates any earlier token. */
    async issue(): Promise<string | null> {
      if (!(await isNeeded())) return null;
      const token = newToken();
      current = { hash: Buffer.from(sha256(token), 'hex'), expiresAt: now() + SETUP_TOKEN_TTL_MS };
      return token;
    },

    /**
     * Uses up the token if it matches. Returns a function that puts it back, for when creating the
     * admin fails afterwards (e.g. an invalid e-mail) and the operator should be able to retry.
     */
    consume(token: string): (() => void) | null {
      const held = current;
      if (!held || now() > held.expiresAt) return null;
      const given = Buffer.from(sha256(token), 'hex');
      if (!timingSafeEqual(given, held.hash)) return null;
      current = null; // synchronously, so a concurrent second request can't use it too
      return () => { current ??= held; };
    },

    /**
     * How the process starts what was held back until setup existed (the in-process worker); start.ts registers it
     * before the server listens. Called once by the first-admin route.
     */
    whenDone(listener: () => void | Promise<void>): void {
      doneListener = listener;
      if (done) void listener();
    },

    /** Setup is no longer needed: run the registered listener once. */
    async notifyDone(): Promise<void> {
      if (done) return;
      done = true;
      await doneListener?.();
    },
  };
}

export type SetupService = ReturnType<typeof createSetupService>;
