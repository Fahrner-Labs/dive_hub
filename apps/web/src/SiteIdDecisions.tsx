import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api, keys, siteIdDecisionsQuery, unwrap, type SiteIdDecisionView } from './api.ts';
import { useErrorText } from './lib/display.ts';
import { Button, Muted, Notice, Panel } from './ui/index.ts';

type Told = { key: string; text: string };
type Choice = 'move' | 'keep';

/** Groups that keep their first-seen order: one panel per Source (FAH-121). */
function groupsOf(entries: SiteIdDecisionView[]) {
  const groups = new Map<string, SiteIdDecisionView[]>();
  for (const e of entries) groups.set(e.providerName, [...(groups.get(e.providerName) ?? []), e]);
  return [...groups.entries()];
}

/**
 * On the Review page: a site where an account holds a private ID and the site also got a public one (FAH-121). One offer
 * per site and account. "Move my dives" removes the private ID, so the account's Dives use the public one with the next
 * send; "Keep them" leaves them private and stops the offer. Nothing is sent by either choice.
 */
export function SiteIdDecisionSections() {
  const { t } = useTranslation();
  const decisions = useQuery(siteIdDecisionsQuery());
  const [told, setTold] = useState<Told[]>([]);
  const entries = decisions.data ?? [];
  const tell = (n: Told) => setTold((all) => [...all.filter((a) => a.key !== n.key), n]);
  return (
    <>
      {told.map((n) => (
        <Notice key={n.key} tone="info">
          <p>{n.text}</p>
          <Button variant="quiet" onPress={() => setTold((all) => all.filter((a) => a.key !== n.key))}>{t('common.close')}</Button>
        </Notice>
      ))}
      {groupsOf(entries).map(([providerName, group]) => (
        <Group key={providerName} providerName={providerName} entries={group} onTold={tell} />
      ))}
    </>
  );
}

function Group({ providerName, entries, onTold }: {
  providerName: string; entries: SiteIdDecisionView[]; onTold: (n: Told) => void;
}) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  // One answer at a time; the other buttons wait (page rules).
  const decide = useMutation({
    mutationFn: async ({ entry, choice }: { entry: SiteIdDecisionView; choice: Choice }) =>
      unwrap(await api.POST('/api/dive-site-decisions/{id}', {
        params: { path: { id: entry.siteId } },
        body: { source: entry.source, diverId: entry.diverId, privateExternalId: entry.privateExternalId, choice },
      })),
    onSuccess: async (_, { entry, choice }) => {
      onTold({
        key: `${entry.siteId}:${entry.source}:${entry.diverId}`,
        text: t(choice === 'move' ? 'siteIdDecisions.moved' : 'siteIdDecisions.kept', { site: entry.siteName, name: providerName }),
      });
      await queryClient.invalidateQueries({ queryKey: keys.siteIdDecisions });
      // A move changes the site's IDs and what its Dives send.
      await queryClient.invalidateQueries({ queryKey: keys.sites });
      await queryClient.invalidateQueries({ queryKey: keys.dives });
    },
  });
  const mine = (e: SiteIdDecisionView) => decide.isPending && decide.variables?.entry.siteId === e.siteId && decide.variables.entry.diverId === e.diverId;
  const other = (e: SiteIdDecisionView) => decide.isPending && !mine(e);
  const several = entries.length > 1;
  return (
    <Panel title={t('siteIdDecisions.title', { name: providerName, count: entries.length })} attention>
      <p className="muted">{t('siteIdDecisions.intro', { name: providerName })}</p>
      <ul className="decisions">
        {entries.map((e) => (
          <li key={`${e.siteId}:${e.source}:${e.diverId}`} className="decision">
            <p className="provider-conflict-question">
              {t('siteIdDecisions.question', { site: e.siteName, name: providerName })}
              {several && <> <Muted>{t('siteIdDecisions.diver', { diver: e.diverName })}</Muted></>}
            </p>
            <div className="form-actions">
              <Button
                icon="move" isPending={mine(e) && decide.variables?.choice === 'move'}
                isDisabled={other(e) || (mine(e) && decide.variables?.choice !== 'move')}
                aria-label={t('siteIdDecisions.moveFor', { site: e.siteName, name: providerName })}
                onPress={() => decide.mutate({ entry: e, choice: 'move' })}
              >
                {t('siteIdDecisions.move')}
              </Button>
              <Button
                variant="quiet" isPending={mine(e) && decide.variables?.choice === 'keep'}
                isDisabled={other(e) || (mine(e) && decide.variables?.choice !== 'keep')}
                aria-label={t('siteIdDecisions.keepFor', { site: e.siteName, name: providerName })}
                onPress={() => decide.mutate({ entry: e, choice: 'keep' })}
              >
                {t('siteIdDecisions.keep')}
              </Button>
            </div>
          </li>
        ))}
      </ul>
      {decide.error && <Notice tone="danger">{errorText(decide.error)}</Notice>}
    </Panel>
  );
}
