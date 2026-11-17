import type { paths } from '@dive-hub/api-client';
import { keepPreviousData, queryOptions } from '@tanstack/react-query';
import { keys } from './keys.ts';
import { api, request, type Ok } from './request.ts';

export type DiverView = Ok<'/api/divers'>[number];
export type DeviceView = Ok<'/api/devices'>[number];
/** A Diver's details and codes as every User sees them (ADR 0043). */
export type DiverDetailsView = Ok<'/api/divers/{id}/details'>;
/** A Diver's current N₂, CNS and OTU estimate from their logged dives, with its basis (ADR 0071). */
export type LoadingsView = NonNullable<Ok<'/api/divers/{id}/loadings'>>;
/** What the server says a scanned or pasted code is. */
export type ReadCodeView = paths['/api/verification-codes/read']['post']['responses'][200]['content']['application/json'];
export type FoundDiverView = Ok<'/api/divers/search'>[number];
export type ExternalDiverView = Ok<'/api/external-divers'>['divers'][number];
export type CertificationView = Ok<'/api/divers/{id}/certifications'>[number];

export const diversQuery = () => queryOptions({ queryKey: keys.divers, queryFn: async () => request(api.GET('/api/divers')) });
export const devicesQuery = () => queryOptions({ queryKey: keys.devices, queryFn: async () => request(api.GET('/api/devices')) });
/** What this Diver's logged dives leave in them now (ADR 0071): computed on request, nothing stored. */
export const diverLoadingsQuery = (diverId: string) => queryOptions({
  queryKey: keys.loadings(diverId),
  queryFn: async () => request(api.GET('/api/divers/{id}/loadings', { params: { path: { id: diverId } } })),
  // An estimate from the Dives changes with them: ask again whenever the Diver's page is opened.
  staleTime: 0,
});

/** Every Diver of the instance whose name matches, the User's own first (ADR 0028). */
export const diverSearchQuery = (q: string) =>
  queryOptions({ queryKey: keys.diverSearch(q), queryFn: async () => request(api.GET('/api/divers/search', { params: { query: { q, limit: 20 } } })), placeholderData: keepPreviousData });

/** People Users dived with who keep no logbook here (ADR 0028). */
export const externalDiversQuery = (q = '') =>
  queryOptions({
    queryKey: keys.externalDivers(q),
    queryFn: async () => request(api.GET('/api/external-divers', { params: { query: { ...(q && { q }), limit: 200 } } })),
    placeholderData: keepPreviousData,
  });

export const diverDetailsQuery = (id: string) => queryOptions({
  queryKey: keys.diverDetails(id),
  queryFn: async () => request(api.GET('/api/divers/{id}/details', { params: { path: { id } } })),
});
export const bodyWeightsQuery = (id: string) => queryOptions({
  queryKey: keys.bodyWeights(id),
  queryFn: async () => request(api.GET('/api/divers/{id}/body-weights', { params: { path: { id } } })),
});
/** A Diver's certification cards, newest first (ADR 0050). */
export const certificationsQuery = (id: string) => queryOptions({
  queryKey: keys.certifications(id),
  queryFn: async () => request(api.GET('/api/divers/{id}/certifications', { params: { path: { id } } })),
});
