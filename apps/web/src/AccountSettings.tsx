import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { formValues } from './lib/form.ts';
import { NewPasswordField, useUserChanged } from './SignedOut.tsx';
import { ApiError, api, authClient, keys, meQuery, request, sessionsQuery, twoFactorEnabledQuery, unwrap, type SessionView, type TwoFactorSetup } from './api.ts';
import { LANGUAGES } from './i18n/languages.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { announce } from './lib/announce.ts';
import { refocusAfterRemoval } from './lib/focus.ts';
import { ReportProblem } from './ReportProblem.tsx';
import { Button, ConfirmButton, CopyField, Form, Notice, Panel, PasswordField, QrCode, RadioGroup, Switch, Table, TextField } from './ui/index.ts';

const AUTO = 'auto';

/** Settings' Display section: the language and the units, each saved as it is chosen. The section's name is the h1. */
export function DisplaySettings() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const me = useQuery(meQuery());
  const save = useMutation({
    mutationFn: async (body: { language?: string | null; units?: 'metric' | 'imperial' | null }) =>
      unwrap(await api.PATCH('/api/me/preferences', { body })),
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: keys.me, exact: true });
      announce(t('account.saved'));
    },
  });
  const prefs = me.data?.preferences;
  if (!prefs) return null;

  return (
    <Panel>
      <div className="form-inline">
        <RadioGroup
          label={t('account.language')}
          value={prefs.language ?? AUTO}
          onChange={(value) => save.mutate({ language: value === AUTO ? null : value })}
          options={[
            { value: AUTO, label: t('account.languageAuto') },
            ...Object.entries(LANGUAGES).map(([value, name]) => ({ value, label: <span lang={value}>{name}</span> })),
          ]}
        />
        <RadioGroup
          label={t('account.units')}
          value={prefs.units ?? AUTO}
          onChange={(value) => save.mutate({ units: value === AUTO ? null : (value as 'metric' | 'imperial') })}
          options={[
            { value: AUTO, label: t('account.unitsAuto') },
            { value: 'metric', label: t('account.metric') },
            { value: 'imperial', label: t('account.imperial') },
          ]}
        />
      </div>
      {save.error && <Notice tone="danger">{errorText(save.error)}</Notice>}
    </Panel>
  );
}

/** Settings' Sign-in and security section: the password, the second factor, and where the User is signed in. */
export function SecuritySettings() {
  return (
    <>
      <ChangePassword />
      <TwoFactor />
      <Sessions />
    </>
  );
}

/**
 * Settings' Report a problem section (FAH-292, ADR 0080): errors stay on this device until the User sends them to the
 * instance they are signed in to. The section's name is the h1.
 */
export function ReportProblemSettings() {
  const { t } = useTranslation();
  return (
    <Panel>
      <p>{t('clientDiagnostics.report.lead')}</p>
      <ReportProblem />
      <p className="muted">{t('clientDiagnostics.report.privacy')}</p>
    </Panel>
  );
}

function ChangePassword() {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const [done, setDone] = useState(false);
  const change = useMutation({
    mutationFn: async ({ currentPassword, password }: Record<string, string>) => {
      const { error } = await authClient.changePassword({
        currentPassword: currentPassword!, newPassword: password!, revokeOtherSessions: true,
      });
      if (error) throw new Error(error.code === 'INVALID_PASSWORD' ? t('account.wrongCurrent') : t('errors.unknown'));
    },
    onSuccess: () => {
      setDone(true);
      void queryClient.invalidateQueries({ queryKey: keys.sessions });
    },
  });

  return (
    <Panel title={t('account.changePassword')}>
      <Form
        className="form narrow-form"
        onSubmit={(e) => {
          const values = formValues(e);
          const form = e.currentTarget;
          setDone(false);
          change.mutate(values, { onSuccess: () => form.reset() });
        }}
      >
        <PasswordField label={t('account.currentPassword')} name="currentPassword" autoComplete="current-password" isRequired />
        <NewPasswordField label={t('account.newPassword')} />
        {change.error && <Notice tone="danger">{change.error.message}</Notice>}
        {done && <Notice tone="success">{t('account.changed')}</Notice>}
        <div className="form-actions"><Button type="submit" variant="primary" isPending={change.isPending}>{t('account.submitChange')}</Button></div>
      </Form>
    </Panel>
  );
}

