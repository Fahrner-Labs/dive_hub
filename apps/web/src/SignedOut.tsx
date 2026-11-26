import { useState } from 'react';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Trans, useTranslation } from 'react-i18next';
import { api, authClient, emailConfigQuery, invitationLinkQuery, passwordResetQuery, registrationOpenQuery, unwrap } from './api.ts';
import { resetDeletion } from './lib/deletion.ts';
import { useErrorText } from './lib/display.ts';
import { formValues } from './lib/form.ts';
import { usePageTitle } from './lib/page.ts';
import { SetupRestore } from './SetupRestore.tsx';
import { Button, Checkbox, Form, Muted, Notice, Panel, PasswordField, RadioGroup, TextField } from './ui/index.ts';

const MIN_PASSWORD_LENGTH = 15;
const MAX_PASSWORD_LENGTH = 128;

/**
 * After the User changes, nothing cached for the previous one may show (nor the Dive they just deleted). resetQueries drops all
 * cached data and refetches what's on screen (so `me` picks up the new session); clear() would
 * detach the mounted queries and leave the page as it was.
 */
export function useUserChanged() {
  const queryClient = useQueryClient();
  return async () => {
    history.replaceState(null, '', location.pathname); // drop tokens from the address bar
    location.hash = '/';
    resetDeletion();
    await queryClient.resetQueries();
  };
}

export function NewPasswordField({ label }: { label: string }) {
  const { t } = useTranslation();
  return (
    <PasswordField
      label={label} name="password" autoComplete="new-password" isRequired
      minLength={MIN_PASSWORD_LENGTH} maxLength={MAX_PASSWORD_LENGTH}
      description={t('form.passwordHint', { count: MIN_PASSWORD_LENGTH })}
    />
  );
}

export function SignIn() {
  const { t } = useTranslation();
  usePageTitle(t('signIn.title'));
  const errorText = useErrorText();
  const signedIn = useUserChanged();
  const emailConfig = useQuery(emailConfigQuery());
  // Sign-up is offered only where the instance reports registration open (hosted plan §6.2); invite-only renders as before.
  const registration = useQuery(registrationOpenQuery());
  const [forgot, setForgot] = useState(false);
  const signIn = useMutation({
    mutationFn: async ({ email, password }: Record<string, string>) => {
      const { error } = await authClient.signIn.email({ email: email!, password: password! });
      if (!error) return;
      if (error.status === 429) throw new Error(t('signIn.tooManyAttempts'));
      if (error.code === 'BANNED_USER') throw new Error(t('signIn.disabled'));
      throw new Error(t('signIn.wrongCredentials'));
    },
    onSuccess: signedIn,
  });
  // With mail configured the User requests the reset link themselves (hosted plan §6.1); otherwise an admin issues it.
  const requestReset = useMutation({
    mutationFn: async ({ email }: Record<string, string>) =>
      unwrap(await api.POST('/api/password-resets/request', { body: { email: email! } })),
  });

  if (forgot) {
    return (
      <Panel title={t('signIn.forgotTitle')} narrow level={1}>
        <p className="muted">{t('signIn.forgotAbout')}</p>
        {requestReset.isSuccess ? (
          <Notice tone="info">{t('signIn.resetRequested')}</Notice>
        ) : (
          <Form className="form" onSubmit={(e) => requestReset.mutate(formValues(e))}>
            <TextField label={t('form.email')} name="email" type="email" autoComplete="username" spellCheck="false" isRequired />
            {requestReset.error && <Notice tone="danger">{errorText(requestReset.error)}</Notice>}
            <div className="form-actions">
              <Button type="submit" variant="primary" isPending={requestReset.isPending}>{t('signIn.sendResetLink')}</Button>
              <Button type="button" variant="quiet" onPress={() => setForgot(false)}>{t('signIn.backToSignIn')}</Button>
            </div>
          </Form>
        )}
      </Panel>
    );
  }

  return (
    <Panel title={t('signIn.title')} narrow level={1}>
      <p className="muted">{t('signIn.about')}</p>
      <Form className="form" onSubmit={(e) => signIn.mutate(formValues(e))}>
        <TextField label={t('form.email')} name="email" type="email" autoComplete="username" spellCheck="false" isRequired />
        <PasswordField label={t('form.password')} name="password" autoComplete="current-password" isRequired />
        {signIn.error && <Notice tone="danger">{signIn.error.message}</Notice>}
        <div className="form-actions"><Button type="submit" variant="primary" isPending={signIn.isPending}>{t('signIn.submit')}</Button></div>
      </Form>
      {emailConfig.data?.configured && (
        <div className="form-actions">
          <Button type="button" variant="quiet" onPress={() => setForgot(true)}>{t('signIn.forgotPassword')}</Button>
        </div>
      )}
      {registration.data?.open
        ? <p className="muted"><a href="#/signup">{t('signIn.createAccount')}</a></p>
        : <Muted>{t('signIn.noAccount')}</Muted>}
    </Panel>
  );
}

