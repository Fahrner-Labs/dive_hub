// Current residual loadings from the Diver's logged dives (ADR 0071): N₂ replayed through ZHL-16C, CNS decayed
// with the 90-minute half-life, OTU summed over 24 h, and the staleness and provenance rules.
import { describe, expect, it } from 'vitest';
import {
  cnsDecayed, N2_REPLAY_WINDOW_HOURS, OTU_WINDOW_HOURS, otuLast24h, tissuesAfterDives, type LoadingDive,
} from '../src/planning/loadings.js';
import { surfaceSaturatedTissues, worstCompartmentPercent } from '../src/planning/tissues.js';

const NOW = Date.UTC(2026, 10, 16, 12, 0, 0);
const HOUR_MS = 3_600_000;
const AIR = { o2: 21, he: 0 };
const SALT = { water: 'salt', altitudeM: 0 } as const;

/** A 30 m dive of `minutes` in all, with a one-minute descent and ascent at the ends. */
function profile(minutes: number) {
  return [
    { tSeconds: 0, depthM: 0 },
    { tSeconds: 60, depthM: 30 },
    { tSeconds: (minutes - 1) * 60, depthM: 30 },
    { tSeconds: minutes * 60, depthM: 0 },
  ];
}

function diveEnding(endMs: number, minutes = 20): LoadingDive {
  return { startMs: endMs - minutes * 60_000, endMs, samples: profile(minutes), gas: AIR };
}

describe('tissuesAfterDives', () => {
  it('raises the worst-compartment load above clean surface saturation', () => {
    const clean = worstCompartmentPercent(surfaceSaturatedTissues(SALT), 85, SALT);
    const loadings = tissuesAfterDives({ dives: [diveEnding(NOW)], nowMs: NOW, gfHigh: 85, conditions: SALT });
    expect(loadings.worstCompartmentPercent).not.toBeNull();
    expect(loadings.worstCompartmentPercent!).toBeGreaterThan(clean);
  });

  it('returns to surface saturation within tolerance after 48 h off-gassing on air', () => {
    const ended = NOW - 48 * HOUR_MS;
    const clean = surfaceSaturatedTissues(SALT);
    const loadings = tissuesAfterDives({ dives: [diveEnding(ended)], nowMs: NOW, gfHigh: 85, conditions: SALT });
    expect(loadings.worstCompartmentPercent).not.toBeNull();
    expect(loadings.tissues).toHaveLength(clean.length);
    // The slowest compartment is the last to clear: 48 h is ~4.5 of its 635-minute half-times, so a few hundredths
    // of a bar of the dive's excess remain.
    loadings.tissues!.forEach((pressure, i) => expect(Math.abs(pressure - clean[i]!)).toBeLessThan(0.15));
  });

  it('is stale when the last dive ended more than 48 h ago', () => {
    const ended = NOW - 49 * HOUR_MS;
    const loadings = tissuesAfterDives({ dives: [diveEnding(ended)], nowMs: NOW, gfHigh: 85, conditions: SALT });
    expect(loadings).toMatchObject({ worstCompartmentPercent: null, reason: 'stale' });
    expect(loadings.basis.lastDiveEndedAt).toBe(new Date(ended).toISOString());
  });

  it('skips dives without a profile and counts what it used', () => {
    const manual: LoadingDive = { startMs: NOW - 30 * 60_000, endMs: NOW - 10 * 60_000, samples: [], gas: AIR };
    const loadings = tissuesAfterDives({ dives: [diveEnding(NOW - HOUR_MS), manual], nowMs: NOW, gfHigh: 85, conditions: SALT });
    expect(loadings.worstCompartmentPercent).not.toBeNull();
    expect(loadings.basis).toMatchObject({ divesUsed: 1, divesSkippedNoProfile: 1 });

    const noProfiles = tissuesAfterDives({ dives: [manual], nowMs: NOW, gfHigh: 85, conditions: SALT });
    expect(noProfiles).toMatchObject({ worstCompartmentPercent: null, reason: 'no_profile' });
  });

  it('replays only dives that ended within the 72 h window', () => {
    const manual: LoadingDive = { startMs: NOW - 30 * 60_000, endMs: NOW - 10 * 60_000, samples: [], gas: AIR };
    const outside = diveEnding(NOW - (N2_REPLAY_WINDOW_HOURS + 1) * HOUR_MS);
    const loadings = tissuesAfterDives({ dives: [outside, manual], nowMs: NOW, gfHigh: 85, conditions: SALT });
    expect(loadings).toMatchObject({ worstCompartmentPercent: null, reason: 'no_profile' });
    expect(loadings.basis).toMatchObject({ divesUsed: 0, divesSkippedNoProfile: 1, windowHours: N2_REPLAY_WINDOW_HOURS });
  });

  it('says there is nothing to replay when no dive is logged', () => {
    const loadings = tissuesAfterDives({ dives: [], nowMs: NOW, gfHigh: 85, conditions: SALT });
    expect(loadings).toMatchObject({ worstCompartmentPercent: null, reason: 'no_recent_dive' });
    expect(loadings.basis.lastDiveEndedAt).toBeNull();
  });
});

describe('cnsDecayed', () => {
  it('halves every 90 minutes on the surface', () => {
    expect(cnsDecayed(80, 0)).toBe(80);
    expect(cnsDecayed(80, 90)).toBeCloseTo(40, 10);
    expect(cnsDecayed(80, 180)).toBeCloseTo(20, 10);
  });
});

describe('otuLast24h', () => {
  const withOtu = (endedHoursAgo: number, otuStart = 0, otuEnd = 10): LoadingDive => ({
    startMs: NOW - endedHoursAgo * HOUR_MS - 60_000,
    endMs: NOW - endedHoursAgo * HOUR_MS,
    samples: [],
    otuStart,
    otuEnd,
  });

  it('includes a dive that ended before the 24 h edge and excludes the edge and older dives', () => {
    expect(OTU_WINDOW_HOURS).toBe(24);
    expect(otuLast24h([withOtu(23), withOtu(24), withOtu(25)], NOW)).toBe(10);
  });

  it('excludes a dive that has not ended yet and sums what is inside the window', () => {
    expect(otuLast24h([withOtu(23.9, 5, 12), withOtu(1, 0, 3), withOtu(-1)], NOW)).toBe(10);
  });

  it('is null when no dive in the window carries OTU', () => {
    expect(otuLast24h([withOtu(25), { startMs: NOW - HOUR_MS, endMs: NOW, samples: [] }], NOW)).toBeNull();
  });
});
