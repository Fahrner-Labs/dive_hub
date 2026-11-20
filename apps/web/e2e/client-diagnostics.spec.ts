// Client diagnostics in the web app (FAH-292, ADR 0080): an admin switches the instance setting, a failing page keeps
// the report button, the account section sends what stayed on the device, and a switched-off instance explains itself.
import { expect, test, type APIRequestContext } from '@playwright/test';
import { expectGoodPage, headers } from './support.ts';

async function setEnabled(request: APIRequestContext, enabled: boolean) {
  const answer = await request.patch('/api/admin/client-diagnostics', { data: { enabled }, headers });
  expect(answer.ok()).toBe(true);
}

test.beforeEach(async ({ request }) => setEnabled(request, false));
test.afterEach(async ({ request }) => setEnabled(request, false));

test('an admin switches client diagnostics on and off again', { tag: ['@client-diagnostics', '@admin'] }, async ({ page }) => {
  await page.goto('/#/settings/admin');
  await expect(page.getByRole('heading', { name: 'Administration', level: 1 })).toBeVisible();

  const panel = page.locator('section', { has: page.getByRole('heading', { name: 'Client diagnostics', exact: true }) });
  await expect(panel.getByText('Off by default. When it is on, signed-in people can send the errors their browser kept on this device.')).toBeVisible();
  await expect(panel.getByText('Entries stay on this instance, are redacted, and are removed after 90 days. They join the support bundle an admin downloads.')).toBeVisible();
  const toggle = panel.getByRole('switch', { name: 'Collect client diagnostics' });
  await expect(toggle).not.toBeChecked();
  await expect(panel.getByText('Off', { exact: true })).toBeVisible();
  await expectGoodPage(page, 'Administration', { axe: false });

  await toggle.click();
  await expect(toggle).toBeChecked();
  await expect(panel.getByText('On', { exact: true })).toBeVisible();

  await toggle.click();
  await expect(toggle).not.toBeChecked();
  await expect(panel.getByText('Off', { exact: true })).toBeVisible();
});

test('a failing page keeps the report button when diagnostics are on', { tag: ['@client-diagnostics'] }, async ({ page, request }) => {
  await setEnabled(request, true);
  // Make the Settings page chunk fail as it renders; the app-wide ErrorBoundary must still show the report surface.
  await page.route('**/assets/SettingsPage-*.js', (route) => route.fulfill({
    status: 200,
    contentType: 'application/javascript',
    body: 'export function SettingsPage() { throw new Error("forced e2e failure"); }',
  }));
  await page.goto('/#/settings');

  await expect(page.getByText('Something went wrong. Try again.')).toBeVisible();
  const send = page.getByRole('button', { name: 'Send diagnostics' });
  await expect(send).toBeVisible();
  // The boundary captured the failure itself, so the buffer is no longer empty and the button is enabled.
  await expect(page.getByText('1 error is kept on this device.')).toBeVisible();
  await expect(send).toBeEnabled();
});

test('the account section sends the captured errors and confirms', { tag: ['@client-diagnostics', '@account'] }, async ({ page, request }) => {
  await setEnabled(request, true);
  await page.goto('/#/settings/report-problem');
  await expect(page.getByRole('heading', { name: 'Report a problem', level: 1 })).toBeVisible();
  await expectGoodPage(page, 'Report a problem');

  const send = page.getByRole('button', { name: 'Send diagnostics' });
  await expect(send).toBeDisabled();
  await expect(page.getByText('No errors have been captured on this device yet.')).toBeVisible();

  // A real window error, captured by the installer main.tsx registered.
  await page.evaluate(() => { setTimeout(() => { throw new Error('E2E local diagnostic'); }, 0); });
  await expect(page.getByText('1 error is kept on this device.')).toBeVisible();
  await expect(send).toBeEnabled();

  const errorsRequest = page.waitForResponse((response) => response.url().endsWith('/api/client-diagnostics') && response.request().method() === 'POST');
  await send.click();
  expect((await errorsRequest).status()).toBe(204);
  await expect(page.getByText('Diagnostics sent. Thank you.')).toBeVisible();
  expect(await page.evaluate(() => localStorage.getItem('divehub.diagnostics.v1'))).toBeNull();
  await expect(page.getByText('No errors have been captured on this device yet.')).toBeVisible();
});

test('a switched-off instance explains why nothing can be sent', { tag: ['@client-diagnostics'] }, async ({ page }) => {
  await page.goto('/#/settings/report-problem');
  await expect(page.getByText('Client diagnostics are switched off on this Dive Hub. An admin can switch them on.')).toBeVisible();
  await expect(page.getByText('Nothing can be sent while an admin has client diagnostics switched off. Errors stay on this device.')).toBeVisible();
  await expect(page.getByRole('button', { name: 'Send diagnostics' })).toBeDisabled();
});
