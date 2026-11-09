// Equipment items, their service schedules and a Dive's gear in a real browser (ADR 0034, slice 23): an item is added
// with a schedule the User sets, serviced (the record resets it), edited and deleted; a record resets its schedules;
// gear is put on and taken off a Dive; a Dive's Cylinder names an item; the navigation counts what needs attention and
// the logbook notice is dismissible until the due set changes.
import { expect, test, type APIRequestContext } from '@playwright/test';
import { E2E_BASE_URL, seededDiveId, setPreferences } from './support.ts';

const headers = { origin: E2E_BASE_URL };
type Item = { id: string; name: string; schedules: { id: string; name: string }[] };
type Diver = { id: string; name: string; isOwn: boolean };

const unique = () => Math.random().toString(36).slice(2, 8);
const iso = (date: Date) => date.toISOString().slice(0, 10);
const daysFromNow = (days: number) => {
  const date = new Date();
  date.setUTCDate(date.getUTCDate() + days);
  return iso(date);
};

async function ownDiver(api: APIRequestContext): Promise<Diver> {
  const divers = await (await api.get('/api/divers')).json() as Diver[];
  return divers.find((diver) => diver.isOwn)!;
}

/** Creates an item through the API where the UI is not the subject, and returns it with its schedules. */
async function createItem(api: APIRequestContext, body: object): Promise<Item> {
  const diver = await ownDiver(api);
  const response = await api.post(`/api/divers/${diver.id}/equipment`, { data: body, headers });
  expect(response.status()).toBe(201);
  return await response.json() as Item;
}

/** Removes what a test made: records first, then the use on the seeded Dive, then the item (an unused item deletes). */
async function removeItem(api: APIRequestContext, id: string, diveId: string) {
  const detail = await api.get(`/api/equipment/${id}`);
  if (detail.ok()) {
    for (const record of (await detail.json() as { records: { id: string }[] }).records) {
      await api.delete(`/api/records/${record.id}`, { headers });
    }
  }
  await api.delete(`/api/dives/${diveId}/equipment/${id}`, { headers });
  await api.delete(`/api/equipment/${id}`, { headers });
}

let diveId: string;
const made: string[] = [];
test.beforeEach(async ({ request }) => {
  await setPreferences(request, { language: null, units: null });
  diveId = await seededDiveId(request);
  made.length = 0;
});
test.afterEach(async ({ request }) => {
  for (const id of made) await removeItem(request, id, diveId);
  await setPreferences(request, { language: null, units: null });
});

test('an item is added with a schedule by dives, edited and deleted', { tag: ['@equipment'] }, async ({ page, request }) => {
  const diver = await ownDiver(request);
  const name = `Regulator ${unique()}`;
  await page.goto('/#/equipment');
  await expect(page.getByRole('heading', { level: 1, name: 'Equipment' })).toBeVisible();
  const panel = page.locator('section.panel', { has: page.getByRole('heading', { name: `${diver.name}'s equipment` }) });
  await panel.getByRole('button', { name: `Add item: ${diver.name}` }).click();

  const dialog = page.getByRole('dialog');
  await dialog.getByRole('button', { name: 'Category' }).click();
  await page.getByRole('option', { name: 'Regulator' }).click();
  await dialog.getByRole('textbox', { name: 'Name' }).fill(name);
  await dialog.getByLabel('In use since').fill('2024-01-01');
  // The User sets the interval: dive Hub ships none (ADR 0034).
  await dialog.getByRole('button', { name: 'Add schedule', exact: true }).click();
  const schedule = dialog.getByRole('group', { name: 'Service schedule' });
  await schedule.getByRole('textbox', { name: 'Name' }).fill('Visual inspection');
  await schedule.getByRole('textbox', { name: 'Every this many Dives' }).fill('50');
  await schedule.getByLabel('Last done on').fill('2025-01-01');
  await dialog.getByRole('button', { name: 'Save item' }).click();
  await expect(dialog).toHaveCount(0);

  const card = page.locator('li.equipment-item').filter({ hasText: name });
  await expect(card).toContainText('In use');
  await expect(card).toContainText('every 50 dives from 2025-01-01');
  await expect(card.getByText('OK')).toBeVisible();
  const items = await (await request.get(`/api/divers/${diver.id}/equipment`)).json() as Item[];
  made.push(items.find((item) => item.name === name)!.id);

  await card.getByRole('button', { name: `Item actions: ${name}, ${diver.name}` }).click();
  await page.getByRole('menuitem', { name: 'Edit item' }).click();
  const edit = page.getByRole('dialog');
  await edit.getByRole('textbox', { name: 'Name' }).fill(`${name} II`);
  await edit.getByRole('button', { name: 'Save item' }).click();
  await expect(edit).toHaveCount(0);
  const renamed = page.locator('li.equipment-item').filter({ hasText: `${name} II` });
  await expect(renamed).toContainText('every 50 dives from 2025-01-01');

  await renamed.getByRole('button', { name: `Item actions: ${name} II, ${diver.name}` }).click();
  await page.getByRole('menuitem', { name: 'Delete item' }).click();
  await page.getByRole('dialog').last().getByRole('button', { name: 'Delete item' }).click();
  await expect(page.locator('li.equipment-item').filter({ hasText: name })).toHaveCount(0);
});

