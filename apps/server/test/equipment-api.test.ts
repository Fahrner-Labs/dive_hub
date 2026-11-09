// Equipment items over HTTP (ADR 0034): CRUD with versions, the due list, a Dive's gear, visibility and the cylinder
// naming validation.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { desc, eq } from 'drizzle-orm';
import { dive, revision } from '../src/db/schema.js';
import { BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase } from './support.js';

type Schedule = {
  id: string; itemId: string; name: string; intervalMonths: number | null; intervalDives: number | null;
  intervalHours: number | null; startDate: string; active: boolean; version: number;
  due: { state: string; reason: string | null } | null;
};
type Item = {
  id: string; diverId: string; category: string; name: string; equipmentStatus: string; onEveryDive: boolean;
  inUseFrom: string; inUseUntil: string | null; version: number; deviceId: string | null; schedules: Schedule[];
};
type Detail = Item & {
  usage: { diveCount: number; hours: number; lastUsedOn: string | null };
  records: { id: string; scheduleIds: string[]; version: number; date: string }[];
  lastCylinder: { volumeL: number | null } | null;
};

describe.skipIf(!(await databaseReachable()))('Equipment over HTTP', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let other: string;
  let timDiver: string;
  let otherDiver: string;

  const inject = (method: 'GET' | 'PATCH' | 'POST' | 'DELETE', url: string, cookie: string, payload?: object) =>
    ctx.app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload !== undefined && { payload }) });

  const createItem = async (body: object, cookie = tim, diver = timDiver) =>
    inject('POST', `/api/divers/${diver}/equipment`, cookie, {
      category: 'regulator', name: 'Regulator set', inUseFrom: '2026-01-01', ...body,
    });

  const addDive = async (diverId: string, day = '2026-02-01') => {
    const [made] = await t.db.insert(dive).values({
      diverId, startsAt: new Date(`${day}T10:00:00Z`), utcOffsetSeconds: 0, durationSeconds: 3600,
    }).returning();
    return made!.id;
  };

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'tim@example.com');
    await createUser(ctx.auth, 'other@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
    other = await signIn(ctx.app, 'other@example.com');
    const own = async (cookie: string) => ((await inject('GET', '/api/divers', cookie)).json() as { id: string; isOwn: boolean }[]).find((d) => d.isOwn)!.id;
    timDiver = await own(tim);
    otherDiver = await own(other);
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('creates an item with its schedules and computes due', async () => {
    const response = await createItem({ schedules: [{ name: 'Annual', intervalMonths: 12, startDate: '2025-01-01' }] });
    expect(response.statusCode).toBe(201);
    const item = response.json() as Detail;
    expect(item).toMatchObject({ category: 'regulator', equipmentStatus: 'in_use', onEveryDive: false, version: 1 });
    expect(item.schedules[0]!.name).toBe('Annual');
    expect(item.schedules[0]!.due?.state).toBe('overdue');
    expect(item.schedules[0]!.due?.reason).toBe('due 2026-01');

    const list = (await inject('GET', `/api/divers/${timDiver}/equipment`, tim)).json() as Item[];
    expect(list.find((i) => i.id === item.id)?.schedules[0]!.due?.state).toBe('overdue');
  });

  it('refuses a schedule without any interval', async () => {
    const response = await createItem({ schedules: [{ name: 'Nothing', startDate: '2025-01-01' }] });
    expect(response.statusCode).toBe(400);
    expect(response.json()).toMatchObject({ code: 'schedule_interval_required' });
  });

  it('changes an item only from the version sent, and maintains the in-use window', async () => {
    const item = (await createItem({})).json() as Detail;
    const stale = await inject('PATCH', `/api/equipment/${item.id}`, tim, { version: item.version + 1, name: 'Nope' });
    expect(stale.statusCode).toBe(409);
    expect(stale.json()).toMatchObject({ code: 'equipment_changed' });

    const retired = (await inject('PATCH', `/api/equipment/${item.id}`, tim, { version: item.version, equipmentStatus: 'retired' })).json() as Detail;
    expect(retired).toMatchObject({ equipmentStatus: 'retired', version: item.version + 1 });
    expect(retired.inUseUntil).not.toBeNull();

    const back = (await inject('PATCH', `/api/equipment/${item.id}`, tim, { version: retired.version, equipmentStatus: 'in_use' })).json() as Detail;
    expect(back).toMatchObject({ equipmentStatus: 'in_use', inUseUntil: null });
  });

  it('records a service that resets its schedule, and keeps the record when the schedule goes', async () => {
    const item = (await createItem({ schedules: [{ name: 'Visual', intervalMonths: 12, startDate: '2025-01-01' }] })).json() as Detail;
    const schedule = item.schedules[0]!;
    const record = await inject('POST', `/api/equipment/${item.id}/records`, tim, {
      date: '2026-01-10', byWhom: 'Dive shop', scheduleIds: [schedule.id], costAmount: 80, costCurrency: 'EUR',
    });
    expect(record.statusCode).toBe(201);
    expect(record.json()).toMatchObject({ costAmount: 80, costCurrency: 'EUR', scheduleIds: [schedule.id] });

    const after = (await inject('GET', `/api/equipment/${item.id}`, tim)).json() as Detail;
    expect(after.records).toHaveLength(1);
    expect(after.records[0]!.scheduleIds).toEqual([schedule.id]);

    const staleRecord = await inject('PATCH', `/api/records/${after.records[0]!.id}`, tim, { version: 99, date: '2026-01-11', scheduleIds: [schedule.id] });
    expect(staleRecord.statusCode).toBe(409);
    expect(staleRecord.json()).toMatchObject({ code: 'equipment_changed' });

    const staleSchedule = await inject('PATCH', `/api/schedules/${schedule.id}`, tim, {
      version: 99, name: 'Visual', intervalMonths: 12, startDate: '2025-01-01',
    });
    expect(staleSchedule.statusCode).toBe(409);

    await inject('DELETE', `/api/schedules/${schedule.id}`, tim);
    const kept = (await inject('GET', `/api/equipment/${item.id}`, tim)).json() as Detail;
    expect(kept.records).toHaveLength(1);
    expect(kept.schedules).toHaveLength(0);
    // A record needs a schedule that exists; with none left, the item counts as used and cannot be deleted.
    expect((await inject('DELETE', `/api/equipment/${item.id}`, tim)).statusCode).toBe(409);
  });

  it('deletes an unused item and refuses a used one', async () => {
    const unused = (await createItem({ name: 'Unused' })).json() as Detail;
    expect((await inject('DELETE', `/api/equipment/${unused.id}`, tim)).statusCode).toBe(204);
    expect((await inject('GET', `/api/equipment/${unused.id}`, tim)).statusCode).toBe(404);
    const [deleted] = await t.db.select({ cause: revision.cause }).from(revision)
      .where(eq(revision.entityId, unused.id)).orderBy(desc(revision.at));
    expect(deleted?.cause).toBe('delete');

    const used = (await createItem({ name: 'Used' })).json() as Detail;
    const diveId = await addDive(timDiver);
    await inject('POST', `/api/dives/${diveId}/equipment`, tim, { itemId: used.id, use: 'put_on' });
    const response = await inject('DELETE', `/api/equipment/${used.id}`, tim);
    expect(response.statusCode).toBe(409);
    expect(response.json()).toMatchObject({ code: 'equipment_in_use' });
  });

  it('keeps a Diver with an item, and clears a removed item so the Diver can go', async () => {
    const diver = (await inject('POST', '/api/divers', tim, { name: 'Diver with gear' })).json() as { id: string };
    const item = (await createItem({ name: 'Spare mask' }, tim, diver.id)).json() as Detail;
    expect((await inject('DELETE', `/api/divers/${diver.id}`, tim)).json()).toMatchObject({ code: 'diver_not_empty' });
    expect((await inject('DELETE', `/api/equipment/${item.id}`, tim)).statusCode).toBe(204);
    expect((await inject('DELETE', `/api/divers/${diver.id}`, tim)).statusCode).toBe(204);
  });

  it('keeps another User\'s Diver and items away, as not found', async () => {
    const item = (await createItem({ name: 'Private' })).json() as Detail;
    expect((await inject('GET', `/api/equipment/${item.id}`, other)).statusCode).toBe(404);
    expect((await inject('GET', `/api/divers/${timDiver}/equipment`, other)).statusCode).toBe(404);
    expect((await inject('POST', `/api/divers/${timDiver}/equipment`, other, { category: 'light', name: 'X', inUseFrom: '2026-01-01' })).statusCode).toBe(404);
  });

  it('lists due schedules across the Divers, worst first', async () => {
    await createItem({ name: 'Overdue', schedules: [{ name: 'Late', intervalMonths: 1, startDate: '2025-01-01' }] });
    const due = (await inject('GET', '/api/equipment/due', tim)).json() as { due: { state: string }; itemName: string }[];
    expect(due.length).toBeGreaterThan(0);
    expect(due[0]!.due.state).toBe('overdue');
  });

  it('rejects a use that contradicts the item\'s mode, and shows the Dive\'s gear', async () => {
    const everyDive = (await createItem({ name: 'Always on', onEveryDive: true, inUseFrom: '2026-01-01' })).json() as Detail;
    const item = (await createItem({ name: 'Occasional' })).json() as Detail;
    const diveId = await addDive(timDiver, '2026-02-03');
    expect((await inject('POST', `/api/dives/${diveId}/equipment`, tim, { itemId: everyDive.id, use: 'put_on' })).statusCode).toBe(409);
    expect((await inject('POST', `/api/dives/${diveId}/equipment`, tim, { itemId: item.id, use: 'taken_off' })).statusCode).toBe(409);
    expect((await inject('POST', `/api/dives/${diveId}/equipment`, tim, { itemId: everyDive.id, use: 'taken_off' })).statusCode).toBe(204);
    expect((await inject('POST', `/api/dives/${diveId}/equipment`, tim, { itemId: item.id, use: 'put_on' })).statusCode).toBe(204);

    const gear = (await inject('GET', `/api/dives/${diveId}/equipment`, tim)).json() as {
      derived: { id: string; sources: string[] }[]; putOn: { id: string }[]; takenOff: { id: string }[];
    };
    expect(gear.takenOff.map((i) => i.id)).toContain(everyDive.id);
    expect(gear.derived.find((i) => i.id === everyDive.id)).toBeUndefined();
    expect(gear.putOn.map((i) => i.id)).toContain(item.id);

    const sameAsLast = await inject('GET', `/api/dives/${await addDive(timDiver, '2026-02-04')}/same-as-last`, tim);
    expect((sameAsLast.json() as { gear: { id: string }[] }).gear.map((i) => i.id)).toContain(item.id);
  });

  it('lets a Dive\'s Cylinder name only a cylinder item of its Diver', async () => {
    const cylinderItem = (await createItem({ name: '12 L steel', category: 'cylinder' })).json() as Detail;
    const notACylinder = (await createItem({ name: 'Torch', category: 'light' })).json() as Detail;
    const foreign = (await createItem({ name: 'Other cylinder', category: 'cylinder' }, other, otherDiver)).json() as Detail;
    const diveId = await addDive(timDiver, '2026-02-05');
    const version = async () => ((await inject('GET', `/api/dives/${diveId}`, tim)).json() as { version: number }).version;

    const accepted = await inject('PATCH', `/api/dives/${diveId}`, tim, { version: await version(), cylinders: [{ equipmentItemId: cylinderItem.id, volumeL: 12 }] });
    expect(accepted.statusCode).toBe(200);
    expect((accepted.json() as { cylinders: { equipmentItemId: string }[] }).cylinders[0]!.equipmentItemId).toBe(cylinderItem.id);

    const wrongCategory = await inject('PATCH', `/api/dives/${diveId}`, tim, { version: await version(), cylinders: [{ equipmentItemId: notACylinder.id }] });
    expect(wrongCategory.statusCode).toBe(400);
    expect(wrongCategory.json()).toMatchObject({ code: 'equipment_item_not_cylinder' });
    const wrongDiver = await inject('PATCH', `/api/dives/${diveId}`, tim, { version: await version(), cylinders: [{ equipmentItemId: foreign.id }] });
    expect(wrongDiver.statusCode).toBe(400);
    expect(wrongDiver.json()).toMatchObject({ code: 'equipment_item_not_cylinder' });
  });
});
