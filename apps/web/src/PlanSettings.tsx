import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { billingCheckout, billingPortal, type BillingView } from './api.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { Button, Muted, Notice, Panel } from './ui/index.ts';

/**
 * Settings' Plan section (hosted-DiveHub plan §4.4, ADR 0092): the User's plan and status, with Stripe's checkout to
 * upgrade and its Customer Portal to manage or cancel. Dive Hub holds no payment data: both are Stripe-hosted pages the
 * server links to. The section is shown only where the server sells the plan (ADR 0087).
 */
export function PlanSettings({ billing }: { billing: BillingView }) {
  const { t } = useTranslation();
  const display = useDisplay();
  const errorText = useErrorText();
  const [busy, setBusy] = useState<'checkout' | 'portal' | null>(null);
  const [error, setError] = useState<unknown>(null);
  const open = async (kind: 'checkout' | 'portal') => {
    setBusy(kind);
    setError(null);
    try {
      const { url } = kind === 'checkout' ? await billingCheckout() : await billingPortal();
      window.location.assign(url);
    } catch (failure) {
      setError(failure);
      setBusy(null);
    }
  };
  const periodEnd = billing.currentPeriodEnd ? display.date(billing.currentPeriodEnd) : null;
  const status = billing.status === 'none' ? t('plan.status.none')
    : billing.status === 'active' ? t('plan.status.active')
    : billing.status === 'past_due' ? t('plan.status.pastDue')
    : periodEnd ? t('plan.status.canceledUntil', { date: periodEnd }) : t('plan.status.canceled');
  return (
    <Panel title={t('plan.current')}>
      <p className="plan-state">
        <strong>{billing.plan === 'pro' ? t('plan.pro') : t('plan.free')}</strong>
        {' — '}
        {status}
      </p>
      {billing.plan === 'pro' && periodEnd && billing.status !== 'canceled' && (
        <Muted>{t('plan.renews', { date: periodEnd })}</Muted>
      )}
      <p>{billing.status === 'none' ? t('plan.upgradeHint') : t('plan.manageHint')}</p>
      <div className="form-actions">
        {billing.status === 'none'
          ? <Button variant="primary" isPending={busy === 'checkout'} onPress={() => open('checkout')}>{t('plan.upgrade')}</Button>
          : <Button isPending={busy === 'portal'} onPress={() => open('portal')}>{t('plan.manage')}</Button>}
      </div>
      {error ? <Notice tone="danger">{errorText(error)}</Notice> : null}
      <Muted>{t('plan.stripe')}</Muted>
    </Panel>
  );
}
