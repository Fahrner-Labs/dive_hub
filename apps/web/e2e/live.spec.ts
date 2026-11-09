// Live updates in the browser (ADR 0066): a background Import's new Dive arrives through the hint stream, the same row
// arrives through polling when the proxy strips the stream, and a reconnect refetches what is on screen. Plus the
// "changed while editing" notice from the design decision (FAH-37).
import { readFileSync } from 'node:fs';
import { expect, test, type APIRequestContext } from '@playwright/test';
import { E2E_BASE_URL, editElsewhere, resetDive, seededDiveId, setPreferences } from './support.ts';

test.describe.configure({ mode: 'serial' });

const headers = { origin: E2E_BASE_URL };
const created: string[] = [];

/** Uploads a fixture while the page is open; `done` waits for the worker and returns the Dive it made. */
async function uploadFixture(request: APIRequestContext, file: string): Promise<{ url: string; done: () => Promise<string> }> {
  const upload = await request.post('/api/imports', {
    headers,
    multipart: { file: { name: file, mimeType: 'application/octet-stream', buffer: readFileSync(`e2e/fixtures/${file}`) } },
  });
  expect(upload.status()).toBe(202);
  const url = `/api/imports/${(await upload.json() as { id: string }).id}`;
  const done = async () => {
    await expect.poll(async () => (await (await request.get(url)).json()).status, { timeout: 15_000 }).toBe('done');
    const outcome = (await (await request.get(url)).json()) as { outcome: { diveId?: string }[] };
    const id = outcome.outcome.find((entry) => entry.diveId)?.diveId;
    if (!id) throw new Error(`${file} did not make a Dive`);
    created.push(id);
    return id;
  };
  return { url, done };
}

/** Uploads a fixture and waits for its Dive. */
async function importFixture(request: APIRequestContext, file: string): Promise<string> {
  return (await uploadFixture(request, file)).done();
}

test.beforeAll(async ({ request }) => {
  await setPreferences(request, { language: null, units: null });
});

test.afterAll(async ({ request }) => {
  for (const id of created) {
    const dive = await request.get(`/api/dives/${id}`);
    if (!dive.ok()) continue;
    const { version } = await dive.json() as { version: number };
    await request.delete(`/api/dives/${id}`, { headers, data: { version } });
  }
  await resetDive(request);
});

test('a background import brings a new Dive into an untouched logbook, tinted as it arrives', { tag: ['@live', '@dives'] }, async ({ page, request }) => {
  await page.goto('/');
  await expect(page.getByRole('heading', { name: 'Logbook', level: 1 })).toBeVisible();
  // Wait for the loaded first page: it is the snapshot an arrival is compared with. An Import that lands while the
  // logbook is still loading is part of that first page (the list mounts with it), so it never "arrives".
  await expect(page.locator('[data-dive-id]').first()).toBeVisible();
  const before = await page.locator('[data-dive-id]').count();

  const started = Date.now();
  const { done } = await uploadFixture(request, 'live-computer.fit');
  // The hint fires when the import commits, which can be seconds before the worker marks it done; the tint lasts only
  // 2.4 s after the row becomes visible. Watch for the tinted row from before the commit rather than sampling after
  // `done`, where the tint can already have faded (FAH-165).
  const arrivingRow = page.locator('.dive-row.is-arrival');
  await expect(arrivingRow).toHaveCount(1, { timeout: 15_000 });
  const id = await arrivingRow.getAttribute('data-dive-id');
  if (!id) throw new Error('the arriving row has no Dive id');
  // The row must show up without a reload or a scroll.
  await expect(page.locator(`[data-dive-id="${id}"]`)).toBeVisible();
  expect(Date.now() - started).toBeLessThan(6_000);
  expect(await page.locator('[data-dive-id]').count()).toBeGreaterThan(before);
  expect(await done()).toBe(id);
});

