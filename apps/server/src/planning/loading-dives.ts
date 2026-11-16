// The Diver's logged Dives as the pure loadings functions see them (ADR 0071), shared by the loadings route and the
// planner's residual seeding (FAH-241): the replay window, each Dive's Recording profile, its first gas and OTU.
import { and, desc, eq, inArray, isNull, sql } from 'drizzle-orm';
import type { Queryable } from '../db/client.js';
import { cylinder, dive, recording, sampleSeries, type RecordingSummary } from '../db/schema.js';
import type { GasMix } from './gas.js';
import { N2_REPLAY_WINDOW_HOURS, type LoadingDive } from './loadings.js';

const HOUR_MS = 3_600_000;

type RecordingRow = { id: string; diveId: string | null; startsAt: Date; summary: RecordingSummary };
export type DiveRow = { id: string; startsAt: Date; durationSeconds: number; primaryRecordingId: string | null };

export const endMsOf = (d: { startsAt: Date; durationSeconds: number }) => d.startsAt.getTime() + d.durationSeconds * 1000;

const depthSamples = (row: { offsetsMs: number[]; values: number[] } | undefined) => (row
  ? row.offsetsMs.flatMap((ms, i) => (row.values[i] === undefined ? [] : [{ tSeconds: ms / 1000, depthM: row.values[i]! }]))
  : []);

/** The Diver's Recording for a Dive: the Primary one, else the latest the Dive has. */
function recordingOf(d: DiveRow, byDive: Map<string, RecordingRow[]>): RecordingRow | undefined {
  const list = byDive.get(d.id) ?? [];
  return list.find((r) => r.id === d.primaryRecordingId) ?? list[0];
}

/** The Dives of the window as the pure loadings functions see them, with their Recording's profile, gas and OTU. */
export async function loadingDivesOf(reader: Queryable, windowDives: readonly DiveRow[]): Promise<LoadingDive[]> {
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
    diveId: cylinder.diveId, position: cylinder.position, o2: cylinder.o2, he: cylinder.he,
  }).from(cylinder).where(inArray(cylinder.diveId, ids));
  // The Dive's first gas is the Cylinder at position 0 (ADR 0071); an unknown one leaves the core's air default.
  const firstGas = new Map<string, GasMix>();
  for (const c of cylinders) {
    if (c.position === 0 && c.o2 !== null && c.he !== null) firstGas.set(c.diveId, { o2: c.o2, he: c.he });
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

export interface DiverDiveRows {
  /** The Dives that ended within the 72 h replay window, oldest first. */
  windowDives: DiveRow[];
  /** The latest Dive also when it is older than the window: the basis names it and the no-fly time uses it. */
  latest: DiveRow | undefined;
}

/** The Diver's Dives that the N₂ replay looks at, and the latest of all for the provenance. */
export async function diverDiveRows(reader: Queryable, diverId: string, nowMs: number): Promise<DiverDiveRows> {
  const endedAt = sql`${dive.startsAt} + (${dive.durationSeconds} * interval '1 second')`;
  const ownDive = and(eq(dive.diverId, diverId), isNull(dive.deletedAt), sql`${endedAt} <= ${new Date(nowMs)}`);
  const columns = { id: dive.id, startsAt: dive.startsAt, durationSeconds: dive.durationSeconds, primaryRecordingId: dive.primaryRecordingId };

  const windowDives: DiveRow[] = await reader.select(columns).from(dive)
    .where(and(ownDive, sql`${endedAt} >= ${new Date(nowMs - N2_REPLAY_WINDOW_HOURS * HOUR_MS)}`))
    .orderBy(dive.startsAt);
  const [latest]: DiveRow[] = await reader.select(columns).from(dive)
    .where(ownDive).orderBy(desc(endedAt)).limit(1);
  return { windowDives, latest };
}

/** The Dives the N₂ replay sees: the window's, plus the latest without a profile when it is older, so the basis names it. */
export function loadingDivesForReplay(rows: DiverDiveRows, window: readonly LoadingDive[]): LoadingDive[] {
  const replay = [...window];
  const { latest, windowDives } = rows;
  if (latest && !windowDives.some((d) => d.id === latest.id)) {
    replay.push({ startMs: latest.startsAt.getTime(), endMs: endMsOf(latest), samples: [] });
  }
  return replay;
}
