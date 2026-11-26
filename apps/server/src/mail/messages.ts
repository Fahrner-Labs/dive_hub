// The two transactional messages Dive Hub sends (hosted plan §6.1): an invitation and a password reset. Plain text on
// purpose — the links are the message, and plain text never renders a token as a broken image or an untrusted button.
import type { MailMessage } from './mail.js';

const day = (at: Date) => at.toISOString().slice(0, 10);

export interface InvitationMailInput {
  to: string;
  url: string;
  expiresAt: Date;
}

export function invitationMail({ to, url, expiresAt }: InvitationMailInput): MailMessage {
  return {
    to,
    subject: 'Your Dive Hub invitation',
    text: [
      'You were invited to Dive Hub.',
      '',
      'Open this link to create your account:',
      url,
      '',
      `The link works once and expires on ${day(expiresAt)}.`,
      '',
      "If you weren't expecting this invitation, you can ignore this e-mail.",
    ].join('\n'),
  };
}

export interface PasswordResetMailInput {
  to: string;
  url: string;
  expiresAt: Date;
}

export function passwordResetMail({ to, url, expiresAt }: PasswordResetMailInput): MailMessage {
  return {
    to,
    subject: 'Reset your Dive Hub password',
    text: [
      'A new password was requested for your Dive Hub account.',
      '',
      'Open this link to choose a new password:',
      url,
      '',
      `The link works once and expires on ${day(expiresAt)}.`,
      '',
      "If you didn't ask for this, ignore this e-mail. Your password stays unchanged.",
    ].join('\n'),
  };
}
