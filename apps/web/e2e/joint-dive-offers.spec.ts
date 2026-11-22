// Joint-dive fill offers in a real browser (ADR 0070): a Participant another User keeps raises a Buddy suggestion;
// accepting it by linking joins the two Dives into a Joint dive and unions their people both ways. When one member
// later fills a shared fact the other lacks, the other is offered to fill it from the buddy's dive — accepting copies
// it into their own Dive, declining dismisses that offer for good.
//
// The two Dives of each test are Imports on their own computers (test/fixtures/write-joint-offer-fixtures.ts), so
// nothing another spec did to the seeded Dive shows here: specifications stand alone (ADR 0023).
import { readFileSync } from 'node:fs';
import { expect, request, test, type APIRequestContext, type Browser, type Page } from '@playwright/test';
import { E2E_BASE_URL, clearParticipants, externalDiver, headers, setBuddies, setPreferences, uniqueWord } from './support.ts';

const OTHER = { email: 'bianca@example.com', name: 'Bianca', password: 'a long enough test password' };
/** The serial of each Imported Dive: the computer tells the Dive apart from every other one in the logbook. */
const SOURCE_SERIAL = '9301';
const TARGET_SERIAL = '9302';
const DECLINE_SOURCE_SERIAL = '9303';

type Dive = {
  id: string;
  version: number;
  values: { startsAt: { at: string; utcOffsetSeconds: number | null } };
  site: { id: string; name: string } | null;
  participants: { name: string }[];
};

let other: APIRequestContext;
let otherSession: Awaited<ReturnType<APIRequestContext['storageState']>>;
let otherOwnDiverId: string;
let otherOwnDiverName: string;
let sourceId: string;
let targetId: string;
let declineSourceId: string;
/** The Dive "Create a new dive" made in the decline test, so its teardown can leave the Joint dive with the offer. */
let createdId: string | null = null;

test.beforeAll(async ({ request }) => {
  await setPreferences(request, { language: null, units: null });
  await ensureOtherUser(request);
  other = await signInOther();
  otherSession = await other.storageState();
  const me = await (await other.get('/api/me')).json() as { ownDiver: { id: string; name: string } };
  otherOwnDiverId = me.ownDiver.id;
  otherOwnDiverName = me.ownDiver.name;
  sourceId = await importedDive(request, 'joint-source.fit', SOURCE_SERIAL);
  targetId = await importedDive(other, 'joint-target.fit', TARGET_SERIAL);
  declineSourceId = await importedDive(request, 'joint-decline-source.fit', DECLINE_SOURCE_SERIAL);
});

test.afterAll(async () => {
  await other?.dispose();
});

test.beforeEach(async ({ request }) => {
  // Neither Dive knows a Site or another person: the offer and the union each test checks must come from that test.
  for (const [api, id] of [[request, sourceId], [other, targetId]] as const) {
    const dive = await (await api.get(`/api/dives/${id}`)).json() as Dive;
    await api.patch(`/api/dives/${id}`, { data: { version: dive.version, siteId: null }, headers });
    await clearParticipants(api, id);
  }
  const declineSource = await (await request.get(`/api/dives/${declineSourceId}`)).json() as Dive;
  await request.patch(`/api/dives/${declineSourceId}`, { data: { version: declineSource.version, siteId: null }, headers });
});

test.afterEach(async () => {
  if (createdId === null) return;
  // Leave the decline source alone for the next test: deleting the copy detaches it and takes the dismissal with it.
  const dive = await (await other.get(`/api/dives/${createdId}`)).json() as Dive;
  await other.delete(`/api/dives/${createdId}`, { data: { version: dive.version }, headers });
  createdId = null;
});

