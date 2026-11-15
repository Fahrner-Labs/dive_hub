import { keepPreviousData, queryOptions } from '@tanstack/react-query';
import { liveHealthy } from '../lib/live.ts';
import { pollUntilDue } from '../lib/waiting-push.ts';
import { keys } from './keys.ts';
import { api, request, type Ok } from './request.ts';

export type ProviderView = Ok<'/api/providers'>[number];
export type ConnectionView = Ok<'/api/connections'>[number];
export type ProviderStatusView = Ok<'/api/dives/{id}/providers/{provider}'>;
export type PushView = ProviderStatusView['pushes'][number];
export type RemoteSiteView = Ok<'/api/dives/{id}/providers/{provider}/sites'>[number];
export type RequirementView = NonNullable<NonNullable<ProviderView['data']['dives']>['export']>['requirements'][number];
export type LeftOutView = NonNullable<PushView['leftOut']>[number];
export type BuddyView = Ok<'/api/connections/{id}/buddies'>[number];
export type DiveImportPreview = Ok<'/api/connections/{id}/dive-import'>;
export type DiveKind = ConnectionView['fetchKinds'][number];
export type DiveImportMode = NonNullable<ConnectionView['diveImport']>['mode'];
export type GoneDivesOfConnection = Ok<'/api/gone-dives'>[number];
export type GoneDiveView = GoneDivesOfConnection['dives'][number];
export type ConflictsOfConnection = Ok<'/api/provider-conflicts'>[number];
export type ConflictEntryView = ConflictsOfConnection['entries'][number];
/** One field changed on both sides, with both values as the import preview says them. */
export type ConflictFieldView = ConflictEntryView['fields'][number];
/** A choice per Conflict: Dive Hub's value goes out (hub), or the Provider's comes in (provider). */
export type ConflictChoices = Partial<Record<ConflictFieldView['field'], 'hub' | 'provider'>>;
/** Sending many Dives to a Provider (ADR 0047). */
export type PushRunView = Ok<'/api/push-runs'>[number];
export type PushRunDetail = Ok<'/api/push-runs/{id}'>;
export type PushRunPreview = Ok<'/api/connections/{id}/push-runs/preview', 'post'>;

/** The Providers of this server and what each offers (ADR 0027); they change only with the server. */
export const providersQuery = () => queryOptions({ queryKey: keys.providers, queryFn: async () => request(api.GET('/api/providers')), staleTime: Infinity });
/** The User's Connections to Providers (ADR 0024). */
export const connectionsQuery = () => queryOptions({ queryKey: keys.connections, queryFn: async () => request(api.GET('/api/connections')) });
/** The Dive at every Provider that takes dives (for the delete dialog). */
export const diveProvidersQuery = (diveId: string) => queryOptions({
  queryKey: keys.diveProviders(diveId),
  queryFn: async () => request(api.GET('/api/dives/{id}/providers', { params: { path: { id: diveId } } })),
});
/** A Dive at one Provider: its Diver's Connection, what is unmet (ADR 0029), the remote dive it has, its Pushes. */
export const providerStatusQuery = (diveId: string, provider: string) =>
  queryOptions({
    queryKey: keys.providerStatus(diveId, provider),
    queryFn: async () => request(api.GET('/api/dives/{id}/providers/{provider}', { params: { path: { id: diveId, provider } } })),
    // An entry that waits goes out at its due time (ADR 0056): the page asks again then, until it is settled.
    refetchInterval: (q) => (liveHealthy() ? false : pollUntilDue(q.state.data?.waiting ? [q.state.data.waiting] : [], Date.now())),
  });

async function fetchBuddies(connectionId: string, diverId?: string) {
  return request(api.GET('/api/connections/{id}/buddies', {
    params: { path: { id: connectionId }, ...(diverId && { query: { for: diverId } }) },
  }));
}
/** The account's list of people at the Provider (SSI's buddy list); asks the Provider, so only when the User opens it. */
export const buddiesQuery = (connectionId: string) =>
  queryOptions({ queryKey: keys.buddies(connectionId), queryFn: () => fetchBuddies(connectionId), staleTime: 2 * 60_000 });
/** The same list for Link or add: the entries that look like this Diver come first (a sort, never a link). */
export const buddiesForQuery = (connectionId: string, diverId: string) =>
  queryOptions({ queryKey: keys.buddiesFor(connectionId, diverId), queryFn: () => fetchBuddies(connectionId, diverId), staleTime: 30_000 });

