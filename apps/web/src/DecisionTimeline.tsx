import { Fragment } from 'react';
import { useTranslation } from 'react-i18next';
import { useDisplay } from './lib/display.ts';
import { timelineGeometry, type TimelineBar, type TimelineLane } from './lib/timeline.ts';

/** What a gap's label calls itself: a plain space, or the surface break between two parts of one Dive (ADR 0063). */
export type GapKind = 'gap' | 'break';

export interface DecisionTimelineProps {
  lanes: TimelineLane[];
  /** `break` labels every gap as a surface break, for a Dive cut in parts (ADR 0063). */
  gapKind?: GapKind;
}

/**
 * The bars of a decision on one wall-clock axis (FAH-214): each Recording, Dive or part a bar, its start and end times
 * at the ends, its duration on the bar, and the space between bars labeled with its length. An overlap reads from the
 * lanes lining up. Decoration like `ProfileSketch`: the comparison below and the hidden summary carry the values as text.
 */
export function DecisionTimeline({ lanes, gapKind = 'gap' }: DecisionTimelineProps) {
  const { t } = useTranslation();
  const display = useDisplay();
  const geometry = timelineGeometry(lanes);
  const flatBars = lanes.flatMap((lane) => lane.bars);
  const offsetOf = (lane: number) => lanes.slice(0, lane).reduce((count, l) => count + l.bars.length, 0);
  const barAt = (lane: number, bar: TimelineBar) => geometry.bars[offsetOf(lane) + lanes[lane]!.bars.indexOf(bar)]!;
  const clockOf = (bar: TimelineBar, ms: number) =>
    display.clock(new Date(ms).toISOString(), bar.utcOffsetSeconds ?? null, bar.utcOffsetSource);
  const gapText = (seconds: number) =>
    gapKind === 'break'
      ? t('checks.surface.break', { duration: display.duration(seconds) })
      : t('timeline.gap', { duration: display.duration(seconds) });
  const summary = [
    ...geometry.bars.map((g, i) => {
      const bar = flatBars[i]!;
      return t('timeline.summaryBar', {
        label: g.label, start: clockOf(bar, g.startMs), end: clockOf(bar, g.endMs), duration: display.duration(bar.durationSeconds),
      });
    }),
    ...geometry.gaps.map((gap) => t(gapKind === 'break' ? 'timeline.summaryBreak' : 'timeline.summaryGap', {
      duration: display.duration(gap.seconds),
    })),
  ].join(' ');
  return (
    <div className="timeline-figure">
      <p className="visually-hidden">{summary}</p>
      <figure className="timeline" aria-hidden="true">
        {lanes.map((lane, laneIndex) => (
          <Fragment key={lane.key}>
            <div className="timeline-lane">
              <span className="timeline-label">
                <span className="timeline-name">{lane.label}</span>
                {lane.meta && <span className="timeline-meta">{lane.meta}</span>}
              </span>
              <span className="timeline-track">
                <svg className="timeline-bars" viewBox="0 0 100 100" preserveAspectRatio="none" focusable="false">
                  {lane.bars.map((bar) => {
                    const g = barAt(laneIndex, bar);
                    return <rect key={bar.key} className="timeline-bar" x={g.left * 100} y="20" width={(g.right - g.left) * 100} height="60" vectorEffect="non-scaling-stroke" />;
                  })}
                </svg>
                {lane.bars.map((bar) => {
                  const g = barAt(laneIndex, bar);
                  return (
                    <Fragment key={bar.key}>
                      <span className="timeline-time timeline-time-start" style={{ left: `${g.left * 100}%` }}>{clockOf(bar, g.startMs)}</span>
                      <span className="timeline-time timeline-time-end" style={{ left: `${g.right * 100}%` }}>{clockOf(bar, g.endMs)}</span>
                      <span className="timeline-duration" style={{ left: `${g.left * 100}%`, width: `${(g.right - g.left) * 100}%` }}>
                        {display.duration(bar.durationSeconds)}
                      </span>
                    </Fragment>
                  );
                })}
              </span>
            </div>
            {geometry.gaps.filter((gap) => gap.lane === laneIndex).map((gap) => {
              // Keep the label inside the track: a gap near an end names itself towards the middle.
              const centre = (gap.left + gap.right) / 2;
              const label = centre < 0.25
                ? { left: 0, transform: 'translateY(-50%)' }
                : centre > 0.75
                  ? { right: 0, left: 'auto', transform: 'translateY(-50%)' }
                  : { left: '50%', transform: 'translate(-50%, -50%)' };
              return (
                <div key={`${gap.left}:${gap.right}`} className="timeline-gap">
                  <span />
                  <span className="timeline-gap-line" style={{ left: `${gap.left * 100}%`, width: `${(gap.right - gap.left) * 100}%` }}>
                    <span className="timeline-gap-label" style={label}>{gapText(gap.seconds)}</span>
                  </span>
                </div>
              );
            })}
          </Fragment>
        ))}
      </figure>
    </div>
  );
}