test('with the stream blocked, the polling fallback still brings the new Dive in', { tag: ['@live', '@dives'] }, async ({ page, request }) => {
  // A proxy that strips text/event-stream: EventSource fails and the list falls back to asking again.
  await page.route('**/api/live', (route) => route.abort());
  await page.goto('/');
  await expect(page.getByRole('heading', { name: 'Logbook', level: 1 })).toBeVisible();

  const id = await importFixture(request, 'live-computer-2.fit');
  await expect(page.locator(`[data-dive-id="${id}"]`)).toBeVisible({ timeout: 20_000 });
});

test('a Dive changed elsewhere while its edit form is dirty keeps the edits and says so', { tag: ['@live', '@dives'] }, async ({ page, request }) => {
  const id = await seededDiveId(request);
  await page.goto(`/#/dives/${id}`);
  await page.getByRole('button', { name: 'Edit dive' }).click();
  await page.getByRole('textbox', { name: 'Notes' }).fill('typed here and not saved yet');

  await editElsewhere(request, id, 'changed elsewhere while the form was open');

  // The notice is also read into the always-present live region, so match the visible paragraph only.
  await expect(page.locator('p', { hasText: 'This dive changed elsewhere. Your edits are still here.' })).toBeVisible({ timeout: 4_000 });
  await expect(page.getByRole('textbox', { name: 'Notes' })).toHaveValue('typed here and not saved yet');
  await expect(page.getByRole('button', { name: 'Load the new version' })).toBeVisible();
});

test('a reconnect refetches what is on screen', { tag: ['@live', '@dives'] }, async ({ page, request }) => {
  const id = await seededDiveId(request);
  await page.goto(`/#/dives/${id}`);
  await expect(page.getByRole('heading', { level: 1 })).toBeVisible();
  await page.context().setOffline(true);
  // Hints missed while the stream is down are covered by the refetch on reconnect.
  await editElsewhere(request, id, 'written while the page was offline');
  await page.context().setOffline(false);
  await expect(page.getByText('written while the page was offline')).toBeVisible({ timeout: 15_000 });
});

test('no polling fires while the live channel is healthy', { tag: ['@live', '@dives'] }, async ({ page }) => {
  const streamOpen = page.waitForResponse((response) => new URL(response.url()).pathname === '/api/live' && response.status() === 200);
  await page.goto('/');
  await expect(page.getByRole('heading', { name: 'Logbook', level: 1 })).toBeVisible();
  // The first load is done and the channel is open; only now is the page sitting there with live updates.
  await expect(page.locator('[data-dive-id]').first()).toBeVisible();
  await streamOpen;
  // A refetch timer armed before the stream opened can still fire once just after the channel turns healthy
  // (`reschedulePolling` can't cancel the armed timer), so let that window pass uncounted. Polling that keeps
  // going while healthy still fails: a fresh 5 s timer would fire inside the counted 6 s.
  await page.waitForTimeout(5_500);
  // The logbook would ask again every five seconds without a channel (ADR 0066); with one, none of those fire.
  let asked = 0;
  page.on('request', (request) => {
    if (new URL(request.url()).pathname === '/api/dives') asked += 1;
  });
  await page.waitForTimeout(6_000);
  expect(asked).toBe(0);
});

test('two tabs of one browser share one live stream', { tag: ['@live', '@account'] }, async ({ context }) => {
  const first = await context.newPage();
  const second = await context.newPage();
  let streams = 0;
  for (const page of [first, second]) {
    page.on('request', (request) => {
      if (new URL(request.url()).pathname === '/api/live') streams += 1;
    });
  }
  await Promise.all([first.goto('/'), second.goto('/')]);
  await expect(first.getByRole('heading', { name: 'Logbook', level: 1 })).toBeVisible();
  await expect(second.getByRole('heading', { name: 'Logbook', level: 1 })).toBeVisible();
  // The leader holds the one stream; the other tab follows over the BroadcastChannel.
  await first.waitForTimeout(2_000);
  expect(streams).toBe(1);
  await first.close();
  await second.close();
});
