import { queryOptions } from '@tanstack/react-query';
import { keys } from './keys.ts';
import { api, request, type Ok } from './request.ts';

/** One backup run as the admin page lists it (plan FAH-175): no archive until it is ready. */
export type BackupRunView = Ok<'/api/admin/backups'>[number];
/** The instance's scheduled-backup setting. */
export type BackupSettingsView = Ok<'/api/admin/backups/settings'>;
/** What an on-demand support bundle would contain, as an admin reviews it before downloading (FAH-291). */
export type SupportBundlePreviewView = Ok<'/api/admin/support-bundle/preview'>;
export type SiteDataView = Ok<'/api/admin/provider-site-data'>[number];

/** Admin backups (plan FAH-175): poll while a run is queued or running, as no live hint carries their progress. */
export const backupsQuery = () =>
  queryOptions({
    queryKey: keys.backups,
    queryFn: async () => request(api.GET('/api/admin/backups')),
    refetchInterval: (q) => (q.state.data?.some((r) => r.status === 'queued' || r.status === 'running') ? 2000 : false),
  });
/** The instance's scheduled-backup setting. */
export const backupSettingsQuery = () =>
  queryOptions({
    queryKey: keys.backupSettings,
    queryFn: async () => request(api.GET('/api/admin/backups/settings')),
  });
/** The support bundle's preview: built on demand, so the page asks only after the admin starts it (FAH-291). */
export const supportBundlePreviewQuery = () =>
  queryOptions({
    queryKey: keys.supportBundlePreview,
    queryFn: async () => request(api.GET('/api/admin/support-bundle/preview')),
  });

/** Admins: per Provider whose dives Users import, whether new Dive sites may be made from its site data (ADR 0030). */
export const siteDataQuery = () => queryOptions({
  queryKey: keys.siteData, queryFn: async () => request(api.GET('/api/admin/provider-site-data')),
});

/** Admins: whether this instance offers the MCP endpoint, and how many AI accesses exist. */
export const aiAccessSettingQuery = () => queryOptions({
  queryKey: keys.aiAccessSetting, queryFn: async () => request(api.GET('/api/admin/ai-access')),
});
