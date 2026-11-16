// What the loadings route and the MCP tool answer for a Diver the User manages (ADR 0071): the N₂ replay over the
// Dives that ended within 72 h, the latest Recording's CNS decayed with the 90-minute half-life, the OTU of the last
// 24 h, and DAN's no-fly time. Computed on request, nothing stored: an estimate from the Diver's own logbook, not advice.
import { and, desc, eq, isNull, sql } from 'drizzle-orm';
import type { Queryable } from '../db/client.js';
import { dive, diveAssessment, recording, type RecordingSummary } from '../db/schema.js';
import { SOURCES } from '../assessment/rules.js';
import { noFlyAfter, seriesDiveOf, type NoFly } from '../assessment/series-rules.js';
import { DEFAULT_GF_HIGH } from './bottom-time-service.js';
import { diverDiveRows, endMsOf, loadingDivesForReplay, loadingDivesOf, type DiveRow } from './loading-dives.js';
import {
  cnsDecayed, CNS_HALF_LIFE_MINUTES, otuLast24h, tissuesAfterDives, type LoadingBasis,
} from './loadings.js';
import { PlanningError } from './planning-error.js';

const HOUR_MS = 3_600_000;
const DAY_MS = 24 * HOUR_MS;
/** The latest Recordings looked through for the Diver's GF high and CNS, as the planner looks for its GF. */
const RECENT_RECORDINGS = 100;

const round = (n: number, places: number) => Math.round(n * 10 ** places) / 10 ** places;

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
  const series = around.filter((d) => d.applies !== false).map((d) => seriesDiveOf(d, d.enteredDeco ?? false));
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

  const rows = await diverDiveRows(reader, diverId, nowMs);
  const loadingDives = await loadingDivesOf(reader, rows.windowDives);

  const { gfHigh, cnsRecording } = await latestSettings(reader, diverId);

  const n2 = tissuesAfterDives({ dives: loadingDivesForReplay(rows, loadingDives), nowMs, gfHigh });
  const otu = otuLast24h(loadingDives, nowMs);

  const noFly = rows.latest ? await noFlyOf(reader, diverId, rows.latest) : null;

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
