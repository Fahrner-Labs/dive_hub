import { useState, type FormEvent } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import { FileTrigger } from 'react-aria-components';
import { Trans, useTranslation } from 'react-i18next';
import { NetworkError, setupRestoreArchivesQuery, unwrap } from './api.ts';
import { useErrorText } from './lib/display.ts';
import { formValues } from './lib/form.ts';
import { Button, Form, Muted, Notice, PasswordField, RadioGroup, Select, TextField } from './ui/index.ts';

type Source = 'upload' | 'placed';
type Restored = { restored: true; createdAt: string; migrationHead: string | null };

/**
 * The setup screen's restore path (plan FAH-175): upload an archive, or pick one the operator placed in
 * /data/restore/ on the server. On success the server exits, so the page says to restart it instead of reloading.
 */
export function SetupRestore() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const archives = useQuery(setupRestoreArchivesQuery());
  const [file, setFile] = useState<File | null>(null);
  const [source, setSource] = useState<Source>('upload');
  const [placedChoice, setPlacedChoice] = useState<string | null>(null);
  const [missingSource, setMissingSource] = useState(false);
  const [restored, setRestored] = useState<Restored | null>(null);

  const placed = archives.data?.archives ?? [];
  const effectiveSource: Source = placed.length > 0 ? source : 'upload';
  const chosen = placedChoice ?? placed[0]?.name ?? null;

  const restore = useMutation({
    mutationFn: async ({ token, passphrase }: Record<string, string>): Promise<Restored> => {
      const body = new FormData();
      body.append('token', token!.trim());
      body.append('passphrase', passphrase!);
      if (effectiveSource === 'upload' && file) body.append('file', file);
      else body.append('placed', chosen ?? '');
      const response = await fetch('/api/setup/restore', { method: 'POST', body })
        .catch(() => { throw new NetworkError('No answer from the server'); });
      const json: unknown = await response.json().catch(() => undefined);
      return unwrap({ data: json as Restored, error: json, response });
    },
    onSuccess: (result) => setRestored(result),
  });

  if (restored) {
    return (
      <div className="form">
        <Notice tone="success">
          <Trans i18nKey="setup.restored" components={{ code: <code /> }} />
        </Notice>
        <Muted>{t('setup.restoredHint')}</Muted>
      </div>
    );
  }

  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (effectiveSource === 'upload' ? !file : !chosen) {
      setMissingSource(true);
      return;
    }
    setMissingSource(false);
    restore.mutate(formValues(event));
  };

  return (
    <>
      <p className="muted">{t('setup.restoreIntro')}</p>
      <Form className="form" onSubmit={submit}>
        <TextField label={t('setup.token')} name="token" isRequired autoComplete="off" autoCorrect="off" spellCheck="false" />
        <PasswordField label={t('setup.passphrase')} name="passphrase" isRequired autoComplete="off" />
        {effectiveSource === 'upload' ? (
          <div className="field">
            <span className="field-label">{t('setup.archive')}</span>
            <div className="form-actions">
              <FileTrigger
                acceptedFileTypes={['.divehub-backup']}
                onSelect={(files) => { setFile(files?.[0] ?? null); setMissingSource(false); }}
              >
                <Button variant="secondary" icon="import">{t('setup.chooseArchive')}</Button>
              </FileTrigger>
              <Muted>{file ? file.name : t('setup.noArchiveChosen')}</Muted>
            </div>
          </div>
        ) : (
          <Select
            label={t('setup.placedArchive')}
            value={chosen}
            onChange={(value) => { setPlacedChoice(value); setMissingSource(false); }}
            options={placed.map((archive) => ({ id: archive.name, label: archive.name }))}
          />
        )}
        {placed.length > 0 && (
          <RadioGroup
            label={t('setup.source')}
            value={effectiveSource}
            onChange={(value) => setSource(value as Source)}
            options={[
              { value: 'upload', label: t('setup.sourceUpload') },
              { value: 'placed', label: t('setup.sourcePlaced') },
            ]}
          />
        )}
        {missingSource && <Notice tone="danger">{t('setup.archiveMissing')}</Notice>}
        {restore.error && <Notice tone="danger">{errorText(restore.error)}</Notice>}
        <div className="form-actions">
          <Button type="submit" variant="primary" isPending={restore.isPending}>{t('setup.restoreSubmit')}</Button>
        </div>
      </Form>
    </>
  );
}
