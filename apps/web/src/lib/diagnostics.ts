// Local client diagnostics (ADR 0080): errors stay in a small ring buffer on the device until the User presses
// "Send diagnostics". Capture is always on; only the upload is gated by the instance setting. Every storage failure is
// swallowed: a browser with localStorage turned off must never break the app it is meant to report on.
import { ApiError } from '../api.ts';

/** The one localStorage key; its version lets a future shape clear the old one instead of reading it. */
export const DIAGNOSTICS_KEY = 'divehub.diagnostics.v1';
/** The most entries the local buffer keeps (ADR 0080 D2); the upload route takes the same number. */
export const MAX_ENTRIES = 50;
/** The most bytes the serialized buffer may take; the oldest entries go first. */
export const MAX_SERIALIZED_BYTES = 64 * 1024;
export const MESSAGE_MAX_LENGTH = 1000;
export const STACK_MAX_LENGTH = 4000;

/** What the web client tells the server it is; DIVEHUB_VERSION is baked in by the image build. */
export const CLIENT_NAME = 'web';
export const CLIENT_VERSION = import.meta.env.VITE_DIVEHUB_VERSION ?? 'dev';

export interface DiagnosticEntry {
  occurredAt: string;
  level: 'error' | 'warn';
  code?: string;
  /** The build the entry was captured in, so a stale tab's error keeps its version past a reload (FAH-456). */
  clientVersion?: string;
  message: string;
  stack?: string;
  context?: Record<string, string>;
}

/** The slice of Storage the buffer needs; tests pass a failing or in-memory one. */
export interface DiagnosticStore {
  getItem(key: string): string | null;
  setItem(key: string, value: string): void;
  removeItem(key: string): void;
}

const CHANGED_EVENT = 'divehub:diagnostics-changed';

function defaultStore(): DiagnosticStore | null {
  try {
    return globalThis.localStorage ?? null;
  } catch {
    return null;
  }
}

function notify() {
  try {
    if (typeof window !== 'undefined') window.dispatchEvent(new Event(CHANGED_EVENT));
  } catch {
    // A listener that cannot be reached changes nothing about the capture.
  }
}

/** Subscribes a component to captures and clears in this tab (and in others through the storage event). */
export function subscribeDiagnostics(listener: () => void): () => void {
  if (typeof window === 'undefined') return () => undefined;
  const storage = () => listener();
  window.addEventListener(CHANGED_EVENT, listener);
  window.addEventListener('storage', storage);
  return () => {
    window.removeEventListener(CHANGED_EVENT, listener);
    window.removeEventListener('storage', storage);
  };
}

function isEntry(value: unknown): value is DiagnosticEntry {
  if (!value || typeof value !== 'object') return false;
  const entry = value as Partial<DiagnosticEntry>;
  return typeof entry.occurredAt === 'string' && (entry.level === 'error' || entry.level === 'warn')
    && typeof entry.message === 'string';
}

/** The stored entries, oldest first; nothing for a missing, broken or unreadable value. */
export function readDiagnostics(store: DiagnosticStore | null = defaultStore()): DiagnosticEntry[] {
  if (!store) return [];
  try {
    const raw = store.getItem(DIAGNOSTICS_KEY);
    if (!raw) return [];
    const parsed: unknown = JSON.parse(raw);
    return Array.isArray(parsed) ? parsed.filter(isEntry) : [];
  } catch {
    return [];
  }
}

function writeDiagnostics(entries: DiagnosticEntry[], store: DiagnosticStore | null) {
  if (!store) return;
  try {
    store.setItem(DIAGNOSTICS_KEY, JSON.stringify(entries));
  } catch {
    // Quota exceeded or storage denied: diagnostics must never break the app.
  }
}

/** Removes the local buffer, e.g. after a successful upload. */
export function clearDiagnostics(store: DiagnosticStore | null = defaultStore()) {
  if (!store) return;
  try {
    store.removeItem(DIAGNOSTICS_KEY);
    notify();
  } catch {
    // Nothing to do about a storage that refuses to forget.
  }
}

const truncate = (text: string, max: number) => (text.length <= max ? text : text.slice(0, max));

