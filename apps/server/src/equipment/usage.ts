// An item's usage (ADR 0034, plan D3): the union of four sources over the Diver's never-deleted Dives (ADR 0026).
// An item on every Dive counts its Diver's Dives in the in-use window except the ones it was taken off; other items
// count the Dives they were put on; a Device counts the Dives of its Recordings; a Cylinder naming the item counts its
// Dive. The result is the set of Dives, with the count and summed hours as aggregates.
import { sql } from 'drizzle-orm';
import type { Queryable } from '../db/client.js';

/** One Dive an item was used on, by its local date (the Diver's wall clock at the Dive). */
export interface UsageDive {
  id: string;
  /** YYYY-MM-DD in the Dive's local time. */
  date: string;
  durationSeconds: number;
}

export interface Usage {
  /** Each Dive once, in date order. */
  dives: UsageDive[];
  count: number;
  /** Summed dive time, rounded to six decimals. */
  hours: number;
}

interface UsageRow {
  item_id: string;
  id: string;
  date: string;
  duration_seconds: number;
}

const itemIds = (items: readonly { id: string }[]) => sql.join(items.map((item) => sql`${item.id}::uuid`), sql`, `);

/**
 * The usage of several items in one query, by item id: the Equipment list and the due list read a whole page at once,
 * so their cost does not grow with the number of items (no N+1).
 */
export async function usageMap(tx: Queryable, items: readonly { id: string }[]): Promise<Map<string, Usage>> {
  const usage = new Map<string, Usage>(items.map((item) => [item.id, { dives: [], count: 0, hours: 0 }]));
  if (items.length === 0) return usage;
  const { rows } = await tx.execute(sql`
    with wanted as (
      select id, diver_id, on_every_dive, in_use_from, in_use_until
      from equipment_item
      where id in (${itemIds(items)})
    ),
    dive_local as (
      select d.id, d.diver_id, d.duration_seconds,
             ((d.starts_at at time zone 'UTC') + make_interval(secs => coalesce(d.utc_offset_seconds, 0)))::date as local_date
      from dive d
      where d.deleted_at is null
    ),
    used as (
      select w.id as item_id, l.id, l.duration_seconds, l.local_date
      from dive_local l
      cross join wanted w
      where w.on_every_dive
        and l.diver_id = w.diver_id
        and l.local_date >= w.in_use_from
        and (w.in_use_until is null or l.local_date < w.in_use_until)
        and not exists (
          select 1 from equipment_use u
          where u.dive_id = l.id and u.item_id = w.id and u.use = 'taken_off'
        )
      union
      select w.id as item_id, l.id, l.duration_seconds, l.local_date
      from dive_local l
      join equipment_use u on u.dive_id = l.id
      join wanted w on w.id = u.item_id
      where u.use = 'put_on'
      union
      select w.id as item_id, l.id, l.duration_seconds, l.local_date
      from dive_local l
      join recording r on r.dive_id = l.id and r.deleted_at is null
      join device dv on dv.id = r.device_id and dv.deleted_at is null
      join wanted w on w.id = dv.equipment_item_id
      union
      select w.id as item_id, l.id, l.duration_seconds, l.local_date
      from dive_local l
      join cylinder c on c.dive_id = l.id
      join wanted w on w.id = c.equipment_item_id
    )
    select item_id, id, local_date::text as date, duration_seconds
    from (select distinct item_id, id, local_date, duration_seconds from used) distinct_used
    order by item_id, local_date, id`);
  for (const row of rows as unknown as UsageRow[]) {
    usage.get(row.item_id)?.dives.push({ id: row.id, date: row.date, durationSeconds: row.duration_seconds });
  }
  for (const item of usage.values()) {
    item.count = item.dives.length;
    const seconds = item.dives.reduce((sum, dive) => sum + dive.durationSeconds, 0);
    item.hours = Math.round((seconds / 3600) * 1e6) / 1e6;
  }
  return usage;
}

/** The Dives an item was used on, with their count and summed hours. */
export async function usageOf(tx: Queryable, itemId: string): Promise<Usage> {
  return (await usageMap(tx, [{ id: itemId }])).get(itemId)!;
}
