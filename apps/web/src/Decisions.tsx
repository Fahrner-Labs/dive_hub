import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect, useRef, useState, type RefObject } from 'react';
import { useTranslation } from 'react-i18next';
import { api, candidatesQuery, keys, unwrap, type CandidateView } from './api.ts';
import { Comparison, type CompareFact } from './ReviewRows.tsx';
import { deviceName } from './lib/devices.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { refocusAfterRemoval } from './lib/focus.ts';
import { Badge, Button, Notice, Panel } from './ui/index.ts';

/**
 * Duplicate candidates on the Review page (ADR 0016): Recordings that don't clearly belong to one Dive, each beside the
 * Dives it might belong to, with the three decisions. Nothing while none waits.
 */
export function RecordingDecisions() {
  const { t } = useTranslation();
  const open = useQuery(candidatesQuery('open'));
  const discarded = useQuery(candidatesQuery('discarded'));
  // The Recording just discarded, offered back with "Undo" (UI review B2).
  const [undo, setUndo] = useState<CandidateView>();
  const list = useRef<HTMLUListElement>(null);
  const count = open.data?.length ?? 0;
  // Offered only while that Recording is still discarded (it may be decided again another way).
  const undoable = undo && discarded.data?.some((c) => c.id === undo.id) ? undo : undefined;
  return (
    <>
      {undoable && <UndoDiscard key={undoable.id} candidate={undoable} onDone={() => setUndo(undefined)} />}
      {count > 0 && (
        <Panel title={t('decisions.title')} attention>
          <p className="muted">{t('decisions.intro', { count })}</p>
          <ul className="decisions" ref={list}>
            {open.data?.map((c, index) => (
              <Decision key={c.id} candidate={c} list={list} index={index} count={count} onDiscarded={() => setUndo(c)} />
            ))}
          </ul>
        </Panel>
      )}
    </>
  );
}

/** The Recordings put aside, with the way to decide again (the Review page's "Decided"). Nothing while there are none. */
export function DiscardedRecordings() {
  const { t } = useTranslation();
  const discarded = useQuery(candidatesQuery('discarded'));
  const list = useRef<HTMLUListElement>(null);
  const count = discarded.data?.length ?? 0;
  if (count === 0) return null;
  return (
    <Panel title={t('decisions.discarded')}>
      <ul className="decisions" ref={list}>
        {discarded.data?.map((c, index) => (
          <DiscardedItem key={c.id} candidate={c} onReopened={() => refocusAfterRemoval(list.current, index, count)} />
        ))}
      </ul>
    </Panel>
  );
}

function RecordingLine({ c }: { c: CandidateView }) {
  const { t } = useTranslation();
  const display = useDisplay();
  const r = c.recording;
  const device = r.device
    ? t('decisions.device', { name: deviceName(r.device.manufacturer, r.device.product), serial: r.device.serialNumber })
    : t('decisions.unknownDevice');
  return (
    <div className="decision-recording">
      <p>
        <strong>{t('decisions.recording')}:</strong>{' '}
        {display.diveTime(r.startsAt, r.utcOffsetSeconds, r.utcOffsetSource)} · {display.depth(r.maxDepthM)} · {display.duration(r.durationSeconds)} · {device}
      </p>
      {/* Why it wasn't added: a sentence of its own, at body size (visual refresh 4). */}
      <p className="muted">{t('decisions.why', { reason: t(`import.reason.${c.reason}`) })}</p>
    </div>
  );
}

/**
 * The decisions about one Duplicate candidate. The candidate leaves its list afterwards; focus then
 * goes to the next one, else the panel or page heading (UI review B1).
 */
type Action = { kind: 'attach'; diveId: string } | { kind: 'new-dive' | 'discard' | 'reopen' };

function useDecide(c: CandidateView, refocus?: (action: Action) => void) {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (action: Action) => {
      const path = { params: { path: { id: c.id } } };
      switch (action.kind) {
        case 'attach': return unwrap(await api.POST('/api/duplicate-candidates/{id}/attach', { ...path, body: { diveId: action.diveId } }));
        case 'new-dive': return unwrap(await api.POST('/api/duplicate-candidates/{id}/new-dive', path));
        case 'discard': return unwrap(await api.POST('/api/duplicate-candidates/{id}/discard', path));
        case 'reopen': return unwrap(await api.POST('/api/duplicate-candidates/{id}/reopen', path));
      }
    },
    onSettled: async (_data, error, action) => {
      await queryClient.invalidateQueries({ queryKey: ['candidates'] });
      await queryClient.invalidateQueries({ queryKey: keys.dives });
      if (!error) refocus?.(action);
    },
  });
}

/** "Discarded the recording from …" with a way back. Takes focus: the Discard button is gone. */
function UndoDiscard({ candidate: c, onDone }: { candidate: CandidateView; onDone: () => void }) {
  const { t } = useTranslation();
  const display = useDisplay();
  const reopen = useDecide(c);
  const button = useRef<HTMLButtonElement>(null);
  useEffect(() => button.current?.focus(), []);
  return (
    <Notice tone="success">
      <p>{t('decisions.discardedNotice', { time: display.diveTime(c.recording.startsAt, c.recording.utcOffsetSeconds, c.recording.utcOffsetSource) })}</p>
      <Button ref={button} icon="undo" isPending={reopen.isPending} onPress={() => reopen.mutate({ kind: 'reopen' }, { onSuccess: onDone })}>
        {t('common.undo')}
      </Button>
    </Notice>
  );
}

