// The Equipment page (ADR 0034): the User's Divers' items by category, each with its status, its service schedules and
// their computed state and reason, its service records and its usage. Items are created and edited here; nothing is
// defaulted, least of all a service interval, which comes from the manual or the cylinder's stamp.
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect, useRef, useState, type FormEvent } from 'react';
import { useTranslation } from 'react-i18next';
import type { TFunction } from 'i18next';
import {
  api, devicesQuery, diversQuery, equipmentItemQuery, equipmentOfDiverQuery, keys, unwrap,
  type DiverView, type EquipmentCategory, type EquipmentItemView, type EquipmentScheduleDue,
  type EquipmentScheduleView, type EquipmentStatus,
} from './api.ts';
import { announce } from './lib/announce.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { usePageTitle } from './lib/page.ts';
import {
  ActionMenu, Badge, Button, Checkbox, ConfirmDialog, Dialog, Form, Muted, Notice, NumberField, PageHeader, Panel,
  RowsSkeleton, Select, Table, TextArea, TextField, type MenuAction,
} from './ui/index.ts';

const CATEGORIES: EquipmentCategory[] = [
  'regulator', 'bcd', 'wing', 'backplate', 'cylinder', 'drysuit', 'wetsuit', 'dive_computer', 'transmitter', 'light', 'camera', 'other',
];
const STATUSES: EquipmentStatus[] = ['in_use', 'retired', 'lost', 'sold'];

const categoryName = (t: TFunction) => (category: EquipmentCategory) => t(`vocabulary.equipmentCategory.${category}`);
const statusName = (t: TFunction) => (status: EquipmentStatus) => t(`vocabulary.equipmentStatus.${status}`);

const statusTone = (status: EquipmentStatus): 'neutral' | 'success' | 'danger' =>
  status === 'in_use' ? 'success' : status === 'lost' ? 'danger' : 'neutral';

const today = () => new Date().toISOString().slice(0, 10);

/** The label that tells two items of the same name apart: their serial, from the Device when the item is one. */
const itemReference = (item: EquipmentItemView, deviceSerials: ReadonlyMap<string, string>) =>
  `${item.name}, ${item.serial ?? deviceSerials.get(item.id) ?? ''}`.replace(/, $/, '');

/** The Equipment page, at "#/equipment"; `itemId` ("?item=…") is the item a Device's Settings row links to. */
export function EquipmentPage({ itemId }: { itemId?: string | undefined }) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  usePageTitle(t('equipment.title'));
  const divers = useQuery(diversQuery());
  // A Device's item carries no serial of its own; the Device's tells two computers of the same model apart.
  const devices = useQuery(devicesQuery());
  const deviceSerials = new Map((devices.data ?? []).map((device) => [device.equipmentItemId, device.serialNumber]));
  return (
    <>
      <PageHeader title={t('equipment.title')} lead={t('equipment.intro')} />
      {divers.isPending && <RowsSkeleton rows={3} />}
      {divers.error && <Notice tone="danger">{errorText(divers.error)}</Notice>}
      {divers.data?.map((diver) => (
        <DiverEquipment key={diver.id} diver={diver} openItemId={itemId} deviceSerials={deviceSerials} devicesPending={devices.isPending} />
      ))}
      {divers.data?.length === 0 && <Panel><Muted>{t('equipment.noDivers')}</Muted></Panel>}
    </>
  );
}

