import { describe, expect, it } from 'vitest';
import {
  createLogRingBuffer, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES,
} from '../src/support/log-ring-buffer.js';

const line = (index: number, extra: Record<string, unknown> = {}) =>
  `${JSON.stringify({ level: 30, time: 1_700_000_000_000 + index, msg: `entry ${index}`, ...extra })}\n`;

describe('the support log ring buffer', () => {
  it('keeps the module bounds', () => {
    expect(LOG_BUFFER_CAPACITY_ENTRIES).toBe(2048);
    expect(LOG_BUFFER_CAPACITY_BYTES).toBe(2 * 1024 * 1024);
  });

  it('forwards every line unchanged and evicts oldest entries by count', () => {
    const forwarded: string[] = [];
    const buffer = createLogRingBuffer({ stdout: { write: (chunk) => { forwarded.push(chunk); return true; } }, capacityEntries: 3 });
    for (let index = 0; index < 5; index += 1) buffer.write(line(index));
    expect(forwarded.join('')).toBe([0, 1, 2, 3, 4].map((index) => line(index)).join(''));
    expect(buffer.snapshot().map((entry) => entry.msg)).toEqual(['entry 2', 'entry 3', 'entry 4']);
    expect(buffer.stats()).toMatchObject({ entries: 3, droppedEntries: 2 });
    expect(buffer.stats().oldestAt).toBe(new Date(1_700_000_000_002).toISOString());
    expect(buffer.stats().newestAt).toBe(new Date(1_700_000_000_004).toISOString());
  });

  it('evicts oldest entries by the serialized byte cap', () => {
    const buffer = createLogRingBuffer({ stdout: { write: () => true }, capacityBytes: 1_600 });
    const big = 'x'.repeat(700);
    buffer.write(line(0, { big }));
    buffer.write(line(1, { big }));
    buffer.write(line(2, { big }));
    expect(buffer.snapshot().map((entry) => entry.msg)).toEqual(['entry 1', 'entry 2']);
    expect(buffer.stats()).toMatchObject({ entries: 2, droppedEntries: 1 });
    expect(buffer.stats().bytes).toBeLessThanOrEqual(1_600);
  });

  it('drops a single entry that cannot fit and keeps logging', () => {
    const buffer = createLogRingBuffer({ stdout: { write: () => true }, capacityBytes: 100 });
    buffer.write(line(0, { big: 'x'.repeat(500) }));
    expect(buffer.stats()).toMatchObject({ entries: 0, droppedEntries: 1 });
    buffer.write(line(1));
    expect(buffer.snapshot().map((entry) => entry.msg)).toEqual(['entry 1']);
  });

  it('forwards a malformed line and skips its capture', () => {
    const forwarded: string[] = [];
    const buffer = createLogRingBuffer({ stdout: { write: (chunk) => { forwarded.push(chunk); return true; } } });
    buffer.write('not json at all\n');
    buffer.write(line(1));
    expect(forwarded.join('')).toBe(`not json at all\n${line(1)}`);
    expect(buffer.snapshot().map((entry) => entry.msg)).toEqual(['entry 1']);
    expect(buffer.stats().droppedEntries).toBe(0);
  });

  it('redacts at capture and counts redactions', () => {
    const buffer = createLogRingBuffer({ stdout: { write: () => true } });
    buffer.write(line(0, { authorization: 'Bearer secret', msg: 'mail ops@example.com from 203.0.113.9' }));
    const [entry] = buffer.snapshot();
    expect(entry).toMatchObject({ authorization: '‹redacted›', msg: 'mail ‹email› from ‹ip›' });
    expect(buffer.redactionCounters()).toEqual({ keysCensored: 1, emailsMasked: 1, ipsMasked: 1 });
  });

  it('returns snapshots isolated from the buffer and from one another', () => {
    const buffer = createLogRingBuffer({ stdout: { write: () => true } });
    buffer.write(line(0, { nested: { value: 'first' } }));
    const first = buffer.snapshot();
    (first[0]!.nested as { value: string }).value = 'mutated';
    buffer.write(line(1));
    const second = buffer.snapshot();
    expect(second[0]!.nested).toEqual({ value: 'first' });
    expect(second.map((entry) => entry.msg)).toEqual(['entry 0', 'entry 1']);
  });
});
