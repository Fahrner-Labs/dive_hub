// Push hints (ADR 0085), second slice: a Dive Revision queues one `send-push` job per manager of its Diver, in the
// same writing transaction that emits the SSE hint. The job is not run here; the dispatcher tests cover sending.
import { eq, sql } from 'drizzle-orm';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import type { FcmConfig } from '../src/config.js';
import { dive, diverManagement } from '../src/db/schema.js';
import { SEND_PUSH_TASK } from '../src/notifications/push-dispatcher.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

const FCM: FcmConfig = {
  serviceAccount: {
    projectId: 'divehub-test', clientEmail: 'push@divehub-test.iam.gserviceaccount.com', privateKey: 'not-used-here',
    raw: { project_id: 'divehub-test', client_email: 'push@divehub-test.iam.gserviceaccount.com', private_key: 'not-used-here' },
  },
  android: {
    projectId: 'divehub-test', applicationId: 'dev.divehub', apiKey: 'public-api-key', gcmSenderId: '123456789',
  },
};

describe.skipIf(!(await databaseReachable()))('push hints for a Dive', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let ann: string;
  let annId: string;
  let bobId: string;
  let diveId: string;

  const dropSendPushJobs = () => t.db.execute(sql`delete from graphile_worker._private_jobs
    where task_id in (select id from graphile_worker._private_tasks where identifier = ${SEND_PUSH_TASK})`);
  const sendPushJobs = async () => (await t.db.execute(sql`select j.payload
    from graphile_worker._private_jobs j
    join graphile_worker._private_tasks t on t.id = j.task_id
    where t.identifier = ${SEND_PUSH_TASK} order by j.payload->>'userId'`)).rows as { payload: { userId: string; kind: string; id: string } }[];

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, { fcm: FCM });
    annId = (await createUser(ctx.auth, 'ann@example.com')).id;
    bobId = (await createUser(ctx.auth, 'bob@example.com')).id;
    ann = await signIn(ctx.app, 'ann@example.com');

    const { payload, headers } = multipartFile('ann.fit', makeSyntheticDive({ serialNumber: 111 }));
    const uploaded = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: ann } });
    await ctx.imports.processImport(uploaded.json().id);
    const imported = await ctx.app.inject({ method: 'GET', url: `/api/imports/${uploaded.json().id}`, headers: { cookie: ann } });
    diveId = (imported.json() as { outcome: { diveId: string }[] }).outcome[0]!.diveId;

    // Bob keeps Ann's Diver too: the Dive's audience is both managers.
    const [row] = await t.db.select({ diverId: dive.diverId }).from(dive).where(eq(dive.id, diveId));
    await t.db.insert(diverManagement).values({ userId: bobId, diverId: row!.diverId });
  });

  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  it('queues one send-push per manager of the Dive\'s Diver, in the writing transaction', async () => {
    await dropSendPushJobs();
    const view = await ctx.app.inject({ method: 'GET', url: `/api/dives/${diveId}`, headers: { cookie: ann } });
    const response = await ctx.app.inject({
      method: 'PATCH', url: `/api/dives/${diveId}`, headers: { cookie: ann, origin: BASE_URL },
      payload: { version: (view.json() as { version: number }).version, notes: 'edited for push' },
    });
    expect(response.statusCode).toBe(200);

    expect(await sendPushJobs()).toEqual([
      { payload: { userId: annId, kind: 'dive', id: diveId } },
      { payload: { userId: bobId, kind: 'dive', id: diveId } },
    ]);
  });
});
