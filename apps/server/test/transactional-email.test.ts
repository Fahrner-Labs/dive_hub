// Transactional mail through the app (hosted plan §6.1): an invitation goes out by e-mail when configured, a User can
// request a reset link themselves, and both fall back to ADR 0012/0013's copy links when DIVEHUB_EMAIL_* is unset.
import { afterAll, beforeAll, beforeEach, describe, expect, it } from 'vitest';
import type { EmailConfig } from '../src/config.js';
import type { MailFetch } from '../src/mail/resend.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase,
} from './support.js';

type App = Awaited<ReturnType<typeof createTestApp>>['app'];

const EMAIL: EmailConfig = {
  provider: 'resend', apiKey: 're_test_key', from: 'Dive Hub <noreply@dives.example.com>',
};

interface SentMail {
  url: string;
  headers: Record<string, string>;
  body: { from: string; to: string[]; subject: string; text: string };
}

/** A Resend stand-in: it records what would have gone out and can be told to refuse the next send. */
function mailbox() {
  const mails: SentMail[] = [];
  const state: { failure?: string; attempts: number } = { attempts: 0 };
  const fetch: MailFetch = async (url, init) => {
    state.attempts += 1;
    if (state.failure) return { status: 422, text: async () => JSON.stringify({ message: state.failure }) };
    mails.push({ url, headers: init.headers, body: JSON.parse(init.body) as SentMail['body'] });
    return { status: 200, text: async () => '{"id":"mail_test"}' };
  };
  return { mails, state, fetch };
}

/** The request answers before the send finishes, so wait for the stand-in to have seen it. */
async function waitFor(check: () => boolean, timeout = 3000) {
  const start = Date.now();
  while (!check()) {
    if (Date.now() - start > timeout) throw new Error('timed out waiting for the mail stand-in');
    await new Promise((resolve) => setTimeout(resolve, 10));
  }
}

const invite = (app: App, cookie: string, email: string) => app.inject({
  method: 'POST', url: '/api/invitations', headers: { cookie, origin: BASE_URL }, payload: { email },
});

