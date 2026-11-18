// SAC on a Dive (ADR 0045, slice 3; ADR 0033): computed from the Dive's Cylinders, never stored.
import type { Cylinder } from '../cylinders.js';
import { surfaceLitres } from './real-gas.js';

/** Below this a Dive is too short for its SAC to say anything (ADR 0033). */
const MIN_SECONDS = 15 * 60;

export interface Sac {
  litresPerMinute: number;
  /** Only for a Dive with exactly one Cylinder. */
  barPerMinute: number | null;
}

/** The unrounded kernel values; the route's answer is `sacOfDive`'s two-place view (FAH-268 vectors pin both). */
export interface SacValues extends Sac {
  /** Minutes at the surface's pressure: the Dive's time times the ambient pressure at its average depth. */
  surfaceMinutes: number;
}

/** Why a Dive with Cylinders has no SAC. */
export const SAC_MISSING = ['too_short', 'no_average_depth', 'cylinder_incomplete'] as const;
export type SacMissing = (typeof SAC_MISSING)[number];

const round = (n: number) => Math.round(n * 100) / 100;

/** The SAC's doubles before the route's rounding. */
export function sacValues(
  dive: { durationSeconds: number; avgDepthM: number | null },
  cylinders: Pick<Cylinder, 'volumeL' | 'startPressureBar' | 'endPressureBar' | 'gas'>[],
): { sac: SacValues | null; missing: SacMissing | null } {
  if (cylinders.length === 0) return { sac: null, missing: null };
  if (dive.durationSeconds < MIN_SECONDS) return { sac: null, missing: 'too_short' };
  if (dive.avgDepthM === null) return { sac: null, missing: 'no_average_depth' };
  if (cylinders.some((c) => c.volumeL === null || c.startPressureBar === null || c.endPressureBar === null)) {
    return { sac: null, missing: 'cylinder_incomplete' };
  }
  // Minutes at the surface's pressure: the time times the ambient pressure at the average depth (ADR 0033).
  const surfaceMinutes = (dive.durationSeconds / 60) * (dive.avgDepthM / 10 + 1);
  const litres = cylinders.reduce((sum, c) =>
    sum + surfaceLitres(c.volumeL!, c.startPressureBar!, c.gas) - surfaceLitres(c.volumeL!, c.endPressureBar!, c.gas), 0);
  const [only] = cylinders;
  return {
    sac: {
      litresPerMinute: litres / surfaceMinutes,
      barPerMinute: cylinders.length === 1 ? (only!.startPressureBar! - only!.endPressureBar!) / surfaceMinutes : null,
      surfaceMinutes,
    },
    missing: null,
  };
}

export function sacOfDive(
  dive: { durationSeconds: number; avgDepthM: number | null },
  cylinders: Pick<Cylinder, 'volumeL' | 'startPressureBar' | 'endPressureBar' | 'gas'>[],
): { sac: Sac | null; missing: SacMissing | null } {
  const values = sacValues(dive, cylinders);
  if (values.sac === null) return values;
  return {
    sac: {
      litresPerMinute: round(values.sac.litresPerMinute),
      barPerMinute: values.sac.barPerMinute === null ? null : round(values.sac.barPerMinute),
    },
    missing: null,
  };
}
