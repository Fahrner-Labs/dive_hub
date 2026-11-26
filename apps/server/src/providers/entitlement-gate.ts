// The paywall's one refusal (hosted-DiveHub plan §4.4): the paid features refuse a User who is not entitled with
// `entitlement_required` (402), carrying the offer the client renders. Used by the Connection's automation writes and
// the Push runs; the worker's backstop skips instead (it answers nobody).
import type { EntitlementService } from '../entitlement/entitlement.js';
import { ProviderServiceError } from './registry.js';

/** The paid plan on offer: what the client shows when it unlocks the action (plan §4.5). */
const PRO_OFFER = { plan: 'pro', features: ['automation', 'push_runs'] } as const;

/** Throws `entitlement_required` when this User may not use the paid features now (a no-op where billing is off). */
export async function requireEntitlement(entitlements: EntitlementService, userId: string): Promise<void> {
  if (!(await entitlements.entitled(userId))) throw new ProviderServiceError('entitlement_required', undefined, { offer: PRO_OFFER });
}