/**
 * Self-serve signup (hosted plan §6.2). The server records the consent versions in force and mails a verification link;
 * no session exists until that link is opened, so the form ends in a "check your e-mail" state with a resend.
 */
export function SignUp() {
  const { t } = useTranslation();
  usePageTitle(t('signup.title'));
  const registration = useQuery(registrationOpenQuery());
  const [accepted, setAccepted] = useState(false);
  const [consentMissing, setConsentMissing] = useState(false);
  const [sentTo, setSentTo] = useState<string | null>(null);
  const signUp = useMutation({
    mutationFn: async ({ name, email, password }: Record<string, string>) => {
      const { error } = await authClient.signUp.email({
        name: name!, email: email!, password: password!, callbackURL: '/#/verify-email',
        consent: { terms: true, privacy: true },
      } as Parameters<typeof authClient.signUp.email>[0]);
      if (!error) return email!;
      if (error.status === 429) throw new Error(t('signIn.tooManyAttempts'));
      throw new Error(t('signup.failed'));
    },
    onSuccess: (email) => setSentTo(email),
  });

  // A closed instance offers no sign-up at all, even when its old address is opened by hand.
  if (registration.data && !registration.data.open) return <SignIn />;

  if (sentTo) {
    return (
      <Panel title={t('signup.sentTitle')} narrow level={1}>
        <Notice tone="success">
          <Trans i18nKey="signup.sent" values={{ email: sentTo }} components={{ strong: <strong /> }} />
        </Notice>
        <p className="muted">{t('signup.sentHint')}</p>
        <ResendVerification email={sentTo} />
        <div className="form-actions">
          <Button type="button" variant="quiet" onPress={() => { location.hash = '/'; }}>{t('verifyEmail.backToSignIn')}</Button>
        </div>
      </Panel>
    );
  }

  return (
    <Panel title={t('signup.title')} narrow level={1}>
      <p className="muted">{t('signup.about')}</p>
      <Form className="form" onSubmit={(event) => {
        const values = formValues(event);
        if (!accepted) {
          setConsentMissing(true);
          return;
        }
        signUp.mutate(values);
      }}>
        <TextField label={t('form.name')} name="name" autoComplete="name" maxLength={100} isRequired />
        <TextField label={t('form.email')} name="email" type="email" autoComplete="username" spellCheck="false" isRequired />
        <NewPasswordField label={t('form.password')} />
        <Checkbox
          isSelected={accepted}
          onChange={(on) => { setAccepted(on); if (on) setConsentMissing(false); }}
          isInvalid={consentMissing}
          aria-describedby={consentMissing ? 'signup-consent-error' : 'signup-consent-hint'}
        >
          <Trans
            i18nKey="signup.consent"
            components={{
              terms: <a href="#/terms" onClick={(event) => event.stopPropagation()} />,
              privacy: <a href="#/privacy" onClick={(event) => event.stopPropagation()} />,
            }}
          />
        </Checkbox>
        {consentMissing
          ? <p className="field-error" id="signup-consent-error" role="alert">{t('signup.consentRequired')}</p>
          : <p className="muted" id="signup-consent-hint">{t('signup.consentHint')}</p>}
        {signUp.error && <Notice tone="danger">{signUp.error.message}</Notice>}
        <div className="form-actions">
          <Button type="submit" variant="primary" isPending={signUp.isPending}>{t('signup.submit')}</Button>
          <Button type="button" variant="quiet" onPress={() => { location.hash = '/'; }}>{t('verifyEmail.backToSignIn')}</Button>
        </div>
      </Form>
    </Panel>
  );
}

