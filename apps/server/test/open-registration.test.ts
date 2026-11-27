// Open registration (hosted plan §6.2): DIVEHUB_REGISTRATION_OPEN adds sign-up, e-mail verification and consent rows;
// unset keeps today's invite-only world where sign-up is not reachable at all.
import { eq } from 'drizzle-orm';
import { afterAll, beforeAll, beforeEach, describe, expect, it } from 'vitest';
import { consentOf, CONSENT_VERSIONS } from '../src/auth/consent.js';
import type { EmailConfig } from '../src/config.js';
import { diver, diverManagement, user, userConsent } from '../src/db/schema.js';
import type { MailFetch } from '../src/mail/resend.js';
import {
  BASE_URL, PASSWORD, cookieHeader, createTestApp, createTestDatabase, databaseReachable, signIn, type TestDatabase,
} from './support.js';

type App = Awaited<ReturnType<typeof createTestApp>>['app'];

const EMAIL: EmailConfig = {
  provider: 'resend', apiKey: 're_test_key', from: 'Dive Hub <noreply@dives.example.com>',
};

interface SentMail {
  url: string;
  body: { from: string; to: string[]; subject: string; text: string };
}

/** A Resend stand-in: records what would have gone out, so the test can read the verification link. */
function mailbox() {
  const mails: SentMail[] = [];
  const fetch: MailFetch = async (url, init) => {
    mails.push({ url, body: JSON.parse(init.body) as SentMail['body'] });
    return { status: 200, text: async () => '{"id":"mail_test"}' };
  };
  return { mails, fetch };
}

/** The request answers before the send finishes, so wait for the stand-in to have seen it. */
async function waitFor(check: () => boolean, timeout = 3000) {
  const start = Date.now();
  while (!check()) {
    if (Date.now() - start > timeout) throw new Error('timed out waiting for the mail stand-in');
    await new Promise((resolve) => setTimeout(resolve, 10));
  }
}

const linkOf = (text: string) => /https?:\/\/\S+/.exec(text)?.[0] ?? '';

const signUp = (app: App, payload: Record<string, unknown>) => app.inject({
  method: 'POST', url: '/api/auth/sign-up/email', headers: { origin: BASE_URL }, payload,
});

const accepted = { terms: true, privacy: true };

describe('consent payload', () => {
  it('records the versions in force when both are accepted', () => {
    expect(consentOf({ consent: accepted })).toEqual([
      { kind: 'terms', version: CONSENT_VERSIONS.terms },
      { kind: 'privacy', version: CONSENT_VERSIONS.privacy },
    ]);
  });

  it('refuses a missing, partial or non-boolean consent', () => {
    for (const body of [
      undefined, null, {}, { consent: {} }, { consent: { terms: true } }, { consent: { terms: true, privacy: 'yes' } },
      { consent: ['terms', 'privacy'] }, { acceptTerms: true }, 'consent',
    ]) {
      expect(consentOf(body), JSON.stringify(body)).toBeNull();
    }
  });
});

