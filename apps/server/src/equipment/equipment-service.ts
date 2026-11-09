// Equipment items, their service schedules and records (ADR 0034): CRUD, usage and due, all scoped to the Divers the
// User manages. Due is computed by due.ts from usage.ts; nothing about it is stored.
import { and, count, desc, eq, getTableColumns, inArray, isNull, sql } from 'drizzle-orm';
import type { Db, Queryable, Tx } from '../db/client.js';
import {
  cylinder, device, dive, diver, diverManagement, equipmentItem, equipmentUse,
  recording, serviceRecord, serviceRecordSchedule, serviceSchedule,
} from '../db/schema.js';
import { managedDiverIds, managementOf } from '../divers/management.js';
import { writeRevision, userActor, type Changes } from '../revisions/revisions.js';
import type { EquipmentCategory, EquipmentStatus, EquipmentUse } from '../vocabulary.js';
import { scheduleDue, type ScheduleDue, type UsageDive } from './due.js';
import { EquipmentError } from './equipment-error.js';
import { usageMap, usageOf, type Usage } from './usage.js';

export interface ScheduleInput {
  name: string;
  intervalMonths: number | null;
  intervalDives: number | null;
  intervalHours: number | null;
  startDate: string;
  active: boolean;
}

export interface RecordInput {
  date: string;
  byWhom: string | null;
  notes: string | null;
  costAmount: number | null;
  costCurrency: string | null;
  scheduleIds: string[];
}

export interface ItemInput {
  category: EquipmentCategory;
  name: string;
  maker: string | null;
  model: string | null;
  serial: string | null;
  purchaseDate: string | null;
  notes: string | null;
  equipmentStatus: EquipmentStatus;
  onEveryDive: boolean;
  inUseFrom: string;
  inUseUntil: string | null;
  schedules?: ScheduleInput[];
}

export interface ItemEdit {
  version: number;
  category?: EquipmentCategory;
  name?: string;
  maker?: string | null;
  model?: string | null;
  serial?: string | null;
  purchaseDate?: string | null;
  notes?: string | null;
  equipmentStatus?: EquipmentStatus;
  onEveryDive?: boolean;
  inUseFrom?: string;
  inUseUntil?: string | null;
}

type ItemRow = typeof equipmentItem.$inferSelect;
type ScheduleRow = typeof serviceSchedule.$inferSelect;
type RecordRow = typeof serviceRecord.$inferSelect;

export interface ScheduleView {
  id: string; itemId: string; name: string;
  intervalMonths: number | null; intervalDives: number | null; intervalHours: number | null;
  startDate: string; active: boolean; version: number; due: ScheduleDue | null;
}

export interface RecordView {
  id: string; itemId: string; date: string; byWhom: string | null; notes: string | null;
  costAmount: number | null; costCurrency: string | null; version: number; scheduleIds: string[];
}

export interface ItemView {
  id: string; diverId: string; category: EquipmentCategory; name: string; maker: string | null; model: string | null;
  serial: string | null; purchaseDate: string | null; notes: string | null; equipmentStatus: EquipmentStatus;
  onEveryDive: boolean; inUseFrom: string; inUseUntil: string | null; version: number; createdAt: string; updatedAt: string;
  deviceId: string | null; schedules: ScheduleView[];
}

export interface LastCylinder {
  volumeL: number | null; workingPressureBar: number | null; material: 'aluminium' | 'steel' | 'carbon' | null;
  gas: { o2: number; he: number } | null;
}

export interface UsageSummary {
  diveCount: number; hours: number; lastUsedOn: string | null;
}

export interface ItemDetailView extends ItemView {
  usage: UsageSummary;
  records: RecordView[];
  lastCylinder: LastCylinder | null;
}

export interface DueEntry {
  itemId: string; itemName: string; category: EquipmentCategory;
  diverId: string; diverName: string;
  scheduleId: string; scheduleName: string;
  due: ScheduleDue;
}