function DiverEquipment({ diver, openItemId, deviceSerials, devicesPending }: {
  diver: DiverView; openItemId?: string | undefined; deviceSerials: ReadonlyMap<string, string>; devicesPending: boolean;
}) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const items = useQuery(equipmentOfDiverQuery(diver.id));
  const [creating, setCreating] = useState(false);
  const pending = items.isPending || devicesPending;
  const list = pending ? [] : (items.data ?? []);
  return (
    <Panel
      title={t('equipment.ofDiver', { name: diver.name })}
      actions={<Button icon="add" onPress={() => setCreating(true)} aria-label={t('common.forItem', { action: t('equipment.add'), item: diver.name })}>{t('equipment.add')}</Button>}
    >
      {pending && <RowsSkeleton rows={2} />}
      {items.error && <Notice tone="danger">{errorText(items.error)}</Notice>}
      {!pending && items.data?.length === 0 && <Muted>{t('equipment.none')}</Muted>}
      {CATEGORIES.map((category) => {
        const group = list.filter((item) => item.category === category);
        if (group.length === 0) return null;
        return (
          // A category heading, not a region: the same category name recurs for every Diver, and two regions with one
          // name are a landmark-unique violation (axe). The heading gives the group its place in the page outline.
          <div key={category} className="equipment-category">
            <h3>{categoryName(t)(category)}</h3>
            <ul className="equipment-list">
              {group.map((item) => (
                <ItemCard key={item.id} item={item} diverName={diver.name} deviceSerials={deviceSerials} open={item.id === openItemId} />
              ))}
            </ul>
          </div>
        );
      })}
      {creating && <ItemForm diverId={diver.id} onClose={() => setCreating(false)} />}
    </Panel>
  );
}

