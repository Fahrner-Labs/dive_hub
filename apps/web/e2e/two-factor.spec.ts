// Two-factor authentication (FAH-54): setting it up in the account, turning it off, and completing the sign-in
// challenge with an authenticator code or a backup code. Each test invites its own User, so enabling the second factor
// never changes the seeded User or another test's state.
import { createHmac } from 'node:crypto';
import { expect, test, type APIRequestContext, type Browser } from '@playwright/test';
import { E2E_BASE_URL, expectGoodPage, headers, uniqueWord } from './support.ts';

const PASSWORD = 'a long enough test password';

/** The plugin puts the raw secret into the URI base32-encoded; decode it to the bytes an authenticator app uses. */
function base32Decode(encoded: string): Buffer {
  const alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ234567';
  let bits = 0;
  let value = 0;
  const bytes: number[] = [];
  for (const char of encoded.toUpperCase().replace(/=+$/, '')) {
    const index = alphabet.indexOf(char);
    if (index < 0) throw new Error(`not a base32 secret: ${encoded}`);
    value = (value << 5) | index;
    bits += 5;
    if (bits >= 8) {
      bits -= 8;
      bytes.push((value >>> bits) & 0xff);
    }
  }
  return Buffer.from(bytes);
}

/** The current 6-digit code for an `otpauth://` URI (RFC 6238, SHA-1, 30 s), as an authenticator app computes it. */
function totpCode(uri: string): string {
  const secret = new URL(uri).searchParams.get('secret');
  if (!secret) throw new Error(`the TOTP URI carries no secret: ${uri}`);
  const counter = Buffer.alloc(8);
  counter.writeBigUInt64BE(BigInt(Math.floor(Date.now() / 30_000)));
  const hmac = createHmac('sha1', base32Decode(secret)).update(counter).digest();
  const offset = hmac[hmac.length - 1]! & 0x0f;
  const value = ((hmac[offset]! & 0x7f) << 24) | (hmac[offset + 1]! << 16) | (hmac[offset + 2]! << 8) | hmac[offset + 3]!;
  return String(value % 1_000_000).padStart(6, '0');
}

const wrongCode = (uri: string) => (totpCode(uri) === '000000' ? '000001' : '000000');

/** A fresh User, signed in in its own browser context, so the second factor belongs to nobody else. */
async function invitedUser(browser: Browser, request: APIRequestContext, name: string, viewport = { width: 1280, height: 900 }) {
  // Unique e-mail and display name: a retry invites another User, and the admin page's row menus are named by name.
  const unique = uniqueWord();
  const email = `${unique}-${name.toLowerCase()}@example.com`;
  const invitation = await (await request.post('/api/invitations', { data: { email }, headers })).json() as { url: string };
  const context = await browser.newContext({
    baseURL: E2E_BASE_URL, extraHTTPHeaders: headers, viewport, storageState: { cookies: [], origins: [] },
  });
  const accepted = await context.request.post('/api/invitations/accept', {
    data: { token: invitation.url.split('/invite/')[1], name: `${name} ${unique}`, password: PASSWORD },
  });
  if (!accepted.ok()) throw new Error(`accepting the invitation failed: ${accepted.status()} ${await accepted.text()}`);
  return { context, email };
}

