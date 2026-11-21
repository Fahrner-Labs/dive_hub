// The transactional e-mail service and its worker task: queued like the other jobs, addressed afresh from the User,
// skipped when mail is off or the User opted out, and retried when the relay refuses.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { sql } from 'drizzle-orm';
import { createMailService, MAIL_SEND_TASK, type MailTransport, type OutgoingMail } from '../src/mail/mail-service.js';
import { createProfileService } from '../src/users/profile-service.js';
import { createTestAuth, createTestDatabase, createUser, databaseReachable, type TestDatabase } from './support.js';

describe.skipIf(!(await databaseReachable()))('transactional e-mail', () => {
  let t: TestDatabase;
  let profiles: ReturnType<typeof createProfileService>;
  let annId: string;
  let bobId: string;
  const sent: OutgoingMail[] = [];
  const logs: string[] = [];
  const log = { info: (_detail: object, message: string) => logs.push(message), warn: (_detail: object, message: string) => logs.push(message) };
  const transport: MailTransport = { send: async (message) => { sent.push(message); } };
  const settings = { transport, from: 'Dive Hub <no-reply@example.com>' };

  const jobs = async () => (await t.db.execute(sql`
    select jobs.payload, jobs.max_attempts
    from graphile_worker._private_jobs jobs
    join graphile_worker._private_tasks tasks on tasks.id = jobs.task_id
    where tasks.identifier = ${MAIL_SEND_TASK}
  `)).rows as {
    payload: { userId: string; subject: string; text: string; html?: string }; max_attempts: number;
  }[];

  beforeAll(async () => {
    t = await createTestDatabase();
    const auth = createTestAuth(t.db);
    profiles = createProfileService(t.db);
    annId = (await createUser(auth, 'ann@example.com')).id;
    bobId = (await createUser(auth, 'bob@example.com')).id;
  });
  afterAll(async () => {
    await t?.drop();
  });

  it('queues one job per e-mail, without the address, and the task sends it to the User', async () => {
    const service = createMailService({ db: t.db, settings, log });
    expect(await service.sendToUser(annId, { subject: 'A certification expires soon', text: 'Your card expires in 30 days.' })).toBe('queued');
    const job = (await jobs()).find((j) => j.payload.subject === 'A certification expires soon')!;
    expect(job.payload).toEqual({ userId: annId, subject: 'A certification expires soon', text: 'Your card expires in 30 days.' });
    expect(job.max_attempts).toBe(5);
    expect(JSON.stringify(job.payload)).not.toContain('ann@example.com');

    expect(await service.deliver(job.payload)).toBe('sent');
    expect(sent.at(-1)).toEqual({
      from: 'Dive Hub <no-reply@example.com>', to: 'ann@example.com',
      subject: 'A certification expires soon', text: 'Your card expires in 30 days.',
    });
  });

  it('carries an HTML part when the sender gives one', async () => {
    const service = createMailService({ db: t.db, settings, log });
    await service.sendToUser(annId, { subject: 'With HTML', text: 'Plain', html: '<p>Rich</p>' });
    const job = (await jobs()).find((j) => j.payload.subject === 'With HTML')!;
    await service.deliver(job.payload);
    expect(sent.at(-1)).toMatchObject({ text: 'Plain', html: '<p>Rich</p>' });
  });

  it('queues nothing for a User who opted out, and the task skips it too', async () => {
    const service = createMailService({ db: t.db, settings, log });
    await profiles.savePreferences(bobId, { mailOptOut: true });
    const before = (await jobs()).length;
    expect(await service.sendToUser(bobId, { subject: 'Opted out', text: 'No thanks.' })).toBe('skipped');
    expect((await jobs()).length).toBe(before);
    expect(logs).toContain('the User opted out of e-mail');

    // Opting out after the queueing stops the send as well.
    await profiles.savePreferences(bobId, { mailOptOut: false });
    await service.sendToUser(bobId, { subject: 'Opted out later', text: 'Too late.' });
    await profiles.savePreferences(bobId, { mailOptOut: true });
    const job = (await jobs()).find((j) => j.payload.subject === 'Opted out later')!;
    expect(await service.deliver(job.payload)).toBe('skipped');
    expect(sent.some((s) => s.subject === 'Opted out later')).toBe(false);
    await profiles.savePreferences(bobId, { mailOptOut: false });
  });

  it('is off without a relay: queueing and the task skip and log, never throw', async () => {
    const off = createMailService({ db: t.db, settings: undefined, log });
    expect(await off.sendToUser(annId, { subject: 'Off', text: 'Nothing.' })).toBe('skipped');
    expect(await off.deliver({ userId: annId, subject: 'Off', text: 'Nothing.' })).toBe('skipped');
    expect(logs).toContain('e-mail is off; not sending');
  });

  it('throws when the relay refuses, so the job is tried again', async () => {
    const failing = createMailService({
      db: t.db, settings: { transport: { send: async () => { throw new Error('relay down'); } }, from: settings.from }, log,
    });
    await expect(failing.deliver({ userId: annId, subject: 'Retry', text: 'Once more.' })).rejects.toThrow('relay down');
  });

  it('refuses a job payload without its fields', async () => {
    await expect(createMailService({ db: t.db, settings, log }).deliver({})).rejects.toThrow('the mail job has no userId');
  });
});