/**
 * The landing the verification link redirects to (GET /api/auth/verify-email). A successful verification signs the User
 * in and App sends them on from here; without a session the token failed, so the page offers a new link.
 */
export function VerifyEmail() {
  const { t } = useTranslation();
  usePageTitle(t('verifyEmail.title'));
  return (
    <Panel title={t('verifyEmail.title')} narrow level={1}>
      <Notice tone="danger">{t('verifyEmail.failed')}</Notice>
      <p className="muted">{t('verifyEmail.about')}</p>
      <ResendVerification />
      <div className="form-actions">
        <Button type="button" variant="quiet" onPress={() => { location.hash = '/'; }}>{t('verifyEmail.backToSignIn')}</Button>
      </div>
    </Panel>
  );
}

/** Asks the server for another verification mail, for the address that signed up or one typed here. */
function ResendVerification({ email: known }: { email?: string }) {
  const { t } = useTranslation();
  const [sent, setSent] = useState(false);
  const resend = useMutation({
    mutationFn: async (email: string) => {
      const { error } = await authClient.sendVerificationEmail({ email, callbackURL: '/#/verify-email' });
      if (error) throw new Error(t('verifyEmail.resendFailed'));
    },
    onSuccess: () => setSent(true),
  });
  if (sent) return <Notice tone="info">{t('verifyEmail.resent')}</Notice>;
  if (known) {
    return (
      <div className="form-actions">
        <Button type="button" variant="secondary" isPending={resend.isPending} onPress={() => resend.mutate(known)}>
          {t('verifyEmail.resend')}
        </Button>
        {resend.error && <Notice tone="danger">{resend.error.message}</Notice>}
      </div>
    );
  }
  return (
    <Form className="form" onSubmit={(event) => resend.mutate(formValues(event).email!)}>
      <TextField label={t('form.email')} name="email" type="email" autoComplete="username" spellCheck="false" isRequired />
      {resend.error && <Notice tone="danger">{resend.error.message}</Notice>}
      <div className="form-actions">
        <Button type="submit" variant="secondary" isPending={resend.isPending}>{t('verifyEmail.resend')}</Button>
      </div>
    </Form>
  );
}

/** First start: create the first admin, or restore the instance from a backup (plan FAH-175). */
export function Setup() {
  const { t } = useTranslation();
  usePageTitle(t('setup.title'));
  const errorText = useErrorText();
  const signedIn = useUserChanged();
  const [path, setPath] = useState<'fresh' | 'restore'>('fresh');
  const setup = useMutation({
    mutationFn: async ({ token, name, email, password }: Record<string, string>) =>
      unwrap(await api.POST('/api/setup', { body: { token: token!.trim(), name: name!, email: email!, password: password! } })),
    onSuccess: signedIn,
  });

  return (
    <Panel title={t('setup.title')} narrow level={1}>
      <RadioGroup
        label={t('setup.path')}
        value={path}
        onChange={(value) => setPath(value as 'fresh' | 'restore')}
        options={[
          { value: 'fresh', label: t('setup.pathFresh') },
          { value: 'restore', label: t('setup.pathRestore') },
        ]}
      />
      {path === 'restore' ? <SetupRestore /> : (
        <>
          <p><Trans i18nKey="setup.intro" components={{ code: <code /> }} /></p>
          <Form className="form" onSubmit={(e) => setup.mutate(formValues(e))}>
            <TextField label={t('setup.token')} name="token" isRequired autoComplete="off" autoCorrect="off" spellCheck="false" />
            <TextField label={t('form.name')} name="name" isRequired maxLength={100} autoComplete="name" />
            <TextField label={t('form.email')} name="email" type="email" isRequired autoComplete="username" spellCheck="false" />
            <NewPasswordField label={t('form.password')} />
            {setup.error && <Notice tone="danger">{errorText(setup.error)}</Notice>}
            <div className="form-actions"><Button type="submit" variant="primary" isPending={setup.isPending}>{t('setup.submit')}</Button></div>
          </Form>
        </>
      )}
    </Panel>
  );
}

