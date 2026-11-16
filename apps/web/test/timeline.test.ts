// Timeline geometry for the Review decision cards (FAH-214): where a bar sits on the shared wall-clock axis, and which
// stretches between bars are gaps or overlaps.
import { describe, expect, it } from 'vitest';
import { laneFitsCapsule, timelineGeometry, type TimelineLane } from '../src/lib/timeline.ts';

const bar = (key: string, startsAt: string, durationSeconds: number, utcOffsetSeconds: number | null = 0) =>
  ({ key, startsAt, utcOffsetSeconds, durationSeconds });
const lane = (key: string, ...bars: ReturnType<typeof bar>[]): TimelineLane => ({ key, label: key, bars });

describe('timeline geometry', () => {
  it('places the bars of both lanes on one axis', () => {
    const { bars, startMs, endMs } = timelineGeometry([
      lane('recording', bar('r', '2026-01-15T10:00:00Z', 30 * 60)),
      lane('dive', bar('d', '2026-01-15T10:05:00Z', 20 * 60)),
    ]);
    // The axis runs from the earliest start to the latest end: 10:00 to 10:30.
    expect(startMs).toBe(Date.parse('2026-01-15T10:00:00Z'));
    expect(endMs).toBe(Date.parse('2026-01-15T10:30:00Z'));
    expect(bars[0]).toMatchObject({ key: 'r', lane: 0, left: 0, right: 1 });
    expect(bars[1]).toMatchObject({ key: 'd', lane: 1, left: 5 / 30, right: 25 / 30 });
  });

  it('adds each Dive\'s own offset to the shared wall-clock axis', () => {
    const { bars } = timelineGeometry([
      // 10:00 UTC at +2 h is 12:00 local; 10:10 UTC at +1 h is 11:10 local, so the second starts before it ends.
      lane('first', bar('a', '2026-01-15T10:00:00Z', 30 * 60, 2 * 3600)),
      lane('second', bar('b', '2026-01-15T10:10:00Z', 20 * 60, 1 * 3600)),
    ]);
    expect(bars[1]!.localStartMs).toBe(Date.parse('2026-01-15T11:10:00Z'));
    expect(bars[0]!.localStartMs).toBe(Date.parse('2026-01-15T12:00:00Z'));
    // The axis runs 11:10 to 12:30 local (80 minutes); the second bar ends 20 minutes in.
    expect(bars[1]!.right).toBe(20 / 80);
  });

  it('labels the space between the reference lane and a candidate as a gap', () => {
    const { gaps } = timelineGeometry([
      lane('recording', bar('r', '2026-01-15T10:00:00Z', 30 * 60)),
      lane('dive', bar('d', '2026-01-15T10:40:00Z', 30 * 60)),
    ]);
    expect(gaps).toHaveLength(1);
    expect(gaps[0]).toMatchObject({ lane: 0, seconds: 10 * 60, left: 30 / 70, right: 40 / 70 });
  });

  it('reports an overlap as the stretch the two bars share', () => {
    const { gaps, overlaps } = timelineGeometry([
      lane('recording', bar('r', '2026-01-15T10:00:00Z', 30 * 60)),
      lane('dive', bar('d', '2026-01-15T10:10:00Z', 30 * 60)),
    ]);
    expect(gaps).toEqual([]);
    expect(overlaps).toEqual([expect.objectContaining({ seconds: 20 * 60, left: 10 / 40, right: 30 / 40 })]);
  });

  it('labels the breaks between the parts of one Dive as gaps in its lane', () => {
    const { gaps } = timelineGeometry([
      lane('parts', bar('a', '2026-01-15T10:00:00Z', 30 * 60), bar('b', '2026-01-15T10:34:00Z', 25 * 60)),
    ]);
    expect(gaps).toEqual([expect.objectContaining({ lane: 0, seconds: 4 * 60 })]);
  });

  it('labels every break when a Dive has more than two parts', () => {
    const { gaps } = timelineGeometry([
      lane(
        'parts',
        bar('a', '2026-01-15T10:00:00Z', 30 * 60),
        bar('b', '2026-01-15T10:34:00Z', 25 * 60),
        bar('c', '2026-01-15T11:05:00Z', 20 * 60),
      ),
    ]);
    expect(gaps).toEqual([
      expect.objectContaining({ lane: 0, seconds: 4 * 60 }),
      expect.objectContaining({ lane: 0, seconds: 6 * 60 }),
    ]);
  });

  it('labels the gap when the reference starts after the candidate', () => {
    const { gaps, overlaps } = timelineGeometry([
      lane('recording', bar('r', '2026-01-15T10:10:00Z', 30 * 60)),
      lane('dive', bar('d', '2026-01-15T10:00:00Z', 5 * 60)),
    ]);
    expect(overlaps).toEqual([]);
    expect(gaps).toEqual([expect.objectContaining({ lane: 0, seconds: 5 * 60, left: 5 / 40, right: 10 / 40 })]);
  });

  it('does not call the same space a gap twice', () => {
    const { gaps } = timelineGeometry([
      lane('dive', bar('a', '2026-01-15T10:00:00Z', 30 * 60)),
      lane('recording', bar('b', '2026-01-15T10:40:00Z', 10 * 60)),
      lane('other', bar('c', '2026-01-15T10:40:00Z', 10 * 60)),
    ]);
    expect(gaps).toHaveLength(1);
  });
});

describe('timeline capsule fit', () => {
  it('carries the times inside a bar that spans at least 45% of the track', () => {
    const geometry = timelineGeometry([
      lane('recording', bar('r', '2026-01-15T10:00:00Z', 30 * 60)),
      lane('dive', bar('d', '2026-01-15T10:05:00Z', 20 * 60)),
    ]);
    // The recording spans the whole axis and the dive 5/30 to 25/30 (67% of it): both fit the capsule.
    expect(laneFitsCapsule(geometry, 0)).toBe(true);
    expect(laneFitsCapsule(geometry, 1)).toBe(true);
  });

  it('accepts a bar at exactly 45% of the track', () => {
    const geometry = timelineGeometry([
      lane('recording', bar('r', '2026-01-15T10:00:00Z', 100 * 60)),
      lane('dive', bar('d', '2026-01-15T10:55:00Z', 45 * 60)),
    ]);
    expect(laneFitsCapsule(geometry, 0)).toBe(true);
    expect(laneFitsCapsule(geometry, 1)).toBe(true);
  });

  it('falls back to the caption under a bar shorter than 45% of the track', () => {
    const geometry = timelineGeometry([
      lane('recording', bar('r', '2026-01-15T10:00:00Z', 50 * 60)),
      lane('dive', bar('d', '2026-01-15T10:35:00Z', 10 * 60)),
    ]);
    // The dive spans 10 of 50 minutes (20%): three labels would not fit inside it.
    expect(laneFitsCapsule(geometry, 0)).toBe(true);
    expect(laneFitsCapsule(geometry, 1)).toBe(false);
  });

  it('falls back for a whole part chain when one part is short', () => {
    const geometry = timelineGeometry([
      lane(
        'parts',
        bar('a', '2026-01-15T10:00:00Z', 20 * 60),
        bar('b', '2026-01-15T10:25:00Z', 5 * 60),
      ),
    ]);
    // The 5-minute part is 1/6 of the 30-minute axis: the lane keeps A's captions under every part.
    expect(laneFitsCapsule(geometry, 0)).toBe(false);
  });
});
