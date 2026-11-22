// A migration that changes after a database applied it never runs again there, so the server
// reports it at start (it happened in slice 5: a dev server applied 0004 before its data step was added).
// Migrations that convert data get a test that stops before them, adds old data, then applies them.
import { execFileSync } from 'node:child_process';
import { createHash } from 'node:crypto';
import { readFileSync } from 'node:fs';
import { appendFile, cp, mkdtemp, readFile, rm, writeFile } from 'node:fs/promises';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { findChangedMigrations, migrateDatabase } from '../src/db/migrate.js';
import { createTestDatabase, databaseReachable, type TestDatabase } from './support.js';

const migrations = fileURLToPath(new URL('../drizzle', import.meta.url));
let base: string | null | undefined;

/**
 * Released migrations are immutable (FAH-221): a database keeps the hash of the bytes it applied, so a file
 * edited after it reached main makes every database that applied it report drift, and a backup carries that
 * stale hash along. Compared with the merge base of this branch and CI's BASELINE_REF (a pull request's base,
 * or the commit a push started from), else origin/main, else the newest release tag.
 */
describe.skipIf(releasedBase() === null)('released migrations', () => {
  const root = fileURLToPath(new URL('../../../', import.meta.url));
  it('keep the files and journal entries they were applied with', () => {
    const base = releasedBase()!;
    const git = (...args: string[]) => execFileSync('git', args, { cwd: root, encoding: 'utf8', stdio: ['ignore', 'pipe', 'ignore'] }).trim();
    // `git diff` compares the base with the working tree: a modified or deleted file that was already there is
    // drift; an added file is a new migration. The journal may only grow at its end, which the prefix check does.
    const drift = git('diff', '--name-status', '--no-renames', base, '--', 'apps/server/drizzle')
      .split('\n').filter((line) => line && !line.startsWith('A\t') && !line.endsWith('\tapps/server/drizzle/meta/_journal.json'));
    expect(drift, 'a migration file changed after it reached main; put the change in a new migration (docs/development.md)').toEqual([]);
    const journalOf = (text: string) => (JSON.parse(text) as { entries: { tag: string }[] }).entries;
    const wasReleased = journalOf(git('show', `${base}:apps/server/drizzle/meta/_journal.json`));
    const isNow = journalOf(readFileSync(join(root, 'apps/server/drizzle/meta/_journal.json'), 'utf8'));
    expect(isNow.slice(0, wasReleased.length), 'a released migration’s journal entry changed; put the change in a new migration').toEqual(wasReleased);
  });
});

/** The commit released migrations are compared with, or null when there is no git history to compare with. */
function releasedBase(): string | null {
  if (base !== undefined) return base;
  const root = fileURLToPath(new URL('../../../', import.meta.url));
  const git = (...args: string[]) => execFileSync('git', args, { cwd: root, encoding: 'utf8', stdio: ['ignore', 'pipe', 'ignore'] }).trim();
  let tags: string[] = [];
  try {
    tags = git('tag', '--list', 'v*', '--sort=-v:refname').split('\n').filter(Boolean);
  } catch { /* no repository, or no tags yet */ }
  for (const ref of [process.env.BASELINE_REF, 'origin/main', tags[0]].filter((r): r is string => Boolean(r))) {
    try {
      base = git('merge-base', 'HEAD', git('rev-parse', '--verify', `${ref}^{commit}`));
      return base;
    } catch { /* not fetched here; try the next */ }
  }
  // Without history the check cannot run; CI always has it (check.yml fetches the full history).
  process.stderr.write('\nNo released migration base (BASELINE_REF, origin/main or a release tag): the immutability check is SKIPPED.\n');
  return (base = null);
}

