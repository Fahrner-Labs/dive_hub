// Push registrations (ADR 0085): the public Firebase client config and the session-scoped push tokens native clients
// register. Nothing here sends a message; the dispatcher and FCM sender are the next slice of the feature.
import { and, eq } from 'drizzle-orm';
import type { FcmClientConfig, FcmConfig } from '../config.js';
import type { Db } from '../db/client.js';
import { pushDevice } from '../db/schema.js';
import type { PushPlatform } from '../vocabulary.js';

/** Longest token we accept; FCM tokens are far shorter, but a bound keeps the row and request sensible. */
export const MAX_PUSH_TOKEN_LENGTH = 4096;

/** What a client needs to find and later remove its registration. The token itself is never echoed back. */
export interface PushDeviceView {
  id: string;
  platform: PushPlatform;
  createdAt: string;
  lastSeenAt: string;
}

/** The public part of the instance's FCM setup; `android` is absent when push is off (ADR 0085). */
export interface PushConfigView {
  android?: FcmClientConfig;
}

const toView = (row: typeof pushDevice.$inferSelect): PushDeviceView => ({
  id: row.id,
  platform: row.platform,
  createdAt: row.createdAt.toISOString(),
  lastSeenAt: row.lastSeenAt.toISOString(),
});

export function createNotificationService(db: Db, fcm: FcmConfig | undefined) {
  return {
    /** The Firebase client config, or an empty object when the instance has no FCM credential (start polling). */
    config(): PushConfigView {
      return fcm ? { android: fcm.android } : {};
    },

    /**
     * Register or refresh a push token for one User and session. The token is unique: registering it again moves it to
     * the latest User and session (a phone signed in as someone else) and refreshes `lastSeenAt`.
     */
    async register(
      userId: string, sessionId: string, input: { platform: PushPlatform; pushToken: string },
    ): Promise<PushDeviceView> {
      const now = new Date();
      const [row] = await db.insert(pushDevice)
        .values({ userId, sessionId, platform: input.platform, pushToken: input.pushToken, lastSeenAt: now })
        .onConflictDoUpdate({
          target: pushDevice.pushToken,
          set: { userId, sessionId, platform: input.platform, lastSeenAt: now },
        })
        .returning();
      return toView(row!);
    },

    /** Remove one of the User's own registrations; false when it is gone or someone else's (the route answers 404). */
    async remove(userId: string, id: string): Promise<boolean> {
      const removed = await db.delete(pushDevice)
        .where(and(eq(pushDevice.id, id), eq(pushDevice.userId, userId)))
        .returning({ id: pushDevice.id });
      return removed.length > 0;
    },
  };
}

export type NotificationService = ReturnType<typeof createNotificationService>;
