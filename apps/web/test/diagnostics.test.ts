import { describe, expect, it } from 'vitest';
import { ApiError } from '../src/api/request.ts';
import {
  captureDiagnostic, clearDiagnostics, CLIENT_VERSION, DIAGNOSTICS_KEY, diagnosticFromError, MAX_ENTRIES,
  MAX_SERIALIZED_BYTES, readDiagnostics, withoutQueryStrings, type DiagnosticEntry, type DiagnosticStore,
} from '../src/lib/diagnostics.ts';

class MemoryStore implements DiagnosticStore {
  readonly values = new Map<string, string>();
  getItem(key: string) { return this.values.get(key) ?? null; }
  setItem(key: string, value: string) { this.values.set(key, value); }
  removeItem(key: string) { this.values.delete(key); }
}

class BrokenStore implements DiagnosticStore {
  getItem(): string | null { throw new Error('storage denied'); }
  setItem(): void { throw new Error('storage denied'); }
  removeItem(): void { throw new Error('storage denied'); }
}

const entry = (message: string, overrides: Partial<DiagnosticEntry> = {}): DiagnosticEntry => ({
  occurredAt: '2026-11-20T10:00:00.000Z', level: 'error', message, ...overrides,
});

const bytes = (entries: DiagnosticEntry[]) => new TextEncoder().encode(JSON.stringify(entries)).length;

describe('the local diagnostics buffer', () => {
  it('keeps at most 50 entries, dropping the oldest first', () => {
    const store = new MemoryStore();
    for (let index = 0; index < MAX_ENTRIES + 5; index += 1) captureDiagnostic(entry(`failure ${index}`), store);

    const kept = readDiagnostics(store);
    expect(kept).toHaveLength(MAX_ENTRIES);
    expect(kept[0]!.message).toBe('failure 5');
    expect(kept.at(-1)!.message).toBe(`failure ${MAX_ENTRIES + 4}`);
  });

  it('stays within 64 KiB serialized, keeping the newest entries', () => {
    const store = new MemoryStore();
    for (let index = 0; index < 20; index += 1) {
      captureDiagnostic(entry(`failure ${index}`, { stack: 's'.repeat(4000) }), store);
    }

    const kept = readDiagnostics(store);
    expect(kept.length).toBeLessThan(20);
    expect(kept.length).toBeGreaterThan(0);
    expect(bytes(kept)).toBeLessThanOrEqual(MAX_SERIALIZED_BYTES);
    expect(kept.at(-1)!.message).toBe('failure 19');
  });

  it('swallows every storage failure', () => {
    const store = new BrokenStore();
    expect(() => captureDiagnostic(entry('failure'), store)).not.toThrow();
    expect(readDiagnostics(store)).toEqual([]);
    expect(() => clearDiagnostics(store)).not.toThrow();
  });

  it('reads nothing from a missing, broken or foreign value', () => {
    const missing = new MemoryStore();
    expect(readDiagnostics(missing)).toEqual([]);

    const broken = new MemoryStore();
    broken.setItem(DIAGNOSTICS_KEY, '{not json');
    expect(readDiagnostics(broken)).toEqual([]);

    const foreign = new MemoryStore();
    foreign.setItem(DIAGNOSTICS_KEY, JSON.stringify([{ nope: true }]));
    expect(readDiagnostics(foreign)).toEqual([]);
  });

  it('clears the key', () => {
    const store = new MemoryStore();
    captureDiagnostic(entry('failure'), store);
    expect(store.getItem(DIAGNOSTICS_KEY)).toBeTruthy();
    clearDiagnostics(store);
    expect(store.getItem(DIAGNOSTICS_KEY)).toBeNull();
  });
});

describe('a captured entry', () => {
  it('truncates the message, stack and context to their limits', () => {
    const store = new MemoryStore();
    captureDiagnostic(entry('m'.repeat(2000), {
      stack: 's'.repeat(5000),
      context: { note: 'v'.repeat(2000) },
    }), store);

    const [kept] = readDiagnostics(store);
    expect(kept!.message).toHaveLength(1000);
    expect(kept!.stack).toHaveLength(4000);
    expect(kept!.context!.note).toHaveLength(500);
  });

  it('keeps no query string or fragment from a URL', () => {
    expect(withoutQueryStrings('GET https://dive.example/api/dives?token=secret#top failed'))
      .toBe('GET https://dive.example/api/dives failed');
    expect(withoutQueryStrings('at /api/dives/42?tab=notes')).toBe('at /api/dives/42');

    const store = new MemoryStore();
    captureDiagnostic(entry('failed at https://dive.example/api/import?key=secret', {
      stack: 'at https://dive.example/assets/app.js?v=secret:1:2',
      context: { source: 'https://dive.example/app.js?token=secret' },
    }), store);
    const [kept] = readDiagnostics(store);
    expect(JSON.stringify(kept)).not.toMatch(/secret/);
    expect(kept!.context!.source).toBe('https://dive.example/app.js');
  });

  it('keeps the stable code of one of our API errors only', () => {
    expect(diagnosticFromError(new ApiError('nope', 429, 'client_diagnostics_limited')).code)
      .toBe('client_diagnostics_limited');
    expect(diagnosticFromError(new Error('plain')).code).toBeUndefined();
  });
});

describe('the capture-time client version', () => {
  it('stamps a captured entry with the build it was captured in', () => {
    const store = new MemoryStore();
    captureDiagnostic(entry('failure'), store);
    expect(readDiagnostics(store)[0]!.clientVersion).toBe(CLIENT_VERSION);
  });

  it('stamps an entry built from an error with the build it was captured in', () => {
    expect(diagnosticFromError(new Error('plain')).clientVersion).toBe(CLIENT_VERSION);
  });

  it('keeps a version the caller already set', () => {
    const store = new MemoryStore();
    captureDiagnostic(entry('failure', { clientVersion: '0.3.0-rc3' }), store);
    expect(readDiagnostics(store)[0]!.clientVersion).toBe('0.3.0-rc3');
  });
});
