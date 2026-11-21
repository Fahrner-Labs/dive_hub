// Transactional e-mail: one way to queue an e-mail to a User, and one worker task that hands it to the SMTP relay
// (ADR 0010 for the job, config.ts for the relay). Without a relay, or for a User who opted out, a send is skipped and
// logged, never an error.
import { eq, sql } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { user } from '../db/schema.js';

/** The worker task that sends one queued e-mail (worker.ts). */
export const MAIL_SEND_TASK = 'mail_send';

/** How often the worker tries a mail the relay refused; a relay that is down comes back. */
const MAIL_ATTEMPTS = 5;

export interface MailMessage {
  subject: string;
  /** The reading copy; every client shows it. */
  text: string;
  /** Optional HTML part, for clients that render one. */
  html?: string;
}

/** What the transport gets: the message and its addresses. */
export interface OutgoingMail extends MailMessage {
  from: string;
  to: string;
}

/** The seam tests replace: one message to the relay. */
export interface MailTransport {
  send(mail: OutgoingMail): Promise<void>;
}

/** The relay and sender of an instance whose mail is on. */
export interface MailSettings {
  transport: MailTransport;
  from: string;
}

export interface MailLog {
  info(detail: object, message: string): void;
  warn(detail: object, message: string): void;
}

interface MailJob extends MailMessage {
  userId: string;
}

function field(job: Record<string, unknown>, key: string): string {
  const value = job[key];
  if (typeof value !== 'string' || value === '') throw new Error(`the mail job has no ${key}`);
  return value;
}

/** A queued job's payload; a malformed one fails here, clearly, as the other tasks' do. */
function mailJob(payload: unknown): MailJob {
  const job = (payload ?? {}) as Record<string, unknown>;
  if (job.html !== undefined && typeof job.html !== 'string') throw new Error('the mail job has an invalid html');
  return {
    userId: field(job, 'userId'), subject: field(job, 'subject'), text: field(job, 'text'),
    ...(typeof job.html === 'string' && { html: job.html }),
  };
}

export function createMailService({ db, settings, log }: {
  db: Db;
  /** Absent while DIVEHUB_SMTP_URL is unset: mail is off. */
  settings?: MailSettings | undefined;
  log: MailLog;
}) {
  async function recipient(userId: string) {
    const [account] = await db.select({ email: user.email, mailOptOut: user.mailOptOut }).from(user).where(eq(user.id, userId));
    return account;
  }

  return {
    /**
     * Queues one e-mail to a User in graphile-worker's table, like the other background jobs (ADR 0010). The address
     * and the opt-out are read now and again when the task sends, so an opted-out User gets nothing either way.
     */
    async sendToUser(userId: string, message: MailMessage): Promise<'queued' | 'skipped'> {
      if (!settings) {
        log.info({ userId, subject: message.subject }, 'e-mail is off; not sending');
        return 'skipped';
      }
      const account = await recipient(userId);
      if (!account) {
        log.warn({ userId, subject: message.subject }, 'no such User; not sending an e-mail');
        return 'skipped';
      }
      if (account.mailOptOut) {
        log.info({ userId, subject: message.subject }, 'the User opted out of e-mail');
        return 'skipped';
      }
      const job: MailJob = {
        userId, subject: message.subject, text: message.text,
        ...(message.html !== undefined && { html: message.html }),
      };
      await db.execute(
        sql`select graphile_worker.add_job(${MAIL_SEND_TASK}, ${JSON.stringify(job)}::json, max_attempts => ${MAIL_ATTEMPTS})`,
      );
      return 'queued';
    },

    /** The mail_send task's work; a refused relay throws so graphile-worker tries again. */
    async deliver(payload: unknown): Promise<'sent' | 'skipped'> {
      const job = mailJob(payload);
      if (!settings) {
        log.info({ userId: job.userId, subject: job.subject }, 'e-mail is off; not sending');
        return 'skipped';
      }
      const account = await recipient(job.userId);
      if (!account) {
        log.warn({ userId: job.userId, subject: job.subject }, 'no such User; not sending an e-mail');
        return 'skipped';
      }
      if (account.mailOptOut) {
        log.info({ userId: job.userId, subject: job.subject }, 'the User opted out of e-mail');
        return 'skipped';
      }
      await settings.transport.send({
        from: settings.from, to: account.email, subject: job.subject, text: job.text,
        ...(job.html !== undefined && { html: job.html }),
      });
      log.info({ userId: job.userId, subject: job.subject }, 'sent an e-mail');
      return 'sent';
    },
  };
}

export type MailService = ReturnType<typeof createMailService>;