type TwoFactorStep = 'idle' | 'password' | 'verify' | 'codes';

const rateLimited = (error: unknown) => error instanceof ApiError && error.status === 429;

/**
 * The second factor (FAH-54): enrol an authenticator app, show its backup codes once, or turn it off with the
 * password and a current code. The TOTP URI is drawn as a QR code and offered as text for apps and screen readers.
 */
function TwoFactor() {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const status = useQuery(twoFactorEnabledQuery());
  const [step, setStep] = useState<TwoFactorStep>('idle');
  const [setup, setSetup] = useState<TwoFactorSetup>();
  const [useBackupCode, setUseBackupCode] = useState(false);
  const done = () => { setStep('idle'); setSetup(undefined); };

  const enable = useMutation({
    mutationFn: (password: string) => request(api.POST('/api/auth/two-factor/enable', { body: { password, method: 'totp' } })),
    onSuccess: (data) => { setSetup(data); setStep('verify'); },
  });
  const verify = useMutation({
    mutationFn: (code: string) => request(api.POST('/api/auth/two-factor/verify-totp', { body: { code } })),
    onSuccess: async () => {
      setStep('codes');
      await queryClient.invalidateQueries({ queryKey: keys.twoFactor });
      announce(t('account.twoFactor.on'));
    },
  });
  const disable = useMutation({
    mutationFn: ({ password, code }: Record<string, string>) =>
      request(api.POST('/api/me/two-factor/disable', { body: { password: password!, code: code! } })),
    onSuccess: async () => {
      setUseBackupCode(false);
      await queryClient.invalidateQueries({ queryKey: keys.twoFactor });
      announce(t('account.twoFactor.off'));
    },
  });

  if (status.isPending) return null;
  const view = step === 'codes' && setup?.backupCodes
    ? <TwoFactorCodes codes={setup.backupCodes} onDone={done} />
    : status.data
      ? (
        <TwoFactorEnabled
          error={disable.error} isPending={disable.isPending} useBackupCode={useBackupCode}
          onUseBackupCode={setUseBackupCode} onSubmit={(values) => disable.mutate(values)}
        />
      )
      : step === 'password'
        ? (
          <TwoFactorPassword
            error={enable.error} isPending={enable.isPending} onCancel={done}
            onSubmit={(password) => enable.mutate(password)}
          />
        )
        : step === 'verify' && setup?.totpURI
          ? (
            <TwoFactorVerify
              uri={setup.totpURI} error={verify.error} isPending={verify.isPending} onCancel={done}
              onSubmit={(code) => verify.mutate(code)}
            />
          )
          : <TwoFactorIntro onStart={() => setStep('password')} />;

  return <Panel title={t('account.twoFactor.title')}>{view}</Panel>;
}

function TwoFactorIntro({ onStart }: { onStart: () => void }) {
  const { t } = useTranslation();
  return (
    <>
      <p>{t('account.twoFactor.intro')}</p>
      <div className="form-actions"><Button variant="primary" onPress={onStart}>{t('account.twoFactor.setUp')}</Button></div>
    </>
  );
}

/** The password that confirms starting the enrolment; Better Auth's refusal is INVALID_PASSWORD, which isn't ours. */
function TwoFactorPassword({ error, isPending, onSubmit, onCancel }: {
  error: unknown; isPending: boolean; onSubmit: (password: string) => void; onCancel: () => void;
}) {
  const { t } = useTranslation();
  const message = !error ? null : rateLimited(error) ? t('errors.too_many_attempts') : t('account.wrongCurrent');
  return (
    <Form className="form narrow-form" onSubmit={(e) => onSubmit(formValues(e).password!)}>
      <p className="muted">{t('account.twoFactor.passwordIntro')}</p>
      <PasswordField label={t('account.currentPassword')} name="password" autoComplete="current-password" isRequired />
      {message && <Notice tone="danger">{message}</Notice>}
      <div className="form-actions">
        <Button type="submit" variant="primary" isPending={isPending}>{t('account.twoFactor.continue')}</Button>
        <Button onPress={onCancel}>{t('common.cancel')}</Button>
      </div>
    </Form>
  );
}

