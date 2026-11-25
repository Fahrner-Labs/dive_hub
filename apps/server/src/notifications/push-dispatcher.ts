// The push dispatcher (ADR 0085): a dive hint queues one `send-push` job per User in the write's own transaction (the
// transactional-outbox pattern of graphile_worker.add_job), and the job sends the hint to every device that User
// registered. Push is optional: without FCM nothing is queued and the job is a no-op, because polling is the fallback.
import { and, eq, sql } from 'drizzle-orm';
import type { FcmConfig } from '../config.js';
import type { Db, Queryable } from '../db/client.js';
import { pushDevice } from '../db/schema.js';
import type { PushPlatform } from '../vocabulary.js';
import { createFcmProvider, type Fetch } from './fcm-provider.js';
import { PushTokenGoneError, type PushHint, type PushProvider } from './push-provider.js';

/** The graphile-worker task native push runs as. */
export const SEND_PUSH_TASK = 'send-push';

/** What the change listener asks the dispatcher to queue: a kind, the changed id and who may already read it. */
export interface QueuedHint {
  kind: 'dive';
  id: string;
  audience: string[];
}

export interface PushDispatcher {
  /** Whether FCM is configured; without it the dispatcher neither queues nor sends. */
  readonly enabled: boolean;
  /** Queues one job per User in the caller's transaction; a rolled-back write queues nothing. */
  enqueue(q: Queryable, hint: QueuedHint): Promise<void>;
  /** The `send-push` job: sends the hint to each device of its User, or does nothing when push is off. */
  send(payload: unknown): Promise<void>;
}

/** The job payload graphile stores; malformed ones fail the job with a clear message instead of sending anything. */
function hintOf(payload: unknown): PushHint {
  if (typeof payload !== 'object' || payload === null) throw new Error('the send-push job has no payload');
  const { userId, kind, id } = payload as Record<string, unknown>;
  if (typeof userId !== 'string' || userId === '') throw new Error('the send-push job payload has no userId');
  if (kind !== 'dive') throw new Error(`the send-push job payload has an unknown kind: ${String(kind)}`);
  if (typeof id !== 'string' || id === '') throw new Error('the send-push job payload has no id');
  return { v: 1, kind: 'dive', id, userId };
}

export function createPushDispatcher(options: {
  db: Db;
  fcm: FcmConfig | undefined;
  /** Where the FCM provider's HTTP goes; tests pass a stand-in, production the global fetch. */
  fetch?: Fetch;
  /** The FCM provider's clock for token expiry; tests pass a settled one. */
  now?: () => number;
}): PushDispatcher {
  const { db, fcm, fetch, now } = options;
  const providers = new Map<PushPlatform, PushProvider>();
  if (fcm) {
    providers.set('android', createFcmProvider({
      serviceAccount: fcm.serviceAccount,
      ...(fetch && { fetch }),
      ...(now && { now }),
    }));
  }

  return {
    get enabled() {
      return fcm !== undefined;
    },

    async enqueue(q: Queryable, hint: QueuedHint): Promise<void> {
      // ADR 0085: with push off, no job is ever queued; clients keep the foreground stream and polling.
      if (!fcm) return;
      for (const userId of new Set(hint.audience)) {
        if (userId.length === 0) continue;
        // Inside the writing transaction: a rolled-back write queues no push, a committed one always does.
        await q.execute(sql`select graphile_worker.add_job(${SEND_PUSH_TASK},
          json_build_object('userId', ${userId}::text, 'kind', ${hint.kind}::text, 'id', ${hint.id}::text))`);
      }
    },

    async send(payload: unknown): Promise<void> {
      if (!fcm) return;
      const hint = hintOf(payload);
      const devices = await db.select({ id: pushDevice.id, platform: pushDevice.platform, pushToken: pushDevice.pushToken })
        .from(pushDevice)
        .where(eq(pushDevice.userId, hint.userId));
      let firstFailure: unknown;
      for (const device of devices) {
        const provider = providers.get(device.platform);
        // iOS and web registrations exist before their providers do (ADR 0085); they wait, they do not fail the job.
        if (!provider) continue;
        try {
          await provider.send(device, hint);
        } catch (error) {
          if (error instanceof PushTokenGoneError) {
            // The platform no longer knows this token: keep it out of every later fan-out.
            await db.delete(pushDevice).where(and(eq(pushDevice.id, device.id), eq(pushDevice.pushToken, device.pushToken)));
            continue;
          }
          // Try the User's other devices now; the first failure still fails the job, so graphile retries with backoff.
          firstFailure ??= error;
        }
      }
      if (firstFailure) throw firstFailure;
    },
  };
}
