// Reading Equipment for the MCP tool (ADR 0035): items with their usage, schedules (computed due) and records for the
// Divers a User manages. Queries only; the page keeps the answer under the tool's size cap.
import { and, asc, count, desc, eq, inArray, isNull } from 'drizzle-orm';
import type { Queryable } from '../db/client.js';
import {
  diver, equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule,
} from '../db/schema.js';
import type { EquipmentCategory, EquipmentStatus } from '../vocabulary.js';
import { scheduleDue, type ScheduleDue } from './due.js';
import { usageMap, type Usage } from './usage.js';

export interface EquipmentListFilter {
  /** The Divers the User manages, already narrowed by `diversAsked`. */
  diverIds: string[];
  category?: EquipmentCategory | undefined;
  limit: number;
  offset: number;
}

export interface EquipmentScheduleRead {
  id: string;
  name: string;
  intervalMonths: number | null;
  intervalDives: number | null;
  intervalHours: number | null;
  startDate: string;
  active: boolean;
  due: ScheduleDue | null;
}

export interface EquipmentRecordRead {
  id: string;
  date: string;
  byWhom: string | null;
  notes: string | null;
  costAmount: number | null;
  costCurrency: string | null;
  scheduleIds: string[];
}

export interface EquipmentItemRead {
  id: string;
  diver: { id: string; name: string };
  category: EquipmentCategory;
  name: string;
  maker: string | null;
  model: string | null;
  serial: string | null;
  purchaseDate: string | null;
  notes: string | null;
  status: EquipmentStatus;
  onEveryDive: boolean;
  inUseFrom: string;
  inUseUntil: string | null;
  usage: { diveCount: number; hours: number; lastUsedOn: string | null };
  schedules: EquipmentScheduleRead[];
  records: EquipmentRecordRead[];
}

const today = () => new Date().toISOString().slice(0, 10);

const usageSummary = (usage: Usage) => ({
  diveCount: usage.count,
  hours: usage.hours,
  lastUsedOn: usage.dives.reduce<string | null>((latest, dive) => (latest === null || dive.date > latest ? dive.date : latest), null),
});

/**
 * The User's managed Divers' items, by Diver, category and name, with their schedules' due today and their records.
 * Deleted items and deleted Divers stay out; usage counts the Dives the item was used on (ADR 0034 D3).
 */
export async function equipmentList(q: Queryable, filter: EquipmentListFilter): Promise<{ items: EquipmentItemRead[]; total: number }> {
  if (filter.diverIds.length === 0) return { items: [], total: 0 };
  const where = and(
    inArray(equipmentItem.diverId, filter.diverIds),
    isNull(equipmentItem.deletedAt),
    isNull(diver.deletedAt),
    ...(filter.category ? [eq(equipmentItem.category, filter.category)] : []),
  );
  const total = (await q.select({ n: count() }).from(equipmentItem)
    .innerJoin(diver, eq(diver.id, equipmentItem.diverId)).where(where))[0]?.n ?? 0;
  const rows = await q.select({ item: equipmentItem, diverName: diver.name }).from(equipmentItem)
    .innerJoin(diver, eq(diver.id, equipmentItem.diverId)).where(where)
    .orderBy(asc(diver.name), asc(equipmentItem.category), asc(equipmentItem.name), asc(equipmentItem.id))
    .limit(filter.limit).offset(filter.offset);
  if (rows.length === 0) return { items: [], total };

  const items = rows.map((row) => row.item);
  const usage = await usageMap(q, items);
  const schedules = await q.select().from(serviceSchedule)
    .where(inArray(serviceSchedule.itemId, items.map((item) => item.id))).orderBy(asc(serviceSchedule.name));
  const resets = schedules.length === 0 ? [] : await q.select({ scheduleId: serviceRecordSchedule.scheduleId, date: serviceRecord.date })
    .from(serviceRecordSchedule).innerJoin(serviceRecord, eq(serviceRecord.id, serviceRecordSchedule.recordId))
    .where(inArray(serviceRecordSchedule.scheduleId, schedules.map((schedule) => schedule.id)));
  const records = await q.select().from(serviceRecord)
    .where(inArray(serviceRecord.itemId, items.map((item) => item.id)))
    .orderBy(desc(serviceRecord.date), desc(serviceRecord.createdAt));
  const joins = records.length === 0 ? [] : await q.select().from(serviceRecordSchedule)
    .where(inArray(serviceRecordSchedule.recordId, records.map((record) => record.id)));
  const day = today();

  return {
    total,
    items: rows.map(({ item, diverName }) => {
      const itemUsage = usage.get(item.id)!;
      return {
        id: item.id,
        diver: { id: item.diverId, name: diverName },
        category: item.category,
        name: item.name,
        maker: item.maker,
        model: item.model,
        serial: item.serial,
        purchaseDate: item.purchaseDate,
        notes: item.notes,
        status: item.status,
        onEveryDive: item.onEveryDive,
        inUseFrom: item.inUseFrom,
        inUseUntil: item.inUseUntil,
        usage: usageSummary(itemUsage),
        schedules: schedules.filter((schedule) => schedule.itemId === item.id).map((schedule) => ({
          id: schedule.id,
          name: schedule.name,
          intervalMonths: schedule.intervalMonths,
          intervalDives: schedule.intervalDives,
          intervalHours: schedule.intervalHours,
          startDate: schedule.startDate,
          active: schedule.active,
          due: scheduleDue({
            schedule,
            resets: resets.filter((reset) => reset.scheduleId === schedule.id).map((reset) => reset.date),
            usage: itemUsage.dives,
            today: day,
          }),
        })),
        records: records.filter((record) => record.itemId === item.id).map((record) => ({
          id: record.id,
          date: record.date,
          byWhom: record.byWhom,
          notes: record.notes,
          costAmount: record.costAmount === null ? null : Number(record.costAmount),
          costCurrency: record.costCurrency,
          scheduleIds: joins.filter((join) => join.recordId === record.id).map((join) => join.scheduleId),
        })),
      };
    }),
  };
}
