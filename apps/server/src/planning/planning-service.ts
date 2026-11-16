// The planning calculations for a signed-in User (ADR 0053): the route passes the User's id, the service finds the Divers
// they manage, so a route holds no query.
import type { Db } from '../db/client.js';
import { managedDiverIds } from '../divers/management.js';
import { planBottomTime, type BottomTimeQuery } from './bottom-time-service.js';
import { planGasPlan, type GasPlanQuery } from './gas-plan-service.js';
import { diverLoadings } from './loadings-service.js';

export function createPlanningService(db: Db) {
  const managedBy = async (userId: string) => [...(await managedDiverIds(db, userId))];
  return {
    bottomTime: async (userId: string, query: BottomTimeQuery) => planBottomTime(db, await managedBy(userId), query),
    gasPlan: async (userId: string, query: GasPlanQuery) => planGasPlan(db, await managedBy(userId), query),
    loadings: async (userId: string, diverId: string) => diverLoadings(db, await managedBy(userId), diverId),
  };
}

export type PlanningService = ReturnType<typeof createPlanningService>;
