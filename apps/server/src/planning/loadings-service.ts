// What the loadings route and the MCP tool answer for a Diver the User manages (ADR 0071): the N₂ replay over the
// Dives that ended within 72 h, the latest Recording's CNS decayed with the 90-minute half-life, the OTU of the last
// 24 h, and DAN's no-fly time. Computed on request, nothing stored: an estimate from the Diver's own logbook, not advice.
import { and, desc, eq, inArray, isNull, sql } from 'drizzle-orm';
import type { Queryable } from '../db/client.js';
import { cylinder, dive, diveAssessment, recording, sampleSeries, type RecordingSummary } from '../db/schema.js';
import { SOURCES } from '../assessment/rules.js';
import { noFlyAfter, type NoFly, type SeriesDive } from '../assessment/series-rules.js';
import { DEFAULT_GF_HIGH } from './bottom-time-service.js';
import type { GasMix } from './gas.js';
import {
  cnsDecayed, CNS_HALF_LIFE_MINUTES, N2_REPLAY_WINDOW_HOURS, otuLast24h, tissuesAfterDives, type LoadingBasis, type LoadingDive,
} from './loadings.js';
import { PlanningError } from './planning-error.js';

const HOUR_MS = 3_600_000;
const DAY_MS = 24 * HOUR_MS;
/** The latest Recordings looked through for the Diver's GF high and CNS, as the planner looks for its GF. */
const RECENT_RECORDINGS = 100;

const round = (n: number, places: number) => Math.round(n * 10 ** places) / 10 ** places;

type RecordingRow = { id: string; diveId: string | null; startsAt: Date; summary: RecordingSummary };
type DiveRow = { id: string; startsAt: Date; durationSeconds: number; primaryRecordingId: string | null };

const endMsOf = (d: { startsAt: Date; durationSeconds: number }) => d.startsAt.getTime() + d.durationSeconds * 1000;

/** The local calendar day a Dive started on, as the series rules count days (ADR 0036). */
const localDay = (startsAt: Date, offsetSeconds: number | null) =>
  new Date(startsAt.getTime() + (offsetSeconds ?? 0) * 1000).toISOString().slice(0, 10);

const depthSamples = (row: { offsetsMs: number[]; values: number[] } | undefined) => (row
  ? row.offsetsMs.flatMap((ms, i) => (row.values[i] === undefined ? [] : [{ tSeconds: ms / 1000, depthM: row.values[i]! }]))
  : []);

/** The Diver's Recording for a Dive: the Primary one, else the latest the Dive has. */
function recordingOf(d: DiveRow, byDive: Map<string, RecordingRow[]>): RecordingRow | undefined {
  const list = byDive.get(d.id) ?? [];
  return list.find((r) => r.id === d.primaryRecordingId) ?? list[0];
}

/** The Dives of the window as the pure loadings functions see them, with their Recording's profile, gas and OTU. */
async function loadingDivesOf(reader: Queryable, windowDives: readonly DiveRow[]): Promise<LoadingDive[]> {
  const ids = windowDives.map((d) => d.id);
  if (ids.length === 0) return [];

  const recordings: RecordingRow[] = await reader.select({
    id: recording.id, diveId: recording.diveId, startsAt: recording.startsAt, summary: recording.summary,
  }).from(recording).where(and(inArray(recording.diveId, ids), isNull(recording.deletedAt))).orderBy(desc(recording.startsAt));
  const recordingsByDive = new Map<string, RecordingRow[]>();
  for (const r of recordings) {
    if (r.diveId) recordingsByDive.set(r.diveId, [...(recordingsByDive.get(r.diveId) ?? []), r]);
  }
  const recordingIds = [...new Set(windowDives.flatMap((d) => {
    const r = recordingOf(d, recordingsByDive);
    return r ? [r.id] : [];
  }))];
  const depthRows = recordingIds.length === 0 ? [] : await reader.select({
    recordingId: sampleSeries.recordingId, offsetsMs: sampleSeries.offsetsMs, values: sampleSeries.values,
  }).from(sampleSeries).where(and(inArray(sampleSeries.recordingId, recordingIds), eq(sampleSeries.channel, 'depth')));
  const depthByRecording = new Map(depthRows.map((r) => [r.recordingId, r]));

  const cylinders = await reader.select({
    diveId: cylinder.diveId, o2: cylinder.o2, he: cylinder.he,
  }).from(cylinder).where(inArray(cylinder.diveId, ids)).orderBy(cylinder.position);
  const firstGas = new Map<string, GasMix>();
  for (const c of cylinders) {
    if (!firstGas.has(c.diveId) && c.o2 !== null && c.he !== null) firstGas.set(c.diveId, { o2: c.o2, he: c.he });
  }

  return windowDives.map((d) => {
    const found = recordingOf(d, recordingsByDive);
    const summary = found?.summary;
    return {
      startMs: d.startsAt.getTime(), endMs: endMsOf(d),
      samples: found ? depthSamples(depthByRecording.get(found.id)) : [],
      gas: firstGas.get(d.id),
      otuStart: typeof summary?.otuStart === 'number' ? summary.otuStart : undefined,
      otuEnd: typeof summary?.otuEnd === 'number' ? summary.otuEnd : undefined,
    };
  });
}

