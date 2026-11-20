/**
 * The web client's API layer, split by domain (FAH-48): the shared client and request helper, the query keys,
 * and one module per domain for its view types and queries. Pages and lib/ import everything from here.
 */
export { api, ApiError, isUnauthorized, NetworkError, noAnswer, request, shouldRetry, unwrap } from './api/request.ts';
export type { ProblemCode, ProblemDetails, UnmetView } from './api/request.ts';
export * from './api/keys.ts';
export * from './api/account.ts';
export * from './api/admin.ts';
export * from './api/diagnostics.ts';
export * from './api/dives.ts';
export * from './api/divers.ts';
export * from './api/equipment.ts';
export * from './api/imports.ts';
export * from './api/planning.ts';
export * from './api/providers.ts';
export * from './api/sites.ts';