function ItemCard({ item, diverName, deviceSerials, open }: {
  item: EquipmentItemView; diverName: string; deviceSerials: ReadonlyMap<string, string>; open: boolean;
}) {
  const { t } = useTranslation();
  const reference = itemReference(item, deviceSerials);
  const display = useDisplay();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const [editing, setEditing] = useState(open);
  const [recording, setRecording] = useState(false);
  const [scheduling, setScheduling] = useState(false);
  const [editingSchedule, setEditingSchedule] = useState<EquipmentScheduleView | null>(null);
  const [deleting, setDeleting] = useState(false);
  const [history, setHistory] = useState(open);
  const [removing, setRemoving] = useState<EquipmentScheduleView | null>(null);
  const card = useRef<HTMLLIElement>(null);
  useEffect(() => {
    if (open) card.current?.scrollIntoView({ block: 'start' });
  }, [open]);
  const refresh = async () => {
    await queryClient.invalidateQueries({ queryKey: keys.equipment });
  };
  const setStatus = useMutation({
    mutationFn: async (equipmentStatus: EquipmentStatus) =>
      unwrap(await api.PATCH('/api/equipment/{id}', { params: { path: { id: item.id } }, body: { version: item.version, equipmentStatus } })),
    onSuccess: async (_answer, equipmentStatus) => {
      await refresh();
      announce(t('equipment.statusChanged', { name: item.name, status: statusName(t)(equipmentStatus) }));
    },
  });
  const remove = useMutation({
    mutationFn: async () => unwrap(await api.DELETE('/api/equipment/{id}', { params: { path: { id: item.id } } })),
    onSuccess: async () => {
      await refresh();
      announce(t('equipment.deleted', { name: item.name }));
    },
  });
  const removeSchedule = useMutation({
    mutationFn: async (schedule: EquipmentScheduleView) => unwrap(await api.DELETE('/api/schedules/{id}', { params: { path: { id: schedule.id } } })),
    onSuccess: refresh,
  });
  const actions: MenuAction[] = [
    { id: 'edit', label: t('equipment.edit'), icon: 'edit', onAction: () => setEditing(true) },
    { id: 'record', label: t('equipment.record'), icon: 'save', onAction: () => setRecording(true) },
    { id: 'schedule', label: t('equipment.addSchedule'), icon: 'add', onAction: () => setScheduling(true) },
    ...(item.equipmentStatus === 'in_use'
      ? [
        { id: 'retire', label: t('equipment.retire'), icon: 'undo' as const, onAction: () => setStatus.mutate('retired') },
        { id: 'sell', label: t('equipment.sell'), icon: 'send' as const, onAction: () => setStatus.mutate('sold') },
        { id: 'lose', label: t('equipment.lose'), icon: 'danger' as const, onAction: () => setStatus.mutate('lost') },
      ]
      : [{ id: 'in-use', label: t('equipment.backInUse'), icon: 'enable' as const, onAction: () => setStatus.mutate('in_use') }]),
    { id: 'delete', label: t('equipment.delete'), icon: 'delete', onAction: () => setDeleting(true) },
  ];
  return (
    <li className="equipment-item reveal" id={`equipment-item-${item.id}`} ref={card}>
      <div className="equipment-item-head">
        <div>
          <h4 className="equipment-item-name">{item.name} <Badge tone={statusTone(item.equipmentStatus)}>{statusName(t)(item.equipmentStatus)}</Badge></h4>
          <p className="meta">{[
            categoryName(t)(item.category),
            [item.maker, item.model].filter(Boolean).join(' '),
            item.serial ?? deviceSerials.get(item.id),
          ].filter(Boolean).join(' · ')}</p>
          <p className="meta">
            {item.onEveryDive
              ? t('equipment.everyDiveFrom', { date: display.date(item.inUseFrom) })
              : t('equipment.putOnOnly')}
            {item.inUseUntil && ` · ${t('equipment.outOfUseSince', { date: display.date(item.inUseUntil) })}`}
            {item.deviceId && <> · <a href="#/settings/devices">{t('equipment.deviceLink')}</a></>}
          </p>
        </div>
        <ActionMenu
          label={t('equipment.actions')}
          aria-label={t('common.forItem', { action: t('equipment.actions'), item: `${reference}, ${diverName}` })}
          actions={actions}
        />
      </div>
      {item.schedules.length === 0 ? <Muted>{t('equipment.noSchedules')}</Muted> : (
        <Table cards label={t('equipment.schedulesOf', { name: item.name })} head={[t('equipment.schedule'), t('equipment.interval'), t('equipment.due'), { label: t('common.actions'), hidden: true }]}>
          {item.schedules.map((schedule) => (
            <tr key={schedule.id}>
              <td>{schedule.name}{!schedule.active && <> <Badge>{t('equipment.inactive')}</Badge></>}</td>
              <td>{intervalText(t, schedule)}</td>
              <td><DueState due={schedule.due} /></td>
              <td className="actions">
                <Button variant="quiet" size="small" icon="edit" aria-label={t('common.forItem', { action: t('equipment.editSchedule'), item: `${reference}, ${schedule.name}` })} onPress={() => setEditingSchedule(schedule)} />
                <Button variant="quiet" size="small" icon="delete" aria-label={t('common.forItem', { action: t('equipment.deleteSchedule'), item: `${reference}, ${schedule.name}` })} onPress={() => setRemoving(schedule)} />
              </td>
            </tr>
          ))}
        </Table>
      )}
      {setStatus.error && <Notice tone="danger">{errorText(setStatus.error)}</Notice>}
      {removeSchedule.error && <Notice tone="danger">{errorText(removeSchedule.error)}</Notice>}
      <div className="form-actions">
        <Button size="small" icon="add" onPress={() => setScheduling(true)} aria-label={t('common.forItem', { action: t('equipment.addSchedule'), item: reference })}>{t('equipment.addSchedule')}</Button>
        <Button size="small" icon="save" onPress={() => setRecording(true)} aria-label={t('common.forItem', { action: t('equipment.record'), item: reference })}>{t('equipment.record')}</Button>
        <Button variant="quiet" size="small" onPress={() => setHistory((shown) => !shown)} aria-expanded={history}
          aria-label={t('common.forItem', { action: history ? t('equipment.hideHistory') : t('equipment.showHistory'), item: reference })}>
          {history ? t('equipment.hideHistory') : t('equipment.showHistory')}
        </Button>
      </div>
      {history && <ItemHistory item={item} />}
      {editing && <ItemForm diverId={item.diverId} item={item} onClose={() => setEditing(false)} />}
      {recording && <RecordForm item={item} onClose={() => setRecording(false)} />}
      {scheduling && <ScheduleForm item={item} onClose={() => setScheduling(false)} />}
      {editingSchedule && <ScheduleForm item={item} schedule={editingSchedule} onClose={() => setEditingSchedule(null)} />}
      <ConfirmDialog
        isOpen={deleting} onOpenChange={setDeleting} title={t('equipment.deleteTitle', { name: item.name })}
        body={t('equipment.deleteBody', { name: item.name })} confirmLabel={t('equipment.delete')}
        onConfirm={() => remove.mutateAsync()}
      />
      <ConfirmDialog
        isOpen={removing !== null} onOpenChange={(open) => !open && setRemoving(null)}
        title={t('equipment.deleteScheduleTitle', { name: removing?.name ?? '' })}
        body={t('equipment.deleteScheduleBody')} confirmLabel={t('equipment.deleteSchedule')}
        onConfirm={async () => { if (removing) await removeSchedule.mutateAsync(removing); setRemoving(null); }}
      />
      {remove.error && <Notice tone="danger">{errorText(remove.error)}</Notice>}
    </li>
  );
}

