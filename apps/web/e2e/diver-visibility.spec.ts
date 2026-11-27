// Diver visibility in a real browser (ADR 0088): the server on E2E_SHARED_BASE_URL runs with
// DIVEHUB_DIVER_VISIBILITY=shared. A User who shares a Dive with a person sees that person's contact details and codes;
// a User outside the person's circle sees the name parts only, with "not shared with you" where a detail is withheld,
// never "not set". The unchanged instance rendering is diver-codes.spec.ts on the default servers.
import { expect, request, test, type APIRequestContext } from '@playwright/test';
import { E2E_SHARED_BASE_URL, E2E_SHARED_SESSION, seededDiveId } from './support.ts';

const headers = { origin: E2E_SHARED_BASE_URL };
/** A person Erika dived with, with everything a buddy and a professional code need. The values are made up. */
const NAME = 'Nia Shared';
const EMAIL = 'nia@example.com';
const ACCOUNT = '4000400';
const STRANGER = { email: 'noor-stranger@example.com', name: 'Noor Stranger', password: 'a long enough test password' };

test.use({ baseURL: E2E_SHARED_BASE_URL, storageState: E2E_SHARED_SESSION });
test.describe.configure({ mode: 'serial' });

let diveId: string;
let diverId: string;
let erikaApi: APIRequestContext;
let strangerApi: APIRequestContext;
let stranger: Awaited<ReturnType<APIRequestContext['storageState']>>;

async function setParticipants(api: APIRequestContext, id: string, diverIds: string[]) {
  const dive = await (await api.get(`/api/dives/${id}`)).json() as { version: number };
  const response = await api.put(`/api/dives/${id}/participants`, {
    data: { version: dive.version, participants: diverIds.map((diverId) => ({ diverId, role: 'buddy' })) }, headers,
  });
  if (!response.ok()) throw new Error(`setting the participants failed: ${response.status()} ${await response.text()}`);
}

/** The stranger, invited like any other User (ADR 0012); a retry finds the User that an earlier attempt made. */
async function strangerSession(api: APIRequestContext) {
  const users = await (await api.get('/api/users')).json() as { email: string }[];
  if (!users.some((u) => u.email === STRANGER.email)) {
    const invitation = await (await api.post('/api/invitations', { data: { email: STRANGER.email }, headers })).json() as { url: string };
    const anonymous = await request.newContext({ baseURL: E2E_SHARED_BASE_URL, extraHTTPHeaders: headers });
    try {
      const accepted = await anonymous.post('/api/invitations/accept', {
        data: { token: invitation.url.split('/invite/')[1], name: STRANGER.name, password: STRANGER.password }, headers,
      });
      if (!accepted.ok()) throw new Error(`accepting the invitation failed: ${accepted.status()} ${await accepted.text()}`);
    } finally {
      await anonymous.dispose();
    }
  }
  const api2 = await request.newContext({ baseURL: E2E_SHARED_BASE_URL, extraHTTPHeaders: headers });
  const signedIn = await api2.post('/api/auth/sign-in/email', { data: { email: STRANGER.email, password: STRANGER.password } });
  if (!signedIn.ok()) throw new Error(`signing the stranger in failed: ${signedIn.status()} ${await signedIn.text()}`);
  return { api: api2, state: await api2.storageState() };
}

test.beforeAll(async () => {
  erikaApi = await request.newContext({ baseURL: E2E_SHARED_BASE_URL, extraHTTPHeaders: headers, storageState: E2E_SHARED_SESSION });
  diveId = await seededDiveId(erikaApi);
  await setParticipants(erikaApi, diveId, []);

  const found = await (await erikaApi.get(`/api/external-divers?q=${encodeURIComponent(NAME)}`)).json() as { divers: { id: string; name: string }[] };
  diverId = found.divers.find((d) => d.name === NAME)?.id
    ?? (await (await erikaApi.post('/api/external-divers', { data: { name: NAME }, headers })).json() as { id: string }).id;
  const details = await erikaApi.patch(`/api/divers/${diverId}/details`, {
    data: { firstName: 'Nia', lastName: 'Shared', email: EMAIL, leaderNumber: '333' }, headers,
  });
  if (!details.ok()) throw new Error(`setting the details failed: ${details.status()} ${await details.text()}`);
  const account = await erikaApi.put(`/api/divers/${diverId}/external-ids/ssi`, { data: { externalId: ACCOUNT }, headers });
  if (!account.ok()) throw new Error(`setting the account failed: ${account.status()} ${await account.text()}`);

  // Erika shares the seeded Dive with her: a buddy may see her details and codes.
  await setParticipants(erikaApi, diveId, [diverId]);

  // A stranger on no Dive with her and managing no Diver of that name sees the name parts only.
  const other = await strangerSession(erikaApi);
  strangerApi = other.api;
  stranger = other.state;
});

