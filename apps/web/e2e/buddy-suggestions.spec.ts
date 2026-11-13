// Buddy suggestions in a real browser (ADR 0070): a Participant whose Diver another User keeps raises a suggestion in
// that User's Review inbox, showing only the shared facts. Accepting either creates a Dive for the invited Diver from
// those facts or links an existing overlapping Dive into a Joint dive; declining creates nothing. The second User is
// invited like any other (ADR 0012), and the fixture e2e/fixtures/buddy-computer.fit is the overlapping Dive
// (apps/server/test/fixtures/write-buddy-fixture.ts).
import { readFileSync } from 'node:fs';
import { expect, request, test, type APIRequestContext, type Browser, type Page } from '@playwright/test';
import { E2E_BASE_URL, clearParticipants, headers, resetDive, setBuddies, setPreferences, uniqueWord } from './support.ts';

const OTHER = { email: 'bianca@example.com', name: 'Bianca', password: 'a long enough test password' };
/** The seeded Dive's start (FIT fixture), where the other User's overlapping Dive is. */
const SOURCE_START = '2026-01-15T09:00';

type Dive = {
  id: string;
  version: number;
  diverId: string;
  values: {
    startsAt: { at: string; utcOffsetSeconds: number | null };
    durationSeconds: number;
    maxDepthM: number | null;
    avgDepthM: number | null;
  };
  overrides: string[];
  notes: string | null;
  site: { id: string; name: string } | null;
  recordings: unknown[];
  participants: { name: string }[];
};

let other: APIRequestContext;
let otherSession: Awaited<ReturnType<APIRequestContext['storageState']>>;
let otherOwnDiver: string;

test.beforeAll(async ({ request }) => {
  await setPreferences(request, { language: null, units: null });
  await ensureOtherUser(request);
  other = await signInOther();
  otherSession = await other.storageState();
  const me = await (await other.get('/api/me')).json() as { ownDiver: { id: string } };
  otherOwnDiver = me.ownDiver.id;
});

test.afterAll(async () => {
  await other?.dispose();
});

test.afterEach(async ({ request }) => {
  // The suggestion follows the Participants: taking them off cancels a pending one for the tests that follow.
  const dive = await resetDive(request);
  await clearParticipants(request, dive.id);
  const source = await (await request.get(`/api/dives/${dive.id}`)).json() as Dive;
  if (source.site) await request.patch(`/api/dives/${dive.id}`, { data: { version: source.version, siteId: null }, headers });
});

test('a Participant another User keeps raises a suggestion in that User\'s Review inbox', { tag: ['@dives', '@divers'] }, async ({ page, request, browser }) => {
  const dive = await resetSource(request);
  const diverName = `Buddy ${uniqueWord()}`;
  await otherManagedDiver(diverName);

  // Erika adds the other User's Diver from the dive's own "Add buddies", as she would any Diver of the instance.
  await page.goto(`/#/dives/${dive.id}`);
  await page.getByRole('group', { name: 'Add details' }).getByRole('button', { name: 'Add buddies' }).click();
  const dialog = page.getByRole('dialog');
  await dialog.getByRole('searchbox', { name: 'Find a diver' }).fill(diverName);
  await dialog.getByRole('option', { name: new RegExp(diverName) }).click();
  await expect(dialog).toBeHidden();
  await expect(page.getByRole('group', { name: 'Buddies and guides' })).toContainText(diverName);

  const { page: theirs, close } = await openAsOther(browser);
  try {
    await theirs.goto('/#/review');
    const panel = suggestionPanel(theirs);
    await expect(panel).toContainText(`Erika shared this dive with ${diverName}.`);
    await expect(panel.getByText('Suggested dive')).toBeVisible();
    await expect(panel.getByRole('button', { name: /^Accept/ })).toBeVisible();
    await expect(panel.getByRole('button', { name: /^Decline/ })).toBeVisible();

    // The suggestion is the invited Diver's: Erika's own inbox does not show it.
    await page.goto('/#/review');
    await expect(page.getByText(`Erika shared this dive with ${diverName}.`)).toHaveCount(0);
  } finally {
    await close();
  }
});

test('a Participant the User keeps raises a suggestion in their own Review inbox', { tag: ['@dives', '@divers'] }, async ({ page, request }) => {
  const dive = await resetSource(request);
  const diverName = `Managed ${uniqueWord()}`;
  const created = await request.post('/api/divers', { data: { name: diverName }, headers });
  if (!created.ok()) throw new Error(`creating the Diver failed: ${created.status()} ${await created.text()}`);
  const diver = await created.json() as { id: string };
  await setBuddies(request, dive.id, [diver.id]);

  await page.goto('/#/review');
  await expect(suggestionPanel(page)).toContainText(`Erika shared this dive with ${diverName}.`);
});

