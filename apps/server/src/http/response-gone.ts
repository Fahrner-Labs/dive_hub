// A response is gone when its answer finished or its connection closed. Both mean the work behind it is done, so an
// after-response action (such as the first-start restore's process exit) must run on either, exactly once.
import type { EventEmitter } from 'node:events';

/** The state Node's `ServerResponse` keeps even after emitting `close` or `finish` before we listen. */
interface ResponseState {
  destroyed?: boolean | undefined;
  writableFinished?: boolean | undefined;
}

/** Calls `callback` once, on the response's `finish`, `close` or `error`, whichever comes first. */
export function whenResponseGone(response: EventEmitter & ResponseState, callback: () => void): void {
  let called = false;
  const fire = () => {
    if (called) return;
    called = true;
    response.off('finish', fire);
    response.off('close', fire);
    response.off('error', fire);
    callback();
  };
  response.once('finish', fire);
  response.once('close', fire);
  response.once('error', fire);
  // A client can disconnect while the handler works, so `close` may have fired before these listeners existed.
  // The response still says so; fire now rather than leave work waiting for an event that already passed.
  if (response.destroyed || response.writableFinished) fire();
}
