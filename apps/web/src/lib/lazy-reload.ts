// A deploy content-hashes the web client's lazy chunks, so an open session asks for files that are gone (FAH-447).
// One automatic reload fetches the new build; a per-tab marker keeps a still-broken build from reloading forever,
// and only chunk-load failures may reload — any other error is the ErrorBoundary's business.
import { lazy, type ComponentProps, type ElementType, type ExoticComponent } from 'react';
import { captureError } from './diagnostics.ts';

/** The one sessionStorage key; its version lets a future shape ignore the old one. */
export const CHUNK_RELOAD_KEY = 'divehub.chunk-reload.v1';
/**
 * How long after an automatic reload another chunk failure counts as the same broken build. A real reload takes
 * seconds; the window only has to outlast it, so a later deploy may still recover once.
 */
export const RELOAD_GUARD_MS = 60_000;
/** Vite fires this on `window` when a dynamic import's module or CSS preload fails. */
export const PRELOAD_ERROR_EVENT = 'vite:preloadError';

/** The slice of sessionStorage the guard needs; tests pass an in-memory or failing one. */
export interface ReloadGuardStore {
  getItem(key: string): string | null;
  setItem(key: string, value: string): void;
  removeItem(key: string): void;
}

export interface ReloadOptions {
  store?: ReloadGuardStore | null;
  now?: number;
  reload?: () => void;
}

function defaultStore(): ReloadGuardStore | null {
  try {
    return globalThis.sessionStorage ?? null;
  } catch {
    return null;
  }
}

function storeOf(options: ReloadOptions): ReloadGuardStore | null {
  return options.store === undefined ? defaultStore() : options.store;
}

const CHUNK_ERROR = /Failed to fetch dynamically imported module|error loading dynamically imported module|Importing a module script failed|ChunkLoadError|Loading chunk \d+ failed|Unable to preload CSS/i;

/** Chromium, Firefox and Safari word a missing chunk differently; a module that throws is not a missing chunk. */
export function isChunkLoadError(error: unknown): boolean {
  const message = error instanceof Error ? error.message : typeof error === 'string' ? error : '';
  return CHUNK_ERROR.test(message);
}

/** Reserves the one reload: false while a recent one is remembered, whatever the build says. */
function takeReloadSlot(store: ReloadGuardStore | null, now: number): boolean {
  if (!store) return false;
  let last: number | null = null;
  try {
    const raw = store.getItem(CHUNK_RELOAD_KEY);
    if (raw !== null) {
      const parsed = Number(raw);
      if (Number.isFinite(parsed)) last = parsed;
    }
  } catch {
    return false;
  }
  // A clock that went backwards also counts as recent, never as permission to loop.
  if (last !== null && now - last < RELOAD_GUARD_MS) return false;
  try {
    store.setItem(CHUNK_RELOAD_KEY, String(now));
    return true;
  } catch {
    return false;
  }
}

/** Reloads once for a stale chunk; false for any other error, for a recent reload, or when no guard can be kept. */
function reloadForStaleChunk(error: unknown, options: ReloadOptions): boolean {
  if (!isChunkLoadError(error)) return false;
  if (!takeReloadSlot(storeOf(options), options.now ?? Date.now())) return false;
  captureError(error, { level: 'warn', context: { event: 'stale-chunk' } });
  (options.reload ?? (() => location.reload()))();
  return true;
}

/**
 * The lazy import every page uses: a rejected chunk load reloads once, then still throws, so the ErrorBoundary
 * shows its fallback and captures the error. A later chunk that loads from cache does not re-arm the guard.
 */
export async function loadChunk<T>(load: () => Promise<T>, options: ReloadOptions = {}): Promise<T> {
  try {
    return await load();
  } catch (error) {
    reloadForStaleChunk(error, options);
    throw error;
  }
}

// React's lazy() ties the result to `ComponentType<any>`; the wrapper keeps the page's own props instead.
const lazyPage = lazy as unknown as <C extends ElementType>(
  load: () => Promise<{ default: C }>,
) => ExoticComponent<ComponentProps<C>>;

/** `lazy()` for a page whose import recovers from a deploy by reloading once (`loadChunk`). */
export function lazyWithReload<C extends ElementType>(
  load: () => Promise<{ default: C }>,
): ExoticComponent<ComponentProps<C>> {
  return lazyPage(() => loadChunk(load));
}

type PreloadErrorEvent = Event & { payload?: unknown };

let installed = false;

/** Registers Vite's preload-error event, for chunk failures whose rejected import no `lazy()` catches. */
export function installStaleChunkReload(win: Window = window, options: ReloadOptions = {}) {
  if (installed) return;
  installed = true;
  win.addEventListener(PRELOAD_ERROR_EVENT, (event) => {
    const payload = (event as PreloadErrorEvent).payload;
    // The event itself says a chunk was missing, even when Vite sends no payload.
    reloadForStaleChunk(payload ?? 'Failed to fetch dynamically imported module', options);
  });
}