test('a buddy links an overlapping dive, then accepts the site the other member fills', { tag: ['@dives', '@divers'] }, async ({ page, request, browser }) => {
  const sourceFriendName = `Source friend ${uniqueWord()}`;
  const targetFriendName = `Target friend ${uniqueWord()}`;
  const sourceFriend = await externalDiver(request, sourceFriendName);
  const targetFriend = await externalDiver(request, targetFriendName);

  // User A names User B's own Diver on her Dive, as she would any Diver of the instance.
  await page.goto(`/#/dives/${sourceId}`);
  await page.getByRole('group', { name: 'Add details' }).getByRole('button', { name: 'Add buddies' }).click();
  const addDialog = page.getByRole('dialog');
  await addDialog.getByRole('searchbox', { name: 'Find a diver' }).fill(otherOwnDiverName);
  await addDialog.getByRole('option', { name: new RegExp(otherOwnDiverName) }).click();
  await expect(addDialog).toBeHidden();
  // A second person only her Dive has: the union must bring them to B's Dive too. B's Dive has one only it has.
  await setBuddies(request, sourceId, [otherOwnDiverId, sourceFriend]);
  await setBuddies(other, targetId, [targetFriend]);
  await expect(page.getByRole('group', { name: 'Buddies and guides' })).toContainText(sourceFriendName);

  const { page: theirs, close } = await openAsOther(browser);
  try {
    // User B has an overlapping Dive, so the suggestion's branch B joins the two instead of copying one.
    await theirs.goto('/#/review');
    await expect(suggestionPanel(theirs).getByRole('heading', { name: `Erika logged this dive with your diver ${otherOwnDiverName}`, level: 3 })).toBeVisible();
    await suggestionPanel(theirs).getByRole('button', { name: /^Accept/ }).click();
    const acceptDialog = theirs.getByRole('dialog', { name: 'Accept this dive' });
    await expect(acceptDialog).toContainText('You already have a dive at this time');
    const linked = theirs.waitForResponse((r) => r.request().method() === 'POST' && r.url().includes('/api/buddy-suggestions/') && r.url().endsWith('/accept'));
    await acceptDialog.getByRole('button', { name: /Link to your dive/ }).click();
    const accepted = await (await linked).json() as { diveId: string; jointDiveId: string };
    expect(accepted.diveId).toBe(targetId);
    expect(accepted.jointDiveId).toBeTruthy();
    await expect(theirs).toHaveURL(new RegExp(`#/dives/${targetId}$`));
    // The people are unioned both ways: B's Dive gains A's own Diver and A's other buddy, and A's Dive gains B's buddy.
    await expect(theirs.getByRole('group', { name: 'Buddies and guides' })).toContainText('Erika');
    await expect(theirs.getByRole('group', { name: 'Buddies and guides' })).toContainText(sourceFriendName);
    await page.goto(`/#/dives/${sourceId}`);
    await expect(page.getByRole('group', { name: 'Buddies and guides' })).toContainText(targetFriendName);

    // A fills the site her Dive has and B's does not: the empty Site is what the offer can fill.
    const siteName = await addSite(page, `Joint reef ${uniqueWord()}`);

    // B is offered the Site on the Review page and inline on the Dive's own page.
    await theirs.goto('/#/review');
    const reviewOffer = offerPanel(theirs);
    await expect(reviewOffer).toContainText('Fill from your buddy\'s dive');
    const card = reviewOffer.getByRole('listitem').filter({ has: theirs.getByRole('heading', { name: /From Erika.s dive/ }) });
    await expect(card.getByRole('rowheader', { name: 'Site' })).toBeVisible();
    await expect(card).toContainText(siteName);

    await theirs.goto(`/#/dives/${targetId}`);
    const diveOffer = offerPanel(theirs);
    await expect(diveOffer).toContainText('Fill from your buddy\'s dive');
    await expect(diveOffer).toContainText(siteName);

    // Accepting copies the Site into B's own Dive; the offer is gone with the value it could fill.
    const filled = theirs.waitForResponse((r) => r.request().method() === 'POST' && r.url().endsWith('/api/joint-dives/offers/accept'));
    await diveOffer.getByRole('button', { name: /^Accept/ }).click();
    const answer = await filled;
    expect(answer.ok()).toBe(true);
    await expect(offerPanel(theirs)).toHaveCount(0);
    await expect(theirs.locator('dl.dive-details').getByRole('link', { name: siteName })).toBeVisible();

    // The Review inbox has nothing left to offer for this Dive either.
    await theirs.goto('/#/review');
    await expect(offerPanel(theirs)).toHaveCount(0);
  } finally {
    await close();
  }
});