describe.skipIf(!(await databaseReachable()))('applied migrations', () => {
  let t: TestDatabase;
  let copy: string;
  beforeAll(async () => {
    t = await createTestDatabase();
    copy = await mkdtemp(join(tmpdir(), 'divehub-migrations-'));
    await cp(migrations, copy, { recursive: true });
  });
  afterAll(async () => {
    await t?.drop();
    if (copy) await rm(copy, { recursive: true, force: true });
  });

  it('match their files when nothing changed', async () => {
    expect(await findChangedMigrations(t.pool, migrations)).toEqual([]);
  });

  it("waiting_push and the Connection's automation columns exist", async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const user = (await q(`insert into "user" (name, email) values ('Automation', 'automation@example.org') returning id`)).rows[0].id as string;
    const diver = (await q(`insert into diver (name) values ('Erika') returning id`)).rows[0].id as string;
    const dive = (await q(`insert into dive (diver_id, starts_at, duration_seconds) values ($1, now(), 1800) returning id`, [diver])).rows[0].id as string;
    const conn = (await q(
      `insert into connection (user_id, diver_id, provider, account_id, account_label, keep_signed_in, credentials) values ($1, $2, 'ledger', '1', 'a@b.c', false, '{}') returning *`,
      [user, diver],
    )).rows[0];
    expect(conn).toMatchObject({ send_changes: false, send_new_dives: false, delete_at_provider: false, fetch_every_minutes: null, last_fetched_at: null, last_fetch_import_id: null, unsent_import_ids: [] });

    const insert = () => q(`insert into waiting_push (dive_id, provider, connection_id, action, due_at, changed_at) values ($1, 'ledger', $2, 'send', now(), now()) returning *`, [dive, conn.id]);
    expect((await insert()).rows[0]).toMatchObject({ state: 'waiting', options: {}, detail: null, attempts: 0, reason: null, first_failed_at: null, stopped_at: null });
    // A full row: every column takes a value and gives it back.
    const full = (await q(
      `insert into waiting_push (dive_id, provider, connection_id, action, state, reason, options, detail, due_at, attempts, first_failed_at, stopped_at, changed_at)
       values ($1, 'ssi', $2, 'delete', 'stopped', 'provider_refused', '{"onExisting": "link"}', '{"remoteId": "555", "remoteNumber": 12, "startsAt": "2026-03-05 08:00"}', now(), 3, now(), now(), now()) returning *`,
      [dive, conn.id],
    )).rows[0];
    expect(full).toMatchObject({ action: 'delete', state: 'stopped', reason: 'provider_refused', options: { onExisting: 'link' }, detail: { remoteId: '555', remoteNumber: 12, startsAt: '2026-03-05 08:00' }, attempts: 3 });
    expect(full.first_failed_at).toBeInstanceOf(Date);
    expect(full.stopped_at).toBeInstanceOf(Date);
    await expect(insert()).rejects.toThrow();

    await q('update connection set fetch_every_minutes = 360 where id = $1', [conn.id]);
    await expect(q('update connection set fetch_every_minutes = 30 where id = $1', [conn.id])).rejects.toThrow(/connection_fetch_every_ck/);
  });

  it('lets only a continuation Recording go without an Original', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const user = (await q(`insert into "user" (name, email) values ('Erika', 'continuation@example.com') returning id`)).rows[0].id as string;
    const diver = (await q(`insert into diver (name) values ('Erika') returning id`)).rows[0].id as string;
    const dive = (await q(`insert into dive (diver_id, starts_at, duration_seconds) values ($1, now(), 1800) returning id`, [diver])).rows[0].id as string;
    const insert = (key: string, parser: string, extra = '', values = '') =>
      q(`insert into recording (recording_key, parser, parser_version, starts_at, duration_seconds${extra}) values ($1, $2, '1', now(), 1800${values}) returning id`, [key, parser]);

    const joined = (await insert('continuation:1', 'continuation')).rows[0].id as string;
    await expect(insert('fit:1', 'fit')).rejects.toThrow(/recording_derived_ck/);

    const append = (await q(`insert into continuation_append (joined_recording_id, kept_dive_id, user_id) values ($1, $2, $3) returning id`, [joined, dive, user])).rows[0].id as string;
    await expect(insert('continuation:2', 'continuation', ', append_id', `, '${append}'`)).rejects.toThrow(/recording_part_ck/);
    await expect(insert('continuation:3', 'continuation', ', joined_recording_id, append_id', `, '${joined}', '${append}'`)).rejects.toThrow(/recording_part_ck/);
  });

  it('are reported by name when a file changed after it was applied', async () => {
    await appendFile(join(copy, '0002_password_reset.sql'), '\n-- an edit made after the fact\n');
    expect(await findChangedMigrations(t.pool, copy)).toEqual(['0002_password_reset']);
  });
});