test.afterAll(async () => {
  await setParticipants(erikaApi, diveId, []);
  const { divers } = await (await erikaApi.get(`/api/external-divers?q=${encodeURIComponent(NAME)}`)).json() as { divers: { id: string; name: string }[] };
  for (const d of divers.filter((x) => x.name === NAME)) await erikaApi.delete(`/api/external-divers/${d.id}`, { headers });
  await strangerApi?.dispose();
  await erikaApi?.dispose();
});

test('a buddy on a shared Dive sees her details and codes, on the Diver and on the Dive', { tag: '@divers' }, async ({ page }) => {
  await page.goto('/#/divers');
  const others = page.locator('section', { has: page.getByRole('heading', { name: 'Other divers' }) });
  const row = others.getByRole('listitem').filter({ hasText: NAME });
  await expect(row).toContainText('SSI account known');
  await expect(row).not.toContainText('not shared with you');
  await row.getByRole('button', { name: `Actions: ${NAME}` }).click();
  await page.getByRole('menuitem', { name: 'Codes' }).click();

  const codes = page.getByRole('dialog', { name: `${NAME}: codes and details` });
  await expect(codes.getByRole('img', { name: `SSI buddy code: ${NAME}` })).toBeVisible();
  await expect(codes.getByRole('img', { name: `SSI professional’s code: ${NAME}` })).toBeVisible();
  await expect(codes.getByText(EMAIL)).toBeVisible();
  await expect(codes.getByText(ACCOUNT)).toBeVisible();
  await expect(codes.getByText(`These details are personal to ${NAME}.`)).toBeVisible();
  await expect(codes.getByText('Not shared with you')).toHaveCount(0);
  await codes.getByRole('button', { name: 'Done' }).click();

  // The Dive's professional code is there too (ADR 0043): she is a Participant and Erika shares the Dive with her.
  await page.goto(`/#/dives/${diveId}`);
  const line = page.getByRole('button', { name: /^Verification codes?$/ });
  if (await line.getAttribute('aria-expanded') !== 'true') await line.click();
  await expect(page.getByRole('img', { name: `SSI verification code of a dive professional: ${NAME}` })).toBeVisible();
});

test('a stranger sees her name parts only, with "not shared with you" where a detail is withheld', { tag: '@divers' }, async ({ browser }) => {
  const context = await browser.newContext({ baseURL: E2E_SHARED_BASE_URL, locale: 'en-GB', storageState: stranger });
  const page = await context.newPage();
  try {
    await page.goto('/#/divers');
    const others = page.locator('section', { has: page.getByRole('heading', { name: 'Other divers' }) });
    const row = others.getByRole('listitem').filter({ hasText: NAME });
    await expect(row).toContainText('SSI details not shared with you');
    await expect(row).not.toContainText('SSI account known');
    await row.getByRole('button', { name: `Actions: ${NAME}` }).click();
    await page.getByRole('menuitem', { name: 'Codes' }).click();

    const codes = page.getByRole('dialog', { name: `${NAME}: codes and details` });
    await expect(codes.getByText(`${NAME}’s details and codes aren’t shared with you.`)).toBeVisible();
    await expect(codes.getByText('Not shared with you').first()).toBeVisible();
    // The name parts stay (ADR 0028), and a withheld field must not read as "not set".
    await expect(codes.getByText('Nia', { exact: true })).toBeVisible();
    await expect(codes.getByText('Shared', { exact: true })).toBeVisible();
    await expect(codes.getByText('Not set')).toHaveCount(0);
    // Nothing of the contact details or the codes reaches the page.
    await expect(codes.getByText(EMAIL)).toHaveCount(0);
    await expect(codes.getByText(ACCOUNT)).toHaveCount(0);
    await expect(codes.getByRole('img')).toHaveCount(0);
    // The edit form would clear fields this User cannot see, so it is not offered.
    await expect(codes.getByRole('button', { name: 'Edit details' })).toHaveCount(0);
  } finally {
    await context.close();
  }
});
