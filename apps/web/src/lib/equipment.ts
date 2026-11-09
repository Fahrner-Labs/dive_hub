// Equipment reminders in the web client (ADR 0034 D9): the due states' counts, a digest of the current due schedules that
// keys the logbook notice's dismissal, and the navigation's count. Due is derived, so the dismissal stays on this client.
import { useQuery } from '@tanstack/react-query';
import { equipmentDueQuery, type EquipmentDueView } from '../api.ts';

const DISMISSED_KEY = 'divehub.equipment-due-dismissed';

/** The due schedules by state; `total` is what the navigation counts. */
export function dueCounts(entries: readonly EquipmentDueView[]) {
  return {
    total: entries.length,
    overdue: entries.filter((e) => e.due.state === 'overdue').length,
    due: entries.filter((e) => e.due.state === 'due').length,
    dueSoon: entries.filter((e) => e.due.state === 'due_soon').length,
  };
}

/** A stable key of the due set: a new schedule becoming due changes it, a service that settles one does too. */
export const dueDigest = (entries: readonly EquipmentDueView[]) =>
  entries.map((entry) => entry.scheduleId).sort().join('|');

export function dismissedDueDigest(): string | null {
  try {
    return localStorage.getItem(DISMISSED_KEY);
  } catch {
    return null;
  }
}

export function dismissDue(digest: string) {
  try {
    localStorage.setItem(DISMISSED_KEY, digest);
  } catch {
    // A browser that keeps no storage shows the notice again on the next visit; nothing else depends on it.
  }
}

/** How many schedules need attention: the Equipment link's count (UI redesign 4.3). */
export function useEquipmentDueCount() {
  return useQuery(equipmentDueQuery()).data?.length ?? 0;
}