/** The schedule's intervals in words ("every 12 months · every 100 dives"); at least one. */
function intervalText(t: TFunction, schedule: EquipmentScheduleView): string {
  const intervals = [
    schedule.intervalMonths !== null ? t('equipment.intervalMonths', { count: schedule.intervalMonths }) : null,
    schedule.intervalDives !== null ? t('equipment.intervalDives', { count: schedule.intervalDives }) : null,
    schedule.intervalHours !== null ? t('equipment.intervalHours', { count: schedule.intervalHours }) : null,
  ].filter(Boolean);
  return t('equipment.every', { intervals: intervals.join(' · '), date: schedule.startDate });
}

/** A schedule's computed state and reason, with the worst dimension's own words. */
function DueState({ due }: { due: EquipmentScheduleDue | null }) {
  const { t } = useTranslation();
  if (!due) return <Muted>{t('equipment.inactive')}</Muted>;
  if (due.state === 'ok') return <Badge>{t('equipment.dueState.ok')}</Badge>;
  return (
    <>
      <Badge tone={due.state === 'due_soon' ? 'neutral' : 'danger'}>{t(`equipment.dueState.${due.state}`)}</Badge>
      {due.reason && <span className="meta"> {due.reason}</span>}
    </>
  );
}

function ItemHistory({ item }: { item: EquipmentItemView }) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const display = useDisplay();
  const detail = useQuery(equipmentItemQuery(item.id));
  if (detail.isPending) return <RowsSkeleton rows={2} />;
  if (detail.error) return <Notice tone="danger">{errorText(detail.error)}</Notice>;
  const d = detail.data;
  const names = new Map(item.schedules.map((schedule) => [schedule.id, schedule.name]));
  return (
    <div className="equipment-history">
      <p className="meta">
        {t('equipment.usedOn', { count: d.usage.diveCount, hours: d.usage.hours })}
        {d.usage.lastUsedOn && ` · ${t('equipment.lastUsed', { date: display.date(d.usage.lastUsedOn) })}`}
      </p>
      {d.lastCylinder && (
        <p className="meta">{t('equipment.lastCylinder', {
          volume: d.lastCylinder.volumeL ?? t('common.none'),
          pressure: d.lastCylinder.workingPressureBar ?? t('common.none'),
          material: d.lastCylinder.material ? t(`vocabulary.cylinderMaterial.${d.lastCylinder.material}`) : t('common.none'),
        })}</p>
      )}
      {d.records.length === 0 ? <Muted>{t('equipment.noRecords')}</Muted> : (
        <Table cards label={t('equipment.recordsOf', { name: item.name })} head={[t('equipment.recordDate'), t('equipment.byWhom'), t('equipment.resets'), t('equipment.notes'), t('equipment.cost')]}>
          {d.records.map((record) => (
            <tr key={record.id}>
              <td className="date">{display.date(record.date)}</td>
              <td>{record.byWhom ?? t('common.none')}</td>
              <td>{record.scheduleIds.map((id) => names.get(id) ?? t('equipment.deletedSchedule')).join(', ') || t('common.none')}</td>
              <td>{record.notes ?? t('common.none')}</td>
              <td className="num">{record.costAmount !== null && record.costCurrency ? `${record.costAmount.toFixed(2)} ${record.costCurrency}` : t('common.none')}</td>
            </tr>
          ))}
        </Table>
      )}
    </div>
  );
}