export interface GearItem {
  id: string; name: string; category: EquipmentCategory; equipmentStatus: EquipmentStatus; onEveryDive: boolean;
}
export interface DiveGear {
  derived: (GearItem & { sources: ('every_dive' | 'device' | 'cylinder')[] })[];
  putOn: GearItem[];
  takenOff: GearItem[];
}

const today = () => new Date().toISOString().slice(0, 10);
const SEVERITY: Record<ScheduleDue['state'], number> = { overdue: 0, due: 1, due_soon: 2, ok: 3 };

const trimName = (name: string) => name.trim();

/** `2026-02-30` is no date, though it looks like one (the same check as the Diver's body weight). */
const isCalendarDate = (text: string) => {
  const parsed = new Date(`${text}T00:00:00Z`);
  return /^\d{4}-\d{2}-\d{2}$/.test(text) && !Number.isNaN(parsed.getTime()) && parsed.toISOString().startsWith(text);
};

/** Every date an input carries is a real calendar date, so PostgreSQL is never asked to reject one. */
function checkDates(...dates: (string | null)[]) {
  for (const date of dates) if (date !== null && !isCalendarDate(date)) throw new EquipmentError('invalid_input');
}

/** An item's date window must be a real one. */
function checkWindow(from: string, until: string | null) {
  if (until !== null && until < from) throw new EquipmentError('invalid_input');
}

/** A record's amount and currency come together, as the database's check requires. */
function checkCost(costAmount: number | null, costCurrency: string | null) {
  if ((costAmount === null) !== (costCurrency === null)) throw new EquipmentError('invalid_input');
}

/** The summary of an item's usage Dives. */
function usageSummary(usage: Usage): UsageSummary {
  return {
    diveCount: usage.count,
    hours: usage.hours,
    lastUsedOn: usage.dives.reduce<string | null>((latest, dive) => (latest === null || dive.date > latest ? dive.date : latest), null),
  };
}

/** The view of one schedule, with its due today. */
function scheduleView(row: ScheduleRow, usage: readonly UsageDive[], resets: readonly { date: string }[]): ScheduleView {
  return {
    id: row.id, itemId: row.itemId, name: row.name,
    intervalMonths: row.intervalMonths, intervalDives: row.intervalDives, intervalHours: row.intervalHours,
    startDate: row.startDate, active: row.active, version: row.version,
    due: scheduleDue({ schedule: row, resets: resets.map((reset) => reset.date), usage, today: today() }),
  };
}

function itemView(row: ItemRow, deviceId: string | null, schedules: ScheduleView[]): ItemView {
  return {
    id: row.id, diverId: row.diverId, category: row.category, name: row.name, maker: row.maker, model: row.model,
    serial: row.serial, purchaseDate: row.purchaseDate, notes: row.notes, equipmentStatus: row.status,
    onEveryDive: row.onEveryDive, inUseFrom: row.inUseFrom, inUseUntil: row.inUseUntil, version: row.version,
    createdAt: row.createdAt.toISOString(), updatedAt: row.updatedAt.toISOString(), deviceId, schedules,
  };
}

const gearItem = (row: ItemRow): GearItem => ({
  id: row.id, name: row.name, category: row.category, equipmentStatus: row.status, onEveryDive: row.onEveryDive,
});

/** How soon a due needs attention: the remaining count of the dimension that made it the worst, for sorting. */
function remainingOf(due: ScheduleDue): number {
  if (due.months?.state === due.state) return due.months.remainingDays;
  if (due.dives?.state === due.state) return due.dives.remaining;
  if (due.hours?.state === due.state) return due.hours.remaining;
  return 0;
}

