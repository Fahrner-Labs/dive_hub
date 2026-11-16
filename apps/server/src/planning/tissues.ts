// Bühlmann ZHL-16C tissue simulation, extracted from ndl.ts (ADR 0071) so the no-decompression limit, the residual
// loadings and (later) the planner's residual seeding all step the same compartments.
// Clean-room from the published coefficients and formulas, not from any GPL implementation:
//   - Bühlmann, "Tauchmedizin" (ZHL-16C half-times and a/b values), as tabulated in
//     https://en.wikipedia.org/wiki/B%C3%BChlmann_decompression_algorithm
//   - Haldane (constant depth) and Schreiner (changing depth) equations; water vapour 0.0627 bar
//   - Baker, "Understanding M-values": gradient factors; the tolerated tissue pressure at GF high is
//     Pamb + GF·(M − Pamb), with the M-value M = a + Pamb/b.
import { ambientPressureBar, surfacePressureBar, type Conditions, type GasMix } from './gas.js';

/** [half-time in minutes, a, b] for the 16 compartments (ZHL-16C, compartment 1b: 5.0 min). */
export const ZHL16C_COMPARTMENTS: readonly (readonly [number, number, number])[] = [
  [5.0, 1.1696, 0.5578], [8.0, 1.0, 0.6514], [12.5, 0.8618, 0.7222], [18.5, 0.7562, 0.7825],
  [27.0, 0.62, 0.8126], [38.3, 0.5043, 0.8434], [54.3, 0.441, 0.8693], [77.0, 0.4, 0.891],
  [109.0, 0.375, 0.9092], [146.0, 0.35, 0.9222], [187.0, 0.3295, 0.9319], [239.0, 0.3065, 0.9403],
  [305.0, 0.2835, 0.9477], [390.0, 0.261, 0.9544], [498.0, 0.248, 0.9602], [635.0, 0.2327, 0.9653],
];

export const WATER_VAPOUR_BAR = 0.0627;

/** One point of a dive's depth profile, its offset from the dive's start in seconds. */
export interface TissuePoint {
  tSeconds: number;
  depthM: number;
}

/** Clean tissues: saturated with air at the surface, where every Bühlmann simulation in Dive Hub starts. */
export function surfaceSaturatedTissues(conditions: Conditions): number[] {
  const surface = surfacePressureBar(conditions.altitudeM);
  return ZHL16C_COMPARTMENTS.map(() => 0.79 * (surface - WATER_VAPOUR_BAR));
}

/**
 * One Schreiner step for every compartment: the inspired inert-gas pressure changes linearly at `rateBarPerMin`
 * over `minutes`. With a rate of zero it is Haldane's equation, as ndl.ts steps a constant depth.
 */
export function schreinerStep(
  tissues: readonly number[],
  inspiredBar: number,
  rateBarPerMin: number,
  minutes: number,
): number[] {
  return ZHL16C_COMPARTMENTS.map(([halfTime], i) => {
    const k = Math.LN2 / halfTime;
    return inspiredBar + rateBarPerMin * (minutes - 1 / k)
      - (inspiredBar - tissues[i]! - rateBarPerMin / k) * Math.exp(-k * minutes);
  });
}

/**
 * Advance the 16 compartments over a depth/time series breathing one gas. Depth moves linearly between points;
 * before the first point the dive is at the surface, and a duplicate timestamp changes depth without time.
 */
export function advanceTissues(
  tissues: readonly number[],
  points: readonly TissuePoint[],
  gas: GasMix,
  conditions: Conditions,
): number[] {
  const fN2 = (100 - gas.o2 - gas.he) / 100;
  let state = [...tissues];
  let previousSeconds = 0;
  let previousDepthM = 0;
  for (const point of [...points].sort((a, b) => a.tSeconds - b.tSeconds)) {
    const seconds = Math.max(previousSeconds, point.tSeconds);
    const minutes = (seconds - previousSeconds) / 60;
    if (minutes > 0) {
      const inspired = (ambientPressureBar(previousDepthM, conditions) - WATER_VAPOUR_BAR) * fN2;
      const nextInspired = (ambientPressureBar(point.depthM, conditions) - WATER_VAPOUR_BAR) * fN2;
      state = schreinerStep(state, inspired, (nextInspired - inspired) / minutes, minutes);
    }
    previousSeconds = seconds;
    previousDepthM = point.depthM;
  }
  return state;
}

/**
 * The worst compartment as a percent of its tolerated tissue pressure at the surface and GF high: 100 % is the
 * gradient-factor limit where ndl.ts would call for a stop. Unlike the tolerated *ambient* pressure the NDL compares
 * with the surface, this stays a positive fraction for clean tissues.
 */
export function worstCompartmentPercent(tissues: readonly number[], gfHigh: number, conditions: Conditions): number {
  const surface = surfacePressureBar(conditions.altitudeM);
  const gf = gfHigh / 100;
  let worst = 0;
  ZHL16C_COMPARTMENTS.forEach(([, a, b], i) => {
    const mValue = a + surface / b;
    const tolerated = surface + gf * (mValue - surface);
    worst = Math.max(worst, (tissues[i]! / tolerated) * 100);
  });
  return worst;
}
