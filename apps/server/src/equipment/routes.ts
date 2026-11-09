// Equipment over HTTP (ADR 0034): items, schedules, records, the due list and a Dive's gear. Every route is scoped to
// the Divers the User manages; another User's Diver or item answers 404, never 403.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import { Type, type Static } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireUser, userOf } from '../auth/fastify.js';
import { EQUIPMENT_CATEGORIES, EQUIPMENT_STATUSES } from '../vocabulary.js';
import { answerErrors, answers, Problem } from '../http/problems.js';
import { IdParams, Nullable, refTo } from '../http/schemas.js';
import { EquipmentError } from './equipment-error.js';
import {
  DiveGearViewSchema, EquipmentDetailViewSchema, EquipmentDueViewSchema, EquipmentItemViewSchema,
  EquipmentUseInput, ServiceRecordViewSchema, ServiceScheduleViewSchema,
} from './equipment-view.js';
import type { EquipmentService, ItemInput, RecordInput, ScheduleInput } from './equipment-service.js';

export interface EquipmentRouteDeps {
  auth: Auth;
  equipment: EquipmentService;
}

const DateString = Type.String({ pattern: '^\\d{4}-\\d{2}-\\d{2}$', description: 'YYYY-MM-DD' });
const Name = Type.String({ minLength: 1, maxLength: 200, pattern: '\\S' });
const OptionalText = (maxLength: number) => Type.Optional(Nullable(Type.String({ maxLength })));
const CostCurrency = Type.String({ pattern: '^[A-Z]{3}$', description: 'Three capital letters, e.g. EUR' });

const ScheduleBody = Type.Object({
  name: Name,
  intervalMonths: Type.Optional(Nullable(Type.Integer({ minimum: 1, maximum: 1200, description: 'Every this many months' }))),
  intervalDives: Type.Optional(Nullable(Type.Integer({ minimum: 1, maximum: 1_000_000, description: 'Every this many Dives' }))),
  intervalHours: Type.Optional(Nullable(Type.Number({ exclusiveMinimum: 0, maximum: 100_000, description: 'Every this many dive hours' }))),
  startDate: DateString,
  active: Type.Optional(Type.Boolean({ description: 'Default true; an inactive schedule computes nothing' })),
}, { additionalProperties: false, description: 'At least one interval (months, dives or hours)' });

const ScheduleEditBody = Type.Object({ version: Type.Integer(), ...ScheduleBody.properties }, { additionalProperties: false });

const RecordBody = Type.Object({
  date: DateString,
  byWhom: Type.Optional(Nullable(Type.String({ maxLength: 200, description: 'The shop or person who did it' }))),
  notes: Type.Optional(Nullable(Type.String({ maxLength: 20_000 }))),
  costAmount: Type.Optional(Nullable(Type.Number({ minimum: 0, maximum: 1_000_000 }))),
  costCurrency: Type.Optional(Nullable(CostCurrency)),
  scheduleIds: Type.Array(Type.String({ format: 'uuid' }), { minItems: 1, description: 'The schedules this service resets' }),
}, { additionalProperties: false });

const RecordEditBody = Type.Object({ version: Type.Integer(), ...RecordBody.properties }, { additionalProperties: false });

const ItemCreateBody = Type.Object({
  category: Type.Enum([...EQUIPMENT_CATEGORIES]),
  name: Name,
  maker: OptionalText(200), model: OptionalText(200), serial: OptionalText(200),
  purchaseDate: Type.Optional(Nullable(DateString)),
  notes: OptionalText(20_000),
  equipmentStatus: Type.Optional(Type.Enum([...EQUIPMENT_STATUSES], { description: 'Default in_use' })),
  onEveryDive: Type.Optional(Type.Boolean({ description: 'Default false: the Dives it was put on count' })),
  inUseFrom: DateString,
  inUseUntil: Type.Optional(Nullable(DateString)),
  schedules: Type.Optional(Type.Array(ScheduleBody, { description: 'Initial schedules, created with the item' })),
}, { additionalProperties: false });

