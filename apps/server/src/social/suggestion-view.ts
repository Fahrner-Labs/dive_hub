// A Buddy suggestion over HTTP (ADR 0070): the Review inbox's list, the shared facts a preview shows and the answers.
// Nothing of the Recording, notes, suit, weighting, cylinders, lead or overrides is here: accepting may copy only these
// facts, or link an existing Dive without copying anything.
import { Type } from 'typebox';
import { PARTICIPANT_ROLES, SUGGESTION_STATUSES } from '../db/schema.js';
import { DateTime, Nullable } from '../http/schemas.js';

const DiverRef = Type.Object({ id: Type.String(), name: Type.String() });
const SiteRef = Nullable(Type.Object({ id: Type.String(), name: Type.String() }));
const Status = Type.Enum([...SUGGESTION_STATUSES], { description: 'pending until the invited Diver accepts or declines; cancelled when the Participant or the Dive goes' });
const Offset = Nullable(Type.Integer({ minimum: -12 * 3600, maximum: 14 * 3600 }));

/** One of the invited Diver's own Dives that meets the suggested Dive's time window (branch B may link it). */
const OverlapView = Type.Object({
  id: Type.String(),
  startsAt: DateTime,
  utcOffsetSeconds: Offset,
  durationSeconds: Type.Number(),
  maxDepthM: Nullable(Type.Number()),
  site: SiteRef,
});

export const SuggestionView = Type.Object({
  id: Type.String(),
  status: Status,
  createdAt: DateTime,
  resolvedAt: Nullable(DateTime, { description: 'When it was accepted, declined or cancelled; null while pending' }),
  diver: DiverRef,
  dive: Type.Object({
    id: Type.String(),
    startsAt: DateTime,
    utcOffsetSeconds: Offset,
    durationSeconds: Type.Number(),
    maxDepthM: Nullable(Type.Number()),
    site: SiteRef,
  }),
}, { description: 'A pending Buddy suggestion for a Diver the signed-in User manages' });

export const SuggestionPreviewView = Type.Object({
  id: Type.String(),
  status: Status,
  createdAt: DateTime,
  resolvedAt: Nullable(DateTime),
  diver: DiverRef,
  dive: Type.Object({
    id: Type.String(),
    startsAt: DateTime,
    utcOffsetSeconds: Offset,
    durationSeconds: Type.Number(),
    maxDepthM: Nullable(Type.Number()),
    avgDepthM: Nullable(Type.Number()),
    site: SiteRef,
    owner: DiverRef,
    centres: Type.Array(Type.Object({ id: Type.String(), name: Type.String() }), {
      description: 'The Dive centres responsible for the site, if any (ADR 0043): the operator a copy may show',
    }),
    participants: Type.Array(Type.Object({
      diverId: Type.String(), name: Type.String(), role: Type.Enum([...PARTICIPANT_ROLES]),
    }), { description: 'The Divers on the Dive besides its own Diver, by name only (ADR 0028)' }),
  }),
  overlaps: Type.Array(OverlapView, {
    description: 'The invited Diver\'s existing Dives that meet the suggested time window, earliest first: pick one to link instead of creating a new Dive',
  }),
}, { description: 'The shared facts of a suggested Dive: site, time, duration, depths, Participants and centre, and nothing of the Recording or its owner\'s private values' });

export const SuggestionAcceptView = Type.Object({
  id: Type.String(),
  status: Type.Literal('accepted'),
  resolvedAt: DateTime,
  diveId: Type.String({ description: 'The Dive in the invited Diver\'s logbook: the linked existing Dive (branch B), or the new one made from the shared facts (branch A)' }),
  jointDiveId: Type.String({ description: 'The Joint dive both this Dive and the suggested one are members of' }),
}, { description: 'A Buddy suggestion accepted, by linking an existing Dive or by copying its shared facts into a new one' });

export const SuggestionDeclineView = Type.Object({
  id: Type.String(),
  status: Type.Literal('declined'),
  resolvedAt: DateTime,
}, { description: 'A Buddy suggestion declined by a User who manages the invited Diver; nothing was created' });
