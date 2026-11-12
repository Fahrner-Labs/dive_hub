import { drizzle, type NodePgDatabase } from 'drizzle-orm/node-postgres';
import pg from 'pg';
import * as schema from './schema.js';

export type Db = NodePgDatabase<typeof schema>;
export type Tx = Parameters<Parameters<Db['transaction']>[0]>[0];
/** For a helper that runs inside the caller's transaction, or on its own when called with the Db. */
export type Queryable = Db | Tx;

export function createDb(databaseUrl: string, { max = 10 }: { max?: number } = {}): { db: Db; pool: pg.Pool } {
  const pool = new pg.Pool({ connectionString: databaseUrl, max });
  // A client losing its connection must not crash the process, whether it is idle in the pool or checked out:
  // pg-pool takes its own listener off a client while it is checked out, so the pool gives each one a listener here.
  const lost = (error: Error) => console.error('PostgreSQL connection error', error);
  pool.on('error', lost);
  pool.on('connect', (client) => client.on('error', lost));
  return { db: drizzle(pool, { schema }), pool };
}
