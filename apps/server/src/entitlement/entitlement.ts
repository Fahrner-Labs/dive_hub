// Entitlements (hosted-DiveHub plan §4, ADR 0087): whether a User may use the paid synchronisation features. A self-hosted
// instance has no billing configured, and then every feature is free: the checks are inert unless DIVEHUB_BILLING_PROVIDER
// is set. Billing writes the row (a later task); this module only reads it. A User without a row is on the free plan.
import { eq, inArray } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { userEntitlement } from '../db/schema.js';

/**
 * Whether a User may use the paid features at `now` (plan §4.6): `pro` and active, or past due (the grace window in which
 * Paddle retries the card), or canceled but still inside the period that was paid for. Free, none, unknown: not entitled.
 */
export function isEntitled(state: { plan: string; status: string; currentPeriodEnd: Date | null } | null, now: Date): boolean {
  if (!state || state.plan !== 'pro') return false;
  if (state.status === 'active' || state.status === 'past_due') return true;
  return state.status === 'canceled' && state.currentPeriodEnd !== null && state.currentPeriodEnd.getTime() > now.getTime();
}

export function createEntitlementService(deps: { db: Db; enforced: boolean; clock?: { now(): number } }) {
  const now = () => new Date((deps.clock ?? { now: Date.now }).now());
  const columns = {
    userId: userEntitlement.userId, plan: userEntitlement.plan,
    status: userEntitlement.status, currentPeriodEnd: userEntitlement.currentPeriodEnd,
  };

  return {
    /** Billing off (no DIVEHUB_BILLING_*): every feature is free, the dual-track invariant (ADR 0087). */
    enforced: deps.enforced,

    /** Whether this User may use the paid features now. One primary-key read, none at all where billing is off. */
    async entitled(userId: string): Promise<boolean> {
      if (!deps.enforced) return true;
      const [row] = await deps.db.select(columns).from(userEntitlement).where(eq(userEntitlement.userId, userId));
      return isEntitled(row ?? null, now());
    },

    /** The entitled ones among these Users, in one query: the worker's tick asks about every Connection at once. */
    async entitledSet(userIds: string[]): Promise<Set<string>> {
      const unique = [...new Set(userIds)];
      if (!deps.enforced) return new Set(unique);
      if (unique.length === 0) return new Set();
      const at = now();
      const rows = await deps.db.select(columns).from(userEntitlement).where(inArray(userEntitlement.userId, unique));
      const found = new Set<string>();
      for (const row of rows) if (isEntitled(row, at)) found.add(row.userId);
      return found;
    },
  };
}

export type EntitlementService = ReturnType<typeof createEntitlementService>;