interface ScheduleDraft {
  key: string;
  name: string;
  months: number;
  dives: number;
  hours: number;
  startDate: string;
  active: boolean;
}

let scheduleDrafts = 0;
const emptySchedule = (): ScheduleDraft => ({ key: `schedule-${++scheduleDrafts}`, name: '', months: Number.NaN, dives: Number.NaN, hours: Number.NaN, startDate: '', active: true });
const intervalValue = (n: number) => (Number.isNaN(n) || n <= 0 ? null : n);

function ScheduleInputs({ draft, onChange }: { draft: ScheduleDraft; onChange: (next: ScheduleDraft) => void }) {
  const { t } = useTranslation();
  const set = <K extends keyof ScheduleDraft>(key: K, value: ScheduleDraft[K]) => onChange({ ...draft, [key]: value });
  return (
    <fieldset className="equipment-schedule">
      <legend>{t('equipment.schedule')}</legend>
      <div className="form-grid">
        <TextField label={t('equipment.scheduleName')} value={draft.name} onChange={(name) => set('name', name)} isRequired maxLength={200} />
        <NumberField label={t('equipment.months')} value={draft.months} onChange={(months) => set('months', months)} minValue={1} formatOptions={{ maximumFractionDigits: 0 }} />
        <NumberField label={t('equipment.dives')} value={draft.dives} onChange={(dives) => set('dives', dives)} minValue={1} formatOptions={{ maximumFractionDigits: 0 }} />
        <NumberField label={t('equipment.hours')} value={draft.hours} onChange={(hours) => set('hours', hours)} minValue={0.1} formatOptions={{ maximumFractionDigits: 1 }} />
        <TextField label={t('equipment.startDate')} type="date" value={draft.startDate} onChange={(startDate) => set('startDate', startDate)} isRequired description={t('equipment.startDateHint')} />
      </div>
      <Checkbox isSelected={draft.active} onChange={(active) => set('active', active)}>{t('equipment.active')}</Checkbox>
    </fieldset>
  );
}

const scheduleBody = (draft: ScheduleDraft) => ({
  name: draft.name.trim(), intervalMonths: intervalValue(draft.months), intervalDives: intervalValue(draft.dives),
  intervalHours: intervalValue(draft.hours), startDate: draft.startDate, active: draft.active,
});

/** The note under every schedule form: Dive Hub has no built-in intervals (ADR 0034). */
function ScheduleNote() {
  const { t } = useTranslation();
  return <Muted>{t('equipment.scheduleNote')}</Muted>;
}

