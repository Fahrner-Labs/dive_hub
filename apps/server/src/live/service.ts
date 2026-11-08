// The live layer of one process (ADR 0066): the hub of SSE streams and the one LISTEN connection that feeds it.
// Without an explicit connection string there is no listener, so the route is not mounted and clients keep polling.
import { createHub, type LiveHub } from './hub.js';
import { createListener, type LiveListener, type LiveLog } from './listener.js';

export interface Live {
  /** Whether this process can hear hints, so the live route may be mounted. */
  enabled: boolean;
  hub: LiveHub;
  /** Whether the LISTEN connection is up now; configuring one is not enough for clients to suspend polling. */
  ready(): boolean;
  start(): void;
  stop(): Promise<void>;
}

export function createLive({ connectionString, log }: { connectionString?: string; log?: LiveLog }): Live {
  const hub = createHub();
  const logger: LiveLog = log ?? {
    info: (detail, message) => console.info(message, detail),
    warn: (detail, message) => console.warn(message, detail),
  };
  const listener: LiveListener | null = connectionString
    ? createListener({ connectionString, hub, log: logger })
    : null;
  return {
    enabled: listener !== null,
    hub,
    ready: () => listener?.ready() ?? false,
    start() {
      if (!listener) {
        logger.warn({}, 'the live channel is off: no LISTEN connection string was given, so clients keep polling');
        return;
      }
      listener.start();
    },
    async stop() {
      await listener?.stop();
      hub.stop();
      hub.closeAll();
    },
  };
}
