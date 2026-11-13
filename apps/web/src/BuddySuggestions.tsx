// Buddy suggestions on the Review page (ADR 0070): cards over the shared facts of a Dive another User logged with a
// Diver this User manages, and the accept dialog that links an overlapping Dive or creates a new one from those facts.
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useRef, useState, type RefObject } from 'react';
import { useTranslation } from 'react-i18next';
import {
  api, buddySuggestionQuery, buddySuggestionsQuery, keys, unwrap,
  type AcceptedBuddySuggestionView, type BuddySuggestionOverlapView, type BuddySuggestionPreviewView, type BuddySuggestionView,
} from './api.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { refocusAfterRemoval } from './lib/focus.ts';
import { useJoinNames } from './lib/providers.ts';
import { Candidates, CandidateRow, type Fact } from './ReviewRows.tsx';
import { Button, Dialog, Muted, Notice, Panel } from './ui/index.ts';

/**
 * What waits over a Dive another User logged with one of the User's Divers (ADR 0070): the shared facts only, with
 * Accept (the dialog below) and Decline. Nothing while nothing waits.
 */
export function BuddySuggestions() {
  const { t } = useTranslation();
  const suggestions = useQuery(buddySuggestionsQuery());
  const list = useRef<HTMLUListElement>(null);
  const all = suggestions.data ?? [];
  if (all.length === 0) return null;
  return (
    <Panel title={t('suggestions.title', { count: all.length })} attention>
      <p className="muted">{t('suggestions.intro')}</p>
      <ul className="decisions" ref={list}>
        {all.map((s, index) => <Suggestion key={s.id} suggestion={s} list={list} index={index} count={all.length} />)}
      </ul>
    </Panel>
  );
}

/**
 * One suggestion with its shared facts. The preview carries the Participants' names and the overlapping Dives; the
 * summary already shows the Dive while it loads. Accepting opens the dialog, declining removes the card.
 */
function Suggestion({ suggestion: s, list, index, count }: {
  suggestion: BuddySuggestionView; list: RefObject<HTMLUListElement | null>; index: number; count: number;
}) {
  const { t } = useTranslation();
  const display = useDisplay();
  const joinNames = useJoinNames();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const preview = useQuery(buddySuggestionQuery(s.id));
  const [accepting, setAccepting] = useState(false);

  const accept = useMutation({
    mutationFn: async (diveId?: string) => unwrap(await api.POST('/api/buddy-suggestions/{id}/accept', {
      params: { path: { id: s.id } }, body: diveId ? { diveId } : null,
    })),
    onSuccess: async ({ diveId }: AcceptedBuddySuggestionView) => {
      setAccepting(false);
      await Promise.all([
        queryClient.invalidateQueries({ queryKey: keys.buddySuggestions }),
        queryClient.invalidateQueries({ queryKey: keys.dives }),
      ]);
      // The contract opens the Dive that was linked or copied; it is in the User's logbook now.
      location.hash = `#/dives/${diveId}`;
    },
    // A suggestion accepted or declined elsewhere comes back as such; read the inbox again (the previews too).
    onError: () => queryClient.invalidateQueries({ queryKey: keys.buddySuggestions }),
  });
  const decline = useMutation({
    mutationFn: async () => unwrap(await api.POST('/api/buddy-suggestions/{id}/decline', { params: { path: { id: s.id } } })),
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: keys.buddySuggestions });
      refocusAfterRemoval(list.current, index, count);
    },
    onError: () => queryClient.invalidateQueries({ queryKey: keys.buddySuggestions }),
  });

  // The list's summary already has the Dive; the preview adds the average depth, centre and people.
  const d = preview.data?.dive ?? s.dive;
  const facts: Fact[] = [
    { key: 'time', text: display.diveTime(d.startsAt, d.utcOffsetSeconds) },
    { key: 'duration', text: display.duration(d.durationSeconds) },
    { key: 'depth', text: display.depth(d.maxDepthM) },
    ...(preview.data?.dive.avgDepthM != null
      ? [{ key: 'avgDepth', text: t('suggestions.avgDepth', { depth: display.depth(preview.data.dive.avgDepthM) }) }]
      : []),
    { key: 'site', text: d.site?.name },
    ...(preview.data && preview.data.dive.centres.length > 0
      ? [{ key: 'centres', text: t('suggestions.centre', { names: joinNames(preview.data.dive.centres.map((c) => c.name)) }) }]
      : []),
  ];
  // The Dive's own Diver and its Participants: the people the copy would list as Divers.
  const people = preview.data
    ? [...new Set([preview.data.dive.owner.name, ...preview.data.dive.participants.map((p) => p.name)])]
    : [];
  const item = display.diveTime(d.startsAt, d.utcOffsetSeconds);
  return (
    <li className="decision">
      {preview.data && <p className="muted">{t('suggestions.from', { owner: preview.data.dive.owner.name, diver: s.diver.name })}</p>}
      <Candidates>
        <CandidateRow title={<span className="candidate-title">{t('suggestions.suggestedDive')}</span>} facts={facts} marked={new Set()} />
      </Candidates>
      {people.length > 0 && <p>{t('suggestions.people', { names: joinNames(people) })}</p>}
      <div className="form-actions">
        <Button
          icon="add" isDisabled={!preview.data} aria-label={t('common.forItem', { action: t('suggestions.accept'), item })}
          onPress={() => setAccepting(true)}
        >
          {t('suggestions.accept')}
        </Button>
        <Button
          variant="quiet" isPending={decline.isPending} aria-label={t('common.forItem', { action: t('suggestions.decline'), item })}
          onPress={() => decline.mutate()}
        >
          {t('suggestions.decline')}
        </Button>
      </div>
      {preview.error && <Notice tone="danger">{errorText(preview.error)}</Notice>}
      {decline.error && <Notice tone="danger">{errorText(decline.error)}</Notice>}
      {accepting && preview.data && (
        <SuggestionAcceptDialog
          preview={preview.data} onClose={() => setAccepting(false)} onAccept={(diveId) => accept.mutate(diveId)}
          isPending={accept.isPending} pendingDiveId={accept.variables} error={accept.error}
        />
      )}
    </li>
  );
}