function ScheduleForm({ item, schedule, onClose }: { item: EquipmentItemView; schedule?: EquipmentScheduleView | undefined; onClose: () => void }) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const [draft, setDraft] = useState<ScheduleDraft>(() => (schedule ? {
    key: schedule.id, name: schedule.name, months: schedule.intervalMonths ?? Number.NaN, dives: schedule.intervalDives ?? Number.NaN,
    hours: schedule.intervalHours ?? Number.NaN, startDate: schedule.startDate, active: schedule.active,
  } : emptySchedule()));
  const [removing, setRemoving] = useState<EquipmentScheduleView | null>(null);
  const save = useMutation({
    mutationFn: async () => (schedule
      ? unwrap(await api.PATCH('/api/schedules/{id}', { params: { path: { id: schedule.id } }, body: { version: schedule.version, ...scheduleBody(draft) } }))
      : unwrap(await api.POST('/api/equipment/{id}/schedules', { params: { path: { id: item.id } }, body: scheduleBody(draft) }))),
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: keys.equipment });
      announce(t(schedule ? 'equipment.scheduleSaved' : 'equipment.scheduleAdded', { name: item.name }));
      onClose();
    },
  });
  const valid = draft.name.trim() !== '' && draft.startDate !== '' && [draft.months, draft.dives, draft.hours].some((n) => intervalValue(n) !== null);
  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (valid) save.mutate();
  };
  return (
    <Dialog title={t(schedule ? 'equipment.editScheduleTitle' : 'equipment.addScheduleTitle', { name: item.name })} isOpen onOpenChange={(open) => !open && onClose()}>
      <Form className="form" onSubmit={submit}>
        <ScheduleNote />
        <ScheduleInputs draft={draft} onChange={setDraft} />
        {item.schedules.length > 0 && (
          <div className="equipment-existing-schedules">
            <Muted>{t('equipment.existingSchedules')}</Muted>
            <ul className="plain-list">
              {item.schedules.map((schedule) => (
                <li key={schedule.id}>
                  <span>{schedule.name} · {intervalText(t, schedule)}</span>
                  <Button variant="quiet" size="small" icon="delete" aria-label={t('common.forItem', { action: t('equipment.deleteSchedule'), item: `${item.name}, ${schedule.name}` })} onPress={() => setRemoving(schedule)} />
                </li>
              ))}
            </ul>
          </div>
        )}
        {save.error && <Notice tone="danger">{errorText(save.error)}</Notice>}
        <div className="form-actions">
          <Button type="submit" variant="primary" icon="save" isPending={save.isPending} isDisabled={!valid}>{t('equipment.saveSchedule')}</Button>
          <Button onPress={onClose}>{t('common.cancel')}</Button>
        </div>
      </Form>
      <ConfirmDialog
        isOpen={removing !== null} onOpenChange={(open) => !open && setRemoving(null)}
        title={t('equipment.deleteScheduleTitle', { name: removing?.name ?? '' })}
        body={t('equipment.deleteScheduleBody')} confirmLabel={t('equipment.deleteSchedule')}
        onConfirm={async () => {
          if (removing) await unwrap(await api.DELETE('/api/schedules/{id}', { params: { path: { id: removing.id } } }));
          await queryClient.invalidateQueries({ queryKey: keys.equipment });
          setRemoving(null);
        }}
      />
    </Dialog>
  );
}

