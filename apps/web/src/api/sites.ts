import { keepPreviousData, queryOptions } from '@tanstack/react-query';
import { SITES_PAGE, type SitesParams } from '../lib/sites-list.ts';
import { liveHealthy } from '../lib/live.ts';
import { keys } from './keys.ts';
import { api, request, type Ok } from './request.ts';

export type SiteView = Ok<'/api/dive-sites/{id}'>;
export type Position = NonNullable<SiteView['position']>;
export type ExternalIdView = SiteView['externalIds'][number];
export type CentreView = Ok<'/api/dive-centres/{id}'>;
export type VerificationCodeView = NonNullable<CentreView['verificationCode']>;
export type SiteRevisionView = Ok<'/api/dive-sites/{id}/revisions'>[number];
export type SiteImportView = Ok<'/api/admin/site-imports/{id}'>;
export type SiteImportArea = SiteImportView['area'];
/** An offer to move one account's Dives from its private site ID to the site's public one (FAH-121). */
export type SiteIdDecisionView = Ok<'/api/dive-site-decisions'>[number];

/** Dive sites (ADR 0020): searched by words, or the ones near a position, nearest first. */
export const sitesQuery = (p: SitesParams & { near?: Position | undefined; within?: number | undefined } = {}) => queryOptions({
  queryKey: [...keys.sites, { q: p.q, country: p.country, mine: p.mine, sort: p.sort, order: p.order, page: p.page, near: p.near, within: p.within }],
  queryFn: async () => request(api.GET('/api/dive-sites', {
    params: {
      query: {
        ...(p.q && { q: p.q }), ...(p.country && { country: p.country }), ...(p.mine && { mine: true }),
        ...(p.sort && { sort: p.sort }), ...(p.order && { order: p.order }),
        ...(p.near && { latitude: p.near.latitude, longitude: p.near.longitude }), ...(p.within && { within: p.within }),
        limit: SITES_PAGE, offset: ((p.page ?? 1) - 1) * SITES_PAGE,
      },
    },
  })),
  placeholderData: keepPreviousData,
});
export const siteQuery = (id: string) => queryOptions({ queryKey: keys.site(id), queryFn: async () => request(api.GET('/api/dive-sites/{id}', { params: { path: { id } } })) });

/** Offers to move an account's Dives from a private site ID to the site's public one (FAH-121); stored, no call to a Provider. */
export const siteIdDecisionsQuery = () => queryOptions({
  queryKey: keys.siteIdDecisions,
  queryFn: async () => request(api.GET('/api/dive-site-decisions')),
});

/** Dive centres (ADR 0043) by name: the ones matching words, or the ones of a Dive site. */
export const centresQuery = (p: { q?: string | undefined; siteId?: string | undefined } = {}) => queryOptions({
  queryKey: [...keys.centres, { q: p.q, siteId: p.siteId }],
  queryFn: async () => request(api.GET('/api/dive-centres', { params: { query: { ...(p.q && { q: p.q }), ...(p.siteId && { siteId: p.siteId }), limit: 100 } } })),
  placeholderData: keepPreviousData,
});
export const centreQuery = (id: string) => queryOptions({ queryKey: keys.centre(id), queryFn: async () => request(api.GET('/api/dive-centres/{id}', { params: { path: { id } } })) });

/** A Dive site's history (ADR 0021): every User reads it; other Users are never named. */
export const siteRevisionsQuery = (id: string) => queryOptions({
  queryKey: keys.siteRevisions(id),
  queryFn: async () => request(api.GET('/api/dive-sites/{id}/revisions', { params: { path: { id } } })),
});

const running = (i: SiteImportView) => i.status === 'queued' || i.status === 'running';
/** The latest Site imports (admins, ADR 0021); asked again every two seconds while one runs. */
export const siteImportsQuery = () => queryOptions({
  queryKey: keys.siteImports,
  queryFn: async () => request(api.GET('/api/admin/site-imports')),
  refetchInterval: (query) => (liveHealthy() ? false : (query.state.data?.some(running) ? 2000 : false)),
});