describe.skipIf(!(await databaseReachable()))('open registration', () => {
  const box = mailbox();
  let db: TestDatabase;
  let closedDb: TestDatabase;
  let open: Awaited<ReturnType<typeof createTestApp>>;
  let closed: Awaited<ReturnType<typeof createTestApp>>;

  beforeAll(async () => {
    db = await createTestDatabase();
    open = await createTestApp(db, {
      registrationOpen: true, rateLimit: false, email: EMAIL, mailFetch: box.fetch, mailUrl: 'https://resend.test/emails',
    });
    closedDb = await createTestDatabase();
    closed = await createTestApp(closedDb);
  });

  afterAll(async () => {
    await open?.app.close();
    await db?.drop();
    await closed?.app.close();
    await closedDb?.drop();
  });

  beforeEach(() => { box.mails.length = 0; });

  it('reports whether this instance accepts self-serve sign-ups', async () => {
    const of = async (app: App) => (await app.inject({ method: 'GET', url: '/api/registration' })).json();
    expect(await of(open.app)).toEqual({ open: true });
    expect(await of(closed.app)).toEqual({ open: false });
  });

  it('keeps sign-up unreachable with DIVEHUB_REGISTRATION_OPEN unset', async () => {
    const response = await signUp(closed.app, {
      email: 'closed@example.com', name: 'Closed', password: PASSWORD, consent: accepted,
    });
    expect(response.statusCode).toBe(404);
    expect(await closedDb.db.select().from(user).where(eq(user.email, 'closed@example.com'))).toHaveLength(0);
  });

  it('creates the User, their own Diver and consent rows, then verifies the mailed link into a session', async () => {
    const email = 'self-serve@example.com';
    const created = await signUp(open.app, { email, name: 'Self Serve', password: PASSWORD, consent: accepted });
    expect(created.statusCode).toBe(200);
    // requireEmailVerification: no session until the link is used.
    expect(created.json()).toMatchObject({ token: null, user: { email, emailVerified: false } });

    await waitFor(() => box.mails.length === 1);
    expect(box.mails[0]!.body.to).toEqual([email]);
    expect(box.mails[0]!.body.subject).toMatch(/verify/i);
    const link = linkOf(box.mails[0]!.body.text);
    expect(link).toContain('/api/auth/verify-email?token=');

    const [row] = await db.db.select().from(user).where(eq(user.email, email));
    expect(row).toMatchObject({ emailVerified: false });
    const management = await db.db.select().from(diverManagement).where(eq(diverManagement.userId, row!.id));
    expect(management).toHaveLength(1);
    const [own] = await db.db.select().from(diver).where(eq(diver.id, management[0]!.diverId));
    expect(own).toMatchObject({ name: 'Self Serve' });

    const consent = await db.db.select().from(userConsent).where(eq(userConsent.userId, row!.id));
    expect(consent.map((c) => ({ kind: c.kind, version: c.version })).sort((a, b) => a.kind.localeCompare(b.kind)))
      .toEqual([
        { kind: 'privacy', version: CONSENT_VERSIONS.privacy },
        { kind: 'terms', version: CONSENT_VERSIONS.terms },
      ]);
    for (const c of consent) expect(c.acceptedAt).toBeInstanceOf(Date);

    // Unverified accounts get no session.
    const early = await open.app.inject({
      method: 'POST', url: '/api/auth/sign-in/email', headers: { origin: BASE_URL },
      payload: { email, password: PASSWORD },
    });
    expect(early.statusCode).toBe(403);

    const url = new URL(link);
    const verified = await open.app.inject({
      method: 'GET', url: `${url.pathname}${url.search}`, headers: { origin: BASE_URL },
    });
    expect(verified.statusCode).toBe(302);
    const cookie = cookieHeader(verified);
    expect(cookie).toContain('session_token');

    const me = await open.app.inject({ method: 'GET', url: '/api/me', headers: { cookie } });
    expect(me.statusCode).toBe(200);
    expect(me.json()).toMatchObject({ user: { email }, ownDiver: { name: 'Self Serve' } });

    const [verifiedRow] = await db.db.select().from(user).where(eq(user.email, email));
    expect(verifiedRow!.emailVerified).toBe(true);
  });

  it('refuses a sign-up without consent and creates no User', async () => {
    for (const consent of [undefined, { terms: true }, { terms: 'yes', privacy: true }]) {
      const response = await signUp(open.app, {
        email: 'no-consent@example.com', name: 'No Consent', password: PASSWORD, ...(consent && { consent }),
      });
      expect(response.statusCode).toBe(400);
      expect(response.json().message).toMatch(/Terms of Service.*Privacy Policy/);
    }
    expect(await db.db.select().from(user).where(eq(user.email, 'no-consent@example.com'))).toHaveLength(0);
    await new Promise((resolve) => setTimeout(resolve, 100));
    expect(box.mails).toHaveLength(0);
  });

  it('resends the verification mail through the public resend path', async () => {
    const email = 'resend@example.com';
    expect((await signUp(open.app, { email, name: 'Resend', password: PASSWORD, consent: accepted })).statusCode).toBe(200);
    await waitFor(() => box.mails.length === 1);
    box.mails.length = 0;

    const resend = await open.app.inject({
      method: 'POST', url: '/api/auth/send-verification-email', headers: { origin: BASE_URL }, payload: { email },
    });
    expect(resend.statusCode).toBe(200);
    await waitFor(() => box.mails.length === 1);
    expect(linkOf(box.mails[0]!.body.text)).toContain('/api/auth/verify-email?token=');
  });

  it('lets an invited User sign in without a verification mail, as the admin vouched for the address', async () => {
    await createUserViaAdmin(open, 'open-admin@example.com', 'admin');
    const admin = await signIn(open.app, 'open-admin@example.com');

    const invitation = await open.app.inject({
      method: 'POST', url: '/api/invitations', headers: { cookie: admin, origin: BASE_URL },
      payload: { email: 'invited@example.com' },
    });
    expect(invitation.statusCode).toBe(201);
    const token = new URL(invitation.json().url).hash.replace('#/invite/', '');
    await waitFor(() => box.mails.some((mail) => /invitation/i.test(mail.body.subject)));
    box.mails.length = 0;

    const accepted = await open.app.inject({
      method: 'POST', url: '/api/invitations/accept', payload: { token, name: 'Invited', password: PASSWORD },
    });
    expect(accepted.statusCode).toBe(201);
    expect(accepted.json()).toMatchObject({ email: 'invited@example.com' });
    // No verification mail: the invitation was the proof of the address.
    await new Promise((resolve) => setTimeout(resolve, 100));
    expect(box.mails).toHaveLength(0);

    expect(await signIn(open.app, 'invited@example.com')).toContain('session_token');
  });
});

describe.skipIf(!(await databaseReachable()))('sign-up rate limit', () => {
  let db: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  const box = mailbox();

  beforeAll(async () => {
    db = await createTestDatabase();
    ctx = await createTestApp(db, {
      registrationOpen: true, rateLimit: false, email: EMAIL, mailFetch: box.fetch, mailUrl: 'https://resend.test/emails',
    });
  });
  afterAll(async () => {
    await ctx?.app.close();
    await db?.drop();
  });

  it('answers 429 on the 31st sign-up attempt from one IP within 15 minutes', async () => {
    const attempt = () => ctx.app.inject({
      method: 'POST', url: '/api/auth/sign-up/email', headers: { origin: BASE_URL }, payload: {},
    });
    for (let i = 0; i < 30; i++) expect((await attempt()).statusCode, `attempt ${i + 1}`).not.toBe(429);
    const blocked = await attempt();
    expect(blocked.statusCode).toBe(429);
    expect(Number(blocked.headers['retry-after'])).toBeGreaterThan(0);
  });
});

/** The admin API path that setup and invitation use; kept here so the test does not depend on a session cookie. */
async function createUserViaAdmin(ctx: Awaited<ReturnType<typeof createTestApp>>, email: string, role: 'user' | 'admin') {
  const { user: created } = await ctx.auth.api.createUser({
    body: { email, name: email.split('@')[0]!, password: PASSWORD, role, data: { emailVerified: true } },
  });
  return created;
}
