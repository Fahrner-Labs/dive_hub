// The entitlement state machine (hosted-DiveHub plan §4.6): pro and active or past due (the grace Paddle retries in),
// or canceled but still inside the period that was paid for. Everything else, and a User without a row, is free.
import { describe, expect, it } from 'vitest';
import { createEntitlementService, isEntitled } from '../src/entitlement/entitlement.js';
import type { Db } from '../src/db/client.js';

type Plan = 'free' | 'pro';
type Status = 'none' | 'active' | 'past_due' | 'canceled';
const NOW = new Date('2026-11-26T12:00:00Z');
const periodEnd = (at: string) => new Date(at);
const state = (plan: Plan, status: Status, end: Date | null = null) => ({ plan, status, currentPeriodEnd: end });

describe('isEntitled', () => {
  it('lets a pro plan that is active or past due through, the grace window included', () => {
    expect(isEntitled(state('pro', 'active'), NOW)).toBe(true);
    expect(isEntitled(state('pro', 'past_due'), NOW)).toBe(true);
  });

  it('keeps a canceled pro plan until the paid period ends, then frees it', () => {
    expect(isEntitled(state('pro', 'canceled', periodEnd('2026-12-10T00:00:00Z')), NOW)).toBe(true);
    expect(isEntitled(state('pro', 'canceled', periodEnd('2026-11-26T11:59:59Z')), NOW)).toBe(false);
    expect(isEntitled(state('pro', 'canceled'), NOW)).toBe(false);
  });

  it('frees a User with no row, no plan, or no subscription', () => {
    expect(isEntitled(null, NOW)).toBe(false);
    expect(isEntitled(state('free', 'active'), NOW)).toBe(false);
    expect(isEntitled(state('free', 'none'), NOW)).toBe(false);
    expect(isEntitled(state('pro', 'none'), NOW)).toBe(false);
  });
});

describe('the entitlement service', () => {
  it('answers entitled for everything where billing is off, without reading the database (the dual-track invariant)', async () => {
    let reads = 0;
    const db = { select: () => { reads++; throw new Error('billing off must not read the database'); } } as unknown as Db;
    const entitlements = createEntitlementService({ db, enforced: false, clock: { now: () => NOW.getTime() } });
    expect(entitlements.enforced).toBe(false);
    expect(await entitlements.entitled('any-user')).toBe(true);
    expect(await entitlements.entitledSet(['a', 'b', 'a'])).toEqual(new Set(['a', 'b']));
    expect(reads).toBe(0);
  });
});
