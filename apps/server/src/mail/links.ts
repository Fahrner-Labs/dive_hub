// The links Dive Hub mails (hosted plan §6.1). They live in the URL fragment like the copy links (ADR 0012, 0013):
// browsers never send a fragment to the server or in a Referer.
import type { MailProvider } from './mail.js';
import { invitationMail, passwordResetMail } from './messages.js';

export interface MailLinks {
  invitation(input: { to: string; token: string; expiresAt: Date }): Promise<void>;
  passwordReset(input: { to: string; token: string; expiresAt: Date }): Promise<void>;
}

export function createMailLinks({ mailer, baseUrl }: { mailer: MailProvider; baseUrl: string }): MailLinks {
  return {
    invitation: ({ to, token, expiresAt }) =>
      mailer.sendMail(invitationMail({ to, url: `${baseUrl}/#/invite/${token}`, expiresAt })),
    passwordReset: ({ to, token, expiresAt }) =>
      mailer.sendMail(passwordResetMail({ to, url: `${baseUrl}/#/reset/${token}`, expiresAt })),
  };
}
