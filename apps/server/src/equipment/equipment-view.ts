// The Equipment views over HTTP (ADR 0034): an item with its schedules and their computed due; the detail adds usage,
// records and the last Cylinder that named a cylinder item. The due list is one row per schedule that needs attention.
import { Type } from 'typebox';
import { CYLINDER_MATERIALS } from '../db/schema.js';
import { EQUIPMENT_CATEGORIES, EQUIPMENT_STATUSES, EQUIPMENT_USES } from '../vocabulary.js';
import { DateTime, Nullable, refTo } from '../http/schemas.js';
import { DUE_STATES } from './due.js';

const DueState = Type.Enum([...DUE_STATES], { description: 'ok, due_soon, due or overdue; the worst dimension wins' });

export const ScheduleDueView = Type.Object({
  anchor: Type.String({ description: 'The newest service record that resets it, else the schedule start, YYYY-MM-DD' }),
  state: DueState,
  reason: Nullable(Type.String({ description: 'The worst dimension, when it is not ok: "due 2027-03", "100 dives reached"' })),
  months: Nullable(Type.Object({
    dueDate: Type.String({ description: 'YYYY-MM-DD' }), remainingDays: Type.Integer(), state: DueState,
  }, { additionalProperties: false })),
  dives: Nullable(Type.Object({ used: Type.Integer(), remaining: Type.Integer(), state: DueState }, { additionalProperties: false })),
  hours: Nullable(Type.Object({ used: Type.Number(), remaining: Type.Number(), state: DueState }, { additionalProperties: false })),
}, { additionalProperties: false, description: 'Computed on every read, never stored (ADR 0034)' });

export const ServiceScheduleViewSchema = Type.Object({
  id: Type.String(), itemId: Type.String(), name: Type.String(),
  intervalMonths: Nullable(Type.Integer({ minimum: 1 })),
  intervalDives: Nullable(Type.Integer({ minimum: 1 })),
  intervalHours: Nullable(Type.Number({ exclusiveMinimum: 0 })),
  startDate: Type.String({ description: 'Last done before Dive Hub, YYYY-MM-DD' }),
  active: Type.Boolean(), version: Type.Integer(),
  due: Nullable(ScheduleDueView, { description: 'Null for an inactive schedule' }),
}, { $id: 'ServiceSchedule', additionalProperties: false });

export const ServiceRecordViewSchema = Type.Object({
  id: Type.String(), itemId: Type.String(), date: Type.String({ description: 'YYYY-MM-DD' }),
  byWhom: Nullable(Type.String()), notes: Nullable(Type.String()),
  costAmount: Nullable(Type.Number()), costCurrency: Nullable(Type.String({ description: 'Three letters, e.g. EUR' })),
  version: Type.Integer(), scheduleIds: Type.Array(Type.String({ description: 'The schedules this record resets' })),
}, { $id: 'ServiceRecord', additionalProperties: false });

export const EquipmentUsageView = Type.Object({
  diveCount: Type.Integer(), hours: Type.Number(), lastUsedOn: Nullable(Type.String({ description: 'YYYY-MM-DD' })),
}, { additionalProperties: false });

/** The values a Cylinder had when it last named the item: what the cylinder form fills in (ADR 0034 D6). */
export const LastCylinderView = Type.Object({
  volumeL: Nullable(Type.Number()), workingPressureBar: Nullable(Type.Number()),
  material: Nullable(Type.Enum([...CYLINDER_MATERIALS])),
  gas: Nullable(Type.Object({ o2: Type.Number(), he: Type.Number() })),
}, { additionalProperties: false });

export const EquipmentItemViewSchema = Type.Object({
  id: Type.String(), diverId: Type.String(),
  category: Type.Enum([...EQUIPMENT_CATEGORIES], { description: 'What kind of gear it is' }),
  name: Type.String(), maker: Nullable(Type.String()), model: Nullable(Type.String()), serial: Nullable(Type.String()),
  purchaseDate: Nullable(Type.String({ description: 'YYYY-MM-DD' })), notes: Nullable(Type.String()),
  equipmentStatus: Type.Enum([...EQUIPMENT_STATUSES], { description: 'in_use, retired, lost or sold; a used item is retired, never deleted' }),
  onEveryDive: Type.Boolean({ description: 'Counts every Dive of its Diver while in use, minus the ones it was taken off' }),
  inUseFrom: Type.String({ description: 'YYYY-MM-DD; earlier logbook Dives count from this date' }),
  inUseUntil: Nullable(Type.String({ description: 'YYYY-MM-DD; set when the status leaves in use' })),
  version: Type.Integer(), createdAt: DateTime, updatedAt: DateTime,
  deviceId: Nullable(Type.String({ description: 'The Device this item is, when it is one' })),
  schedules: Type.Array(refTo(ServiceScheduleViewSchema)),
}, { $id: 'EquipmentItem', additionalProperties: false });

export const EquipmentDetailViewSchema = Type.Object({
  ...EquipmentItemViewSchema.properties,
  usage: EquipmentUsageView,
  records: Type.Array(refTo(ServiceRecordViewSchema)),
  lastCylinder: Nullable(LastCylinderView, { description: 'For a cylinder item: the values its last Cylinder on a Dive had' }),
}, { $id: 'EquipmentItemDetail', additionalProperties: false });

/** One schedule that needs attention, across the User's Divers, worst first. */
export const EquipmentDueViewSchema = Type.Object({
  itemId: Type.String(), itemName: Type.String(), category: Type.Enum([...EQUIPMENT_CATEGORIES]),
  diverId: Type.String(), diverName: Type.String(),
  scheduleId: Type.String(), scheduleName: Type.String(),
  due: ScheduleDueView,
}, { $id: 'EquipmentDue', additionalProperties: false });

export const EquipmentUseInput = Type.Enum([...EQUIPMENT_USES], { description: 'put_on or taken_off' });

/** A brief item as a Dive shows its gear. */
export const DiveGearItemViewSchema = Type.Object({
  id: Type.String(), name: Type.String(), category: Type.Enum([...EQUIPMENT_CATEGORIES]),
  equipmentStatus: Type.Enum([...EQUIPMENT_STATUSES]),
  onEveryDive: Type.Boolean(),
}, { $id: 'DiveGearItem', additionalProperties: false });

export const DiveGearViewSchema = Type.Object({
  derived: Type.Array(Type.Object({
    ...DiveGearItemViewSchema.properties,
    sources: Type.Array(Type.Enum(['every_dive', 'device', 'cylinder']), { description: 'Why the Dive has this item without an explicit use' }),
  }, { additionalProperties: false })),
  putOn: Type.Array(refTo(DiveGearItemViewSchema)),
  takenOff: Type.Array(refTo(DiveGearItemViewSchema)),
}, { $id: 'DiveGear', additionalProperties: false });
