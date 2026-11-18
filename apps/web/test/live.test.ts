// The browser's live channel (ADR 0066): the first stream open must resync too, so a change committed between the
// last poll and the stream opening is not lost (FAH-249).
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import type { QueryClient } from '@tanstack/react-query';

type Listener = (event: MessageEvent<string>) => void;

/** An EventSource the test opens and emits on by hand. */
class FakeEventSource {
  static instances: FakeEventSource[] = [];
  readonly listeners = new Map<string, Listener[]>();
  closed = false;

  constructor(readonly url: string) {
    FakeEventSource.instances.push(this);
  }

  addEventListener(type: string, listener: Listener): void {
    const listeners = this.listeners.get(type) ?? [];
    listeners.push(listener);
    this.listeners.set(type, listeners);
  }

  emit(type: string, data = ''): void {
    for (const listener of this.listeners.get(type) ?? []) listener({ data } as MessageEvent<string>);
  }

  close(): void {
    this.closed = true;
  }
}

/** A BroadcastChannel that only records what the tab announces. */
class FakeBroadcastChannel {
  readonly messages: unknown[] = [];

  addEventListener(): void {}

  postMessage(message: unknown): void {
    this.messages.push(message);
  }

  close(): void {}
}

const locks = {
  request: (_name: string, _options: unknown, callback: () => Promise<void>) => callback(),
};

beforeEach(() => {
  FakeEventSource.instances = [];
  vi.stubGlobal('window', new EventTarget());
  vi.stubGlobal('EventSource', FakeEventSource);
  vi.stubGlobal('BroadcastChannel', FakeBroadcastChannel);
  vi.stubGlobal('navigator', { locks });
});

afterEach(() => {
  vi.unstubAllGlobals();
  vi.resetModules();
});

/** Starts the channel with a fake QueryClient and returns the stream the test drives. */
async function start(): Promise<{ source: FakeEventSource; invalidateQueries: ReturnType<typeof vi.fn>; stop: () => void }> {
  const { startLive } = await import('../src/lib/live.ts');
  const invalidateQueries = vi.fn(async () => undefined);
  const queryClient = {
    invalidateQueries,
    getQueryCache: () => ({ getAll: () => [] }),
  } as unknown as QueryClient;
  const stop = startLive(queryClient);
  const source = FakeEventSource.instances.at(-1);
  if (!source) throw new Error('startLive opened no stream');
  return { source, invalidateQueries, stop };
}

describe('the live channel resync', () => {
  it('refetches what is on screen on the first stream open', async () => {
    const { source, invalidateQueries, stop } = await start();
    source.emit('open');
    expect(invalidateQueries).toHaveBeenCalledOnce();
    expect(invalidateQueries).toHaveBeenCalledWith(expect.objectContaining({ refetchType: 'active' }));
    stop();
  });

  it('refetches again when the stream reconnects', async () => {
    const { source, invalidateQueries, stop } = await start();
    source.emit('open');
    source.emit('error');
    source.emit('open');
    expect(invalidateQueries).toHaveBeenCalledTimes(2);
    stop();
  });

  it('refetches once per open, never twice', async () => {
    const { source, invalidateQueries, stop } = await start();
    source.emit('open');
    await new Promise((resolve) => setTimeout(resolve, 10));
    expect(invalidateQueries).toHaveBeenCalledOnce();
    stop();
  });
});
