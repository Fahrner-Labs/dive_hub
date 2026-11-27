// Open registration (hosted plan §6.2): the signup page, the mailed verification landing, the consent requirement and
// the legal shells' links. The e2e server runs with DIVEHUB_REGISTRATION_OPEN and keeps its mail in /e2e/mail (FAH-533).
import { randomUUID } from 'node:crypto';
import { expect, test } from '@playwright/test';

// Every test here is signed out; the server's seeded session would otherwise skip the auth pages.
test.use({ storageState: { cookies: [], origins: [] } });

const PASSWORD = 'a long enough test password';

async function signUp(page: import('@playwright/test').Page, email: string, consent = true) {
  await page.goto('/#/signup');
  await expect(page.getByRole('heading', { name: 'Create your account', level: 1 })).toBeVisible();
  await page.getByLabel('Your name').fill('Self Serve');
  await page.getByLabel('E-mail').fill(email);
  await page.getByRole('textbox', { name: 'Password' }).fill(PASSWORD);
  if (consent) {
    const checkbox = page.getByRole('checkbox', { name: /Terms of Service/ });
    await checkbox.click({ force: true });
    await expect(checkbox).toBeChecked();
  }
  await page.getByRole('button', { name: 'Create account' }).click();
}

test('signs up, opens the mailed link and lands in the logbook', { tag: ['@account'] }, async ({ page, request }) => {
  const email = `self-serve-${randomUUID()}@example.com`;
  await signUp(page, email);
  await expect(page.getByRole('heading', { name: 'Check your e-mail', level: 1 })).toBeVisible();
  await expect(page.getByText(email)).toBeVisible();

  const mailOf = async () => (await (await request.get(`/e2e/mail?to=${encodeURIComponent(email)}&subject=verify`)).json()) as { text: string } | null;
  await expect.poll(async () => (await mailOf()) !== null).toBe(true);
  const link = /https?:\/\/\S+/.exec((await mailOf())!.text)?.[0];
  expect(link, 'the verification mail carries a link').toContain('/api/auth/verify-email?token=');

  await page.goto(link!);
  await expect(page.getByRole('heading', { name: 'Logbook', level: 1 })).toBeVisible();
  await expect(page).toHaveURL(/#\/$/);
});

test('does not create an account without accepting the terms', { tag: ['@account'] }, async ({ page }) => {
  const email = `no-consent-${randomUUID()}@example.com`;
  await signUp(page, email, false);
  await expect(page.getByText('Accept the Terms of Service and the Privacy Policy to create an account.')).toBeVisible();
  await expect(page.getByRole('heading', { name: 'Check your e-mail' })).toHaveCount(0);
});

test('reaches both legal pages from signup and from the footer', { tag: ['@account'] }, async ({ page }) => {
  await page.goto('/#/signup');
  await page.getByRole('main').getByRole('link', { name: 'Terms of Service' }).click();
  await expect(page.getByRole('heading', { name: 'Terms of Service', level: 1 })).toBeVisible();
  await expect(page.getByRole('heading', { name: 'Acceptable use' })).toBeVisible();

  await page.getByRole('contentinfo').getByRole('link', { name: 'Privacy Policy' }).click();
  await expect(page.getByRole('heading', { name: 'Privacy Policy', level: 1 })).toBeVisible();
  await expect(page.getByText('Hetzner')).toBeVisible();
  await expect(page.getByText('Paddle')).toBeVisible();
  await expect(page.getByText('Resend')).toBeVisible();
});

test('keeps the invite-only sign-in when the server reports registration closed', { tag: ['@account'] }, async ({ page }) => {
  await page.route('**/api/registration', (route) => route.fulfill({ json: { open: false } }));
  await page.goto('/');
  await expect(page.getByRole('heading', { name: 'Sign in', level: 1 })).toBeVisible();
  await expect(page.getByText('No account? Ask the admin of this Dive Hub for an invitation.')).toBeVisible();
  await expect(page.getByRole('link', { name: /Create an account/ })).toHaveCount(0);

  await page.goto('/#/signup');
  await expect(page.getByRole('heading', { name: 'Sign in', level: 1 })).toBeVisible();
});

test('a failed verification link offers a new one', { tag: ['@account'] }, async ({ page }) => {
  const callback = encodeURIComponent('/#/verify-email');
  await page.goto(`/api/auth/verify-email?token=not-a-token&callbackURL=${callback}`);
  await expect(page.getByRole('heading', { name: 'Confirm your e-mail', level: 1 })).toBeVisible();
  await expect(page.getByText('This confirmation link is invalid or has expired.')).toBeVisible();
  await expect(page.getByRole('textbox', { name: 'E-mail' })).toBeVisible();
  await expect(page.getByRole('button', { name: 'Send a new link' })).toBeVisible();
});