export function AcceptInvitation({ token }: { token: string }) {
  const { t } = useTranslation();
  usePageTitle(t('invitation.title'));
  const errorText = useErrorText();
  const signedIn = useUserChanged();
  const invitation = useQuery(invitationLinkQuery(token));
  const accept = useMutation({
    mutationFn: async ({ name, password }: Record<string, string>) =>
      unwrap(await api.POST('/api/invitations/accept', { body: { token, name: name!, password: password! } })),
    onSuccess: signedIn,
  });

  if (invitation.isPending) return <Muted>{t('invitation.checking')}</Muted>;
  if (invitation.error) {
    return (
      <Panel title={t('invitation.title')} narrow level={1}>
        <Notice tone="danger">{errorText(invitation.error)}</Notice>
        <Muted>{t('invitation.askForNewLink')}</Muted>
      </Panel>
    );
  }
  return (
    <Panel title={t('invitation.title')} narrow level={1}>
      <p className="muted">{t('signIn.about')}</p>
      <p><Trans i18nKey="invitation.intro" values={{ email: invitation.data.email }} components={{ strong: <strong /> }} /></p>
      <Form className="form" onSubmit={(e) => accept.mutate(formValues(e))}>
        {/* Lets password managers save the e-mail with the new password. */}
        <input type="email" name="email" value={invitation.data.email} autoComplete="username" readOnly hidden />
        <TextField label={t('form.name')} name="name" isRequired maxLength={100} autoComplete="name" />
        <NewPasswordField label={t('form.password')} />
        {accept.error && <Notice tone="danger">{errorText(accept.error)}</Notice>}
        <div className="form-actions"><Button type="submit" variant="primary" isPending={accept.isPending}>{t('invitation.submit')}</Button></div>
      </Form>
    </Panel>
  );
}

/** Opened from a password reset link an admin passed on (ADR 0013). */
export function ResetPassword({ token }: { token: string }) {
  const { t } = useTranslation();
  usePageTitle(t('reset.title'));
  const errorText = useErrorText();
  const signedIn = useUserChanged();
  const link = useQuery(passwordResetQuery(token));
  const reset = useMutation({
    mutationFn: async ({ password }: Record<string, string>) =>
      unwrap(await api.POST('/api/password-resets/complete', { body: { token, password: password! } })),
    onSuccess: signedIn,
  });

  if (link.isPending) return <Muted>{t('reset.checking')}</Muted>;
  if (link.error) {
    return (
      <Panel title={t('reset.title')} narrow level={1}>
        <Notice tone="danger">{errorText(link.error)}</Notice>
        <Muted>{t('invitation.askForNewLink')}</Muted>
      </Panel>
    );
  }
  return (
    <Panel title={t('reset.title')} narrow level={1}>
      <p><Trans i18nKey="reset.intro" values={{ email: link.data.email }} components={{ strong: <strong /> }} /></p>
      <Form className="form" onSubmit={(e) => reset.mutate(formValues(e))}>
        <input type="email" name="email" value={link.data.email} autoComplete="username" readOnly hidden />
        <NewPasswordField label={t('reset.newPassword')} />
        {reset.error && <Notice tone="danger">{errorText(reset.error)}</Notice>}
        <div className="form-actions"><Button type="submit" variant="primary" isPending={reset.isPending}>{t('reset.submit')}</Button></div>
      </Form>
    </Panel>
  );
}

/** Signs out and shows the sign-in page. */
export function useSignOut() {
  const userChanged = useUserChanged();
  return async () => {
    await authClient.signOut();
    await userChanged(); // `me` refetches as nobody, which shows the sign-in page
  };
}
