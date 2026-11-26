// The provider seam of push dispatch (ADR 0085): one sender per client platform. A sender takes a device and a hint,
// delivers the high-priority data message, and throws `PushTokenGoneError` when the platform no longer knows the token.
import type { PushPlatform } from '../vocabulary.js';

/**
 * The hint a push wakes a client with, exactly the SSE hint of ADR 0066 in shape: `{ v, kind, id }`, never content.
 * `userId` is not part of the message; it only forms FCM's collapse key, so one User's pushes of a kind replace each
 * other on the same device.
 */
export interface PushHint {
  v: 1;
  kind: 'dive';
  id: string;
  userId: string;
}

/** One registered device to send to; `id` is the row's, so a gone token's row can be removed. */
export interface PushTarget {
  id: string;
  platform: PushPlatform;
  pushToken: string;
}

/** The platform says the token is no longer registered: the registration is useless, so its row is deleted. */
export class PushTokenGoneError extends Error {
  constructor(readonly platform: PushPlatform, detail: string) {
    super(`${platform}: the push token is no longer registered (${detail})`);
    this.name = 'PushTokenGoneError';
  }
}

/** `sendPush` (ADR 0085): Android over FCM now; iOS and web join as further providers behind the dispatcher. */
export interface PushProvider {
  readonly platform: PushPlatform;
  send(device: PushTarget, hint: PushHint): Promise<void>;
}