/** A copy of the migrations that ends before `tag`. */
async function migrationsBefore(tag: string) {
  const folder = await mkdtemp(join(tmpdir(), 'divehub-migrations-'));
  await cp(migrations, folder, { recursive: true });
  const journalPath = join(folder, 'meta', '_journal.json');
  const journal = JSON.parse(await readFile(journalPath, 'utf8')) as { entries: { tag: string }[] };
  journal.entries = journal.entries.slice(0, journal.entries.findIndex((e) => e.tag === tag));
  await writeFile(journalPath, JSON.stringify(journal));
  return folder;
}

describe.skipIf(!(await databaseReachable()))('0060: the hashes of a restored backup (FAH-221)', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0060_reconcile_migration_hashes');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('replaces the hashes of an earlier draft of 0011 and 0038 with the released files’ hashes', async () => {
    const tags = ['0011_site_water_type_ssi_import', '0038_freeing_dive_numbers'];
    const journal = JSON.parse(await readFile(join(before, 'meta', '_journal.json'), 'utf8')) as { entries: { tag: string; when: number }[] };
    const whens = tags.map((tag) => journal.entries.find((entry) => entry.tag === tag)!.when);
    // What a restored backup from an instance that applied an earlier draft holds, and what the server reports at start.
    for (const when of whens) await t.pool.query('update drizzle.__drizzle_migrations set hash = $1 where created_at = $2', [`draft-${when}`, when]);
    expect(await findChangedMigrations(t.pool, before)).toEqual(tags);

    await migrateDatabase(t.db, t.pool, migrations);

    expect(await findChangedMigrations(t.pool, migrations)).toEqual([]);
    const released = await Promise.all(tags.map(async (tag) => createHash('sha256').update(await readFile(join(migrations, `${tag}.sql`))).digest('hex')));
    const { rows } = await t.pool.query<{ hash: string }>(
      'select hash from drizzle.__drizzle_migrations where created_at = any($1::bigint[]) order by created_at', [whens],
    );
    expect(rows.map((row) => row.hash)).toEqual(released);
  });
});

describe.skipIf(!(await databaseReachable()))('0011 and 0012: the water type moves from the Dive to its site (ADR 0025)', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0011_site_water_type_ssi_import');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it("drops the Dive's water type and its Overrides, keeps the other Overrides and old Revisions, and lets references keep an offer", async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const diver = (await q(`insert into diver (name) values ('Erika') returning id`)).rows[0].id as string;
    const dive = (await q(`insert into dive (diver_id, starts_at, duration_seconds, water_type, overrides)
      values ($1, now(), 1800, 'salt', '{waterType,number}') returning id`, [diver])).rows[0].id as string;
    await q(`insert into revision (entity_type, entity_id, actor_type, actor_id, cause, changes)
      values ('dive', $1, 'user', 'u1', 'edit', '{"waterType": {"from": "fresh", "to": "salt"}}')`, [dive]);

    await migrateDatabase(t.db, t.pool, migrations);

    const { rows: [after] } = await q('select overrides from dive where id = $1', [dive]);
    // 'number' was kept by that migration; ADR 0048 (0032) dropped it, as a number is no Override any more.
    expect(after.overrides).toEqual([]);
    const columns = (await q(`select table_name, column_name from information_schema.columns where column_name = 'water_type'`)).rows;
    expect(columns).toEqual([{ table_name: 'dive_site', column_name: 'water_type' }]);
    const { rows: [revision] } = await q('select changes from revision where entity_id = $1', [dive]);
    expect(revision.changes).toEqual({ waterType: { from: 'fresh', to: 'salt' } });

    const site = (await q(`insert into dive_site (name, water_type) values ('Hausreef', 'salt') returning id`)).rows[0].id as string;
    await q(`insert into dive_site_external_id (site_id, source, external_id, provides_data, imported) values ($1, 'ssi', '3314', false, '{"name": "Hausreef"}')`, [site]);
    await expect(q(`insert into dive_site_external_id (site_id, source, external_id, provides_data) values ($1, 'osm', 'node/1', true)`, [site])).rejects.toThrow(/imported_ck/);
    await expect(q(`update dive_site set water_type = 'en13319' where id = $1`, [site])).rejects.toThrow(/water_type_ck/);
  });
});

