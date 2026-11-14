// Two-factor authentication (ADR 0012's later slice): enrolment, the sign-in challenge, backup codes and disabling.
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { twoFactor, user } from '../src/db/schema.js';
import {
  BASE_URL, PASSWORD, cookieHeader, createTestApp, createTestDatabase, createUser, databaseReachable, signIn,
  type TestDatabase,
} from './support.js';

const online = await databaseReachable();

describe.skipIf(!online)('two-factor authentication', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  beforeAll(async () => {
    t = await createTestDatabase();
    // The plugin limits every /two-factor/ path to 3 per 10 s; these tests exercise the flows, not the limiter.
    ctx = await createTestApp(t, { rateLimit: false });
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  const post = (url: string, payload: object, cookie?: string) => ctx.app.inject({
    method: 'POST',
    url,
    headers: { origin: BASE_URL, ...(cookie ? { cookie } : {}) },
    payload,
  });

  /** The plugin puts the raw secret into the authenticator URI base32-encoded; generate codes from the raw value. */
  const totpSecret = (uri: string) => {
    const encoded = new URL(uri).searchParams.get('secret');
    if (!encoded) throw new Error(`the TOTP URI carries no secret: ${uri}`);
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
    return Buffer.from(bytes).toString('utf8');
  };
  const currentCode = async (uri: string) =>
    (await ctx.auth.api.generateTOTP({ body: { secret: totpSecret(uri) } })).code;
  const wrongCode = async (uri: string) => {
    const code = await currentCode(uri);
    return code === '000000' ? '000001' : '000000';
  };

  /** Enrols a User the way the account page does: enable, then confirm the first authenticator code. */
  async function enrol(email: string) {
    await createUser(ctx.auth, email);
    const cookie = await signIn(ctx.app, email);
    const started = await post('/api/auth/two-factor/enable', { password: PASSWORD }, cookie);
    expect(started.statusCode).toBe(200);
    const { totpURI, backupCodes } = started.json() as { totpURI: string; backupCodes: string[] };
    const verified = await post('/api/auth/two-factor/verify-totp', { code: await currentCode(totpURI) }, cookie);
    expect(verified.statusCode).toBe(200);
    return { cookie: cookieHeader(verified), totpURI, backupCodes };
  }

  const passwordSignIn = (email: string, password = PASSWORD) =>
    post('/api/auth/sign-in/email', { email, password });

  it('does not switch 2FA on until the first authenticator code is verified', async () => {
    await createUser(ctx.auth, 'pending@example.com');
    const cookie = await signIn(ctx.app, 'pending@example.com');
    expect((await post('/api/auth/two-factor/enable', { password: PASSWORD }, cookie)).statusCode).toBe(200);

    const [row] = await t.db.select({ enabled: user.twoFactorEnabled }).from(user).where(eq(user.email, 'pending@example.com'));
    expect(row!.enabled).not.toBe(true);
    const plain = await passwordSignIn('pending@example.com');
    expect(plain.statusCode).toBe(200);
    expect(plain.json()).not.toMatchObject({ twoFactorRedirect: true });
    expect((await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie: cookieHeader(plain) } })).statusCode).toBe(200);
  });

  it('answers a password sign-in with a challenge, not a session, and takes the authenticator code', async () => {
    const { totpURI } = await enrol('totp@example.com');

    const challenge = await passwordSignIn('totp@example.com');
    expect(challenge.statusCode).toBe(200);
    expect(challenge.json()).toMatchObject({ twoFactorRedirect: true, twoFactorMethods: ['totp'] });
    const challengeCookie = cookieHeader(challenge);
    expect((await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie: challengeCookie } })).statusCode).toBe(401);

    expect((await post('/api/auth/two-factor/verify-totp', { code: await wrongCode(totpURI) }, challengeCookie)).statusCode).toBe(401);
    const verified = await post('/api/auth/two-factor/verify-totp', { code: await currentCode(totpURI) }, challengeCookie);
    expect(verified.statusCode).toBe(200);
    const me = await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie: cookieHeader(verified) } });
    expect(me.statusCode).toBe(200);
    expect(me.json()).toMatchObject({ user: { email: 'totp@example.com' } });
  });

  it('stores the TOTP secret and the backup codes encrypted', async () => {
    const { totpURI, backupCodes } = await enrol('encrypted@example.com');
    const [row] = await t.db.select({ secret: twoFactor.secret, backupCodes: twoFactor.backupCodes }).from(twoFactor)
      .innerJoin(user, eq(user.id, twoFactor.userId)).where(eq(user.email, 'encrypted@example.com'));
    expect(row!.secret).not.toContain(totpSecret(totpURI));
    for (const code of backupCodes) expect(row!.backupCodes).not.toContain(code);
  });

  it('takes each backup code once', async () => {
    const { backupCodes } = await enrol('backup@example.com');
    const first = backupCodes[0]!;

    const challenge = await passwordSignIn('backup@example.com');
    const used = await post('/api/auth/two-factor/verify-backup-code', { code: first }, cookieHeader(challenge));
    expect(used.statusCode).toBe(200);
    expect((await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie: cookieHeader(used) } })).statusCode).toBe(200);
    expect((await post('/api/auth/sign-out', {}, cookieHeader(used))).statusCode).toBe(200);

    const again = await passwordSignIn('backup@example.com');
    expect((await post('/api/auth/two-factor/verify-backup-code', { code: first }, cookieHeader(again))).statusCode).toBe(401);
    expect((await post('/api/auth/two-factor/verify-backup-code', { code: backupCodes[1]! }, cookieHeader(again))).statusCode).toBe(200);
  });

  it('disables only with the password and a current code, then allows plain sign-in', async () => {
    const { cookie, totpURI, backupCodes } = await enrol('disable@example.com');

    const wrong = await post('/api/me/two-factor/disable', { password: PASSWORD, code: await wrongCode(totpURI) }, cookie);
    expect(wrong.statusCode).toBe(401);
    expect(wrong.json()).toMatchObject({ code: 'two_factor_code_invalid' });

    const wrongPassword = await post('/api/me/two-factor/disable', { password: 'not the right password', code: backupCodes[0]! }, cookie);
    expect(wrongPassword.statusCode).toBe(400);
    expect(wrongPassword.json()).toMatchObject({ code: 'two_factor_password_invalid' });

    // The wrong password did not spend the backup code.
    const disabled = await post('/api/me/two-factor/disable', { password: PASSWORD, code: backupCodes[0]! }, cookie);
    expect(disabled.statusCode).toBe(200);
    expect(disabled.json()).toEqual({ status: true });

    await post('/api/auth/sign-out', {}, cookieHeader(disabled));
    const plain = await passwordSignIn('disable@example.com');
    expect(plain.statusCode).toBe(200);
    expect(plain.json()).not.toMatchObject({ twoFactorRedirect: true });
    expect((await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie: cookieHeader(plain) } })).statusCode).toBe(200);
  });

  it('asks a 2FA User for the second factor after a reset link, instead of signing in', async () => {
    await enrol('reset@example.com');
    const [row] = await t.db.select({ id: user.id }).from(user).where(eq(user.email, 'reset@example.com'));
    const { token } = await ctx.passwordResets.issue(row!.id, row!.id);

    const reset = await post('/api/password-resets/complete', { token, password: 'a new correct horse battery staple' });
    expect(reset.statusCode).toBe(200);
    expect(reset.json()).toMatchObject({ twoFactorRedirect: true });
    expect((await ctx.app.inject({ method: 'GET', url: '/api/me', headers: { cookie: cookieHeader(reset) } })).statusCode).toBe(401);
  });

  it('keeps the endpoints it does not use off HTTP', async () => {
    expect((await post('/api/auth/two-factor/enable', { password: PASSWORD })).statusCode).toBe(401);

    await createUser(ctx.auth, 'off@example.com');
    const cookie = await signIn(ctx.app, 'off@example.com');
    expect((await post('/api/auth/two-factor/disable', { password: PASSWORD }, cookie)).statusCode).toBe(404);
    expect((await post('/api/auth/two-factor/generate-backup-codes', { password: PASSWORD }, cookie)).statusCode).toBe(404);
  });
});
