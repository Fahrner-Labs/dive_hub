// The mail module (hosted plan §6.1): the invitation and reset messages a User reads, and the Resend sender behind the
// MailProvider seam. The sender is tested against a stand-in endpoint, so no real mail leaves the process.
import { describe, expect, it } from 'vitest';
import { invitationMail, passwordResetMail } from '../src/mail/messages.js';
import { createResendMailProvider, RESEND_URL, type MailFetch } from '../src/mail/resend.js';

const invitation = () => invitationMail({
  to: 'diver@example.com', url: 'https://dives.example.com/#/invite/abc123', expiresAt: new Date('2027-01-02T03:04:05Z'),
});

describe('invitation and reset messages', () => {
  it('names the recipient and carries the invitation link and its expiry', () => {
    const mail = invitation();
    expect(mail.to).toBe('diver@example.com');
    expect(mail.subject).toMatch(/invitation/i);
    expect(mail.text).toContain('https://dives.example.com/#/invite/abc123');
    expect(mail.text).toContain('2027-01-02');
    expect(mail.text).toMatch(/works once/i);
  });

  it('carries the reset link and says the password stays without it', () => {
    const mail = passwordResetMail({
      to: 'diver@example.com', url: 'https://dives.example.com/#/reset/xyz789', expiresAt: new Date('2027-01-02T03:04:05Z'),
    });
    expect(mail.to).toBe('diver@example.com');
    expect(mail.subject).toMatch(/password/i);
    expect(mail.text).toContain('https://dives.example.com/#/reset/xyz789');
    expect(mail.text).toMatch(/works once/i);
    expect(mail.text).toMatch(/password stays/i);
  });
});

describe('the Resend sender', () => {
  const from = 'Dive Hub <noreply@dives.example.com>';

  function sender(answer: { status: number; body: string }, calls: { url: string; init: Parameters<MailFetch>[1] }[], url?: string) {
    const fetch: MailFetch = async (target, init) => {
      calls.push({ url: target, init });
      return { status: answer.status, text: async () => answer.body };
    };
    return createResendMailProvider({ apiKey: 're_test_key', from, ...(url && { url }), fetch });
  }

  it('posts one message to Resend with the bearer key and the sender', async () => {
    const calls: { url: string; init: Parameters<MailFetch>[1] }[] = [];
    await sender({ status: 200, body: '{"id":"mail_1"}' }, calls)
      .sendMail({ to: 'diver@example.com', subject: 'Your Dive Hub invitation', text: 'Open the link' });

    expect(calls).toHaveLength(1);
    expect(calls[0]!.url).toBe(RESEND_URL);
    expect(calls[0]!.init.headers.authorization).toBe('Bearer re_test_key');
    expect(calls[0]!.init.headers['content-type']).toBe('application/json');
    expect(JSON.parse(calls[0]!.init.body)).toEqual({
      from, to: ['diver@example.com'], subject: 'Your Dive Hub invitation', text: 'Open the link',
    });
  });

  it('uses the endpoint it is given, so a test never reaches Resend', async () => {
    const calls: { url: string; init: Parameters<MailFetch>[1] }[] = [];
    await sender({ status: 200, body: '{"id":"mail_1"}' }, calls, 'https://resend.test/emails')
      .sendMail({ to: 'diver@example.com', subject: 's', text: 't' });
    expect(calls[0]!.url).toBe('https://resend.test/emails');
  });

  it('throws the provider reason on a refusal, never the API key', async () => {
    const calls: { url: string; init: Parameters<MailFetch>[1] }[] = [];
    const send = sender({ status: 422, body: '{"message":"The from address is not verified"}' }, calls)
      .sendMail({ to: 'diver@example.com', subject: 's', text: 't' });
    await expect(send).rejects.toThrow(/HTTP 422.*not verified/);
    await expect(send).rejects.not.toThrow(/re_test_key/);
  });
});