const ItemEditBody = Type.Object({
  version: Type.Integer(),
  category: Type.Optional(Type.Enum([...EQUIPMENT_CATEGORIES])),
  name: Type.Optional(Name),
  maker: OptionalText(200), model: OptionalText(200), serial: OptionalText(200),
  purchaseDate: Type.Optional(Nullable(DateString)),
  notes: OptionalText(20_000),
  equipmentStatus: Type.Optional(Type.Enum([...EQUIPMENT_STATUSES], { description: 'Leaving in use stamps inUseUntil; returning to it clears it' })),
  onEveryDive: Type.Optional(Type.Boolean()),
  inUseFrom: Type.Optional(DateString),
  inUseUntil: Type.Optional(Nullable(DateString)),
}, { additionalProperties: false });

const UseBody = Type.Object({
  itemId: Type.String({ format: 'uuid' }),
  use: EquipmentUseInput,
}, { additionalProperties: false });

const DiveEquipmentParams = Type.Object({ id: Type.String({ format: 'uuid' }), itemId: Type.String({ format: 'uuid' }) });

const STATUS: Record<EquipmentError['code'], number> = {
  diver_not_found: 404, equipment_not_found: 404, equipment_changed: 409, equipment_in_use: 409,
  equipment_use_mismatch: 409, schedule_not_found: 404, schedule_interval_required: 400,
  record_not_found: 404, equipment_item_not_cylinder: 400, invalid_input: 400,
};

const scheduleInput = (body: Static<typeof ScheduleBody>): ScheduleInput => ({
  name: body.name.trim(),
  intervalMonths: body.intervalMonths ?? null,
  intervalDives: body.intervalDives ?? null,
  intervalHours: body.intervalHours ?? null,
  startDate: body.startDate,
  active: body.active ?? true,
});

const recordInput = (body: Static<typeof RecordBody>): RecordInput => ({
  date: body.date, byWhom: body.byWhom ?? null, notes: body.notes ?? null,
  costAmount: body.costAmount ?? null, costCurrency: body.costCurrency ?? null, scheduleIds: body.scheduleIds,
});

const errors = { 400: Problem, 404: Problem, 409: Problem };

