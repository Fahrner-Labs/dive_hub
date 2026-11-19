// The view of one Dive over HTTP (DiveView), built from the same reads for GET /api/dives/{id} and the logbook
// list's batched `include=details` mode (FAH-297): both answer the same shape, so a client's sync and its dive page
// see one Dive alike.
import type { Static } from 'typebox';
import { dive, type RecordingSummary } from '../db/schema.js';
import type { CentreService } from '../centres/centre-service.js';
import type { DiverService } from '../divers/diver-service.js';
import { recordingPosition } from '../sites/dive-site-link.js';
import { sacOfDive } from './calc/sac.js';
import { waterMismatch } from './calc/water.js';
import type { DiveCertifications } from './certification-port.js';
import { valuesFromRecording, type DiveValues } from './dive-values.js';
import { DiveView, ValuesSchema } from './dive-view.js';
import type { LogbookService } from './logbook-service.js';
import { leadTotalKg, suitOf, weightingFeedbackOf } from './weighting.js';

export interface DiveViewDeps {
  logbook: Pick<LogbookService, 'recordingsAndSite' | 'partsOf' | 'participantsOfDive'>;
  centres: Pick<CentreService, 'codesOfDive'>;
  divers: Pick<DiverService, 'professionalCodesOfDive'>;
  certifications: DiveCertifications;
}

const toValues = (v: DiveValues): Static<typeof ValuesSchema> => ({
  ...v, startsAt: { at: v.startsAt.at.toISOString(), utcOffsetSeconds: v.startsAt.utcOffsetSeconds },
});

/** The view of a Dive for a User who manages its Diver; the same reads for the dive page and the list's details mode. */
export const createDiveView = ({ logbook, centres, divers, certifications }: DiveViewDeps) =>
  async (row: typeof dive.$inferSelect, userId: string): Promise<Static<typeof DiveView>> => {
    const { recordings: recs, site } = await logbook.recordingsAndSite(row);
    const primary = recs.find(({ r }) => r.id === row.primaryRecordingId)?.r;
    const { cylinders, lead } = await logbook.partsOf(row.id);
    const { sac, missing: sacMissing } = sacOfDive(row, cylinders);
    const participants = await logbook.participantsOfDive(row.id);
    const certificationHints = await certifications.hintsFor(userId, participants.map((p) => p.diverId));
    return {
      id: row.id,
      diverId: row.diverId,
      version: row.version,
      values: { number: row.number, ...toValues({
        startsAt: { at: row.startsAt, utcOffsetSeconds: row.utcOffsetSeconds },
        durationSeconds: row.durationSeconds, maxDepthM: row.maxDepthM, avgDepthM: row.avgDepthM,
        waterTemperatureC: row.waterTemperatureC,
      }) },
      overrides: row.overrides,
      utcOffsetSource: row.utcOffsetSource,
      fromProvider: row.fromProvider,
      fromRecording: primary ? toValues(valuesFromRecording(primary)) : null,
      notes: row.notes,
      site: site ? { id: site.id, name: site.name } : null,
      waterType: site?.waterType ?? null,
      waterMismatch: waterMismatch(site?.waterType ?? null, primary?.summary),
      position: primary ? recordingPosition(primary) : null,
      recordings: recs.map(({ r, d, channels }) => ({
        id: r.id, isPrimary: r.id === row.primaryRecordingId,
        device: d ? { manufacturer: d.manufacturer, product: d.product, serialNumber: d.serialNumber } : null, startsAt: r.startsAt.toISOString(),
        durationSeconds: r.durationSeconds, maxDepthM: r.maxDepthM, parser: r.parser, partOf: r.joinedRecordingId,
        summary: r.summary as RecordingSummary, channels,
      })),
      cylinders,
      lead,
      leadTotalKg: leadTotalKg(lead),
      weightingFeedback: weightingFeedbackOf(row),
      suit: suitOf(row),
      sac,
      sacMissing,
      participants: participants.map((p) => {
        const hint = certificationHints.get(p.diverId);
        return hint ? { ...p, certificationHint: hint } : p;
      }),
      verificationCodes: [
        ...(await centres.codesOfDive(row.id)).map((c) => ({ kind: 'centre' as const, diver: null, ...c })),
        ...(await divers.professionalCodesOfDive(row.id)).map((c) => ({ kind: 'professional' as const, centre: null, ...c })),
      ],
    };
  };