describe.skipIf(!(await databaseReachable()))('0013: Providers as adapters (ADR 0027)', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0013_provider_layer');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('drops the old token and password so the Connection signs in again, and renames what Pushes stored', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const user = (await q(`insert into "user" (name, email, email_verified) values ('Erika', 'erika@example.com', true) returning id`)).rows[0].id as string;
    const diver = (await q(`insert into diver (name) values ('Erika') returning id`)).rows[0].id as string;
    const dive = (await q(`insert into dive (diver_id, starts_at, duration_seconds) values ($1, now(), 1800) returning id`, [diver])).rows[0].id as string;
    const conn = (await q(`insert into connection (user_id, diver_id, target, account_id, account_email, keep_signed_in, token, password)
      values ($1, $2, 'ssi', '5012047', 'erika@example.com', true, 'v1.a.b', 'v1.c.d') returning id`, [user, diver])).rows[0].id as string;
    const pushOf = async (state: string, action: string, code: string | null) => (await q(`insert into push (dive_id, connection_id, target, mode, action, state, dive_version, error_code)
      values ($1, $2, 'ssi', 'api', $3, $4, 1, $5) returning id`, [dive, conn, action, state, code])).rows[0].id as string;
    const goneUpdate = await pushOf('failed', 'update', 'ssi_dive_gone');
    const goneDelete = await pushOf('confirmed', 'delete', 'ssi_dive_gone');
    const outage = await pushOf('failed', 'create', 'ssi_unavailable');
    const noSite = await pushOf('failed', 'create', 'ssi_site_missing');

    await migrateDatabase(t.db, t.pool, migrations);

    const { rows: [after] } = await q('select provider, account_label, credentials, state, keep_signed_in from connection where id = $1', [conn]);
    // 0044 gave it credentials again: its account label as login, nothing to sign in with.
    expect(after).toMatchObject({ provider: 'ssi', account_label: 'erika@example.com', state: 'needs_sign_in', keep_signed_in: false });
    expect(JSON.parse(after.credentials)).toEqual({ kind: 'password', login: 'erika@example.com', access: null, password: null });
    const pushes = Object.fromEntries((await q('select id, provider, error_code, remote_gone from push')).rows.map((r) => [r.id, r]));
    expect(pushes[goneUpdate]).toMatchObject({ provider: 'ssi', error_code: 'provider_dive_gone', remote_gone: true });
    expect(pushes[goneDelete]).toMatchObject({ error_code: null, remote_gone: true });
    expect(pushes[outage]).toMatchObject({ error_code: 'provider_unavailable', remote_gone: false });
    // Renamed again by 0015, when Push requirements replaced the site ID code (ADR 0029).
    expect(pushes[noSite]).toMatchObject({ error_code: 'provider_requirements_unmet' });
    expect((await q(`select 1 from pg_type where typname = 'target'`)).rows).toEqual([]);
  });
  it('marks the saves SSI answered and never stored as failed Pushes: an update with another dive ID, a create with an invented one', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const diver = (await q(`insert into diver (name) values ('Tim') returning id`)).rows[0].id as string;
    const dive = (await q(`insert into dive (diver_id, starts_at, duration_seconds) values ($1, now(), 1800) returning id`, [diver])).rows[0].id as string;
    const pushOf = async (action: string, remoteId: string, sentId: number | null, differences: string | null, at = '2026-10-09T15:24:57Z') =>
      (await q(`insert into push (dive_id, provider, mode, action, state, dive_version, remote_id, remote_number, payload, differences, created_at)
        values ($1, 'ssi', 'api', $2, 'confirmed', 1, $3, 86, $4, $5, $6) returning id`,
      [dive, action, remoteId, JSON.stringify({ odin_user_log_id: sentId }), differences, at])).rows[0].id as string;
    const stored = await pushOf('update', '27031918', 27_031_918, '[]', '2026-10-07T10:45:38Z');
    const dropped = await pushOf('update', '902545080', 27_031_918, null);
    const created = await pushOf('create', '29837680', null, '[]', '2026-10-06T09:40:51Z');
    const invented = await pushOf('create', '931274039', null, null, '2026-10-10T06:21:25Z');
    // The migration ran with the others above; its statements again, over these rows.
    const sql = await readFile(join(migrations, '0033_mark_saves_ssi_did_not_store.sql'), 'utf8');
    for (const statement of sql.split('--> statement-breakpoint')) await q(statement);
    const pushes = Object.fromEntries((await q('select id, state, error_code, remote_id, remote_number from push where dive_id = $1', [dive])).rows.map((r) => [r.id, r]));
    expect(pushes[stored]).toMatchObject({ state: 'confirmed', error_code: null, remote_id: '27031918' });
    expect(pushes[dropped]).toMatchObject({ state: 'failed', error_code: 'provider_not_stored', remote_id: '27031918' });
    expect(pushes[created]).toMatchObject({ state: 'confirmed', remote_id: '29837680' });
    expect(pushes[invented]).toMatchObject({ state: 'failed', error_code: 'provider_not_stored', remote_id: null, remote_number: null });
  });
});

