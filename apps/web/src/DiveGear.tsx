// The Dive's gear (ADR 0034): the items it derives from the Diver's every-Dive items, the Recordings' Devices and the
// Cylinders that name one, plus the put-on and taken-off exceptions. Putting an item on, taking one off and "same as
// last dive" work here; nothing is filled without the User's action (ADR 0031).
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api, diveGearQuery, equipmentOfDiverQuery, keys, unwrap, type DiveView, type EquipmentCategory } from './api.ts';
import { announce } from './lib/announce.ts';
import { useErrorText } from './lib/display.ts';
import { Button, Dialog, Muted, Notice, RowsSkeleton, Select } from './ui/index.ts';

const sourceKey = (source: 'every_dive' | 'device' | 'cylinder') => `equipment.source.${source}` as const;

export function DiveGear({ dive }: { dive: DiveView }) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const [adding, setAdding] = useState(false);
  const gear = useQuery(diveGearQuery(dive.id));
  const items = useQuery(equipmentOfDiverQuery(dive.diverId));
  const refresh = async () => {
    await Promise.all([
      queryClient.invalidateQueries({ queryKey: keys.diveGear(dive.id) }),
      queryClient.invalidateQueries({ queryKey: keys.equipment }),
    ]);
  };
  const use = useMutation({
    mutationFn: async ({ itemId, kind }: { itemId: string; kind: 'put_on' | 'taken_off' }) =>
      unwrap(await api.POST('/api/dives/{id}/equipment', { params: { path: { id: dive.id } }, body: { itemId, use: kind } })),
    onSuccess: refresh,
  });
  const removeUse = useMutation({
    mutationFn: async (itemId: string) =>
      unwrap(await api.DELETE('/api/dives/{id}/equipment/{itemId}', { params: { path: { id: dive.id, itemId } } })),
    onSuccess: refresh,
  });
  const sameAsLast = useMutation({
    mutationFn: async () => {
      const last = unwrap(await api.GET('/api/dives/{id}/same-as-last', { params: { path: { id: dive.id } } }));
      const current = gear.data;
      const present = new Set([
        ...(current?.putOn.map((item) => item.id) ?? []),
        ...(current?.derived.map((item) => item.id) ?? []),
      ]);
      const missing = last.gear.filter((item) => !present.has(item.id));
      for (const item of missing) {
        await unwrap(await api.POST('/api/dives/{id}/equipment', { params: { path: { id: dive.id } }, body: { itemId: item.id, use: 'put_on' } }));
      }
      return missing.length;
    },
    onSuccess: async (count) => {
      await refresh();
      announce(t('equipment.gearFromLast', { count }));
    },
  });
  if (gear.isPending) return <><h2 className="subheading">{t('equipment.gearTitle')}</h2><RowsSkeleton rows={2} /></>;
  if (gear.error) return <><h2 className="subheading">{t('equipment.gearTitle')}</h2><Notice tone="danger">{errorText(gear.error)}</Notice></>;
  const g = gear.data;
  const candidates = (items.data ?? []).filter((item) => !item.onEveryDive
    && !g.putOn.some((used) => used.id === item.id) && !g.derived.some((used) => used.id === item.id));
  const none = g.derived.length === 0 && g.putOn.length === 0 && g.takenOff.length === 0;
  return (
    <div className="dive-gear">
      <h2 className="subheading">{t('equipment.gearTitle')}</h2>
      {none && <Muted>{t('equipment.gearNone')}</Muted>}
      {g.derived.length > 0 && (
        <>
          <h3 className="equipment-subheading">{t('equipment.derived')}</h3>
          <ul className="plain-list">
            {g.derived.map((item) => (
              <li key={item.id}>
                <span>{item.name}</span>{' '}
                <span className="meta">
                  {item.sources.map((source) => t(sourceKey(source))).join(', ')}
                  {item.sources.includes('every_dive') && (
                    <Button variant="quiet" size="small" onPress={() => use.mutate({ itemId: item.id, kind: 'taken_off' })}>
                      {t('equipment.takeOff')}
                    </Button>
                  )}
                </span>
              </li>
            ))}
          </ul>
        </>
      )}
      {g.putOn.length > 0 && (
        <>
          <h3 className="equipment-subheading">{t('equipment.putOn')}</h3>
          <ul className="plain-list">
            {g.putOn.map((item) => (
              <li key={item.id}>
                <span>{item.name}</span>{' '}
                <span className="meta">{t(`vocabulary.equipmentCategory.${item.category}`)}
                  <Button variant="quiet" size="small" onPress={() => removeUse.mutate(item.id)}>{t('equipment.remove')}</Button>
                </span>
              </li>
            ))}
          </ul>
        </>
      )}
      {g.takenOff.length > 0 && (
        <>
          <h3 className="equipment-subheading">{t('equipment.takenOff')}</h3>
          <ul className="plain-list">
            {g.takenOff.map((item) => (
              <li key={item.id}>
                <span>{item.name}</span>{' '}
                <span className="meta">{t(`vocabulary.equipmentCategory.${item.category}`)}
                  <Button variant="quiet" size="small" onPress={() => removeUse.mutate(item.id)}>{t('equipment.putBackOn')}</Button>
                </span>
              </li>
            ))}
          </ul>
        </>
      )}
      {(use.error ?? removeUse.error ?? sameAsLast.error) && <Notice tone="danger">{errorText(use.error ?? removeUse.error ?? sameAsLast.error)}</Notice>}
      <div className="form-actions">
        <Button size="small" icon="add" onPress={() => setAdding(true)} isDisabled={candidates.length === 0}>{t('equipment.putOnItem')}</Button>
        <Button size="small" icon="undo" isPending={sameAsLast.isPending} onPress={() => sameAsLast.mutate()}>{t('equipment.sameAsLast')}</Button>
      </div>
      {adding && (
        <PutOnDialog
          items={candidates} onClose={() => setAdding(false)}
          onPutOn={(itemId) => use.mutate({ itemId, kind: 'put_on' }, { onSuccess: () => setAdding(false) })}
          pending={use.isPending}
        />
      )}
    </div>
  );
}

function PutOnDialog({ items, onClose, onPutOn, pending }: {
  items: { id: string; name: string; category: EquipmentCategory }[]; onClose: () => void; onPutOn: (itemId: string) => void; pending: boolean;
}) {
  const { t } = useTranslation();
  const [chosen, setChosen] = useState<string | null>(items[0]?.id ?? null);
  return (
    <Dialog title={t('equipment.putOnTitle')} isOpen onOpenChange={(open) => !open && onClose()}>
      <Select
        label={t('equipment.item')} value={chosen}
        options={items.map((item) => ({ id: item.id, label: `${item.name} · ${t(`vocabulary.equipmentCategory.${item.category}`)}` }))}
        onChange={setChosen}
      />
      <div className="form-actions">
        <Button variant="primary" icon="add" isPending={pending} isDisabled={chosen === null} onPress={() => chosen && onPutOn(chosen)}>{t('equipment.putOnItem')}</Button>
        <Button onPress={onClose}>{t('common.cancel')}</Button>
      </div>
    </Dialog>
  );
}
