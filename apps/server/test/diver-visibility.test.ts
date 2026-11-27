// Who may see a Diver's contact details (ADR 0088): under `instance` (the default) every User, as since ADR 0043;
// under `shared` only the Diver's Users, the User who added an external Diver, admins, and Users who share a Dive with
// it. The other Users see the name parts only, and no account, code, e-mail or leader number. Placeholders only.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { buddiesOf } from '../src/mcp/read-service.js';
import { seesDiverDetails } from '../src/divers/diver-visibility.js';
import type { VisibilityViewer } from '../src/divers/diver-visibility.js';
import type { DiverVisibility } from '../src/vocabulary.js';
import { makeSyntheticDive } from './fixtures/synthetic-dive.js';
import {
  BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, multipartFile, signIn, type TestDatabase,
} from './support.js';

type Details = {
  id: string; name: string; external: boolean; canEdit: boolean; detailsVisible: boolean;
  firstName: string | null; lastName: string | null; email: string | null; leaderNumber: string | null;
  accounts: { source: string; externalId: string }[];
  codes: { kind: string; provider: string; text: string }[];
};
type ExternalPage = { divers: { id: string; name: string; accounts: string[]; detailsVisible: boolean }[]; total: number };
type DiveView = { version: number; verificationCodes: { kind: string; diver: { id: string; name: string } | null; text: string }[] };

const TIM_ACCOUNT = '4000100';
const TIM_BUDDY = `buddy;${TIM_ACCOUNT};firstName:Tim;lastName:Tester;email:tim@example.com`;
const TIM_PRO = `${TIM_BUDDY};leaderNr:111`;
const ANNA_ACCOUNT = '4000200';
const ANNA_BUDDY = `buddy;${ANNA_ACCOUNT};firstName:Anna;lastName:Anders;email:anna@example.com`;
const ANNA_PRO = `${ANNA_BUDDY};leaderNr:222`;
const EXTERN_ACCOUNT = '4000300';
const EXTERN_BUDDY = `buddy;${EXTERN_ACCOUNT};firstName:Extern;lastName:Example;email:extern@example.com`;

