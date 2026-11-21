import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState, useSyncExternalStore } from 'react';
import { useTranslation } from 'react-i18next';
import { api, ApiError, clientDiagnosticsStatusQuery, keys, request, unwrap } from '../api.ts';
import { useErrorText } from '../lib/display.ts';
import {
  clearDiagnostics, CLIENT_NAME, CLIENT_VERSION, readDiagnostics, subscribeDiagnostics,
} from '../lib/diagnostics.ts';
import { Button, Notice } from '../ui/index.ts';

const countEntries = () => readDiagnostics().length;

/**
 * "Report a problem" (FAH-292, ADR 0080): the local buffer's status, a button that uploads it to this instance, and the
 * result by code. Kept independent of the page's ErrorBoundary so a failing subtree cannot take the report button with
 * it; it only reads the buffer and the instance's status, never the failed component's data.
 */
export function ReportProblem() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const status = useQuery(clientDiagnosticsStatusQuery());
  const count = useSyncExternalStore(subscribeDiagnostics, countEntries, () => 0);
  const [sent, setSent] = useState<boolean | null>(null);
  const send = useMutation({
    mutationFn: async () => {
      // Re-check at the moment of sending: an admin may have switched collection off since the panel opened.
      const current = await request(api.GET('/api/client-diagnostics/status'));
      if (!current.enabled) throw new ApiError('client_diagnostics_disabled', 403, 'client_diagnostics_disabled');
      const entries = readDiagnostics();
      unwrap(await api.POST('/api/client-diagnostics', {
        body: { client: { name: CLIENT_NAME, version: CLIENT_VERSION }, capturedAt: new Date().toISOString(), entries },
      }));
      clearDiagnostics();
      return entries.length;
    },
    onSuccess: () => {
      setSent(true);
      queryClient.setQueryData(keys.clientDiagnosticsStatus, { enabled: true });
    },
    onError: (error) => {
      setSent(false);
      if (error instanceof ApiError && error.code === 'client_diagnostics_disabled') {
        queryClient.setQueryData(keys.clientDiagnosticsStatus, { enabled: false });
      }
    },
  });
  const enabled = status.data?.enabled ?? false;

  return (
    <>
      <p>
        {status.isPending
          ? t('clientDiagnostics.report.checking')
          : status.data?.enabled ? t('clientDiagnostics.report.on') : status.data ? t('clientDiagnostics.report.off') : null}
      </p>
      {status.error && <Notice tone="danger">{errorText(status.error)}</Notice>}
      {status.data && (
        <p className="muted">
          {!enabled ? t('clientDiagnostics.report.offHint')
            : count === 0 ? t('clientDiagnostics.report.nothing')
              : t('clientDiagnostics.report.count', { count })}
        </p>
      )}
      {sent && <Notice tone="success">{t('clientDiagnostics.report.sent')}</Notice>}
      {send.error && <Notice tone="danger">{errorText(send.error)}</Notice>}
      <div className="form-actions">
        <Button
          variant="primary" isPending={send.isPending} isDisabled={!enabled || count === 0}
          onPress={() => { setSent(null); send.mutate(); }}
        >
          {t('clientDiagnostics.report.send')}
        </Button>
      </div>
    </>
  );
}
