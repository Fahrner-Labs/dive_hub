// The admin backups page (plan FAH-175): a manual backup runs to `ready`, its archive downloads, and the scheduled
// setting saves. The e2e server has a real database and pg_dump on CI, so the backup is a real one.
import { expect, test } from '@playwright/test';
import { expectGoodPage } from './support.ts';

// A valid X25519 recipient for the schedule form; it is public and belongs to no one here.
const RECIPIENT = 'age18tnm29sxy6dxwxkuzkj93uyl48xld8mtjxpqrjsx5pkk4tcvwvesrhqj9h';

test('an admin takes a manual backup, downloads it, and saves a schedule', { tag: ['@admin'] }, async ({ page }) => {
  await page.goto('/#/settings/admin/backups');
  await expect(page.getByRole('heading', { name: 'Backups', level: 1 })).toBeVisible();
  await expectGoodPage(page, 'Backups');

  // A manual run: the passphrase is used for this run only.
  await page.getByRole('textbox', { name: 'Backup passphrase' }).fill('correct horse battery staple');
  await page.getByRole('button', { name: 'Create backup' }).click();
  const run = page.getByRole('region', { name: 'Backup runs' }).locator('tbody tr').first();
  await expect(run).toContainText('Manual');
  await expect(run).toContainText('ready', { timeout: 30_000 });

  // The ready archive leaves as a file, named by the server.
  const download = page.waitForEvent('download');
  await run.getByRole('link', { name: /^Download the backup from/ }).click();
  const archive = await download;
  expect(archive.suggestedFilename()).toMatch(/^divehub-\d{4}-\d{2}-\d{2}-[0-9a-f-]+\.divehub-backup$/);
  expect(await archive.path()).toBeTruthy();

  // The schedule saves, and reads back after a reload.
  const saveSchedule = () => Promise.all([
    page.waitForResponse((response) => response.url().endsWith('/api/admin/backups/settings')
      && response.request().method() === 'PUT' && response.ok()),
    page.getByRole('button', { name: 'Save schedule' }).click(),
  ]);
  await page.getByText('Back up on a schedule', { exact: true }).click();
  await page.getByRole('textbox', { name: 'Every (hours)' }).fill('12');
  await page.getByRole('textbox', { name: 'Runs to keep' }).fill('4');
  await page.getByRole('textbox', { name: 'Recipient public key' }).fill(RECIPIENT);
  await saveSchedule();
  await expect(page.getByRole('checkbox', { name: 'Back up on a schedule' })).toBeChecked();

  await page.reload();
  await expect(page.getByRole('checkbox', { name: 'Back up on a schedule' })).toBeChecked();
  await expect(page.getByRole('textbox', { name: 'Every (hours)' })).toHaveValue('12');
  await expect(page.getByRole('textbox', { name: 'Runs to keep' })).toHaveValue('4');
  await expect(page.getByRole('textbox', { name: 'Recipient public key' })).toHaveValue(RECIPIENT);

  // Leave none behind for the next test on this server: a schedule would queue runs of its own.
  await page.getByText('Back up on a schedule', { exact: true }).click();
  await saveSchedule();
  await expect(page.getByRole('checkbox', { name: 'Back up on a schedule' })).not.toBeChecked();
});