function RecordForm({ item, onClose }: { item: EquipmentItemView; onClose: () => void }) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const [date, setDate] = useState(today);
  const [byWhom, setByWhom] = useState('');
  const [notes, setNotes] = useState('');
  const [cost, setCost] = useState('');
  const [currency, setCurrency] = useState('');
  const [resets, setResets] = useState<Set<string>>(() => new Set(item.schedules.map((schedule) => schedule.id)));
  const save = useMutation({
    mutationFn: async () => {
      const amount = cost.trim() === '' ? null : Number(cost);
      return unwrap(await api.POST('/api/equipment/{id}/records', {
        params: { path: { id: item.id } },
        body: {
          date,
          byWhom: byWhom.trim() === '' ? null : byWhom.trim(),
          notes: notes.trim() === '' ? null : notes,
          costAmount: amount, costCurrency: amount === null ? null : currency.trim().toUpperCase(),
          scheduleIds: [...resets],
        },
      }));
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: keys.equipment });
      announce(t('equipment.recordAdded', { name: item.name }));
      onClose();
    },
  });
  const amount = cost.trim() === '' ? null : Number(cost);
  const costValid = amount === null || (!Number.isNaN(amount) && amount >= 0 && /^[A-Za-z]{3}$/.test(currency.trim()));
  const valid = date !== '' && resets.size > 0 && costValid;
  const toggle = (id: string, checked: boolean) => setResets((old) => {
    const next = new Set(old);
    if (checked) next.add(id); else next.delete(id);
    return next;
  });
  return (
    <Dialog title={t('equipment.recordTitle', { name: item.name })} isOpen onOpenChange={(open) => !open && onClose()}>
      <Form className="form" onSubmit={(event) => { event.preventDefault(); if (valid) save.mutate(); }}>
        <div className="form-grid">
          <TextField label={t('equipment.recordDate')} type="date" value={date} onChange={setDate} isRequired />
          <TextField label={t('equipment.byWhom')} value={byWhom} onChange={setByWhom} maxLength={200} description={t('equipment.byWhomHint')} />
          <TextField label={t('equipment.cost')} value={cost} onChange={setCost} inputMode="decimal" description={t('equipment.costHint')} />
          <TextField label={t('equipment.currency')} value={currency} onChange={setCurrency} maxLength={3} description={t('equipment.currencyHint')} />
        </div>
        <TextArea label={t('equipment.recordNotes')} value={notes} onChange={setNotes} maxLength={20_000} />
        <fieldset className="equipment-resets">
          <legend>{t('equipment.resets')}</legend>
          <Muted>{t('equipment.resetsHint')}</Muted>
          {item.schedules.length === 0 && <Muted>{t('equipment.noSchedulesToReset')}</Muted>}
          {item.schedules.map((schedule) => (
            <Checkbox key={schedule.id} isSelected={resets.has(schedule.id)} onChange={(checked) => toggle(schedule.id, checked)}>
              {schedule.name} · {intervalText(t, schedule)}
            </Checkbox>
          ))}
        </fieldset>
        {save.error && <Notice tone="danger">{errorText(save.error)}</Notice>}
        <div className="form-actions">
          <Button type="submit" variant="primary" icon="save" isPending={save.isPending} isDisabled={!valid}>{t('equipment.saveRecord')}</Button>
          <Button onPress={onClose}>{t('common.cancel')}</Button>
        </div>
      </Form>
    </Dialog>
  );
}