test('a service record resets the schedule it names', { tag: ['@equipment'] }, async ({ page, request }) => {
  const item = await createItem(request, {
    category: 'regulator', name: `Serviced ${unique()}`, inUseFrom: '2020-01-01',
    schedules: [{ name: 'Annual service', intervalMonths: 6, startDate: daysFromNow(-200) }],
  });
  made.push(item.id);
  await page.goto('/#/equipment');
  const card = page.locator(`#equipment-item-${item.id}`);
  await expect(card.getByText('Overdue')).toBeVisible();

  await card.getByRole('button', { name: `Record service: ${item.name}` }).click();
  const dialog = page.getByRole('dialog');
  await expect(dialog.getByRole('checkbox', { name: /Annual service/ })).toBeChecked();
  await dialog.getByRole('textbox', { name: 'By whom' }).fill('Dive shop Dahab');
  await dialog.getByRole('button', { name: 'Save record' }).click();
  await expect(dialog).toHaveCount(0);

  // The record is the new anchor: six months from today, so it is ok again.
  await expect(card.getByText('Overdue')).toHaveCount(0);
  await expect(card.getByText('OK')).toBeVisible();
  await card.getByRole('button', { name: `Service history: ${item.name}` }).click();
  await expect(card.getByText('Dive shop Dahab')).toBeVisible();
});

test('an item is put on and taken off a Dive', { tag: ['@equipment', '@dives'] }, async ({ page, request }) => {
  const token = unique();
  const regulator = await createItem(request, { category: 'regulator', name: `Regulator ${token}`, inUseFrom: '2020-01-01' });
  const suit = await createItem(request, { category: 'drysuit', name: `Drysuit ${token}`, inUseFrom: '2020-01-01', onEveryDive: true });
  made.push(regulator.id, suit.id);

  await page.goto(`/#/dives/${diveId}`);
  await expect(page.getByRole('heading', { name: 'Gear', exact: true })).toBeVisible();

  // Nothing is filled automatically: the item is put on this Dive (ADR 0031).
  await page.getByRole('button', { name: 'Put on an item' }).click();
  const dialog = page.getByRole('dialog');
  // A React Aria Select's button is named "<value> <label>": match the Item label at the end, not the bare word (the
  // dialog's submit button is "Put on an item").
  await dialog.getByRole('button', { name: / Item$/ }).click();
  await page.getByRole('option', { name: `${regulator.name} · Regulator` }).click();
  await dialog.getByRole('button', { name: 'Put on an item' }).click();
  await expect(dialog).toHaveCount(0);
  const putOnLine = page.locator('.plain-list li').filter({ hasText: regulator.name });
  await expect(putOnLine).toBeVisible();
  await putOnLine.getByRole('button', { name: `Remove: ${regulator.name}` }).click();
  await expect(page.locator('.plain-list li').filter({ hasText: regulator.name })).toHaveCount(0);

  // An every-Dive item shows on the Dive until it is taken off, and comes back.
  await expect(page.getByRole('button', { name: `Take off: ${suit.name}` })).toBeVisible();
  await page.getByRole('button', { name: `Take off: ${suit.name}` }).click();
  await expect(page.getByRole('button', { name: `Put back on: ${suit.name}` })).toBeVisible();
  await page.getByRole('button', { name: `Put back on: ${suit.name}` }).click();
  await expect(page.getByRole('button', { name: `Take off: ${suit.name}` })).toBeVisible();
});

