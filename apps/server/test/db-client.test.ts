// A connection lost while checked out must not take the process down (FAH-192). pg-pool removes its own idle error
// listener while a client is checked out, so the pool has to give every client an error listener of its own.
import pg from 'pg';
import { afterAll, beforeAll, describe, expect, it } from 'vitest';
import { createTestDatabase, databaseReachable, type TestDatabase } from './support.js';

describe.skipIf(!(await databaseReachable()))('the database pool', () => {
  let t: TestDatabase;

  beforeAll(async () => {
    t = await createTestDatabase();
  });

  afterAll(async () => {
    await t?.drop();
  });

  it('survives a server-side termination of a checked-out connection and replaces it', async () => {
    const client = await t.pool.connect();
    const { rows } = await client.query<{ pid: number }>('select pg_backend_pid() as pid');
    const admin = new pg.Client({ connectionString: t.pool.options.connectionString });
    await admin.connect();
    try {
      await admin.query('select pg_terminate_backend($1)', [rows[0]!.pid]);
    } finally {
      await admin.end();
    }
    // Without a listener on the checked-out client this server-side termination is an uncaught exception.
    await expect(client.query('select 1')).rejects.toThrow();
    client.release(true);
    expect((await t.pool.query<{ n: number }>('select 1 as n')).rows).toEqual([{ n: 1 }]);
  });
});
