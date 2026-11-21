// Fill offers for a Joint dive's shared facts (FAH-412, ADR 0070): the Review page's cards beside Buddy suggestions,
// and the inline prompt on a Dive's page. One card per source Dive; accepting copies its current values into the
// User's Dive, declining drops them for this Dive and Joint dive. Nothing individual is ever shown (the API never
// sends it).
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useRef, type RefObject } from 'react';
import { useTranslation } from 'react-i18next';
import {
  api, diveJointOffersQuery, jointOffersQuery, keys, unwrap, type JointOfferField, type JointOfferView,
} from './api.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { refocusAfterRemoval } from './lib/focus.ts';
import { Comparison, type CompareFact } from './ReviewRows.tsx';
import { Button, Notice, Panel } from './ui/index.ts';

type OfferFacts = Pick<JointOfferView['dive'], 'startsAt' | 'utcOffsetSeconds' | 'durationSeconds' | 'maxDepthM' | 'avgDepthM' | 'site'>;

/** Every fill offer of the User's Joint dives, in the Review page's "To decide" part; nothing while none waits. */
export function JointOffers() {
  const { t } = useTranslation();
  const offers = useQuery(jointOffersQuery());
  const list = useRef<HTMLUListElement>(null);
  const all = offers.data ?? [];
  if (all.length === 0) return null;
  return (
    <Panel title={t('jointOffers.title')} attention>
      <p className="muted">{t('jointOffers.intro')}</p>
      <OfferList offers={all} list={list} />
    </Panel>
  );
}

/** A Dive's fill offers, inline on its page; nothing while none waits. */
export function DiveJointOffers({ diveId }: { diveId: string }) {
  const { t } = useTranslation();
  const offers = useQuery(diveJointOffersQuery(diveId));
  const list = useRef<HTMLUListElement>(null);
  const all = offers.data ?? [];
  if (all.length === 0) return null;
  return (
    <Panel title={t('jointOffers.title')} attention>
      <p className="muted">{t('jointOffers.intro')}</p>
      <OfferList offers={all} list={list} />
    </Panel>
  );
}

function OfferList({ offers, list }: { offers: JointOfferView[]; list: RefObject<HTMLUListElement | null> }) {
  return (
    <ul className="decisions" ref={list}>
      {offers.map((offer, index) => (
        <JointOfferCard
          key={`${offer.dive.id}:${offer.source.diveId}`} offer={offer} list={list} index={index} count={offers.length}
        />
      ))}
    </ul>
  );
}

/**
 * One source Dive's offer: the facts it can fill beside the receiving Dive's, and the two answers. Accepting copies
 * every offered field; declining drops them and the card disappears once the list is read again.
 */
export function JointOfferCard({ offer, list, index = 0, count = 1 }: {
  offer: JointOfferView;
  list?: RefObject<HTMLUListElement | null>;
  index?: number;
  count?: number;
}) {
  const { t } = useTranslation();
  const display = useDisplay();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const accept = useMutation({
    mutationFn: async () => unwrap(await api.POST('/api/joint-dives/offers/accept', {
      body: { diveId: offer.dive.id, fields: offer.fields },
    })),
    onSuccess: async () => {
      await Promise.all([
        queryClient.invalidateQueries({ queryKey: keys.jointOffers }),
        queryClient.invalidateQueries({ queryKey: keys.dives }),
      ]);
    },
    // An offer accepted or declined elsewhere, or a field filled meanwhile, comes back as such: read them again.
    onError: () => queryClient.invalidateQueries({ queryKey: keys.jointOffers }),
  });
  const decline = useMutation({
    mutationFn: async () => unwrap(await api.POST('/api/joint-dives/offers/decline', {
      body: { diveId: offer.dive.id, fields: offer.fields },
    })),
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: keys.jointOffers });
      if (list) refocusAfterRemoval(list.current, index, count);
    },
    onError: () => queryClient.invalidateQueries({ queryKey: keys.jointOffers }),
  });

  const value = (facts: OfferFacts, field: JointOfferField): string | undefined => {
    switch (field) {
      case 'siteId': return facts.site?.name;
      case 'startsAt': return display.diveTime(facts.startsAt, facts.utcOffsetSeconds);
      case 'durationSeconds': return display.duration(facts.durationSeconds);
      case 'maxDepthM': return facts.maxDepthM === null ? undefined : display.depth(facts.maxDepthM);
      case 'avgDepthM': return facts.avgDepthM === null ? undefined : display.depth(facts.avgDepthM);
    }
  };
  const facts: CompareFact[] = offer.fields.map((field) => ({
    key: field,
    label: t(`jointOffers.field.${field}`),
    values: [value(offer.source, field), value(offer.dive, field)],
  }));
  const from = t('jointOffers.from', { diver: offer.source.diver.name });
  const item = `${from} · ${display.diveTime(offer.dive.startsAt, offer.dive.utcOffsetSeconds)}`;
  return (
    <li className="decision">
      <h3 className="decision-question">{from}</h3>
      <Comparison
        label={t('jointOffers.compareLabel', { diver: offer.source.diver.name })}
        subjects={[
          { key: offer.source.diveId, label: offer.source.diver.name, title: from },
          {
            key: offer.dive.id,
            label: t('jointOffers.yourDive'),
            title: t('jointOffers.yourDive'),
            note: display.diveTime(offer.dive.startsAt, offer.dive.utcOffsetSeconds),
          },
        ]}
        facts={facts}
      />
      <p className="muted">{t('jointOffers.consequence')}</p>
      <div className="form-actions">
        <Button
          icon="add" isPending={accept.isPending}
          aria-label={t('common.forItem', { action: t('jointOffers.accept'), item })}
          onPress={() => accept.mutate()}
        >
          {t('jointOffers.accept')}
        </Button>
        <Button
          variant="quiet" isPending={decline.isPending}
          aria-label={t('common.forItem', { action: t('jointOffers.decline'), item })}
          onPress={() => decline.mutate()}
        >
          {t('jointOffers.decline')}
        </Button>
      </div>
      {accept.error && <Notice tone="danger">{errorText(accept.error)}</Notice>}
      {decline.error && <Notice tone="danger">{errorText(decline.error)}</Notice>}
    </li>
  );
}