/**
 * Accepting a suggestion (ADR 0070): each of the invited Diver's overlapping Dives is offered to link, and creating a
 * new Dive from the shared facts is always possible. With no overlap it says that a new Dive is the only way; a failed
 * accept keeps the dialog open with its error.
 */
export function SuggestionAcceptDialog({ preview, onClose, onAccept, isPending = false, pendingDiveId, error }: {
  preview: BuddySuggestionPreviewView;
  onClose: () => void;
  onAccept: (diveId?: string) => void;
  isPending?: boolean;
  pendingDiveId?: string | undefined;
  error?: unknown;
}) {
  const { t } = useTranslation();
  const display = useDisplay();
  const errorText = useErrorText();
  const overlapFacts = (o: BuddySuggestionOverlapView): Fact[] => [
    { key: 'time', text: display.diveTime(o.startsAt, o.utcOffsetSeconds) },
    { key: 'duration', text: display.duration(o.durationSeconds) },
    { key: 'depth', text: display.depth(o.maxDepthM) },
    { key: 'site', text: o.site?.name },
  ];
  return (
    <Dialog title={t('suggestions.acceptTitle')} isOpen onOpenChange={(open) => !open && !isPending && onClose()}>
      <p>{t('suggestions.acceptIntro', { diver: preview.diver.name })}</p>
      <Muted>{t('suggestions.acceptWhat')}</Muted>
      {preview.overlaps.length === 0 ? <Muted>{t('suggestions.noOverlaps')}</Muted> : (
        <>
          <p>{t('suggestions.overlapIntro')}</p>
          {preview.overlaps.map((o) => {
            const item = display.diveTime(o.startsAt, o.utcOffsetSeconds);
            const linking = isPending && pendingDiveId === o.id;
            return (
              <div key={o.id} className="decision">
                <Candidates>
                  <CandidateRow title={<span className="candidate-title">{t('suggestions.yourDive')}</span>} facts={overlapFacts(o)} marked={new Set()} />
                </Candidates>
                <Button
                  icon="link" isPending={linking} isDisabled={isPending && !linking}
                  aria-label={t('common.forItem', { action: t('suggestions.link'), item })} onPress={() => onAccept(o.id)}
                >
                  {t('suggestions.link')}
                </Button>
              </div>
            );
          })}
        </>
      )}
      {error != null && <Notice tone="danger">{errorText(error)}</Notice>}
      <div className="form-actions">
        <Button
          icon="add" isPending={isPending && pendingDiveId === undefined} isDisabled={isPending && pendingDiveId !== undefined}
          onPress={() => onAccept()}
        >
          {t('suggestions.create')}
        </Button>
        <Button isDisabled={isPending} onPress={onClose}>{t('common.cancel')}</Button>
      </div>
    </Dialog>
  );
}