test('declining a fill offer dismisses it and it is not offered again', { tag: ['@dives', '@divers'] }, async ({ page, request, browser }) => {
  const buddyName = `Decline ${uniqueWord()}`;
  const buddyId = await otherManagedDiver(buddyName);
  await setBuddies(request, declineSourceId, [buddyId]);

  const { page: theirs, close } = await openAsOther(browser);
  try {
    // Branch A makes B a Dive of this Joint dive with the shared facts; its Site stays empty because A's is empty.
    await theirs.goto('/#/review');
    await suggestionPanel(theirs).getByRole('button', { name: /^Accept/ }).click();
    const acceptDialog = theirs.getByRole('dialog', { name: 'Accept this dive' });
    const accepted = theirs.waitForResponse((r) => r.request().method() === 'POST' && r.url().includes('/api/buddy-suggestions/') && r.url().endsWith('/accept'));
    await acceptDialog.getByRole('button', { name: 'Create a new dive' }).click();
    const copy = await (await accepted).json() as { diveId: string; jointDiveId: string };
    createdId = copy.diveId;
    await expect(theirs).toHaveURL(new RegExp(`#/dives/${copy.diveId}$`));

    // A now fills the Site on her Dive; B's copy still lacks it.
    await page.goto(`/#/dives/${declineSourceId}`);
    const siteName = await addSite(page, `Declined reef ${uniqueWord()}`);

    await theirs.goto('/#/review');
    const offer = offerPanel(theirs);
    await expect(offer).toContainText('Fill from your buddy\'s dive');
    await expect(offer).toContainText(siteName);

    const declined = theirs.waitForResponse((r) => r.request().method() === 'POST' && r.url().endsWith('/api/joint-dives/offers/decline'));
    await offer.getByRole('button', { name: /^Decline/ }).click();
    expect((await declined).ok()).toBe(true);
    await expect(offerPanel(theirs)).toHaveCount(0);

    // The dismissal is stored: a reload does not bring the offer back.
    await theirs.reload();
    await expect(offerPanel(theirs)).toHaveCount(0);
    await expect(theirs.getByText(siteName)).toHaveCount(0);
  } finally {
    await close();
  }
});

/** The Review page's fill-offer panel, whatever it offers. */
function offerPanel(page: Page) {
  return page.locator('section', { has: page.getByRole('heading', { name: 'Fill from your buddy\'s dive' }) });
}

/** The Review page's Buddy suggestions panel, whatever its count. */
function suggestionPanel(page: Page) {
  return page.locator('section', { has: page.getByRole('heading', { name: /Dive suggestions/ }) });
}

/** Adds a Site the Dive has no position for through the page's own "Add site", as a User would. */
async function addSite(page: Page, name: string): Promise<string> {
  await page.getByRole('button', { name: 'Add site' }).click();
  const dialog = page.getByRole('dialog', { name: 'Dive site' });
  await dialog.getByRole('searchbox', { name: 'Find a dive site' }).fill(name);
  await dialog.getByRole('option', { name: `Create “${name}” as a new dive site` }).click();
  await expect(dialog.getByRole('textbox', { name: 'Name' })).toHaveValue(name);
  await dialog.getByRole('button', { name: 'Create and choose' }).click();
  await expect(page.getByRole('dialog')).toHaveCount(0);
  await expect(page.locator('dl.dive-details').getByRole('link', { name })).toBeVisible();
  return name;
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

/** The Dive the fixture made on this computer, importing it once if it is not here yet. */
async function importedDive(api: APIRequestContext, fixture: string, serial: string): Promise<string> {
  const { dives } = await (await api.get('/api/dives?limit=200')).json() as { dives: { id: string }[] };
  for (const dive of dives) {
    const view = await (await api.get(`/api/dives/${dive.id}`)).json() as { recordings: { device: { serialNumber: string } | null }[] };
    if (view.recordings.some((r) => r.device?.serialNumber === serial)) return dive.id;
  }
  const upload = await api.post('/api/imports', {
    headers,
    multipart: { file: { name: fixture, mimeType: 'application/octet-stream', buffer: readFileSync(`e2e/fixtures/${fixture}`) } },
  });
  if (!upload.ok()) throw new Error(`uploading ${fixture} failed: ${upload.status()} ${await upload.text()}`);
  const { id } = await upload.json() as { id: string };
  const url = `/api/imports/${id}`;
  await expect.poll(async () => (await (await api.get(url)).json() as { status: string }).status, { timeout: 20_000 }).toBe('done');
  const { outcome } = await (await api.get(url)).json() as { outcome: { diveId?: string }[] };
  const made = outcome.find((o) => o.diveId)?.diveId;
  if (!made) throw new Error(`importing ${fixture} made no Dive: ${JSON.stringify(outcome)}`);
  return made;
}
