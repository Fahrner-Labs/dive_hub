import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { supportBundlePreviewQuery, type SupportBundlePreviewView } from '../api.ts';
import { formatBytes } from '../lib/backups.ts';
import { useDisplay, useErrorText } from '../lib/display.ts';
import { usePageTitle } from '../lib/page.ts';
import { settingsHref } from '../lib/settings.ts';
import { SUPPORT_BUNDLE_DOWNLOAD_HREF } from '../lib/support-bundle.ts';
import { Button, Notice, PageHeader, Panel, RowsSkeleton } from '../ui/index.ts';

/**
 * Administration's support bundle page (FAH-291): an admin previews what a redacted support archive would contain,
 * reviews it, then downloads it to send to the developer. Nothing leaves the instance until the admin downloads it.
 */
export function SupportBundlePage() {
  const { t } = useTranslation();
  usePageTitle(t('support.title'));
  return (
    <>
      <PageHeader
        title={t('support.title')}
        lead={t('support.intro')}
        back={{ href: settingsHref('admin'), label: t('settings.section.admin') }}
      />
      <CreateBundle />
    </>
  );
}

/** The preview is built on demand (never on page load), and again whenever the admin asks for a fresh one. */
function CreateBundle() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const [started, setStarted] = useState(false);
  const preview = useQuery({ ...supportBundlePreviewQuery(), enabled: started });
  const start = () => {
    setStarted(true);
    if (started) void preview.refetch();
  };

  return (
    <Panel title={t('support.preview')}>
      <p>{t('support.lead')}</p>
      {preview.error && <Notice tone="danger">{errorText(preview.error)}</Notice>}
      <div className="form-actions">
        <Button variant="primary" icon="download" isPending={preview.isFetching} onPress={start}>
          {t('support.create')}
        </Button>
        {preview.data && (
          <a className="btn btn-secondary" href={SUPPORT_BUNDLE_DOWNLOAD_HREF}>{t('support.download')}</a>
        )}
      </div>
      {started && preview.isPending ? <RowsSkeleton rows={4} /> : preview.data && <Summary data={preview.data} />}
    </Panel>
  );
}

/** What the archive holds, as the preview reports it: versions, log coverage, what was masked, and health. */
function Summary({ data }: { data: SupportBundlePreviewView }) {
  const { t } = useTranslation();
  const display = useDisplay();
  const number = (value: number) => new Intl.NumberFormat(display.locale).format(value);
  const logs = data.logs;
  const coverage = logs.oldestAt && logs.newestAt
    ? t('support.windowValue', { oldest: display.dateTime(logs.oldestAt), newest: display.dateTime(logs.newestAt) })
    : t('support.noLogs');

  return (
    <div className="support-summary">
      <h3>{t('support.overview')}</h3>
      <dl className="facts">
        <div><dt>{t('support.generated')}</dt><dd>{display.dateTime(data.generatedAt)}</dd></div>
        <div><dt>{t('support.size')}</dt><dd>{formatBytes(data.estimatedBytes, display.locale)}</dd></div>
        <div><dt>{t('support.appVersion')}</dt><dd>{data.appVersion}</dd></div>
        <div><dt>{t('support.schemaVersion')}</dt><dd>{data.schemaHead ?? t('support.unknown')}</dd></div>
      </dl>

      <h3>{t('support.logs')}</h3>
      <dl className="facts">
        <div><dt>{t('support.entries')}</dt><dd>{number(logs.entries)}</dd></div>
        <div><dt>{t('support.logSize')}</dt><dd>{formatBytes(logs.bytes, display.locale)}</dd></div>
        <div><dt>{t('support.window')}</dt><dd>{coverage}</dd></div>
        <div><dt>{t('support.dropped')}</dt><dd>{number(logs.droppedEntries)}</dd></div>
      </dl>

      <h3>{t('support.redaction')}</h3>
      <dl className="facts">
        <div><dt>{t('support.keysCensored')}</dt><dd>{number(data.redaction.keysCensored)}</dd></div>
        <div><dt>{t('support.emailsMasked')}</dt><dd>{number(data.redaction.emailsMasked)}</dd></div>
        <div><dt>{t('support.ipsMasked')}</dt><dd>{number(data.redaction.ipsMasked)}</dd></div>
        <div><dt>{t('support.failedJobs')}</dt><dd>{number(data.jobs.failed)}</dd></div>
      </dl>

      <h3>{t('support.health')}</h3>
      <dl className="facts">
        <div><dt>{t('support.uptime')}</dt><dd>{display.duration(data.health.uptimeSec)}</dd></div>
        <div>
          <dt>{t('support.dbLatency')}</dt>
          <dd>{data.health.dbLatencyMs === null ? t('support.unknown') : t('support.millis', { value: number(data.health.dbLatencyMs) })}</dd>
        </div>
        <div><dt>{t('support.worker')}</dt><dd>{t(data.health.workerRunning ? 'support.workerRunning' : 'support.workerStopped')}</dd></div>
        <div>
          <dt>{t('support.queueDepth')}</dt>
          <dd>{data.health.queueDepth === null ? t('support.unknown') : number(data.health.queueDepth)}</dd>
        </div>
      </dl>
    </div>
  );
}
