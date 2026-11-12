import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState, type FormEvent } from 'react';
import { useTranslation } from 'react-i18next';
import {
  api, backupSettingsQuery, backupsQuery, keys, unwrap, type BackupRunView, type BackupSettingsView,
} from './api.ts';
import { announce } from './lib/announce.ts';
import { backupDownloadHref, formatBytes, isBackupActive, isBackupDownloadable, scheduleProblem, type ScheduleProblem } from './lib/backups.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { formValues } from './lib/form.ts';
import { usePageTitle } from './lib/page.ts';
import { settingsHref } from './lib/settings.ts';
import {
  Badge, Button, Checkbox, Form, Muted, Notice, NumberField, PageHeader, Panel, PasswordField, RowsSkeleton, Table, TextField,
} from './ui/index.ts';

/**
 * Administration's backups page (plan FAH-175): take a manual backup encrypted with a passphrase, download a ready
 * archive, and set up scheduled backups. An archive leaves as a file; nothing of another User's data is shown here
 * (plan FAH-175, admins only).
 */
export function BackupsPage() {
  const { t } = useTranslation();
  usePageTitle(t('backups.title'));
  return (
    <>
      <PageHeader
        title={t('backups.title')}
        lead={t('backups.intro')}
        back={{ href: settingsHref('admin'), label: t('settings.section.admin') }}
      />
      <CreateBackup />
      <Backups />
      <Schedule />
    </>
  );
}

/** A manual run: the passphrase is used by the server for this run only and is never stored or logged. */
function CreateBackup() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const runs = useQuery(backupsQuery());
  const busy = runs.data?.some((run) => isBackupActive(run.status)) ?? false;
  const create = useMutation({
    mutationFn: async (passphrase: string) => unwrap(await api.POST('/api/admin/backups', { body: { passphrase } })),
    onSuccess: async () => {
      announce(t('backups.created'));
      await queryClient.invalidateQueries({ queryKey: keys.backups });
    },
  });

  const submit = (event: FormEvent<HTMLFormElement>) => {
    const form = event.currentTarget;
    const values = formValues(event);
    create.mutate(values.passphrase ?? '', { onSuccess: () => form.reset() });
  };

  return (
    <Panel title={t('backups.new')}>
      <Form className="form" onSubmit={submit}>
        <PasswordField
          label={t('backups.passphrase')}
          name="passphrase"
          isRequired
          minLength={8}
          maxLength={1024}
          autoComplete="off"
          description={t('backups.passphraseHint')}
        />
        {busy && <Muted>{t('backups.busy')}</Muted>}
        {create.error && <Notice tone="danger">{errorText(create.error)}</Notice>}
        <div className="form-actions">
          <Button type="submit" variant="primary" isPending={create.isPending} isDisabled={busy}>
            {t('backups.create')}
          </Button>
        </div>
      </Form>
    </Panel>
  );
}

/** Every run newest first. The page polls while one is queued or running, then the row turns ready or failed. */
function Backups() {
  const { t } = useTranslation();
  const display = useDisplay();
  const errorText = useErrorText();
  const list = useQuery(backupsQuery());
  const tone = (run: BackupRunView) => (run.status === 'ready' ? 'success' : run.status === 'failed' ? 'danger' : 'neutral');
  return (
    <Panel title={t('backups.list')}>
      {list.error && <Notice tone="danger">{errorText(list.error)}</Notice>}
      {list.isPending ? <RowsSkeleton rows={2} /> : !list.data?.length ? <Muted>{t('backups.empty')}</Muted> : (
        <Table cards label={t('backups.list')} head={[
          t('backups.time'), t('backups.kind'), t('backups.status'), { label: t('backups.size'), numeric: true },
          t('backups.error'), { label: t('common.actions'), hidden: true },
        ]}>
          {list.data.map((run) => {
            const created = display.dateTime(run.createdAt);
            return (
              <tr key={run.id}>
                <td className="date">{created}</td>
                <td>{t(`backups.kindName.${run.kind}`)}</td>
                <td><Badge tone={tone(run)}>{t(`backups.statusName.${run.status}`)}</Badge></td>
                <td className="num">{formatBytes(run.bytes, display.locale)}</td>
                <td className="backup-error">{run.error ?? t('common.none')}</td>
                <td>
                  {isBackupDownloadable(run.status) && (
                    <a
                      className="btn btn-secondary btn-small"
                      href={backupDownloadHref(run.id)}
                      aria-label={t('backups.downloadOf', { date: created })}
                    >
                      {t('backups.download')}
                    </a>
                  )}
                </td>
              </tr>
            );
          })}
        </Table>
      )}
    </Panel>
  );
}