describe.skipIf(!(await databaseReachable()))('transactional e-mail', () => {
  const box = mailbox();
  let configuredDb: TestDatabase;
  let plainDb: TestDatabase;
  let configured: Awaited<ReturnType<typeof createTestApp>>;
  let plain: Awaited<ReturnType<typeof createTestApp>>;
  let admin: string;
  let adminId: string;

  beforeAll(async () => {
    configuredDb = await createTestDatabase();
    configured = await createTestApp(configuredDb, {
      email: EMAIL, mailFetch: box.fetch, mailUrl: 'https://resend.test/emails',
    });
    plainDb = await createTestDatabase();
    plain = await createTestApp(plainDb);
    adminId = (await createUser(configured.auth, 'mail-admin@example.com', 'admin')).id;
    admin = await signIn(configured.app, 'mail-admin@example.com');
  });

  afterAll(async () => {
    await configured?.app.close();
    await configuredDb?.drop();
    await plain?.app.close();
    await plainDb?.drop();
  });

  beforeEach(() => {
    box.mails.length = 0;
    box.state.attempts = 0;
    delete box.state.failure;
  });

  it('says on the public config route that it can send e-mail', async () => {
    const response = await configured.app.inject({ method: 'GET', url: '/api/email/config' });
    expect(response.statusCode).toBe(200);
    expect(response.json()).toEqual({ configured: true });
    expect((await plain.app.inject({ method: 'GET', url: '/api/email/config' })).json()).toEqual({ configured: false });
  });

  it('mails the invitation link and still answers it for copying, through the configured endpoint', async () => {
    const response = await invite(configured.app, admin, 'invited@example.com');
    expect(response.statusCode).toBe(201);
    const invitation = response.json();
    expect(invitation.emailSent).toBe(true);
    expect(invitation.url).toMatch(/\/#\/invite\/[\w-]+$/);

    expect(box.mails).toHaveLength(1);
    expect(box.mails[0]!.url).toBe('https://resend.test/emails');
    expect(box.mails[0]!.headers.authorization).toBe('Bearer re_test_key');
    expect(box.mails[0]!.body.from).toBe(EMAIL.from);
    expect(box.mails[0]!.body.to).toEqual(['invited@example.com']);
    expect(box.mails[0]!.body.text).toContain(invitation.url);
  });

  it('keeps the invitation link when the send fails, and says the mail did not go out', async () => {
    box.state.failure = 'The from address is not verified';
    const response = await invite(configured.app, admin, 'failed@example.com');
    expect(response.statusCode).toBe(201);
    expect(response.json().emailSent).toBe(false);
    expect(response.json().url).toMatch(/\/#\/invite\/[\w-]+$/);
    expect(box.mails).toHaveLength(0);
  });

  it('mails a reset link on a self-serve request, without revealing whether the address has an account', async () => {
    await createUser(configured.auth, 'reset-member@example.com');
    const result = await configured.app.inject({
      method: 'POST', url: '/api/password-resets/request', payload: { email: 'reset-member@example.com' },
    });
    expect(result.statusCode).toBe(204);
    await waitFor(() => box.mails.length === 1);
    expect(box.mails[0]!.body.to).toEqual(['reset-member@example.com']);
    expect(box.mails[0]!.body.text).toMatch(/password/i);

    const token = /#\/reset\/([\w-]+)/.exec(box.mails[0]!.body.text)?.[1];
    expect(token).toBeTruthy();
    const lookup = await configured.app.inject({
      method: 'POST', url: '/api/password-resets/lookup', payload: { token },
    });
    expect(lookup.statusCode).toBe(200);
    expect(lookup.json().email).toBe('reset-member@example.com');

    box.mails.length = 0;
    const unknown = await configured.app.inject({
      method: 'POST', url: '/api/password-resets/request', payload: { email: 'nobody@example.com' },
    });
    expect(unknown.statusCode).toBe(204);
    await new Promise((resolve) => setTimeout(resolve, 100));
    expect(box.mails).toHaveLength(0);
  });

  it('keeps an earlier reset link working when the new mail could not be sent', async () => {
    const first = await configured.app.inject({
      method: 'POST', url: '/api/password-resets/request', payload: { email: 'reset-member@example.com' },
    });
    expect(first.statusCode).toBe(204);
    await waitFor(() => box.mails.length === 1);
    const oldToken = /#\/reset\/([\w-]+)/.exec(box.mails[0]!.body.text)?.[1];
    expect(oldToken).toBeTruthy();

    box.mails.length = 0;
    box.state.failure = 'The from address is not verified';
    const second = await configured.app.inject({
      method: 'POST', url: '/api/password-resets/request', payload: { email: 'reset-member@example.com' },
    });
    expect(second.statusCode).toBe(204);
    await waitFor(() => box.state.attempts === 2);

    const lookup = await configured.app.inject({
      method: 'POST', url: '/api/password-resets/lookup', payload: { token: oldToken },
    });
    expect(lookup.statusCode).toBe(200);
  });

  it('sends no reset mail to a disabled User but answers the same', async () => {
    const memberId = (await createUser(configured.auth, 'disabled@example.com')).id;
    await configured.userAdmin.disable(adminId, memberId);
    const result = await configured.app.inject({
      method: 'POST', url: '/api/password-resets/request', payload: { email: 'disabled@example.com' },
    });
    expect(result.statusCode).toBe(204);
    await new Promise((resolve) => setTimeout(resolve, 100));
    expect(box.mails).toHaveLength(0);
  });

  it('stays copy-link only without DIVEHUB_EMAIL_*, with the admin reset link still available', async () => {
    await createUser(plain.auth, 'plain-admin@example.com', 'admin');
    const plainAdmin = await signIn(plain.app, 'plain-admin@example.com');
    const invitation = await invite(plain.app, plainAdmin, 'plain-invited@example.com');
    expect(invitation.statusCode).toBe(201);
    expect(invitation.json().url).toMatch(/\/#\/invite\/[\w-]+$/);
    expect(invitation.json().emailSent).toBeUndefined();

    const request = await plain.app.inject({
      method: 'POST', url: '/api/password-resets/request', payload: { email: 'plain-invited@example.com' },
    });
    expect(request.statusCode).toBe(404);

    const memberId = (await createUser(plain.auth, 'plain-member@example.com')).id;
    const reset = await plain.app.inject({
      method: 'POST', url: `/api/users/${memberId}/password-reset`, headers: { cookie: plainAdmin, origin: BASE_URL },
    });
    expect(reset.statusCode).toBe(201);
    expect(reset.json().url).toMatch(/\/#\/reset\/[\w-]+$/);
    expect(reset.json().emailSent).toBeUndefined();
  });
});