/** What importing the account's dives would do now (ADR 0030); reads the Provider, so only when the User asks. */
export const diveImportPreviewQuery = (connectionId: string) =>
  queryOptions({
    queryKey: keys.diveImport(connectionId),
    queryFn: async () => request(api.GET('/api/connections/{id}/dive-import', { params: { path: { id: connectionId } } })),
    staleTime: Infinity,
    retry: false,
  });

/** Dives whose remote dive is gone at a Provider, per Connection (ADR 0051); stored findings, no call to the Provider. */
export const goneDivesQuery = () => queryOptions({ queryKey: keys.goneDives, queryFn: async () => request(api.GET('/api/gone-dives')) });

/** Conflicts with a Provider that wait for the User's choice, per Connection (ADR 0056); stored, no call to the Provider. */
export const providerConflictsQuery = () => queryOptions({ queryKey: keys.providerConflicts, queryFn: async () => request(api.GET('/api/provider-conflicts')) });

/** How each dive at the Provider started and came about, to find why times differ (read only); reads the Provider, so only when asked. */
export const diveTimesQuery = (connectionId: string) =>
  queryOptions({
    queryKey: keys.diveTimes(connectionId),
    queryFn: async () => request(api.GET('/api/connections/{id}/dive-times', { params: { path: { id: connectionId } } })),
    staleTime: 0,
    gcTime: 0,
    retry: false,
  });

/** The Provider's sites near the Dive, nearest first; asks the Provider, so only when the User wants to pick one. */
export const providerSitesQuery = (diveId: string, provider: string) =>
  queryOptions({
    queryKey: keys.providerSites(diveId, provider),
    queryFn: async () => request(api.GET('/api/dives/{id}/providers/{provider}/sites', { params: { path: { id: diveId, provider } } })),
    staleTime: 5 * 60_000,
  });

/**
 * What a run sends: a list (the logbook's, or a scope of the dialog's own) or exactly these Dives (`ids`, when the User
 * left some out), and the three choices (unticked unless the User ticks them).
 */
export interface PushRunChoices {
  list: { siteId?: string | undefined; q?: string | undefined; only?: string | undefined };
  ids?: string[] | undefined;
  updates: boolean; updatesRemovingVerification: boolean; numbers: boolean;
  /** Confirmed once for the run: numbers that can remove a verification (ADR 0052). */
  numbersRemovingVerification: boolean;
  /** With `ids`: also the Dives in their way (a Dive's chain, ADR 0052). */
  withHolders?: boolean | undefined;
}
const pushRunBody = (c: PushRunChoices) => ({
  dives: c.ids
    ? { ids: c.ids }
    : { list: { ...(c.list.siteId && { siteId: c.list.siteId }), ...(c.list.q && { q: c.list.q }), ...(c.list.only && { only: c.list.only }) } },
  updates: c.updates, updatesRemovingVerification: c.updatesRemovingVerification, numbers: c.numbers,
  numbersRemovingVerification: c.numbersRemovingVerification, ...(c.withHolders && { withHolders: true }),
});
export const pushRunActive = (r: Pick<PushRunView, 'status'>) => r.status === 'queued' || r.status === 'running';
/** The User's runs, newest first; asked again every two seconds while one is going (no faster: ADR 0047). */
export const pushRunsQuery = () => queryOptions({
  queryKey: keys.pushRuns, queryFn: async () => request(api.GET('/api/push-runs')), refetchInterval: (q) => (liveHealthy() ? false : (q.state.data?.some(pushRunActive) ? 2000 : false)),
});
/** One run with its items; the list above has the progress, this has what happened to each Dive. */
export const pushRunQuery = (id: string) => queryOptions({ queryKey: keys.pushRun(id), queryFn: async () => request(api.GET('/api/push-runs/{id}', { params: { path: { id } } })) });
/** What a run with these choices would do: Dive Hub's data, and one read of the Provider's logbook where numbers are chosen. */
export const pushRunPreviewQuery = (connectionId: string, choices: PushRunChoices) => queryOptions({
  queryKey: keys.pushRunPreview(connectionId, choices),
  queryFn: async () => request(api.POST('/api/connections/{id}/push-runs/preview', { params: { path: { id: connectionId } }, body: pushRunBody(choices) })),
  placeholderData: keepPreviousData, staleTime: 0,
});
/** A named helper only because two screens start runs and the body is shaped as for the preview; other mutations call `api` inline. */
export async function startPushRun(connectionId: string, choices: PushRunChoices) {
  return request(api.POST('/api/connections/{id}/push-runs', { params: { path: { id: connectionId } }, body: pushRunBody(choices) }));
}
