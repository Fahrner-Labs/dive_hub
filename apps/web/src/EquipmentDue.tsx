// The logbook's Equipment notice (ADR 0034 D9): a summary of what is due, due soon or overdue, dismissible on this
// client by a digest of the current due-schedule ids. A new schedule becoming due, or a service settling one, changes the
// digest and the notice comes back. Due is derived, so nothing is stored on the server.
import { useQuery } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { equipmentDueQuery } from './api.ts';
import { dismissDue, dismissedDueDigest, dueCounts, dueDigest } from './lib/equipment.ts';
import { focusHeading } from './lib/focus.ts';
import { Button, LinkButton, Notice } from './ui/index.ts';

export function EquipmentDueNotice() {
  const { t } = useTranslation();
  const due = useQuery(equipmentDueQuery());
  const entries = due.data ?? [];
  const digest = dueDigest(entries);
  const [dismissed, setDismissed] = useState(() => dismissedDueDigest() === digest);
  // A different due set is a new notice, even when one was dismissed before.
  useEffect(() => setDismissed(dismissedDueDigest() === digest), [digest]);
  if (entries.length === 0 || dismissed) return null;
  const counts = dueCounts(entries);
  const summary = [
    counts.overdue > 0 ? t('equipment.noticeOverdue', { count: counts.overdue }) : null,
    counts.due > 0 ? t('equipment.noticeDue', { count: counts.due }) : null,
    counts.dueSoon > 0 ? t('equipment.noticeDueSoon', { count: counts.dueSoon }) : null,
  ].filter(Boolean).join(' · ');
  return (
    <Notice tone="info">
      <p>{t('equipment.notice', { count: counts.total })}</p>
      <p>{summary}</p>
      <div className="form-actions">
        <LinkButton href="#/equipment">{t('equipment.open')}</LinkButton>
        <Button variant="quiet" onPress={() => { dismissDue(digest); setDismissed(true); focusHeading(); }}>{t('common.dismiss')}</Button>
      </div>
    </Notice>
  );
}