function Decision({ candidate: c, list, index, count, onDiscarded }: {
  candidate: CandidateView; list: RefObject<HTMLUListElement | null>; index: number; count: number; onDiscarded: () => void;
}) {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const display = useDisplay();
  const decide = useDecide(c, (action) => {
    if (action.kind === 'discard') onDiscarded(); // the Undo notice takes focus
    else refocusAfterRemoval(list.current, index, count);
  });
  // Several Recordings may wait at once; their buttons say which one they decide about.
  const recordingName = display.diveTime(c.recording.startsAt, c.recording.utcOffsetSeconds, c.recording.utcOffsetSource);
  const r = c.recording;
  const device = r.device
    ? t('decisions.device', { name: deviceName(r.device.manufacturer, r.device.product), serial: r.device.serialNumber })
    : t('decisions.unknownDevice');
  const diveName = (d: CandidateView['dives'][number]) => (d.number !== null ? t('dive.title', { number: d.number }) : display.diveTime(d.startsAt, d.utcOffsetSeconds, d.utcOffsetSource));
  const valuesOf = (v: CandidateView['dives'][number] | CandidateView['recording']) => ({
    time: display.diveTime(v.startsAt, v.utcOffsetSeconds, v.utcOffsetSource), depth: display.depth(v.maxDepthM), duration: display.duration(v.durationSeconds),
  });
  const recordingValues = valuesOf(r);
  const diveValues = c.dives.map(valuesOf);
  const facts: CompareFact[] = [
    { key: 'time', label: t('decisions.fact.time'), values: [recordingValues.time, ...diveValues.map((v) => v.time)] },
    { key: 'duration', label: t('decisions.fact.duration'), values: [recordingValues.duration, ...diveValues.map((v) => v.duration)] },
    { key: 'depth', label: t('decisions.fact.maxDepth'), values: [recordingValues.depth, ...diveValues.map((v) => v.depth)] },
    // Only the Recording has a Device; the Dives it might belong to have none.
    { key: 'device', label: t('decisions.fact.device'), values: [device, ...c.dives.map(() => undefined)] },
  ];
  const subjects = [
    { key: 'recording', title: t('decisions.recording'), badge: <Badge>{t(`import.reason.${c.reason}`)}</Badge> },
    ...c.dives.map((d) => ({
      key: d.id,
      title: <a className="candidate-title" href={`#/dives/${d.id}`}>{d.number !== null ? t('dive.title', { number: d.number }) : t('dive.titleNoNumber')}</a>,
    })),
  ];
  return (
    <li className="decision">
      <h3 className="decision-question">{t('decisions.question')}</h3>
      <Comparison subjects={subjects} facts={facts} />
      {/* The Dives it might belong to were deleted (ADR 0026): what's left is a Dive of its own, or discarding it. */}
      {c.dives.length === 0 && <p className="muted">{t('decisions.divesGone')}</p>}
      <p className="muted">{t('decisions.consequence')}</p>
      <div className="form-actions">
        {c.dives.map((d) => (
          <Button
            key={d.id} icon="merge" aria-label={t('common.forItem', { action: t('decisions.addTo', { name: diveName(d) }), item: recordingName })}
            isPending={decide.isPending && decide.variables.kind === 'attach' && decide.variables.diveId === d.id}
            isDisabled={decide.isPending}
            onPress={() => decide.mutate({ kind: 'attach', diveId: d.id })}
          >
            {t('decisions.addTo', { name: diveName(d) })}
          </Button>
        ))}
        <Button
          icon="add" aria-label={t('common.forItem', { action: t('decisions.newDive'), item: recordingName })}
          isPending={decide.isPending && decide.variables.kind === 'new-dive'} isDisabled={decide.isPending}
          onPress={() => decide.mutate({ kind: 'new-dive' })}
        >
          {t('decisions.newDive')}
        </Button>
        <Button
          variant="quiet" aria-label={t('common.forItem', { action: t('decisions.discard'), item: recordingName })}
          isPending={decide.isPending && decide.variables.kind === 'discard'} isDisabled={decide.isPending}
          onPress={() => decide.mutate({ kind: 'discard' })}
        >
          {t('decisions.discard')}
        </Button>
      </div>
      {decide.error && <Notice tone="danger">{errorText(decide.error)}</Notice>}
    </li>
  );
}

function DiscardedItem({ candidate: c, onReopened }: { candidate: CandidateView; onReopened: () => void }) {
  const { t } = useTranslation();
  const display = useDisplay();
  const decide = useDecide(c, onReopened);
  return (
    <li className="decision">
      <RecordingLine c={c} />
      <Button
        variant="quiet"
        aria-label={t('common.forItem', { action: t('decisions.reopen'), item: display.diveTime(c.recording.startsAt, c.recording.utcOffsetSeconds, c.recording.utcOffsetSource) })}
        isPending={decide.isPending}
        onPress={() => decide.mutate({ kind: 'reopen' })}
      >
        {t('decisions.reopen')}
      </Button>
    </li>
  );
}
