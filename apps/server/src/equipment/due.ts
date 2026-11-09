// When an item's service is due (ADR 0034, plan D4/D5): a pure function, computed on every read and never stored.
// A schedule is due at whichever of months, dives or hours comes first, counted from the newest service record that
// resets it, else its start date. The boundary day itself does not count as usage: a Dive on the anchor date (or a
// dive on a schedule's start date) happened before that day's service, so only later Dives count.
export const DUE_STATES = ['ok', 'due_soon', 'due', 'overdue'] as const;
export type DueState = (typeof DUE_STATES)[number];

/** The part of a usage Dive the due dimensions read: its local date and duration. */
export interface UsageDive {
  date: string;
  durationSeconds: number;
}

/** The parts of a service schedule the due computation reads. */
export interface DueSchedule {
  intervalMonths: number | null;
  intervalDives: number | null;
  intervalHours: number | null;
  /** YYYY-MM-DD: when the service was last done before Dive Hub. */
  startDate: string;
  active: boolean;
}

export interface MonthsDue {
  dueDate: string;
  remainingDays: number;
  state: DueState;
}

export interface CountDue {
  used: number;
  remaining: number;
  state: DueState;
}

export interface ScheduleDue {
  /** The newest resetting service record's date, else the schedule's start date. */
  anchor: string;
  /** The worst dimension's state. */
  state: DueState;
  /** The worst dimension, when it isn't ok: "due 2027-03", "100 dives reached", "10 hours reached". */
  reason: string | null;
  months: MonthsDue | null;
  dives: CountDue | null;
  hours: CountDue | null;
}

export interface DueInput {
  schedule: DueSchedule;
  /** Dates (YYYY-MM-DD) of the service records that reset this schedule. */
  resets: readonly string[];
  /** The item's usage dives (see usage.ts). */
  usage: readonly UsageDive[];
  /** Today as YYYY-MM-DD. */
  today: string;
}

const STATE_RANK: Record<DueState, number> = { ok: 0, due_soon: 1, due: 2, overdue: 3 };
const DAY_MS = 86_400_000;
const DUE_SOON_DAYS = 30;
const DUE_SOON_FRACTION = 0.1;

const utcDay = (date: string): number => {
  const [year, month, day] = date.split('-').map(Number) as [number, number, number];
  return Date.UTC(year, month - 1, day);
};

const isoDay = (ms: number): string => new Date(ms).toISOString().slice(0, 10);

/** Calendar months after a date, clamped to the target month's last day (2026-01-31 + 1 month → 2026-02-28). */
function addMonths(date: string, months: number): string {
  const [year, month, day] = date.split('-').map(Number) as [number, number, number];
  const target = new Date(Date.UTC(year, month - 1 + months, 1));
  const lastDay = new Date(Date.UTC(target.getUTCFullYear(), target.getUTCMonth() + 1, 0)).getUTCDate();
  target.setUTCDate(Math.min(day, lastDay));
  return isoDay(target.getTime());
}

function dimensionState(remaining: number, threshold: number): DueState {
  if (remaining < 0) return 'overdue';
  if (remaining === 0) return 'due';
  if (remaining <= threshold) return 'due_soon';
  return 'ok';
}

const round = (value: number): number => Math.round(value * 1e6) / 1e6;

const plural = (hours: number): string => `${hours} ${hours === 1 ? 'hour' : 'hours'}`;

/**
 * The schedule's due state, or null when it is inactive (it computes nothing). A backdated service record does not
 * move the clock back: the anchor is the newest resetting record's date, still stored as the fact it is.
 */
export function scheduleDue({ schedule, resets, usage, today }: DueInput): ScheduleDue | null {
  if (!schedule.active) return null;

  const anchor = resets.reduce((newest, date) => (date > newest ? date : newest), schedule.startDate);
  const afterAnchor = usage.filter((dive) => dive.date > anchor);

  const months = schedule.intervalMonths === null ? null : (() => {
    const dueDate = addMonths(anchor, schedule.intervalMonths);
    const remainingDays = Math.round((utcDay(dueDate) - utcDay(today)) / DAY_MS);
    return { dueDate, remainingDays, state: dimensionState(remainingDays, DUE_SOON_DAYS) };
  })();

  const dives = schedule.intervalDives === null ? null : (() => {
    const used = afterAnchor.length;
    const remaining = schedule.intervalDives - used;
    return { used, remaining, state: dimensionState(remaining, schedule.intervalDives * DUE_SOON_FRACTION) };
  })();

  const hoursInterval = schedule.intervalHours;
  const hours = hoursInterval === null ? null : (() => {
    const used = round(afterAnchor.reduce((sum, dive) => sum + dive.durationSeconds, 0) / 3600);
    const remaining = round(hoursInterval - used);
    return { used, remaining, state: dimensionState(remaining, hoursInterval * DUE_SOON_FRACTION) };
  })();

  const dimensions: { state: DueState; reason: string }[] = [];
  if (months) dimensions.push({ state: months.state, reason: `due ${months.dueDate.slice(0, 7)}` });
  if (dives) dimensions.push({ state: dives.state, reason: `${schedule.intervalDives} dives reached` });
  if (hours && hoursInterval !== null) dimensions.push({ state: hours.state, reason: `${plural(hoursInterval)} reached` });

  let state: DueState = 'ok';
  let reason: string | null = null;
  for (const dimension of dimensions) {
    if (STATE_RANK[dimension.state] > STATE_RANK[state]) {
      state = dimension.state;
      reason = dimension.reason;
    }
  }

  return { anchor, state, reason, months, dives, hours };
}
