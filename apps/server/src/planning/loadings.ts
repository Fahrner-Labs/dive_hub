// Current residual loadings from the Diver's logged dives (ADR 0071): pure functions, nothing stored and no DB here.
// N₂ replays the recorded profiles through the shared ZHL-16C core; CNS decays with a fixed sourced half-life; OTU
// sums a hard 24 h window. An estimate from the Diver's own logbook, never advice (ADR 0032/0036).
import { type Conditions, type GasMix } from './gas.js';
import { advanceTissues, surfaceSaturatedTissues, worstCompartmentPercent, type TissuePoint } from './tissues.js';

/** The slowest compartment (635 min) gets more than six half-times in this window. */
export const N2_REPLAY_WINDOW_HOURS = 72;
/** After this, the tissues are back at surface saturation within noise; the planner uses the same window. */
export const N2_STALE_AFTER_HOURS = 48;
/** Shearwater, "The CNS oxygen clock": a 90-minute elimination half-life on the surface. */
export const CNS_HALF_LIFE_MINUTES = 90;
/** Garmin documents that a dive's OTU expire after 24 hours; NOAA's REPEX is the accumulation model. */
export const OTU_WINDOW_HOURS = 24;

const HOUR_MS = 3_600_000;
const AIR: GasMix = { o2: 21, he: 0 };
/**
 * This slice replays every Dive as salt water at sea level, the planner's default. Per-Dive water (on the Recording)
 * and altitude (on the site) are a later refinement; ADR 0071 records the approximation.
 */
const DEFAULT_CONDITIONS: Conditions = { water: 'salt', altitudeM: 0 };

/** A logged dive as the loadings functions see it; the service fills these from the Dive and its Recording. */
export interface LoadingDive {
  startMs: number;
  endMs: number;
  /** Depth samples relative to the dive's start; empty when there is no profile. */
  samples: readonly TissuePoint[];
  /** The first gas; air when the logbook has none. Switch information is not used yet. */
  gas?: GasMix | undefined;
  otuStart?: number | undefined;
  otuEnd?: number | undefined;
}

/** What the N₂ estimate rests on: always returned, so a display can name its basis. */
export interface LoadingBasis {
  windowHours: number;
  divesUsed: number;
  divesSkippedNoProfile: number;
  lastDiveEndedAt: string | null;
}

export type N2UnavailableReason = 'stale' | 'no_profile' | 'no_recent_dive';

interface N2LoadingsBase {
  gfHigh: number;
  basis: LoadingBasis;
  /** The replayed compartments; null when there is no estimate. */
  tissues: readonly number[] | null;
}

export type N2Loadings = N2LoadingsBase & (
  | { worstCompartmentPercent: number }
  | { worstCompartmentPercent: null; reason: N2UnavailableReason }
);

export interface TissuesAfterDivesInput {
  dives: readonly LoadingDive[];
  nowMs: number;
  gfHigh: number;
  conditions?: Conditions | undefined;
}

function offGasAtSurface(tissues: readonly number[], minutes: number, conditions: Conditions): number[] {
  return advanceTissues(tissues, [{ tSeconds: 0, depthM: 0 }, { tSeconds: minutes * 60, depthM: 0 }], AIR, conditions);
}

/** Replay one dive from the current state; the last sample is brought to the surface at the dive's end. */
function replayDive(tissues: readonly number[], dive: LoadingDive, conditions: Conditions): number[] {
  const points = [...dive.samples].sort((a, b) => a.tSeconds - b.tSeconds);
  const durationSeconds = (dive.endMs - dive.startMs) / 1000;
  if (points.at(-1)!.tSeconds < durationSeconds) points.push({ tSeconds: durationSeconds, depthM: 0 });
  return advanceTissues(tissues, points, dive.gas ?? AIR, conditions);
}

/**
 * Replay the dives that ended within 72 h, off-gas on air at the surface up to `nowMs`, and express the worst
 * compartment as a percent of its tolerated tissue pressure at `gfHigh`. The result is `null` with a reason when
 * the last dive is stale, no usable dive is in the window, or no dive is logged at all.
 */
export function tissuesAfterDives({
  dives, nowMs, gfHigh, conditions = DEFAULT_CONDITIONS,
}: TissuesAfterDivesInput): N2Loadings {
  const past = dives.filter((dive) => dive.endMs <= nowMs);
  const lastEndMs = past.reduce<number | null>(
    (latest, dive) => (latest === null || dive.endMs > latest ? dive.endMs : latest), null,
  );
  const inWindow = past.filter((dive) => nowMs - dive.endMs <= N2_REPLAY_WINDOW_HOURS * HOUR_MS);
  const used = inWindow.filter((dive) => dive.samples.length > 0).sort((a, b) => a.startMs - b.startMs);
  const basis: LoadingBasis = {
    windowHours: N2_REPLAY_WINDOW_HOURS,
    divesUsed: used.length,
    divesSkippedNoProfile: inWindow.length - used.length,
    lastDiveEndedAt: lastEndMs === null ? null : new Date(lastEndMs).toISOString(),
  };
  const unavailable = (reason: N2UnavailableReason): N2Loadings => ({
    worstCompartmentPercent: null, reason, gfHigh, basis, tissues: null,
  });

  if (lastEndMs === null) return unavailable('no_recent_dive');
  if (nowMs - lastEndMs > N2_STALE_AFTER_HOURS * HOUR_MS) return unavailable('stale');
  if (used.length === 0) return unavailable('no_profile');

  let tissues = surfaceSaturatedTissues(conditions);
  let cursorMs = used[0]!.startMs;
  for (const dive of used) {
    const gapMinutes = Math.max(0, dive.startMs - cursorMs) / 60_000;
    if (gapMinutes > 0) tissues = offGasAtSurface(tissues, gapMinutes, conditions);
    tissues = replayDive(tissues, dive, conditions);
    cursorMs = Math.max(cursorMs, dive.endMs);
  }
  tissues = offGasAtSurface(tissues, Math.max(0, nowMs - cursorMs) / 60_000, conditions);

  return {
    worstCompartmentPercent: worstCompartmentPercent(tissues, gfHigh, conditions),
    gfHigh,
    basis,
    tissues,
  };
}

/**
 * CNS percent left after `elapsedMinutes` on the surface. Shearwater, "The CNS oxygen clock": CNS never decreases
 * during a dive; at the surface the elimination half-life is 90 minutes (the source already cited as
 * `SOURCES.noaa_oxygen` in apps/server/src/assessment/rules.ts). Fixed, not a setting (ADR 0071).
 */
export function cnsDecayed(cnsEnd: number, elapsedMinutes: number): number {
  if (elapsedMinutes <= 0) return cnsEnd;
  return cnsEnd * 0.5 ** (elapsedMinutes / CNS_HALF_LIFE_MINUTES);
}

/** The OTU of the dives that ended within the last 24 h; null when none in the window carries OTU. */
export function otuLast24h(dives: readonly LoadingDive[], nowMs: number): number | null {
  let total = 0;
  let found = false;
  for (const dive of dives) {
    const elapsedMs = nowMs - dive.endMs;
    if (elapsedMs < 0 || elapsedMs >= OTU_WINDOW_HOURS * HOUR_MS || dive.otuEnd === undefined) continue;
    total += dive.otuEnd - (dive.otuStart ?? 0);
    found = true;
  }
  return found ? total : null;
}
