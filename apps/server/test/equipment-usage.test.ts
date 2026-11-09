// An item's usage (ADR 0034, plan D3): the union of four sources over the Diver's never-deleted Dives. A Dive counts
// once, however many sources name it, and its duration feeds the item's hours.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { eq } from 'drizzle-orm';
import { user } from '../src/db/auth-schema.js';
import { cylinder, device, dive, diver, equipmentItem, equipmentUse, original, recording } from '../src/db/schema.js';
import { usageMap, usageOf } from '../src/equipment/usage.js';
import { createTestDatabase, databaseReachable, type TestDatabase } from './support.js';

const ids = async (usage: { dives: { id: string }[] }) => usage.dives.map((d) => d.id).sort();

describe.skipIf(!(await databaseReachable()))('equipment usage (PostgreSQL)', () => {
  let t: TestDatabase;
  let tim: string;
  let ana: string;
  let userId: string;

  beforeAll(async () => {
    t = await createTestDatabase();
    [{ id: tim }] = await t.db.insert(diver).values({ name: 'Tim' }).returning({ id: diver.id }) as [{ id: string }];
    [{ id: userId }] = await t.db.insert(user).values({ name: 'Tim', email: 'equipment@example.com' }).returning({ id: user.id }) as [{ id: string }];
    [{ id: ana }] = await t.db.insert(diver).values({ name: 'Ana' }).returning({ id: diver.id }) as [{ id: string }];
  });
  afterAll(async () => {
    await t?.drop();
  });

  const makeDive = async (
    diverId: string, localDate: string, hours = 1,
    options: { at?: string; offsetSeconds?: number; deleted?: boolean } = {},
  ) => (await t.db.insert(dive).values({
    diverId,
    startsAt: new Date(options.at ?? `${localDate}T09:00:00Z`),
    utcOffsetSeconds: options.offsetSeconds ?? 0,
    durationSeconds: hours * 3600,
    ...(options.deleted && { deletedAt: new Date() }),
  }).returning({ id: dive.id }))[0]!.id;

  const makeItem = async (over: Partial<typeof equipmentItem.$inferInsert> = {}) => (await t.db.insert(equipmentItem).values({
    diverId: tim, category: 'regulator', name: 'Set', onEveryDive: false, inUseFrom: '2026-01-01', ...over,
  }).returning({ id: equipmentItem.id }))[0]!.id;

  it('an every-Dive item counts its window, edges included and excluded, minus taken-off Dives', async () => {
    const item = await makeItem({ onEveryDive: true, inUseFrom: '2026-02-01', inUseUntil: '2026-02-10' });
    const before = await makeDive(tim, '2026-01-31');
    const first = await makeDive(tim, '2026-02-01');
    const last = await makeDive(tim, '2026-02-09');
    const after = await makeDive(tim, '2026-02-10');
    const holiday = await makeDive(tim, '2026-02-05');
    // Stored the evening before in UTC, but the Dive's local date is 1 February: the window is in local dates.
    const localFirst = await makeDive(tim, '2026-02-01', 1, { at: '2026-01-31T23:30:00Z', offsetSeconds: 2 * 3600 });
    await t.db.insert(equipmentUse).values({ diveId: holiday, itemId: item, use: 'taken_off' });

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual([first, last, localFirst].sort());
    expect(usage.dives.map((d) => d.id)).not.toContain(before);
    expect(usage.dives.map((d) => d.id)).not.toContain(after);
    expect(usage.count).toBe(3);
    expect(usage.hours).toBe(3);
    expect(usage.dives.find((d) => d.id === localFirst)?.date).toBe('2026-02-01');
  });

  it('an every-Dive item without an end date keeps counting', async () => {
    const item = await makeItem({ onEveryDive: true, inUseFrom: '2026-03-01', inUseUntil: null });
    const first = await makeDive(tim, '2026-03-01');
    const later = await makeDive(tim, '2026-12-31');
    const before = await makeDive(tim, '2026-02-28');

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual([first, later].sort());
    expect(usage.dives.map((d) => d.id)).not.toContain(before);
  });

  it('another item counts only the Dives it was put on', async () => {
    const item = await makeItem();
    const putOn = [await makeDive(tim, '2026-04-01'), await makeDive(tim, '2026-04-02')];
    await makeDive(tim, '2026-04-03');
    const takenOff = await makeDive(tim, '2026-04-04');
    await t.db.insert(equipmentUse).values([
      { diveId: putOn[0]!, itemId: item, use: 'put_on' },
      { diveId: putOn[1]!, itemId: item, use: 'put_on' },
      { diveId: takenOff, itemId: item, use: 'taken_off' },
    ]);

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual(putOn.sort());
    expect(usage.count).toBe(2);
    expect(usage.hours).toBe(2);
  });

  it('a Device counts the Dives of its Recordings', async () => {
    const item = await makeItem({ category: 'dive_computer', name: 'Computer' });
    const [computer] = await t.db.insert(device).values({
      diverId: tim, manufacturer: 'Shearwater', product: 'Perdix', serialNumber: 'PD-1', equipmentItemId: item,
    }).returning({ id: device.id });
    const recorded = await makeDive(tim, '2026-05-01');
    await makeDive(tim, '2026-05-02');
    const deletedRecordingDive = await makeDive(tim, '2026-05-03');
    const record = async (diveId: string, key: string, deleted = false) => {
      const [file] = await t.db.insert(original).values({
        userId, sha256: key, mediaType: 'application/octet-stream', sizeBytes: 1, storageKey: `originals/${key}`,
      }).returning({ id: original.id });
      await t.db.insert(recording).values({
        diveId, deviceId: computer!.id, originalId: file!.id, recordingKey: key, parser: 'fit', parserVersion: '1',
        startsAt: new Date('2026-05-01T09:00:00Z'), durationSeconds: 3600, ...(deleted && { deletedAt: new Date() }),
      });
    };
    await record(recorded, 'rec-1');
    await record(deletedRecordingDive, 'rec-2', true);

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual([recorded]);
  });

  it('a reassigned Device counts only the new Diver\'s Dives', async () => {
    const item = await makeItem({ category: 'dive_computer', name: 'Moved' });
    const [computer] = await t.db.insert(device).values({
      diverId: tim, manufacturer: 'Shearwater', serialNumber: 'MOVE-1', equipmentItemId: item,
    }).returning({ id: device.id });
    const record = async (diveId: string, key: string) => {
      const [file] = await t.db.insert(original).values({
        userId, sha256: key, mediaType: 'application/octet-stream', sizeBytes: 1, storageKey: `originals/${key}`,
      }).returning({ id: original.id });
      await t.db.insert(recording).values({
        diveId, deviceId: computer!.id, originalId: file!.id, recordingKey: key, parser: 'fit', parserVersion: '1',
        startsAt: new Date('2026-10-01T09:00:00Z'), durationSeconds: 3600,
      });
    };
    const oldDive = await makeDive(tim, '2026-10-01');
    const newDive = await makeDive(ana, '2026-10-02');
    await record(oldDive, 'moved-old');
    await record(newDive, 'moved-new');
    await t.db.insert(equipmentUse).values({ diveId: oldDive, itemId: item, use: 'put_on' });
    const timItem = await makeItem({ name: 'Tim item' });
    expect(await ids(await usageOf(t.db, item))).toEqual([oldDive]);

    await t.db.transaction(async (tx) => {
      await tx.update(device).set({ diverId: ana }).where(eq(device.id, computer!.id));
      await tx.update(equipmentItem).set({ diverId: ana }).where(eq(equipmentItem.id, item));
    });
    expect(await ids(await usageOf(t.db, item))).toEqual([newDive]);
    // With an item of the old Diver on the same page, the old Diver's Dive still does not count.
    const page = await usageMap(t.db, [{ id: item }, { id: timItem }]);
    expect(await ids(page.get(item)!)).toEqual([newDive]);
  });

  it('a Cylinder naming the item counts its Dive', async () => {
    const item = await makeItem({ category: 'cylinder', name: 'Steel 12' });
    const named = [await makeDive(tim, '2026-06-01'), await makeDive(tim, '2026-06-02')];
    await makeDive(tim, '2026-06-03');
    await t.db.insert(cylinder).values([
      { diveId: named[0]!, position: 1, equipmentItemId: item },
      { diveId: named[1]!, position: 1, equipmentItemId: item },
    ]);

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual(named.sort());
    expect(usage.count).toBe(2);
  });

  it('counts a Dive once when several sources name it', async () => {
    const item = await makeItem({ category: 'cylinder', name: 'Shared' });
    const shared = await makeDive(tim, '2026-07-01', 1.5);
    await t.db.insert(equipmentUse).values({ diveId: shared, itemId: item, use: 'put_on' });
    await t.db.insert(cylinder).values({ diveId: shared, position: 1, equipmentItemId: item });

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual([shared]);
    expect(usage.count).toBe(1);
    expect(usage.hours).toBe(1.5);
  });

  it('a deleted Dive counts nowhere, though all four sources name it', async () => {
    const item = await makeItem({ category: 'cylinder', name: 'Deleted', onEveryDive: true, inUseFrom: '2026-08-01', inUseUntil: '2026-08-31' });
    const [computer] = await t.db.insert(device).values({
      diverId: tim, manufacturer: 'Suunto', serialNumber: 'SU-1', equipmentItemId: item,
    }).returning({ id: device.id });
    const gone = await makeDive(tim, '2026-08-01', 1, { deleted: true });
    await t.db.insert(equipmentUse).values({ diveId: gone, itemId: item, use: 'put_on' });
    await t.db.insert(cylinder).values({ diveId: gone, position: 1, equipmentItemId: item });
    const [file] = await t.db.insert(original).values({
      userId, sha256: 'rec-gone', mediaType: 'application/octet-stream', sizeBytes: 1, storageKey: 'originals/rec-gone',
    }).returning({ id: original.id });
    await t.db.insert(recording).values({
      diveId: gone, deviceId: computer!.id, originalId: file!.id, recordingKey: 'rec-gone', parser: 'fit', parserVersion: '1',
      startsAt: new Date('2026-08-01T09:00:00Z'), durationSeconds: 3600,
    });
    const live = await makeDive(tim, '2026-08-02');

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual([live]);
    expect(usage.count).toBe(1);
  });

  it('counts the dives of the item\'s own Diver, not another Diver\'s', async () => {
    const item = await makeItem({ onEveryDive: true, inUseFrom: '2026-09-01', inUseUntil: '2026-09-30' });
    const own = await makeDive(tim, '2026-09-01');
    await makeDive(ana, '2026-09-01');

    const usage = await usageOf(t.db, item);
    expect(await ids(usage)).toEqual([own]);
  });
});
