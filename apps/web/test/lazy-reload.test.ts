// A deploy content-hashes the web client's lazy chunks, so an open session requests files that are gone (FAH-447):
// one reload may recover it, and the guard keeps a still-broken build from reloading forever.
import { describe, expect, it, vi } from 'vitest';
import {
  CHUNK_RELOAD_KEY, RELOAD_GUARD_MS, installStaleChunkReload, isChunkLoadError, loadChunk, type ReloadGuardStore,
} from '../src/lib/lazy-reload.ts';

class MemoryStore implements ReloadGuardStore {
  readonly values = new Map<string, string>();
  getItem(key: string) { return this.values.get(key) ?? null; }
  setItem(key: string, value: string) { this.values.set(key, value); }
  removeItem(key: string) { this.values.delete(key); }
}

const chunkError = () => new TypeError(
  'Failed to fetch dynamically imported module: https://divehub.test/assets/DivePage-BRJAeRl1.js',
);
const otherError = () => new Error('DepthProfile threw while rendering');

describe('recovering from a stale lazy chunk', () => {
  it('reloads once when a lazy chunk no longer exists', async () => {
    const store = new MemoryStore();
    const reload = vi.fn();

    await expect(loadChunk(() => Promise.reject(chunkError()), { store, now: 1_000, reload }))
      .rejects.toThrow(/Failed to fetch/);

    expect(reload).toHaveBeenCalledOnce();
    expect(store.getItem(CHUNK_RELOAD_KEY)).toBe('1000');
  });

  it('does not reload again when the chunk still fails after the reload', async () => {
    const store = new MemoryStore();
    const reload = vi.fn();
    const load = () => Promise.reject(chunkError());

    await expect(loadChunk(load, { store, now: 1_000, reload })).rejects.toThrow();
    await expect(loadChunk(load, { store, now: 1_001, reload })).rejects.toThrow();

    expect(reload).toHaveBeenCalledOnce();
  });

  it('may reload again once the guard window has passed, for a later deploy', async () => {
    const store = new MemoryStore();
    const reload = vi.fn();
    const load = () => Promise.reject(chunkError());

    await expect(loadChunk(load, { store, now: 1_000, reload })).rejects.toThrow();
    await expect(loadChunk(load, { store, now: 1_000 + RELOAD_GUARD_MS, reload })).rejects.toThrow();

    expect(reload).toHaveBeenCalledTimes(2);
  });

  it('does not re-arm the guard when another chunk loads from cache', async () => {
    const store = new MemoryStore();
    const reload = vi.fn();

    await expect(loadChunk(() => Promise.reject(chunkError()), { store, now: 1_000, reload })).rejects.toThrow();
    await expect(loadChunk(() => Promise.resolve('cached page'), { store, now: 1_001, reload })).resolves.toBe('cached page');
    await expect(loadChunk(() => Promise.reject(chunkError()), { store, now: 1_002, reload })).rejects.toThrow();

    expect(reload).toHaveBeenCalledOnce();
  });

  it('never reloads for an error that is not a chunk failure', async () => {
    const store = new MemoryStore();
    const reload = vi.fn();

    await expect(loadChunk(() => Promise.reject(otherError()), { store, now: 1_000, reload }))
      .rejects.toThrow('DepthProfile threw while rendering');

    expect(reload).not.toHaveBeenCalled();
    expect(store.getItem(CHUNK_RELOAD_KEY)).toBeNull();
  });

  it('never reloads when the guard cannot be stored, so a storage failure cannot cause a loop', async () => {
    const reload = vi.fn();

    await expect(loadChunk(() => Promise.reject(chunkError()), { store: null, now: 1_000, reload }))
      .rejects.toThrow(/Failed to fetch/);

    expect(reload).not.toHaveBeenCalled();
  });

  it('recognises the browsers\' chunk failure messages', () => {
    expect(isChunkLoadError(new TypeError('Failed to fetch dynamically imported module: /assets/DivePage.js'))).toBe(true);
    expect(isChunkLoadError(new TypeError('error loading dynamically imported module'))).toBe(true);
    expect(isChunkLoadError(new Error('Importing a module script failed.'))).toBe(true);
    expect(isChunkLoadError(new Error('Loading chunk 12 failed'))).toBe(true);
    expect(isChunkLoadError(new TypeError('Failed to fetch'))).toBe(false);
    expect(isChunkLoadError('not an error at all')).toBe(false);
  });

  it('reloads once from a Vite preload error event and blocks a second one', () => {
    const store = new MemoryStore();
    const reload = vi.fn();
    const target = new EventTarget();
    installStaleChunkReload(target as unknown as Window, { store, now: 1_000, reload });

    const first = Object.assign(new Event('vite:preloadError', { cancelable: true }), { payload: chunkError() });
    target.dispatchEvent(first);
    const second = Object.assign(new Event('vite:preloadError', { cancelable: true }), { payload: chunkError() });
    target.dispatchEvent(second);

    expect(reload).toHaveBeenCalledOnce();
    expect(first.defaultPrevented).toBe(false);
    expect(second.defaultPrevented).toBe(false);
  });
});
