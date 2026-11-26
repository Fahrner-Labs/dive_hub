// The lock a Site import and a Source purge share (FAH-528): at most one of the two runs at a time. Each start
// transaction takes it, then checks the other job's table and inserts its row. It lives here so neither service has
// to import the other.
import { sql } from 'drizzle-orm';

export const SITE_JOB_LOCK = sql`select pg_advisory_xact_lock(hashtext('divehub:site-jobs'))`;