describe.skipIf(!(await databaseReachable()))('0044 and 0045: every Connection has credentials', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0044_fill_pre_0027_credentials');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('gives a Connection from before ADR 0027 its account label as login, keeps sealed ones, and refuses none', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const user = (await q(`insert into "user" (name, email) values ('Erika', 'erika@example.com') returning id`)).rows[0].id as string;
    const diver = (await q(`insert into diver (name) values ('Erika') returning id`)).rows[0].id as string;
    const insert = (provider: string, credentials: string | null) => q(
      `insert into connection (user_id, diver_id, provider, account_id, account_label, keep_signed_in, state, credentials)
       values ($1, $2, $3, '5012047', 'erika@example.com', false, 'needs_sign_in', $4) returning id`,
      [user, diver, provider, credentials],
    );
    const old = (await insert('ssi', null)).rows[0].id as string;
    const sealed = (await insert('ledger', 'v1.a.b')).rows[0].id as string;

    await migrateDatabase(t.db, t.pool, migrations);

    const credentialsOf = async (id: string) => (await q('select credentials from connection where id = $1', [id])).rows[0].credentials as string;
    // Plain JSON: SecretBox.open hands back a value that isn't sealed, and it holds nothing secret.
    expect(JSON.parse(await credentialsOf(old))).toEqual({ kind: 'password', login: 'erika@example.com', access: null, password: null });
    expect(await credentialsOf(sealed)).toBe('v1.a.b');
    await expect(q('update connection set credentials = null where id = $1', [sealed])).rejects.toThrow(/not-null/);
  });
});

describe.skipIf(!(await databaseReachable()))('0046 and 0047: the backfills of earlier Recordings are retired', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0046_retire_backfills');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('stop while a Recording still waits for its positions, and drop the marker columns once none does', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const user = (await q(`insert into "user" (name, email) values ('Erika', 'erika@example.com') returning id`)).rows[0].id as string;
    const original = (await q(`insert into original (user_id, sha256, media_type, size_bytes, file_name, storage_key)
      values ($1, 'abc', 'application/vnd.ant.fit', 1, 'a.fit', 'k') returning id`, [user])).rows[0].id as string;
    const job = (await q(`insert into import (user_id, upload_name) values ($1, 'a.fit') returning id`, [user])).rows[0].id as string;
    const rec = (await q(`insert into recording (original_id, import_id, recording_key, parser, parser_version, starts_at, duration_seconds)
      values ($1, $2, 'r1', 'fit', '1', now(), 1800) returning id`, [original, job])).rows[0].id as string;

    await expect(migrateDatabase(t.db, t.pool, migrations)).rejects.toThrow(/positions/);

    await q('update recording set positions_read_at = now() where id = $1', [rec]);
    await migrateDatabase(t.db, t.pool, migrations);
    const columns = (await q(`select column_name from information_schema.columns where table_name = 'recording' and column_name like '%_read_at'`)).rows;
    expect(columns).toEqual([]);
  });
});

