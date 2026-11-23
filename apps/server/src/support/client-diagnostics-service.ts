// Client diagnostics (ADR 0080): the instance switch, the bounded upload store behind POST /api/client-diagnostics,
// its 90-day purge, and the newest-first rows a support bundle reads. The server never trusts the client's redaction:
// it truncates and redacts every entry again before persisting.
import { desc, eq, lt, sql } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { clientDiagnostic, clientDiagnosticsSettings } from '../db/schema.js';
import { redactValue } from './redact.js';

/** The most entries one upload may carry (ADR 0080 D2). */
export const MAX_UPLOAD_ENTRIES = 50;
/** Uploads one User may make per hour; the count is taken at insert, so no limiter infrastructure is needed. */
export const UPLOADS_PER_HOUR = 5;
/** The store keeps only the newest rows on the whole instance. */
export const KEEP_NEWEST = 500;
/** How long the worker keeps a row (ADR 0080 D3). */
export const PURGE_AFTER_DAYS = 90;
/** Long fields are cut to these lengths before redaction and persistence. */
export const MESSAGE_MAX_LENGTH = 1000;
export const STACK_MAX_LENGTH = 4000;

export class ClientDiagnosticsError extends Error {
  constructor(readonly code: 'client_diagnostics_disabled' | 'client_diagnostics_limited') {
    super(code);
  }
}

export interface ClientDiagnosticsEntryInput {
  occurredAt: string;
  level: 'error' | 'warn';
  code?: string;
  /** The build the client captured this entry in; absent in buffers written before FAH-456. */
  clientVersion?: string;
  message: string;
  stack?: string;
  context?: Record<string, string>;
}

export interface ClientDiagnosticsUpload {
  client: { name: 'web' | 'android'; version: string };
  capturedAt: string;
  entries: ClientDiagnosticsEntryInput[];
}

export interface ClientDiagnosticsEntryView {
  client: { name: string; version: string };
  capturedAt: string;
  occurredAt: string;
  level: string;
  code?: string;
  message: string;
  stack?: string;
  context?: Record<string, string>;
}

/** The entry as it may be stored: cut to the field limits, then every string and keyed secret redacted (R1–R3). */
function prepareEntry(entry: ClientDiagnosticsEntryInput): ClientDiagnosticsEntryInput {
  return redactValue({
    occurredAt: entry.occurredAt,
    level: entry.level,
    ...(entry.code !== undefined && { code: entry.code }),
    ...(entry.clientVersion !== undefined && { clientVersion: entry.clientVersion.slice(0, 100) }),
    message: entry.message.slice(0, MESSAGE_MAX_LENGTH),
    ...(entry.stack !== undefined && { stack: entry.stack.slice(0, STACK_MAX_LENGTH) }),
    ...(entry.context !== undefined && { context: entry.context }),
  });
}

const toView = (row: typeof clientDiagnostic.$inferSelect): ClientDiagnosticsEntryView => ({
  client: { name: row.clientName, version: row.clientVersion },
  capturedAt: row.capturedAt.toISOString(),
  occurredAt: row.occurredAt.toISOString(),
  level: row.level,
  ...(row.code !== null && { code: row.code }),
  message: row.message,
  ...(row.stack !== null && { stack: row.stack }),
  ...(row.contextJson !== null && { context: row.contextJson }),
});

export function createClientDiagnosticsService(db: Db) {
  async function readEnabled(): Promise<boolean> {
    const [row] = await db.select({ enabled: clientDiagnosticsSettings.enabled })
      .from(clientDiagnosticsSettings).where(eq(clientDiagnosticsSettings.id, true));
    return row?.enabled ?? false;
  }

  return {
    /** Whether this instance accepts uploads; off when no admin saved the setting. */
    readEnabled,

    /** The one settings row as admins see it. */
    async settings(): Promise<{ enabled: boolean }> {
      return { enabled: await readEnabled() };
    },

    /** Admins: switch collection on or off for the instance. Off refuses every new upload; stored rows stay. */
    async setEnabled(enabled: boolean): Promise<{ enabled: boolean }> {
      const [row] = await db.insert(clientDiagnosticsSettings).values({ id: true, enabled, updatedAt: new Date() })
        .onConflictDoUpdate({ target: clientDiagnosticsSettings.id, set: { enabled, updatedAt: new Date() } })
        .returning({ enabled: clientDiagnosticsSettings.enabled });
      return { enabled: row!.enabled };
    },

    /**
     * Store one upload. The setting check, the hourly upload count, the insert and the 500-newest eviction share one
     * transaction: a refused upload writes nothing. All rows of an upload share the transaction's `created_at`, so
     * counting distinct `created_at` values counts uploads, not entries.
     */
    async ingest(userId: string, upload: ClientDiagnosticsUpload): Promise<void> {
      const entries = upload.entries.slice(0, MAX_UPLOAD_ENTRIES).map(prepareEntry);
      await db.transaction(async (tx) => {
        const [setting] = await tx.select({ enabled: clientDiagnosticsSettings.enabled })
          .from(clientDiagnosticsSettings).where(eq(clientDiagnosticsSettings.id, true));
        if (!setting?.enabled) throw new ClientDiagnosticsError('client_diagnostics_disabled');

        const [recent] = (await tx.execute(sql`
          select count(distinct created_at)::int as n
          from client_diagnostic
          where user_id = ${userId} and created_at > now() - interval '1 hour'`)).rows as { n: number }[];
        if ((recent?.n ?? 0) >= UPLOADS_PER_HOUR) throw new ClientDiagnosticsError('client_diagnostics_limited');
        if (entries.length === 0) return;

        await tx.insert(clientDiagnostic).values(entries.map((entry) => ({
          userId,
          clientName: upload.client.name,
          clientVersion: entry.clientVersion ?? upload.client.version,
          capturedAt: new Date(upload.capturedAt),
          occurredAt: new Date(entry.occurredAt),
          level: entry.level,
          code: entry.code ?? null,
          message: entry.message,
          stack: entry.stack ?? null,
          contextJson: entry.context ?? null,
        })));

        await tx.execute(sql`
          delete from client_diagnostic
          where id not in (
            select id from client_diagnostic order by created_at desc, id desc limit ${KEEP_NEWEST}
          )`);
      });
    },

    /** The store's newest rows, for the support bundle; empty while the instance has collection switched off. */
    async entries(): Promise<ClientDiagnosticsEntryView[]> {
      if (!(await readEnabled())) return [];
      const rows = await db.select().from(clientDiagnostic)
        .orderBy(desc(clientDiagnostic.createdAt), desc(clientDiagnostic.id));
      return rows.map(toView);
    },

    /** The worker's daily purge: removes rows older than 90 days and returns how many. */
    async purge(now: Date = new Date()): Promise<number> {
      const cutoff = new Date(now.getTime() - PURGE_AFTER_DAYS * 24 * 60 * 60 * 1000);
      const removed = await db.delete(clientDiagnostic).where(lt(clientDiagnostic.createdAt, cutoff))
        .returning({ id: clientDiagnostic.id });
      return removed.length;
    },
  };
}

export type ClientDiagnosticsService = ReturnType<typeof createClientDiagnosticsService>;
