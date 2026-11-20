// The admin support bundle page (FAH-291): an admin builds the redacted archive on demand, reviews the summary, and
// downloads it. Someone who is not an admin is refused, like every Administration page.
import { expect, test } from '@playwright/test';
import { E2E_BASE_URL, expectGoodPage, headers } from './support.ts';

test('an admin creates a support bundle, reviews the summary, and downloads it', { tag: ['@support-bundle'] }, async ({ page }) => {
  await page.goto('/#/settings/admin/support-bundle');
  await expect(page.getByRole('heading', { name: 'Support bundle', level: 1 })).toBeVisible();
  await expectGoodPage(page, 'Support bundle');

  // Nothing is built before the admin asks; the preview then reports what the archive would hold.
  await expect(page.getByRole('link', { name: 'Download bundle' })).toHaveCount(0);
  await page.getByRole('button', { name: 'Create support bundle' }).click();
  await expect(page.getByText('Application version')).toBeVisible();
  await expect(page.getByText('Schema version')).toBeVisible();
  await expect(page.getByText('Failed jobs')).toBeVisible();
  await expect(page.getByText('Database latency')).toBeVisible();

  // The archive leaves as a file, named by the server's Content-Disposition.
  const download = page.waitForEvent('download');
  await page.getByRole('link', { name: 'Download bundle' }).click();
  const archive = await download;
  expect(archive.suggestedFilename()).toMatch(/^divehub-support-bundle-\d{8}-\d{6}\.tar\.gz$/);
  expect(await archive.path()).toBeTruthy();
});

test('someone not an admin is refused the support bundle page', { tag: ['@support-bundle'] }, async ({ browser }) => {
  const context = await browser.newContext({
    baseURL: E2E_BASE_URL, storageState: { cookies: [], origins: [] }, viewport: { width: 1280, height: 900 },
  });
  try {
    const signedIn = await context.request.post('/api/auth/sign-in/email', {
      data: { email: 'bartholomaeus.oberhuber@example.com', password: 'a long enough test password' }, headers,
    });
    expect(signedIn.ok()).toBe(true);
    const page = await context.newPage();
    await page.goto('/#/settings/admin/support-bundle');
    await expect(page.getByRole('heading', { name: 'Support bundle', level: 1 })).toBeVisible();
    await expect(page.getByText('Only admins can do this.')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Create support bundle' })).toHaveCount(0);
  } finally {
    await context.close();
  }
});