test('accepting "Create a new dive" copies the shared facts into the invited Diver\'s logbook', { tag: ['@dives'] }, async ({ request, browser }) => {
  const site = await buddySite(request);
  const dive = await resetSource(request);
  const source = await (await request.get(`/api/dives/${dive.id}`)).json() as Dive;
  await request.patch(`/api/dives/${dive.id}`, {
    data: { version: source.version, notes: 'Only on the source dive', siteId: site.id }, headers,
  });
  const diverName = `Copy ${uniqueWord()}`;
  const diverId = await otherManagedDiver(diverName);
  await setBuddies(request, dive.id, [diverId]);

  const { page, close } = await openAsOther(browser);
  try {
    await page.goto('/#/review');
    await suggestionPanel(page).getByRole('button', { name: /^Accept/ }).click();
    const dialog = page.getByRole('dialog', { name: 'Accept this dive' });
    // The invited Diver has no Dive at this time: creating one is the only way, and the dialog says so.
    await expect(dialog).toContainText('no dive at this time');
    const answered = page.waitForResponse((r) => r.request().method() === 'POST' && r.url().endsWith('/accept'));
    await dialog.getByRole('button', { name: 'Create a new dive' }).click();
    const accepted = await (await answered).json() as { diveId: string; jointDiveId: string };
    expect(accepted.diveId).not.toBe(dive.id);
    expect(accepted.jointDiveId).toBeTruthy();
    // The contract opens the Dive that was added to the User's logbook.
    await expect(page).toHaveURL(new RegExp(`#/dives/${accepted.diveId}$`));
    await expect(page.getByRole('heading', { level: 1 })).toBeVisible();
    await expect(page.locator('#main')).toContainText(site.name);

    const copy = await (await other.get(`/api/dives/${accepted.diveId}`)).json() as Dive;
    const sourceNow = await (await request.get(`/api/dives/${dive.id}`)).json() as Dive;
    expect(copy.diverId).toBe(diverId);
    // Shared facts only: no Recording, notes, gear or own overrides; depths are plain values.
    expect(copy.recordings).toEqual([]);
    expect(copy.notes).toBeNull();
    expect(copy.overrides).toEqual([]);
    expect(copy.site).toEqual(sourceNow.site);
    expect(copy.values.startsAt).toEqual(sourceNow.values.startsAt);
    expect(copy.values.durationSeconds).toBe(sourceNow.values.durationSeconds);
    expect(copy.values.maxDepthM).toBe(sourceNow.values.maxDepthM);
    expect(copy.values.avgDepthM).toBe(sourceNow.values.avgDepthM);
    // The people on the source Dive become Participants of the copy; its own Diver is not listed twice.
    expect(copy.participants.map((p) => p.name)).toEqual(['Erika']);
  } finally {
    await close();
  }
});

test('accepting "Link to your dive" joins the overlapping dives without copying or merging them', { tag: ['@dives'] }, async ({ request, browser }) => {
  const existing = await otherOverlappingDive();
  const notes = `Mine ${uniqueWord()}`;
  const mine = await (await other.get(`/api/dives/${existing}`)).json() as Dive;
  await other.patch(`/api/dives/${existing}`, { data: { version: mine.version, notes }, headers });
  const dive = await resetSource(request);
  await setBuddies(request, dive.id, [otherOwnDiver]);

  const { page, close } = await openAsOther(browser);
  try {
    await page.goto('/#/review');
    await suggestionPanel(page).getByRole('button', { name: /^Accept/ }).click();
    const dialog = page.getByRole('dialog', { name: 'Accept this dive' });
    // The other User's own overlapping Dive is offered to link.
    await expect(dialog).toContainText('You already have a dive at this time');
    const answered = page.waitForResponse((r) => r.request().method() === 'POST' && r.url().endsWith('/accept'));
    await dialog.getByRole('button', { name: /Link to your dive/ }).click();
    const accepted = await (await answered).json() as { diveId: string; jointDiveId: string };
    expect(accepted.diveId).toBe(existing);
    expect(accepted.jointDiveId).toBeTruthy();
    await expect(page).toHaveURL(new RegExp(`#/dives/${existing}$`));
    await expect(page.locator('#main')).toContainText(notes);

    // Linking copies and merges nothing: the Dive keeps its Recording, its notes and its own Participants.
    const linked = await (await other.get(`/api/dives/${existing}`)).json() as Dive;
    expect(linked.recordings).toHaveLength(1);
    expect(linked.notes).toBe(notes);
    expect(linked.participants).toEqual([]);
  } finally {
    await close();
  }
});

