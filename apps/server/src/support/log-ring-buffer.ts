import { Writable } from 'node:stream';
import {
  emptyRedactionCounters, redactLogObject, redactValue, type RedactionCounters,
} from './redact.js';

// A support bundle should cover hours, not the last ~75 minutes: 4x the entries and bytes of the first bound (FAH-450).
export const LOG_BUFFER_CAPACITY_ENTRIES = 8192;
export const LOG_BUFFER_CAPACITY_BYTES = 8 * 1024 * 1024;

export interface LogBufferStats {
  entries: number;
  droppedEntries: number;
  oldestAt: string | null;
  newestAt: string | null;
  bytes: number;
}

interface BufferedLog {
  at: string;
  bytes: number;
  value: Record<string, unknown>;
}

export interface LogRingBufferOptions {
  capacityEntries?: number;
  capacityBytes?: number;
  stdout?: { write(chunk: string): boolean };
  now?: () => number;
}

export class LogRingBuffer extends Writable {
  private readonly entries: BufferedLog[] = [];
  private readonly capacityEntries: number;
  private readonly capacityBytes: number;
  private readonly stdout: { write(chunk: string): boolean };
  private readonly now: () => number;
  private readonly counters: RedactionCounters = emptyRedactionCounters();
  private bytes = 0;
  private droppedEntries = 0;
  private pending = '';

  constructor(options: LogRingBufferOptions = {}) {
    super({ decodeStrings: false });
    this.capacityEntries = options.capacityEntries ?? LOG_BUFFER_CAPACITY_ENTRIES;
    this.capacityBytes = options.capacityBytes ?? LOG_BUFFER_CAPACITY_BYTES;
    this.stdout = options.stdout ?? process.stdout;
    this.now = options.now ?? Date.now;
  }

  override _write(chunk: string | Buffer, _encoding: BufferEncoding, callback: (error?: Error | null) => void): void {
    const text = typeof chunk === 'string' ? chunk : chunk.toString('utf8');
    try {
      this.stdout.write(text);
    } catch {
      // Logging stays usable even when its destination is gone.
    }
    try {
      this.capture(text);
    } catch {
      // A capture fault must never break logging.
    }
    callback();
  }

  override _final(callback: (error?: Error | null) => void): void {
    try {
      if (this.pending.length > 0) this.captureLine(this.pending);
    } catch {
      // A capture fault must never break logging.
    }
    this.pending = '';
    callback();
  }

  private capture(text: string): void {
    const lines = (this.pending + text).split('\n');
    this.pending = lines.pop() ?? '';
    for (const line of lines) {
      if (line.length > 0) this.captureLine(line);
    }
  }

  private captureLine(line: string): void {
    const parsed: unknown = JSON.parse(line);
    const value = parsed !== null && typeof parsed === 'object' && !Array.isArray(parsed)
      ? redactLogObject(parsed as Record<string, unknown>, this.counters)
      : { value: redactValue(parsed, undefined, this.counters) };
    const time = this.timeOf(value);
    const bytes = Buffer.byteLength(JSON.stringify(value)) + 1;
    this.entries.push({ at: time, bytes, value });
    this.bytes += bytes;
    while (this.entries.length > this.capacityEntries || this.bytes > this.capacityBytes) {
      const evicted = this.entries.shift();
      if (!evicted) break;
      this.bytes -= evicted.bytes;
      this.droppedEntries += 1;
    }
  }

  private timeOf(value: Record<string, unknown>): string {
    const raw = value.time;
    const fromValue = typeof raw === 'number' ? raw : typeof raw === 'string' ? Date.parse(raw) : Number.NaN;
    return new Date(Number.isFinite(fromValue) ? fromValue : this.now()).toISOString();
  }

  snapshot(): Record<string, unknown>[] {
    return this.entries.map((entry) => structuredClone(entry.value));
  }

  stats(): LogBufferStats {
    return {
      entries: this.entries.length,
      droppedEntries: this.droppedEntries,
      oldestAt: this.entries[0]?.at ?? null,
      newestAt: this.entries.at(-1)?.at ?? null,
      bytes: this.bytes,
    };
  }

  redactionCounters(): RedactionCounters {
    return { ...this.counters };
  }
}

export const createLogRingBuffer = (options: LogRingBufferOptions = {}): LogRingBuffer => new LogRingBuffer(options);
