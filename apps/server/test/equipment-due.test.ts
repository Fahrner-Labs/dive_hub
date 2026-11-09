// Due computation for an item's service schedules (ADR 0034, plan D4/D5): a pure function, never stored. Whichever of
// months, dives or hours comes first, counted from the last service record that resets the schedule (else its start
// date), with the boundary day itself not counting as usage.
import { describe, expect, it } from 'vitest';
import { scheduleDue, type DueSchedule, type UsageDive } from '../src/equipment/due.js';

const schedule = (over: Partial<DueSchedule> = {}): DueSchedule => ({
  intervalMonths: null, intervalDives: null, intervalHours: null, startDate: '2026-01-01', active: true, ...over,
});
const dived = (date: string, hours = 1): UsageDive => ({ date, durationSeconds: hours * 3600 });
const run = (
  over: Partial<DueSchedule>, options: { resets?: string[]; usage?: UsageDive[]; today?: string } = {},
) => scheduleDue({
  schedule: schedule(over),
  resets: options.resets ?? [],
  usage: options.usage ?? [],
  today: options.today ?? '2026-01-01',
});

describe('service schedule due', () => {
  it('is due at whichever dimension comes first', () => {
    const due = run(
      { intervalMonths: 12, intervalDives: 10, intervalHours: 20 },
      { usage: Array.from({ length: 10 }, (_, i) => dived(`2026-02-${String(i + 1).padStart(2, '0')}`, 0.5)), today: '2026-03-01' },
    );
    expect(due?.state).toBe('due');
    expect(due?.reason).toBe('10 dives reached');
    expect(due?.dives).toEqual({ used: 10, remaining: 0, state: 'due' });
    expect(due?.hours).toEqual({ used: 5, remaining: 15, state: 'ok' });
    expect(due?.months).toEqual({ dueDate: '2027-01-01', remainingDays: 306, state: 'ok' });
  });

  it('counts from the last service record that resets the schedule', () => {
    const due = run(
      { intervalMonths: 12 },
      { resets: ['2025-12-01', '2026-06-01'], today: '2026-06-02' },
    );
    expect(due?.anchor).toBe('2026-06-01');
    expect(due?.months?.dueDate).toBe('2027-06-01');
    expect(due?.months?.remainingDays).toBe(364);
    expect(due?.state).toBe('ok');
    expect(due?.reason).toBeNull();
  });

  it('a backdated record never moves the clock back', () => {
    const due = run(
      { intervalMonths: 12 },
      { resets: ['2026-06-01', '2026-03-01'], today: '2026-06-02' },
    );
    expect(due?.anchor).toBe('2026-06-01');
    expect(due?.months?.dueDate).toBe('2027-06-01');
  });

  it('clamps a month interval to the end of a shorter month', () => {
    expect(run({ intervalMonths: 1, startDate: '2026-01-31' }, { today: '2026-02-01' })?.months?.dueDate).toBe('2026-02-28');
    expect(run({ intervalMonths: 1, startDate: '2024-01-31' }, { today: '2024-02-01' })?.months?.dueDate).toBe('2024-02-29');
    expect(run({ intervalMonths: 1, startDate: '2026-03-31' }, { today: '2026-04-01' })?.months?.dueDate).toBe('2026-04-30');
  });

  it('is due soon within 30 days of the months due date', () => {
    const soon = run({ intervalMonths: 1 }, { today: '2026-01-02' });
    expect(soon?.months).toEqual({ dueDate: '2026-02-01', remainingDays: 30, state: 'due_soon' });
    expect(soon?.state).toBe('due_soon');
    expect(soon?.reason).toBe('due 2026-02');

    const ok = run({ intervalMonths: 1 }, { today: '2026-01-01' });
    expect(ok?.months?.remainingDays).toBe(31);
    expect(ok?.state).toBe('ok');
    expect(ok?.reason).toBeNull();
  });

  it('is due soon within 10 % of a dive interval', () => {
    expect(run({ intervalDives: 100 }, { usage: [dived('2026-02-01')], today: '2026-02-02' })?.dives?.remaining).toBe(99);
    const soon = run(
      { intervalDives: 100 },
      { usage: Array.from({ length: 90 }, () => dived('2026-02-01')), today: '2026-02-02' },
    );
    expect(soon?.dives).toEqual({ used: 90, remaining: 10, state: 'due_soon' });
    expect(soon?.reason).toBe('10 dives to go');
    const oneLeft = run(
      { intervalDives: 10 },
      { usage: Array.from({ length: 9 }, () => dived('2026-02-01')), today: '2026-02-02' },
    );
    expect(oneLeft?.dives).toEqual({ used: 9, remaining: 1, state: 'due_soon' });
    expect(oneLeft?.reason).toBe('1 dive to go');
    const ok = run(
      { intervalDives: 100 },
      { usage: Array.from({ length: 89 }, () => dived('2026-02-01')), today: '2026-02-02' },
    );
    expect(ok?.dives).toEqual({ used: 89, remaining: 11, state: 'ok' });
    const reached = run(
      { intervalDives: 100 },
      { usage: Array.from({ length: 100 }, () => dived('2026-02-01')), today: '2026-02-02' },
    );
    expect(reached?.state).toBe('due');
    expect(reached?.reason).toBe('100 dives reached');
    const over = run(
      { intervalDives: 100 },
      { usage: Array.from({ length: 101 }, () => dived('2026-02-01')), today: '2026-02-02' },
    );
    expect(over?.state).toBe('overdue');
  });

  it('is due soon within 10 % of an hour interval', () => {
    expect(run({ intervalHours: 10 }, { usage: [dived('2026-02-01', 9)], today: '2026-02-02' })?.hours)
      .toEqual({ used: 9, remaining: 1, state: 'due_soon' });
    expect(run({ intervalHours: 10 }, { usage: [dived('2026-02-01', 8.9)], today: '2026-02-02' })?.hours)
      .toEqual({ used: 8.9, remaining: 1.1, state: 'ok' });
    const soonHours = run({ intervalHours: 10 }, { usage: [dived('2026-02-01', 9.5)], today: '2026-02-02' });
    expect(soonHours?.hours).toEqual({ used: 9.5, remaining: 0.5, state: 'due_soon' });
    expect(soonHours?.reason).toBe('0.5 hours to go');
    const over = run({ intervalHours: 10 }, { usage: [dived('2026-02-01', 12)], today: '2026-02-02' });
    expect(over?.hours).toEqual({ used: 12, remaining: -2, state: 'overdue' });
    expect(over?.reason).toBe('10 hours reached');
  });

  it('does not count a dive on the anchor day itself, for a record or a start date', () => {
    const atStart = run({ intervalDives: 1, startDate: '2026-01-01' }, { usage: [dived('2026-01-01')], today: '2026-01-01' });
    expect(atStart?.dives).toEqual({ used: 0, remaining: 1, state: 'ok' });

    const afterStart = run({ intervalDives: 1, startDate: '2026-01-01' }, { usage: [dived('2026-01-02')], today: '2026-01-02' });
    expect(afterStart?.dives).toEqual({ used: 1, remaining: 0, state: 'due' });

    const atRecord = run(
      { intervalDives: 1 },
      { resets: ['2026-05-01'], usage: [dived('2026-05-01')], today: '2026-05-02' },
    );
    expect(atRecord?.anchor).toBe('2026-05-01');
    expect(atRecord?.dives).toEqual({ used: 0, remaining: 1, state: 'ok' });

    const afterRecord = run(
      { intervalDives: 1 },
      { resets: ['2026-05-01'], usage: [dived('2026-05-02')], today: '2026-05-02' },
    );
    expect(afterRecord?.dives).toEqual({ used: 1, remaining: 0, state: 'due' });
  });

  it('an inactive schedule computes nothing', () => {
    expect(run({ intervalMonths: 1, active: false }, { today: '2026-06-01' })).toBeNull();
  });

  it('takes the worst dimension and names it as the reason', () => {
    const due = run(
      { intervalMonths: 12, intervalDives: 100, intervalHours: 10 },
      {
        usage: [...Array.from({ length: 95 }, () => dived('2026-02-01', 0.05)), dived('2026-02-02', 9.25)],
        today: '2026-02-03',
      },
    );
    expect(due?.months?.state).toBe('ok');
    expect(due?.dives?.state).toBe('due_soon');
    expect(due?.hours?.state).toBe('overdue');
    expect(due?.state).toBe('overdue');
    expect(due?.reason).toBe('10 hours reached');
  });
});
