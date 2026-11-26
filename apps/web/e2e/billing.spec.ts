// Billing and the paywall (hosted-DiveHub plan §4.4, ADR 0087, 0089): the Plan section with Paddle's links, and the
// automation switches greyed for a free User with the offer's upsell, usable for an entitled one. One extra server runs
// with billing on; the workers' servers stay without it, which is the dual-track invariant (no billing, every feature free).
import { expect, request, test, type APIRequestContext, type Page } from '@playwright/test';
import { billingSessionFile, connectSsi, E2E_BILLING_URL, SSI_ACCOUNT } from './support.ts';

const billingHeaders = { origin: E2E_BILLING_URL };
/** Erika's own Connection's automation settings (the region's name is the Provider's, as other specs use it). */
const automatic = (page: Page) => page.getByRole('region', { name: 'Automatic: Erika' });

/** An API context signed in on the billing server (the project's `request` points at the worker's server). */
const billingApi = () => request.newContext({
  baseURL: E2E_BILLING_URL, storageState: billingSessionFile, extraHTTPHeaders: billingHeaders,
});

/** Sets Erika's entitlement row on the billing server, or clears it for the free case. */
async function setEntitlement(api: APIRequestContext, body: { clear?: true; status?: 'active' | 'past_due' | 'canceled' }) {
  const response = await api.post('/e2e/billing/entitlement', { data: body, headers: billingHeaders });
  expect(response.ok()).toBe(true);
}

/** Erika's own SSI Connection on the billing server, so its automation switches render. */
async function connectBillingSsi(api: APIRequestContext) {
  const me = await (await api.get('/api/me')).json() as { ownDiver: { id: string } };
  const connections = await (await api.get('/api/connections')).json() as { id: string; provider: string; diverId: string; state: string }[];
  const own = connections.find((c) => c.provider === 'ssi' && c.diverId === me.ownDiver.id);
  if (own?.state === 'active') return;
  const response = own
    ? await api.post(`/api/connections/${own.id}/sign-in`, { data: { password: SSI_ACCOUNT.password, keepSignedIn: true }, headers: billingHeaders })
    : await api.post('/api/connections/ssi', { data: { diverId: me.ownDiver.id, ...SSI_ACCOUNT, keepSignedIn: true }, headers: billingHeaders });
  expect(response.ok()).toBe(true);
}

test('a free User sees the Plan section, and the automation switches greyed with the offer’s upsell', { tag: ['@account', '@dives'] }, async ({ browser }) => {
  const api = await billingApi();
  try {
    await setEntitlement(api, { clear: true });
    await connectBillingSsi(api);
  } finally {
    await api.dispose();
  }
  const context = await browser.newContext({ baseURL: E2E_BILLING_URL, storageState: billingSessionFile, locale: 'en-GB' });
  try {
    const page = await context.newPage();
    await page.setViewportSize({ width: 1280, height: 900 });
    await page.goto('/#/settings/connections');
    const settings = automatic(page);
    await expect(settings).toBeVisible();
    const changes = settings.getByRole('switch', { name: 'Send changes automatically' });
    await expect(changes).toBeDisabled();
    await expect(settings.getByText('Automation is part of the pro plan.')).toBeVisible();
    await expect(settings.getByRole('link', { name: 'See the plan' })).toHaveAttribute('href', '#/settings/plan');

    await page.goto('/#/settings/plan');
    await expect(page.getByRole('heading', { name: 'Plan', level: 1 })).toBeVisible();
    await expect(page.getByText('Free — no paid plan')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Upgrade to Pro' })).toBeVisible();
    // The Plan section is among the sections only where the server sells a plan.
    await expect(page.getByRole('navigation', { name: 'Settings' }).getByRole('link', { name: 'Plan' })).toBeVisible();
  } finally {
    await context.close();
  }
});

test('an entitled User’s automation switches work, and the Plan section offers the portal', { tag: ['@account', '@dives'] }, async ({ browser }) => {
  const api = await billingApi();
  try {
    await setEntitlement(api, { status: 'active' });
    await connectBillingSsi(api);
  } finally {
    await api.dispose();
  }
  const context = await browser.newContext({ baseURL: E2E_BILLING_URL, storageState: billingSessionFile, locale: 'en-GB' });
  try {
    const page = await context.newPage();
    await page.setViewportSize({ width: 1280, height: 900 });
    await page.goto('/#/settings/connections');
    const settings = automatic(page);
    await expect(settings).toBeVisible();
    const changes = settings.getByRole('switch', { name: 'Send changes automatically' });
    await expect(changes).toBeEnabled();
    await expect(settings.getByText('Automation is part of the pro plan.')).toHaveCount(0);
    // The switch's input is visually hidden; the label is what a User presses (as in waiting-pushes.spec.ts).
    const label = settings.getByText('Send changes automatically', { exact: true });
    await label.click();
    await expect(changes).toBeChecked();

    await page.goto('/#/settings/plan');
    await expect(page.getByText('Pro — active')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Manage subscription' })).toBeVisible();

    // Downgraded while the switch is on: the server still accepts turning it off, so it must stay usable for that (the
    // paid feature cannot be turned on). A free User's off switch is greyed; an on one is not.
    await page.goto('/#/settings/connections');
    const downgrade = await billingApi();
    try {
      await setEntitlement(downgrade, { clear: true });
    } finally {
      await downgrade.dispose();
    }
    await page.reload();
    const downgraded = automatic(page);
    const stillOn = downgraded.getByRole('switch', { name: 'Send changes automatically' });
    await expect(stillOn).toBeChecked();
    await expect(stillOn).toBeEnabled();
    await expect(downgraded.getByText('Automation is part of the pro plan.')).toBeVisible();
    await downgraded.getByText('Send changes automatically', { exact: true }).click();
    await expect(stillOn).not.toBeChecked();
  } finally {
    await context.close();
  }
});

test('without billing there is no Plan section and the automation stays usable (the dual-track invariant)', { tag: ['@account', '@dives'] }, async ({ page, request }) => {
  await connectSsi(request);
  await page.setViewportSize({ width: 1280, height: 900 });
  // No Plan section at all: its address goes where an unknown one goes.
  await page.goto('/#/settings/plan');
  await expect(page).toHaveURL(/#\/settings\/display$/);
  await expect(page.getByRole('heading', { name: 'Display', level: 1 })).toBeVisible();
  await expect(page.getByRole('navigation', { name: 'Settings' }).getByRole('link', { name: 'Plan' })).toHaveCount(0);

  // Every switch works, and no upsell names a plan.
  await page.goto('/#/settings/connections');
  const settings = automatic(page);
  await expect(settings).toBeVisible();
  await expect(settings.getByRole('switch', { name: 'Send changes automatically' })).toBeEnabled();
  await expect(settings.getByText(/part of the .* plan/)).toHaveCount(0);
});
