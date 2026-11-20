import { queryOptions } from '@tanstack/react-query';
import { keys } from './keys.ts';
import { api, request, type Ok } from './request.ts';

/** Whether this instance accepts client diagnostics (ADR 0080); off by default. */
export type ClientDiagnosticsStatusView = Ok<'/api/client-diagnostics/status'>;
/** The same switch as an admin reads and changes it. */
export type ClientDiagnosticsSettingsView = Ok<'/api/admin/client-diagnostics'>;

/** Asked when the report surface opens and again before the upload (ADR 0080). */
export const clientDiagnosticsStatusQuery = () =>
  queryOptions({
    queryKey: keys.clientDiagnosticsStatus,
    queryFn: async () => request(api.GET('/api/client-diagnostics/status')),
  });

/** Admins: whether this instance collects client diagnostics. */
export const clientDiagnosticsSettingQuery = () =>
  queryOptions({
    queryKey: keys.clientDiagnosticsSetting,
    queryFn: async () => request(api.GET('/api/admin/client-diagnostics')),
  });