test('declining resolves the suggestion, creates nothing and keeps the Participant', { tag: ['@dives'] }, async ({ request, browser }) => {
  const dive = await resetSource(request);
  const diverName = `Decline ${uniqueWord()}`;
  const diverId = await otherManagedDiver(diverName);
  await setBuddies(request, dive.id, [diverId]);

  const { page, close } = await openAsOther(browser);
  try {
    await page.goto('/#/review');
    const declined = page.waitForResponse((r) => r.request().method() === 'POST' && r.url().endsWith('/decline'));
    await suggestionPanel(page).getByRole('button', { name: /^Decline/ }).click();
    expect((await declined).ok()).toBe(true);
    await expect(page.getByRole('heading', { name: /Dive suggestions/ })).toHaveCount(0);

    expect(await (await other.get('/api/buddy-suggestions')).json()).toEqual([]);
    const { dives } = await (await other.get(`/api/dives?diverId=${diverId}`)).json() as { dives: unknown[] };
    expect(dives).toEqual([]);
    // Declining keeps the invitation: Tim's Participant stays, so adding them again raises it once more.
    const source = await (await request.get(`/api/dives/${dive.id}`)).json() as Dive;
    expect(source.participants.map((p) => p.name)).toContain(diverName);
  } finally {
    await close();
  }
});

/** The Review page's Buddy suggestions panel, whatever its count. */
function suggestionPanel(page: Page) {
  return page.locator('section', { has: page.getByRole('heading', { name: /Dive suggestions/ }) });
}

/** The seeded Dive ready for a suggestion: nobody on it, its facts as imported. */
async function resetSource(api: APIRequestContext) {
  const dive = await resetDive(api);
  await clearParticipants(api, dive.id);
  return dive;
}

/** The other User, invited like anyone else (ADR 0012); a retry finds the User that an earlier attempt made. */
async function ensureOtherUser(api: APIRequestContext) {
  const users = await (await api.get('/api/users')).json() as { email: string }[];
  if (users.some((u) => u.email === OTHER.email)) return;
  const invitation = await (await api.post('/api/invitations', { data: { email: OTHER.email }, headers })).json() as { url: string };
  const anonymous = await request.newContext({ baseURL: E2E_BASE_URL, extraHTTPHeaders: headers });
  try {
    const accepted = await anonymous.post('/api/invitations/accept', {
      data: { token: invitation.url.split('/invite/')[1], name: OTHER.name, password: OTHER.password }, headers,
    });
    if (!accepted.ok()) throw new Error(`accepting the invitation failed: ${accepted.status()} ${await accepted.text()}`);
  } finally {
    await anonymous.dispose();
  }
}

async function signInOther(): Promise<APIRequestContext> {
  const api = await request.newContext({ baseURL: E2E_BASE_URL, extraHTTPHeaders: headers });
  const signed = await api.post('/api/auth/sign-in/email', { data: { email: OTHER.email, password: OTHER.password } });
  if (!signed.ok()) {
    await api.dispose();
    throw new Error(`signing the other User in failed: ${signed.status()} ${await signed.text()}`);
  }
  return api;
}

/** A browser signed in as the other User, its own cookies and tab. */
async function openAsOther(browser: Browser): Promise<{ page: Page; close: () => Promise<void> }> {
  const context = await browser.newContext({ baseURL: E2E_BASE_URL, locale: 'en-GB', storageState: otherSession });
  return { page: await context.newPage(), close: () => context.close() };
}

/** Another Diver the other User keeps, so each test starts with an invited Diver no other Dive is on. */
async function otherManagedDiver(name: string): Promise<string> {
  const created = await other.post('/api/divers', { data: { name }, headers });
  if (!created.ok()) throw new Error(`creating the Diver failed: ${created.status()} ${await created.text()}`);
  return (await created.json() as { id: string }).id;
}

/** A Dive site of this test, so the copy's shared facts have a name to show. */
async function buddySite(api: APIRequestContext): Promise<{ id: string; name: string }> {
  const name = `Buddy test reef ${uniqueWord()}`;
  const created = await api.post('/api/dive-sites', { data: { name }, headers });
  if (!created.ok()) throw new Error(`creating the site failed: ${created.status()} ${await created.text()}`);
  return await created.json() as { id: string; name: string };
}

/** The other User's own Dive at the seeded Dive's start: imported once from the fixture, then found again. */
async function otherOverlappingDive(): Promise<string> {
  const { dives } = await (await other.get(`/api/dives?diverId=${otherOwnDiver}&limit=200`)).json() as { dives: { id: string; startsAt: string }[] };
  const found = dives.find((d) => d.startsAt.startsWith(SOURCE_START));
  if (found) return found.id;
  const upload = await other.post('/api/imports', {
    headers,
    multipart: { file: { name: 'buddy-computer.fit', mimeType: 'application/octet-stream', buffer: readFileSync('e2e/fixtures/buddy-computer.fit') } },
  });
  if (!upload.ok()) throw new Error(`uploading the Dive failed: ${upload.status()} ${await upload.text()}`);
  const { id } = await upload.json() as { id: string };
  const url = `/api/imports/${id}`;
  await expect.poll(async () => (await (await other.get(url)).json() as { status: string }).status, { timeout: 20_000 }).toBe('done');
  const { outcome } = await (await other.get(url)).json() as { outcome: { diveId?: string }[] };
  return outcome[0]!.diveId!;
}
