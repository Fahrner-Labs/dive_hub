// The setup screen (plan FAH-175): a fresh instance is created with the first admin, or restored from a backup archive
// uploaded in the browser or placed in /data/restore/ on the server. The server's setup route is covered by
// apps/server/test/setup-restore.test.ts; here the screen is driven against mocked answers because the browser test's
// server is already set up.
import { expect, test } from '@playwright/test';

test.use({ storageState: { cookies: [], origins: [] } });

test.beforeEach(async ({ page }) => {
  await page.route('**/api/setup', (route) => route.fulfill({ json: { needed: true } }));
});

test('offers starting fresh or restoring, and the fresh form is unchanged', { tag: ['@account'] }, async ({ page }) => {
  await page.goto('/#/setup');
  await expect(page.getByRole('heading', { name: 'Set up Dive Hub', level: 1 })).toBeVisible();
  await expect(page.getByRole('radio', { name: 'Start fresh' })).toBeChecked();
  await expect(page.getByLabel('Setup token')).toBeVisible();
  await expect(page.getByLabel('Name')).toBeVisible();
  await expect(page.getByLabel('E-mail')).toBeVisible();
  await expect(page.getByRole('textbox', { name: 'Password' })).toBeVisible();

  await page.getByText('Restore from backup', { exact: true }).click();
  await expect(page.getByRole('textbox', { name: 'Backup passphrase' })).toBeVisible();
  await expect(page.getByRole('button', { name: 'Choose archive' })).toBeVisible();
  await expect(page.getByRole('button', { name: 'Restore this instance' })).toBeVisible();

  await page.getByText('Start fresh', { exact: true }).click();
  await expect(page.getByLabel('Name')).toBeVisible();
  await expect(page.getByRole('textbox', { name: 'Backup passphrase' })).toHaveCount(0);
});

test('restores from an archive placed in /data/restore/ and says to restart the server', { tag: ['@account'] }, async ({ page }) => {
  await page.route('**/api/setup/restore/archives', (route) => route.fulfill({ json: { archives: [{ name: 'nightly.divehub-backup', bytes: 12_345 }] } }));
  const sent: string[] = [];
  await page.route('**/api/setup/restore', async (route) => {
    sent.push(route.request().postData() ?? '');
    await route.fulfill({ json: { restored: true, createdAt: '2026-11-12T10:00:00.000Z', migrationHead: '0056_keen_maelstrom' } });
  });

  await page.goto('/#/setup');
  await page.getByText('Restore from backup', { exact: true }).click();
  await expect(page.getByText('nightly.divehub-backup')).toBeVisible();
  await page.getByLabel('Setup token').fill('a-setup-token-long-enough');
  await page.getByRole('textbox', { name: 'Backup passphrase' }).fill('the backup passphrase');
  await page.getByRole('button', { name: 'Restore this instance' }).click();

  await expect(page.getByText(/The backup was restored/)).toBeVisible();
  await expect(page.getByText(/docker compose restart app/)).toBeVisible();
  expect(sent).toHaveLength(1);
  expect(sent[0]).toContain('name="placed"');
  expect(sent[0]).toContain('nightly.divehub-backup');
  expect(sent[0]).not.toContain('name="file"');
});
