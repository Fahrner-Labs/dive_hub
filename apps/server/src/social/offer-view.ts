// Joint-dive fill offers over HTTP (FAH-411, ADR 0070): the shared facts offered from another member's Dive, and the two
// answers. The individual facts stay out by construction: the server never reads them for an offer and the views below
// name only the shared set.
import { Type } from 'typebox';
import { JOINT_FILL_FIELDS } from '../db/schema.js';
import { DateTime, Nullable } from '../http/schemas.js';

const SiteRef = Nullable(Type.Object({ id: Type.String(), name: Type.String() }));
const Offset = Nullable(Type.Integer({ minimum: -12 * 3600, maximum: 14 * 3600 }));
const Field = Type.Enum([...JOINT_FILL_FIELDS], {
  description: 'A shared fact of the descent: the site, the start with its UTC offset, the duration, the max or the average depth',
});

const Facts = {
  startsAt: DateTime,
  utcOffsetSeconds: Offset,
  durationSeconds: Type.Number(),
  maxDepthM: Nullable(Type.Number()),
  avgDepthM: Nullable(Type.Number()),
  site: SiteRef,
};

export const JointOfferView = Type.Object({
  dive: Type.Object({
    id: Type.String({ description: 'The receiving Dive, one of the signed-in User\'s Dives in a Joint dive' }),
    ...Facts,
  }),
  source: Type.Object({
    diveId: Type.String(),
    diver: Type.Object({ id: Type.String(), name: Type.String() }),
    ...Facts,
  }, { description: 'The member Dive the values come from (the newest filled value when the Joint dive has more than two members)' }),
  fields: Type.Array(Field, { minItems: 1, description: 'The receiving Dive\'s empty fields this source can fill' }),
}, { description: 'The shared facts of two member Dives of one Joint dive, and which of them the signed-in User may fill' });

export const JointOfferFieldsBody = Type.Object({
  diveId: Type.String({ format: 'uuid' }),
  fields: Type.Array(Field, { minItems: 1, maxItems: JOINT_FILL_FIELDS.length, uniqueItems: true }),
}, { additionalProperties: false });

export const JointOfferAcceptView = Type.Object({
  diveId: Type.String(),
  fields: Type.Array(Field),
  version: Type.Integer({ description: 'The Dive\'s version after the fill' }),
  values: Type.Object({
    startsAt: DateTime,
    utcOffsetSeconds: Offset,
    durationSeconds: Type.Number(),
    maxDepthM: Nullable(Type.Number()),
    avgDepthM: Nullable(Type.Number()),
    site: SiteRef,
  }, { description: 'The values now in effect; the fill copied the offering Dive\'s current values, not a preview' }),
}, { description: 'A fill offer accepted; the shared facts were written as the User\'s own values' });

export const JointOfferDeclineView = Type.Object({
  diveId: Type.String(),
  fields: Type.Array(Field),
}, { description: 'The fields declined for this Dive of the Joint dive; they are not offered again' });
