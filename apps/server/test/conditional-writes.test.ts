// Conditional writes (ADR 0083): reads carry an ETag with the version, a native client sends it back as If-Match,
// a stale one answers 412 with the current entity view, and the web's body-version 409 path stays as it was.
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { dive } from '../src/db/schema.js';
import { BASE_URL, createTestApp, createTestDatabase, createUser, databaseReachable, signIn, type TestDatabase } from './support.js';

describe.skipIf(!(await databaseReachable()))('conditional writes', () => {
  let t: TestDatabase;
  let ctx: Awaited<ReturnType<typeof createTestApp>>;
  let tim: string;
  let diverId: string;

  const inject = (method: 'GET' | 'PATCH' | 'PUT' | 'POST' | 'DELETE', url: string, cookie: string, payload?: object, headers?: Record<string, string>) =>
    ctx.app.inject({
      method, url, headers: { cookie, origin: BASE_URL, ...headers }, ...(payload !== undefined && { payload }),
    });
  const ifMatch = (version: number) => ({ 'if-match': `"${version}"` });
  const get = async (url: string) => (await inject('GET', url, tim)).json();

  const newDive = async () => {
    const [made] = await t.db.insert(dive).values({
      diverId, startsAt: new Date('2026-02-01T10:00:00Z'), utcOffsetSeconds: 0, durationSeconds: 3600,
    }).returning();
    return made!.id;
  };

  beforeAll(async () => {
    t = await createTestDatabase();
    ctx = await createTestApp(t);
    await createUser(ctx.auth, 'tim@example.com');
    tim = await signIn(ctx.app, 'tim@example.com');
    diverId = ((await get('/api/divers')) as { id: string; isOwn: boolean }[]).find((d) => d.isOwn)!.id;
  });
  afterAll(async () => {
    await ctx?.app.close();
    await t?.drop();
  });

  describe('a Dive', () => {
    it('carries its version as ETag, answers a stale If-Match with 412 and the current Dive, and takes a fresh one', async () => {
      const id = await newDive();
      const first = await get(`/api/dives/${id}`);
      const firstRead = await inject('GET', `/api/dives/${id}`, tim);
      expect(firstRead.headers.etag).toBe(`"${first.version}"`);

      // The web's body version keeps working: it moves the Dive on.
      const moved = await inject('PATCH', `/api/dives/${id}`, tim, { version: first.version, notes: 'from the web' });
      expect(moved.statusCode).toBe(200);
      expect(moved.headers.etag).toBe(`"${first.version + 1}"`);

      const stale = await inject('PATCH', `/api/dives/${id}`, tim, { notes: 'stale' }, ifMatch(first.version));
      expect(stale.statusCode).toBe(412);
      expect(stale.json()).toMatchObject({
        code: 'dive_changed', dive: { id, version: first.version + 1, notes: 'from the web' },
      });

      const fresh = await inject('PATCH', `/api/dives/${id}`, tim, { notes: 'from the phone' }, ifMatch(first.version + 1));
      expect(fresh.statusCode).toBe(200);
      expect(fresh.headers.etag).toBe(`"${first.version + 2}"`);
      expect(fresh.json()).toMatchObject({ id, version: first.version + 2, notes: 'from the phone' });
    });

    it('lets If-Match win over a differing body version, and keeps the 409 without one', async () => {
      const id = await newDive();
      const current = await get(`/api/dives/${id}`);

      const wins = await inject('PATCH', `/api/dives/${id}`, tim, { version: 999, notes: 'If-Match wins' }, ifMatch(current.version));
      expect(wins.statusCode).toBe(200);

      const legacy = await inject('PATCH', `/api/dives/${id}`, tim, { version: current.version, notes: 'too late' });
      expect(legacy.statusCode).toBe(409);
      expect(legacy.json()).toMatchObject({ code: 'dive_changed' });
      expect(legacy.json()).not.toHaveProperty('dive');
    });

    it('refuses a write with neither a body version nor If-Match, and a malformed If-Match', async () => {
      const id = await newDive();
      const none = await inject('PATCH', `/api/dives/${id}`, tim, { notes: 'no version' });
      expect(none.statusCode).toBe(400);
      expect(none.json()).toMatchObject({ code: 'invalid_input' });

      const malformed = await inject('PATCH', `/api/dives/${id}`, tim, { notes: 'bad tag' }, { 'if-match': '1' });
      expect(malformed.statusCode).toBe(400);
      expect(malformed.json()).toMatchObject({ code: 'invalid_input' });
    });

    it('answers a stale If-Match on Participants with 412 and the current Dive', async () => {
      const id = await newDive();
      const current = await get(`/api/dives/${id}`);
      await inject('PATCH', `/api/dives/${id}`, tim, { version: current.version, notes: 'changed meanwhile' });

      const stale = await inject('PUT', `/api/dives/${id}/participants`, tim, { participants: [] }, ifMatch(current.version));
      expect(stale.statusCode).toBe(412);
      expect(stale.json()).toMatchObject({ code: 'dive_changed', dive: { id, notes: 'changed meanwhile' } });
    });

    it('answers a stale If-Match on deleting a Dive with 412, and deletes on a fresh one', async () => {
      const id = await newDive();
      const current = await get(`/api/dives/${id}`);
      await inject('PATCH', `/api/dives/${id}`, tim, { version: current.version, notes: 'changed meanwhile' });

      const stale = await inject('DELETE', `/api/dives/${id}`, tim, {}, ifMatch(current.version));
      expect(stale.statusCode).toBe(412);
      expect(stale.json()).toMatchObject({ code: 'dive_changed', dive: { id, notes: 'changed meanwhile' } });

      const deleted = await inject('DELETE', `/api/dives/${id}`, tim, {}, ifMatch(current.version + 1));
      expect(deleted.statusCode).toBe(200);
      expect((await inject('GET', `/api/dives/${id}`, tim)).statusCode).toBe(404);
    });
  });

  describe('a Dive site', () => {
    it('carries its version as ETag, and a stale If-Match on an edit or a delete answers 412 with the site', async () => {
      const id = (await inject('POST', '/api/dive-sites', tim, { name: 'Coral Garden' })).json().id as string;
      const first = await get(`/api/dive-sites/${id}`);
      expect((await inject('GET', `/api/dive-sites/${id}`, tim)).headers.etag).toBe(`"${first.version}"`);

      const renamed = await inject('PATCH', `/api/dive-sites/${id}`, tim, { version: first.version, name: 'Coral Garden Reef' });
      expect(renamed.statusCode).toBe(200);
      expect(renamed.headers.etag).toBe(`"${first.version + 1}"`);

      const staleEdit = await inject('PATCH', `/api/dive-sites/${id}`, tim, { name: 'Stale' }, ifMatch(first.version));
      expect(staleEdit.statusCode).toBe(412);
      expect(staleEdit.json()).toMatchObject({ code: 'site_changed', site: { id, name: 'Coral Garden Reef' } });

      const staleDelete = await inject('DELETE', `/api/dive-sites/${id}`, tim, undefined, ifMatch(first.version));
      expect(staleDelete.statusCode).toBe(412);
      expect(staleDelete.json()).toMatchObject({ code: 'site_changed', site: { id, name: 'Coral Garden Reef' } });

      const deleted = await inject('DELETE', `/api/dive-sites/${id}`, tim, undefined, ifMatch(first.version + 1));
      expect(deleted.statusCode).toBe(204);
      expect((await inject('GET', `/api/dive-sites/${id}`, tim)).statusCode).toBe(404);
    });
  });

  describe('a Dive centre', () => {
    it('carries its version as ETag, and a stale If-Match on a rename answers 412 with the centre', async () => {
      const id = (await inject('POST', '/api/dive-centres', tim, { name: 'Blue Lagoon' })).json().id as string;
      const first = await get(`/api/dive-centres/${id}`);
      expect((await inject('GET', `/api/dive-centres/${id}`, tim)).headers.etag).toBe(`"${first.version}"`);

      await inject('PATCH', `/api/dive-centres/${id}`, tim, { version: first.version, name: 'Blue Lagoon SSI' });
      const stale = await inject('PATCH', `/api/dive-centres/${id}`, tim, { name: 'Stale' }, ifMatch(first.version));
      expect(stale.statusCode).toBe(412);
      expect(stale.json()).toMatchObject({ code: 'centre_changed', centre: { id, name: 'Blue Lagoon SSI' } });
    });
  });

  describe('an Equipment item', () => {
    it('carries its version as ETag, and a stale If-Match answers 412 with the item', async () => {
      const created = await inject('POST', `/api/divers/${diverId}/equipment`, tim, {
        category: 'regulator', name: 'Regulator set', inUseFrom: '2026-01-01',
      });
      const id = created.json().id as string;
      const first = await get(`/api/equipment/${id}`);
      expect((await inject('GET', `/api/equipment/${id}`, tim)).headers.etag).toBe(`"${first.version}"`);

      await inject('PATCH', `/api/equipment/${id}`, tim, { version: first.version, name: 'Regulator set 2' });
      const stale = await inject('PATCH', `/api/equipment/${id}`, tim, { name: 'Stale' }, ifMatch(first.version));
      expect(stale.statusCode).toBe(412);
      expect(stale.json()).toMatchObject({ code: 'equipment_changed', equipment: { id, name: 'Regulator set 2' } });

      const fresh = await inject('PATCH', `/api/equipment/${id}`, tim, { name: 'From the phone' }, ifMatch(first.version + 1));
      expect(fresh.statusCode).toBe(200);
      expect(fresh.json()).toMatchObject({ name: 'From the phone', version: first.version + 2 });
    });
  });
});
