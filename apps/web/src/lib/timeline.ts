// Timeline geometry for the Review decision cards (FAH-214): the bars of a Recording and its candidate Dives, of two
// Dives at the same time, or of a Dive's parts, on one wall-clock axis. The card draws them; this helper works out
// where each bar sits, which stretches are gaps and where bars overlap.

export interface TimelineBar {
  key: string;
  /** The Dive or Recording this bar is, where its lane holds more than one bar (a surface-break chain). */
  label?: string;
  startsAt: string;
  utcOffsetSeconds?: number | null;
  utcOffsetSource?: string;
  durationSeconds: number;
}

export interface TimelineLane {
  key: string;
  label: string;
  meta?: string;
  bars: TimelineBar[];
}

export interface TimelineBarGeometry {
  key: string;
  /** The lane the bar belongs to, 0-based. */
  lane: number;
  /** The bar's own name for the card's text: its label, else its lane's. */
  label: string;
  /** The bar's start and end in UTC, for the times at its ends. */
  startMs: number;
  endMs: number;
  /** The same moments with the Dive's UTC offset added: the shared wall-clock axis. */
  localStartMs: number;
  localEndMs: number;
  /** The bar's place on the axis: 0 is the earliest start, 1 the latest end. */
  left: number;
  right: number;
}

export interface TimelineGapGeometry {
  /** The lane under which the gap is drawn. */
  lane: number;
  startMs: number;
  endMs: number;
  /** How long the space between the two bars was, in seconds. */
  seconds: number;
  left: number;
  right: number;
}

export interface TimelineOverlapGeometry {
  startMs: number;
  endMs: number;
  seconds: number;
  left: number;
  right: number;
}

export interface TimelineGeometry {
  startMs: number;
  endMs: number;
  bars: TimelineBarGeometry[];
  gaps: TimelineGapGeometry[];
  /** The stretches two bars share; the card shows them by lining the lanes up. */
  overlaps: TimelineOverlapGeometry[];
}

/** A bar's start on the wall-clock axis: the logged moment plus the Dive's own offset. */
const wallClockMs = (bar: TimelineBar) => Date.parse(bar.startsAt) + (bar.utcOffsetSeconds ?? 0) * 1000;

/** The space between two bars, when there is one. */
function addGap(gaps: Map<string, TimelineGapGeometry>, from: TimelineBarGeometry, to: TimelineBarGeometry, at: (ms: number) => number) {
  if (to.localStartMs <= from.localEndMs) return;
  // The same space seen from either bar is one gap.
  gaps.set(`${from.localEndMs}:${to.localStartMs}`, {
    lane: Math.min(from.lane, to.lane), startMs: from.endMs, endMs: to.startMs,
    seconds: Math.round((to.localStartMs - from.localEndMs) / 1000),
    left: at(from.localEndMs), right: at(to.localStartMs),
  });
}

/**
 * The gaps and overlaps: consecutive parts within a lane (a Dive cut by surface breaks), and the reference lane
 * against each other lane (the Recording against each candidate Dive, the suggested Dive against the User's own).
 */
function relations(bars: TimelineBarGeometry[], laneCount: number, at: (ms: number) => number) {
  const gaps = new Map<string, TimelineGapGeometry>();
  const overlaps = new Map<string, TimelineOverlapGeometry>();
  for (let lane = 0; lane < laneCount; lane++) {
    const parts = bars.filter((bar) => bar.lane === lane).sort((a, b) => a.localStartMs - b.localStartMs);
    for (let i = 1; i < parts.length; i++) addGap(gaps, parts[i - 1]!, parts[i]!, at);
  }
  // Overlap needs no label: the lanes line the shared stretch up.
  for (const a of bars.filter((bar) => bar.lane === 0)) {
    for (const b of bars.filter((bar) => bar.lane > 0)) {
      addGap(gaps, a.localStartMs <= b.localStartMs ? a : b, a.localStartMs <= b.localStartMs ? b : a, at);
      const start = Math.max(a.localStartMs, b.localStartMs);
      const end = Math.min(a.localEndMs, b.localEndMs);
      if (end <= start) continue;
      overlaps.set(`${start}:${end}`, {
        startMs: Math.max(a.startMs, b.startMs), endMs: Math.min(a.endMs, b.endMs),
        seconds: Math.round((end - start) / 1000), left: at(start), right: at(end),
      });
    }
  }
  return { gaps: [...gaps.values()], overlaps: [...overlaps.values()] };
}

/** Places every bar of every lane on one axis. */
export function timelineGeometry(lanes: TimelineLane[]): TimelineGeometry {
  const flat = lanes.flatMap((lane, laneIndex) => lane.bars.map((bar) => ({ laneIndex, bar })));
  if (flat.length === 0) return { startMs: 0, endMs: 0, bars: [], gaps: [], overlaps: [] };
  const times = flat.map(({ bar }) => ({ start: wallClockMs(bar), end: wallClockMs(bar) + bar.durationSeconds * 1000 }));
  const axisStart = Math.min(...times.map((t) => t.start));
  const axisEnd = Math.max(...times.map((t) => t.end));
  const span = axisEnd - axisStart || 1;
  const at = (ms: number) => Math.min(1, Math.max(0, (ms - axisStart) / span));
  const bars = flat.map(({ laneIndex, bar }, i) => {
    const t = times[i]!;
    return {
      key: bar.key, lane: laneIndex, label: bar.label ?? lanes[laneIndex]!.label,
      startMs: Date.parse(bar.startsAt), endMs: Date.parse(bar.startsAt) + bar.durationSeconds * 1000,
      localStartMs: t.start, localEndMs: t.end, left: at(t.start), right: at(t.end),
    };
  });
  return { startMs: axisStart, endMs: axisEnd, bars, ...relations(bars, lanes.length, at) };
}