function ItemForm({ diverId, item, onClose }: { diverId: string; item?: EquipmentItemView | undefined; onClose: () => void }) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  const [category, setCategory] = useState<EquipmentCategory | null>(item?.category ?? null);
  const [name, setName] = useState(item?.name ?? '');
  const [maker, setMaker] = useState(item?.maker ?? '');
  const [model, setModel] = useState(item?.model ?? '');
  const [serial, setSerial] = useState(item?.serial ?? '');
  const [purchaseDate, setPurchaseDate] = useState(item?.purchaseDate ?? '');
  const [notes, setNotes] = useState(item?.notes ?? '');
  const [status, setStatus] = useState<EquipmentStatus>(item?.equipmentStatus ?? 'in_use');
  const [onEveryDive, setOnEveryDive] = useState(item?.onEveryDive ?? false);
  const [inUseFrom, setInUseFrom] = useState(item?.inUseFrom ?? '');
  const [inUseUntil, setInUseUntil] = useState(item?.inUseUntil ?? '');
  const [schedules, setSchedules] = useState<ScheduleDraft[]>([]);
  const save = useMutation({
    mutationFn: async () => {
      const common = {
        category: category!, name: name.trim(), maker: maker.trim() === '' ? null : maker.trim(),
        model: model.trim() === '' ? null : model.trim(), serial: serial.trim() === '' ? null : serial.trim(),
        purchaseDate: purchaseDate === '' ? null : purchaseDate, notes: notes.trim() === '' ? null : notes,
        equipmentStatus: status, onEveryDive, inUseFrom,
        // Blank when leaving use: the server stamps today. Sending null would clear it instead.
        ...(status !== 'in_use' && inUseUntil !== '' ? { inUseUntil } : {}),
      };
      if (item) return unwrap(await api.PATCH('/api/equipment/{id}', { params: { path: { id: item.id } }, body: { version: item.version, ...common } }));
      return unwrap(await api.POST('/api/divers/{id}/equipment', {
        params: { path: { id: diverId } },
        body: { ...common, schedules: schedules.map(scheduleBody) },
      }));
    },
    onSuccess: async (saved) => {
      await queryClient.invalidateQueries({ queryKey: keys.equipment });
      announce(t(item ? 'equipment.saved' : 'equipment.added', { name: saved.name }));
      onClose();
    },
  });
  const scheduleValid = schedules.every((draft) => draft.name.trim() !== '' && draft.startDate !== '' && [draft.months, draft.dives, draft.hours].some((n) => intervalValue(n) !== null));
  const valid = category !== null && name.trim() !== '' && inUseFrom !== '' && scheduleValid && (status === 'in_use' || inUseUntil === '' || inUseUntil >= inUseFrom);
  const setSchedule = (key: string, next: ScheduleDraft) => setSchedules((old) => old.map((draft) => (draft.key === key ? next : draft)));
  return (
    <Dialog title={t(item ? 'equipment.editTitle' : 'equipment.addTitle', { name: item?.name ?? t('equipment.newItem') })} isOpen onOpenChange={(open) => !open && onClose()}>
      <Form className="form" onSubmit={(event) => { event.preventDefault(); if (valid) save.mutate(); }}>
        <div className="form-grid">
          <Select
            label={t('equipment.category')} value={category}
            options={CATEGORIES.map((value) => ({ id: value, label: categoryName(t)(value) }))}
            onChange={setCategory}
          />
          <TextField label={t('equipment.name')} value={name} onChange={setName} isRequired maxLength={200} />
          <TextField label={t('equipment.maker')} value={maker} onChange={setMaker} maxLength={200} />
          <TextField label={t('equipment.model')} value={model} onChange={setModel} maxLength={200} />
          <TextField label={t('equipment.serial')} value={serial} onChange={setSerial} maxLength={200} />
          <TextField label={t('equipment.purchaseDate')} type="date" value={purchaseDate} onChange={setPurchaseDate} />
          <Select label={t('equipment.status')} value={status} options={STATUSES.map((value) => ({ id: value, label: statusName(t)(value) }))} onChange={(value) => setStatus(value ?? 'in_use')} />
          <TextField
            label={t('equipment.inUseFrom')} type="date" value={inUseFrom} onChange={setInUseFrom} isRequired
            description={t('equipment.inUseFromHint')}
          />
          {status !== 'in_use' && (
            <TextField
              label={t('equipment.inUseUntil')} type="date" value={inUseUntil} onChange={setInUseUntil}
              description={t('equipment.inUseUntilHint')}
            />
          )}
        </div>
        <Checkbox isSelected={onEveryDive} onChange={setOnEveryDive}>{t('equipment.onEveryDive')}</Checkbox>
        <Muted>{t('equipment.onEveryDiveHint')}</Muted>
        <TextArea label={t('equipment.notes')} value={notes} onChange={setNotes} maxLength={20_000} />
        {!item && (
          <>
            <Muted>{t('equipment.schedulesIntro')}</Muted>
            <ScheduleNote />
            {schedules.map((draft) => (
              <div key={draft.key}>
                <ScheduleInputs draft={draft} onChange={(next) => setSchedule(draft.key, next)} />
                <div className="form-actions">
                  <Button variant="quiet" size="small" icon="delete" onPress={() => setSchedules((old) => old.filter((x) => x.key !== draft.key))}>
                    {t('equipment.removeSchedule')}
                  </Button>
                </div>
              </div>
            ))}
            <div className="form-actions">
              <Button icon="add" onPress={() => setSchedules((old) => [...old, emptySchedule()])}>{t('equipment.addSchedule')}</Button>
            </div>
          </>
        )}
        {save.error && <Notice tone="danger">{errorText(save.error)}</Notice>}
        <div className="form-actions">
          <Button type="submit" variant="primary" icon="save" isPending={save.isPending} isDisabled={!valid}>{t('equipment.saveItem')}</Button>
          <Button onPress={onClose}>{t('common.cancel')}</Button>
        </div>
      </Form>
    </Dialog>
  );
}
