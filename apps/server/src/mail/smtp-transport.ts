// The SMTP half of mail/ (nodemailer). config.ts has checked that the URL is smtp:// (STARTTLS) or smtps:// (TLS).
import nodemailer from 'nodemailer';
import type { MailTransport } from './mail-service.js';

export function createSmtpTransport(url: string): MailTransport {
  const transport = nodemailer.createTransport(url);
  return {
    send: async (mail) => {
      await transport.sendMail(mail);
    },
  };
}
