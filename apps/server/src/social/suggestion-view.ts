// A Buddy suggestion over HTTP (ADR 0070): the Review inbox's list, and the shared facts a preview shows. Nothing of the
// Recording, notes, suit, weighting, cylinders, lead or overrides is here: a later slice may copy only these facts.
import { Type } from 'typebox';
import { PARTICIPANT_ROLES, SUGGESTION_STATUSES } from '../db/schema.js';
import { DateTime, Nullable } from '../http/schemas.js';

const DiverRef = Type.Object({ id: Type.String(), name: Type.String() });
const SiteRef = Nullable(Type.Object({ id: Type.String(), name: Type.String() }));
const Status = Type.Enum([...SUGGESTION_STATUSES], { description: 'pending until the invited Diver accepts or declines; cancelled when the Participant or the Dive goes' });

export const SuggestionView = Type.Object({
  id: Type.String(),
  status: Status,
  createdAt: DateTime,
  resolvedAt: Nullable(DateTime, { description: 'When it was accepted, declined or cancelled; null while pending' }),
  diver: DiverRef,
  dive: Type.Object({
    id: Type.String(),
    startsAt: DateTime,
    utcOffsetSeconds: Nullable(Type.Integer({ minimum: -12 * 3600, maximum: 14 * 3600 })),
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
    utcOffsetSeconds: Nullable(Type.Integer({ minimum: -12 * 3600, maximum: 14 * 3600 })),
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
}, { description: 'The shared facts of a suggested Dive: site, time, duration, depths, Participants and centre, and nothing of the Recording or its owner\'s private values' });