const PROBLEM_TEXT = {
  interval: 'backups.intervalInvalid',
  retention: 'backups.retentionInvalid',
  recipient: 'backups.recipientMissing',
} as const satisfies Record<ScheduleProblem, string>;

/** The one scheduled-backup setting: interval, retention and the public key scheduled archives encrypt to. */
function Schedule() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const settings = useQuery(backupSettingsQuery());
  return (
    <Panel title={t('backups.schedule')}>
      <Muted>{t('backups.scheduleLead')}</Muted>
      {settings.error && <Notice tone="danger">{errorText(settings.error)}</Notice>}
      {settings.isPending ? <RowsSkeleton rows={3} /> : settings.data && <ScheduleForm initial={settings.data} />}
    </Panel>
  );
}

function ScheduleForm({ initial }: { initial: BackupSettingsView }) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const [enabled, setEnabled] = useState(initial.enabled);
  const [intervalHours, setIntervalHours] = useState(initial.intervalHours);
  const [retentionCount, setRetentionCount] = useState(initial.retentionCount);
  const [recipientKey, setRecipientKey] = useState(initial.recipientPublicKey ?? '');
  const [problem, setProblem] = useState<ScheduleProblem | null>(null);
  const save = useMutation({
    mutationFn: async (values: { enabled: boolean; intervalHours: number; retentionCount: number; recipientPublicKey?: string }) =>
      unwrap(await api.PUT('/api/admin/backups/settings', { body: values })),
    onSuccess: (saved) => {
      queryClient.setQueryData(keys.backupSettings, saved);
      setEnabled(saved.enabled);
      setIntervalHours(saved.intervalHours);
      setRetentionCount(saved.retentionCount);
      setRecipientKey(saved.recipientPublicKey ?? '');
      setProblem(null);
      announce(t('backups.saved'));
    },
  });

  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const found = scheduleProblem({ enabled, intervalHours, retentionCount, recipientPublicKey: recipientKey });
    setProblem(found);
    if (found) return;
    const key = recipientKey.trim();
    save.mutate({ enabled, intervalHours, retentionCount, ...(key ? { recipientPublicKey: key } : {}) });
  };

  return (
    <Form className="form" onSubmit={submit}>
      <Checkbox isSelected={enabled} onChange={setEnabled}>{t('backups.enabled')}</Checkbox>
      <div className="form-grid">
        <NumberField
          label={t('backups.intervalHours')}
          value={intervalHours}
          onChange={setIntervalHours}
          minValue={1}
          maxValue={168}
          isRequired
          description={t('backups.intervalHint')}
        />
        <NumberField
          label={t('backups.retentionCount')}
          value={retentionCount}
          onChange={setRetentionCount}
          minValue={1}
          maxValue={365}
          isRequired
          description={t('backups.retentionHint')}
        />
      </div>
      <TextField
        label={t('backups.recipientPublicKey')}
        value={recipientKey}
        onChange={setRecipientKey}
        isRequired={enabled}
        autoComplete="off"
        spellCheck="false"
        description={t('backups.recipientHint')}
      />
      {problem && <Notice tone="danger">{t(PROBLEM_TEXT[problem])}</Notice>}
      {save.error && <Notice tone="danger">{errorText(save.error)}</Notice>}
      <div className="form-actions">
        <Button type="submit" variant="primary" isPending={save.isPending}>{t('backups.save')}</Button>
      </div>
    </Form>
  );
}