export const equipmentRoutes: FastifyPluginAsyncTypebox<EquipmentRouteDeps> = async (app, { auth, equipment }) => {
  app.addHook('onRequest', requireUser(auth));
  app.setErrorHandler(answerErrors(answers(EquipmentError, STATUS)));

  app.get('/divers/:id/equipment', {
    schema: {
      summary: 'A Diver\'s Equipment items with their schedules and computed due',
      params: IdParams, response: { 200: Type.Array(refTo(EquipmentItemViewSchema)), ...errors },
    },
  }, async (request) => equipment.list(userOf(request).id, request.params.id));

  app.post('/divers/:id/equipment', {
    schema: {
      summary: 'Create an Equipment item, optionally with its first schedules',
      description: 'No interval comes from Dive Hub: the User enters what the manual or the cylinder\'s stamp says.',
      params: IdParams, body: ItemCreateBody, response: { 201: refTo(EquipmentDetailViewSchema), ...errors },
    },
  }, async (request, reply) => {
    const body = request.body;
    const input: ItemInput = {
      category: body.category, name: body.name, maker: body.maker ?? null, model: body.model ?? null,
      serial: body.serial ?? null, purchaseDate: body.purchaseDate ?? null, notes: body.notes ?? null,
      equipmentStatus: body.equipmentStatus ?? 'in_use', onEveryDive: body.onEveryDive ?? false,
      inUseFrom: body.inUseFrom, inUseUntil: body.inUseUntil ?? null,
      schedules: (body.schedules ?? []).map(scheduleInput),
    };
    return reply.code(201).send(await equipment.create(userOf(request).id, request.params.id, input));
  });

  app.get('/equipment/due', {
    schema: {
      summary: 'Every schedule due, due soon or overdue across the User\'s Divers, worst first',
      response: { 200: Type.Array(refTo(EquipmentDueViewSchema)) },
    },
  }, async (request) => equipment.due(userOf(request).id));

  app.get('/equipment/:id', {
    schema: {
      summary: 'An Equipment item with schedules, usage, records and the last Cylinder that named it',
      params: IdParams, response: { 200: refTo(EquipmentDetailViewSchema), ...errors },
    },
  }, async (request) => equipment.detail(userOf(request).id, request.params.id));

  app.patch('/equipment/:id', {
    schema: {
      summary: 'Change an Equipment item; send the version you started from',
      description: '409 equipment_changed when it changed meanwhile. When the status leaves in use the server stamps '
        + 'inUseUntil (today unless sent); returning to in use clears it. Only what is sent changes.',
      params: IdParams, body: ItemEditBody, response: { 200: refTo(EquipmentDetailViewSchema), ...errors },
    },
  }, async (request) => equipment.edit(userOf(request).id, request.params.id, request.body));

  app.delete('/equipment/:id', {
    schema: {
      summary: 'Delete an Equipment item that was never used; a used one is retired instead',
      description: '409 equipment_in_use when it has any use on a Dive, a Device, a naming Cylinder or a service record.',
      params: IdParams, response: { 204: Type.Null(), ...errors },
    },
  }, async (request, reply) => {
    await equipment.remove(userOf(request).id, request.params.id);
    return reply.code(204).send(null);
  });

  app.post('/equipment/:id/schedules', {
    schema: {
      summary: 'Add a service schedule to an item', description: '400 schedule_interval_required without any interval.',
      params: IdParams, body: ScheduleBody, response: { 201: refTo(ServiceScheduleViewSchema), ...errors },
    },
  }, async (request, reply) => reply.code(201).send(await equipment.addSchedule(userOf(request).id, request.params.id, scheduleInput(request.body))));

  app.patch('/schedules/:id', {
    schema: {
      summary: 'Change a service schedule; send the version you started from',
      params: IdParams, body: ScheduleEditBody, response: { 200: refTo(ServiceScheduleViewSchema), ...errors },
    },
  }, async (request) => {
    const { version, ...body } = request.body;
    return equipment.editSchedule(userOf(request).id, request.params.id, version, scheduleInput(body));
  });

  app.delete('/schedules/:id', {
    schema: {
      summary: 'Delete a service schedule; its records stay', params: IdParams, response: { 204: Type.Null(), ...errors },
    },
  }, async (request, reply) => {
    await equipment.removeSchedule(userOf(request).id, request.params.id);
    return reply.code(204).send(null);
  });

  app.post('/equipment/:id/records', {
    schema: {
      summary: 'Record a service: it resets the schedules it names',
      description: 'At least one schedule; 404 schedule_not_found for one of another item.',
      params: IdParams, body: RecordBody, response: { 201: refTo(ServiceRecordViewSchema), ...errors },
    },
  }, async (request, reply) => reply.code(201).send(await equipment.addRecord(userOf(request).id, request.params.id, recordInput(request.body))));

  app.patch('/records/:id', {
    schema: {
      summary: 'Change a service record; send the version you started from',
      params: IdParams, body: RecordEditBody, response: { 200: refTo(ServiceRecordViewSchema), ...errors },
    },
  }, async (request) => {
    const { version, ...body } = request.body;
    return equipment.editRecord(userOf(request).id, request.params.id, version, recordInput(body));
  });

  app.delete('/records/:id', {
    schema: { summary: 'Delete a service record', params: IdParams, response: { 204: Type.Null(), ...errors } },
  }, async (request, reply) => {
    await equipment.removeRecord(userOf(request).id, request.params.id);
    return reply.code(204).send(null);
  });

  app.get('/dives/:id/equipment', {
    schema: {
      summary: 'The Dive\'s gear: derived items and the explicit put-on/taken-off exceptions',
      params: IdParams, response: { 200: refTo(DiveGearViewSchema), ...errors },
    },
  }, async (request) => equipment.diveGear(userOf(request).id, request.params.id));

  app.post('/dives/:id/equipment', {
    schema: {
      summary: 'Put an item on a Dive or take it off (an exception to the item\'s mode)',
      description: '409 equipment_use_mismatch for put_on on an every-Dive item or taken_off on another.',
      params: IdParams, body: UseBody, response: { 204: Type.Null(), ...errors },
    },
  }, async (request, reply) => {
    await equipment.setUse(userOf(request).id, request.params.id, request.body.itemId, request.body.use);
    return reply.code(204).send(null);
  });

  app.delete('/dives/:id/equipment/:itemId', {
    schema: {
      summary: 'Remove a put-on/taken-off row from a Dive', params: DiveEquipmentParams,
      response: { 204: Type.Null(), ...errors },
    },
  }, async (request, reply) => {
    await equipment.removeUse(userOf(request).id, request.params.id, request.params.itemId);
    return reply.code(204).send(null);
  });
};