test('turns two-factor authentication on with an authenticator app, shows the backup codes once, and turns it off', { tag: ['@account'] }, async ({ browser, request }) => {
  const { context } = await invitedUser(browser, request, 'Enable');
  try {
    const page = await context.newPage();
    await page.goto('/#/settings/security');
    const panel = page.locator('.panel').filter({ has: page.getByRole('heading', { name: 'Two-factor authentication' }) });

    await panel.getByRole('button', { name: 'Set up two-factor authentication' }).click();
    await panel.getByRole('textbox', { name: 'Current password', exact: true }).fill(PASSWORD);
    await panel.getByRole('button', { name: 'Continue' }).click();

    // The URI is drawn as a QR code and offered as text: an authenticator app can scan it or take the link.
    await expect(panel.getByRole('img', { name: 'QR code for the authenticator set-up link' })).toBeVisible();
    const uri = await panel.getByRole('textbox', { name: 'Set-up link' }).inputValue();
    expect(uri).toMatch(/^otpauth:\/\/totp\//);

    // The factor is off until the first code is verified.
    const before = await (await context.request.get('/api/auth/get-session')).json() as { user: { twoFactorEnabled: boolean } };
    expect(before.user.twoFactorEnabled).toBe(false);

    await panel.getByLabel('Authenticator code').fill(totpCode(uri));
    await panel.getByRole('button', { name: 'Turn on two-factor authentication' }).click();

    // The backup codes are shown now, and only now.
    await expect(panel.getByRole('heading', { name: 'Backup codes' })).toBeVisible();
    await expect(panel.getByRole('list', { name: 'Backup codes' }).getByRole('listitem')).toHaveCount(10);
    await expect(panel.getByRole('button', { name: 'Copy backup codes' })).toBeVisible();
    const after = await (await context.request.get('/api/auth/get-session')).json() as { user: { twoFactorEnabled: boolean } };
    expect(after.user.twoFactorEnabled).toBe(true);

    await panel.getByRole('button', { name: 'Done' }).click();
    await expect(panel.getByText('Two-factor authentication is on.')).toBeVisible();

    // Turning it off asks the password and a current code, not a second password.
    await panel.getByRole('textbox', { name: 'Current password', exact: true }).fill(PASSWORD);
    await panel.getByLabel('Authenticator code').fill(totpCode(uri));
    await panel.getByRole('button', { name: 'Turn off two-factor authentication' }).click();
    await expect(panel.getByRole('button', { name: 'Set up two-factor authentication' })).toBeVisible();
    const off = await (await context.request.get('/api/auth/get-session')).json() as { user: { twoFactorEnabled: boolean } };
    expect(off.user.twoFactorEnabled).toBe(false);
  } finally {
    await context.close();
  }
});

test('asks a password sign-in for the second factor and takes a backup code', { tag: ['@account'] }, async ({ browser, request }) => {
  const { context: enrolled, email } = await invitedUser(browser, request, 'Challenge');
  let uri: string;
  let backupCode: string;
  try {
    const started = await enrolled.request.post('/api/auth/two-factor/enable', { data: { password: PASSWORD, method: 'totp' } });
    expect(started.ok()).toBe(true);
    const setup = await started.json() as { totpURI: string; backupCodes: string[] };
    uri = setup.totpURI;
    backupCode = setup.backupCodes[0]!;
    expect((await enrolled.request.post('/api/auth/two-factor/verify-totp', { data: { code: totpCode(uri) } })).ok()).toBe(true);
    expect((await enrolled.request.post('/api/auth/sign-out', { data: {} })).ok()).toBe(true);
  } finally {
    await enrolled.close();
  }

  const context = await browser.newContext({
    baseURL: E2E_BASE_URL, extraHTTPHeaders: headers, viewport: { width: 390, height: 844 },
    storageState: { cookies: [], origins: [] },
  });
  try {
    const page = await context.newPage();
    await page.goto('/#/');
    await page.getByLabel('E-mail').fill(email);
    await page.getByRole('textbox', { name: 'Password', exact: true }).fill(PASSWORD);
    await page.getByRole('button', { name: 'Sign in', exact: true }).click();

    // No second password: the password sign-in answers with a challenge and the code field.
    await expect(page.getByText('Enter the 6-digit code from your authenticator app.')).toBeVisible();
    await expect(page.getByRole('textbox', { name: 'Password', exact: true })).toHaveCount(0);
    await expectGoodPage(page, 'Sign in');

    await page.getByLabel('Authenticator code').fill(wrongCode(uri));
    await page.getByRole('button', { name: 'Verify' }).click();
    await expect(page.getByText('That two-factor code is wrong or has expired.')).toBeVisible();

    await page.getByText('Use a backup code', { exact: true }).click();
    await expect(page.getByText('Enter one of your backup codes.')).toBeVisible();
    await page.getByLabel('Backup code').fill(backupCode);
    await page.getByRole('button', { name: 'Verify' }).click();

    // Signed in: the logbook replaces the sign-in page.
    await expect(page.getByRole('heading', { level: 1 })).toHaveText('Logbook');
    expect((await context.request.get('/api/me')).status()).toBe(200);
  } finally {
    await context.close();
  }
});
