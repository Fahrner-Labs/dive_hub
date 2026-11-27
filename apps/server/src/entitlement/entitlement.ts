// Entitlements (hosted-DiveHub plan §4, ADR 0087): whether a User may use the paid synchronisation features. A self-hosted
// instance has no billing configured, and then every feature is free: the checks are inert unless DIVEHUB_BILLING_PROVIDER
// is set. This module owns the one `user_entitlement` row per User: the read the paywall asks, and the upsert the billing
// webhook (ADR 0089) applies. A User without a row is on the free plan.
import { eq, inArray, lte } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { userEntitlement } from '../db/schema.js';

/** The provider-neutral state a subscription event settles on (plan §4.6). `none` is a row that was deleted. */
export type EntitlementStatus = 'active' | 'past_due' | 'canceled';

/** The paid plan on offer: what a client shows when a not-entitled User meets a gate (plan §4.5, ADR 0089). */
export const PRO_OFFER = { plan: 'pro', features: ['automation', 'push_runs'] } as const;

/** The plan and status a client shows in Settings; a User without a row is on the free plan (hosted-DiveHub plan §4.4). */
export interface PlanSummary {
  plan: 'free' | 'pro';
  status: EntitlementStatus | 'none';
  currentPeriodEnd: Date | null;
  entitled: boolean;
}

/** One subscription state the billing webhook applies, already mapped from the provider's vocabulary. */
export interface SubscriptionState {
  userId: string;
  paddleCustomerId: string;
  paddleSubscriptionId: string;
  status: EntitlementStatus;
  /** The period that was paid for; `canceled` keeps it so the User stays entitled until it ends. */
  currentPeriodEnd: Date | null;
  /** The provider's event time: an older event never overwrites a newer one (out-of-order safe). */
  occurredAt: Date;
}

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

    /**
     * The plan and status the Settings' Plan section shows, with whether the paid features are usable now. No row is the
     * free plan; where billing is off everything is free and the section is hidden.
     */
    async summary(userId: string): Promise<PlanSummary> {
      if (!deps.enforced) return { plan: 'free', status: 'none', currentPeriodEnd: null, entitled: true };
      const [row] = await deps.db.select(columns).from(userEntitlement).where(eq(userEntitlement.userId, userId));
      const entitled = isEntitled(row ?? null, now());
      // A canceled subscription whose paid period has ended is back to no plan (ADR 0089: canceled → none), even though
      // its row stays for the Paddle customer. Otherwise Settings would still call it Pro and hide the Upgrade action.
      if (row?.status === 'canceled' && !entitled) return { plan: 'free', status: 'none', currentPeriodEnd: null, entitled: false };
      return {
        plan: row?.plan === 'pro' ? 'pro' : 'free',
        status: (row?.status as EntitlementStatus | undefined) ?? 'none',
        currentPeriodEnd: row?.currentPeriodEnd ?? null,
        entitled,
      };
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

    /**
     * The Paddle IDs kept for a User, so checkout reuses the customer and the portal has one to open; null when the User
     * has no row (never subscribed).
     */
    async paddleAccount(userId: string): Promise<{ customerId: string | null; subscriptionId: string | null } | null> {
      const [row] = await deps.db.select({
        customerId: userEntitlement.paddleCustomerId, subscriptionId: userEntitlement.paddleSubscriptionId,
      }).from(userEntitlement).where(eq(userEntitlement.userId, userId));
      return row ?? null;
    },

    /**
     * The User a stored subscription belongs to, matched on the subscription id alone. A webhook for one of a User's
     * earlier subscriptions must not be applied to their current one, so the customer id is deliberately not a key here
     * (ADR 0089).
     */
    async userOfSubscription(subscriptionId: string): Promise<string | null> {
      const [row] = await deps.db.select({ userId: userEntitlement.userId }).from(userEntitlement)
        .where(eq(userEntitlement.paddleSubscriptionId, subscriptionId));
      return row?.userId ?? null;
    },

    /**
     * Upserts one subscription state (plan §4.6). Keyed by the User, so replaying an event changes nothing; an event older
     * than the row's last write is ignored, so a late `subscription.updated` cannot roll back a `canceled` or the reverse.
     * The write is one atomic statement: two concurrent deliveries cannot leave the older one last.
     */
    async applySubscription(state: SubscriptionState): Promise<void> {
      const values = {
        userId: state.userId,
        plan: 'pro',
        status: state.status,
        paddleCustomerId: state.paddleCustomerId,
        paddleSubscriptionId: state.paddleSubscriptionId,
        currentPeriodEnd: state.currentPeriodEnd,
        updatedAt: state.occurredAt,
      };
      await deps.db.insert(userEntitlement).values(values).onConflictDoUpdate({
        target: userEntitlement.userId,
        set: values,
        setWhere: lte(userEntitlement.updatedAt, state.occurredAt),
      });
    },
  };
}

export type EntitlementService = ReturnType<typeof createEntitlementService>;
