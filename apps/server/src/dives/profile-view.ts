// The depth sketch a Review card draws (ADR 0041): the profile stored with a Dive's current assessment, the same shape
// the logbook's rows carry. A logbook check, a Duplicate candidate and a Buddy suggestion's preview each ask for it.
import { Type } from 'typebox';
import { Nullable, refTo } from '../http/schemas.js';

export interface DiveProfile {
  depthsM: number[];
  spanSeconds: number;
  ascentBands: [number, number, 1 | 2 | 3][];
}

export const DiveProfileViewSchema = Type.Object({
  depthsM: Type.Array(Type.Number(), { description: 'Depth in metres, evenly spread over `spanSeconds`; the deepest sample of each stretch' }),
  spanSeconds: Type.Number({ description: 'Seconds from the first to the last depth sample of the Primary recording' }),
  ascentBands: Type.Array(Type.Tuple([Type.Number(), Type.Number(), Type.Integer({ minimum: 1, maximum: 3 })]), {
    description: 'Stretches of the ascent that were fast, [start s, end s, band 1–3 for brisk, quick, very quick], in seconds from the first sample (ADR 0036)',
  }),
}, {
  $id: 'DiveProfileView',
  description: 'The Primary recording’s depth, reduced to draw a sketch of the dive (ADR 0041); null without a Recording, while the Dive waits to be assessed, or when its assessment is of a Recording it no longer has as Primary',
});
export const DiveProfileView = Nullable(refTo(DiveProfileViewSchema));

/**
 * The stored sketch, only while it is of the Dive's current Primary recording (a change is assessed again shortly);
 * null when the Dive has no assessment or its assessment is of a Recording it no longer has as Primary.
 */
export const profileOf = (
  d: { primaryRecordingId: string | null },
  a: { recordingId: string | null; profile: { depthsM: number[]; spanSeconds: number } | null; ascentBands: [number, number, 1 | 2 | 3][] } | null | undefined,
): DiveProfile | null =>
  a?.profile && a.recordingId === d.primaryRecordingId ? { ...a.profile, ascentBands: a.ascentBands } : null;