describe.skipIf(!(await databaseReachable()))('0048 to 0050: an Import keeps its error code apart from the detail', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0049_split_import_error');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('moves a code to error_code and anything else to error_detail under processing_failed', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const user = (await q(`insert into "user" (name, email) values ('Erika', 'erika@example.com') returning id`)).rows[0].id as string;
    const insert = async (error: string | null) =>
      (await q(`insert into import (user_id, upload_name, status, error) values ($1, 'a.fit', 'failed', $2) returning id`, [user, error])).rows[0].id as string;
    const ids = {
      unsupported: await insert('unsupported_file'), expired: await insert('choice_expired'),
      crashed: await insert('connect ECONNREFUSED'), none: await insert(null),
    };

    await migrateDatabase(t.db, t.pool, migrations);

    const rowOf = async (id: string) => (await q('select error_code, error_detail from import where id = $1', [id])).rows[0];
    expect(await rowOf(ids.unsupported)).toEqual({ error_code: 'unsupported_file', error_detail: null });
    expect(await rowOf(ids.expired)).toEqual({ error_code: 'choice_expired', error_detail: null });
    expect(await rowOf(ids.crashed)).toEqual({ error_code: 'processing_failed', error_detail: 'connect ECONNREFUSED' });
    expect(await rowOf(ids.none)).toEqual({ error_code: null, error_detail: null });
    expect((await q(`select 1 from information_schema.columns where table_name = 'import' and column_name = 'error'`)).rows).toEqual([]);
  });
});

describe.skipIf(!(await databaseReachable()))('0040: private site IDs (ADR 0057)', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0040_private_site_ids');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('keeps public IDs and allows one private ID per account', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const diver = (await q(`insert into diver (name) values ('Erika') returning id`)).rows[0].id as string;
    const site = (await q(`insert into dive_site (name) values ('Blue Hole') returning id`)).rows[0].id as string;
    const other = (await q(`insert into dive_site (name) values ('Reef') returning id`)).rows[0].id as string;
    await q(`insert into dive_site_external_id (site_id, source, external_id) values ($1, 'ssi', '100')`, [other]);

    await migrateDatabase(t.db, t.pool, migrations);

    expect((await q('select account_id from dive_site_external_id')).rows).toEqual([{ account_id: null }]);
    const account = (await q(`insert into diver_external_id (diver_id, source, external_id) values ($1, 'ssi', '5012047') returning id`, [diver])).rows[0].id as string;
    const insert = (siteId: string, externalId: string, accountId: string | null) =>
      q(`insert into dive_site_external_id (site_id, source, external_id, account_id) values ($1, 'ssi', $2, $3)`, [siteId, externalId, accountId]);

    await insert(site, '1', null);
    await expect(insert(site, '2', null)).rejects.toThrow();
    await insert(site, '3', account);
    await expect(insert(site, '4', account)).rejects.toThrow();
    await expect(insert(other, '1', null)).rejects.toThrow();
    await expect(insert(other, '3', account)).rejects.toThrow();
    expect((await q(`select count(*)::int as n from dive_site_external_id where site_id = $1`, [site])).rows[0].n).toBe(2);

    await q('delete from diver_external_id where id = $1', [account]);
    expect((await q(`select external_id from dive_site_external_id where site_id = $1`, [site])).rows).toEqual([{ external_id: '1' }]);
  });
});

describe.skipIf(!(await databaseReachable()))('0055: an Equipment item for every existing Device (ADR 0034 D7)', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0055_sweet_rocket_raccoon');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('names and links one item per Device, with the in-use window from its earliest Recording or today', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const user = (await q(`insert into "user" (name, email) values ('Tim', 'tim@example.org') returning id`)).rows[0].id as string;
    const original = (await q(`insert into original (user_id, sha256, media_type, size_bytes, storage_key) values ($1, 'sha', 'application/octet-stream', 1, 'key') returning id`, [user])).rows[0].id as string;
    const diver = (await q(`insert into diver (name) values ('Tim') returning id`)).rows[0].id as string;
    const used = (await q(`insert into device (diver_id, manufacturer, product, serial_number) values ($1, 'Garmin', 'Descent G1', 'G1') returning id`, [diver])).rows[0].id as string;
    const unused = (await q(`insert into device (diver_id, manufacturer, product, serial_number) values ($1, 'Shearwater', 'Perdix', 'P1') returning id`, [diver])).rows[0].id as string;
    const record = (key: string, startsAt: string, deleted = false) => q(
      `insert into recording (device_id, original_id, recording_key, parser, parser_version, starts_at, duration_seconds, utc_offset_seconds, deleted_at)
       values ($1, $2, $3, 'fit', '1', $4, 1800, 0, $5)`,
      [used, original, key, startsAt, deleted ? '2020-01-01T00:00:00Z' : null],
    );
    await record('rec-later', '2024-05-01T10:00:00Z');
    await record('rec-earliest', '2023-02-01T10:00:00Z');
    await record('rec-deleted', '2020-01-01T10:00:00Z', true);

    await migrateDatabase(t.db, t.pool, migrations);

    const today = (await q(`select to_char(current_date, 'YYYY-MM-DD') as date`)).rows[0].date as string;
    const { rows } = await q(`select d.id, d.equipment_item_id, i.diver_id, i.name, i.category, i.status, i.on_every_dive,
        to_char(i.in_use_from, 'YYYY-MM-DD') as in_use_from
      from device d join equipment_item i on i.id = d.equipment_item_id where d.id = any($1)`, [[used, unused]]);
    expect(rows.find((r) => r.id === used)).toMatchObject({
      equipment_item_id: used, diver_id: diver, name: 'Garmin Descent G1', category: 'dive_computer', status: 'in_use', on_every_dive: false, in_use_from: '2023-02-01',
    });
    expect(rows.find((r) => r.id === unused)).toMatchObject({ equipment_item_id: unused, name: 'Shearwater Perdix', in_use_from: today });
    expect((await q('select count(*)::int as n from equipment_item')).rows[0].n).toBe(2);
  });
});

