// What dives needs of the provider layer, and nothing more (ADR 0053's seam for dives and providers): a Dive's copies
// at Providers (its remote dives and its Waiting pushes), and the Provider's answer when one refuses. dives owns these
// shapes; the provider layer implements them and compose.ts/app.ts pass the real services in.
import type { FastifyReply } from 'fastify';
import type { push } from '../db/schema.js';
import type { WaitingPushView } from './waiting-push-view.js';

/** One of a Dive's Pushes, as the remote-dive rule reads it. */
export type DivePushRow = typeof push.$inferSelect;

/** The remote dive a Dive has at a Provider now (providers/current-remote.ts), passed in by compose.ts. */
export type CurrentRemote = (pushes: DivePushRow[]) => DivePushRow | null;

/**
 * A Provider refusal as dives passes it on: enough for the route to answer it (providers/route-parts.ts). The real
 * error is a ProviderServiceError; dives only needs the problem's code, the Provider and the extras.
 */
export interface DiveProviderRefusal extends Error {
  readonly code: string;
  readonly provider?: { id: string; name: string } | undefined;
  readonly extra?: Record<string, unknown> | undefined;
}

/** What deleting a Dive at Providers did: each copy deleted or kept, and why it stopped, if it did. */
export interface DiveRemoval {
  copies: { provider: string; copy: 'deleted' | 'kept' }[];
  /** The refusal that stopped it; the copies after it were kept. Null when every copy asked for is gone. */
  failure: DiveProviderRefusal | null;
}

/** The Push lookups dives uses: where a Dive is, deleting its copies there, and which changed since they were sent. */
export interface DivePushes {
  currentOf(diveIds: string[]): Promise<Map<string, Map<string, DivePushRow>>>;
  removeAt(userId: string, diveId: string, providers: string[]): Promise<DiveRemoval>;
  changedSinceSent(userId: string, diverIds: string[]): Promise<string[]>;
}

/** The Waiting-push lookups dives uses. */
export interface DiveWaitingPushes {
  deletingItself(diveIds: string[]): Promise<Map<string, Set<string>>>;
  waitingOf(diveIds: string[]): Promise<Map<string, WaitingPushView[]>>;
}

/** How a refusal about a Provider is answered (providers/route-parts.ts), passed in by app.ts. */
export interface DiveProviderAnswers {
  reply(error: DiveProviderRefusal, reply: FastifyReply, extra?: object): FastifyReply;
  answer(error: unknown, reply: FastifyReply): FastifyReply | undefined;
}
