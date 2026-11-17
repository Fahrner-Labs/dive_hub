// The ZHL-16C compartment core extracted from ndl.ts (ADR 0071): clean tissues, Schreiner stepping and the
// gradient-factor limit, tested so the extraction leaves ndlMinutes' behaviour unchanged.
import { describe, expect, it } from 'vitest';
import { surfacePressureBar } from '../src/planning/gas.js';
import { ndlMinutes } from '../src/planning/ndl.js';
import {
  advanceTissues, surfaceSaturatedTissues, WATER_VAPOUR_BAR, worstCompartmentPercent, ZHL16C_COMPARTMENTS,
} from '../src/planning/tissues.js';

const AIR = { o2: 21, he: 0 };
const SALT = { water: 'salt', altitudeM: 0 } as const;
const FRESH = { water: 'fresh', altitudeM: 0 } as const;

describe('clean tissues', () => {
  it('are saturated with air at the surface, as ndlMinutes started before the extraction', () => {
    const clean = surfaceSaturatedTissues(SALT);
    expect(clean).toHaveLength(ZHL16C_COMPARTMENTS.length);
    expect(clean).toEqual(ZHL16C_COMPARTMENTS.map(() => 0.79 * (surfacePressureBar(0) - WATER_VAPOUR_BAR)));
  });

  it('stay put at the surface breathing air', () => {
    const clean = surfaceSaturatedTissues(SALT);
    const after = advanceTissues(clean, [{ tSeconds: 0, depthM: 0 }, { tSeconds: 90 * 60, depthM: 0 }], AIR, SALT);
    after.forEach((pressure, i) => expect(pressure).toBeCloseTo(clean[i]!, 10));
  });
});

describe('ndlMinutes after the extraction', () => {
  it('still gives the same clean-tissue value (18 m, GF 100, fresh water: 62 min, in GasPlanner\'s 61-minute band)', () => {
    expect(ndlMinutes({ gas: AIR, depthM: 18, gfHigh: 100, conditions: FRESH })).toBe(62);
  });

  it('crosses the gradient-factor limit where the tissue core says 100 %', () => {
    const depthM = 18;
    const gfHigh = 100;
    const ndl = ndlMinutes({ gas: AIR, depthM, gfHigh, conditions: FRESH });
    const dive = (minutes: number) => advanceTissues(surfaceSaturatedTissues(FRESH), [
      { tSeconds: 0, depthM: 0 }, { tSeconds: 60, depthM }, { tSeconds: minutes * 60, depthM },
    ], AIR, FRESH);
    // The NDL carries its one-minute margin (ADR 0046), so the limit is still clear at `ndl` and passed two
    // minutes later: the same crossing the two implementations used to compute separately.
    expect(worstCompartmentPercent(dive(ndl), gfHigh, FRESH)).toBeLessThan(100);
    expect(worstCompartmentPercent(dive(ndl + 2), gfHigh, FRESH)).toBeGreaterThan(100);
  });
});