describe.skipIf(!(await databaseReachable()))('0064: Participants of Joint dives linked before FAH-410', () => {
  let t: TestDatabase;
  let before: string;
  beforeAll(async () => {
    before = await migrationsBefore('0064_union_joint_dive_participants');
    t = await createTestDatabase({ migrationsFolder: before });
  });
  afterAll(async () => {
    await t?.drop();
    if (before) await rm(before, { recursive: true, force: true });
  });

  it('unions every member’s people into every member, keeping existing roles and adding only', async () => {
    const q = (sql: string, params: unknown[] = []) => t.pool.query(sql, params);
    const diver = async (name: string) => (await q('insert into diver (name) values ($1) returning id', [name])).rows[0].id as string;
    const alice = await diver('Alice');
    const bob = await diver('Bob');
    const carol = await diver('Carol');
    const dave = await diver('Dave');
    const joint = (await q('insert into joint_dive default values returning id')).rows[0].id as string;
    const dive = async (id: string, diverId: string) => {
      await q(`insert into dive (id, diver_id, starts_at, duration_seconds, joint_dive_id) values ($1, $2, now(), 1800, $3)`, [id, diverId, joint]);
      return id;
    };
    const a = await dive('00000000-0000-0000-0000-000000000001', alice);
    const b = await dive('00000000-0000-0000-0000-000000000002', bob);
    const c = await dive('00000000-0000-0000-0000-000000000003', carol);
    // A lists Carol as guide and Dave as buddy; B lists Dave as instructor and Alice as buddy. Dave's role differs
    // between the members, so each keeps its own.
    await q(`insert into participant (dive_id, diver_id, role) values ($1, $2, 'guide'), ($1, $3, 'buddy')`, [a, carol, dave]);
    await q(`insert into participant (dive_id, diver_id, role) values ($1, $2, 'instructor'), ($1, $3, 'buddy')`, [b, dave, alice]);

    await migrateDatabase(t.db, t.pool, migrations);

    const people = async (diveId: string) => (await q(
      `select p.diver_id, p.role from participant p join diver d on d.id = p.diver_id
       where p.dive_id = $1 order by d.name`, [diveId],
    )).rows;
    expect(await people(a)).toEqual([
      { diver_id: bob, role: 'buddy' }, { diver_id: carol, role: 'guide' }, { diver_id: dave, role: 'buddy' },
    ]);
    expect(await people(b)).toEqual([
      { diver_id: alice, role: 'buddy' }, { diver_id: carol, role: 'guide' }, { diver_id: dave, role: 'instructor' },
    ]);
    expect(await people(c)).toEqual([
      { diver_id: alice, role: 'buddy' }, { diver_id: bob, role: 'buddy' }, { diver_id: dave, role: 'buddy' },
    ]);

    // Idempotent: running the one-time union again adds nothing and changes no role.
    await t.pool.query(readFileSync(join(migrations, '0064_union_joint_dive_participants.sql'), 'utf8'));
    expect(await people(a)).toEqual([
      { diver_id: bob, role: 'buddy' }, { diver_id: carol, role: 'guide' }, { diver_id: dave, role: 'buddy' },
    ]);
  });
});