/** The QR code and link, and the first authenticator code that switches the factor on. */
function TwoFactorVerify({ uri, error, isPending, onSubmit, onCancel }: {
  uri: string; error: unknown; isPending: boolean; onSubmit: (code: string) => void; onCancel: () => void;
}) {
  const { t } = useTranslation();
  const message = !error ? null : rateLimited(error) ? t('errors.too_many_attempts') : t('errors.two_factor_code_invalid');
  return (
    <>
      <p>{t('account.twoFactor.scan')}</p>
      <div className="two-factor-setup">
        <QrCode text={uri} label={t('account.twoFactor.qrLabel')} />
        <CopyField value={uri} label={t('account.twoFactor.setupLink')} />
      </div>
      <Form className="form narrow-form" onSubmit={(e) => onSubmit(formValues(e).code!)}>
        <TextField
          label={t('account.twoFactor.code')} name="code" autoComplete="one-time-code" spellCheck="false"
          autoCapitalize="none" isRequired description={t('account.twoFactor.codeHint')}
        />
        {message && <Notice tone="danger">{message}</Notice>}
        <div className="form-actions">
          <Button type="submit" variant="primary" isPending={isPending}>{t('account.twoFactor.verify')}</Button>
          <Button onPress={onCancel}>{t('common.cancel')}</Button>
        </div>
      </Form>
    </>
  );
}

/** On already: turning it off asks the password and a current authenticator or backup code. */
function TwoFactorEnabled({ error, isPending, useBackupCode, onUseBackupCode, onSubmit }: {
  error: unknown; isPending: boolean; useBackupCode: boolean; onUseBackupCode: (selected: boolean) => void;
  onSubmit: (values: Record<string, string>) => void;
}) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const message = !error ? null : rateLimited(error) ? t('errors.too_many_attempts') : errorText(error);
  return (
    <>
      <p>{t('account.twoFactor.on')}</p>
      <Form className="form narrow-form" onSubmit={(e) => onSubmit(formValues(e))}>
        <p className="muted">{t('account.twoFactor.disableIntro')}</p>
        <PasswordField label={t('account.currentPassword')} name="password" autoComplete="current-password" isRequired />
        <Switch isSelected={useBackupCode} onChange={onUseBackupCode}>{t('account.twoFactor.useBackupCode')}</Switch>
        <TextField
          label={useBackupCode ? t('account.twoFactor.backupCode') : t('account.twoFactor.code')} name="code"
          autoComplete="one-time-code" spellCheck="false" autoCapitalize="none" isRequired
        />
        {message && <Notice tone="danger">{message}</Notice>}
        <div className="form-actions"><Button type="submit" variant="danger" isPending={isPending}>{t('account.twoFactor.disable')}</Button></div>
      </Form>
    </>
  );
}

/** The backup codes, shown once after the first code is verified. */
function TwoFactorCodes({ codes, onDone }: { codes: string[]; onDone: () => void }) {
  const { t } = useTranslation();
  return (
    <>
      <Notice tone="success">{t('account.twoFactor.on')}</Notice>
      <p>{t('account.twoFactor.backupCodesIntro')}</p>
      <h3 className="subheading">{t('account.twoFactor.backupCodes')}</h3>
      <BackupCodes codes={codes} />
      <div className="form-actions"><Button variant="primary" onPress={onDone}>{t('account.twoFactor.done')}</Button></div>
    </>
  );
}

function BackupCodes({ codes }: { codes: string[] }) {
  const { t } = useTranslation();
  return (
    <>
      <ul className="backup-codes" aria-label={t('account.twoFactor.backupCodes')}>
        {codes.map((code) => <li key={code}>{code}</li>)}
      </ul>
      <div className="form-actions">
        <Button icon="copy" onPress={async () => { await navigator.clipboard.writeText(codes.join('\n')); announce(t('common.copied')); }}>
          {t('account.twoFactor.copyCodes')}
        </Button>
      </div>
    </>
  );
}