export function createEquipmentService(db: Db) {
  /** The item and, when asked, its lock; 404 for an item of a Diver the User doesn't manage. */
  async function managedItem(q: Queryable, userId: string, itemId: string, lock = false): Promise<ItemRow> {
    const query = q.select().from(equipmentItem).where(and(eq(equipmentItem.id, itemId), isNull(equipmentItem.deletedAt)));
    const [row] = lock ? await query.for('update') : await query;
    if (!row || !(await managementOf(q, userId, row.diverId))) throw new EquipmentError('equipment_not_found');
    return row;
  }

  async function managedSchedule(q: Queryable, userId: string, scheduleId: string, lock = false) {
    const query = q.select({ schedule: serviceSchedule, item: equipmentItem }).from(serviceSchedule)
      .innerJoin(equipmentItem, eq(equipmentItem.id, serviceSchedule.itemId))
      .where(and(eq(serviceSchedule.id, scheduleId), isNull(equipmentItem.deletedAt)));
    const [row] = lock ? await query.for('update') : await query;
    if (!row || !(await managementOf(q, userId, row.item.diverId))) throw new EquipmentError('schedule_not_found');
    return row;
  }

  async function managedRecord(q: Queryable, userId: string, recordId: string, lock = false) {
    const query = q.select({ record: serviceRecord, item: equipmentItem }).from(serviceRecord)
      .innerJoin(equipmentItem, eq(equipmentItem.id, serviceRecord.itemId))
      .where(and(eq(serviceRecord.id, recordId), isNull(equipmentItem.deletedAt)));
    const [row] = lock ? await query.for('update') : await query;
    if (!row || !(await managementOf(q, userId, row.item.diverId))) throw new EquipmentError('record_not_found');
    return row;
  }

  /** Every schedule of the given items with its due, in one pass for the whole page. */
  async function schedulesFor(q: Queryable, items: ItemRow[], usage: Map<string, Usage>): Promise<Map<string, ScheduleView[]>> {
    const out = new Map<string, ScheduleView[]>(items.map((item) => [item.id, []]));
    if (items.length === 0) return out;
    const rows = await q.select().from(serviceSchedule).where(inArray(serviceSchedule.itemId, items.map((item) => item.id)))
      .orderBy(serviceSchedule.name);
    if (rows.length === 0) return out;
    const resets = await q.select({ scheduleId: serviceRecordSchedule.scheduleId, date: serviceRecord.date })
      .from(serviceRecordSchedule).innerJoin(serviceRecord, eq(serviceRecord.id, serviceRecordSchedule.recordId))
      .where(inArray(serviceRecordSchedule.scheduleId, rows.map((row) => row.id)));
    for (const row of rows) {
      out.get(row.itemId)?.push(scheduleView(
        row, usage.get(row.itemId)?.dives ?? [], resets.filter((reset) => reset.scheduleId === row.id),
      ));
    }
    return out;
  }

  async function schedulesOf(q: Queryable, item: ItemRow, usage: Usage): Promise<ScheduleView[]> {
    return (await schedulesFor(q, [item], new Map([[item.id, usage]]))).get(item.id) ?? [];
  }

  async function recordsOf(q: Queryable, itemId: string): Promise<RecordView[]> {
    const rows = await q.select().from(serviceRecord).where(eq(serviceRecord.itemId, itemId))
      .orderBy(desc(serviceRecord.date), desc(serviceRecord.createdAt));
    if (rows.length === 0) return [];
    const joins = await q.select().from(serviceRecordSchedule).where(inArray(serviceRecordSchedule.recordId, rows.map((row) => row.id)));
    return rows.map((row) => recordView(row, joins.filter((join) => join.recordId === row.id).map((join) => join.scheduleId)));
  }

  const recordView = (row: RecordRow, scheduleIds: string[]): RecordView => ({
    id: row.id, itemId: row.itemId, date: row.date, byWhom: row.byWhom, notes: row.notes,
    costAmount: row.costAmount === null ? null : Number(row.costAmount), costCurrency: row.costCurrency,
    version: row.version, scheduleIds,
  });

  const deviceOf = async (q: Queryable, itemId: string) => {
    const [row] = await q.select({ id: device.id }).from(device)
      .where(and(eq(device.equipmentItemId, itemId), isNull(device.deletedAt))).limit(1);
    return row?.id ?? null;
  };

  async function assemble(q: Queryable, items: ItemRow[]): Promise<ItemView[]> {
    if (items.length === 0) return [];
    const usage = await usageMap(q, items);
    const schedules = await schedulesFor(q, items, usage);
    const devices = await q.select({ id: device.id, itemId: device.equipmentItemId }).from(device)
      .where(and(inArray(device.equipmentItemId, items.map((item) => item.id)), isNull(device.deletedAt)));
    return items.map((item) => itemView(
      item,
      devices.find((made) => made.itemId === item.id)?.id ?? null,
      schedules.get(item.id) ?? [],
    ));
  }

  async function detailOf(userId: string, itemId: string): Promise<ItemDetailView> {
    const item = await managedItem(db, userId, itemId);
    const usage = await usageOf(db, item.id);
    return {
      ...itemView(item, await deviceOf(db, item.id), await schedulesOf(db, item, usage)),
      usage: usageSummary(usage),
      records: await recordsOf(db, item.id),
      lastCylinder: item.category === 'cylinder' ? await lastCylinderOf(db, item.id) : null,
    };
  }

  /** A validated schedule input: at least one positive interval. */
  function checkSchedule(input: ScheduleInput) {
    checkDates(input.startDate);
    if (input.intervalMonths === null && input.intervalDives === null && input.intervalHours === null) throw new EquipmentError('schedule_interval_required');
    if ((input.intervalMonths !== null && input.intervalMonths <= 0)
      || (input.intervalDives !== null && input.intervalDives <= 0)
      || (input.intervalHours !== null && input.intervalHours <= 0)) throw new EquipmentError('invalid_input');
  }

  const scheduleValues = (input: ScheduleInput) => ({
    name: trimName(input.name), intervalMonths: input.intervalMonths, intervalDives: input.intervalDives,
    intervalHours: input.intervalHours, startDate: input.startDate, active: input.active,
  });

  async function insertSchedule(tx: Tx, itemId: string, input: ScheduleInput): Promise<ScheduleRow> {
    checkSchedule(input);
    const [made] = await tx.insert(serviceSchedule).values({ itemId, ...scheduleValues(input) }).returning();
    return made!;
  }

  /** A record resets at least one schedule, and only schedules of its item. */
  async function checkResets(q: Queryable, itemId: string, scheduleIds: string[]) {
    if (scheduleIds.length === 0) throw new EquipmentError('invalid_input');
    const found = await q.select({ id: serviceSchedule.id }).from(serviceSchedule)
      .where(and(eq(serviceSchedule.itemId, itemId), inArray(serviceSchedule.id, scheduleIds)));
    if (found.length !== new Set(scheduleIds).size) throw new EquipmentError('schedule_not_found');
  }

  async function lastCylinderOf(q: Queryable, itemId: string): Promise<LastCylinder | null> {
    const [row] = await q.select({
      volumeL: cylinder.volumeL, workingPressureBar: cylinder.workingPressureBar, material: cylinder.material,
      o2: cylinder.o2, he: cylinder.he,
    }).from(cylinder).innerJoin(dive, eq(dive.id, cylinder.diveId))
      .where(and(eq(cylinder.equipmentItemId, itemId), isNull(dive.deletedAt)))
      .orderBy(desc(dive.startsAt)).limit(1);
    if (!row) return null;
    return {
      volumeL: row.volumeL, workingPressureBar: row.workingPressureBar, material: row.material,
      gas: row.o2 === null || row.he === null ? null : { o2: row.o2, he: row.he },
    };
  }

  return {
    /** The items of one managed Diver, with schedules and due (the Equipment page). */
    async list(userId: string, diverId: string): Promise<ItemView[]> {
      if (!(await managementOf(db, userId, diverId))) throw new EquipmentError('diver_not_found');
      const rows = await db.select().from(equipmentItem)
        .where(and(eq(equipmentItem.diverId, diverId), isNull(equipmentItem.deletedAt)))
        .orderBy(equipmentItem.category, equipmentItem.name);
      return assemble(db, rows);
    },

    /** Every schedule that is due, due soon or overdue across the User's Divers, worst first. */
    async due(userId: string): Promise<DueEntry[]> {
      const diverIds = [...(await managedDiverIds(db, userId, { live: true }))];
      if (diverIds.length === 0) return [];
      const rows = await db.select({ item: equipmentItem, diverName: diver.name })
        .from(equipmentItem).innerJoin(diver, eq(diver.id, equipmentItem.diverId))
        .where(and(inArray(equipmentItem.diverId, diverIds), isNull(equipmentItem.deletedAt)));
      const usage = await usageMap(db, rows.map((row) => row.item));
      const schedules = await schedulesFor(db, rows.map((row) => row.item), usage);
      const entries: DueEntry[] = [];
      for (const { item, diverName } of rows) {
        for (const schedule of schedules.get(item.id) ?? []) {
          if (!schedule.due || schedule.due.state === 'ok') continue;
          entries.push({
            itemId: item.id, itemName: item.name, category: item.category, diverId: item.diverId, diverName,
            scheduleId: schedule.id, scheduleName: schedule.name, due: schedule.due,
          });
        }
      }
      return entries.sort((a, b) => SEVERITY[a.due.state] - SEVERITY[b.due.state]
        || remainingOf(a.due) - remainingOf(b.due) || a.itemName.localeCompare(b.itemName));
    },

    detail: detailOf,

    async create(userId: string, diverId: string, input: ItemInput): Promise<ItemDetailView> {
      if (!(await managementOf(db, userId, diverId))) throw new EquipmentError('diver_not_found');
      checkDates(input.inUseFrom, input.inUseUntil, input.purchaseDate);
      checkWindow(input.inUseFrom, input.inUseUntil);
      const status = input.equipmentStatus;
      const inUseUntil = status === 'in_use' ? null : (input.inUseUntil ?? today());
      checkWindow(input.inUseFrom, inUseUntil);
      const id = await db.transaction(async (tx) => {
        const [made] = await tx.insert(equipmentItem).values({
          diverId, category: input.category, name: trimName(input.name), maker: input.maker, model: input.model,
          serial: input.serial, purchaseDate: input.purchaseDate, notes: input.notes, status,
          onEveryDive: input.onEveryDive, inUseFrom: input.inUseFrom, inUseUntil,
        }).returning();
        for (const schedule of input.schedules ?? []) await insertSchedule(tx, made!.id, schedule);
        await writeRevision(tx, 'equipment_item', made!.id, userActor(userId), 'create', {
          name: { from: null, to: made!.name }, category: { from: null, to: made!.category },
        });
        return made!.id;
      });
      return detailOf(userId, id);
    },

    async edit(userId: string, itemId: string, edit: ItemEdit): Promise<ItemDetailView> {
      await db.transaction(async (tx) => {
        const current = await managedItem(tx, userId, itemId, true);
        if (current.version !== edit.version) throw new EquipmentError('equipment_changed');
        const status = edit.equipmentStatus ?? current.status;
        const inUseFrom = edit.inUseFrom ?? current.inUseFrom;
        const inUseUntil = status === 'in_use'
          ? null
          : edit.inUseUntil !== undefined ? edit.inUseUntil : (current.inUseUntil ?? today());
        checkDates(inUseFrom, inUseUntil, edit.purchaseDate ?? null);
        checkWindow(inUseFrom, inUseUntil);
        const next = {
          category: edit.category ?? current.category, name: edit.name === undefined ? current.name : trimName(edit.name),
          maker: edit.maker === undefined ? current.maker : edit.maker, model: edit.model === undefined ? current.model : edit.model,
          serial: edit.serial === undefined ? current.serial : edit.serial,
          purchaseDate: edit.purchaseDate === undefined ? current.purchaseDate : edit.purchaseDate,
          notes: edit.notes === undefined ? current.notes : edit.notes, status,
          onEveryDive: edit.onEveryDive === undefined ? current.onEveryDive : edit.onEveryDive,
          inUseFrom, inUseUntil, version: current.version + 1, updatedAt: new Date(),
        };
        const changes: Changes = {};
        for (const key of ['category', 'name', 'maker', 'model', 'serial', 'purchaseDate', 'notes', 'status', 'onEveryDive', 'inUseFrom', 'inUseUntil'] as const) {
          if (JSON.stringify(current[key]) !== JSON.stringify(next[key])) changes[key] = { from: current[key], to: next[key] };
        }
        if (Object.keys(changes).length === 0) return;
        await tx.update(equipmentItem).set(next).where(eq(equipmentItem.id, itemId));
        await writeRevision(tx, 'equipment_item', itemId, userActor(userId), 'edit', changes);
      });
      return detailOf(userId, itemId);
    },

    /** Deletes an item only while it was never used (ADR 0034 D2); a used one is retired instead. */
    async remove(userId: string, itemId: string): Promise<void> {
      await db.transaction(async (tx) => {
        await managedItem(tx, userId, itemId, true);
        const [used] = await tx.select({ n: count() }).from(equipmentUse).where(eq(equipmentUse.itemId, itemId));
        const [linkedDevice] = await tx.select({ id: device.id }).from(device).where(eq(device.equipmentItemId, itemId)).limit(1);
        const [linkedCylinder] = await tx.select({ diveId: cylinder.diveId }).from(cylinder).where(eq(cylinder.equipmentItemId, itemId)).limit(1);
        const [serviced] = await tx.select({ id: serviceRecord.id }).from(serviceRecord).where(eq(serviceRecord.itemId, itemId)).limit(1);
        if ((used?.n ?? 0) > 0 || linkedDevice || linkedCylinder || serviced) throw new EquipmentError('equipment_in_use');
        const at = new Date();
        await tx.update(equipmentItem).set({ deletedAt: at, version: sql`${equipmentItem.version} + 1`, updatedAt: at })
          .where(eq(equipmentItem.id, itemId));
        await writeRevision(tx, 'equipment_item', itemId, userActor(userId), 'delete', { deletedAt: { from: null, to: at } });
      });
    },

    async addSchedule(userId: string, itemId: string, input: ScheduleInput): Promise<ScheduleView> {
      const item = await managedItem(db, userId, itemId);
      const made = await db.transaction((tx) => insertSchedule(tx, itemId, input));
      return scheduleView(made, (await usageOf(db, item.id)).dives, []);
    },

    async editSchedule(userId: string, scheduleId: string, version: number, input: ScheduleInput): Promise<ScheduleView> {
      const item = await db.transaction(async (tx) => {
        const { schedule, item } = await managedSchedule(tx, userId, scheduleId, true);
        if (schedule.version !== version) throw new EquipmentError('equipment_changed');
        checkSchedule(input);
        await tx.update(serviceSchedule).set({ ...scheduleValues(input), version: schedule.version + 1, updatedAt: new Date() })
          .where(eq(serviceSchedule.id, scheduleId));
        return item;
      });
      const usage = await usageOf(db, item.id);
      const [row] = await db.select().from(serviceSchedule).where(eq(serviceSchedule.id, scheduleId));
      const resets = await db.select({ date: serviceRecord.date }).from(serviceRecordSchedule)
        .innerJoin(serviceRecord, eq(serviceRecord.id, serviceRecordSchedule.recordId))
        .where(eq(serviceRecordSchedule.scheduleId, scheduleId));
      return scheduleView(row!, usage.dives, resets);
    },

    async removeSchedule(userId: string, scheduleId: string): Promise<void> {
      await db.transaction(async (tx) => {
        await managedSchedule(tx, userId, scheduleId, true);
        // The join rows go; the records (facts) stay (ADR 0034).
        await tx.delete(serviceSchedule).where(eq(serviceSchedule.id, scheduleId));
      });
    },

    async addRecord(userId: string, itemId: string, input: RecordInput): Promise<RecordView> {
      checkDates(input.date);
      checkCost(input.costAmount, input.costCurrency);
      const made = await db.transaction(async (tx) => {
        // The lock makes deleting the item wait for this record; once deleted, the item is not found here.
        await managedItem(tx, userId, itemId, true);
        await checkResets(tx, itemId, input.scheduleIds);
        const [record] = await tx.insert(serviceRecord).values({
          itemId, date: input.date, byWhom: input.byWhom, notes: input.notes,
          costAmount: input.costAmount === null ? null : String(input.costAmount), costCurrency: input.costCurrency,
        }).returning();
        await tx.insert(serviceRecordSchedule).values([...new Set(input.scheduleIds)].map((scheduleId) => ({ recordId: record!.id, scheduleId })));
        return record!;
      });
      return recordView(made, input.scheduleIds);
    },

    async editRecord(userId: string, recordId: string, version: number, input: RecordInput): Promise<RecordView> {
      checkDates(input.date);
      checkCost(input.costAmount, input.costCurrency);
      return db.transaction(async (tx) => {
        const { record } = await managedRecord(tx, userId, recordId, true);
        if (record.version !== version) throw new EquipmentError('equipment_changed');
        await checkResets(tx, record.itemId, input.scheduleIds);
        const [made] = await tx.update(serviceRecord).set({
          date: input.date, byWhom: input.byWhom, notes: input.notes,
          costAmount: input.costAmount === null ? null : String(input.costAmount), costCurrency: input.costCurrency,
          version: record.version + 1, updatedAt: new Date(),
        }).where(eq(serviceRecord.id, recordId)).returning();
        await tx.delete(serviceRecordSchedule).where(eq(serviceRecordSchedule.recordId, recordId));
        await tx.insert(serviceRecordSchedule).values([...new Set(input.scheduleIds)].map((scheduleId) => ({ recordId, scheduleId })));
        return recordView(made!, [...new Set(input.scheduleIds)]);
      });
    },

    async removeRecord(userId: string, recordId: string): Promise<void> {
      await db.transaction(async (tx) => {
        await managedRecord(tx, userId, recordId, true);
        await tx.delete(serviceRecord).where(eq(serviceRecord.id, recordId));
      });
    },

    /** Put an item on a Dive or take it off (ADR 0034 D3): only as an exception to the item's mode. */
    async setUse(userId: string, diveId: string, itemId: string, use: EquipmentUse): Promise<void> {
      await db.transaction(async (tx) => {
        const [row] = await tx.select({ dive }).from(dive)
          .innerJoin(diverManagement, and(eq(diverManagement.diverId, dive.diverId), eq(diverManagement.userId, userId)))
          .where(and(eq(dive.id, diveId), isNull(dive.deletedAt)));
        if (!row) throw new EquipmentError('diver_not_found');
        const item = await managedItem(tx, userId, itemId, true);
        if (item.diverId !== row.dive.diverId) throw new EquipmentError('equipment_not_found');
        if (item.onEveryDive === (use === 'put_on')) throw new EquipmentError('equipment_use_mismatch');
        await tx.insert(equipmentUse).values({ diveId, itemId, use })
          .onConflictDoUpdate({ target: [equipmentUse.diveId, equipmentUse.itemId], set: { use } });
      });
    },

    async removeUse(userId: string, diveId: string, itemId: string): Promise<void> {
      await db.transaction(async (tx) => {
        const [row] = await tx.select({ dive }).from(dive)
          .innerJoin(diverManagement, and(eq(diverManagement.diverId, dive.diverId), eq(diverManagement.userId, userId)))
          .where(and(eq(dive.id, diveId), isNull(dive.deletedAt)));
        if (!row) throw new EquipmentError('diver_not_found');
        await managedItem(tx, userId, itemId);
        await tx.delete(equipmentUse).where(and(eq(equipmentUse.diveId, diveId), eq(equipmentUse.itemId, itemId)));
      });
    },

    /** The Dive's gear: what its owner's items say without an explicit use, and its put-on/taken-off exceptions. */
    async diveGear(userId: string, diveId: string): Promise<DiveGear> {
      const [row] = await db.select({ dive }).from(dive)
        .innerJoin(diverManagement, and(eq(diverManagement.diverId, dive.diverId), eq(diverManagement.userId, userId)))
        .where(and(eq(dive.id, diveId), isNull(dive.deletedAt)));
      if (!row) throw new EquipmentError('diver_not_found');
      const localDate = new Date(row.dive.startsAt.getTime() + (row.dive.utcOffsetSeconds ?? 0) * 1000).toISOString().slice(0, 10);
      const derived = new Map<string, ItemRow & { sources: ('every_dive' | 'device' | 'cylinder')[] }>();
      const add = (items: ItemRow[], source: 'every_dive' | 'device' | 'cylinder') => {
        for (const item of items) {
          const found = derived.get(item.id) ?? { ...item, sources: [] };
          if (!found.sources.includes(source)) found.sources.push(source);
          derived.set(item.id, found);
        }
      };
      add(await db.select().from(equipmentItem).where(and(
        eq(equipmentItem.diverId, row.dive.diverId), eq(equipmentItem.onEveryDive, true), isNull(equipmentItem.deletedAt),
        sql`${equipmentItem.inUseFrom} <= ${localDate}::date`,
        sql`(${equipmentItem.inUseUntil} is null or ${equipmentItem.inUseUntil} > ${localDate}::date)`,
        sql`not exists (select 1 from equipment_use eu where eu.dive_id = ${diveId}::uuid and eu.item_id = ${equipmentItem.id} and eu.use = 'taken_off')`,
      )), 'every_dive');
      add(await db.selectDistinct(getTableColumns(equipmentItem)).from(equipmentItem)
        .innerJoin(device, eq(device.equipmentItemId, equipmentItem.id))
        .innerJoin(recording, and(eq(recording.deviceId, device.id), isNull(recording.deletedAt)))
        .where(and(eq(recording.diveId, diveId), isNull(equipmentItem.deletedAt),
          eq(equipmentItem.diverId, row.dive.diverId), isNull(device.deletedAt))), 'device');
      add(await db.selectDistinct(getTableColumns(equipmentItem)).from(equipmentItem)
        .innerJoin(cylinder, eq(cylinder.equipmentItemId, equipmentItem.id))
        .where(and(eq(cylinder.diveId, diveId), isNull(equipmentItem.deletedAt),
          eq(equipmentItem.diverId, row.dive.diverId))), 'cylinder');
      const uses = await db.select({ use: equipmentUse.use, item: equipmentItem }).from(equipmentUse)
        .innerJoin(equipmentItem, eq(equipmentItem.id, equipmentUse.itemId))
        .where(and(eq(equipmentUse.diveId, diveId), isNull(equipmentItem.deletedAt)));
      return {
        derived: [...derived.values()].map((item) => ({ ...gearItem(item), sources: item.sources })),
        putOn: uses.filter((use) => use.use === 'put_on').map((use) => gearItem(use.item)),
        takenOff: uses.filter((use) => use.use === 'taken_off').map((use) => gearItem(use.item)),
      };
    },
  };
}

export type EquipmentService = ReturnType<typeof createEquipmentService>;

/**
 * The Equipment item a Device is (ADR 0034 D7): created with the Device, named after it, as a dive computer (the Device
 * table cannot tell a transmitter). It counts its Dives through the Device's Recordings, not as an every-Dive item.
 */
export async function ensureItemForDevice(
  tx: Tx, deviceOf: { diverId: string; manufacturer: string; product: string | null; inUseFrom?: string },
): Promise<string> {
  const [made] = await tx.insert(equipmentItem).values({
    diverId: deviceOf.diverId, category: 'dive_computer',
    name: trimName(`${deviceOf.manufacturer} ${deviceOf.product ?? ''}`), status: 'in_use',
    onEveryDive: false, inUseFrom: deviceOf.inUseFrom ?? today(),
  }).returning({ id: equipmentItem.id });
  return made!.id;
}