describe.skipIf(!(await databaseReachable()))('Diver detail visibility (ADR 0088)', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let instanceCtx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let anna: string;
  let noor: string;
  let admin: string;
  let timViewer: VisibilityViewer;
  let annaViewer: VisibilityViewer;
  let noorViewer: VisibilityViewer;
  let adminViewer: VisibilityViewer;
  let timDiver: string;
  let annaDiver: string;
  let annaDiverName: string;
  let externDiver: string;
  let diveId: string;

  const call = (
    app: Awaited<ReturnType<typeof createTestApp>>['app'],
    method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE', url: string, cookie: string, payload?: object,
  ) => app.inject({ method, url, headers: { cookie, origin: BASE_URL }, ...(payload && { payload }) });
  const json = async <T>(
    app: Awaited<ReturnType<typeof createTestApp>>['app'],
    method: Parameters<typeof call>[1], url: string, cookie: string, payload?: object,
  ) => (await call(app, method, url, cookie, payload)).json() as T;
  const ownDiver = async (app: Awaited<ReturnType<typeof createTestApp>>['app'], cookie: string) =>
    (await json<{ id: string; isOwn: boolean }[]>(app, 'GET', '/api/divers', cookie)).find((d) => d.isOwn)!.id;
  const details = (app: Awaited<ReturnType<typeof createTestApp>>['app'], id: string, cookie: string) =>
    json<Details>(app, 'GET', `/api/divers/${id}/details`, cookie);
  const setDetails = (app: Awaited<ReturnType<typeof createTestApp>>['app'], id: string, cookie: string, body: object) =>
    call(app, 'PATCH', `/api/divers/${id}/details`, cookie, body);
  const setAccount = (app: Awaited<ReturnType<typeof createTestApp>>['app'], id: string, cookie: string, externalId: string) =>
    call(app, 'PUT', `/api/divers/${id}/external-ids/ssi`, cookie, { externalId });

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t, { diverVisibility: 'shared' });
    instanceCtx = await createTestApp(t, { diverVisibility: 'instance' });
    const timUser = await createUser(ctx.auth, 'tim@example.com');
    const annaUser = await createUser(ctx.auth, 'anna@example.com');
    const noorUser = await createUser(ctx.auth, 'noor@example.com');
    const adminUser = await createUser(ctx.auth, 'admin@example.com', 'admin');
    timViewer = { userId: timUser.id, isAdmin: false };
    annaViewer = { userId: annaUser.id, isAdmin: false };
    noorViewer = { userId: noorUser.id, isAdmin: false };
    adminViewer = { userId: adminUser.id, isAdmin: true };
    tim = await signIn(ctx.app, 'tim@example.com');
    anna = await signIn(ctx.app, 'anna@example.com');
    noor = await signIn(ctx.app, 'noor@example.com');
    admin = await signIn(ctx.app, 'admin@example.com');
    timDiver = await ownDiver(ctx.app, tim);
    annaDiver = await ownDiver(ctx.app, anna);
    annaDiverName = (await json<{ id: string; name: string }[]>(ctx.app, 'GET', '/api/divers', anna)).find((d) => d.id === annaDiver)!.name;

    expect((await setDetails(ctx.app, timDiver, tim, {
      firstName: 'Tim', lastName: 'Tester', email: 'tim@example.com', leaderNumber: '111',
    })).statusCode).toBe(200);
    await setAccount(ctx.app, timDiver, tim, TIM_ACCOUNT);
    expect((await setDetails(ctx.app, annaDiver, anna, {
      firstName: 'Anna', lastName: 'Anders', email: 'anna@example.com', leaderNumber: '222',
    })).statusCode).toBe(200);
    await setAccount(ctx.app, annaDiver, anna, ANNA_ACCOUNT);

    externDiver = (await json<{ id: string }>(ctx.app, 'POST', '/api/external-divers', tim, { name: 'Extern Example' })).id;
    expect((await setDetails(ctx.app, externDiver, tim, {
      firstName: 'Extern', lastName: 'Example', email: 'extern@example.com',
    })).statusCode).toBe(200);
    await setAccount(ctx.app, externDiver, tim, EXTERN_ACCOUNT);

    const { payload, headers } = multipartFile('main.fit', makeSyntheticDive({ serialNumber: 111 }));
    const imported = await ctx.app.inject({ method: 'POST', url: '/api/imports', payload, headers: { ...headers, cookie: tim } });
    await ctx.imports.processImport(imported.json().id);
    diveId = (await json<{ outcome: { diveId: string }[] }>(ctx.app, 'GET', `/api/imports/${imported.json().id}`, tim)).outcome[0]!.diveId;
    // Anna is on Tim's Dive: they share it, so each may see the other's contact details under `shared`.
    await call(ctx.app, 'PUT', `/api/dives/${diveId}/participants`, tim, {
      version: (await json<DiveView>(ctx.app, 'GET', `/api/dives/${diveId}`, tim)).version,
      participants: [{ diverId: annaDiver, role: 'buddy' }],
    });
  });
  afterAll(async () => {
    await instanceCtx?.app.close();
    await ctx?.app.close();
    await t?.drop();
  });

  describe('the predicate matrix', () => {
    const matrix = (visibility: DiverVisibility, diverId: () => string) => Promise.all(
      [...Object.values({ timViewer, annaViewer, noorViewer, adminViewer })].map((v) => seesDiverDetails(t.db, v, diverId(), visibility)),
    );

    it('lets every User see every Diver under instance, whatever their relation', async () => {
      for (const diver of [() => timDiver, () => annaDiver, () => externDiver]) {
        expect(await matrix('instance', diver)).toEqual([true, true, true, true]);
      }
    });

    it('lets the owner, a Dive partner and an admin see under shared, and no one else', async () => {
      // Tim's own Diver: Tim manages it, Anna shares Tim's Dive, Noor does not, the admin supports.
      expect(await matrix('shared', () => timDiver)).toEqual([true, true, false, true]);
      // Anna's Diver: Anna manages it, Tim owns the Dive she is on, Noor does not.
      expect(await matrix('shared', () => annaDiver)).toEqual([true, true, false, true]);
      // The external Diver: Tim added it (its owner), Anna has no Dive with it, Noor neither, the admin supports.
      expect(await matrix('shared', () => externDiver)).toEqual([true, false, false, true]);
    });
  });

  describe('under shared', () => {
    it('redacts a stranger to the name parts, with no account, code, e-mail or leader number', async () => {
      expect(await details(ctx.app, timDiver, noor)).toMatchObject({
        firstName: 'Tim', lastName: 'Tester', detailsVisible: false,
        email: null, leaderNumber: null, accounts: [], codes: [],
      });
      expect(await details(ctx.app, annaDiver, noor)).toMatchObject({ detailsVisible: false, email: null, accounts: [], codes: [] });
      expect(await details(ctx.app, externDiver, noor)).toMatchObject({ detailsVisible: false, email: null, accounts: [], codes: [] });
    });

    it('shows the details to the Diver\'s User, a Dive partner and an admin', async () => {
      expect(await details(ctx.app, timDiver, tim)).toMatchObject({
        detailsVisible: true, email: 'tim@example.com', leaderNumber: '111',
        accounts: [{ source: 'ssi', externalId: TIM_ACCOUNT }],
        codes: [{ kind: 'buddy', text: TIM_BUDDY }, { kind: 'professional', text: TIM_PRO }],
      });
      // Anna does not manage Tim's Diver, but they share a Dive.
      expect(await details(ctx.app, timDiver, anna)).toMatchObject({ email: 'tim@example.com', accounts: [{ source: 'ssi', externalId: TIM_ACCOUNT }] });
      expect(await details(ctx.app, timDiver, admin)).toMatchObject({ email: 'tim@example.com' });
      // Tim added the external Diver: he may read what he entered.
      expect(await details(ctx.app, externDiver, tim)).toMatchObject({
        email: 'extern@example.com', accounts: [{ source: 'ssi', externalId: EXTERN_ACCOUNT }],
        codes: [{ kind: 'buddy', text: EXTERN_BUDDY }],
      });
    });

    it('keeps the search name-only for everyone', async () => {
      const found = await json<Record<string, unknown>[]>(ctx.app, 'GET', '/api/divers/search?q=Tim', noor);
      expect(found.length).toBeGreaterThan(0);
      for (const row of found) expect(Object.keys(row).sort()).toEqual(['external', 'id', 'managed', 'name']);
    });

    it('drops the account sources from the external-Diver list for a stranger only', async () => {
      const forStranger = await json<ExternalPage>(ctx.app, 'GET', `/api/external-divers?q=Extern`, noor);
      expect(forStranger.divers).toEqual([{ id: externDiver, name: 'Extern Example', accounts: [], inUse: false, canDelete: false, detailsVisible: false }]);
      const forOwner = await json<ExternalPage>(ctx.app, 'GET', `/api/external-divers?q=Extern`, tim);
      expect(forOwner.divers[0]).toMatchObject({ id: externDiver, accounts: ['ssi'], detailsVisible: true });
    });

    it('still shows a Dive partner\'s professional code on the Dive itself', async () => {
      const dive = await json<DiveView>(ctx.app, 'GET', `/api/dives/${diveId}`, tim);
      expect(dive.verificationCodes).toEqual([
        { kind: 'professional', centre: null, diver: { id: annaDiver, name: annaDiverName }, provider: 'ssi', text: ANNA_PRO },
      ]);
    });

    it('leaves the MCP reads name-only: they select no contact detail to gate', async () => {
      const { rows } = await t.db.transaction((tx) => buddiesOf(
        { tx, userId: timViewer.userId, diverIds: [timDiver] },
        { divers: [timDiver], limit: 30, offset: 0 },
      ));
      expect(rows.map((r) => Object.keys(r).sort())).toEqual([[
        'buddy', 'first', 'guide', 'id', 'instructor', 'last', 'mine', 'name', 'together',
      ]]);
      expect(JSON.stringify(rows)).not.toContain('@');
    });
  });

  describe('under instance', () => {
    it('shows every User what ADR 0043 showed, unchanged', async () => {
      for (const cookie of [noor, anna, admin]) {
        expect(await details(instanceCtx.app, timDiver, cookie)).toMatchObject({
          detailsVisible: true, email: 'tim@example.com', leaderNumber: '111',
          accounts: [{ source: 'ssi', externalId: TIM_ACCOUNT }],
          codes: [{ kind: 'buddy', text: TIM_BUDDY }, { kind: 'professional', text: TIM_PRO }],
        });
      }
      const page = await json<ExternalPage>(instanceCtx.app, 'GET', '/api/external-divers?q=Extern', noor);
      expect(page.divers[0]).toMatchObject({ accounts: ['ssi'] });
    });
  });
});