/** "Firefox on Windows" from a user agent string; good enough to recognise one's own devices. */
function useDescribeAgent() {
  const { t } = useTranslation();
  return (userAgent: string | null): string => {
    if (!userAgent) return t('account.unknownDevice');
    const browser = [['Edg/', 'Edge'], ['Firefox/', 'Firefox'], ['Chrome/', 'Chrome'], ['Safari/', 'Safari']]
      .find(([marker]) => userAgent.includes(marker!))?.[1] ?? t('account.browser');
    const os = [['Windows', 'Windows'], ['Android', 'Android'], ['iPhone', 'iOS'], ['iPad', 'iPadOS'], ['Mac OS X', 'macOS'], ['Linux', 'Linux']]
      .find(([marker]) => userAgent.includes(marker!))?.[1];
    return os ? t('account.agent', { browser, os }) : browser;
  };
}

function Sessions() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const display = useDisplay();
  const describe = useDescribeAgent();
  const queryClient = useQueryClient();
  const userChanged = useUserChanged();
  const sessions = useQuery(sessionsQuery());
  const list = useRef<HTMLDivElement>(null);
  const end = useMutation({
    mutationFn: async (s: SessionView) => {
      unwrap(await api.DELETE('/api/me/sessions/{id}', { params: { path: { id: s.id } } }));
      return s;
    },
    onSuccess: async (s) => (s.current ? userChanged() : queryClient.invalidateQueries({ queryKey: keys.sessions })),
  });
  const endOthers = useMutation({
    mutationFn: async () => unwrap(await api.DELETE('/api/me/sessions')),
    onSettled: () => queryClient.invalidateQueries({ queryKey: keys.sessions }),
  });
  const error = (end.variables?.current ? null : end.error) ?? sessions.error; // the dialog shows its own
  const label = (s: SessionView) => t('common.forItem', { action: t('account.signOutSession'), item: `${describe(s.userAgent)}, ${display.dateTime(s.createdAt)}` });

  return (
    <Panel
      title={t('account.sessions')}
      actions={sessions.data && sessions.data.length > 1 && (
        <ConfirmButton
          tone="primary" title={t('account.signOutOthersTitle')} body={t('account.signOutOthersBody')} confirmLabel={t('account.signOutOthers')}
          onConfirm={() => endOthers.mutateAsync()}
        >
          {t('account.signOutOthers')}
        </ConfirmButton>
      )}
    >
      {error && <Notice tone="danger">{errorText(error)}</Notice>}
      {sessions.data && (
        <div ref={list}>
        <Table cards
          label={t('account.sessions')}
          head={[t('account.device'), t('account.ipAddress'), t('account.signedIn'), t('account.lastActive'), { label: t('common.actions'), hidden: true }]}
        >
          {sessions.data.map((s, index) => (
            <tr key={s.id}>
              <td><span>{describe(s.userAgent)}{s.current && <> (<strong>{t('account.thisDevice')}</strong>)</>}</span></td>
              <td>{s.ipAddress ?? t('common.none')}</td>
              <td className="date">{display.dateTime(s.createdAt)}</td>
              <td className="date">{s.lastActiveAt ? display.dateTime(s.lastActiveAt) : t('common.none')}</td>
              <td>
                {s.current ? (
                  <ConfirmButton
                    variant="quiet" aria-label={label(s)} tone="primary"
                    title={t('account.signOutThisTitle')} body={t('account.signOutThisBody')} confirmLabel={t('account.signOutSession')}
                    onConfirm={() => end.mutateAsync(s)}
                  >
                    {t('account.signOutSession')}
                  </ConfirmButton>
                ) : (
                  <Button
                    variant="quiet" aria-label={label(s)}
                    isPending={end.isPending && end.variables?.id === s.id} isDisabled={end.isPending}
                    onPress={() => end.mutate(s, {
                      onSuccess: () => refocusAfterRemoval(list.current?.querySelector('tbody') ?? null, index, sessions.data.length),
                    })}
                  >
                    {t('account.signOutSession')}
                  </Button>
                )}
              </td>
            </tr>
          ))}
        </Table>
        </div>
      )}
    </Panel>
  );
}