/** The Diver's own settings: the latest Recording that carries a GF high, and the latest one that carries CNS. */
async function latestSettings(reader: Queryable, diverId: string) {
  const recent: { summary: RecordingSummary; startsAt: Date; durationSeconds: number }[] = await reader.select({
    summary: recording.summary, startsAt: dive.startsAt, durationSeconds: dive.durationSeconds,
  }).from(recording).innerJoin(dive, eq(dive.id, recording.diveId))
    .where(and(eq(dive.diverId, diverId), isNull(dive.deletedAt), isNull(recording.deletedAt)))
    .orderBy(desc(recording.startsAt)).limit(RECENT_RECORDINGS);
  return {
    gfHigh: recent.find((r) => typeof r.summary.gfHigh === 'number')?.summary.gfHigh ?? DEFAULT_GF_HIGH,
    cnsRecording: recent.find((r) => typeof r.summary.cnsEnd === 'number'),
  };
}

/** DAN's no-fly time after the latest Dive, from the Dives of its day and the day before (ADR 0036, 0071). */
async function noFlyOf(reader: Queryable, diverId: string, latest: DiveRow): Promise<LoadingsView['noFly']> {
  const around = await reader.select({
    id: dive.id, startsAt: dive.startsAt, utcOffsetSeconds: dive.utcOffsetSeconds, durationSeconds: dive.durationSeconds,
    maxDepthM: dive.maxDepthM, enteredDeco: diveAssessment.enteredDeco, applies: diveAssessment.applies,
  }).from(dive).leftJoin(diveAssessment, eq(diveAssessment.diveId, dive.id))
    .where(and(eq(dive.diverId, diverId), isNull(dive.deletedAt),
      sql`${dive.startsAt} between ${new Date(latest.startsAt.getTime() - 3 * DAY_MS)} and ${new Date(latest.startsAt.getTime() + 2 * DAY_MS)}`));
  const series: SeriesDive[] = around.filter((d) => d.applies !== false).map((d) => ({
    id: d.id, startMs: d.startsAt.getTime(), endMs: endMsOf(d), day: localDay(d.startsAt, d.utcOffsetSeconds),
    maxDepthM: d.maxDepthM, enteredDeco: d.enteredDeco ?? false,
  }));
  const found = noFlyAfter(series, latest.id);
  return found && { ...found, source: SOURCES.dan_flying };
}

export interface LoadingsView {
  computedAt: string;
  basis: LoadingBasis;
  n2: { worstCompartmentPercent: number; gfHigh: number } | null;
  cns: { percentNow: number; percentAtLastDiveEnd: number; halfLifeMinutes: number } | null;
  otu: { last24Hours: number } | null;
  noFly: (NoFly & { source: { title: string; url: string } }) | null;
}

/**
 * What the Diver's logged dives leave in them now, with the basis it rests on. A Diver the User doesn't manage is
 * "not found", as for bottom time: only their own logbook is read.
 */
export async function diverLoadings(
  reader: Queryable, managedIds: readonly string[], diverId: string, nowMs: number = Date.now(),
): Promise<LoadingsView> {
  if (!managedIds.includes(diverId)) throw new PlanningError('diver_not_found');

  const endedAt = sql`${dive.startsAt} + (${dive.durationSeconds} * interval '1 second')`;
  const ownDive = and(eq(dive.diverId, diverId), isNull(dive.deletedAt), sql`${endedAt} <= ${new Date(nowMs)}`);
  const columns = { id: dive.id, startsAt: dive.startsAt, durationSeconds: dive.durationSeconds, primaryRecordingId: dive.primaryRecordingId };

  const windowDives: DiveRow[] = await reader.select(columns).from(dive)
    .where(and(ownDive, sql`${endedAt} >= ${new Date(nowMs - N2_REPLAY_WINDOW_HOURS * HOUR_MS)}`))
    .orderBy(dive.startsAt);
  // The latest Dive also when it is older than the window: the basis names it, and it decides stale and no-fly.
  const [latest]: DiveRow[] = await reader.select(columns).from(dive)
    .where(ownDive).orderBy(desc(endedAt)).limit(1);

  const loadingDives = await loadingDivesOf(reader, windowDives);

  const { gfHigh, cnsRecording } = await latestSettings(reader, diverId);

  const forReplay = [...loadingDives];
  if (latest && !windowDives.some((d) => d.id === latest.id)) {
    forReplay.push({ startMs: latest.startsAt.getTime(), endMs: endMsOf(latest), samples: [] });
  }
  const n2 = tissuesAfterDives({ dives: forReplay, nowMs, gfHigh });
  const otu = otuLast24h(loadingDives, nowMs);

  const noFly = latest ? await noFlyOf(reader, diverId, latest) : null;

  const cnsEnd = cnsRecording?.summary.cnsEnd;
  const cns = cnsEnd === undefined ? null : {
    percentNow: round(cnsDecayed(cnsEnd, (nowMs - endMsOf(cnsRecording!)) / 60_000), 1),
    percentAtLastDiveEnd: round(cnsEnd, 1),
    halfLifeMinutes: CNS_HALF_LIFE_MINUTES,
  };

  return {
    computedAt: new Date(nowMs).toISOString(),
    basis: n2.basis,
    n2: n2.worstCompartmentPercent === null ? null : { worstCompartmentPercent: round(n2.worstCompartmentPercent, 1), gfHigh: n2.gfHigh },
    cns,
    otu: otu === null ? null : { last24Hours: round(otu, 1) },
    noFly,
  };
}