test("a Dive's Cylinder names a cylinder item", { tag: ['@equipment', '@dives'] }, async ({ page, request }) => {
  const item = await createItem(request, { category: 'cylinder', name: `12 L steel ${unique()}`, inUseFrom: '2020-01-01' });
  made.push(item.id);
  const clear = async () => {
    const dive = await (await request.get(`/api/dives/${diveId}`)).json() as { version: number };
    expect((await request.patch(`/api/dives/${diveId}`, { data: { version: dive.version, cylinders: [] }, headers })).status()).toBe(200);
  };
  await clear();
  try {
    await page.goto(`/#/dives/${diveId}`);
    await page.getByRole('button', { name: 'Add cylinders' }).click();
    const cylinder = page.getByRole('group', { name: 'Cylinder 1' });
    await cylinder.getByRole('button', { name: 'Cylinder item' }).click();
    await page.getByRole('option', { name: item.name }).click();
    // Its first use fills nothing; the facts are typed (ADR 0034 D6).
    await cylinder.getByRole('textbox', { name: 'Volume' }).fill('12');
    await cylinder.getByRole('textbox', { name: /Working pressure/ }).fill('232');
    await cylinder.getByRole('button', { name: 'Material' }).click();
    await page.getByRole('option', { name: 'Steel' }).click();
    // The Cylinder fields live in the Dive's edit form, whose save is "Save dive" (not "Save").
    await page.getByRole('button', { name: 'Save dive' }).click();
    await expect(page.locator('.cylinder-list li').filter({ hasText: '12 L, Steel, 232 bar' })).toBeVisible();
    const saved = await (await request.get(`/api/dives/${diveId}`)).json() as { cylinders: { equipmentItemId: string }[] };
    expect(saved.cylinders).toMatchObject([{ equipmentItemId: item.id }]);
  } finally {
    await clear();
  }
});

test('the navigation counts what needs attention', { tag: ['@equipment'] }, async ({ page, request }) => {
  const item = await createItem(request, {
    category: 'regulator', name: `Nav ${unique()}`, inUseFrom: '2020-01-01',
    schedules: [{ name: 'Annual service', intervalMonths: 1, startDate: daysFromNow(-45) }],
  });
  made.push(item.id);
  const due = await (await request.get('/api/equipment/due')).json() as { itemId: string }[];
  expect(due.some((entry) => entry.itemId === item.id)).toBe(true);

  await page.goto('/');
  const link = page.getByRole('navigation', { name: 'Main' }).getByRole('link', { name: /Equipment/ });
  await expect(link.locator('.count')).toBeVisible();
  await expect(link.locator('.count [aria-hidden="true"]')).toHaveText(String(due.length));
  await expect(link).toContainText(due.length === 1 ? '1 equipment service due' : `${due.length} equipment services due`);

  await link.click();
  await expect(page.getByRole('heading', { level: 1, name: 'Equipment' })).toBeVisible();
});

test('the logbook notice is dismissed until the due set changes', { tag: ['@equipment'] }, async ({ page, request }) => {
  const first = await createItem(request, {
    category: 'regulator', name: `Notice ${unique()}`, inUseFrom: '2020-01-01',
    schedules: [{ name: 'Annual service', intervalMonths: 1, startDate: daysFromNow(-45) }],
  });
  made.push(first.id);
  await page.goto('/');
  const notice = page.locator('.notice-info').filter({ hasText: 'Open equipment' });
  await expect(notice).toBeVisible();

  await notice.getByRole('button', { name: 'Dismiss' }).click();
  await expect(page.locator('.notice-info').filter({ hasText: 'Open equipment' })).toHaveCount(0);
  await page.reload();
  await expect(page.locator('.notice-info').filter({ hasText: 'Open equipment' })).toHaveCount(0);

  // A schedule that becomes due is a new digest: the notice comes back.
  const second = await createItem(request, {
    category: 'regulator', name: `Notice again ${unique()}`, inUseFrom: '2020-01-01',
    schedules: [{ name: 'Annual service', intervalMonths: 1, startDate: daysFromNow(-45) }],
  });
  made.push(second.id);
  await page.reload();
  await expect(page.locator('.notice-info').filter({ hasText: 'Open equipment' })).toBeVisible();
});
