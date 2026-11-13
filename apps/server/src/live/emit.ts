// Hints for open pages (ADR 0066): a mutation tells PostgreSQL, inside its own transaction, that something a User can
// read has changed. The payload carries only a kind, an id and who may hear it; the client refetches through the normal
// API, which stays the only source of truth.
import { eq, sql } from 'drizzle-orm';
import type { Queryable } from '../db/client.js';
import { connection, dive, diverManagement } from '../db/schema.js';

/** The PostgreSQL channel every app process listens on. */
export const HINT_CHANNEL = 'divehub_hints';

/** What changed; each maps to a query-key root in apps/web/src/api.ts. */
export const HINT_KINDS = [
  'dive', 'site', 'waitingPush', 'import', 'siteImport', 'pushRun', 'certification', 'connection', 'buddySuggestion',
] as const;
export type HintKind = (typeof HINT_KINDS)[number];

/**
 * Who may hear a hint: the listed Users, or `*` for every signed-in User. Only shared data (Dive sites, ADR 0020) uses
 * the wildcard; naming every User would grow the payload with the instance and overflow PostgreSQL's 8000-byte NOTIFY
 * limit. Both forms stay off the wire.
 */
export type HintAudience = string[] | '*';

export interface Hint {
  kind: HintKind;
  /** The changed thing's id; the client uses it to keep a dirty form pinned (a Dive). */
  id: string;
  /** Who may already read the thing (or every signed-in User, for shared data); never sent on the wire. */
  audience: HintAudience;
}

const kinds = new Set<string>(HINT_KINDS);

/**
 * Sends the hint in the caller's transaction. A rolled-back write notifies nobody, and a committed write always does
 * (the same transactional outbox pattern as graphile_worker.add_job). An empty audience sends nothing.
 */
export async function emitHint(q: Queryable, hint: Hint): Promise<void> {
  const audience = hint.audience === '*' ? '*' : [...new Set(hint.audience)].filter((id) => id.length > 0);
  if (audience !== '*' && audience.length === 0) return;
  await q.execute(sql`select pg_notify(${HINT_CHANNEL}, ${JSON.stringify({ v: 1, kind: hint.kind, id: hint.id, audience })})`);
}

/** The Users who keep this Diver's logbook, and so may read its Dives. */
export async function audienceOfDiver(q: Queryable, diverId: string): Promise<string[]> {
  const rows = await q.select({ userId: diverManagement.userId }).from(diverManagement).where(eq(diverManagement.diverId, diverId));
  return rows.map((r) => r.userId);
}

/** The Users who may read this Dive. After a merge the Dive still names its Diver. */
export async function audienceOfDive(q: Queryable, diveId: string): Promise<string[]> {
  const [row] = await q.select({ diverId: dive.diverId }).from(dive).where(eq(dive.id, diveId));
  return row ? audienceOfDiver(q, row.diverId) : [];
}

/** The Users who may read a Connection's Diver. */
export async function audienceOfConnection(q: Queryable, connectionId: string): Promise<string[]> {
  const [row] = await q.select({ diverId: connection.diverId }).from(connection).where(eq(connection.id, connectionId));
  return row ? audienceOfDiver(q, row.diverId) : [];
}

/** Parses a NOTIFY payload; malformed or unknown ones are dropped rather than delivered. */
export function parseHint(payload: string): Hint | null {
  let value: unknown;
  try {
    value = JSON.parse(payload);
  } catch {
    return null;
  }
  if (typeof value !== 'object' || value === null) return null;
  const { v, kind, id, audience } = value as Record<string, unknown>;
  if (v !== 1 || typeof kind !== 'string' || !kinds.has(kind) || typeof id !== 'string' || id.length === 0) return null;
  // The wildcard is only for shared data: a Diver hint must still name who may read it.
  if (audience === '*') {
    if (kind !== 'site') return null;
    return { kind: 'site', id, audience: '*' };
  }
  if (!Array.isArray(audience) || !audience.every((u) => typeof u === 'string')) return null;
  return { kind: kind as HintKind, id, audience: audience as string[] };
}
