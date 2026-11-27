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
const BUDDY = { email: 'buddy-diver@example.com', name: 'Bella Buddy', password: 'a long enough test password' };
const STRANGER = { email: 'noor-stranger@example.com', name: 'Noor Stranger', password: 'a long enough test password' };

test.use({ baseURL: E2E_SHARED_BASE_URL, storageState: E2E_SHARED_SESSION });
test.describe.configure({ mode: 'serial' });

let diveId: string;
let diverId: string;
let buddy: Awaited<ReturnType<typeof personSession>>;
let stranger: Awaited<ReturnType<typeof personSession>>;

type Person = { email: string; name: string; password: string };

async function setParticipants(api: APIRequestContext, id: string, diverIds: string[]) {
  const dive = await (await api.get(`/api/dives/${id}`)).json() as { version: number };
  const response = await api.put(`/api/dives/${id}/participants`, {
    data: { version: dive.version, participants: diverIds.map((diverId) => ({ diverId, role: 'buddy' })) }, headers,
  });
  if (!response.ok()) throw new Error(`setting the participants failed: ${response.status()} ${await response.text()}`);
}

/** The person's own Diver (created with every User), to put them on a Dive. */
async function ownDiver(api: APIRequestContext): Promise<string> {
  const divers = await (await api.get('/api/divers')).json() as { id: string; isOwn: boolean }[];
  const own = divers.find((d) => d.isOwn);
  if (!own) throw new Error('the person has no own Diver');
  return own.id;
}

/** A User invited like any other (ADR 0012), signed in with their own API context; a retry finds the User an earlier attempt made. */
async function personSession(api: APIRequestContext, person: Person) {
  const users = await (await api.get('/api/users')).json() as { email: string }[];
  if (!users.some((u) => u.email === person.email)) {
    const invitation = await (await api.post('/api/invitations', { data: { email: person.email }, headers })).json() as { url: string };
    const anonymous = await request.newContext({ baseURL: E2E_SHARED_BASE_URL, extraHTTPHeaders: headers });
    try {
      const accepted = await anonymous.post('/api/invitations/accept', {
        data: { token: invitation.url.split('/invite/')[1], name: person.name, password: person.password }, headers,
      });
      if (!accepted.ok()) throw new Error(`accepting the invitation failed: ${accepted.status()} ${await accepted.text()}`);
    } finally {
      await anonymous.dispose();
    }
  }
  const signedIn = await request.newContext({ baseURL: E2E_SHARED_BASE_URL, extraHTTPHeaders: headers });
  const response = await signedIn.post('/api/auth/sign-in/email', { data: { email: person.email, password: person.password } });
  if (!response.ok()) throw new Error(`signing ${person.name} in failed: ${response.status()} ${await response.text()}`);
  return { api: signedIn, state: await signedIn.storageState(), ownDiver: await ownDiver(signedIn) };
}

test.beforeAll(async () => {
  const erikaApi = await request.newContext({ baseURL: E2E_SHARED_BASE_URL, extraHTTPHeaders: headers, storageState: E2E_SHARED_SESSION });
  try {
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

    // A non-admin buddy: both their own Diver and Nia are Participants on Erika's seeded Dive, so they share it.
    buddy = await personSession(erikaApi, BUDDY);
    await setParticipants(erikaApi, diveId, [buddy.ownDiver, diverId]);

    // A stranger on no Dive with Nia and managing no Diver of that name.
    stranger = await personSession(erikaApi, STRANGER);
  } finally {
    await erikaApi.dispose();
  }
});

test.afterAll(async () => {
  const erikaApi = await request.newContext({ baseURL: E2E_SHARED_BASE_URL, extraHTTPHeaders: headers, storageState: E2E_SHARED_SESSION });
  try {
    await setParticipants(erikaApi, diveId, []);
    const { divers } = await (await erikaApi.get(`/api/external-divers?q=${encodeURIComponent(NAME)}`)).json() as { divers: { id: string; name: string }[] };
    for (const d of divers.filter((x) => x.name === NAME)) await erikaApi.delete(`/api/external-divers/${d.id}`, { headers });
  } finally {
    await erikaApi.dispose();
    await buddy?.api.dispose();
    await stranger?.api.dispose();
  }
});

test('a non-admin buddy on a shared Dive sees her details and codes', { tag: '@divers' }, async ({ browser }) => {
  const context = await browser.newContext({ baseURL: E2E_SHARED_BASE_URL, locale: 'en-GB', storageState: buddy.state });
  const page = await context.newPage();
  try {
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
  } finally {
    await context.close();
  }
});

test('a stranger sees her name parts only, with "not shared with you" where a detail is withheld', { tag: '@divers' }, async ({ browser }) => {
  const context = await browser.newContext({ baseURL: E2E_SHARED_BASE_URL, locale: 'en-GB', storageState: stranger.state });
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

test('the Dive owner sees the professional’s verification code on the Dive', { tag: '@divers' }, async ({ page }) => {
  await page.goto(`/#/dives/${diveId}`);
  const line = page.getByRole('button', { name: /^Verification codes?$/ });
  if (await line.getAttribute('aria-expanded') !== 'true') await line.click();
  await expect(page.getByRole('img', { name: `SSI verification code of a dive professional: ${NAME}` })).toBeVisible();
});