/**
 * Drops a query string or fragment from any absolute or root-relative URL in a message or stack. Diagnostics carry no
 * tokens, so a URL that happened to hold one arrives as its path alone.
 */
export function withoutQueryStrings(text: string): string {
  return text
    .replace(/https?:\/\/[^\s?#]+[?#][^\s]*/g, (url) => url.split(/[?#]/)[0]!)
    .replace(/(^|\s)(\/[^\s?#]*)[?#][^\s]*/g, '$1$2');
}

function cleanContext(context: Record<string, string>): Record<string, string> | undefined {
  const entries = Object.entries(context)
    .slice(0, 20)
    .map(([key, value]) => [truncate(withoutQueryStrings(String(key)), 100), truncate(withoutQueryStrings(String(value)), 500)] as const)
    .filter(([key]) => key.length > 0);
  return entries.length > 0 ? Object.fromEntries(entries) : undefined;
}

function prepareEntry(entry: DiagnosticEntry): DiagnosticEntry {
  const at = new Date(entry.occurredAt);
  const clean: DiagnosticEntry = {
    occurredAt: Number.isNaN(at.getTime()) ? new Date().toISOString() : at.toISOString(),
    level: entry.level === 'warn' ? 'warn' : 'error',
    clientVersion: entry.clientVersion ?? CLIENT_VERSION,
    message: truncate(withoutQueryStrings(entry.message), MESSAGE_MAX_LENGTH),
  };
  if (entry.code) clean.code = truncate(withoutQueryStrings(entry.code), 200);
  if (entry.stack) clean.stack = truncate(withoutQueryStrings(entry.stack), STACK_MAX_LENGTH);
  if (entry.context) {
    const context = cleanContext(entry.context);
    if (context) clean.context = context;
  }
  return clean;
}

const serializedBytes = (entries: DiagnosticEntry[]) => new TextEncoder().encode(JSON.stringify(entries)).length;

/** Adds one entry, evicting the oldest until both the entry count and the serialized size fit. */
export function captureDiagnostic(entry: DiagnosticEntry, store: DiagnosticStore | null = defaultStore()) {
  if (!store) return;
  const entries = [...readDiagnostics(store), prepareEntry(entry)];
  while (entries.length > 1 && (entries.length > MAX_ENTRIES || serializedBytes(entries) > MAX_SERIALIZED_BYTES)) {
    entries.shift();
  }
  writeDiagnostics(entries, store);
  notify();
}

/** The entry for a thrown value. An API error keeps its stable code, which the server and the UI translate. */
export function diagnosticFromError(
  error: unknown, level: 'error' | 'warn' = 'error', context?: Record<string, string>,
): DiagnosticEntry {
  const message = error instanceof Error ? error.message
    : typeof error === 'string' ? error
      : 'Unknown error';
  const code = error instanceof ApiError ? error.code : undefined;
  return {
    occurredAt: new Date().toISOString(),
    level,
    clientVersion: CLIENT_VERSION,
    ...(code && { code }),
    message,
    ...(error instanceof Error && error.stack && { stack: error.stack }),
    ...(context && { context }),
  };
}

/** Captures a thrown value without ever throwing itself. */
export function captureError(
  error: unknown, options: { level?: 'error' | 'warn'; context?: Record<string, string> } = {},
) {
  try {
    captureDiagnostic(diagnosticFromError(error, options.level, options.context));
  } catch {
    // Capture is best effort.
  }
}

let installed = false;

/**
 * Registers the browser-wide capture points: `window.onerror` and `unhandledrejection`. Called once from `main.tsx`;
 * React's ErrorBoundary captures render failures of its own.
 */
export function installDiagnostics(win: Window = window) {
  if (installed) return;
  installed = true;
  win.onerror = (message, source, lineno, colno, error) => {
    captureError(error ?? message, {
      context: {
        event: 'error',
        ...(source && { source: withoutQueryStrings(source) }),
        ...(lineno && { line: String(lineno) }),
        ...(colno && { column: String(colno) }),
      },
    });
    return false;
  };
  win.addEventListener('unhandledrejection', (event) => {
    captureError((event as PromiseRejectionEvent).reason, { context: { event: 'unhandledrejection' } });
  });
}
